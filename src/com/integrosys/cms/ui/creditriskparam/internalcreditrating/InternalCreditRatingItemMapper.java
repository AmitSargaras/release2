package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternalCreditRatingItemMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
            {"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
			      {"curItem", "com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating", SERVICE_SCOPE},
		});
	}

	
	public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
			throws MapperException {
		try
		{		
			        
			IInternalCreditRating creditRatingItem = (IInternalCreditRating)obj;
			InternalCreditRatingItemForm itemForm = (InternalCreditRatingItemForm)commonForm;
			
			itemForm.setCreditRatingGrade( creditRatingItem.getIntCredRatCode());
			itemForm.setCreditRatingLmtAmtCode( creditRatingItem.getIntCredRatLmtAmtCurCode());		
			itemForm.setCreditRatingLmtAmt(String.valueOf(creditRatingItem.getIntCredRatLmtAmt()));		
			
			return itemForm;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new MapperException();
		}
	}

	
	public Object mapFormToOB(CommonForm commonForm, HashMap inputs)
			throws MapperException {
		// TODO Auto-generated method stub
		try
		{

			InternalCreditRatingItemForm itemForm = (InternalCreditRatingItemForm)commonForm;
			//IInternalCreditRating item = (IInternalCreditRating)(inputs.get("curItem"));
			
			String intCredRatLmtAmtRegex = "[-]*[0-9]*";			

			IInternalCreditRating item = new OBInternalCreditRating();

  		if (item != null)
			{ 
				item.setIntCredRatCode( itemForm.getCreditRatingGrade());
				//item.setIntCredRatLmtAmtCurCode( itemForm.getCreditRatingLmtAmtCode());
				item.setIntCredRatLmtAmtCurCode("MYR");
				if (itemForm.getCreditRatingLmtAmt() != null && !itemForm.getCreditRatingLmtAmt().equals("") && Validator.checkPattern(itemForm.getCreditRatingLmtAmt(), intCredRatLmtAmtRegex)) {
					
					item.setIntCredRatLmtAmt(Double.parseDouble(itemForm.getCreditRatingLmtAmt()));
				}
				item.setStatus(ICMSConstant.STATE_ACTIVE);
			}
			return item;

	 }
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new MapperException();
		}
	}	

}
