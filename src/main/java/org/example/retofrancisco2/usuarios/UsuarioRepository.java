package org.example.retofrancisco2.usuarios;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements Repository<Usuario> {
    private SessionFactory sessionFactory;

    public UsuarioRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Usuario save(Usuario entity) {
        return null;
    }

    @Override
    public Optional<Usuario> delete(Usuario entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public Long count() {
        return 0L;
    }

    public Optional<Usuario> findByNombreUsuario(String nombre_usuario){
        try(Session session =sessionFactory.openSession() ){
            Query<Usuario> query=session.createQuery("from Usuario where nombre_usuario=:nombre_usuario");
            query.setParameter("nombre_usuario",nombre_usuario);
            return Optional.ofNullable(query.uniqueResult());
        }
    }
}
