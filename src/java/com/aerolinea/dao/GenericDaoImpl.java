package com.aerolinea.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
  
  
    @Autowired
    protected SessionFactory sessionFactory;
  
    private final static Logger LOGGER = Logger.getLogger(GenericDaoImpl.class.getName());

    public GenericDaoImpl() {
    }
 
    @Override
    public T create() throws Exception {
        try {
            return getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
 
    @Override
    public void saveOrUpdate(T entity) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } 
        catch (Exception ex) {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } catch (Exception exc) {
                LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
            }
            throw new Exception(ex);
        }finally{session.close();}
    }
 
    @Override
    public T get(ID id) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            T entity = (T) session.get(getEntityClass(), id);
            session.getTransaction().commit();

            return entity;
       } catch (Exception ex) {
           try {
               if (session.getTransaction().isActive()) {
                   session.getTransaction().rollback();
               }
           } catch (Exception exc) {
               LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
           }
           throw new Exception(ex);
       }finally{session.close();}
    }

    @Override
    public void delete(ID id) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            T entity = (T) session.get(getEntityClass(), id);
            if (entity == null) {
                throw new Exception();
           }
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            try {
               if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
               }
            } catch (Exception exc) {
                LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
            }
            throw ex;

        } finally{session.close();}
    }

    @Override
    public List<T> findAll() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT e FROM " + getEntityClass().getName() + " e");
            List<T> entities = query.list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception ex) {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } catch (Exception exc) {
                LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
            }
            throw new RuntimeException(ex);
        }finally{session.close();}
    }

    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
 }
