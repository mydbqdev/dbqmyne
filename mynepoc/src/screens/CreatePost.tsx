import * as React from "react";
import { StyleSheet, View, Text, Image, TextInput, TouchableOpacity, Dimensions, Alert } from "react-native";
import { FontSize, FontFamily, Color } from "../assets/globalstyles";
import { launchImageLibrary, ImageLibraryOptions } from 'react-native-image-picker';
import { NavigationContainer, useNavigation } from "@react-navigation/native";
import ApiService from '../Api/ApiService';
import { BASE_URL } from "../../devprofile";
import useAuthStore from "../zustand/useAuthStore";
import useStore from "../zustand/useStore";
import { showToast } from "./ToastUtils";
import HomeScreen from './HomeScreen';
// Get device dimensions
const { width, height } = Dimensions.get('window');
 
const CreatePost = ({navigation}: any) => {
  const { setPostSuccess } = useAuthStore();
  const userDetails = useStore(state => state.userDetails);
  const [description, setDescription] = React.useState<string>("");
  const [media, setMedia] = React.useState<any[]>([]);
 
  const handlePost = async () => {
    if (!description && media.length === 0) {
      console.log('Please add a description or media before posting');
      return;
    }
 
    const formData = new FormData();
 
    // Add media files to FormData directly as File objects
    media.forEach((file, index) => {
      formData.append('files', {
        uri: file.uri,
        type: file.type,
        name: file.fileName || `media_${index}`,
      });
    });
 
    // Add post info as a JSON string
    const postInfo = {
      zipCode: userDetails?.zipCode,
      userId:userDetails?.id ,
      description,
      privacy: "public",
    };
    formData.append('postInfo', JSON.stringify(postInfo));
 
    try {
      const response = await ApiService.post(`${BASE_URL}/post/posts/save`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
 
      if (response.status === 200) {
        console.log(response.status); // Success message
        Alert.alert("posted suscessfully");
        setPostSuccess(true);
        navigation.navigate('home', {
        screen: 'Home',
        params: {
          screen: 'ForYou',
        },
      });
       
       
      }
    } catch (error) {
      console.error('Error creating post:', error);
    }
  };
 
 
 
  const selectMedia = () => {
    const options: ImageLibraryOptions = {
      mediaType: 'mixed',
      selectionLimit: 10,
    };
 
    launchImageLibrary(options, (response: any) => {
      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      } else if (response.assets) {
        setMedia(prevMedia => [...prevMedia, ...response.assets]);
      }
    });
  };
 
  const removeMedia = (index: number) => {
    setMedia(prevMedia => prevMedia.filter((_, i) => i !== index));
  };
 
  const goToHome = () => {
    navigation.navigate('home');
  };
 
  return (
    <View style={styles.createPost2}>
      <View style={styles.loremIpsumDolorSitAmetCoParent}>
        {/* Conditional Rendering for Description Placeholder */}
        <Text style={description ? styles.loremIpsumDolor : styles.placeholderText}>
          {description || "Add a description..."} {/* Display placeholder text if description is empty */}
        </Text>
 
        <View style={styles.mediaContainer}>
          {media.length === 0 ? (
            <Text style={styles.placeholderText}>Add Memories...</Text> // Placeholder for no media
          ) : (
            media.map((item, index) => (
              <View key={index} style={styles.mediaItemContainer}>
                <Image
                  style={styles.selectedImage}
                  source={{ uri: item.uri }}
                />
                <TouchableOpacity
                  style={styles.cancelButton}
                  onPress={() => removeMedia(index)}
                >
                  <Text style={styles.cancelButtonText}>X</Text>
                </TouchableOpacity>
              </View>
            ))
          )}
        </View>
      </View>
 
      <View style={styles.footer}>
        <TouchableOpacity onPress={selectMedia} style={styles.footerIcons}>
          <Text style={styles.iconText}>Choose Photo/Video</Text>
        </TouchableOpacity>
       
        <TextInput
          style={styles.descriptionInput}
          placeholder="Add a description..."
          placeholderTextColor="#999"
          value={description}
          onChangeText={setDescription}
        />
      </View>
      <TouchableOpacity style={styles.floatingButton} onPress={goToHome}>
        <Text style={styles.buttonText}>Home</Text>
      </TouchableOpacity>
 
      <TouchableOpacity style={styles.homeButton} onPress={handlePost}>
        <Text style={styles.homeButtonText}>Post</Text>
      </TouchableOpacity>
    </View>
  );
};
 
const styles = StyleSheet.create({
  createPost2: {
    backgroundColor: Color.colorWhite,
    flex: 1,
    width: "100%",
  },
  footer: {
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
    height: height * 0.1,
    backgroundColor: "#f8f8f8",
    flexDirection: "row",
    alignItems: "center",
    paddingHorizontal: width * 0.02,
    borderTopWidth: 1,
    borderColor: "#ccc",
  },
  descriptionInput: {
    flex: 1,
    height: 40,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 20,
    paddingLeft: 10,
    marginRight: 10,
  },
  footerIcons: {
    flexDirection: "row",
    alignItems: "center",
  },
  iconText: {
    color: '#007bff',
    marginHorizontal: 10,
    fontWeight: 'bold',
  },
  selectedImage: {
    width: '100%',
    height: height * 0.15,
    borderRadius: 10,
  },
  mediaItemContainer: {
    position: 'relative',
    marginBottom: 10,
    width: '48%',
    margin: '1%',
  },
  cancelButton: {
    position: 'absolute',
    top: 5,
    right: 5,
    backgroundColor: 'red',
    borderRadius: 15,
    padding: 5,
  },
  cancelButtonText: {
    color: 'white',
    fontWeight: 'bold',
  },
  loremIpsumDolor: {
    fontSize: 20,
    width: "100%",
    textAlign: "left",
    fontFamily: FontFamily.segoeUI,
    color: 'black',
    marginVertical: 10,
  },
  placeholderText: {
    fontSize: 20,
    width: "100%",
    textAlign: "left",
    fontFamily: FontFamily.segoeUI,
    color: '#999', // Light color for placeholder
    marginVertical: 10,
  },
  loremIpsumDolorSitAmetCoParent: {
    padding: 10,
  },
  mediaContainer: {
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "space-between",
    marginBottom: height * 0.1,
    marginTop: 60,
  },
  floatingButton: {
    position: "absolute",
    right: 20,
    top: 20,
    backgroundColor: "#007bff",
    borderRadius: 40,
    padding: 15,
    elevation: 5,
  },
  buttonText: {
    color: "white",
    fontWeight: "bold",
  },
  homeButton: {
    position: "absolute",
    bottom: height * 0.1 + 20,
    right: 20,
    backgroundColor: "#FF6347",
    borderRadius: 40,
    padding: 15,
    elevation: 5,
  },
  homeButtonText: {
    color: "white",
    fontWeight: "bold",
  },
});
 
export default CreatePost;
