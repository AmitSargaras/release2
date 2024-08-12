/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.manualinput.partygroup;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author Bharat Waghela Mapper for Party Group master
 */

public class PartyGroupMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		MIPartyGroupForm form = (MIPartyGroupForm) cForm;

		com.integrosys.cms.app.partygroup.bus.IPartyGroup obItem = null;
		try {

			obItem = new OBPartyGroup();
			obItem.setPartyCode(form.getPartyCode());
			obItem.setPartyName(form.getPartyName() == null ? "" : form
					.getPartyName());
			
			if(form.getGroupExpLimit()==null){
				form.setGroupExpLimit("");
			}
			
			if (form.getGroupExpLimit()!=null && form.getGroupExpLimit().equals("")) {
				obItem.setGroupExpLimit(new Amount(new BigDecimal("0.00"),BaseCurrency.getCurrencyCode()));
			} else
				//obItem.setGroupExpLimit(new Amount(new BigDecimal(form.getGroupExpLimit()), BaseCurrency.getCurrencyCode()));
				

				//Phase 3 CR:input field to be comma seperated
				obItem.setGroupExpLimit(new Amount(new BigDecimal(UIUtil.removeComma(form.getGroupExpLimit())), BaseCurrency.getCurrencyCode()));
			obItem.setDeprecated(form.getDeprecated());

			if (form.getId() != null && (!form.getId().equals(""))) {
				obItem.setId(Long.parseLong(form.getId()));
			}
			obItem.setCreateBy(form.getCreateBy()==null?"":form.getCreateBy());
			
			obItem.setLastUpdateBy(form.getLastUpdateBy()==null?"":form.getLastUpdateBy());
			
			if (form.getLastUpdateDate() != null
					&& (!form.getLastUpdateDate().equals(""))) {
				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
			} else {
				obItem.setLastUpdateDate(new Date());
			}
			if (form.getCreationDate() != null
					&& (!form.getCreationDate().equals(""))) {
				obItem.setCreationDate(df.parse(form.getCreationDate()));
			} else {
				obItem.setCreationDate(new Date());
			}
			obItem.setVersionTime(0l);
			obItem.setStatus(form.getStatus());
			obItem.setOperationName(form.getOperationName());
			obItem.setCpsId(form.getCpsId());
			
			return obItem;

		} catch (Exception ex) {
			throw new MapperException(
					"failed to map form to ob of partyGroup item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		Locale locale = (Locale) inputs
				.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		MIPartyGroupForm form = (MIPartyGroupForm) cForm;
		IPartyGroup item = (IPartyGroup) obj;

		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));

		//form.setGroupExpLimit(String.valueOf(item.getGroupExpLimit().getAmountAsBigDecimal()));
		
		//Phase 3 CR:input field to be comma seperated
		form.setGroupExpLimit(UIUtil.formatWithCommaAndDecimal(String.valueOf(item.getGroupExpLimit().getAmountAsBigDecimal())));
		form.setPartyCode(item.getPartyCode());
		form.setPartyName(item.getPartyName());

		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

		return form;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * 
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER,
						"com.integrosys.component.user.app.bus.ICommonUser",
						GLOBAL_SCOPE },
				{ "partyGroupObj",
						"com.integrosys.cms.app.partygroup.bus.OBPartyGroup",
						SERVICE_SCOPE }, });
	}
}
