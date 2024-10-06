import axios from 'axios';
import { enqueueSnackbar } from 'notistack';

const axiosConfig = axios.create({
  baseURL: '/api'
});

axiosConfig.interceptors.request.use(
  (config) => {
    const controller = new AbortController();
    const token = localStorage.getItem('jwt');
    const url = config.url;
    
    if (!token && !url?.includes("public") && !url?.includes("nominatim") && !url?.includes("api/rides/search")) {
      controller.abort();
      enqueueSnackbar('You must be logged in to continue', {
        variant: 'error', 
        autoHideDuration: 3000
      });
      window.location.href = '/login';
    }
    
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return {
      ...config,
      signal: controller.signal
    };
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