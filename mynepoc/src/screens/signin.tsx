import * as React from "react";
import {
  StyleSheet,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  Image,
  Alert,
} from "react-native";
import { NavigationProp, useNavigation } from "@react-navigation/native";
import ApiService from "../Api/ApiService";
import { BASE_URL } from "../../devprofile";
import axios from "axios";
import Icon from 'react-native-vector-icons/FontAwesome';
import useAuthStore from "../zustand/useAuthStore";


const LogIn = () => {
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const navigation = useNavigation<NavigationProp<any>>();
  const { setToken } = useAuthStore();
  const handleLogin = async () => {
    try {
      const response = await ApiService.post(`${BASE_URL}/auth/login`, {
        userEmail: email,
        password: password,
      });

      if (response.status === 200) {
        Alert.alert("Login Successful", `Welcome ${email}!`);
        setToken(response.data.token)
        navigation.navigate("home");
      }
    } catch (error) {
      Alert.alert("Error", "Something went wrong. Please try again later.");
      console.error("Fetch Error:", error);
    }
  };

  return (
    <View style={styles.logIn}>
      <View style={styles.loginForm} />
      <View style={styles.frameParent}>
        <View style={styles.introParent}>
          <View style={styles.intro}>
            <Image
              style={styles.logo}
              source={require("../assets/images/Logo-2.png")}
            />
            <Text style={styles.login}>Login</Text>
          </View>
          <View style={styles.form}>
            <View style={styles.input}>
              <Text style={styles.label}>Email</Text>
              <TextInput
                style={styles.inputField}
                placeholder="Example@email.com"
                placeholderTextColor="#A9A9A9"
                value={email}
                onChangeText={(text) => setEmail(text)}
              />
            </View>
            <View style={styles.input}>
              <Text style={styles.label}>Password</Text>
              <TextInput
                style={styles.inputField}
                placeholder="Your password"
                placeholderTextColor="#A9A9A9"
                secureTextEntry
                value={password}
                onChangeText={(text) => setPassword(text)}
              />
            </View>
            <TouchableOpacity>
              <Text style={styles.forgotPassword}>Forgot Password?</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.mainButton} onPress={handleLogin}>
              <Text style={styles.signIn}>Log in</Text>
            </TouchableOpacity>
          </View>
        </View>

        <View style={styles.spcialLogin}>
          <View style={styles.or}>
            <View style={styles.orLine} />
            <Text style={styles.orLogIn}>Or log in with</Text>
            <View style={styles.orLine} />
          </View>
          <View style={styles.socialButtons}>
            <TouchableOpacity style={styles.socialButton}>
            <Icon name="google" size={20} color="#EA4335" />
              <Text style={styles.socialText}>Google</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.socialButton}>
            <Icon name="apple" size={20} color="#000" />
              <Text style={styles.socialText}>Apple</Text>
            </TouchableOpacity>
          </View>
          <Text style={styles.dontHaveAnContainer}>
            Don't have an account?{" "}
            <TouchableOpacity onPress={() => navigation.navigate("SignUp")}>
              <Text style={styles.signUp}>Sign up</Text>
            </TouchableOpacity>
          </Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  logIn: {
    flex: 1,
    backgroundColor: "#FFFFFF",
    alignItems: "center",
    justifyContent: "center",
  },
  frameParent: {
    width: "85%",
    alignItems: "center",
    justifyContent: "space-between",
  },
  introParent: {
    width: "100%",
    alignItems: "center",
    marginBottom: 30,
  },
  intro: {
    alignItems: "center",
    marginBottom: 20,
  },
  logo: {
    width: 150,
    height: 60,
    resizeMode: "contain",
    marginBottom: 10,
  },
  login: {
    fontSize: 26,
    fontWeight: "600",
    color: "#000",
    marginBottom: 20,
  },
  form: {
    width: "100%",
    gap: 15,
  },
  input: {
    width: "100%",
  },
  loginForm: {
    top: 201,
    left: 6,
    width: 390,
    height: 588,
    position: "absolute",
  },
  label: {
    fontSize: 14,
    marginBottom: 5,
    color: "#000",
  },
  inputField: {
    height: 42,
    borderWidth: 1,
    borderColor: "#D3D3D3",
    borderRadius: 5,
    paddingHorizontal: 10,
    backgroundColor: "#F5F5F5",
  },
  forgotPassword: {
    alignSelf: "flex-end",
    marginVertical: 10,
    color: "#008080",
  },
  mainButton: {
    backgroundColor: "#008080",
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: "center",
  },
  signIn: {
    color: "#FFF",
    fontSize: 18,
    fontWeight: "500",
  },
  spcialLogin: {
    width: "100%",
    alignItems: "center",
    marginTop: 20,
  },
  
  or: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  orLine: {
    flex: 1,
    height: 1,
    backgroundColor: "#D3D3D3",
  },
  orLogIn: {
    marginHorizontal: 10,
    fontSize: 14,
    color: "#294957",
  },
  socialButtons: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
  },
  socialButton: {
    backgroundColor: "#F5F5F5",
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
    alignItems: "center",
    flex: 1,
    marginHorizontal: 5,
    flexDirection: "row", // Added for icon and text alignment
    justifyContent: "center", // Center content
  },
  socialText: {
    fontSize: 16,
    color: "#000",
    marginLeft: 5, // Space between icon and text
  },
  dontHaveAnContainer: {
    marginTop: 20,
    fontSize: 16,
    color: "#000",
  },
  signUp: {
    color: "#008080",
    fontWeight: "600",
  },
});

export default LogIn;
