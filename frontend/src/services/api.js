import authService from './authService';

// Koristimo authService umesto direktnog axios-a
const api = authService;


// Company API
export const companyAPI = {
  getAll: () => api.get('/companies'),
  getById: (id) => api.get(`/companies/${id}`),
  create: (data) => api.post('/companies', data),
  update: (id, data) => api.put(`/companies/${id}`, data),
  delete: (id) => api.delete(`/companies/${id}`),
  searchByName: (name) => api.get(`/companies/search/name?name=${name}`),
  searchByIndustry: (industry) => api.get(`/companies/search/industry?industry=${industry}`),
  getByIndustry: (industry) => api.get(`/companies/industry/${industry}`),
};

// Position API
export const positionAPI = {
  getAll: () => api.get('/positions'),
  getOpen: () => api.get('/positions/open'),
  getClosed: () => api.get('/positions/closed'),
  getById: (id) => api.get(`/positions/${id}`),
  create: (data) => api.post('/positions', data),
  createForCompany: (companyId, data) => api.post(`/positions/company/${companyId}`, data),
  update: (id, data) => api.put(`/positions/${id}`, data),
  delete: (id) => api.delete(`/positions/${id}`),
  getByCompany: (companyId) => api.get(`/positions/company/${companyId}`),
  getOpenByCompany: (companyId) => api.get(`/positions/company/${companyId}/open`),
  searchByName: (name) => api.get(`/positions/search/name?name=${name}`),
  searchByCompanyName: (companyName) => api.get(`/positions/search/company?companyName=${companyName}`),
  close: (id) => api.put(`/positions/${id}/close`),
  open: (id) => api.put(`/positions/${id}/open`),
};

// Candidate API
export const candidateAPI = {
  getAll: () => api.get('/candidates'),
  getById: (id) => api.get(`/candidates/${id}`),
  create: (data) => api.post('/candidates', data),
  update: (id, data) => api.put(`/candidates/${id}`, data),
  delete: (id) => api.delete(`/candidates/${id}`),
  getByStatus: (status) => api.get(`/candidates/status/${status}`),
  searchByName: (name) => api.get(`/candidates/search/name?name=${name}`),
  searchByStatusAndName: (status, name) => api.get(`/candidates/search/status?status=${status}&name=${name}`),
  updateStatus: (id, status) => api.put(`/candidates/${id}/status?status=${status}`),
};

// Interview API
export const interviewAPI = {
  getAll: () => api.get('/interviews'),
  getById: (id) => api.get(`/interviews/${id}`),
  create: (data) => api.post('/interviews', data),
  createForCandidateAndPosition: (candidateId, positionId, date) => 
    api.post(`/interviews/candidate/${candidateId}/position/${positionId}?date=${date}`),
  update: (id, data) => api.put(`/interviews/${id}`, data),
  delete: (id) => api.delete(`/interviews/${id}`),
  getByStatus: (status) => api.get(`/interviews/status/${status}`),
  getByCandidate: (candidateId) => api.get(`/interviews/candidate/${candidateId}`),
  getByPosition: (positionId) => api.get(`/interviews/position/${positionId}`),
  getByDateRange: (startDate, endDate) => 
    api.get(`/interviews/date-range?startDate=${startDate}&endDate=${endDate}`),
  getByCandidateAndPosition: (candidateId, positionId) => 
    api.get(`/interviews/candidate/${candidateId}/position/${positionId}`),
  searchByCandidateName: (candidateName) => api.get(`/interviews/search/candidate?candidateName=${candidateName}`),
  searchByPositionName: (positionName) => api.get(`/interviews/search/position?positionName=${positionName}`),
  updateStatus: (id, status) => api.put(`/interviews/${id}/status?status=${status}`),
  addRound: (interviewId, type, number) => 
    api.post(`/interviews/${interviewId}/rounds?type=${type}&number=${number}`),
  hireCandidate: (interviewId) => api.post(`/interviews/${interviewId}/hire`),
  getRounds: (interviewId) => api.get(`/interviews/${interviewId}/rounds`),
  getRoundsByType: (type) => api.get(`/interviews/rounds/type/${type}`),
  deleteRound: (roundId) => api.delete(`/interviews/rounds/${roundId}`),
};

export default api;
