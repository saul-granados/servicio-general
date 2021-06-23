package com.cominvi.app.oauth.services;

import java.util.List;
import java.util.Map;

import com.cominvi.app.commons.general.Usuarios;
import com.cominvi.app.commons.capitalhumano.Empleados;

public interface IUsuarioService {

    List<Map<String, Object>> cargaUsuarios();

    Map<String, Object> cargaParametrosEmaill();

    Usuarios findByIdEmpleado(Long idempleado);

    Usuarios update(Usuarios usuario, Long idempleado);

    Usuarios updatepass(Usuarios usuario, Long idempleado, boolean changepass);

    Usuarios guardar(Usuarios usuario);

    Empleados finByNomina(String numeronomina);
    
    boolean existeusuario(String numeronomina);
    
    Empleados finByIdempleado(long idempleado);

    Usuarios validaUsuario(String usuario, String password);

    List<Map<String, Object>> getlistadoperfilusuario(Long idsistema, Long idempresa, Long idempleado);

    List<Map<String, Object>> getlistadoperfilsistema(Long idsistema, Long idempresa, Long idempleado);

    boolean removeperfilusuario(long idempleado, long idperfil);

    boolean addperfilusuario(long idempleado, long idperfil);
    
    String test();
    
    

}//-->
