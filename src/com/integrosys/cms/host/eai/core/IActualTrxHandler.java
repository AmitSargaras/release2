package com.integrosys.cms.host.eai.core;

import com.integrosys.cms.host.eai.Message;

/**
 * Centralized handler to process message passed from EAI
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @version $Id$
 */
public interface IActualTrxHandler {
	public static final char CREATEINDICATOR = IEaiConstant.CREATEINDICATOR;

	public static final char UPDATEINDICATOR = IEaiConstant.UPDATEINDICATOR;

	public static final char DELETEINDICATOR = IEaiConstant.DELETEINDICATOR;

	public static final char CHANGEINDICATOR = IEaiConstant.CHANGEINDICATOR;

	public String getTrxKey();

	/**
	 * This method should persist the EAI user data + CMS generated IDs into the
	 * db.
	 * 
	 * @param msg Message contains EAI user data. Implementing classes should
	 *        retrieve the CMS generated IDs if it is a update or delete.
	 * @return Message Any modification in the Message object should be return
	 *         for Staging and Transaction purpose.
	 */
	public Message persistActualTrx(Message msg);

	/**
	 * This method should persist the EAI user data + staging CMS generated IDs
	 * into the db.
	 * 
	 * @param msg Message contains EAI user data. Implementing classes should
	 *        retrieve the CMS generated IDs if it is a update or delete.
	 * @param trxValue normally a map contains trx values objects backed by
	 *        certain key combination
	 * @return Message return the staging Object.
	 */
	public Message persistStagingTrx(Message msg, Object trxValue);

	/**
	 * Pre process message such as fix the change / update status indicator by
	 * comparing the data in persistent storage. This can ensure data are
	 * correctly processed after passed into actual trx action routine
	 * 
	 * @param msg eai message to be pre processed before doing the actual trx
	 *        action
	 * @return preprocessed message that is ready to be passed into actual trx
	 *         action routine
	 */
	public Message preprocess(Message msg);

	/**
	 * To check the old cmsid passed in (for the variation) is a valid one, i.e
	 * exist in the cms database.
	 * @param mssage to be validated for the variation.
	 * @return validated message which is to be processed for the variation
	 *         case.
	 */
}
