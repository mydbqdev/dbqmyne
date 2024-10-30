import { View, Text, Image } from 'react-native'
import React from 'react'
import { TouchableOpacity } from 'react-native'

const YourListing = () => {
    return (
        <View className='flex-1 bg-white'>
            <View className='items-center mt-36'>
                <Image
                    source={require('../assets/MYNE-Images/Frame3234377.png')}
                    style={{ height: 160, width: 100 }}
                />
                <Text>No active listings</Text>
                <Text>Post ites for sale and manage your</Text>
                <Text>listings easily here</Text>
                <TouchableOpacity className='mt-5'>
                    <Text className='bg-cyan-500 px-3.5 py-2 text-white text-base rounded-lg'>Start Selling</Text>
                </TouchableOpacity>
            </View>
        </View>
    )
}

export default YourListing