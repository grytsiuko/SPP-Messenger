export interface IMessage {
    id: string;
    text: string;
    senderName: string;
    senderId: string;
    createdAt: number;
}

export interface ILastMessage {
    text: string;
    createdAt: number;
}
