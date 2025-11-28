package org.example.retofrancisco2.peliculas;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del patrón Repository para la entidad Pelicula
 * utilizando Hibernate para la persistencia de datos.
 *
 * Esta clase gestiona las operaciones CRUD (Crear, Leer, Actualizar, Borrar)
 * para objetos de tipo Pelicula.
 */
public class PeliculaRepository implements Repository<Pelicula> {
    /**
     * Factoría de sesiones de Hibernate inyectada en el constructor,
     * utilizada para abrir sesiones y realizar operaciones de persistencia.
     */
    SessionFactory sessionFactory;

    /**
     * Constructor para inicializar el repositorio con un SessionFactory de Hibernate.
     *
     * @param sessionFactory La factoría de sesiones utilizada para interactuar con la base de datos.
     */
    public PeliculaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Guarda una nueva entidad pelicula en la base de datos.
     *
     * Inicia una transacción, persiste la entidad y confirma la transacción.
     *
     * @param entity La entidad pelicula a guardar.
     * @return La misma entidad pelicula que ha sido guardada.
     */
    @Override
    public Pelicula save(Pelicula entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    /**
     * Elimina una entidad pelicula existente de la base de datos.
     *
     * Inicia una transacción, elimina la entidad y confirma la transacción.
     *
     * @param entity La entidad pelicula a eliminar.
     * @return Un Optional que contiene la entidad eliminada si la operación fue exitosa.
     */
    @Override
    public Optional<Pelicula> delete(Pelicula entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    /**
     * Busca y elimina una entidad pelicula de la base de datos por su identificador.
     *
     * @param id El identificador (ID) de tipo Long de la entidad a eliminar.
     * @return Un Optional que contiene la entidad pelicula que ha sido eliminada.
     * Devuelve un **Optional** vacío si no se encontró ninguna entidad con ese ID.
     */
    @Override
    public Optional<Pelicula> deleteById(Long id) {
        try(Session session=sessionFactory.openSession()) {
            session.beginTransaction();
            // Buscar la entidad antes de eliminarla.
            Pelicula pelicula = session.find(Pelicula.class, id);
            if (pelicula != null) {
                session.remove(pelicula);
                session.getTransaction().commit();
                return Optional.of(pelicula);
            }
            session.getTransaction().commit();
            return Optional.empty();
        }
    }

    /**
     * Busca una entidad pelicula por su id.
     *
     * @param id El identificador (ID) de tipo Long de la entidad a buscar.
     * @return Un Optional que contiene la entidad pelicula si se encuentra,
     * o un **Optional** vacío si no existe.
     */
    @Override
    public Optional<Pelicula> findById(Long id) {
        try(Session session=sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Pelicula.class,id));
        }
    }

    /**
     * Recupera todas las entidades pelicula almacenadas en la base de datos.
     *
     * Utiliza una consulta HQL simple ("from Pelicula").
     *
     * @return Una Lista de todas las peliculas
     * Devuelve una lista vacía si ocurre una excepción o si no hay resultados.
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula", Pelicula.class).list();
        } catch (Exception e) {
            // Manejo de la excepción, en este caso, se devuelve una lista vacía.
        }
        return List.of();
    }

    /**
     * Cuenta el número total de peliculas en la base de datos.
     *
     * @return El número total de Peliculas
     */
    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count(*) from Pelicula", Long.class).getSingleResult();
        }
    }
}