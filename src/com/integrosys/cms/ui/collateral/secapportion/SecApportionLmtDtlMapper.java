/*
 * Created on Jul 17, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionLmtDtlMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE } };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		try {
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			OBSecApportionmentDtl apportionDtl = (OBSecApportionmentDtl) obj;
			SecApportionmentForm curForm = (SecApportionmentForm) cForm;
			curForm.setLeId(apportionDtl.getLeId());
			curForm.setLeName(apportionDtl.getLeName());
			curForm.setSubProfileId(apportionDtl.getSubProfileId());

			if (apportionDtl.getLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
				curForm.setLimitId(String.valueOf(apportionDtl.getLimitID()));
			}
			curForm.setProductDesc(apportionDtl.getProductDesc());
			if ((apportionDtl.getApprovedLimitAmt() != null) && (apportionDtl.getApprovedLimitCcy() != null)) {
				Amount amt = new Amount(Double.parseDouble(apportionDtl.getApprovedLimitAmt()), apportionDtl
						.getApprovedLimitCcy());
				curForm.setApprovedLimitAmt(CurrencyManager.convertToString(locale, amt));
				curForm.setApprovedLimitCcy(apportionDtl.getApprovedLimitCcy());
			}
			if ((apportionDtl.getActivatedLimitAmt() != null) && (apportionDtl.getActivatedLimitAmt() != null)) {
				Amount amt = new Amount(Double.parseDouble(apportionDtl.getActivatedLimitAmt()), apportionDtl
						.getActivatedLimitCcy());
				curForm.setActivatedLimitAmt(CurrencyManager.convertToString(locale, amt));
				curForm.setActivatedLimitCcy(apportionDtl.getActivatedLimitCcy());
			}
			return curForm;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys
	 * .base.uiinfra.common.CommonForm, java.util.HashMap)
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		// TODO Auto-generated method stub
		return null;
	}

}
