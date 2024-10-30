import React from "react";
import { SafeAreaView, Image, StyleSheet, View, Dimensions } from "react-native";

const { width, height } = Dimensions.get("window");

const SplashScreen = () => {
  return (
    <View style={styles.splashScreen}>
      <Image
        style={styles.logo}
        resizeMode="contain"
        source={require("../assets/images/Logo-2.png")}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  splashScreen: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#05838b",
  },
  logo: {
    width: width * 0.5, // Set width to 50% of screen width
    height: height * 0.25, // Set height to 25% of screen height
    maxWidth: 300, // Set a max width for larger screens
    maxHeight: 200, // Set a max height for larger screens
    tintColor: "#FFFFFF",
  },
});

export default SplashScreen;
