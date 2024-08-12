/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesMapper.java
 *
 * Created on February 5, 2007, 6:04 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;

public class MaintainCCEntryListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm commonForm, Object object, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");
		MaintainCCEntryListForm form = (MaintainCCEntryListForm) commonForm;
		ArrayList entryList = (ArrayList) object;
		if ((entryList == null) || (entryList.size() == 0)) {
			form.reset();
			return form;
		}
		int size = entryList.size();
		String entryName[] = new String[size];
		String entryCode[] = new String[size];
		String country[] = new String[size];
		String refCategoryCode[] = new String[size];
		String activeStatus[] = new String[size];
		for (int index = 0; index < size; index++) {
			OBCommonCodeEntry entry = (OBCommonCodeEntry) entryList.get(index);
			entryName[index] = entry.getEntryName();
			entryCode[index] = entry.getEntryCode();
			DefaultLogger.debug(this, "entryCode:" + entryCode[index]);
			country[index] = entry.getCountry();
			refCategoryCode[index] = entry.getRefEntryCode();
			activeStatus[index] = entry.getActiveStatus() ? "A" : "N";
		}
		form.setEntryName(entryName);
		form.setEntryCode(entryCode);
		form.setCountry(country);
		form.setRefCategoryCode(refCategoryCode);
		form.setActiveStatus(activeStatus);
		return form;
	}

	public Object mapFormToOB(CommonForm commonForm, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		MaintainCCEntryListForm form = (MaintainCCEntryListForm) commonForm;

		boolean isMaintainRef = "Y".equals((String) hashMap.get("IsMaintainRef"));

		String entryName[] = form.getEntryName();
		String entryCode[] = form.getEntryCode();
		String country[] = form.getCountry();
		String refCategoryCode[] = form.getRefCategoryCode();
		String activeStatus[] = form.getActiveStatus();
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		String applicationDate = generalParamEntry.getParamValue();
		Date appDate = new Date(applicationDate);
		
		int length = 0;
		if (isMaintainRef) {
			length = refCategoryCode == null ? 0 : refCategoryCode.length;
			DefaultLogger.debug(this, "Length of refCategoryCode:" + length);
		}
		else {
			length = entryName == null ? 0 : entryName.length;
			DefaultLogger.debug(this, "Length of entryName:" + length);
		}

		ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) hashMap
				.get(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX);
		Integer offset = (Integer) hashMap.get(CommonCodeEntryConstant.OFFSET);
		ArrayList entryList = MaintainCCEntryUtil.getCurrentPageCCEntryList(trxValue, offset, false);
		for (int index = 0; index < length; index++) {
			OBCommonCodeEntry entry = (OBCommonCodeEntry) entryList.get(index);
			if (isMaintainRef) {
				if (isEquals(entry.getRefEntryCode(), refCategoryCode[index])) {
					continue;
				}
				entry.setRefEntryCode("".equals(refCategoryCode[index]) ? null : refCategoryCode[index]);
				entry.setUpdateFlag('U');
			}
			else {
				if (isEquals(entry.getEntryName(), entryName[index])
						&& isEquals(entry.getEntryCode(), entryCode[index])
						&& isEquals(entry.getCountry(), country[index])
						&& (entry.getActiveStatus() == ("A".equals(activeStatus[index])))) {
					continue;
				}
				entry.setEntryName(entryName[index]);
				entry.setEntryCode(entryCode[index]);
				entry.setCountry("".equals(country[index]) ? null : country[index]);
				entry.setActiveStatus("A".equals(activeStatus[index]));
				DefaultLogger.debug(this, "Origal Flag:" + entry.getUpdateFlag());
				if(entry.getEntryId()==ICMSConstant.LONG_INVALID_VALUE){
					entry.setUpdateFlag('I');
					entry.setCreationDate(appDate);
				}else{
					entry.setUpdateFlag('U');
				}
				entry.setLastUpdateDate(appDate);
			}
			DefaultLogger.debug(this, "Detect one changed entry - ID:" + entry.getEntryId());
		}
		return entryList;
	}

	private boolean isEquals(String v1, String v2) {
		if (isEmpty(v1)) {
			return isEmpty(v2);
		}
		else {
			return v1.equals(v2);
		}
	}

	private boolean isEmpty(String val) {
		if (val == null) {
			return true;
		}
		if (val.trim().equals("")) {
			return true;
		}
		return false;
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "IsMaintainRef", "java.lang.String", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE } };
	}
}
