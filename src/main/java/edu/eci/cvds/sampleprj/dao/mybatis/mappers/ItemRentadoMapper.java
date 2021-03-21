package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemRentadoMapper {
    /**
     * Metodo indica items rentaods por clientes en especifico
    **/
    public List<ItemRentado> consultarItemsRentados(@Param("idcli") long id);


    /**
     * Metodo consulta los items rentados en general
    
     */
    public List<ItemRentado> consultarItemRentados();

    /**
     * Metodo que consulta la informacion de un item que ha sido rentadp
     * @throws PersistenceException, si existe algun error de persitencia al buscar el item rentado
     */
    public ItemRentado consultarItemRentado( @Param("idItem") int idItem );


}