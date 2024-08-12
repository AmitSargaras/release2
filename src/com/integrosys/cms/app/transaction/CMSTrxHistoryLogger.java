/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTrxHistoryLogger.java,v 1.9 2004/06/28 09:53:07 jhe Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.OBTrxHistoryValue;
import com.integrosys.base.businfra.transaction.TrxLogException;
import com.integrosys.component.common.transaction.ITrxHistoryLogger;

/**
 * This class provides a method that facilitates mapping of ITrxValue into an
 * OBTrxHistory object for trx logging purposes.
 * 
 * @author Alfred Lee
 * @version $Revision: 1.9 $
 * @since $Date: 2004/06/28 09:53:07 $ Tag: $Name: $
 */
public class CMSTrxHistoryLogger implements ITrxHistoryLogger {
	/**
	 * Logs the transaction into the transaction log
	 * @param result is of type ITrxResult
	 * @return OBTrxHistoryValue
	 * @throws com.integrosys.base.businfra.transaction.TrxLogException on error
	 */
	public OBTrxHistoryValue logProcess(ITrxResult result) throws TrxLogException {
		ICMSTrxValue value = (ICMSTrxValue) result.getTrxValue();

		OBCMSTrxHistoryValue history = new OBCMSTrxHistoryValue(value.getTransactionID(), value.getTransactionDate());
		history.setTransactionHistoryID(value.getCurrentTrxHistoryID());
		history.setFromUser(String.valueOf(value.getUID()), String.valueOf(value.getTeamID()));
		history.setOperationDescField(value.getOpDesc());
		history.setFromState(value.getFromState());
		history.setToState(value.getToState());
		history.setComment(value.getRemarks());
		history.setReferenceID(value.getReferenceID());
		history.setStagingReferenceID(value.getStagingReferenceID());
		history.setStatus(value.getToState());
		history.setCreateDate(value.getCreateDate());
		history.setTransactionType(value.getTransactionType());
		history.setTrxReferenceID(value.getTrxReferenceID());
		history.setLegalName(value.getLegalName());
		history.setTeamTypeID(value.getTeamTypeID());
		history.setCustomerName(value.getCustomerName());
		history.setLegalID(value.getLegalID());
		history.setCustomerID(value.getCustomerID());
		history.setOriginatingCountry(value.getOriginatingCountry());
		history.setOriginatingOrganisation(value.getOriginatingOrganisation());
		history.setLimitProfileID(value.getLimitProfileID());
		history.setLimitProfileReferenceNumber(value.getLimitProfileReferenceNumber());
		history.setTransactionSubType(value.getTransactionSubType());
		history.setTeamMembershipID(value.getTeamMembershipID());
		history.setLoginId(value.getLoginId());
		history.setMinEmployeeGrade(value.getMinEmployeeGrade());
		

		long uid = value.getToUserId();
		String suid = (new Long(uid)).toString();
		long id = value.getToAuthGId();
		String sid = (new Long(id)).toString();
		history.setToUser(suid, sid);

		if (value.getTrxContext() != null) {
			if (value.getTrxContext().getCustomer() != null) {
				if (value.getTrxContext().getCustomer().getCMSLegalEntity() != null) {
					String customerSegment = value.getTrxContext().getCustomer().getCMSLegalEntity()
							.getCustomerSegment();

					history.setSegment(customerSegment);
				}
			}
		}
		return history;
	}
}
