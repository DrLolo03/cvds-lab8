package edu.eci.cvds.sampleprj.dao.mybatis;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import org.mybatis.guice.transactional.Transactional;
import javax.inject.Inject;
import java.util.List;

public class MyBATISTipoItemDAO implements TipoItemDAO {
    @Inject
    private TipoItemMapper tipoItemMapper;

    @Transactional
    @Override
    public void save(TipoItem ti) throws PersistenceException {
        try{
            tipoItemMapper.agregarTipoItem(ti);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al agregar el nuevo tipo de item.",e);
        }
    }

    @Override
    public TipoItem load(int id) throws PersistenceException {
        try{
            return tipoItemMapper.getTipoItem(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar el tipo con id: "+ id,e);
        }
    }

    @Override
    public List<TipoItem> loadTiposItems() throws PersistenceException {
        try{
            return tipoItemMapper.getTiposItems();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los tipos en la base de datos.",e);
        }
    }
}