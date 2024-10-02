import axios from "axios";
import { getToken } from "../../authServices/authService";
const PERSONA_BASE_REST_API_URL = "http://localhost:9090/persona";

class PersonaAdminService {
    getAllPersonas() {
        return axios.get(PERSONA_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
            },
        });
    }

    getPersonaById(idPersona) {
        return axios.get(`${PERSONA_BASE_REST_API_URL}/${idPersona}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
            },
        });
    }

    // Modificación de createPersona para manejar FormData
    createPersona(persona) {
        return axios.post(PERSONA_BASE_REST_API_URL, persona, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
                // No especificamos 'Content-Type', axios lo maneja automáticamente cuando usamos FormData
            },
        });
    }

    // Modificación de updatePersona para manejar FormData
    updatePersona(idPersona, persona) {
        return axios.put(`${PERSONA_BASE_REST_API_URL}/${idPersona}`, persona, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
                // No especificamos 'Content-Type', axios lo maneja automáticamente cuando usamos FormData
            },
        });
    }

    deletePersona(idPersona) {
        return axios.delete(`${PERSONA_BASE_REST_API_URL}/${idPersona}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
            },
        });
    }
}

export default new PersonaAdminService();