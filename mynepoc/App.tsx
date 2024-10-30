import React from 'react';
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
      activeColor="#6200ea"
      inactiveColor="#a1a1a1"
      barStyle={{ backgroundColor: '#ffffff' }}
    >
      <BottomTab.Screen name="Home" component={HomeTabScreen} options={{ title: 'Home' }} />
      <BottomTab.Screen name="Discover" component={DiscoverScreen} />
      <BottomTab.Screen name="Search" component={HomeScreen} />
      <BottomTab.Screen name="ForSale" component={DiscoverScreen} options={{ title: 'For Sale' }} />
      <BottomTab.Screen name="Chats" component={HomeScreen} options={{ title: 'Chats' }} />
    </BottomTab.Navigator>
  );
};

// Create a separate component for the Top Tab Navigator
const HomeTabScreen = () => {
  return (
    <Tab.Navigator
      initialRouteName="ForYou"
      screenOptions={{
        tabBarIndicatorStyle: { backgroundColor: '#6200ea' },
        tabBarLabelStyle: { fontSize: 12, fontWeight: 'bold' },
        tabBarActiveTintColor: '#6200ea',
        tabBarInactiveTintColor: '#a1a1a1',
        tabBarStyle: { backgroundColor: '#ffffff' },
      }}
    >
      <Tab.Screen name="ForYou" component={HomeScreen} options={{ title: 'For You' }} />
      <Tab.Screen name="MyPosts" component={HomeScreen} options={{ title: 'My Posts' }} />
      <Tab.Screen name="Recent" component={HomeScreen} />
      <Tab.Screen name="Nearby" component={HomeScreen} />
      <Tab.Screen name="Trending" component={HomeScreen} />
    </Tab.Navigator>
  );
};

export default App;
