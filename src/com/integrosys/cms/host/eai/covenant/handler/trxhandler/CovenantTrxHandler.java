package com.integrosys.cms.host.eai.covenant.handler.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.recurrent.trx.OBRecurrentCheckListTrxValue;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.covenant.CovenantMessageBody;
import com.integrosys.cms.host.eai.covenant.bus.CovenantItem;
import com.integrosys.cms.host.eai.covenant.bus.ICovenantDao;
import com.integrosys.cms.host.eai.covenant.bus.RecurrentDoc;
import com.integrosys.cms.host.eai.covenant.handler.ConvenantHandlerHelper;

/**
 * @author Thurein
 * @author Chong Jun Yong
 * @version 1.0
 * @since 20-Nov-2008
 */
public class CovenantTrxHandler extends AbstractCommonTrxHandler {

	private static final long serialVersionUID = 7443930592781707757L;

	public final static String COVENANT_TRX_KEY = "Covenant";

	private ConvenantHandlerHelper convenantHandlerHelper;

	private ICovenantDao covenantDao;

	private IRecurrentProxyManager recurrentProxy;

	public ICovenantDao getCovenantDao() {
		return covenantDao;
	}

	public void setCovenantDao(ICovenantDao covenantDao) {
		this.covenantDao = covenantDao;
	}

	public ConvenantHandlerHelper getConvenantHandlerHelper() {
		return convenantHandlerHelper;
	}

	public void setConvenantHandlerHelper(ConvenantHandlerHelper convenantHandlerHelper) {
		this.convenantHandlerHelper = convenantHandlerHelper;
	}

	public IRecurrentProxyManager getRecurrentProxy() {
		return recurrentProxy;
	}

	public void setRecurrentProxy(IRecurrentProxyManager recurrentProxy) {
		this.recurrentProxy = recurrentProxy;
	}

	protected void hostExecution(Map trxValue, Message msg) throws EAITransactionException {
		boolean covItemChaged = false;

		IRecurrentProxyManager pm = getRecurrentProxy();

		CovenantMessageBody covenantMsgBdy = (CovenantMessageBody) msg.getMsgBody();
		RecurrentDoc recurrentDoc = covenantMsgBdy.getRecurrentDoc();
		Vector convenentItems = recurrentDoc.getConvenantItems();
		if (convenentItems != null && convenentItems.size() > 0) {
			Iterator iter = convenentItems.iterator();
			while (iter.hasNext()) {
				CovenantItem item = (CovenantItem) iter.next();
				if (convenantHandlerHelper.isCovenantItemChanged(item)) {
					covItemChaged = true;
					break;
				}
			}

			if (covItemChaged) {
				IRecurrentCheckListTrxValue recurrentTrxValue = (IRecurrentCheckListTrxValue) trxValue.get(recurrentDoc
						.getLOSAANumber());
				if (recurrentTrxValue != null && recurrentTrxValue.getReferenceID() == null) {
					try {

						pm.systemCreateCheckList(recurrentTrxValue);
					}
					catch (RecurrentException e) {
						throw new EAITransactionException(
								"Failed to under create recurrent checklist workflow object, trx [" + recurrentTrxValue
										+ "], message header info [" + msg.getMsgHeader() + "]", e);
					}
				}
			}
		}

	}

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValue) throws EAIMessageException {

		RecurrentDoc actRecurrentDoc = ((CovenantMessageBody) msg.getMsgBody()).getRecurrentDoc();
		RecurrentDoc stgRecurrentDoc = ((CovenantMessageBody) stagingMsg.getMsgBody()).getRecurrentDoc();

		if (convenantHandlerHelper.isCovenantItemsChanged(actRecurrentDoc.getConvenantItems())) {
			IRecurrentCheckListTrxValue recurrentTrxVal = (IRecurrentCheckListTrxValue) trxValue.get(actRecurrentDoc
					.getLOSAANumber());

			/** Following 2 objects are eai objects **/

			if ((recurrentTrxVal != null) && (recurrentTrxVal.getReferenceID() == null)) {
				/** Create Event **/

				/**
				 * Copy the values into OBs which have to be assigned to
				 * RecurrentChecklist trx value
				 **/
				OBRecurrentCheckList actualOBRecurrent = convenantHandlerHelper
						.convertToOBrecurrentCheckList(actRecurrentDoc);
				OBRecurrentCheckList stageOBRecurrent = convenantHandlerHelper
						.convertToOBrecurrentCheckList(stgRecurrentDoc);

				recurrentTrxVal.setCheckList(actualOBRecurrent);
				recurrentTrxVal.setStagingCheckList(stageOBRecurrent);
				recurrentTrxVal.setToState("ACTIVE");
				recurrentTrxVal.setLimitProfileID(actRecurrentDoc.getCmsLimitProfileID());

				trxValue.put(actRecurrentDoc.getLOSAANumber(), recurrentTrxVal);

			}
		}

		return trxValue;

	}

	public String getOpDesc() {
		return null;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException, EAIMessageException {
		IRecurrentCheckListTrxValue trxvalue = null;
		RecurrentDoc recurrentDoc = ((CovenantMessageBody) msg.getMsgBody()).getRecurrentDoc();

		HashMap trxMap = new HashMap();
		// if create new
		if (recurrentDoc.getStatus().equals(ICMSConstant.ACTION_SYSTEM_CREATE_CHECKLIST)) {
			trxvalue = new OBRecurrentCheckListTrxValue();
		}
		else if (recurrentDoc.getStatus().equals(ICMSConstant.ACTION_SYSTEM_UPDATE_CHECKLIST)) {
			try {
				trxvalue = getRecurrentProxy().getRecurrentCheckListTrx(recurrentDoc.getCmsLimitProfileID(),
						recurrentDoc.getCmsSubProfileID());
			}
			catch (RecurrentException ex) {
				throw new EAITransactionException(
						"failed to retrieve recurrent checklist transaction, for LOS AA Number ["
								+ recurrentDoc.getLOSAANumber() + "]", ex);
			}

		}

		trxMap.put(recurrentDoc.getLOSAANumber(), trxvalue);
		flatMessage.put(getTrxKey(), trxMap);

		return flatMessage;
	}

	public String getTrxKey() {
		return COVENANT_KEY;
	}

	public String getOperationName() {
		return null;
	}

	/**
	 * Check if the convenant Item has changed.
	 * 
	 * @param covenant of type Covenant
	 * @return boolean
	 */
	protected boolean isCovenantItemChanged(CovenantItem convenantItem) {
		return convenantItem.getChangeIndicator().equals(String.valueOf(IEaiConstant.CHANGEINDICATOR));
	}

}
