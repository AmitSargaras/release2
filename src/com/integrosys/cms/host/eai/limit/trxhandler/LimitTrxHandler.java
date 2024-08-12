package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.EAICustomerMessageException;
import com.integrosys.cms.host.eai.customer.trxhandler.AbstractEaiCustomerTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.limit.support.LimitProfileHelper;

public class LimitTrxHandler extends AbstractEaiCustomerTrxHandler {

	private static final long serialVersionUID = 557124344826909784L;

	/**
	 * Lastest changes add a HashMap into flatMessage. HashMap will contain a
	 * multiple Limits. The Key will be <limitkey> + "|" + <leid> + "|" +
	 * <limitid>. Refer to LimitHelper.constructLimitKey().
	 * @see com.integrosys.cms.host.message.castor.eai.limit.support.sci.customer.LimitHelper
	 */
	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException {
		LimitHelper hp = new LimitHelper();
		HashMap limitMap = new HashMap();

		try {
			AAMessageBody aaMessageBody = ((AAMessageBody) ((EAIMessage) msg).getMsgBody());
			Vector vecLimit = aaMessageBody.getLimits();
			if (!hp.anyLimit(vecLimit)) {
				vecLimit = new Vector();
			}
			for (Iterator iter = vecLimit.iterator(); iter.hasNext();) {
				ILimitTrxValue ltv = new OBLimitTrxValue();
				Limits lmts = (Limits) iter.next();
				if (hp.isUpdate(lmts.getLimitGeneral()) || hp.isDelete(lmts.getLimitGeneral())) {

					ILimitProxy limitProxy = LimitProxyFactory.getProxy();
					ltv = limitProxy.getTrxLimit(lmts.getLimitGeneral().getCmsId());

				}
				String mapKey = hp.constructLimitKey(lmts.getLimitGeneral().getCIFId(), lmts.getLimitGeneral()
						.getSubProfileId(), lmts.getLimitGeneral().getCmsLimitProfileId(), lmts.getLimitGeneral()
						.getLOSLimitId());
				limitMap.put(mapKey, ltv);
			}

			flatMessage.put(getTrxKey(), limitMap);
			return flatMessage;

		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to retrieve limit workflow value, msg header info ["
					+ msg.getMsgHeader() + "]", e);
		}
	}

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map originalTrxValue) throws EAIMessageException {

		// Actual
		AAMessageBody aaMessageBody = ((AAMessageBody) ((EAIMessage) msg).getMsgBody());
		LimitProfile limitProfile = aaMessageBody.getLimitProfile();
		Vector limits = ((AAMessageBody) msg.getMsgBody()).getLimits();

		// Staging
		AAMessageBody stagingMessageBody = ((AAMessageBody) ((EAIMessage) stagingMsg).getMsgBody());
		Vector stagingLimits = stagingMessageBody.getLimits();

		Set set = originalTrxValue.keySet();

		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			OBLimitTrxValue trxValue = (OBLimitTrxValue) originalTrxValue.get(key);

			/**
			 * NOTE ** Have to set the follow for preprocess check and
			 * AbstractLimitTrxOperation preprocess check will use it to set the
			 * ParentReferanceID . Field used is LimitID from staging
			 * AbstractLimitTrxOperation will use it to overwrite the
			 * referanceID and StagingreferanceID. Field used is LimitID from
			 * actual and staging.
			 */
			prepareStagingValue(stagingLimits, trxValue, key);
			prepareTrxValue(limitProfile, limits, trxValue, key);

			originalTrxValue.put(key, trxValue);

		}

		return originalTrxValue;
	}

	/**
	 * @param trxValue A Collection of OBLimitTrxValue.
	 * @return Vector a collection of OBLimitTrxValue
	 */
	private OBLimitTrxValue prepareTrxValue(LimitProfile limitProfile, Vector vecLimit, OBLimitTrxValue trxValue,
			String key) throws EAICustomerMessageException {
		LimitHelper hp = new LimitHelper();
		LimitProfileHelper lphp = new LimitProfileHelper();
		OBTrxContext context = new OBTrxContext();

		String leid = hp.extractCIFID(key);
		long subprofileid = limitProfile.getSubProfileId();
		long limitprofileid = hp.extractLimitProfileId(key);
		String limitid = hp.extractLimitId(key);

		Limits limit = hp.getLimit(vecLimit, leid, subprofileid, limitprofileid, limitid);

		LimitProfile lp = lphp.getLimitProfile(limitProfile, leid, subprofileid, limitprofileid);

		OBCMSCustomer cust = new OBCMSCustomer();

		cust.setCustomerID(limitProfile.getCmsSubProfileId());

		context.setCustomer(cust);

		OBLimit obl = new OBLimit();
		obl.setLimitProfileID(lp.getLimitProfileId());
		obl.setLimitID(limit.getLimitGeneral().getCmsId());
		trxValue.setLimit(obl);
		trxValue.setLegalID(lp.getCIFId());
		trxValue.setLimitProfileID(lp.getLimitProfileId());
		trxValue.setLimitProfileReferenceNumber(lp.getLOSAANumber());

		if (lp.getOriginatingLocation() != null) {
			context.setTrxCountryOrigin(lp.getOriginatingLocation().getOriginatingLocationCountry());
			context.setTrxOrganisationOrigin(lp.getOriginatingLocation().getOriginatingLocationOrganisation());
		}
		else {
			logger.info("Originating Location is null!");
		}

		trxValue.setTrxContext(context);
		trxValue.setReferenceID(Long.toString(limit.getLimitGeneral().getCmsId()));

		return trxValue;
	}

	/**
	 * @param key contain a static String , leid and limitid (e.g.
	 *        "LimitKey~10000800400~12345");
	 */
	private OBLimitTrxValue prepareStagingValue(Vector vecLimit, OBLimitTrxValue trxValue, String key)
			throws EAICustomerMessageException {
		LimitHelper hp = new LimitHelper();

		String leid = hp.extractCIFID(key);
		String limitId = hp.extractLimitId(key);
		long subprofileid = hp.extractSubProfileId(key);
		long limitprofileid = hp.extractLimitProfileId(key);

		Limits stagingL = hp.getLimit(vecLimit, leid, subprofileid, limitprofileid, limitId);
		OBLimit stagingOBL = new OBLimit();
		stagingOBL.setLimitProfileID(trxValue.getLimitProfileID());
		stagingOBL.setLimitID(stagingL.getLimitGeneral().getCmsId());
		
		trxValue.setStagingLimit(stagingOBL);

		trxValue.setStagingReferenceID(Long.toString(stagingL.getLimitGeneral().getCmsId()));

		return trxValue;
	}

	public String getTrxKey() {
		return IEaiConstant.LIMIT_KEY;
	}

	protected void hostCreateCall(ICMSTrxValue trxValue, Message msg) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			limitProxy.createLimit((OBLimitTrxValue) trxValue);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to process create limit workflow, trx [" + trxValue
					+ "] message header [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostUpdateCall(ICMSTrxValue trxValue, Message msg, String key) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			limitProxy.hostUpdateLimit(trxValue.getTrxContext(), (OBLimitTrxValue) trxValue);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to process update limit workflow, trx [" + trxValue
					+ "] message header [" + msg.getMsgHeader() + "]", e);
		}
	}

	protected void hostDeleteCall(ICMSTrxValue trxValue, Message msg, String key) throws EAITransactionException {
		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			limitProxy.systemDeleteLimit((OBLimitTrxValue) trxValue);
		}
		catch (LimitException e) {
			throw new EAITransactionException("Failed to process delete limit workflow, trx [" + trxValue
					+ "] message header [" + msg.getMsgHeader() + "]", e);
		}
	}

	public String getOpDesc() {
		return ICMSConstant.ACTION_HOST_UPDATE_LIMIT;
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_HOST_UPDATE_LIMIT;
	}

	public boolean isCreate(Message msg, String key) {

		AAMessageBody aaMessageBody = ((AAMessageBody) ((EAIMessage) msg).getMsgBody());
		Vector veclimit = aaMessageBody.getLimits();
		LimitHelper hp = new LimitHelper();

		String leid = hp.extractCIFID(key);
		String limitid = hp.extractLimitId(key);
		long subprofileid = hp.extractSubProfileId(key);
		long limitprofileid = hp.extractLimitProfileId(key);
		Limits limit = hp.getLimit(veclimit, leid, subprofileid, limitprofileid, limitid);

		return (limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf( CHANGEINDICATOR))
				&& (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf( CREATEINDICATOR))));
	}

	public boolean isUpdate(Message msg, String key) {
		AAMessageBody aaMessageBody = ((AAMessageBody) ((EAIMessage) msg).getMsgBody());
		Vector veclimit = aaMessageBody.getLimits();
		LimitHelper hp = new LimitHelper();

		String leid = hp.extractCIFID(key);
		String limitid = hp.extractLimitId(key);
		long subprofileid = hp.extractSubProfileId(key);
		long limitprofileid = hp.extractLimitProfileId(key);
		Limits limit = hp.getLimit(veclimit, leid, subprofileid, limitprofileid, limitid);

		return (limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR))
				&& (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))));
	}

	public boolean isDelete(Message msg, String key) {
		AAMessageBody aaMessageBody = ((AAMessageBody) ((EAIMessage) msg).getMsgBody());
		Vector veclimit = aaMessageBody.getLimits();
		LimitHelper hp = new LimitHelper();

		String leid = hp.extractCIFID(key);
		String limitid = hp.extractLimitId(key);
		long subprofileid = hp.extractSubProfileId(key);
		long limitprofileid = hp.extractLimitProfileId(key);
		Limits limit = hp.getLimit(veclimit, leid, subprofileid, limitprofileid, limitid);

		return (limit.getLimitGeneral().getChangeIndicator() .equals(String.valueOf( CHANGEINDICATOR))
				&& (limit.getLimitGeneral().getUpdateStatusIndicator() .equals(String.valueOf( DELETEINDICATOR))));
	}

}
