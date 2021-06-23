package com.cominvi.app.oauth.services;

import java.util.List;
import com.cominvi.app.commons.general.EmpleadosPerfiles;

public interface IEmpleadosPerfilesService {

    List<EmpleadosPerfiles> findAll();

    EmpleadosPerfiles findById(Long idempleado, Long idperfil);

    EmpleadosPerfiles save(EmpleadosPerfiles empleadosperfile);

    Boolean saveAll(List<EmpleadosPerfiles> empleadosperfiles);

    EmpleadosPerfiles update(EmpleadosPerfiles empleadosperfile, Long idempleado, Long idperfil);

    Boolean removeById(Long idempleado, Long idperfil);
}
