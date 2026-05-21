package com.taller.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.taller.domain.entities.OrdenDeTrabajo;
import com.taller.domain.enums.EstadoDelTrabajo;
import com.taller.domain.interfaces.IErrorApp;
import com.taller.domain.utils.Result;
import com.taller.usecases.output.EntidadConteo;

/**
 * Contrato de persistencia para la entidad {@link OrdenDeTrabajo}.
 *
 * <p>
 * Define las operaciones que el dominio necesita sobre órdenes de trabajo
 * sin conocer el mecanismo de almacenamiento subyacente. Es el repositorio
 * más completo del sistema dado que {@link OrdenDeTrabajo} es la entidad
 * central del negocio.
 * </p>
 *
 * @see OrdenDeTrabajo
 * @see EstadoDelTrabajo
 */
public interface IOrdenRepository {
  /**
   * Persiste una orden de trabajo nueva en el almacenamiento.
   *
   * @param orden la orden a guardar, no puede ser nula
   * @return {@link Result} con la orden guardada si la operación
   *         fue exitosa, o error si falla la persistencia
   */
  Result<OrdenDeTrabajo, IErrorApp> guardar(OrdenDeTrabajo orden);

  /**
   * Actualiza los datos de una orden de trabajo existente.
   *
   * <p>
   * Se usa principalmente al avanzar el estado de la orden
   * o al agregar servicios.
   * </p>
   *
   * @param orden la orden con los datos actualizados
   * @return {@link Result} con la orden actualizada si la operación
   *         fue exitosa, o error si la orden no existe o falla la persistencia
   */
  Result<OrdenDeTrabajo, IErrorApp> actualizar(OrdenDeTrabajo orden);

  /**
   * Busca una orden de trabajo por su identificador único.
   *
   * @param id identificador de la orden, ej: "ORD20250319001"
   * @return {@link Optional} con la orden si existe,
   *         {@link Optional#empty()} si no se encuentra
   */
  Optional<OrdenDeTrabajo> buscarPorId(String id);

  /**
   * Retorna todas las órdenes de trabajo registradas.
   *
   * @return lista con todas las órdenes, lista vacía si no hay ninguna,
   *         nunca retorna null
   */
  List<OrdenDeTrabajo> listarTodos();

  List<OrdenDeTrabajo> obtenerOrdenesPorServicioId(String servicioId);

  List<OrdenDeTrabajo> listarConFiltros(EstadoDelTrabajo estado, String empleadoId, String placaVehiculo);

  /**
   * Elimina una orden de trabajo por su identificador único.
   *
   * @param id identificador de la orden a eliminar
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
  List<EntidadConteo> serviciosMasPedidos();

  List<EntidadConteo> mecanicoConMasServicio();

  List<EntidadConteo> vehiculosPorServicio();

  int siguienteNumeroParaId();
}
