
package com.integrosys.cms.ui.creditApproval;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * 
 * @author $Govind: Sahu $<br>
 * @version $Revision: 0.0 $
 * @since $Date: 2011/03/31 5:44:10 $ Tag: $Name: $
 */
public class CreditApprovalMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public CommonForm mapOBToForm(CommonForm cform, Object obj, HashMap hashMap) throws MapperException {
		CreditApprovalForm form = (CreditApprovalForm) cform;
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form");

			

			ICreditApproval item = (ICreditApproval) obj;

			form.setCreateBy(item.getCreateBy());
			form.setLastUpdateBy(item.getLastUpdateBy());
			form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
			form.setCreationDate(df.format(item.getCreationDate()));
			//form.setVersionTime(Long.toString(item.getVersionTime()));
			form.setDeprecated(item.getDeprecated());
			form.setStatus(item.getStatus());
			
			form.setId(Long.toString(item.getId()));
			form.setApprovalCode(item.getApprovalCode());
			form.setApprovalName(item.getApprovalName());
			form.setMaximumLimit(UIUtil.formatWithCommaAndDecimal(item.getMaximumLimit().toString())); //Phase 3 CR:comma separated
			form.setMinimumLimit(UIUtil.formatWithCommaAndDecimal(item.getMinimumLimit().toString()));
			form.setSegmentId((item.getSegmentId()));
			form.setSenior(item.getSenior());
			form.setRisk(item.getRisk());
			form.setEmail(item.getEmail());
			form.setDeferralPowers(item.getDeferralPowers());
			form.setWaivingPowers(item.getWaivingPowers());
			form.setRegionId(String.valueOf(item.getRegionId()));
			form.setEmployeeId(item.getEmployeeId().toUpperCase());
			
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return form;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in CreditApprovalMapper is" + e);
		}
		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		CreditApprovalForm form = (CreditApprovalForm) aForm;
		String event = form.getEvent();
		OBCreditApproval oBcredutApproval = new OBCreditApproval();
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommonUser user = (ICommonUser) hashMap.get(IGlobalConstant.USER);

		if (CreditApprovalAction.EVENT_PREPARE_SUBMIT.equals(event)
				||CreditApprovalAction.MAKER_CONFIRM_RESUBMIT_EDIT.equals(event)
				||CreditApprovalAction.MAKER_CONFIRM_RESUBMIT_EDIT_REJECTED.equals(event)
				||CreditApprovalAction.CONFIRM_MAKER_SUBMIT_REMOVE.equals(event)
				||CreditApprovalAction.MAKER_DRAFT_CREDIT_APPROVAL.equals(event)
				||CreditApprovalAction.MAKER_SAVE_UPDATE.equals(event)
				||CreditApprovalAction.MAKER_UPDATE_DRAFT_CREDIT_APPROVAL.equals(event)
				||CreditApprovalAction.MAKER_SAVE_CREATE.equals(event)
				
				) {
			// Will return a ICreditApproval.

			
			if (form.getId() != null) {
				oBcredutApproval.setId(Long.parseLong(form.getId()));
			}	
			if ((form.getApprovalCode() != null) && !form.getApprovalCode().trim().equals("")) {
			oBcredutApproval.setApprovalCode(form.getApprovalCode());
			}
			if ((form.getApprovalName() != null) && !form.getApprovalName().trim().equals("")) {
				oBcredutApproval.setApprovalName((form.getApprovalName()));
			}
			else
			{
				oBcredutApproval.setApprovalName("");
			}
			
			/* if ((form.getMaximumLimit() != null) && !form.getMaximumLimit().trim().equals("")) {
			oBcredutApproval.setMaximumLimit(new BigDecimal(form.getMaximumLimit()));
			} */
			
			//Phase 3 CR:comma separated
			String maximumLimit = form.getMaximumLimit();
			maximumLimit=UIUtil.removeComma(maximumLimit);
			String minimumLimit = form.getMinimumLimit();
			minimumLimit=UIUtil.removeComma(minimumLimit);
			
			if ((maximumLimit != null) && !maximumLimit.trim().equals("")) {
				oBcredutApproval.setMaximumLimit(new BigDecimal(maximumLimit));
				}
			else
			{
				 oBcredutApproval.setMaximumLimit(new BigDecimal(0));
		    }
//			if ((form.getMinimumLimit() != null) && !form.getMinimumLimit().trim().equals("")) {
//				oBcredutApproval.setMinimumLimit(new BigDecimal(form.getMinimumLimit()));
//			}
			
			if ((minimumLimit != null) && !minimumLimit.trim().equals("")) {
				oBcredutApproval.setMinimumLimit(new BigDecimal(minimumLimit));
			}
			else
			{
				 oBcredutApproval.setMinimumLimit(new BigDecimal(0));
		    }
			oBcredutApproval.setSegmentId(form.getSegmentId());
			oBcredutApproval.setEmployeeId(form.getEmployeeId().toUpperCase());
			oBcredutApproval.setEmail(form.getEmail());
			if(form.getSenior()!=null){
			if(form.getSenior().equals("on")||form.getSenior().trim().equals("")||form.getSenior().trim().equals("Y"))
			{
				oBcredutApproval.setSenior("Y");
			}else
			    {
				oBcredutApproval.setSenior("N");
			    }
			}
			else
			    {
				oBcredutApproval.setSenior("N");
			    }
			if(form.getRisk()!=null ){
			if(form.getRisk().equals("on")||form.getRisk().trim().equals("")||form.getRisk().trim().equals("Y"))
			{
				oBcredutApproval.setRisk("Y");
			}else
			   {
				oBcredutApproval.setRisk("N");
			    }
			}else
			   {
				oBcredutApproval.setRisk("N");
			    }
//			if(form.getVersionTime()!=null && !form.getVersionTime().trim().equals(""))
//			{
//				oBcredutApproval.setVersionTime(Long.parseLong(form.getVersionTime()));
//			}
			if(form.getCreateBy()!=null && !form.getCreateBy().trim().equals(""))
			{
			oBcredutApproval.setCreateBy(form.getCreateBy());
			}
			else
			{
				oBcredutApproval.setCreateBy(user.getUserName());
			}
			if(CreditApprovalAction.MAKER_CONFIRM_RESUBMIT_EDIT.equals(event))
			{
				 try {    
		         
					 oBcredutApproval.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
				     oBcredutApproval.setCreationDate(df.parse(form.getCreationDate()));            
		    } 
			catch (ParseException e)
		    	{
				e.printStackTrace();
				DefaultLogger.debug(this,"Date conversion exception:"+e);  
				throw new CreditApprovalException("Date conversion exception.");
		    	}      
		    }
			else{
			oBcredutApproval.setCreationDate(new Date());
			oBcredutApproval.setLastUpdateDate(new Date());
			}
			
			if(form.getLastUpdateBy()!=null && !form.getLastUpdateBy().trim().equals(""))
			{
			oBcredutApproval.setLastUpdateBy(form.getLastUpdateBy());
			}
			else
			{
				oBcredutApproval.setLastUpdateBy(user.getUserName());
			}
			
			oBcredutApproval.setDeprecated(form.getDeprecated());
			if( form.getStatus() != null && ! form.getStatus().equals(""))
				oBcredutApproval.setStatus(form.getStatus());
			else
				oBcredutApproval.setStatus("ACTIVE");			
		}
		
		oBcredutApproval.setDeferralPowers(form.getDeferralPowers());
		oBcredutApproval.setWaivingPowers(form.getWaivingPowers());
		
		if(form.getRegionId()!=null && !form.getRegionId().trim().equals(""))
		{
			oBcredutApproval.setRegionId(Long.parseLong(form.getRegionId()));
		}
		//File Upload
		if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
        {
			oBcredutApproval.setFileUpload(form.getFileUpload());
        }
		

		return oBcredutApproval;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the mapFormToOB method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }};
	}
}
