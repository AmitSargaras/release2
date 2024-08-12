package com.integrosys.cms.host.stp.core;

import com.integrosys.base.techinfra.util.AccessorUtil;

import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.stp.*;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.core.AbstractCommonMessageHandler;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * Implementation of {@link com.integrosys.cms.host.eai.core.AbstractCommonMessageHandler} to process respective
 * eai message which required the message to be persisted into domain persistent
 * storage, and interact with workflow engine.
 *
 * <p>
 * This abstract implementation will need validation handler, actual trx (domain
 * objects) handler and trx handler to process each of the message.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 */
public abstract class AbstractWorkflowAwareMessageHandler extends AbstractCommonMessageHandler {

	private List actualTrxHandlerList;

	private List trxHandlerList;

	private List republishActualTrxHandlerList;

	private List republishTrxHandlerList;

	/**
	 * @return list of eai actual trx handler
	 *         <b>&lt;IEAIActualTrxHandler&gt;</b>
	 */
	public List getActualTrxHandlerList() {
		return actualTrxHandlerList;
	}

	public final void setActualTrxHandlerList(List actualTrxHandlerList) {
		this.actualTrxHandlerList = actualTrxHandlerList;
	}

	/**
	 * @return list of eai trx handler <b>&lt;IEAITrxHandler&gt;</b>
	 */
	public List getTrxHandlerList() {
		return trxHandlerList;
	}

	public final void setTrxHandlerList(List trxHandlerList) {
		this.trxHandlerList = trxHandlerList;
	}

	/**
	 * @return list of eai trx handler <b>&lt;IEAIActualTrxHandler&gt;</b> for
	 *         republish mechanism
	 */
	public List getRepublishActualTrxHandlerList() {
		return republishActualTrxHandlerList;
	}

	public final void setRepublishActualTrxHandlerList(List republishActualTrxHandlerList) {
		this.republishActualTrxHandlerList = republishActualTrxHandlerList;
	}

	/**
	 * @return list of eai trx handler <b>&lt;IEAITrxHandler&gt;</b> for
	 *         republish mechanism
	 */
	public List getRepublishTrxHandlerList() {
		return republishTrxHandlerList;
	}

	public final void setRepublishTrxHandlerList(List republishTrxHandlerList) {
		this.republishTrxHandlerList = republishTrxHandlerList;
	}

	/**
	 * This method implements the common process flow for all type of messages.
	 *
	 * <ol>
	 * <b>Common Process flow :</b>
	 * <li>Get Transaction with VersionTime. Object will be empty if its a
	 * creation.
	 * <li>persist EAI user data with CMS generated IDs.
	 * <li>clone EAI user data for staging, with staging CMS generated IDs.
	 * <li>finally transaction engine is called to Create , Update or Delete.
	 * </ol>
	 *
	 * <p>
	 * Transaction values are stored, kept in <code>flatMessage</code> instance,
	 * in this format <b>Map&lt;String, Map&lt;String, ITrxValue&gt;&gt;</b>
	 *
	 * @param message eai message parsed from other sources such as XML
	 * @return properties having actual message, staging message, trx values
	 */
	public Properties doProcessMessage(STPMessage message) throws STPMessageException {
		Properties prop = new Properties();

		STPMessage stagingMsg = null;

		// data binding
		//STPMessage message = (STPMessage) message;

		Map flatMessage = null;
		// store Business Transaction
		if ((message.getMsgHeader().getPublishType() != null)
				&& message.getMsgHeader().getPublishType().trim().equalsIgnoreCase(IEaiConstant.REPUBLISH_INDICATOR)) {
			message = preprocess(message);

			flatMessage = getRepublishWorkflowValues(message);

			message = storeRepublishTransaction(message);

			// Create Staging Transaction
			try {
				stagingMsg = (STPMessage) AccessorUtil.deepClone(message);
			}
			catch (IOException e) {
				throw new FileSystemAccessException(
						"failed to clone message object for staging record purpose, possible ?", e);
			}
			catch (ClassNotFoundException e) {
				throw new STPProcessFailedException(
						"failed to clone message object for staging record purpose, possible ?", e);
			}

			stagingMsg = storeRepublishStagingTransaction(stagingMsg, flatMessage);
		}
		else {
			message = storeActualTransaction(message);

			flatMessage = retrieveWorkflowValues(message);

			// Create Staging Transaction
			try {
				stagingMsg = (STPMessage) AccessorUtil.deepClone(message);
			}
			catch (IOException e) {
				throw new FileSystemAccessException(
						"failed to clone message object for staging record purpose, possible ?", e);
			}
			catch (ClassNotFoundException e) {
				throw new STPProcessFailedException(
						"failed to clone message object for staging record purpose, possible ?", e);
			}

			stagingMsg = storeStagingTransaction(stagingMsg, flatMessage);
		}

		prop.put(IEaiConstant.MSG_OBJ, message);
		prop.put(IEaiConstant.STAG_MSG_OBJ, stagingMsg);
		prop.put(IEaiConstant.FLATMSG_OBJ, flatMessage);

		return prop;
	}

	public void postprocess(STPMessage msg, STPMessage stagingMsg, Map flatMessage) throws STPMessageException {
		logger.debug("\n\n< --- Post Process --- >");

		/*if ((msg.getMsgHeader().getPublishType() != null)
				&& msg.getMsgHeader().getPublishType().trim().equalsIgnoreCase(IEaiConstant.REPUBLISH_INDICATOR)) {
			createRepublishWorkflowValues(msg, stagingMsg, flatMessage);
		}
		else {
			createWorkflowValues(msg, stagingMsg, flatMessage);
		}*/
	}

	/**
	 * This method will getTransaction with VersionTime information .
	 * Transaction Object will be store into a HashMap. Hashmap key is determine
	 * by the interface IEAITrxHandler method "getTrxKey()".
	 * @throws com.integrosys.cms.host.eai.EAITransactionException
	 */
	protected Map retrieveWorkflowValues(STPMessage msg) throws STPMessageException, STPTransactionException {
		logger.debug("\n\n< --- Getting  Workflow Values --- >");

		Map flatMessage = new HashMap();

		/*List trxHandlerList = getTrxHandlerList();
		if (trxHandlerList == null) {
			return flatMessage;
		}

		for (Iterator iter = trxHandlerList.iterator(); iter.hasNext();) {
			ITrxHandler scicth = (ITrxHandler) iter.next();
			scicth.getTransaction(msg, flatMessage);
		}*/

		return flatMessage;
	}

	/**
	 * This method get transaction using RepublishTrxHandler.
	 * @throws com.integrosys.cms.host.eai.EAITransactionException
	 */
	protected Map getRepublishWorkflowValues(STPMessage msg) throws STPMessageException, STPTransactionException {
		logger.debug("\n\n< --- Republish Getting Workflow Values --- >");

		Map flatMessage = new HashMap();

		/*List republishTrxHandlerList = getRepublishTrxHandlerList();
		if (republishTrxHandlerList == null) {
			return flatMessage;
		}

		for (Iterator iter = republishTrxHandlerList.iterator(); iter.hasNext();) {
			ITrxHandler scicth = (ITrxHandler) iter.next();
			scicth.getTransaction(msg, flatMessage);
		}*/

		return flatMessage;
	}

	/**
	 * This method will call ActualHandler to persist Staging data.
	 */
	protected STPMessage storeStagingTransaction(STPMessage msg, Map flatMessage) throws STPMessageException {
		logger.debug("\n\n< --- Staging XML --- >");
		/*List actualTrxHandlerList = getActualTrxHandlerList();

		if (actualTrxHandlerList == null) {
			return msg;
		}

		for (Iterator iter = actualTrxHandlerList.iterator(); iter.hasNext();) {
			IActualTrxHandler sciath = (IActualTrxHandler) iter.next();

			msg = sciath.persistStagingTrx(msg, flatMessage.get(sciath.getTrxKey()));

		}*/
		return msg;
	}

	/**
	 * This method will call republishActualHandler to persist EAI user data +
	 * CMS generated IDs.
	 */
	protected STPMessage storeRepublishTransaction(STPMessage msg) throws STPMessageException {
		logger.debug("\n\n< --- Republish Storing  XML --- >");

		/*List republishActualTrxHandlerList = getRepublishActualTrxHandlerList();
		if (republishActualTrxHandlerList == null) {
			return msg;
		}

		for (Iterator iter = republishActualTrxHandlerList.iterator(); iter.hasNext();) {
			IActualTrxHandler sciath = (IActualTrxHandler) iter.next();

			msg = sciath.persistActualTrx(msg);

		}*/

		return msg;
	}

	/**
	 * This method will execute the preprocess method of
	 * RepublishActuallHandler. It enable republish message to prepare/sort out
	 * data before proceeding with the normal flow .
	 */
	protected STPMessage preprocess(STPMessage msg) throws STPMessageException {

		/*logger.debug("\n\n< --- Republish Preprocess  XML --- >");

		List republishActualTrxHandlerList = getRepublishActualTrxHandlerList();
		if (republishActualTrxHandlerList == null) {
			return msg;
		}

		for (Iterator iter = republishActualTrxHandlerList.iterator(); iter.hasNext();) {
			IActualTrxHandler sciath = (IActualTrxHandler) iter.next();
			msg = sciath.preprocess(msg);
		}
         */
		return msg;
	}

	/**
	 * This method will persist Staging data.
	 */
	protected STPMessage storeRepublishStagingTransaction(STPMessage msg, Map flatMessage) throws STPMessageException {
		logger.debug("\n\n< --- Staging XML --- >");

		/*List republishActualTrxHandlerList = getRepublishActualTrxHandlerList();
		if (republishActualTrxHandlerList == null) {
			return msg;
		}

		for (Iterator iter = republishActualTrxHandlerList.iterator(); iter.hasNext();) {
			IActualTrxHandler sciath = (IActualTrxHandler) iter.next();

			msg = sciath.persistStagingTrx(msg, flatMessage.get(sciath.getTrxKey()));

		}*/
		return msg;
	}

	/**
	 * This method will persist EAI user data + CMS generated IDs.
	 */
	protected STPMessage storeActualTransaction(STPMessage msg) throws STPMessageException {
		logger.debug("\n\n< --- Storing  XML --- >");

		/*List actualTrxHanderList = getActualTrxHandlerList();
		if (actualTrxHanderList == null) {
			return msg;
		}

		for (Iterator iter = actualTrxHanderList.iterator(); iter.hasNext();) {
			IActualTrxHandler sciath = (IActualTrxHandler) iter.next();

			msg = sciath.persistActualTrx(msg);

		}*/

		return msg;
	}

	/**
	 * THis method will do either a Create, Update or Delete transaction.
	 */
	protected void createWorkflowValues(STPMessage msg, STPMessage stagingMsg, Map flatMessage)
			throws StpCommonException {
		logger.debug("\n\n< --- Create Workflow Values --- >");

		/*List trxHanderList = getTrxHandlerList();
		if (trxHanderList == null) {
			return;
		}

		for (Iterator iter = trxHanderList.iterator(); iter.hasNext();) {
			ITrxHandler scicth = (ITrxHandler) iter.next();
			scicth.transact(msg, stagingMsg, (Map) flatMessage.get(scicth.getTrxKey()));
		}*/

		logger.debug("\n\n< --- Finished Create Workflow Values --- >");
	}

	protected void createRepublishWorkflowValues(STPMessage msg, STPMessage stagingMsg, Map flatMessage)
			throws StpCommonException {
		logger.debug("\n\n< --- Create Republish Workflow Values --- >");

		/*List trxHanderList = getRepublishTrxHandlerList();
		if (trxHanderList == null) {
			return;
		}

		for (Iterator iter = trxHanderList.iterator(); iter.hasNext();) {
			ITrxHandler scicth = (ITrxHandler) iter.next();
			scicth.transact(msg, stagingMsg, (Map) flatMessage.get(scicth.getTrxKey()));
		}*/
	}
}