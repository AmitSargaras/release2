package com.integrosys.cms.ui.valuationAmountAndRating;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class ValuationAmountAndRatingMapper extends AbstractCommonMapper{

DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	
	public Object mapFormToOB(CommonForm cForm, HashMap map)
			throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		ValuationAmountAndRatingForm form=(ValuationAmountAndRatingForm)cForm;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		Locale locale=(Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IValuationAmountAndRating obItem=null;
		
		try {
			
			obItem = new OBValuationAmountAndRating();
			
			if(form.getCriteria()!=null && (!form.getCriteria().trim().equals("")))
			{
				obItem.setCriteria(form.getCriteria());
			}
			
			if(form.getValuationAmount()!=null && (!form.getValuationAmount().trim().equals("")))
			{
				String num = form.getValuationAmount();
				num = UIUtil.removeComma(num);
				num = num.replace(".00", "");
//				obItem.setValuationAmount(form.getValuationAmount());
				obItem.setValuationAmount(num);
			}
			
			if(form.getExcludePartyId()!=null && (!form.getExcludePartyId().trim().equals("")))
			{
				obItem.setExcludePartyId(form.getExcludePartyId());
			}
			if(form.getRamRating()!=null && (!form.getRamRating().trim().equals("")))
			{
				obItem.setRamRating(form.getRamRating());
			}
			
			if(form.getDeprecated()!=null && (!form.getDeprecated().trim().equals(""))){
			obItem.setDeprecated(form.getDeprecated());
			}else{
				obItem.setDeprecated("N");
			}
			if(form.getId()!=null && (!form.getId().trim().equals("")))
            {
				obItem.setId(Long.parseLong(form.getId()));
            }
//			obItem.setCreateBy(form.getCreateBy());
//			obItem.setLastUpdateBy(form.getLastUpdateBy());
			
			if(form.getCreateBy()!=null && !form.getCreateBy().trim().equals("")){
				obItem.setCreateBy(form.getCreateBy());
			}else{
				obItem.setCreateBy(user.getLoginID());
			}	
			
			if(form.getLastUpdateBy()!=null && !form.getLastUpdateBy().trim().equals("")){
				obItem.setLastUpdateBy(form.getLastUpdateBy());
			}else{
				obItem.setLastUpdateBy(user.getLoginID());
			}	
			
			
			if(form.getLastUpdateDate()!=null && (!form.getLastUpdateDate().equals("")))
            {
			obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
            }else{
            	obItem.setLastUpdateDate(new Date());
            }
			if(form.getCreationDate()!=null && (!form.getCreationDate().equals("")))
            {
			obItem.setCreationDate(df.parse(form.getCreationDate()));
            }else{
            	obItem.setCreationDate(new Date());
            }
			obItem.setVersionTime(0l);
			if( form.getStatus() != null && ! form.getStatus().equals(""))
				obItem.setStatus(form.getStatus());
			else
				obItem.setStatus("ACTIVE");
			
			if(form.getOperationName()!=null && (!form.getOperationName().trim().equals(""))){
				obItem.setOperationName(form.getOperationName());
			}

		return obItem;

	}
	catch (Exception ex) {
		throw new MapperException("failed to map form to ob of ValuationAmountAndRating ", ex);
	}
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)	throws MapperException {
		
		ValuationAmountAndRatingForm form = (ValuationAmountAndRatingForm) cForm;
		IValuationAmountAndRating item = (IValuationAmountAndRating) obj;

		form.setCriteria(item.getCriteria());
		
		form.setValuationAmount(item.getValuationAmount());
		form.setExcludePartyId(item.getExcludePartyId());
		form.setRamRating(item.getRamRating());
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "valuationAmountAndRatingObj", "com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", SERVICE_SCOPE }, });
	}
}
