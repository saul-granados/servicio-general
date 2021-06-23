package com.cominvi.app.oauth.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cominvi.app.commons.general.EmpleadosPerfiles;

@Repository
public class JdbcEmpleadosPerfilesRepository {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private final String tabla = "general.dbo.empleados_perfiles";

    private final String insert
            = "INSERT INTO "
            + tabla
            + " "
            + "(idempleado,idperfil,fechahoraalta,idempleadoalta) "
            + "values (?,?,getdate(),?)";

    public List<EmpleadosPerfiles> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + tabla, new EmpleadosPerfilesRowMapper());
    }

    public EmpleadosPerfiles findById(Long idempleado, Long idperfil) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + tabla + " WHERE idempleado=" + idempleado + " and idperfil=" + idperfil,
                new EmpleadosPerfilesRowMapper());
    }

    public EmpleadosPerfiles save(final EmpleadosPerfiles empleadosperfile) {

        jdbcTemplate.update(
                new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, empleadosperfile.getIdempleado());
                ps.setObject(2, empleadosperfile.getIdperfil());
                ps.setObject(3, empleadosperfile.getIdempleadoalta());
                

                return ps;
            }
        });

        return empleadosperfile;
    }

    public Boolean saveAll(final List<EmpleadosPerfiles> empleadosperfiles) {

        jdbcTemplate.batchUpdate(
                insert,
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                EmpleadosPerfiles empleadosperfile = empleadosperfiles.get(i);
                ps.setObject(1, empleadosperfile.getIdempleado());
                ps.setObject(2, empleadosperfile.getIdperfil());
                ps.setObject(3, empleadosperfile.getIdempleadoalta());
                ps.setObject(
                        4,
                        empleadosperfile.getPerfiles() != null
                        ? empleadosperfile.getPerfiles().getIdperfil()
                        : null);
            }

            @Override
            public int getBatchSize() {
                return empleadosperfiles.size();
            }
        });

        return true;
    }

    public EmpleadosPerfiles update(
            final EmpleadosPerfiles empleadosperfile, Long idempleado, Long idperfil) {
        String sql = "UPDATE " + tabla + " SET idperfil = ?  WHERE idempleado= ? and idperfil= ?";

        jdbcTemplate.update(
                sql,
                new Object[]{
                    empleadosperfile.getPerfiles().getIdperfil(),
                    empleadosperfile.getIdempleado(),
                    empleadosperfile.getIdperfil()
                });

        return empleadosperfile;
    }

    public Boolean removeById(Long idempleado, Long idperfil) {
        String delete = "DELETE FROM " + tabla + " WHERE idempleado= ? and idperfil= ?";
        jdbcTemplate.update(delete, new Object[]{idempleado, idperfil});
        return true;
    }

    private static class EmpleadosPerfilesRowMapper implements RowMapper<EmpleadosPerfiles> {

        @Override
        public EmpleadosPerfiles mapRow(ResultSet rs, int rowNum) throws SQLException {

            EmpleadosPerfiles empleadosperfile = new EmpleadosPerfiles();
            empleadosperfile.setIdempleado(rs.getLong("idempleado"));
            empleadosperfile.setIdperfil(rs.getLong("idperfil"));
            empleadosperfile.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
            empleadosperfile.setIdempleadoalta(rs.getLong("idempleadoalta"));
            empleadosperfile.setPerfiles(
                    new com.cominvi.app.commons.general.Perfiles(rs.getLong("idperfil")));

            return empleadosperfile;
        }
    }
}
