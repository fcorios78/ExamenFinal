package com.aerolinea.dao;

import com.aerolinea.entidad.Rol;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component("RolDaoImpl")
public class RolDaoImpl extends GenericDaoImpl<Rol, Integer> 
        implements RolDao, Serializable{
}
