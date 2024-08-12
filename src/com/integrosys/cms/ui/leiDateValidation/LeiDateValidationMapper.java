package com.integrosys.cms.ui.leiDateValidation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.leiDateValidation.LeiDateValidationForm;

public class LeiDateValidationMapper extends AbstractCommonMapper {

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		LeiDateValidationForm form = (LeiDateValidationForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ILeiDateValidation obItem = null;

		try {

			obItem = new OBLeiDateValidation();

			if (form.getPartyID() != null && (!form.getPartyID().trim().equals(""))) {
				obItem.setPartyID(form.getPartyID());
			}

			if (form.getPartyName() != null && (!form.getPartyName().trim().equals(""))) {
				obItem.setPartyName(form.getPartyName());
			}

			if (form.getId() != null && (!form.getId().trim().equals(""))) {
				obItem.setId(Long.parseLong(form.getId()));
			}
			obItem.setCreateBy(form.getCreateBy());
			obItem.setLastUpdateBy(form.getLastUpdateBy());
			if (form.getLastUpdateDate() != null && (!form.getLastUpdateDate().equals(""))) {
				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
			} else {
				obItem.setLastUpdateDate(new Date());
			}
			if (form.getCreationDate() != null && (!form.getCreationDate().equals(""))) {
				obItem.setCreationDate(df.parse(form.getCreationDate()));
			} else {
				obItem.setCreationDate(new Date());
			}
			obItem.setVersionTime(0l);
			if (form.getStatus() != null && !form.getStatus().equals(""))
				obItem.setStatus(form.getStatus());
			else
				obItem.setStatus("ACTIVE");

			if (form.getLeiDateValidationPeriod() != null && (!form.getLeiDateValidationPeriod().trim().equals(""))) {
				obItem.setLeiDateValidationPeriod(form.getLeiDateValidationPeriod());
			}

			return obItem;

		} catch (Exception ex) {
			throw new MapperException("failed to map form to ob of LeiDateValidation ", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		LeiDateValidationForm form = (LeiDateValidationForm) cForm;
		ILeiDateValidation item = (ILeiDateValidation) obj;

		form.setPartyID(item.getPartyID());

		form.setPartyName(item.getPartyName());
		form.setLeiDateValidationPeriod(item.getLeiDateValidationPeriod());
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "leiDateValidationObj", "com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation",
						SERVICE_SCOPE }, });
	}
}
