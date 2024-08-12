/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2005/10/27 05:41:20 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This mapper is responsible for mapping objects between the action form html
 * attributes to that of the business value object and vice versa
 * 
 * @author $Author: jtan $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/10/27 05:41:20 $ Tag: $Name: $
 */

public class DiaryItemMapper extends AbstractCommonMapper {
	private static final String DATE_FORMAT = "dd/MMM/yyyy";

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		DiaryItemForm form = (DiaryItemForm) cForm;

		String event = form.getEvent();
		OBDiaryItem obItem = new OBDiaryItem() ;
		try {
			if (event.equals(EVENT_CREATE)) {
				obItem = new OBDiaryItem();
				obItem.setCustomerReference(form.getLeID());
				obItem.setCustomerName(form.getCustomerName());
				obItem.setAllowedCountry(form.getAllowedCountry());
				if (form.getItemId() != null) {
					obItem.setItemID(Long.parseLong(form.getItemId()));
				}
			}
			else if (event.equals(EVENT_UPDATE)) {
				IDiaryItem item = (IDiaryItem) inputs.get("diaryItemObj");
				if (item == null) {
					throw new MapperException("fail to retrieve diary item object from session!");
				}
				obItem =  (OBDiaryItem) AccessorUtil.deepClone(item);
			}
			else if (event.equals("refreshFacilityLine") || event.equals("refreshFacilitySerialNo")) {
				IDiaryItem item = (IDiaryItem) inputs.get("diaryItemObj");
				if (item == null) {
					throw new MapperException("fail to retrieve diary item object from session!");
				}
				obItem =  (OBDiaryItem) AccessorUtil.deepClone(item);
			}

			// common processing for create and update events
			ITeam team = (ITeam) inputs.get(IGlobalConstant.USER_TEAM);
			ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
			String obsoleteInd = form.getObsolete();

			if (obsoleteInd != null) {
				obItem.setObsoleteInd(true);
			}
			else {
				obItem.setObsoleteInd(false);
			}
            if (!(event.equals("refreshFacilityLine") || event.equals("refreshFacilitySerialNo"))) {
			DefaultLogger.debug(this, "******** TeamTypeID: " + team.getTeamType().getTeamTypeID() + "\n");
			obItem.setDescription(form.getDescription());
			obItem.setNarration(form.getNarration());
			obItem.setCustomerSegment(form.getCustomerSegment());
			obItem.setFAM(form.getFAM());
			obItem.setTeamTypeID(team.getTeamType().getTeamTypeID());
			obItem.setTeamId(team.getTeamID());
			obItem.setLastUpdatedBy(user.getUserName());
			obItem.setLastUpdatedTime(DateUtil.getDate());
			if(!("Y".equals(form.getDropLineOD()))) {
				if(null!=form.getItemDueDate())
					obItem.setDueDate(DateUtil.convertDate(form.getItemDueDate()));
			}
			//obItem.setExpiryDate(DateUtil.convertDate(form.getItemExpiryDate()));
			if(!(("").equals(form.getDiaryNumber()))){
				if(null!=form.getDiaryNumber())
					obItem.setDiaryNumber(Long.parseLong(form.getDiaryNumber()));
			}
			obItem.setRegion(form.getRegion());
			obItem.setFacilityBoardCategory(form.getFacilityBoardCategory());
			obItem.setFacilityLineNo(form.getFacilityLineNo());
			obItem.setDropLineOD(form.getDropLineOD());
			obItem.setOdScheduleUploadFile(form.getOdScheduleUploadFile());
			obItem.setActivity(form.getActivity());
			obItem.setMakerId(form.getMakerId());
			obItem.setMakerDateTime(DateUtil.getDate());
			obItem.setStatus(form.getStatus());
			obItem.setFacilitySerialNo(form.getFacilitySerialNo());
			obItem.setAction(form.getAction());
			obItem.setCloseBy(form.getCloseBy());
			obItem.setCloseDate(form.getCloseDate());
			obItem.setUploadFileError(form.getUploadFileError());
			obItem.setIsDelete(form.getIsDelete());
			obItem.setMonth(form.getMonth());
			obItem.setClosingAmount(form.getClosingAmount());
			if ("read".equals(event) || "update".equals(event)) {
			if(!("Y".equals(form.getDropLineOD()))) {
				if("Extend".equals(form.getAction())){
					if(null!=form.getNextTargetDate())
						obItem.setNextTargetDate(DateUtil.convertDate(form.getNextTargetDate()));
				}
				}
            }
            }
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of diary item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DiaryItemForm form = (DiaryItemForm) cForm;
		IDiaryItem item = (IDiaryItem) obj;

		form.setLeID(item.getCustomerReference());
		form.setCustomerName(item.getCustomerName());
		form.setDescription(item.getDescription());
		form.setItemDueDate(DateUtil.formatDate(locale, item.getDueDate(), DATE_FORMAT));
	//	form.setItemExpiryDate(DateUtil.formatDate(locale, item.getExpiryDate(), DATE_FORMAT));
		form.setItemId(Long.toString(item.getItemID()));
		form.setLastUpdatedBy(item.getLastUpdatedBy());
		form.setLastUpdatedDate(DateUtil.formatDate(locale, item.getLastUpdatedTime(), DATE_FORMAT));
		form.setNarration(item.getNarration());
		form.setCustomerSegment(item.getCustomerSegment());
		form.setFAM(item.getFAM());
		form.setDiaryNumber(Long.toString(item.getDiaryNumber()));
		form.setRegion(item.getRegion());
		form.setFacilityBoardCategory(item.getFacilityBoardCategory());
		form.setFacilityLineNo(item.getFacilityLineNo());
		form.setActivity(item.getActivity());
		form.setOdScheduleUploadFile(item.getOdScheduleUploadFile());
		form.setDropLineOD(item.getDropLineOD());
		form.setMakerId(item.getMakerId());
		form.setMakerDateTime(DateUtil.formatDate(locale, item.getMakerDateTime(), "dd/MM/yyyy HH:MM"));
		form.setStatus(item.getStatus());
		form.setFacilitySerialNo(item.getFacilitySerialNo());
		form.setAction(item.getAction());
		form.setCloseBy(item.getCloseBy());
		form.setCloseDate(item.getCloseDate());
		form.setNextTargetDate(DateUtil.formatDate(locale, item.getNextTargetDate(), DATE_FORMAT));
		form.setLinkEvent(item.getLinkEvent());
		form.setUploadFileError(item.getUploadFileError());
		form.setIsDelete(item.getIsDelete());
		form.setMonth(item.getMonth());
		form.setClosingAmount(item.getClosingAmount());
		form.setCustomerSegmentName(item.getCustomerSegmentName());
		if (item.getObsoleteInd()) {
			form.setObsolete(ICMSConstant.TRUE_VALUE);
		}
		else {
			form.setObsolete(ICMSConstant.FALSE_VALUE);
		}

		return form;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "diaryItemObj", "com.integrosys.cms.app.diary.bus.OBDiaryItem", SERVICE_SCOPE }, });
	}
}
