/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/RejectCollateralCommand.java,v 1.10.10.1 2006/12/14 12:19:04 jychong Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.ICashFd;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10.10.1 $
 * @since $Date: 2006/12/14 12:19:04 $ Tag: $Name: DEV_20060126_B286V1 $
 */

public class RejectCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		ICollateralTrxValue returnValue = new OBCollateralTrxValue();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		
		String remarks = (String) map.get("remarks");

		try {
			// CollateralUiUtil.setTrxLocation(ctx, itrxValue.getCollateral());
			// checker reject to get the trx context from existing trx
			if(remarks == null || remarks.equals("")){
				  exceptionMap.put("remarkError", new ActionMessage("error.string.mandatory"));
				  ICollateralTrxValue vATrxValue = null;
				    temp.put("request.ITrxValue", itrxValue);
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
	            }else{
	            	    ctx.setRemarks(remarks);
	             }
			
			
			ITrxContext oldCtx = itrxValue.getTrxContext();
			if (oldCtx != null) {
				ctx.setCustomer(oldCtx.getCustomer());
				ctx.setLimitProfile(oldCtx.getLimitProfile());
			}
			//Commented By sachin 03 May
			/**
			 * add code hear to update staging collateral values with actual collateral if fd receipt no is same.
			 * */

			if(itrxValue.getCollateral() instanceof  ICashFd)
			{
				ICashFd iCol;
				ICashFd iColActual;
				 iCol = (ICashFd) itrxValue.getStagingCollateral();
				 iColActual = (ICashFd) itrxValue.getCollateral();
				 ICashDeposit[] deposit = ((ICashCollateral) iCol).getDepositInfo();
				 ICashDeposit[] depositActual = ((ICashCollateral) iColActual).getDepositInfo();
				if(depositActual.length > 0)
				{
				 for (int i = 0; i < deposit.length; i++) {					 
					 for (int j = 0; j < depositActual.length; j++) {
					 if(deposit[i].getDepositRefNo().equals(depositActual[j].getDepositRefNo()))
					 {
						 deposit[i].setDepositAmount(depositActual[j].getDepositAmount());
						 deposit[i].setIssueDate(depositActual[j].getIssueDate());
						 deposit[i].setDepositMaturityDate(depositActual[j].getDepositMaturityDate());
						 deposit[i].setVerificationDate(depositActual[j].getVerificationDate());
						 deposit[i].setDepositeInterestRate(depositActual[j].getDepositeInterestRate());
						 deposit[i].setActive(depositActual[j].getActive());
					 }
					 }												
					}
				 iCol.setDepositInfo(deposit);
				 itrxValue.setStagingCollateral(iCol);
				}
			}
			//start-------------------------------24Sept2013-------------------------------------------
			ICollateral stageCol = (ICollateral)itrxValue.getStagingCollateral();
			stageCol.setStatus("ACTIVE");
			itrxValue.setStagingCollateral(stageCol);
			//end--------------------------------------------------------------------------------------- 
			returnValue = CollateralProxyFactory.getProxy().checkerRejectCollateral(ctx, itrxValue);
			result.put("request.ITrxValue", returnValue);
		}
		catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to reject collateral transaction, transaction id [" + itrxValue.getTransactionID() + "]");
			cpe.initCause(e);
			throw cpe;
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
