/**
 * 
 */
package com.integrosys.cms.asst.validator;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;

/**
 * @author janki.mandalia
 * 
 */
public class IsacUserDaoImpl {

	String getUserStatus(long userId) {
		String query="SELECT IS_UNLOCK,STATUS FROM CMS_USER WHERE USER_ID="+userId+"";
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			String status="";
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
			//	String isUnlock=rs.getString("IS_UNLOCK");
				 status=rs.getString("STATUS");
				//return status;
			}
			return status;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
	

}
