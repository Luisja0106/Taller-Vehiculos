package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.Vehiculo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.domain.valueobjects.Placa;

/**
 * Contrato de persistencia para la entidad {@link Vehiculo}.
 *
 * <p>
 * Define las operaciones que el dominio necesita sobre vehículos
 * sin conocer el mecanismo de almacenamiento subyacente. A diferencia
 * de otros repositorios, no tiene {@code siguienteNumeroId()} porque
 * el identificador natural del vehículo es su {@link Placa}, no un
 * número secuencial generado por el sistema.
 * </p>
 *
 * @see Vehiculo
 * @see Placa
 */
public interface IVehiculoRepository {

  /**
   * Persiste un vehículo nuevo en el almacenamiento.
   *
   * @param vehiculo el vehículo a guardar, no puede ser nulo
   * @return {@link Result} con el vehículo guardado si la operación
   *         fue exitosa, o error si falla la persistencia
   */
  Result<Vehiculo, IErrorApp> guardar(Vehiculo vehiculo);

  /**
   * Actualiza los datos de un vehículo existente.
   *
   * @param vehiculo el vehículo con los datos actualizados
   * @return {@link Result} con el vehículo actualizado si la operación
   *         fue exitosa, o error si el vehículo no existe o falla la persistencia
   */
  Result<Vehiculo, IErrorApp> actualizar(Vehiculo vehiculo);

  /**
   * Busca un vehículo por su placa.
   *
   * <p>
   * La placa es el identificador natural del vehículo en el negocio,
   * por lo que es el criterio principal de búsqueda.
   * </p>
   *
   * @param placa placa del vehículo como Value Object
   * @return {@link Optional} con el vehículo si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<Vehiculo> buscarPorPlaca(Placa placa);

  /**
   * Retorna todos los vehículos registrados.
   *
   * @return lista con todos los vehículos, lista vacía si no hay ninguno,
   *         nunca retorna null
   */
  List<Vehiculo> listarTodos();

  /**
   * Retorna todos los vehículos pertenecientes a un cliente específico.
   *
   * <p>
   * Útil para ver el historial de vehículos de un cliente
   * en la UI sin cargar el objeto {@link com.taller.domain.entities.Cliente}
   * completo.
   * </p>
   *
   * @param clienteId identificador del cliente, ej: "CLI001"
   * @return lista de vehículos del cliente, lista vacía si no tiene ninguno
   */
  List<Vehiculo> listarPorCliente(String clienteId);

  /**
   * Elimina un vehículo por su placa.
   *
   * @param placa placa del vehículo a eliminar como Value Object
   */
  void eliminar(Placa placa);
}
