/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealMapper
 *
 * Created on 3:17:12 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.predeal.bus.OBPreDeal;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 21, 2007 Time: 3:17:12 PM
 */
public class PreDealMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
		OBPreDeal ob = new OBPreDeal();
		PreDealForm form = (PreDealForm) commonForm;
		Locale locale = Locale.getDefault(); // (Locale) inputs .get(
												// com.integrosys
												// .base.uiinfra.common
												// .Constants.GLOBAL_LOCALE_KEY
												// ) ;

		ob.setAaNumber(form.getAaNumber());
		ob.setAccountNo(form.getAccountNo());
		ob.setBranchCode(form.getBranchCode());
		ob.setBranchName(form.getBranchName());
		ob.setCifNo(form.getCifNumber());
		ob.setCustomerName(form.getCustomerName());
		ob.setSourceSystem(form.getSourceSystem());
		ob.setSecurityId(form.getSecurityId());
		ob.setWaiveApproveInd(form.getWaiveApproveInd());
		ob.setInfoCorrectInd(form.getInfoCorrectInd());
		ob.setInfoCorrectIndStr(form.getInfoCorrectIndStr());
		ob.setReleaseStatus(form.getReleaseStatus());
		ob.setInfoIncorrectDetails(form.getInfoIncorrectDetails());
		ob.setHoldingInd(form.getHoldingInd());
		ob.setStatus(form.getStatus());
		ob.setPurposeOfEarmarking(form.getPurposeOfEarmarking());

		try {
			ob.setEarMarkingDate(DateUtil.convertDate(locale, form.getEarMarkingDate()));
		}
		catch (Exception e) {
			// e.printStackTrace ();
		}

		try {
			if (form.getEarMarkId() != null) {
				ob.setEarMarkId(new Long(form.getEarMarkId()));
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (form.getEarMarkUnits() != null) {
				ob.setEarMarkUnits(Long.parseLong(form.getEarMarkUnits()));
			}
		}
		catch (NumberFormatException e) {
			// e.printStackTrace ();
		}

		try {
			if (form.getFeedId() != null) {
				ob.setFeedId(Long.parseLong(form.getFeedId()));
			}
		}
		catch (NumberFormatException e) {
			// e.printStackTrace ();
		}

		// try
		// {
		// ob.setEarMarkGroupId ( Long.parseLong ( form.getEarMarkGroupId () ) )
		// ;
		// }
		// catch ( NumberFormatException e )
		// {
		// // e.printStackTrace ();
		// }

		return ob;
	}

	public CommonForm mapOBToForm(CommonForm commonForm, Object object, HashMap inputs) throws MapperException {
		OBPreDeal ob = (OBPreDeal) object;
		PreDealForm form = (PreDealForm) commonForm;
		Locale locale = Locale.getDefault(); // (Locale) inputs .get(
												// com.integrosys
												// .base.uiinfra.common
												// .Constants.GLOBAL_LOCALE_KEY
												// ) ;

		form.setAaNumber(ob.getAaNumber());
		form.setAccountNo(ob.getAccountNo());
		form.setBranchCode(ob.getBranchCode());
		form.setBranchName(ob.getBranchName());
		form.setCifNumber(ob.getCifNo());
		form.setCustomerName(ob.getCustomerName());
		form.setEarMarkUnits(String.valueOf(ob.getEarMarkUnits()));
		form.setFeedId(String.valueOf(ob.getFeedId()));
		// form.setEarMarkGroupId ( String.valueOf ( ob.getEarMarkGroupId () ) )
		// ;
		form.setSourceSystem(ob.getSourceSystem());
		form.setSecurityId(ob.getSecurityId());
		form.setWaiveApproveInd(ob.getWaiveApproveInd());
		form.setInfoCorrectInd(ob.getInfoCorrectInd());
		form.setInfoCorrectIndStr(ob.getInfoCorrectIndStr());
		form.setReleaseStatus(ob.getReleaseStatus());
		form.setInfoIncorrectDetails(ob.getInfoIncorrectDetails());
		form.setHoldingInd(ob.getHoldingInd());
		form.setStatus(ob.getStatus());
		form.setPurposeOfEarmarking(ob.getPurposeOfEarmarking());

		try {
			form.setEarMarkingDate(DateUtil.convertToDisplayDate(ob.getEarMarkingDate()));
		}
		catch (Exception e) {
			// e.printStackTrace ();
		}

		form.setEarMarkId(String.valueOf(ob.getEarMarkId()));

		return form;
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE } };
	}
}
