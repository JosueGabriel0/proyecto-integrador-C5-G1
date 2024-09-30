import React, { useState } from 'react';
import { login } from '../../services/authServices/authService'; // Asegúrate de la ruta correcta
import { useNavigate } from 'react-router-dom';

const GeneralLoginComponent = () => {
    const [credentials, setCredentials] = useState({ userName: '', password: '' });
    const [error, setError] = useState(null);
    const navigate = useNavigate(); // Hook para redirección

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
            setError('Invalid username or password'); // Manejo de errores
        }
    };

    return (
        <div>
            <h2>Login</h2>
            {error && <p>{error}</p>}
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username:</label>
                    <input type="text" name="userName" value={credentials.userName} onChange={handleInputChange} />
                </div>
                <div>
                    <label>Password:</label>
                    <input type="password" name="password" value={credentials.password} onChange={handleInputChange} />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default GeneralLoginComponent;