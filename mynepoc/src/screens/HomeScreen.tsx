import React, { useEffect, useState } from 'react';
import { StyleSheet, View, FlatList, Text, Modal, TouchableOpacity, Image } from 'react-native';
import { Button, TextInput } from 'react-native-paper';
import axios from 'axios';
import ApiService from '../Api/ApiService'; // Adjust the import path
import { BASE_URL } from '../../devprofile';

// Define an interface for Post
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

const HomeScreen = () => {
  const [posts, setPosts] = useState<Post[]>([]); // To store the fetched posts
  const [modalVisible, setModalVisible] = useState(false); // Modal visibility state
  const [newPost, setNewPost] = useState(''); // New post input state
  const [filter, setFilter] = useState('recent'); // Filter state
  const [pageIndex, setPageIndex] = useState(0); // Pagination state
  const [loading, setLoading] = useState(false); // Loading state
  const [hasMore, setHasMore] = useState(true); // Check if there are more posts to load

  // Fetch posts from API with filters
  const fetchPosts = async () => {
    if (loading || !hasMore) return; // Prevent multiple requests if already loading or no more posts

    setLoading(true);
    // Define the request body
    const requestBody = {
      filterType: "recent",
      pageIndex: pageIndex, // Use the current page index
      pageSize: 10, // Adjust the page size as needed
      zipCode: "560034", // Replace with the actual zip code or input value
    };

    try {
      const response = await ApiService.post(`${BASE_URL}/post/getPosts`, requestBody);
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
      console.error(error);
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

  return (
    <View style={styles.container}>
      <FlatList
        data={posts}
        keyExtractor={(item) => item.postId} // Use postId as key
        renderItem={({ item }: { item: Post }) => ( // Specify the type for item
          <View style={styles.postContainer}>
            <Text style={styles.creatorName}>{item.creatorName}</Text>
            <Text style={styles.postText}>{item.description}</Text>
            {/* Render media details */}
            {item.mediaDetails && item.mediaDetails.map((media, index) => {
              if (media.contentType.startsWith('image/')) {
                return (
                  <Image
                    key={index}
                    source={{ uri: media.url }}
                    style={styles.image}
                    resizeMode="cover"
                  />
                );
              }
              return null; // Handle other media types if necessary
            })}
            <View style={styles.statsContainer}>
              <Text style={styles.statsText}>{item.likeCount} Likes</Text>
              <Text style={styles.statsText}>{item.commentsCount} Comments</Text>
            </View>
          </View>
        )}
        onEndReached={loadMorePosts} // Load more posts on scroll to end
        onEndReachedThreshold={0.5} // Trigger when 50% of the screen is left
        ListFooterComponent={loading ? <Text>Loading more posts...</Text> : null} // Show loading indicator
      />

      {/* Floating Action Button */}
      <TouchableOpacity style={styles.fab} onPress={() => setModalVisible(true)}>
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
  video: {
    height: 200,
    marginBottom: 8,
  },
  image: {
    height: 200,
    marginBottom: 8,
    borderRadius: 5, // Optional: Add rounded corners to the image
  },
  statsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 8,
  },
  statsText: {
    fontSize: 14,
    color: '#888',
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 16,
    width: 56,
    height: 56,
    backgroundColor: '#6200ea',
    borderRadius: 28,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 8,
  },
  fabText: {
    color: '#fff',
    fontSize: 24,
    lineHeight: 56,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    width: '80%',
    padding: 20,
    backgroundColor: 'white',
    borderRadius: 10,
    elevation: 5,
  },
  input: {
    marginBottom: 20,
  },
});

export default HomeScreen;

