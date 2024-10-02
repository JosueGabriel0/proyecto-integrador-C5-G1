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

    // Método para obtener la imagen de una persona
    async getPersonaImagen(fotoPerfil) {
        // Construir la URL de la imagen utilizando el atributo fotoPerfil
        const imagenUrl = `${PERSONA_BASE_REST_API_URL}/images/${fotoPerfil}`; // fotoPerfil contiene el nombre del archivo o la ruta

        try {
            const response = await axios.get(imagenUrl, {
                headers: {
                    Authorization: `Bearer ${getToken()}`,
                    responseType: 'blob' // Para manejar la imagen como blob
                },
            });
            return URL.createObjectURL(response.data); // Crear una URL para la imagen
        } catch (error) {
            console.error("Error al obtener la imagen:", error);
            return null; // Manejo de errores
        }
    }
}

export default new PersonaAdminService();