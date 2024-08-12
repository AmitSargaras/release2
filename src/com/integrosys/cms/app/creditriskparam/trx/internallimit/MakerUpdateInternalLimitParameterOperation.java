package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;

public class MakerUpdateInternalLimitParameterOperation extends AbstractInternalLimitParameterOperation {
	
	private static final long serialVersionUID = 1L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		
		IInternalLimitParameterTrxValue ilpTrxValue = super.getILParamTrxValue (anITrxValue);
		
		ilpTrxValue = updateStagingILParam(ilpTrxValue);
		
		ilpTrxValue = super.createStagingILP(ilpTrxValue);
		
		if (ilpTrxValue.getStatus().equals (ICMSConstant.STATE_ND)) {
			ilpTrxValue = super.createTransaction(ilpTrxValue);
		} else {
			ilpTrxValue = super.updateTransaction(ilpTrxValue);
		}
		
		return super.prepareResult(ilpTrxValue);
		
	}
	
	protected IInternalLimitParameterTrxValue updateStagingILParam (IInternalLimitParameterTrxValue trxValue) throws TrxOperationException {
		
		try {
				List sILPList = trxValue.getStagingILPList();
				List aILPList = trxValue.getActualILPList();
				
				for (int i = 0; i < aILPList.size(); i++) {
					((IInternalLimitParameter)sILPList.get(i)).setId (((IInternalLimitParameter)aILPList.get(i)).getId()); 
		        }
				
				trxValue.setStagingILPList(sILPList);
	            
	            return trxValue;
				
		} catch (Exception e) {
			throw new TrxOperationException(
			"Exception in updateActualProfile(): " + e.toString());
		}
	}
	
}
