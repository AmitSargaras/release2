package com.integrosys.cms.host.eai.limit.trxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;

/**
 * <p>
 * Facility republish workflow handler
 * 
 * <p>
 * Facility master id will be populated in
 * {@link #prepareTrxValuesMap(Message, Message, Map)}, which the data has been
 * processed, rather than in {@link #getTransaction(Message, Map)}.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FacilityRepublishTrxHander extends FacilityTrxHandler {

	private static final long serialVersionUID = -9111920627656622659L;

	private boolean accessStpSystem = false;

	/**
	 * List of facility status to stp direct without user interference
	 */
	private List facilityStatusDirectStpList;

	public boolean isAccessStpSystem() {
		return accessStpSystem;
	}

	public void setAccessStpSystem(boolean accessStpSystem) {
		this.accessStpSystem = accessStpSystem;
	}

	public List getFacilityStatusDirectStpList() {
		return facilityStatusDirectStpList;
	}

	public void setFacilityStatusDirectStpList(List facilityStatusDirectStpList) {
		this.facilityStatusDirectStpList = facilityStatusDirectStpList;
	}

	public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException, EAIMessageException {
		EAIMessage eaiMessage = (EAIMessage) msg;
		AAMessageBody aaMsgBody = (AAMessageBody) eaiMessage.getMsgBody();

		LimitProfile limitProfile = aaMsgBody.getLimitProfile();
		String aaType = limitProfile.getAAType();

		if (facilityNotRequiredAppTypeList.contains(aaType)) {
			return flatMessage;
		}

		long limitProfileKey = limitProfile.getLimitProfileId();

		Map keyTrxValuesMap = new HashMap();

		List facilityMasterList = getFacilityProxy().retrieveListOfFacilityMasterByLimitProfileId(limitProfileKey);
		for (Iterator itr = facilityMasterList.iterator(); itr.hasNext();) {
			IFacilityMaster facilityMaster = (IFacilityMaster) itr.next();

			IFacilityTrxValue facilityTrxValue = getFacilityProxy().retrieveFacilityMasterTransactionById(
					facilityMaster.getId());

			keyTrxValuesMap.put(facilityMaster.getLimit().getLosLimitRef(), facilityTrxValue);
		}

		for (Iterator itr = aaMsgBody.getLimits().iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();
			FacilityMaster facility = limit.getFacilityMaster();

			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) keyTrxValuesMap.get(limit.getLimitGeneral()
					.getLOSLimitId());

			if ((facilityTrxValue == null) || StringUtils.isBlank(facilityTrxValue.getTransactionID())) {
				facilityTrxValue = new OBFacilityTrxValue();

				facilityTrxValue.setReferenceID(Long.toString(facility.getId()));

				IFacilityMaster facilityMaster = new OBFacilityMaster();
				facilityMaster.setId(facility.getId());

				facilityTrxValue.setFacilityMaster(facilityMaster);
				facilityTrxValue.setStatus(ICMSConstant.STATE_ND);

				keyTrxValuesMap.put(limit.getLimitGeneral().getLOSLimitId(), facilityTrxValue);
			}
		}

		flatMessage.put(getTrxKey(), keyTrxValuesMap);

		return flatMessage;
	}

	protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValuesMap) throws EAIMessageException {
		AAMessageBody aaMsgBody = (AAMessageBody) ((EAIMessage) msg).getMsgBody();
		AAMessageBody stgAaMsgBody = (AAMessageBody) ((EAIMessage) stagingMsg).getMsgBody();

		LimitProfile limitProfile = aaMsgBody.getLimitProfile();
		String aaType = limitProfile.getAAType();

		if (facilityNotRequiredAppTypeList.contains(aaType)) {
			return trxValuesMap;
		}

		Vector stagingLimits = stgAaMsgBody.getLimits();
		for (Iterator itr = stagingLimits.iterator(); itr.hasNext();) {
			Limits stagingLimit = (Limits) itr.next();
			FacilityMaster stagingFacility = stagingLimit.getFacilityMaster();

			IFacilityTrxValue trxValue = (IFacilityTrxValue) trxValuesMap.get(stagingLimit.getLimitGeneral()
					.getLOSLimitId());
			IFacilityMaster stagingFacilityMaster = new OBFacilityMaster();
			stagingFacilityMaster.setId(stagingFacility.getId());

			IFacilityMaster actualFaciltiyMaster = trxValue.getFacilityMaster();
			long actualId = findCmsFacilityIdByLimitRefInAAMessage(stagingLimit.getLimitGeneral().getLOSLimitId(),
					aaMsgBody);
			actualFaciltiyMaster.setId(actualId);

			trxValue.setReferenceID(Long.toString(actualId));

			trxValue.setStagingFacilityMaster(stagingFacilityMaster);
			trxValue.setStagingReferenceID(Long.toString(stagingFacilityMaster.getId()));

			populateCustomerAndAAInfo(trxValue, limitProfile);

			trxValuesMap.put(stagingLimit.getLimitGeneral().getLOSLimitId(), trxValue);
		}

		return trxValuesMap;
	}

	protected long findCmsFacilityIdByLimitRefInAAMessage(String limitRef, AAMessageBody aaMsgBody) {
		Vector limits = aaMsgBody.getLimits();
		for (Iterator itr = limits.iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();
			if (limitRef.equals(limit.getLimitGeneral().getLOSLimitId())) {
				return limit.getFacilityMaster().getId();
			}
		}

		throw new NoSuchLimitException("Limit, Id [" + limitRef + "] not found in the message object");
	}

}
