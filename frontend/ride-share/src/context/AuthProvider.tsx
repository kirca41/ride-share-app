import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { UserResponse } from "../interfaces/response/UserResponse";
import { getActiveUser } from "../services/authService";

interface AuthContextType {
    activeUser: UserResponse | null;
    fetchActiveUser: () => Promise<void>;
}

interface AuthProviderProps {
    children: ReactNode;
}

const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider = ({ children }: AuthProviderProps) => {

    const [activeUser, setActiveUser] = useState<UserResponse | null>(null);

    useEffect(() => {
        fetchActiveUser();
    }, [])

    const fetchActiveUser = async () => {
        const response = await getActiveUser();
        setActiveUser(response.data);
    }


    return <AuthContext.Provider value={{activeUser, fetchActiveUser}}>{children}</AuthContext.Provider>;
  };
  
  export default AuthProvider;
  
  export const useAuth = () => {
    return useContext(AuthContext);
  };