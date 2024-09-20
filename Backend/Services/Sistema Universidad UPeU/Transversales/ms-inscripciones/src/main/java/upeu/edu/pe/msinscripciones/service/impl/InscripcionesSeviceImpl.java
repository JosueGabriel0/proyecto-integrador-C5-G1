package upeu.edu.pe.msinscripciones.service.impl;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import upeu.edu.pe.msinscripciones.entity.Inscripcion;
import upeu.edu.pe.msinscripciones.exception.ResourceNotFoundException;
import upeu.edu.pe.msinscripciones.repository.InscripcionesRepository;
import upeu.edu.pe.msinscripciones.service.InscripcionesService;

import java.util.List;

@Service
public class InscripcionesSeviceImpl implements InscripcionesService {
    //INYECCION DE DEPENDENCIAS PRINCIPALES PARA Inscripcion
    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    //METODOS PRINCIPALES DE Inscripcion
    @Override
    public Inscripcion guardarInscripcion(Inscripcion Inscripcion) {
        return inscripcionesRepository.save(Inscripcion);
    }

    @Override
    public List<Inscripcion> listarInscripcion(){
        List<Inscripcion> inscripciones = inscripcionesRepository.findAll();



        return inscripciones;
    }

    @Override
    public Inscripcion buscarInscripcionPorId(Long id){
        Inscripcion Inscripcion = inscripcionesRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("La Inscripcion con ID "+id+" no existe"));
        try {
            ResponseEntity<Usuario> usuarioResponse = usuarioFeign.listarUsuarioDtoPorId(Inscripcion.getIdUsuario());
            if(usuarioResponse.getBody() == null){
                throw new ResourceNotFoundException("Usuario con ID "+Inscripcion.getIdUsuario()+" no encontrado");
            }
            Inscripcion.setUsuario(usuarioResponse.getBody());
        }catch (FeignException e){
            throw new RuntimeException("Error al obtener el Usuario con ID " + Inscripcion.getIdUsuario(),e);
        }

        return Inscripcion;
    }

    @Override
    public Inscripcion editarInscripcion(Inscripcion Inscripcion) {
        return inscripcionesRepository.save(Inscripcion);
    }

    @Override
    public void eliminarInscripcion(Long id){
        inscripcionesRepository.deleteById(id);
    }
}
