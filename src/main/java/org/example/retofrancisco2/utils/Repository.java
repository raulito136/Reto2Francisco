package org.example.retofrancisco2.utils;

import java.util.List;
import java.util.Optional;

/**

 * Interfaz genérica para operaciones CRUD sobre cualquier entidad.
 *
 * @param <T> el tipo de entidad que maneja el repositorio.
 */
public interface Repository<T> {

    /**

     * Guarda una entidad en el repositorio.
     *
     * @param entity la entidad a guardar.
     * @return la entidad guardada.
     */
    T save(T entity);

    /**

     * Elimina una entidad del repositorio.
     *
     * @param entity la entidad a eliminar.
     * @return un Optional con la entidad eliminada, o vacío si no existe.
     */
    Optional<T> delete(T entity);

    /**

     * Elimina una entidad por su ID.
     *
     * @param id el ID de la entidad a eliminar.
     * @return un Optional con la entidad eliminada, o vacío si no existe.
     */
    Optional<T> deleteById(Long id);

    /**

     * Busca una entidad por su ID.
     *
     * @param id el ID de la entidad.
     * @return un Optional con la entidad encontrada, o vacío si no existe.
     */
    Optional<T> findById(Long id);

    /**

     * Devuelve todas las entidades del repositorio.
     *
     * @return lista de todas las entidades.
     */
    List<T> findAll();

    /**

     * Devuelve el número total de entidades en el repositorio.
     *
     * @return número de entidades.
     */
    Long count();
}
