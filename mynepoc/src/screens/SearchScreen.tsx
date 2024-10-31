import React, { useState } from 'react';
import { View, Text, TextInput, FlatList, TouchableOpacity, StyleSheet } from 'react-native';
import { CustomHeader } from '../../App';

const mockResults = [
  { id: '1', title: 'Vintage Lamp' },
  { id: '2', title: 'Mountain Bike' },
  { id: '3', title: 'Leather Jacket' },
  { id: '4', title: 'Smartphone' },
];

const SearchScreen = () => {
  const [searchText, setSearchText] = useState('');
  const [filteredResults, setFilteredResults] = useState(mockResults);

  const handleSearch = (text: string) => {
    setSearchText(text);
    setFilteredResults(
      mockResults.filter((item) =>
        item.title.toLowerCase().includes(text.toLowerCase())
      )
    );
  };

  return (
    <>
      <CustomHeader />
      <View style={styles.container}>
        <TextInput
          style={styles.searchBar}
          placeholder="Search for items..."
          value={searchText}
          onChangeText={handleSearch}
        />
        <FlatList
          data={filteredResults}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <TouchableOpacity style={styles.resultItem}>
              <Text style={styles.resultText}>{item.title}</Text>
            </TouchableOpacity>
          )}
          ListEmptyComponent={<Text style={styles.noResults}>No results found</Text>}
          contentContainerStyle={styles.resultsList}
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
  searchBar: {
    height: 40,
    marginHorizontal: 16,
    marginVertical: 8,
    borderRadius: 8,
    paddingHorizontal: 10,
    backgroundColor: '#e0e0e0',
  },
  resultsList: {
    paddingHorizontal: 16,
  },
  resultItem: {
    padding: 16,
    marginVertical: 4,
    backgroundColor: '#ffffff',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#ddd',
  },
  resultText: {
    fontSize: 16,
    color: '#333',
  },
  noResults: {
    textAlign: 'center',
    marginVertical: 20,
    fontSize: 16,
    color: '#aaa',
  },
});

export default SearchScreen;
