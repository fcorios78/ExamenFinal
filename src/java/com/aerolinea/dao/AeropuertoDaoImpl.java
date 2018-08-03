package com.aerolinea.dao;

import com.aerolinea.entidad.Aeropuerto;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component("AeropuertoDaoImpl")
public class AeropuertoDaoImpl extends GenericDaoImpl<Aeropuerto, Integer> 
        implements AeropuertoDao, Serializable{
    
}

