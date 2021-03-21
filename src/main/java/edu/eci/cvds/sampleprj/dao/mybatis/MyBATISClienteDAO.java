package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import org.mybatis.guice.transactional.Transactional;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO {
    @Inject
    private ClienteMapper ClienteMapper;

    @Override
    public Cliente load(long id) throws PersistenceException {
        try{
            return ClienteMapper.consultarCliente(id);
        }
        catch (org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error consultado cliente id " + id,e);
        }
    }

    @Transactional
    @Override
    public void save(Cliente cli) throws PersistenceException {
        try{
            ClienteMapper.agregarCliente(cli);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al agregar cliente.",e);
        }
    }

    @Transactional
    @Override
    public void agregarItemRentado(long docu, int id, Date fechainicio, Date fechafin) throws PersistenceException {
        try{
            ClienteMapper.agregarItemRentadoACliente(docu,id,fechainicio,fechafin);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al agregando item rentado " + id + " al cliente " + docu +".",e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws PersistenceException {
        try{
            return ClienteMapper.consultarClientes();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error consultando clientes.",e);
        }
    }

    @Transactional
    @Override
    public void vetar(long docu, boolean estado) throws PersistenceException {
        try{
            ClienteMapper.vetar(docu,estado);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error  vetando clientes.",e);
        }
    }
}