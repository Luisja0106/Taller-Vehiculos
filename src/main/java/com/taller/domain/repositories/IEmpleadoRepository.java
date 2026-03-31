package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Empleado;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;

/**
 * Contrato de persistencia para la entidad {@link Empleado}.
 *
 * <p>
 * Define las operaciones que el dominio necesita sobre empleados
 * sin conocer el mecanismo de almacenamiento subyacente. Puede ser
 * implementado con JPA, MongoDB, archivos JSON o cualquier otra
 * tecnología sin afectar al dominio.
 * </p>
 *
 * @see Empleado
 */
public interface IEmpleadoRepository {
  /**
   * Persiste un empleado nuevo en el almacenamiento.
   *
   * @param empleado el empleado a guardar, no puede ser nulo
   * @return {@link Result} con el empleado guardado si la operación
   *         fue exitosa, o error si falla la persistencia
   */
  Result<Empleado, IErrorApp> guardar(Empleado empleado);

  /**
   * Actualiza los datos de un empleado existente.
   *
   * @param empleado el empleado con los datos actualizados
   * @return {@link Result} con el empleado actualizado si la operación
   *         fue exitosa, o error si el empleado no existe o falla la persistencia
   */
  Result<Empleado, IErrorApp> actualizar(Empleado empleado);

  /**
   * Busca un empleado por su identificador único.
   *
   * @param id identificador del empleado, ej: "EMP001"
   * @return {@link Optional} con el empleado si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<Empleado> buscarPorId(String id);

  /**
   * Busca un cliente por su email.
   *
   * <p>
   * Se usa principalmente para verificar duplicados antes
   * de registrar un Empleado nuevo.
   * </p>
   *
   * @param email email del empleado a buscar
   * @return {@link Optional} con el empleado si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<Empleado> buscarPorEmail(String Email);

  /**
   * Retorna todos los empleados registrados.
   *
   * @return lista con todos los empleados, lista vacía si no hay ninguno,
   *         nunca retorna null
   */
  List<Empleado> listarTodos();

  /**
   * Elimina un empleado por su identificador único.
   *
   * @param id identificador del empleado a eliminar
   */
  void eliminar(String id);

  /**
   * Retorna el siguiente número disponible para generar un ID legible.
   *
   * <p>
   * La implementación debe garantizar atomicidad para evitar
   * duplicados en entornos concurrentes.
   * </p>
   *
   * @return número entero único y creciente
   */
  int siguienteNumeroId();

}
