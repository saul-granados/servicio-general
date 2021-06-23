package com.cominvi.app.oauth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cominvi.app.commons.general.EmpleadosPerfiles;
import com.cominvi.app.oauth.repositories.JdbcEmpleadosPerfilesRepository;
import com.cominvi.app.oauth.services.IEmpleadosPerfilesService;

@Service
public class EmpleadosPerfilesService implements IEmpleadosPerfilesService {

    @Autowired
    private JdbcEmpleadosPerfilesRepository empleadosperfileDao;

    @Transactional(readOnly = true)
    @Override
    public List<EmpleadosPerfiles> findAll() {
        return empleadosperfileDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public EmpleadosPerfiles findById(Long idempleado, Long idperfil) {
        return empleadosperfileDao.findById(idempleado, idperfil);
    }

    @Transactional
    @Override
    public EmpleadosPerfiles save(EmpleadosPerfiles empleadosperfile) {
        return empleadosperfileDao.save(empleadosperfile);
    }

    @Transactional
    @Override
    public Boolean saveAll(List<EmpleadosPerfiles> empleadosperfiles) {
        return empleadosperfileDao.saveAll(empleadosperfiles);
    }

    @Transactional
    @Override
    public EmpleadosPerfiles update(
            EmpleadosPerfiles empleadosperfile, Long idempleado, Long idperfil) {
        return empleadosperfileDao.update(empleadosperfile, idempleado, idperfil);
    }

    @Transactional
    @Override
    public Boolean removeById(Long idempleado, Long idperfil) {
        return empleadosperfileDao.removeById(idempleado, idperfil);
    }
}
