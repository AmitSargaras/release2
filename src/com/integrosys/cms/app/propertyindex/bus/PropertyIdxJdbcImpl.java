/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockExchangeDAO.java,v 1.2 2003/09/25 09:30:35 btchng Exp $
 */
package com.integrosys.cms.app.propertyindex.bus;

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
 * User: Andy Wong
 * Date: Aug 25, 2008
 * Time: 11:32:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class PropertyIdxJdbcImpl extends JdbcDaoSupport implements IPropertyIdxJdbc {

	private static final String SELECT_PROIDX_SEC_SUBTYPE = "SELECT T.SECURITY_SUB_TYPE_ID FROM CMS_PROPERTY_IDX_SEC_SUBTYPE T " +
			"WHERE T.STATUS = ? AND T.PROPERTY_IDX_ID = ?";

	private static final String QUERY_SELECT_SEC_SUBTYPE_AND_VAL_TYPE =
			"select count(*) from  CMS_PROPERTY_IDX p, CMS_PROPERTY_IDX_SEC_SUBTYPE s " +
			"where " +
			"p.PROPERTY_IDX_ID = s.PROPERTY_IDX_ID " +
			"and p.STATUS <> '" + ICMSConstant.STATE_DELETED + "' " +
			"and s.STATUS <> '" + ICMSConstant.STATE_DELETED + "' " +
			"and p.VAL_DESCR = ? AND p.PROPERTY_IDX_ID <> ?";

    private static final String SELECT_PROIDX_TRX =
    	"SELECT * from CMS_PROPERTY_IDX param, TRANSACTION trx WHERE param.PROPERTY_IDX_ID = trx.REFERENCE_ID " +
			"AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = '"+ ICMSConstant.INSTANCE_PROPERTY_IDX +"'";

    /**
     * Method to check if security subtype and valuation desc combination exist
     * @param propertyIndexID
     * @param secSubTypeList
     * @param valDesc
     * @return
     * @throws SearchDAOException
     */
    public boolean isSecSubTypeValTypeExist (long propertyIndexID, Set secSubTypeList, String valDesc) {
		ResultSet rs = null;
        StringBuffer strBuf = new StringBuffer();
		StringBuffer andCondition = new StringBuffer();
		strBuf.append( QUERY_SELECT_SEC_SUBTYPE_AND_VAL_TYPE );
		andCondition.append(" AND s.SECURITY_SUB_TYPE_ID ");

		ArrayList params = new ArrayList();
		ArrayList secSubTypeIDList = new ArrayList();
		if( secSubTypeList != null ) {
			for (Iterator iterator = secSubTypeList.iterator(); iterator.hasNext();) {
				IPropertyIdxSecSubType dtl = (IPropertyIdxSecSubType) iterator.next();
				secSubTypeIDList.add( dtl.getSecuritySubTypeId() );
			}
		}
		params.add(valDesc);
        params.add(new Long(propertyIndexID));

        CommonUtil.buildSQLInList(secSubTypeIDList, andCondition, params);
        strBuf.append(andCondition.toString());

        int count = getJdbcTemplate().queryForInt(strBuf.toString(), params.toArray(new Object[0]));
        if(count > 0)
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieve valid property index records
     * @return
     */
    public List getAllPropertyIdx ()
    {
        List resultList = getJdbcTemplate().query(SELECT_PROIDX_TRX, new PropertyIdxRowMapper());

        for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
            OBPropertyIdx propertyIdx = (OBPropertyIdx) iterator.next();
            List childResultList = getJdbcTemplate().query(SELECT_PROIDX_SEC_SUBTYPE, new Object[] {ICMSConstant.STATE_ACTIVE, Long.toString(propertyIdx.getPropertyIdxId())},new PropertyIdxSecSubTypeRowMapper());
            Set set = new HashSet();
            set.addAll(childResultList);
            propertyIdx.setPropertyIdxSecSubTypeList(set);
        }

        return resultList;
    }

    private class PropertyIdxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            OBPropertyIdx propertyIdx = new OBPropertyIdx();
            propertyIdx.setCountryCode(rs.getString("COUNTRY_CODE"));
            propertyIdx.setPropertyIdxId(rs.getLong("PROPERTY_IDX_ID"));
            propertyIdx.setStatus(rs.getString("STATUS"));
            propertyIdx.setValDescr(rs.getString("VAL_DESCR"));
            propertyIdx.setVersionTime(rs.getLong("VERSION_TIME"));

			return propertyIdx;
		}
	}

    private class PropertyIdxSecSubTypeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            OBPropertyIdxSecSubType propertyIdxSecSubType = new OBPropertyIdxSecSubType();
            propertyIdxSecSubType.setSecuritySubTypeId(rs.getString("SECURITY_SUB_TYPE_ID"));

			return propertyIdxSecSubType;
		}
	}
}