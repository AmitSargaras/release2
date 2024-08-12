package com.integrosys.cms.host.eai.customer.actualtrxhandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.CreditGrade;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.CustomerMessageBody;
import com.integrosys.cms.host.eai.customer.DisplayCustomer;
import com.integrosys.cms.host.eai.customer.MainProfileDetails;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.CustomerAddress;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.host.eai.customer.bus.CustomerNatureOfBiz;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class MainProfileActualTrxHandler extends AbstractCommonActualTrxHandler {

	protected static String[] fixpk = new String[] { IEaiConstant.CMSID, IEaiConstant.CIFID };

	private ICustomerDao customerDao;

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public Message persistActualTrx(Message msg) {

		MainProfileDetails mpd = ((CustomerMessageBody) msg.getMsgBody()).getMainProfileDetails();

		// To support Tagging Message Acknowledgement message
		// If both UpdateStatusIndicator & ChangeIndicator are empty , skip
		// operation .
		if (StringUtils.isEmpty("" + mpd.getMainProfile().getUpdateStatusIndicator())
				&& StringUtils.isEmpty("" + mpd.getMainProfile().getChangeIndicator())) {

			logger.info("Tagging Acknowledgement Message , skipping persisting operation.");
			return msg;
		}

		String source = mpd.getMainProfile().getSource();

		Vector idInfoVector = mpd.getMainProfile().getIdInfo();
		if (idInfoVector != null) {
			for (int i = 0; i < idInfoVector.size(); i++) {
				CustomerIdInfo idInfo = (CustomerIdInfo) idInfoVector.get(i);
				switch (i) {
				case 0:
					mpd.getMainProfile().setIdInfo1(idInfo);
					break;
				case 1:
					mpd.getMainProfile().setIdInfo2(idInfo);
					break;
				case 2:
					mpd.getMainProfile().setIdInfo3(idInfo);
					break;
				}
			}
		}
		// Store main profile and sub profile

		mpd = storeMainProfile(mpd, source);

		deleteExistingAddress(mpd.getMainProfile().getCIFId());

		deleteExistingCreditGrade(mpd.getMainProfile().getCIFId());

		deleteExistingNatureOfBiz(mpd.getMainProfile().getCIFId());

		Vector addressVector = mpd.getMainProfile().getAddress();

		if (addressVector == null) {
			addressVector = new Vector();
		}
		for (int i = 0; i < addressVector.size(); i++) {
			CustomerAddress address = (CustomerAddress) addressVector.get(i);
			address.setCmsCIFId(mpd.getMainProfile().getCmsId());
			address.setCIFId(mpd.getMainProfile().getCIFId());
			storeRegisteredAddress(address);

		}
		Vector creditGradeVector = mpd.getCreditGrade();
		if (creditGradeVector != null) {
			for (int i = 0; i < creditGradeVector.size(); i++) {
				CreditGrade creditGrade = (CreditGrade) creditGradeVector.get(i);
				creditGrade.setCmsMainProfileId(mpd.getMainProfile().getCmsId());
				creditGrade.setCIFId(mpd.getMainProfile().getCIFId());
				storeCreditGrade(creditGrade);
			}
		}

		CustomerNatureOfBiz natureOfBiz = new CustomerNatureOfBiz();
		natureOfBiz.setCmsMainProfileId(mpd.getMainProfile().getCmsId());
		natureOfBiz.setCIFId(mpd.getMainProfile().getCIFId());
		natureOfBiz.setNatureOfBiz(mpd.getMainProfile().getNatureOfBiz());
		storeNatureOfBiz(natureOfBiz);

		return msg;
	}

	private void deleteExistingNatureOfBiz(String CIFid) {
		Map parameters = new HashMap();
		parameters.put("CIFId", CIFid);
		List obj = getCustomerDao().retrieveObjectListByParameters(parameters, CustomerNatureOfBiz.class);
		for (int j = 0; j < obj.size(); j++) {
			CustomerNatureOfBiz cg = (CustomerNatureOfBiz) obj.get(j);
			getCustomerDao().remove(cg, CustomerNatureOfBiz.class);
		}
	}

	private void deleteExistingCreditGrade(String CIFid) {
		Map parameters = new HashMap();
		parameters.put("CIFId", CIFid);
		List obj = getCustomerDao().retrieveObjectsListByParameters(parameters, CreditGrade.class);
		for (int j = 0; j < obj.size(); j++) {
			CreditGrade cg = (CreditGrade) obj.get(j);
			getCustomerDao().remove(cg, CreditGrade.class);
		}
	}

	private void deleteExistingAddress(String CIFid) {
		Map parameters = new HashMap();
		parameters.put("CIFId", CIFid);
		List obj = getCustomerDao().retrieveObjectsListByParameters(parameters, CustomerAddress.class);
		Iterator addressIter = obj.iterator();
		while (addressIter.hasNext()) {
			CustomerAddress ca = (CustomerAddress) addressIter.next();
			getCustomerDao().remove(ca, CustomerAddress.class);
		}
	}

	/**
	 * the following will store the MainProfile base on the ChangeIndicator and
	 * UPdateIndicator. condition 1 : if changeIndicator is 'Y' and
	 * updateIndicator is 'C'. The system will do create . condition 2 : if
	 * changeIndicator is 'Y' and updateIndicator is 'U' or 'D' . The System
	 * will do update condition 3 : If changeIndicator is 'N' . Just load the
	 * CMSID(table's primary key). Main Profile cms details needs to be loaded
	 * because other component rely on it.
	 * 
	 */
	protected MainProfileDetails storeMainProfile(MainProfileDetails mpd, String source) {

		String origCountry = CommonDataSingleton.getCodeCategoryCountryByValue("37", source);
		Validate.notNull(origCountry, "Invalid Origin Country, when processing Main Profile CIF["
				+ mpd.getMainProfile().getCIFId() + "]");

		MainProfile mp = mpd.getMainProfile();
		DisplayCustomer.getInstance().displayMainProfile(mp);

		// Check if exists in DB, then perform Insert/Update
		logger.debug("Checking Customer CIF Id [" + mp.getCIFId() + "] : Source [" + source + "]");

		// retrieve MainProfile
		Map mpKeyParameters = new HashMap();
		mpKeyParameters.put("CIFId", mp.getCIFId());
		mpKeyParameters.put("source", source);

		MainProfile tmpMainProfile = (MainProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(
				mpKeyParameters, "updateStatusIndicator", MainProfile.class);

		// Setting back the indicator for usage of subsequent callers
		if (tmpMainProfile == null) {
			logger.warn("Customer records not found for CIF id [" + mp.getCIFId() + "], creating the records");
			mp.setChangeIndicator(new Character(CHANGEINDICATOR));
			mp.setUpdateStatusIndicator(new Character(CREATEINDICATOR));
		}
		else {
			mp.setCmsId(tmpMainProfile.getCmsId());
			mp.setChangeIndicator(new Character(CHANGEINDICATOR));
			mp.setUpdateStatusIndicator(new Character(UPDATEINDICATOR));
		}

		if (mp.getJDOIncorporationDate() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(mp.getJDOIncorporationDate());

			if (cal.get(Calendar.YEAR) < 1800) {
				mp.setIncorporationDate(null);
				mp.setJDOIncorporationDate(null);
			}
		}

		if ((mp.getChangeIndicator().charValue() == CHANGEINDICATOR)
				&& (mp.getUpdateStatusIndicator().charValue() == CREATEINDICATOR)) {

			Long mainProfileKey = (Long) getCustomerDao().store(mp, MainProfile.class);
			mp.setCmsId(mainProfileKey.longValue());

			SubProfile subProfile = new SubProfile();
			subProfile.setFIIndicator(mp.getFIIndicator());
			subProfile.setCifId(mp.getCIFId());
			subProfile.setSubProfileName(mp.getCustomerNameShort());
			subProfile.setCmsMainProfileId(mainProfileKey.longValue());
			subProfile.setSubProfileId(Long.parseLong(IEaiConstant.SUBPROFILE_ID));
			subProfile.setOriginatingCountry(mp.getDomicileCountry());
			subProfile.setDomicileCountry(mp.getDomicileCountry());
			if (mp.getCustomerBranch() != null) {
				subProfile.setOriginatingOrganisation(mp.getCustomerBranch().getStandardCodeValue());
			}
			subProfile.setNonBorrowerIndicator(ICMSConstant.TRUE_VALUE.charAt(0));
			subProfile.setUpdateStatusIndicator(new Character(CREATEINDICATOR));
			subProfile.setChangeIndicator(new Character(CHANGEINDICATOR));

			Long subProfileKey = (Long) getCustomerDao().store(subProfile, SubProfile.class);
			mp.setSubProfilePrimaryKey(subProfileKey.longValue());
		}
		else if ((mp.getChangeIndicator().charValue() == CHANGEINDICATOR)
				&& ((mp.getUpdateStatusIndicator().charValue() == UPDATEINDICATOR) || (mp.getUpdateStatusIndicator()
						.charValue() == DELETEINDICATOR))) {
			logger.debug("CMS ID [" + mp.getCmsId() + "] CIF ID [" + mp.getCIFId() + "] Cust name short ["
					+ mp.getCustomerNameShort() + "] Cust name long [" + mp.getCustomerNameLong() + "]");

			MainProfile tmpmp = tmpMainProfile;
			tmpmp.setFIIndicator(mp.getFIIndicator());

			Map subProfileParameters = new HashMap();
			subProfileParameters.put("cmsMainProfileId", new Long(tmpmp.getCmsId()));

			SubProfile subProfile = (SubProfile) getCustomerDao().retrieveNonDeletedObjectByParameters(
					subProfileParameters, "updateStatusIndicator", SubProfile.class);

			if (subProfile == null) {
				throw new NoSuchCustomerException(tmpmp.getCIFId(), tmpmp.getSource());
			}

			subProfile.setFIIndicator(mp.getFIIndicator());
			subProfile.setSubProfileName(mp.getCustomerNameShort());
			subProfile.setCmsMainProfileId(tmpmp.getCmsId());
			subProfile.setSubProfileId(Long.parseLong(IEaiConstant.SUBPROFILE_ID));
			subProfile.setChangeIndicator(mp.getChangeIndicator());
			subProfile.setUpdateStatusIndicator(mp.getUpdateStatusIndicator());
			subProfile.setCifId(mp.getCIFId());
			subProfile.setOriginatingCountry(mp.getDomicileCountry());
			subProfile.setDomicileCountry(mp.getDomicileCountry());
			subProfile.setOriginatingOrganisation(mp.getCustomerBranch().getStandardCodeValue());

			getCustomerDao().update(subProfile, SubProfile.class);

			mp.setSubProfilePrimaryKey(subProfile.getCmsId());

			AccessorUtil.copyValue(mp, tmpmp, fixpk); // copy value to update
			tmpmp.setIdInfo1(null);
			tmpmp.setIdInfo2(null);
			tmpmp.setIdInfo3(null);
			getCustomerDao().update(tmpmp, MainProfile.class);
			tmpmp.setIdInfo1(mp.getIdInfo1());
			tmpmp.setIdInfo2(mp.getIdInfo2());
			tmpmp.setIdInfo3(mp.getIdInfo3());
			getCustomerDao().update(tmpmp, MainProfile.class);
		}

		mpd.setMainProfile(mp);

		return mpd;
	}

	protected CustomerNatureOfBiz storeNatureOfBiz(CustomerNatureOfBiz natureOfBiz) {
		Long id = (Long) getCustomerDao().store(natureOfBiz, CustomerNatureOfBiz.class);
		natureOfBiz.setCmsId(id.longValue());
		return natureOfBiz;
	}

	protected CreditGrade storeCreditGrade(CreditGrade creditGrade) {

		Long id = (Long) getCustomerDao().store(creditGrade, CreditGrade.class);
		creditGrade.setCmsId(id.longValue());
		return creditGrade;
	}

	protected CustomerAddress storeRegisteredAddress(CustomerAddress customerAddress) {

		Long id = (Long) getCustomerDao().store(customerAddress, CustomerAddress.class);
		customerAddress.setCmsId(id.longValue());
		return customerAddress;
	}

	public String getTrxKey() {
		return IEaiConstant.CUSTOMER_KEY;
	}

}
