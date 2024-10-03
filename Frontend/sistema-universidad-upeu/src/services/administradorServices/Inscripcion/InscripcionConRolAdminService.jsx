import axios from "axios";
import { getToken } from "../../authServices/authService";

const INSCRIPCION_BASE_REST_API_URL = "http://localhost:9090/inscripcion/con-rol";

class InscripcionService {

    getAllInscripciones(){
        return axios.get(INSCRIPCION_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getInscripcionById(idInscripcion){
        return axios.get(INSCRIPCION_BASE_REST_API_URL + "/" +idInscripcion, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        })
    }

    createInscripcion(inscripcion){
        return axios.post(INSCRIPCION_BASE_REST_API_URL,inscripcion, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        })
    }

    updateInscripcion(idInscripcion, inscripcion){
        return axios.put(INSCRIPCION_BASE_REST_API_URL + "/" + idInscripcion, inscripcion, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        })
    }

    deleteInscripcion(idInscripcion){
        return axios.delete(INSCRIPCION_BASE_REST_API_URL + "/" + idInscripcion, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        })
    }
}

export default new InscripcionService();