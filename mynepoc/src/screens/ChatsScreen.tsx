import { NavigationProp, useNavigation } from '@react-navigation/native';
import React, { useState } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet } from 'react-native';
import AntDesign from 'react-native-vector-icons/AntDesign';
import { CustomHeader } from '../../App';
interface Chat {
    id: string;
    name: string;
    lastMessage: string;
  }
const ChatsScreen = () => {
    const navigation = useNavigation<NavigationProp<any>>();
  const [chats, setChats] = useState([
    { id: '1', name: 'John Doe', lastMessage: 'Hey! How are you?' },
    { id: '2', name: 'Jane Smith', lastMessage: 'Let’s catch up soon.' },
    { id: '3', name: 'Sam Wilson', lastMessage: 'Check out this new place!' },
    { id: '4', name: 'Emma Brown', lastMessage: 'Are you coming today?' },
  ]);

  const renderItem =({ item }: { item: Chat }) => (
    <TouchableOpacity
      style={styles.chatItem}
      onPress={() => navigation.navigate('ChatDetails', { chatId: item.id, chatName: item.name })}
    >
      <View style={styles.chatIcon}>
        <AntDesign name="user" size={30} color="#008080" />
      </View>
      <View style={styles.chatDetails}>
        <Text style={styles.chatName}>{item.name}</Text>
        <Text style={styles.lastMessage}>{item.lastMessage}</Text>
      </View>
      <AntDesign name="right" size={20} color="#a1a1a1" />
    </TouchableOpacity>
  );

  return (
    <><CustomHeader /><View style={styles.container}>
          <Text style={styles.header}>Chats</Text>
          <FlatList
              data={chats}
              keyExtractor={(item) => item.id}
              renderItem={renderItem}
              contentContainerStyle={styles.chatList} />
      </View></>
  );
};

export default ChatsScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#ffffff',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#008080',
    padding: 16,
  },
  chatList: {
    padding: 16,
  },
  chatItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f9f9f9',
    padding: 16,
    borderRadius: 10,
    marginBottom: 10,
  },
  chatIcon: {
    marginRight: 16,
  },
  chatDetails: {
    flex: 1,
  },
  chatName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  lastMessage: {
    fontSize: 14,
    color: '#a1a1a1',
  },
});
