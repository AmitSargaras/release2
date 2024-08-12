/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/MakerCloseDraftCreateSLTOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-23
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.MakerCloseDraftCreateSLTOperation.java
 */
public class MakerSaveInternalLimitParameterOperation extends AbstractInternalLimitParameterOperation {
	
	private static final long serialVersionUID = 1L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		
		IInternalLimitParameterTrxValue ilpTrxValue = createStagingILP(getILParamTrxValue(anITrxValue));
		
		if (ilpTrxValue.getStatus().equals (ICMSConstant.STATE_ND)) {
			ilpTrxValue = createTransaction(ilpTrxValue);
		} else {
			ilpTrxValue = updateTransaction(ilpTrxValue);
		}
		
		return prepareResult(ilpTrxValue);
	}
}
