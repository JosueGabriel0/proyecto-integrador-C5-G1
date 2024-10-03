import axios from "axios";
import { getToken } from "../../authServices/authService";

const ADMINISTRATIVO_BASE_REST_API_URL = "http://localhost:9090/administrador";

class AdministrativoAdminService{
    getAllAdministrativos(){
        return axios.get(ADMINISTRATIVO_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getAdministrativoById(idAdministrativo){
        return axios.get(ADMINISTRATIVO_BASE_REST_API_URL, idAdministrativo, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createAdministrativo(administrativo){
        return axios.post(ADMINISTRATIVO_BASE_REST_API_URL, administrativo, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updateAdministrativo(idAdministrativo, administrativo){
        return axios.put(ADMINISTRATIVO_BASE_REST_API_URL + "/" + idAdministrativo, administrativo, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deleteAdministrativo(idAdministrativo){
        return axios.delete(ADMINISTRATIVO_BASE_REST_API_URL + "/" + idAdministrativo, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }
}

export default new AdministrativoAdminService();