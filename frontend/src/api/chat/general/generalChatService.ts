import {IChatDetails} from "./generalChatModels";
import apiClient from "../../apiClient";

const generalChatService = {

    getChatsList: async (): Promise<IChatDetails[]> => {
        const response = await apiClient.get('/api/chat/general/all');
        return response.data.data;
    },
};

export default generalChatService;
