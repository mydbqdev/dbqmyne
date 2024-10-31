import * as React from "react";
import { StyleSheet, View, Text, Image, ScrollView, TouchableOpacity, Switch } from "react-native";
import MaterialIcons from 'react-native-vector-icons/MaterialIcons'
import { TextInput } from "react-native-paper";
import { useState } from "react";
import MapView, { Marker } from "react-native-maps";
import DropDownPicker from "react-native-dropdown-picker";
import AntDesign from 'react-native-vector-icons/AntDesign';
import { NavigationProp, useNavigation } from "@react-navigation/native";
import { launchImageLibrary } from "react-native-image-picker";

const Listing = () => {
    const nav=useNavigation<NavigationProp<any>>()

    const [isEnabled, setIsEnabled] = useState(false);
    const [splocation, setSplocation] = useState(false)
    const toggleSwitch = () => setIsEnabled(!isEnabled);
    const toggleSwitch1 = () => setSplocation(!isEnabled);
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    const [pickupLocation, setPickupLocation] = useState("");

    const [region, setRegion] = useState({
        latitude: 37.78825,
        longitude: -122.4324,
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
    });
    const [photos, setPhotos] = useState<any[]>([]);
    const [categoryOpen, setCategoryOpen] = useState(false);
    const [categoryValue, setCategoryValue] = useState(null);
    const [categories, setCategories] = useState([
        { label: "Electronics", value: "electronics" },
        { label: "Furniture", value: "furniture" },
        { label: "Clothing", value: "clothing" },
        { label: "Books", value: "books" },
        { label: "Sports", value: "sports" },
    ]);
    const handleSelectPhotos = () => {
        launchImageLibrary(
            { mediaType: 'photo', selectionLimit: 10 }, // Adjust selectionLimit as needed
            (response) => {
                if (response.assets) {
                    setPhotos(response.assets);
                }
            }
        );
    };

    return (
        <ScrollView nestedScrollEnabled={true} className="flex-1 bg-white mb-5">
            <View className="mt-2 ml-4 mr-2">
                <View className="flex-row items-center justify-between">
                    <TouchableOpacity onPress={nav.goBack}>
                        <AntDesign name="arrowleft" color={'black'} size={25} />
                    </TouchableOpacity>
                    <TouchableOpacity className=" mr-1">
                        <Text className=" text-white bg-cyan-400 px-4 py-2 rounded-lg">Post</Text>
                    </TouchableOpacity>
                </View>
                <View className="items-start">

                {photos.length > 0 && (
                    <ScrollView horizontal className="flex-row mt-4">
                        {photos.map((photo, index) => (
                            <Image
                                key={index}
                                source={{ uri: photo.uri }}
                                style={styles.photo}
                            />
                        ))}
                    </ScrollView>
                )}

                    <TouchableOpacity className=" py-8 px-4 bg-green-50 rounded-lg items-center"onPress={handleSelectPhotos} >
                        <MaterialIcons name="add-photo-alternate" size={15} />
                        <Text className="">Add Photos</Text>
                    </TouchableOpacity>
                </View>
                <View>
                    <Text className="text-black">Add upto 10 photos. Drag to reorder </Text>
                    <Text className="text-xl my-2 text-black font-normal">What Are You Selling?</Text>
                    <Text className="text-black font-normal text-lg">Title</Text>
                    <TextInput
                        placeholder="Title"
                        placeholderTextColor={'gray'}
                        className="border border-gray-300 bg-green-50 rounded-lg"
                        underlineColor="transparent"
                        activeUnderlineColor="transparent"
                        cursorColor="black"
                        value={title}
                        onChangeText={setTitle}
                    />
                    <Text className="text-black font-normal text-lg">Description</Text>
                    <TextInput
                        placeholder="Description"
                        placeholderTextColor={'gray'}
                        className="border border-gray-300 bg-green-50 rounded-lg"
                        underlineColor="transparent"
                        activeUnderlineColor="transparent"
                        cursorColor="black"
                        value={description}
                        onChangeText={setDescription}
                    />
                    <Text className="text-black font-normal text-lg">Price</Text>

                    <View className="flex-row items-center">

                        <View>
                            <TextInput
                                placeholder="Price"
                                placeholderTextColor={'gray'}
                                className="border border-gray-300 bg-green-50 rounded-lg pr-10"
                                underlineColor="transparent"
                                activeUnderlineColor="transparent"
                                cursorColor="black"
                                value={price}
                                onChangeText={setPrice}
                            />
                        </View>
                        <View className="ml-10 flex-row items-center">
                            <Text className="text-black">Free</Text>
                            <Switch
                                trackColor={{ false: "#767577", true: "#81b0ff" }}
                                thumbColor={isEnabled ? "#05838b" : "#05838b"}
                                onValueChange={toggleSwitch}
                                value={isEnabled}
                            />
                        </View>
                    </View>
                    <View>
                        <Text className="text-black font-normal text-lg">Category</Text>
                        <DropDownPicker
                            open={categoryOpen}
                            value={categoryValue}
                            items={categories}
                            setOpen={setCategoryOpen}
                            setValue={setCategoryValue}
                            setItems={setCategories}
                            placeholder="Select a category"
                            style={styles.dropdown}
                            dropDownContainerStyle={styles.dropdownContainer}
                        />
                    </View>
                </View>
                <Text className="text-black font-normal text-lg">Pick-Up Location</Text>
                <View className="flex-row items-center">
                    <TextInput
                        placeholder="Eldora/Village"
                        placeholderTextColor={'gray'}
                        className="border border-gray-300 bg-green-50 rounded-lg pr-8 mr-5"
                        underlineColor="transparent"
                        activeUnderlineColor="transparent"
                        cursorColor="black"
                        value={pickupLocation}
                        onChangeText={setPickupLocation}
                    />
                    <TouchableOpacity>
                        <Text className="underline text-black text-base">Edit</Text>
                    </TouchableOpacity>
                </View>
                <MapView
                    style={styles.map}
                    region={region}
                    onRegionChangeComplete={(newRegion) => setRegion(newRegion)}
                >
                    <Marker
                        coordinate={{ latitude: region.latitude, longitude: region.longitude }}
                        title="Pick-Up Location"
                    />
                </MapView>
                <View className="flex-row items-center">
                    <Text className="mt-2">Show specific location</Text>

                    <Switch
                        trackColor={{ false: "#767577", true: "#81b0ff" }}
                        thumbColor={splocation ? "cyan" : "cyan"}
                        onValueChange={toggleSwitch1}
                        value={splocation}
                    />
                </View>

            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    photo: {
        width: 100,
        height: 100,
        marginRight: 10,
        borderRadius: 8,
    },
    map: {
        height: 200,
        width: "100%",
        marginTop: 20,
        borderRadius: 30,
    },
    dropdown: {
        borderColor: "#c4c4c4",
        backgroundColor: "#e6fffa",
        borderRadius: 8,
    },
    dropdownContainer: {
        borderColor: "#c4c4c4",
    },

});

export default Listing;