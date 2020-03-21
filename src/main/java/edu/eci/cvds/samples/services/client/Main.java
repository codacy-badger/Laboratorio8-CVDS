package edu.eci.cvds.samples.services.client;

import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException, ExcepcionServiciosAlquiler {
        System.out.println(ServiciosAlquilerFactory.getInstance().getServiciosAlquiler().consultarCliente(1000));
    }
}
