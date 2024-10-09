import axios from 'axios';
import qs from 'qs'; // Importa qs para serializar

const EMAIL_BASE_REST_API_URL = "http://localhost:9090/api/email/send";

const sendEmail = async (to, subject, body) => {
    try {
        const response = await axios.post(EMAIL_BASE_REST_API_URL, qs.stringify({ // Usa qs.stringify para serializar
            to,
            subject,
            body,
        }), {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Asegúrate de establecer el tipo de contenido
            },
        });
        return response.data; // O el objeto completo según lo que necesites
    } catch (error) {
        throw new Error('Error al enviar el correo: ' + error.message);
    }
};

export { sendEmail };