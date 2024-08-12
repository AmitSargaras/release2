package com.integrosys.cms.host.eai.customer.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.CustomerMessageBody;
import com.integrosys.cms.host.eai.customer.EAICustomerMessageException;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;

/**
 * @author marvin
 * @author Chong Jun Yong
 */
public class MainProfileTrxHandler extends AbstractEaiCustomerTrxHandler {

	private static final long serialVersionUID = -3001351349645173118L;

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map originalTrxValue)
			throws EAICustomerMessageException {

		MainProfile mainProfile = ((CustomerMessageBody) ((EAIMessage) msg).getMsgBody()).getMainProfileDetails()
				.getMainProfile();
		Set set = originalTrxValue.keySet();

		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			OBCMSCustomerTrxValue trxValue = (OBCMSCustomerTrxValue) originalTrxValue.get(key);

			trxValue = prepareTrxValue(trxValue, mainProfile, key);

			trxValue = prepareStagingValue(trxValue, mainProfile, key);

			originalTrxValue.put(key, trxValue);
		}

		return originalTrxValue;
	}

	/**
	 * This method will set all necessary cusotmer details to
	 * OBCMSCustomerTrxValue.
	 */
	private OBCMSCustomerTrxValue prepareTrxValue(OBCMSCustomerTrxValue trxValue, MainProfile mainProfile, String key)
			throws EAICustomerMessageException {
		OBTrxContext context = new OBTrxContext();
		OBCMSCustomer cust = new OBCMSCustomer();

		cust.setCMSLegalEntity(null);
		cust.setCustomerName(mainProfile.getCustomerNameShort());
		cust.setCustomerID(mainProfile.getSubProfilePrimaryKey());

		context.setCustomer(cust);

		/**
		 * NOTE ** Have to set the following for preprocess check and
		 * AbstractCustomerTrxOperation preprocess check will use it to set the
		 * ParentReferanceID . Field used is CsutomerID from staging
		 * AbstractCUstomerTrxOperation will use it to overwrite the referanceID
		 * and StagingreferanceID. Field used is CustomerID from actual and
		 * staging.
		 */
		OBCMSCustomer obcmsc = new OBCMSCustomer();
		obcmsc.setCustomerID(mainProfile.getSubProfilePrimaryKey());
		trxValue.setCustomer(obcmsc);
		trxValue.setLegalID(String.valueOf(mainProfile.getCmsId()));
		trxValue.setLegalName(mainProfile.getCustomerNameLong());
		trxValue.setReferenceID(mainProfile.getSubProfilePrimaryKey() + "");

		trxValue.setTrxContext(context);

		return trxValue;
	}

	private OBCMSCustomerTrxValue prepareStagingValue(OBCMSCustomerTrxValue trxValue, MainProfile mainProfile,
			String key) throws EAICustomerMessageException {
		// long leid = extractLEID( key );
		// long subprofileid = extractSubProfileId( key );

		OBCMSCustomer stagingC = new OBCMSCustomer();

		stagingC.setCustomerID(mainProfile.getSubProfilePrimaryKey());

		trxValue.setStagingCustomer(stagingC);

		return trxValue;
	}

	public String getTrxKey() {
		return IEaiConstant.CUSTOMER_KEY;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		HashMap hm = new HashMap();

		MainProfile mainProfile = ((CustomerMessageBody) ((EAIMessage) msg).getMsgBody()).getMainProfileDetails()
				.getMainProfile();
		ICMSTrxValue ctv = new OBCMSCustomerTrxValue();
		String source = mainProfile.getSource();

		if (isUpdate(msg, "") || isDelete(msg, "")) {
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();

			Map parameters = new HashMap();
			parameters.put("CIFId", mainProfile.getCIFId());
			parameters.put("source", source);

			MainProfile mp = (MainProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(parameters,
					"updateStatusIndicator", MainProfile.class);

			if (mp == null) {
				throw new IllegalStateException("MainProfile not found for cifId : [" + mainProfile.getCIFId()
						+ "] and source : [" + source + "]");
			}

			parameters.clear();
			parameters.put("cmsMainProfileId", new Long(mp.getCmsId()));

			SubProfile tmpsubProfile = (SubProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(parameters,
					"updateStatusIndicator", SubProfile.class);

			if (tmpsubProfile == null) {
				throw new IllegalStateException("SubProfile not found for cmsMainProfileId : [" + mp.getCmsId()
						+ "] CIF Id [" + mp.getCIFId() + "]");
			}

			ctv = proxy.getTrxCustomer(tmpsubProfile.getCmsId());
			ctv.setFromState("ND");
		}

		String key = CUSTOMER_KEY + DELIMITER + mainProfile.getCIFId() + DELIMITER
				+ mainProfile.getSubProfilePrimaryKey();

		logger.debug("The Key of Customer Trx Value constructed is : [" + key + "]");

		hm.put(key, ctv);
		flatMessage.put(getTrxKey(), hm);

		return flatMessage;

	}

	protected void hostCreateCall(ICMSTrxValue trxValue, Message msg) throws EAITransactionException {
		try {
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();

			proxy.createCustomer((OBCMSCustomerTrxValue) trxValue);
		}
		catch (CustomerException e) {
			throw new EAITransactionException("Failed to create customer workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostUpdateCall(ICMSTrxValue trxValue, Message msg, String key) throws EAITransactionException {
		try {
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			proxy.hostUpdateCustomer((OBCMSCustomerTrxValue) trxValue);
		}
		catch (CustomerException e) {
			throw new EAITransactionException("Failed to update customer workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostDeleteCall(ICMSTrxValue trxValue, Message msg, String key) throws EAITransactionException {
		try {
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			proxy.hostDeleteCustomer((OBCMSCustomerTrxValue) trxValue);
		}
		catch (CustomerException e) {
			throw new EAITransactionException("Failed to delete customer workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	public String getOpDesc() {
		return ICMSConstant.ACTION_CREATE_CUSTOMER;
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_CUSTOMER;
	}

	protected boolean isCreate(Message msg, String key) {
		MainProfile mainProfile = ((CustomerMessageBody) ((EAIMessage) msg).getMsgBody()).getMainProfileDetails()
				.getMainProfile();

		return (mainProfile.getChangeIndicator().charValue() == CHANGEINDICATOR)
				&& (mainProfile.getUpdateStatusIndicator().charValue() == CREATEINDICATOR);
	}

	protected boolean isUpdate(Message msg, String key) {
		MainProfile mainProfile = ((CustomerMessageBody) ((EAIMessage) msg).getMsgBody()).getMainProfileDetails()
				.getMainProfile();
		return (mainProfile.getChangeIndicator().charValue() == CHANGEINDICATOR)
				&& (mainProfile.getUpdateStatusIndicator().charValue() == UPDATEINDICATOR);
	}

	protected boolean isDelete(Message msg, String key) {
		MainProfile mainProfile = ((CustomerMessageBody) ((EAIMessage) msg).getMsgBody()).getMainProfileDetails()
				.getMainProfile();
		return (mainProfile.getChangeIndicator().charValue() == CHANGEINDICATOR)
				&& (mainProfile.getUpdateStatusIndicator().charValue() == DELETEINDICATOR);
	}

}
