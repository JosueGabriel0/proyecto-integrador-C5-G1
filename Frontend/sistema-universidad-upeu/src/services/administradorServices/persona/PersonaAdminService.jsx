import axios from "axios";
import { getToken } from "../../authServices/authService";
const PERSONA_BASE_REST_API_URL = "http://localhost:9090/persona";

class PersonaAdminService{
    getAllPersonas(){
        return axios.get(PERSONA_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getPersonaById(idPersona){
        return axios.get(PERSONA_BASE_REST_API_URL+"/"+idPersona, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createPersona(persona){
        return axios.post(PERSONA_BASE_REST_API_URL,persona,{
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updatePersona(idPersona,persona){
        return axios.put(PERSONA_BASE_REST_API_URL+"/"+idPersona,persona, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deletePersona(idPersona){
        return axios.delete(PERSONA_BASE_REST_API_URL+"/"+idPersona,{
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    async uploadProfileImage(idPersona, formData) {
        return await axios.post(`http://localhost:9090/persona/uploadProfileImage/${idPersona}`, formData, {
            headers: {
                Authorization: `Bearer ${getToken()}`,
                'Content-Type': 'multipart/form-data',
            },
        });
    }
}

export default new PersonaAdminService();