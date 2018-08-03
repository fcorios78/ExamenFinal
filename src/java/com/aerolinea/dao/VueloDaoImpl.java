package com.aerolinea.dao;

import com.aerolinea.entidad.Vuelo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("VueloDaoImpl")
public class VueloDaoImpl extends GenericDaoImpl<Vuelo, Integer> implements VueloDao, Serializable{
    
    public List<Vuelo> listarVuelos(Date fecha1, Date fecha2, Integer idorigen, 
            Integer iddestino) throws Exception {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            String hql = " select v from Vuelo v join fetch v.aeropuertoByIdorigen "
                    + " join fetch v.aeropuertoByIddestino join fetch v.avion "
+" where v.idvuelo>0 ";
            System.out.println(hql);
            if (fecha1!=null && fecha2 !=null)
                hql+=" and v.fecha between :f1 and :f2";
                
            if (iddestino!=0 && idorigen!=0)
                hql+=" and v.aeropuertoByIdorigen.idaeropuerto = :idorigen and v.aeropuertoByIddestino.idaeropuerto = :iddestino";
            
            Query query = session.createQuery(hql);
            if (fecha1!=null && fecha2 !=null){
                query.setParameter("f1", fecha1);
                query.setParameter("f2", fecha2);
            }
            if (iddestino!=0 && idorigen!=0){
                query.setParameter("idorigen", idorigen);
                query.setParameter("iddestino", iddestino);
            }
            List<Vuelo> entities = query.list();
            session.getTransaction().commit();
            return entities;
        } catch (Exception ex) {
            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            } catch (Exception exc) {
            }
            throw new RuntimeException(ex);
        }finally{session.close();}
    }  
}
