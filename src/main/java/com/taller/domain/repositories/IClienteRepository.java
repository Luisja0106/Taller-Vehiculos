package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Cliente;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Email;

/**
 * Contrato de persistencia para la entidad {@link Cliente}.
 *
 * <p>
 * Define las operaciones que el dominio necesita sobre clientes
 * sin conocer el mecanismo de almacenamiento subyacente. Puede ser
 * implementado con JPA, MongoDB, archivos JSON o cualquier otra
 * tecnología sin afectar al dominio.
 * </p>
 *
 * @see Cliente
 */
public interface IClienteRepository {
  /**
   * Persiste un cliente nuevo en el almacenamiento.
   *
   * @param cliente el cliente a guardar, no puede ser nulo
   * @return {@link Result} con el cliente guardado si la operación
   *         fue exitosa, o error si falla la persistencia
   */
  Result<Cliente, IErrorApp> guardar(Cliente Cliente);

  /**
   * Actualiza los datos de un cliente existente.
   *
   * @param cliente el cliente con los datos actualizados
   * @return {@link Result} con el cliente actualizado si la operación
   *         fue exitosa, o error si el cliente no existe o falla la persistencia
   */
  Result<Cliente, IErrorApp> actualizar(Cliente cliente);

  /**
   * Busca un cliente por su identificador único.
   *
   * @param id identificador del cliente, ej: "CLI001"
   * @return {@link Optional} con el cliente si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<Cliente> buscarPorId(String id);

  /**
   * Busca un cliente por su email.
   *
   * <p>
   * Se usa principalmente para verificar duplicados antes
   * de registrar un cliente nuevo.
   * </p>
   *
   * @param email email del cliente a buscar
   * @return {@link Optional} con el cliente si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<Cliente> buscarPorEmail(Email Email);

  /**
   * Elimina un cliente por su identificador único.
   *
   * @param id identificador del cliente a eliminar
   */
  List<Cliente> listarTodos();

  /**
   * Elimina un cliente por su identificador único.
   *
   * @param id identificador del cliente a eliminar
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
   *
   */
  int siguienteNumeroId();
}
