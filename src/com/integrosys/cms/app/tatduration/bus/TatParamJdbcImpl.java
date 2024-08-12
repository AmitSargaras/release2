package com.integrosys.cms.app.tatduration.bus;

import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxSecSubType;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cynthia
 * @since Sep 1, 2008
 */
public class TatParamJdbcImpl extends JdbcDaoSupport implements ITatParamJdbc 
{
	private static final String SELECT_TAT_PARAM_TRX =
    	"SELECT * from CMS_TAT_PARAM tat, TRANSACTION trx WHERE tat.TAT_PARAM_ID = trx.REFERENCE_ID " +
			"AND trx.STATUS NOT IN ('CLOSED','DELETED') AND trx.TRANSACTION_TYPE = '"+ ITatParamConstant.INSTANCE_TAT_DURATION +"'";

	private static final String SELECT_TAT_PARAM_ITEM = "SELECT T.TAT_PARAM_ITEM_ID FROM CMS_TAT_PARAM_ITEM T " +
	"WHERE T.STATUS = ? AND T.TAT_PARAM_ID = ?";
	
	
	public List getAllTatParamList()
    {
        List resultList = getJdbcTemplate().query(SELECT_TAT_PARAM_TRX, new TatParamRowMapper());

        for(Iterator iterator = resultList.iterator(); iterator.hasNext();)
        {
            OBTatParam tatParam = (OBTatParam) iterator.next();
            List childResultList = getJdbcTemplate().query(SELECT_TAT_PARAM_ITEM, 
            	new Object[] {ICMSConstant.STATE_ACTIVE, Long.toString(tatParam.getTatParamId())},
            	new TatParamItemRowMapper());
            Set set = new HashSet();
            set.addAll(childResultList);
            tatParam.setTatParamItemList(set);
        }

        return resultList;
    }

	public String getTrxValueByLimitProfileID(long limitProfileID) {

		String query = "SELECT TRANSACTION_ID FROM TRANSACTION WHERE STATUS <> 'CLOSED' "
				+ "AND TRANSACTION_TYPE = 'TAT_DOC' AND LIMIT_PROFILE_ID = ?";

		String result = (String) getJdbcTemplate().query(query, new Object[] { new Long(limitProfileID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("TRANSACTION_ID");
						}

						return null;
					}

				});

		return result;
	}

	public void insertOrRemovePendingPerfectionCreditFolder(ITatParam tatParam,	ILimitProfile limitProfile) 
	{
		// TODO Auto-generated method stub
	}

	private class TatParamRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
            OBTatParam tatParam = new OBTatParam();
            tatParam.setApplicationType(rs.getString("APPLICATION_TYPE"));
            tatParam.setTatParamId(rs.getLong("TAT_PARAM_ID"));
            tatParam.setVersionTime(rs.getLong("VERSION_TIME"));

			return tatParam;
		}
	}
	
	private class TatParamItemRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
            OBTatParamItem tatParamItem = new OBTatParamItem();
            tatParamItem.setTatParamItemId(rs.getLong("TAT_PARAM_ITEM_ID"));

			return tatParamItem;
		}
	}
}
