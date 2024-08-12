package com.integrosys.cms.host.eai.customer.trxhandler;

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
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public abstract class AbstractEaiCustomerTrxHandler extends AbstractCommonTrxHandler {

	private static final long serialVersionUID = 1633541287513717590L;

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public abstract Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException,
			EAIMessageException;

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

			if (isCreate(msg, key)) {
				hostCreateCall(cmsTrxValue, msg);
			}
			else if (isUpdate(msg, key)) {
				hostUpdateCall(cmsTrxValue, msg, key);
			}
			else if (isDelete(msg, key)) {
				hostDeleteCall(cmsTrxValue, msg, key);
			}
		}
	}

	public abstract String getOpDesc();

	public abstract String getTrxKey();

	public abstract String getOperationName();

	protected abstract Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValuesMap)
			throws EAIMessageException;

	protected abstract void hostCreateCall(ICMSTrxValue trxValue, Message msg) throws EAITransactionException;

	protected abstract void hostUpdateCall(ICMSTrxValue trxValue, Message msg, String key)
			throws EAITransactionException;

	protected abstract void hostDeleteCall(ICMSTrxValue trxValue, Message msg, String key)
			throws EAITransactionException;

	protected abstract boolean isCreate(Message msg, String key);

	protected abstract boolean isUpdate(Message msg, String key);

	protected abstract boolean isDelete(Message msg, String key);

}
