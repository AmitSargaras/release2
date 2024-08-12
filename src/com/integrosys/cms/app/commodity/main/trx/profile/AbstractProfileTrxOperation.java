/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/AbstractProfileTrxOperation.java,v 1.13 2006/11/01 09:31:24 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

// ofa
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.13 $
 * @since $Date: 2006/11/01 09:31:24 $ Tag: $Name: $
 */
public abstract class AbstractProfileTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	private IProfile[] mergeProfile(IProfile[] actualList, IProfile[] stagingList) throws TrxOperationException {

		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualList.length; i++) {
			actualMap.put(new Long(actualList[i].getCommonRef()), actualList[i]);
		}

		// get actual groupid if available
		long groupID = getGroupID(actualList);
		groupID = (groupID == ICMSConstant.LONG_INVALID_VALUE) ? getGroupID(stagingList) : groupID;

		for (int i = 0; i < stagingList.length; i++) {
			OBProfile staging = (OBProfile) stagingList[i];
			IProfile actual = (IProfile) actualMap.get(new Long(staging.getCommonRef()));
			if (actual == null) {
				staging.setProfileID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				staging.setProfileID(actual.getProfileID());
				staging.setVersionTime(actual.getVersionTime());
			}
			staging.setGroupID(groupID);
		}
		return stagingList;
	}

	private long getGroupID(IProfile[] profiles) {
		if (profiles != null) {
			for (int i = 0; i < profiles.length; i++) {
				if (profiles[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return profiles[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	protected IProfileTrxValue createStagingProfile(IProfileTrxValue anIProfileTrxValue) throws TrxOperationException {
		try {
			IProfile[] value = anIProfileTrxValue.getStagingProfile();
			value = synchronizeTrxValue(value, anIProfileTrxValue.getTransactionType(), anIProfileTrxValue
					.getSearchCriteria());
			fillInExtraInfo(value);
			anIProfileTrxValue.setStagingProfile(value);
			ICommodityMainInfo[] commodityInfoArray = getStagingBusManager().createInfo(
					anIProfileTrxValue.getStagingProfile());
			IProfile[] valueCreated = (IProfile[]) commodityInfoArray;

			anIProfileTrxValue.setStagingProfile(valueCreated);
			anIProfileTrxValue.setStagingReferenceID(String.valueOf(valueCreated[0].getGroupID()));

			return anIProfileTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	protected IProfileTrxValue updateActualProfile(IProfileTrxValue anIProfileTrxValue) throws TrxOperationException {
		try {
			IProfile[] staging = anIProfileTrxValue.getStagingProfile();
			IProfile[] actual = anIProfileTrxValue.getProfile();
			IProfile[] updActual = (IProfile[]) CommonUtil.deepClone(staging);
			if (actual != null) {
				updActual = mergeProfile(actual, updActual);
			}
			IProfile[] actualProfile = (IProfile[]) getBusManager().updateInfo(updActual);

			anIProfileTrxValue.setProfile(actualProfile);
			return anIProfileTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualProfile(): " + ex.toString());
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualProfile(): " + ex.toString());
		}
	}

	protected IProfileTrxValue updateProfileTransaction(IProfileTrxValue anIProfileTrxValue)
			throws TrxOperationException {
		try {
			anIProfileTrxValue = prepareTrxValue(anIProfileTrxValue);

			ICMSTrxValue tempValue = super.updateTransaction(anIProfileTrxValue);

			OBProfileTrxValue newValue = new OBProfileTrxValue(tempValue);
			newValue.setProfile(anIProfileTrxValue.getProfile());
			newValue.setStagingProfile(anIProfileTrxValue.getStagingProfile());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	protected IProfileTrxValue getProfileTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IProfileTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBProfileTrxValue: " + cex.toString());
		}
	}

	protected ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	protected ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	protected IProfileTrxValue prepareTrxValue(IProfileTrxValue aTrxValue) {
		if (aTrxValue != null) {
			IProfile[] actualValues = aTrxValue.getProfile();
			IProfile[] stagingValues = aTrxValue.getStagingProfile();

			// updates the transaction value with the correct reference id and
			// staging reference id
			// which in this case will be the group id which identifies a set of
			// profiles
			String referenceID = aTrxValue.getReferenceID();

			if ((actualValues != null) && (actualValues.length > 0)) {
				long gID = actualValues[0].getGroupID();
				if ((ICMSConstant.LONG_INVALID_VALUE != gID) && (gID != 0)) {
					referenceID = String.valueOf(gID);
				}
			}
			DefaultLogger.debug(this, "Refer ID : " + referenceID);
			aTrxValue.setReferenceID(referenceID);

			String stagingReferenceID = ((stagingValues != null) && (stagingValues.length > 0)) ? String
					.valueOf(stagingValues[0].getGroupID()) : null;
			aTrxValue.setStagingReferenceID(stagingReferenceID);

			return aTrxValue;
		}
		return null;
	}

	protected ITrxResult prepareResult(IProfileTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected IProfileTrxValue createTransaction(IProfileTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBProfileTrxValue newValue = new OBProfileTrxValue(tempValue);
			newValue.setProfile(value.getProfile());
			newValue.setStagingProfile(value.getStagingProfile());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	protected OBProfileTrxValue getProfileTrxValue(String trxType, ProfileSearchCriteria searchCriteria)
			throws CommodityException, TransactionException, RemoteException, TrxOperationException, SearchDAOException {
		ICMSTrxValue cmsTrxValue;
		ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
		SearchResult result = mgr.searchCommodityMainInfos(searchCriteria);
		IProfile[] actualProfiles = (IProfile[]) result.getResultList().toArray(new IProfile[0]);

		String category = searchCriteria.getCategory();
		DefaultLogger.debug(this, "- Category: " + category);

		IProfile[] goupProfiles = actualProfiles;
		if (((goupProfiles == null) || (goupProfiles.length == 0))
				&& ((category != null) && !category.trim().equals(""))) {
			DefaultLogger.debug(this, "Search with rough condition.");
			ProfileSearchCriteria aSearchCriteria = new ProfileSearchCriteria();
			aSearchCriteria.setCategory(category);
			goupProfiles = (IProfile[]) mgr.searchCommodityMainInfos(aSearchCriteria).getResultList().toArray(
					new IProfile[0]);
		}

		long actualRefID = getGroupID(goupProfiles);

		DefaultLogger.debug(this, " - GroupID : " + actualRefID);
		DefaultLogger.debug(this, " - TrxType : " + trxType);
		IProfile[] stagingProfile = null;
		ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();
		if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID), trxType);
			stagingProfile = (IProfile[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue.getStagingReferenceID(),
					ICommodityMainInfo.INFO_TYPE_PROFILE);
			stagingProfile = filterBySearchCriteria(stagingProfile, searchCriteria);
		}
		else if ((category == null) || category.trim().equals("")) {
			cmsTrxValue = new OBProfileTrxValue();
		}
		else {
			DefaultLogger.debug(this, "find trx for staging profile without actual");
			cmsTrxValue = new ProfileTrxDAO().getProfileTrxValue(true, category);
		}
		DefaultLogger.debug(this, " - Status : " + cmsTrxValue.getStatus());
		OBProfileTrxValue newValue = new OBProfileTrxValue(cmsTrxValue);
		DefaultLogger.debug(this, "ReferID : " + newValue.getReferenceID());
		DefaultLogger.debug(this, "StageReferID : " + newValue.getStagingReferenceID());
		if ((stagingProfile != null) && (stagingProfile.length > 0)) {
			newValue.setStagingProfile(stagingProfile);
		}
		if ((actualProfiles != null) && (actualProfiles.length > 0)) {
			newValue.setProfile(actualProfiles);
		}
		return newValue;
	}

	private IProfile[] filterBySearchCriteria(IProfile[] stagingProfile, ProfileSearchCriteria searchCriteria) {
		if ((stagingProfile == null) || (stagingProfile.length == 0)) {
			return null;
		}
		if (searchCriteria == null) {
			return stagingProfile;
		}
		ArrayList profileList = new ArrayList();
		for (int index = 0; index < stagingProfile.length; index++) {
			String tempStr = searchCriteria.getCategory();
			if (isNotEqual(tempStr, stagingProfile[index].getCategory())) {
				continue;
			}
			tempStr = searchCriteria.getProductSubType();
			if (isNotEqual(tempStr, stagingProfile[index].getProductSubType())) {
				continue;
			}
			tempStr = searchCriteria.getPriceType();
			if (isNotEqual(tempStr, stagingProfile[index].getPriceType())) {
				continue;
			}
			tempStr = searchCriteria.getNonRICCode();
			if (isNotEqual(tempStr, stagingProfile[index].getReuterSymbol())) {
				continue;
			}
			profileList.add(stagingProfile[index]);
		}
		DefaultLogger.debug(this, "Num before filter :" + stagingProfile.length);
		DefaultLogger.debug(this, "Num after filter :" + profileList.size());
		if (profileList.size() == 0) {
			return null;
		}
		else {
			return (IProfile[]) profileList.toArray(new IProfile[0]);
		}
	}

	private boolean isNotEqual(String val1, String val2) {
		return isNotEmpty(val1) && !val1.equals(val2);
	}

	private boolean isNotEmpty(String value) {
		if (value == null) {
			return false;
		}
		if (value.trim().equals("")) {
			return false;
		}
		return true;
	}

	protected long generateSeq(String sequenceName, boolean formated) throws CommodityException {
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

	private long generateGroupId() throws CommodityException {
		return generateSeq(CommodityConstant.SEQUENCE_PROFILE_GROUP_SEQ, true);
	}

	private long generateNonRICRefCode() throws CommodityException {
		return generateSeq(CommodityConstant.SEQUENCE_NON_RIC_SEQ, false);
	}

	private String getNonRICRefCode(String country, String category) throws CommodityException {
		long num = generateNonRICRefCode();
		return country + category + num;
	}

	private void fillInExtraInfo(IProfile[] array) throws CommodityException {
		if (array == null) {
			return;
		}
		long groupID = generateGroupId();
		for (int i = 0; i < array.length; i++) {
			OBProfile profile = (OBProfile) array[i];
			profile.setGroupID(groupID);
			if (IProfile.RIC_TYPE_NON.equals(array[i].getRICType()) && (profile.getReuterSymbol() == null)) {
				String refCode = getNonRICRefCode(profile.getCountryArea(), profile.getCategory());
				profile.setReuterSymbol(refCode);
			}
		}
	}

	private IProfile[] synchronizeTrxValue(IProfile[] profileArray, String trxType, ProfileSearchCriteria searchCriteria)
			throws Exception {
		if (searchCriteria == null) {
			DefaultLogger.debug(this, " Don't need synchronize.");
			return profileArray;
		}
		String category = searchCriteria.getCategory();
		DefaultLogger.debug(this, " Synchronize - Category : " + category);
		if (category != null) {
			ProfileSearchCriteria newCriteria = new ProfileSearchCriteria();
			newCriteria.setCategory(category);
			OBProfileTrxValue trxValue = getProfileTrxValue(trxType, newCriteria);
			IProfile[] existPArray = trxValue.getProfile();
			profileArray = mergeBaseOnCriteria(profileArray, existPArray, searchCriteria);
		}
		return profileArray;
	}

	private IProfile[] mergeBaseOnCriteria(IProfile[] displayPArray, IProfile[] existPArray,
			ProfileSearchCriteria criteria) {
		if ((existPArray == null) || (existPArray.length == 0)) {
			DefaultLogger.debug(this, " Num of exist : 0");
			return displayPArray;
		}
		if ((displayPArray == null) || (displayPArray.length == 0)) {
			DefaultLogger.debug(this, " Num of displayed : 0");
			return existPArray;
		}
		DefaultLogger.debug(this, " Num of exist : " + existPArray.length);
		DefaultLogger.debug(this, "Num of displayed : " + displayPArray.length);

		ArrayList profileList = new ArrayList();
		HashMap profileMap = new HashMap();
		for (int index = 0; index < displayPArray.length; index++) {
			if (displayPArray[index].getProfileID() != ICMSConstant.LONG_INVALID_VALUE) {
				profileMap.put(String.valueOf(displayPArray[index].getProfileID()), displayPArray[index]);
			}
			profileList.add(displayPArray[index]);
		}
		for (int index = 0; index < existPArray.length; index++) {
			String profileId = String.valueOf(existPArray[index].getProfileID());
			IProfile profile = (IProfile) profileMap.get(profileId);
			if (profile == null) {
				profileList.add(existPArray[index]);
			}
		}
		DefaultLogger.debug(this, "Num of total : " + profileList.size());
		return (IProfile[]) profileList.toArray(new IProfile[profileList.size()]);
	}
}