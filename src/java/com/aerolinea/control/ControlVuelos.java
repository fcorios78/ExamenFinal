
package com.aerolinea.control;

import com.aerolinea.dao.AeropuertoDaoImpl;
import com.aerolinea.dao.AvionDaoImpl;
import com.aerolinea.dao.VueloDaoImpl;
import com.aerolinea.entidad.Avion;
import com.aerolinea.entidad.Aeropuerto;
import com.aerolinea.entidad.Vuelo;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import javax.faces.application.FacesMessage;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;



@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST,proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ControlVuelos implements Serializable {
    private Vuelo vuelo;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private Avion avion;
    private List<Vuelo> vuelos;
    private List<Avion> aviones;
    private List<Aeropuerto> origenes;
    private List<Aeropuerto> destinos;
    @Autowired
    private VueloDaoImpl vueloDaoImpl;
    @Autowired
    private AeropuertoDaoImpl aeropuertoDaoImpl;
    @Autowired
    private AvionDaoImpl avionDaoImpl;
    
    //busqueda
    private Date fecha1;
    private Date fecha2;
    private Integer idorigen;
    private Integer iddestino;
    
    @PostConstruct
    public void init(){
        try{
        vuelo=vueloDaoImpl.create();
        origen = aeropuertoDaoImpl.create();
        destino = aeropuertoDaoImpl.create();
        avion = avionDaoImpl.create();
        }catch(Exception e){}
    }
    
    public ControlVuelos() {
    }
  
    public void onRowSelect(SelectEvent event){
        vuelo.setAvion(avion);        
        FacesContext.getCurrentInstance().addMessage(null, 
        (new FacesMessage("Avion seleccionado", 
                avion.getDescripcion())));
    }
    public String preparaNuevo(){
        try{
        vuelo=vueloDaoImpl.create();
        origen = aeropuertoDaoImpl.create();   
        destino = aeropuertoDaoImpl.create();
        avion = avionDaoImpl.create();
        }catch(Exception e){}
        return "VueloNuevo.xhtml?faces-redirect=true";
    }
    
    public String guardarVuelo() throws Exception{
        vuelo.setAeropuertoByIddestino(destino);
        vuelo.setAeropuertoByIdorigen(origen);        
        vuelo.setAvion(avion);
        try{
            vueloDaoImpl.saveOrUpdate(vuelo);
            FacesContext.getCurrentInstance().addMessage(null,(new FacesMessage("Vuelo Agregado Exitosamente","")));
        }catch(Exception excepcion){
            
        }        
        return "Vuelos.xhtml?faces-redirect=true";
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN'})")
    @PostFilter("hasRole('ROLE_ADMIN')||"+"filterObject.idusuario==principal.username")    
    public List<Vuelo> getVuelos() throws Exception {
        String o,d;
        idorigen=idorigen==null?0:idorigen;
        iddestino=iddestino==null?0:iddestino;
        vuelos = vueloDaoImpl.listarVuelos(fecha1, fecha2, idorigen, iddestino);
        return vuelos;
    }
    
    @PreAuthorize("hasAnyRole({'ROLE_OTRO','ROLE_ADMIN'})")
   // @PreFilter("hasRole('ROLE_ADMIN')||"+"targetObject.idusuario==principal.username")
    public void eliminar (Vuelo v) throws Exception{        
         //vueloDaoImpl.delete(u.getIdvuelo());        
       
        try{
            vueloDaoImpl.delete(v.getIdvuelo());         
            FacesContext.getCurrentInstance().addMessage(null,(new FacesMessage("Vuelo Eliminado Exitosamente","")));
        }catch(Exception excepcion){
            
        }        
        
    }    
    
    @PreAuthorize("hasAnyRole({'ROLE_OTRO','ROLE_ADMIN'})")
   // @PreFilter("hasRole('ROLE_ADMIN')||"+"targetObject.idusuario==principal.username")
    public void reservar (Vuelo u) throws Exception{        
         vueloDaoImpl.delete(u.getIdvuelo());        
    }      

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public List<Avion> getAviones() throws Exception {
        aviones = avionDaoImpl.findAll();
        return aviones;
    }

    public void setAviones(List<Avion> aviones) {
        this.aviones = aviones;
    }

    public List<Aeropuerto> getOrigenes() throws Exception {
        origenes = aeropuertoDaoImpl.findAll();
        return origenes;
    }

    public void setOrigenes(List<Aeropuerto> origenes) {
        this.origenes = origenes;
    }

    public List<Aeropuerto> getDestinos() throws Exception {
        destinos = aeropuertoDaoImpl.findAll();
        return destinos;
    }

    public void setDestinos(List<Aeropuerto> destinos) {
        this.destinos = destinos;
    }

    public VueloDaoImpl getVueloDaoImpl() {
        return vueloDaoImpl;
    }

    public void setVueloDaoImpl(VueloDaoImpl vueloDaoImpl) {
        this.vueloDaoImpl = vueloDaoImpl;
    }

    public AeropuertoDaoImpl getAeropuertoDaoImpl() {
        return aeropuertoDaoImpl;
    }

    public void setAeropuertoDaoImpl(AeropuertoDaoImpl aeropuertoDaoImpl) {
        this.aeropuertoDaoImpl = aeropuertoDaoImpl;
    }

    public AvionDaoImpl getAvionDaoImpl() {
        return avionDaoImpl;
    }

    public void setAvionDaoImpl(AvionDaoImpl avionDaoImpl) {
        this.avionDaoImpl = avionDaoImpl;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public Integer getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(Integer idorigen) {
        this.idorigen = idorigen;
    }

    public Integer getIddestino() {
        return iddestino;
    }

    public void setIddestino(Integer iddestino) {
        this.iddestino = iddestino;
    }


    
}

