import { NavigationProp, useNavigation } from '@react-navigation/native';
import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Image, StyleSheet, ScrollView, Alert } from 'react-native';
import Toast from 'react-native-toast-message';
import { showToast } from './ToastUtils';
import axios from 'axios';
import { BASE_URL } from '../../devprofile';
import ApiService from '../Api/ApiService';

const SignUp = () => {
  // State for inputs
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [zipcode, setZipcode] = useState('');
  const navigation = useNavigation<NavigationProp<any>>();

  const handleSignUp = async () => {
    try {
      console.log("IN EH:: ")
      
      const response = await ApiService.post(`${BASE_URL}/auth/register`, {
        zipCode: zipcode,
        userEmail: email,
        password: password,
        userFirstName: firstName,
        userLastName: lastName,
      });
      

console.log("RES:: ",response)
      if (response.status===200) {
        showToast({type: 'success', text1: `Registration Successful`});
        navigation.navigate('Login'); // Replace with your desired screen
       
      } 
      
    } catch (error:any) {
      showToast({type: 'error', text1: `Registration Failed,${error.message}`});
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Image
        style={styles.logo}
        source={require('../assets/images/Logo-2.png')}
      />
      <View style={styles.introParent}>
        <Text style={styles.title}>Sign up</Text>

        <View style={styles.inputRow}>
          <View style={styles.inputContainer}>
            <Text style={styles.label}>First name</Text>
            <TextInput
              style={styles.input}
              placeholder="Tina"
              value={firstName}
              onChangeText={setFirstName}
            />
          </View>

          <View style={styles.inputContainer}>
            <Text style={styles.label}>Last name</Text>
            <TextInput
              style={styles.input}
              placeholder="Jones"
              value={lastName}
              onChangeText={setLastName}
            />
          </View>
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.label}>Email</Text>
          <TextInput
            style={styles.input}
            placeholder="Example@email.com"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
          />
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.label}>Password</Text>
          <TextInput
            style={styles.input}
            placeholder="At least 8 characters"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
          />
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.label}>Zipcode</Text>
          <TextInput
            style={styles.input}
            placeholder="Zipcode"
            value={zipcode}
            onChangeText={setZipcode}
            keyboardType="numeric"
          />
        </View>

        <Text style={styles.terms}>
          By signing up, you agree to our{' '}
          <Text style={styles.link}>Terms of Service</Text> and{' '}
          <Text style={styles.link}>Privacy Policy</Text>.
        </Text>

        <TouchableOpacity style={styles.button} onPress={handleSignUp}>
          <Text style={styles.buttonText}>Sign up</Text>
        </TouchableOpacity>

        <View style={styles.socialLoginContainer}>
          <Text style={styles.orText}>Or sign up with</Text>
          <View style={styles.socialButtons}>
            <TouchableOpacity style={styles.socialButton}>
              <Text style={styles.socialText}>Google</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.socialButton}>
              <Text style={styles.socialText}>Apple</Text>
            </TouchableOpacity>
          </View>
        </View>

        <Text style={styles.alreadyHaveAn}>
          Already have an account?
          <TouchableOpacity onPress={() => navigation.navigate('Login')}>
            <Text style={styles.link}> Log In</Text>
          </TouchableOpacity>
        </Text>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  introParent: {
    marginTop: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'normal',
    marginBottom: 20,
    color: "#333",
  },
  inputRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  inputContainer: {
    marginBottom: 15,
    flex: 1,
    marginRight: 10,
  },
  label: {
    fontSize: 14,
    marginBottom: 5,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 10,
    padding: 10,
    fontSize: 16,
  },
  terms: {
    marginVertical: 20,
    fontSize: 12,
    textAlign: 'center',
  },
  link: {
    color: 'blue',
    textDecorationLine: 'underline',
  },
  button: {
    backgroundColor: "#05838b",
    padding: 15,
    borderRadius: 10,
    alignItems: 'center',
  },
  buttonText: {
    color: "#FFFFFF",
    fontSize: 16,
  },
  socialLoginContainer: {
    alignItems: 'center',
    marginVertical: 20,
  },
  orText: {
    marginBottom: 10,
  },
  socialButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '100%',
  },
  socialButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f0f0f0',
    padding: 10,
    borderRadius: 10,
    flex: 1,
    marginHorizontal: 5,
    justifyContent: 'center',
  },
  socialText: {
    fontSize: 16,
  },
  alreadyHaveAn: {
    textAlign: 'center',
    marginTop: 10,
  },
  logo: {
    marginTop: 30,
    width: '80%',
    height: 40,
    resizeMode: 'contain',
    alignSelf: 'flex-start',
    marginLeft: 20,
  },
});

export default SignUp;
