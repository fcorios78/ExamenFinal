package com.aerolinea.dao;

import com.aerolinea.entidad.Usuario;
import javax.inject.Named;

@Named
public interface UsuarioDao extends GenericDao<Usuario, String>{
    
}
