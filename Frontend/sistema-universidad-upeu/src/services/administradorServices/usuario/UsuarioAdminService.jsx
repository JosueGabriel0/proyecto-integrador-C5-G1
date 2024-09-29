import axios from "axios";

const USUARIO_BASE_REST_API_URL = "http://localhost:9090/usuario";

class UsuarioAdminService{

    getAllUsuarios(){
        return axios.get(USUARIO_BASE_REST_API_URL);
    }

    createUsuario(usuario){
        return axios.post(USUARIO_BASE_REST_API_URL,usuario);
    }

    deleteUsuario(idUsuario){
        return axios.delete(USUARIO_BASE_REST_API_URL+ "/"+ idUsuario);
    }
}

export default new UsuarioAdminService();