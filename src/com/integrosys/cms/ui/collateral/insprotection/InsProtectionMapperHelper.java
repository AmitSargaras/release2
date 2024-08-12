/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/InsProtectionMapperHelper.java,v 1.3 2003/08/21 13:50:23 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.ui.collateral.insprotection.inscrdt.InsCrdtForm;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/21 13:50:23 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class InsProtectionMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		InsProtectionForm aForm = (InsProtectionForm) cForm;
		IInsuranceCollateral iObj = (IInsuranceCollateral) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		try{
			
			if(!AbstractCommonMapper.isEmptyOrNull(aForm.getPremiumAmount())){
				iObj.setInsurancePremium(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getPremiumAmount()));
			}else{
				iObj.setInsurancePremium(null);
			}
			
		}catch (Exception e) {
			DefaultLogger.debug("InsProtectionMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		IInsuranceCollateral iObj = (IInsuranceCollateral) obj;
		InsProtectionForm aForm = (InsProtectionForm) cForm;
		boolean withCCY = false;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		try{
			if (iObj!=null&&iObj.getInsurancePremium() != null&&iObj.getInsurancePremium().getCurrencyCode()!=null) { 
				aForm.setPremiumAmount(UIUtil.formatAmount(iObj.getInsurancePremium(), 2, locale, withCCY));
			}
		}catch (Exception e) {
			DefaultLogger.debug("InsProtectionMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		
		return aForm;
	}

}
