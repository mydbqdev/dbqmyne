import { View, Text, ScrollView, Image, StyleSheet, TouchableOpacity } from 'react-native'
import React from 'react'
import { NavigationProp, RouteProp, useNavigation, useRoute } from '@react-navigation/native';
import { Item } from './Items';
import AntDesign from 'react-native-vector-icons/AntDesign'
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons'
type ListingDetailsScreenRouteProp = RouteProp<{ params: { item: any } }, 'params'>;

const ListingDetails = () => {
    const nav = useNavigation<NavigationProp<any>>()

    const route = useRoute<ListingDetailsScreenRouteProp>();
    const { item } = route.params;
    console.log(item, "ittttt");
    const back = () => {
        nav.goBack()
    }
    const createdAtDate = new Date(item.createdAt);
    const currentTime = new Date();
    const timeDifferenceMs = currentTime.getTime() - createdAtDate.getTime();

    let timeAgo;
    const minutesSinceCreated = Math.floor(timeDifferenceMs / (1000 * 60));
    const hoursSinceCreated = Math.floor(timeDifferenceMs / (1000 * 60 * 60));
    const daysSinceCreated = Math.floor(timeDifferenceMs / (1000 * 60 * 60 * 24));

    if (minutesSinceCreated < 60) {
        timeAgo = minutesSinceCreated === 1 ? `${minutesSinceCreated} minute ago` : `${minutesSinceCreated} minutes ago`;
    } else if (hoursSinceCreated < 24) {
        timeAgo = hoursSinceCreated === 1 ? `${hoursSinceCreated} hour ago` : `${hoursSinceCreated} hours ago`;
    } else {
        timeAgo = daysSinceCreated === 1 ? `${daysSinceCreated} day ago` : `${daysSinceCreated} days ago`;
    }

    const openFullScreenImage = () => {
        nav.navigate('ImageScreen', { mediaPaths: item.mediaPaths });
    };
    

    return (
        <ScrollView contentContainerStyle={styles.container}>
            <View className='flex-row items-center mb-2'>
                <TouchableOpacity onPress={back}>
                    <AntDesign name='arrowleft' color={'black'} size={25} />
                </TouchableOpacity>
            </View>
            {item.mediaPaths && item.mediaPaths.length > 0 && (
                <TouchableOpacity onPress={openFullScreenImage}>
                    <Image
                        source={{ uri: item.mediaPaths[0].url }}
                        style={styles.image}
                        resizeMode="cover"
                    />
                </TouchableOpacity>
            )}
  {item.mediaPaths && item.mediaPaths.length > 1 && (
                <TouchableOpacity onPress={() => openFullScreenImage()} style={styles.seeMoreButton}>
                    <Text style={styles.seeMoreText}>See More</Text>
                </TouchableOpacity>
            )}
            <Text style={styles.title}>{item.title}</Text>
            <View className='flex-row items-center'>
                <Text className='text-black text-lg'>₹ {item.price}</Text>
            </View>
            <View className='bg-gray-100 p-2 rounded-md'>
                <View className='flex-row items-center'>
                    <MaterialCommunityIcons name='account-circle-outline' size={20} color={'black'} />
                    <Text className='text-black text-lg ml-2 font-bold'>{item.creatorName}</Text>
                </View>
                <Text className='text-gray-500 text-lg ml-8'>{item.description}</Text>
                <View className='items-end'>
                    <Text>{timeAgo}</Text>
                </View>
            </View>
        </ScrollView>
    )
}

export default ListingDetails



const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: 'white'
    },
    title: {
        fontSize: 24,
        fontWeight: '500',
        marginBottom: 1,
        color: 'black'
    },
    image: {
        width: '100%',
        height: 200,
        borderRadius: 10,
        marginBottom: 20,
    },
    sectionTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginTop: 10,
        marginBottom: 5,
    },
    description: {
        fontSize: 16,
        color: '#555',
        marginBottom: 10,
    },
    text: {
        fontSize: 16,
        color: '#333',
        marginBottom: 10,
    },
    price: {
        fontSize: 20,
        fontWeight: 'bold',
        color: 'green',
        marginBottom: 10,
    },
    discount: {
        fontSize: 18,
        fontWeight: 'bold',
        color: 'red',
        marginBottom: 10,
    },
    
    seeMoreButton: {
        alignItems: 'center',
        marginVertical: 10,
    },
    seeMoreText: {
        color: '#007BFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
})