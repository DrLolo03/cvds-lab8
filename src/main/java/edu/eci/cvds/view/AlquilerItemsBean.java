package edu.eci.cvds.view;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


/**
 * @author Iván Camilo Rincón Saavedra
 * @author Leonardo Galeano
 * @version 10/5/2020
 */
@ManagedBean(name = "alquiler")
@ApplicationScoped
public class AlquilerItemsBean extends BasePageBean {
    private long id=0;
    private long costo = 0;
    private List<Cliente>clientes;
    private List<ItemRentado> itemsNoDevueltos;
    private Cliente selectedCliente;
    private java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    public void consultarClientes(){
        try {
            clientes = (id == 0)?serviciosAlquiler.consultarClientes():Arrays.asList(serviciosAlquiler.consultarCliente(id));
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            reset();
        }
    }

    public void registrarCliente(String nombre, long documento, String telefono, String direccion, String email ){
        try {
            serviciosAlquiler.registrarCliente( new Cliente(nombre,documento,telefono,direccion,email) );
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
        }
    }

    public void registrarItemRentado(int idItem,int numdias){
        try {
            Item item = serviciosAlquiler.consultarItem(idItem);
            serviciosAlquiler.registrarAlquilerCliente(date,selectedCliente.getDocumento(),item,numdias);
        }
        catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
        }

    }

    public void consultarCosto( int idItem,int numDias){
        try {
            costo = serviciosAlquiler.consultarCostoAlquiler(idItem,numDias);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            costo=0;
        }
    }

    public void consultarItemsNoRentados(){
        try {
            itemsNoDevueltos =  serviciosAlquiler.consultarItemsCliente( selectedCliente.getDocumento());
        }
        catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            id=0;
        }
    }
    /*
    public long consultarMulta(int idItem ){
        try {
            return serviciosAlquiler.consultarMultaAlquiler(idItem,date);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            return 0;
        }

    }
    */
    public void resetItems(){itemsNoDevueltos=null;}
    public void reset(){
        id=0;
        costo=0;
        clientes = null;
    }
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public List<ItemRentado> getItemsNoDevueltos() {
        return itemsNoDevueltos;
    }

    public void setItemsNoDevueltos(List<ItemRentado> itemsNoDevueltos) {
        this.itemsNoDevueltos = itemsNoDevueltos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCosto() {
        return costo;
    }

    public void setCosto(long costo) {
        this.costo = costo;
    }
}
