/**
* Copyright Integro Technologies Pte Ltd
*/
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.text.NumberFormat;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;
import com.integrosys.cms.ui.creditriskparam.internallimit.list.InternalLmtParamAction;

/**
* Describe this class.
* Purpose: Map the form to OB or OB to form for Internal Limit Parameter
* Description: Map the value from database to the screen or from the screen that user key in to database
*
* @author $Author: Grace Teh$<br>
* @version $Revision:$
* @since $Date: 28/05/2008$
* Tag: $Name$
*/

public class InternalLmtParameterMapper extends AbstractCommonMapper {
	
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	                {ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE},
	            }
	         );

	    }

	public CommonForm mapOBToForm(CommonForm aForm, Object object,HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");
		InternalLmtParameterForm form = (InternalLmtParameterForm) aForm;
		DefaultLogger.debug(this, "inside mapOBToForm, InternalLmtParameterForm1111::" + form);
		ArrayList entryList = (ArrayList) object;
		if (entryList == null || entryList.size() == 0) {
			DefaultLogger.debug(this, "ob == null.");
			return form;
		}
		
		int size = entryList.size();
		String internalLmtDesc[] = new String[size];
		String internalLmtPercentage[] = new String[size];
		String capitalFundAmtCurrencyCode[] = new String[size];
		String capitalFundAmt[] = new String[size];
		String totalLoanAdvAmtCurrencyCode[] = new String[size];
		String totalLoanAdvAmt[] = new String[size];
		String gp5LmtPercentage[] = new String[size];
		//String currency = new String[size];
		String status[] = new String[size];

		//NumberFormat intergerFormatter = NumberFormat.getIntegerInstance();
   // String intOut[] = new String[size];



		for (int index = 0; index < size; index++) {
			IInternalLimitParameter entry = (IInternalLimitParameter) entryList.get(index);
			internalLmtDesc[index] = entry.getDescriptionCode();
			//description[index] = entry.getDescription();
			internalLmtPercentage[index] = String.valueOf(entry.getInternalLimitPercentage());
			//currency[index] = entry.getCurrency();
			capitalFundAmtCurrencyCode[index] = entry.getCapitalFundAmountCurrencyCode();
			capitalFundAmt[index] = String.valueOf(entry.getCapitalFundAmount());
			//intOut = intergerFormatter.format(capitalFundAmt[index]);
			totalLoanAdvAmtCurrencyCode[index] = entry.getTotalLoanAdvanceAmountCurrencyCode();
			totalLoanAdvAmt[index] = String.valueOf(entry.getTotalLoanAdvanceAmount());
			gp5LmtPercentage[index] = String.valueOf(entry.getGp5LimitPercentage());
			status[index] = entry.getStatus();
		}

		form.setInternalLmtDesc(internalLmtDesc);
		form.setInternalLmtPercentage(internalLmtPercentage);
		form.setCapitalFundAmtCurrencyCode(capitalFundAmtCurrencyCode);
		
		
		
		form.setCapitalFundAmt(capitalFundAmt);
		form.setTotalLoanAdvAmtCurrencyCode(totalLoanAdvAmtCurrencyCode);
		form.setTotalLoanAdvAmt(totalLoanAdvAmt);
		form.setGp5LmtPercentage(gp5LmtPercentage);
		form.setStatus(status);
		DefaultLogger.debug(this, "inside mapOBToForm, InternalLmtParameterForm::" + form);
		return form;
		
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		InternalLmtParameterForm form = (InternalLmtParameterForm) aForm;
		
		DefaultLogger.debug(this, "inside mapFormToOB, InternalLmtParameterForm::" + form);
		
		String internalLmtDesc[] = form.getInternalLmtDesc();
		String internalLmtPercentage[] = form.getInternalLmtPercentage();
		String capitalFundAmtCurrencyCode[] = form.getCapitalFundAmtCurrencyCode();
		String capitalFundAmt[] = form.getCapitalFundAmt();
		String totalLoanAdvAmtCurrencyCode[] = form.getTotalLoanAdvAmtCurrencyCode();
		String totalLoanAdvAmt[] = form.getTotalLoanAdvAmt();
		String gp5LmtPercentage[] = form.getGp5LmtPercentage();
		String status[] = form.getStatus();
		

		int length = internalLmtPercentage == null ? 0 : internalLmtPercentage.length;
		
		String event = (String)hashMap.get(ICommonEventConstant.EVENT);
		
		if (event.equals("internal_limit_list") || event.equals("maker_edit_internallimit") || event.equals("maker_edit_internalLmt_reject")) {

            OBInternalLimitParameter obInternalLimitParameter = new OBInternalLimitParameter();
            return obInternalLimitParameter;

    }
		else if (event.equals("maker_edit_internalLmt_confirm") || event.equals("maker_resubmit_edit_internalLmt_confirm")) {
			ArrayList entryList = new ArrayList();
			
			for (int index = 0; index < length; index++) {
				IInternalLimitParameter entry = new OBInternalLimitParameter();
				DefaultLogger.debug(this, "entry::::" + entry);
				//entry.setDescription(description[index]);
				entry.setDescriptionCode(internalLmtDesc[index]);
				entry.setInternalLimitPercentage(Double.parseDouble(internalLmtPercentage[index]));
				//entry.setCurrency(currency[index]);
				entry.setCapitalFundAmountCurrencyCode(capitalFundAmtCurrencyCode[index]);
				entry.setCapitalFundAmount(Double.parseDouble(capitalFundAmt[index]));
				entry.setTotalLoanAdvanceAmountCurrencyCode(totalLoanAdvAmtCurrencyCode[index]);
				entry.setTotalLoanAdvanceAmount(Double.parseDouble(totalLoanAdvAmt[index]));
				entry.setGp5LimitPercentage(Double.parseDouble(gp5LmtPercentage[index]));
				//entry.setStatus(status[index]);
				entryList.add(entry);
			}
			
			DefaultLogger.debug(this, "entryList::::" + entryList);
			return entryList;
		}
		
		else {
			return null;
		}

		
	}


}