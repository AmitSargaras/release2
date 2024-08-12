package com.integrosys.cms.ui.todo;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

public class PrepareNewLimitProfileCommand extends PrepareTodoCommand {

	/**
	 * Default Constructor
	 */
	public PrepareNewLimitProfileCommand() {

	}

	protected CMSTrxSearchCriteria prepareSearchCriteria(CMSTrxSearchCriteria criteria, String event) {

		criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
		String[] trxx = { ICMSConstant.INSTANCE_LIMIT_PROFILE };
		criteria.setTransactionTypes(trxx);
		criteria.setCurrentState(ICMSConstant.STATE_NEW);

		if ("newborrower".equals(event)) {
			criteria.setCurrentState(true);
			criteria.setCustomerType(ICMSConstant.CUSTOMER_TYPE_BORROWER);
		}
		else if ("newnonborrower".equals(event)) {
			criteria.setCurrentState(true);
			criteria.setCustomerType(ICMSConstant.CUSTOMER_TYPE_NONBORROWER);
		}
		else if (event.equals("newlimitprofile")) {
			criteria.setCurrentState(true);
		}
		else {
			criteria.setCurrentState(false);
		}
		criteria.setOnlyMembershipRecords(false);
		return criteria;
	}

	protected SearchResult getSearchResult(CMSTrxSearchCriteria criteria) throws TrxOperationException,
			SearchDAOException, RemoteException {
		return getWorkflowManager().searchWorkflowTransactions(criteria);
	}

}
