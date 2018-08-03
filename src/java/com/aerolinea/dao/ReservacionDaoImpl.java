/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.dao;

import com.aerolinea.entidad.Reservacion;
import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 *
 * @author fcori
 */
@Component("ReservacionDaoImpl")
public class ReservacionDaoImpl extends GenericDaoImpl<Reservacion, Integer>  implements ReservacionDao, Serializable{
    
}
