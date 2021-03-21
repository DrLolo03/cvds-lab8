package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import org.mybatis.guice.transactional.Transactional;
import java.sql.SQLException;
import java.util.List;

public class MyBATISItemDAO  implements ItemDAO{

    @Inject
    private ItemMapper itemMapper;

    @Transactional
    @Override
    public void save(Item it) throws PersistenceException{
        try{
            itemMapper.insertarItem(it);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("No se puede registrar item "+it.toString(),e);
        }

    }

    @Override
    public Item load(int id) throws PersistenceException {
        try{
            return itemMapper.consultarItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("no se puede consultar item "+id,e);
        }
    }

    @Override
    public List<Item> load() throws PersistenceException {
        try{
            return itemMapper.consultarItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("error consultando items",e);
        }
    }

    @Transactional
    @Override
    public void actualizarTarifa(int id, long tarifa) throws PersistenceException {
        try {
            itemMapper.actualizarTarifa(id,tarifa);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("error actualizar tarifa" + id,e);
        }
    }

}