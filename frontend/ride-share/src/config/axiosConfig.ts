import axios from 'axios';
import { enqueueSnackbar } from 'notistack';

const axiosConfig = axios.create({
  baseURL: '/api'
});

axiosConfig.interceptors.request.use(
  (config) => {   
    const token = localStorage.getItem('jwt');
    
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosConfig.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      console.error('Error Response:', error.response.data);
      enqueueSnackbar(error.response.data.errorMessage, {
        variant: 'error', 
        autoHideDuration: 3000
      });
    }
    return Promise.reject(error);
  }
);

export default axiosConfig;