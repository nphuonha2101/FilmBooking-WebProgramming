package com.filmbooking.dao;

import com.filmbooking.model.Room;
import com.filmbooking.hibernate.HibernateSessionProvider;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomDAOImpl implements IDAO<Room> {
    private static RoomDAOImpl instance = null;
    private Session session;

    private RoomDAOImpl() {
    }

    public static RoomDAOImpl getInstance() {
        if (instance == null) {
            instance = new RoomDAOImpl();
        }
        return instance;
    }

    @Override
    public void setSessionProvider(HibernateSessionProvider sessionProvider) {
        this.session = sessionProvider.getSession();
    }

    @Override
    public List<Room> getAll() {
        List<Room> result;

        CriteriaBuilder criteriaBuilder = this.session.getCriteriaBuilder();
        // declare an object that want to query
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> rootEntry = criteriaQuery.from(Room.class);
        CriteriaQuery<Room> all = criteriaQuery.select(rootEntry);

        TypedQuery<Room> allQuery = this.session.createQuery(all);

        result = allQuery.getResultList();

        return result;
    }
    @Override
    public Room getByID(String id) {
        long lID = Long.parseLong(id);
        Room result = null;

        try {
            CriteriaBuilder criteriaBuilder = this.session.getCriteriaBuilder();
            CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            Root<Room> rootEntry = criteriaQuery.from(Room.class);
            criteriaQuery.select(rootEntry).where(criteriaBuilder.equal(rootEntry.get("id"), lID));

            TypedQuery<Room> typedQuery = this.session.createQuery(criteriaQuery);

            result = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace(System.out);
        }

        return result;
    }
    @Override
    public void save(Room room) {
        Transaction transaction = session.beginTransaction();
        session.persist(room);
        transaction.commit();
    }

    @Override
    public void update(Room room) {
        Transaction transaction = session.beginTransaction();
        session.merge(room);
        transaction.commit();
    }

    @Override
    public void delete(Room room) {
        Transaction transaction = session.beginTransaction();
        session.remove(room);
        transaction.commit();
    }
}
