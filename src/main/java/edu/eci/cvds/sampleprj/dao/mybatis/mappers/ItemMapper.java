package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {
    
    
    public List<Item> consultarItems();        
    
    public Item consultarItem(@Param("idItem") int id);
    
    public void insertarItem(@Param("item") Item it);

     public void actualizarTarifa(@Param("iditem") int id, @Param("tarifa") long tarifa);

        
}
