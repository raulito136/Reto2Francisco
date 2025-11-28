package org.example.retofrancisco2.peliculas;

import org.example.retofrancisco2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PeliculaRepository implements Repository<Pelicula> {
    SessionFactory sessionFactory;

    public PeliculaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pelicula save(Pelicula entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Optional<Pelicula> delete(Pelicula entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Pelicula> deleteById(Long id) {
        try(Session session=sessionFactory.openSession()) {
            session.beginTransaction();
            Pelicula pelicula = session.find(Pelicula.class, id);
            session.remove(pelicula);
            session.getTransaction().commit();
            return Optional.ofNullable(pelicula);
        }
    }

    @Override
    public Optional<Pelicula> findById(Long id) {
        try(Session session=sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Pelicula.class,id));
        }
    }

    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula", Pelicula.class).list();
        } catch (Exception e) {
        }
        return List.of();
    }

    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count(*) from Pelicula", Long.class).getSingleResult();
        }
    }
}
