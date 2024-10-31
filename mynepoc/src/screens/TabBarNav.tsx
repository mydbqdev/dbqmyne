import { View, Text, Dimensions } from 'react-native';
import React from 'react';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import YourListing from './YourListing';
import Listing from './Listing';
import ExpiredListingsScreen from './ExpiredListingsScreen';

const TopTab = createMaterialTopTabNavigator();

const TabBarNav = () => {
    const windowWidth = Dimensions.get('window').width;
    const labelSize = windowWidth < 768 ? 12 : 22;

    return (
        <TopTab.Navigator
            initialRouteName="Selling"
            screenOptions={{ tabBarStyle: { backgroundColor: '#f2f2f2' } }}
        >
            <TopTab.Screen
                name="Selling"
                component={YourListing}
                options={{
                    tabBarLabelStyle: {
                        color: 'black',
                        fontWeight: '700',
                        fontSize: labelSize,
                    },
                }}
            />
         
            <TopTab.Screen
                name="Expired"
                component={ExpiredListingsScreen}
                options={{
                    tabBarLabelStyle: {
                        color: 'black',
                        fontWeight: '700',
                        fontSize: labelSize,
                    },
                }}
            />
          
        </TopTab.Navigator>
    );
};

export default TabBarNav;