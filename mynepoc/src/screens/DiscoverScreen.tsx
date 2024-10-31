import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { CustomHeader } from '../../App';

const categories = [
  { id: '1', name: 'Trending' },
  { id: '2', name: 'New Arrivals' },
  { id: '3', name: 'Popular Near You' },
  { id: '4', name: 'Editor’s Picks' },
];

const DiscoverScreen = () => {
  return (
    <>
      <CustomHeader />
      <View style={styles.container}>
        <Text style={styles.title}>Discover</Text>
        <FlatList
          data={categories}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <TouchableOpacity style={styles.categoryButton}>
              <Text style={styles.categoryText}>{item.name}</Text>
            </TouchableOpacity>
          )}
          contentContainerStyle={styles.list}
        />
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 10,
    backgroundColor: '#f9f9f9',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginVertical: 16,
    textAlign: 'center',
  },
  list: {
    paddingHorizontal: 16,
  },
  categoryButton: {
    padding: 16,
    marginVertical: 8,
    backgroundColor: '#008080',
    borderRadius: 8,
    alignItems: 'center',
  },
  categoryText: {
    fontSize: 16,
    color: '#fff',
  },
});

export default DiscoverScreen;
