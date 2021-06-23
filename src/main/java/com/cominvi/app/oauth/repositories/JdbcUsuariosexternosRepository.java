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
import com.cominvi.app.commons.general.Usuariosexternos;

@Repository
public class JdbcUsuariosexternosRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.usuariosexternos";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(apematerno,apepaterno,celular,claveactivacion,correo,estatus,fechahoraalta,fechahoramod,fechavencimientoclave,nombre,password,reestablecerpassword,registro) "
          + "values (?,?,?,?,?,?,getdate(),getdate(),?,?,?,?,?)";

  public List<Usuariosexternos> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new UsuariosexternosRowMapper());
  }

  public Usuariosexternos findById(Long idusuarioexterno) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idusuarioexterno=" + idusuarioexterno,
        new UsuariosexternosRowMapper());
  }

  public Usuariosexternos save(final Usuariosexternos usuariosexterno) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, usuariosexterno.getApematerno());
            ps.setObject(2, usuariosexterno.getApepaterno());
            ps.setObject(3, usuariosexterno.getCelular());
            ps.setObject(4, usuariosexterno.getClaveactivacion());
            ps.setObject(5, usuariosexterno.getCorreo());
            ps.setObject(6, usuariosexterno.getEstatus());
            ps.setObject(
                7,
                usuariosexterno.getFechavencimientoclave() != null
                    ? new java.sql.Timestamp(usuariosexterno.getFechavencimientoclave().getTime())
                    : null);
            ps.setObject(8, usuariosexterno.getNombre());
            ps.setObject(9, usuariosexterno.getPassword());
            ps.setObject(10, usuariosexterno.getReestablecerpassword());
            ps.setObject(11, usuariosexterno.getRegistro());

            return ps;
          }
        },
        keyHolder);

    usuariosexterno.setIdusuarioexterno(keyHolder.getKey().longValue());

    return usuariosexterno;
  }

  public Boolean saveAll(final List<Usuariosexternos> usuariosexternos) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Usuariosexternos usuariosexterno = usuariosexternos.get(i);
            ps.setObject(1, usuariosexterno.getApematerno());
            ps.setObject(2, usuariosexterno.getApepaterno());
            ps.setObject(3, usuariosexterno.getCelular());
            ps.setObject(4, usuariosexterno.getClaveactivacion());
            ps.setObject(5, usuariosexterno.getCorreo());
            ps.setObject(6, usuariosexterno.getEstatus());
            ps.setObject(
                7,
                usuariosexterno.getFechavencimientoclave() != null
                    ? new java.sql.Timestamp(usuariosexterno.getFechavencimientoclave().getTime())
                    : null);
            ps.setObject(8, usuariosexterno.getNombre());
            ps.setObject(9, usuariosexterno.getPassword());
            ps.setObject(10, usuariosexterno.getReestablecerpassword());
            ps.setObject(11, usuariosexterno.getRegistro());
          }

          @Override
          public int getBatchSize() {
            return usuariosexternos.size();
          }
        });

    return true;
  }

  public Usuariosexternos update(final Usuariosexternos usuariosexterno, Long idusuarioexterno) {
    String sql =
        "UPDATE "
            + tabla
            + " SET apematerno = ?,apepaterno = ?,celular = ?,claveactivacion = ?,correo = ?,estatus = ?,fechahoramod = getdate(),fechavencimientoclave = ?,nombre = ?,password = ?,reestablecerpassword = ?,registro = ?  WHERE idusuarioexterno= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          usuariosexterno.getApematerno(),
          usuariosexterno.getApepaterno(),
          usuariosexterno.getCelular(),
          usuariosexterno.getClaveactivacion(),
          usuariosexterno.getCorreo(),
          usuariosexterno.getEstatus(),
          usuariosexterno.getFechavencimientoclave(),
          usuariosexterno.getNombre(),
          usuariosexterno.getPassword(),
          usuariosexterno.getReestablecerpassword(),
          usuariosexterno.getRegistro(),
          usuariosexterno.getIdusuarioexterno()
        });

    return usuariosexterno;
  }

  public Boolean removeById(Long idusuarioexterno) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idusuarioexterno= ?";
    jdbcTemplate.update(delete, new Object[] {idusuarioexterno});
    return true;
  }

  public static class UsuariosexternosRowMapper implements RowMapper<Usuariosexternos> {

    @Override
    public Usuariosexternos mapRow(ResultSet rs, int rowNum) throws SQLException {

      Usuariosexternos usuariosexterno = new Usuariosexternos();
      usuariosexterno.setIdusuarioexterno(rs.getLong("idusuarioexterno"));
      usuariosexterno.setApematerno(rs.getString("apematerno"));
      usuariosexterno.setApepaterno(rs.getString("apepaterno"));
      usuariosexterno.setCelular(rs.getString("celular"));
      usuariosexterno.setClaveactivacion(rs.getString("claveactivacion"));
      usuariosexterno.setCorreo(rs.getString("correo"));
      usuariosexterno.setEstatus(rs.getInt("estatus"));
      usuariosexterno.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      usuariosexterno.setFechahoramod(rs.getTimestamp("fechahoramod"));
      usuariosexterno.setFechavencimientoclave(rs.getTimestamp("fechavencimientoclave"));
      usuariosexterno.setNombre(rs.getString("nombre"));
      usuariosexterno.setPassword(rs.getString("password"));
      usuariosexterno.setReestablecerpassword(rs.getInt("reestablecerpassword"));
      usuariosexterno.setRegistro(rs.getInt("registro"));

      return usuariosexterno;
    }
  }
}
