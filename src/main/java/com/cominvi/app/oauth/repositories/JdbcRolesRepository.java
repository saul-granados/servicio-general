package com.cominvi.app.oauth.repositories;



import com.cominvi.app.commons.general.Roles;
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


@Repository
public class JdbcRolesRepository extends JdbcGlobalRepository{


  private final String tabla = "general.dbo.roles";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(descripcion,estatus,fechahoraalta,idempleadoalta,indicador,nombre,rolmaster) "
          + "values (?,?,getdate(),?,?,?,?)";

  public List<Roles> findAll() {
    return jdbcTemplate.query("SELECT * FROM " + tabla, new RolesRowMapper());
  }

  public Roles findById(Long idrol) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idrol=" + idrol, new RolesRowMapper());
  }

  public Roles save(final Roles rol) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, rol.getDescripcion());
            ps.setObject(2, rol.getEstatus());
            ps.setObject(3, rol.getIdempleadoalta());
            ps.setObject(4, rol.getIndicador());
            ps.setObject(5, rol.getNombre());
            ps.setObject(6, rol.getRolmaster());

            return ps;
          }
        },
        keyHolder);

    rol.setIdrol(keyHolder.getKey().longValue());

    return rol;
  }

  public Boolean saveAll(final List<Roles> rols) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Roles rol = rols.get(i);
            ps.setObject(1, rol.getDescripcion());
            ps.setObject(2, rol.getEstatus());
            ps.setObject(3, rol.getIdempleadoalta());
            ps.setObject(4, rol.getIndicador());
            ps.setObject(5, rol.getNombre());
            ps.setObject(6, rol.getRolmaster());
          }

          @Override
          public int getBatchSize() {
            return rols.size();
          }
        });

    return true;
  }

  public Roles update(final Roles rol, Long idrol) {
    String sql =
        "UPDATE "
            + tabla
            + " SET descripcion = ?,estatus = ?,indicador = ?,nombre = ?,rolmaster = ?  WHERE idrol= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          rol.getDescripcion(),
          rol.getEstatus(),
          rol.getIndicador(),
          rol.getNombre(),
          rol.getRolmaster(),
          rol.getIdrol()
        });

    return rol;
  }

  public Boolean removeById(Long idrol) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idrol= ?";
    jdbcTemplate.update(delete, new Object[] {idrol});
    return true;
  }

  private static class RolesRowMapper implements RowMapper<Roles> {

    @Override
    public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {

      Roles rol = new Roles();
      rol.setIdrol(rs.getLong("idrol"));
      rol.setDescripcion(rs.getString("descripcion"));
      rol.setEstatus(rs.getByte("estatus"));
      rol.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      rol.setIdempleadoalta(rs.getLong("idempleadoalta"));
      rol.setIndicador(rs.getByte("indicador"));
      rol.setNombre(rs.getString("nombre"));
      rol.setRolmaster(rs.getByte("rolmaster"));

      return rol;
    }
  }
}
