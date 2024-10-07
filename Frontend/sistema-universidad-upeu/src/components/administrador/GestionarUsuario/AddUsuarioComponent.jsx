import { Link, useNavigate, useParams} from "react-router-dom";
import UsuarioAdminService from "../../../services/administradorServices/usuario/UsuarioAdminService";
import { useEffect, useState } from "react";
import RolAdminService from "../../../services/administradorServices/rol/RolAdminService";
function AddUsuarioComponent(){
    
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [enabled, setEnabled] = useState("");
    const [idRol, setIdRol] = useState("");
    const [ultimoLogin, setUltimoLogin] = useState("");
    const [tokenRecuperacion, setTokenRecuperacion] = useState("");
    const [tokenRecuperacionExpiracion, setTokenRecuperacionExpiracion] = useState("");

    const [roles, setRoles] = useState([]);

    const navigate = useNavigate();
    const {id} = useParams();

    function saveOrUpdateUsuario(e){
        e.preventDefault();
        const usuario = {username, password, email, enabled, idRol, ultimoLogin, tokenRecuperacion, tokenRecuperacionExpiracion};
        console.log(usuario);

        if(id){
            UsuarioAdminService.updateUsuario(id,usuario).then(response => {
                console.log(response.data);
                navigate("/usuarios");
            }).catch(error => {
                console.log(error);
            })
        }else{
            UsuarioAdminService.createUsuario(usuario).then(response => {
                console.log(response.data);
                navigate("/usuarios");
            }).catch(error => {
                console.log(error);
            })
        }
    }

    useEffect(()=>{
        // Cargar roles
        RolAdminService.getAllRoles().then(response => {
            setRoles(response.data); // Guardar los roles en el estado
        }).catch(error => {
            console.log(error);
        });

        // Si hay un ID en la URL, cargar los datos del usuario
        if(id){
            UsuarioAdminService.getUsuarioById(id).then(response => {
                setUsername(response.data.username);
                setPassword(response.data.password);
                setEmail(response.data.email);
                setEnabled(response.data.enabled);
                setIdRol(response.data.idRol);
                setUltimoLogin(response.data.ultimoLogin);
                setTokenRecuperacion(response.data.tokenRecuperacion);
                setTokenRecuperacionExpiracion(response.data.tokenRecuperacionExpiracion);
            }).catch(error =>{
                console.log(error);
            });
        }
    },[id])

    function title(){
        if(id){
            return <h2>Actualizar Usuario</h2>
        }else{
            return <h2>Agregar Usuario</h2>
        }
    }

    function botonAgregarOActualizar(){
        if(id){
            return <h2>Actualizar</h2>
        }else{
            return <h2>Agregar</h2>
        }
    }
    
    function PasswordInput() {
        const [password, setPassword] = useState("");
        const [showPassword, setShowPassword] = useState(false);
      
        return (
          <div>
            <label>Contraseña</label>
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Inserte la contraseña"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete="off"
              minLength={8} // longitud mínima recomendada
              required
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? "Ocultar" : "Mostrar"}
            </button>
          </div>
        );
      }

    return(
        <div>
            <h1>{ title() }</h1>
            <form>
                <div>
                    <label>Nombre de Usuario</label>
                    <input required type="text" placeholder="Inserte el nombre de ususario" name="username" value={username} onChange={(e) => setUsername(e.target.value)}/>
                </div>
                
                {/*<div>
                    <label>Contraseña</label>
                    <input type="text" placeholder="Inserte la contraseña" name="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>*/}
                {PasswordInput()}

                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="Ingrese el email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    {/* Mensaje de error opcional */}
                    <span className="error-message" style={{ display: email && !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email) ? 'block' : 'none' }}>
                        Por favor, ingrese un correo electrónico válido.
                    </span>
                </div>

                <div>
                    <label>Disponible</label>
                    <select required value={enabled} onChange={(e) => setEnabled(e.target.value === 'true')}>
                        <option value={true}>Disponible</option>
                        <option value={false}>No disponible</option>
                    </select>
                </div>

                <div>
                    <label>Rol</label>
                    <select required value={idRol} onChange={(e) => setIdRol(e.target.value)}>
                        <option required value="">Seleccione un rol</option>
                        {roles.map((rol) => (
                            <option key={rol.idRol} value={rol.idRol}>
                                {rol.nombreRol}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Ultimo Login</label>
                    <input required type="text" placeholder="Inserte el ultimo login" name="ultimoLogin" value={ultimoLogin} onChange={(e) => setUltimoLogin(e.target.value)}/>
                </div>

                <div>
                    <label htmlFor="">Token de Recuperacion</label>
                    <input required type="text" placeholder="Inserte el token de recuperacion" name="tokenRecuperacion" value={tokenRecuperacion} onChange={(e) => setTokenRecuperacion(e.target.value)}/>
                </div>

                <div>
                    <label htmlFor="">Expiracion del Token de Recuperacion</label>
                    <input required type="text" placeholder="Inserte la expiracion del token de recuperacion" name="tokenRecuperacionExpiracion" value={tokenRecuperacionExpiracion} onChange={(e) => setTokenRecuperacionExpiracion(e.target.value)}/>
                </div>
                
                <button onClick={(e) => saveOrUpdateUsuario(e)}>{botonAgregarOActualizar()}</button>
                &nbsp;
                &nbsp;
                <Link to="/usuarios">Cancelar</Link>
            </form>
        </div>
    )
}

export default AddUsuarioComponent;