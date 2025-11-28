package org.example.retofrancisco2.copias;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CopiaRepository implements Repository<Copia> {
    SessionFactory sessionFactory;

    public CopiaRepository(SessionFactory session) {
        this.sessionFactory = session;
    }

    @Override
    public Copia save(Copia entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Optional<Copia> delete(Copia entity) {
        sessionFactory.inTransaction(session -> session.remove(entity));
        return Optional.of(entity);
    }

    @Override
    public Optional<Copia> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Copia> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Long count() {
        return 0L;
    }

    public List<Copia> findByUsuarioId(Long usuarioId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Copia> query = session.createQuery("FROM Copia c WHERE c.usuario.id = :usuarioId", Copia.class);
            query.setParameter("usuarioId", usuarioId);
            return query.list();
        }
    }

    public Optional<Copia> edit(Copia copia){
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            Copia copiaEditada = session.merge(copia);;
            session.getTransaction().commit();
            return Optional.ofNullable(copiaEditada);
        }
    }

}
