package com.integrosys.cms.ui.manualinput.customer;

/**
 * Purpose : Used for Validating customer
 *
 * @author $Author: Bharat waghela $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-13 15:13:16 +0800 
 * Tag : $Name$
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLogDao;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationDao;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.manualinput.party.bus.IPanValidationLogDao;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingDao;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.systemBankBranch.SystemBranchValidator;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class ManualInputCustomerValidator {
	public static ActionErrors validateInput(ActionForm commonform, Locale locale) {

		ActionErrors errors = new ActionErrors();
		String errorCode = null;

		ManualInputCustomerInfoForm form = (ManualInputCustomerInfoForm) commonform;

		
		String cinllpin = null ;
		int length = 0 ,numeric = 0 ;
		char alpha = '\u0000' ;
		if (!(form.getCinLlpin() == null || "".equals(form.getCinLlpin().trim()))) {
			 cinllpin = form.getCinLlpin();
			 length = cinllpin.length();
			 alpha = cinllpin.charAt(0);
			 numeric = cinllpin.charAt(length-1); 
			}
		
		String event = form.getEvent();
		
		if(event.equals("save_vendor_name")
				|| event.equals("save_vendor_name_in_edit")
				|| event.equals("save_vendor_name_in_resubmit")
				|| event.equals("save_edited_vendor")
				|| event.equals("save_edited_vendor_in_edit")
				|| event.equals("save_edited_vendor_in_resubmit")
				) {
			if (form.getVendorName() == null || "".equals(form.getVendorName().trim())) {
				errors.add("VendorNameError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "VendorNameError");
			}
			
			return errors;
		}
		
		if (event.equals("save_other_system") || event.equals("save_other_system_in_edit")
				|| event.equals("save_other_system_in_resubmit") || event.equals("save_edited_system")
				|| event.equals("save_edited_system_in_edit") || event.equals("save_edited_system_in_resubmit")
				|| event.equals("save_other_system_partymaker") || event.equals("save_other_system_in_edit_partymaker")
				|| event.equals("save_other_system_in_resubmit_partymaker")
				|| event.equals("save_edited_system_partymaker")
				|| event.equals("save_edited_system_in_edit_partymaker")
				|| event.equals("save_edited_system_in_resubmit_partymaker") ) {

			if (form.getSystem() == null || "".equals(form.getSystem().trim())) {
				errors.add("systemError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemError");
			}
			if (form.getSystemCustomerId() == null || "".equals(form.getSystemCustomerId().trim())) {
				errors.add("systemCustomerIdError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemCustomerIdError");
			} else if (form.getSystemCustomerId().length() > 16) {
				errors.add("systemCustomerIdLengthError", new ActionMessage("error.systemCustomerId.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.systemCustomerId.length.exceeded");
			} else {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSystemCustomerId());
				if (nameFlag == true)
					errors.add("specialCharacterSystemCustomerIdError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			
			return errors;
		}
		
		if(event.equals("WS_create_customer")) {
			if (form.getSystem() == null || "".equals(form.getSystem().trim())) {
				errors.add("systemError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemError");
			}
			if (form.getSystemCustomerId() == null || "".equals(form.getSystemCustomerId().trim())) {
				errors.add("systemCustomerIdError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemCustomerIdError");
			} else if (form.getSystemCustomerId().length() > 16) {
				errors.add("systemCustomerIdLengthError", new ActionMessage("error.systemCustomerId.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.systemCustomerId.length.exceeded");
			} else {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSystemCustomerId());
				if (nameFlag == true)
					errors.add("specialCharacterSystemCustomerIdError",new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		if (form.getGobutton() != null) {
			if (form.getGobutton().equals("1")) {
				if (!(errorCode = Validator.checkString(form.getCustomerNameShort(), true, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("customerShortName",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode)));
				}
			} else if (form.getGobutton().equals("2")) {

				if (!(errorCode = Validator.checkString(form.getLegalId(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("legalId",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
				}
			} else if (form.getGobutton().equals("3")) {
				if (!(errorCode = Validator.checkString(form.getSystemName(), true, 2, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("systemName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode)));
				}
				if (!(errorCode = Validator.checkString(form.getSystemId(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("systemId",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "40"));
				}
			}

		}
		if (event.equals("create_customer_brmaker") || event.equals("edit_customer_brmaker")
				|| event.equals("maker_resubmit_create_customer_brmaker")
				|| event.equals("maker_resubmit_update_customer_brmaker")
				|| event.equals("create_draft_customer_brmaker") || event.equals("update_draft_customer_brmaker")) {

			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}

			if (form.getMainBranch() == null || "".equals(form.getMainBranch().trim())) {
				errors.add("mainBranchError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "mainBranchError");
			}
			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}
			if (form.getCustomerNameShort() != null && !("".equals(form.getCustomerNameShort().trim()))) {
				if (form.getCustomerNameShort().length() > 100) {
					errors.add("customerNameShortLengthError",
							new ActionMessage("error.customerNameShort.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.customerNameShort.length.exceeded");
				}
			}
			if (form.getCustomerSegment() == null || "".equals(form.getCustomerSegment().trim())) {
				errors.add("customerSegmentError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerSegmentError");
			}
			if (form.getStatus() == null || "".equals(form.getStatus().trim())) {
				errors.add("statusError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");
			}
			
		}	
		if (event.equals("create_customer") || event.equals("edit_customer") || event.equals("validate_customer_form")
				|| event.equals("maker_resubmit_create_customer") || event.equals("maker_resubmit_update_customer")
				|| event.equals("validate_customer_form_in_edit") || event.equals("validate_customer_form_in_resubmit")
				|| event.equals("create_draft_customer") || event.equals("update_draft_customer")
				|| event.equals("WS_create_customer") || event.equals("WS_update_customer")) {
			

			if (!event.equalsIgnoreCase("WS_create_customer") && !event.equalsIgnoreCase("WS_update_customer")) {
				IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
				List udfMandatoryList = udfDao.getUdfByMandatory("1");

				List udfNumericList = udfDao.getUdfByFieldTypeId("1", 7);
				System.out.println("CIF Id "+form.getCifId());
				
				double releaseAmount = validateScmReleaseAmount(form.getCifId());
				System.out.println("releaseAmount "+releaseAmount);

				if (udfMandatoryList != null) {
					for (int udf = 0; udf < udfMandatoryList.size(); udf++) {
						IUdf iUdf = (IUdf) udfMandatoryList.get(udf);
						switch (iUdf.getSequence()) {
						case 1:
							if (form.getUdf1() == null || form.getUdf1().trim().equals("")) {
								errors.add("1udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 2:
							if (form.getUdf2() == null || form.getUdf2().trim().equals("")) {
								errors.add("2udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 3:
							if (form.getUdf3() == null || form.getUdf3().trim().equals("")) {
								errors.add("3udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 4:
							if (form.getUdf4() == null || form.getUdf4().trim().equals("")) {
								errors.add("4udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 5:
							if (form.getUdf5() == null || form.getUdf5().trim().equals("")) {
								errors.add("5udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 6:
							if (form.getUdf6() == null || form.getUdf6().trim().equals("")) {
								errors.add("6udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 7:
							if (form.getUdf7() == null || form.getUdf7().trim().equals("")) {
								errors.add("7udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 8:
							if (form.getUdf8() == null || form.getUdf8().trim().equals("")) {
								errors.add("8udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 9:
							if (form.getUdf9() == null || form.getUdf9().trim().equals("")) {
								errors.add("9udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 10:
							if (form.getUdf10() == null || form.getUdf10().trim().equals("")) {
								errors.add("10udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 11:
							if (form.getUdf11() == null || form.getUdf11().trim().equals("")) {
								errors.add("11udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 12:
							if (form.getUdf12() == null || form.getUdf12().trim().equals("")) {
								errors.add("12udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 13:
							if (form.getUdf13() == null || form.getUdf13().trim().equals("")) {
								errors.add("13udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 14:
							if (form.getUdf14() == null || form.getUdf14().trim().equals("")) {
								errors.add("14udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 15:
							if (form.getUdf15() == null || form.getUdf15().trim().equals("")) {
								errors.add("15udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 16:
							if (form.getUdf16() == null || form.getUdf16().trim().equals("")) {
								errors.add("16udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 17:
							if (form.getUdf17() == null || form.getUdf17().trim().equals("")) {
								errors.add("17udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							/*if (form.getUdf17() != null || !form.getUdf17().trim().equals("")) {
								if(form.getUdf17().equals("No") && releaseAmount!=0) {
									errors.add("17udfError",
											new ActionMessage("error.scmFlag.udf.change"));
								}
									
								}*/
							break;
						case 18:
							if (form.getUdf18() == null || form.getUdf18().trim().equals("")) {
								errors.add("18udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 19:
							if (form.getUdf19() == null || form.getUdf19().trim().equals("")) {
								errors.add("19udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 20:
							if (form.getUdf20() == null || form.getUdf20().trim().equals("")) {
								errors.add("20udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 21:
							if (form.getUdf21() == null || form.getUdf21().trim().equals("")) {
								errors.add("21udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 22:
							if (form.getUdf22() == null || form.getUdf22().trim().equals("")) {
								errors.add("22udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 23:
							if (form.getUdf23() == null || form.getUdf23().trim().equals("")) {
								errors.add("23udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 24:
							if (form.getUdf24() == null || form.getUdf24().trim().equals("")) {
								errors.add("24udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 25:
							if (form.getUdf25() == null || form.getUdf25().trim().equals("")) {
								errors.add("25udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 26:
							if (form.getUdf26() == null || form.getUdf26().trim().equals("")) {
								errors.add("26udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 27:
							if (form.getUdf27() == null || form.getUdf27().trim().equals("")) {
								errors.add("27udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 28:
							if (form.getUdf28() == null || form.getUdf28().trim().equals("")) {
								errors.add("28udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 29:
							if (form.getUdf29() == null || form.getUdf29().trim().equals("")) {
								errors.add("29udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 30:
							if (form.getUdf30() == null || form.getUdf30().trim().equals("")) {
								errors.add("30udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 31:
							if (form.getUdf31() == null || form.getUdf31().trim().equals("")) {
								errors.add("31udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 32:
							if (form.getUdf32() == null || form.getUdf32().trim().equals("")) {
								errors.add("32udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 33:
							if (form.getUdf33() == null || form.getUdf33().trim().equals("")) {
								errors.add("33udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 34:
							if (form.getUdf34() == null || form.getUdf34().trim().equals("")) {
								errors.add("34udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 35:
							if (form.getUdf35() == null || form.getUdf35().trim().equals("")) {
								errors.add("35udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 36:
							if (form.getUdf36() == null || form.getUdf36().trim().equals("")) {
								errors.add("36udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 37:
							if (form.getUdf37() == null || form.getUdf37().trim().equals("")) {
								errors.add("37udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 38:
							if (form.getUdf38() == null || form.getUdf38().trim().equals("")) {
								errors.add("38udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 39:
							if (form.getUdf39() == null || form.getUdf39().trim().equals("")) {
								errors.add("39udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 40:
							if (form.getUdf40() == null || form.getUdf40().trim().equals("")) {
								errors.add("40udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 41:
							if (form.getUdf41() == null || form.getUdf41().trim().equals("")) {
								errors.add("41udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 42:
							if (form.getUdf42() == null || form.getUdf42().trim().equals("")) {
								errors.add("42udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 43:
							if (form.getUdf43() == null || form.getUdf43().trim().equals("")) {
								errors.add("43udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 44:
							if (form.getUdf44() == null || form.getUdf44().trim().equals("")) {
								errors.add("44udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 45:
							if (form.getUdf45() == null || form.getUdf45().trim().equals("")) {
								errors.add("45udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 46:
							if (form.getUdf46() == null || form.getUdf46().trim().equals("")) {
								errors.add("46udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 47:
							if (form.getUdf47() == null || form.getUdf47().trim().equals("")) {
								errors.add("47udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 48:
							if (form.getUdf48() == null || form.getUdf48().trim().equals("")) {
								errors.add("48udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 49:
							if (form.getUdf49() == null || form.getUdf49().trim().equals("")) {
								errors.add("49udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 50:
							if (form.getUdf50() == null || form.getUdf50().trim().equals("")) {
								errors.add("50udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 51:
							if (form.getUdf51() == null || form.getUdf51().trim().equals("")) {
								errors.add("51udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 52:
							if (form.getUdf52() == null || form.getUdf52().trim().equals("")) {
								errors.add("52udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 53:
							if (form.getUdf53() == null || form.getUdf53().trim().equals("")) {
								errors.add("53udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 54:
							if (form.getUdf54() == null || form.getUdf54().trim().equals("")) {
								errors.add("54udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 55:
							if (form.getUdf55() == null || form.getUdf55().trim().equals("")) {
								errors.add("55udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 56:
							if (form.getUdf56() == null || form.getUdf56().trim().equals("")) {
								errors.add("56udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 57:
							if (form.getUdf57() == null || form.getUdf57().trim().equals("")) {
								errors.add("57udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 58:
							if (form.getUdf58() == null || form.getUdf58().trim().equals("")) {
								errors.add("58udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 59:
							if (form.getUdf59() == null || form.getUdf59().trim().equals("")) {
								errors.add("59udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 60:
							if (form.getUdf60() == null || form.getUdf60().trim().equals("")) {
								errors.add("60udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 61:
							if (form.getUdf61() == null || form.getUdf61().trim().equals("")) {
								errors.add("61udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 62:
							if (form.getUdf62() == null || form.getUdf62().trim().equals("")) {
								errors.add("62udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 63:
							if (form.getUdf63() == null || form.getUdf63().trim().equals("")) {
								errors.add("63udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 64:
							if (form.getUdf64() == null || form.getUdf64().trim().equals("")) {
								errors.add("64udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 65:
							if (form.getUdf65() == null || form.getUdf65().trim().equals("")) {
								errors.add("65udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 66:
							if (form.getUdf66() == null || form.getUdf66().trim().equals("")) {
								errors.add("66udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 67:
							if (form.getUdf67() == null || form.getUdf67().trim().equals("")) {
								errors.add("67udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 68:
							if (form.getUdf68() == null || form.getUdf68().trim().equals("")) {
								errors.add("68udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 69:
							if (form.getUdf69() == null || form.getUdf69().trim().equals("")) {
								errors.add("69udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 70:
							if (form.getUdf70() == null || form.getUdf70().trim().equals("")) {
								errors.add("70udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 71:
							if (form.getUdf71() == null || form.getUdf71().trim().equals("")) {
								errors.add("71udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 72:
							if (form.getUdf72() == null || form.getUdf72().trim().equals("")) {
								errors.add("72udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 73:
							if (form.getUdf73() == null || form.getUdf73().trim().equals("")) {
								errors.add("73udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 74:
							if (form.getUdf74() == null || form.getUdf74().trim().equals("")) {
								errors.add("74udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 75:
							if (form.getUdf75() == null || form.getUdf75().trim().equals("")) {
								errors.add("75udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 76:
							if (form.getUdf76() == null || form.getUdf76().trim().equals("")) {
								errors.add("76udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 77:
							if (form.getUdf77() == null || form.getUdf77().trim().equals("")) {
								errors.add("77udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 78:
							if (form.getUdf78() == null || form.getUdf78().trim().equals("")) {
								errors.add("78udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 79:
							if (form.getUdf79() == null || form.getUdf79().trim().equals("")) {
								errors.add("79udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 80:
							if (form.getUdf80() == null || form.getUdf80().trim().equals("")) {
								errors.add("80udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 81:
							if (form.getUdf81() == null || form.getUdf81().trim().equals("")) {
								errors.add("81udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 82:
							if (form.getUdf82() == null || form.getUdf82().trim().equals("")) {
								errors.add("82udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 83:
							if (form.getUdf83() == null || form.getUdf83().trim().equals("")) {
								errors.add("83udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 84:
							if (form.getUdf84() == null || form.getUdf84().trim().equals("")) {
								errors.add("84udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 85:
							if (form.getUdf85() == null || form.getUdf85().trim().equals("")) {
								errors.add("85udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 86:
							if (form.getUdf86() == null || form.getUdf86().trim().equals("")) {
								errors.add("86udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 87:
							if (form.getUdf87() == null || form.getUdf87().trim().equals("")) {
								errors.add("87udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 88:
							if (form.getUdf88() == null || form.getUdf88().trim().equals("")) {
								errors.add("88udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 89:
							if (form.getUdf89() == null || form.getUdf89().trim().equals("")) {
								errors.add("89udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 90:
							if (form.getUdf90() == null || form.getUdf90().trim().equals("")) {
								errors.add("90udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 91:
							if (form.getUdf91() == null || form.getUdf91().trim().equals("")) {
								errors.add("91udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 92:
							if (form.getUdf92() == null || form.getUdf92().trim().equals("")) {
								errors.add("92udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 93:
							if (form.getUdf93() == null || form.getUdf93().trim().equals("")) {
								errors.add("93udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 94:
							if (form.getUdf94() == null || form.getUdf94().trim().equals("")) {
								errors.add("94udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 95:
							if (form.getUdf95() == null || form.getUdf95().trim().equals("")) {
								errors.add("95udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 96:
							if (form.getUdf96() == null || form.getUdf96().trim().equals("")) {
								errors.add("96udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 97:
							if (form.getUdf97() == null || (form.getUdf97() != null && form.getUdf97().trim().equals(""))){
								errors.add("97udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
								
								if (form.getUdf97() != null && !form.getUdf97().trim().equals("")) {
								if(form.getUdf97().equals("No") && releaseAmount!=0) {
									errors.add("97udfError",
											new ActionMessage("error.scmFlag.udf.change"));
								}
								}
							}
							break;
						case 98:
							if (form.getUdf98() == null || form.getUdf98().trim().equals("")) {
								errors.add("98udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 99:
							if (form.getUdf99() == null || form.getUdf99().trim().equals("")) {
								errors.add("99udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 100:
							if (form.getUdf100() == null || form.getUdf100().trim().equals("")) {
								errors.add("100udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 101:
							if (form.getUdf101() == null || form.getUdf101().trim().equals("")) {
								errors.add("101udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 102:
							if (form.getUdf102() == null || form.getUdf102().trim().equals("")) {
								errors.add("102udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 103:
							if (form.getUdf103() == null || form.getUdf103().trim().equals("")) {
								errors.add("103udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 104:
							if (form.getUdf104() == null || form.getUdf104().trim().equals("")) {
								errors.add("104udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 105:
							if (form.getUdf105() == null || form.getUdf105().trim().equals("")) {
								errors.add("105udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 106:
							if (form.getUdf106() == null || form.getUdf106().trim().equals("")) {
								errors.add("106udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 107:
							if (form.getUdf107() == null || form.getUdf107().trim().equals("")) {
								errors.add("107udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 108:
							if (form.getUdf108() == null || form.getUdf108().trim().equals("")) {
								errors.add("108udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 109:
							if (form.getUdf109() == null || form.getUdf109().trim().equals("")) {
								errors.add("109udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 110:
							if (form.getUdf110() == null || form.getUdf110().trim().equals("")) {
								errors.add("110udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 111:
							if (form.getUdf111() == null || form.getUdf111().trim().equals("")) {
								errors.add("111udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 112:
							if (form.getUdf112() == null || form.getUdf112().trim().equals("")) {
								errors.add("112udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 113:
							if (form.getUdf113() == null || form.getUdf113().trim().equals("")) {
								errors.add("113udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 114:
							if (form.getUdf114() == null || form.getUdf114().trim().equals("")) {
								errors.add("114udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 115:
							if (form.getUdf115() == null || form.getUdf115().trim().equals("")) {
								errors.add("115udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 116:
							if (form.getUdf116() == null || form.getUdf116().trim().equals("")) {
								errors.add("116udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 117:
							if (form.getUdf117() == null || form.getUdf117().trim().equals("")) {
								errors.add("117udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 118:
							if (form.getUdf118() == null || form.getUdf118().trim().equals("")) {
								errors.add("118udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 119:
							if (form.getUdf119() == null || form.getUdf119().trim().equals("")) {
								errors.add("119udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						case 120:
							if (form.getUdf120() == null || form.getUdf120().trim().equals("")) {
								errors.add("120udfError",
										new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
							}
							break;
						default:
							break;
						}

					}
				}

				if (udfNumericList != null) {
					for (int udf = 0; udf < udfNumericList.size(); udf++) {
						IUdf iUdf = (IUdf) udfNumericList.get(udf);
						switch (iUdf.getSequence()) {

						case 1:
							if (!(form.getUdf1() == null || form.getUdf1().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf1().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("1udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 2:
							if (!(form.getUdf2() == null || form.getUdf2().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf2().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("2udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 3:
							if (!(form.getUdf3() == null || form.getUdf3().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf3().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("3udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 4:
							if (!(form.getUdf4() == null || form.getUdf4().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf4().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("4udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 5:
							if (!(form.getUdf5() == null || form.getUdf5().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf5().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("5udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 6:
							if (!(form.getUdf6() == null || form.getUdf6().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf6().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("6udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 7:
							if (!(form.getUdf7() == null || form.getUdf7().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf7().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("7udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 8:
							if (!(form.getUdf8() == null || form.getUdf8().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf8().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("8udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 9:
							if (!(form.getUdf9() == null || form.getUdf9().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf9().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("9udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 10:
							if (!(form.getUdf10() == null || form.getUdf10().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf10().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("10udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 11:
							if (!(form.getUdf11() == null || form.getUdf11().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf11().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("11udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 12:
							if (!(form.getUdf12() == null || form.getUdf12().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf12().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("12udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 13:
							if (!(form.getUdf13() == null || form.getUdf13().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf13().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("13udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 14:
							if (!(form.getUdf14() == null || form.getUdf14().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf14().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("14udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 15:
							if (!(form.getUdf15() == null || form.getUdf15().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf15().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("15udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 16:
							if (!(form.getUdf16() == null || form.getUdf16().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf16().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("16udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 17:
							if (!(form.getUdf17() == null || form.getUdf17().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf17().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("17udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 18:
							if (!(form.getUdf18() == null || form.getUdf18().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf18().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("18udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 19:
							if (!(form.getUdf19() == null || form.getUdf19().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf19().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("19udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 20:
							if (!(form.getUdf20() == null || form.getUdf20().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf20().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("20udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 21:
							if (!(form.getUdf21() == null || form.getUdf21().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf21().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("21udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 22:
							if (!(form.getUdf22() == null || form.getUdf22().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf22().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("22udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 23:
							if (!(form.getUdf23() == null || form.getUdf23().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf23().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("23udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 24:
							if (!(form.getUdf24() == null || form.getUdf24().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf24().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("24udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 25:
							if (!(form.getUdf25() == null || form.getUdf25().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf25().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("25udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 26:
							if (!(form.getUdf26() == null || form.getUdf26().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf26().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("26udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 27:
							if (!(form.getUdf27() == null || form.getUdf27().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf27().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("27udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 28:
							if (!(form.getUdf28() == null || form.getUdf28().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf28().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("28udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 29:
							if (!(form.getUdf29() == null || form.getUdf29().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf29().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("29udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 30:
							if (!(form.getUdf30() == null || form.getUdf30().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf30().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("30udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 31:
							if (!(form.getUdf31() == null || form.getUdf31().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf31().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("31udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 32:
							if (!(form.getUdf32() == null || form.getUdf32().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf32().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("32udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 33:
							if (!(form.getUdf33() == null || form.getUdf33().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf33().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("33udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 34:
							if (!(form.getUdf34() == null || form.getUdf34().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf34().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("34udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 35:
							if (!(form.getUdf35() == null || form.getUdf35().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf35().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("35udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 36:
							if (!(form.getUdf36() == null || form.getUdf36().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf36().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("36udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 37:
							if (!(form.getUdf37() == null || form.getUdf37().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf37().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("37udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 38:
							if (!(form.getUdf38() == null || form.getUdf38().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf38().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("38udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 39:
							if (!(form.getUdf39() == null || form.getUdf39().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf39().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("39udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 40:
							if (!(form.getUdf40() == null || form.getUdf40().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf40().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("40udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 41:
							if (!(form.getUdf41() == null || form.getUdf41().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf41().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("41udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 42:
							if (!(form.getUdf42() == null || form.getUdf42().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf42().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("42udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 43:
							if (!(form.getUdf43() == null || form.getUdf43().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf43().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("43udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 44:
							if (!(form.getUdf44() == null || form.getUdf44().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf44().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("44udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 45:
							if (!(form.getUdf45() == null || form.getUdf45().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf45().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("45udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 46:
							if (!(form.getUdf46() == null || form.getUdf46().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf46().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("46udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 47:
							if (!(form.getUdf47() == null || form.getUdf47().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf47().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("47udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 48:
							if (!(form.getUdf48() == null || form.getUdf48().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf48().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("48udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 49:
							if (!(form.getUdf49() == null || form.getUdf49().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf49().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("49udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 50:
							if (!(form.getUdf50() == null || form.getUdf50().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf50().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("50udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 51:
							if (!(form.getUdf51() == null || form.getUdf51().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf51().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("51udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 52:
							if (!(form.getUdf52() == null || form.getUdf52().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf52().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("52udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 53:
							if (!(form.getUdf53() == null || form.getUdf53().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf53().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("53udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 54:
							if (!(form.getUdf54() == null || form.getUdf54().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf54().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("54udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 55:
							if (!(form.getUdf55() == null || form.getUdf55().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf55().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("55udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 56:
							if (!(form.getUdf56() == null || form.getUdf56().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf56().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("56udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 57:
							if (!(form.getUdf57() == null || form.getUdf57().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf57().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("57udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 58:
							if (!(form.getUdf58() == null || form.getUdf58().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf58().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("58udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 59:
							if (!(form.getUdf59() == null || form.getUdf59().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf59().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("59udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 60:
							if (!(form.getUdf60() == null || form.getUdf60().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf60().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("60udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 61:
							if (!(form.getUdf61() == null || form.getUdf61().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf61().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("61udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 62:
							if (!(form.getUdf62() == null || form.getUdf62().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf62().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("62udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 63:
							if (!(form.getUdf63() == null || form.getUdf63().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf63().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("63udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 64:
							if (!(form.getUdf64() == null || form.getUdf64().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf64().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("64udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 65:
							if (!(form.getUdf65() == null || form.getUdf65().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf65().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("65udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 66:
							if (!(form.getUdf66() == null || form.getUdf66().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf66().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("66udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 67:
							if (!(form.getUdf67() == null || form.getUdf67().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf67().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("67udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 68:
							if (!(form.getUdf68() == null || form.getUdf68().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf68().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("68udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 69:
							if (!(form.getUdf69() == null || form.getUdf69().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf69().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("69udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 70:
							if (!(form.getUdf70() == null || form.getUdf70().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf70().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("70udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 71:
							if (!(form.getUdf71() == null || form.getUdf71().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf71().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("71udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 72:
							if (!(form.getUdf72() == null || form.getUdf72().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf72().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("72udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 73:
							if (!(form.getUdf73() == null || form.getUdf73().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf73().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("73udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 74:
							if (!(form.getUdf74() == null || form.getUdf74().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf74().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("74udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 75:
							if (!(form.getUdf75() == null || form.getUdf75().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf75().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("75udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 76:
							if (!(form.getUdf76() == null || form.getUdf76().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf76().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("76udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 77:
							if (!(form.getUdf77() == null || form.getUdf77().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf77().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("77udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 78:
							if (!(form.getUdf78() == null || form.getUdf78().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf78().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("78udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 79:
							if (!(form.getUdf79() == null || form.getUdf79().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf79().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("79udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 80:
							if (!(form.getUdf80() == null || form.getUdf80().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf80().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("80udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 81:
							if (!(form.getUdf81() == null || form.getUdf81().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf81().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("81udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 82:
							if (!(form.getUdf82() == null || form.getUdf82().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf82().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("82udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 83:
							if (!(form.getUdf83() == null || form.getUdf83().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf83().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("83udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 84:
							if (!(form.getUdf84() == null || form.getUdf84().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf84().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("84udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 85:
							if (!(form.getUdf85() == null || form.getUdf85().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf85().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("85udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 86:
							if (!(form.getUdf86() == null || form.getUdf86().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf86().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("86udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 87:
							if (!(form.getUdf87() == null || form.getUdf87().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf87().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("87udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 88:
							if (!(form.getUdf88() == null || form.getUdf88().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf88().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("88udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 89:
							if (!(form.getUdf89() == null || form.getUdf89().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf89().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("89udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 90:
							if (!(form.getUdf90() == null || form.getUdf90().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf90().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("90udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 91:
							if (!(form.getUdf91() == null || form.getUdf91().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf91().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("91udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 92:
							if (!(form.getUdf92() == null || form.getUdf92().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf92().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("92udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 93:
							if (!(form.getUdf93() == null || form.getUdf93().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf93().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("93udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 94:
							if (!(form.getUdf94() == null || form.getUdf94().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf94().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("94udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 95:
							if (!(form.getUdf95() == null || form.getUdf95().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf95().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("95udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 96:
							if (!(form.getUdf96() == null || form.getUdf96().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf96().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("96udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 97:
							if (!(form.getUdf97() == null || form.getUdf97().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf97().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("97udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 98:
							if (!(form.getUdf98() == null || form.getUdf98().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf98().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("98udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 99:
							if (!(form.getUdf99() == null || form.getUdf99().trim().equals(""))) {
								if (!Validator.ERROR_NONE.equals(errorCode = Validator
										.checkNumber(form.getUdf99().toString().trim(), false, 0, 999999999999999.D))) {
									errors.add("99udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 100:
							if (!(form.getUdf100() == null || form.getUdf100().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf100().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("100udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 101:
							if (!(form.getUdf101() == null || form.getUdf101().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf101().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("101udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 102:
							if (!(form.getUdf102() == null || form.getUdf102().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf102().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("102udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 103:
							if (!(form.getUdf103() == null || form.getUdf103().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf103().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("103udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 104:
							if (!(form.getUdf104() == null || form.getUdf104().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf104().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("104udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 105:
							if (!(form.getUdf105() == null || form.getUdf105().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf105().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("105udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 106:
							if (!(form.getUdf106() == null || form.getUdf106().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf106().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("106udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 107:
							if (!(form.getUdf107() == null || form.getUdf107().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf107().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("107udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 108:
							if (!(form.getUdf108() == null || form.getUdf108().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf108().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("108udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 109:
							if (!(form.getUdf109() == null || form.getUdf109().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf109().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("109udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 110:
							if (!(form.getUdf110() == null || form.getUdf110().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf110().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("110udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 111:
							if (!(form.getUdf111() == null || form.getUdf111().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf111().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("111udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 112:
							if (!(form.getUdf112() == null || form.getUdf112().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf112().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("112udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 113:
							if (!(form.getUdf113() == null || form.getUdf113().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf113().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("113udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 114:
							if (!(form.getUdf114() == null || form.getUdf114().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf114().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("114udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 115:
							if (!(form.getUdf115() == null || form.getUdf115().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf115().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("115udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 116:
							if (!(form.getUdf116() == null || form.getUdf116().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf116().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("116udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 117:
							if (!(form.getUdf117() == null || form.getUdf117().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf117().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("117udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 118:
							if (!(form.getUdf118() == null || form.getUdf118().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf118().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("118udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 119:
							if (!(form.getUdf119() == null || form.getUdf119().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf119().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("119udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						case 120:
							if (!(form.getUdf120() == null || form.getUdf120().trim().equals(""))) {
								if (!Validator.ERROR_NONE
										.equals(errorCode = Validator.checkNumber(form.getUdf120().toString().trim(),
												false, 0, 999999999999999.D))) {
									errors.add("120udfError",
											new ActionMessage("error.integer.udf", iUdf.getFieldName()));
								}
							}
							break;
						default:
							break;
						}

					}
				}
			} // IF condition ends for event!="WS_create_customer" check

			if (form.getStatus().equals("INACTIVE") && form.getSubLine().equals("OPEN")) {
				long subprofileID = 0L;
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				List customerList = customerDAO.searchCustomerByCIFNumber(form.getCifId());
				if (customerList != null) {
					OBCMSCustomer customer = (OBCMSCustomer) customerList.get(0);
					subprofileID = customer.getCustomerID();
				}
				if (subprofileID != 0) {
					CustomerDAO customerDao = new CustomerDAO();
					try {

						boolean status = false;
						status = customerDao.getSublineCount(subprofileID);

						if (status) {
							errors.add("statusError", new ActionMessage("error.string.close.status"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (form.getSublineListEmpty() != null && form.getSublineListEmpty().equals("Y")
					&& form.getSubLine().equals("OPEN")) {
				errors.add("sublineListEmptyError", new ActionMessage("error.string.subline.empty"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "sublineListEmptyError");
			}

			if (form.getSystemListEmpty() != null && form.getSystemListEmpty().equals("Y")) {
				errors.add("systemListEmptyError", new ActionMessage("error.string.system.empty"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemListEmptyError");
			}

			if (form.getDirectorListEmpty() != null && form.getDirectorListEmpty().equals("Y")) {
				errors.add("directorListEmptyError", new ActionMessage("error.string.director.empty"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorListEmptyError");
			}

			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getMainBranch() == null || "".equals(form.getMainBranch().trim())) {
				errors.add("mainBranchError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "mainBranchError");
			}
			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}

			if (form.getCustomerNameShort() != null && !("".equals(form.getCustomerNameShort().trim()))) {
				if (form.getCustomerNameShort().length() > 100) {
					errors.add("customerNameShortLengthError",
							new ActionMessage("error.customerNameShort.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.customerNameShort.length.exceeded");
				}
			}
			// Mandatory field validation for WSDL
			if (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer")) {
				if (form.getPan() == null || form.getPan().trim().isEmpty()) {
					errors.add("invalidPanError", new ActionMessage("error.string.mandatory"));
				}
				if (form.getCustomerExtRating() == null || form.getCustomerExtRating().trim().isEmpty()) {
					errors.add("customerExtRating", new ActionMessage("error.string.mandatory"));
				}
				if (form.getEmail() == null || form.getEmail().trim().isEmpty()) {
					errors.add("emailError", new ActionMessage("error.string.mandatory"));
				}
			}
			// End
			if(!(event.equals("WS_create_customer") || event.equals("WS_update_customer"))) {
				if (form.getCreditMgrEmpId() != null && !"".equals(form.getCreditMgrEmpId().trim())) {
					if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCreditMgrEmpId(), true, 1, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add("creditMgrEmpIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
								"100"));
					}
				}
			}
			if (form.getPan() != null && !("".equals(form.getPan().trim()))) {
				/*
				 * boolean nameFlag = ASSTValidator
				 * .isValidAlphaNumStringWithoutSpace(form.getPan()); if
				 * (nameFlag == true) errors.add("specialCharacterPanError", new
				 * ActionMessage( "error.string.invalidCharacter"));
				 */
				if (form.getPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getPan());
					if (flag == true)
						errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				} else {
					errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				}

				// CHECK If PAN NO Already validated with NSDL - Added by Ankit
				// on dt. 19-FEB-2016

				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("VALIDATE_PAN_WITH_NSDL");
				String paramValue = generalParamEntry.getParamValue();
				ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
				int panStatusJdbc = 0;

				// if(!"Y".equalsIgnoreCase(form.getForm6061Checked())){
				if (!"WS_create_customer".equalsIgnoreCase(event) && !"WS_update_customer".equalsIgnoreCase(event)) {
					System.out.println("param value==================> "+paramValue);
					System.out.println("form.getIsPanValidated()=============>"+form.getIsPanValidated());
					System.out.println("form.getPartyId()=============>"+form.getPartyId());
					System.out.println("form.getCifId()=============>"+form.getCifId());
//					System.out.println("form.getPan()=============>"+form.getPan());
					if ("Y".equalsIgnoreCase(paramValue)) {
						if (form.getIsPanValidated() != null && form.getIsPanValidated() == 'N') {
							errors.add("validatePANWithNSDLError",
									new ActionMessage("error.string.validatePANWithNSDL", form.getPan()));
						} else {
							IPanValidationLogDao panValDao = (IPanValidationLogDao) BeanHouse
									.get("panValidationLogDao");
//							System.out.println("===========line no 2143=============>"+panValDao.checkPANAlreadyValidated("actualPANValidationLog", form.getPan(),
//									form.getPartyId()));
//							System.out.println("===========line no 2145=============>"+panValDao.checkPANAlreadyValidated("actualPANValidationLog", form.getPan(),
//									form.getCifId()));
							String tempPartyId = form.getPartyId()!=null?form.getPartyId():form.getCifId();
							if (!panValDao.checkPANAlreadyValidated("actualPANValidationLog", form.getPan(),tempPartyId,form.getPartyNameAsPerPan(),form.getDateOfIncorporation())) {
								System.out.println("===========line no 2147=============>"+tempPartyId);
								panStatusJdbc = custDAO.checkPanStatus(tempPartyId,form.getPan(),form.getPartyNameAsPerPan(),form.getDateOfIncorporation());
								if(panStatusJdbc==0) {
								errors.add("validatePANWithNSDLError",
										new ActionMessage("error.string.validatePANWithNSDL", form.getPan()));
								}
							}
						}
					}
				}
				// End
			}
			
			// LEI Code validation with CCIL
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntryforExposureAmount = generalParamDao
					.getGeneralParamEntryByParamCodeActual("EXPOSURE_AMOUNT_FOR_LEI_VALIDN");
			BigDecimal exposureAmount = new BigDecimal(generalParamEntryforExposureAmount.getParamValue());
			try {
			BigDecimal sanctionedAmount = new BigDecimal(UIUtil.removeComma(form.getTotalSanctionedLimit()));								
//			if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {
				
				if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {
				// boolean flag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getLeiCode());
					boolean flag = ASSTValidator.isValidLeiCode(form.getLeiCode());
					if (!flag)
						errors.add("invalidLeiCodeError",new ActionMessage("error.string.invalidLEIFormat"));
					else if(form.getLeiCode().trim().length()!=20)
						errors.add("invalidLeiCodeError",new ActionMessage("error.string.leiCode.length"));					
				}
				// CHECK If LEI Code Already validated with CCIL - Added by Nilkanth				
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("VALIDATE_LEI_WITH_CCIL");
				String paramValue = generalParamEntry.getParamValue();
				ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
				int leiStatusJdbc = 0;
				if ( sanctionedAmount.compareTo(exposureAmount) == 0 || sanctionedAmount.compareTo(exposureAmount) == 1 ) {
					if(!"Y".equalsIgnoreCase(form.getDeferLEI())){			    
						if (!"WS_create_customer".equalsIgnoreCase(event) && !"WS_update_customer".equalsIgnoreCase(event)) {
							System.out.println("param value==================> "+paramValue);
							System.out.println("form.getIsLeiValidated()=============>"+form.getIsLeiValidated());
							System.out.println("form.getLeiCode()=============>"+form.getLeiCode());
							if ("Y".equalsIgnoreCase(paramValue)) {
								if (form.getIsLeiValidated() != null && form.getIsLeiValidated() == 'N'
										|| (form.getLeiCode() == null || "".equals(form.getLeiCode().trim()) )) {
									errors.add("validateLEIWithCCILError",
											new ActionMessage("error.string.validateLEIWithCCIL", form.getLeiCode()));
								} else {
									if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {	
										ILEIValidationLogDao leiValDao = (ILEIValidationLogDao) BeanHouse.get("logLeiDao");
										String tempPartyId = form.getPartyId()!=null?form.getPartyId():form.getCifId();
										if (!leiValDao.checkLEIAlreadyValidated("actualLEIValidationLog", form.getLeiCode(),tempPartyId)) {
											leiStatusJdbc = custDAO.checkLeiStatus(tempPartyId,form.getLeiCode());
											if(leiStatusJdbc==0) {
												errors.add("validateLEIWithCCILError",
														new ActionMessage("error.string.validateLEIWithCCIL", form.getLeiCode()));
											}
										}
									}
								}
							}
						}
						// End
					}	
				}	
//			}
			}
			catch(NumberFormatException e)
			{
				DefaultLogger.error(ManualInputCustomerValidator.class, "Error in Sanctioned Amount");
				DefaultLogger.error(ManualInputCustomerValidator.class, e.getStackTrace());
			}
			//END
			
			
			if (form.getBankingMethod() != null && !("".equals(form.getBankingMethod().trim()))) {
				if (
						(form.getBankingMethod().contains("MULTIPLE-MULTIPLE")
								&& form.getBankingMethod().contains("CONSORTIUM-CONSORTIUM"))
						&&
						(form.getExceptionalCases()==null || "".equals(form.getExceptionalCases().trim()))) {

					if (!event.equalsIgnoreCase("WS_create_customer")
							&& !event.equalsIgnoreCase("WS_update_customer")) {
						if (form.getNodalLead() == null || "".equals(form.getNodalLead().trim())) {
							errors.add("nodalLeadError", new ActionMessage("error.string.bankingmethod.nodal.empty"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "nodalLeadError");
						}
						if (form.getLeadValue() == null || "".equals(form.getLeadValue().trim())) {
							errors.add("leadValueError", new ActionMessage("error.string.bankingmethod.lead.empty"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "leadValueError");
						}

					}
				}
				if (
						(form.getBankingMethod().contains("MULTIPLE-MULTIPLE")
								|| form.getBankingMethod().contains("CONSORTIUM-CONSORTIUM"))
						&&
						(form.getExceptionalCases()==null || "".equals(form.getExceptionalCases().trim()))) {

					if (!event.equalsIgnoreCase("WS_create_customer")
							&& !event.equalsIgnoreCase("WS_update_customer")) {

						if ( (form.getNodalLead() == null || "".equals(form.getNodalLead().trim())) && (form.getLeadValue() == null || "".equals(form.getLeadValue().trim()))) {
							errors.add("nodalLeadError", new ActionMessage("error.string.bankingmethod.empty"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "nodalLeadError");
						}
					}
				}

				if ((
						form.getBankingMethod().equals("OUTSIDEMULTIPLE") 
						|| form.getBankingMethod().equals("OUTSIDECONSORTIUM")


						|| form.getBankingMethod().contains(",OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
						|| form.getBankingMethod().contains(",OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM")


						|| form.getBankingMethod().contains("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE,")
						|| form.getBankingMethod().contains("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM,")


						|| form.getBankingMethod().equals("OUTSIDEMULTIPLE-OUTSIDE MULTIPLE")
						|| form.getBankingMethod().equals("OUTSIDECONSORTIUM-OUTSIDE CONSORTIUM"))
						&& (form.getExceptionalCases()==null || "".equals(form.getExceptionalCases().trim()))) {

					if (!event.equalsIgnoreCase("WS_create_customer")
							&& !event.equalsIgnoreCase("WS_update_customer")) {
						if ( (form.getNodalLead() == null || "".equals(form.getNodalLead().trim())) && (form.getLeadValue() == null || "".equals(form.getLeadValue().trim()))) {
							errors.add("nodalLeadError", new ActionMessage("error.string.bankingmethod.empty"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "nodalLeadError");
						}
					}



				}
			}
			

				
			if (form.getCustomerSegment() == null || "".equals(form.getCustomerSegment().trim())) {
				errors.add("customerSegmentError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerSegmentError");
			}
			if (form.getStatus() == null || "".equals(form.getStatus().trim())) {
				errors.add("statusError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");
			}
			/*
			 * if (form.getPartyGroupName() == null ||
			 * "".equals(form.getPartyGroupName().trim())) {
			 * errors.add("partyGroupNameError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "partyGroupNameError"); }
			 */
			System.out.println("ManualInputCustomerValidator.java=>Line 2392=>form.getRelationshipMgrEmpCode()=>"+form.getRelationshipMgrEmpCode()+"*****");
			if (form.getRelationshipMgrEmpCode() == null || "".equals(form.getRelationshipMgrEmpCode().trim())) {
				errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.mandatory"));
				// DefaultLogger.debug(ManualInputCustomerValidator.class, "rmRegionError");
			} else {
				List data = fetchRMData(form.getRelationshipMgrEmpCode());
				if (data.isEmpty()) {
					errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.disable.emp.code"));
				}
				
			boolean isAlphaNumeric = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRelationshipMgrEmpCode());
			
			if (isAlphaNumeric){
				errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.specialcharacter"));
			}
			}

//			if (form.getRelationshipMgr() == null || "".equals(form.getRelationshipMgr().trim())) {
//
//				errors.add("relationshipMgrError", new ActionMessage("error.string.mandatory"));
//				DefaultLogger.debug(ManualInputCustomerValidator.class, "relationshipMgrError");
//			}
			if (form.getCycle() == null || "".equals(form.getCycle().trim())) {

				errors.add("cycleError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "cycleError");
			}
			if (form.getEntity() == null || "".equals(form.getEntity().trim())) {

				errors.add("entityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "entityError");
			}
			if (form.getIndustryName() == null || "".equals(form.getIndustryName().trim())) {

				errors.add("industryNameError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "industryNameError");
				
			}
			if (form.getRbiIndustryCode() == null || "".equals(form.getRbiIndustryCode().trim())) {

				errors.add("rbiIndustryCodeError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "rbiIndustryCodeError");
			}
			if (!event.equalsIgnoreCase("WS_create_customer") && !event.equalsIgnoreCase("WS_update_customer")) {
			if (form.getDateOfIncorporation() == null || "".equals(form.getDateOfIncorporation().trim())) {
				errors.add("corporationDateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			}
			if (form.getDateOfIncorporation() != null && !"".equals(form.getDateOfIncorporation())) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				try {
					if (DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).equals(DateUtil.clearTime(d))
							|| DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).after(DateUtil.clearTime(d))) {
						errors.add("corporationDateError", new ActionMessage("error.string.dateValidation"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			}
			
			if(form.getEntity().equalsIgnoreCase("22") || form.getEntity().equalsIgnoreCase("PVT.LTD.") || form.getEntity().equalsIgnoreCase("LIMITED")) {
				if (form.getCinLlpin() == null || "".equals(form.getCinLlpin().trim())) {
				    errors.add("cinLlpinError", new ActionMessage("error.string.mandatory"));
				    DefaultLogger.debug(ManualInputCustomerValidator.class, "cinLlpinError");
				}
				else if (!(((alpha >= 'a' && alpha <= 'z') || (alpha >= 'A' && alpha <= 'Z')) && (numeric >= '0' && numeric <= '9'))) {
					errors.add("cinError", new ActionMessage("error.cinllpin.invalid.alphanumeric"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.cinllpin.invalid.alphanumeric");    
				}
					
		    }
			}
			
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (!(errorCode = Validator.checkString(form.getListedCompany(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("listedCompanyError", new ActionMessage("error.string.mandatory", "1", "10"));
				}				
			}
			
			if (form.getListedCompany() != null && "Yes".equalsIgnoreCase(form.getListedCompany().trim())  ) {

				if (!(errorCode = Validator.checkString(form.getIsinNo(), true, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isinNoError", new ActionMessage("error.string.length.error", "1", "20"));
				}
			}
			
			if(form.getRaroc() != null && !form.getRaroc().trim().isEmpty())
			{
			if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getRaroc(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10,3,locale))) {
				errors.add("rarocCharacterError", new ActionMessage("error.raroc.format"));
			}
			}
			
			/*if (!(errorCode = Validator.checkString(form.getYearEndPeriod().trim(), true, 6, 6))
					.equals(Validator.ERROR_NONE)) {
				errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory", "6", "6"));
				
			}*/
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (form.getYearEndPeriod() == null || "".equals(form.getYearEndPeriod()))
				{
					errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory"));

				}
			}
			
			 Calendar currentDate = Calendar.getInstance();
             int  year = currentDate.get(Calendar.YEAR);
             
             
				if (!form.getYearEndPeriod().isEmpty()
						&& form.getYearEndPeriod() != null) {
					if (form.getYearEndPeriod().matches("\\d{2}-\\w{3}")) {
						String dt = form.getYearEndPeriod() + "-"
								+ String.valueOf(year);
						if (!(errorCode = Validator.checkDate(dt, true,
								Locale.getDefault()))
										.equals(Validator.ERROR_NONE)
								|| dt.contains("/")) {
							errors.add("yearEndPeriodFormatError",
									new ActionMessage(
											"error.yearEndFormat.invalid.format"));
						}
					} else {
						errors.add("yearEndPeriodFormatError",
								new ActionMessage(
										"error.yearEndFormat.invalid.format"));
					}
				}
				
			if ( !"".equalsIgnoreCase(form.getCreditMgrEmpId())  &&  form.getCreditMgrEmpId() != null ) {

				if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCreditMgrEmpId(), true, 5, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("creditMgrEmpIdError", new ActionMessage("error.creditMgrEmpId.length.error"));
				}
			}
			
			if ( !"".equalsIgnoreCase(form.getPfLrdCreditMgrEmpId())  &&  form.getPfLrdCreditMgrEmpId() != null ) {

				if (!AbstractCommonMapper.isEmptyOrNull(form.getPfLrdCreditMgrEmpId())) {
					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getPfLrdCreditMgrEmpId());
					if (codeFlag == true) {
						errors.add("pfLrdCreditMgrEmpIdError", new ActionMessage("error.string.invalidCharacter"));
					}
				}
			}
			
			if (!event.equalsIgnoreCase("WS_create_customer") && !event.equalsIgnoreCase("WS_update_customer")) {
			if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() > 12) {
					errors.add("aadharLengthError", new ActionMessage("error.aadhar.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.exceeded");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() < 12) {
					errors.add("aadharLengthLessError", new ActionMessage("error.aadhar.length.LessThan"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.LessThan");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (!(form.getAadharNumber().matches("[0-9]+"))){
					errors.add("aadharCharacterError", new ActionMessage("error.aadhar.character.contain"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.character.contain");
				}
				}
			}
//				if (form.getAadharNumber() == null || "".equals(form.getAadharNumber().trim())){
//					errors.add("aadharNumberError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.string.mandatory");
//				}
			
			if (form.getAddress1() == null || "".equals(form.getAddress1().trim())) {

				errors.add("address1Error", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "address1Error");
			} else if (form.getAddress1().length() > 75) {
				errors.add("address1LengthError", new ActionMessage("error.address1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address1.length.exceeded");
			}
			if (form.getAddress2() != null && !("".equals(form.getAddress2().trim()))) {
				if (form.getAddress2().length() > 75) {
					errors.add("address2LengthError", new ActionMessage("error.address2.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address2.length.exceeded");
				}
			}
			if (form.getAddress3() != null && !("".equals(form.getAddress3().trim()))) {
				if (form.getAddress3().length() > 75) {
					errors.add("address3LengthError", new ActionMessage("error.address3.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address3.length.exceeded");
				}
			}
			/*
			 * if (form.getTelephoneNo() != null &&
			 * !("".equals(form.getTelephoneNo().trim()))) { if
			 * (form.getTelephoneNo().length() > 15) {
			 * errors.add("telephoneNoLengthError", new ActionMessage(
			 * "error.telephoneNo.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.telephoneNo.length.exceeded"); } }
			 */
			if (!(form.getTelephoneNo() == null || form.getTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getStdCodeTelNo() == null || form.getStdCodeTelNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeTelNo().toString().trim(), false, 1, 99999))) {
					errors.add("specialCharacterStdTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}

			if (!(form.getTelex() == null || form.getTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getTelex().toString().trim(),
						false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			if (!(form.getStdCodeTelex() == null || form.getStdCodeTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeTelex().toString().trim(), false, 1, 99999))) {
					errors.add("specialCharacterStdTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
				}
			}
			/*
			 * if (form.getTelex() != null &&
			 * !("".equals(form.getTelex().trim()))) { if
			 * (form.getTelex().length() > 15) { errors.add("telexLengthError",
			 * new ActionMessage( "error.telex.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.telex.length.exceeded"); } }
			 */
			if (!(form.getEmail() == null || form.getEmail().trim().equals(""))) {

				if (form.getEmail().length() > 50) {
					errors.add("emailError", new ActionMessage("error.email.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.email.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getEmail(), true))) {
					errors.add("emailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (!event.equalsIgnoreCase("WS_create_customer") && !event.equalsIgnoreCase("WS_update_customer")) {
				if (form.getCountry() == null || "".equals(form.getCountry().trim())) {

					errors.add("countryError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "countryError");
				}
			}
			if (form.getState() == null || "".equals(form.getState().trim())) {

				errors.add("stateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "stateError");
			}
			if (form.getRegion() == null || "".equals(form.getRegion().trim())) {

				errors.add("regionError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regionError");
			}
			if (form.getCity() == null || "".equals(form.getCity().trim())) {

				errors.add("cityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "cityError");
			}

			if (form.getSubLine() == null || "".equals(form.getSubLine().trim())) {

				errors.add("subLineError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "subLineError");
			}
//			if (form.getBankingMethod() == null || "".equals(form.getBankingMethod().trim())) {
//
//				errors.add("bankingMethodError", new ActionMessage("error.string.banking.empty"));
//				DefaultLogger.debug(ManualInputCustomerValidator.class, "bankingMethodError");
//			}
			if (form.getTotalFundedLimit() == null || "".equals(form.getTotalFundedLimit().trim())) {

				errors.add("totalFundedLimitError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "totalFundedLimitError");
			} else if (!(errorCode = Validator.checkNumber(form.getTotalFundedLimit(), false, 0,
					99999999999999999999.99, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalFundedLimitLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			if (form.getFundedSharePercent() == null || "".equals(form.getFundedSharePercent().trim())) {

				errors.add("fundedSharePercentError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "fundedSharePercentError");
			}
			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("MULTIPLE") || form.getBankingMethod().equals("CONSORTIUM") 
							|| form.getBankingMethod().contains(",MULTIPLE-MULTIPLE")
							|| form.getBankingMethod().contains(",CONSORTIUM-CONSORTIUM")
							|| form.getBankingMethod().contains("MULTIPLE-MULTIPLE,")
							|| form.getBankingMethod().contains("CONSORTIUM-CONSORTIUM,")
							|| form.getBankingMethod().equals("MULTIPLE-MULTIPLE")
							|| form.getBankingMethod().equals("CONSORTIUM-CONSORTIUM")
							)) {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 0, 99.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99.99"));
				}
			} else if (form.getBankingMethod() != null && (form.getBankingMethod().equals("SOLE") || form.getBankingMethod().equals("SOLE-SOLE"))) {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 100.00, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentError", new ActionMessage("error.must.hunderd"));
				}
			} else {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 0, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
				}
			}
			if (form.getTotalNonFundedLimit() == null || "".equals(form.getTotalNonFundedLimit().trim())) {

				errors.add("totalNonFundedLimitError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "totalNonFundedLimitError");
			} else if (!(errorCode = Validator.checkNumber(form.getTotalNonFundedLimit(), false, 0,
					99999999999999999999.99, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalNonFundedLimitLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("MULTIPLE") || form.getBankingMethod().equals("CONSORTIUM")
							|| form.getBankingMethod().contains(",MULTIPLE-MULTIPLE")
							|| form.getBankingMethod().contains(",CONSORTIUM-CONSORTIUM")
							|| form.getBankingMethod().contains("MULTIPLE-MULTIPLE,")
							|| form.getBankingMethod().contains("CONSORTIUM-CONSORTIUM,")
							|| form.getBankingMethod().equals("MULTIPLE-MULTIPLE")
							|| form.getBankingMethod().equals("CONSORTIUM-CONSORTIUM"))
					&& !event.equalsIgnoreCase("WS_create_customer") && !event.equalsIgnoreCase("WS_update_customer")) {
				if (!(errorCode = Validator.checkNumber(form.getNonFundedSharePercent(), false, 0, 99.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonFundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99.99"));
				}
			} else {
				if (!(errorCode = Validator.checkNumber(form.getNonFundedSharePercent(), false, 0, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonFundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
				}
			}
			if (form.getMemoExposure() == null || "".equals(form.getMemoExposure().trim())) {

				errors.add("memoExposureError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "memoExposureError");
			} else if (!(errorCode = Validator.checkNumber(form.getMemoExposure(), false, 0, 99999999999999999999.99, 3,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("memoExposureLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}

			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("MULTIPLE")  
							|| form.getBankingMethod().contains(",MULTIPLE-MULTIPLE")
							|| form.getBankingMethod().contains("MULTIPLE-MULTIPLE,")
							|| form.getBankingMethod().equals("MULTIPLE-MULTIPLE")
							)) {
				
				if (form.getMultBankFundBasedHdfcBankPer() != null
						&& !"".equals(form.getMultBankFundBasedHdfcBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getMultBankFundBasedHdfcBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("multBankFundBasedHdfcBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00 for HDFC Bank - Fund Based"));
					}
				}
				
				if (form.getMultBankNonFundBasedHdfcBankPer() != null
						&& !"".equals(form.getMultBankNonFundBasedHdfcBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getMultBankNonFundBasedHdfcBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("multBankNonFundBasedHdfcBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00  for HDFC Bank - Non Fund Based"));
					}
				}
				
				if (form.getMultBankFundBasedLeadBankPer() != null
						&& !"".equals(form.getMultBankFundBasedLeadBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getMultBankFundBasedLeadBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("multBankFundBasedLeadBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00  for LEAD Bank - Fund Based"));
					}
				}
				
				if (form.getMultBankNonFundBasedLeadBankPer() != null
						&& !"".equals(form.getMultBankNonFundBasedLeadBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getMultBankNonFundBasedLeadBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("multBankNonFundBasedLeadBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00  for LEAD Bank - Non Fund Based"));
					}
				}
				
				
			}
			
			
			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("CONSORTIUM") 
							|| form.getBankingMethod().contains(",CONSORTIUM-CONSORTIUM")
							|| form.getBankingMethod().contains("CONSORTIUM-CONSORTIUM,")
							|| form.getBankingMethod().equals("CONSORTIUM-CONSORTIUM")
							)) {
				if (form.getConsBankFundBasedHdfcBankPer() != null
						&& !"".equals(form.getConsBankFundBasedHdfcBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getConsBankFundBasedHdfcBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("consBankFundBasedHdfcBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00 for HDFC Bank - Fund Based"));
					}
				}
				
				if (form.getConsBankNonFundBasedHdfcBankPer() != null
						&& !"".equals(form.getConsBankNonFundBasedHdfcBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getConsBankNonFundBasedHdfcBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("consBankNonFundBasedHdfcBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00 for HDFC Bank - Non Fund Based"));
					}
				}
				
				if (form.getConsBankFundBasedLeadBankPer() != null
						&& !"".equals(form.getConsBankFundBasedLeadBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getConsBankFundBasedLeadBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("consBankFundBasedLeadBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00  for LEAD Bank - Fund Based"));
					}
				}
				
				if (form.getConsBankNonFundBasedLeadBankPer() != null
						&& !"".equals(form.getConsBankNonFundBasedLeadBankPer())) {
					if (!(errorCode = Validator.checkNumber(form.getConsBankNonFundBasedLeadBankPer(), false, 0, 100.00, 3,
							locale)).equals(Validator.ERROR_NONE)) {
						errors.add("consBankNonFundBasedLeadBankPerError",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00  for LEAD Bank - Non Fund Based"));
					}
				}
			}
			
			
			/*
			 * if (form.getMpbf().length() > 10) { errors.add("mpbfLengthError",
			 * new ActionMessage( "error.mpbf.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.mpbf.length.exceeded"); }
			 */
			/*
			 * if (form.getMpbf() == null || "".equals(form.getMpbf().trim())) {
			 * 
			 * errors.add("mpbfError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "mpbfError"); } else if (form.getMpbf().length() > 10) {
			 * errors.add("mpbfLengthError", new ActionMessage(
			 * "error.mpbf.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.mpbf.length.exceeded"); }
			 */

			if (form.getBorrowerDUNSNo() == null || "".equals(form.getBorrowerDUNSNo().trim())) {

				// errors.add("borrowerDUNSNoError", new ActionMessage(
				// "error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "borrowerDUNSNoError");
			} else if (form.getBorrowerDUNSNo().length() > 9) {
				errors.add("borrowerDUNSNoLengthError", new ActionMessage("error.borrowerDUNSNoError.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.borrowerDUNSNoError.length.exceeded");
			} else {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getBorrowerDUNSNo());
				if (nameFlag == true)
					errors.add("specialCharacterBorrowerDUNSNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}

			if ("WS_create_customer".equalsIgnoreCase(event)) {
				if (form.getWillfulDefaultStatus() == null || "".equals(form.getWillfulDefaultStatus().trim())) {

					errors.add("willfulDefaultStatusError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "willfulDefaultStatusError");
				}
			}
			
			if ("WS_create_customer".equalsIgnoreCase(event) || "WS_update_customer".equalsIgnoreCase(event)) {
				if (form.getSecurityValue() != null && !form.getSecurityValue().trim().equals("")) {
					// form.setSecurityValue("0");
					if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0, 99999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("securityValue",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
					}
				}
			
				if (form.getFacilityAmount() != null && !form.getFacilityAmount().trim().equals("")) {
					if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0, 99999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("facilityAmount",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
					}
				}
				
				if (!(errorCode = Validator.checkNumber(form.getLimitAmount(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("limitAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

				} else if (form.getLimitAmount() != null && !form.getLimitAmount().trim().equals("")) {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getLimitAmount())) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getLimitAmount())) {
							errors.add("limitAmount", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			}
			
			if (!"WS_create_customer".equalsIgnoreCase(event) && !"WS_update_customer".equalsIgnoreCase(event)) {
				if (form.getCustomerFyClouser() == null || "".equals(form.getCustomerFyClouser().trim())) {
					errors.add("customerFyClouserError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "customerFyClouserError");
				}
			}
			
			if (form.getRegOfficeAddress1() == null || "".equals(form.getRegOfficeAddress1().trim())) {

				errors.add("regOfficeAddress1Error", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regOfficeAddress1Error");
			} else if (form.getRegOfficeAddress1().length() > 75) {
				errors.add("regOfficeAddress1LengthError",
						new ActionMessage("error.regOfficeAddress1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress1.length.exceeded");

			}//IF condition ends for event!="WS_create_customer" check
			
			if( form.getStatus().equals("INACTIVE") && form.getSubLine().equals("OPEN"))
			{
				long subprofileID = 0L;
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				List customerList = customerDAO.searchCustomerByCIFNumber(form.getCifId());
				if (customerList != null) {
					OBCMSCustomer customer = (OBCMSCustomer) customerList.get(0);
					subprofileID = customer.getCustomerID();
				}
				if (subprofileID != 0) {
					CustomerDAO customerDao = new CustomerDAO();
					try {

						boolean status = false;
						status = customerDao.getSublineCount(subprofileID);

						if (status) {
							errors.add("statusError", new ActionMessage("error.string.close.status"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/*
			 * if (form.getRegOfficeAddress2() == null ||
			 * "".equals(form.getRegOfficeAddress2().trim())) {
			 * 
			 * errors.add("regOfficeAddress2Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "regOfficeAddress2Error"); } else
			 */
			if (form.getRegOfficeAddress2() != null && !("".equals(form.getRegOfficeAddress2().trim()))) {
				if (form.getRegOfficeAddress2().length() > 75) {
					errors.add("regOfficeAddress2LengthError",
							new ActionMessage("error.regOfficeAddress2.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress2.length.exceeded");
				}
			}
			/*
			 * if (form.getRegOfficeAddress3() == null ||
			 * "".equals(form.getRegOfficeAddress3().trim())) {
			 * 
			 * errors.add("regOfficeAddress3Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "regOfficeAddress3Error"); } else
			 */
			if (form.getRegOfficeAddress3() != null && !("".equals(form.getRegOfficeAddress3().trim()))) {
				if (form.getRegOfficeAddress3().length() > 75) {
					errors.add("regOfficeAddress3LengthError",
							new ActionMessage("error.regOfficeAddress3.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress3.length.exceeded");
				}
			}
			/*
			 * if (form.getOfficeAddress1() == null ||
			 * "".equals(form.getOfficeAddress1().trim())) {
			 * 
			 * errors.add("officeAddress1Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeAddress1Error"); } if (form.getOfficeAddress2() == null ||
			 * "".equals(form.getOfficeAddress2().trim())) {
			 * 
			 * errors.add("officeAddress2Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeAddress2Error"); } if (form.getOfficeAddress3() == null ||
			 * "".equals(form.getOfficeAddress3().trim())) {
			 * 
			 * errors.add("officeAddress3Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeAddress3Error"); }
			 * 
			 * if (form.getOfficeCountry() == null ||
			 * "".equals(form.getOfficeCountry().trim())) {
			 * 
			 * errors.add("officeCountryError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeCountryError"); } if (form.getOfficeRegion() == null ||
			 * "".equals(form.getOfficeRegion().trim())) {
			 * 
			 * errors.add("officeRegionError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeRegionError"); } if (form.getOfficeState() == null ||
			 * "".equals(form.getOfficeState().trim())) {
			 * 
			 * errors.add("officeStateError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeStateError"); } if (form.getOfficeCity() == null ||
			 * "".equals(form.getOfficeCity().trim())) {
			 * 
			 * errors.add("officeCityError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "officeCityError"); }
			 */
			if (form.getRegOfficeCountry() == null || "".equals(form.getRegOfficeCountry().trim())) {

				errors.add("regOfficeCountryError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regOfficeCountryError");
			}
			if (form.getRegOfficeRegion() == null || "".equals(form.getRegOfficeRegion().trim())) {

				errors.add("regOfficeRegionError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regOfficeRegionError");
			}
			if (form.getRegOfficeState() == null || "".equals(form.getRegOfficeState().trim())) {

				errors.add("regOfficeStateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regOfficeStateError");
			}
			if (form.getRegOfficeCity() == null || "".equals(form.getRegOfficeCity().trim())) {

				errors.add("regOfficeCityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regOfficeCityError");
			}
			if (!(form.getRegOfficeTelephoneNo() == null || form.getRegOfficeTelephoneNo().trim().equals(""))) {
				if (form.getRegOfficeTelephoneNo().length() > 15) {
					errors.add("regOfficeTelephoneNoLengthError",
							new ActionMessage("error.regOfficeTelephoneNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"error.regOfficeTelephoneNo.length.exceeded");
				}
			}
			if (!(form.getRegOfficeTelephoneNo() == null || form.getRegOfficeTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getRegOfficeTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterRegOfficeTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}

			if (!(form.getStdCodeOfficeTelNo() == null || form.getStdCodeOfficeTelNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeOfficeTelNo().toString().trim(), false, 1, 99999))) {
					errors.add("specialCharacterRegOfficeStdTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getRegOfficeTelex() == null || form.getRegOfficeTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getRegOfficeTelex().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterRegOfficeTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			if (!(form.getStdCodeOfficeTelex() == null || form.getStdCodeOfficeTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeOfficeTelex().toString().trim(), false, 1, 99999))) {
					errors.add("specialCharacterRegOfficeStdTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
				}
			}
			if (!(form.getRegOfficeTelex() == null || form.getRegOfficeTelex().trim().equals(""))) {
				if (form.getRegOfficeTelex().length() > 15) {
					errors.add("regOfficeTelexLengthError", new ActionMessage("error.regOfficeTelex.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeTelex.length.exceeded");
				}
			}
			if (!(form.getRegOfficeDUNSNo() == null || form.getRegOfficeDUNSNo().trim().equals(""))) {
				if (form.getRegOfficeDUNSNo().length() > 10) {
					errors.add("regOfficeDUNSNoLengthError",
							new ActionMessage("error.regOfficeDUNSNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeDUNSNo.length.exceeded");
				}
			}
			if (form.getRegOfficeDUNSNo() != null && !("".equals(form.getRegOfficeDUNSNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRegOfficeDUNSNo());
				if (nameFlag == true)
					errors.add("specialCharacterRegOfficeDUNSNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getRegOfficePostCode() != null && !("".equals(form.getRegOfficePostCode().trim()))) {
				if (form.getRegOfficePostCode().length() > 6) {
					errors.add("regOfficePostCodeLengthError",
							new ActionMessage("error.regOfficePostCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficePostCode.length.exceeded");
				}
				if (form.getRegOfficePostCode() != null && !("".equals(form.getRegOfficePostCode().trim()))) {
					boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRegOfficePostCode());
					if (nameFlag == true)
						errors.add("specialCharacterRegOfficePostCodeError",
								new ActionMessage("error.string.invalidCharacter"));
				}
				
				if (!(event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if (pincodeDao != null && form.getRegOfficeState() != null) {

					String stateId = form.getRegOfficeState();
					String pincode = form.getRegOfficePostCode();
					if (pincode != null) {
						if (pincode.length() > 1 || pincode.length() == 1) {
							pincode = pincode.substring(0, 1);
							if(null != stateId && !"".equals(stateId)){
							if (stateId != null && !pincodeDao.isPincodeMappingValid(pincode, stateId)) {
								errors.add("regPostcodeError", new ActionMessage("error.string.invalidMapping"));
							}
							}
						} else {
							errors.add("regPostcodeError", new ActionMessage("error.string.invalidMapping"));
						}
					}

				}
			}
			}

			/*
			 * if (form.getRegOfficeEmail().length() > 50) {
			 * errors.add("regOfficeEmailLengthError", new ActionMessage(
			 * "error.regOfficeEmail.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.regOfficeEmail.length.exceeded"); }
			 */
			if (!(form.getRegOfficeEmail() == null || form.getRegOfficeEmail().trim().equals(""))) {

				if (form.getRegOfficeEmail().length() > 50) {
					errors.add("regOfficeEmailError", new ActionMessage("error.regOfficeEmail.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeEmail.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getRegOfficeEmail(), true))) {
					errors.add("regOfficeEmailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (form.getPostcode() != null && !("".equals(form.getPostcode().trim()))) {
				if (form.getPostcode().length() > 6) {
					errors.add("postCodeLengthError", new ActionMessage("error.postCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.postCode.length.exceeded");
				}
			}
			/*
			 * if (form.getEmail().length() > 50) {
			 * errors.add("emailLengthError", new ActionMessage(
			 * "error.email.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.email.length.exceeded"); }
			 */
			if (!(errorCode = Validator.checkNumber(form.getSuitAmount(), false, 0, 99999999999999999999.99, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("suitAmountLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			if (form.getSuitReferenceNo() != null && !("".equals(form.getSuitReferenceNo().trim()))) {
				if (form.getSuitReferenceNo().length() > 10) {
					errors.add("suitReferenceNoLengthError",
							new ActionMessage("error.suitReferenceNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.suitReferenceNo.length.exceeded");
				}
			}
			if (form.getSuitReferenceNo() != null && !("".equals(form.getSuitReferenceNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSuitReferenceNo());
				if (nameFlag == true)
					errors.add("specialCharacterSuitReferenceNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getPan() != null && !("".equals(form.getPan().trim()))) {
				if (form.getPan().length() > 10) {
					errors.add("panLengthError", new ActionMessage("error.pan.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.pan.length.exceeded");
				}
			}

			Date d1 = DateUtil.getDate();
			if ((form.getDateOfSuit() != null) && (form.getDateOfSuit().trim().length() != 0)) {
				int a = d1.compareTo(DateUtil.convertDate(locale, form.getDateOfSuit()));

				if (a < 0) {
					errors.add("dateOfSuit",
							new ActionMessage("error.date.compareDate.more", "Date Of Suit", "Current Date"));
				}

			}

			if ((form.getDateWillfulDefault() != null) && (form.getDateWillfulDefault().trim().length() != 0)) {
				int a = d1.compareTo(DateUtil.convertDate(locale, form.getDateWillfulDefault()));

				if (a < 0) {
					errors.add("dateWillfulDefault", new ActionMessage("error.date.compareDate.more",
							"Date Of Willful Default", "Current Date"));
				}

			}

			if (form.getWillfulDefaultStatus() != null && !("".equals(form.getWillfulDefaultStatus().trim()))) {
				if (form.getWillfulDefaultStatus().equals("NOT_DEFAULTER")) {
					form.setDateWillfulDefault("");
					form.setSuitFilledStatus("");
					form.setSuitReferenceNo("");
					form.setSuitAmount("");
					form.setDateOfSuit("");
				}
			}

			// if(!event.equalsIgnoreCase("WS_create_customer") &&
			// !event.equalsIgnoreCase("WS_update_customer")){
			// A Shiv 230811
			// -------------------

			HashSet set = new HashSet();
			if (!AbstractCommonMapper.isEmptyOrNull(form.getTempFacilityData())) {
				String[] strArray = form.getTempFacilityData().split("\\|");
				set.addAll(Arrays.asList(strArray));
			}

			if (!(errorCode = Validator.checkNumber(form.getCustomerRamID(), false, 0, 99999999999999999999.0))
					.equals(Validator.ERROR_NONE)) {
				errors.add("customerRamID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1",
						"99999999999999999999.0"));
			} else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCustomerRamID());
				if (codeFlag == true) {
					errors.add("customerRamID", new ActionMessage("error.string.invalidCharacter"));
				}
			}

			if (!(errorCode = Validator.checkString(form.getCustomerAprCode(), false, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("customerAprCode", new ActionMessage("error.string.mandatory", "0", "10"));
			} else {
				boolean codeFlag = ASSTValidator.isValidCustomerAPRCode(form.getCustomerAprCode());
				if (codeFlag == true) {
					errors.add("customerAprCode", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			if (form.getCustomerExtRating() != null && !"".equals(form.getCustomerExtRating().trim())) {
				if (form.getCustomerExtRating().length() > 1000) {
					errors.add("customerExtRatingLengthError",
							new ActionMessage("error.customerExtRating.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.customerExtRating.length.exceeded");
				}
				boolean codeFlag = ASSTValidator.isValidGenericASST(form.getCustomerExtRating());
				if (codeFlag == true) {
					errors.add("customerExtRating", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// -------------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsNbfs())) {
				if (!(errorCode = Validator.checkString(form.getIsNbfs(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("isNbfs", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsNbfs().equals("Yes")) {
					if (!(errorCode = Validator.checkString(form.getNbfsA(), true, 1, 200))
							.equals(Validator.ERROR_NONE)) {
						errors.add("nbfsA", new ActionMessage("error.string.mandatory", "1"));
					}
					if (!(errorCode = Validator.checkString(form.getNbfsB(), true, 1, 200))
							.equals(Validator.ERROR_NONE)) {
						errors.add("nbfsB", new ActionMessage("error.string.mandatory", "1"));
					}
				}
			} else {
				form.setIsNbfs("No");
			}

			// -----------
			if (!(errorCode = Validator.checkString(form.getMsmeClassification(), false, 0, 200))
					.equals(Validator.ERROR_NONE)) {
				errors.add("msmeClassification", new ActionMessage("error.string.mandatory", "0"));
			}

			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsWeakerSection())) {
				if (!(errorCode = Validator.checkString(form.getIsWeakerSection(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isWeakerSection", new ActionMessage("error.string.mandatory", "1"));
				} else if (form.getIsWeakerSection().equals("Yes")) {
					if (!(errorCode = Validator.checkString(form.getWeakerSection(), true, 1, 200))
							.equals(Validator.ERROR_NONE)) {
						errors.add("weakerSection", new ActionMessage("error.string.mandatory", "1"));
					}
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getGovtSchemeType(), true, 1,
					 * 200)).equals(Validator.ERROR_NONE)) {
					 * errors.add("govtSchemeType", new
					 * ActionMessage("error.string.mandatory", "1")); }
					 */

					/*
					 * if(form.getGovtSchemeType()!=null &&
					 * !form.getGovtSchemeType().trim().isEmpty()){
					 * if(ASSTValidator.isValidGenericASST(form.
					 * getGovtSchemeType().trim())){
					 * errors.add("govtSchemeType", new
					 * ActionMessage("error.string.invalidIdentifiers")); }
					 * if(form.getGovtSchemeType().trim().length()>200){
					 * errors.add("govtSchemeType", new
					 * ActionMessage("error.field.length.exceeded","200")); }
					 * }else{ errors.add("govtSchemeType", new
					 * ActionMessage("error.string.mandatory")); }
					 */
				}
			} else {
				form.setIsWeakerSection("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsKisanCreditCard())) {
				if (!(errorCode = Validator.checkString(form.getIsKisanCreditCard(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isKisanCreditCard", new ActionMessage("error.string.mandatory", "1"));
				}
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * else if(form.getIsKisanCreditCard().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Kisan Credit Card")) {
				 * errors.add("isKisanCreditCard", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsKisanCreditCard("No");
			}
			// -----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsMinorityCommunity())) {
				if (!(errorCode = Validator.checkString(form.getIsMinorityCommunity(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isMinorityCommunity", new ActionMessage("error.string.mandatory", "1"));
				} else if (form.getIsMinorityCommunity().equals("Yes")) {
					if (!(errorCode = Validator.checkString(form.getMinorityCommunity(), true, 1, 200))
							.equals(Validator.ERROR_NONE)) {
						errors.add("minorityCommunity", new ActionMessage("error.string.mandatory", "1"));
					}
				}
			} else {
				form.setIsMinorityCommunity("No");
			}

			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsCommoditiesExposer())) {
				if (form.getIsCommoditiesExposer().equals("Yes")) {
					if (!(errorCode = Validator.checkString(form.getIsSensitive(), true, 1, 200))
							.equals(Validator.ERROR_NONE)) {
						errors.add("isSensitive", new ActionMessage("error.string.mandatory", "1"));
					} else if (form.getIsSensitive().equals("Yes")) {
						if (!(errorCode = Validator.checkString(form.getCommoditiesName(), true, 1, 200))
								.equals(Validator.ERROR_NONE)) {
							errors.add("commoditiesName", new ActionMessage("error.string.mandatory", "1"));
						}
					}
				}
			} else {
				form.setIsCommoditiesExposer("No");
			}

			// ----------Code changes made on 14-JUN-2016 : Defect #100, As
			// confirmed by BPRG team, MSME validations should be removed for
			// WSDL Requests.Remains as it is on front-end.
			if (!"WS_create_customer".equalsIgnoreCase(event) && !"WS_update_customer".equalsIgnoreCase(event)) {
				if (!(errorCode = Validator.checkNumber(form.getGrossInvsInPM(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("grossInvsInPM", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
				} else {
					if (form.getGrossInvsInPM().trim().equals("")) {
						form.setGrossInvsInPM("0");
					}

					// Phase 3 CR:comma separated
					String grossInvsInPM = form.getGrossInvsInPM();
					grossInvsInPM = UIUtil.removeComma(grossInvsInPM);
					BigDecimal grossInvestInPMAmount = new BigDecimal(grossInvsInPM);

					// BigDecimal grossInvestInPMAmount = new
					// BigDecimal(form.getGrossInvsInPM());
					grossInvestInPMAmount = grossInvestInPMAmount.multiply(new BigDecimal(1000000));

					/*
					 * if(form.getMsmeClassification().equals(
					 * "Super_Micro_Entp_Manuf")){
					 * if(grossInvestInPMAmount.compareTo(new
					 * BigDecimal(100000)) > 0){ errors.add("msmeGrossInvsInPM",
					 * new ActionMessage("error.Super_Micro_Entp_Manuf.limit"));
					 * } }
					 * if(form.getMsmeClassification().equals("Small_Entp_Manuf"
					 * )){ if(grossInvestInPMAmount.compareTo(new
					 * BigDecimal(2500000)) < 0 ||
					 * grossInvestInPMAmount.compareTo(new BigDecimal(50000000))
					 * > 0){ errors.add("msmeGrossInvsInPM", new
					 * ActionMessage("error.Small_Entp_Manuf.limit")); } }
					 * if(form.getMsmeClassification().equals("Mic_Entp_Manuf"))
					 * { if(grossInvestInPMAmount.compareTo(new
					 * BigDecimal(500000)) <0 ||
					 * grossInvestInPMAmount.compareTo(new BigDecimal(2500000))
					 * >0 ){ errors.add("msmeGrossInvsInPM", new
					 * ActionMessage("error.Mic_Entp_Manuf.limit")); } }
					 * if(form.getMsmeClassification().equals(
					 * "Medium_Entp_Manuf")){
					 * if(grossInvestInPMAmount.compareTo(new
					 * BigDecimal(50000000)) < 0 ||
					 * grossInvestInPMAmount.compareTo(new
					 * BigDecimal(100000000)) >0) {
					 * errors.add("msmeGrossInvsInPM", new
					 * ActionMessage("error.Medium_Entp_Manuf.limit")); } }
					 */

					// Start:Uma Khot:CRI Field addition enhancement
					if (form.getMsmeClassification().equals("Super_Micro_Entp_Manuf")) {
						if (grossInvestInPMAmount.compareTo(new BigDecimal(1000000)) > 0) {
							errors.add("msmeGrossInvsInPM", new ActionMessage("error.Super_Micro_Entp_Manuf.limit"));
						}
					}
					if (form.getMsmeClassification().equals("Small_Entp_Manuf")) {
						if (grossInvestInPMAmount.compareTo(new BigDecimal(100000000)) > 0) {
							errors.add("msmeGrossInvsInPM", new ActionMessage("error.Small_Entp_Manuf.limit"));
						}
					}
					if (form.getMsmeClassification().equals("Mic_Entp_Manuf")) {
						if (grossInvestInPMAmount.compareTo(new BigDecimal(10000000)) > 0) {
							errors.add("msmeGrossInvsInPM", new ActionMessage("error.Mic_Entp_Manuf.limit"));
						}
					}
					if (form.getMsmeClassification().equals("Medium_Entp_Manuf")) {
						if (grossInvestInPMAmount.compareTo(new BigDecimal(500000000)) > 0) {
							errors.add("msmeGrossInvsInPM", new ActionMessage("error.Medium_Entp_Manuf.limit"));
						}
					}

					//MSME CR
					
					if(form.getMsmeClassification().equals("Mic_Entp_Manuf") || form.getMsmeClassification().equals("Small_Entp_Manuf")
							|| form.getMsmeClassification().equals("Medium_Entp_Manuf")) {
						if(null == form.getGrossInvsInPM() || form.getGrossInvsInPM().equals("")) {
						errors.add("grossInvsInPM",new ActionMessage("error.string.mandatory"));
						}
						if(null == form.getFirstYearTurnover() || form.getFirstYearTurnover().equals("")) {
						errors.add("firstYearTurnover",new ActionMessage("error.string.mandatory"));
						}
					}
					
					if(null != form.getFirstYearTurnover() && !form.getFirstYearTurnover().equals("") && form.getFirstYearTurnoverCurr().equals("INR")) {
					String firstYearTurnover = form.getFirstYearTurnover();
					firstYearTurnover = UIUtil.removeComma(firstYearTurnover);
					BigDecimal firstYearTurno = new BigDecimal(firstYearTurnover);
					
					
					
					if (form.getMsmeClassification().equals("Small_Entp_Manuf")) {
						if (firstYearTurno.compareTo(new BigDecimal(500000000)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Small_Entp_Manuf.limit.turnOver"));
						}
					}
					if (form.getMsmeClassification().equals("Mic_Entp_Manuf")) {
						
						if (firstYearTurno.compareTo(new BigDecimal(50000000)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Mic_Entp_Manuf.limit.turnOver"));
						}
					}
					if (form.getMsmeClassification().equals("Medium_Entp_Manuf")) {
						if (firstYearTurno.compareTo(new BigDecimal(2500000000L)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Medium_Entp_Manuf.limit.turnOver"));
						}
					}

					}
					
					
					// End:Uma Khot:CRI Field addition enhancement

					/*
					 * if(form.getMsmeClassification().equals(
					 * "Khadi_village_industries_sec")){
					 * if(grossInvestInPMAmount.compareTo(new BigDecimal(0)) <
					 * 0){ errors.add("msmeGrossInvsInPM", new
					 * ActionMessage("error.Khadi_village_industries_sec.limit")
					 * ); } }
					 */
				}

				
				
				/*if (!(errorCode = Validator.checkNumber(form.getSalesPercentage(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_2_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("salesPercentage",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									IGlobalConstant.MAXIMUM_ALLOWED_VALUE_2_2_STR));

				} else*/

//				if (form.getSalesPercentage() != null && !"".equals(form.getSalesPercentage().trim())) {
//					
//					if (!(errorCode = Validator.checkNumber(form.getSalesPercentage(), false, 0, 100.00, 3, locale))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("salesPercentage",
//								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
//					}
//				}
//
//				/*if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0,
//						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
//					errors.add("facilityAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
//							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));
//
//				} else*/
//
//				if (form.getFacilityAmount() != null && !form.getFacilityAmount().trim().equals("")) {
//					
//					if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0, 99999999.99, 3, locale))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("facilityAmount",
//								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
//					}
//				}
//
//				/*if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0,
//						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
//					errors.add("securityValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
//							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));
//
//				} else*/
//
//				if (form.getSecurityValue() != null && !form.getSecurityValue().trim().equals("")) {
//					// form.setSecurityValue("0");
//					if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0, 99999999.99, 3, locale))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("securityValue",
//								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
//					}
//				}

				/*if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("facilityAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

				} else*/

				if (form.getFacilityAmount() != null && !form.getFacilityAmount().trim().equals("")) {
					
					if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0, 999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("facilityAmount",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999999.99"));
					}
				}

				/*if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("securityValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

				} else*/

				if (form.getSecurityValue() != null && !form.getSecurityValue().trim().equals("")) {
					// form.setSecurityValue("0");
					if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0, 99999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("securityValue",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
					}
				}

				if (!(errorCode = Validator.checkNumber(form.getGrossInvsInEquip(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("grossInvsInEquip",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
									IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
				} else {
					if (form.getGrossInvsInEquip().trim().equals("")) {
						form.setGrossInvsInEquip("0");
					}

					// Phase 3 CR:comma separated
					String grossInvsInEquip = form.getGrossInvsInEquip();
					grossInvsInEquip = UIUtil.removeComma(grossInvsInEquip);
					BigDecimal grossInvestInEquipAmount = new BigDecimal(grossInvsInEquip);

					// BigDecimal grossInvestInEquipAmount = new
					// BigDecimal(form.getGrossInvsInEquip());
					grossInvestInEquipAmount = grossInvestInEquipAmount.multiply(new BigDecimal(1000000));

					/*
					 * if(form.getMsmeClassification().equals(
					 * "Super_Micro_Entp_Serv")){
					 * if(grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(200000)) > 0){
					 * errors.add("msmeGrossInvsInEquip", new
					 * ActionMessage("error.Super_Micro_Entp_Serv.limit")); } }
					 * 
					 * if(form.getMsmeClassification().equals("Small_Entp_Serv")
					 * ){ if(grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(1000000)) < 0 ||
					 * grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(20000000)) > 0){
					 * errors.add("msmeGrossInvsInEquip", new
					 * ActionMessage("error.Small_Entp_Serv.limit")); } }
					 * if(form.getMsmeClassification().equals("Medium_Entp_Serv"
					 * )){ if(grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(20000000)) < 0 ||
					 * grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(50000000)) > 0){
					 * errors.add("msmeGrossInvsInEquip", new
					 * ActionMessage("error.Medium_Entp_Serv.limit")); } }
					 * if(form.getMsmeClassification().equals("Micro_Entp_Serv")
					 * ){ if(grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(200000))< 0 ||
					 * grossInvestInEquipAmount.compareTo(new
					 * BigDecimal(1000000)) > 0) {
					 * errors.add("msmeGrossInvsInEquip", new
					 * ActionMessage("error.Micro_Entp_Serv.limit")); } }
					 */

					// Start:Uma Khot:CRI Field addition enhancement
					if (form.getMsmeClassification().equals("Super_Micro_Entp_Serv")) {
						if (grossInvestInEquipAmount.compareTo(new BigDecimal(400000)) > 0) {
							errors.add("msmeGrossInvsInEquip", new ActionMessage("error.Super_Micro_Entp_Serv.limit"));
						}
					}

					if (form.getMsmeClassification().equals("Small_Entp_Serv")) {
						if (grossInvestInEquipAmount.compareTo(new BigDecimal(100000000)) > 0) {
							errors.add("msmeGrossInvsInEquip", new ActionMessage("error.Small_Entp_Serv.limit"));
						}
					}
					if (form.getMsmeClassification().equals("Medium_Entp_Serv")) {
						if (grossInvestInEquipAmount.compareTo(new BigDecimal(500000000)) > 0) {
							errors.add("msmeGrossInvsInEquip", new ActionMessage("error.Medium_Entp_Serv.limit"));
						}
					}
					if (form.getMsmeClassification().equals("Micro_Entp_Serv")) {
						if (grossInvestInEquipAmount.compareTo(new BigDecimal(10000000)) > 0) {
							errors.add("msmeGrossInvsInEquip", new ActionMessage("error.Micro_Entp_Serv.limit"));
						}
					}
					
					if(form.getMsmeClassification().equals("Micro_Entp_Serv") || form.getMsmeClassification().equals("Medium_Entp_Serv")
							|| form.getMsmeClassification().equals("Small_Entp_Serv")) {
						if(null == form.getGrossInvsInPM() || form.getGrossInvsInPM().equals("")) {
							errors.add("grossInvsInPM",new ActionMessage("error.string.mandatory"));
							}
							if(null == form.getFirstYearTurnover() || form.getFirstYearTurnover().equals("")) {
							errors.add("firstYearTurnover",new ActionMessage("error.string.mandatory"));
							}
					}
					
					if(null != form.getFirstYearTurnover() && !form.getFirstYearTurnover().equals("") && form.getFirstYearTurnoverCurr().equals("INR")) {
						String firstYearTurnover = form.getFirstYearTurnover();
						firstYearTurnover = UIUtil.removeComma(firstYearTurnover);
						BigDecimal firstYearTurno = new BigDecimal(firstYearTurnover);
					
					if (form.getMsmeClassification().equals("Small_Entp_Serv")) {
						if (firstYearTurno.compareTo(new BigDecimal(500000000)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Small_Entp_Serv.limit.turnOver"));
						}
					}
					if (form.getMsmeClassification().equals("Medium_Entp_Serv")) {
						if (firstYearTurno.compareTo(new BigDecimal(2500000000L)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Medium_Entp_Serv.limit.turnOver"));
						}
					}
					if (form.getMsmeClassification().equals("Micro_Entp_Serv")) {
						if (firstYearTurno.compareTo(new BigDecimal(50000000)) > 0) {
							errors.add("firstYearTurnover", new ActionMessage("error.Micro_Entp_Serv.limit.turnOver"));
						}
					}
					
					}

					// End:Uma Khot:CRI Field addition enhancement

					/*
					 * if(form.getMsmeClassification().equals(
					 * "Khadi_village_industries_sec")){
					 * if(grossInvestInPMAmount.compareTo(new BigDecimal(0)) <
					 * 0){ errors.add("msmeGrossInvsInPM", new
					 * ActionMessage("error.Khadi_village_industries_sec.limit")
					 * ); } }
					 */
				}
			}

				if (form.getSalesPercentage() != null && !"".equals(form.getSalesPercentage().trim())) {
					
					if (!(errorCode = Validator.checkNumber(form.getSalesPercentage(), false, 0, 100.00, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("salesPercentage",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
					}
				}

				/*if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("facilityAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

				} else*/

				if (form.getFacilityAmount() != null && !form.getFacilityAmount().trim().equals("")) {
					
					if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), false, 0, 99999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("facilityAmount",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
					}
				}

				/*if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("securityValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

				} else*/

				if (form.getSecurityValue() != null && !form.getSecurityValue().trim().equals("")) {
					// form.setSecurityValue("0");
					if (!(errorCode = Validator.checkNumber(form.getSecurityValue(), false, 0, 99999999.99, 3, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("securityValue",
								new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999.99"));
					}
				}

			// if(!AbstractCommonMapper.isEmptyOrNull(form.getGrossInvsInPM())){
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getGrossInvsInPM()))
			// {

			// Phase 3 CR:comma separated
			// Phase 3 CR:comma separated
			String grossInvsInPM = form.getGrossInvsInPM();
			grossInvsInPM = UIUtil.removeComma(grossInvsInPM);
			if (!AbstractCommonMapper.isEmptyOrNull(grossInvsInPM)) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(grossInvsInPM)) {
					errors.add("grossInvsInPM", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// -----------
			/*
			 * if (!(errorCode =
			 * Validator.checkNumber(form.getGrossInvsInEquip(), true, 0.01,
			 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("grossInvsInEquip", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			 * errorCode), "0.01",
			 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR)); }
			 */
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getGrossInvsInEquip())){
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getGrossInvsInEquip()))
			// {

			// Phase 3 CR:comma separated
			String grossInvsInEquip = form.getGrossInvsInEquip();
			grossInvsInEquip = UIUtil.removeComma(grossInvsInEquip);
			if (!AbstractCommonMapper.isEmptyOrNull(grossInvsInEquip)) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(grossInvsInEquip)) {
					errors.add("grossInvsInEquip", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ------------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getPsu())) {
				if (!(errorCode = Validator.checkString(form.getPsu(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("psu", new ActionMessage("error.string.mandatory", "1"));
				} else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getPsu())) {
						if (form.getPsu().equals("Central") || form.getPsu().equals("State")) {
							if (!(errorCode = Validator.checkNumber(form.getPsuPercentage(), true, 51,
									IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE, 3, locale))
											.equals(Validator.ERROR_NONE)) {
								errors.add("psuPercentage", new ActionMessage(
										ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "51", "100"));
							}
						}
					}
				}
			} else {
				form.setPsu("No");
			}
			if (!AbstractCommonMapper.isEmptyOrNull(form.getPsuPercentage())) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getPsuPercentage())) {
					errors.add("psuPercentage", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getGovtUnderTaking())) {
				if (!(errorCode = Validator.checkString(form.getGovtUnderTaking(), true, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("govtUnderTaking", new ActionMessage("error.string.mandatory", "1"));
				}
			} else {
				form.setGovtUnderTaking("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsInfrastructureFinanace())) {
				if (!(errorCode = Validator.checkString(form.getIsInfrastructureFinanace(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isInfrastructureFinanace", new ActionMessage("error.string.mandatory", "1"));
				}
				/*
				 * Below validation commented : 10-MAY-2016 : CRI Fields related
				 * CR
				 * 
				 * if(form.getIsInfrastructureFinanace().equals("Yes")){ if
				 * (!(errorCode =
				 * Validator.checkString(form.getInfrastructureFinanaceType(),
				 * true, 1, 200)).equals(Validator.ERROR_NONE)) {
				 * errors.add("infrastructureFinanaceType", new
				 * ActionMessage("error.string.mandatory", "1")); }
				 * if(!set.contains("Infrastructure Finance")) {
				 * errors.add("isInfrastructureFinanace", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsInfrastructureFinanace("No");
			}

			// ---------
			/*
			 * if (!(errorCode = Validator.checkString(form.getAuditType(),
			 * true, 1, 20)).equals(Validator.ERROR_NONE)) {
			 * errors.add("auditType", new
			 * ActionMessage("error.string.mandatory", "1")); }
			 */

			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsProjectFinance())) {
				if (!(errorCode = Validator.checkString(form.getIsProjectFinance(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isProjectFinance", new ActionMessage("error.string.mandatory", "1"));
				}
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * else if(form.getIsProjectFinance().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Project Finance")) {
				 * errors.add("isProjectFinance", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsProjectFinance("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsCapitalMarketExpos())) {
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * if(form.getIsCapitalMarketExpos().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Capital Market Exposure")) {
				 * errors.add("isCapitalMarketExpos", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsCapitalMarketExpos("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsRealEstateExpos())) {
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * if(form.getIsRealEstateExpos().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Real Estate Exposure")) {
				 * errors.add("isRealEstateExpos", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsPrioritySector("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPrioritySector())) {
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * if(form.getIsPrioritySector().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Priority/Non priority Sector")) {
				 * errors.add("isPrioritySector", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsPrioritySector("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSemsGuideApplicable())) {
				if (!(errorCode = Validator.checkString(form.getIsSemsGuideApplicable(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isSemsGuideApplicable", new ActionMessage("error.string.mandatory", "1"));
				}
			} else {
				form.setIsSemsGuideApplicable("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsFailIfcExcluList())) {
				if (!(errorCode = Validator.checkString(form.getIsFailIfcExcluList(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isFailIfcExcluList", new ActionMessage("error.string.mandatory", "1"));
				}

			}

			else {
				form.setIsFailIfcExcluList("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsTufs())) {
				if (!(errorCode = Validator.checkString(form.getIsTufs(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("isTufs", new ActionMessage("error.string.mandatory", "1"));
				}
			} else {
				form.setIsTufs("No");
			}

			//Uma Khot:Cam upload and Dp field calculation CR
			if(!"WS_create_customer".equalsIgnoreCase(event) && !"WS_update_customer".equalsIgnoreCase(event)){
				if(null!=form.getDpSharePercent()){
					if (!(errorCode = Validator.checkNumber(form.getDpSharePercent(), false, 0, 100.00, 3, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("dpSharePercentLengthError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"0", "100.00"));
					}
				}
			}
			
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsRbiDefaulter())) {
				if (!(errorCode = Validator.checkString(form.getIsRbiDefaulter(), true, 1, 10))

						.equals(Validator.ERROR_NONE)) {
					errors.add("isRbiDefaulter", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsRbiDefaulter().equals("Yes")) {
					if (!(errorCode = Validator.checkString(form.getRbiDefaulter(), true, 1, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add("rbiDefaulter", new ActionMessage("error.string.mandatory", "1"));
					} else {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getRbiDefaulter());
						if (codeFlag == true) {
							errors.add("rbiDefaulter", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsRbiDefaulter("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsLitigation())) {
				if (!(errorCode = Validator.checkString(form.getIsLitigation(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isLitigation", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsLitigation().equals("Yes")) {

					if (form.getLitigationBy() == null || form.getLitigationBy().trim().isEmpty()) {
						errors.add("litigationBy", new ActionMessage("error.string.mandatory"));
					} else {
						boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getLitigationBy().trim());
						if (nameFlag == true) {
							errors.add("litigationBy", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getLitigationBy().length() > 100) {
							errors.add("litigationBy", new ActionMessage("error.litigationBy.length.exceeded"));
						}
					}

					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getLitigationBy(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("litigationBy", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getLitigationBy()); if( codeFlag == true) {
					 * errors.add("litigationBy", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
				}
			} else {
				form.setIsLitigation("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsInterestOfBank())) {
				if (!(errorCode = Validator.checkString(form.getIsInterestOfBank(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isInterestOfBank", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsInterestOfBank().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getInterestOfBank(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("interestOfBank", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getInterestOfBank()); if( codeFlag == true) {
					 * errors.add("interestOfBank", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */

					if (form.getInterestOfBank() != null && !form.getInterestOfBank().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getInterestOfBank().trim())) {
							errors.add("interestOfBank", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getInterestOfBank().trim().length() > 100) {
							errors.add("interestOfBank", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("interestOfBank", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsInterestOfBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsAdverseRemark())) {
				if (!(errorCode = Validator.checkString(form.getIsAdverseRemark(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isAdverseRemark", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsAdverseRemark().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getAdverseRemark(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("adverseRemark", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getAdverseRemark()); if( codeFlag == true) {
					 * errors.add("adverseRemark", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */

					if (form.getAdverseRemark() != null && !form.getAdverseRemark().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getAdverseRemark().trim())) {
							errors.add("adverseRemark", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getAdverseRemark().trim().length() > 100) {
							errors.add("adverseRemark", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("adverseRemark", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsAdverseRemark("No");
			}
			// ---------
			if (form.getIsAdverseRemark() != null && form.getIsAdverseRemark().equals("Yes")) {
				if (AbstractCommonMapper.isEmptyOrNull(form.getAuditType())) {
					if (!(errorCode = Validator.checkString(form.getAuditType(), true, 1, 25))
							.equals(Validator.ERROR_NONE)) {
						errors.add("auditType", new ActionMessage("error.string.mandatory", "1"));
					}
				}
			}
			// -----------
			if (!(errorCode = Validator.checkNumber(form.getAvgAnnualTurnover(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("avgAnnualTurnover", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getAvgAnnualTurnover())){
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getAvgAnnualTurnover()))
			// {

			// Phase 3 CR:comma separated
			String avgAnnualTurnover = form.getAvgAnnualTurnover();
			avgAnnualTurnover = UIUtil.removeComma(avgAnnualTurnover);
			if (!AbstractCommonMapper.isEmptyOrNull(avgAnnualTurnover)) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(avgAnnualTurnover)) {

					errors.add("avgAnnualTurnover", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ------------
			if (!(errorCode = Validator.checkNumber(form.getIndustryExposer(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_PERCENTAGE, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("industryExposer",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "9999.99"));
			}
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIndustryExposer())) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getIndustryExposer())) {
					errors.add("industryExposer", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsDirecOtherBank())) {
				if (!(errorCode = Validator.checkString(form.getIsDirecOtherBank(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isDirecOtherBank", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsDirecOtherBank().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getDirecOtherBank(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("direcOtherBank", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getDirecOtherBank()); if( codeFlag == true) {
					 * errors.add("direcOtherBank", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */

					if (form.getDirecOtherBank() != null && !form.getDirecOtherBank().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getDirecOtherBank().trim())) {
							errors.add("direcOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getDirecOtherBank().trim().length() > 100) {
							errors.add("direcOtherBank", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("direcOtherBank", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsDirecOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPartnerOtherBank())) {
				if (!(errorCode = Validator.checkString(form.getIsPartnerOtherBank(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isPartnerOtherBank", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsPartnerOtherBank().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getPartnerOtherBank(), true,
					 * 1, 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("partnerOtherBank", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getPartnerOtherBank()); if( codeFlag == true) {
					 * errors.add("partnerOtherBank", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
					if (form.getPartnerOtherBank() != null && !form.getPartnerOtherBank().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getPartnerOtherBank().trim())) {
							errors.add("partnerOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getPartnerOtherBank().trim().length() > 100) {
							errors.add("partnerOtherBank", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("partnerOtherBank", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsPartnerOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSubstantialOtherBank())) {
				if (!(errorCode = Validator.checkString(form.getIsSubstantialOtherBank(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isSubstantialOtherBank", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsSubstantialOtherBank().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getSubstantialOtherBank(),
					 * true, 1, 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("substantialOtherBank", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getSubstantialOtherBank()); if( codeFlag == true) {
					 * errors.add("substantialOtherBank", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */

					if (form.getSubstantialOtherBank() != null && !form.getSubstantialOtherBank().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getSubstantialOtherBank().trim())) {
							errors.add("substantialOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getSubstantialOtherBank().trim().length() > 100) {
							errors.add("substantialOtherBank", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("substantialOtherBank", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsSubstantialOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsHdfcDirecRltv())) {
				if (!(errorCode = Validator.checkString(form.getIsHdfcDirecRltv(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isHdfcDirecRltv", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsHdfcDirecRltv().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getHdfcDirecRltv(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("hdfcDirecRltv", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getHdfcDirecRltv()); if( codeFlag == true) {
					 * errors.add("hdfcDirecRltv", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
					if (form.getHdfcDirecRltv() != null && !form.getHdfcDirecRltv().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getHdfcDirecRltv().trim())) {
							errors.add("hdfcDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getHdfcDirecRltv().trim().length() > 100) {
							errors.add("hdfcDirecRltv", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("hdfcDirecRltv", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsHdfcDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsObkDirecRltv())) {
				if (!(errorCode = Validator.checkString(form.getIsObkDirecRltv(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isObkDirecRltv", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsObkDirecRltv().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getObkDirecRltv(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("obkDirecRltv", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getObkDirecRltv()); if( codeFlag == true) {
					 * errors.add("obkDirecRltv", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
					if (form.getObkDirecRltv() != null && !form.getObkDirecRltv().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getObkDirecRltv().trim())) {
							errors.add("obkDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getObkDirecRltv().trim().length() > 100) {
							errors.add("obkDirecRltv", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("obkDirecRltv", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsObkDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPartnerDirecRltv())) {
				if (!(errorCode = Validator.checkString(form.getIsPartnerDirecRltv(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isPartnerDirecRltv", new ActionMessage("error.string.mandatory", "1"));
				}
				if (form.getIsPartnerDirecRltv().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getPartnerDirecRltv(), true,
					 * 1, 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("partnerDirecRltv", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getPartnerDirecRltv()); if( codeFlag == true) {
					 * errors.add("partnerDirecRltv", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
					if (form.getPartnerDirecRltv() != null && !form.getPartnerDirecRltv().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getPartnerDirecRltv().trim())) {
							errors.add("partnerDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getPartnerDirecRltv().trim().length() > 100) {
							errors.add("partnerDirecRltv", new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("partnerDirecRltv", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsPartnerDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSubstantialRltvHdfcOther())) {
				if (!(errorCode = Validator.checkString(form.getIsSubstantialRltvHdfcOther(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isSubstantialRltvHdfcOther", new ActionMessage("error.string.mandatory", "1"));
				}

				if (form.getIsSubstantialRltvHdfcOther().equals("Yes")) {
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getSubstantialRltvHdfcOther(),
					 * true, 1, 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("substantialRltvHdfcOther", new
					 * ActionMessage("error.string.mandatory", "1"));
					 * 
					 * }else { boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getSubstantialRltvHdfcOther()); if( codeFlag == true) {
					 * errors.add("substantialRltvHdfcOther", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
					if (form.getSubstantialRltvHdfcOther() != null
							&& !form.getSubstantialRltvHdfcOther().trim().isEmpty()) {
						if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getSubstantialRltvHdfcOther().trim())) {
							errors.add("substantialRltvHdfcOther", new ActionMessage("error.string.invalidCharacter"));
						}
						if (form.getSubstantialRltvHdfcOther().trim().length() > 100) {
							errors.add("substantialRltvHdfcOther",
									new ActionMessage("error.field.length.exceeded", "100"));
						}
					} else {
						errors.add("substantialRltvHdfcOther", new ActionMessage("error.string.mandatory"));
					}
				}
			} else {
				form.setIsSubstantialRltvHdfcOther("No");
			}

			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPermenentSsiCert())) {
				if (!(errorCode = Validator.checkString(form.getIsPermenentSsiCert(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isPermenentSsiCert", new ActionMessage("error.string.mandatory", "1"));
				}
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * else
				 * if(form.getIsPermenentSsiCert().equalsIgnoreCase("Yes")){
				 * if(!set.contains("Permanent SSI Certificate")) {
				 * errors.add("isPermenentSsiCert", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsPermenentSsiCert("No");
			}
			// ---------

			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsBackedByGovt())) {
				if (!(errorCode = Validator.checkString(form.getIsBackedByGovt(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isBackedByGovt", new ActionMessage("error.string.mandatory", "1"));
				}
				// Below validation commented : 10-MAY-2016 : CRI Fields related
				// CR
				/*
				 * else if(form.getIsBackedByGovt().equalsIgnoreCase("State") ||
				 * form.getIsBackedByGovt().equalsIgnoreCase("Central")){
				 * if(!set.contains("Backed by Govt.")) {
				 * errors.add("isBackedByGovt", new
				 * ActionMessage("error.facility.selected")); } }
				 */
			} else {
				form.setIsBackedByGovt("No");
			}
			// ---------

			if (!(errorCode = Validator.checkNumber(form.getFirstYearTurnover(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("firstYearTurnover", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}

			if (!(errorCode = Validator.checkNumber(form.getSecondYearTurnover(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("secondYearTurnover", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}

			if (!(errorCode = Validator.checkNumber(form.getThirdYearTurnover(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("thirdYearTurnover", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}

			if (form.getFirstYear() != null && !("".equals(form.getFirstYear().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getFirstYear());
				if (nameFlag == true)
					errors.add("firstYear", new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getSecondYear() != null && !("".equals(form.getSecondYear().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSecondYear());
				if (nameFlag == true)
					errors.add("secondYear", new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getThirdYear() != null && !("".equals(form.getThirdYear().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getThirdYear());
				if (nameFlag == true)
					errors.add("thirdYear", new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getPostcode() != null && !("".equals(form.getPostcode().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getPostcode());
				if (nameFlag == true)
					errors.add("specialCharacterPostcodeError", new ActionMessage("error.string.invalidCharacter"));
				
				if (!(event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if (pincodeDao != null && form.getState() != null) {

					String stateCode = form.getState();
					String pincode = form.getPostcode();
					if (pincode != null) {
						if (pincode.length() > 1 || pincode.length() == 1) {
							pincode = pincode.substring(0, 1);
							if(null != stateCode && !"".equals(stateCode)){
								if (stateCode != null && !pincodeDao.isPincodeMappingValid(pincode, stateCode)) {
									errors.add("postcodeError", new ActionMessage("error.string.invalidMapping"));
								}
							}
						} else {
							errors.add("postcodeError", new ActionMessage("error.string.invalidMapping"));
						}
					}
				}
			}
			}
			if (!(errorCode = Validator.checkNumber(form.getLimitAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("limitAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

			} else if (form.getLimitAmount() != null && !form.getLimitAmount().trim().equals("")) {
				if (!AbstractCommonMapper.isEmptyOrNull(form.getLimitAmount())) {
					if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getLimitAmount())) {
						errors.add("limitAmount", new ActionMessage("error.string.invalidCharacter"));
					}
				}
			}

			if (form.getDateOfCautionList() != null && !("".equals(form.getDateOfCautionList()))) {
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				String applicationDate = generalParamEntry.getParamValue();
				Date appDate = new Date();
				if (applicationDate != null) {
					appDate = new Date(applicationDate);
				}

				if (DateUtil.convertDate(locale, form.getDateOfCautionList()).after(appDate)) {
					errors.add("dateOfCautionListError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}
			}

			if (form.getRbiDateOfCautionList() != null && !("".equals(form.getRbiDateOfCautionList()))) {
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				String applicationDate = generalParamEntry.getParamValue();
				Date appDate = new Date();
				if (applicationDate != null) {
					appDate = new Date(applicationDate);
				}

				if (DateUtil.convertDate(locale, form.getRbiDateOfCautionList()).after(appDate)) {
					errors.add("rbiDateOfCautionListError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}
			}

			if (form.getNameOfHoldingCompany() != null && !form.getNameOfHoldingCompany().trim().isEmpty()) {
				if (form.getNameOfHoldingCompany().trim().length() > 255) {
					errors.add("nameOfHoldingCompanyLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}
			
			if (form.getComment() != null && !form.getComment().trim().isEmpty()) {
				if (form.getComment().trim().length() > 250) {
					errors.add("commentLengthError", new ActionMessage("error.field.length.exceeded", "250"));
				}
			}
			
			
			if (form.getLandHolding() != null && !form.getLandHolding().trim().isEmpty()) {
				if (form.getLandHolding().trim().length() > 255) {
					errors.add("landHoldingLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}

			if (form.getStateImplications() != null && !form.getStateImplications().trim().isEmpty()) {
				if (form.getStateImplications().trim().length() > 300) {
					errors.add("stateImplicationsLengthError", new ActionMessage("error.field.length.exceeded", "300"));
				}
			}

			if (form.getComments() != null && !form.getComments().trim().isEmpty()) {
				if (form.getComments().trim().length() > 150) {
					errors.add("commentsLengthError", new ActionMessage("error.field.length.exceeded", "150"));
				}
			}

			if (form.getRejectHistoryReason() != null && !form.getRejectHistoryReason().trim().isEmpty()) {
				if (form.getRejectHistoryReason().trim().length() > 4000) {
					errors.add("rejectHistoryReasonLengthError",
							new ActionMessage("error.field.length.exceeded", "4000"));
				}
			}

			if (form.getHoldingCompnay() != null && !form.getHoldingCompnay().trim().isEmpty()) {
				if (form.getHoldingCompnay().trim().length() > 255) {
					errors.add("holdingCompanyLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}

			if (form.getDetailsOfDefault() != null && !form.getDetailsOfDefault().trim().isEmpty()) {
				if (form.getDetailsOfDefault().trim().length() > 150) {
					errors.add("detailsOfDefaultLengthError", new ActionMessage("error.field.length.exceeded", "150"));
				}
			}

			if (form.getExtOfCompromiseAndWriteoff() != null
					&& !form.getExtOfCompromiseAndWriteoff().trim().isEmpty()) {
				if (form.getExtOfCompromiseAndWriteoff().trim().length() > 300) {
					errors.add("extOfCompromiseAndWriteoffLengthError",
							new ActionMessage("error.field.length.exceeded", "300"));
				}
			}

			if (form.getDetailsOfCleanCibil() != null && !form.getDetailsOfCleanCibil().trim().isEmpty()) {
				if (form.getDetailsOfCleanCibil().trim().length() > 150) {
					errors.add("detailsOfCleanCibilLengthError",
							new ActionMessage("error.field.length.exceeded", "150"));
				}
			}
			if (form.getNameOfDirectorsAndCompany() != null && form.getNameOfDirectorsAndCompany().trim().isEmpty()) {
				if (form.getNameOfDirectorsAndCompany().trim().length() > 150) {
					errors.add("nameOfDirectorsAndCompanyLengthError",
							new ActionMessage("error.field.length.exceeded", "150"));
				}
			}
			
			if (form.getIndirectCountryRiskExposure() != null && form.getIndirectCountryRiskExposure().equalsIgnoreCase("Yes")) {
				if (form.getSalesPercentage() == null || form.getSalesPercentage().equalsIgnoreCase("")) {
					errors.add("salesPercentage", new ActionMessage("error.string.mandatory"));
				}
				if (form.getCriCountryName() == null || form.getCriCountryName().equalsIgnoreCase("")) {
					errors.add("criCountryNameError", new ActionMessage("error.string.mandatory"));
				}
			}

			// }// Validations For CRI skipped when Data fetched from WSDL file
			// -- END
		}
		// ==========================================================

		// Start:Uma Khot:Added for Valid Rating CR

		if (event.equals("create_customer_partymaker") || event.equals("edit_customer_partymaker")
				|| event.equals("maker_resubmit_create_customer_partymaker")
				|| event.equals("maker_resubmit_update_customer_partymaker")
				|| event.equals("create_draft_customer_partymaker")
				|| event.equals("update_draft_customer_partymaker")) {

			if (form.getStatus().equals("INACTIVE") && form.getSubLine().equals("OPEN")) {
				long subprofileID = 0L;
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				List customerList = customerDAO.searchCustomerByCIFNumber(form.getCifId());
				if (customerList != null) {
					OBCMSCustomer customer = (OBCMSCustomer) customerList.get(0);
					subprofileID = customer.getCustomerID();
				}
				if (subprofileID != 0) {
					CustomerDAO customerDao = new CustomerDAO();
					try {

						boolean status = false;
						status = customerDao.getSublineCount(subprofileID);

						if (status) {
							errors.add("statusError", new ActionMessage("error.string.close.status"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (form.getSystemListEmpty() != null && form.getSystemListEmpty().equals("Y")) {
				errors.add("systemListEmptyError", new ActionMessage("error.string.system.empty"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemListEmptyError");
			}

			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getMainBranch() == null || "".equals(form.getMainBranch().trim())) {
				errors.add("mainBranchError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "mainBranchError");
			}
			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}

			if (form.getCustomerNameShort() != null && !("".equals(form.getCustomerNameShort().trim()))) {
				if (form.getCustomerNameShort().length() > 100) {
					errors.add("customerNameShortLengthError",
							new ActionMessage("error.customerNameShort.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.customerNameShort.length.exceeded");
				}
			}

			if (form.getPan() != null && !("".equals(form.getPan().trim()))) {
				/*
				 * boolean nameFlag = ASSTValidator
				 * .isValidAlphaNumStringWithoutSpace(form.getPan()); if
				 * (nameFlag == true) errors.add("specialCharacterPanError", new
				 * ActionMessage( "error.string.invalidCharacter"));
				 */
				if (form.getPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getPan());
					if (flag == true)
						errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				} else {
					errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				}

				// CHECK If PAN NO Already validated with NSDL - Added by Ankit
				// on dt. 19-FEB-2016

				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("VALIDATE_PAN_WITH_NSDL");
				String paramValue = generalParamEntry.getParamValue();

				// if(!"Y".equalsIgnoreCase(form.getForm6061Checked())){
				if ("Y".equalsIgnoreCase(paramValue)) {

					if (form.getIsPanValidated() != null && form.getIsPanValidated() == 'N') {

						errors.add("validatePANWithNSDLError",
								new ActionMessage("error.string.validatePANWithNSDL", form.getPan()));
					} else {
						IPanValidationLogDao panValDao = (IPanValidationLogDao) BeanHouse.get("panValidationLogDao");
						if (!panValDao.checkPANAlreadyValidated("actualPANValidationLog", form.getPan(),
								form.getPartyId(),form.getPartyNameAsPerPan(),form.getDateOfIncorporation())) {
							errors.add("validatePANWithNSDLError",
									new ActionMessage("error.string.validatePANWithNSDL", form.getPan()));
						}
					}
				}
				// End

			}
			
//			if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {

				if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {
				//	boolean flag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getLeiCode());
					boolean flag = ASSTValidator.isValidLeiCode(form.getLeiCode());
					if (!flag)
						errors.add("invalidLeiCodeError",new ActionMessage("error.string.invalidLEIFormat"));
					else if(form.getLeiCode().trim().length()!=20)
						errors.add("invalidLeiCodeError",new ActionMessage("error.string.leiCode.length"));					
				}
				
				// CHECK If LEI Code Already validated with CCIL - Added by Nilkanth

				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("VALIDATE_LEI_WITH_CCIL");
				String paramValue = generalParamEntry.getParamValue();
				IGeneralParamEntry generalParamEntryforExposureAmount = generalParamDao
						.getGeneralParamEntryByParamCodeActual("EXPOSURE_AMOUNT_FOR_LEI_VALIDN");
				BigDecimal exposureAmount = new BigDecimal(generalParamEntryforExposureAmount.getParamValue());
				try {
				BigDecimal sanctionedAmount = new BigDecimal(UIUtil.removeComma(form.getTotalSanctionedLimit()));								
				if ( sanctionedAmount.compareTo(exposureAmount) == 0 || sanctionedAmount.compareTo(exposureAmount) == 1 ) {
					if(!"Y".equalsIgnoreCase(form.getDeferLEI())){
						if ("Y".equalsIgnoreCase(paramValue)) {
							if ((form.getIsLeiValidated() != null && form.getIsLeiValidated() == 'N')
									|| (form.getLeiCode() == null || "".equals(form.getLeiCode().trim()) )) {
								errors.add("validateLEIWithCCILError",
										new ActionMessage("error.string.validateLEIWithCCIL", form.getLeiCode()));
							} else {
								if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {
									ILEIValidationLogDao leiValDao = (ILEIValidationLogDao) BeanHouse.get("logLeiDao");
									if (!leiValDao.checkLEIAlreadyValidated("actualLEIValidationLog", form.getLeiCode(),
											form.getPartyId())) {
										errors.add("validateLEIWithCCILError",
												new ActionMessage("error.string.validateLEIWithCCIL", form.getLeiCode()));
									}
								}
							}
						}
						// End
					}
			   }
			}catch(NumberFormatException e)
				{
					DefaultLogger.error(ManualInputCustomerValidator.class, "Error in Sanctioned Amount");
					DefaultLogger.error(ManualInputCustomerValidator.class, e.getStackTrace());
				}
				
//			}

			if (form.getCustomerSegment() == null || "".equals(form.getCustomerSegment().trim())) {
				errors.add("customerSegmentError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerSegmentError");
			}
			if (form.getStatus() == null || "".equals(form.getStatus().trim())) {
				errors.add("statusError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "statusError");
			}
			/*
			 * if (form.getPartyGroupName() == null ||
			 * "".equals(form.getPartyGroupName().trim())) {
			 * errors.add("partyGroupNameError", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "partyGroupNameError"); }
			 */

//			if (form.getRmRegion() == null || "".equals(form.getRmRegion().trim())) {
//				errors.add("rmRegionError", new ActionMessage("error.string.mandatory"));
//				DefaultLogger.debug(ManualInputCustomerValidator.class, "rmRegionError");
//			}
//
//			if (form.getRelationshipMgr() == null || "".equals(form.getRelationshipMgr().trim())) {
//
//				errors.add("relationshipMgrError", new ActionMessage("error.string.mandatory"));
//				DefaultLogger.debug(ManualInputCustomerValidator.class, "relationshipMgrError");
//			}
			System.out.println("ManualInputCustomerValidator.java=>Line 4781=>form.getRelationshipMgrEmpCode()=>"+form.getRelationshipMgrEmpCode()+"*****");
			if (form.getRelationshipMgrEmpCode() == null || "".equals(form.getRelationshipMgrEmpCode().trim())) {
				errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.mandatory"));
				// DefaultLogger.debug(ManualInputCustomerValidator.class, "rmRegionError");
			} else {
				List data = fetchRMData(form.getRelationshipMgrEmpCode());
				if (data.isEmpty()) {
					errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.disable.emp.code"));
				}
				
			boolean isAlphaNumeric = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRelationshipMgrEmpCode());
			
			if (isAlphaNumeric){
				errors.add("relationshipMgrEmpCodeError", new ActionMessage("error.string.specialcharacter"));
			}
			}
			if (form.getCycle() == null || "".equals(form.getCycle().trim())) {

				errors.add("cycleError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "cycleError");
			}
			if (form.getEntity() == null || "".equals(form.getEntity().trim())) {

				errors.add("entityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "entityError");
			}
			if (form.getIndustryName() == null || "".equals(form.getIndustryName().trim())) {

				errors.add("industryNameError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "industryNameError");
			}
			if (form.getRbiIndustryCode() == null || "".equals(form.getRbiIndustryCode().trim())) {

				errors.add("rbiIndustryCodeError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "rbiIndustryCodeError");
			}
			if (form.getAddress1() == null || "".equals(form.getAddress1().trim())) {

				errors.add("address1Error", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "address1Error");
			} else if (form.getAddress1().length() > 75) {
				errors.add("address1LengthError", new ActionMessage("error.address1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address1.length.exceeded");
			}
			if (form.getAddress2() != null && !("".equals(form.getAddress2().trim()))) {
				if (form.getAddress2().length() > 75) {
					errors.add("address2LengthError", new ActionMessage("error.address2.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address2.length.exceeded");
				}
			}
			if (form.getAddress3() != null && !("".equals(form.getAddress3().trim()))) {
				if (form.getAddress3().length() > 75) {
					errors.add("address3LengthError", new ActionMessage("error.address3.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address3.length.exceeded");
				}
			}
			if (form.getTelephoneNo() != null && !("".equals(form.getTelephoneNo().trim()))) {
				if (form.getTelephoneNo().length() > 15) {
					errors.add("telephoneNoLengthError", new ActionMessage("error.telephoneNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.telephoneNo.length.exceeded");
				}
			}
			if (!(form.getTelephoneNo() == null || form.getTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getStdCodeTelNo() == null || form.getStdCodeTelNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeTelNo().toString().trim(), false, 1, 99999))) {
					errors.add("specialCharacterStdTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}

			if (!(form.getTelex() == null || form.getTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getTelex().toString().trim(),
						false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			if (!(form.getStdCodeTelex() == null || form.getStdCodeTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getStdCodeTelex().toString().trim(), false, 1,99999))) {
					errors.add("specialCharacterStdTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
				}
			}
			if (form.getTelex() != null && !("".equals(form.getTelex().trim()))) {
				if (form.getTelex().length() > 15) {
					errors.add("telexLengthError", new ActionMessage("error.telex.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.telex.length.exceeded");
				}
			}
			if (!(form.getEmail() == null || form.getEmail().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getEmail(), true))) {
					errors.add("emailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (form.getCountry() == null || "".equals(form.getCountry().trim())) {

				errors.add("countryError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "countryError");
			}
			if (form.getState() == null || "".equals(form.getState().trim())) {

				errors.add("stateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "stateError");
			}
			if (form.getRegion() == null || "".equals(form.getRegion().trim())) {

				errors.add("regionError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "regionError");
			}
			if (form.getCity() == null || "".equals(form.getCity().trim())) {

				errors.add("cityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "cityError");
			}

			if (form.getPostcode() != null && !("".equals(form.getPostcode().trim()))) {
				if (form.getPostcode().length() > 6) {
					errors.add("postCodeLengthError", new ActionMessage("error.postCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.postCode.length.exceeded");
				}
			}
			
			if (form.getDateOfIncorporation() == null || "".equals(form.getDateOfIncorporation().trim())) {
				errors.add("corporationDateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			} 
			if (form.getDateOfIncorporation() != null && !"".equals(form.getDateOfIncorporation())) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				try {
					if (DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).equals(DateUtil.clearTime(d))
							|| DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).after(DateUtil.clearTime(d))) {
						errors.add("corporationDateError", new ActionMessage("error.string.dateValidation"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			}
			if(form.getEntity().equalsIgnoreCase("22") || form.getEntity().equalsIgnoreCase("PVT.LTD.") || form.getEntity().equalsIgnoreCase("LIMITED")) {
				if (form.getCinLlpin() == null || "".equals(form.getCinLlpin().trim())) {
				    errors.add("cinLlpinError", new ActionMessage("error.string.mandatory"));
				    DefaultLogger.debug(ManualInputCustomerValidator.class, "cinLlpinError");
				}
				else if (!(((alpha >= 'a' && alpha <= 'z') || (alpha >= 'A' && alpha <= 'Z')) && (numeric >= '0' && numeric <= '9'))) {
					errors.add("cinError", new ActionMessage("error.cinllpin.invalid.alphanumeric"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.cinllpin.invalid.alphanumeric");    
				}
					
		    }
			
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (!(errorCode = Validator.checkString(form.getListedCompany(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("listedCompanyError", new ActionMessage("error.string.mandatory", "1", "10"));
				}
			}
			
			if (form.getListedCompany() != null &&  "Yes".equalsIgnoreCase(form.getListedCompany().trim())  ) {

				if (!(errorCode = Validator.checkString(form.getIsinNo(), true, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isinNoError", new ActionMessage("error.string.length.error", "1", "20"));
				}
				
			}
			
			if(form.getRaroc() != null && !form.getRaroc().trim().isEmpty())
			{
			if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getRaroc(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10,3,locale))) {
				errors.add("rarocCharacterError", new ActionMessage("error.raroc.format"));
			}
			}
			/*if (!(errorCode = Validator.checkString(form.getYearEndPeriod().trim(), true, 6, 6))
					.equals(Validator.ERROR_NONE)) {
				errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory", "6", "6"));
			}*/
			
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (form.getYearEndPeriod() == null || "".equals(form.getYearEndPeriod()))
				{
					errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory"));

				}
			}
	
	
	 Calendar currentDate = Calendar.getInstance();
    int  year = currentDate.get(Calendar.YEAR);
    
    
   if(!form.getYearEndPeriod().isEmpty()  &&  form.getYearEndPeriod() != null) {
		if(form.getYearEndPeriod().matches("\\d{2}-\\w{3}"))
       {
		String dt= form.getYearEndPeriod()+"-"+String.valueOf(year);
			if (! (errorCode = Validator.checkDate(dt, true, Locale.getDefault())).equals(Validator.ERROR_NONE) || dt.contains("/")) {
			errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));
		 }
	}
		else
       {
       	errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));	
       }
   }
			
			if ( !"".equalsIgnoreCase(form.getCreditMgrEmpId())  &&  form.getCreditMgrEmpId() != null ) {

				if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCreditMgrEmpId(), true, 5, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("creditMgrEmpIdError", new ActionMessage("error.creditMgrEmpId.length.error"));
				}
			}
			
			if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() > 12) {
					errors.add("aadharLengthError", new ActionMessage("error.aadhar.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.exceeded");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() < 12) {
					errors.add("aadharLengthLessError", new ActionMessage("error.aadhar.length.LessThan"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.LessThan");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (!(form.getAadharNumber().matches("[0-9]+"))){
					errors.add("aadharCharacterError", new ActionMessage("error.aadhar.character.contain"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.character.contain");
				}
				}
//				if (form.getAadharNumber() == null || "".equals(form.getAadharNumber().trim())){
//					errors.add("aadharNumberError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.string.mandatory");
//				}
		}
		if (event.equals("save_customer_partymaker") || event.equals("save_customer_in_edit_partymaker")) {

			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}

			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}

			if (form.getPan() != null && !("".equals(form.getPan().trim()))) {
				/*
				 * boolean nameFlag = ASSTValidator
				 * .isValidAlphaNumStringWithoutSpace(form.getPan()); if
				 * (nameFlag == true) errors.add("specialCharacterPanError", new
				 * ActionMessage( "error.string.invalidCharacter"));
				 */
				if (form.getPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getPan());
					if (flag == true)
						errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));

				} else {
					errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				}

			}

			if (form.getAddress1().length() > 75) {
				errors.add("address1LengthError", new ActionMessage("error.address1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address1.length.exceeded");
			}

			if (form.getAddress2().length() > 75) {
				errors.add("address2LengthError", new ActionMessage("error.address2.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address2.length.exceeded");
			}

			if (form.getAddress3().length() > 75) {
				errors.add("address3LengthError", new ActionMessage("error.address3.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address3.length.exceeded");
			}

			if (!(form.getTelephoneNo() == null || form.getTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getTelex() == null || form.getTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getTelex().toString().trim(),
						false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			if (!(form.getEmail() == null || form.getEmail().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getEmail(), true))) {
					errors.add("emailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}

			if (form.getPan().length() > 10) {
				errors.add("panLengthError", new ActionMessage("error.pan.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.pan.length.exceeded");
			}
			
			if (form.getDateOfIncorporation() == null || "".equals(form.getDateOfIncorporation().trim())) {
				errors.add("corporationDateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			}
			if (form.getDateOfIncorporation() != null && !"".equals(form.getDateOfIncorporation())) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				try {
					if (DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).equals(DateUtil.clearTime(d))
							|| DateUtil.clearTime(sdf.parse(form.getDateOfIncorporation())).after(DateUtil.clearTime(d))) {
						errors.add("corporationDateError", new ActionMessage("error.string.dateValidation"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DefaultLogger.debug(ManualInputCustomerValidator.class, "corporationDateError");
			}
			if(form.getEntity().equalsIgnoreCase("22") || form.getEntity().equalsIgnoreCase("PVT.LTD.") || form.getEntity().equalsIgnoreCase("LIMITED")) {
				if (form.getCinLlpin() == null || "".equals(form.getCinLlpin().trim())) {
				    errors.add("cinLlpinError", new ActionMessage("error.string.mandatory"));
				    DefaultLogger.debug(ManualInputCustomerValidator.class, "cinLlpinError");
				}
				else if (!(((alpha >= 'a' && alpha <= 'z') || (alpha >= 'A' && alpha <= 'Z')) && (numeric >= '0' && numeric <= '9'))) {
					errors.add("cinError", new ActionMessage("error.cinllpin.invalid.alphanumeric"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.cinllpin.invalid.alphanumeric");    
				}
					
		    }
			
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (!(errorCode = Validator.checkString(form.getListedCompany(), true, 1, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("listedCompanyError", new ActionMessage("error.string.mandatory", "1", "10"));
				}
			}
			
			if (form.getListedCompany() != null &&  "Yes".equalsIgnoreCase(form.getListedCompany().trim())  ) {

				if (!(errorCode = Validator.checkString(form.getIsinNo(), true, 1, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("isinNoError", new ActionMessage("error.string.length.error", "1", "20"));
				}
				
			}
			
			if(form.getRaroc() != null && !form.getRaroc().trim().isEmpty())
			{
			if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getRaroc(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10,3,locale))) {
				errors.add("rarocCharacterError", new ActionMessage("error.raroc.format"));
			}
			}
			/*if (!(errorCode = Validator.checkString(form.getYearEndPeriod().trim(), true, 6, 6))
					.equals(Validator.ERROR_NONE)) {
				errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory", "6", "6"));
				

			}*/
			if(PropertyManager.getValue("new.cam.webservice.mandatory.field.flag").equals("OFF") && (event.equalsIgnoreCase("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				// bypass mandatory validation
			}else {
				if (form.getYearEndPeriod() == null || "".equals(form.getYearEndPeriod()))
				{
					errors.add("yearEndPeriodError", new ActionMessage("error.string.mandatory"));

				}
			}
	
	 Calendar currentDate = Calendar.getInstance();
    int  year = currentDate.get(Calendar.YEAR);
    
    
   if(!form.getYearEndPeriod().isEmpty()  &&  form.getYearEndPeriod() != null) {
		if(form.getYearEndPeriod().matches("\\d{2}-\\w{3}"))
       {
		String dt= form.getYearEndPeriod()+"-"+String.valueOf(year);
			if (! (errorCode = Validator.checkDate(dt, true, Locale.getDefault())).equals(Validator.ERROR_NONE) || dt.contains("/")) {
			errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));
		 }
	}
		else
       {
       	errors.add("yearEndPeriodFormatError", new ActionMessage("error.yearEndFormat.invalid.format"));	
       }
   }
			
			if ( !"".equalsIgnoreCase(form.getCreditMgrEmpId())  &&  form.getCreditMgrEmpId() != null ) {

				if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCreditMgrEmpId(), true, 5, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("creditMgrEmpIdError", new ActionMessage("error.creditMgrEmpId.length.error"));
				}
			}
			
			if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() > 12) {
					errors.add("aadharLengthError", new ActionMessage("error.aadhar.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.exceeded");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (form.getAadharNumber().length() < 12) {
					errors.add("aadharLengthLessError", new ActionMessage("error.aadhar.length.LessThan"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.length.LessThan");
				}
				}
				if((form.getAadharNumber() != null && !("".equals(form.getAadharNumber().trim())))) {
				if (!(form.getAadharNumber().matches("[0-9]+"))){
					errors.add("aadharCharacterError", new ActionMessage("error.aadhar.character.contain"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.aadhar.character.contain");
				}
				}
//				if (form.getAadharNumber() == null || "".equals(form.getAadharNumber().trim())){
//					errors.add("aadharNumberError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.string.mandatory");
//				}
		}
		// End:Uma Khot:Added for Valid Rating CR
		// ==========================================================

		// --------------
		if (event.equals("save_other_system") || event.equals("save_other_system_in_edit")
				|| event.equals("save_other_system_in_resubmit") || event.equals("save_edited_system")
				|| event.equals("save_edited_system_in_edit") || event.equals("save_edited_system_in_resubmit")
				|| event.equals("WS_create_customer")
				// Start:Uma:Added for Valid Rating CR
				|| event.equals("save_other_system_partymaker") || event.equals("save_other_system_in_edit_partymaker")
				|| event.equals("save_other_system_in_resubmit_partymaker")
				|| event.equals("save_edited_system_partymaker")
				|| event.equals("save_edited_system_in_edit_partymaker")
				|| event.equals("save_edited_system_in_resubmit_partymaker")
				
		// End:Uma:Added for Valid Rating CR

		) {

			if (form.getSystem() == null || "".equals(form.getSystem().trim())) {
				errors.add("systemError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemError");
			}
			if (form.getSystemCustomerId() == null || "".equals(form.getSystemCustomerId().trim())) {
				errors.add("systemCustomerIdError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "systemCustomerIdError");
			} else if (form.getSystemCustomerId().length() > 16) {
				errors.add("systemCustomerIdLengthError", new ActionMessage("error.systemCustomerId.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.systemCustomerId.length.exceeded");
			} else {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSystemCustomerId());
				if (nameFlag == true)
					errors.add("specialCharacterSystemCustomerIdError",
							new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		if(event.equals("save_vendor_name")
				|| event.equals("save_vendor_name_in_edit")
				|| event.equals("save_vendor_name_in_resubmit")
				|| event.equals("save_edited_vendor")
				|| event.equals("save_edited_vendor_in_edit")
				|| event.equals("save_edited_vendor_in_resubmit")
				) {
			if (form.getVendorName() == null || "".equals(form.getVendorName().trim())) {
				errors.add("VendorNameError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "VendorNameError");
			}
		}

		if (event.equals("save_party_group") || event.equals("save_party_group_in_edit")
				|| event.equals("save_party_group_in_resubmit")) {
			if (form.getPartyId() == null || "".equals(form.getPartyId().trim())) {

				errors.add("partyIdError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "partyIdError");
			}

		}

		if (event.equals("save_director") || event.equals("save_director_in_edit")
				|| event.equals("save_director_in_resubmit") || event.equals("save_edited_director")
				|| event.equals("save_edited_director_in_edit") || event.equals("save_edited_director_in_resubmit")) {

			if (form.getDinNo() != null && !("".equals(form.getDinNo().trim()))) {
				if (form.getDinNo().length() > 9) {
					errors.add("dinNoLengthError", new ActionMessage("error.dinNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.dinNo.length.exceeded");
				} else {
					boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getDinNo());
					if (nameFlag == true)
						errors.add("specialCharacterDinNoError", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			if (form.getDinNo() != null && !("".equals(form.getDinNo().trim()))) {
				if (form.getDirectorName().length() > 100) {

					errors.add("directorNameLengthError", new ActionMessage("error.directorName.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorName.length.exceeded");
				} else {
					boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDirectorName());
					if (nameFlag == true)
						errors.add("specialCharactergetDirectorNameError",
								new ActionMessage("error.string.invalidCharacter"));
				}
			}
			if (form.getRelatedDUNSNo() != null && form.getRelatedDUNSNo().length() > 9) {
				errors.add("relatedDUNSNoLengthError", new ActionMessage("error.relatedDUNSNo.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.relatedDUNSNo.length.exceeded");
			}

			if (form.getRelatedDUNSNo() != null && !("".equals(form.getRelatedDUNSNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRelatedDUNSNo());
				if (nameFlag == true)
					errors.add("specialCharacterRelatedDUNSNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getDirectorPan() != null && !("".equals(form.getDirectorPan().trim()))) {
				if (form.getDirectorPan().length() > 10) {
					errors.add("directorPanLengthError", new ActionMessage("error.directorPan.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorPan.length.exceeded");
				}
			}
			if (form.getDirectorPan() != null && !("".equals(form.getDirectorPan().trim()))) {
				/*
				 * boolean nameFlag = ASSTValidator
				 * .isValidAlphaNumStringWithoutSpace(form.getDirectorPan()); if
				 * (nameFlag == true)
				 * errors.add("specialCharacterDirectorPanError", new
				 * ActionMessage("error.string.invalidCharacter"));
				 */
				if (form.getDirectorPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getDirectorPan());
					if (flag == true)
						errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));

				} else {
					errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				}
			}
			if (form.getBusinessEntityName() != null && !("".equals(form.getBusinessEntityName().trim()))) {
				if (form.getBusinessEntityName().length() > 100) {
					errors.add("businessEntityNameLengthError",
							new ActionMessage("error.businessEntityName.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.businessEntityName.length.exceeded");
				}
			}
			if (form.getBusinessEntityName() != null && !("".equals(form.getBusinessEntityName().trim()))) {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getBusinessEntityName());
				if (nameFlag == true)
					errors.add("specialCharacterBusinessEntityNameError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getFullName() != null && !("".equals(form.getFullName().trim()))) {
				if (form.getFullName().length() > 100) {
					errors.add("fullNameLengthError", new ActionMessage("error.fullName.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.fullName.length.exceeded");
				}
			}
			if (form.getFullName() != null && !("".equals(form.getFullName().trim()))) {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getFullName());
				if (nameFlag == true)
					errors.add("specialCharacterFullNameError", new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getNamePrefix() != null && !("".equals(form.getNamePrefix().trim()))) {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getNamePrefix());
				if (nameFlag == true)
					errors.add("specialCharacterNamePrefixError", new ActionMessage("error.string.invalidCharacter"));
			}
			if (!(errorCode = Validator.checkNumber(form.getPercentageOfControl(), false, 0, 100.00, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("percentageOfControlLengthError",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
			}
			if (form.getRelatedType() == null || "".equals(form.getRelatedType().trim())) {

				errors.add("relatedTypeError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "relatedTypeError");
			}
			if (form.getRelationship() == null || "".equals(form.getRelationship().trim())) {

				errors.add("relationshipError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "relationshipError");
			}
			if (form.getDirectorAddress1() == null || "".equals(form.getDirectorAddress1().trim())) {

				errors.add("directorAddress1Error", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorAddress1Error");
			} else if (form.getDirectorAddress1().length() > 75) {
				errors.add("directorAddress1LengthError", new ActionMessage("error.directorAddress1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAddress1.length.exceeded");
			}
			if (form.getDirectorAddress2() == null || "".equals(form.getDirectorAddress2().trim())) {

				errors.add("directorAddress2Error", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorAddress2Error");
			} else if (form.getDirectorAddress2().length() > 75) {
				errors.add("directorAddress2LengthError", new ActionMessage("error.directorAddress2.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAddress2.length.exceeded");
			}
			
			if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
			if (form.getDirectorAadhar().length() > 12) {
				errors.add("directorAadharLengthError", new ActionMessage("error.directorAadhar.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.length.exceeded");
			}
			}
			if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
			if (form.getDirectorAadhar().length() < 12) {
				errors.add("directorAadharLengthLessError", new ActionMessage("error.directorAadhar.length.LessThan"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.length.LessThan");
			}
			}
			if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
			if (!(form.getDirectorAadhar().matches("[0-9]+"))){
				errors.add("directorAadharCharacterError", new ActionMessage("error.directorAadhar.character.contain"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.character.contain");
			}
			}
//			if (form.getDirectorAadhar() == null || "".equals(form.getDirectorAadhar().trim())){
//			errors.add("directorAadharNumberError", new ActionMessage("error.string.mandatory"));
//			DefaultLogger.debug(ManualInputCustomerValidator.class, "error.string.mandatory");
//		}
			if (form.getDirectorAddress3() != null && !"".equals(form.getDirectorAddress3())) {
				if (form.getDirectorAddress3().length() > 75) {
					errors.add("directorAddress3LengthError",
							new ActionMessage("error.directorAddress3.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAddress3.length.exceeded");
				}
			}
			if (form.getDirectorTelNo().length() > 15) {
				errors.add("directorTelNoLengthError", new ActionMessage("error.directorTelNo.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorTelNo.length.exceeded");
			}
			if (!(form.getDirectorTelNo() == null || form.getDirectorTelNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirectorTelNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterDirectorTelNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getDirectorFax() == null || form.getDirectorFax().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirectorFax().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterDirectorFaxError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			if (form.getDirectorFax().length() > 15) {
				errors.add("directorFaxLengthError", new ActionMessage("error.directorFax.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorFax.length.exceeded");
			}
			/*
			 * if (form.getDirectorEmail().length() > 50) {
			 * errors.add("directorEmailLengthError", new ActionMessage(
			 * "error.directorEmail.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.directorEmail.length.exceeded"); }
			 */
			if (!(form.getDirectorEmail() == null || form.getDirectorEmail().trim().equals(""))) {

				if (form.getDirectorEmail().length() > 50) {
					errors.add("directorEmailError", new ActionMessage("error.directorEmail.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorEmail.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getDirectorEmail(), true))) {
					errors.add("directorEmailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (form.getDirectorCountry() == null || "".equals(form.getDirectorCountry().trim())) {

				errors.add("directorCountryError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorCountryError");
			}
			if (form.getDirectorRegion() == null || "".equals(form.getDirectorRegion().trim())) {

				errors.add("directorRegionError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorRegionError");
			}
			if (form.getDirectorState() == null || "".equals(form.getDirectorState().trim())) {

				errors.add("directorStateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorStateError");
			}
			if (form.getDirectorCity() == null || "".equals(form.getDirectorCity().trim())) {

				errors.add("directorCityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorCityError");
			}
			if (form.getDirectorPostCode() != null && !("".equals(form.getDirectorPostCode().trim()))) {
				if (form.getDirectorPostCode().length() > 6) {
					errors.add("directorPostCodeLengthError",
							new ActionMessage("error.directorPostCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorPostCode.length.exceeded");
				}
				if (form.getDirectorPostCode() != null && !("".equals(form.getDirectorPostCode().trim()))) {
					boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getDirectorPostCode());
					if (nameFlag == true)
						errors.add("specialCharacterdirectorPostCodeError",
								new ActionMessage("error.string.invalidCharacter"));
				}
				
				if (!(event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if (pincodeDao != null && form.getDirectorState() != null) {

					String stateCode = form.getDirectorState();
					String pincode = form.getDirectorPostCode();
					if (pincode != null) {
						if (pincode.length() > 1 || pincode.length() == 1) {
							pincode = pincode.substring(0, 1);
							if(null != stateCode && !"".equals(stateCode)){
							if (stateCode != null && !pincodeDao.isPincodeMappingValid(pincode, stateCode)) {
								errors.add("directorPostcodeError", new ActionMessage("error.string.invalidMapping"));
							}
							}
						} else {
							errors.add("directorPostcodeError", new ActionMessage("error.string.invalidMapping"));
						}
					}

				}
			}

		}
			
		return errors;	
	}
		
		if (event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer")) {

			if (form.getRelatedType() == null || "".equals(form.getRelatedType().trim())) {

				errors.add("relatedTypeError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "relatedTypeError");
			}
			if (form.getDirectorPan() != null && !("".equals(form.getDirectorPan().trim()))) {
				if (form.getDirectorPan().length() > 10) {
					errors.add("directorPanLengthError", new ActionMessage("error.directorPan.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorPan.length.exceeded");
				}

				if (form.getDirectorPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getDirectorPan());
					if (flag == true)
						errors.add("invalidDirectorPanError", new ActionMessage("error.string.invalidPANFormat"));

				} else {
					errors.add("invalidDirectorPanError", new ActionMessage("error.string.invalidPANFormat"));
				}
			}
			
			if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
				if (form.getDirectorAadhar().length() > 12) {
					errors.add("directorAadharLengthError", new ActionMessage("error.directorAadhar.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.length.exceeded");
				}
				}
				if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
				if (form.getDirectorAadhar().length() < 12) {
					errors.add("directorAadharLengthLessError", new ActionMessage("error.directorAadhar.length.LessThan"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.length.LessThan");
				}
				}
				if((form.getDirectorAadhar() != null && !("".equals(form.getDirectorAadhar().trim())))) {
				if (!(form.getDirectorAadhar().matches("[0-9]+"))){
					errors.add("directorAadharCharacterError", new ActionMessage("error.directorAadhar.character.contain"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorAadhar.character.contain");
				}
				}
//				if (form.getDirectorAadhar() == null || "".equals(form.getDirectorAadhar().trim())){
//					errors.add("directorAadharNumberError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.string.mandatory");
//				}

			if (form.getRelationship() == null || "".equals(form.getRelationship().trim())) {

				errors.add("relationshipError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "relationshipError");
			}

			if (form.getBusinessEntityName() != null && !("".equals(form.getBusinessEntityName().trim()))) {
				if (form.getBusinessEntityName().length() > 100) {
					errors.add("businessEntityNameLengthError",
							new ActionMessage("error.businessEntityName.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.businessEntityName.length.exceeded");
				}

				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getBusinessEntityName());
				if (nameFlag == true)
					errors.add("specialCharacterBusinessEntityNameError",
							new ActionMessage("error.string.invalidCharacter"));
			}

			if (form.getNamePrefix() != null && !("".equals(form.getNamePrefix().trim()))) {

				if (form.getNamePrefix().length() > 5) {
					errors.add("specialCharacterNamePrefixError",
							new ActionMessage("error.nameprefix.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.nameprefix.length.exceeded");
				}

				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getNamePrefix());
				if (nameFlag == true)
					errors.add("specialCharacterNamePrefixError", new ActionMessage("error.string.invalidCharacter"));
			}

			if (form.getFullName() != null && !("".equals(form.getFullName().trim()))) {
				if (form.getFullName().length() > 100) {
					errors.add("fullNameLengthError", new ActionMessage("error.fullName.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.fullName.length.exceeded");
				}

				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getFullName());
				if (nameFlag == true)
					errors.add("specialCharacterFullNameError", new ActionMessage("error.string.invalidCharacter"));
			}

			if (!(errorCode = Validator.checkNumber(form.getPercentageOfControl(), false, 0, 100.00, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("percentageOfControlLengthError",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
			}

			if (form.getDirectorTelNo() != null && !form.getDirectorTelNo().trim().isEmpty()) {
				if (form.getDirectorTelNo().length() > 15) {
					errors.add("directorTelNoLengthError", new ActionMessage("error.directorTelNo.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorTelNo.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirectorTelNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterDirectorTelNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}

			if (form.getDirectorFax() != null && !form.getDirectorFax().trim().isEmpty()) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirectorFax().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterDirectorFaxError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}

				if (form.getDirectorFax().length() > 15) {
					errors.add("directorFaxLengthError", new ActionMessage("error.directorFax.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.directorFax.length.exceeded");
				}
			}

			if (!(form.getDirectorEmail() == null || form.getDirectorEmail().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getDirectorEmail(), true))) {
					errors.add("directorEmailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (form.getDirectorCountry() == null || "".equals(form.getDirectorCountry().trim())) {

				errors.add("directorCountryError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorCountryError");
			}
			if (form.getDirectorRegion() == null || "".equals(form.getDirectorRegion().trim())) {

				errors.add("directorRegionError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorRegionError");
			}
			if (form.getDirectorState() == null || "".equals(form.getDirectorState().trim())) {

				errors.add("directorStateError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorStateError");
			}
			if (form.getDirectorCity() == null || "".equals(form.getDirectorCity().trim())) {

				errors.add("directorCityError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "directorCityError");
			}

			if (form.getDirectorPostCode() != null && !form.getDirectorPostCode().trim().isEmpty()) {
				if (form.getDirectorPostCode().length() > 6) {
					errors.add("directorpincodeError", new ActionMessage("error.wsdl.directorPincode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"error.wsdl.directorPincode.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirectorPostCode().toString().trim(), false, 6, 999999.D))) {
					errors.add("directorpincodeError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "6"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "directorpincodeError");
				}
				
				if (!(event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if (pincodeDao != null && form.getDirectorState() != null) {

					String stateCode = form.getDirectorState();
					String pincode = form.getDirectorPostCode();
					if (pincode != null) {
						if (pincode.length() > 1 || pincode.length() == 1) {
							pincode = pincode.substring(0, 1);
							if(null != stateCode && !"".equals(stateCode)){
							if (stateCode != null && !pincodeDao.isPincodeMappingValid(pincode, stateCode)) {
								errors.add("directorPostcodeError", new ActionMessage("error.string.invalidMapping"));
							}
							}
						} else {
							errors.add("directorPostcodeError", new ActionMessage("error.string.invalidMapping"));
						}
					}

				}
				}
			}
			if (form.getDirStdCodeTelNo() != null && !form.getDirStdCodeTelNo().trim().isEmpty()) {
				if (form.getDirStdCodeTelNo().length() > 5) {
					errors.add("directorSTDCodeTelNoLengthError",
							new ActionMessage("error.wsdl.directorTelephoneStdCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"error.wsdl.directorTelephoneStdCode.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirStdCodeTelNo().toString().trim(), false, 5, 99999.D))) {
					errors.add("specialCharacterDirectorSTDCodeTelNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"specialCharacterDirectorSTDCodeTelNoError");
				}
			}
			if (form.getDirStdCodeTelex() != null && !form.getDirStdCodeTelex().trim().isEmpty()) {
				if (form.getDirStdCodeTelex().length() > 5) {
					errors.add("directorSTDCodeFaxLengthError",
							new ActionMessage("error.wsdl.directorFaxStdCode.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class,
							"error.wsdl.directorFaxStdCode.length.exceeded");
				}

				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getDirStdCodeTelex().toString().trim(), false, 5, 99999.D))) {
					errors.add("specialCharacterDirectorSTDCodeFaxError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "5"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "specialCharacterDirectorSTDCodeFaxError");
				}
			}
		}
		
		if (event.equals("save_customer_brmaker") || event.equals("save_customer_in_edit_brmaker")) {
			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}
		}
		if (event.equals("save_customer") || event.equals("save_customer_in_edit")
				|| event.equals("validate_customer_form_save") || event.equals("validate_customer_form_in_edit_save")) {

			if (form.getCustomerNameShort() == null || "".equals(form.getCustomerNameShort().trim())) {
				errors.add("customerNameShortError", new ActionMessage("error.string.mandatory"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "customerNameShortError");
			} else {
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getCustomerNameShort());
				if (nameFlag == true)
					errors.add("specialCharacterCustomerNameShortError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			
			if(form.getRaroc() != null && !form.getRaroc().trim().isEmpty())
			{
			if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getRaroc(), true, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10,3,locale))) {
				errors.add("rarocCharacterError", new ActionMessage("error.raroc.format"));
			}
			}

			Date d = DateUtil.getDate();
			if ((form.getRelationshipStartDate() != null) && (form.getRelationshipStartDate().trim().length() != 0)) {
				int a = d.compareTo(DateUtil.convertDate(locale, form.getRelationshipStartDate()));

				if (a < 0) {
					errors.add("relationshipDateError",
							new ActionMessage("error.date.compareDate.more", "Relationship  Date ", "Current Date"));
				}

			}
			/*
			 * if (form.getCustomerNameShort() != null &&
			 * !("".equals(form.getCustomerNameShort().trim()))) {
			 * 
			 * boolean nameFlag = ASSTValidator
			 * .isValidAlphaNumStringWithSpace(form .getCustomerNameShort()); if
			 * (nameFlag == true)
			 * errors.add("specialCharacterCustomerNameShortError", new
			 * ActionMessage("error.string.invalidCharacter")); }
			 */
			if (form.getPan() != null && !("".equals(form.getPan().trim()))) {
				/*
				 * boolean nameFlag = ASSTValidator
				 * .isValidAlphaNumStringWithoutSpace(form.getPan()); if
				 * (nameFlag == true) errors.add("specialCharacterPanError", new
				 * ActionMessage( "error.string.invalidCharacter"));
				 */
				if (form.getPan().length() == 10) {
					boolean flag = ASSTValidator.isValidPanNo(form.getPan());
					if (flag == true)
						errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));

				} else {
					errors.add("invalidPanError", new ActionMessage("error.string.invalidPANFormat"));
				}

			}
			if (form.getPostcode() != null && !("".equals(form.getPostcode().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getPostcode());
				if (nameFlag == true)
					errors.add("specialCharacterPostcodeError", new ActionMessage("error.string.invalidCharacter"));

			}
			if (form.getAddress1().length() > 75) {
				errors.add("address1LengthError", new ActionMessage("error.address1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address1.length.exceeded");
			}

			if (form.getAddress2().length() > 75) {
				errors.add("address2LengthError", new ActionMessage("error.address2.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address2.length.exceeded");
			}

			if (form.getAddress3().length() > 75) {
				errors.add("address3LengthError", new ActionMessage("error.address3.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.address3.length.exceeded");
			}
			/*
			 * if (form.getTelephoneNo().length() > 15) {
			 * errors.add("telephoneNoLengthError", new ActionMessage(
			 * "error.telephoneNo.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.telephoneNo.length.exceeded"); }
			 */
			if (!(form.getTelephoneNo() == null || form.getTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getTelex() == null || form.getTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getTelex().toString().trim(),
						false, 15, 999999999999999.D))) {
					errors.add("specialCharacterTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			/*
			 * if (form.getTelex().length() > 15) {
			 * errors.add("telexLengthError", new ActionMessage(
			 * "error.telex.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.telex.length.exceeded"); }
			 */ if (!(form.getEmail() == null || form.getEmail().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getEmail(), true))) {
					errors.add("emailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}

			if (!(errorCode = Validator.checkNumber(form.getTotalFundedLimit(), false, 0, 99999999999999999999.99, 3,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalFundedLimitLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("MULTIPLE") || form.getBankingMethod().equals("CONSORTIUM"))) {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 0, 99.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99.99"));
				}
			} else {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 0, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
				}
			}
			if (!(errorCode = Validator.checkNumber(form.getTotalNonFundedLimit(), false, 0, 99999999999999999999.99, 3,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalNonFundedLimitLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			if (form.getBankingMethod() != null
					&& (form.getBankingMethod().equals("MULTIPLE") || form.getBankingMethod().equals("CONSORTIUM"))) {
				if (!(errorCode = Validator.checkNumber(form.getNonFundedSharePercent(), false, 0, 99.99, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonFundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99.99"));
				}
			} else if (form.getBankingMethod() != null && (form.getBankingMethod().equals("SOLE"))) {
				if (!(errorCode = Validator.checkNumber(form.getFundedSharePercent(), false, 100.00, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("fundedSharePercentError", new ActionMessage("error.must.hunderd"));
				}
			} else {
				if (!(errorCode = Validator.checkNumber(form.getNonFundedSharePercent(), false, 0, 100.00, 3, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nonFundedSharePercentLengthError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100.00"));
				}
			}
			if (!(errorCode = Validator.checkNumber(form.getMemoExposure(), false, 0, 99999999999999999999.99, 3,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("memoExposureLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99999999999999999999.99"));
			}
			/*
			 * Below validation has been commented as to be in sync with
			 * production version, As discussed with Ganesh and Vasanti on
			 * 27-JUL-2016
			 * 
			 * if (form.getMpbf().length() > 10) { errors.add("mpbfLengthError",
			 * new ActionMessage( "error.mpbf.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.mpbf.length.exceeded"); }
			 */

			if (form.getBorrowerDUNSNo().length() > 9) {
				errors.add("borrowerDUNSNoLengthError", new ActionMessage("error.borrowerDUNSNoError.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.borrowerDUNSNoError.length.exceeded");
			} else {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getBorrowerDUNSNo());
				if (nameFlag == true)
					errors.add("specialCharacterBorrowerDUNSNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}

			if (form.getRegOfficeAddress1().length() > 75) {
				errors.add("regOfficeAddress1LengthError",
						new ActionMessage("error.regOfficeAddress1.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress1.length.exceeded");
			}
			/*
			 * if (form.getRegOfficeAddress2() == null ||
			 * "".equals(form.getRegOfficeAddress2().trim())) {
			 * 
			 * errors.add("regOfficeAddress2Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "regOfficeAddress2Error"); } else
			 */ if (form.getRegOfficeAddress2().length() > 75) {
				errors.add("regOfficeAddress2LengthError",
						new ActionMessage("error.regOfficeAddress2.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress2.length.exceeded");
			}
			/*
			 * if (form.getRegOfficeAddress3() == null ||
			 * "".equals(form.getRegOfficeAddress3().trim())) {
			 * 
			 * errors.add("regOfficeAddress3Error", new ActionMessage(
			 * "error.string.mandatory"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "regOfficeAddress3Error"); } else
			 */ if (form.getRegOfficeAddress3().length() > 75) {
				errors.add("regOfficeAddress3LengthError",
						new ActionMessage("error.regOfficeAddress3.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeAddress3.length.exceeded");
			}

			/*
			 * if (form.getRegOfficeTelephoneNo().length() > 15) {
			 * errors.add("regOfficeTelephoneNoLengthError", new ActionMessage(
			 * "error.regOfficeTelephoneNo.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.regOfficeTelephoneNo.length.exceeded"); }
			 */
			if (!(form.getRegOfficeTelephoneNo() == null || form.getRegOfficeTelephoneNo().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getRegOfficeTelephoneNo().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterRegOfficeTelephoneNoError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
					DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
				}
			}
			if (!(form.getRegOfficeTelex() == null || form.getRegOfficeTelex().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator
						.checkNumber(form.getRegOfficeTelex().toString().trim(), false, 15, 999999999999999.D))) {
					errors.add("specialCharacterRegOfficeTelexError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "15"));
				}
			}
			/*
			 * if (form.getRegOfficeTelex().length() > 15) {
			 * errors.add("regOfficeTelexLengthError", new ActionMessage(
			 * "error.regOfficeTelex.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.regOfficeTelex.length.exceeded"); }
			 */

			if (form.getRegOfficeDUNSNo().length() > 10) {
				errors.add("regOfficeDUNSNoLengthError", new ActionMessage("error.regOfficeDUNSNo.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficeDUNSNo.length.exceeded");
			}
			if (form.getRegOfficeDUNSNo() != null && !("".equals(form.getRegOfficeDUNSNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRegOfficeDUNSNo());
				if (nameFlag == true)
					errors.add("specialCharacterRegOfficeDUNSNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getRegOfficePostCode().length() > 6) {
				errors.add("regOfficePostCodeLengthError",
						new ActionMessage("error.regOfficePostCode.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.regOfficePostCode.length.exceeded");
			}
			if (form.getRegOfficePostCode() != null && !("".equals(form.getRegOfficePostCode().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRegOfficePostCode());
				if (nameFlag == true)
					errors.add("specialCharacterRegOfficePostCodeError",
							new ActionMessage("error.string.invalidCharacter"));
				
				if (!(event.equals("WS_create_customer") || event.equalsIgnoreCase("WS_update_customer"))) {
				IPincodeMappingDao pincodeDao = (IPincodeMappingDao) BeanHouse.get("pincodeMappingDao");
				if (pincodeDao != null && form.getState() != null) {

					String stateCode = form.getState();
					String pincode = form.getRegOfficePostCode();
					if (pincode != null) {
						if (pincode.length() > 1 || pincode.length() == 1) {
							pincode = pincode.substring(0, 1);
							if(null != stateCode && !"".equals(stateCode)){
							if (stateCode != null && !pincodeDao.isPincodeMappingValid(pincode, stateCode)) {
								errors.add("regPostcodeError", new ActionMessage("error.string.invalidMapping"));
							}
							}
						} else {
							errors.add("regPostcodeError", new ActionMessage("error.string.invalidMapping"));
						}
					}

				}
				}
			}
			}
			/*
			 * if (form.getRegOfficeEmail().length() > 50) {
			 * errors.add("regOfficeEmailLengthError", new ActionMessage(
			 * "error.regOfficeEmail.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.regOfficeEmail.length.exceeded"); }
			 */
			if (!(form.getRegOfficeEmail() == null || form.getRegOfficeEmail().trim().equals(""))) {
				if (!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getRegOfficeEmail(), true))) {
					errors.add("regOfficeEmailError",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "50"));

				}
			}
			if (form.getPostcode()!=null && form.getPostcode().length() > 6) {
				errors.add("postCodeLengthError", new ActionMessage("error.postCode.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.postCode.length.exceeded");
			}
			/*
			 * if (form.getEmail().length() > 50) {
			 * errors.add("emailLengthError", new ActionMessage(
			 * "error.email.length.exceeded"));
			 * DefaultLogger.debug(ManualInputCustomerValidator.class,
			 * "error.email.length.exceeded"); }
			 */
			if (!(errorCode = Validator.checkNumber(form.getSuitAmount(), false, 0, 999999999999999999.99, 3, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("suitAmountLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999999999999999999.99"));
			}
			if (form.getSuitReferenceNo()!=null && form.getSuitReferenceNo().length() > 10) {
				errors.add("suitReferenceNoLengthError", new ActionMessage("error.suitReferenceNo.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.suitReferenceNo.length.exceeded");
			}
			if (form.getSuitReferenceNo() != null && !("".equals(form.getSuitReferenceNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSuitReferenceNo());
				if (nameFlag == true)
					errors.add("specialCharacterSuitReferenceNoError",
							new ActionMessage("error.string.invalidCharacter"));
			}
			if (form.getPan()!=null && form.getPan().length() > 10) {
				errors.add("panLengthError", new ActionMessage("error.pan.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class, "error.pan.length.exceeded");
			}

			/*if (form.getBankingMethod() != null && !("".equals(form.getBankingMethod().trim()))) {
				if (form.getBankingMethod().equals("MULTIPLE") || form.getBankingMethod().equals("CONSORTIUM")) {
					if (form.getNodalLead() == null || "".equals(form.getNodalLead().trim())) {
						errors.add("nodalLeadError", new ActionMessage("error.string.bankingmethod.empty"));
						DefaultLogger.debug(ManualInputCustomerValidator.class, "nodalLeadError");
					}
				}
			}*/

			// A Shiv 230811
			if (!AbstractCommonMapper.isEmptyOrNull(form.getCustomerRamID())) {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCustomerRamID());
				if (codeFlag == true) {
					errors.add("customerRamID", new ActionMessage("error.string.invalidCharacter"));
				}
			}

			if (form.getCustomerExtRating() != null && !"".equals(form.getCustomerExtRating().trim())) {
				if (form.getCustomerExtRating().length() > 1000) {
					errors.add("customerExtRatingLengthError",
							new ActionMessage("error.customerExtRating.length.exceeded"));
					DefaultLogger.debug(ManualInputCustomerValidator.class, "error.customerExtRating.length.exceeded");
				}
				boolean codeFlag = ASSTValidator.isValidGenericASST(form.getCustomerExtRating());
				if (codeFlag == true) {
					errors.add("customerExtRating", new ActionMessage("error.string.invalidCharacter"));
				}
			}

			if (!AbstractCommonMapper.isEmptyOrNull(form.getCustomerAprCode())) {
				boolean codeFlag = ASSTValidator.isValidCustomerAPRCode(form.getCustomerAprCode());
				if (codeFlag == true) {
					errors.add("customerAprCode", new ActionMessage("error.string.invalidCharacter"));
				}
			}

			// -------------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsNbfs())) {
				// if (!(errorCode = Validator.checkString(form.getIsNbfs(),
				// true, 1, 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isNbfs", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// if(form.getIsNbfs().equals("Yes")){
				// if (!(errorCode = Validator.checkString(form.getNbfsA(),
				// true, 1, 200)).equals(Validator.ERROR_NONE)) {
				// errors.add("nbfsA", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// if (!(errorCode = Validator.checkString(form.getNbfsB(),
				// true, 1, 200)).equals(Validator.ERROR_NONE)) {
				// errors.add("nbfsB", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// }
			} else {
				form.setIsNbfs("No");
			}
			// -----------
			// if (!(errorCode =
			// Validator.checkString(form.getMsmeClassification(), false, 1,
			// 200)).equals(Validator.ERROR_NONE)) {
			// errors.add("msmeClassification", new
			// ActionMessage("error.string.mandatory", "1"));
			// }
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsWeakerSection())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsWeakerSection(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isWeakerSection", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			}
			if ("Yes".equals(form.getIsWeakerSection())) {
				// if (!(errorCode =
				// Validator.checkString(form.getWeakerSection(), true, 1,
				// 200)).equals(Validator.ERROR_NONE)) {
				// errors.add("weakerSection", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			} else {
				form.setIsWeakerSection("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsKisanCreditCard())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsKisanCreditCard(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isKisanCreditCard", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			} else {
				form.setIsKisanCreditCard("No");
			}
			// -----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsMinorityCommunity())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsMinorityCommunity(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isMinorityCommunity", new
				// ActionMessage("error.string.mandatory", "1"));
				// }else
				// if(form.getIsMinorityCommunity().equals("Yes")){
				// if (!(errorCode =
				// Validator.checkString(form.getMinorityCommunity(), true, 1,
				// 200)).equals(Validator.ERROR_NONE)) {
				// errors.add("minorityCommunity", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// }
			} else {
				form.setIsMinorityCommunity("No");
			}
			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsCommoditiesExposer())) {
				if (form.getIsCommoditiesExposer().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getIsSensitive(), true, 1,
					// 200)).equals(Validator.ERROR_NONE)) {
					// errors.add("isSensitive", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else
					// if(form.getIsSensitive().equals("Yes")){
					// if (!(errorCode =
					// Validator.checkString(form.getCommoditiesName(), true, 1,
					// 200)).equals(Validator.ERROR_NONE)) {
					// errors.add("commoditiesName", new
					// ActionMessage("error.string.mandatory", "1"));
					// }
					// }
				}
			} else {
				form.setIsCommoditiesExposer("No");
			}

			// ----------
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getGrossInvsInPM())){
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getGrossInvsInPM()))
			// {

			// Phase 3 CR:comma separated
			String grossInvsInPM = form.getGrossInvsInPM();
			grossInvsInPM = UIUtil.removeComma(grossInvsInPM);

			if (!AbstractCommonMapper.isEmptyOrNull(grossInvsInPM)) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(grossInvsInPM)) {
					errors.add("grossInvsInPM", new ActionMessage("error.string.invalidCharacter"));
				}
				// }else{
				// double grossInvestInPMAmount =
				// Double.parseDouble(form.getGrossInvsInPM());
				// if(form.getMsmeClassification().equals("Super_Micro_Entp_Serv")){
				// if(grossInvestInPMAmount > 200000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Super_Micro_Entp_Serv.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Super_Micro_Entp_Manuf")){
				// if(grossInvestInPMAmount > 100000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Super_Micro_Entp_Manuf.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Small_Entp_Serv")){
				// if(grossInvestInPMAmount < 1000000 || grossInvestInPMAmount >
				// 20000000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Small_Entp_Serv.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Small_Entp_Manuf")){
				// if(grossInvestInPMAmount < 2500000 || grossInvestInPMAmount >
				// 50000000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Small_Entp_Manuf.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Micro_Entp_Serv")){
				// if(grossInvestInPMAmount < 200000 || grossInvestInPMAmount >
				// 1000000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Micro_Entp_Serv.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Mic_Entp_Manuf")){
				// if(grossInvestInPMAmount < 500000 || grossInvestInPMAmount >
				// 2500000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Mic_Entp_Manuf.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Medium_Entp_Serv")){
				// if(grossInvestInPMAmount < 20000000 || grossInvestInPMAmount
				// > 50000000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Medium_Entp_Serv.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Medium_Entp_Manuf")){
				// if(grossInvestInPMAmount < 50000000 || grossInvestInPMAmount
				// > 100000000){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Medium_Entp_Manuf.limit"));
				// }
				// }
				// if(form.getMsmeClassification().equals("Khadi_village_industries_sec")){
				// if(grossInvestInPMAmount < 0){
				// errors.add("msmeGrossInvsInPM", new
				// ActionMessage("error.Khadi_village_industries_sec.limit"));
				// }
				// }
			}
			// -----------
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getGrossInvsInEquip())){
			// boolean codeFlag =
			// ASSTValidator.isValidAlphaNumStringWithSpace(form.getGrossInvsInEquip());

			// Phase 3 CR:comma separated
			String grossInvsInEquip = form.getGrossInvsInEquip();
			grossInvsInEquip = UIUtil.removeComma(grossInvsInEquip);
			if (!AbstractCommonMapper.isEmptyOrNull(grossInvsInEquip)) {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(grossInvsInEquip);
				if (codeFlag == true) {
					errors.add("grossInvsInEquip", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ------------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getPsu())) {
				// if (!(errorCode = Validator.checkString(form.getPsu(), true,
				// 1, 20)).equals(Validator.ERROR_NONE)) {
				// errors.add("psu", new ActionMessage("error.string.mandatory",
				// "1"));
				// }else{
				// if(!AbstractCommonMapper.isEmptyOrNull(form.getPsu())) {
				// if(form.getPsu().equals("Central") ||
				// form.getPsu().equals("State") ){
				// if (!(errorCode =
				// Validator.checkNumber(form.getPsuPercentage(),true, 51,
				// IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE, 3,
				// locale)).equals(Validator.ERROR_NONE)) {
				// errors.add("psuPercentage", new
				// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
				// errorCode), "51", "100"));
				// }
				// }
				// }
				// }
			} else {
				form.setPsu("No");
			}
			if (!AbstractCommonMapper.isEmptyOrNull(form.getPsuPercentage())) {
				if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getPsuPercentage())) {
					errors.add("psuPercentage", new ActionMessage("error.string.invalidCharacter"));
				}
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getGovtUnderTaking())) {
				// if (!(errorCode =
				// Validator.checkString(form.getGovtUnderTaking(), true, 1,
				// 20)).equals(Validator.ERROR_NONE)) {
				// errors.add("govtUnderTaking", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			} else {
				form.setGovtUnderTaking("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsInfrastructureFinanace())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsInfrastructureFinanace(),
				// true, 1, 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isInfrastructureFinanace", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// if(form.getIsInfrastructureFinanace().equals("Yes")){
				// if (!(errorCode =
				// Validator.checkString(form.getInfrastructureFinanaceType(),
				// true, 1, 200)).equals(Validator.ERROR_NONE)) {
				// errors.add("infrastructureFinanaceType", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				// }
			} else {
				form.setIsInfrastructureFinanace("No");
			}

			// ---------

			// if (!(errorCode = Validator.checkString(form.getAuditType(),
			// true, 1, 20)).equals(Validator.ERROR_NONE)) {
			// errors.add("auditType", new
			// ActionMessage("error.string.mandatory", "1"));
			// }

			// ----------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsProjectFinance())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsProjectFinance(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isProjectFinance", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			} else {
				form.setIsProjectFinance("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSemsGuideApplicable())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsSemsGuideApplicable(), true,
				// 1, 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isSemsGuideApplicable", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			} else {
				form.setIsSemsGuideApplicable("No");
			}
			// ---------
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getIsFailIfcExcluList())){
			// if (!(errorCode =
			// Validator.checkString(form.getIsFailIfcExcluList(), true, 1,
			// 10)).equals(Validator.ERROR_NONE)) {
			// errors.add("isFailIfcExcluList", new
			// ActionMessage("error.string.mandatory", "1"));
			// }
			// }else{
			// form.setIsFailIfcExcluList("No");
			// }
			// ---------
			// if(!AbstractCommonMapper.isEmptyOrNull(form.getIsTufs())){
			// if (!(errorCode = Validator.checkString(form.getIsTufs(), true,
			// 1, 10)).equals(Validator.ERROR_NONE)) {
			// errors.add("isTufs", new ActionMessage("error.string.mandatory",
			// "1"));
			// }
			// }else{
			// form.setIsTufs("No");
			// }
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsRbiDefaulter())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsRbiDefaulter(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isRbiDefaulter", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsRbiDefaulter().equals("Yes")) {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getRbiDefaulter())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getRbiDefaulter());
						if (codeFlag == true) {
							errors.add("rbiDefaulter", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsRbiDefaulter("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsLitigation())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsLitigation(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isLitigation", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsLitigation().equals("Yes")) {

					if (form.getLitigationBy() == null || "".equals(form.getLitigationBy().trim())) {
						errors.add("litigationBy", new ActionMessage("error.string.mandatory"));

					} else {
						boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getLitigationBy());
						if (nameFlag == true)
							errors.add("litigationBy", new ActionMessage("error.string.invalidCharacter"));
					}

					if (form.getLitigationBy() != null && !("".equals(form.getLitigationBy().trim()))) {
						if (form.getLitigationBy().length() > 100) {
							errors.add("litigationBy", new ActionMessage("error.litigationBy.length.exceeded"));

						}
					}
					/*
					 * if (!(errorCode =
					 * Validator.checkString(form.getLitigationBy(), true, 1,
					 * 100)).equals(Validator.ERROR_NONE)) {
					 * errors.add("litigationBy", new
					 * ActionMessage("error.string.mandatory", "1")); }else {
					 * boolean codeFlag =
					 * ASSTValidator.isValidAlphaNumStringWithSpace(form.
					 * getLitigationBy()); if( codeFlag == true) {
					 * errors.add("litigationBy", new
					 * ActionMessage("error.string.invalidCharacter")); } }
					 */
				}
			} else {
				form.setIsLitigation("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsInterestOfBank())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsInterestOfBank(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isInterestOfBank", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsInterestOfBank().equals("Yes")) {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getInterestOfBank())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getInterestOfBank());
						if (codeFlag == true) {
							errors.add("interestOfBank", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsInterestOfBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsAdverseRemark())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsAdverseRemark(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isAdverseRemark", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsAdverseRemark().equals("Yes")) {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getAdverseRemark())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getAdverseRemark());
						if (codeFlag == true) {
							errors.add("adverseRemark", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsAdverseRemark("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getAuditType())) {
				// if (!(errorCode = Validator.checkString(form.getAuditType(),
				// true, 1, 25)).equals(Validator.ERROR_NONE)) {
				// errors.add("auditType", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
			}
			// -----------
			// if (!(errorCode =
			// Validator.checkNumber(form.getAvgAnnualTurnover(), true, 1,
			// IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3,
			// locale)).equals(Validator.ERROR_NONE)) {
			// errors.add("avgAnnualTurnover", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			// errorCode), "1",
			// IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			// }
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getAvgAnnualTurnover()))
			// {
			// errors.add("avgAnnualTurnover", new
			// ActionMessage("error.string.invalidCharacter"));
			// }
			// //------------
			// if (!(errorCode =
			// Validator.checkNumber(form.getIndustryExposer(), true, 1,
			// IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE,3,locale)).equals(Validator.ERROR_NONE))
			// {
			// errors.add("industryExposer", new
			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			// errorCode), "1", "100"));
			// }
			// if(ASSTValidator.isValidAlphaNumStringWithSpace(form.getIndustryExposer()))
			// {
			// errors.add("industryExposer", new
			// ActionMessage("error.string.invalidCharacter"));
			// }
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsDirecOtherBank())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsDirecOtherBank(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isDirecOtherBank", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsDirecOtherBank().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getDirecOtherBank(), true, 1,
					// 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("direcOtherBank", new
					// ActionMessage("error.string.mandatory", "1"));
					// }
					if (!AbstractCommonMapper.isEmptyOrNull(form.getDirecOtherBank())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDirecOtherBank());
						if (codeFlag == true) {
							errors.add("direcOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsDirecOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPartnerOtherBank())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsPartnerOtherBank(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isPartnerOtherBank", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsPartnerOtherBank().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getPartnerOtherBank(), true,
					// 1, 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("partnerOtherBank", new
					// ActionMessage("error.string.mandatory", "1"));
					// }
					if (!AbstractCommonMapper.isEmptyOrNull(form.getPartnerOtherBank())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getPartnerOtherBank());
						if (codeFlag == true) {
							errors.add("partnerOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsPartnerOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSubstantialOtherBank())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsSubstantialOtherBank(), true,
				// 1, 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isSubstantialOtherBank", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsSubstantialOtherBank().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getSubstantialOtherBank(),
					// true, 1, 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("substantialOtherBank", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getSubstantialOtherBank())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getSubstantialOtherBank());
						if (codeFlag == true) {
							errors.add("substantialOtherBank", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsSubstantialOtherBank("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsHdfcDirecRltv())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsHdfcDirecRltv(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isHdfcDirecRltv", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsHdfcDirecRltv().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getHdfcDirecRltv(), true, 1,
					// 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("hdfcDirecRltv", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getHdfcDirecRltv())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getHdfcDirecRltv());
						if (codeFlag == true) {
							errors.add("hdfcDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsHdfcDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsObkDirecRltv())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsObkDirecRltv(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isObkDirecRltv", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsObkDirecRltv().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getObkDirecRltv(), true, 1,
					// 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("obkDirecRltv", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getObkDirecRltv())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getObkDirecRltv());
						if (codeFlag == true) {
							errors.add("obkDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsObkDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsPartnerDirecRltv())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsPartnerDirecRltv(), true, 1,
				// 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isPartnerDirecRltv", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsPartnerDirecRltv().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getPartnerDirecRltv(), true,
					// 1, 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("partnerDirecRltv", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getPartnerDirecRltv())) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getPartnerDirecRltv());
						if (codeFlag == true) {
							errors.add("partnerDirecRltv", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsPartnerDirecRltv("No");
			}
			// ---------
			if (!AbstractCommonMapper.isEmptyOrNull(form.getIsSubstantialRltvHdfcOther())) {
				// if (!(errorCode =
				// Validator.checkString(form.getIsSubstantialRltvHdfcOther(),
				// true, 1, 10)).equals(Validator.ERROR_NONE)) {
				// errors.add("isSubstantialRltvHdfcOther", new
				// ActionMessage("error.string.mandatory", "1"));
				// }
				if (form.getIsSubstantialRltvHdfcOther().equals("Yes")) {
					// if (!(errorCode =
					// Validator.checkString(form.getSubstantialRltvHdfcOther(),
					// true, 1, 100)).equals(Validator.ERROR_NONE)) {
					// errors.add("substantialRltvHdfcOther", new
					// ActionMessage("error.string.mandatory", "1"));
					// }else {
					if (!AbstractCommonMapper.isEmptyOrNull(form.getSubstantialRltvHdfcOther())) {
						boolean codeFlag = ASSTValidator
								.isValidAlphaNumStringWithSpace(form.getSubstantialRltvHdfcOther());
						if (codeFlag == true) {
							errors.add("substantialRltvHdfcOther", new ActionMessage("error.string.invalidCharacter"));
						}
					}
				}
			} else {
				form.setIsSubstantialRltvHdfcOther("No");
			}

			if (form.getNameOfHoldingCompany() != null && !form.getNameOfHoldingCompany().trim().isEmpty()) {
				if (form.getNameOfHoldingCompany().trim().length() > 255) {
					errors.add("nameOfHoldingCompanyLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}
			
			if (form.getComment() != null && !form.getComment().trim().isEmpty()) {
				if (form.getComment().trim().length() > 250) {
					errors.add("commentLengthError", new ActionMessage("error.field.length.exceeded", "250"));
				}
			}
			
			if (form.getLandHolding() != null && !form.getLandHolding().trim().isEmpty()) {
				if (form.getLandHolding().trim().length() > 255) {
					errors.add("landHoldingLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}

			if (form.getStateImplications() != null && !form.getStateImplications().trim().isEmpty()) {
				if (form.getStateImplications().trim().length() > 300) {
					errors.add("stateImplicationsLengthError", new ActionMessage("error.field.length.exceeded", "300"));
				}
			}

			if (form.getComments() != null && !form.getComments().trim().isEmpty()) {
				if (form.getComments().trim().length() > 150) {
					errors.add("commentsLengthError", new ActionMessage("error.field.length.exceeded", "150"));
				}
			}

			if (form.getRejectHistoryReason() != null && !form.getRejectHistoryReason().trim().isEmpty()) {
				if (form.getRejectHistoryReason().trim().length() > 4000) {
					errors.add("rejectHistoryReasonLengthError",
							new ActionMessage("error.field.length.exceeded", "4000"));
				}
			}

			if (form.getHoldingCompnay() != null && !form.getHoldingCompnay().trim().isEmpty()) {
				if (form.getHoldingCompnay().trim().length() > 255) {
					errors.add("holdingCompanyLengthError", new ActionMessage("error.field.length.exceeded", "255"));
				}
			}

			if (form.getDetailsOfDefault() != null && !form.getDetailsOfDefault().trim().isEmpty()) {
				if (form.getDetailsOfDefault().trim().length() > 150) {
					errors.add("detailsOfDefaultLengthError", new ActionMessage("error.field.length.exceeded", "150"));
				}
			}

			if (form.getExtOfCompromiseAndWriteoff() != null
					&& !form.getExtOfCompromiseAndWriteoff().trim().isEmpty()) {
				if (form.getExtOfCompromiseAndWriteoff().trim().length() > 300) {
					errors.add("extOfCompromiseAndWriteoffLengthError",
							new ActionMessage("error.field.length.exceeded", "300"));
				}
			}

			if (form.getDetailsOfCleanCibil() != null && !form.getDetailsOfCleanCibil().trim().isEmpty()) {
				if (form.getDetailsOfCleanCibil().trim().length() > 150) {
					errors.add("detailsOfCleanCibilLengthError",
							new ActionMessage("error.field.length.exceeded", "150"));
				}
			}
			
			if (form.getNameOfDirectorsAndCompany() != null && !form.getNameOfDirectorsAndCompany().trim().isEmpty()) {
				if (form.getNameOfDirectorsAndCompany().trim().length() > 150) {
					errors.add("nameOfDirectorsAndCompanyLengthError",
							new ActionMessage("error.field.length.exceeded", "150"));
				}
			}
			
			if (!(errorCode = Validator.checkNumber(form.getLimitAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("limitAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_VALUE_6_2));

			} else if (form.getLimitAmount() != null && !form.getLimitAmount().trim().equals("")) {
				if (!AbstractCommonMapper.isEmptyOrNull(form.getLimitAmount())) {
					if (ASSTValidator.isValidAlphaNumStringWithSpace(form.getLimitAmount())) {
						errors.add("limitAmount", new ActionMessage("error.string.invalidCharacter"));
					}
				}
			}
			
			if (form.getDateOfCautionList() != null && !("".equals(form.getDateOfCautionList()))) {
				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				String applicationDate = generalParamEntry.getParamValue();
				Date appDate = new Date();
				if (applicationDate != null) {
					appDate = new Date(applicationDate);
				}

				if (DateUtil.convertDate(locale, form.getDateOfCautionList()).after(appDate)) {
					errors.add("dateOfCautionListError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}
			}

			if (form.getRbiDateOfCautionList() != null && !("".equals(form.getRbiDateOfCautionList()))) {
				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				String applicationDate = generalParamEntry.getParamValue();
				Date appDate = new Date();
				if (applicationDate != null) {
					appDate = new Date(applicationDate);
				}

				if (DateUtil.convertDate(locale, form.getRbiDateOfCautionList()).after(appDate)) {
					errors.add("rbiDateOfCautionListError", new ActionMessage(FeedConstants.ERROR_FUTURE_DATE));
				}
			}
			
			
		// ---------------
		// ---------------
		if (event.equals("edit_cri_facility") || event.equals("process_cri_facility")) {
			if (!(errorCode = Validator.checkString(form.getFacilityFor(), true, 1, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("facilityFor", new ActionMessage("error.string.mandatory"));
			}
			if (!(errorCode = Validator.checkString(form.getFacilityName(), true, 1, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("facilityName", new ActionMessage("error.string.mandatory"));
			}
			if (!(errorCode = Validator.checkNumber(form.getFacilityAmount(), true, 1,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("facilityAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}

			// if(
			// ASSTValidator.isValidAlphaNumStringWithSpace(form.getFacilityAmount()))
			// {

			// Phase 3 CR:comma separated
			String facilityAmount = form.getFacilityAmount();

			if (ASSTValidator.isValidAlphaNumStringWithSpace(UIUtil.removeComma(facilityAmount))) {
				errors.add("facilityAmount", new ActionMessage("error.string.invalidCharacter"));
			}
			if (!AbstractCommonMapper.isEmptyOrNull(form.getFacilityFor())) {
				if (form.getFacilityFor().equalsIgnoreCase("Capital Market Exposure")
						|| form.getFacilityFor().equalsIgnoreCase("Priority/Non priority Sector")
						|| form.getFacilityFor().equalsIgnoreCase("Real Estate Exposure")) {
					if (!(errorCode = Validator.checkString(form.getSerialNo(), true, 1, 150))
							.equals(Validator.ERROR_NONE)) {
						errors.add("serialNo", new ActionMessage("error.string.mandatory"));
					}
				}
			}
		}

		// Added By Dayananda Laishram to make PAN field mandatory || Starts

		// Added panflag so that pan validation is not checked when we add or
		// edit System details--- UMA
		Boolean panflag = true;
		if ("save_other_system".equals(event) || "save_edited_system".equals(event)
				|| "save_other_system_in_edit".equals(event) || "save_edited_system_in_edit".equals(event)
				|| "save_edited_system_in_resubmit".equals(event) || "save_other_system_in_resubmit".equals(event)

				// Start:Uma : Valid Rating CR
				|| event.equals("save_other_system_partymaker") || event.equals("save_other_system_in_edit_partymaker")
				|| event.equals("save_other_system_in_resubmit_partymaker")
				|| event.equals("save_edited_system_partymaker")
				|| event.equals("save_edited_system_in_edit_partymaker")
				|| event.equals("save_edited_system_in_resubmit_partymaker")
		// End:Uma : Valid Rating CR
		) {
			panflag = false;
		}
		if (panflag) {
			if (null != form.getEntity() && null != form.getCountry()) {
				/*
				 * if("10".equalsIgnoreCase(form.getCountry())) { if
				 * (form.getPan() == null || "".equals(form.getPan().trim())) {
				 * errors.add("invalidPanError", new
				 * ActionMessage("error.string.mandatory"));
				 * DefaultLogger.debug(ManualInputCustomerValidator.class,
				 * "invalidPanError"); } } else
				 * if(!"SOVEREIGN".equalsIgnoreCase(form.getEntity()) &&
				 * !"BANK".equalsIgnoreCase(form.getEntity()))
				 * 
				 * { if (form.getPan() == null ||
				 * "".equals(form.getPan().trim())) {
				 * errors.add("invalidPanError", new
				 * ActionMessage("error.string.mandatory"));
				 * DefaultLogger.debug(ManualInputCustomerValidator.class,
				 * "invalidPanError"); } }
				 */

				// Form 60-61 FLAG condition added by Ankit - 18/FEB/2016
				if (!"Y".equalsIgnoreCase(form.getForm6061Checked()) && form.getEvent() != null
						&& !"save_director".equalsIgnoreCase(form.getEvent())) {
					if ("10".equalsIgnoreCase(form.getCountry()) && ((!"SOVEREIGN".equalsIgnoreCase(form.getEntity()))
							&& (!"BANK".equalsIgnoreCase(form.getEntity()))))

					{
						if (form.getPan() == null || "".equals(form.getPan().trim())) {
							errors.add("invalidPanError", new ActionMessage("error.string.mandatory"));
							DefaultLogger.debug(ManualInputCustomerValidator.class, "invalidPanError");
						}
					}
				}
	
	     	}
		
		}
		// Added By Dayananda Laishram to make PAN field mendatory || Ends
		//Santosh LEI CR
			
//		if (form.getLeiCode() != null && !("".equals(form.getLeiCode().trim()))) {
//		//	boolean flag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getLeiCode());
//			boolean flag = ASSTValidator.isValidLeiCode(form.getLeiCode());
//			if (!flag)
//				errors.add("invalidLeiCodeError",new ActionMessage("error.string.invalidLEIFormat"));
//			else if(form.getLeiCode().trim().length()!=20)
//				errors.add("invalidLeiCodeError",new ActionMessage("error.string.leiCode.length"));
//			if(form.getLeiExpDate()==null || form.getLeiExpDate().toString().trim().isEmpty()) {
//				errors.add("invalidLeiExpDate",new ActionMessage("error.string.leiExpDate.mandatory"));
//			}
//		}
		
		if (form.getLeiExpDate() != null && !("".equals(form.getLeiExpDate().trim()))) {
			if(form.getLeiCode()==null || form.getLeiCode().toString().trim().isEmpty()) {
				errors.add("invalidLeiCodeError",new ActionMessage("error.string.leiCode.mandatory"));
			}
			
			ILeiDateValidationDao leiDateValidationDao = (ILeiDateValidationDao) BeanHouse.get("leiDateValidationDao");
			Integer leiDateValidationPeriod = leiDateValidationDao.getLeiDateValidationPeriod(form.getCifId());
			
			if(leiDateValidationPeriod == 0) {
				IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
				leiDateValidationPeriod = Integer.parseInt(generalParamDao.getGenralParamValues("LEI_Period"));
			}		
			int monthsCount = 0;
			try{
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
				  Date date = sdf.parse(form.getLeiExpDate());
				  monthsCount = - calculateMonthsFromDate(date);	
			}catch(Exception e){
				System.out.println("Error over here "+e);
				e.printStackTrace();
			}
			

			if(form.getLeiExpDate() != null && leiDateValidationPeriod < monthsCount) {
				errors.add("invalidLeiExpDate",new ActionMessage("error.string.leiExpDate.exceed",leiDateValidationPeriod));
			}
		}
		//End santosh
		
		coBorrowerDetailsValidator(form, locale, errors);

		return errors;

	}
	
	private static void coBorrowerDetailsValidator(ManualInputCustomerInfoForm form, Locale locale, ActionErrors errors ) {
		boolean isCoBorrowerEmpty = false;
		if("WS_create_customer".equals(form.getEvent()) ){
			isCoBorrowerEmpty = CollectionUtils.isEmpty(form.getCoBorrowerDetails());
		}else {
			isCoBorrowerEmpty = StringUtils.isBlank(form.getCoBorrowerLiabIdList());
		}
		//if(ICMSConstant.YES.equals(form.getCoBorrowerDetailsInd())) {
	 if(ICMSConstant.YES.equals(form.getCoBorrowerDetailsInd()) && !"WS_update_customer".equals(form.getEvent())) {

			if(isCoBorrowerEmpty) {
				errors.add("coBorrowerDetailsIndError", new ActionMessage("error.coborrower.minimum.record"));
				return;
			}
			
			if("WS_create_customer".equals(form.getEvent()) ){
				
				StringBuilder liabIds = new StringBuilder();
				for(CoBorrowerDetailsForm coBorrowerForm : form.getCoBorrowerDetails()) {
					String coBorrowerLiabIds = liabIds.toString();
					coBorrowerForm.setCoBorrowerLiabIdList(coBorrowerLiabIds);
					CoBorrowerDetailsValidator.validateInput(coBorrowerForm, errors, locale);
					liabIds.append(coBorrowerForm.getCoBorrowerLiabId()).append(",");
				}
				if(form.getCoBorrowerDetails().size()>5) {
					errors.add("coBorrowerLiabIdError", new ActionMessage("error.coborrower.limit.exceeded", 5));
				}
			}
		}
	}
	
	 private static int calculateMonthsFromDate(Date date){
         int numberOfMonths = 0;
         Calendar currentDate = Calendar.getInstance();
         int year = 0; 
         int months = 0;
          
         if(date != null)
         {
               Calendar dateOfBirth = Calendar.getInstance();
               dateOfBirth.setTime(date);
               year = currentDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
               months = (currentDate.get(Calendar.MONTH)+1) - ((dateOfBirth.get(Calendar.MONTH) +1) );
                     if(currentDate.get(Calendar.DAY_OF_MONTH) < (dateOfBirth.get(Calendar.DAY_OF_MONTH)) )     
                     {
                           months--;
                     }
               numberOfMonths = months + (year * 12);
         }

         return numberOfMonths;

         }
	 
	 public static double validateScmReleaseAmount(String s){
			double releaseAmount = 0.0;
			if(null!=s && !s.isEmpty()){
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				try {
					releaseAmount = (double) proxy.getReleaseAmountForParty(s);
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
				}
			
		    return releaseAmount;   
		}

	private static List fetchRMData(String rmEmpCode){
		List data = new ArrayList();	
		
		String sql="select RM_MGR_NAME,(select reg.REGION_NAME from cms_region reg where reg.id= REGION) as region from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' and STATUS = 'ACTIVE'";
		System.out.println("ManualInputCustomerValidator.java=>List fetchRMData(String rmEmpCode)=>sql=>"+sql);
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	               
	               data.add(rs.getString("RM_MGR_NAME"));
	               data.add(rs.getString("REGION"));
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		return data;
	}
}
