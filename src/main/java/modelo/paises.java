/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;



import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 *
 * @author AllanRamiro
 */
public class paises implements Serializable {

    private static final long serialVersionUID = 1L;
    private int paisid;
    private String nomPais;
    private int idContinente;
    private String fecIndependencia;
    private String sn_monarquia;

    public paises()
    {
    }
    
        public paises(int paisid, String nomPais, Integer idContinente, String fecIndependencia, String sn_monarquia) 
        {
       this.paisid = paisid;
       this.nomPais = nomPais;
       this.idContinente = idContinente;
       this.fecIndependencia = fecIndependencia;
       this.sn_monarquia = sn_monarquia;
    }
   
    
    
    public int getPaisid() {
        return paisid;
    }

    public void setPaisid(int paisid) {
        this.paisid = paisid;
    }

    public String getNomPais() {
        return nomPais;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }

    public String getFecIndependencia() {
        return fecIndependencia;
    }

    public void setFecIndependencia(String fecIndependencia) {
        this.fecIndependencia = fecIndependencia;
    }
  

    public String getSn_monarquia() {
        return sn_monarquia;
    }

    public void setSn_monarquia(String sn_monarquia) {
        this.sn_monarquia = sn_monarquia;
    }

    
  
}