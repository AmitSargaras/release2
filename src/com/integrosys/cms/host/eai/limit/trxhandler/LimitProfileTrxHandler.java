package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.EaiLimitProfileMessageException;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.support.LimitProfileHelper;

/**
 * @author marvin
 * @author Chong Jun Yong
 */
public class LimitProfileTrxHandler extends AbstractEaiLimitProfileTrxHandler {

	private static final long serialVersionUID = -4075309777087799363L;

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map originalTrxValue)
			throws EaiLimitProfileMessageException {

		LimitProfile limitProfile = ((AAMessageBody) ((EAIMessage) msg).getMsgBody()).getLimitProfile();
		LimitProfile stagingLimitProfile = ((AAMessageBody) ((EAIMessage) stagingMsg).getMsgBody()).getLimitProfile();
		Set set = originalTrxValue.keySet();

		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			OBLimitProfileTrxValue trxValue = (OBLimitProfileTrxValue) originalTrxValue.get(key);

			trxValue = prepareTrxValue(trxValue, limitProfile, key);

			trxValue = prepareStagingValue(trxValue, stagingLimitProfile, key);

			originalTrxValue.put(key, trxValue);
		}

		return originalTrxValue;
	}

	/**
	 * This method will set all necessary cusotmer details to
	 * OBLimitProfileTrxValue.
	 */
	protected OBLimitProfileTrxValue prepareTrxValue(OBLimitProfileTrxValue trxValue, LimitProfile limitProfile,
			String key) {

		OBTrxContext context = new OBTrxContext();

		OBCMSLegalEntity ent = new OBCMSLegalEntity();
		OBCMSCustomer cust = new OBCMSCustomer();
		cust.setCMSLegalEntity(ent);
		cust.setCustomerName(limitProfile.getCIFId());
		cust.setCustomerID(limitProfile.getLimitProfileId());
		context.setCustomer(cust);

		/**
		 * NOTE ** Have to set the following for preprocess check and
		 * AbstractCustomerTrxOperation preprocess check will use it to set the
		 * ParentReferanceID . Field used is CsutomerID from staging
		 * AbstractCUstomerTrxOperation will use it to overwrite the referanceID
		 * and StagingreferanceID. Field used is CustomerID from actual and
		 * staging.
		 */
		OBLimitProfile obLimitProfile = new OBLimitProfile();

		// CIFID not equals to Customer ID
		// obLimitProfile.setCustomerID(Long.parseLong(limitProfile.getCIFId()));
		obLimitProfile.setCustomerID(limitProfile.getCmsSubProfileId());

		obLimitProfile.setLimitProfileID(limitProfile.getLimitProfileId());
		trxValue.setLimitProfile(obLimitProfile);
		trxValue.setLimitProfileID(limitProfile.getLimitProfileId());
		trxValue.setLimitProfileReferenceNumber(limitProfile.getLOSAANumber());
		trxValue.setLegalID(limitProfile.getCIFId());
		trxValue.setLegalName(limitProfile.getCIFSource());
		trxValue.setReferenceID(limitProfile.getLimitProfileId() + "");
		trxValue.setTrxContext(context);

		return trxValue;
	}

	protected OBLimitProfileTrxValue prepareStagingValue(OBLimitProfileTrxValue trxValue, LimitProfile limitProfile,
			String key) {
		OBLimitProfile stagingLimitProfile = new OBLimitProfile();

		stagingLimitProfile.setCustomerID(limitProfile.getCmsSubProfileId());

		stagingLimitProfile.setLimitProfileID(limitProfile.getLimitProfileId());
		trxValue.setStagingLimitProfile(stagingLimitProfile);
		trxValue.setLegalID(limitProfile.getCIFId());
		trxValue.setLegalName(limitProfile.getCIFSource());
		trxValue.setReferenceID(limitProfile.getLimitProfileId() + "");

		return trxValue;
	}

	public String getTrxKey() {
		return IEaiConstant.LIMITPROFILE_KEY;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		HashMap hm = new HashMap();
		LimitProfileHelper hp = new LimitProfileHelper();

		try {
			LimitProfile lp = ((AAMessageBody) msg.getMsgBody()).getLimitProfile();
			ILimitProfileTrxValue lptv = new OBLimitProfileTrxValue();

			if (hp.isUpdate(lp) || hp.isDelete(lp)) {
				LimitProfile tmplp = (LimitProfile) getLimitDao().retrieve(new Long(lp.getLimitProfileId()),
						LimitProfile.class);

				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				lptv = limitProxy.getTrxLimitProfile(tmplp.getLimitProfileId());
			}

			String key = hp.constructLimitProfileKey(lp.getCIFId(), lp.getSubProfileId(), lp.getLimitProfileId());

			hm.put(key, lptv);
			flatMessage.put(getTrxKey(), hm);

			return flatMessage;
		}
		catch (Exception le) {
			throw new EAITransactionException("Caught Exception while getting Transaction", le);
		}
	}

	protected void hostCreateCall(ICMSTrxValue trxValue, Message msg) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();

			limitProxy.createLimitProfile((ILimitProfileTrxValue) trxValue);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to create limit profile workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostUpdateCall(ICMSTrxValue trxValue, Message msg, String key) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue lptv = limitProxy.getTrxLimitProfile(trxValue.getLimitProfileID());

			limitProxy.hostUpdateLimitProfile(trxValue.getTrxContext(), lptv);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to update limit profile workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostDeleteCall(ICMSTrxValue trxValue, Message msg, String keyStr) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue lptv = limitProxy.getTrxLimitProfile(trxValue.getLimitProfileID());

			limitProxy.systemDeleteLimitProfile(lptv);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to delete limit profile workflow object, trx [" + trxValue
					+ "], msg header info [" + msg.getMsgHeader() + "]", e);
		}
	}

	public String getOpDesc() {
		return ICMSConstant.ACTION_CREATE_CUSTOMER;
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_HOST_UPDATE_LIMIT_PROFILE;
	}

	protected boolean isCreate(Message msg) {
		LimitProfile limitProfile = ((AAMessageBody) ((EAIMessage) msg).getMsgBody()).getLimitProfile();
		return (limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR))
				&& (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR))));
	}

	protected boolean isUpdate(Message msg) {
		LimitProfile limitProfile = ((AAMessageBody) ((EAIMessage) msg).getMsgBody()).getLimitProfile();

		return (limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR))
				&& (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))));
	}

	protected boolean isDelete(Message msg) {
		LimitProfile limitProfile = ((AAMessageBody) ((EAIMessage) msg).getMsgBody()).getLimitProfile();

		return (limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR))
				&& (limitProfile.getUpdateStatusIndicator().equals(String.valueOf( DELETEINDICATOR))));
	}
}
