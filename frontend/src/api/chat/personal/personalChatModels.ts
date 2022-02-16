import {IUserShortDto} from "../../user/userModels";

export interface IPersonalChatInfo {
    id: string;
    companion: IUserShortDto;
}
