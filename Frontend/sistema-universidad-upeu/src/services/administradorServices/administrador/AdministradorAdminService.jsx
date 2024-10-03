import axios from "axios";
import { getToken } from "../../authServices/authService";
const ADMINISTRADOR_BASE_REST_API_URL = "http://localhost:9090/administrador";


class administradorAdminService{
    getAllAdministradores(){
        return axios.get(ADMINISTRADOR_BASE_REST_API_URL, {
            headers: {
                 Authorization: `Bearer ${getToken()}`
                }
            });
    }

    getAdministradorById(idAdministrador){
        return axios.get(ADMINISTRADOR_BASE_REST_API_URL + "/" + idAdministrador, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createAdministrador(administrador){
        return axios.post(ADMINISTRADOR_BASE_REST_API_URL,administrador,{
            headers:{
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updateAdministrador(idAdministrador,administrador){
        return axios.put(ADMINISTRADOR_BASE_REST_API_URL + "/" + idAdministrador, administrador, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deleteAdministrador(idAdministrador){
        return axios.delete(ADMINISTRADOR_BASE_REST_API_URL + "/" + idAdministrador, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }
}

export default new administradorAdminService();