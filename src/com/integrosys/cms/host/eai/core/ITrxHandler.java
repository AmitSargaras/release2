package com.integrosys.cms.host.eai.core;

import java.util.Map;

import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;

/**
 * Centralized handler to process workflow related item
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public interface ITrxHandler extends IEaiConstant {

	/**
	 * Passed in the Message object with the domain keys filled in, then
	 * construct the a Map, which key is for the whole domain types, value is
	 * another Map (which key is the domain key(constructed), value is the
	 * transaction value object)
	 * 
	 * @param msg the message object with all the domain keys filled in, but the
	 *        keys might be empty if the publish type is REPUBLISH
	 * @param flatMessage a new Map instance, could be empty, which is filled in
	 *        the entry by this method
	 * @return a Map which key is the key for whole same type domain, value is
	 *         another map for each domain object transaction value(which key is
	 *         contructed key for the domain, value is the transactionv value
	 *         object)
	 * @throws EAIMessageException if there is any error occurred in the eai
	 *         message process
	 * @throws EAITransactionException if there is any error occurred in the
	 *         workflow engine
	 */
	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException, EAIMessageException;

	/**
	 * Execute the workflow action after Message object is read and processed to
	 * the actual and staging.
	 * 
	 * @param msg the message object contains the domain keys
	 * @param stagingMsg the staging message object contains the domain keys
	 *        from staging
	 * @param keyTrxValueMap the Map constructed from the
	 *        {@link #getTransaction(Message, Map)}, key is the domain key,
	 *        value is the trx value of the domain object
	 * @return the Map for the trxValue's map which the workflow has been
	 *         executed
	 * @throws EAITransactionException if there is any error encountered in
	 *         workflow engine
	 */
	public Map transact(Message msg, Message stagingMsg, Map keyTrxValueMap) throws EAITransactionException;

	/**
	 * @return transaction operation description
	 */
	public String getOpDesc();

	/**
	 * The transaction key for the whole same type domains object, which is used
	 * as the key to back the Map (which key is constructed for each domain
	 * object workflow value)
	 * 
	 * @return the Key used for the Map return by
	 *         {@link #getTransaction(Message, Map)} method .
	 */
	public String getTrxKey();
}
