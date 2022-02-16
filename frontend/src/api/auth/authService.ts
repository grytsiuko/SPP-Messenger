import {ICurrentUser, ILoginRequest, ILoginResponse, IRegisterRequest} from "./authModels";
import tokenService from "../token/tokenService";
import apiClient from "../apiClient";

const authService = {

    login: async (loginDto: ILoginRequest): Promise<void> => {
        const response = await apiClient.post('/api/auth/login', loginDto);
        const tokens: ILoginResponse = response.data.data;
        tokenService.setTokens(tokens.accessToken, tokens.refreshToken);
    },

    register: async (registerDto: IRegisterRequest): Promise<void> => {
        await apiClient.post('/api/auth/register', registerDto);
    },

    logout: async (): Promise<void> => {
        const refreshToken = tokenService.getRefreshToken();
        await apiClient.post('/api/auth/logout', {refreshToken});
        tokenService.removeTokens();
    },

    me: async (): Promise<ICurrentUser> => {
        const response = await apiClient.get('/api/auth/me');
        return response.data.data;
    },

    isLoggedIn: (): boolean => {
        return tokenService.isLoggedIn();
    }

};

export default authService;
