import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import UsuarioAdminService from '../../services/administradorServices/usuario/UsuarioAdminService';

const GeneralCambiarContraseniaComponent = () => {
    const { token } = useParams();
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [tokenValid, setTokenValid] = useState(false);

    // Validar el token cuando se carga el componente
    useEffect(() => {
        if (token) {  // Asegurarse de que el token esté definido antes de validar
            console.log('Token recibido desde useParams:', token); // Depurar token recibido

            const validateToken = async () => {
                try {
                    const response = await UsuarioAdminService.validateResetToken(token);
                    console.log('Respuesta de la validación del token:', response); // Depurar respuesta de la API
                    setTokenValid(response.data.valid);
                    console.log('Estado del token validado:', response.data.valid);
                } catch (err) {
                    console.error('Error al validar el token:', err); // Depurar error
                    setError('Token inválido o expirado. Solicita un nuevo enlace de restablecimiento.');
                    setTokenValid(false);
                }
            };

            validateToken();
        } else {
            console.error('No se recibió ningún token desde useParams.');
        }
    }, [token]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (newPassword !== confirmPassword) {
            setError('Las contraseñas no coinciden');
            return;
        }

        try {
            // Actualizar la contraseña del usuario utilizando el token
            await UsuarioAdminService.resetPasswordWithToken(token, newPassword);
            setSuccess('Contraseña actualizada con éxito');
            setNewPassword('');
            setConfirmPassword('');
        } catch (err) {
            console.error('Error al actualizar la contraseña:', err); // Depurar error
            setError('Error al actualizar la contraseña: ' + err.message);
        }
    };

    return (
        <div>
            <h2>Cambiar Contraseña</h2>
            {tokenValid ? (
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
            ) : (
                <p style={{ color: 'red' }}>{error || 'Verificando el token...'}</p>
            )}
        </div>
    );
};

export default GeneralCambiarContraseniaComponent;