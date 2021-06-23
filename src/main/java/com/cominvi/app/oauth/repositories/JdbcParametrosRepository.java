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
import com.cominvi.app.commons.general.Parametros;

@Repository
public class JdbcParametrosRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.parametros";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(idparametro,fechahoramod,idempleadomod,nombre,valor) "
          + "values (?,getdate(),?,?,?)";

  public List<Parametros> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new ParametrosRowMapper());
  }

  public Parametros findById(Long idparametro) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idparametro=" + idparametro, new ParametrosRowMapper());
  }

  public Parametros save(final Parametros parametro) {

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, parametro.getIdparametro());
            ps.setObject(2, parametro.getIdempleadomod());
            ps.setObject(3, parametro.getNombre());
            ps.setObject(4, parametro.getValor());

            return ps;
          }
        });

    return parametro;
  }

  public Boolean saveAll(final List<Parametros> parametros) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Parametros parametro = parametros.get(i);
            ps.setObject(1, parametro.getIdparametro());
            ps.setObject(2, parametro.getIdempleadomod());
            ps.setObject(3, parametro.getNombre());
            ps.setObject(4, parametro.getValor());
          }

          @Override
          public int getBatchSize() {
            return parametros.size();
          }
        });

    return true;
  }

  public Parametros update(final Parametros parametro, Long idparametro) {
    String sql =
        "UPDATE "
            + tabla
            + " SET fechahoramod = getdate(),idempleadomod = ?,nombre = ?,valor = ?  WHERE idparametro= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          parametro.getIdempleadomod(),
          parametro.getNombre(),
          parametro.getValor(),
          parametro.getIdparametro()
        });

    return parametro;
  }

  public Boolean removeById(Long idparametro) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idparametro= ?";
    jdbcTemplate.update(delete, new Object[] {idparametro});
    return true;
  }

  private static class ParametrosRowMapper implements RowMapper<Parametros> {

    @Override
    public Parametros mapRow(ResultSet rs, int rowNum) throws SQLException {

      Parametros parametro = new Parametros();
      parametro.setIdparametro(rs.getLong("idparametro"));
      parametro.setFechahoramod(rs.getTimestamp("fechahoramod"));
      parametro.setIdempleadomod(rs.getLong("idempleadomod"));
      parametro.setNombre(rs.getString("nombre"));
      parametro.setValor(rs.getString("valor"));

      return parametro;
    }
  }
}
