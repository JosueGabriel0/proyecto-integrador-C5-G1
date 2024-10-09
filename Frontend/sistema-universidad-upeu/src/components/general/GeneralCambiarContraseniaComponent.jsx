import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import UsuarioAdminService from '../../services/administradorServices/usuario/UsuarioAdminService';

const GeneralCambiarContraseniaComponent = () => {
    const { idUsuario } = useParams();
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (newPassword !== confirmPassword) {
            setError('Las contraseñas no coinciden');
            return;
        }

        try {
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

    return (
        <div>
            <h2>Cambiar Contraseña</h2>
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
                {success && <p style={{ color: 'green' }}>{success}</p>}
            </form>
        </div>
    );
};

export default GeneralCambiarContraseniaComponent;