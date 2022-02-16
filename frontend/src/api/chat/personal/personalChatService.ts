import {IPersonalChatInfo} from "./personalChatModels";
import apiClient from "../../apiClient";
import {IChatDetails} from "../general/generalChatModels";

const personalChatService = {

    getById: async (chatId: string): Promise<IPersonalChatInfo> => {
        const response = await apiClient.get(`/api/chat/personal/${chatId}`);
        return response.data.data;
    },

    create: async (targetId: string): Promise<IChatDetails> => {
        const response = await apiClient.post(`/api/chat/personal/create`, {targetId});
        return response.data.data;
    },

    deleteById: async (chatId: string): Promise<void> => {
        await apiClient.post(`/api/chat/personal/delete`, {chatId});
    },
};

export default personalChatService;
