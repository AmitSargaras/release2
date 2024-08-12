package com.integrosys.cms.host.stp.core;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.support.DataAccessExceptionUtils;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.STPMessageValidationException;
import com.integrosys.cms.host.stp.STPProcessFailedException;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.support.IMessageHandler;
import com.integrosys.cms.host.stp.support.IStpMessageValidator;
import org.springframework.dao.DataAccessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Common Message Handler to do the validation, cover try and catch exception.
 * So the subclasses only need to do the actual processing on the message.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public abstract class AbstractCommonMessageHandler implements IMessageHandler {

	/** logger available for subclasses */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private List validatorList;

	public final void setValidatorList(List validatorList) {
		this.validatorList = validatorList;
	}

	public final List getValidatorList() {
		return validatorList;
	}

    public long generateSeq(String sequenceName, boolean formated) throws STPMessageException {
		try {
			SequenceManager seqmgr = new SequenceManager();
			String seq = seqmgr.getSeqNum(sequenceName, formated);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			throw new CommodityException(this.getClass().getName() + " : Exception in generating Sequence '"
					+ sequenceName + "' \n The exception is : " + e);
		}

	}

	public abstract void postprocess(STPMessage msg, STPMessage stagingMsg, Map flatMessage) throws STPMessageException;

	public final Properties processMessage(STPMessage stpMessage) throws STPMessageException {
		Properties prop = new Properties();

		try {
			validateMessage(stpMessage);

			prop = doProcessMessage(stpMessage);
		}
/*		catch (EAIMessageException ex) {
			throw ex;
		}*/
		catch (OFAException ex) {
			throw new StpCommonException("failed to process full cycle of SI, message header info ["
					+ stpMessage.getMsgHeader() + "]", ex);
		}
		/*catch (DataAccessException ex) {
			throw DataAccessExceptionUtils.handleDataAccessException(ex);
		}*/
		catch (Throwable t) {
			StringBuffer appendedMsg = new StringBuffer();
			if (StringUtils.isBlank(t.getMessage())) {
				StackTraceElement[] stackTraces = t.getStackTrace();
				if (stackTraces != null) {
					appendedMsg.append(stackTraces[0].toString());
				}
			}

			throw new StpCommonException("failed to process full cycle of SI, message header info ["
					+ stpMessage.getMsgHeader() + "]"
					+ ((appendedMsg.length() > 0) ? ("; last known at " + appendedMsg.toString()) : ""), t);
		}

		return prop;
	}


    public final Properties processMessage(Object obj) throws STPMessageException {
		Properties prop = new Properties();

		try {
			//validateMessage(obj);

			prop = doProcessMessage(obj);
		}
		catch (STPMessageException ex) {
			throw ex;
		}
		catch (OFAException ex) {
			throw new STPProcessFailedException("failed to create message", ex);
		}
		catch (DataAccessException ex) {
			throw DataAccessExceptionUtils.handleDataAccessException(ex);
		}
		catch (Throwable t) {
/*			StringBuffer appendedMsg = new StringBuffer();
			if (StringUtils.isBlank(t.getMessage())) {
				StackTraceElement[] stackTraces = t.getStackTrace();
				if (stackTraces != null) {
					appendedMsg.append(stackTraces[0].toString());
				}
			}*/

			throw new STPProcessFailedException("failed to create message", t);
		}

		return prop;
	}

	/**
	 * To do the actual process on the message object after passed the
	 * validation stage. Subclasses can either process the message for the CRUD
	 * on the persistent storage syste, or just prepare result to response back
	 * to the source system
	 *
	 * @param stpMessage the message object ready to be processed
	 * @return message context contains of message objects, one for actual
	 *         domain, one for staging domain, and the workflow values.
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occured
	 */
	protected abstract Properties doProcessMessage(STPMessage stpMessage) throws STPMessageException;

    protected abstract Properties doProcessMessage(Object obj) throws STPMessageException;

	/**
	 * Validate the message object using the {@link com.integrosys.cms.host.eai.core.IEaiMessageValidator}
	 *
	 * @param msg the message object to be validated
	 * @throws com.integrosys.cms.host.eai.EAIMessageValidationException if there is any validation fail
	 */
	protected final void validateMessage(STPMessage msg) throws STPMessageValidationException {
		List validatorList = getValidatorList();
		if (validatorList == null) {
			return;
		}

		for (Iterator iter = getValidatorList().iterator(); iter.hasNext();) {
			IStpMessageValidator sciV = (IStpMessageValidator) iter.next();
			sciV.validate(msg);
		}
	}
}