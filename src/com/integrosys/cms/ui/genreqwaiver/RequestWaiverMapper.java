/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genreqwaiver;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 09:54:02 $ Tag: $Name: $
 */

public class RequestWaiverMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public RequestWaiverMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "waiverReq", "com.integrosys.cms.app.generatereq.bus.IWaiverRequest", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		IWaiverRequest temp = (IWaiverRequest) map.get("waiverReq");
		if (temp == null) {
			throw new MapperException("waiverReq Session value is null");
		}
		GenerateReqWaiverForm aForm = (GenerateReqWaiverForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			temp.setProposedByName(aForm.getPropName());
			temp.setProposedByDate(DateUtil.convertDate(locale, aForm.getPropDate()));
			temp.setProposedByDesignation(aForm.getPropDesi());
			temp.setProposedBySignNo(aForm.getPropSignNo());

			temp.setSupportedByCoinNo(aForm.getSuppCoinNo());
			temp.setSupportedByDate(DateUtil.convertDate(locale, aForm.getSuppDate()));
			temp.setSupportedByDesignation(aForm.getSuppDesi());
			temp.setSupportedByName(aForm.getSuppName());

			temp.setApprovedBySCOCoinNo(aForm.getScoCoinNo());
			temp.setApprovedBySCODate(DateUtil.convertDate(locale, aForm.getScoDate()));
			temp.setApprovedBySCODesignation(aForm.getScoDesi());
			temp.setApprovedBySCOName(aForm.getScoName());

			temp.setApprovedByRCOCoinNo(aForm.getRcoCoinNo());
			temp.setApprovedByRCODate(DateUtil.convertDate(locale, aForm.getRcoDate()));
			temp.setApprovedByRCODesignation(aForm.getRcoDesi());
			temp.setApprovedByRCOName(aForm.getRcoName());

			temp.setApprovedByCCODate(DateUtil.convertDate(locale, aForm.getCcoDate()));
			temp.setApprovedByCCODesignation(aForm.getCcoDesi());
			temp.setApprovedByCCOName(aForm.getCcoName());

			temp.setName(aForm.getAppName());
			temp.setMinsOfMeeting(aForm.getMeetingMinutes());
			temp.setCreditCommittee(aForm.getCreditCommittee());
			temp.setMeetingDate(DateUtil.convertDate(locale, aForm.getMeetingDate()));
			temp.setReason(aForm.getWaiverReason());
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Waiber Obkect" + temp);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return temp;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		GenerateReqWaiverForm aForm = (GenerateReqWaiverForm) cForm;
		DefaultLogger.debug(this, "Form " + aForm);
		IWaiverRequest cert = (IWaiverRequest) obj;
		// DefaultLogger.debug(this,"Certificate  "+cert);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this, "Locale" + locale);
		try {
			if (cert != null) {

				aForm.setPropName(cert.getProposedByName());
				aForm.setPropDate(DateUtil.formatDate(locale, cert.getProposedByDate()));
				aForm.setPropDesi(cert.getProposedByDesignation());
				aForm.setPropSignNo(cert.getProposedBySignNo());

				aForm.setSuppCoinNo(cert.getSupportedByCoinNo());
				aForm.setSuppDate(DateUtil.formatDate(locale, cert.getSupportedByDate()));
				aForm.setSuppDesi(cert.getSupportedByDesignation());
				aForm.setSuppName(cert.getSupportedByName());

				aForm.setScoCoinNo(cert.getApprovedBySCOCoinNo());
				aForm.setScoDate(DateUtil.formatDate(locale, cert.getApprovedBySCODate()));
				aForm.setScoDesi(cert.getApprovedBySCODesignation());
				aForm.setScoName(cert.getApprovedBySCOName());

				aForm.setRcoCoinNo(cert.getApprovedByRCOCoinNo());
				aForm.setRcoDate(DateUtil.formatDate(locale, cert.getApprovedByRCODate()));
				aForm.setRcoDesi(cert.getApprovedByRCODesignation());
				aForm.setRcoName(cert.getApprovedByRCOName());

				aForm.setCcoDate(DateUtil.formatDate(locale, cert.getApprovedByCCODate()));
				aForm.setCcoDesi(cert.getApprovedByCCODesignation());
				aForm.setCcoName(cert.getApprovedByCCOName());

				aForm.setAppName(cert.getName());
				aForm.setMeetingMinutes(cert.getMinsOfMeeting());
				aForm.setCreditCommittee(cert.getCreditCommittee());
				aForm.setMeetingDate(DateUtil.formatDate(locale, cert.getMeetingDate()));
				aForm.setWaiverReason(cert.getReason());
				// cert. getRequestDescription().get
				// aForm.setWaiverReason(cert.;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
