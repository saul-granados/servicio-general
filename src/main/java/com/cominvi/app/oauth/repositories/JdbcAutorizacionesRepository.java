package com.cominvi.app.oauth.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.cominvi.app.commons.oauth.Autorizaciones;

@Repository
public class JdbcAutorizacionesRepository extends JdbcGlobalRepository {


  private final String tabla = "general.dbo.autorizaciones";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(equipoalta,fechahoraalta,idempleado,observaciones,tipodispositivoalta,idempresa,idmodulo) "
          + "values (?,getdate(),?,?,?,?,?)";

  public List<Autorizaciones> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new AutorizacionesRowMapper());
  }

  public Autorizaciones findById(Long idautorizacion) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idautorizacion=" + idautorizacion,
        new AutorizacionesRowMapper());
  }

  public Autorizaciones save(final Autorizaciones autorizacion) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, autorizacion.getEquipoalta());
            ps.setObject(2, autorizacion.getIdempleado());
            ps.setObject(3, autorizacion.getObservaciones());
            ps.setObject(4, autorizacion.getTipodispositivoalta());
            ps.setObject(
                5,
                autorizacion.getEmpresas() != null
                    ? autorizacion.getEmpresas().getIdempresa()
                    : null);
            ps.setObject(
                6,
                autorizacion.getModulos() != null ? autorizacion.getModulos().getIdmodulo() : null);

            return ps;
          }
        },
        keyHolder);

    autorizacion.setIdautorizacion(keyHolder.getKey().longValue());

    return autorizacion;
  }

  public Boolean saveAll(final List<Autorizaciones> autorizacions) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Autorizaciones autorizacion = autorizacions.get(i);
            ps.setObject(1, autorizacion.getEquipoalta());
            ps.setObject(2, autorizacion.getIdempleado());
            ps.setObject(3, autorizacion.getObservaciones());
            ps.setObject(4, autorizacion.getTipodispositivoalta());
            ps.setObject(
                5,
                autorizacion.getEmpresas() != null
                    ? autorizacion.getEmpresas().getIdempresa()
                    : null);
            ps.setObject(
                6,
                autorizacion.getModulos() != null ? autorizacion.getModulos().getIdmodulo() : null);
          }

          @Override
          public int getBatchSize() {
            return autorizacions.size();
          }
        });

    return true;
  }

  public Autorizaciones update(final Autorizaciones autorizacion, Long idautorizacion) {
    String sql =
        "UPDATE "
            + tabla
            + " SET equipoalta = ?,idempleado = ?,observaciones = ?,tipodispositivoalta = ?,idempresa = ?,idmodulo = ?  WHERE idautorizacion= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          autorizacion.getEquipoalta(),
          autorizacion.getIdempleado(),
          autorizacion.getObservaciones(),
          autorizacion.getTipodispositivoalta(),
          autorizacion.getEmpresas().getIdempresa(),
          autorizacion.getModulos().getIdmodulo(),
          autorizacion.getIdautorizacion()
        });

    return autorizacion;
  }

  public Boolean removeById(Long idautorizacion) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idautorizacion= ?";
    jdbcTemplate.update(delete, new Object[] {idautorizacion});
    return true;
  }

  public static class AutorizacionesRowMapper implements RowMapper<Autorizaciones> {

    @Override
    public Autorizaciones mapRow(ResultSet rs, int rowNum) throws SQLException {

      Autorizaciones autorizacion = new Autorizaciones();
      autorizacion.setIdautorizacion(rs.getLong("idautorizacion"));
      autorizacion.setEquipoalta(rs.getString("equipoalta"));
      autorizacion.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      autorizacion.setIdempleado(rs.getLong("idempleado"));
      autorizacion.setObservaciones(rs.getString("observaciones"));
      autorizacion.setTipodispositivoalta(rs.getString("tipodispositivoalta"));
      autorizacion.setEmpresas(new com.cominvi.app.commons.oauth.Empresas(rs.getLong("idempresa")));
      autorizacion.setModulos(new com.cominvi.app.commons.oauth.Modulos(rs.getLong("idmodulo")));

      return autorizacion;
    }
  }
}
