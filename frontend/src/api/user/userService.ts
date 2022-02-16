import {IUserSearchDto} from "./userModels";
import apiClient from "../apiClient";

const userService = {

    search: async (username: string): Promise<IUserSearchDto> => {
        const params = new URLSearchParams();
        params.append('username', username);
        const response = await apiClient.get(`/api/users/search?${params.toString()}`);
        return response.data.data;
    },
};

export default userService;
