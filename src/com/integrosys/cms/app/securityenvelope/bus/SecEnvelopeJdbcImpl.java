/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.securityenvelope.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.integrosys.cms.app.feed.bus.stock.IStockExchangeJdbc;
import com.integrosys.cms.app.feed.bus.stock.IStockExchange;
import com.integrosys.cms.app.feed.bus.stock.OBStockExchange;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Erene Wong
 * Date: Jan 25, 2010
 * Time: 11:01:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class SecEnvelopeJdbcImpl extends JdbcDaoSupport implements ISecEnvelopeJdbc {
    private String lspLmtProfleId;

   private static final String SELECT_ALL_SECENV_TRX =
    	"SELECT * from CMS_SEC_ENVELOPE param, TRANSACTION trx WHERE param.SEC_ENVELOPE_ID = trx.REFERENCE_ID " +
  		"AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = '"+ ICMSConstant.INSTANCE_SECURITY_ENVELOPE +"' ";

   private static final String SELECT_ALL_SECENVITM_STAGING =
        "SELECT * FROM " +
        "CMS_STAGE_SEC_ENVELOPE_ITEM WHERE SEC_ENVELOPE_ID IN " +
        "(SELECT MAX(SEC_ENVELOPE_ID) FROM CMS_STAGE_SEC_ENVELOPE GROUP BY SEC_LSP_LMT_PROFILE_ID) " +
        "ORDER BY SEC_ENVELOPE_ITEM_BARCODE";

   private static final String SELECT_SECENV_TRX =
    	"SELECT TRANSACTION_ID from TRANSACTION trx, CMS_SEC_ENVELOPE param WHERE param.SEC_ENVELOPE_ID = trx.REFERENCE_ID " +
  		"AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = '"+ ICMSConstant.INSTANCE_SECURITY_ENVELOPE +"' " +
        "and trx.LIMIT_PROFILE_ID = ? ";

    private static final String SELECT_SECENV_ITEM_TRX =
    	"SELECT paramItem.* FROM CMS_SEC_ENVELOPE param, CMS_SEC_ENVELOPE_ITEM paramItem, TRANSACTION trx WHERE param.SEC_ENVELOPE_ID = trx.REFERENCE_ID " +
  		"AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = '"+ ICMSConstant.INSTANCE_SECURITY_ENVELOPE +"' " +
        "and trx.LIMIT_PROFILE_ID = ? ";

    private static final String SELECT_DOCITEM_IN_ENVELOPE =
	     "SELECT custodian_doc_item_barcode FROM " +
		 "cms_cust_doc_item WHERE " +
		 "status not in ('"+ICMSConstant.STATE_RECEIVED+"') and " +
		 "sec_envelope_barcode is not null and " +
		 "sec_envelope_barcode = ? ";

    /**
     * Method to check if security subtype and valuation desc combination exist
     * @param propertyIndexID
     * @param secSubTypeList
     * @param valDesc
     * @return
     * @throws SearchDAOException
     */


    /**
     * Retrieve valid property index records
     * @return
     */
    public List getAllSecEnvelope ()
    {
        List resultList = getJdbcTemplate().query(SELECT_ALL_SECENV_TRX, new SecEnvelopeRowMapper());
        return resultList;
    }

    public List getAllSecEnvelopeItemStaging()
    {
        List resultList = getJdbcTemplate().query(SELECT_ALL_SECENVITM_STAGING, new SecEnvelopeItemRowMapper());
        return resultList;
    }

    public int getNumDocItemInEnvelope(String envBarcode)
    {   int totCount = 0;
        List resultList = getJdbcTemplate().query(SELECT_DOCITEM_IN_ENVELOPE, new Object[] {new String(envBarcode)}, new SecEnvelopeDocItemRowMapper());
        if (resultList != null){
            totCount = resultList.size();
        }    
        return totCount;
    }

    public List getActualSecEnvelope(long lspLmtProfId){
        List trxId = getJdbcTemplate().query(SELECT_SECENV_TRX, new Object[] {new Long(lspLmtProfId)}, new SecEnvelopeTrxIdRowMapper());
        return trxId;
    }

    public List getAllSecEnvItemLmtProf (long lspLmtProfId)
    {

        List resultListLmt = getJdbcTemplate().query(SELECT_SECENV_ITEM_TRX, new Object[] {new Long(lspLmtProfId)}, new SecEnvelopeRowMapper());
        return resultListLmt;
    }

    private class SecEnvelopeTrxIdRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            String secEnvelopeTrxValue = rs.getString("TRANSACTION_ID");
			return secEnvelopeTrxValue;
		}
	}

    private class SecEnvelopeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            OBSecEnvelope secEnvelope = new OBSecEnvelope();
            secEnvelope.setSecEnvelopeId(rs.getLong("SEC_ENVELOPE_ID"));
            secEnvelope.setSecLspLmtProfileId(rs.getLong("SEC_LSP_LMT_PROFILE_ID"));
            secEnvelope.setStatus(rs.getString("STATUS"));
            secEnvelope.setVersionTime(rs.getLong("VERSION_TIME"));

			return secEnvelope;
		}
	}

    private class SecEnvelopeItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            OBSecEnvelopeItem secEnvelopeItem = new OBSecEnvelopeItem();
            secEnvelopeItem.setSecEnvelopeItemId(rs.getLong("SEC_ENVELOPE_ITEM_ID"));
            secEnvelopeItem.setSecEnvelopeId(rs.getLong("SEC_ENVELOPE_ID"));
            secEnvelopeItem.setSecEnvelopeItemAddr(rs.getString("SEC_ENVELOPE_ITEM_ADDR"));
            secEnvelopeItem.setSecEnvelopeItemCab(rs.getString("SEC_ENVELOPE_ITEM_CAB"));
            secEnvelopeItem.setSecEnvelopeItemDrw(rs.getString("SEC_ENVELOPE_ITEM_DRW"));
            secEnvelopeItem.setSecEnvelopeItemBarcode(rs.getString("SEC_ENVELOPE_ITEM_BARCODE"));
            secEnvelopeItem.setStatus(rs.getString("STATUS"));
            secEnvelopeItem.setSecEnvelopeRefId(rs.getLong("SEC_ENVELOPE_REF_ID"));
			return secEnvelopeItem;
		}
	}

    private class SecEnvelopeDocItemRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            String docItemBarcode;
            docItemBarcode = rs.getString("custodian_doc_item_barcode");            
			return docItemBarcode;
		}
    }

}