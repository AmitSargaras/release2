/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/CheckerApproveUpdateDDNOperation.java,v 1.6 2006/02/03 02:03:20 czhou Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//java

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.IDDN;

/**
 * This operation allows a checker to approve a ddn update
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/02/03 02:03:20 $ Tag: $Name: $
 */
public class CheckerApproveUpdateDDNOperation extends AbstractDDNTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateDDNOperation() {
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
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IDDNTrxValue trxValue = getDDNTrxValue(anITrxValue);
		trxValue = updateActualDDN(trxValue);
		trxValue = updateDDNTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return IDDNTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IDDNTrxValue updateActualDDN(IDDNTrxValue anIDDNTrxValue) throws TrxOperationException {
		try {
			IDDN staging = anIDDNTrxValue.getStagingDDN();
			IDDN actual = anIDDNTrxValue.getDDN();
			IDDN updActual = (IDDN) CommonUtil.deepClone(staging);
			updActual = mergeDDN(actual, updActual);
			updActual.setDateGenerated(DateUtil.getDate());

			// if( staging.getCreditOfficerName() != null &&
			// !staging.getCreditOfficerName
			// ().equals(actual.getCreditOfficerName()))
			if (staging.getCreditOfficerName() != null) {
				updActual.setCreditOfficerDt(DateUtil.getDate());
			}

			// if( staging.getSeniorOfficerName() != null &&
			// !staging.getSeniorOfficerName
			// ().equals(actual.getSeniorOfficerName()) )
			if (staging.getSeniorOfficerName() != null) {
				updActual.setSeniorOfficerDt(DateUtil.getDate());
			}

			updActual.setRemarks(anIDDNTrxValue.getRemarks());
			IDDN actualDDN = getSBDDNBusManager().updateDDN(updActual);
			anIDDNTrxValue.setDDN(actualDDN);
			anIDDNTrxValue.setReferenceID(String.valueOf(actualDDN.getDDNID()));
			return anIDDNTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (DDNException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualDDN(): " + ex.toString());
		}
	}

}