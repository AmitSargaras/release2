package com.integrosys.cms.app.limit.trx;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * Transaction Controller to be used for reading transaction values
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class FacilityReadController extends AbstractTrxController implements ITrxOperationFactory {

	private static final long serialVersionUID = -4214051964686781037L;

	public static final String ACTION_READ_TRX = "ACTION_READ_TRX";

	public static final String ACTION_READ_TRX_BY_FAC_MASTER_ID = "ACTION_READ_TRX_BY_FAC_MASTER_ID";

	/** instance or transaction type of this trx controller */
	public static final String INSTANCE_FACILITY = "FACILITY";

	private Map nameTrxOperationMap;

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		String action = param.getAction();

		if (ACTION_READ_TRX.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("ReadFacilityOperation");
		}

		if (ACTION_READ_TRX_BY_FAC_MASTER_ID.equals(action)) {
			return (ITrxOperation) getNameTrxOperationMap().get("ReadFacilityByFacilityIdOperation");
		}

		throw new IllegalArgumentException("Cannot find operation using action [" + action + "]");
	}

	public String getInstanceName() {
		return INSTANCE_FACILITY;
	}

	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		Validate.notNull(value, "'value' must not be null.");
		Validate.notNull(param, "'param' must not be null.");

		value = setInstanceName(value);

		ITrxOperation op = getOperation(value, param);

		CMSReadTrxManager mgr = new CMSReadTrxManager();
		return mgr.operateTransaction(op, value);
	}
}
