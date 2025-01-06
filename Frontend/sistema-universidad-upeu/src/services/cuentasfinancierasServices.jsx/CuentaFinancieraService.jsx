import axios from "axios";
import { getToken } from "../authServices/authService";

const CUENTAFINANCIERA_BASE_REST_API_URL = `${process.env.REACT_APP_API_BASE_URL}/cuentaFinanciera`;

class CuentaFinancieraService {

    getAllCuentasFinancieras() {
        return axios.get(CUENTAFINANCIERA_BASE_REST_API_URL, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    createCuentaFinanciera(cuentaFinanciera) {
        return axios.post(CUENTAFINANCIERA_BASE_REST_API_URL, cuentaFinanciera, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    getCuentaFinancieraById(idCuentaFinanciera) {
        return axios.get(`${CUENTAFINANCIERA_BASE_REST_API_URL}/${idCuentaFinanciera}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    updateCuentaFinanciera(idCuentaFinanciera, cuentaFinanciera) {
        return axios.put(`${CUENTAFINANCIERA_BASE_REST_API_URL}/${idCuentaFinanciera}`, cuentaFinanciera, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }

    deleteCuentaFinanciera(idCuentaFinanciera) {
        return axios.delete(`${USUARIO_BASE_REST_API_URL}/${idCuentaFinanciera}`, {
            headers: {
                Authorization: `Bearer ${getToken()}`
            }
        });
    }
}

export default new CuentaFinancieraService();