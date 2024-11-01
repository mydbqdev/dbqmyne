import axios from 'axios';
import { BASE_URL } from '../../devprofile';
import useAuthStore from '../zustand/useAuthStore'; // Import the Zustand store

const ApiService = axios.create({
    baseURL: BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
    withCredentials: true, // Optional: if cookies need to be sent (like JSESSIONID)
});

ApiService.interceptors.request.use((config) => {
    const { token } = useAuthStore.getState(); // Get the token from the Zustand store

    console.log("DELERE:: ",token)
    const method = config.method?.toLowerCase();

    if (token && !config.url?.includes('/auth/login') && !config.url?.includes('/auth/register')) {
        config.headers['Authorization'] = `Bearer ${token}`; // Set the Bearer token
    }

    // Ensure config.data is an object for certain methods
    if ((method === 'get' || method === 'post' || method === 'put' || method === 'patch' || method === 'delete') && !config.data) {
        config.data = {};
    }

    return config;
}, (error) => {
    return Promise.reject(error);
});

export default ApiService;
