import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import useStore from '../zustand/useStore';

const ProfileScreen = () => {
  const navigation = useNavigation<NavigationProp<any>>();
  const { userDetails } = useStore();

  const handleLogout = () => {
    navigation.navigate('Login'); // Navigate back to the login screen after logout
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Profile</Text>
      <View style={styles.infoContainer}>
        <Text style={styles.value}>{userDetails?.userFirstName} {userDetails?.userLastName}</Text>
        <Text style={styles.value}>{userDetails?.zipCode}</Text>
      </View>
      <TouchableOpacity style={styles.logoutButton} onPress={handleLogout}>
        <Text style={styles.logoutButtonText}>Logout</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f7f9fc', // Lighter background for a fresh look
  },
  header: {
    fontSize: 32, // Larger font for the header
    fontWeight: 'bold',
    color: '#333', // Darker color for contrast
    marginBottom: 20,
    textAlign: 'center',
    borderBottomWidth: 2, // Underline for the header
    borderBottomColor: '#008080', // Color matching the button
    paddingBottom: 10, // Space between header and underline
  },
  infoContainer: {
    backgroundColor: '#ffffff',
    borderRadius: 15,
    padding: 30,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.2,
    shadowRadius: 10,
    elevation: 5, // For Android shadow
    marginBottom: 40, // Increased space from logout button
    alignItems: 'center',
  },
  value: {
    fontSize: 22, // Slightly larger font for the value text
    color: '#495057',
    marginBottom: 15, // Increased margin for better separation
    textAlign: 'center',
  },
  logoutButton: {
    backgroundColor: '#008080', // Blue color for the button
    paddingVertical: 15, // More padding for a larger button
    borderRadius: 15,
    alignItems: 'center',
    marginTop: 20,
    width: '100%', // Make the button full-width
  },
  logoutButtonText: {
    color: '#ffffff',
    fontSize: 20, // Larger font size for better readability
    fontWeight: 'bold',
  },
});

export default ProfileScreen;
