import React from 'react';
import { View, StyleSheet, Image, Dimensions, TouchableOpacity, Text } from 'react-native';
import { NavigationProp, RouteProp, useNavigation } from '@react-navigation/native';
import Video from 'react-native-video';
import Swiper from 'react-native-swiper';

// Define the type for the route parameters
type GalleryScreenRouteProp = RouteProp<{
  Gallery: {
    mediaDetails: { contentType: string; url: string }[];
    creatorName: string;
    description: string;
  };
}, 'Gallery'>;

interface Props {
  route: GalleryScreenRouteProp;
}

const GalleryScreen = ({ route }: any) => {
  const { mediaDetails } = route.params;
  const navigation = useNavigation<NavigationProp<any>>();

  const handleClose = () => {
    navigation.goBack(); // Navigate back to the previous screen
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={[styles.closeButton, { zIndex: 10 }]} onPress={handleClose}>
        <Text style={styles.closeButtonText}>X</Text>
      </TouchableOpacity>

      <Swiper style={styles.wrapper} showsButtons={true} autoplay={false} dot={<View style={styles.dot} />} activeDot={<View style={styles.activeDot} />}>
        {mediaDetails.map((item: { contentType: string; url: any; }, index: React.Key | null | undefined) => (
          <View style={styles.itemContainer} key={index}>
<Text style={styles.infoText}>
              {route.params.creatorName} 
            </Text>
            {item.contentType.startsWith('image/') ? (
              <Image source={{ uri: item.url }} style={styles.media} resizeMode="contain" />
            ) : (
              <Video source={{ uri: item.url }} style={styles.media} resizeMode="contain" controls />
            )}
            <Text style={styles.infoText}>
               {route.params.description}
            </Text>
          </View>
        ))}
      </Swiper>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
    paddingVertical: 20,
  },
  closeButton: {
    position: 'absolute',
    top: 20,
    right: 20,
    padding: 10,
    borderRadius: 20,
    backgroundColor: 'rgba(255, 255, 255, 0.3)',
  },
  infoText: {
    marginTop: 10,
    textAlign: 'center',
    color: '#fff',
  },
  closeButtonText: {
    fontSize: 20,
    color: '#fff',
  },
  itemContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    flex: 1, // Fill the swiper space
    borderRadius: 10,
    overflow: 'hidden',
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
  },
  media: {
    width: '100%',
    height: 300,
  },
  wrapper: {
    height: '100%', // Make sure swiper fills the screen
  },
  dot: {
    backgroundColor: 'rgba(255, 255, 255, 0.5)', // Dot color
    width: 8,
    height: 8,
    borderRadius: 4,
    marginHorizontal: 3,
  },
  activeDot: {
    backgroundColor: '#fff', // Active dot color
    width: 10,
    height: 10,
    borderRadius: 5,
    marginHorizontal: 3,
  },
});

export default GalleryScreen;
