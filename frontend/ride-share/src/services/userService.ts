import axios from "../config/axiosConfig"
import { UserResponse } from "../interfaces/response/UserResponse"

const path = '/users';

export const UserService = {
    getById: async (id: number) => {
        return axios.get<UserResponse>(`${path}/${id}`);
    }
}