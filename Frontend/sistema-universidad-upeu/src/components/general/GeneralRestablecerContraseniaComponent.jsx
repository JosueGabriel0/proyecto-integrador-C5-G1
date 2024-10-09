import React, { useState } from 'react';
import { sendEmail } from '../../services/authServices/emailServices/emailService'; // Ajusta la ruta según tu proyecto
import UsuarioAdminService from '../../services/administradorServices/usuario/UsuarioAdminService'; // Ajusta la ruta según tu proyecto

const GeneralRestablecerContraseniaComponent = () => {
    const [email, setEmail] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        try {
            // Usar el método getUsuarioByEmail para verificar si el correo existe
            const response = await UsuarioAdminService.getUsuarioByEmail(email);
            const usuarioEncontrado = response.data;

            if (!usuarioEncontrado) {
                setError('El correo electrónico no coincide con ningún usuario registrado');
                return;
            }

            // Si el correo existe, proceder a enviar el correo de restablecimiento de contraseña
            const subject = 'Restablecimiento de contraseña';
            const body = `
                <div style="text-align: center;">
                    <h2>Solicitud de Restablecimiento de Contraseña</h2>
                    <p>Hemos recibido una solicitud para restablecer tu contraseña.</p>
                    <p>Si no hiciste esta solicitud, simplemente ignora este correo.</p>
                    <a 
                        href="http://localhost:3000/cambiar-contrasenia?idUsuario=${usuarioEncontrado.idUsuario}&email=${encodeURIComponent(email)}" 
                        style="padding: 10px 20px; color: white; background-color: #007bff; text-decoration: none; border-radius: 5px;">
                        Restablecer Contraseña
                    </a>
                </div>
            `;
            const isHtml = true;

            await sendEmail(email, subject, body, isHtml);
            setSuccess('Correo para restablecer contraseña enviado con éxito');
            setEmail('');
        } catch (err) {
            setError('Error al enviar el correo: ' + err.message);
        }
    };

    return (
        <div>
            <h2>Restablecer Contraseña</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Ingresa tu correo electrónico"
                    required
                    style={{ marginBottom: '10px', padding: '10px', width: '100%' }}
                />
                <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '5px' }}>
                    Restablecer Contraseña
                </button>
            </form>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
        </div>
    );
};

export default GeneralRestablecerContraseniaComponent;