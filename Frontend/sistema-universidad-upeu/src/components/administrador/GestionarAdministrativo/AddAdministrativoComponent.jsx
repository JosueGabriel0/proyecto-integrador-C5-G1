import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import AdministrativoAdminService from "../../../services/administradorServices/administrativo/AdministrativoAdminService";

function AddAdmistrativoComponent() {

    const { id } = useParams();
    const [registroPagos, setRegistroPagos] = useState("");
    const [montoTotalPagos, setMontoTotalPagos] = useState("");
    const [fechaUltimoPago, setFechaUltimoPago] = useState("");
    const [gestionEmpleados, setGestionEmpleados] = useState("");
    const [fechaContratacion, setFechaContratacion] = useState("");
    const [cargoEmpleado, setCargoEmpleado] = useState("");
    const [solicitudesPendientes, setSolicitudesPendientes] = useState("");
    const [fechaSolicitud, setFechaSolicitud] = useState("");
    const [idPersona, setIdPersona] = useState("");

    const [personas, setPersonas] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        setRegistroPagos()
    }, [])

    saveOrUpdateAdministrativo(){
        const administrativos = { registroPagos, montoTotalPagos, fechaUltimoPago, gestionEmpleados, fechaContratacion, cargoEmpleado, solicitudesPendientes, fechaSolicitud, idPersona }
        if (id) {
            AdministrativoAdminService.updateAdministrativo(id, administrativos).then(response => {
                console.log(response.data);
                navigate("/administrativos");
            }).catch(error => {
                console.log(error);
            })
        } else {
            AdministrativoAdminService.createAdministrativo(administrativos).then(response => {
                console.log(response.data);
                navigate("/administrativos")
            }).catch(error => {
                console.log(error);
            })
        }
    }
    return (
        <div className="container"></div>
    )
}

export default AddAdmistrativoComponent;