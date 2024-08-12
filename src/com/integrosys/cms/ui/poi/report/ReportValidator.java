package com.integrosys.cms.ui.poi.report;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.ReportDaoImpl;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.newTat.NewTatValidator;

public class ReportValidator {
	public static HashMap validateReport(HashMap input, OBFilter reportOb) {
		HashMap exceptionMap = new HashMap();
		String reportId = reportOb.getReportId();
		String reportsFilterType = (String) ReportFilterTypeList.getInstance().getReportFilterList().get(reportId);
		ReportDaoImpl reportImpl=new ReportDaoImpl();
		
		DefaultLogger.debug("ReportValidator","====party====="+input.get("filterParty"));
		DefaultLogger.debug("ReportValidator","====user====="+input.get("filterUser"));
		
		if("TYPE2".equals(reportsFilterType) 
				|| "TYPE3".equals(reportsFilterType)
				|| "TYPE6".equals(reportsFilterType)
				|| "TYPE7".equals(reportsFilterType)
				|| "TYPE9".equals(reportsFilterType)
				|| "TYPE11".equals(reportsFilterType)
				|| "TYPE81".equals(reportsFilterType)
				){
//			String filterParty = (String)input.get("filterPartyMode");
			if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
				if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
				}
			}
		}
		
		/*if("TYPE9".equals(reportsFilterType)){
			if(reportOb.getRelationManager().equals("")||null==reportOb.getRelationManager()){
				exceptionMap.put("managerError",  new ActionMessage("error.report.select.manager.mandatory"));
			}
			if(reportOb.getSegment().equals("")||null==reportOb.getSegment()){
				exceptionMap.put("managerError",  new ActionMessage("error.report.select.segment.mandatory"));
			}
		}*/
		
		if("TYPE4".equals(reportsFilterType) || "TYPE8".equals(reportsFilterType)){
//			String filterUser = (String)input.get("filterUserMode");
			if("SELECTD_USER".equals(reportOb.getFilterUserMode())){
				if (!(Validator.checkString(reportOb.getUserId(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterUserError",  new ActionMessage("error.report.select.user.mandatory"));
				}
			}
		}
		
		if("TYPE10".equals(reportsFilterType) ){
			if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
		}}
		if ("RPT0072".equals(reportId)) {
			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}

			if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
			}else {
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>1) {
					errorCode="compareDate.greater";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"fromDate and toDate difference", "2 days"));
				}
			}
		}
		if ("TYPE8".equals(reportsFilterType) || "TYPE4".equals(reportsFilterType) || "TYPE9".equals(reportsFilterType)
				|| "RPT0001".equals(reportId) || "TYPE10".equals(reportsFilterType) 
				|| "RPT0002".equals(reportId) || "RPT0012".equals(reportId) || "RPT0018".equals(reportId)
				|| "RPT0020".equals(reportId) || "RPT0043".equals(reportId) || "RPT0044".equals(reportId)
				|| "RPT0066".equals(reportId) || "RPT0067".equals(reportId)) {

			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}

			if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
			}
			
			//added by prachit for cersai report
			if("RPT0066".equals(reportId) || "RPT0067".equals(reportId)){
				if (reportOb.getTypeOfSecurity() == null
						|| "".equals(reportOb.getTypeOfSecurity())) {
					exceptionMap.put("typeOfSecurityError", new ActionMessage(
							"error.string.mandatory"));
					DefaultLogger.debug(ReportValidator.class,
							"typeOfSecurityError");
				}
				
				if (reportOb.getBankingMethod() == null
						|| "".equals(reportOb.getBankingMethod())) {
					exceptionMap.put("bankingMethodError", new ActionMessage(
							"error.string.mandatory"));
					DefaultLogger.debug(ReportValidator.class,
							"bankingMethodError");
				}
				if("RPT0067".equals(reportId)) {
				if (reportOb.getReportFormat() == null
						|| "".equals(reportOb.getReportFormat()) ) {
					exceptionMap.put("reportFormatError", new ActionMessage(
							"error.string.mandatory"));
					DefaultLogger.debug(ReportValidator.class,
							"reportFormatError");
		}
				}
				
				if("RPT0066".equals(reportId)) {
					if (!isMandatory
							&& !(errorCode = UIValidator.compareDateEarlier("23/Feb/2016",reportOb.getFromDate(), locale))
									.equals(Validator.ERROR_NONE)) {
						exceptionMap.put("fromDatePostError", new ActionMessage("error.date.postdate", "From Date", "22/Feb/2016"));
					}
					}
				
			}
		}
		//Added By Prachit For Cersai Report
		if("RPT0068".equals(reportId)){		
		if ((reportOb.getSecurityId() == null
				|| "".equals(reportOb.getSecurityId())) && (reportOb.getSearchCustomerName() == null
				|| "".equals(reportOb.getSearchCustomerName()))) {
			exceptionMap.put("partyNameError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ReportValidator.class,
					"partyNameError");
					
			exceptionMap.put("securityIdError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(ReportValidator.class,
					"securityIdError");
		}
		
		if(!(reportOb.getSecurityId() == null
				|| "".equals(reportOb.getSecurityId()))) {
			int secIdList=0;
			String regex = "\\d+";
			if((reportOb.getSecurityId()).matches(regex) == true) {
				secIdList=reportImpl.getSecurityIds(reportOb.getSecurityId());
			}
			if(secIdList == 0) {
				exceptionMap.put("securityIdError", new ActionMessage(
						"error.string.wrong.securityid"));
				DefaultLogger.debug(ReportValidator.class,
						"securityIdError");
			}
		}
		}
		//added by santosh For UBS CR
		if ("TYPE60".equals(reportsFilterType) || "RPT0100".equals(reportId)) {
			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			
			
			if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
					&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
				if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
				}
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>31) {
					errorCode="compareDate";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "1 month from To Date"));
				}
			}
		}
		//End Santosh
		
		if ( "RPT0064".equals(reportId) || "RPT0065".equals(reportId) || "RPT0095".equals(reportId) || "RPT0096".equals(reportId) || "RPT0097".equals(reportId) || "RPT0098".equals(reportId)) {
			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			
			if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
					&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
				if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
				}
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>7) {
					errorCode="compareDate";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "7 days from To Date"));
				}
			}
		}
		
		//added by santosh For Diary CR
		if ("TYPE61".equals(reportsFilterType) || "RPT0071".equals(reportId) || "RPT0076".equals(reportId) || "RPT0077".equals(reportId)) {
			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
			if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
					&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
				if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
				}
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>7) {
					errorCode="compareDate.greater";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"Date", "7 days"));
				}
			}
		}
		//End Santosh
		if("RPT0025".equals(reportId) || "RPT0062".equals(reportId) || "RPT0081".equals(reportId)){		
			if (reportOb.getUploadSystem() == null
					|| "".equals(reportOb.getUploadSystem())) {
				exceptionMap.put("uploadSystemError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ReportValidator.class,
						"uploadSystemError");
			}
				}
		
		if("RPT0081".equals(reportId)) {
			if("ALL".equals(reportOb.getFilterPartyMode()) && "ALL".equals(reportOb.getUploadSystem())) {
				exceptionMap.put("uploadSystemError", new ActionMessage(
						"error.report.systemAndParty.all"));
			}
		}
		
		
		if ("RPT0047".equals(reportId) || "RPT0048".equals(reportId)) {

			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			
			if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
				if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
				}
			}
				if(!"RPT0048".equals(reportId)){		
			if (reportOb.getProfile() == null
					|| "".equals(reportOb.getProfile().trim())) {
				exceptionMap.put("profileError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ReportValidator.class,
						"profileError");
			}
				}
			
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
			}
			DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(),df.format(new Date()), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
			}
			
			DefaultLogger.debug("ReportValidator","====FromDate====="+ reportOb.getFromDate());
			DefaultLogger.debug("ReportValidator","====ToDate====="+ reportOb.getToDate());
			DefaultLogger.debug("ReportValidator","====currentDate====="+df.format(new Date()));
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getToDate(),df.format(new Date()), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.report.toDate.later.currentDate"));
			}
			
		}
		
		
		
		if("RPT0103".equals(reportId))
		{
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if(StringUtils.isBlank(reportOb.getToDate()) ) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
			}
			if(StringUtils.isBlank(reportOb.getFromDate())) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
			}
			
			if(StringUtils.isNotBlank(reportOb.getFromDate()) && StringUtils.isNotBlank(reportOb.getToDate()) ) {
				if (!(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
				}
			}
			if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
				if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
				}
			}
			if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
					&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
				
					                long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
					                if(dateDiff>366) {
					                    errorCode="compareDate";
					                    exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					                            "From Date", "366 days from To Date"));
					                }
					            }
		}
		
		if("RPT0104".equals(reportId))
		{
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if(StringUtils.isBlank(reportOb.getToDate()) ) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
			}
			if(StringUtils.isBlank(reportOb.getFromDate())) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
			}
			
			if(StringUtils.isNotBlank(reportOb.getFromDate()) && StringUtils.isNotBlank(reportOb.getToDate()) ) {
				if (!(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"From Date", "To Date"));
				}
			}
			if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
					&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
				
					                long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
					                if(dateDiff>3650) {
					                    errorCode="compareDate";
					                    exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					                            "From Date", "3650 days/10 years from To Date"));
					                }
					            }
		}

		//Uma:For Cam Quarter Activity CR
		if("RPT0059".equals(reportId)){		
			if (reportOb.getQuarter() == null || "".equals(reportOb.getQuarter())) {
				exceptionMap.put("camReportQuarter", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ReportValidator.class,"camReportQuarter");
			}
		}

		//Added by Uma Khot: Start:For Monthly Basel Report 08/09/2015
		
		
		if("RPT0054".equals(reportId)){		
			if (reportOb.getSecurityType() == null
					|| "".equals(reportOb.getSecurityType())) {
				exceptionMap.put("securityTypeError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(ReportValidator.class,
						"securityTypeError");
			}
		}

		//Added by Uma Khot: End:For Monthly Basel Report 08/09/2015
		//Added for Auto Upload Exchange rate CR
		if("RPT0063".equals(reportId) || "RPT0062".equals(reportId) || "RPT0069".equals(reportId))
		{
			boolean isMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDate(reportOb.getFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
			}
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDate(reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.report.toDate.later.currentDate"));
			}
			
			if("RPT0069".equals(reportId)) {
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>7) {
					errorCode="compareDate";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "7 Days from To Date"));
				}
			}
			
			/*if (!isMandatory
					&& !(errorCode = UIValidator.compareDate(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						 "To Date" ,"From Date"));
			}*/
		}
		
		if("RPT0087".equals(reportId)) {
			boolean isMandatory = false;
			boolean isCompare= true;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
				isCompare=false;
			}
			if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
				isCompare=false;
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDate(reportOb.getFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("fromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
				isCompare=false;
			}
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDate(reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("toDateError", new ActionMessage("error.report.toDate.later.currentDate"));
				isCompare=false;
			}
			if(isCompare) {
				long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
				if(dateDiff>7) {
					errorCode="compareDate";
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "7 Days from To Date"));
				}
			}
			if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
				if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
				}
			}
		}
		
		if("RPT0070".equals(reportId)) {
			boolean isMandatory = false;
			boolean isScodToDateMandatory = false;
			String errorCode = "";
			Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
				if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
					exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
				}
			}
			
			if (!(errorCode = Validator.checkDate(reportOb.getScodFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("scodFromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isMandatory = true;
			}
			
			/*if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDateWithRange(reportOb.getScodFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("scodFromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareBackDateWithRange(reportOb.getScodFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("scodFromDateError", new ActionMessage("error.report.fromDate.before.currentDate"));//error.report.fromDate.before.currentDate
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDateWithRange(reportOb.getScodFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("scodFromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
			}
			*/
			//new validation
			if (!(errorCode = Validator.checkDate(reportOb.getScodToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("scodToDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				isScodToDateMandatory=true;
			}
			
			if(!isScodToDateMandatory  && !isMandatory){
			long dateDiff=UIValidator.calculateDateDiff(reportOb.getScodFromDate(),reportOb.getScodToDate(),  locale);
			if(dateDiff>180) {
				errorCode="compareDate.greater";
				exceptionMap.put("scodFromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"SCOD From Date and SCOD to Date difference", "6 months"));
			}
			}
			
		/*	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
			Date dateobj = new Date();
			long dateDiff=UIValidator.calculateDateDiff(reportOb.getScodFromDate(),df.format(dateobj),  locale);
			if(dateDiff>180) {
				errorCode="compareDate";
				exceptionMap.put("scodFromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"SCOD From Date", "6 months from Date"));
			}
			*/
			
	/*		if(!(reportOb.getScodToDate()).isEmpty() && reportOb.getScodToDate() != null) {
//			if (!isMandatory
//					&& !(errorCode = UIValidator.compareFutureDateWithRange(reportOb.getScodToDate(), locale))
//							.equals(Validator.ERROR_NONE)) {
//				exceptionMap.put("scodToDateError", new ActionMessage("error.report.toDate.later.currentDate"));
//			}
			
			if (!isMandatory
			&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getScodFromDate(), reportOb.getScodToDate(), locale))
					.equals(Validator.ERROR_NONE)) {
		exceptionMap.put("scodFromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
				"From Date", "To Date"));
			}
			
			}*/
			
			
			if(!(reportOb.getEscodFromDate()).isEmpty() && reportOb.getEscodFromDate() != null) {
			if (!isMandatory
					&& !(errorCode = UIValidator.compareFutureDateWithRange(reportOb.getEscodFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("escodfromDateError", new ActionMessage("error.report.fromDate.later.currentDate"));
			}
			
			if (!isMandatory
					&& !(errorCode = UIValidator.compareBackDateWithRange(reportOb.getEscodFromDate(), locale))
							.equals(Validator.ERROR_NONE)) {
				exceptionMap.put("escodfromDateError", new ActionMessage("error.report.fromDate.before.currentDate"));
			}
			
			
//			if(!(reportOb.getEscodToDate()).isEmpty() && reportOb.getEscodToDate() != null) {
//				if (!isMandatory
//						&& !(errorCode = UIValidator.compareFutureAndBackDate(reportOb.getEscodToDate(), locale))
//								.equals(Validator.ERROR_NONE)) {
//					exceptionMap.put("escodtoDateError", new ActionMessage("error.report.toDate.later.currentDate"));
//				}
//			}
			}
			
			if(!(reportOb.getEscodFromDate()).isEmpty() && reportOb.getEscodFromDate() != null) {
				if(!(reportOb.getEscodToDate()).isEmpty() && reportOb.getEscodToDate() != null) {
				
				if (!isMandatory
						&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getEscodFromDate(), reportOb.getEscodToDate(), locale))
								.equals(Validator.ERROR_NONE)) {
					exceptionMap.put("escodfromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "To Date"));
						}
			}
			}
			if((reportOb.getEscodFromDate()).isEmpty() || reportOb.getEscodFromDate() == null) {
				if(!(reportOb.getEscodToDate()).isEmpty() && reportOb.getEscodToDate() != null) {
					exceptionMap.put("escodfromDateError", new ActionMessage("error.report.fromDate.enter"));
				}
			}
			
		}
		
		//added by Prachit For Mortgage Report
				if ("RPT0073".equals(reportId)) {
					boolean isMandatory = false;
					String errorCode = "";
					Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
					
					if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
						exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
						isMandatory = true;
					}

					if(null!=reportOb.getFromDate() && !"".equals(reportOb.getFromDate()) 
							&& null!=reportOb.getToDate() && !"".equals(reportOb.getToDate())) {
						if (!isMandatory
							&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
									.equals(Validator.ERROR_NONE)) {
						exceptionMap.put("fromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
								"From Date", "To Date"));
						}
						if (!isMandatory
								&& !(errorCode = UIValidator.compareDateWithRange(reportOb.getFromDate(), reportOb.getToDate(), locale))
										.equals(Validator.ERROR_NONE)) {
							exceptionMap.put("toDateError", new ActionMessage("error.report.fromDate.difference"));
								}
//						DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//						Calendar date1 = Calendar.getInstance();
//						date1.setTime(DateUtil.convertDate(locale, reportOb.getFromDate()));
//						
//						date1.add(Calendar.MONTH, 6);
//						String d2 = df.format(date1.getTime());
//						System.out.println("Date 2 To Date : =>"+d2);
						
					}
					
					if(null == reportOb.getSecurityStatus() || "".equals(reportOb.getSecurityStatus())) {
						exceptionMap.put("securityStatusError", new ActionMessage("error.date.mandatory"));
					}
					
					if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())) {
						if(null == reportOb.getPartyId() || "".equals(reportOb.getPartyId())) {
							exceptionMap.put("filterPartyError", new ActionMessage("error.date.mandatory"));
						}
						
					}
				}
				//End Santosh
				
//				if (!isMandatory
//						&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getEscodFromDate(), reportOb.getEscodToDate(), locale))
//								.equals(Validator.ERROR_NONE)) {
//					exceptionMap.put("escodfromDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
//							"From Date", "To Date"));
//						}
		

			if("RPT0084".equals(reportId)){
				String errorCode = "";
				Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

				if(StringUtils.isBlank(reportOb.getIsExceptionalUser())) {
					exceptionMap.put("isExceptionalUserError", new ActionMessage("error.string.mandatory"));
				}
				if(StringUtils.isNotBlank(reportOb.getFromDate()) && StringUtils.isBlank(reportOb.getToDate()) ) {
					exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				}
				else if(StringUtils.isBlank(reportOb.getFromDate())) {
					exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
				}
				else if(StringUtils.isNotBlank(reportOb.getFromDate()) && StringUtils.isNotBlank(reportOb.getToDate()) ) {
					if (!(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
								.equals(Validator.ERROR_NONE)) {
					exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
							"From Date", "To Date"));
					}
					long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
					if(dateDiff>31) {
						errorCode="compareDate";
						exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
								"From Date", "1 month from To Date"));
					}
				}
			}

		
			if ("RPT0085".equals(reportId)) {

				if (reportOb.getEventOrCriteria() == null || "".equals(reportOb.getEventOrCriteria())) {
					exceptionMap.put("eventOrCriteriaError",  new ActionMessage("error.string.mandatory"));
				}

				if ("Stock Statement is due for more than 2 months".equals(reportOb.getEventOrCriteria())
						|| "Post updation of stock statement along with DP arrived".equals(reportOb.getEventOrCriteria())

						) {
					if (reportOb.getSelectYearDropdown() == null || "".equals(reportOb.getSelectYearDropdown())) {
						exceptionMap.put("selectYearDropdownError",  new ActionMessage("error.string.mandatory"));
					}
				}

				if ("Limit is reduced or increased due to DP amount is changed".equals(reportOb.getEventOrCriteria())) {

					boolean isMandatory = false;
					String errorCode = "";
					Locale locale = (Locale) input.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
					if (!(errorCode = Validator.checkDate(reportOb.getFromDate(), true, locale)).equals(Validator.ERROR_NONE)) {
						exceptionMap.put("fromDateError", new ActionMessage("error.date.mandatory", "1", "256"));
						isMandatory = true;
					}
					if (!(errorCode = Validator.checkDate(reportOb.getToDate(), true, locale)).equals(Validator.ERROR_NONE)) {
						exceptionMap.put("toDateError", new ActionMessage("error.date.mandatory", "1", "256"));
						isMandatory = true;
					}
					else if("RPT0089".equals(reportId)) {
						if (!ERROR_NONE.equals(errorCode = Validator.checkDate(reportOb.getEodSyncUpDate(), true, Locale.getDefault()))) {
							exceptionMap.put("eodSyncUpDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
						}
						if(ERROR_NONE.equals(errorCode)) {
							Date inputDate = DateUtil.convertDate(reportOb.getEodSyncUpDate());
							if ( inputDate.compareTo(UIUtil.getDate()) >= 0 ) {
								exceptionMap.put("eodSyncUpDateError", new ActionMessage("error.date.compareDate.more", "Sync Up Date", "Current Date"));
							}				
						}
					}

					if (!isMandatory
							&& !(errorCode = UIValidator.compareDateEarlier(reportOb.getFromDate(), reportOb.getToDate(), locale))
							.equals(Validator.ERROR_NONE)) {
						exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
								"From Date", "To Date"));
					}else {
						long dateDiff=UIValidator.calculateDateDiff(reportOb.getFromDate(), reportOb.getToDate(), locale);
						if(dateDiff>6) {
							errorCode="compareDate.greater";
							exceptionMap.put("toDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
									"fromDate and toDate difference", "7 days"));
						}
					}
				}
			}
				
				if ("RPT0086".equals(reportId)) {
					
					if("SELECTD_PARTY".equals(reportOb.getFilterPartyMode())){
						if (!(Validator.checkString(reportOb.getParty(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
							exceptionMap.put("filterPartyError",  new ActionMessage("error.report.select.customer.mandatory"));
						}
					}
					
					if("".equals(reportOb.getMonthsOfAuditTrail()) || reportOb.getMonthsOfAuditTrail() == null){
							exceptionMap.put("monthsOfAuditTrailError",  new ActionMessage("error.string.mandatory"));
					}
				}

		return exceptionMap;
	} 
}
