import React from 'react';
import SplashScreen from './src/screens/SplashScreen';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';

import { Provider } from 'react-redux';
import { Dimensions, TouchableOpacity } from 'react-native';
import AntDesign from 'react-native-vector-icons/AntDesign';
import Toast from 'react-native-toast-message';
import { AutocompleteDropdownContextProvider } from 'react-native-autocomplete-dropdown';
import SignUp from './src/screens/signup';
import LogIn from './src/screens/signin';
import SplashLoginScreen from './src/screens/SplashLoginScreen';
import HomeScreen from './src/screens/HomeScreen';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';

// Define the navigators
const Stack = createNativeStackNavigator();
const Tab = createMaterialTopTabNavigator();

export const mob = Dimensions.get('screen').width < 768;

const App = () => {
  return (
    <AutocompleteDropdownContextProvider>
      {/* <Provider store={store}> */}
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
          {/* Wrap the Tab Navigator in a Stack Screen */}
          <Stack.Screen
            name="home"
            component={HomeTabs} // Use a new component for the tabs
            options={{ headerShown: false }}
          />
        </Stack.Navigator>
      </NavigationContainer>
      {/* </Provider> */}
    </AutocompleteDropdownContextProvider>
  );
};

// Create a separate component for the Tab Navigator
const HomeTabs = () => {
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
      <Tab.Screen name="ForYou" component={HomeScreen} options={{ title: 'For you' }} />
      <Tab.Screen name="MyPosts" component={HomeScreen} options={{ title: 'My posts' }} />
      <Tab.Screen name="Recent" component={HomeScreen} />
      <Tab.Screen name="Nearby" component={HomeScreen} />
      <Tab.Screen name="Trending" component={HomeScreen} />
    </Tab.Navigator>
  );
};

export default App;
