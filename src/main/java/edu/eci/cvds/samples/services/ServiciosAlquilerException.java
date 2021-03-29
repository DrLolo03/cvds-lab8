package edu.eci.cvds.samples.services;

public class ServiciosAlquilerException extends Exception{
    public static final  String NO_ID_ITEM = "No Existe un item con ID relacionado";
    public static final  String NO_ID_CLIENTE = "No Existe un cliente con ID relacionado";
    public static final  String NO_ID_TIPOITEM = "No Existe un tipo item con ID relacionado";
    public static final  String NO__ALQUILERITEM = "No esta en alquiler el item con ID relacionado: ";
    public static final  String FECHA_LIMITE_INVALIDA = "Error al consultar los items rentados ";
    public static final  String FECHAS_INVALIDAS = "Los dias deben ser mayor o igual a 0";
    public static final  String TARIFA_INVALIDA = "La tarifa no puede ser menor a 0";
    public ServiciosAlquilerException(String message){
        super(message);
    }

    public ServiciosAlquilerException(String message, Exception e){
        super(message,e);
    }
}