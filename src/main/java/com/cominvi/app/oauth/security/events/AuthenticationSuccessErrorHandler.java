package com.cominvi.app.oauth.security.events;

import com.cominvi.app.commons.general.Usuarios;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cominvi.app.commons.capitalhumano.Empleados;
import com.cominvi.app.commons.util.UtilService;
import com.cominvi.app.oauth.services.IUsuarioService;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        // UserDetails user = (UserDetails) authentication.getPrincipal();
        UtilService utilService = new UtilService();
        String username = authentication.getName();
        if( !username.contains("|UserExterno|") ) { // Empleado
        	if (utilService.asignarToken(authentication.getName()) != null) {
                return;
            }

            Empleados empleado = usuarioService.finByNomina(authentication.getName());
            Usuarios usuario = usuarioService.findByIdEmpleado(empleado.getIdempleado());

            if (usuario != null && usuario.getIntentos() > 0) {
                usuario.setIntentos((byte) 0);
                usuario.setBloqueado((byte) 0);
                usuario.setFechahorabloqueo(null);
                usuarioService.update(usuario, usuario.getIdempleado());
            }
        }
        

    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

        try {
        	UtilService utilService = new UtilService();
        	String username = authentication.getName();
        	if( !username.contains("|UserExterno|") ) { // Empleado
        	
        		if (utilService.asignarToken(authentication.getName()) != null) {
                    return;
                }

                
                Empleados empleado = usuarioService.finByNomina(authentication.getName());
                Usuarios usuario = usuarioService.findByIdEmpleado(empleado.getIdempleado());
                
                short incrementos = usuario.getIntentos();
                int total = incrementos + 1;
                usuario.setIntentos((short) total);

                // Deshabilitamos el usuario
                if (usuario.getIntentos() > 2 && usuario.getBloqueado() == 0) {
                    log.error("Usuario : " + usuario.getIdempleado() + " deshabilitado por máximos de intentos");
                    usuario.setBloqueado((byte)1);
                    usuario.setFechahorabloqueo(new Date());
                }

                usuarioService.update(usuario, usuario.getIdempleado());
        		
        	}
        	

        } catch (DataAccessException e) {
            log.error("Ocurrio un error al acceder a base de datos : " + e.getMostSpecificCause().toString());
        }

    }

}
