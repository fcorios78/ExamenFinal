package com.aerolinea.control;

import com.aerolinea.dao.ReservacionDaoImpl;
import com.aerolinea.dao.UsuarioDaoImpl;
import com.aerolinea.dao.VueloDaoImpl;
import com.aerolinea.entidad.Reservacion;
import com.aerolinea.entidad.Usuario;
import com.aerolinea.entidad.Vuelo;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ControlReservacion implements Serializable {

    //
    private Reservacion reservacion;
    private Usuario usuario;
    private Vuelo vuelo;

    //LIST
    private List<Reservacion> reservaciones;
    private List<Usuario> usuarios;
    private List<Vuelo> vuelos;

    //AUTOWIRED
    @Autowired
    private ReservacionDaoImpl reservacionDaoImpl;
    @Autowired
    private UsuarioDaoImpl usuarioDaoImpl;
    @Autowired
    private VueloDaoImpl vueloDaoImpl;

    //GET AND SET 1
    public ReservacionDaoImpl getReservacionDaoImp() {
        return reservacionDaoImpl;
    }

    public void setReservacionDaoImpl(ReservacionDaoImpl reservacionDaoImpl) {
        this.reservacionDaoImpl = reservacionDaoImpl;
    }

    public UsuarioDaoImpl getUsuarioDaoImpl() {
        return usuarioDaoImpl;
    }

    public void setUsuarioDaoImpl(UsuarioDaoImpl usuarioDaoImpl) {
        this.usuarioDaoImpl = usuarioDaoImpl;
    }

    public VueloDaoImpl getVueloDaoImpl() {
        return vueloDaoImpl;
    }

    public void setVueloDaoImpl(VueloDaoImpl vueloDaoImpl) {
        this.vueloDaoImpl = vueloDaoImpl;
    }
    
    //GET AND SET 2    
    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }    
    //GET AND SET 3
    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }
    //@PreAutorize("")
    public List<Reservacion> getReservaciones()throws Exception{
        reservaciones=reservacionDaoImpl.findAll();
        return reservaciones;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    //
    @PostConstruct
    public void init() {
        try {
            reservacion = reservacionDaoImpl.create();
            usuario = usuarioDaoImpl.create();
            vuelo = vueloDaoImpl.create();
        } catch (Exception e) {

        }
    }

    public String guardar() throws Exception {
        reservacion.setUsuario(usuario);
        reservacion.setVuelo(vuelo);
        reservacionDaoImpl.saveOrUpdate(reservacion);
        return "/index.xhtml?faces-redirect=true";
    }

    //@PreAuthorize("(hasRole('ROLE_ADMIN')and #u.idusuario.length()<==4")
    public String selecionaEdit(Reservacion r) {
        reservacion = r;
        return "ReservacionForm.xhtml?faces-redirect=treue";
    }

    @PreAuthorize("hasAnyRole({'ROLE_OTRO','ROLE_ADMIN'})")    
    public void eliminar(Reservacion u) throws Exception{
        reservacionDaoImpl.delete(u.getIdreservacion());
    }
    
}
