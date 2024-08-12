package com.integrosys.cms.batch.sibs.stp;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Oct 2, 2008
 * Time: 11:25:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpReconcileRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        OBStpReconcile stpReconcile = new OBStpReconcile();
        stpReconcile.setRecordType("D");
        stpReconcile.setTrxUID(rs.getString(1));
        stpReconcile.setFiller("  ");
        return stpReconcile;
    }

}

