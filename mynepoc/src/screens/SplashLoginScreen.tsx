import React from "react";
import { Image, StyleSheet, View, Text, TouchableOpacity } from "react-native";
import { FontFamily, FontSize, Color, Padding, Border } from "../styles/globalStyles";
import { NavigationProp, useNavigation } from "@react-navigation/native";

const SplashLoginScreen = () => {
  const navigation = useNavigation<NavigationProp<any>>();

  return (
    <View style={styles.container}>
      {/* Logo and Title Section */}
      <View style={styles.header}>
        <Image
          style={styles.notchIcon}
          source={require("../assets/images/Logo-2.png")}
        />
        <Text style={styles.title}>My Neighborhood Hub</Text>
      </View>

      {/* Splash Image */}
      <Image
        style={styles.splashImage}
        source={require("../assets/images/splash.png")}
      />

      {/* Button Section */}
      <View style={styles.buttonContainer}>
        <View style={styles.mainButton}>
          <TouchableOpacity onPress={() => navigation.navigate('Login')}>
            <Text style={styles.buttonText}>Log in</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.secondaryButton}>
          <TouchableOpacity onPress={() => navigation.navigate('SignUp')}>
            <Text style={styles.secondaryButtonText}>Sign up</Text>
          </TouchableOpacity>
        </View>
      </View>

      {/* Footer Section */}
      <View style={styles.footer}>
        <View style={styles.vectorContainer}>
          <Image style={styles.vectorIcon} />
          <Image style={styles.frameIcon} />
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Color.colorWhite,
    alignItems: 'center',
    justifyContent: 'center', // Center elements vertically
    paddingVertical: 20, // Reduced overall padding
  },
  header: {
    alignItems: 'center',
    marginBottom: 0, // No margin between header and splash image
  },
  notchIcon: {
    width: 164,
    height: 31,
    resizeMode: 'contain',
  },
  title: {
    marginTop: 2, // Minimal spacing between logo and title
    fontSize: 22,
    fontFamily: FontFamily.segoeUI,
    color: '#037d7f',
    fontWeight: '600',
    textAlign: 'center',
  },
  splashImage: {
    width: '85%',
    height: 250,
    resizeMode: 'contain',
    marginTop: 5, // Brought splash image closer to header
  },
  buttonContainer: {
    width: '100%',
    alignItems: 'center',
    paddingHorizontal: 20,
    marginTop: 10, // Reduced space between splash image and buttons
  },
  mainButton: {
    backgroundColor: Color.colorTeal_100,
    width: '100%',
    paddingVertical: Padding.p_sm,
    borderRadius: Border.br_3xs,
    alignItems: 'center',
    marginBottom: 8, // Reduced space between buttons
  },
  secondaryButton: {
    borderColor: Color.colorTeal_100,
    borderWidth: 1,
    backgroundColor: Color.colorWhite,
    width: '100%',
    paddingVertical: Padding.p_sm,
    borderRadius: Border.br_3xs,
    alignItems: 'center',
  },
  buttonText: {
    color: Color.colorWhite,
    fontFamily: FontFamily.robotoMedium,
    fontSize: FontSize.size_lg,
    fontWeight: '500',
  },
  secondaryButtonText: {
    color: Color.colorTeal_100,
    fontFamily: FontFamily.robotoMedium,
    fontSize: FontSize.size_lg,
    fontWeight: '500',
  },
  footer: {
    alignItems: 'center',
    marginBottom: 10, // Reduced bottom margin for tighter fit
  },
  vectorContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  vectorIcon: {
    width: 64,
    height: 41,
    resizeMode: 'contain',
    marginRight: 10,
  },
  frameIcon: {
    width: 115,
    height: 30,
    resizeMode: 'contain',
  },
});

export default SplashLoginScreen;
