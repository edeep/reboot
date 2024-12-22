package com.pradeep.reboot.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pradeep.reboot.entity.RebootData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RebootDataRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class RebootDataRowMapper implements RowMapper<RebootData> {
        @Override
        public RebootData mapRow(ResultSet rs, int rowNum) throws SQLException {
        	RebootData rebootData = new RebootData();
        	rebootData.setId(rs.getInt("id"));
        	rebootData.setSerialNo(rs.getString("serialNo"));
            
            return rebootData;
        }
    }

    public int save(RebootData rebootData) {
        return jdbcTemplate.update("INSERT INTO REBOOT_DATA (ID, SERIAL_NUMBER) VALUES (REBOOT_DATA_SEQ.NEXTVAL, ?)", 
        		rebootData.getSerialNo());
    }

    public List<RebootData> findAll() {
        return jdbcTemplate.query("SELECT * FROM REBOOT_DATA", new RebootDataRowMapper());
    }

    public RebootData findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM REBOOT_DATA_SEQ WHERE ID = ?", new RebootDataRowMapper(), id);
    }

    public int update(RebootData rebootData) {
        return jdbcTemplate.update("UPDATE REBOOT_DATA SET STATUS = ? WHERE ID = ?", 
                 rebootData.getStatus(), rebootData.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM REBOOT_DATA WHERE ID = ?", id);
    }
}
