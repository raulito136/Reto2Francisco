package org.example.retofrancisco2.copias;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD sobre la entidad Copia.
 */
public class CopiaRepository implements Repository<Copia> {

    /**
     * Factoría de sesiones de Hibernate usada para interactuar con la base de datos.
     */
    SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el repositorio
     *
     * @param sessionFactory la factoría de sesiones de Hibernate.
     */
    public CopiaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**

     * Persiste una copia en la base de datos.
     *
     * @param entity la copia a guardar.
     * @return la copia guardada.
     */
    @Override
    public Copia save(Copia entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    /**

     * Elimina una copia de la base de datos.
     *
     * @param entity la copia a eliminar.
     * @return copia eliminada.
     */
    @Override
    public Optional<Copia> delete(Copia entity) {
        sessionFactory.inTransaction(session -> session.remove(entity));
        return Optional.of(entity);
    }

    /**

     * No implementado actualmente.
     *
     * @param id id de la copia a eliminar.
     * @return un {@link Optional} vacío.
     */
    @Override
    public Optional<Copia> deleteById(Long id) {
        return Optional.empty();
    }

    /**

     * No implementado actualmente.
     *
     * @param id id de la copia a buscar.
     * @return un {@link Optional} vacío.
     */
    @Override
    public Optional<Copia> findById(Long id) {
        return Optional.empty();
    }

    /**

     * Devuelve todas las copias de la base de datos.
     *
     * @return lista de todas las copias, o lista vacía en caso de error.
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**

     * Devuelve el número de copias en la base de datos.
     * Actualmente no implementado y retorna 0.
     *
     * @return 0L.
     */
    @Override
    public Long count() {
        return 0L;
    }

    /**

     * Busca todas las copias asociadas a un usuario específico.
     *
     * @param usuarioId id del usuario.
     * @return lista de copias del usuario.
     */
    public List<Copia> findByUsuarioId(Long usuarioId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Copia> query = session.createQuery("FROM Copia c WHERE c.usuario.id = :usuarioId", Copia.class);
            query.setParameter("usuarioId", usuarioId);
            return query.list();
        }
    }

    /**

     * Edita una copia existente en la base de datos.
     *
     * @param copia la copia con los cambios.
     * @return la copia editada.
     */
    public Optional<Copia> edit(Copia copia) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Copia copiaEditada = session.merge(copia);
            session.getTransaction().commit();
            return Optional.ofNullable(copiaEditada);
        }
    }

}
