package com.integrosys.cms.batch.valuation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.dao.DataAccessException;
import com.integrosys.cms.batch.factory.BatchJob;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;

import java.util.Map;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Dec 18, 2008
 * Time: 9:55:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValuationPreparationMain implements BatchJob {

    private Logger logger = LoggerFactory.getLogger(ValuationPreparationMain.class);
    private JdbcTemplate jdbcTemplate;
    private String mktSecPrepProcedureName;


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getMktSecPrepProcedureName() {
        return mktSecPrepProcedureName;
    }

    public void setMktSecPrepProcedureName(String mktSecPrepProcedureName) {
        this.mktSecPrepProcedureName = mktSecPrepProcedureName;
    }


    public void execute(Map context) throws BatchJobException {
        executeInternal(context);
    }

    private void executeInternal(Map context) {
        logger.info("calling marketable sec valuation preparation stored procedure [" + getMktSecPrepProcedureName() + "]");

        try {
            getJdbcTemplate().execute("{call " + getMktSecPrepProcedureName() + "()}",
                    new CallableStatementCallback() {

                        public Object doInCallableStatement(CallableStatement cs) throws SQLException,
                                DataAccessException {
                            cs.executeUpdate();
                            return null;
                        }
                    });
        }
        catch (DataAccessException ex) {
            throw new IncompleteBatchJobException(
                    "Failed to finish valuation preparation batch job, possibly due to database error.", ex);
        }
    }

}
