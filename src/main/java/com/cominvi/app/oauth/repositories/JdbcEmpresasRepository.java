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
import com.cominvi.app.commons.general.Empresas;

@Repository
public class JdbcEmpresasRepository extends JdbcGlobalRepository {

  private final String tabla = "general.dbo.empresas";

  private final String insert =
      "INSERT INTO "
          + tabla
          + " "
          + "(contpaq,direccion,direccionfactura,emisora,estatus,fechahoraalta,fechahoramod,foliofirebird,idempleadoalta,idempleadomod,linkedserver,logofull,logomin,orden,razonsocial,registropatronal,rfc) "
          + "values (?,?,?,?,?,getdate(),getdate(),?,?,?,?,?,?,?,?,?,?)";

  public List<Empresas> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM " + tabla + " where estatus > 0", new EmpresasRowMapper());
  }

  public Empresas findById(Long idempresa) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM " + tabla + " WHERE idempresa=" + idempresa, new EmpresasRowMapper());
  }

  public Empresas save(final Empresas empresa) {

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        new PreparedStatementCreator() {
          @Override
          public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, empresa.getContpaq());
            ps.setObject(2, empresa.getDireccion());
            ps.setObject(3, empresa.getDireccionfactura());
            ps.setObject(4, empresa.getEmisora());
            ps.setObject(5, empresa.getEstatus());
            ps.setObject(6, empresa.getFoliofirebird());
            ps.setObject(7, empresa.getIdempleadoalta());
            ps.setObject(8, empresa.getIdempleadomod());
            ps.setObject(9, empresa.getLinkedserver());
            ps.setObject(10, empresa.getLogofull());
            ps.setObject(11, empresa.getLogomin());
            ps.setObject(12, empresa.getOrden());
            ps.setObject(13, empresa.getRazonsocial());
            ps.setObject(14, empresa.getRegistropatronal());
            ps.setObject(15, empresa.getRfc());

            return ps;
          }
        },
        keyHolder);

    empresa.setIdempresa(keyHolder.getKey().longValue());

    return empresa;
  }

  public Boolean saveAll(final List<Empresas> empresas) {

    jdbcTemplate.batchUpdate(
        insert,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            Empresas empresa = empresas.get(i);
            ps.setObject(1, empresa.getContpaq());
            ps.setObject(2, empresa.getDireccion());
            ps.setObject(3, empresa.getDireccionfactura());
            ps.setObject(4, empresa.getEmisora());
            ps.setObject(5, empresa.getEstatus());
            ps.setObject(6, empresa.getFoliofirebird());
            ps.setObject(7, empresa.getIdempleadoalta());
            ps.setObject(8, empresa.getIdempleadomod());
            ps.setObject(9, empresa.getLinkedserver());
            ps.setObject(10, empresa.getLogofull());
            ps.setObject(11, empresa.getLogomin());
            ps.setObject(12, empresa.getOrden());
            ps.setObject(13, empresa.getRazonsocial());
            ps.setObject(14, empresa.getRegistropatronal());
            ps.setObject(15, empresa.getRfc());
          }

          @Override
          public int getBatchSize() {
            return empresas.size();
          }
        });

    return true;
  }

  public Empresas update(final Empresas empresa, Long idempresa) {
    String sql =
        "UPDATE "
            + tabla
            + " SET contpaq = ?,direccion = ?,direccionfactura = ?,emisora = ?,estatus = ?,fechahoramod = getdate(),foliofirebird = ?,idempleadomod = ?,linkedserver = ?,logofull = ?,logomin = ?,orden = ?,razonsocial = ?,registropatronal = ?,rfc = ?  WHERE idempresa= ?";

    jdbcTemplate.update(
        sql,
        new Object[] {
          empresa.getContpaq(),
          empresa.getDireccion(),
          empresa.getDireccionfactura(),
          empresa.getEmisora(),
          empresa.getEstatus(),
          empresa.getFoliofirebird(),
          empresa.getIdempleadomod(),
          empresa.getLinkedserver(),
          empresa.getLogofull(),
          empresa.getLogomin(),
          empresa.getOrden(),
          empresa.getRazonsocial(),
          empresa.getRegistropatronal(),
          empresa.getRfc(),
          empresa.getIdempresa()
        });

    return empresa;
  }

  public Boolean removeById(Long idempresa) {
    String delete = "UPDATE " + tabla + " set estatus = 0 WHERE idempresa= ?";
    jdbcTemplate.update(delete, new Object[] {idempresa});
    return true;
  }

  public static class EmpresasRowMapper implements RowMapper<Empresas> {

    @Override
    public Empresas mapRow(ResultSet rs, int rowNum) throws SQLException {

      Empresas empresa = new Empresas();
      empresa.setIdempresa(rs.getLong("idempresa"));
      empresa.setContpaq(rs.getString("contpaq"));
      empresa.setDireccion(rs.getString("direccion"));
      empresa.setDireccionfactura(rs.getString("direccionfactura"));
      empresa.setEmisora(rs.getInt("emisora"));
      empresa.setEstatus(rs.getInt("estatus"));
      empresa.setFechahoraalta(rs.getTimestamp("fechahoraalta"));
      empresa.setFechahoramod(rs.getTimestamp("fechahoramod"));
      empresa.setFoliofirebird(rs.getInt("foliofirebird"));
      empresa.setIdempleadoalta(rs.getLong("idempleadoalta"));
      empresa.setIdempleadomod(rs.getLong("idempleadomod"));
      empresa.setLinkedserver(rs.getString("linkedserver"));
      empresa.setLogofull(rs.getString("logofull"));
      empresa.setLogomin(rs.getString("logomin"));
      empresa.setOrden(rs.getInt("orden"));
      empresa.setRazonsocial(rs.getString("razonsocial"));
      empresa.setRegistropatronal(rs.getString("registropatronal"));
      empresa.setRfc(rs.getString("rfc"));

      return empresa;
    }
  }
}
