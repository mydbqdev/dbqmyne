import React from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import SplashLoginScreen from './src/screens/SplashLoginScreen';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import Toast from 'react-native-toast-message';
import { AutocompleteDropdownContextProvider } from 'react-native-autocomplete-dropdown';
import SignUp from './src/screens/signup';
import LogIn from './src/screens/signin';
import HomeScreen from './src/screens/HomeScreen';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import DiscoverScreen from './src/screens/DiscoverScreen';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import AntDesign from 'react-native-vector-icons/AntDesign';
import FontAwesome from 'react-native-vector-icons/FontAwesome'; // Import FontAwesome for icons
import ForSaleScreen from './src/screens/ForSaleScreen';
import ChatsScreen from './src/screens/ChatsScreen';
import SearchScreen from './src/screens/SearchScreen';

// Define the navigators
const Stack = createNativeStackNavigator();
const Tab = createMaterialTopTabNavigator();
const BottomTab = createMaterialBottomTabNavigator();

const App = () => {
  return (
    <AutocompleteDropdownContextProvider>
      <Toast />
      <NavigationContainer>
        <Stack.Navigator initialRouteName={'splash'}>
          <Stack.Screen
            name="splash"
            component={SplashLoginScreen}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="SignUp"
            component={SignUp}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="Login"
            component={LogIn}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="home"
            component={BottomTabs} // Use BottomTabs for main navigation
            options={{ headerShown: false }}
          />
        </Stack.Navigator>
      </NavigationContainer>
    </AutocompleteDropdownContextProvider>
  );
};

// Create a separate component for the Bottom Tab Navigator
const BottomTabs = () => {
  return (
    
    
    <BottomTab.Navigator
      initialRouteName="Home"
      activeColor="#008080"
      inactiveColor="#a1a1a1"
      barStyle={styles.bottomTabBar}
    >
      <BottomTab.Screen 
        name="Home" 
        component={HomeTabScreen} 
        options={{ 
          title: 'Home',
          tabBarIcon: ({ color }) => (
            <FontAwesome name="home" size={30} color="#008080" />
          ),
        }} 
      />
      <BottomTab.Screen 
        name="Discover" 
        component={DiscoverScreen} 
        options={{ 
          title: 'Discover',
          tabBarIcon: ({ color }) => (
            <AntDesign name="star" size={30} color="#008080" />
          ),
        }} 
      />
      <BottomTab.Screen 
        name="Search" 
        component={SearchScreen} 
        options={{ 
          title: 'Search',
          tabBarIcon: ({ color }) => (
            <AntDesign name="search1" size={30} color="#008080" />
          ),
        }} 
      />
      <BottomTab.Screen 
        name="ForSale" 
        component={ForSaleScreen} 
        options={{ 
          title: 'For Sale',
          tabBarIcon: ({ color }) => (
            <AntDesign name="tag" size={30} color="#008080" />
          ),
        }} 
      />
      <BottomTab.Screen 
        name="Chats" 
        component={ChatsScreen} 
        options={{ 
          title: 'Chats',
          tabBarIcon: ({ color }) => (
            <AntDesign name="message1" size={30} color={color} />
          ),
        }} 
      />
    </BottomTab.Navigator>
  );
};

// Create a separate component for the Top Tab Navigator
const HomeTabScreen = () => {
  return (
    <View style={{ flex: 1 }}>
      <CustomHeader />
      <Tab.Navigator
        initialRouteName="ForYou"
        screenOptions={{
          tabBarIndicatorStyle: styles.tabIndicator,
          tabBarLabelStyle: styles.tabLabel,
          tabBarActiveTintColor: '#008080',
          tabBarInactiveTintColor: '#a1a1a1',
          tabBarStyle: styles.topTabBar,
        }}
      >
        <Tab.Screen name="ForYou" component={HomeScreen} options={{ title: 'For You' }} />
        <Tab.Screen name="MyPosts" component={HomeScreen} options={{ title: 'My Posts' }} />
        <Tab.Screen name="Recent" component={HomeScreen} />
        <Tab.Screen name="Nearby" component={HomeScreen} />
        <Tab.Screen name="Trending" component={HomeScreen} />
      </Tab.Navigator>
    </View>
  );
};

// Custom header component
export const CustomHeader = () => {
  return (
    <View style={styles.headerContainer}>
      <TouchableOpacity style={styles.iconContainer}>
        <AntDesign name="user" size={30} color="black" />
      </TouchableOpacity>
      <Image
        style={styles.logo}
        source={require("./src/assets/images/Logo-2.png")}
      />
      <TouchableOpacity style={styles.iconContainer}>
        <AntDesign name="notification" size={30} color="black" />
      </TouchableOpacity>
    </View>
  );
};

// Styles
const styles = StyleSheet.create({
  headerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 10,
    backgroundColor: '#ffffff',
    elevation: 4, // Add shadow for Android
    shadowColor: '#000', // iOS shadow
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
  },
  logo: {
    width: 100, // Adjust as needed
    height: 40, // Adjust as needed
    resizeMode: 'contain',
  },
  iconContainer: {
    padding: 10,
  },
  bottomTabBar: {
    backgroundColor: '#ffffff',
    elevation: 0, // Set elevation to 0 to remove shadow
    shadowColor: 'transparent', // Ensure no shadow color
  },
  topTabBar: {
    backgroundColor: '#ffffff',
  },
  tabIndicator: {
    backgroundColor: '#008080',
  },
  tabLabel: {
    fontSize: 12,
    fontWeight: 'bold',
  },
});

export default App;
