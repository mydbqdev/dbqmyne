import { View, StyleSheet, TouchableOpacity, FlatList, Image, Dimensions, Text } from 'react-native';

import React from 'react';

import { RouteProp, useNavigation, useRoute } from '@react-navigation/native';

import AntDesign from 'react-native-vector-icons/AntDesign';

type FullScreenImageRouteProp = RouteProp<{ params: { mediaPaths: { url: string }[] } }, 'params'>;

const ImageScreen = () => {

    const route = useRoute<FullScreenImageRouteProp>();

    const navigation = useNavigation();

    const { mediaPaths } = route.params || { mediaPaths: [] };

    const goBack = () => {

        navigation.goBack();

    };

    return (

        <View style={styles.container}>

            <TouchableOpacity style={styles.backButton} onPress={goBack}>

                <AntDesign name="close" size={30} color="white" />

            </TouchableOpacity>

            {mediaPaths && mediaPaths.length > 0 ? (

                <FlatList

                    data={mediaPaths}

                    horizontal

                    pagingEnabled

                    keyExtractor={(item, index) => index.toString()}

                    renderItem={({ item }) => (

                        <Image source={{ uri: item.url }} style={styles.image} resizeMode="contain" />

                    )}

                />

            ) : (

                <Text style={{ color: 'white' }}>No Images Available</Text>

            )}

        </View>

    );

};

export default ImageScreen;

const styles = StyleSheet.create({

    container: {

        flex: 1,

        backgroundColor: 'black',

        alignItems: 'center',

        justifyContent: 'center',

    },

    backButton: {

        position: 'absolute',

        top: 40,

        right: 20,

        zIndex: 1,

    },

    image: {

        width: Dimensions.get('window').width,

        height: Dimensions.get('window').height,

    },

});