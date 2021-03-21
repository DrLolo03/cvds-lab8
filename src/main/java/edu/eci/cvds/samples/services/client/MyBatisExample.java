/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;



import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;


/**
 *
 * @author hcadavid
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws ParseException, SQLException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        /** 
        //Crear el mapper y usarlo: 
        ClienteMapper cm=sqlss.getMapper(ClienteMapper.class);
        //cm...
        //consultar clientes
        System.out.println("consultar clientes");
        System.out.println(cm.consultarClientes());
        //agregar item rentado a cliente
        System.out.println("agregando item rentado a cliente");
        cm.agregarItemRentadoACliente(321817,4,Date.valueOf("2021-03-14"),Date.valueOf("2021-03-28"));
        //consultar un  cliente
        System.out.println("consultar un solo cliente ");
        System.out.println(cm.consultarCliente(321817));

        System.out.println("items");
        ItemMapper itm = sqlss.getMapper(ItemMapper.class);
        System.out.println("Consultar items");
        System.out.println(itm.consultarItems());
        System.out.println("Buscando el item de id 1022367709,");
        System.out.println(itm.consultarItem(1022367709));
        System.out.println("añadir item id 0987");
        
        //Item(TipoItem tipo, int id, String nombre, String descripcion, Date fechaLanzamiento, long tarifaxDia, String formatoRenta, String genero)
        
         //al momenot de susstentar hay que cambiar el numero 9877778 porque va a aparecer como llave duplicada
        itm.insertarItem( new Item( new TipoItem(2,"Accion"),9877778,"Como dijo Vladimir, wa mirmir","descripcion",Date.valueOf("2021-03-14"),2313,"renta","accion" ));
        
        System.out.println("Consultando item");
        System.out.println(itm.consultarItem(9877778));
        **/
        ///////mappers ahora
        //***************************************************************************** Probando MapperCLiente
        ClienteMapper cm =sqlss.getMapper(ClienteMapper.class);
        System.out.println("---------------------");
        System.out.println(cm.consultarClientes());
        System.out.println("---------------------");
        System.out.println(cm.consultarCliente(321817).getNombre());
        //cm.agregarItemRentadoACliente(-700,4,Date.valueOf("2021-09-25"),Date.valueOf("2021-09-26"));
        //cm.agregarCliente( new Cliente("NicolasT",3146879,"8527415963", "direccion","email2",true, new ArrayList<ItemRentado>()  ));
        System.out.println("---------------------");
        //System.out.println(cm.consultarCliente(3146879));
         System.out.println("----------------------");
         System.out.println(cm.consultarClientesVetados());
         System.out.println("----------------------");

        // Item Rentado
        ItemRentadoMapper irm = sqlss.getMapper(ItemRentadoMapper.class);
        System.out.println("--------------------- consultarItemsRentados-");
        
        System.out.println(irm.consultarItemsRentados(1478521)); // items rentados por el cliente 1478521
        //System.out.println(irm.consultarItemsRentados(1478521)); // items rentados por el cliente 1478521
        System.out.println("----------------------");
        System.out.println(irm.consultarItemRentados());
        System.out.println("----------------------");

        //  ItemMapper
        ItemMapper im = sqlss.getMapper(ItemMapper.class);
        System.out.println("----------------------");
        System.out.println( im.consultarItems());
        System.out.println("----------------------");
        im.insertarItem( new Item( new TipoItem(2,"Accion"),9877778,"Como dijo Vladimir, wa mirmir","descripcion",Date.valueOf("2021-03-14"),2313,"renta","accion" ));
        System.out.println("Consultando item");
        System.out.println(im.consultarItem(9877778));
        

        // Probando Tipo Item
        System.out.println("----------------------");
        TipoItemMapper tim = sqlss.getMapper(TipoItemMapper.class);
        System.out.println(tim.getTiposItems());
        //tim.agregarTipoItem(new TipoItem(5,"pretselz"));
        System.out.println("----------------------");
        System.out.println( tim.getTipoItem(1));
        
        sqlss.commit();
        
        
        sqlss.close();

        
        
    }


}
