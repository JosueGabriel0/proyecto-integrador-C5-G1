import axios from "axios";

const ROL_BASE_REST_API_URL = "http://localhost:9090/rol";

class RolService{

    getAllRoles(){
        return axios.get(ROL_BASE_REST_API_URL);
    }
}

export default new RolService();