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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.cominvi.app.commons.general.Sistemas;

@Repository
public class JdbcSistemasRepository {

  @Autowired public JdbcTemplate jdbcTemplate;

  private final String tabla = "general.dbo.sistemas";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(descripcion,estatus,fechahoraalta,fechahoramod,idempleadoalta,idempleadomod,logo,nombre,url,version) "
          + "values (?,?,getdate(),getdate(),?,?,?,?,?,?)";

  public List<Sistemas> findAll() {
    return jdbcTemplate.query("SELECT * FROM " + tabla, new SistemasRowMapper());
  }

  public Sistemas findById(Long idsistema) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idsistema=" + idsistema, new SistemasRowMapper());
  }

  public Sistemas save(final Sistemas sistema) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, sistema.getDescripcion());
            ps.setObject(2, sistema.getEstatus());
            ps.setObject(3, sistema.getIdempleadoalta());
            ps.setObject(4, sistema.getIdempleadomod());
            ps.setObject(5, sistema.getLogo());
            ps.setObject(6, sistema.getNombre());
            ps.setObject(7, sistema.getUrl());
            ps.setObject(8, sistema.getVersion());

            return ps;
          }
        },
        keyHolder);

    sistema.setIdsistema(keyHolder.getKey().longValue());

    return sistema;
  }

  public Boolean saveAll(final List<Sistemas> sistemas) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Sistemas sistema = sistemas.get(i);
            ps.setObject(1, sistema.getDescripcion());
            ps.setObject(2, sistema.getEstatus());
            ps.setObject(3, sistema.getIdempleadoalta());
            ps.setObject(4, sistema.getIdempleadomod());
            ps.setObject(5, sistema.getLogo());
            ps.setObject(6, sistema.getNombre());
            ps.setObject(7, sistema.getUrl());
            ps.setObject(8, sistema.getVersion());
          }

          @Override
          public int getBatchSize() {
            return sistemas.size();
          }
        });

    return true;
  }

  public Sistemas update(final Sistemas sistema, Long idsistema) {
    String sql =
        "UPDATE "
            + tabla
            + " SET descripcion = ?,estatus = ?,fechahoramod = getdate(),idempleadomod = ?,logo = ?,nombre = ?,url = ?,version = ?  WHERE idsistema= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          sistema.getDescripcion(),
          sistema.getEstatus(),
          sistema.getIdempleadomod(),
          sistema.getLogo(),
          sistema.getNombre(),
          sistema.getUrl(),
          sistema.getVersion(),
          sistema.getIdsistema()
        });

    return sistema;
  }

  public Boolean removeById(Long idsistema) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idsistema= ?";
    jdbcTemplate.update(delete, new Object[] {idsistema});
    return true;
  }

  private static class SistemasRowMapper implements RowMapper<Sistemas> {

    @Override
    public Sistemas mapRow(ResultSet rs, int rowNum) throws SQLException {

      Sistemas sistema = new Sistemas();
      sistema.setIdsistema(rs.getLong("idsistema"));
      sistema.setDescripcion(rs.getString("descripcion"));
      sistema.setEstatus(rs.getByte("estatus"));
      sistema.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      sistema.setFechahoramod(rs.getTimestamp("fechahoramod"));
      sistema.setIdempleadoalta(rs.getLong("idempleadoalta"));
      sistema.setIdempleadomod(rs.getLong("idempleadomod"));
      sistema.setLogo(rs.getString("logo"));
      sistema.setNombre(rs.getString("nombre"));
      sistema.setUrl(rs.getString("url"));
      sistema.setVersion(rs.getString("version"));

      return sistema;
    }
  }
}
