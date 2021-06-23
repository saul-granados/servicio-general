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
import org.springframework.stereotype.Repository;
import com.cominvi.app.commons.general.Usuarios;

@Repository
public class JdbcUsuariosRepository extends JdbcGlobalRepository {;

  private final String tabla = "general.dbo.usuarios";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(idempleado,bloqueado,correo,estatus,fechahoraalta,fechahorabloqueo,fechahoramod,fechahoraultimoinicio,fechapassword,idempleadoalta,idempleadomod,intentos,password,passwordcorreo) "
          + "values (?,?,?,?,getdate(),?,getdate(),?,?,?,?,?,?,?)";

  public List<Usuarios> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new UsuariosRowMapper());
  }

  public Usuarios findById(Long idempleado) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idempleado=" + idempleado, new UsuariosRowMapper());
  }

  public Usuarios save(final Usuarios usuario) {

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, usuario.getIdempleado());
            ps.setObject(2, usuario.getBloqueado());
            ps.setObject(3, usuario.getCorreo());
            ps.setObject(4, usuario.getEstatus());
            ps.setObject(
                5,
                usuario.getFechahorabloqueo() != null
                    ? new java.sql.Timestamp(usuario.getFechahorabloqueo().getTime())
                    : null);
            ps.setObject(
                6,
                usuario.getFechahoraultimoinicio() != null
                    ? new java.sql.Timestamp(usuario.getFechahoraultimoinicio().getTime())
                    : null);
            ps.setObject(7, usuario.getFechapassword());
            ps.setObject(8, usuario.getIdempleadoalta());
            ps.setObject(9, usuario.getIdempleadomod());
            ps.setObject(10, usuario.getIntentos());
            ps.setObject(11, usuario.getPassword());
            ps.setObject(12, usuario.getPasswordcorreo());

            return ps;
          }
        });

    return usuario;
  }

  public Boolean saveAll(final List<Usuarios> usuarios) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Usuarios usuario = usuarios.get(i);
            ps.setObject(1, usuario.getIdempleado());
            ps.setObject(2, usuario.getBloqueado());
            ps.setObject(3, usuario.getCorreo());
            ps.setObject(4, usuario.getEstatus());
            ps.setObject(
                5,
                usuario.getFechahorabloqueo() != null
                    ? new java.sql.Timestamp(usuario.getFechahorabloqueo().getTime())
                    : null);
            ps.setObject(
                6,
                usuario.getFechahoraultimoinicio() != null
                    ? new java.sql.Timestamp(usuario.getFechahoraultimoinicio().getTime())
                    : null);
            ps.setObject(7, usuario.getFechapassword());
            ps.setObject(8, usuario.getIdempleadoalta());
            ps.setObject(9, usuario.getIdempleadomod());
            ps.setObject(10, usuario.getIntentos());
            ps.setObject(11, usuario.getPassword());
            ps.setObject(12, usuario.getPasswordcorreo());
          }

          @Override
          public int getBatchSize() {
            return usuarios.size();
          }
        });

    return true;
  }

  public Usuarios update(final Usuarios usuario, Long idempleado) {
    String sql =
        "UPDATE "
            + tabla
            + " SET bloqueado = ?,correo = ?,estatus = ?,fechahorabloqueo = ?,fechahoramod = getdate(),fechahoraultimoinicio = ?,fechapassword = ?,idempleadomod = ?,intentos = ?,password = ?,passwordcorreo = ?  WHERE idempleado= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          usuario.getBloqueado(),
          usuario.getCorreo(),
          usuario.getEstatus(),
          usuario.getFechahorabloqueo(),
          usuario.getFechahoraultimoinicio(),
          usuario.getFechapassword(),
          usuario.getIdempleadomod(),
          usuario.getIntentos(),
          usuario.getPassword(),
          usuario.getPasswordcorreo(),
          usuario.getIdempleado()
        });

    return usuario;
  }

  public Boolean removeById(Long idempleado) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idempleado= ?";
    jdbcTemplate.update(delete, new Object[] {idempleado});
    return true;
  }

  public static class UsuariosRowMapper implements RowMapper<Usuarios> {

    @Override
    public Usuarios mapRow(ResultSet rs, int rowNum) throws SQLException {

      Usuarios usuario = new Usuarios();
      usuario.setIdempleado(rs.getLong("idempleado"));
      usuario.setBloqueado(rs.getByte("bloqueado"));
      usuario.setCorreo(rs.getString("correo"));
      usuario.setEstatus(rs.getByte("estatus"));
      usuario.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      usuario.setFechahorabloqueo(rs.getTimestamp("fechahorabloqueo"));
      usuario.setFechahoramod(rs.getTimestamp("fechahoramod"));
      usuario.setFechahoraultimoinicio(rs.getTimestamp("fechahoraultimoinicio"));
      usuario.setFechapassword(rs.getDate("fechapassword"));
      usuario.setIdempleadoalta(rs.getLong("idempleadoalta"));
      usuario.setIdempleadomod(rs.getLong("idempleadomod"));
      usuario.setIntentos(rs.getShort("intentos"));
      usuario.setPassword(rs.getString("password"));
      usuario.setPasswordcorreo(rs.getString("passwordcorreo"));

      return usuario;
    }
  }
}
