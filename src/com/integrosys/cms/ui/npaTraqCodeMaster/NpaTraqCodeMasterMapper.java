package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class NpaTraqCodeMasterMapper extends AbstractCommonMapper {

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		NpaTraqCodeMasterForm form = (NpaTraqCodeMasterForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		INpaTraqCodeMaster obItem = null;

		try {

			obItem = new OBNpaTraqCodeMaster();

			if (form.getNpaTraqCode() != null && (!form.getNpaTraqCode().trim().equals(""))) {
				obItem.setNpaTraqCode(form.getNpaTraqCode());
			}

			if (form.getSecurityType() != null && (!form.getSecurityType().trim().equals(""))) {
				obItem.setSecurityType(form.getSecurityType());
			}
			
			if (form.getSecuritySubType() != null && (!form.getSecuritySubType().trim().equals(""))) {
				obItem.setSecuritySubType(form.getSecuritySubType());
			}

			if (form.getPropertyTypeCodeDesc() != null && (!form.getPropertyTypeCodeDesc().trim().equals(""))) {
				obItem.setPropertyTypeCodeDesc(form.getPropertyTypeCodeDesc());
			}

			if (form.getDeprecated() != null && (!form.getDeprecated().trim().equals(""))) {
				obItem.setDeprecated(form.getDeprecated());
			} else {
				obItem.setDeprecated("N");
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

			if (form.getOperationName() != null && (!form.getOperationName().trim().equals(""))) {
				obItem.setOperationName(form.getOperationName());
			}
			if (form.getCpsId() != null && (!form.getCpsId().trim().equals(""))) {
				obItem.setCpsId(form.getCpsId());
			}

			return obItem;

		} catch (Exception ex) {
			throw new MapperException("failed to map form to ob of NpaTraqCodeMaster ", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		NpaTraqCodeMasterForm form = (NpaTraqCodeMasterForm) cForm;
		INpaTraqCodeMaster item = (INpaTraqCodeMaster) obj;

		form.setNpaTraqCode(item.getNpaTraqCode());
		form.setSecurityType(item.getSecurityType());
		form.setSecuritySubType(item.getSecuritySubType());
		form.setPropertyTypeCodeDesc(item.getPropertyTypeCodeDesc());
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
				{ "npaTraqCodeMasterObj", "com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaTraqCodeMaster", SERVICE_SCOPE }, });
	}
}
