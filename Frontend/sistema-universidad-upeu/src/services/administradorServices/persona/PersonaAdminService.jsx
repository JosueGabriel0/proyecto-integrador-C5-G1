import axios from "axios";

const PERSONA_BASE_REST_API_URL = "http://localhost:9090/persona";

class PersonaAdminService{
    getAllPersonas(){
        return axios.get(PERSONA_BASE_REST_API_URL);
    }

    getPersonaById(idPersona){
        return axios.get(PERSONA_BASE_REST_API_URL+"/"+idPersona);
    }

    createPersona(persona){
        return axios.post(PERSONA_BASE_REST_API_URL,persona);
    }

    updatePersona(idPersona,persona){
        return axios.put(PERSONA_BASE_REST_API_URL+"/"+idPersona,persona);
    }

    deletePersona(idPersona){
        return axios.delete(PERSONA_BASE_REST_API_URL+"/"+idPersona);
    }
}

export default new PersonaAdminService();