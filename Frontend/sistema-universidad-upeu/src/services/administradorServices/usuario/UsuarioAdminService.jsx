import axios from "axios";
import { getToken } from "../../authServices/authService";

const USUARIO_BASE_REST_API_URL = `${process.env.REACT_APP_API_BASE_URL}/usuario`;

class UsuarioAdminService {
    getAllUsuarios() {
        return axios.get(USUARIO_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createUsuario(usuario) {
        return axios.post(USUARIO_BASE_REST_API_URL, usuario, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getUsuarioById(idUsuario) {
        return axios.get(`${USUARIO_BASE_REST_API_URL}/${idUsuario}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getUsuarioByEmail(email) {
        return axios.get(`${USUARIO_BASE_REST_API_URL}/email/${email}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getUsuarioByUsername(username) {
        return axios.get(`${USUARIO_BASE_REST_API_URL}/search?username=${username}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updateUsuario(idUsuario, usuario) {
        return axios.put(`${USUARIO_BASE_REST_API_URL}/${idUsuario}`, usuario, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deleteUsuario(idUsuario) {
        return axios.delete(`${USUARIO_BASE_REST_API_URL}/${idUsuario}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    generateResetToken(idUsuario) {
        return axios.post(`${USUARIO_BASE_REST_API_URL}/${idUsuario}/generate-reset-token`, null, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    resetPasswordWithToken(token, newPassword) {
        return axios.post(`${USUARIO_BASE_REST_API_URL}/reset-password`, 
            { newPassword }, 
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
    }

    // Nuevo método para validar el token de restablecimiento de contraseña
    validateResetToken(token) {
        return axios.get(`${USUARIO_BASE_REST_API_URL}/validar-token/${token}`, {
            headers: {
                Authorization: `Bearer ${getToken()}` // Asegúrate de incluir el token de autorización si es necesario
            }
        });
    }
}

export default new UsuarioAdminService();