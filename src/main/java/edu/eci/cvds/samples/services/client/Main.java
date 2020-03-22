package edu.eci.cvds.samples.services.client;

import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String args[]) throws SQLException, ExcepcionServiciosAlquiler, ParseException {
        Date fecha =new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-03");
        ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting().registrarItem(new Item(new TipoItem(1, "Videojuego" ),1444,
                "item99", "item99", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/28"),
                99,"Digital","99"));
        System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting().consultarItem(1444).getNombre());
    }
}
