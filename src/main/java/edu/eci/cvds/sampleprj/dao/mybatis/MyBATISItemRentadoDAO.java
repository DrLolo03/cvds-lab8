package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.ItemRentado;

import java.util.List;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO {
    @Inject
    private ItemRentadoMapper itemRentadoMapper;

    @Override
    public List<ItemRentado> load() throws PersistenceException {
        try{
            return itemRentadoMapper.consultarItemRentados();
        }
        catch (org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar items rentados" ,e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsRentados(long idCliente) throws PersistenceException {
        try{
            return itemRentadoMapper.consultarItemsRentados(idCliente);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar los items rentados del cliente id:" + idCliente, e);
        }
    }

    @Override
    public ItemRentado consultarItemRentado(int idItem) throws PersistenceException {
        try{
            return itemRentadoMapper.consultarItemRentado( idItem );
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar el item rentado con id:"+idItem, e);
        }

    }
}