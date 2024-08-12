/*
 * Created on Jul 17, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AvailableColAmtMapper extends AbstractCommonMapper {

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
	// this mapper is used when the user click add new apportionment button
	// we need to calculate the available collateral amount and set
	// priority ranking amount = available collateral amount
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		try {
			Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			SecApportionmentForm curForm = (SecApportionmentForm) cForm;
			HashMap mappingObj = (HashMap) obj;
			/*
			 * Double d = (Double)(mappingObj.get("availableCollateralAmt")); if
			 * (d != null) { double availableCollateralAmt = d.doubleValue();
			 * String currencyCode = (String)(mappingObj.get("fsvCurrency"));
			 * 
			 * String disp =
			 * ApportionSummaryViewHelper.addDot(roundUp(availableCollateralAmt
			 * )); curForm.setAvailableCollateralAmt(disp); if
			 * (curForm.getPriorityRankingAmt() == null) {
			 * curForm.setPriorityRankingAmt(disp); }
			 * 
			 * Amount amt = new Amount(availableCollateralAmt, currencyCode);
			 * //curForm
			 * .setAvailableCollateralAmt(CurrencyManager.convertToString
			 * (locale, amt));
			 * //curForm.setPriorityRankingAmt(CurrencyManager.convertToString
			 * (locale, amt)); curForm.setFsvCurrency(currencyCode); }
			 */
			curForm.setFsvCurrency((String) (mappingObj.get("fsvCurrency")));
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

	public static String roundUp(double d) {
		try {
			// return String.valueOf(Math.round(d * 100) / 100.0);
			return ApportionSummaryViewHelper.roundUp(d, 0);
		}
		catch (Exception ex) {
		}
		return "";
	}
}
