import axios from "axios";
import { getToken } from "../../authServices/authService";

const INSCRIPCION_BASE_REST_API_URL = "http://localhost:9090/inscripcion/con-rol";

class InscripcionService {

    async getAllInscripciones(){
        try {
            const response = await axios.get(INSCRIPCION_BASE_REST_API_URL, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error al obtener las inscripciones:", error);
            throw error;
        }
    }

    async getInscripcionById(idInscripcion){
        try {
            const response = await axios.get(`${INSCRIPCION_BASE_REST_API_URL}/${idInscripcion}`, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error al obtener la inscripción:", error);
            throw error;
        }
    }

    async createInscripcion(inscripcion){
        try {
            const response = await axios.post(INSCRIPCION_BASE_REST_API_URL, inscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error al crear la inscripción:", error);
            throw error;
        }
    }

    async updateInscripcion(idInscripcion, inscripcion){
        try {
            const response = await axios.put(`${INSCRIPCION_BASE_REST_API_URL}/${idInscripcion}`, inscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error al actualizar la inscripción:", error);
            throw error;
        }
    }

    async deleteInscripcion(idInscripcion){
        try {
            const response = await axios.delete(`${INSCRIPCION_BASE_REST_API_URL}/${idInscripcion}`, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            });
            return response.data;
        } catch (error) {
            console.error("Error al eliminar la inscripción:", error);
            throw error;
        }
    }
}

export default new InscripcionService();
