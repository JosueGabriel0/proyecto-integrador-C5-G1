package upeu.edu.pe.msmatriculas.service;

import upeu.edu.pe.msmatriculas.entity.Matricula;

import java.util.List;

public interface MatriculaService {

    /**
     * Crea una nueva matrícula después de validar todas las entidades relacionadas y las condiciones necesarias.
     *
     * @param matricula la matrícula a crear.
     * @return la matrícula creada.
     */
    Matricula crearMatricula(Matricula matricula);

    /**
     * Obtiene una matrícula por su ID.
     *
     * @param idMatricula el ID de la matrícula.
     * @return la matrícula encontrada.
     * @throws RuntimeException si la matrícula no se encuentra.
     */
    Matricula obtenerMatriculaPorId(Long idMatricula);

    /**
     * Obtiene todas las matrículas existentes en el sistema.
     *
     * @return una lista de todas las matrículas.
     */
    List<Matricula> obtenerMatriculas();

    /**
     * Actualiza una matrícula existente.
     *
     * @param idMatricula   el ID de la matrícula a actualizar.
     * @param nuevaMatricula los nuevos datos de la matrícula.
     * @return la matrícula actualizada.
     * @throws RuntimeException si no se cumple alguna de las validaciones.
     */
    Matricula actualizarMatricula(Long idMatricula, Matricula nuevaMatricula);

    /**
     * Elimina una matrícula por su ID. Solo puede eliminarse si está en estado PENDIENTE.
     *
     * @param idMatricula el ID de la matrícula a eliminar.
     * @throws RuntimeException si la matrícula no está en estado PENDIENTE.
     */
    void eliminarMatricula(Long idMatricula);

    public boolean validarEstudiante(Long idInscripcion);
}