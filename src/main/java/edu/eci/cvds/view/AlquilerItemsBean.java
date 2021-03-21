package edu.eci.cvds.view;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.Arrays;

@ManagedBean(name = "alquiler")
@ApplicationScoped
public class AlquilerItemsBean extends BasePageBean {
    
    private List<Cliente>clientes;
    private List<ItemRentado> itemsNoDevueltos;
    private Cliente selectedCliente;
    private long id=0;
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
            excepcionServiciosAlquiler.printStackTrace();
        }
    }

    public void consultarItemsNoRentados(){
        try {
            itemsNoDevueltos =  serviciosAlquiler.consultarCliente( selectedCliente.getDocumento() ).getRentados();
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            excepcionServiciosAlquiler.printStackTrace();
        }

    }

    public void reset(){
        clientes = null;
    }
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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

    public void resetItems(){
        itemsNoDevueltos=null;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }
}