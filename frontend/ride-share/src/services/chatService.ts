import axiosConfig from "../config/axiosConfig"
import { ChatResponse } from "../interfaces/response/ChatResponse"

const path = '/chats';

export const ChatService = {
    getOrCreate: async (otherParticipantId: number) => {
        return axiosConfig.put<ChatResponse>(`${path}/${otherParticipantId}`);
    },
    getByUuid: async (uuid: string) => {
        return axiosConfig.get<ChatResponse>(`${path}/${uuid}`);
    }
}