package com.integrosys.cms.app.workspace;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.workflow.ITaskListDAO;
import com.integrosys.base.businfra.workflow.ITaskListSearchCriteria;
import com.integrosys.base.businfra.workflow.WorkflowCredentials;
import com.integrosys.base.businfra.workflow.WorkflowException;
import com.integrosys.base.businfra.workflow.WorkflowManager;
import com.integrosys.base.businfra.workflow.WorkflowUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.proxy.BizStructureProxyFactory;

public abstract class AbstractTaskListDAO implements ITaskListDAO {

	/**
	 * Returns Collection of ITask
	 * 
	 * @param searchCriteria
	 * @return Logical View::java::util::Collections
	 * @roseuid 3F0916A401AF
	 */
	public abstract SearchResult getTaskList(ITaskListSearchCriteria searchCriteria);

	public Collection getPendingTaskTxns(ITaskListSearchCriteria searchCriteria) {
		WorkflowManager manager = new WorkflowManager();
		ITeam team = null;
		try {
			team = BizStructureProxyFactory.getProxy().getTeam(Long.parseLong(searchCriteria.getGroupID()));
		}
		catch (Throwable enfe) {
			throw new RuntimeException("Team not found !");
		}
		long teamTypeId = team.getTeamType().getTeamTypeID();
		// credentials.setUserId(searchCriteria.getUserID());
		// credentials.setGroupId(searchCriteria.getGroupID());
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
		WorkflowCredentials credentials = new WorkflowCredentials();
		String actorId = new Long(teamTypeId).toString() + "|" + new Long(memTypeId).toString();
		credentials.setUserId(actorId);

		Collection flowIdList = null;
		try {
			flowIdList = manager.getPendingTaskList(new Long(searchCriteria.getProcessID()), credentials);
		}
		catch (WorkflowException wfe) {
			throw new RuntimeException("WorkflowException during getPendingTaskList ");
		}

		// country get from team...if more than one form IN clause
		// team.getCountryCodes()[0]
		// team.getSegmentCodes()[0]
		String countryCode = "";
		String segmentCode = "8";
		// segment get from team...if more than one form IN clause

		// get transaction ids from flowids
		Vector txnList = new Vector();

		Iterator itr = flowIdList.iterator();
		while (itr.hasNext()) {
			Long flowId = (Long) itr.next();
			DefaultLogger.debug(this, "Retrieved flowid ------" + flowId.longValue());
			// getTransactionId(long flowId, long teamTypeId, long
			// membershipType, String countryCode, String segmentCode) {
			// Long txnId =
			// WorkflowUtil.getTransactionId(Long.parseLong(flowId), teamTypeId,
			// memTypeId, countryCode, segmentCode);
			Long txnId = WorkflowUtil.getTransactionId(flowId.longValue(), countryCode, segmentCode);
			DefaultLogger.debug(this, "Retrieved txnId ------" + txnId);
			if (txnId != null) {
				txnList.add(txnId);
			}
		}
		return txnList;
		// now prepare the object specific to workspace
	}

}
