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
import com.cominvi.app.commons.general.Centroscostousuario;

@Repository
public class JdbcCentroscostousuarioRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.centroscostousuario";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(equipoalta,equipomod,fechahoraalta,fechahoramod,idcentrocosto,idempleado,idempleadoalta,idempleadomod,tipodispositivoalta,tipodispositivomod,idmodulo) "
          + "values (?,?,getdate(),getdate(),?,?,?,?,?,?,?)";

  public List<Centroscostousuario> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new CentroscostousuarioRowMapper());
  }

  public Centroscostousuario findById(Long idcentroscostousuario) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idcentroscostousuario=" + idcentroscostousuario,
        new CentroscostousuarioRowMapper());
  }

  public Centroscostousuario save(final Centroscostousuario centroscostousuario) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, centroscostousuario.getEquipoalta());
            ps.setObject(2, centroscostousuario.getEquipomod());
            ps.setObject(3, centroscostousuario.getIdcentrocosto());
            ps.setObject(4, centroscostousuario.getIdempleado());
            ps.setObject(5, centroscostousuario.getIdempleadoalta());
            ps.setObject(6, centroscostousuario.getIdempleadomod());
            ps.setObject(7, centroscostousuario.getTipodispositivoalta());
            ps.setObject(8, centroscostousuario.getTipodispositivomod());
            ps.setObject(
                9,
                centroscostousuario.getModulos() != null
                    ? centroscostousuario.getModulos().getIdmodulo()
                    : null);

            return ps;
          }
        },
        keyHolder);

    centroscostousuario.setIdcentroscostousuario(keyHolder.getKey().longValue());

    return centroscostousuario;
  }

  public Boolean saveAll(final List<Centroscostousuario> centroscostousuarios) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Centroscostousuario centroscostousuario = centroscostousuarios.get(i);
            ps.setObject(1, centroscostousuario.getEquipoalta());
            ps.setObject(2, centroscostousuario.getEquipomod());
            ps.setObject(3, centroscostousuario.getIdcentrocosto());
            ps.setObject(4, centroscostousuario.getIdempleado());
            ps.setObject(5, centroscostousuario.getIdempleadoalta());
            ps.setObject(6, centroscostousuario.getIdempleadomod());
            ps.setObject(7, centroscostousuario.getTipodispositivoalta());
            ps.setObject(8, centroscostousuario.getTipodispositivomod());
            ps.setObject(
                9,
                centroscostousuario.getModulos() != null
                    ? centroscostousuario.getModulos().getIdmodulo()
                    : null);
          }

          @Override
          public int getBatchSize() {
            return centroscostousuarios.size();
          }
        });

    return true;
  }

  public Centroscostousuario update(
      final Centroscostousuario centroscostousuario, Long idcentroscostousuario) {
    String sql =
        "UPDATE "
            + tabla
            + " SET equipoalta = ?,equipomod = ?,fechahoramod = getdate(),idcentrocosto = ?,idempleado = ?,idempleadomod = ?,tipodispositivoalta = ?,tipodispositivomod = ?,idmodulo = ?  WHERE idcentroscostousuario= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          centroscostousuario.getEquipoalta(),
          centroscostousuario.getEquipomod(),
          centroscostousuario.getIdcentrocosto(),
          centroscostousuario.getIdempleado(),
          centroscostousuario.getIdempleadomod(),
          centroscostousuario.getTipodispositivoalta(),
          centroscostousuario.getTipodispositivomod(),
          centroscostousuario.getModulos().getIdmodulo(),
          centroscostousuario.getIdcentroscostousuario()
        });

    return centroscostousuario;
  }

  public Boolean removeById(Long idcentroscostousuario) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idcentroscostousuario= ?";
    jdbcTemplate.update(delete, new Object[] {idcentroscostousuario});
    return true;
  }

  public static class CentroscostousuarioRowMapper implements RowMapper<Centroscostousuario> {

    @Override
    public Centroscostousuario mapRow(ResultSet rs, int rowNum) throws SQLException {

      Centroscostousuario centroscostousuario = new Centroscostousuario();
      centroscostousuario.setIdcentroscostousuario(rs.getLong("idcentroscostousuario"));
      centroscostousuario.setEquipoalta(rs.getString("equipoalta"));
      centroscostousuario.setEquipomod(rs.getString("equipomod"));
      centroscostousuario.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      centroscostousuario.setFechahoramod(rs.getTimestamp("fechahoramod"));
      centroscostousuario.setIdcentrocosto(rs.getLong("idcentrocosto"));
      centroscostousuario.setIdempleado(rs.getLong("idempleado"));
      centroscostousuario.setIdempleadoalta(rs.getLong("idempleadoalta"));
      centroscostousuario.setIdempleadomod(rs.getLong("idempleadomod"));
      centroscostousuario.setTipodispositivoalta(rs.getString("tipodispositivoalta"));
      centroscostousuario.setTipodispositivomod(rs.getString("tipodispositivomod"));
      centroscostousuario.setModulos(
          new com.cominvi.app.commons.general.Modulos(rs.getLong("idmodulo")));

      return centroscostousuario;
    }
  }
}
