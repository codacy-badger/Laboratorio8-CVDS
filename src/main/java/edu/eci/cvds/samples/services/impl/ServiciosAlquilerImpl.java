package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.*;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import org.mybatis.guice.transactional.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private ItemRentadoDAO itemRentadoDAO;
    @Inject
    private TipoItemDAO tipoitemDAO;

    @Override
    public void registrarTipoItem(TipoItem tipoitem) throws ExcepcionServiciosAlquiler {
        try{
            tipoitemDAO.save(tipoitem);
        } catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("error al registrar tipo item", e);
        }
    }

    @Override
    public long valorMultaRetrasoxDia(int itemId) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.load(itemId).getTarifaxDia();
        } catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("error al consultar multa retraso x dia", e);
        }
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.consultarCliente(docu);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("error al consultar al cliente", e);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.consultarItemsCliente(idcliente);
        } catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar items del cliente", e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return clienteDAO.consultarClientes();
        } catch (PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar clientes", e);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
        try{
            return itemDAO.consultarItemsDisponibles();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar items disponibles" , ex);
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        List<Cliente> clientes = consultarClientes();
        for (int i=0 ; i<clientes.size() ; i++) {
            ArrayList<ItemRentado> rentados = clientes.get(i).getRentados();
            for (int j=0 ; j<rentados.size() ; j++) {
                if (rentados.get(j).getItem().getId() == iditem) {
                    LocalDate fechafinrenta = rentados.get(j).getFechafinrenta().toLocalDate();
                    LocalDate fechadevolucion = fechaDevolucion.toLocalDate();
                    long diasRetraso = ChronoUnit.DAYS.between(fechafinrenta, fechadevolucion);
                    if (diasRetraso < 0) { return 0; }
                    return diasRetraso * valorMultaRetrasoxDia(rentados.get(j).getId());
                }
            }
        }
        throw  new ExcepcionServiciosAlquiler("El item"+iditem+"no se encuentra rentado");
    }
    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try{
            if(tipoitemDAO.load(id)==null){
                throw new ExcepcionServiciosAlquiler("el tipo item no existe");
            }
            return tipoitemDAO.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo de item" , ex);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try{
            return tipoitemDAO.loadTipoItems();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de items" , ex);
        }
    }
    @Transactional
    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try{
            if(clienteDAO.consultarCliente(docu)==null){
                throw new ExcepcionServiciosAlquiler("El cliente no existe") ;
            }
            int idItem = item.getId();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, numdias);
            clienteDAO.agregarItemRentadoACliente(docu,item.getId(),date,new java.sql.Date(calendar.getTime().getTime()));
        }  catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al agregar item rentado al cliente" , ex);
        }
    }
    @Transactional
    @Override
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try{
            clienteDAO.registrarCliente(c);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al registrar cliente" , ex);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            Item item = itemDAO.load(iditem);
            if(item==null){
                throw new ExcepcionServiciosAlquiler("El Item no existe");
            }
            long tarifa =  item.getTarifaxDia();
            return numdias * tarifa;
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el el costo de alquiler.", ex);
        }
    }
    @Transactional
    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        try {
            if(itemDAO.load(id)==null){
                throw new ExcepcionServiciosAlquiler("No existe ese item.");
            }
            itemDAO.actualizarTarifaItem(id,tarifa);
        } catch (PersistenceException ex)  {
            throw new ExcepcionServiciosAlquiler("No se pudo actualizar tarifa .", ex);
        }

    }
    @Override
    @Transactional
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.save(i);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("No se pudo registrar item", ex);
        }
    }
    @Transactional
    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
            if(clienteDAO.consultarCliente(docu)==null){
                throw new ExcepcionServiciosAlquiler("El cliente no existe");
            }
            clienteDAO.vetarCliente(docu,estado);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("No se pudo vetar al cliente", ex);
        }

    }
}
