/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.paises;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import org.apache.http.entity.StringEntity;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Allan Ramiro Flores Murillo
 */
@Named(value = "paisBean")
@RequestScoped
public class paisBean {

    private paises tpaises;
    private List<paises> tb = null;
    private String titulo = "Catalogo de Paises";
    private String msg = "";
    private String url = "http://localhost:8088/cines/api/servicio.paises/";

    FacesMessage mensaje = new FacesMessage();
    DefaultHttpClient httpClient = new DefaultHttpClient();

    ObjectMapper convertir = new ObjectMapper();

    //Implementando estandar defacto log4j 
    final static Logger log = Logger.getLogger(paisBean.class);
    
    public paisBean() throws IOException {
       
        BasicConfigurator.configure();
        
        this.msg = "";

        this.tpaises = new paises();

        ListarTodo();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public List<paises> getTb() {
        return tb;
    }

    public void setTb(List<paises> tb) {
        this.tb = tb;
    }

    public paises getTpaises() {
        return tpaises;
    }

    public void setTpaises(paises tpaises) {
        this.tpaises = tpaises;
    }

    public void instanciar() {
        this.tpaises = new paises();
    }

    // Metodo para listar todos las datos de la tabla de paises via Jax-RS  
    public void ListarTodo() throws IOException {
        HttpGet getRequest = new HttpGet(this.url);
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = this.httpClient.execute(getRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Fallo : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();

        //Convierte el resultado del json recibio de objeto de tipo ArrayList
        this.tb = mapper.readValue(result, new TypeReference<ArrayList<paises>>() {
        });

    }

    public void cmd_crear_registro() throws UnsupportedEncodingException, JsonProcessingException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        this.convertir = new ObjectMapper();

        HttpPost httpPost = new HttpPost(this.url);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        //Convierte el objeto de tipo Entity Class a un formato de tipo json
        String inputJson = this.convertir.writeValueAsString(this.tpaises);

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPost.setEntity(stringEntity);
     
        this.msg = "Enviado la peticion " + httpPost.getRequestLine();
        log.info(this.msg);
     
        HttpResponse response = httpclient.execute(httpPost);
     
        if (response.getStatusLine().getStatusCode() == 204) {
            this.msg = "Registro guardado";
            this.mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, this.msg, null);
            FacesContext.getCurrentInstance().addMessage(this.msg, this.mensaje);
        } else {

            this.msg = "Solicitud rechazada: " + response.getStatusLine().getStatusCode();
            log.info(this.msg);
            throw new RuntimeException(this.msg);
        }

        httpclient.close();
        ListarTodo();

    }

    // Ejecuta la instrucciones para actualizar el registro de los datos 
    public void cmd_actualizar_registro() throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPut httpPut = new HttpPut(this.url + this.tpaises.getPaisid());
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        String inputJson = this.convertir.writeValueAsString(this.tpaises);

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPut.setEntity(stringEntity);

        this.msg = "Enviado la peticion " + httpPut.getRequestLine();
        log.info(this.msg);
     
        
        HttpResponse response = httpclient.execute(httpPut);

        if (response.getStatusLine().getStatusCode() == 204) {
            this.msg = "Registro editado";
            this.mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, this.msg, null);
            FacesContext.getCurrentInstance().addMessage(this.msg, this.mensaje);

        } else {
            this.msg = "Solicitud rechazada: " + response.getStatusLine().getStatusCode();
             log.info(this.msg);
             throw new RuntimeException(this.msg);
        }

        httpclient.close();
        ListarTodo();

    }

    public void cmd_borrar_registro() throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpDelete httpborrar = new HttpDelete(this.url + this.tpaises.getPaisid());
        httpborrar.setHeader("Accept", "application/json");

        this.msg = "Enviado la peticion " + httpborrar.getRequestLine();
        log.info(this.msg);
          
        HttpResponse response = httpclient.execute(httpborrar);

        if (response.getStatusLine().getStatusCode() == 204) {
            this.msg = "Registro borrado";

            this.mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, this.msg, null);
            FacesContext.getCurrentInstance().addMessage(this.msg, this.mensaje);

        } else {
            this.msg = "Solicitud rechazada: " + response.getStatusLine().getStatusCode();
            log.info(this.msg);
            throw new RuntimeException(this.msg);
        }

        httpclient.close();
        ListarTodo();

    }

    
}
