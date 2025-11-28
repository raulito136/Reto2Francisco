package org.example.retofrancisco2.usuarios;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**

 * Repositorio para manejar operaciones CRUD sobre la entidad Usuario.
 * Implementa la interfaz Repository utilizando Hibernate para la
 * persistencia en base de datos.
 */
public class UsuarioRepository implements Repository<Usuario> {

    private SessionFactory sessionFactory;

    /**

     * Constructor que inicializa el repositorio con un sessionFactory.
     *
     * @param sessionFactory factoría de sesiones de Hibernate.
     */
    public UsuarioRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**

     * Guarda un usuario en la base de datos.
     *
     * @param entity el usuario a guardar.
     * @return el usuario guardado (actualmente no implementado, retorna null).
     */
    @Override
    public Usuario save(Usuario entity) {
        return null;
    }

    /**

     * Elimina un usuario de la base de datos.
     *
     * @param entity el usuario a eliminar.
     * @return un Optional con el usuario eliminado (vacío actualmente).
     */
    @Override
    public Optional<Usuario> delete(Usuario entity) {
        return Optional.empty();
    }

    /**

     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return un Optional con el usuario eliminado (vacío actualmente).
     */
    @Override
    public Optional<Usuario> deleteById(Long id) {
        return Optional.empty();
    }

    /**

     * Busca un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return un Optional con el usuario encontrado (vacío actualmente).
     */
    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.empty();
    }

    /**

     * Devuelve todos los usuarios de la base de datos.
     *
     * @return lista de usuarios (vacía actualmente).
     */
    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    /**

     * Devuelve el número total de usuarios en la base de datos.
     *
     * @return número de usuarios (0 actualmente).
     */
    @Override
    public Long count() {
        return 0L;
    }

    /**

     * Busca un usuario por su nombre de usuario.
     *
     * @param nombre_usuario el nombre de usuario a buscar.
     * @return un Optional con el usuario si existe, o vacío si no.
     */
    public Optional<Usuario> findByNombreUsuario(String nombre_usuario) {
        try (Session session = sessionFactory.openSession()) {
            Query<Usuario> query = session.createQuery("from Usuario where nombre_usuario=:nombre_usuario", Usuario.class);
            query.setParameter("nombre_usuario", nombre_usuario);
            return Optional.ofNullable(query.uniqueResult());
        }
    }
}
