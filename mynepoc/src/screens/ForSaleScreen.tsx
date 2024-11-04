import { View, Text, TouchableOpacity, FlatList, ScrollView, StyleSheet, Image, ActivityIndicator } from 'react-native';
import React, { useCallback, useState } from 'react';
import { NavigationProp, useFocusEffect, useNavigation } from '@react-navigation/native';
import ApiService from '../Api/ApiService';
import { BASE_URL } from '../../devprofile';
import { CustomHeader } from '../../App';
import useStore from '../zustand/useStore';

// Define a type for the listing item
interface ListingItem {
  listingId: string;
  price: number;
  mediaPaths: { url: string }[];
}

const ForSaleScreen = () => {
  const nav = useNavigation<NavigationProp<any>>();
  const create = () => nav.navigate('Listing');
  const yourlist = () => nav.navigate('SellingScreen');

  const [listings, setListings] = useState<ListingItem[]>([]);
  const [pageIndex, setPageIndex] = useState(0);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const { userDetails } = useStore();

  const fetchListings = async () => {
    if (loading || !hasMore) return;

    setLoading(true);
    try {
      const response = await ApiService.post(`${BASE_URL}/post/getlistings`, {
        listingType: 'all',
        filterType: 'all',
        userId: userDetails?.id,
        pageIndex,
        pageSize: 10,
      });
      const newData: ListingItem[] = response.data;
      
      if (newData.length > 0) {
        setListings((prevListings) => [...prevListings, ...newData]);
        setPageIndex(pageIndex + 1);
      } else {
        setHasMore(false);
      }
    } catch (error) {
      console.error('Error fetching listings:', error);
    }
    setLoading(false);
  };

  useFocusEffect(
    useCallback(() => {
      setListings([]);  // Reset the listings on component focus
      setPageIndex(0);
      setHasMore(true);
      fetchListings();
    }, [])
  );

  const renderItem = ({ item }: { item: ListingItem }) => (
    <TouchableOpacity onPress={() => nav.navigate('ListingDetails', { item })}>
      <View style={styles.listingContainer}>
        {item.mediaPaths && item.mediaPaths.length > 0 ? (
          <Image source={{ uri: item.mediaPaths[0].url }} style={styles.listingImage} />
        ) : (
          <Image style={styles.gridImage} />
        )}
        <View style={styles.listingDetails}>
          <Text style={styles.priceText}>Price: ₹{item.price}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <CustomHeader />
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.createButton} onPress={create}>
          <Text style={styles.buttonText}>Create a listing</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.createButton} onPress={yourlist}>
          <Text style={styles.buttonText}>Your Listings</Text>
        </TouchableOpacity>
      </View>
   <View className=''>
   <Text style={styles.sectionHeader}>Popular Listings</Text>

        <View className='flex-row justify-evenly'>
          <View  >
            <TouchableOpacity className='flex-row items-center mb-3 bg-green-100 rounded-md px-4 py-2'>
              <Text>Hone Decor</Text>
              <Image
                source={require('../assets/images/202938654_10852128.png')}
                style={{ height: 50, width: 50 }}
              />
            </TouchableOpacity>
            <TouchableOpacity className='flex-row items-center bg-blue-100 rounded-md px-4 py-2'>
              <Text className='mr-3'>Furniture</Text>
              <Image
                source={require('../assets/images/358181730_11444651.png')}
                style={{ height: 50, width: 50 }}
              />
            </TouchableOpacity>
          </View>

          <View >
            <TouchableOpacity className='flex-row items-center mb-3 bg-pink-100 rounded-md px-4 py-2'>
              <Text>Baby & Kids</Text>
              <Image
                source={require('../assets/images/65a3713e-0e20-4b5e-9c1a-81a0f6f1f5c9.png')}
                style={{ height: 50, width: 50 }}
              />
            </TouchableOpacity>
            <TouchableOpacity className='flex-row items-center bg-cyan-100 rounded-md px-4 py-2'>
              <Text>Electronics</Text>
              <Image
                source={require('../assets/images/fd83e35d-5ba1-435d-b290-82377056132e.png')}
                style={{ height: 50, width: 50 }}
              />
            </TouchableOpacity>
          </View>
        </View>
      </View>
      <Text style={styles.sectionHeader}>Listings</Text>
      <FlatList
        data={listings}
        renderItem={renderItem}
        keyExtractor={(item) => item.listingId}
        onEndReached={fetchListings}
        onEndReachedThreshold={0.5}
        ListFooterComponent={
          loading ? <ActivityIndicator size="small" color="#05838b" /> : null
        }
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    marginVertical: 15,
  },
  createButton: {
    backgroundColor: '#05838b',
    borderRadius: 8,
    paddingVertical: 8,
    paddingHorizontal: 20,
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: '600',
  },
  sectionHeader: {
    fontSize: 18,
    fontWeight: '600',
    color: 'black',
    paddingHorizontal: 20,
    paddingVertical: 10,
  },
  listingContainer: {
    borderColor: 'gray',
    borderWidth: 0.3,
    marginHorizontal: 20,
    marginBottom: 15,
    borderRadius: 8,
    overflow: 'hidden',
    backgroundColor: '#f9f9f9',
  },
  // Style for carousel images
  listingImage: {
    height: 150,
    width: '100%',
    borderRadius: 8,
  },
  // Style for grid layout
  imageGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
  },
  gridImage: {
    width: '48%',
    height: 75,
    margin: 2,
    borderRadius: 8,
  },
  listingDetails: {
    padding: 10,
  },
  priceText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#333',
  },
});


export default ForSaleScreen;
