import React, { useState } from 'react';
import { login } from '../../services/authServices/authService'; // Asegúrate de la ruta correcta
import { useNavigate, Link } from 'react-router-dom';

const GeneralLoginComponent = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState(null);
    const navigate = useNavigate(); // Hook para redirección

    const [showPassword, setShowPassword] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCredentials({
            ...credentials,
            [name]: value,
        });
    };

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const token = await login(credentials); // Intenta iniciar sesión y obtiene el token
            navigate('/dashboard-administrador'); // Redirige a dashboard-administrador tras login exitoso
        } catch (error) {
            setError('El nombre de Usuario o la Contraseña son incorrectos'); // Manejo de errores
        }
    };

    return (
        <div>
            <Link to="/">Inicio</Link>
            <h2>Login</h2>
            {error && <p>{error}</p>}
            <form onSubmit={handleLogin}>
                <div>
                    <label>Nombre de Usuario:</label>
                    <input type="text" placeholder="Ingrese su usuario" name="username" value={credentials.username} onChange={handleInputChange} />
                </div>
                <div>
                    <label>Contraseña:</label>
                    <input type={showPassword ? "text" : "password"} placeholder="Ingrese su contraseña" name="password" value={credentials.password} onChange={handleInputChange} autoComplete="off" minLength={8} required />
                    <button
                        type="button"
                        onClick={() => setShowPassword(!showPassword)}
                    >
                        {showPassword ? "Ocultar" : "Mostrar"}
                    </button>
                </div>
                <button type="submit">Login</button>
            </form>
            <div>
                <Link to="/restablecimiento-contrasenia">Olvide mi Contraseña</Link>
            </div>
        </div>
    );
};

export default GeneralLoginComponent;