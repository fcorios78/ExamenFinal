package com.aerolinea.dao;

import com.aerolinea.entidad.Avion;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component("AvionDaoImpl")
public class AvionDaoImpl extends GenericDaoImpl<Avion, Integer> 
        implements AvionDao, Serializable{
    
    
}

