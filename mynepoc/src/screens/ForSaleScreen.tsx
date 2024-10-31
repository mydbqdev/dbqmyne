import { View, Text, TouchableOpacity, FlatList, ScrollView, StyleSheet, Image } from 'react-native';
import React, { useCallback, useState } from 'react';
import { NavigationProp, useFocusEffect, useNavigation } from '@react-navigation/native';
import { Item } from './Items';
import ApiService from '../Api/ApiService';
import { BASE_URL } from '../../devprofile';
import { CustomHeader } from '../../App';
import useStore from '../zustand/useStore';

const ForSaleScreen = () => {
  const nav = useNavigation<NavigationProp<any>>();

  const create = () => {
    nav.navigate('Listing');
  };
  
  const yourlist = () => {
    nav.navigate('SellingScreen');
  };

  const [res, setRes] = useState([]);
const {userDetails} =useStore();
  useFocusEffect(
    useCallback(() => {
      const fetchData = async () => {
        try {
          const response = await ApiService.post(`${BASE_URL}/post/getlistings`, {
            listingType: "all",
            filterType: "all",
            userId: userDetails?.id,
            pageIndex: 0,
            pageSize: 10,
          });
          console.log('Listings fetched:', response.data);
          const uniqueData = response.data.filter(
            (item: Item, index: number, self: Item[]) =>
              index === self.findIndex((t) => t.listingId === item.listingId)
          );
          setRes(uniqueData);
        } catch (error) {
          console.error('axios', error);
        }
      };
      fetchData();
    }, [])
  );

  const renderItem = ({ item }: { item: Item }) => (
    <View style={styles.listingContainer}>
      {item.mediaPaths && item.mediaPaths.length > 0 ? (
        <Image
          source={{ uri: item.mediaPaths[0].url }}
          style={styles.listingImage}
        />
      ) : (
        <Image
          style={styles.defaultImage}
        />
      )}
      <View style={styles.listingDetails}>
        <Text style={styles.priceText}>Price: ₹{item.price}</Text>
      </View>
    </View>
  );

  return (
    <ScrollView style={styles.container}>
      <CustomHeader />
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.createButton} onPress={create}>
          <Text style={styles.buttonText}>Create a listing</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.createButton} onPress={yourlist}>
          <Text style={styles.buttonText}>Your Listings</Text>
        </TouchableOpacity>
      </View>
      <View>
        <Text style={styles.sectionHeader}>Saved Listings</Text>
        <FlatList
          data={res}
          renderItem={renderItem}
          keyExtractor={(item) => item.listingId}
        />
      </View>
    </ScrollView>
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
  listingImage: {
    height: 150,
    width: '100%',
    borderRadius: 8,
  },
  defaultImage: {
    height: 100,
    width: 100,
    alignSelf: 'center',
    marginVertical: 10,
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
