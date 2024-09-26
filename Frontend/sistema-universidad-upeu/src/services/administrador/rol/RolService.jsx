import axios from "axios";

const ROL_BASE_REST_API_URL = "http://localhost:9090/rol";

class RolService{

    getAllRoles(){
        return axios.get(ROL_BASE_REST_API_URL);
    }

    createRol(rol){
        return axios.post(ROL_BASE_REST_API_URL,rol);
    }
}

export default new RolService();