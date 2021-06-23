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
import com.cominvi.app.commons.general.Dominios;

@Repository
public class JdbcDominiosRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.dominios";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(dominio,estatus,fechahoraalta,fechahoramod,idempleadoalta,idempleadomod) "
          + "values (?,?,getdate(),getdate(),?,?)";

  public List<Dominios> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new DominiosRowMapper());
  }

  public Dominios findById(Long iddominio) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE iddominio=" + iddominio, new DominiosRowMapper());
  }

  public Dominios save(final Dominios dominio) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, dominio.getDominio());
            ps.setObject(2, dominio.getEstatus());
            ps.setObject(3, dominio.getIdempleadoalta());
            ps.setObject(4, dominio.getIdempleadomod());

            return ps;
          }
        },
        keyHolder);

    dominio.setIddominio(keyHolder.getKey().longValue());

    return dominio;
  }

  public Boolean saveAll(final List<Dominios> dominios) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Dominios dominio = dominios.get(i);
            ps.setObject(1, dominio.getDominio());
            ps.setObject(2, dominio.getEstatus());
            ps.setObject(3, dominio.getIdempleadoalta());
            ps.setObject(4, dominio.getIdempleadomod());
          }

          @Override
          public int getBatchSize() {
            return dominios.size();
          }
        });

    return true;
  }

  public Dominios update(final Dominios dominio, Long iddominio) {
    String sql =
        "UPDATE "
            + tabla
            + " SET dominio = ?,estatus = ?,fechahoramod = getdate(),idempleadomod = ?  WHERE iddominio= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          dominio.getDominio(),
          dominio.getEstatus(),
          dominio.getIdempleadomod(),
          dominio.getIddominio()
        });

    return dominio;
  }

  public Boolean removeById(Long iddominio) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE iddominio= ?";
    jdbcTemplate.update(delete, new Object[] {iddominio});
    return true;
  }

  private static class DominiosRowMapper implements RowMapper<Dominios> {

    @Override
    public Dominios mapRow(ResultSet rs, int rowNum) throws SQLException {

      Dominios dominio = new Dominios();
      dominio.setIddominio(rs.getLong("iddominio"));
      dominio.setDominio(rs.getString("dominio"));
      dominio.setEstatus(rs.getInt("estatus"));
      dominio.setFechahoraalta(rs.getString("fechahoraalta"));
      dominio.setFechahoramod(rs.getTimestamp("fechahoramod"));
      dominio.setIdempleadoalta(rs.getLong("idempleadoalta"));
      dominio.setIdempleadomod(rs.getLong("idempleadomod"));

      return dominio;
    }
  }
}
