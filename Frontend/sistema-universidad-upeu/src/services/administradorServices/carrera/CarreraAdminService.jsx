import axios from "axios";
import { getToken } from "../../authServices/authService";

const CARRERA_DATABASE_REST_API_URL = "http://localhost:9090/carrera";

class CarreraAdminService{
    getAllCarreras(){
        return(
            axios.get(CARRERA_DATABASE_REST_API_URL, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    getCarreraById(idCarrera){
        return(
            axios.get(CARRERA_DATABASE_REST_API_URL + "/" + idCarrera, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    postCarrera(carrera){
        return(
            axios.post(CARRERA_DATABASE_REST_API_URL, carrera, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    putCarrera(idCarrera, carrera){
        return(
            axios.put(CARRERA_DATABASE_REST_API_URL + "/" + idCarrera, carrera, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }

    deleteCarrera(idCarrera){
        return(
            axios.delete(CARRERA_DATABASE_REST_API_URL + "/" + idCarrera, {
                headers: {
                    Authorization: `Bearer ${getToken()}`
                }
            })
        )
    }
}

export default new CarreraAdminService();