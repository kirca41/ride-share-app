import axiosConfig from "../config/axiosConfig"
import { CreateMessageRequest } from "../interfaces/request/CreateMessageRequest";
import { MessageResponse } from "../interfaces/response/MessageResponse"

const path = '/messages'

export const MessageService = {
    getAllByChatUuid: async (chatUuid: string) => {
        return axiosConfig.get<MessageResponse[]>(`${path}/chat/${chatUuid}`);
    },
    create: async (request: CreateMessageRequest) => {
        return axiosConfig.post<MessageResponse>(path, request);
    }
}