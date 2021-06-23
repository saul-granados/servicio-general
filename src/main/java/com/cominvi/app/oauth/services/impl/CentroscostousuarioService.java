package com.cominvi.app.oauth.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cominvi.app.commons.general.Centroscostousuario;
import com.cominvi.app.oauth.repositories.JdbcCentroscostousuarioRepository;
import com.cominvi.app.oauth.services.ICentroscostousuarioService;

@Service
public class CentroscostousuarioService implements ICentroscostousuarioService {

    @Autowired
    private JdbcCentroscostousuarioRepository centroscostousuarioDao;

    @Transactional(readOnly = true)
    @Override
    public List<Centroscostousuario> findAll() {
        return centroscostousuarioDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Centroscostousuario findById(Long idcentroscostousuario) {
        return centroscostousuarioDao.findById(idcentroscostousuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Centroscostousuario findByIdmoduloAndIdempleado(Long idmodulo, Long idempleado, Long idempresa) {
        String sql = "SELECT ccu.*"
                + " FROM general.dbo.centroscostousuario ccu"
                + " INNER JOIN compras.dbo.centroscosto cc ON cc.idcentrocosto =  ccu.idcentrocosto"
                + " WHERE ccu.idmodulo = " + idmodulo + " AND ccu.idempleado = " + idempleado + " AND cc.idempresa = " + idempresa + ";";
        return findOneCustom(sql);
    }

    @Transactional
    @Override
    public Centroscostousuario save(Centroscostousuario centroscostousuario) {
        return centroscostousuarioDao.save(centroscostousuario);
    }

    @Transactional
    @Override
    public Boolean saveAll(List<Centroscostousuario> centroscostousuarios) {
        return centroscostousuarioDao.saveAll(centroscostousuarios);
    }

    @Transactional
    @Override
    public Centroscostousuario update(
            Centroscostousuario centroscostousuario, Long idcentroscostousuario) {
        return centroscostousuarioDao.update(centroscostousuario, idcentroscostousuario);
    }

    @Transactional
    @Override
    public Boolean removeById(Long idcentroscostousuario) {
        return centroscostousuarioDao.removeById(idcentroscostousuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Centroscostousuario findOneCustom(String sql) {
        try {
            return (Centroscostousuario) centroscostousuarioDao.regresaObjeto(
                    sql, new JdbcCentroscostousuarioRepository.CentroscostousuarioRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Centroscostousuario> findAllCustom(String sql) {
        return centroscostousuarioDao.regresaLista(
                sql, new JdbcCentroscostousuarioRepository.CentroscostousuarioRowMapper());
    }

}
