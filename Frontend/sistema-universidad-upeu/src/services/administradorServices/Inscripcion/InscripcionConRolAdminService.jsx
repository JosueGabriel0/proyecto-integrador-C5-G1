import axios from "axios";
import { getToken } from "../../authServices/authService";

const INSCRIPCION_DATABASE_REST_API_URL = "http://localhost:9090/inscripcion";

class InscripcionConRolAdminService {

    getAllInscripciones(){
        return(
            axios.get(INSCRIPCION_DATABASE_REST_API_URL, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    getInscripcionById(idInscripcion){
        return(
            axios.get(INSCRIPCION_DATABASE_REST_API_URL + "/" + idInscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    postInscripcion(inscripcion){
        return(
            axios.post(INSCRIPCION_DATABASE_REST_API_URL + "/con-rol", inscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    putInscripcion(idInscripcion, inscripcion){
        return(
            axios.put(INSCRIPCION_DATABASE_REST_API_URL + "/con-rol/" + idInscripcion, inscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    deleteInscripcion(idInscripcion){
        return(
            axios.delete(INSCRIPCION_DATABASE_REST_API_URL + "/con-rol/" + idInscripcion, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }
}

export default new InscripcionConRolAdminService();
