package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;

/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli") long id);
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("idcli") long id, 
           @Param("idit")  int idit, 
           @Param("fechainicio")  Date fechainicio,
           @Param("fechafin")  Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();

    /**
     * Metodo agrega cliente
     * @param cli
     */
    public void agregarCliente(@Param("cli") Cliente cli);

    /**
     * Metodo indica clientes vetados
     * @return List<Cliente>
     */
    public List<Cliente> consultarClientesVetados();

    public void vetar(long docu, boolean estado);
    
}
