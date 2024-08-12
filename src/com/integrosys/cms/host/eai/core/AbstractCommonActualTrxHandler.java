package com.integrosys.cms.host.eai.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.host.eai.Message;

/**
 * <p>
 * Provide implementation for {@link #persistStagingTrx(Message, Object)} and
 * {@link #preprocess(Message)} which some of the message type might not
 * available for the staging records.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public abstract class AbstractCommonActualTrxHandler implements IActualTrxHandler {

	/* logger available for sub classes */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public abstract Message persistActualTrx(Message msg);

	public String getTrxKey() {
		// for classes without staging , extend this method.
		return "";
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		logger.info("No implementation of staging.");
		return msg;
	}

	public Message preprocess(Message msg) {
		logger.info(" No Preprocesses .");
		return msg;
	}

}
