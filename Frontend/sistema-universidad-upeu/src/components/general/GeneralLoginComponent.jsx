import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Necesario para redireccionar después de login

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); // Hook para redirigir

  useEffect(() => {
    if (error) {
      const timer = setTimeout(() => {
        setError('');
      }, 3000);

      return () => clearTimeout(timer);
    }
  }, [error]);

  useEffect(() => {
    if (email !== '' && password !== '') {
      console.log('Ambos campos están completos.');
    }
  }, [email, password]);

  const handleLogin = (e) => {
    e.preventDefault();

    if (email === '' || password === '') {
      setError('Por favor, rellene todos los campos');
    } else {
      // Simula la autenticación exitosa
      localStorage.setItem('token', 'usuarioAutenticado'); // Guardar token simulado en localStorage
      setError('');
      console.log(`Email: ${email}, Password: ${password}`);
      navigate('/dashboard-administrador'); // Redirigir al dashboard después del login exitoso
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <div>
        <label>Email:</label>
        <input 
          type="email" 
          value={email}  
          onChange={(e) => setEmail(e.target.value)}  
        />
      </div>
      <div>
        <label>Password:</label>
        <input 
          type="password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
        />
      </div>
      <button type="submit">Login</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </form>
  );
};

export default Login;