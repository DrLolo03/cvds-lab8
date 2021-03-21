package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import java.util.List;
import java.sql.Date;

public interface ClienteDAO {

    public Cliente load(long id) throws PersistenceException;

    public void save(Cliente cli) throws PersistenceException;

    public void agregarItemRentado(long docu, int id, Date fechainicio, Date fechafin) throws PersistenceException;

    public List<Cliente> consultarClientes() throws PersistenceException;

    public void vetar(long docu, boolean estado) throws PersistenceException;

}