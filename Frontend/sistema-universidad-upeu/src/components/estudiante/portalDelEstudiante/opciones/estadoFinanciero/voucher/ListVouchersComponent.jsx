import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import VoucherService from "../../../../../../services/cuentaFinancieraServices/VoucherService";

function ListVouchersComponent(){

    const [vouchers, setVouchers] = useState([]);

    const { idCuentaFinanciera, anioSeleccionado } = useParams();

    function listarVouchersPorCuentaYAnio(idCuentaFinanciera, anio){
        console.log("Este es el idCuentaFinanciera: "+ idCuentaFinanciera)
        console.log("Este es el anio: " + anio);
        VoucherService.getVoucherByCuentaYAnio(idCuentaFinanciera, anio).then((response) => {
            setVouchers(response.data);
            console.log(response.data)
        }).catch((error) => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarVouchersPorCuentaYAnio(idCuentaFinanciera, anioSeleccionado);
    }, [])

    return(
        <div>
            <Link to="/estado-financiero">Retroceder</Link>
            <h2>Mis voucher adjuntos: </h2>
            <Link to={`/add-voucher/${idCuentaFinanciera}/${anioSeleccionado}`}>Agregar Nuevo</Link>
            <table>
                <thead>
                    <tr>
                        Registrado
                        Verificado
                        Procesado
                        Rechazado
                    </tr>
                    <tr>
                        <th>Nombre del Banco</th>
                        <th>N° operacion</th>
                        <th>Fecha operación</th>
                        <th>Importe</th>
                        <th>Estado</th>
                        <th>Ver V.</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        vouchers.map((voucher) => {
                            return(
                                <tr key={voucher.idVoucher}>
                                    <td>{voucher.nombreBanco}</td>
                                    <td>{voucher.numeroDeOperacion}</td>
                                    <td>{voucher.fechaDeOperacion}</td>
                                    <td>{voucher.importe}</td>
                                    <td>{voucher.estado}</td>
                                    <td><Link to={`/ver-voucher/${voucher.voucherURL}`}>Ver</Link></td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListVouchersComponent;