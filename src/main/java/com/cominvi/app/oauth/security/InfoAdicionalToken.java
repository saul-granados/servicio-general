package com.cominvi.app.oauth.security;

import com.cominvi.app.commons.general.Usuarios;
import com.cominvi.app.commons.general.Usuariosexternos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.cominvi.app.commons.capitalhumano.Empleados;
import com.cominvi.app.oauth.clients.IPlazasFeignClient;
import com.cominvi.app.oauth.services.IUsuarioService;
import com.cominvi.app.oauth.services.IUsuariosexternosService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private IUsuariosexternosService usuarioextService;
    
    @Autowired
    private IPlazasFeignClient plazaFeignClient;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Map<String, Object> info = new HashMap<String, Object>();
        
        String username = authentication.getName();
        
        if(  !username.contains("|UserExterno|")  ) {
        	Empleados empleado = usuarioService.finByNomina(authentication.getName());
            Usuarios usuario = usuarioService.findByIdEmpleado(empleado.getIdempleado());
            long idcentrotrabajo = plazaFeignClient.findCentrotrabajoByIdempleado(empleado.getIdempleado());
            
            info.put("id", usuario.getIdempleado());
            info.put("nombre", empleado.getNombre());
            info.put("apepaterno", empleado.getApellidopaterno());
            info.put("apematerno", empleado.getApellidomaterno());
            info.put("numeronomina", empleado.getNumeronomina());
            info.put("idcentrotrabajo", idcentrotrabajo);
            info.put("isEmpleado", true);
            info.put("isReestablecer", false);
        } else {
        	
        	Usuariosexternos usuario = usuarioextService.findByCorreoAndCelular(username.replace("|UserExterno|", ""));
        	
        	info.put("id", usuario.getIdusuarioexterno());
            info.put("nombre", usuario.getNombre());
            info.put("apepaterno", usuario.getApepaterno());
            info.put("apematerno", usuario.getApematerno());
            info.put("numeronomina", "");
            info.put("idcentrotrabajo", 0);
            info.put("isEmpleado", false);
            
            if( usuario.getReestablecerpassword() == 0 ) {
            	info.put("isReestablecer", false);
            } else {
            	info.put("isReestablecer", true);
            }
        }
        
        System.out.println(accessToken);
        
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
