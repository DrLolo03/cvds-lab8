package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Item;
import java.util.List;

public interface ItemDAO {

    /**
     * 
     * @param it
     * @throws PersistenceException
     */
    public void save(Item it) throws PersistenceException;

    /**
     * 
     * @param id, 
     * @return
     * @throws PersistenceException
     */
    public Item load(int id) throws PersistenceException;

    /**
     * 
     * @return 
     * @throws PersistenceException
     */
    public List<Item> load()  throws PersistenceException;

    public void actualizarTarifa(int id, long tarifa) throws PersistenceException;
}