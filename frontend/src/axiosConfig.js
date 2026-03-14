import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080/api', // Backend API base URL
});

// Request interceptor to add JWT token to headers
instance.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => Promise.reject(error)
);

export default instance;