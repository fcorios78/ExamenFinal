package com.aerolinea.dao;

import com.aerolinea.entidad.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("UsuarioDaoImpl")
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, String> 
        implements UsuarioDao, Serializable{
    @Override
    public List<Usuario> findAll() throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("select u from Usuario u "
                    + " join fetch u.pais"
                    + " join fetch u.rol");
            List<Usuario> entities = query.list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception ex) {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } catch (Exception exc) {
//                LOGGER.log(Level.WARNING,"Falló al hacer un rollback", exc);
            }
            throw new RuntimeException(ex);
        }finally{session.close();}
    }  
}
