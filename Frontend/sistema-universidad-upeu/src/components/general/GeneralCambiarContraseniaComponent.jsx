import React, { useState, useEffect } from 'react'; 
import { useParams } from 'react-router-dom';
import UsuarioAdminService from '../../services/administradorServices/usuario/UsuarioAdminService';
import { getShortLivedToken } from '../../services/authServices/authService'; // Importar el servicio
import { Link } from 'react-router-dom';

const GeneralCambiarContraseniaComponent = () => {
    const { idUsuario } = useParams();
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [timeLeft, setTimeLeft] = useState(3600); // 1 hora en segundos

    useEffect(() => {
        const interval = setInterval(() => {
            setTimeLeft((prevTime) => {
                if (prevTime <= 0) {
                    clearInterval(interval);
                    return 0;
                }
                return prevTime - 1;
            });
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (newPassword !== confirmPassword) {
            setError('Las contraseñas no coinciden');
            return;
        }

        try {
            // Obtener un nuevo token de 5 minutos
            const token = await getShortLivedToken(); // Obtener el token corto

            // Obtener los datos actuales del usuario
            const response = await UsuarioAdminService.getUsuarioById(idUsuario);
            const usuarioExistente = response.data;

            // Actualizar solo la contraseña, manteniendo el resto de los campos iguales
            const usuarioData = {
                username: usuarioExistente.username,
                password: newPassword,
                email: usuarioExistente.email,
                enabled: usuarioExistente.enabled,
                ultimoLogin: usuarioExistente.ultimoLogin,
                tokenRecuperacion: usuarioExistente.tokenRecuperacion,
                tokenRecuperacionExpiracion: usuarioExistente.tokenRecuperacionExpiracion
            };

            await UsuarioAdminService.updateUsuario(idUsuario, usuarioData);
            setSuccess('Contraseña actualizada con éxito');
            setNewPassword('');
            setConfirmPassword('');
        } catch (err) {
            setError('Error al actualizar la contraseña: ' + err.message);
        }
    };

    const formatTimeLeft = (time) => {
        const minutes = Math.floor(time / 60);
        const seconds = time % 60;
        return `${minutes} min ${seconds < 10 ? `0${seconds}` : seconds} seg`;
    };

    return (
        <div>
            <h2>Cambiar Contraseña</h2>
            {timeLeft > 0 && (
                <div style={{ textAlign: 'right', color: 'red' }}>
                    El token dejará de ser válido en: {formatTimeLeft(timeLeft)}
                </div>
            )}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nueva Contraseña:</label>
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Confirmar Contraseña:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Cambiar Contraseña</button>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {success && (
                    <div style={{ color: 'green' }}>
                        <p>{success}</p>
                        <Link to="/login" style={{ textDecoration: 'underline', color: '#007bff' }}>
                            Volver al Login
                        </Link>
                    </div>
                )}
            </form>
        </div>
    );
};

export default GeneralCambiarContraseniaComponent;