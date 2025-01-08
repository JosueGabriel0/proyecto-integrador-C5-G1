import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import VoucherService from "../../../../../../services/cuentaFinancieraServices/VoucherService";

function ListVouchersComponent(){

    const [vouchers, setVouchers] = useState([]);

    const { anioSeleccionado } = useParams();

    function listarVouchersPorAnio(anio){
        console.log("Este es el anio: " + anio);
        VoucherService.getVoucherByYear(anio).then((response) => {
            setVouchers(response.data);
            console.log(response.data)
        }).catch((error) => {
            console.log(error);
        })
    }

    useEffect(() => {
        listarVouchersPorAnio(anioSeleccionado);
    }, [])

    return(
        <div>
            <h2>Mis voucher adjuntos: </h2>
            <Link to="/add-voucher">Agregar Nuevo</Link>
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
                                    <td>{voucher.idVoucher}</td>
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