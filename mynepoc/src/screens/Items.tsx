import { View, Text, ScrollView, FlatList, TouchableOpacity } from 'react-native'
import React, { useCallback, useState } from 'react'
import { Image } from 'react-native'
import { useFocusEffect } from '@react-navigation/native'
import ApiService from '../Api/ApiService'
import { BASE_URL } from '../../devprofile'
import useAuthStore from '../zustand/useAuthStore'
import Entypo from 'react-native-vector-icons/Entypo'
import useStore from '../zustand/useStore'

export interface Item {
    mediaPaths: { url: string }[]; // Updated to reflect the object structure for mediaPaths
    title: string;
    description: string;
    price: number; // Assuming price is a number
    pickupLocation: string;
    listingId: string
}

const { token } = useAuthStore.getState();
console.log(token);

const Items = () => {
    const [res, setRes] = useState([])
    const {userDetails} =useStore();
    useFocusEffect(
        useCallback(() => {
            const fetchData = async () => {
                try {
                    const response = await ApiService.post(`${BASE_URL}/post/getlistings`,
                        {
                            listingType: "forFree",
                            filterType: "all",
                            userId: userDetails?.id,
                            pageIndex: 0,
                            pageSize: 10
                        }
                    )
                    console.log('Listings fetched:', response.data)
                    // setres(response.data)
                    const uniqueData = response.data.filter((item: Item, index: number, self: Item[]) =>
                        index === self.findIndex((t) => t.listingId === item.listingId)
                    );

                    setRes(uniqueData);
                } catch (error) {
                    console.error('axios', error)
                }
            }
            console.log(res);

            fetchData()
        }, [])
    )

    const renderItem = ({ item }: { item: Item }) => (
        <View style={{borderColor:'gray',borderWidth:0.3 }}>
            <View style={{ flexDirection: 'row', marginBottom: 10}}>
                <View style={{ marginVertical: 10 }}>
                    {item.mediaPaths && item.mediaPaths.length > 0 ? (
                        <Image
                            source={{ uri: item.mediaPaths[0].url }}
                            style={{ height: 130, width: 200, marginRight: 20, borderRadius: 20 }}
                        />
                    ) : (
                        <Image
                            // source={require('../assets/images/No_Image_Available.jpg')}
                            style={{ height: 100, width: 100, marginRight: 20 }}
                        />
                    )}
                </View>

                <View style={{ flex: 1, marginTop: 10 }}>
                    {/* Title Row with Three Dots */}
                    <View style={{ flexDirection: 'row', justifyContent: 'space-between' }}>
                        <Text style={{ fontWeight: 'bold', fontSize: 16 }}>{item.title}</Text>
                        <Entypo name="dots-three-vertical" size={20} />
                    </View>

                    {/* Price */}
                    <Text style={{ fontWeight: 'bold', marginTop: 5 }}>Price: ₹{item.price}</Text>
                </View>
            </View>
            <View style={{ flexDirection: 'row', justifyContent: 'space-evenly',marginBottom:5 }}>
                <TouchableOpacity className='bg-gray-200 px-8 py-3 rounded-lg'>
                    <Text className='text-black font-medium text-base'>Mark Sold</Text>
                </TouchableOpacity>
                <TouchableOpacity className='bg-gray-200 px-6 py-3 rounded-lg'>
                    <Text className='text-black font-medium text-base' >Share</Text>
                </TouchableOpacity>
            </View>
        </View>
    );






    return (
        <View className='flex-row'>
            <FlatList
                data={res}
                renderItem={renderItem}
                keyExtractor={(item) => item.listingId}
            />

        </View>
    )
}

export default Items