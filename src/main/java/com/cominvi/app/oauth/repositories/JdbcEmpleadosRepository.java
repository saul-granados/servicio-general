package com.cominvi.app.oauth.repositories;

import com.cominvi.app.commons.capitalhumano.Empleados;
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
public class JdbcEmpleadosRepository extends JdbcGlobalRepository{

  private final String tabla = "capitalhumano.dbo.empleados";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(apellidomaterno,apellidopaterno,estatus,fechahoraalta,fechahoramod,fechanacimiento,idempleadoalta,idempleadomod,nombre,numeronomina,rfc) "
          + "values (?,?,?,getdate(),getdate(),?,?,?,?,?,?)";

  public List<Empleados> findAll() {
    return jdbcTemplate.query("SELECT * FROM " + tabla, new EmpleadosRowMapper());
  }

  public Empleados findById(Long idempleado) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idempleado=" + idempleado, new EmpleadosRowMapper());
  }

  public Empleados save(final Empleados empleado) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, empleado.getApellidomaterno());
            ps.setObject(2, empleado.getApellidopaterno());
            ps.setObject(3, empleado.getEstatus());
            ps.setObject(4, empleado.getFechanacimiento());
            ps.setObject(5, empleado.getIdempleadoalta());
            ps.setObject(6, empleado.getIdempleadomod());
            ps.setObject(7, empleado.getNombre());
            ps.setObject(8, empleado.getNumeronomina());
            ps.setObject(9, empleado.getRfc());

            return ps;
          }
        },
        keyHolder);

    empleado.setIdempleado(keyHolder.getKey().longValue());

    return empleado;
  }

  public Boolean saveAll(final List<Empleados> empleados) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Empleados empleado = empleados.get(i);
            ps.setObject(1, empleado.getApellidomaterno());
            ps.setObject(2, empleado.getApellidopaterno());
            ps.setObject(3, empleado.getEstatus());
            ps.setObject(4, empleado.getFechanacimiento());
            ps.setObject(5, empleado.getIdempleadoalta());
            ps.setObject(6, empleado.getIdempleadomod());
            ps.setObject(7, empleado.getNombre());
            ps.setObject(8, empleado.getNumeronomina());
            ps.setObject(9, empleado.getRfc());
          }

          @Override
          public int getBatchSize() {
            return empleados.size();
          }
        });

    return true;
  }

  public Empleados update(final Empleados empleado, Long idempleado) {
    String sql =
        "UPDATE "
            + tabla
            + " SET apellidomaterno = ?,apellidopaterno = ?,estatus = ?,fechahoramod = getdate(),fechanacimiento = ?,idempleadomod = ?,nombre = ?,numeronomina = ?,rfc = ?  WHERE idempleado= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          empleado.getApellidomaterno(),
          empleado.getApellidopaterno(),
          empleado.getEstatus(),
          empleado.getFechanacimiento(),
          empleado.getIdempleadomod(),
          empleado.getNombre(),
          empleado.getNumeronomina(),
          empleado.getRfc(),
          empleado.getIdempleado()
        });

    return empleado;
  }

  public Boolean removeById(Long idempleado) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idempleado= ?";
    jdbcTemplate.update(delete, new Object[] {idempleado});
    return true;
  }
  
  public Empleados FindByNomina(String numeronomina) {
        return jdbcTemplate.queryForObject("select * from " + tabla + " where numeronomina = '" + numeronomina + "' ", new EmpleadosRowMapper());
    }

  public static class EmpleadosRowMapper implements RowMapper<Empleados> {

    @Override
    public Empleados mapRow(ResultSet rs, int rowNum) throws SQLException {

      Empleados empleado = new Empleados();
      empleado.setIdempleado(rs.getLong("idempleado"));
      empleado.setApellidomaterno(rs.getString("apellidomaterno"));
      empleado.setApellidopaterno(rs.getString("apellidopaterno"));
      empleado.setEstatus(rs.getInt("estatus"));
      empleado.setFechahoraalta(rs.getString("fechahoraalta"));
      empleado.setFechahoramod(rs.getTimestamp("fechahoramod"));
      empleado.setFechanacimiento(rs.getString("fechanacimiento"));
      empleado.setIdempleadoalta(rs.getLong("idempleadoalta"));
      empleado.setIdempleadomod(rs.getLong("idempleadomod"));
      empleado.setNombre(rs.getString("nombre"));
      empleado.setNumeronomina(rs.getString("numeronomina"));
      empleado.setRfc(rs.getString("rfc"));

      return empleado;
    }
  }
}
