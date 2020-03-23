package edu.eci.cvds.view;



import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "AlquilerItem")
@SessionScoped
public class AlquilerItemBeans extends BasePageBean {
    @Inject
    private ServiciosAlquiler serviciosAlquiler;
    private Cliente selectedCliente;

    public void registrarCliente(String nombre,long doc,String telefono, String direccion,String email) throws ExcepcionServiciosAlquiler {
        try{
            serviciosAlquiler.registrarCliente(new Cliente(nombre,doc,telefono,direccion,email));
        } catch (ExcepcionServiciosAlquiler ex) {
            throw new ExcepcionServiciosAlquiler("Error al registrar el Cliente");
        }
    }

    public Cliente getselectedCliente() {
        return selectedCliente;
    }

    public void setselectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try{
            return serviciosAlquiler.consultarClientes();
        } catch (ExcepcionServiciosAlquiler excepcionServiciosAlquiler) {
            throw new ExcepcionServiciosAlquiler("Error al consultar clientes");
        }
    }
}
