import React, { useEffect, useState } from 'react';
import { StyleSheet, View, FlatList, Text, Alert, Image } from 'react-native';
import Video from 'react-native-video'; // Import Video component
import ApiService from '../Api/ApiService'; // Adjust the import path
import { BASE_URL } from '../../devprofile';
import useStore from '../zustand/useStore';
import { CustomHeader } from '../../App';

interface MediaDetail {
  contentType: string;
  type: string;
  url: string;
}

interface Listing {
  listingId: string;
  title: string;
  description: string;
  price: number;
  mediaDetails: MediaDetail[];
}

const ForSaleScreen = () => {
  const [listings, setListings] = useState<Listing[]>([]);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const { userDetails } = useStore();
  const [pageIndex, setPageIndex] = useState(0);

  const fetchPosts = async (pageIndex: number) => {
    try {
      setLoading(true);
      const response: any = await ApiService.post(`${BASE_URL}/post/getlistings`, {
        listingType: 'forFree',
        filterType: 'all',
        userId: userDetails?.userId,
        pageIndex: pageIndex,
        pageSize: 10,
      });

      if (response && response.data) {
        const data: Listing[] = response.data;
        if (data.length > 0) {
          setListings((prevListings) => [...prevListings, ...data]);
        } else {
          setHasMore(false);
        }
      } else {
        console.error("No data found in the response");
        Alert.alert("Error", "No listings found.");
      }
    } catch (error: any) {
      console.error('Error fetching listings:', error);
      Alert.alert("Error", "Failed to fetch listings. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts(pageIndex);
  }, [pageIndex, userDetails]);

  const loadMorePosts = () => {
    if (hasMore) {
      setPageIndex((prevPageIndex) => prevPageIndex + 1); // Increment page index for the next fetch
    }
  };

  return (
    <><CustomHeader /><View style={styles.container}>
          <FlatList
              data={listings}
              keyExtractor={(item) => item.listingId} // Use listingId as key
              renderItem={({ item }) => (
                  <View style={styles.postContainer}>
                      <Text style={styles.postText}>{item.description}</Text>
                      {/* Render media details */}
                      {item.mediaDetails && item.mediaDetails.map((media, index) => {
                          if (media.contentType === 'video/mp4') { // Ensure this matches your content type
                              return (
                                  <Video
                                      key={index}
                                      source={{ uri: media.url }}
                                      style={styles.video}
                                      controls
                                      resizeMode="cover" />
                              );
                          } else if (media.contentType.startsWith('image/')) {
                              return (
                                  <Image
                                      key={`${item.listingId}-${media.url}-${index}`} // Use listingId combined with media URL for uniqueness
                                      source={{ uri: media.url }}
                                      style={styles.image}
                                      resizeMode="cover" />
                              );
                          }
                          return null; // Handle other media types if necessary
                      })}
                  </View>
              )}
              onEndReached={loadMorePosts} // Load more posts on scroll to end
              onEndReachedThreshold={0.5} // Trigger when 50% of the screen is left
              ListFooterComponent={loading ? <Text>Loading more posts...</Text> : null} // Show loading indicator
          />
      </View></>
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
});

export default ForSaleScreen;
