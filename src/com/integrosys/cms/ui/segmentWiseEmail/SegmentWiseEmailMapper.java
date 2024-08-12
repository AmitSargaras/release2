package com.integrosys.cms.ui.segmentWiseEmail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class SegmentWiseEmailMapper extends AbstractCommonMapper {

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		SegmentWiseEmailForm form = (SegmentWiseEmailForm) cForm;
		ISegmentWiseEmail obItem = new OBSegmentWiseEmail();

		try {

			if (form.getSegmentWiseEmailList() != null && (!form.getSegmentWiseEmailList().trim().equals(""))) {
				obItem.setEmail(form.getSegmentWiseEmailList());
			}
			if (form.getSegment() != null && (!form.getSegment().trim().equals(""))) {
				obItem.setSegment(form.getSegment());
			}
			if (form.getID() != null && (!form.getID().trim().equals(""))) {
				obItem.setID(Long.parseLong(form.getID()));
			}

			if (form.getCreatedBy() != null && !"".equals(form.getCreatedBy())) {
				obItem.setCreatedBy(form.getCreatedBy());
			} else {
				obItem.setCreatedBy(user.getLoginID());
			}

			if (form.getLastUpdatedBy() != null && !"".equals(form.getCreatedBy())) {
				obItem.setLastUpdatedBy(user.getLoginID());
			} else {
				obItem.setLastUpdatedBy(user.getLoginID());
			}

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

			return obItem;
		} catch (Exception ex) {
			throw new MapperException("failed to map form to ob of segmentWiseEmail ", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		SegmentWiseEmailForm form = (SegmentWiseEmailForm) cForm;
		ISegmentWiseEmail item = (ISegmentWiseEmail) obj;
		form.setSegment(item.getSegment());
		form.setEmailList(item.getEmail());
		form.setCreatedBy(item.getCreatedBy());
		form.setLastUpdatedBy(item.getLastUpdatedBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setID(Long.toString(item.getID()));
		form.setStatus(item.getStatus());

		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "segmentWiseEmailObj", "com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail",
						SERVICE_SCOPE }, });
	}
}
