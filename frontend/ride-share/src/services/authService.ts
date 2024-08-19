import axiosConfig from "../config/axiosConfig";
import { AuthenticationResponse } from "../interfaces/AuthenticationResponse";
import { UserLogin } from "../interfaces/UserLogin";
import { UserRegister } from "../interfaces/UserRegister";

const path = 'public/auth';

async function register(userRegister: UserRegister) {
    const response = await axiosConfig.post(`${path}/register`, userRegister);    
    return response.data as AuthenticationResponse;    
}

async function login(userLogin: UserLogin) {
    const response = await axiosConfig.post(`${path}/login`, userLogin);
    return response.data as AuthenticationResponse;
}

export { register, login };