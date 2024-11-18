import axios from "axios";
import { getToken } from "../../authServices/authService";

const CURSO_DATABASE_REST_API_URL = `${process.env.REACT_APP_API_BASE_URL}/curso`;

class CursoAdminService{
    getAllCursos(){
        return(
            axios.get(CURSO_DATABASE_REST_API_URL, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    getCursoById(idCurso){
        return(
            axios.get(CURSO_DATABASE_REST_API_URL + "/" + idCurso, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    postCurso(curso){
        return(
            axios.post(CURSO_DATABASE_REST_API_URL, curso, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    putCurso(idCurso, curso){
        return(
            axios.put(CURSO_DATABASE_REST_API_URL + "/" + idCurso, curso, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    deleteCurso(idCurso){
        return(
            axios.delete(CURSO_DATABASE_REST_API_URL + "/" + idCurso, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }
}

export default new CursoAdminService();