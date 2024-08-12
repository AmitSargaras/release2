package com.integrosys.cms.host.eai.core;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;

/**
 * @author marvin
 * @author Chong Jun Yong
 */
public abstract class AbstractCommonTrxHandler extends CMSTrxOperation implements ITrxHandler {

	private static final long serialVersionUID = 3074629214268998168L;

	protected Log logger = LogFactory.getLog(getClass());

	private SBCMSTrxManager cmsTrxManager;

	public void setCmsTrxManager(SBCMSTrxManager cmsTrxManager) {
		this.cmsTrxManager = cmsTrxManager;
	}

	public SBCMSTrxManager getCmsTrxManager() {
		return cmsTrxManager;
	}

	public abstract Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException,
			EAIMessageException;

	public final Map transact(Message msg, Message stagingMsg, Map keyTrxValueMap) throws EAITransactionException {
		Map trxValuesMap = prepareTrxValuesMap(msg, stagingMsg, keyTrxValueMap);

		hostExecution(trxValuesMap, msg);

		return trxValuesMap;
	}

	/**
	 * <p>
	 * To retrieve the workflow values just before under go workflow execution,
	 * which shall be invoked in the {@link #transact(Message, Message, Map)}.
	 * 
	 * <p>
	 * This step should include the preparation of actual object and staging
	 * object into the workflow value.
	 * 
	 * @param msg the message object which has been processed
	 * @param stagingMsg the staging message object which has been processed, at
	 *        this point, the keys has been filled in as well, to facilitate the
	 *        workflow engine to deal with staging object correctly.
	 * @param trxValue a Map which key is the key for whole same type domain,
	 *        value is another map for each domain object transaction
	 *        value(which key is contructed key for the domain, value is the
	 *        transactionv value object)
	 * @return the map supplied which is ready to go through the workflow
	 *         execution
	 * @throws EAIMessageException if there is any error encountered
	 */
	protected abstract Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValuesMap)
			throws EAIMessageException;

	/**
	 * To execute the exact workflow engine, which could based on the indicator
	 * in the message supplied to determine whether to do create, update or
	 * delete of the workflow value.
	 * 
	 * @param trxValue the transaction value map, key is the string value, value
	 *        is the cms trx value object
	 * @param msg the message parsed from the xml file
	 * @throws EAITransactionException if there is any error under go workflow
	 *         engine
	 */
	protected abstract void hostExecution(Map trxValue, Message msg) throws EAITransactionException;

	public abstract String getOpDesc();

	public abstract String getTrxKey();

}
