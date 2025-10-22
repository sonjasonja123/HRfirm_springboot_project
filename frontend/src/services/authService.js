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
          this.logout();
        }
        return Promise.reject(error);
      }
    );
  }

  // Login za kompaniju
  async loginCompany(username, password) {
    try {
      const response = await this.api.post('/company/login', { username, password });
      const userData = response.data;
      userData.password = password; // Čuvamo za kasnije API pozive
      localStorage.setItem('userType', 'company');
      localStorage.setItem('userData', JSON.stringify(userData));
      localStorage.setItem('jwtToken', 'company-' + userData.idCompany); // Mock token
      return userData;
    } catch (error) {
      throw error;
    }
  }

  // Login za radnika
  async loginWorker(username, password) {
    try {
      const response = await this.api.post('/user/login', { username, password });
      const userData = response.data;
      localStorage.setItem('userType', 'worker');
      localStorage.setItem('userData', JSON.stringify(userData));
      localStorage.setItem('jwtToken', 'worker-' + userData.id); // Mock token
      return userData;
    } catch (error) {
      throw error;
    }
  }

  // Legacy metoda za postojeći kod
  async login(username, password) {
    try {
      const response = await this.api.post('/auth/login', { username, password });
      const token = response.data.accessToken;
      localStorage.setItem('jwtToken', token);
      localStorage.setItem('userType', 'worker');
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userType');
    localStorage.removeItem('userData');
    window.location.href = '/login';
  }

  isAuthenticated() {
    return !!localStorage.getItem('jwtToken');
  }

  getUserType() {
    return localStorage.getItem('userType');
  }

  getCurrentUser() {
    const userData = localStorage.getItem('userData');
    return userData ? JSON.parse(userData) : null;
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
