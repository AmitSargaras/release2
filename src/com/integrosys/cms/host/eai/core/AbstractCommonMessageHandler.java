package com.integrosys.cms.host.eai.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.EAIProcessFailedException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.support.DataAccessExceptionUtils;

/**
 * Common Message Handler to do the validation, cover try and catch exception.
 * So the subclasses only need to do the actual processing on the message.
 * 
 * @author Chong Jun Yong
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

	public abstract void postprocess(Message msg, Message stagingMsg, Map flatMessage) throws EAIMessageException;

	public final Properties processMessage(EAIMessage eaiMessage) throws EAIMessageException {
		Properties prop = new Properties();

		try {
			validateMessage(eaiMessage);

			prop = doProcessMessage(eaiMessage);
		}
		catch (EAIMessageException ex) {
			throw ex;
		}
		catch (OFAException ex) {
			throw new EAIProcessFailedException("failed to process full cycle of SI, message header info ["
					+ eaiMessage.getMsgHeader() + "]", ex);
		}
		catch (DataAccessException ex) {
			throw DataAccessExceptionUtils.handleDataAccessException(ex);
		}
		catch (Throwable t) {
			StringBuffer appendedMsg = new StringBuffer();
			if (StringUtils.isBlank(t.getMessage())) {
				StackTraceElement[] stackTraces = t.getStackTrace();
				if (stackTraces != null) {
					appendedMsg.append(stackTraces[0].toString());
				}
			}

			throw new EAIProcessFailedException("failed to process full cycle of SI, message header info ["
					+ eaiMessage.getMsgHeader() + "]"
					+ ((appendedMsg.length() > 0) ? ("; last known at " + appendedMsg.toString()) : ""), t);
		}

		return prop;
	}

	/**
	 * To do the actual process on the message object after passed the
	 * validation stage. Subclasses can either process the message for the CRUD
	 * on the persistent storage syste, or just prepare result to response back
	 * to the source system
	 * 
	 * @param eaiMessage the message object ready to be processed
	 * @return message context contains of message objects, one for actual
	 *         domain, one for staging domain, and the workflow values.
	 * @throws EAIMessageException if there is any error occured
	 */
	protected abstract Properties doProcessMessage(EAIMessage eaiMessage) throws EAIMessageException;

	/**
	 * Validate the message object using the {@link IEaiMessageValidator}
	 * 
	 * @param msg the message object to be validated
	 * @throws EAIMessageValidationException if there is any validation fail
	 */
	protected final void validateMessage(EAIMessage msg) throws EAIMessageValidationException {
		List validatorList = getValidatorList();
		if (validatorList == null) {
			return;
		}

		for (Iterator iter = getValidatorList().iterator(); iter.hasNext();) {
			IEaiMessageValidator sciV = (IEaiMessageValidator) iter.next();
			sciV.validate(msg);
		}
	}

}
