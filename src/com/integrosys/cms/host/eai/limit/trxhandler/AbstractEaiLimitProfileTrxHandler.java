package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonTrxHandler;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public abstract class AbstractEaiLimitProfileTrxHandler extends AbstractCommonTrxHandler {

	private static final long serialVersionUID = -6164710844795142996L;

	private ILimitDao limitDao;

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public abstract Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException,
			EAIMessageException;

	public abstract String getTrxKey();

	public abstract String getOpDesc();

	public abstract String getOperationName();

	protected void hostExecution(Map trxValue, Message msg) throws EAITransactionException {

		Set keys = trxValue.keySet();

		for (Iterator iterKeys = keys.iterator(); iterKeys.hasNext();) {
			String key = (String) iterKeys.next();
			OBCMSTrxValue cmsTrxValue = (OBCMSTrxValue) trxValue.get(key);

			cmsTrxValue.setTransactionDate(DateUtil.getDate());

			if (cmsTrxValue.getTrxContext() == null) {
				cmsTrxValue.setTrxContext(new OBTrxContext());
			}
			// if it is a update previousState will be used.
			cmsTrxValue.setPreviousState(cmsTrxValue.getStatus());
			cmsTrxValue.setOpDesc(getOpDesc());

			if (isCreate(msg)) {
				logger.debug("EAILimitProfileTrxHandler.hostExecution - isCreate");
				hostCreateCall(cmsTrxValue, msg);
			}
			else if (isUpdate(msg)) {
				logger.debug("EAILimitProfileTrxHandler.hostExecution - isUpdate");
				hostUpdateCall(cmsTrxValue, msg, key);
			}
			else if (isDelete(msg)) {
				logger.debug("EAILimitProfileTrxHandler.hostExecution - isDelete");
				hostDeleteCall(cmsTrxValue, msg, key);
			}
		}
	}

	protected abstract Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValuesMap)
			throws EAIMessageException;

	protected abstract void hostCreateCall(ICMSTrxValue trxValue, Message msg) throws EAITransactionException;

	protected abstract void hostUpdateCall(ICMSTrxValue trxValue, Message msg, String key)
			throws EAITransactionException;

	protected abstract void hostDeleteCall(ICMSTrxValue trxValue, Message msg, String key)
			throws EAITransactionException;

	protected abstract boolean isCreate(Message msg);

	protected abstract boolean isUpdate(Message msg);

	protected abstract boolean isDelete(Message msg);

}
