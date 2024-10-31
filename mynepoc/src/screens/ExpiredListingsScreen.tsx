import React, { useState } from 'react';
import { View, Text, FlatList, StyleSheet, TouchableOpacity, Image } from 'react-native';

const ExpiredListingsScreen = () => {
  // Mock data for expired listings
  const [expiredListings, setExpiredListings] = useState([
    { id: '1', title: 'Vintage Sofa', price: 1500, imageUrl: 'https://via.placeholder.com/150' },
    { id: '2', title: 'Old Bicycle', price: 800, imageUrl: 'https://via.placeholder.com/150' },
    { id: '3', title: 'Antique Clock', price: 1200, imageUrl: 'https://via.placeholder.com/150' },
  ]);

  const renderItem = ({ item }: { item: { id: string; title: string; price: number; imageUrl: string } }) => (
    <View style={styles.listingItem}>
      <Image source={{ uri: item.imageUrl }} style={styles.listingImage} />
      <View style={styles.listingDetails}>
        <Text style={styles.listingTitle}>{item.title}</Text>
        <Text style={styles.listingPrice}>Price: ₹{item.price}</Text>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Expired Listings</Text>
      {expiredListings.length === 0 ? (
        <Text style={styles.noListings}>No expired listings available.</Text>
      ) : (
        <FlatList
          data={expiredListings}
          renderItem={renderItem}
          keyExtractor={(item) => item.id}
        />
      )}
    </View>
  );
};

export default ExpiredListingsScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
    color: '#333',
  },
  noListings: {
    fontSize: 16,
    textAlign: 'center',
    color: '#888',
  },
  listingItem: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 15,
    marginVertical: 8,
    backgroundColor: '#f9f9f9',
    borderRadius: 8,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 10,
    elevation: 5,
  },
  listingImage: {
    width: 60,
    height: 60,
    borderRadius: 8,
    marginRight: 15,
  },
  listingDetails: {
    flex: 1,
  },
  listingTitle: {
    fontSize: 18,
    fontWeight: '600',
    color: '#333',
  },
  listingPrice: {
    fontSize: 16,
    color: '#888',
    marginTop: 4,
  },
});
