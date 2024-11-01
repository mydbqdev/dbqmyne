import React, { useCallback, useEffect, useState } from 'react';
import { StyleSheet, View, FlatList, Text, Modal, TouchableOpacity, Image, RefreshControl } from 'react-native';
import { Button } from 'react-native-paper';
import Video from 'react-native-video';
import axios from 'axios';
import ApiService from '../Api/ApiService';
import { BASE_URL } from '../../devprofile';
import useStore from '../zustand/useStore';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { NavigationContainer, NavigationProp, useFocusEffect, useNavigation } from "@react-navigation/native";
import useAuthStore from '../zustand/useAuthStore';

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
  adsHyperLink?: string; 
}

const HomeScreen = ({ filterType }: any) => {
  const createpostsus = useAuthStore((state) => state.isPostSuccessful);
  const [posts, setPosts] = useState<Post[]>([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [mediaModalVisible, setMediaModalVisible] = useState(false);
  const [selectedMedia, setSelectedMedia] = useState<MediaDetail | null>(null);
  const [selectedDescription, setSelectedDescription] = useState('');
  const [creatorName, setCreatorName] = useState('');
  const [newPost, setNewPost] = useState('');
  const [filter, setFilter] = useState('recent');
  const [pageIndex, setPageIndex] = useState(0);
  const [loading, setLoading] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const { userDetails } = useStore();
  const navigation = useNavigation<NavigationProp<any>>();
  const setPostSuccess = useAuthStore((state) => state.setPostSuccess);
  const fetchPosts = async () => {
    if (loading || !hasMore) return;

    setLoading(true);
    const requestBody = {
        filterType: filterType,
        pageIndex: pageIndex,
        pageSize: 10,
        ...(filterType === "myPost"
            ? { userId: userDetails?.id, zipCode: userDetails?.zipCode }
            : { zipCode: userDetails?.zipCode }),
    };

    console.log("POST: ", requestBody);

    try {
        const response = await ApiService.post<Post[]>(`${BASE_URL}/post/getPosts`, requestBody);
        if (response.data && response.data.length > 0) {
            // Filter out posts that have adsHyperLink
            const filteredPosts = response.data.filter(post => !post.adsHyperLink);

            // Optionally, if you want to keep other properties but ignore adsHyperLink
            // const filteredPosts = response.data.map(({ adsHyperLink, ...post }: Post) => post);

            setPosts((prevPosts) => [...prevPosts, ...filteredPosts]);
        } else {
            setHasMore(false);
        }
    } catch (error) {
        console.error("Error fetching posts:", error);
    } finally {
        setLoading(false);
    }
};


   
  const toggleModal = () => {
    navigation.navigate("createpost");
  };

  const onRefresh = async () => {
    setRefreshing(true);
    setPageIndex(0);
    setPosts([]);
    setHasMore(true);
    await fetchPosts();
    setRefreshing(false);
  };

  const toggleLike = async (postId: string, isLiked: boolean) => {
    try {
      await ApiService.post(`${BASE_URL}/post/posts/${userDetails?.id}/${postId}/like`);
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
    }
  };

  const openMediaModal = (createrName:string,media: MediaDetail, description: string) => {
    setCreatorName(createrName);
    setSelectedMedia(media);
    setSelectedDescription(description);
    setMediaModalVisible(true);
  };

  useEffect(() => {
    fetchPosts();
  }, [filter, pageIndex, filterType]);

  const loadMorePosts = () => {
    if (hasMore) {
      setPageIndex((prevPageIndex) => prevPageIndex + 1);
    }
  };
  useFocusEffect(
    useCallback(() => {
      if (createpostsus) {
        fetchPosts(); // Refresh posts with myPost filter when returning from CreatePost
        setPostSuccess(false); // Reset the post success state
      }
    }, [createpostsus])
  );

  useEffect(() => {
    if (createpostsus === true) {
      fetchPosts();
    }
  }, [createpostsus,filterType]);
 
  return (
    <View style={styles.container}>
      <FlatList
        data={posts}
        keyExtractor={(item) => item.postId}
        renderItem={({ item }: { item: Post }) => (
          <View style={styles.postContainer}>
            <Text style={styles.creatorName}>{item.creatorName}</Text>
            <Text style={styles.postText}>{item.description}</Text>
            {item.mediaDetails &&
              item.mediaDetails.map((media, index) => {
                if (media.contentType.startsWith('image/')) {
                  return (
                    <TouchableOpacity key={`${item.postId}-${media.url}-${index}`} onPress={() => openMediaModal(item.creatorName,media, item.description)}>
                      <Image
                        source={{ uri: media.url }}
                        style={styles.image}
                        resizeMode="cover"
                      />
                    </TouchableOpacity>
                  );
                } else if (media.contentType.startsWith('video/')) {
                  return (
                    <TouchableOpacity key={`${item.postId}-${media.url}-${index}`} onPress={() => openMediaModal(item.creatorName,media, item.description)}>
                      <Video
                        source={{ uri: media.url }}
                        style={styles.video}
                        resizeMode="cover"
                        controls={true}
                      />
                    </TouchableOpacity>
                  );
                }
                return null;
              })}
            <View style={styles.statsContainer}>
              <TouchableOpacity onPress={() => toggleLike(item.postId, item.isLiked)}>
                <View style={styles.likeContainer}>
                  <Icon
                    name="favorite"
                    size={24}
                    color={item.isLiked ? 'red' : 'gray'}
                  />
                  <Text style={[styles.statsText, item.isLiked && styles.likedText]}>
                    {item.likeCount || 0}
                  </Text>
                </View>
              </TouchableOpacity>
              <Text style={styles.statsText}>{item.commentsCount} Comments</Text>
            </View>
          </View>
        )}
        onEndReached={loadMorePosts}
        onEndReachedThreshold={0.5}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        ListFooterComponent={loading ? <Text>Loading posts...</Text> : null}
      />

      <TouchableOpacity style={styles.fab} onPress={toggleModal}>
        <Text style={styles.fabText}>+</Text>
      </TouchableOpacity>

      {/* Modal for Viewing Selected Media */}
      <Modal
        animationType="slide"
        transparent={true}
        visible={mediaModalVisible}
        onRequestClose={() => setMediaModalVisible(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
          <Text style={styles.modalDescription}>{creatorName}</Text>
            {selectedMedia?.contentType.startsWith('image/') ? (
              <Image source={{ uri: selectedMedia.url }} style={styles.modalImage} resizeMode="contain" />
            ) : (
              <Video source={{ uri: selectedMedia?.url }} style={styles.modalVideo} resizeMode="contain" controls />
            )}
            <Text style={styles.modalDescription}>{selectedDescription}</Text>
            <Button mode="outlined" onPress={() => setMediaModalVisible(false)}>Close</Button>
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
    borderRadius: 5,
  },
  video: {
    height: 200,
    marginBottom: 8,
    borderRadius: 5,
  },
  statsContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginTop: 8,
  },
  likeContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  statsText: {
    fontSize: 14,
    color: '#888',
  },
  likedText: {
    color: 'blue',
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
  },
  fabText: {
    color: '#fff',
    fontSize: 24,
  },
  modalContainer: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.7)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalContent: {
    width: '90%',
    padding: 16,
    backgroundColor: '#fff',
    borderRadius: 8,
    alignItems: 'center',
  },
  modalImage: {
    width: '100%',
    height: 300,
    borderRadius: 8,
  },
  modalVideo: {
    width: '100%',
    height: 300,
    borderRadius: 8,
  },
  modalDescription: {
    fontSize: 16,
    marginTop: 10,
    textAlign: 'center',
  },
});

export default HomeScreen;
