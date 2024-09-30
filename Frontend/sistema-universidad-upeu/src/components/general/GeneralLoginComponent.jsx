// GeneralLoginComponent.js

import React, { useState } from 'react';
import { login } from '../../services/authServices/authService';
import { useNavigate } from 'react-router-dom';

const GeneralLoginComponent = () => {
  const [credentials, setCredentials] = useState({ userName: '', password: '' });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

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
      await login(credentials);
      navigate('/dashboard-administrador'); // Redirigir al dashboard después del login
    } catch (error) {
      setError('Invalid username or password');
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