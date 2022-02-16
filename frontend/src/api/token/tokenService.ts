const ACCESS_TOKEN_PROPERTY = 'accessToken';
const REFRESH_TOKEN_PROPERTY = 'refreshToken';

const tokenService = {

    isLoggedIn: (): boolean => !!localStorage.getItem(ACCESS_TOKEN_PROPERTY),

    getAccessToken: (): string | null => localStorage.getItem(ACCESS_TOKEN_PROPERTY),

    getRefreshToken: (): string | null => localStorage.getItem(REFRESH_TOKEN_PROPERTY),

    setTokens: (accessToken: string, refreshToken: string): void => {
        localStorage.setItem(ACCESS_TOKEN_PROPERTY, accessToken);
        localStorage.setItem(REFRESH_TOKEN_PROPERTY, refreshToken);
    },

    removeTokens: (): void => {
        localStorage.removeItem(ACCESS_TOKEN_PROPERTY);
        localStorage.removeItem(REFRESH_TOKEN_PROPERTY);
    },

};

export default tokenService;
