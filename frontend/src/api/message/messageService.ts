import {IMessage} from "./messageModels";
import apiClient from "../apiClient";

const messageService = {

    getMessagesByChatId: async (chatId: string): Promise<IMessage[]> => {
        const response = await apiClient.get(`/api/messages/chat/${chatId}`);
        return response.data.data;
    },

    sendMessage: async (chatId: string, text: string): Promise<IMessage> => {
        const response = await apiClient.post(`/api/messages/chat`, {chatId, text});
        return response.data.data;
    },

};

export default messageService;
