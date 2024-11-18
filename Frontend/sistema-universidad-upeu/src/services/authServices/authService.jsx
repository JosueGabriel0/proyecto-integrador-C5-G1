// authService.jsx

// Guardar el token JWT en localStorage
export const saveToken = (token) => {
    localStorage.setItem('authToken', token);
};

// Obtener el token JWT desde localStorage
export const getToken = () => {
    return localStorage.getItem('authToken');
};

// Guardar el refresh token en localStorage
export const saveRefreshToken = (refreshToken) => {
    localStorage.setItem('refreshToken', refreshToken);
};

// Obtener el refresh token desde localStorage
export const getRefreshToken = () => {
    return localStorage.getItem('refreshToken');
};

// Verificar si el usuario está autenticado
export const isAuthenticated = () => {
    const token = getToken();
    return !!token; // Devuelve true si el token existe, false si no.
};

// authService.jsx
// Función para realizar el login (petición al microservicio ms-auth)
export const login = async (credentials) => {
    try {
        const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
        });

        if (!response.ok) {
            throw new Error(`Login failed: ${response.statusText}`);
        }

        const data = await response.json();
        saveToken(data.accessToken); // Guardar el accessToken en localStorage
        saveRefreshToken(data.refreshToken); // Guardar el refreshToken en localStorage
        localStorage.setItem('userRole', data.role); // Guardar el rol en localStorage
        return data.accessToken; // Devuelve el accessToken
    } catch (error) {
        console.error('Error logging in:', error);
        throw error;
    }
};

// Función para obtener un nuevo token que dura 5 minutos usando el refreshToken
export const getShortLivedToken = async () => {
    const refreshToken = getRefreshToken(); // Obtener el refreshToken

    if (!refreshToken) {
        throw new Error('No refresh token available');
    }

    try {
        const response = await fetch('http://localhost:9090/auth/refresh', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ refreshToken }), // Enviar el refresh token en el cuerpo de la solicitud
        });

        if (!response.ok) {
            throw new Error(`Token refresh failed: ${response.statusText}`);
        }

        const data = await response.json();
        saveToken(data.accessToken); // Guardar el nuevo accessToken en localStorage
        return data.accessToken; // Devuelve el nuevo accessToken
    } catch (error) {
        console.error('Error getting short-lived token:', error);
        throw error; // Propaga el error para manejarlo en el componente
    }
};

// Esta función debería devolver el rol del usuario
export const getUserRole = () => {
    const token = getToken();
    if (!token) return null;

    try {
        // Decodificar el payload del JWT
        const payload = JSON.parse(atob(token.split('.')[1])); // Decodifica el payload del JWT
        
        console.log("Este es el rol: " + payload.nombreRol); // Asegúrate de que el campo coincide con el nombre en el token
        return payload.nombreRol; // Devuelve el nombre del rol si está presente
    } catch (error) {
        console.error('Error decoding token:', error);
        return null; // Devuelve null si ocurre un error durante la decodificación
    }
};

// Función para validar el token (usada si es necesario validar en algún punto)
export const validateToken = async () => {
    const token = getToken();

    if (!token) {
        return false;
    }

    try {
        const response = await fetch('http://localhost:9090/auth/validate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ token }), // Enviar el token en el cuerpo de la solicitud
        });

        if (!response.ok) {
            throw new Error(`Token validation failed: ${response.statusText}`); // Mejora en el manejo de errores
        }

        return true; // Si la validación es exitosa
    } catch (error) {
        console.error('Error validating token:', error);
        return false; // Devuelve false si ocurre un error durante la validación
    }
};

// Función para cerrar sesión y limpiar el token
export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('refreshToken');
};