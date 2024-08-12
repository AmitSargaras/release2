/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/CheckerApproveCreateDDNOperation.java,v 1.4 2005/06/08 06:33:54 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.IDDN;

/**
 * This operation allows a checker to approve a ddn create
 * 
 * @author $Author: htli $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/06/08 06:33:54 $ Tag: $Name: $
 */
public class CheckerApproveCreateDDNOperation extends AbstractDDNTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDDNTrxValue trxValue = getDDNTrxValue(anITrxValue);
		trxValue = createActualDDN(trxValue);
		trxValue = updateDDNTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return IDDNTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IDDNTrxValue createActualDDN(IDDNTrxValue anIDDNTrxValue) throws TrxOperationException {
		try {
			IDDN ddn = anIDDNTrxValue.getStagingDDN();
			ddn.setDateGenerated(DateUtil.getDate());
			if ((ddn.getCreditOfficerName() != null) && (ddn.getCreditOfficerName().trim().length() > 0)) {
				ddn.setCreditOfficerDt(DateUtil.getDate());
			}
			if ((ddn.getSeniorOfficerName() != null) && (ddn.getSeniorOfficerName().trim().length() > 0)) {
				ddn.setSeniorOfficerDt(DateUtil.getDate());
			}
			IDDN actualDDN = getSBDDNBusManager().createDDN(ddn);
			anIDDNTrxValue.setDDN(actualDDN);
			anIDDNTrxValue.setReferenceID(String.valueOf(actualDDN.getDDNID()));
			return anIDDNTrxValue;
		}
		catch (DDNException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualDDN(): " + ex.toString());
		}
	}
}