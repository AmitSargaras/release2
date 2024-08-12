/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CheckerApproveUpdateCollateralOperation.java,v 1.6 2006/08/11 03:06:31 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.ICashFd;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to verify the collateral updated
 * by a maker.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/11 03:06:31 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = 3140930574876932320L;

	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_COL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create actual collateral record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		
		DefaultLogger.debug(this, "===============================105-1====================== ");
		ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);
		DefaultLogger.debug(this, "===============================105-2====================== ");
		if (trxValue.getCollateral() instanceof ICommodityCollateral) {
			trxValue = super.createStagingCollateral(trxValue);
		}
		//Added By Anil
		if (trxValue.getCollateral() instanceof IGeneralCharge) {
			IGeneralCharge staging = (IGeneralCharge)trxValue.getStagingCollateral();
			
			IGeneralChargeDetails[] generalChargeDetails = staging.getGeneralChargeDetails();
			if(generalChargeDetails!=null){
			for (int i = 0; i < generalChargeDetails.length; i++) {
				IGeneralChargeDetails iGeneralChargeDetails = generalChargeDetails[i];
				iGeneralChargeDetails.setStatus(IGeneralChargeDetails.STATUS_APPROVED);
				generalChargeDetails[i]=iGeneralChargeDetails;
			}
			}
			staging.setGeneralChargeDetails(generalChargeDetails);
			
			trxValue = super.updateStagingCollateral(trxValue);
		}
		DefaultLogger.debug(this, "===============================105-3====================== ");
		if (StringUtils.isNotBlank(trxValue.getReferenceID())) {
			
			DefaultLogger.debug(this, "===============================105-4=========update============= ");
			//added by Anup K.
			if (trxValue.getCollateral() instanceof ICashFd) 
			//all ative FD's are update with respect to FD receipt no.
			trxValue = super.updateStagingCollateral(trxValue);
			//End By Anup K.
			trxValue = super.updateActualCollateral(trxValue);
			DefaultLogger.debug(this, "===============================105-5=======after update=============== ");
		}
		else {
			DefaultLogger.debug(this, "===============================105-6=========create============= ");
			trxValue = super.createActualCollateral(trxValue);
			DefaultLogger.debug(this, "===============================105-7=========after create============= ");
		}
		DefaultLogger.debug(this, "===============================105-8===========update transaction=========== ");
		trxValue = super.updateTransaction(trxValue);
		DefaultLogger.debug(this, "===============================105-9===========after update transaction=========== ");
		return super.prepareResult(trxValue);

	}
}
