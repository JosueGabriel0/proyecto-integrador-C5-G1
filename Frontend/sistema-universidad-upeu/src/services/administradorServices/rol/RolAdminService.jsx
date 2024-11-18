import axios from "axios";
import { getToken } from '../../authServices/authService';

const ROL_BASE_REST_API_URL = `${process.env.REACT_APP_API_BASE_URL}/rol`;

class RolAdminService {

    getAllRoles() {
        return axios.get(ROL_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createRol(rol) {
        return axios.post(ROL_BASE_REST_API_URL, rol, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getRolById(idRol) {
        return axios.get(`${ROL_BASE_REST_API_URL}/${idRol}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updateRol(idRol, rol) {
        return axios.put(`${ROL_BASE_REST_API_URL}/${idRol}`, rol, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deleteRol(idRol) {
        return axios.delete(`${ROL_BASE_REST_API_URL}/${idRol}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }
}

export default new RolAdminService();