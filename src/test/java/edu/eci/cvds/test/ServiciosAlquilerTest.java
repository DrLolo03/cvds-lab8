package edu.eci.cvds.test;

import java.sql.Date;
import java.util.Calendar;
import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquilerException;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;
    
    @Inject
    private ServiciosAlquiler serviciosAlquiler;

    private long idCliente;
    private long tarifa;
    private int idItem;
    private int idTipoItem;
    private int numDias;
    private boolean vetado;


    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }


    @Test
    public void emptyDB() {    
        boolean r = false;
        try {
            Cliente cliente = serviciosAlquiler.consultarCliente(1010);
            r=true;
        } catch(ServiciosAlquilerException e) {
            r = false;
        } catch(IndexOutOfBoundsException e) {
            r = false;
        }
        // Validate no Client was found;
        Assert.assertFalse(r);
    }
    


    @Test
    public void consultandoUnCliente() throws ServiciosAlquilerException {
        try {
            Cliente cliente = serviciosAlquiler.consultarCliente(321817);
            Assert.assertEquals("Rigoberto Uran",cliente.getNombre());
        } catch(ServiciosAlquilerException e) {
            throw new ServiciosAlquilerException("Error Prueba Consultar Cliente.",e);
        }
    }

    @Test
    public void itemNoFound(){
        boolean found = false;
        try{
            Item item = serviciosAlquiler.consultarItem(1022367709);
            found=true;
        }
        catch (ServiciosAlquilerException e){
            found = false;
        }
        catch (IndexOutOfBoundsException e){
            found = false;
        }
        finally{
            Assert.assertTrue(found);
        }
    }

    @Test
    public void insertarItemRentado() throws ServiciosAlquilerException {
        boolean insert = true;
        try{
            Item item = serviciosAlquiler.consultarItem(102);
            serviciosAlquiler.registrarAlquilerCliente(Date.valueOf("2019-02-01"),999,item,5);
        }
        catch(ServiciosAlquilerException e){
            insert = false;
        }
        finally{
            Assert.assertTrue(insert);
        }
    }

    @Test
    /*
    error(?)
    */
    public void consultarMultaAlquilerValido() throws ServiciosAlquilerException {
        System.out.println("1");
        int diasRetraso = 5;
        idItem = 4;
        Date fechaFinRentaItem = serviciosAlquiler.consultarItemsRentados(idItem).getFechafinrenta();
        System.out.println(fechaFinRentaItem);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( fechaFinRentaItem );
        calendar.add( Calendar.DAY_OF_YEAR,diasRetraso);
        java.sql.Date entrega = new java.sql.Date(calendar.getTime().getTime());

        Assert.assertEquals( serviciosAlquiler.consultarMultaAlquiler(idItem,entrega ),serviciosAlquiler.valorMultaRetrasoxDia(idItem) * diasRetraso );
    }

    @Test
    /*
    error(?)
    */
    public void consultarMultaAlquilerValidoIgualaCero() throws ServiciosAlquilerException {
        int diasRetraso = 0;
        idItem = 4;
        Date fechaFinRentaItem = serviciosAlquiler.consultarItemsRentados(idItem).getFechafinrenta();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime( fechaFinRentaItem );
        calendar.add( Calendar.DAY_OF_YEAR,diasRetraso);

        java.sql.Date entrega = new java.sql.Date(calendar.getTime().getTime());
        Assert.assertEquals( serviciosAlquiler.consultarMultaAlquiler(idItem,entrega ),0 );
    }

    @Test
    public void consultarMultaAlquilerValidoExcepcionItem() {
        int diasRetraso = 0;
        idItem = -100;
        try {
            Date fechaFinRentaItem = serviciosAlquiler.consultarItemsRentados(idItem).getFechafinrenta();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( fechaFinRentaItem );
            calendar.add( Calendar.DAY_OF_YEAR,diasRetraso);

            java.sql.Date entrega = new java.sql.Date(calendar.getTime().getTime());
            serviciosAlquiler.consultarMultaAlquiler(idItem,entrega );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals( excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.NO_ID_ITEM);
        }
    }
    @Test
    /*
    comparacion erronea
    */
    public void consultarMultaAlquilerValidoExcepcionItemNoRentado() {
        
        int diasRetraso = 0;
        idItem = 1001169369;
        try {
            Date fechaFinRentaItem = serviciosAlquiler.consultarItemsRentados(idItem).getFechafinrenta();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( fechaFinRentaItem );
            calendar.add( Calendar.DAY_OF_YEAR,diasRetraso);

            java.sql.Date entrega = new java.sql.Date(calendar.getTime().getTime());
            serviciosAlquiler.consultarMultaAlquiler(idItem,entrega );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals( excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.NO__ALQUILERITEM+idItem);
        }
        

    }

    @Test
    /*
    comparacion erronea
    */
    public void consultarMultaAlquilerValidoExcepcionFechaIncorrecta() {
        int diasRetraso = -1000;
        idItem = 4;
        try {
            Date fechaFinRentaItem = serviciosAlquiler.consultarItemsRentados(idItem).getFechafinrenta();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( fechaFinRentaItem );
            calendar.add( Calendar.DAY_OF_YEAR,diasRetraso);

            java.sql.Date entrega = new java.sql.Date(calendar.getTime().getTime());
            serviciosAlquiler.consultarMultaAlquiler(idItem,entrega );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals( excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.FECHA_LIMITE_INVALIDA);
        }
    }


    @Test
    public void consultarCostoAlquilerValido() throws ServiciosAlquilerException{
        long answer = -1;
        idItem = 2;
        numDias = 20;
        answer = serviciosAlquiler.consultarCostoAlquiler(idItem, numDias );
        Assert.assertEquals( answer ,serviciosAlquiler.consultarItem(idItem).getTarifaxDia() * numDias );
    }

    @Test
    public void consultarCostoAlquilExcepcionDia(){
        long answer = -1;
        idItem = 2;
        numDias = -20;
        try {
            answer = serviciosAlquiler.consultarCostoAlquiler(idItem, numDias );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertTrue(answer== -1);
            Assert.assertEquals(ServiciosAlquilerException.FECHAS_INVALIDAS,excepcionServiciosAlquiler.getMessage());
        }
    }

    @Test
    public void consultarCostoAlquilExcepcionItem(){
        long answer = -1;
        idItem = -2;
        numDias = 20;
        try {
            answer = serviciosAlquiler.consultarCostoAlquiler(idItem, numDias );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertTrue(answer== -1);
            Assert.assertEquals(ServiciosAlquilerException.NO_ID_ITEM,excepcionServiciosAlquiler.getMessage());
        }
    }


    @Test
    /*
    error
    */
    public void actualizarTarifaItemValido() throws ServiciosAlquilerException {
        tarifa = 16;
        idItem = 1;

        long currentTarifa;
        serviciosAlquiler.actualizarTarifaItem( idItem, tarifa);
        currentTarifa = serviciosAlquiler.consultarItem(idItem).getTarifaxDia();
        Assert.assertEquals( tarifa,currentTarifa );
    }

    @Test
    public void actualizarTarifaItemExcepcionItem(){
        tarifa = 30000;
        idItem = -1100;
        try {
            serviciosAlquiler.actualizarTarifaItem( idItem, tarifa);
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals( excepcionServiciosAlquiler.getMessage(),ServiciosAlquilerException.NO_ID_ITEM);
        }


    }
    @Test
    public void actualizarTarifaItemExcepcionTarifa(){
        tarifa = -151515;
        idItem = 2;
        try {
            serviciosAlquiler.actualizarTarifaItem( idItem, tarifa);
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals( excepcionServiciosAlquiler.getMessage(),ServiciosAlquilerException.TARIFA_INVALIDA);
        }
    }



    @Test
    public void vetarClienteValido(){
        Cliente cliente = null;
        vetado = true;
        idCliente = 3146879;
        try {
            serviciosAlquiler.vetarCliente(idCliente, vetado );
            cliente = serviciosAlquiler.consultarCliente(idCliente);
            Assert.assertEquals(cliente.isVetado(),vetado );
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
        }
    }


    @Test
    public void vetarClienteExcepcionCliente(){
        vetado = true;
        idCliente = 3146879;
        String error = "Error al vetar al cliente con id: "+idCliente;
        try {
            serviciosAlquiler.vetarCliente(idCliente, vetado );
            Assert.assertFalse(true);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals(error,excepcionServiciosAlquiler.getMessage());
        }
    }

    @Test
    public void registrarAlquilerClienteValido() throws ServiciosAlquilerException{
        Date fechaInicioRenta;
        Date fechaFinRenta;
        int size;
        numDias = 5;

        java.sql.Date fechaInicio = java.sql.Date.valueOf("2021-02-07");
        Item item = serviciosAlquiler.consultarItem(1);

        serviciosAlquiler.registrarAlquilerCliente(fechaInicio,1,item,numDias);
        Cliente cliente = serviciosAlquiler.consultarCliente(1);
        size = cliente.getRentados().size() -1 ;

        fechaInicioRenta = cliente.getRentados().get( size ).getFechainiciorenta();
        fechaFinRenta = cliente.getRentados().get( size ).getFechafinrenta();
        java.sql.Date fechaFin = java.sql.Date.valueOf("2021-02-12");

        Assert.assertEquals( fechaInicioRenta, fechaInicio);
        Assert.assertEquals( fechaFinRenta, fechaFin);
    }
    @Test
    public void registrarAlquilerClienteExcepcionCliente() {
        numDias = 5;
        java.sql.Date fechaInicio = java.sql.Date.valueOf("2021-02-07");
        try {
            Item item = serviciosAlquiler.consultarItem(1);
            serviciosAlquiler.registrarAlquilerCliente(fechaInicio,-1564898189,item,numDias);
            Assert.assertTrue( false);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals(excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.NO_ID_CLIENTE);
        }
    }
    @Test
    public void registrarAlquilerClienteExcepcionItem() {
        numDias = 5;
        java.sql.Date fechaInicio = java.sql.Date.valueOf("2021-02-07");
        try {
            Item item = new Item();
            item.setId(-10000);
            serviciosAlquiler.registrarAlquilerCliente(fechaInicio,1,item,numDias);
            Assert.assertTrue( false);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals(excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.NO_ID_ITEM);
        }
    }
    @Test
    public void registrarAlquilerClienteExcepcionDias() {
        numDias = -1;
        java.sql.Date fechaInicio = java.sql.Date.valueOf("2021-02-07");
        try {
            Item item = serviciosAlquiler.consultarItem(1);
            serviciosAlquiler.registrarAlquilerCliente(fechaInicio,1,item,numDias);
            Assert.assertTrue( false);
        } catch (ServiciosAlquilerException excepcionServiciosAlquiler) {
            Assert.assertEquals(excepcionServiciosAlquiler.getMessage(), ServiciosAlquilerException.FECHAS_INVALIDAS);
        }
    }
}