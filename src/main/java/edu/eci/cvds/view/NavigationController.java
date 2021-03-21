package edu.eci.cvds.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {
    public String paginaCliente() {
        return "registrocliente";
    }

    public String paginaItem() {
        return "registroalquiler";
    }
}