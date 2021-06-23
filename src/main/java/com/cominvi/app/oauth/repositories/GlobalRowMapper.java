package com.cominvi.app.oauth.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalRowMapper extends JdbcGlobalRepository {

	public static class GRowMapper implements RowMapper<Map<String, Object>> {

	    @Override
	    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Map<String, Object> map = new HashMap<>();
	        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	            map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
	        }

	        return map;
	    }
	}
	
}
