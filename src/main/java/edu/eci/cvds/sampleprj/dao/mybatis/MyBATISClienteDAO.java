package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import org.mybatis.guice.transactional.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDAO {

    @Inject
    private ClienteMapper clienteMapper;

    @Override
    public List<Cliente> consultarClientes() throws PersistenceException{
        try{
            return clienteMapper.consultarClientes();
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar clientes",e);
        }


    }

    @Override
    public Cliente consultarCliente(long id) throws PersistenceException {
        try{
            return clienteMapper.consultarCliente(id);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar cliente con documento"+id,e);
        }
    }
    @Transactional
    @Override
    public void agregarItemRentadoACliente(long id, int idit, Date fechainicio, Date fechafin) throws PersistenceException {
        try{
            clienteMapper.agregarItemRentadoACliente(id,idit,fechainicio,fechafin);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al agregar item rentado al cliente con id del item : "+id+" y id del tipo del item:"+idit,e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws PersistenceException {
        try{
            return clienteMapper.consultarItems(idcliente);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al consultar los items del client" ,e);
        }
    }

    @Transactional
    @Override
    public void registrarCliente(Cliente c) throws PersistenceException {
        try{
            clienteMapper.registrarCliente(c);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al registrar al cliente" ,e);
        }
    }

    @Transactional
    @Override
    public void vetarCliente(long docu, boolean estado) throws PersistenceException {
        try{
            clienteMapper.vetarCliente(docu,estado);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al vetar cliente" ,e);
        }
    }
}