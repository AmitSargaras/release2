package com.integrosys.cms.app.function.trx;

import java.rmi.RemoteException;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * Transaction operation to read team function group transaction value, either
 * using staging reference id (if transaction id is empty) or transaction id to
 * retrieve the workflow entity.
 * 
 * @author Roy
 * @author Chong Jun Yong
 * 
 */
public class ReadTeamFunctionGrpOperation extends AbstractTeamFunctionGrpTrxOperation implements ITrxReadOperation {

	private static final long serialVersionUID = 7534882994334522469L;

	public ReadTeamFunctionGrpOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_TEAM_FUNCTION_GRP;
	}

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		ITeamFunctionGrpTrxValue teamFunctionGrpTrxValue = (ITeamFunctionGrpTrxValue) value;

		try {
			ICMSTrxValue trxValue = null;
			SBCMSTrxManager trxManager = getTrxManager();

			if (null == teamFunctionGrpTrxValue.getTransactionID()) {
				// for makerReadList
				trxValue = trxManager.getTrxByStageRefIDAndTrxType(teamFunctionGrpTrxValue.getStagingReferenceID(),
						ICMSConstant.INSTANCE_TEAM_FUNCTION_GRP);
			}
			else {
				// for checkerReadApproveReject
				trxValue = trxManager.getTransaction(teamFunctionGrpTrxValue.getTransactionID());
			}

			if (trxValue != null) {
				teamFunctionGrpTrxValue = new OBTeamFunctionGrpTrxValue(trxValue);

				if (trxValue.getReferenceID() != null) {
					List actualTeamFunctionGrpList = getTeamFunctionGrpBusManager().getTeamFunctionGrpByGroupId(
							Long.parseLong(trxValue.getReferenceID()));
					teamFunctionGrpTrxValue.setTeamFunctionGrps(actualTeamFunctionGrpList);

				}

				if (trxValue.getStagingReferenceID() != null) {
					List stagingTeamFunctionGrpList = getStagingTeamFunctionGrpBusManager()
							.getTeamFunctionGrpByGroupId(Long.parseLong(trxValue.getStagingReferenceID()));
					teamFunctionGrpTrxValue.setStagingTeamFunctionGrps(stagingTeamFunctionGrpList);
				}

				return teamFunctionGrpTrxValue;
			}
		}
		catch (RemoteException e) {
			throw new TrxOperationException("failed to remote call on workflow manager[" + getTrxManager()
					+ "]; team function group trx value [" + teamFunctionGrpTrxValue + "]; throwing root cause", e
					.getCause());
		}

		return null;
	}
}
