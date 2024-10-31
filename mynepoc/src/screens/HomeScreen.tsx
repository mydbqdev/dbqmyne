import React, { useEffect, useState } from 'react';
import { StyleSheet, View, FlatList, Text, Modal, TouchableOpacity, Image } from 'react-native';
import { Button, TextInput } from 'react-native-paper';
import axios from 'axios';
import ApiService from '../Api/ApiService'; // Adjust the import path
import { BASE_URL } from '../../devprofile';
import useStore from '../zustand/useStore';
import Icon from 'react-native-vector-icons/MaterialIcons'; // Import MaterialIcons or any other icon library
import { NavigationContainer,useNavigation } from "@react-navigation/native";
import useAuthStore from '../zustand/useAuthStore';
// Define an interface for Post and MediaDetail
interface MediaDetail {
  contentType: string;
  type: string;
  url: string;
}

interface Post {
  postId: string;
  userId: string;
  creatorName: string;
  zipCode: string;
  description: string;
  likeCount: number;
  isLiked: boolean;
  commentsCount: number;
  mediaDetails: MediaDetail[];
  createdAt: string;
  updatedAt: string;
}
 
const HomeScreen = ({navigation}:any) => {
  const createpostsus = useAuthStore((state) => state.isPostSuccessful);
  const [posts, setPosts] = useState<Post[]>([]); // To store the fetched posts
  const [modalVisible, setModalVisible] = useState(false); // Modal visibility state
  const [newPost, setNewPost] = useState(''); // New post input state
  const [filter, setFilter] = useState('recent'); // Filter state
  const [pageIndex, setPageIndex] = useState(0); // Pagination state
  const [loading, setLoading] = useState(false); // Loading state
  const [hasMore, setHasMore] = useState(true); // Check if there are more posts to load
  const { userDetails } = useStore();

  // Fetch posts from API with filters
  const fetchPosts = async () => {
    if (loading || !hasMore) return; // Prevent multiple requests if already loading or no more posts

    setLoading(true);
    // Define the request body
    const requestBody = {
      filterType: "forYou",
      pageIndex: pageIndex, // Use the current page index
      pageSize: 10, // Adjust the page size as needed
      zipCode: userDetails?.zipCode, // Replace with the actual zip code or input value
    };

    try {
      const response = await ApiService.post(`${BASE_URL}/post/getPosts`, requestBody);
      console.log("Fetched Posts: ", response.data);

      // Check if response contains data
      if (response.data && response.data.length > 0) {
        setPosts((prevPosts) => [...prevPosts, ...response.data]); // Append new posts to existing ones
      } else {
        setHasMore(false); // No more posts to load
      }
    } catch (error) {
      console.error("Error fetching posts:", error);
    } finally {
      setLoading(false); // Set loading to false after fetching
    }
  };
   
  const toggleModal = () => {
    navigation.navigate("createpost");
  };
  // Create a new post
  const createPost = async () => {
    if (!newPost) return;

    try {
      await axios.post(`${BASE_URL}/posts`, { content: newPost }); // Replace with your API endpoint for creating posts
      setNewPost(''); // Clear the input
      setModalVisible(false); // Close the modal
      setPageIndex(0); // Reset to the first page after creating a post
      setPosts([]); // Clear current posts to fetch from the start
      fetchPosts(); // Refresh the post list
    } catch (error) {
      console.error("Error creating post:", error);
    }
  };


  // Like or unlike a post
  const toggleLike = async (postId: string, isLiked: boolean) => {
    try {
      // Send the like/unlike request to your API
      await ApiService.post(`${BASE_URL}/post/posts/${userDetails?.id}/${postId}/like`);
  
      // Update local state
      setPosts((prevPosts) =>
        prevPosts.map((post) =>
          post.postId === postId
            ? {
                ...post,
                isLiked: !isLiked,
                likeCount: isLiked ? post.likeCount - 1 : post.likeCount + 1,
              }
            : post
        )
      );
    } catch (error) {
      console.error("Error liking/unliking post:", error);
      // Optionally, handle error feedback here
    }
  };
  

  useEffect(() => {
    fetchPosts(); // Fetch posts on initial load
  }, [filter, pageIndex]); // Fetch when filter or page index changes

  const loadMorePosts = () => {
    if (hasMore) {
      setPageIndex((prevPageIndex) => prevPageIndex + 1); // Increment page index for the next fetch
    }
  };
 
  useEffect(() => {
    if(createpostsus===true){
    fetchPosts();
    // Fetch posts on initial load
    console.log("createpostsus");
    }
  }, [createpostsus]);
 
  return (
    <View style={styles.container}>
      <FlatList
        data={posts}
        keyExtractor={(item) => item.postId} // Use postId as key
        renderItem={({ item }: { item: Post }) => (
          <View style={styles.postContainer}>
            <Text style={styles.creatorName}>{item.creatorName}</Text>
            <Text style={styles.postText}>{item.description}</Text>
            {/* Render media details */}
            {item.mediaDetails && item.mediaDetails.map((media, index) => {
              if (media.contentType.startsWith('image/')) {
                return (
                  <Image
                    key={`${item.postId}-${media.url}-${index}`} // Unique key
                    source={{ uri: media.url }}
                    style={styles.image}
                    resizeMode="cover"
                  />
                );
              }
              return null; // Handle other media types if necessary
            })}
           <View style={styles.statsContainer}>
           <TouchableOpacity onPress={() => toggleLike(item.postId, item.isLiked)}>
  <View style={styles.likeContainer}>
    <Icon
      name="favorite"
      size={24}
      color={item.isLiked ? 'red' : 'gray'} // Change color based on like state
    />
    <Text style={[styles.statsText, item.isLiked && styles.likedText]}>
      {item.likeCount || 0} {/* Fallback to 0 */}
    </Text>
  </View>
</TouchableOpacity>

  <Text style={styles.statsText}>{item.commentsCount} Comments</Text>
</View>

          </View>
        )}
        
        onEndReached={loadMorePosts} // Load more posts on scroll to end
        onEndReachedThreshold={0.5} // Trigger when 50% of the screen is left
        ListFooterComponent={loading ? <Text>Loading more posts...</Text> : null} // Show loading indicator
      />

      {/* Floating Action Button */}
      <TouchableOpacity style={styles.fab} onPress={toggleModal}>
        <Text style={styles.fabText}>+</Text>
      </TouchableOpacity>

      {/* Modal for Creating a Post */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <TextInput
              label="New Post"
              value={newPost}
              onChangeText={setNewPost}
              style={styles.input}
            />
            <Button mode="contained" onPress={createPost}>
              Post
            </Button>
            <Button mode="outlined" onPress={() => setModalVisible(false)}>
              Cancel
            </Button>
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#f0f0f0',
  },
  postContainer: {
    padding: 10,
    marginVertical: 8,
    backgroundColor: '#fff',
    borderRadius: 5,
    elevation: 2,
  },
  creatorName: {
    fontWeight: 'bold',
    marginBottom: 4,
  },
  postText: {
    fontSize: 16,
    marginBottom: 8,
  },
  image: {
    height: 200,
    marginBottom: 8,
    borderRadius: 5, // Optional: Add rounded corners to the image
  },
  statsContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: 8,
  },
  likeContainer: {
    flexDirection: 'row',
    alignItems: 'center', // Align items vertically centered
  },
  statsText: {
    fontSize: 14,
    color: '#888',
  },
  likedText: {
    color: 'blue', // Change the color for liked state
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 16,
    width: 56,
    height: 56,
    backgroundColor: '#008080',
    borderRadius: 28,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 5,
  },
  fabText: {
    color: '#fff',
    fontSize: 28,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    width: '80%',
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 20,
    elevation: 5,
  },
  input: {
    marginBottom: 12,
  },
});

export default HomeScreen;
