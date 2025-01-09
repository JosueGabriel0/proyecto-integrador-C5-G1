import React, { useEffect, useState } from "react";
import VoucherService from "../../../../../../services/cuentaFinancieraServices/VoucherService";
import { useParams } from "react-router-dom";

function VerVoucherComponent() {
  const { voucherURL } = useParams(); // Obtener el parámetro de la URL
  const [imagenUrl, setImagenUrl] = useState(null); // Estado para almacenar la URL de la imagen
  const [error, setError] = useState(null); // Estado para manejar errores

  // Función para obtener la imagen al cargar el componente
  useEffect(() => {
    async function fetchImagen() {
      try {
        const imagenObtenida = await VoucherService.getVoucherImagen(voucherURL);
        if (imagenObtenida) {
          setImagenUrl(imagenObtenida); // Guardar la URL de la imagen en el estado
        } else {
          setError("No se pudo obtener la imagen.");
        }
      } catch (e) {
        setError("Hubo un error al cargar la imagen.");
        console.error(e);
      }
    }
    fetchImagen();
  }, [voucherURL]); // Dependencia de useEffect para que se ejecute cuando cambie voucherURL

  return (
    <div>
      <h2>Imagen del Voucher Enviado</h2>
      {error ? (
        <p style={{ color: "red" }}>{error}</p>
      ) : imagenUrl ? (
        <img
          src={imagenUrl}
          alt="Imagen del Voucher"
          style={{ width: "300px", height: "300px" }}
        />
      ) : (
        <p>Cargando imagen...</p>
      )}
    </div>
  );
}

export default VerVoucherComponent;