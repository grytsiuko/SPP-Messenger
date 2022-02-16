import {RoleEnum} from "../chat/group/groupChatModels";

export interface IUserShortDto {
    id: string;
    username: string;
    fullName: string;
    bio: string;
    permissionLevel: RoleEnum;
}

export interface IUserSearchDto {
    id: string;
    username: string;
    fullName: string;
}
