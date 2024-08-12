package com.integrosys.cms.app.workspace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.integrosys.base.businfra.workflow.ITaskActionConstructor;
import com.integrosys.base.businfra.workflow.OBTaskAction;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.StringTokenizerUtil;

public class CustodianActionConstuctor implements ITaskActionConstructor {

	/**
	 * @param taskAttribute
	 * @return ITaskAction (Logical
	 *         View::com::integrosys::base::businfra::workflow::ITaskAction)
	 * @roseuid 3F091F0001E8
	 */
	public Collection getAction(String txnType, String status, long teamTypeId, long membershipTypeId) {
		DBUtil dbUtil = null;
		try {
			DefaultLogger.debug(this, "Obtained TxnType - " + txnType);
			DefaultLogger.debug(this, "Obtained status - " + status);
			DefaultLogger.debug(this, "Obtained teamTypeId - " + teamTypeId);
			DefaultLogger.debug(this, "Obtained membershipTypeId - " + membershipTypeId);
			dbUtil = new DBUtil();
			String sql = "select * from cms_task_actions where txnType = ? and status = ? and teamTypeId = ? and membershipTypeId = ? and wf_indicator = 'CUST_CHECKER_TODO' ";
			dbUtil.setSQL(sql);
			dbUtil.setString(1, txnType);
			dbUtil.setString(2, status);
			dbUtil.setLong(3, teamTypeId);
			dbUtil.setLong(4, membershipTypeId);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			String actionListStr = rs.getString("action_list");
			String actionLabelStr = rs.getString("action_labels");
			String urlListStr = rs.getString("url_list");

			ArrayList al = new ArrayList();

			Vector actionList = new Vector();
			Vector actionLabel = new Vector();
			Vector urlList = new Vector();

			StringTokenizerUtil tokenUtil = new StringTokenizerUtil(actionListStr, "|");
			while (tokenUtil.hasMoreTokens()) {
				actionList.add(tokenUtil.nextToken());
			}
			StringTokenizerUtil tokUtil = new StringTokenizerUtil(actionLabelStr, "|");
			while (tokUtil.hasMoreTokens()) {
				actionLabel.add(tokUtil.nextToken());
			}
			StringTokenizerUtil tknUtil = new StringTokenizerUtil(urlListStr, "|");
			while (tknUtil.hasMoreTokens()) {
				urlList.add(tknUtil.nextToken());
			}
			// static data should be correct -- list length dhld be same for all
			for (int i = 0; i < actionList.size(); i++) {
				OBTaskAction tAction = new OBTaskAction((String) actionList.elementAt(i), (String) actionLabel
						.elementAt(i), (String) urlList.elementAt(i), new Long(i));
				al.add(tAction);
			}
			return al;
			// testing
			// return null;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("SQLException in getAction");
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Exception in getAction");
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new RuntimeException("SQLException in getAction");
			}
		}
	}

}
