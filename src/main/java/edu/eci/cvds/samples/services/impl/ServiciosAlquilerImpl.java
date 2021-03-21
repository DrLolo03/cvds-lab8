package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.*;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import org.mybatis.guice.transactional.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {
    @Inject
    private ItemDAO itemDAO;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private ItemRentadoDAO itemRentadoDAO;
    @Inject
    private TipoItemDAO tipoItemDAO;
    private static final int MULTA_DIARIA=5000;

    @Override
    public int valorMultaRetrasoxDia(int itemId) {
        //Esta implementacion se baso de la implementacion de valorMultaRetrasoxDia que esta en ServiciosAlquilerItemsStub
        //consultarItem( itemId ).getTarifaxDia();
        return MULTA_DIARIA;
    }

    @Override
    public Item consultarItem(int id) throws ServiciosAlquilerException {
        try {
            Optional<Item> optionalItem= Optional.ofNullable(itemDAO.load(id) );
            optionalItem.orElseThrow(() -> new ServiciosAlquilerException(ServiciosAlquilerException.NO_ID_ITEM));
            return optionalItem.get();
        } catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al consultar el item con id:"+id,persistenceException);
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws ServiciosAlquilerException {
        try{
            Optional<Cliente> optionalCliente = Optional.ofNullable(clienteDAO.load(docu) );
            optionalCliente.orElseThrow(() -> new ServiciosAlquilerException(ServiciosAlquilerException.NO_ID_CLIENTE));
            return optionalCliente.get();
        }
        catch(PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al consultar el cliente con id: " + docu,persistenceException);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ServiciosAlquilerException {
        try{
            consultarCliente( idcliente);
            return itemRentadoDAO.consultarItemsRentados(idcliente);
        }
        catch (PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al consultar los items rentados del cliente con id : " + idcliente ,persistenceException);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ServiciosAlquilerException {
        try{
            return clienteDAO.consultarClientes();
        }
        catch (PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al consultar clientes.",persistenceException);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ServiciosAlquilerException{
        try{
            return itemDAO.load();
        }
        catch (PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al consultar Items disponibles.",persistenceException);
        }
    }

    @Override
    public long consultarMultaAlquiler(int idItem, Date fechaDevolucion) throws ServiciosAlquilerException {
        //Esta implementacion se baso de la implementacion de consultarMultaAlquiler que esta en ServiciosAlquilerItemsStub
        try {
            consultarItem( idItem );// valida que exista el item
            Optional<ItemRentado> optionalItemRentado = Optional.ofNullable( itemRentadoDAO.consultarItemRentado( idItem) );
            optionalItemRentado.orElseThrow(() -> new ServiciosAlquilerException(ServiciosAlquilerException.NO__ALQUILERITEM + idItem));

            LocalDate fechaInicioRenta = optionalItemRentado.get().getFechainiciorenta().toLocalDate();
            LocalDate fechaMinimaEntrega=optionalItemRentado.get().getFechafinrenta().toLocalDate();
            LocalDate fechaEntrega=fechaDevolucion.toLocalDate();
            if(fechaEntrega.isBefore( fechaInicioRenta ) ){
                throw new ServiciosAlquilerException( ServiciosAlquilerException.FECHA_LIMITE_INVALIDA );
            }
            else if(fechaEntrega.isBefore( fechaMinimaEntrega )  ){
                return 0;
            }
            long diasRetraso = ChronoUnit.DAYS.between(fechaMinimaEntrega, fechaEntrega);
            return diasRetraso * MULTA_DIARIA;


        } catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al consultar multa de alquiler del Item  id: " + idItem, persistenceException);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ServiciosAlquilerException {
        try {
            Optional<TipoItem> optionalTipoItem = Optional.ofNullable( tipoItemDAO.load(id) );
            optionalTipoItem.orElseThrow(() -> new ServiciosAlquilerException(ServiciosAlquilerException.NO_ID_TIPOITEM));
            return optionalTipoItem.get();
        } catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al consultar Tipo Item con id " + id, persistenceException);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ServiciosAlquilerException {
        try {
            return tipoItemDAO.loadTiposItems();
        } catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al consultar Tipo Items.", persistenceException);
        }
    }

    @Transactional
    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ServiciosAlquilerException {
        try {
            Cliente cliente = consultarCliente( docu );
            consultarItem( item.getId());
            if( numdias < 0 ){
                throw new ServiciosAlquilerException( ServiciosAlquilerException.FECHAS_INVALIDAS);
            }
            clienteDAO.agregarItemRentado(docu,item.getId(),date, Date.valueOf(date.toLocalDate().plusDays(numdias)));
        } catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al registrar Alquiler a cliente.", persistenceException);
        }
    }

    @Transactional
    @Override
    public void registrarCliente(Cliente c) throws ServiciosAlquilerException {
        try {
            clienteDAO.save(c);
        }
        catch (PersistenceException persistenceException) {
            throw new ServiciosAlquilerException("Error al agregar cliente.", persistenceException);
        }
    }

    @Override
    public long consultarCostoAlquiler(int idItem, int numdias) throws ServiciosAlquilerException {
        Item item = consultarItem( idItem );
        if( numdias < 0){
            throw new ServiciosAlquilerException( ServiciosAlquilerException.FECHAS_INVALIDAS);
        }
        return item.getTarifaxDia() * numdias;
    }

    @Transactional
    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ServiciosAlquilerException {
        try{
            consultarItem( id );
            if( tarifa < 0 ){
                throw new ServiciosAlquilerException(ServiciosAlquilerException.TARIFA_INVALIDA);
            }
            itemDAO.actualizarTarifa(id,tarifa);
        }
        catch(PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al cambiar tarifa del item con id: " + id,persistenceException);
        }
    }

    @Transactional
    @Override
    public void registrarItem(Item i) throws ServiciosAlquilerException {
       try{
           itemDAO.save(i);
       }
       catch (PersistenceException persistenceException){
           throw new ServiciosAlquilerException("Error al registrar item.",persistenceException);
       }
    }

    @Transactional
    @Override
    public void vetarCliente(long docu, boolean estado) throws ServiciosAlquilerException {
        try{
            consultarCliente( docu );
            clienteDAO.vetar(docu,estado);
        }
        catch(PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al vetar al cliente con id: " + docu ,persistenceException);
        }
    }
    @Override
    public ItemRentado consultarItemsRentados(int idItem) throws ServiciosAlquilerException{
        try{
            consultarItem( idItem ); // validar si existe el item
            Optional<ItemRentado> optionalItemRentado = Optional.ofNullable( itemRentadoDAO.consultarItemRentado( idItem ) );
            optionalItemRentado.orElseThrow(() -> new ServiciosAlquilerException( ServiciosAlquilerException.NO__ALQUILERITEM+idItem));
            return optionalItemRentado.get();
        }
        catch (PersistenceException persistenceException){
            throw new ServiciosAlquilerException("Error al consultar los items rentados ");
        }
    }

} 