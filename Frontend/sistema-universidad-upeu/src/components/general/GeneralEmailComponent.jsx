import React, { useState } from 'react';
import { sendEmail } from '../../services/authServices/emailServices/emailService'; // Asegúrate de la ruta correcta

const GeneralEmailComponent = () => {
    const [to, setTo] = useState('');
    const [subject, setSubject] = useState('');
    const [body, setBody] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault(); // Evita que la página se recargue
        setError(null);
        setSuccess(null);

        try {
            // Enviar el correo como HTML
            await sendEmail(to, subject, body, true); // Establece isHtml en true
            setSuccess('Correo enviado con éxito');
            setTo('');
            setSubject('');
            setBody('');
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="email"
                value={to}
                onChange={(e) => setTo(e.target.value)}
                placeholder="Correo del destinatario"
                required
            />
            <input
                type="text"
                value={subject}
                onChange={(e) => setSubject(e.target.value)}
                placeholder="Asunto"
                required
            />
            <textarea
                value={body}
                onChange={(e) => setBody(e.target.value)}
                placeholder="Cuerpo del correo (HTML)"
                required
            />
            <button type="submit">Enviar Correo</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
        </form>
    );
};

export default GeneralEmailComponent;