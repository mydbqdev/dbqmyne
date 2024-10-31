import { View, Text, Image, RefreshControl, ScrollView } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { TouchableOpacity } from 'react-native'
import { NavigationProp, useFocusEffect, useNavigation } from '@react-navigation/native'
import ApiService from '../Api/ApiService'
import { BASE_URL } from '../../devprofile'
import useAuthStore from '../zustand/useAuthStore'
import Items from './Items'
import useStore from '../zustand/useStore'

const YourListing = () => {
    const nav = useNavigation<NavigationProp<any>>()
    const [data, setData] = useState([]);
    const [refreshing, setRefreshing] = useState(false);  // For refreshing state

    const navi = () => {
        nav.navigate('Listing')
    }
    const { token } = useAuthStore.getState();
    console.log(token);
    const {userDetails} =useStore();

    const fetchData = async () => {
        try {
            setRefreshing(true);
            const response = await ApiService.post(`${BASE_URL}/post/getlistings`,
                {
                    listingType: "forFree",  
                    filterType: "all", 
                    userId: userDetails?.id,           
                    pageIndex: 0,
                    pageSize: 10
                }
            )
            setData(response.data)
            console.log('Listings fetched:', response.data)
        } catch (error) {
            console.error('axios', error)
        } finally {
            setRefreshing(false);  // End refreshing
        }
    }
    useFocusEffect(
        useCallback(() => {
            fetchData()
        }, [data])
    )

    const onRefresh = () => {
        fetchData();  // Fetch data on pull-to-refresh
    }

    console.log(data, 'data');

    return (
        <ScrollView className='flex-1 bg-white'
            refreshControl={
                <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
            }>
            {data && data.length > 0 ? (
                <Items />
            ) : (
                <View className=' '>
                    <View className='items-center mt-36'>
                        <Image
                            // source={require('../assets/MYNE-Images/Frame3234377.png')}
                            style={{ height: 160, width: 100 }}
                        />
                        <Text>No active listings</Text>
                        <Text>Post ites for sale and manage your</Text>
                        <Text>listings easily here</Text>
                        <TouchableOpacity className='mt-5' onPress={navi}>
                            <Text className='bg-cyan-500 px-3.5 py-2 text-white text-base rounded-lg'>Start Selling</Text>
                        </TouchableOpacity>
                    </View>
                </View >
            )}
        </ScrollView >
    )
}

export default YourListing