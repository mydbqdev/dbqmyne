import { View, Text, TouchableOpacity } from 'react-native';
import React from 'react';
import TabBarNav from './TabBarNav';
import AntDesign from 'react-native-vector-icons/AntDesign'
import MaterialCommunityIcons
    from 'react-native-vector-icons/MaterialCommunityIcons'

const SellingScreen = () => {
    return (
        <View style={{ flex: 1 }}>
            <View className='flex-row items-center justify-between p-3'>
                <TouchableOpacity>
                    <AntDesign name='arrowleft' color={'black'} size={25} />
                </TouchableOpacity>
                <Text className='text-lg text-black font-medium'>Your Listings</Text>
                <TouchableOpacity>
                    <MaterialCommunityIcons name='chat-outline' color={'black'} size={25} />
                </TouchableOpacity>

            </View>
            <TabBarNav />
        </View>
    );
};

export default SellingScreen;