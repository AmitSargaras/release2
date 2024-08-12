/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/MakerCloseDraftCreateSLTOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-23
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.MakerCloseDraftCreateSLTOperation.java
 */
public class CheckApproveInternalLimitParameterOperation extends AbstractInternalLimitParameterOperation {
	
	private static final long serialVersionUID = 1L;

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_LIMIT;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		
		IInternalLimitParameterTrxValue trxValue = super.getILParamTrxValue (anITrxValue);
		trxValue = updateActualILParam(trxValue);
		trxValue = super.updateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	protected IInternalLimitParameterTrxValue updateActualILParam (IInternalLimitParameterTrxValue trxValue) throws TrxOperationException {
		
		try {
				List sILPList = trxValue.getStagingILPList();
				List aILPList = trxValue.getActualILPList();
				
				for (int i = 0; i < aILPList.size(); i++) {
					((IInternalLimitParameter)sILPList.get(i)).setVersionTime (((IInternalLimitParameter)aILPList.get(i)).getVersionTime()); 
					((IInternalLimitParameter)sILPList.get(i)).setStatus (trxValue.getToState());
		        }

//				SBInternalLimitParameterBusManager mgr = getActualSBInternalLimitParameterLocal();
				aILPList = getInternalLimitParameterBusManager().updateInternalListParameter (sILPList);
				trxValue.setActualILPList (aILPList);
	            
	            return trxValue;
				
		} catch (Exception e) {
			throw new TrxOperationException(
			"Exception in updateActualProfile(): " + e.toString());
		}
	}
}
