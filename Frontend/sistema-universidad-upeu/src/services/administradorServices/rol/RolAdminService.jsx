import axios from "axios";

const ROL_BASE_REST_API_URL = "http://localhost:9090/rol";

class RolService{

    getAllRoles(){
        return axios.get(ROL_BASE_REST_API_URL);
    }

    createRol(rol){
        return axios.post(ROL_BASE_REST_API_URL,rol);
    }

    getRolById(idRol){
        return axios.get(ROL_BASE_REST_API_URL+"/"+idRol);
    }

    updateRol(idRol,rol){
        return axios.put(ROL_BASE_REST_API_URL+"/"+idRol,rol);
    }

    deleteRol(idRol){
        return axios.delete(ROL_BASE_REST_API_URL + '/' + idRol)
    }
}

export default new RolService();