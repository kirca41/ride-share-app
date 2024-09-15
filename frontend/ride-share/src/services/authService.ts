import axiosConfig from "../config/axiosConfig";
import { AuthenticationResponse } from "../interfaces/response/AuthenticationResponse";
import { UserResponse } from "../interfaces/response/UserResponse";
import { UserLogin } from "../interfaces/UserLogin";
import { UserRegister } from "../interfaces/UserRegister";

const path = 'public/auth';

export const register = async (userRegister: UserRegister) =>  {
    const response = await axiosConfig.post(`${path}/register`, userRegister);    
    return response.data as AuthenticationResponse;    
}

export const login = async (userLogin: UserLogin) => {
    const response = await axiosConfig.post(`${path}/login`, userLogin);
    return response.data as AuthenticationResponse;
}

export const getActiveUser = async () => {
    return axiosConfig.get<UserResponse>(`${path}/active-user`);
}