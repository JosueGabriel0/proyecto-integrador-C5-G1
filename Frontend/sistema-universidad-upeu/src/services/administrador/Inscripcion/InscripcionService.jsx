import axios from "axios";

const INSCRIPCION_BASE_REST_API_URL = "http://localhost:9090/inscripcion/con-rol";

class InscripcionService {

    getAllInscripciones(){
        return axios.get(INSCRIPCION_BASE_REST_API_URL);
    }
}

export default new InscripcionService();