import axios from 'axios';
import { BASE_URL, USERNAME, PASSWORD } from './devprofile';  // Your global values

// Create an Axios instance with default headers
const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Basic em95YXBwOnpveXBhc3M=',
  },
});

// Add a request interceptor to handle CRUD operations when no body is passed
apiClient.interceptors.request.use((config) => {
  // Handle CRUD methods: GET, POST, PUT, PATCH, DELETE
  const method = config.method?.toLowerCase();

  if ((method === 'get' || method === 'post' || method === 'put' || method === 'patch' || method === 'delete') && !config.data) {
    config.data = {};  // Add an empty object if no request body is passed
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});

export default apiClient;
