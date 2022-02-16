import axios from 'axios';
import tokenService from "./token/tokenService";

const apiClient = axios.create();

const responseErrorHandler = (e: any) => {
    const status = e.response.status;
    const originalRequest = e.config;

    if (status !== 403) {
        return Promise.reject(new Error(e.response.data.message));
    }

    const refreshToken = tokenService.getRefreshToken();
    tokenService.removeTokens();

    if (originalRequest._retry) {
        window.location.replace('/auth');
        return Promise.reject(e);
    }

    originalRequest._retry = true;

    return apiClient.post('/api/auth/refresh', {refreshToken})
        .then(res => {
            tokenService.setTokens(res.data.data.accessToken, res.data.data.refreshToken);
            return apiClient(originalRequest);
        });
};

apiClient.interceptors.request.use(request => {
    const token = tokenService.getAccessToken();
    if (token) {
        request.headers.Authorization = token;
    }
    const prefix = 'http://localhost:8080';
    if (!request.url?.startsWith(prefix)) {
        request.url = prefix + request.url;
    }
    return request;
});

apiClient.interceptors.response.use(undefined, responseErrorHandler);

export default apiClient;
