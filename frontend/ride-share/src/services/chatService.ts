import axios from "../config/axiosConfig"
import { ChatResponse } from "../interfaces/response/ChatResponse"

const path = '/chats';

export const ChatService = {
    getOrCreate: async (otherParticipantId: number) => {
        return axios.put<ChatResponse>(`${path}/${otherParticipantId}`);
    },
    getByUuid: async (uuid: string) => {
        return axios.get<ChatResponse>(`${path}/${uuid}`);
    },
    getAllForParticipant: async (participantId: number) => {
        return axios.get<ChatResponse[]>(`${path}/participant/${participantId}`);
    }
}