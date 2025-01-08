import React from "react";
import VoucherService from "../../../../../../services/cuentaFinancieraServices/VoucherService";
import { useParams } from "react-router-dom";

function VerVoucher(){

    const { voucherURL } = useParams();

    async function imagenObtenida(){
        const imagenObtenida = await VoucherService.getVoucherImagen(voucherURL);
        return imagenObtenida ? imagenObtenida : "Desconocido";
    }

    return(
        <div>
            <h2>Imagen del Voucher Enviado</h2>
            <img src={imagenObtenida()} alt="Imagen del Voucher" style={{ width: '50px', height: '50px' }} />
        </div>
    )
}

export default VerVoucher;