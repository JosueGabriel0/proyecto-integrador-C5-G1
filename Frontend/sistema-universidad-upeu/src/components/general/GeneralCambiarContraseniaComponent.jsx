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
    const [timeLeft, setTimeLeft] = useState(120); // Tiempo de expiración en segundos (15 minutos)
    const [showPassword, setShowPassword] = useState(false);

    // Validar el token cuando se carga el componente
    useEffect(() => {
        const validateToken = async () => {
            console.log("Token recibido desde useParams:", token);
            try {
                const response = await UsuarioAdminService.validateResetToken(token);
                console.log("Respuesta de la validación del token:", JSON.stringify(response.data, null, 2));
                setTokenValid(response.data.valid);
                console.log("Estado del token validado:", response.data.valid);
            } catch (err) {
                setError('Token inválido o expirado. Solicita un nuevo enlace de restablecimiento.');
                setTokenValid(false);
                console.error("Error al validar el token:", err);
            }
        };

        validateToken();
    }, [token]);

    useEffect(() => {
        if (tokenValid) {
            const intervalId = setInterval(() => {
                setTimeLeft((prevTime) => {
                    if (prevTime <= 1) {
                        clearInterval(intervalId);
                        setTokenValid(false); // Opcional: considera el token como inválido si el tiempo se acaba
                        return 0;
                    }
                    return prevTime - 1;
                });
            }, 1000); // Decrementar cada segundo

            return () => clearInterval(intervalId); // Limpiar el intervalo al desmontar
        }
    }, [tokenValid]);

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
            setError('Error al actualizar la contraseña: ' + err.message);
        }
    };

    return (
        <div>
            <h2>Cambiar Contraseña</h2>
            {tokenValid ? (
                <>
                    <div style={{ position: 'absolute', right: '20px', top: '20px' }}>
                        <p>Tiempo restante: {Math.floor(timeLeft / 60)}:{(timeLeft % 60).toString().padStart(2, '0')}</p>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label>Nueva Contraseña:</label>
                            <input
                                type={showPassword ? "text" : "password"}
                                placeholder="Ingrese su contraseña"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                                autoComplete="off"
                                minLength={8}
                                required
                            />
                            <button
                                type="button"
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? "Ocultar" : "Mostrar"}
                            </button>
                        </div>
                        <div>
                            <label>Confirmar Contraseña:</label>
                            <input
                                type={showPassword ? "text" : "password"}
                                placeholder="Ingrese su contraseña"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                autoComplete="off"
                                minLength={8}
                                required
                            />
                            <button
                                type="button"
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? "Ocultar" : "Mostrar"}
                            </button>
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
                </>
            ) : (
                <p style={{ color: 'red' }}>{error || 'Verificando el token...'}</p>
            )}
        </div>
    );
};

export default GeneralCambiarContraseniaComponent;