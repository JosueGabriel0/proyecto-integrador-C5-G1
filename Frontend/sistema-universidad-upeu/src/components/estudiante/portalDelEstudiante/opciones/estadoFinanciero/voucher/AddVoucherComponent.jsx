import { useState } from "react";
import VoucherService from "../../../../../../services/cuentaFinancieraServices/VoucherService";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from "react-router-dom";

function AddVoucherComponent() {
    const [ nombreBanco, setNombreBanco ] = useState("");
    const [ numeroDeOperacion, setNumeroDeOperacion ] = useState("");
    const [ fechaDeOperacion, setFechaDeOperacion ] = useState("");
    const [ importe, setImporte ] = useState("");
    const [ estado, setEstado ] = useState("REGISTRADO");
    const [ voucherURL, setVoucherURL ] = useState(null);

    const { idCuentaFinanciera, anioSeleccionado } = useParams();
    const navigate = useNavigate();

    function saveVoucher(e){
        e.preventDefault();

        const formData = new FormData();

        formData.append("nombreBanco", nombreBanco);
        formData.append("numeroDeOperacion", numeroDeOperacion);
        formData.append("fechaDeOperacion", fechaDeOperacion);
        formData.append("importe", importe);
        formData.append("estado", estado);

        if(voucherURL){
            formData.append("file", voucherURL)
        }
        VoucherService.postVoucherToCuentaFinanciera(idCuentaFinanciera, formData).then((response) => {
            console.log(response.data);
            navigate(`/list-vouchers/${idCuentaFinanciera}/${anioSeleccionado}`);
        }).catch((error) => {
            console.log(error);
        });
    }

    return (
        <div>
            <Link to={`/list-vouchers/${idCuentaFinanciera}/${anioSeleccionado}`}>Cancelar</Link>
            <h1>Nuevo Adjunto</h1>
            <form onSubmit={(e) => saveVoucher(e)} encType="multipart/form-data">
                <div>
                    <label>Banco:</label>
                    <select name="nombreBanco" value={nombreBanco} onChange={(e) => {setNombreBanco(e.target.value)}}>
                        <option value="">Ingrese el banco</option>
                        <option value="BANCO DE CREDITO DEL PERU">BANCO DE CRÉDITO DEL PERÚ</option>
                        <option value="BBVA BANCO CONTINENTAL">BBVA BANCO CONTINENTAL</option>
                        <option value="CMAC CAJA AREQUIPA">CMAC CAJA AREQUIPA</option>
                        <option value="INTERBANK">INTERBANK</option>
                        <option value="SCOTIABANK PERÚ">SCOTIABANK PERÚ</option>
                        <option value="OTROS">OTROS</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="">N° Operación</label>
                    <input type="text" placeholder="Ingrese el numero" name="numeroDeOperacion" value={numeroDeOperacion} onChange={(e) => {setNumeroDeOperacion(e.target.value)}}/>
                </div>

                <div>
                    <label htmlFor="">Fecha Operación</label>
                    <input type="date" placeholder="Fecha operación" name="fechaDeOperacion" value={fechaDeOperacion} onChange={(e) => {setFechaDeOperacion(e.target.value)}}/>
                </div>

                <div>
                    <label htmlFor="">Importe:</label>
                    <input type="text" placeholder="Ingrese el importe" name="importe" value={importe} onChange={(e) => {setImporte(e.target.value)}}/>
                </div>

                <div>
                    <label htmlFor="">Constancia de depósito:</label>
                    <input type="file" accept="image/*" name="voucherURL" id="voucherURL" onChange={(e) => {setVoucherURL(e.target.files[0])}} />
                </div>

                <div>
                    <button type="submit">GUARDAR</button>
                </div>

            </form>
        </div>
    )
}

export default AddVoucherComponent;