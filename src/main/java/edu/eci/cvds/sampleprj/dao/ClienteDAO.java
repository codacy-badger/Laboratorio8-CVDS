package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;

import java.util.Date;
import java.util.List;

public interface ClienteDAO {

    public List<Cliente> consultarClientes() throws PersistenceException;

    public Cliente consultarCliente(long id) throws PersistenceException;

    public void agregarItemRentadoACliente(int id, int idit, Date fechainicio, Date fechafin)  throws PersistenceException ;
}