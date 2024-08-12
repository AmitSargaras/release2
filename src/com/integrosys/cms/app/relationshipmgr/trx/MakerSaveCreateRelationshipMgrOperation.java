package com.integrosys.cms.app.relationshipmgr.trx;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Transaction Operation to be invoked to create Relationship Manager, create transaction.
 * 
 * Maker Submit Save create operation to save record  in Draft by maker
 * 
 * @author Dattatray Thorat
 * 
 */
public class MakerSaveCreateRelationshipMgrOperation extends AbstractRelationshipMgrTrxOperation {

	private static final long serialVersionUID = -6138935003644406544L;

	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_CREATE_RELATIONSHIP_MGR;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		Validate.notNull(value, "transaction value must not be null");

		IRelationshipMgrTrxValue trxValue = (IRelationshipMgrTrxValue) value;
		trxValue = super.createStagingRelationshipMgr(trxValue);

		if (StringUtils.isNotBlank(trxValue.getTransactionID())) {
			trxValue = super.updateTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
}
