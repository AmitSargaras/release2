package com.integrosys.cms.app.workspace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.workflow.ActionContructorFactory;
import com.integrosys.base.businfra.workflow.ITaskActionConstructor;
import com.integrosys.base.businfra.workflow.ITaskAttributeNameConstant;
import com.integrosys.base.businfra.workflow.ITaskListSearchCriteria;
import com.integrosys.base.businfra.workflow.ITaskSearchCriteriaAttributeNameConstant;
import com.integrosys.base.businfra.workflow.OBTask;
import com.integrosys.base.businfra.workflow.OBTaskAction;
import com.integrosys.base.businfra.workflow.OBTaskAttribute;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.proxy.BizStructureProxyFactory;

public class CustodianTaskListDAO extends AbstractTaskListDAO {

	/**
	 * Returns Collection of ITask
	 * 
	 * @param searchCriteria
	 * @return Logical View::java::util::Collections
	 * @roseuid 3F0916A401AF
	 */
	public SearchResult getTaskList(ITaskListSearchCriteria searchCriteria) {
		if (searchCriteria == null) {
			throw new RuntimeException("The ITaskListSearchCriteria is null !!!");
		}
		// String firstSort = searchCriteria.getFirstSort();
		// String secondSort = searchCriteria.getSecondSort();
		int startIndex = searchCriteria.getStartIndex();
		int nItems = searchCriteria.getNItems();
		ITeam team = null;
		try {
			team = BizStructureProxyFactory.getProxy().getTeam(Long.parseLong(searchCriteria.getGroupID()));
		}
		catch (Throwable enfe) {
			throw new RuntimeException("Team not found !");
		}
		long teamTypeId = team.getTeamType().getTeamTypeID();

		ITeamMembership membership = null;
		try {
			membership = BizStructureProxyFactory.getProxy().getTeamMembershipByUserID(
					Long.parseLong(searchCriteria.getUserID()));
		}
		catch (Throwable be) {
			be.printStackTrace();
			throw new RuntimeException("ITeamMembership not resolved !");
		}
		long memTypeId = membership.getTeamTypeMembership().getMembershipType().getMembershipTypeID();

		Collection col = getPendingTaskTxns(searchCriteria);
		if (col == null) {
			return null;
		}
		if (col.isEmpty() || (col.size() < startIndex)) {
			return null;
		}
		int sIndex = startIndex;
		Iterator itr = col.iterator();
		while ((--startIndex > 0) && itr.hasNext()) {
			itr.next();
		}
		// now form the workspace item using transaction data i.e., return
		// collection of ITask
		Vector vec = new Vector();
		int colsize = col.size();
		int count = 0;
		if (colsize < nItems) {
			count = colsize;
		}
		else {
			count = colsize;
		}
		for (int k = 0; k < count; k++) {
			Long txnId = (Long) itr.next();
			vec.add(getTask(txnId.longValue(), teamTypeId, memTypeId));
		}

		return new SearchResult(sIndex, vec.size(), 45, vec);
	}

	private OBTask getTask(long txnId, long teamTypeId, long memTypeId) {
		OBTask task = new OBTask();
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String sql = "select * from transaction where transaction_id = ?";
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, txnId);
			ResultSet rs = dbUtil.executeQuery();
			// check the types...no prims allowed....
			rs.next();
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.LEGAL_NAME, rs
					.getString(ITaskSearchCriteriaAttributeNameConstant.LEGAL_NAME), new Long(1)));
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.CUSTOMER_NAME, rs
					.getString(ITaskSearchCriteriaAttributeNameConstant.CUSTOMER_NAME), new Long(1)));
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.LE_ID, new Long(rs
					.getLong(ITaskSearchCriteriaAttributeNameConstant.LE_ID)), new Long(2)));
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.SUB_PROFILE_ID, new Long(rs
					.getLong(ITaskSearchCriteriaAttributeNameConstant.SUB_PROFILE_ID)).toString(), new Long(2)));
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.ORIGINATING_LOCATION, rs
					.getString(ITaskSearchCriteriaAttributeNameConstant.ORIGINATING_LOCATION), new Long(3)));
			// task.addTaskAttribute(new
			// OBTaskAttribute(ITaskAttributeNameConstant
			// .FAM,rs.getString(ITaskSearchCriteriaAttributeNameConstant.FAM),
			// new Long(4) ));
			String txnType = rs.getString(ITaskSearchCriteriaAttributeNameConstant.TRANSACTION_TYPE);
			task
					.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.TRANSACTION_TYPE, txnType,
							new Long(4)));
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.TRANSACTION_DATE, rs
					.getDate(ITaskSearchCriteriaAttributeNameConstant.TRANSACTION_DATE), new Long(5)));
			String status = rs.getString(ITaskSearchCriteriaAttributeNameConstant.STATUS);
			task.addTaskAttribute(new OBTaskAttribute(ITaskAttributeNameConstant.STATUS, status, new Long(6)));
			task.setTaskReferenceId(new Long(txnId).toString());
			// action constructor specific to use case

			ITaskActionConstructor actConstructor = ActionContructorFactory.getActionConstructor("CUST_CHECKER_TODO");
			Collection actionList = actConstructor.getAction(txnType, status, teamTypeId, memTypeId);
			task.setTaskActionList((OBTaskAction[]) actionList.toArray(new OBTaskAction[actionList.size()]));
			return task;
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("SQLException in getTaskList");
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Exception in getTaskList");
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new RuntimeException("SQLException in getTaskList");
			}
		}

	}

}
