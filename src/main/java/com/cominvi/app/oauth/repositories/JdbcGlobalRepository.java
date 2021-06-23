package com.cominvi.app.oauth.repositories;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcGlobalRepository<E> {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public List<E> regresaLista(String sql, RowMapper<E> rowMapper) {
		return jdbcTemplate.query(sql, rowMapper);
	}

	public E regresaObjeto(String sql, RowMapper<E> rowMapper) {
		return jdbcTemplate.queryForObject(sql, rowMapper);
	}

	public void ejecutaInstruccion(String sql) {
    	jdbcTemplate.update(sql);
    }


}
