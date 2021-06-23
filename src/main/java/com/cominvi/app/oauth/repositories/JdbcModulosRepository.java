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
import com.cominvi.app.commons.general.Modulos;

@Repository
public class JdbcModulosRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.modulos";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(descripcion,estatus,fechahoraalta,fechahoramod,idempleadoalta,idempleadomod,nombre,idsistema) "
          + "values (?,?,getdate(),getdate(),?,?,?,?)";

  public List<Modulos> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new ModulosRowMapper());
  }

  public Modulos findById(Long idmodulo) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idmodulo=" + idmodulo, new ModulosRowMapper());
  }

  public Modulos save(final Modulos modulo) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, modulo.getDescripcion());
            ps.setObject(2, modulo.getEstatus());
            ps.setObject(3, modulo.getIdempleadoalta());
            ps.setObject(4, modulo.getIdempleadomod());
            ps.setObject(5, modulo.getNombre());
            ps.setObject(
                6, modulo.getSistemas() != null ? modulo.getSistemas().getIdsistema() : null);

            return ps;
          }
        },
        keyHolder);

    modulo.setIdmodulo(keyHolder.getKey().longValue());

    return modulo;
  }

  public Boolean saveAll(final List<Modulos> modulos) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Modulos modulo = modulos.get(i);
            ps.setObject(1, modulo.getDescripcion());
            ps.setObject(2, modulo.getEstatus());
            ps.setObject(3, modulo.getIdempleadoalta());
            ps.setObject(4, modulo.getIdempleadomod());
            ps.setObject(5, modulo.getNombre());
            ps.setObject(
                6, modulo.getSistemas() != null ? modulo.getSistemas().getIdsistema() : null);
          }

          @Override
          public int getBatchSize() {
            return modulos.size();
          }
        });

    return true;
  }

  public Modulos update(final Modulos modulo, Long idmodulo) {
    String sql =
        "UPDATE "
            + tabla
            + " SET descripcion = ?,estatus = ?,fechahoramod = getdate(),idempleadomod = ?,nombre = ?,idsistema = ?  WHERE idmodulo= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          modulo.getDescripcion(),
          modulo.getEstatus(),
          modulo.getIdempleadomod(),
          modulo.getNombre(),
          modulo.getSistemas().getIdsistema(),
          modulo.getIdmodulo()
        });

    return modulo;
  }

  public Boolean removeById(Long idmodulo) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idmodulo= ?";
    jdbcTemplate.update(delete, new Object[] {idmodulo});
    return true;
  }

  public static class ModulosRowMapper implements RowMapper<Modulos> {

    @Override
    public Modulos mapRow(ResultSet rs, int rowNum) throws SQLException {

      Modulos modulo = new Modulos();
      modulo.setIdmodulo(rs.getLong("idmodulo"));
      modulo.setDescripcion(rs.getString("descripcion"));
      modulo.setEstatus(rs.getByte("estatus"));
      modulo.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      modulo.setFechahoramod(rs.getTimestamp("fechahoramod"));
      modulo.setIdempleadoalta(rs.getLong("idempleadoalta"));
      modulo.setIdempleadomod(rs.getLong("idempleadomod"));
      modulo.setNombre(rs.getString("nombre"));
      modulo.setSistemas(new com.cominvi.app.commons.general.Sistemas(rs.getLong("idsistema")));

      return modulo;
    }
  }
}
