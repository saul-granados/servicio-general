package com.cominvi.app.oauth.services.impl;

import com.cominvi.app.commons.general.Usuarios;
import com.cominvi.app.commons.general.Usuariosexternos;
import com.cominvi.app.oauth.services.IUsuarioService;
import com.cominvi.app.oauth.services.IUsuariosexternosService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cominvi.app.commons.capitalhumano.Empleados;
import com.cominvi.app.commons.util.PerfilesJwt;
import com.cominvi.app.commons.util.UtilService;

import com.cominvi.app.oauth.repositories.GlobalRowMapper;
import com.cominvi.app.oauth.repositories.JdbcEmpleadosRepository;
import com.cominvi.app.oauth.repositories.JdbcUsuariosRepository;


@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private JdbcUsuariosRepository usuarioDao;

    @Autowired
    private GlobalRowMapper globalDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private IUsuariosexternosService usuarioextService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Boolean enabled = true;

        try {
            UtilService utilService = new UtilService();
            PerfilesJwt perfil = utilService.asignarToken(username);
            username = (perfil == null) ? username : perfil.getNumeronomina();
            
            List<GrantedAuthority> authorities = new ArrayList<>();
            String sql = "";
            String user = "";
            String password = "";
            
            
            if( !username.contains("|UserExterno|") ) { // Empleado
            	// Buscamos al empleado en el micro servicio de capital humano
            	sql = "select * from capitalhumano.dbo.empleados where numeronomina = '" + username + "'";
            	
            	Empleados empleado = (Empleados) usuarioDao.regresaObjeto(sql, new JdbcEmpleadosRepository.EmpleadosRowMapper());
            	user = empleado.getNumeronomina();
            	
                // Una vez que encontramos al empleado buscamos en la tabla de usuarios
                Usuarios usuario = usuarioDao.findById(empleado.getIdempleado());
                password = usuario.getPassword();
                
                
                // Si perfil es diferente de null, buscamos los roles de acuerdo a la empresa-sistema que selecciono
                if (perfil != null) {
                    List<Map<String, Object>> roles = this.buscarRolesxPerfil(usuario.getIdempleado(), perfil.getIdempresa(), perfil.getIdsistema());
                    authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role.get("nombre").toString()))
                            .collect(Collectors.toList());

                    // .peek(authority -> log.info("Role: " + authority.getAuthority()))
                }

                // Verifica si el usuario esta bloqueado
                enabled = this.verificaUsuario(usuario);
            } else { // Usuario de bolsa de trabajo
            	
            	Usuariosexternos usuario = usuarioextService.findByCorreoAndCelular(username.replace("|UserExterno|", ""));
            	if( usuario == null ) {
            		throw new UsernameNotFoundException("Usuario no encontrado");
            	}
            	
            	List<String> roles = Arrays.asList("ROLE_USERBOLSATRABAJO");
            	user = username;
            	password = usuario.getPassword();
            	
            	authorities = roles.stream()
            			.map(role -> new SimpleGrantedAuthority(role))
            			.collect(Collectors.toList());
            	
            	// Verificamos si el usuario se encuentra activo
            	if( usuario.getEstatus() == 0) {
            		enabled = false;
            	}
            	
            }
            
            return new User(user, password, enabled, true, true, true, authorities);

        } catch (DataAccessException | UsernameNotFoundException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            log.error(e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public Usuarios findByIdEmpleado(Long idempleado) {
        return usuarioDao.findById(idempleado);
    }

    @Override
    public Usuarios update(Usuarios usuario, Long idempleado) {
        return usuarioDao.update(usuario, idempleado);
    }

    @Override
    public Usuarios updatepass(Usuarios usuario, Long idempleado, boolean changepass) {
        if (changepass) {
            // Encryptamos la contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setFechapassword(new Date());
        }
        return usuarioDao.update(usuario, idempleado);
    }

    @Override
    public Empleados finByNomina(String numeronomina) {
        String sql = "select * from capitalhumano.dbo.empleados where numeronomina = '" + numeronomina + "'";
        Empleados empleado = (Empleados) usuarioDao.regresaObjeto(sql, new JdbcEmpleadosRepository.EmpleadosRowMapper());
        return empleado;
    }

    @Override
    public Empleados finByIdempleado(long idempleado) {
        String sql = "select * from capitalhumano.dbo.empleados where idempleado = " + idempleado + ";";
        Empleados empleado = (Empleados) usuarioDao.regresaObjeto(sql, new JdbcEmpleadosRepository.EmpleadosRowMapper());
        return empleado;
    }//finByIdempledo

    @Override
    public boolean existeusuario(String numeronomina) {
        List<Map<String, Object>> lstobjquery;
        boolean existe = false;

        lstobjquery = getusuario(numeronomina);
        if (lstobjquery != null && !lstobjquery.isEmpty()) {
            existe = true;
        }

        return existe;
    }//existeusuario

    /**
     * Consulta
     *
     * @param numeronomina
     * @return
     */
    public List<Map<String, Object>> getusuario(String numeronomina) {
        String sql = "SELECT *"
                + " FROM general.dbo.usuarios u"
                + " INNER JOIN capitalhumano.dbo.empleados e ON e.idempleado = u.idempleado"
                + " WHERE e.numeronomina = '" + numeronomina + "';";
        return globalDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }//getusuario()

    /**
     * Metodo privado para verificar si un usuario esta bloqueado por n maximo
     * de intentos, en caso de ser asi, verifica si ya pasaron 30 minutos
     * despues de la fecha de bloqueo para poder habilitarlo nuevamente
     *
     * @param usuario
     * @return
     */
    private Boolean verificaUsuario(Usuarios usuario) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean enabled = true;

        if (usuario.getBloqueado() == 1 && usuario.getIntentos() > 2) {

            // Buscamos la diferencia en minutos
            String fecha = df.format(usuario.getFechahorabloqueo());
            String sql = "select DATEDIFF(minute, '" + fecha + "', CURRENT_TIMESTAMP) as minutos";
            Map<String, Object> respuesta = (Map<String, Object>) usuarioDao.regresaObjeto(sql, new GlobalRowMapper.GRowMapper());
            String strminutos = respuesta.get("minutos").toString();

            int minutos = Integer.parseInt(strminutos);
            // Si ya pasaron 30 minutos entonces habilitamos al usuario0
            if (minutos >= 30) {
                // Habilitamos al usuario
                usuario.setBloqueado((byte) 0);
                usuario.setIntentos((byte) 0);
                usuario.setFechahorabloqueo(null);
                usuarioDao.update(usuario, usuario.getIdempleado());
                enabled = true;
            } else {
                enabled = false;
            }
        }

        return enabled;

    }

    /**
     * Metodo privado que busca los roles de acuerdo a los parametros que se
     * envian
     *
     * @param idempleado
     * @param idempresa
     * @param idsistema
     * @return
     */
    private List<Map<String, Object>> buscarRolesxPerfil(Long idempleado, Long idempresa, Long idsistema) {

        String sql = "select r.nombre "
        		+ "from general.dbo.empleados_perfiles e "
                + "inner join general.dbo.perfiles p on e.idperfil = p.idperfil "
                + "inner join general.dbo.roles r on p.idrol = r.idrol "
                + "where e.idempleado = " + idempleado + " and p.idempresa = " + idempresa + " and r.estatus = 1";

        return usuarioDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }

    @Override
    public Usuarios validaUsuario(String usuario, String password) {

        String sql = "select * from capitalhumano.dbo.empleados where numeronomina = '" + usuario + "'";
        Empleados empleado = (Empleados) usuarioDao.regresaObjeto(sql, new JdbcEmpleadosRepository.EmpleadosRowMapper());
        Usuarios user = usuarioDao.findById(empleado.getIdempleado());
        
        

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> cargaUsuarios() {

        String sql = "SELECT u.idempleado, CONCAT(e.nombre,' ',e.apellidopaterno,' ',e.apellidomaterno) as nombrecompleto, e.numeronomina,"
                + " u.correo, u.intentos, CASE u.bloqueado WHEN 0 THEN 'NO' WHEN 1 THEN 'SI' ELSE 'OTHER' END AS bloqueado,"
                + " CASE u.estatus WHEN 0 THEN 'INACTIVO' WHEN 1 THEN 'ACTIVO' ELSE 'OTHER' END AS estatus"
                + " FROM general.dbo.usuarios u"
                + " INNER JOIN capitalhumano.dbo.empleados e on u.idempleado = e.idempleado"
                + " WHERE e.estatus = 1 and u.estatus IN (0,1);";

        return globalDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }// cargaUsuarios()

    @Override
    public Usuarios guardar(Usuarios usuario) {
        // Encryptamos la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechapassword(new Date());
        return usuarioDao.save(usuario);
    }

    @Override
    public Map<String, Object> cargaParametrosEmaill() {

        String sql = "select idparametro, nombre, valor from general.dbo.parametros where idparametro in (4,5,6,7)";
        List<Map<String, Object>> parametros = globalDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
        Map<String, Object> parametro = new HashMap<String, Object>();
        for (Map<String, Object> m : parametros) {
            String valor = m.get("valor").toString();
            // Host
            if (Long.parseLong(m.get("idparametro").toString()) == 4) {
                parametro.put("host", valor);
            }
            // Puerto
            if (Long.parseLong(m.get("idparametro").toString()) == 5) {
                parametro.put("puerto", valor);
            }
            // Correo
            if (Long.parseLong(m.get("idparametro").toString()) == 6) {
                parametro.put("correo", valor);
            }

            // Password
            if (Long.parseLong(m.get("idparametro").toString()) == 7) {
                parametro.put("password", valor);
            }

        }

        return parametro;
    }

    /**
     * Metodo public que busca los perfiles del usuario
     *
     * @param idempleado
     * @param idempresa
     * @param idsistema
     * @return
     */
    @Override
    public List<Map<String, Object>> getlistadoperfilusuario(Long idsistema, Long idempresa, Long idempleado) {

        String sql = "SELECT"
                + " e.idempleado, e.numeronomina, CONCAT(e.nombre, ' ', e.apellidopaterno, ' ', e.apellidomaterno) as nombre,"
                + " ep.idperfil, p.idperfil, p.idrol, r.nombre, r.descripcion, 'NO' AS remover"
                + " FROM capitalhumano.dbo.empleados e"
                + " INNER JOIN general.dbo.empleados_perfiles ep ON ep.idempleado = e.idempleado"
                + " INNER JOIN general.dbo.perfiles p ON p.idperfil = ep.idperfil AND p.idsistema = " + idsistema + " AND p.idempresa = " + idempresa
                + " INNER JOIN general.dbo.roles r ON r.idrol = p.idrol AND r.estatus = 1"
                + " WHERE e.idempleado = " + idempleado + " ;";

        return usuarioDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }// getlistadoperfilusuario()

    /**
     * Metodo public que busca los perfiles del usuario
     *
     * @param idempleado
     * @param idempresa
     * @param idsistema
     * @return
     */
    @Override
    public List<Map<String, Object>> getlistadoperfilsistema(Long idsistema, Long idempresa, Long idempleado) {

        String sql = "SELECT r.idrol, p.idperfil, r.nombre, r.descripcion, 'NO' AS agregar"
                + " FROM general.dbo.roles r"
                + " INNER JOIN general.dbo.perfiles p ON p.idrol = r.idrol"
                + " WHERE r.estatus = 1"
                + " AND p.idsistema = " + idsistema + " AND p.idempresa = " + idempresa
                + " AND p.idperfil NOT IN(select idperfil from general.dbo.empleados_perfiles where idempleado = " + idempleado + ");";

        return usuarioDao.regresaLista(sql, new GlobalRowMapper.GRowMapper());
    }// getlistadoperfilsistema()

    /**
     * Metodo public
     *
     * @param idempleado
     * @param idperfil
     * @return
     */
    @Override
    public boolean removeperfilusuario(long idempleado, long idperfil) {
        return true;
    }// removeperfilusuario()

    /**
     * Metodo public
     *
     * @param idempleado
     * @param idperfil
     * @return
     */
    @Override
    public boolean addperfilusuario(long idempleado, long idperfil) {
        return true;
    }// addperfilusuario()

	@Override
	public String test() {
		
		String password1 = "sistemas2020";
		String passencode = passwordEncoder.encode(password1);
		String passencode2 = passwordEncoder.encode(password1);
		
		log.info(password1);
		log.info(passencode);
		log.info(passencode2);
		
		if (passwordEncoder.matches(passencode, passencode2)) {
            return "exito";
        } else {
            return "denegado";
        }
		
		
		
		
	}

}// -->
