import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

class AuthService {
  constructor() {
    this.api = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Interceptor za dodavanje tokena u zahteve
    this.api.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    // Interceptor za rukovanje odgovorima
    this.api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          // Token je istekao ili nije valjan
          localStorage.removeItem('jwtToken');
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }
    );
  }

  async login(username, password) {
    try {
      const response = await this.api.post('/auth/login', { username, password });
      const token = response.data.accessToken;
      localStorage.setItem('jwtToken', token);
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  logout() {
    localStorage.removeItem('jwtToken');
    window.location.href = '/login';
  }

  isAuthenticated() {
    return !!localStorage.getItem('jwtToken');
  }

  getToken() {
    return localStorage.getItem('jwtToken');
  }

  // Wrapper metode za API pozive
  get(url) {
    return this.api.get(url);
  }

  post(url, data) {
    return this.api.post(url, data);
  }

  put(url, data) {
    return this.api.put(url, data);
  }

  delete(url) {
    return this.api.delete(url);
  }
}

export default new AuthService();
