import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthProvider";
import { useEffect } from "react";

const Logout: React.FC = () => {

    const navigate = useNavigate();
    const { fetchActiveUser } = useAuth();

    useEffect(() => {
        localStorage.removeItem('jwt');
        fetchActiveUser();
        navigate('/login');
    }, []);

    return <></>;
}

export default Logout;