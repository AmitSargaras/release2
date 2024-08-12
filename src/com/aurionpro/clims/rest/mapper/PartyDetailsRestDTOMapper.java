package com.aurionpro.clims.rest.mapper;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.aurionpro.clims.rest.dto.PartyVendorDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartycriFacilityDetailsRestRequestDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.aurionpro.clims.rest.dto.CoBorrowerDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyBankingMethodDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyCoBorrowerDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyDetailsRestBodyRequestDTO;
import com.aurionpro.clims.rest.dto.PartyDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartyDirectorDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.PartySystemDetailsRestRequestDTO;
import com.aurionpro.clims.rest.dto.UdfRestRequestDTO;
import com.aurionpro.clims.rest.mapper.UdfDetailsRestDTOMapper;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomerUdf;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBCriInfo;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.ws.dto.CoBorrowerDetailsRequestDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.customer.CoBorrowerDetailsForm;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.customer.bus.OBVendor;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBCriFac;
import com.integrosys.cms.app.customer.bus.ICriFac;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.aurionpro.clims.rest.dto.PartySubLineDetailsRestRequestDTO;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.partygroup.proxy.PartyGroupProxyManagerImpl;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;

public class PartyDetailsRestDTOMapper {

	public static String DEFAULT_VALUE_FOR_CRI_INFO = "No";
	public static String DEFAULT_STATUS = "ACTIVE";
	public static String DEFAULT_CYCLE = "LAD";
	public static String DEFAULT_WILLFUL_STATUS = "0";
	public static String DEFAULT_SUBLINE = "CLOSE";
	public static String DEFAULT_UDF_VALUE = "REGULAR";
	public static String DEFAULT_NON_FUNDED_SHARE_PERCENT = "0";

	UdfDetailsRestDTOMapper udfDetailsRestDTOMapper = new UdfDetailsRestDTOMapper();

	public void setUdfDetailsRestDTOMapper(UdfDetailsRestDTOMapper udfDetailsRestDTOMapper) {
		this.udfDetailsRestDTOMapper = udfDetailsRestDTOMapper;
	}

	public PartyDetailsRestRequestDTO getRequestDTOWithActualValuesRest(PartyDetailsRestRequestDTO requestDTO) {

		List<PartyDetailsRestBodyRequestDTO> partyBodyReqList = new LinkedList<PartyDetailsRestBodyRequestDTO>();
		PartyDetailsRestBodyRequestDTO reqObj = requestDTO.getBodyDetails().get(0);
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);
		MasterAccessUtility masterObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		ActionErrors errors = new ActionErrors();

		if (null != requestDTO.getBodyDetails().get(0).getClimsPartyId()
				&& !requestDTO.getBodyDetails().get(0).getClimsPartyId().trim().isEmpty()) {
			reqObj.setClimsPartyId(requestDTO.getBodyDetails().get(0).getClimsPartyId().trim());
		} else {
			reqObj.setClimsPartyId("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getEvent()
				&& !requestDTO.getBodyDetails().get(0).getEvent().trim().isEmpty()) {
			reqObj.setEvent(requestDTO.getBodyDetails().get(0).getEvent().trim());
		} else {
			reqObj.setEvent("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getBusinessGroup()
				&& !requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().isEmpty()) {			
			if(!(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().length()>19)) {
				try {
					Object obj = masterObj.getObjectforMaster("actualOBPartyGroup",
							new Long(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim()));
					if (obj != null) {
						reqObj.setBusinessGroup(Long.toString(((IPartyGroup) obj).getId()));
					} else {
						errors.add("businessGroup", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("businessGroup", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("businessGroup", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setBusinessGroup("");
		}

		if (requestDTO.getBodyDetails().get(0).getMainBranch() != null
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()) {
			String bankCode = requestDTO.getBodyDetails().get(0).getMainBranch().trim().split("-")[0];
			Object obj = masterObj.getObjectByEntityNameAndBranchCode("actualSystemBankBranch", bankCode.trim(),
					"mainBranch", errors);
			if (!(obj instanceof ActionErrors)) {
				reqObj.setMainBranch(((ISystemBankBranch) obj).getSystemBankBranchCode() + "-"
						+ ((ISystemBankBranch) obj).getSystemBankBranchName());
			}
			reqObj.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch().trim());
		} else {
			reqObj.setMainBranch("");
		}
		
		if (null != requestDTO.getBodyDetails().get(0).getCinLlpin()
				&& !requestDTO.getBodyDetails().get(0).getCinLlpin().trim().isEmpty()) {
			reqObj.setCinLlpin(requestDTO.getBodyDetails().get(0).getCinLlpin().trim());
		} else {
			reqObj.setCinLlpin("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getDateOfIncorporation()
				&& !requestDTO.getBodyDetails().get(0).getDateOfIncorporation().trim().isEmpty()) {
			try {
				Date dt = cautionDateFormat.parse(requestDTO.getBodyDetails().get(0).getDateOfIncorporation().trim());
				reqObj.setDateOfIncorporation(cautionDateFormat.format(dt));
			} catch (ParseException e) {
				errors.add("dateOfIncorporation", new ActionMessage("error.party.date.format"));
			}
		} else {
			errors.add("dateOfIncorporation", new ActionMessage("error.string.mandatory"));
		}

		if (null != requestDTO.getBodyDetails().get(0).getCycle()
				&& !requestDTO.getBodyDetails().get(0).getCycle().trim().isEmpty()) {
			reqObj.setCycle(requestDTO.getBodyDetails().get(0).getCycle().trim());
		} else {
			reqObj.setCycle(DEFAULT_CYCLE);
		}
		if (null != requestDTO.getBodyDetails().get(0).getForm6061()
				&& !requestDTO.getBodyDetails().get(0).getForm6061().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getForm6061().trim().equalsIgnoreCase("Y")
					|| requestDTO.getBodyDetails().get(0).getForm6061().trim().equalsIgnoreCase("N")) {
				reqObj.setForm6061(requestDTO.getBodyDetails().get(0).getForm6061().trim());
			} else {
				errors.add("form6061", new ActionMessage("error.invalid.field.value"));
			}
		} else {
			reqObj.setForm6061("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getAadharNumber()
				&& !requestDTO.getBodyDetails().get(0).getAadharNumber().trim().isEmpty()) {
			reqObj.setAadharNumber(requestDTO.getBodyDetails().get(0).getAadharNumber().trim());
		} else {
			reqObj.setAadharNumber("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan()
				&& !requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("EXCEPTIONAL_CASES_ID".equals(codeEntry.getCategoryCode())) {
							reqObj.setExceptionalCasesSpan((codeEntry).getEntryCode());
						} else {
							errors.add("exceptionalCasesSpan", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("exceptionalCasesSpan", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("exceptionalCasesSpan", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("exceptionalCasesSpan", new ActionMessage("error.string.field.length.exceeded"));
			}
			// reqObj.setExceptionalCasesSpan(requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim());
		} else {
			reqObj.setExceptionalCasesSpan("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getiFSCCode()
				&& !requestDTO.getBodyDetails().get(0).getiFSCCode().trim().isEmpty()) {
			reqObj.setiFSCCode(requestDTO.getBodyDetails().get(0).getiFSCCode().trim());
		} else {
			reqObj.setiFSCCode("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getBankName()
				&& !requestDTO.getBodyDetails().get(0).getBankName().trim().isEmpty()) {
			reqObj.setBankName(requestDTO.getBodyDetails().get(0).getBankName().trim());
		} else {
			reqObj.setBankName("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getBankBranchName()
				&& !requestDTO.getBodyDetails().get(0).getBankBranchName().trim().isEmpty()) {
			reqObj.setBankBranchName(requestDTO.getBodyDetails().get(0).getBankBranchName().trim());
		} else {
			reqObj.setBankBranchName("");
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerFyClouser() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim().length()>19)) {			
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("FINANCIAL_CLOSUER".equals(codeEntry.getCategoryCode())) {
							reqObj.setCustomerFyClouser((codeEntry).getEntryCode());
						} else {
							errors.add("customerFyClouser", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("customerFyClouser", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("customerFyClouser", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("customerFyClouser", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("customerFyClouser", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getSecondYear() != null
				&& !requestDTO.getBodyDetails().get(0).getSecondYear().trim().isEmpty()) {
			try {
				Criterion c1 = Property.forName("entryName")
						.eq(requestDTO.getBodyDetails().get(0).getSecondYear().trim());
				Criterion c2 = Property.forName("categoryCode").eq("CRI_TENURE_YEAR");
				DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
				List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
				if (objectList != null && objectList.size() > 0) {
					String entryCode = ((ICommonCodeEntry) objectList.get(0)).getEntryCode();
					reqObj.setSecondYear(entryCode);
				} else {
					errors.add("secondYear", new ActionMessage("error.invalid.field.value"));
				}
			} catch (Exception e) {
				errors.add("secondYear", new ActionMessage("error.invalid.field.value"));
			}
		} else {
			reqObj.setSecondYear("");
		}

		if (requestDTO.getBodyDetails().get(0).getThirdYear() != null
				&& !requestDTO.getBodyDetails().get(0).getThirdYear().trim().isEmpty()) {
			try {
				Criterion c1 = Property.forName("entryName")
						.eq(requestDTO.getBodyDetails().get(0).getThirdYear().trim());
				Criterion c2 = Property.forName("categoryCode").eq("CRI_TENURE_YEAR");
				DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
				List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
				if (objectList != null && objectList.size() > 0) {
					String entryCode = ((ICommonCodeEntry) objectList.get(0)).getEntryCode();
					reqObj.setThirdYear(entryCode);
				} else {
					errors.add("thirdYear", new ActionMessage("error.invalid.field.value"));
				}
			} catch (Exception e) {
				errors.add("thirdYear", new ActionMessage("error.invalid.field.value"));
			}
		} else {
			reqObj.setThirdYear("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSecondYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim().isEmpty()) {
			reqObj.setSecondYearTurnover(requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim());
		} else {
			reqObj.setSecondYearTurnover("");
		}

		if (requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr() != null
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim().isEmpty()) {
			Object obj = masterObj.getObjectByEntityNameAndCountryISOCode("actualForexFeedEntry",
					requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim(), "secondYearTurnoverCurr",
					errors);
			if (!(obj instanceof ActionErrors)) {
				reqObj.setSecondYearTurnoverCurr(((IForexFeedEntry) obj).getCurrencyIsoCode());
			}
		} else {
			reqObj.setSecondYearTurnoverCurr("INR");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim().isEmpty()) {
			reqObj.setThirdYearTurnover(requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim());
		} else {
			reqObj.setThirdYearTurnover("");
		}

		if (requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr() != null
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim().isEmpty()) {
			Object obj = masterObj.getObjectByEntityNameAndCountryISOCode("actualForexFeedEntry",
					requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim(), "thirdYearTurnoverCurr",
					errors);
			if (!(obj instanceof ActionErrors)) {
				reqObj.setThirdYearTurnoverCurr(((IForexFeedEntry) obj).getCurrencyIsoCode());
			}
		} else {
			reqObj.setThirdYearTurnoverCurr("INR");
		}

		if (requestDTO.getBodyDetails().get(0).getSubLine() != null
				&& !requestDTO.getBodyDetails().get(0).getSubLine().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getSubLine().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getSubLine().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				if (requestDTO.getBodyDetails().get(0).getSubLine().trim().equalsIgnoreCase("Yes")) {
					reqObj.setSubLine("OPEN");
					List<PartySubLineDetailsRestRequestDTO> subLineList = new LinkedList<PartySubLineDetailsRestRequestDTO>();
					if (requestDTO.getBodyDetails().get(0).getSubLineDetailsList() != null
							&& !requestDTO.getBodyDetails().get(0).getSubLineDetailsList().isEmpty()) {
						for (PartySubLineDetailsRestRequestDTO subLineDetReqDTO : requestDTO.getBodyDetails().get(0)
								.getSubLineDetailsList()) {
							PartySubLineDetailsRestRequestDTO partySubLineDTO = new PartySubLineDetailsRestRequestDTO();
							if (subLineDetReqDTO.getSubLinePartyId() != null
									&& !subLineDetReqDTO.getSubLinePartyId().trim().isEmpty()) {
								long id = fetchSubLineDataUsingPartyId(subLineDetReqDTO.getSubLinePartyId().trim());
								if (id == 0) {
									errors.add("subLinePartyId", new ActionMessage("error.string.party.not.found",
											subLineDetReqDTO.getSubLinePartyId().trim()));
								} else {
									partySubLineDTO.setSubLinePartyId(Long.toString(id));
								}
							} else {
								errors.add("subLinePartyId", new ActionMessage("error.string.mandatory"));
							}
							if (subLineDetReqDTO.getGuaranteedAmt() != null
									&& !subLineDetReqDTO.getGuaranteedAmt().trim().isEmpty()) {
								if (ASSTValidator.isNumeric(subLineDetReqDTO.getGuaranteedAmt().trim()))
									partySubLineDTO.setGuaranteedAmt(subLineDetReqDTO.getGuaranteedAmt().trim());
								else {
									errors.add("guaranteedAmt", new ActionMessage("error.amount.numbers.format"));
								}
							} else {
								partySubLineDTO.setGuaranteedAmt("0");
							}
							subLineList.add(partySubLineDTO);
						}
					} else {
						errors.add("subLineDetailsList", new ActionMessage("error.string.list.empty"));
					}
					reqObj.setSubLineDetailsList(subLineList);
				} else {
					reqObj.setSubLine(DEFAULT_SUBLINE);
				}
			} else {
				errors.add("subLine", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setSubLine(DEFAULT_SUBLINE);
		}

		if (requestDTO.getBodyDetails().get(0).getIsSPVFunding() != null
				&& !requestDTO.getBodyDetails().get(0).getIsSPVFunding().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsSPVFunding().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getIsSPVFunding().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsSPVFunding(requestDTO.getBodyDetails().get(0).getIsSPVFunding().trim());
			} else {
				errors.add("isSPVFunding", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsSPVFunding(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIndirectCountryRiskExposure(
						requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim());
			} else {
				errors.add("indirectCountryRiskExposure", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure())) {
				reqObj.setIndirectCountryRiskExposure("Yes");
				if (requestDTO.getBodyDetails().get(0).getCriCountryName() != null
						&& !requestDTO.getBodyDetails().get(0).getCriCountryName().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getCriCountryName().trim().length()>19)) {		
						Object obj = masterObj.getObjectByEntityNameAndId("actualCountry",
								requestDTO.getBodyDetails().get(0).getCriCountryName().trim(), "criCountryName", errors);
						if (!(obj instanceof ActionErrors)) {
							reqObj.setCriCountryName(Long.toString(((ICountry) obj).getIdCountry()));
						}
					}else {
						errors.add("criCountryName", new ActionMessage("error.string.field.length.exceeded"));
					}
				}
				if (requestDTO.getBodyDetails().get(0).getSalesPercentage() != null
						&& !requestDTO.getBodyDetails().get(0).getSalesPercentage().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getSalesPercentage().trim()))
						reqObj.setSalesPercentage(requestDTO.getBodyDetails().get(0).getSalesPercentage().trim());
					else {
						errors.add("salesPercentage", new ActionMessage("error.amount.numbers.format"));
					}
				} else {
					errors.add("salesPercentage", new ActionMessage("error.string.mandatory"));
				}
			}
		} else {
			reqObj.setIndirectCountryRiskExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsCGTMSE() != null
				&& !requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getIsCGTMSE().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsCGTMSE(requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim());
			} else {
				errors.add("isCGTMSE", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsCGTMSE(DEFAULT_VALUE_FOR_CRI_INFO);
		}
		if (requestDTO.getBodyDetails().get(0).getIsIPRE() != null
				&& !requestDTO.getBodyDetails().get(0).getIsIPRE().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsIPRE().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getIsIPRE().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsIPRE(requestDTO.getBodyDetails().get(0).getIsIPRE().trim());
			} else {
				errors.add("isIPRE", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsIPRE(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getFinanceForAccquisition() != null
				&& !requestDTO.getBodyDetails().get(0).getFinanceForAccquisition().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getFinanceForAccquisition().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getFinanceForAccquisition().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setFinanceForAccquisition(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition());
			} else {
				errors.add("financeForAccquisition", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition())) {
				reqObj.setFinanceForAccquisition("Yes");
				if (requestDTO.getBodyDetails().get(0).getFacilityApproved() != null
						&& !requestDTO.getBodyDetails().get(0).getFacilityApproved().trim().isEmpty()) {
					Object obj = masterObj.getObjByEntityNameAndFacilityCode("actualFacilityNewMaster",
							requestDTO.getBodyDetails().get(0).getFacilityApproved().trim(), "facilityApproved",
							errors);
					if (!(obj instanceof ActionErrors)) {
						reqObj.setFacilityApproved(((IFacilityNewMaster) obj).getNewFacilityCode());

					} else {
						errors.add("facilityApproved", new ActionMessage("error.invalid.field.value"));
					}
					if (requestDTO.getBodyDetails().get(0).getFacilityAmount() != null
							&& !requestDTO.getBodyDetails().get(0).getFacilityAmount().trim().isEmpty()) {
						if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getFacilityAmount()))
							reqObj.setFacilityAmount(requestDTO.getBodyDetails().get(0).getFacilityAmount().trim());
						else {
							errors.add("facilityAmount", new ActionMessage("error.amount.numbers.format"));
						}
					} else {
						reqObj.setFacilityAmount("");
					}
				}
				if (requestDTO.getBodyDetails().get(0).getSecurityName() != null
						&& !requestDTO.getBodyDetails().get(0).getSecurityName().trim().isEmpty()) {
					Object obj = masterObj.getObjByEntityNameAndCollateralCode("actualCollateralNewMaster",
							requestDTO.getBodyDetails().get(0).getSecurityName().trim(), "securityName", errors);
					if (!(obj instanceof ActionErrors)) {
						reqObj.setSecurityName(((ICollateralNewMaster) obj).getNewCollateralCode());
					}
				} else {
					reqObj.setSecurityName("");
				}

				if (requestDTO.getBodyDetails().get(0).getSecurityType() != null
						&& !requestDTO.getBodyDetails().get(0).getSecurityType().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getSecurityType().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getSecurityType().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("31".equals(codeEntry.getCategoryCode())) {
									ICollateralDAO collateralDao = CollateralDAOFactory.getDAO();
									List securityCodeAndTypeList=new ArrayList();
									securityCodeAndTypeList=collateralDao.getSeurityCodeAndType(requestDTO.getBodyDetails().get(0).getSecurityName().trim());
									if(securityCodeAndTypeList != null) {
										boolean aa = true;
										for(int i=0;i<securityCodeAndTypeList.size();i++) {
											LabelValueBean lvBean=(LabelValueBean)securityCodeAndTypeList.get(i);
											if(lvBean.getValue().equals(codeEntry.getEntryCode())) {
												reqObj.setSecurityType((codeEntry).getEntryCode());	
												aa = false;
											}
										}
										if(aa) {
											errors.add("securityType", new ActionMessage("This securityType is not mapped to given securityName"));	
										}	
									}else {
										errors.add("securityType", new ActionMessage("error.invalid.field.value"));
									}
								} else {
									errors.add("securityType", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("securityType", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("securityType", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("securityType", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					reqObj.setSecurityType("");
				}
				if (requestDTO.getBodyDetails().get(0).getSecurityValue() != null
						&& !requestDTO.getBodyDetails().get(0).getSecurityValue().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getSecurityValue().trim()))
						reqObj.setSecurityValue(requestDTO.getBodyDetails().get(0).getSecurityValue().trim());
					else {
						errors.add("securityValue", new ActionMessage("error.amount.numbers.format"));
					}
				} else {
					reqObj.setSecurityValue("");
				}
			}
		} else {
			reqObj.setFinanceForAccquisition(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions() != null
				&& !requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIfBreachWithPrescriptions(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions());
			} else {
				errors.add("ifBreachWithPrescriptions", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions())) {
				reqObj.setComments(requestDTO.getBodyDetails().get(0).getComments().trim());
			} else {
				reqObj.setComments("");
			}
		} else {
			reqObj.setIfBreachWithPrescriptions(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCompanyType() != null
				&& !requestDTO.getBodyDetails().get(0).getCompanyType().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCompanyType().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getCompanyType().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setCompanyType(requestDTO.getBodyDetails().get(0).getCompanyType());
			} else {
				errors.add("companyType", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCompanyType())) {
				reqObj.setNameOfHoldingCompany(requestDTO.getBodyDetails().get(0).getNameOfHoldingCompany().trim());
				if (requestDTO.getBodyDetails().get(0).getType() != null
						&& !requestDTO.getBodyDetails().get(0).getType().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getType().trim().length()>19)) {
					try {
						Object obj = masterObj.getMasterData("entryCode",
								Long.parseLong(requestDTO.getBodyDetails().get(0).getType().trim()));
						if (obj != null) {
							ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
							if ("TYPE_OF_COMPANY".equals(codeEntry.getCategoryCode())) {
								reqObj.setType((codeEntry).getEntryCode());
							} else {
								errors.add("type", new ActionMessage("error.party.type"));
							}
						} else {
							errors.add("type", new ActionMessage("error.party.type"));
						}
					} catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
						errors.add("type", new ActionMessage("error.party.type"));
					}
					}else {
						errors.add("type", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					reqObj.setType("");
				}
			} else {
				reqObj.setNameOfHoldingCompany("");
				reqObj.setType("");
			}
		} else {
			reqObj.setCompanyType(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCategoryOfFarmer() != null
				&& !requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("CATEGORY_OF_FARMER".equals(codeEntry.getCategoryCode())) {
							reqObj.setCategoryOfFarmer((codeEntry).getEntryCode());
						} else {
							errors.add("categoryOfFarmer", new ActionMessage("error.party.categoryOfFarmer"));
						}
					} else {
						errors.add("categoryOfFarmer", new ActionMessage("error.party.categoryOfFarmer"));
					}

				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("categoryOfFarmer", new ActionMessage("error.party.categoryOfFarmer"));
				}
				if (requestDTO.getBodyDetails().get(0).getLandHolding() != null
						&& !requestDTO.getBodyDetails().get(0).getLandHolding().trim().isEmpty()) {
					reqObj.setLandHolding(requestDTO.getBodyDetails().get(0).getLandHolding().trim());
				}
			}else {
				errors.add("categoryOfFarmer", new ActionMessage("error.string.field.length.exceeded"));
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCountryOfGuarantor() != null
				&& !requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCountryOfGuarantor() != null
					&& !requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim().isEmpty()) {
				if(!(requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim().length()>19)) {
					Object obj = masterObj.getObjectByEntityNameAndId("actualCountry",
							requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim(), "countryOfGuarantor",
							errors);
					if (!(obj instanceof ActionErrors)) {
						reqObj.setCountryOfGuarantor(Long.toString(((ICountry) obj).getIdCountry()));
					}
				}else {
					errors.add("countryOfGuarantor", new ActionMessage("error.string.field.length.exceeded"));
				}
			}
		}

		if (requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim().isEmpty()) {

			if (requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsAffordableHousingFinance(
						requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim());
			} else {
				errors.add("isAffordableHousingFinance", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsAffordableHousingFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsInRestrictedList() != null
				&& !requestDTO.getBodyDetails().get(0).getIsInRestrictedList().trim().isEmpty()) {

			if (requestDTO.getBodyDetails().get(0).getIsInRestrictedList().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsInRestrictedList().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsInRestrictedList(requestDTO.getBodyDetails().get(0).getIsInRestrictedList());
			} else {
				errors.add("isInRestrictedList", new ActionMessage("error.string.cri.default.values"));
			}

			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsInRestrictedList())) {

				if (requestDTO.getBodyDetails().get(0).getRestrictedFinancing() != null
						&& !requestDTO.getBodyDetails().get(0).getRestrictedFinancing().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getRestrictedFinancing().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getRestrictedFinancing().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("RESTRICTED_FINANCING".equals(codeEntry.getCategoryCode())) {
									reqObj.setRestrictedFinancing((codeEntry).getEntryCode());
								} else {
									errors.add("restrictedFinancing", new ActionMessage("error.party.restrictedFinancing"));
								}
							} else {
								errors.add("restrictedFinancing", new ActionMessage("error.party.restrictedFinancing"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("restrictedFinancing", new ActionMessage("error.party.restrictedFinancing"));
						}
					}else {
						errors.add("restrictedFinancing", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("restrictedFinancing", new ActionMessage("error.string.mandatory"));
				}
			}
		} else {
			reqObj.setIsInRestrictedList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getRestrictedIndustries() != null
				&& !requestDTO.getBodyDetails().get(0).getRestrictedIndustries().trim().isEmpty()) {

			if (requestDTO.getBodyDetails().get(0).getRestrictedIndustries().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getRestrictedIndustries().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setRestrictedIndustries(requestDTO.getBodyDetails().get(0).getRestrictedIndustries());
			} else {
				errors.add("restrictedIndustries", new ActionMessage("error.string.cri.default.values"));
			}

			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestrictedIndustries())) {

				if (requestDTO.getBodyDetails().get(0).getRestrictedListIndustries() != null
						&& !requestDTO.getBodyDetails().get(0).getRestrictedListIndustries().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getRestrictedListIndustries().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode", Long
									.parseLong(requestDTO.getBodyDetails().get(0).getRestrictedListIndustries().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("CREDIT_LIST_RESTRICTED_INDUSTRIES".equals(codeEntry.getCategoryCode())) {
									reqObj.setRestrictedListIndustries((codeEntry).getEntryCode());
								} else {
									errors.add("restrictedListIndustries",
											new ActionMessage("error.party.restrictedListIndustries"));
								}
							} else {
								errors.add("restrictedListIndustries",
										new ActionMessage("error.party.restrictedListIndustries"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("restrictedListIndustries",
									new ActionMessage("error.party.restrictedListIndustries"));
						}
					}else {
						errors.add("restrictedListIndustries", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("restrictedListIndustries", new ActionMessage("error.string.mandatory"));
				}
			}
		} else {
			reqObj.setRestrictedIndustries(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent() != null
				&& !requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsQualifyingNotesPresent(
						requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim());
			} else {
				errors.add("isQualifyingNotesPresent", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsQualifyingNotesPresent(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (null != requestDTO.getBodyDetails().get(0).getStateImplications()
				&& !requestDTO.getBodyDetails().get(0).getStateImplications().trim().isEmpty()) {
			reqObj.setStateImplications(requestDTO.getBodyDetails().get(0).getStateImplications().trim());
		} else {
			reqObj.setStateImplications("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase() != null
				&& !requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsBorrowerInRejectDatabase(
						requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase());
			} else {
				errors.add("isBorrowerInRejectDatabase", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase())) {
				reqObj.setRejectHistoryReason(requestDTO.getBodyDetails().get(0).getRejectHistoryReason().trim());
			} else {
				reqObj.setRejectHistoryReason("");
			}
		} else {
			reqObj.setIsBorrowerInRejectDatabase(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (null != requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange()
				&& !requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim().isEmpty()) {
			if (!("Broker"
					.equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim())
					|| "Non Broker".equalsIgnoreCase(
							requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim()))) {
				errors.add("capitalForCommodityAndExchange", new ActionMessage("error.string.invalid"));
			} else {
				if ("Broker".equalsIgnoreCase(
						requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim())) {
					reqObj.setCapitalForCommodityAndExchange("Broker");
				} else {
					reqObj.setCapitalForCommodityAndExchange("Non Broker");
				}
			}
		} else {
			reqObj.setCapitalForCommodityAndExchange("Non Broker");
		}

		if (null != requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange()
				&& !requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim().isEmpty()) {
			if ("Broker"
					.equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim())) {
				if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare()
						&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim().isEmpty()) {
					if (requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim().equals("Y")
							|| requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim().equals("N")) {
						reqObj.setIsBrokerTypeShare(requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim());
					} else {
						errors.add("isBrokerTypeShare", new ActionMessage("error.invalid.field.checkbox.value"));
					}
				} else {
					reqObj.setIsBrokerTypeShare("");
				}

				if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity()
						&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim().isEmpty()) {
					if (requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim().equals("Y")
							|| requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim().equals("N")) {
						reqObj.setIsBrokerTypeCommodity(
								requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim());
					} else {
						errors.add("isBrokerTypeCommodity", new ActionMessage("error.invalid.field.checkbox.value"));
					}
				} else {
					reqObj.setIsBrokerTypeCommodity("");
				}
			}
		}

		if (requestDTO.getBodyDetails().get(0).getObjectFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getObjectFinance().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getObjectFinance().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getObjectFinance().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setObjectFinance(requestDTO.getBodyDetails().get(0).getObjectFinance().trim());
			} else {
				errors.add("objectFinance", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setObjectFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer() != null
				&& !requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsCommodityFinanceCustomer(
						requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim());
			} else {
				errors.add("isCommodityFinanceCustomer", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsCommodityFinanceCustomer(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility() != null
				&& !requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setRestructuedBorrowerOrFacility(
						requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility());
			} else {
				errors.add("restructuedBorrowerOrFacility", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility())) {
				if (requestDTO.getBodyDetails().get(0).getFacility() != null
						&& !requestDTO.getBodyDetails().get(0).getFacility().trim().isEmpty()) {
					Object obj = masterObj.getObjByEntityNameAndFacilityCode("actualFacilityNewMaster",
							requestDTO.getBodyDetails().get(0).getFacility().trim(), "facility", errors);
					if (!(obj instanceof ActionErrors)) {
						reqObj.setFacility(((IFacilityNewMaster) obj).getNewFacilityCode());
					}
				}
				if (requestDTO.getBodyDetails().get(0).getLimitAmount() != null
						&& !requestDTO.getBodyDetails().get(0).getLimitAmount().trim().isEmpty()) {
					if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getLimitAmount().trim()))
						reqObj.setLimitAmount(requestDTO.getBodyDetails().get(0).getLimitAmount().trim());
					else {
						errors.add("limitAmount", new ActionMessage("error.amount.numbers.format"));
					}
				} else {
					reqObj.setLimitAmount("");
				}
			}
		} else {
			reqObj.setRestructuedBorrowerOrFacility(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getTufs().trim())) {
			if (requestDTO.getBodyDetails().get(0).getSubsidyFlag() != null
					&& !requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().equalsIgnoreCase("Yes") || requestDTO
						.getBodyDetails().get(0).getSubsidyFlag().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
					reqObj.setSubsidyFlag(requestDTO.getBodyDetails().get(0).getSubsidyFlag());
				} else {
					errors.add("subsidyFlag", new ActionMessage("error.string.cri.default.values"));
				}
				if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getSubsidyFlag())) {
					if (requestDTO.getBodyDetails().get(0).getHoldingCompnay() != null
							&& !requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim().isEmpty()) {
						reqObj.setHoldingCompnay(requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim());
					} else {
						reqObj.setHoldingCompnay("");
					}
				}
			} else {
				reqObj.setSubsidyFlag(DEFAULT_VALUE_FOR_CRI_INFO);
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCautionList() != null
				&& !requestDTO.getBodyDetails().get(0).getCautionList().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCautionList().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getCautionList().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setCautionList(requestDTO.getBodyDetails().get(0).getCautionList().trim());
			} else {
				errors.add("cautionList", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCautionList())) {
				if (requestDTO.getBodyDetails().get(0).getDateOfCautionList() != null
						&& !requestDTO.getBodyDetails().get(0).getDateOfCautionList().trim().isEmpty()) {
					try {
						Date dt = cautionlistDateFormat
								.parse(requestDTO.getBodyDetails().get(0).getDateOfCautionList().trim());
						reqObj.setDateOfCautionList(cautionDateFormat.format(dt));
					} catch (ParseException e) {
						errors.add("dateOfCautionList", new ActionMessage("error.party.date.format"));
					}
				} else {
					reqObj.setDateOfCautionList("");
				}
				if (requestDTO.getBodyDetails().get(0).getCompany() != null
						&& !requestDTO.getBodyDetails().get(0).getCompany().trim().isEmpty()) {
					reqObj.setCompany(requestDTO.getBodyDetails().get(0).getCompany().trim());
				} else {
					reqObj.setCompany("");
				}
				if (requestDTO.getBodyDetails().get(0).getDirectors() != null
						&& !requestDTO.getBodyDetails().get(0).getDirectors().trim().isEmpty()) {
					reqObj.setDirectors(requestDTO.getBodyDetails().get(0).getDirectors().trim());
				} else {
					reqObj.setDirectors("");
				}
				if (requestDTO.getBodyDetails().get(0).getGroupCompanies() != null
						&& !requestDTO.getBodyDetails().get(0).getGroupCompanies().trim().isEmpty()) {
					reqObj.setGroupCompanies(requestDTO.getBodyDetails().get(0).getGroupCompanies().trim());
				} else {
					reqObj.setGroupCompanies("");
				}
			}
		} else {
			reqObj.setCautionList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getDefaultersList() != null
				&& !requestDTO.getBodyDetails().get(0).getDefaultersList().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getDefaultersList().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getDefaultersList().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setDefaultersList(requestDTO.getBodyDetails().get(0).getDefaultersList().trim());
			} else {
				errors.add("defaultersList", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getDefaultersList())) {

				if (requestDTO.getBodyDetails().get(0).getRbiDateOfCautionList() != null
						&& !requestDTO.getBodyDetails().get(0).getRbiDateOfCautionList().trim().isEmpty()) {

					try {
						Date dt = cautionlistDateFormat
								.parse(requestDTO.getBodyDetails().get(0).getRbiDateOfCautionList().trim());
						reqObj.setRbiDateOfCautionList(cautionDateFormat.format(dt));
					} catch (ParseException e) {
						errors.add("rbiDateOfCautionList", new ActionMessage("error.party.date.format"));
					}
				} else {
					reqObj.setRbiDateOfCautionList("");
				}
				if (requestDTO.getBodyDetails().get(0).getRbiCompany() != null
						&& !requestDTO.getBodyDetails().get(0).getRbiCompany().trim().isEmpty()) {
					reqObj.setRbiCompany(requestDTO.getBodyDetails().get(0).getRbiCompany().trim());
				} else {
					reqObj.setRbiCompany("");
				}
				if (requestDTO.getBodyDetails().get(0).getRbiDirectors() != null
						&& !requestDTO.getBodyDetails().get(0).getRbiDirectors().trim().isEmpty()) {
					reqObj.setRbiDirectors(requestDTO.getBodyDetails().get(0).getRbiDirectors().trim());
				} else {
					reqObj.setRbiDirectors("");
				}
				if (requestDTO.getBodyDetails().get(0).getRbiGroupCompanies() != null
						&& !requestDTO.getBodyDetails().get(0).getRbiGroupCompanies().trim().isEmpty()) {
					reqObj.setRbiGroupCompanies(requestDTO.getBodyDetails().get(0).getRbiGroupCompanies().trim());
				} else {
					reqObj.setRbiGroupCompanies("");
				}
			}
		} else {
			reqObj.setDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate() != null
				&& !requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setCommericialRealEstate(requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim());
			} else {
				errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.values"));
			}

			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCommericialRealEstate())) {
				if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue() != null
						&& !requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode", Long
									.parseLong(requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("COMMERCIAL_REAL_ESTATE".equals(codeEntry.getCategoryCode())) {
									reqObj.setCommericialRealEstateValue((codeEntry).getEntryCode());
								} else {
									errors.add("commericialRealEstateValue",
											new ActionMessage("error.party.commericialRealEstateValue"));
								}
							} else {
								errors.add("commericialRealEstateValue",
										new ActionMessage("error.party.commericialRealEstateValue"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("commericialRealEstateValue",
									new ActionMessage("error.party.commericialRealEstateValue"));
						}
					}else{
						errors.add("commericialRealEstateValue", new ActionMessage("error.string.field.length.exceeded"));
					}
				}

				if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing().trim()
						.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
					reqObj.setCommericialRealEstateResidentialHousing("No");
				} else {
					errors.add("commericialRealEstateResidentialHousing",
							new ActionMessage("error.string.cri.default.value"));
				}

				if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate().trim()
						.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
					reqObj.setResidentialRealEstate("No");
				} else {
					errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
				}

				if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate().trim()
						.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
					reqObj.setIndirectRealEstate("No");
				} else {
					errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
				}

			} else {
				if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing() != null
						&& "Yes".equalsIgnoreCase(
								requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing())) {

					reqObj.setCommericialRealEstateResidentialHousing(
							requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing().trim());

					if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setResidentialRealEstate("No");
					} else {
						errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setIndirectRealEstate("No");
					} else {
						errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

				} else if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate() != null
						&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getResidentialRealEstate())) {

					reqObj.setResidentialRealEstate(
							requestDTO.getBodyDetails().get(0).getResidentialRealEstate().trim());

					if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setCommericialRealEstateResidentialHousing("No");
					} else {
						errors.add("commericialRealEstateResidentialHousing",
								new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setIndirectRealEstate("No");
					} else {
						errors.add("indirectRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

				} else if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate() != null
						&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIndirectRealEstate())) {

					reqObj.setIndirectRealEstate(requestDTO.getBodyDetails().get(0).getIndirectRealEstate().trim());

					if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setCommericialRealEstate("No");
					} else {
						errors.add("commericialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setCommericialRealEstateResidentialHousing("No");
					} else {
						errors.add("commericialRealEstateResidentialHousing",
								new ActionMessage("error.string.cri.default.value"));
					}

					if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
						reqObj.setResidentialRealEstate("No");
					} else {
						errors.add("residentialRealEstate", new ActionMessage("error.string.cri.default.value"));
					}
				}
			}
		} else {
			reqObj.setCommericialRealEstate(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (null != requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo()
				&& !requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim().isEmpty()) {
			reqObj.setBorrowerDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim());
		} else {
			reqObj.setBorrowerDUNSNo("");
		}

		if (requestDTO.getBodyDetails().get(0).getPartyConsent() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyConsent().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getPartyConsent().trim().equalsIgnoreCase("Y")
					|| requestDTO.getBodyDetails().get(0).getPartyConsent().trim().equalsIgnoreCase("N")) {
				reqObj.setPartyConsent(requestDTO.getBodyDetails().get(0).getPartyConsent().trim());
			} else {
				errors.add("partyConsent", new ActionMessage("error.invalid.field.checkbox.value"));
			}
		} else {
			reqObj.setPartyConsent("N");
		}

		if (requestDTO.getBodyDetails().get(0).getClassActivity1() != null
				&& !requestDTO.getBodyDetails().get(0).getClassActivity1().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getClassActivity1().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getClassActivity1().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("HDFC_RBI_CODE".equals(codeEntry.getCategoryCode())) {
							reqObj.setClassActivity1((codeEntry).getEntryCode());
						} else {
							errors.add("classActivity1", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("classActivity1", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("classActivity1", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("classActivity1", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setClassActivity1("");
		}

		if (requestDTO.getBodyDetails().get(0).getClassActivity2() != null
				&& !requestDTO.getBodyDetails().get(0).getClassActivity2().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getClassActivity2().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getClassActivity2().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("HDFC_RBI_CODE".equals(codeEntry.getCategoryCode())) {
							reqObj.setClassActivity2((codeEntry).getEntryCode());
						} else {
							errors.add("classActivity2", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("classActivity2", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("classActivity2", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("classActivity2", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setClassActivity2("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getRegOffice()
				&& !requestDTO.getBodyDetails().get(0).getRegOffice().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRegOffice().length() == 1
					&& (requestDTO.getBodyDetails().get(0).getRegOffice().equals("Y")
							|| requestDTO.getBodyDetails().get(0).getRegOffice().equals("N"))) {
				reqObj.setRegOffice(requestDTO.getBodyDetails().get(0).getRegOffice());
			} else {
				errors.add("regOffice", new ActionMessage("Invalid Registered Office details"));
			}
		} else {
			reqObj.setRegOffice("N");
		}

		if (requestDTO.getBodyDetails().get(0).getRegOffice() != null
				&& !requestDTO.getBodyDetails().get(0).getRegOffice().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRegOffice().trim().equalsIgnoreCase("Y")
					|| requestDTO.getBodyDetails().get(0).getRegOffice().trim().equalsIgnoreCase("N")) {
				reqObj.setRegOffice(requestDTO.getBodyDetails().get(0).getRegOffice().trim());
				if (requestDTO.getBodyDetails().get(0).getRegOffice().trim().equals("Y")) {
					reqObj.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim());
					reqObj.setRegisteredCountry(requestDTO.getBodyDetails().get(0).getCountry().trim());
					reqObj.setRegisteredRegion(requestDTO.getBodyDetails().get(0).getRegion().trim());
					reqObj.setRegisteredState(requestDTO.getBodyDetails().get(0).getState().trim());
					reqObj.setRegisteredCity(requestDTO.getBodyDetails().get(0).getCity().trim());
					reqObj.setRegisteredAddr1(requestDTO.getBodyDetails().get(0).getAddress1().trim());
					reqObj.setRegisteredAddr2(requestDTO.getBodyDetails().get(0).getAddress2().trim());
					reqObj.setRegisteredAddr3(requestDTO.getBodyDetails().get(0).getAddress3().trim());
					reqObj.setRegisteredPincode(requestDTO.getBodyDetails().get(0).getPincode().trim());
					reqObj.setRegisteredTelNo(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
					reqObj.setRegisteredFaxNumber(requestDTO.getBodyDetails().get(0).getFaxNumber().trim());
					reqObj.setRegisteredTelephoneStdCode(
							requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim());
					reqObj.setRegisteredFaxStdCode(requestDTO.getBodyDetails().get(0).getFaxStdCode().trim());
					reqObj.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getEmailId().trim());
				} else {
					if (null != requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo()
							&& !requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim().isEmpty()) {
						reqObj.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim());
					} else {
						reqObj.setRegOfficeDUNSNo("");
					}
					if (requestDTO.getBodyDetails().get(0).getRegisteredAddr1() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim().isEmpty()) {
						reqObj.setRegisteredAddr1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim());
					} else {
						reqObj.setRegisteredAddr1("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredAddr2() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim().isEmpty()) {
						reqObj.setRegisteredAddr2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim());
					} else {
						reqObj.setRegisteredAddr2("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredAddr3() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim().isEmpty()) {
						reqObj.setRegisteredAddr3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim());
					} else {
						reqObj.setRegisteredAddr3("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
						if(!(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().length()>19)) {
							try {
								Object objCountry = masterObj.getObjectforMaster("actualCountry",
										new Long(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim()));
								if (objCountry != null) {
									reqObj.setRegisteredCountry(Long.toString(((ICountry) objCountry).getIdCountry()));
								} else {
									errors.add("registeredCountry", new ActionMessage("error.invalid.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this, e.getMessage());
								errors.add("registeredCountry", new ActionMessage("error.invalid.field.value"));
							}
						}else{
							errors.add("registeredCountry", new ActionMessage("error.string.field.length.exceeded"));
						}
					} else {
						errors.add("registeredCountry", new ActionMessage("error.string.mandatory"));
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
						if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
								&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {
							if(!(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().length()>19)) {
								try {
									Object obRegion = masterObj.getObjectforMaster("actualRegion",
											new Long(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim()));
									if (obRegion != null) {
										reqObj.setRegisteredRegion(
												requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim());

										if (requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim()
												.equalsIgnoreCase(Long
														.toString(((IRegion) obRegion).getCountryId().getIdCountry()))) {
											reqObj.setRegisteredCountry(
													Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()));
										} else {
											reqObj.setRegisteredCountry(
													requestDTO.getBodyDetails().get(0).getRegisteredCountry());
											System.out.println("Given Region is not Present in the list of Country");
											errors.add("registeredRegion", new ActionMessage("error.region.field.value"));
										}

									} else {
										errors.add("registeredRegion", new ActionMessage("error.region.field.value"));
									}
								} catch (Exception e) {
									DefaultLogger.error(this, e.getMessage());
									errors.add("registeredRegion", new ActionMessage("error.invalid.field.value"));
								}
							}else {
								errors.add("registeredRegion", new ActionMessage("error.string.field.length.exceeded"));
							}
						} else {
							errors.add("registeredRegion", new ActionMessage("error.string.mandatory"));
						}
					} else {
						errors.add("registeredCountry", new ActionMessage("error.string.mandatory"));
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
						if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
								&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {

							if (requestDTO.getBodyDetails().get(0).getRegisteredState() != null
									&& !requestDTO.getBodyDetails().get(0).getRegisteredState().trim().isEmpty()) {
								if(!(requestDTO.getBodyDetails().get(0).getRegisteredState().trim().length()>19)) {
									try {
										Object objState = masterObj.getObjectforMaster("actualState",
												new Long(requestDTO.getBodyDetails().get(0).getRegisteredState().trim()));
										if (objState != null) {
											reqObj.setRegisteredState(
													requestDTO.getBodyDetails().get(0).getRegisteredState().trim());

											if (requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim()
													.equalsIgnoreCase(Long
															.toString(((IState) objState).getRegionId().getIdRegion()))) {
												reqObj.setRegisteredRegion(
														Long.toString(((IState) objState).getRegionId().getIdRegion()));
											} else {
												System.out.println(
														"Given State is not Present in the registeredState list of Region");
												errors.add("registeredState", new ActionMessage("error.state.field.value"));
											}

										} else {
											errors.add("registeredState", new ActionMessage("error.state.field.value"));
										}
									} catch (Exception e) {
										DefaultLogger.error(this, e.getMessage());
										errors.add("registeredState", new ActionMessage("error.invalid.field.value"));
									}
								}else{
									errors.add("registeredState", new ActionMessage("error.string.field.length.exceeded"));
								}
							} else {
								errors.add("registeredState", new ActionMessage("error.string.mandatory"));
							}
						}
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
						if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
								&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {
							if (requestDTO.getBodyDetails().get(0).getRegisteredState() != null
									&& !requestDTO.getBodyDetails().get(0).getRegisteredState().trim().isEmpty()) {
								if (requestDTO.getBodyDetails().get(0).getRegisteredCity() != null
										&& !requestDTO.getBodyDetails().get(0).getRegisteredCity().trim().isEmpty()) {
									if(!(requestDTO.getBodyDetails().get(0).getRegisteredCity().trim().length()>19)) {
										try {
											Object obCity = masterObj.getObjectforMaster("actualCity", new Long(
													requestDTO.getBodyDetails().get(0).getRegisteredCity().trim()));
											if (obCity != null) {
												reqObj.setRegisteredCity(
														requestDTO.getBodyDetails().get(0).getRegisteredCity().trim());

												if (requestDTO.getBodyDetails().get(0).getRegisteredState().trim()
														.equalsIgnoreCase(Long
																.toString(((ICity) obCity).getStateId().getIdState()))) {
													reqObj.setRegisteredState(
															Long.toString(((ICity) obCity).getStateId().getIdState()));
												} else {
													System.out.println(
															"Given City is not Present in the registeredState list of State");
													errors.add("registeredCity",
															new ActionMessage("error.city.field.value"));
												}

											} else {
												errors.add("registeredCity", new ActionMessage("error.city.field.value"));
											}
										} catch (Exception e) {
											DefaultLogger.error(this, e.getMessage());
											errors.add("registeredCity", new ActionMessage("error.invalid.field.value"));
										}
									}else {
										errors.add("registeredCity", new ActionMessage("error.string.field.length.exceeded"));
									}
								} else {
									errors.add("registeredCity", new ActionMessage("error.string.mandatory"));
								}
							}
						}
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredPincode() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim().isEmpty()) {

						if (requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim().length() > 6) {
							errors.add("regOfficePostCodeLengthError",
									new ActionMessage("error.regOfficePostCode.length.exceeded"));
						} else {
							reqObj.setRegisteredPincode(
									requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
						}

						Pattern p = Pattern.compile(".*\\D.*");
						Matcher m = p.matcher(requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
						boolean isAlphaNumeric = m.matches();
						if (isAlphaNumeric) {
							errors.add("specialCharacterRegOfficePostCode",
									new ActionMessage("error.pincode.numeric.format"));
						}
					} else {
						reqObj.setRegisteredPincode("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim().isEmpty()) {
						reqObj.setRegisteredTelephoneStdCode(
								requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim());
					} else {
						reqObj.setRegisteredTelephoneStdCode("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredTelNo() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim().isEmpty()) {
						reqObj.setRegisteredTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim());
					} else {
						reqObj.setRegisteredTelNo("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim().isEmpty()) {
						reqObj.setRegisteredFaxStdCode(
								requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim());
					} else {
						reqObj.setRegisteredFaxStdCode("");
					}

					if (requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim().isEmpty()) {
						reqObj.setRegisteredFaxNumber(
								requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim());
					} else {
						reqObj.setRegisteredFaxNumber("");
					}
					if (null != requestDTO.getBodyDetails().get(0).getRegOfficeEmail()
							&& !requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim().isEmpty()) {
						reqObj.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim());
					} else {
						reqObj.setRegOfficeEmail("");
					}
				}
			} else {
				errors.add("regOffice", new ActionMessage("error.invalid.field.checkbox.value"));
			}
		} else {
			reqObj.setRegOffice("N");
			if (null != requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo()
					&& !requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim().isEmpty()) {
				reqObj.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim());
			} else {
				reqObj.setRegOfficeDUNSNo("");
			}
			if (requestDTO.getBodyDetails().get(0).getRegisteredAddr1() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim().isEmpty()) {
				reqObj.setRegisteredAddr1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim());
			} else {
				reqObj.setRegisteredAddr1("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredAddr2() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim().isEmpty()) {
				reqObj.setRegisteredAddr2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim());
			} else {
				reqObj.setRegisteredAddr2("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredAddr3() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim().isEmpty()) {
				reqObj.setRegisteredAddr3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim());
			} else {
				reqObj.setRegisteredAddr3("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
				if(!(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().length()>19)) {
					try {
						Object objCountry = masterObj.getObjectforMaster("actualCountry",
								new Long(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim()));
						if (objCountry != null) {
							reqObj.setRegisteredCountry(Long.toString(((ICountry) objCountry).getIdCountry()));
						} else {
							errors.add("registeredCountry", new ActionMessage("error.invalid.field.value"));
						}
					} catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
						errors.add("registeredCountry", new ActionMessage("error.invalid.field.value"));
					}
				}else {
					errors.add("registeredCountry", new ActionMessage("error.string.field.length.exceeded"));
				}
			} else {
				errors.add("registeredCountry", new ActionMessage("error.string.mandatory"));
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
						&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().length()>19)) {
						try {
							Object obRegion = masterObj.getObjectforMaster("actualRegion",
									new Long(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim()));
							if (obRegion != null) {
								reqObj.setRegisteredRegion(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim());

								if (requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().equalsIgnoreCase(
										Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()))) {
									reqObj.setRegisteredCountry(
											Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()));
								} else {
									reqObj.setRegisteredCountry(requestDTO.getBodyDetails().get(0).getRegisteredCountry());
									System.out.println("Given Region is not Present in the list of Country");
									errors.add("registeredRegion", new ActionMessage("error.region.field.value"));
								}

							} else {
								errors.add("registeredRegion", new ActionMessage("error.region.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("registeredRegion", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("registeredRegion", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("registeredRegion", new ActionMessage("error.string.mandatory"));
				}
			} else {
				errors.add("registeredCountry", new ActionMessage("error.string.mandatory"));
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
						&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {

					if (requestDTO.getBodyDetails().get(0).getRegisteredState() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredState().trim().isEmpty()) {
						if(!(requestDTO.getBodyDetails().get(0).getRegisteredState().trim().length()>19)) {
							try {
								Object objState = masterObj.getObjectforMaster("actualState",
										new Long(requestDTO.getBodyDetails().get(0).getRegisteredState().trim()));
								if (objState != null) {
									reqObj.setRegisteredState(
											requestDTO.getBodyDetails().get(0).getRegisteredState().trim());

									if (requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().equalsIgnoreCase(
											Long.toString(((IState) objState).getRegionId().getIdRegion()))) {
										reqObj.setRegisteredRegion(
												Long.toString(((IState) objState).getRegionId().getIdRegion()));
									} else {
										System.out.println(
												"Given State is not Present in the registeredState list of Region");
										errors.add("registeredState", new ActionMessage("error.state.field.value"));
									}

								} else {
									errors.add("registeredState", new ActionMessage("error.state.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this, e.getMessage());
								errors.add("registeredState", new ActionMessage("error.invalid.field.value"));
							}
						}else {
							errors.add("registeredState", new ActionMessage("error.string.field.length.exceeded"));
						}
					} else {
						errors.add("registeredState", new ActionMessage("error.string.mandatory"));
					}
				}
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredCountry() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getRegisteredRegion() != null
						&& !requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim().isEmpty()) {
					if (requestDTO.getBodyDetails().get(0).getRegisteredState() != null
							&& !requestDTO.getBodyDetails().get(0).getRegisteredState().trim().isEmpty()) {
						if (requestDTO.getBodyDetails().get(0).getRegisteredCity() != null
								&& !requestDTO.getBodyDetails().get(0).getRegisteredCity().trim().isEmpty()) {
							if(!(requestDTO.getBodyDetails().get(0).getRegisteredCity().trim().length()>19)) {
								try {
									Object obCity = masterObj.getObjectforMaster("actualCity",
											new Long(requestDTO.getBodyDetails().get(0).getRegisteredCity().trim()));
									if (obCity != null) {
										reqObj.setRegisteredCity(
												requestDTO.getBodyDetails().get(0).getRegisteredCity().trim());

										if (requestDTO.getBodyDetails().get(0).getRegisteredState().trim().equalsIgnoreCase(
												Long.toString(((ICity) obCity).getStateId().getIdState()))) {
											reqObj.setRegisteredState(
													Long.toString(((ICity) obCity).getStateId().getIdState()));
										} else {
											System.out.println(
													"Given City is not Present in the registeredState list of State");
											errors.add("registeredCity", new ActionMessage("error.city.field.value"));
										}

									} else {
										errors.add("registeredCity", new ActionMessage("error.city.field.value"));
									}
								} catch (Exception e) {
									DefaultLogger.error(this, e.getMessage());
									errors.add("registeredCity", new ActionMessage("error.invalid.field.value"));
								}
							}else {
								errors.add("registeredCity", new ActionMessage("error.string.field.length.exceeded"));
							}
						} else {
							errors.add("registeredCity", new ActionMessage("error.string.mandatory"));
						}
					}
				}
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredPincode() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim().isEmpty()) {

				if (requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim().length() > 6) {
					errors.add("regOfficePostCodeLengthError",
							new ActionMessage("error.regOfficePostCode.length.exceeded"));
				} else {
					reqObj.setRegisteredPincode(requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
				}

				Pattern p = Pattern.compile(".*\\D.*");
				Matcher m = p.matcher(requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
				boolean isAlphaNumeric = m.matches();
				if (isAlphaNumeric) {
					errors.add("specialCharacterRegOfficePostCode", new ActionMessage("error.pincode.numeric.format"));
				}
			} else {
				reqObj.setRegisteredPincode("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim().isEmpty()) {
				reqObj.setRegisteredTelephoneStdCode(
						requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim());
			} else {
				reqObj.setRegisteredTelephoneStdCode("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredTelNo() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim().isEmpty()) {
				reqObj.setRegisteredTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim());
			} else {
				reqObj.setRegisteredTelNo("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim().isEmpty()) {
				reqObj.setRegisteredFaxStdCode(requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim());
			} else {
				reqObj.setRegisteredFaxStdCode("");
			}

			if (requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber() != null
					&& !requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim().isEmpty()) {
				reqObj.setRegisteredFaxNumber(requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim());
			} else {
				reqObj.setRegisteredFaxNumber("");
			}
			if (null != requestDTO.getBodyDetails().get(0).getRegOfficeEmail()
					&& !requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim().isEmpty()) {
				reqObj.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim());
			} else {
				reqObj.setRegOfficeEmail("");
			}
		}

		if (requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus() != null
				&& !requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().trim().length()>19)) {
				try {
					boolean status = false;
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("WILLFUL_DEFAULT_STATUS".equals(codeEntry.getCategoryCode())) {
							reqObj.setWillfulDefaultStatus((codeEntry).getEntryCode());
							if ((codeEntry).getEntryCode().equals("DEFAULTER") || (codeEntry).getEntryCode().equals("1")) {
								status = true;
							}
						} else {
							errors.add("willfulDefaultStatus", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("willfulDefaultStatus", new ActionMessage("error.invalid.field.value"));
					}
					reqObj.setDateWillfulDefault(requestDTO.getBodyDetails().get(0).getDateWillfulDefault().trim());
					System.out.println("<<<<<<<<<<<<<<<<<WILLFUL DEFAULT STATUS>>>>>>>>>>>>>>>>>" + status);
					if (status) {
						System.out.println("<<<<<<<<<<<<<<<<<IN IF LOOP line number 6015>>>>>>>>>>>>>>>>>");
						if (requestDTO.getBodyDetails().get(0).getDateWillfulDefault() != null
								&& !requestDTO.getBodyDetails().get(0).getDateWillfulDefault().trim().isEmpty()) {
							try {
								Date dt = cautionDateFormat
										.parse(requestDTO.getBodyDetails().get(0).getDateWillfulDefault().trim());
								reqObj.setDateWillfulDefault(cautionDateFormat.format(dt));
							} catch (ParseException e) {
								errors.add("dateWillfulDefault", new ActionMessage("error.party.date.format"));
							}
						} else {
							reqObj.setDateWillfulDefault("");
						}

						if (requestDTO.getBodyDetails().get(0).getSuitFilledStatus() != null
								&& !requestDTO.getBodyDetails().get(0).getSuitFilledStatus().trim().isEmpty()) {
							if(!(requestDTO.getBodyDetails().get(0).getSuitFilledStatus().trim().length()>19)) {
								try {
									Object obj1 = masterObj.getMasterData("entryCode",
											Long.parseLong(requestDTO.getBodyDetails().get(0).getSuitFilledStatus().trim()));
									if (obj1 != null) {
										ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj1;
										if ("SUIT_FILLED_STATUS".equals(codeEntry.getCategoryCode())) {
											reqObj.setSuitFilledStatus((codeEntry).getEntryCode());
										} else {
											errors.add("suitFilledStatus", new ActionMessage("error.invalid.field.value"));
										}
									} else {
										errors.add("suitFilledStatus", new ActionMessage("error.invalid.field.value"));
									}
								} catch (Exception e) {
									DefaultLogger.error(this, e.getMessage());
									errors.add("suitFilledStatus", new ActionMessage("error.invalid.field.value"));
								}
							}else {
								errors.add("suitFilledStatus", new ActionMessage("error.string.field.length.exceeded"));
							}
						} else {
							reqObj.setSuitFilledStatus("");
						}
						if (null != requestDTO.getBodyDetails().get(0).getSuitReferenceNo()
								&& !requestDTO.getBodyDetails().get(0).getSuitReferenceNo().trim().isEmpty()) {
							if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getSuitReferenceNo().trim())) {
								reqObj.setSuitReferenceNo(requestDTO.getBodyDetails().get(0).getSuitReferenceNo().trim());
							} else {
								errors.add("suitReferenceNo", new ActionMessage("error.number.field.value"));
							}
						} else {
							reqObj.setSuitReferenceNo("");
						}
						if (null != requestDTO.getBodyDetails().get(0).getSuitAmount()
								&& !requestDTO.getBodyDetails().get(0).getSuitAmount().trim().isEmpty()) {
							reqObj.setSuitAmount(requestDTO.getBodyDetails().get(0).getSuitAmount().trim());
						} else {
							reqObj.setSuitAmount("");
						}
						if (requestDTO.getBodyDetails().get(0).getDateOfSuit() != null
								&& !requestDTO.getBodyDetails().get(0).getDateOfSuit().trim().isEmpty()) {
							try {
								Date dt = cautionDateFormat
										.parse(requestDTO.getBodyDetails().get(0).getDateOfSuit().trim());
								reqObj.setDateOfSuit(cautionDateFormat.format(dt));
							} catch (ParseException e) {
								errors.add("dateOfSuit", new ActionMessage("error.party.date.format"));
							}
						} else {
							reqObj.setDateOfSuit("");
						}
					}
					System.out.println("<<<<<<<<<<<<<<<<<IF LOOP ENDS line number 6070>>>>>>>>>>>>>>>>>");
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("willfulDefaultStatus", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("willfulDefaultStatus", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("willfulDefaultStatus", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks() != null
				&& !requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim()
					.equalsIgnoreCase("classified")
					|| requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim()
							.equalsIgnoreCase("Satisfactory")) {
				reqObj.setConductOfAccountWithOtherBanks(
						requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim());
			} else {
				errors.add("conductOfAccountWithOtherBanks", new ActionMessage("error.string.default.values"));
			}
			if (requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim()
					.equalsIgnoreCase("classified")) {
				reqObj.setConductOfAccountWithOtherBanks("classified");
				if (requestDTO.getBodyDetails().get(0).getCrilicStatus() != null
						&& !requestDTO.getBodyDetails().get(0).getCrilicStatus().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getCrilicStatus().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getCrilicStatus().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("CRILIC_STATUS".equals(codeEntry.getCategoryCode())) {
									reqObj.setCrilicStatus((codeEntry).getEntryCode());
								} else {
									errors.add("crilicStatus", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("crilicStatus", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("crilicStatus", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("crilicStatus", new ActionMessage("error.string.field.length.exceeded"));
					}
				}
			}
		} else {
			reqObj.setConductOfAccountWithOtherBanks("Satisfactory");
		}

		if (requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList() != null
				&& !requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsRBIWilfulDefaultersList(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList());
			} else {
				errors.add("isRBIWilfulDefaultersList", new ActionMessage("error.string.cri.default.values"));
			}
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList())) {
				if (requestDTO.getBodyDetails().get(0).getNameOfBank() != null
						&& !requestDTO.getBodyDetails().get(0).getNameOfBank().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getNameOfBank().trim().length()>19)) {
						Object obj = masterObj.getObjectByEntityNameAndId("actualOtherBank",
								requestDTO.getBodyDetails().get(0).getNameOfBank().trim(), "nameOfBank", errors);
						if (!(obj instanceof ActionErrors)) {
							reqObj.setNameOfBank(((IOtherBank) obj).getOtherBankCode());
						} else {
							errors.add("nameOfBank", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("nameOfBank", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					reqObj.setNameOfBank("");
				}
				if ("Yes".equals(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne().trim())
						|| "No".equals(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne().trim())) {
					reqObj.setIsDirectorMoreThanOne(
							requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne().trim());
				} else {
					errors.add("isDirectorMoreThanOne", new ActionMessage("error.invalid.field.value"));
				}
				if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne())) {
					reqObj.setNameOfDirectorsAndCompany(
							requestDTO.getBodyDetails().get(0).getNameOfDirectorsAndCompany());
				} else {
					reqObj.setNameOfDirectorsAndCompany("");
				}

			} else {
				reqObj.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			}
		} else {
			reqObj.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (null != requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank()
				&& !requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim().isEmpty()) {
			if(requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim().equalsIgnoreCase("Yes") || requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsBorrDefaulterWithBank(requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim());
				if (null != requestDTO.getBodyDetails().get(0).getDetailsOfDefault()
						&& !requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim().isEmpty()) {
					reqObj.setDetailsOfDefault(requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim());
				} else {
					reqObj.setDetailsOfDefault("");
				}
				if (null != requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff()
						&& !requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim().isEmpty()) {
					reqObj.setExtOfCompromiseAndWriteoff(
							requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim());
				} else {
					reqObj.setExtOfCompromiseAndWriteoff("");
				}
			}else {
				errors.add("isBorrDefaulterWithBank", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setDetailsOfDefault("");
			reqObj.setExtOfCompromiseAndWriteoff("");
			reqObj.setIsBorrDefaulterWithBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsCibilStatusClean() != null
				&& !requestDTO.getBodyDetails().get(0).getIsCibilStatusClean().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsCibilStatusClean().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsCibilStatusClean().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsCibilStatusClean(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean());
			} else {
				errors.add("isCibilStatusClean", new ActionMessage("error.string.cri.default.values"));
			}
			if ("No".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean())) {
				reqObj.setDetailsOfCleanCibil(requestDTO.getBodyDetails().get(0).getDetailsOfCleanCibil());
			} else {
				reqObj.setIsCibilStatusClean("Yes");
				reqObj.setDetailsOfCleanCibil("");
			}

		} else {
			reqObj.setIsCibilStatusClean("Yes");
		}
		// +++++++++++++++++++++++++++++

		if (requestDTO.getBodyDetails().get(0).getRelationshipManager() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipManager().trim().isEmpty()) {
			try {
				int counts = fetchMGRCodeCountWithActiveStatus(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim());
				if (counts == 0) {
					errors.add("relationshipManager", new ActionMessage("error.invalid.field.value"));
				}else {
					reqObj.setRelationshipManager(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim());	
					reqObj.setRelationshipMgrCode(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim());	
				}
			}catch(Exception e) {
				errors.add("relationshipManager", new ActionMessage("error.invalid.field.value"));
			}
		} else {
			errors.add("relationshipManager", new ActionMessage("error.string.mandatory"));
		}


		if (requestDTO.getBodyDetails().get(0).getEntity() != null
				&& !requestDTO.getBodyDetails().get(0).getEntity().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getEntity().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getEntity().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("Entity".equals(codeEntry.getCategoryCode())) {
							reqObj.setEntity((codeEntry).getEntryCode());
						} else {
							errors.add("entity", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("entity", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("entity", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("entity", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("entity", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getEntityType() != null
				&& !requestDTO.getBodyDetails().get(0).getEntityType().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getEntityType().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getEntityType().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("ENTITY_TYPE".equals(codeEntry.getCategoryCode())) {
							reqObj.setEntityType((codeEntry).getEntryCode());
						} else {
							errors.add("entityType", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("entityType", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("entityType", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("entityType", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setEntityType("");
		}

		if (requestDTO.getBodyDetails().get(0).getPAN() != null
				&& !requestDTO.getBodyDetails().get(0).getPAN().trim().isEmpty()) {
			ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
			List<String> partyListWithSamePAN = new ArrayList<String>();
			try {
				if ("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
					partyListWithSamePAN = custDAO.checkIfPANExistsInOtherParty(
							requestDTO.getBodyDetails().get(0).getClimsPartyId(),
							requestDTO.getBodyDetails().get(0).getPAN().trim());
				} else {
					partyListWithSamePAN = custDAO.checkIfPANExistsInOtherParty(null,
							requestDTO.getBodyDetails().get(0).getPAN().trim());
				}

				if (partyListWithSamePAN != null && partyListWithSamePAN.size() > 0) {
					errors.add("PAN",
							new ActionMessage("error.string.panAlreadyExists",
									Arrays.toString(partyListWithSamePAN.toArray())));
				} else {
					reqObj.setPAN(requestDTO.getBodyDetails().get(0).getPAN().trim());
				}
			} catch (SearchDAOException e) {
				e.printStackTrace();
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			reqObj.setPAN("");
		}

		if (requestDTO.getBodyDetails().get(0).getRBIIndustryCode() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("HDFC_RBI_CODE".equals(codeEntry.getCategoryCode())) {
							reqObj.setRBIIndustryCode((codeEntry).getEntryCode());
						} else {
							errors.add("RBIIndustryCode", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("RBIIndustryCode", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("RBIIndustryCode", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("RBIIndustryCode", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setRBIIndustryCode("");
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryName() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryName().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getIndustryName().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getIndustryName().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("HDFC_INDUSTRY".equals(codeEntry.getCategoryCode())) {
							reqObj.setIndustryName((codeEntry).getEntryCode());
						} else {
							errors.add("industryName", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("industryName", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("industryName", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("industryName", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setIndustryName("");
		}

		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethod().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getBankingMethod().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getBankingMethod().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("BANKING_METHOD".equals(codeEntry.getCategoryCode())) {
							reqObj.setBankingMethod((codeEntry).getEntryCode());
						} else {
							errors.add("bankingMethod", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("bankingMethod", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("bankingMethod", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("bankingMethod", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("bankingMethod", new ActionMessage("error.string.mandatory"));
		}

		// New fields added for Wholesale Rest API
		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null && 
				("OUTSIDECONSORTIUM".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
				|| "OUTSIDEMULTIPLE".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
				|| "MULTIPLE".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
				|| "CONSORTIUM".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim()))				
		   ) {
			
			if (null != requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer()
					&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim().isEmpty()) {
				reqObj.setMultBankFundBasedHdfcBankPer(
						requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim());
			} else {
				reqObj.setMultBankFundBasedHdfcBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer()
					&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim().isEmpty()) {
				reqObj.setMultBankFundBasedLeadBankPer(
						requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim());
			} else {
				reqObj.setMultBankFundBasedLeadBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer()
					&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
				reqObj.setMultBankNonFundBasedHdfcBankPer(
						requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim());
			} else {
				reqObj.setMultBankNonFundBasedHdfcBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer()
					&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim().isEmpty()) {
				reqObj.setMultBankNonFundBasedLeadBankPer(
						requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim());
			} else {
				reqObj.setMultBankNonFundBasedLeadBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer()
					&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim().isEmpty()) {
				reqObj.setConsBankFundBasedHdfcBankPer(
						requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim());
			} else {
				reqObj.setConsBankFundBasedHdfcBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer()
					&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim().isEmpty()) {
				reqObj.setConsBankFundBasedLeadBankPer(
						requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim());
			} else {
				reqObj.setConsBankFundBasedLeadBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer()
					&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
				reqObj.setConsBankNonFundBasedHdfcBankPer(
						requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim());
			} else {
				reqObj.setConsBankNonFundBasedHdfcBankPer("");
			}

			if (null != requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer()
					&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim().isEmpty()) {
				reqObj.setConsBankNonFundBasedLeadBankPer(
						requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim());
			} else {
				reqObj.setConsBankNonFundBasedLeadBankPer("");
			}
		}else {
			reqObj.setMultBankFundBasedLeadBankPer("");
			reqObj.setMultBankFundBasedHdfcBankPer("");
			reqObj.setMultBankNonFundBasedHdfcBankPer("");
			reqObj.setMultBankNonFundBasedLeadBankPer("");
			reqObj.setConsBankFundBasedHdfcBankPer("");
			reqObj.setConsBankFundBasedLeadBankPer("");
			reqObj.setConsBankNonFundBasedHdfcBankPer("");
			reqObj.setConsBankNonFundBasedLeadBankPer("");
		}
		
		if (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim().isEmpty()) {
			if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim())) {
				reqObj.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim());
			} else {
				errors.add("totalFundedLimit", new ActionMessage("error.number.field.value"));
			}
		} else {
			reqObj.setTotalFundedLimit("");
		}

		if (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim().isEmpty()) {
			if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim())) {
				reqObj.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim());
			} else {
				errors.add("totalNonFundedLimit", new ActionMessage("error.number.field.value"));
			}
		} else {
			reqObj.setTotalNonFundedLimit("");
		}

		if (requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim().isEmpty()) {
			reqObj.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim());
		} else {
			reqObj.setFundedSharePercent("");
		}

		if (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getMemoExposure().trim().isEmpty()) {
			reqObj.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure().trim());
		} else {
			reqObj.setMemoExposure("");
		}

		if (requestDTO.getBodyDetails().get(0).getMpbf() != null
				&& !requestDTO.getBodyDetails().get(0).getMpbf().trim().isEmpty()) {

			if (!(requestDTO.getBodyDetails().get(0).getMpbf().trim().length() > 10)) {
				if (ASSTValidator.isNumeric(requestDTO.getBodyDetails().get(0).getMpbf().trim()))
					reqObj.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf().trim());
				else {
					errors.add("mpbf", new ActionMessage("error.amount.numbers.format"));
				}
			} else {
				errors.add("mpbfLengthError", new ActionMessage("error.mpbf.length.exceeded"));
			}

		} else {
			reqObj.setMpbf("");
		}

		if (requestDTO.getBodyDetails().get(0).getPartyName() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyName().trim().isEmpty()) {
			reqObj.setPartyName(requestDTO.getBodyDetails().get(0).getPartyName().trim());
		} else {
			reqObj.setPartyName("");
		}

		if (requestDTO.getBodyDetails().get(0).getSegment() != null
				&& !requestDTO.getBodyDetails().get(0).getSegment().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getSegment().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getSegment().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("HDFC_SEGMENT".equals(codeEntry.getCategoryCode())) {
							reqObj.setSegment((codeEntry).getEntryCode());
						} else {
							errors.add("segment", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("segment", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("segment", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("segment", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("segment", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getRelationshipStartDate() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipStartDate().toString().trim().isEmpty()) {
			try {
				relationshipDateFormat
						.parse(requestDTO.getBodyDetails().get(0).getRelationshipStartDate().toString().trim());
				reqObj.setRelationshipStartDate(requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim());
			} catch (ParseException e) {
				errors.add("relationshipStartDate", new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
			}
		} else {
			reqObj.setRelationshipStartDate("");
		}
		// Santosh LEI CR
		if (requestDTO.getBodyDetails().get(0).getLeiExpDate() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiExpDate().toString().trim().isEmpty()) {
			try {
				relationshipDateFormat.parse(requestDTO.getBodyDetails().get(0).getLeiExpDate().toString().trim());
				reqObj.setLeiExpDate(requestDTO.getBodyDetails().get(0).getLeiExpDate().trim());
			} catch (ParseException e) {
				errors.add("leiExpDate", new ActionMessage("error.wsdl.relationshipDate.invalid.format"));
			}
			/*
			 * if(requestDTO.getBodyDetails().get(0).getLeiCode()==null ||
			 * requestDTO.getBodyDetails().get(0).getLeiCode().toString().trim().isEmpty())
			 * { errors.add("leiCode",new ActionMessage("error.wsdl.leiCode.mandatory")); }
			 */
		} else {
			reqObj.setLeiExpDate("");
		}

		if (requestDTO.getBodyDetails().get(0).getLeiCode() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiCode().toString().trim().isEmpty()) {
			boolean flag = ASSTValidator
					.isValidAlphaNumStringWithoutSpace(requestDTO.getBodyDetails().get(0).getLeiCode());
			if (flag == true)
				errors.add("leiCode", new ActionMessage("error.wsdl.leiCode.string.invalidCharacter"));
			else if (requestDTO.getBodyDetails().get(0).getLeiCode().trim().length() != 20)
				errors.add("leiCode", new ActionMessage("error.wsdl.leiCode.length.exceeded"));
			else {
				reqObj.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode().trim());
			}
			/*
			 * if(requestDTO.getBodyDetails().get(0).getLeiExpDate()==null ||
			 * requestDTO.getBodyDetails().get(0).getLeiExpDate().toString().trim().isEmpty(
			 * )) { errors.add("leiExpDate",new
			 * ActionMessage("error.wsdl.leiExpDate.mandatory")); }
			 */
		} else {
			reqObj.setLeiCode("");
		}
		// End LEI CR
		// *********************CRI Info (One-to-one)****************************

		if (requestDTO.getBodyDetails().get(0).getCustomerRAMId() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim().isEmpty()) {
			reqObj.setCustomerRAMId(requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim());
		} else {
			reqObj.setCustomerRAMId("");
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerAPRCode() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim().isEmpty()) {
			if (!(requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim().length() > 10)) {
				reqObj.setCustomerAPRCode(requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim());
			} else {
				errors.add("customerAPRCode", new ActionMessage("error.customerAprCode.length.exceeded"));
			}
		} else {
			reqObj.setCustomerAPRCode("");
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerExtRating() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim().isEmpty()) {
			reqObj.setCustomerExtRating(requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim());
		} else {
			reqObj.setCustomerExtRating("");
		}

		if (requestDTO.getBodyDetails().get(0).getNbfcFlag() != null
				&& !requestDTO.getBodyDetails().get(0).getNbfcFlag().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getNbfcFlag().trim().equalsIgnoreCase("Yes")) {

				reqObj.setNbfcFlag("Yes");

				if (requestDTO.getBodyDetails().get(0).getNbfcA() != null
						&& !requestDTO.getBodyDetails().get(0).getNbfcA().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getNbfcA().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getNbfcA().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("NBFC_A".equals(codeEntry.getCategoryCode())) {
									reqObj.setNbfcA((codeEntry).getEntryCode());
								} else {
									errors.add("nbfcA", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("nbfcA", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("nbfcA", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("nbfcA", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("nbfcA", new ActionMessage("error.string.mandatory"));
				}

				if (requestDTO.getBodyDetails().get(0).getNbfcB() != null
						&& !requestDTO.getBodyDetails().get(0).getNbfcB().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getNbfcB().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getNbfcB().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("NBFC_B".equals(codeEntry.getCategoryCode())) {
									reqObj.setNbfcB((codeEntry).getEntryCode());
								} else {
									errors.add("nbfcB", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("nbfcB", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("nbfcB", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("nbfcB", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("nbfcB", new ActionMessage("error.string.mandatory"));
				}

			} else if (requestDTO.getBodyDetails().get(0).getNbfcFlag().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setNbfcFlag(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isNbfs", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setNbfcFlag(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getMsmeClassification() != null
				&& !requestDTO.getBodyDetails().get(0).getMsmeClassification().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getMsmeClassification().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getMsmeClassification().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("MSME_CLASSIC".equals(codeEntry.getCategoryCode())) {
							reqObj.setMsmeClassification((codeEntry).getEntryCode());
						} else {
							errors.add("msmeClassification", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("msmeClassification", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("msmeClassification", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("msmeClassification", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setMsmeClassification("");
		}

		// Added-----------
		if (requestDTO.getBodyDetails().get(0).getWeakerSection() != null
				&& !requestDTO.getBodyDetails().get(0).getWeakerSection().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getWeakerSection().trim().equalsIgnoreCase("Yes")) {

				reqObj.setWeakerSection("Yes");

				if (requestDTO.getBodyDetails().get(0).getWeakerSectionType() != null
						&& !requestDTO.getBodyDetails().get(0).getWeakerSectionType().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getWeakerSectionType().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getWeakerSectionType().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("WEAKER_SEC".equals(codeEntry.getCategoryCode())) {
									reqObj.setWeakerSectionType((codeEntry).getEntryCode());
								} else {
									errors.add("weakerSectionType", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("weakerSectionType", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("weakerSectionType", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("weakerSectionType", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("weakerSectionType", new ActionMessage("error.string.mandatory"));
				}

				if (null != requestDTO.getBodyDetails().get(0).getWeakerSectionValue()
						&& !requestDTO.getBodyDetails().get(0).getWeakerSectionValue().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getWeakerSectionValue().trim().length()>100)) {
						reqObj.setWeakerSectionValue(requestDTO.getBodyDetails().get(0).getWeakerSectionValue().trim());
					}else {
						errors.add("weakerSectionValue", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					reqObj.setWeakerSectionValue("");
				}

			} else if (requestDTO.getBodyDetails().get(0).getWeakerSection().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setWeakerSection(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("weakerSection", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setWeakerSection(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getMinorityCommunity() != null
				&& !requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim().equalsIgnoreCase("Yes")) {
				reqObj.setMinorityCommunity("Yes");
				if (requestDTO.getBodyDetails().get(0).getMinorityCommunityType() != null
						&& !requestDTO.getBodyDetails().get(0).getMinorityCommunityType().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getMinorityCommunityType().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(requestDTO.getBodyDetails().get(0).getMinorityCommunityType().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("MINORITY_COMMU".equals(codeEntry.getCategoryCode())) {
									reqObj.setMinorityCommunityType((codeEntry).getEntryCode());
								} else {
									errors.add("minorityCommunityType", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("minorityCommunityType", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("minorityCommunityType", new ActionMessage("error.invalid.field.value"));
						}	
						
					}else {
						errors.add("minorityCommunityType", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("minorityCommunityType", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setMinorityCommunity(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("minorityCommunity", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setMinorityCommunity(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCommodityExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getCommodityExposure().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCommodityExposure().trim().equals("Yes") || requestDTO
					.getBodyDetails().get(0).getCommodityExposure().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setCommodityExposure(requestDTO.getBodyDetails().get(0).getCommodityExposure().trim());
			} else {
				errors.add("commodityExposure", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setCommodityExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCommodityExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getCommodityExposure().trim().isEmpty()
				&& requestDTO.getBodyDetails().get(0).getCommodityExposure().trim().equals("Yes")) {
			if (requestDTO.getBodyDetails().get(0).getSensitive() != null
					&& !requestDTO.getBodyDetails().get(0).getSensitive().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getSensitive().trim().equals("Yes") || requestDTO
						.getBodyDetails().get(0).getSensitive().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
					reqObj.setSensitive(requestDTO.getBodyDetails().get(0).getSensitive().trim());
				} else {
					errors.add("isSensitive", new ActionMessage("error.string.cri.default.values"));
				}
			} else {
				reqObj.setSensitive("No");
			}
		} else {
			reqObj.setSensitive("No");
		}

		if (requestDTO.getBodyDetails().get(0).getCommodityName() != null
				&& !requestDTO.getBodyDetails().get(0).getCommodityName().trim().isEmpty()) {
			// Category Code == COMMODITY_NAMES
			if(!(requestDTO.getBodyDetails().get(0).getCommodityName().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getCommodityName().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("COMMODITY_NAMES".equals(codeEntry.getCategoryCode())) {
							reqObj.setCommodityName((codeEntry).getEntryCode());
						} else {
							errors.add("commodityName", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("commodityName", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("commodityName", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("commodityName", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			if (requestDTO.getBodyDetails().get(0).getSensitive() != null
					&& !requestDTO.getBodyDetails().get(0).getSensitive().trim().isEmpty()
					&& requestDTO.getBodyDetails().get(0).getSensitive().trim().equals("Yes")) {
				errors.add("commoditiesName", new ActionMessage("error.string.mandatory"));
			} else {
				reqObj.setCommodityName("");
			}
		}

		if (requestDTO.getBodyDetails().get(0).getPsu() != null
				&& !requestDTO.getBodyDetails().get(0).getPsu().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getPsu().trim().equals("State")
					|| requestDTO.getBodyDetails().get(0).getPsu().trim().equals("Central")
					|| requestDTO.getBodyDetails().get(0).getPsu().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setPsu(requestDTO.getBodyDetails().get(0).getPsu().trim());
			} else {
				errors.add("psu", new ActionMessage("error.string.cri.psu.default.values"));
			}
		} else {
			reqObj.setPsu(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getPercentageOfShareholding() != null
				&& !requestDTO.getBodyDetails().get(0).getPercentageOfShareholding().trim().isEmpty()) {
			reqObj.setPercentageOfShareholding(requestDTO.getBodyDetails().get(0).getPercentageOfShareholding().trim());
		} else {
			if (requestDTO.getBodyDetails().get(0).getPsu() != null
					&& !requestDTO.getBodyDetails().get(0).getPsu().trim().isEmpty()
					&& !requestDTO.getBodyDetails().get(0).getPsu().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				errors.add("psuPercentage", new ActionMessage("error.string.cri.psuPercentage.default.values"));
			} else {
				reqObj.setPercentageOfShareholding("");
			}
		}

		if (requestDTO.getBodyDetails().get(0).getGovtUndertaking() != null
				&& !requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim().equals("State")
					|| requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim().equals("Central") || requestDTO
							.getBodyDetails().get(0).getGovtUndertaking().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setGovtUndertaking(requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim());
			} else {
				errors.add("govtUnderTaking", new ActionMessage("error.string.cri.govUndertaking.default.values"));
			}
		} else {
			reqObj.setGovtUndertaking(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable() != null
				&& !requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable().trim().equals("Yes") || requestDTO
					.getBodyDetails().get(0).getSEMSGuideApplicable().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setSEMSGuideApplicable(requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable().trim());
			} else {
				errors.add("isSemsGuideApplicable", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setSEMSGuideApplicable(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion() != null
				&& !requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim().equals("Yes") || requestDTO
					.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setFailsUnderIFCExclusion(requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim());
			} else {
				errors.add("isFailIfcExcluList", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setFailsUnderIFCExclusion(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getTufs() != null
				&& !requestDTO.getBodyDetails().get(0).getTufs().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getTufs().trim().equals("Yes")
					|| requestDTO.getBodyDetails().get(0).getTufs().trim().equals(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setTufs(requestDTO.getBodyDetails().get(0).getTufs().trim());
			} else {
				errors.add("isTufs", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setTufs(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getRBIDefaulterList() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim().equalsIgnoreCase("Yes")) {
				reqObj.setRBIDefaulterList("Yes");

				// Change for RBI defaulter Type i.e Company ,Director and Group Companies
				if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany().trim().isEmpty()
						&& requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany().trim()
								.equals("Company")) {
					reqObj.setRbiDefaulterListTypeCompany("Company");
					reqObj.setRbiDefaulterListTypeDirectors("");
					reqObj.setRbiDefaulterListTypeGroupCompanies("");
				} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors().trim().isEmpty()
						&& requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors().trim()
								.equals("Directors")) {
					reqObj.setRbiDefaulterListTypeDirectors("Directors");
					reqObj.setRbiDefaulterListTypeCompany("");
					reqObj.setRbiDefaulterListTypeGroupCompanies("");
				} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies().trim().isEmpty()
						&& requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies().trim()
								.equals("Group Companies")) {
					reqObj.setRbiDefaulterListTypeGroupCompanies("Group Companies");
					reqObj.setRbiDefaulterListTypeDirectors("");
					reqObj.setRbiDefaulterListTypeCompany("");
				} else {
					errors.add("rbiDefaulterListTypeCompany",
							new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
					errors.add("rbiDefaulterListTypeDirectors",
							new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
					errors.add("rbiDefaulterListTypeGroupCompanies",
							new ActionMessage("error.wsdl.rbiDefaulterListTypeCDG.valid.values"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setRBIDefaulterList(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isRbiDefaulter", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setRBIDefaulterList(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getLitigationPending() != null
				&& !requestDTO.getBodyDetails().get(0).getLitigationPending().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getLitigationPending().trim().equalsIgnoreCase("Yes")) {
				reqObj.setLitigationPending("Yes");
				if (requestDTO.getBodyDetails().get(0).getLitigationPendingBy() != null
						&& !requestDTO.getBodyDetails().get(0).getLitigationPendingBy().trim().isEmpty()) {
					reqObj.setLitigationPendingBy(requestDTO.getBodyDetails().get(0).getLitigationPendingBy().trim());
				} else {
					errors.add("litigationPendingBy", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getLitigationPending().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setLitigationPending(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isLitigation", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setLitigationPending(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getInterestOfDirectors() != null
				&& !requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim().equalsIgnoreCase("Yes")) {
				reqObj.setInterestOfDirectors("Yes");
				if (requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType() != null
						&& !requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType().trim().isEmpty()) {
					reqObj.setInterestOfDirectorsType(
							requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType().trim());
				} else {
					errors.add("interestOfDirectorsType", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setInterestOfDirectors(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isInterestOfBank", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setInterestOfDirectors(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getAdverseRemark() != null
				&& !requestDTO.getBodyDetails().get(0).getAdverseRemark().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getAdverseRemark().trim().equalsIgnoreCase("Yes")) {
				reqObj.setAdverseRemark("Yes");
				if (requestDTO.getBodyDetails().get(0).getAdverseRemarkValue() != null
						&& !requestDTO.getBodyDetails().get(0).getAdverseRemarkValue().trim().isEmpty()) {
					reqObj.setAdverseRemarkValue(requestDTO.getBodyDetails().get(0).getAdverseRemarkValue().trim());
				} else {
					errors.add("adverseRemarkValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getAdverseRemark().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setAdverseRemark(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isAdverseRemark", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setAdverseRemark(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getAudit() != null
				&& !requestDTO.getBodyDetails().get(0).getAudit().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getAudit().trim().equalsIgnoreCase("RBI Audit")
					|| requestDTO.getBodyDetails().get(0).getAudit().trim().equalsIgnoreCase("External Audit")
					|| requestDTO.getBodyDetails().get(0).getAudit().trim().equalsIgnoreCase("Internal Audit")) {
				reqObj.setAudit(requestDTO.getBodyDetails().get(0).getAudit().trim());
			} else {
				errors.add("auditType", new ActionMessage("error.string.cri.audit.default.values"));
			}
		} else {
			reqObj.setAudit("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getIndustryExposurePercent()
				&& !requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim().isEmpty()) {
			reqObj.setIndustryExposurePercent(requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim());
		} else {
			reqObj.setIndustryExposurePercent("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerDirector() != null
				&& !requestDTO.getBodyDetails().get(0).getIsBorrowerDirector().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsBorrowerDirector().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsBorrowerDirector("Yes");
				if (requestDTO.getBodyDetails().get(0).getBorrowerDirectorValue() != null
						&& !requestDTO.getBodyDetails().get(0).getBorrowerDirectorValue().trim().isEmpty()) {
					reqObj.setBorrowerDirectorValue(
							requestDTO.getBodyDetails().get(0).getBorrowerDirectorValue().trim());
				} else {
					errors.add("borrowerDirectorValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsBorrowerDirector().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsBorrowerDirector(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isBorrowerDirector", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsBorrowerDirector(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerPartner() != null
				&& !requestDTO.getBodyDetails().get(0).getIsBorrowerPartner().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsBorrowerPartner().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsBorrowerPartner("Yes");
				if (requestDTO.getBodyDetails().get(0).getBorrowerPartnerValue() != null
						&& !requestDTO.getBodyDetails().get(0).getBorrowerPartnerValue().trim().isEmpty()) {
					reqObj.setBorrowerPartnerValue(requestDTO.getBodyDetails().get(0).getBorrowerPartnerValue().trim());
				} else {
					errors.add("borrowerPartnerValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsBorrowerPartner().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsBorrowerPartner(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isBorrowerPartner", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsBorrowerPartner(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank() != null
				&& !requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsDirectorOfOtherBank("Yes");
				if (requestDTO.getBodyDetails().get(0).getDirectorOfOtherBankValue() != null
						&& !requestDTO.getBodyDetails().get(0).getDirectorOfOtherBankValue().trim().isEmpty()) {
					reqObj.setDirectorOfOtherBankValue(
							requestDTO.getBodyDetails().get(0).getDirectorOfOtherBankValue().trim());
				} else {
					errors.add("directorOfOtherBankValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsDirectorOfOtherBank(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isDirecOtherBank", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsDirectorOfOtherBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank() != null
				&& !requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsRelativeOfHDFCBank("Yes");
				if (requestDTO.getBodyDetails().get(0).getRelativeOfHDFCBankValue() != null
						&& !requestDTO.getBodyDetails().get(0).getRelativeOfHDFCBankValue().trim().isEmpty()) {
					reqObj.setRelativeOfHDFCBankValue(
							requestDTO.getBodyDetails().get(0).getRelativeOfHDFCBankValue().trim());
				} else {
					errors.add("relativeOfHDFCBankValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsRelativeOfHDFCBank(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isHdfcDirecRltv", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsRelativeOfHDFCBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman() != null
				&& !requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsRelativeOfChairman("Yes");
				if (requestDTO.getBodyDetails().get(0).getRelativeOfChairmanValue() != null
						&& !requestDTO.getBodyDetails().get(0).getRelativeOfChairmanValue().trim().isEmpty()) {
					reqObj.setRelativeOfChairmanValue(
							requestDTO.getBodyDetails().get(0).getRelativeOfChairmanValue().trim());
				} else {
					errors.add("relativeOfChairmanValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsRelativeOfChairman(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isRelativeOfChairman", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsRelativeOfChairman(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks() != null
				&& !requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsPartnerRelativeOfBanks("Yes");
				if (requestDTO.getBodyDetails().get(0).getPartnerRelativeOfBanksValue() != null
						&& !requestDTO.getBodyDetails().get(0).getPartnerRelativeOfBanksValue().trim().isEmpty()) {
					reqObj.setPartnerRelativeOfBanksValue(
							requestDTO.getBodyDetails().get(0).getPartnerRelativeOfBanksValue().trim());
				} else {
					errors.add("partnerRelativeOfBanksValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsPartnerRelativeOfBanks(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isPartnerOtherBank", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsPartnerRelativeOfBanks(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank() != null
				&& !requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank().trim().equalsIgnoreCase("Yes")) {
				reqObj.setIsShareholderRelativeOfBank("Yes");
				if (requestDTO.getBodyDetails().get(0).getShareholderRelativeOfBankValue() != null
						&& !requestDTO.getBodyDetails().get(0).getShareholderRelativeOfBankValue().trim().isEmpty()) {
					reqObj.setShareholderRelativeOfBankValue(
							requestDTO.getBodyDetails().get(0).getShareholderRelativeOfBankValue().trim());
				} else {
					errors.add("shareholderRelativeOfBankValue", new ActionMessage("error.string.mandatory"));
				}
			} else if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank().trim()
					.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsShareholderRelativeOfBank(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isSubstantialOtherBank", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsShareholderRelativeOfBank(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfDirector() != null
				&& !requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfDirector().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfDirector().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfDirector().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				reqObj.setIsShareholderRelativeOfDirector(DEFAULT_VALUE_FOR_CRI_INFO);
			} else {
				errors.add("isSubstantialRltvHdfcOther", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setIsShareholderRelativeOfDirector(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if(requestDTO.getBodyDetails().get(0).getEvent() == "Rest_create_customer") {
			reqObj.setInfraFinance(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setInfraFinanceType("");
			reqObj.setProjectFinance(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setKisanCreditCard(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setPermSSICert(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setBackedByGovt(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setPrioritySector(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setCapitalMarketExposure(DEFAULT_VALUE_FOR_CRI_INFO);
			reqObj.setRealEstateExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}else {
		if (requestDTO.getBodyDetails().get(0).getInfraFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getInfraFinance().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getInfraFinance().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getInfraFinance().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setInfraFinance(requestDTO.getBodyDetails().get(0).getInfraFinance().trim());
			} else {
				errors.add("isInfrastructureFinanace", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setInfraFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getInfraFinanceType() != null
				&& !requestDTO.getBodyDetails().get(0).getInfraFinanceType().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getInfraFinanceType().trim().length()>19)) {
				try {
					Object obj = masterObj.getMasterData("entryCode",
							Long.parseLong(requestDTO.getBodyDetails().get(0).getInfraFinanceType().trim()));
					if (obj != null) {
						ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
						if ("INFRA_FINANACE_TYPE".equals(codeEntry.getCategoryCode())) {
							reqObj.setInfraFinanceType((codeEntry).getEntryCode());
						} else {
							errors.add("infrastructFinTypeError", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("infrastructFinTypeError", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("infrastructFinTypeError", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("infrastructFinTypeError", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			reqObj.setInfraFinanceType("");
		}
		
		if (requestDTO.getBodyDetails().get(0).getProjectFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getProjectFinance().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getProjectFinance().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getProjectFinance().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setProjectFinance(requestDTO.getBodyDetails().get(0).getProjectFinance().trim());
			} else {
				errors.add("isProjectFinance", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setProjectFinance(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getKisanCreditCard() != null
				&& !requestDTO.getBodyDetails().get(0).getKisanCreditCard().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getKisanCreditCard().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getKisanCreditCard().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setKisanCreditCard(requestDTO.getBodyDetails().get(0).getKisanCreditCard().trim());
			} else {
				errors.add("isKisanCreditCard", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setKisanCreditCard(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getPermSSICert() != null
				&& !requestDTO.getBodyDetails().get(0).getPermSSICert().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getPermSSICert().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getPermSSICert().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setPermSSICert(requestDTO.getBodyDetails().get(0).getPermSSICert().trim());
			} else {
				errors.add("isPermenentSsiCert", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setPermSSICert(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getBackedByGovt() != null
				&& !requestDTO.getBodyDetails().get(0).getBackedByGovt().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getBackedByGovt().trim().equalsIgnoreCase("State")
					|| requestDTO.getBodyDetails().get(0).getBackedByGovt().trim().equalsIgnoreCase("Central")
					|| requestDTO.getBodyDetails().get(0).getBackedByGovt().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setBackedByGovt(requestDTO.getBodyDetails().get(0).getBackedByGovt().trim());
			} else {
				errors.add("isBackedByGovt", new ActionMessage("error.string.cri.backedByGov.default.values"));
			}
		} else {
			reqObj.setBackedByGovt(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getPrioritySector() != null
				&& !requestDTO.getBodyDetails().get(0).getPrioritySector().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getPrioritySector().trim().equalsIgnoreCase("Yes") || requestDTO
					.getBodyDetails().get(0).getPrioritySector().trim().equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setPrioritySector(requestDTO.getBodyDetails().get(0).getPrioritySector().trim());
			} else {
				errors.add("isPrioritySector", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setPrioritySector(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getCapitalMarketExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setCapitalMarketExposure(requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim());
			} else {
				errors.add("isCapitalMarketExpos", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setCapitalMarketExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}

		if (requestDTO.getBodyDetails().get(0).getRealEstateExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim().equalsIgnoreCase("Yes")
					|| requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim()
							.equalsIgnoreCase(DEFAULT_VALUE_FOR_CRI_INFO)) {
				// Changed on 10-MAY-2016 : FOR CRI Fields related CR
				reqObj.setRealEstateExposure(requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim());
			} else {
				errors.add("isRealEstateExpos", new ActionMessage("error.string.cri.default.values"));
			}
		} else {
			reqObj.setRealEstateExposure(DEFAULT_VALUE_FOR_CRI_INFO);
		}
	}
		// -------- END ------------

		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentPM() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim().length() < 23) {
				reqObj.setGrossInvestmentPM(requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim());
			} else {
				errors.add("GrossInvestmentPM", new ActionMessage("error.string.length"));
			}
		} else {
			reqObj.setGrossInvestmentPM("0");
		}
		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim().isEmpty()) {
			reqObj.setGrossInvestmentEquip(requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim());
		} else {
			reqObj.setGrossInvestmentEquip("0");
		}

		/*
		 * Infrastructure Finance Field value will be set as 'No' by default. Hence,
		 * Infra Finance Type field value will not be validated. So commented below code
		 * 
		 * 
		 * -------------------------- 10-MAY-2016 ------------- As per the change
		 * request raised by Bank, CRI fields value should get fetched as it is in WSDL
		 * file. Ignore validation part related to 'Infrastructure Type' and 'Add
		 * Facility Details'.
		 * 
		 * Following field values to be set as received in WSDL request. 1.
		 * Infrastructure Finance 2. Infra Type 3. Project Finance 4. Kisan Credit Card
		 * 5. Permanent SSI cert 6. Backed by Govt. 7. Priority/Non priority sector 8.
		 * Capital market exposure 9. Real Estate Exposure
		 * 
		 */

	

		if (requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim().isEmpty()) {
			reqObj.setAvgAnnualTurnover(requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim());
		} else {
			reqObj.setAvgAnnualTurnover("");
		}

		// reqObj.setBusinessGroupExposureLimit(requestDTO.getBodyDetails().get(0).getBusinessGroupExposureLimit());
		if (requestDTO.getBodyDetails().get(0).getFirstYear() != null
				&& !requestDTO.getBodyDetails().get(0).getFirstYear().trim().isEmpty()) {
			try {
				Criterion c1 = Property.forName("entryName")
						.eq(requestDTO.getBodyDetails().get(0).getFirstYear().trim());
				Criterion c2 = Property.forName("categoryCode").eq("CRI_TENURE_YEAR");
				DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
				List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
				if (objectList != null && objectList.size() > 0) {
					String entryCode = ((ICommonCodeEntry) objectList.get(0)).getEntryCode();
					reqObj.setFirstYear(entryCode);
				} else {
					errors.add("firstYear", new ActionMessage("error.invalid.field.value"));
				}
			} catch (Exception e) {
				errors.add("firstYear", new ActionMessage("error.invalid.field.value"));
			}
		} else {
			reqObj.setFirstYear("");
		}

		if (requestDTO.getBodyDetails().get(0).getFirstYearTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim().isEmpty()) {
			reqObj.setFirstYearTurnover(requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim());
		} else {
			reqObj.setFirstYearTurnover("");
		}

		if (requestDTO.getBodyDetails().get(0).getTurnoverCurrency() != null
				&& !requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim().isEmpty()) {
			Object obj = masterObj.getObjectByEntityNameAndCountryISOCode("actualForexFeedEntry",
					requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim(), "turnoverCurrency", errors);
			if (!(obj instanceof ActionErrors)) {
				reqObj.setTurnoverCurrency(((IForexFeedEntry) obj).getCurrencyIsoCode());
			}
		} else {
			reqObj.setTurnoverCurrency("INR");
		}

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {

			if (requestDTO.getBodyDetails().get(0).getCoBorrowerDetailsList() != null
					&& requestDTO.getBodyDetails().get(0).getCoBorrowerDetailsList().getCoBorrowerDetails() != null) {

				if (ICMSConstant.YES.equals(requestDTO.getBodyDetails().get(0).getCoBorrowerDetailsInd())) {
					reqObj.setCoBorrowerDetailsInd(ICMSConstant.YES);
				} else {
					if (StringUtils.isBlank(requestDTO.getBodyDetails().get(0).getCoBorrowerDetailsInd())) {
						errors.add("coBorrowerDetailsInd", new ActionMessage("error.coborrower.ind.mandatory"));
					}
					reqObj.setCoBorrowerDetailsInd(ICMSConstant.NO);
				}

				PartyCoBorrowerDetailsRestRequestDTO coBorrowerDetails = requestDTO.getBodyDetails().get(0)
						.getCoBorrowerDetailsList();
				List<CoBorrowerDetailsRestRequestDTO> coBorrowerDetailsList = coBorrowerDetails.getCoBorrowerDetails();

				StringBuilder liabIds = new StringBuilder();
				String coBorrowerLiabIdsNew = "";

				for (CoBorrowerDetailsRestRequestDTO coBorrower : coBorrowerDetailsList) {

					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId())) {

						List<String> coBorrowerLiabIdList = UIUtil.getListFromDelimitedString(coBorrowerLiabIdsNew,
								",");

						if (coBorrower.getCoBorrowerLiabId().trim().length() > 16) {
							errors.add("coBorrowerLiabId", new ActionMessage("error.coborrower.size.coBorrowerLiabId",
									coBorrower.getCoBorrowerLiabId()));
						}

						if (!CollectionUtils.isEmpty(coBorrowerLiabIdList)) {
							if (coBorrowerLiabIdList.contains(coBorrower.getCoBorrowerLiabId()))
								errors.add("coBorrowerLiabId",
										new ActionMessage("error.coborrower.duplicate.coBorrowerLiabId",
												coBorrower.getCoBorrowerLiabId()));
						} else {
							coBorrower.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId().trim());
							liabIds.append(coBorrower.getCoBorrowerLiabId().trim()).append(",");

							coBorrowerLiabIdsNew = liabIds.toString();

						}

					} else {
						coBorrower.setCoBorrowerLiabId("");
					}

					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerName())) {

						if (coBorrower.getCoBorrowerName().trim().length() > 2000) {
							errors.add("coBorrowerName", new ActionMessage("error.coborrower.size.coBorrowerName",
									coBorrower.getCoBorrowerName()));
						} else {
							coBorrower.setCoBorrowerName(coBorrower.getCoBorrowerName().trim());
						}

					} else {
						coBorrower.setCoBorrowerName("");
					}
				}
				reqObj.setCoBorrowerDetailsList(coBorrowerDetails);
			}
		} // End NPA COBORROWER

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List<PartyVendorDetailsRestRequestDTO> venList = new LinkedList<PartyVendorDetailsRestRequestDTO>();

			if (requestDTO.getBodyDetails().get(0).getVendorDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getVendorDetReqList().isEmpty()) {

				List<String> vendorList = new LinkedList<String>();

				for (PartyVendorDetailsRestRequestDTO vendorDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getVendorDetReqList()) {

					PartyVendorDetailsRestRequestDTO partyVenDTO = new PartyVendorDetailsRestRequestDTO();

					if (vendorDetReqDTO.getVendorName() != null && !vendorDetReqDTO.getVendorName().trim().isEmpty()) {

						ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
						try {
							if (vendorList != null && vendorList.contains(vendorDetReqDTO.getVendorName().trim() + "::"
									+ vendorDetReqDTO.getVendorName().trim())) {
								errors.add("Vendor", new ActionMessage("error.string.duplicatesvendor.name",
										vendorDetReqDTO.getVendorName().trim()));
							} else {
								if ("Rest_update_customer"
										.equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
									if (customerDAO.checkVendorExistsInSameParty(vendorDetReqDTO.getVendorName().trim(),
											requestDTO.getBodyDetails().get(0).getClimsPartyId())) {
										errors.add("vendor", new ActionMessage("error.string.duplicatesvendor.name",
												vendorDetReqDTO.getVendorName().trim()));
									} else {
										vendorList.add(vendorDetReqDTO.getVendorName().trim() + "::"
												+ vendorDetReqDTO.getVendorName().trim());
									}

								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					if (vendorDetReqDTO.getVendorName() != null && !vendorDetReqDTO.getVendorName().trim().isEmpty()) {
						partyVenDTO.setVendorName(vendorDetReqDTO.getVendorName().trim());
					}

					venList.add(partyVenDTO);
				}
			} else {
				errors.add("vendorName", new ActionMessage("error.string.vendor.empty"));
			}
			reqObj.setVendorDetReqList(venList);
		}

		if ("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List<PartycriFacilityDetailsRestRequestDTO> criFacList = new LinkedList<PartycriFacilityDetailsRestRequestDTO>();

			if (requestDTO.getBodyDetails().get(0).getCriFacilityList() != null
					&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().isEmpty()) {

				for (PartycriFacilityDetailsRestRequestDTO criFacDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getCriFacilityList()) {
					MILimitUIHelper helper = new MILimitUIHelper();
					SBMILmtProxy proxy = helper.getSBMILmtProxy();
					String facName = criFacDetReqDTO.getFacilityFor().trim();
					String referenceId = reqObj.getClimsPartyId();
					String strFacilityAmount = "";

					PartycriFacilityDetailsRestRequestDTO partycriFacDTO = new PartycriFacilityDetailsRestRequestDTO();
					if (criFacDetReqDTO.getFacilityFor() != null
							&& !criFacDetReqDTO.getFacilityFor().trim().isEmpty()) {
						partycriFacDTO.setFacilityFor(criFacDetReqDTO.getFacilityFor().trim());
					} else {
						errors.add("facilityFor", new ActionMessage("error.string.mandatory"));
					}
					if (criFacDetReqDTO.getFacName() != null && !criFacDetReqDTO.getFacName().trim().isEmpty()) {

						List lbValList = new ArrayList();
						// String facName = criFacDetReqDTO.getFacilityFor().trim();
						// String referenceId = reqObj.getClimsPartyId();
						try {
							if (facName.equals("Capital Market Exposure")
									|| facName.equals("Priority/Non priority Sector")
									|| facName.equals("Real Estate Exposure")) {
								List lmtList = proxy.getLimitListByFacilityFor(referenceId, facName);
								if (lmtList != null && lmtList.size() > 0) {
									String label;
									String value;
									for (int i = 0; i < lmtList.size(); i++) {
										LimitListSummaryItemBase limitSummaryItem = (LimitListSummaryItemBase) lmtList
												.get(i);
										label = limitSummaryItem.getCmsLimitId() + " - "
												+ limitSummaryItem.getProdTypeCode();
										value = limitSummaryItem.getCmsLimitId();
										LabelValueBean lvBean = new LabelValueBean(label, label);
										lbValList.add(lvBean);
									}
								}
							} else {

								List lmtList = proxy.getLimitSummaryListByCustID(referenceId);
								if (lmtList != null && lmtList.size() > 0) {
									String label;
									String value;
									for (int i = 0; i < lmtList.size(); i++) {
										LimitListSummaryItemBase limitSummaryItem = (LimitListSummaryItemBase) lmtList
												.get(i);
										label = limitSummaryItem.getCmsLimitId() + " - "
												+ limitSummaryItem.getProdTypeCode();
										value = limitSummaryItem.getCmsLimitId();
										LabelValueBean lvBean = new LabelValueBean(label, label);
										lbValList.add(lvBean);
									}
								}
							}
						} catch (LimitException e) {
							e.printStackTrace();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						String ReqFacName = criFacDetReqDTO.getFacName().trim();
						System.out.println("FacName in Rest API : " + ReqFacName);
						boolean flag = false;
						for (int i = 0; i < lbValList.size(); i++) {
							LabelValueBean fac = (LabelValueBean) lbValList.get(i);
							String lmtApprId = fac.getValue().split("-")[0].trim();
							System.out.println("with equalsIgnoreCase " + ReqFacName + " = " + lmtApprId + " : "
									+ lmtApprId.equalsIgnoreCase(ReqFacName));
							if (lmtApprId.equalsIgnoreCase(ReqFacName)) {
								System.out.println(
										"lmtApprId and FacName matched : " + criFacDetReqDTO.getFacName().trim());
								System.out.println("FacName : " + fac.getValue());
								partycriFacDTO.setFacName(fac.getValue());
								flag = true;

								try {
									List lmtListAmt = proxy.getLimitSummaryListByCustID(referenceId, ReqFacName);
									if (lmtListAmt != null && lmtListAmt.size() > 0) {
										String label;
										String value;
										for (int j = 0; j < lmtListAmt.size(); j++) {
											LimitListSummaryItemBase limitSummaryItem = (LimitListSummaryItemBase) lmtListAmt
													.get(j);
											strFacilityAmount = limitSummaryItem.getActualSecCoverage();
											System.out.println("facility Amount is : " + strFacilityAmount);
										}

									}

								} catch (LimitException e) {
									e.printStackTrace();
								} catch (RemoteException e) {
									e.printStackTrace();
								}
								break;
							}
						}
						if (flag == false) {
							errors.add("facName", new ActionMessage("error.string.notInFacList"));
						}

					} else {
						errors.add("facName", new ActionMessage("error.string.mandatory"));
					}

					if (criFacDetReqDTO.getFacAmt() != null && !criFacDetReqDTO.getFacAmt().trim().isEmpty()) {
						if (ASSTValidator.isNumeric(criFacDetReqDTO.getFacAmt().trim()))
							partycriFacDTO.setFacAmt(criFacDetReqDTO.getFacAmt().trim());
						else {
							errors.add("facAmt", new ActionMessage("error.amount.numbers.format"));
						}
					} else {
						if (strFacilityAmount != null && !strFacilityAmount.trim().isEmpty()) {
							partycriFacDTO.setFacAmt(strFacilityAmount);
						} else {
							errors.add("facAmt", new ActionMessage("error.facAmt.notFound"));
						}
					}

					criFacList.add(partycriFacDTO);					
				}
			}
			reqObj.setCriFacilityList(criFacList);
		}

		// System info (one-to-many)
		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List<PartySystemDetailsRestRequestDTO> sysList = new LinkedList<PartySystemDetailsRestRequestDTO>();

			if (requestDTO.getBodyDetails().get(0).getSystemDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getSystemDetReqList().isEmpty()) {

				List<String> systemList = new LinkedList<String>();

				for (PartySystemDetailsRestRequestDTO systemDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getSystemDetReqList()) {

					PartySystemDetailsRestRequestDTO partySysDTO = new PartySystemDetailsRestRequestDTO();

					// systemId value should be unique for each system Name. Below code added to
					// validate accordingly.
					if (systemDetReqDTO.getSystem() != null && !systemDetReqDTO.getSystem().trim().isEmpty()
							&& systemDetReqDTO.getSystemId() != null
							&& !systemDetReqDTO.getSystemId().trim().isEmpty()) {

						ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
						try {
							if (systemList != null && systemList.contains(
									systemDetReqDTO.getSystem().trim() + "::" + systemDetReqDTO.getSystemId().trim())) {
								errors.add("system", new ActionMessage("error.wsdl.duplicatesystem.inlist",
										systemDetReqDTO.getSystemId().trim()));
							} else {

								if ("Rest_update_customer"
										.equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
									if (customerDAO.checkSystemExistsInOtherParty(systemDetReqDTO.getSystem().trim(),
											systemDetReqDTO.getSystemId().trim(),
											requestDTO.getBodyDetails().get(0).getClimsPartyId())) {
										errors.add("system", new ActionMessage("error.wsdl.duplicatesystem.id",
												systemDetReqDTO.getSystemId().trim()));
									} else {
										systemList.add(systemDetReqDTO.getSystem().trim() + "::"
												+ systemDetReqDTO.getSystemId().trim());
									}
								} else {
									if (customerDAO.checkSystemExists(systemDetReqDTO.getSystem().trim(),
											systemDetReqDTO.getSystemId().trim())) {
										errors.add("system", new ActionMessage("error.wsdl.duplicatesystem.id",
												systemDetReqDTO.getSystemId().trim()));
									} else {
										systemList.add(systemDetReqDTO.getSystem().trim() + "::"
												+ systemDetReqDTO.getSystemId().trim());
									}
								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					if (systemDetReqDTO.getSystem() != null && !systemDetReqDTO.getSystem().trim().isEmpty()) {
						try {
							Criterion c1 = Property.forName("entryCode").eq(systemDetReqDTO.getSystem().trim());
							Criterion c2 = Property.forName("categoryCode").eq("SYSTEM");
							DetachedCriteria criteria = DetachedCriteria.forEntityName("entryCode").add(c1).add(c2);
							List<Object> objectList = masterObj.getObjectListBySpecification(criteria);
							if (objectList != null && !objectList.isEmpty() && objectList.size() == 1) {
								partySysDTO.setSystem(systemDetReqDTO.getSystem().trim());
							} else {
								errors.add("system", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("system", new ActionMessage("error.invalid.field.value"));
						}
					} else {
						errors.add("system", new ActionMessage("error.string.mandatory"));
					}

					if (systemDetReqDTO.getSystemId() != null && !systemDetReqDTO.getSystemId().trim().isEmpty()) {
						if (systemDetReqDTO.getSystemId().trim().length() > 16) {
							errors.add("systemId", new ActionMessage("error.systemCustomerId.length.exceeded"));
						} else {
							partySysDTO.setSystemId(systemDetReqDTO.getSystemId().trim());
						}
					} else {
						errors.add("systemId", new ActionMessage("error.string.mandatory"));
					}

					sysList.add(partySysDTO);
				}
			} else {
				errors.add("systemId", new ActionMessage("error.string.system.empty"));
			}
			reqObj.setSystemDetReqList(sysList);
		}

		ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");

		List<PartyBankingMethodDetailsRestRequestDTO> bankList = new LinkedList<PartyBankingMethodDetailsRestRequestDTO>();
		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethod().trim().isEmpty()) {
			if ("OUTSIDECONSORTIUM".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
					|| "OUTSIDEMULTIPLE".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
					|| "MULTIPLE".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())
					|| "CONSORTIUM".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getBankingMethod().trim())) {
				// Banking Method Info(one-to-many)
				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().isEmpty()) {

					Boolean defaultNodal = true;
					Boolean defaultLead = true;
					Boolean Nodalflag = true;
					Boolean Leadflag = true;
					PartyBankingMethodDetailsRestRequestDTO systemBankDTO = new PartyBankingMethodDetailsRestRequestDTO();
					List systemBankList = new ArrayList();
					try {
						systemBankList = (ArrayList) systemBankProxy.getAllActual();
						OBSystemBank hdfc = (OBSystemBank) systemBankList.get(0);
						systemBankDTO.setBankType("S");
						systemBankDTO.setBranchId(Long.toString(hdfc.getId()));
					} catch (TrxParameterException e) {
						e.printStackTrace();
					} catch (TransactionException e) {
						e.printStackTrace();
					}

					for (PartyBankingMethodDetailsRestRequestDTO bankMthdDetReqDTO : requestDTO.getBodyDetails().get(0)
							.getBankingMethodDetailList()) {

						PartyBankingMethodDetailsRestRequestDTO partyBankDtlsRequestDTO = new PartyBankingMethodDetailsRestRequestDTO();

						if (bankMthdDetReqDTO.getNodal() != null && !bankMthdDetReqDTO.getNodal().trim().isEmpty()) {
							if (!(bankMthdDetReqDTO.getNodal().trim().equals("Y")
									|| bankMthdDetReqDTO.getNodal().trim().equals("N"))) {
								errors.add("nodal", new ActionMessage("error.invalid.field.value"));
							}
						}
						if (bankMthdDetReqDTO.getLead() != null && !bankMthdDetReqDTO.getLead().trim().isEmpty()) {
							if (!(bankMthdDetReqDTO.getLead().trim().equals("Y")
									|| bankMthdDetReqDTO.getLead().trim().equals("N"))) {
								errors.add("lead", new ActionMessage("error.invalid.field.value"));
							}
						}
						DefaultLogger.debug(this, "bankMthdDetReqDTO.getLead:::" + bankMthdDetReqDTO.getLead());
						if (null != bankMthdDetReqDTO.getLead() && !bankMthdDetReqDTO.getLead().trim().isEmpty()) {
							if ("Y".equalsIgnoreCase(bankMthdDetReqDTO.getLead().trim())) {
								if (Leadflag) {
									defaultLead = false;
								} else {
									errors.add("Lead", new ActionMessage("error.string.multiple"));
								}
							}
						}

						if (null != bankMthdDetReqDTO.getNodal() && !bankMthdDetReqDTO.getNodal().trim().isEmpty()) {
							if ("Y".equalsIgnoreCase(bankMthdDetReqDTO.getNodal().trim())) {
								if (Nodalflag) {
									defaultNodal = false;
								} else {
									errors.add("Nodal", new ActionMessage("error.string.multiple"));
								}

							}
						}

						if (defaultLead == false && Leadflag == true) {
							partyBankDtlsRequestDTO.setLead(bankMthdDetReqDTO.getLead().trim());
							Leadflag = false;
						} else {
							partyBankDtlsRequestDTO.setLead("");
						}
						if (defaultNodal == false && Nodalflag == true) {
							partyBankDtlsRequestDTO.setNodal(bankMthdDetReqDTO.getNodal().trim());
							Nodalflag = false;
						} else {
							partyBankDtlsRequestDTO.setNodal("");
						}

						if (bankMthdDetReqDTO.getBankName() != null
								&& !bankMthdDetReqDTO.getBankName().trim().isEmpty()) {
							if (bankMthdDetReqDTO.getBranchName() != null
									&& !bankMthdDetReqDTO.getBranchName().trim().isEmpty()) {
								partyBankDtlsRequestDTO.setBankType("O");
								try {
									String bankCode = null;
									String bankname = bankMthdDetReqDTO.getBankName().trim();
									String branchName = bankMthdDetReqDTO.getBranchName().trim();

									IOtherBankDAO otherBankDao = (IOtherBankDAO) BeanHouse.get("otherBankDao");
									List<OBOtherBank> OtherBankList = otherBankDao.getOtherBankList(bankCode, bankname,
											branchName, "");
									if (OtherBankList != null) {
										partyBankDtlsRequestDTO.setBankType("O");
										partyBankDtlsRequestDTO
												.setBranchId(OtherBankList.get(0).getOtherBranchId().trim());
										if (bankMthdDetReqDTO.getRevisedEmailIds() != null
												&& !bankMthdDetReqDTO.getRevisedEmailIds().trim().isEmpty()) {
											if (!ASSTValidator
													.isValidEmail(bankMthdDetReqDTO.getRevisedEmailIds().trim())) {
												partyBankDtlsRequestDTO.setRevisedEmailIds(
														bankMthdDetReqDTO.getRevisedEmailIds().trim());
											} else {
												errors.add("revisedEmailIds",
														new ActionMessage("error.string.email.format"));
											}
										} else {
											partyBankDtlsRequestDTO.setRevisedEmailIds("");
										}
										bankList.add(partyBankDtlsRequestDTO);

									} else {
										errors.add("branchName/bankName",
												new ActionMessage("error.invalid.field.value"));
									}
								} catch (Exception e) {
									DefaultLogger.error(this, e.getMessage());
									errors.add("branchName/bankName", new ActionMessage("error.invalid.field.value"));
								}

							} else {
								errors.add("branchName", new ActionMessage("error.string.banking.bankName.empty"));
							}
						} else {
							errors.add("bankName", new ActionMessage("error.string.mandatory"));
						}
					}
					// if(defaultNodal){
					// systemBankDTO.setNodal("Y");
					// }else{
					// systemBankDTO.setNodal(null);
					// }
					// if(defaultLead){
					// systemBankDTO.setLead("Y");
					// }else{
					// systemBankDTO.setLead(null);
					// }
					bankList.add(systemBankDTO);
				}

				// else{
				// errors.add("bankListEmpty", new ActionMessage("error.string.banking.empty"));
				// }
			}
		}
		reqObj.setBankingMethodDetailList(bankList);

		// ---------------------to do
		List<PartyDirectorDetailsRestRequestDTO> directorList = new LinkedList<PartyDirectorDetailsRestRequestDTO>();
		// Director info(one-to-many)
		if (requestDTO.getBodyDetails().get(0).getDirectorDetailList() != null
				&& !requestDTO.getBodyDetails().get(0).getDirectorDetailList().isEmpty()) {
			for (PartyDirectorDetailsRestRequestDTO directorDetReqDTO : requestDTO.getBodyDetails().get(0)
					.getDirectorDetailList()) {

				PartyDirectorDetailsRestRequestDTO partyDirectorDetReqDTO = new PartyDirectorDetailsRestRequestDTO();

				if (directorDetReqDTO.getRelatedType() != null
						&& !directorDetReqDTO.getRelatedType().trim().isEmpty()) {
					if(!(directorDetReqDTO.getRelatedType().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(directorDetReqDTO.getRelatedType().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("RELATED_TYPE".equals(codeEntry.getCategoryCode())) {
									partyDirectorDetReqDTO.setRelatedType((codeEntry).getEntryCode());
								} else {
									errors.add("relatedType", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("relatedType", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("relatedType", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("relatedType", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("relatedType", new ActionMessage("error.string.mandatory"));
				}

				if (directorDetReqDTO.getRelationship() != null
						&& !directorDetReqDTO.getRelationship().trim().isEmpty()) {
					if(!(directorDetReqDTO.getRelationship().trim().length()>19)) {
						try {
							Object obj = masterObj.getMasterData("entryCode",
									Long.parseLong(directorDetReqDTO.getRelationship().trim()));
							if (obj != null) {
								ICommonCodeEntry codeEntry = (ICommonCodeEntry) obj;
								if ("RELATIONSHIP_TYPE".equals(codeEntry.getCategoryCode())) {
									partyDirectorDetReqDTO.setRelationship((codeEntry).getEntryCode());
								} else {
									errors.add("relationship", new ActionMessage("error.invalid.field.value"));
								}
							} else {
								errors.add("relationship", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							errors.add("relationship", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("relationship", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("relationship", new ActionMessage("error.string.mandatory"));
				}

				if (directorDetReqDTO.getDirectorEmailId() != null
						&& !directorDetReqDTO.getDirectorEmailId().trim().isEmpty()) {
					partyDirectorDetReqDTO.setDirectorEmailId(directorDetReqDTO.getDirectorEmailId().trim());
				} else {
					partyDirectorDetReqDTO.setDirectorEmailId("");
				}

				if (directorDetReqDTO.getDirectorFaxNo() != null
						&& !directorDetReqDTO.getDirectorFaxNo().trim().isEmpty()) {
					partyDirectorDetReqDTO.setDirectorFaxNo(directorDetReqDTO.getDirectorFaxNo().trim());
				} else {
					partyDirectorDetReqDTO.setDirectorFaxNo("");
				}

				if (directorDetReqDTO.getDirectorTelNo() != null
						&& !directorDetReqDTO.getDirectorTelNo().trim().isEmpty()) {
					partyDirectorDetReqDTO.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo().trim());
				} else {
					partyDirectorDetReqDTO.setDirectorTelNo("");
				}

				if (directorDetReqDTO.getDirectorTelephoneStdCode() != null
						&& !directorDetReqDTO.getDirectorTelephoneStdCode().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorTelephoneStdCode().trim().length() > 5) {
						errors.add("telephoneNoSTDCode",
								new ActionMessage("error.wsdl.directorTelephoneStdCode.length.exceeded"));
					} else {
						partyDirectorDetReqDTO
								.setDirectorTelephoneStdCode(directorDetReqDTO.getDirectorTelephoneStdCode().trim());
					}
				} else {
					partyDirectorDetReqDTO.setDirectorTelephoneStdCode("");
				}

				if (directorDetReqDTO.getDirectorFaxStdCode() != null
						&& !directorDetReqDTO.getDirectorFaxStdCode().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorFaxStdCode().trim().length() > 5) {
						errors.add("faxNoSTDCode", new ActionMessage("error.wsdl.directorFaxStdCode.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorFaxStdCode(directorDetReqDTO.getDirectorFaxStdCode().trim());
					}
				} else {
					partyDirectorDetReqDTO.setDirectorFaxStdCode("");
				}

				if (directorDetReqDTO.getDirectorCountry() != null
						&& !directorDetReqDTO.getDirectorCountry().trim().isEmpty()) {					
					if(!(directorDetReqDTO.getDirectorCountry().trim().length()>19)) {
						try {
							Object objCountry = masterObj.getObjectforMaster("actualCountry",
									new Long(directorDetReqDTO.getDirectorCountry().trim()));
							if (objCountry != null) {
								partyDirectorDetReqDTO
								.setDirectorCountry(Long.toString(((ICountry) objCountry).getIdCountry()));
							} else {
								errors.add("directorCountry", new ActionMessage("error.invalid.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("directorCountry", new ActionMessage("error.invalid.field.value"));
						}	
					}else {
						errors.add("directorCountry", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("directorCountry", new ActionMessage("error.string.mandatory"));
				}

				if (directorDetReqDTO.getDirectorCountry() != null
						&& !directorDetReqDTO.getDirectorCountry().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorRegion() != null
							&& !directorDetReqDTO.getDirectorRegion().trim().isEmpty()) {						
						if(!(directorDetReqDTO.getDirectorRegion().trim().length()>19)) {
							try {
								Object obRegion = masterObj.getObjectforMaster("actualRegion",
										new Long(directorDetReqDTO.getDirectorRegion().trim()));
								if (obRegion != null) {
									partyDirectorDetReqDTO.setDirectorRegion(directorDetReqDTO.getDirectorRegion().trim());
									if (directorDetReqDTO.getDirectorCountry().trim().equalsIgnoreCase(
											Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()))) {
										partyDirectorDetReqDTO.setDirectorCountry(
												Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()));
									} else {
										partyDirectorDetReqDTO.setDirectorCountry(directorDetReqDTO.getDirectorCountry());
										System.out.println("Given Region is not Present in the list of Country");
										errors.add("directorRegion", new ActionMessage("error.region.field.value"));
									}

								} else {
									errors.add("directorRegion", new ActionMessage("error.region.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this, e.getMessage());
								errors.add("directorRegion", new ActionMessage("error.invalid.field.value"));
							}
						}else {
							errors.add("directorRegion", new ActionMessage("error.string.field.length.exceeded"));
						}
					} else {
						errors.add("directorRegion", new ActionMessage("error.string.mandatory"));
					}
				} else {
					errors.add("directorCountry", new ActionMessage("error.string.mandatory"));
				}

				if (partyDirectorDetReqDTO.getDirectorCountry() != null
						&& !partyDirectorDetReqDTO.getDirectorCountry().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorRegion() != null
							&& !directorDetReqDTO.getDirectorRegion().trim().isEmpty()) {

						if (directorDetReqDTO.getDirectorState() != null
								&& !directorDetReqDTO.getDirectorState().trim().isEmpty()) {
							if(!(directorDetReqDTO.getDirectorState().trim().length()>19)) {
								try {
									Object objState = masterObj.getObjectforMaster("actualState",
											new Long(directorDetReqDTO.getDirectorState().trim()));
									if (objState != null) {
										partyDirectorDetReqDTO
										.setDirectorState(directorDetReqDTO.getDirectorState().trim());
										if (directorDetReqDTO.getDirectorRegion().trim().equalsIgnoreCase(
												Long.toString(((IState) objState).getRegionId().getIdRegion()))) {
											partyDirectorDetReqDTO.setDirectorRegion(
													Long.toString(((IState) objState).getRegionId().getIdRegion()));
										} else {
											System.out.println("Given State is not Present in the state list of Region");
											errors.add("directorState", new ActionMessage("error.state.field.value"));
										}

									} else {
										errors.add("directorState", new ActionMessage("error.state.field.value"));
									}
								} catch (Exception e) {
									DefaultLogger.error(this, e.getMessage());
									errors.add("directorState", new ActionMessage("error.invalid.field.value"));
								}
							}else {
								errors.add("directorState", new ActionMessage("error.string.field.length.exceeded"));
							}
						} else {
							errors.add("directorState", new ActionMessage("error.string.mandatory"));
						}
					}
				}

				if (partyDirectorDetReqDTO.getDirectorCountry() != null
						&& !partyDirectorDetReqDTO.getDirectorCountry().trim().isEmpty()) {
					if (partyDirectorDetReqDTO.getDirectorRegion() != null
							&& !partyDirectorDetReqDTO.getDirectorRegion().trim().isEmpty()) {
						if (directorDetReqDTO.getDirectorState() != null
								&& !directorDetReqDTO.getDirectorState().trim().isEmpty()) {
							if (directorDetReqDTO.getDirectorCity() != null
									&& !directorDetReqDTO.getDirectorCity().trim().isEmpty()) {
								if(!(directorDetReqDTO.getDirectorCity().trim().length()>19)) {
									try {
										Object obCity = masterObj.getObjectforMaster("actualCity",
												new Long(directorDetReqDTO.getDirectorCity().trim()));
										if (obCity != null) {
											partyDirectorDetReqDTO
											.setDirectorCity(directorDetReqDTO.getDirectorCity().trim());

											if (directorDetReqDTO.getDirectorState().trim().equalsIgnoreCase(
													Long.toString(((ICity) obCity).getStateId().getIdState()))) {
												partyDirectorDetReqDTO.setDirectorState(
														Long.toString(((ICity) obCity).getStateId().getIdState()));
											} else {
												System.out.println("Given City is not Present in the state list of State");
												errors.add("directorCity", new ActionMessage("error.city.field.value"));
											}

										} else {
											errors.add("directorCity", new ActionMessage("error.city.field.value"));
										}
									} catch (Exception e) {
										DefaultLogger.error(this, e.getMessage());
										errors.add("directorCity", new ActionMessage("error.invalid.field.value"));
									}
								}else {
									errors.add("directorCity", new ActionMessage("error.string.field.length.exceeded"));
								}
							} else {
								errors.add("directorCity", new ActionMessage("error.string.mandatory"));
							}
						}
					}
				}

				if (directorDetReqDTO.getDirectorAddr1() != null
						&& !directorDetReqDTO.getDirectorAddr1().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorAddr1().trim().length() > 75) {
						errors.add("directorAddress1",
								new ActionMessage("error.wsdl.directorAddress1.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorAddr1(directorDetReqDTO.getDirectorAddr1().trim());
					}
				} else {
					errors.add("directorAddress1", new ActionMessage("error.string.mandatory"));
				}

				if (directorDetReqDTO.getDirectorAddr2() != null
						&& !directorDetReqDTO.getDirectorAddr2().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorAddr2().trim().length() > 75) {
						errors.add("directorAddress2",
								new ActionMessage("error.wsdl.directorAddress2.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorAddr2(directorDetReqDTO.getDirectorAddr2().trim());
					}
				} else {
					errors.add("directorAddress2", new ActionMessage("error.string.mandatory"));
				}

				if (directorDetReqDTO.getDirectorAddr3() != null
						&& !directorDetReqDTO.getDirectorAddr3().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorAddr3().trim().length() > 75) {
						errors.add("directorAddress3",
								new ActionMessage("error.wsdl.directorAddress3.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorAddr3(directorDetReqDTO.getDirectorAddr3().trim());
					}
				} else {
					partyDirectorDetReqDTO.setDirectorAddr3("");
				}

				/*
				 * if(directorDetReqDTO.getDirectorPincode()!=null &&
				 * !directorDetReqDTO.getDirectorPincode().isEmpty()){
				 * if(directorDetReqDTO.getDirectorPincode().length() > 6){
				 * errors.add("directorpincodeError", new
				 * ActionMessage("error.wsdl.directorPincode.length.exceeded")); }else{
				 * partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.
				 * getDirectorPincode()); } }
				 */

				// As per the discussion opn date : 11-Nov-2014, pincode value will be first 6
				// characters of given value in WSDL file
				// If First 6 characters contain any alphabet/ special characters, System should
				// validate value
				// An appropriate validation message will be displayed in response.

				if (directorDetReqDTO.getDirectorPincode() != null
						&& !directorDetReqDTO.getDirectorPincode().trim().isEmpty()) {

					if (directorDetReqDTO.getDirectorPincode().trim().length() > 6) {
						// partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.getDirectorPincode().trim().substring(0,
						// 6));
						errors.add("directorpincode", new ActionMessage("error.pinCode.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorPincode(directorDetReqDTO.getDirectorPincode().trim());
					}

					Pattern p = Pattern.compile(".*\\D.*");
					Matcher m = p.matcher(directorDetReqDTO.getDirectorPincode().trim());
					boolean isAlphaNumeric = m.matches();
					if (isAlphaNumeric) {
						errors.add("directorpincode", new ActionMessage("error.pincode.numeric.format"));
					}
				} else {
					partyDirectorDetReqDTO.setDirectorPincode("");
				}

				if (directorDetReqDTO.getPercentageOfControl() != null
						&& !directorDetReqDTO.getPercentageOfControl().trim().isEmpty()) {
					partyDirectorDetReqDTO.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl().trim());
				} else {
					partyDirectorDetReqDTO.setPercentageOfControl("");
				}
				if (directorDetReqDTO.getFullName() != null && !directorDetReqDTO.getFullName().trim().isEmpty()) {
					partyDirectorDetReqDTO.setFullName(directorDetReqDTO.getFullName().trim());
				} else {
					partyDirectorDetReqDTO.setFullName("");
				}

				if (directorDetReqDTO.getNamePrefix() != null && !directorDetReqDTO.getNamePrefix().trim().isEmpty()) {
					partyDirectorDetReqDTO.setNamePrefix(directorDetReqDTO.getNamePrefix().trim());
				} else {
					partyDirectorDetReqDTO.setNamePrefix("");
				}

				if (directorDetReqDTO.getBusinessEntityName() != null
						&& !directorDetReqDTO.getBusinessEntityName().trim().isEmpty()) {
					partyDirectorDetReqDTO.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName().trim());
				} else {
					partyDirectorDetReqDTO.setBusinessEntityName("");
				}

				if (directorDetReqDTO.getDirectorPAN() != null
						&& !directorDetReqDTO.getDirectorPAN().trim().isEmpty()) {
					partyDirectorDetReqDTO.setDirectorPAN(directorDetReqDTO.getDirectorPAN().trim());
				} else {
					partyDirectorDetReqDTO.setDirectorPAN("");
				}

				if (directorDetReqDTO.getDirectorAADHAR() != null
						&& !directorDetReqDTO.getDirectorAADHAR().trim().isEmpty()) {
					partyDirectorDetReqDTO.setDirectorAADHAR(directorDetReqDTO.getDirectorAADHAR().trim());
				} else {
					partyDirectorDetReqDTO.setDirectorAADHAR("");
				}
				/*
				 * if (directorDetReqDTO.getDinNo() != null &&
				 * directorDetReqDTO.getDinNo().length() > 9) { errors.add("dinNoLengthError",
				 * new ActionMessage("error.dinNo.length.exceeded"));
				 * 
				 * } else { boolean nameFlag =
				 * ASSTValidator.isValidAlphaNumStringWithoutSpace(directorDetReqDTO.getDinNo())
				 * ; if (nameFlag == true) errors.add("specialCharacterDinNoError", new
				 * ActionMessage("error.string.invalidCharacter")); }
				 */

				if (directorDetReqDTO.getDinNo() != null && !directorDetReqDTO.getDinNo().trim().isEmpty()) {
					if (directorDetReqDTO.getDinNo().trim().length() > 9) {
						errors.add("dinNo", new ActionMessage("error.dinNo.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDinNo(directorDetReqDTO.getDinNo().trim());
					}
				} else {
					partyDirectorDetReqDTO.setDinNo("");
				}
				/*
				 * else{ errors.add("DinNo", new ActionMessage("error.string.mandatory")); }
				 */

				if (directorDetReqDTO.getDirectorName() != null
						&& !directorDetReqDTO.getDirectorName().trim().isEmpty()) {
					if (directorDetReqDTO.getDirectorName().trim().length() > 50) {
						errors.add("directorName", new ActionMessage("error.directorname.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setDirectorName(directorDetReqDTO.getDirectorName().trim());
					}
				} else {
					partyDirectorDetReqDTO.setDirectorName("");
				}

				if (directorDetReqDTO.getRelatedDUNSNo() != null
						&& !directorDetReqDTO.getRelatedDUNSNo().trim().isEmpty()) {

					if (directorDetReqDTO.getRelatedDUNSNo().trim().length() > 9) {
						errors.add("relatedDUNSNo", new ActionMessage("error.relatedDUNSNo.length.exceeded"));
					} else {
						partyDirectorDetReqDTO.setRelatedDUNSNo(directorDetReqDTO.getRelatedDUNSNo().trim());
					}
				} else {
					partyDirectorDetReqDTO.setRelatedDUNSNo("");
				}

				directorList.add(partyDirectorDetReqDTO);
			}
		} else {
			errors.add("directorList", new ActionMessage("error.string.director.empty"));
		}
		reqObj.setDirectorDetailList(directorList);

		// contact info
		if (requestDTO.getBodyDetails().get(0).getAddress1() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress1().trim().isEmpty()) {
			reqObj.setAddress1(requestDTO.getBodyDetails().get(0).getAddress1().trim());
		} else {
			reqObj.setAddress1("");
		}

		if (requestDTO.getBodyDetails().get(0).getAddress2() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress2().trim().isEmpty()) {
			reqObj.setAddress2(requestDTO.getBodyDetails().get(0).getAddress2().trim());
		} else {
			reqObj.setAddress2("");
		}

		if (requestDTO.getBodyDetails().get(0).getAddress3() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress3().trim().isEmpty()) {
			reqObj.setAddress3(requestDTO.getBodyDetails().get(0).getAddress3().trim());
		} else {
			reqObj.setAddress3("");
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			if(!(requestDTO.getBodyDetails().get(0).getCountry().trim().length()>19)) {
				try {
					Object objCountry = masterObj.getObjectforMaster("actualCountry",
							new Long(requestDTO.getBodyDetails().get(0).getCountry().trim()));
					if (objCountry != null) {
						reqObj.setCountry(Long.toString(((ICountry) objCountry).getIdCountry()));
					} else {
						errors.add("country", new ActionMessage("error.invalid.field.value"));
					}
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage());
					errors.add("country", new ActionMessage("error.invalid.field.value"));
				}
			}else {
				errors.add("country", new ActionMessage("error.string.field.length.exceeded"));
			}
		} else {
			errors.add("country", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRegion() != null
					&& !requestDTO.getBodyDetails().get(0).getRegion().trim().isEmpty()) {
				if(!(requestDTO.getBodyDetails().get(0).getRegion().trim().length()>19)) {
					try {
						Object obRegion = masterObj.getObjectforMaster("actualRegion",
								new Long(requestDTO.getBodyDetails().get(0).getRegion().trim()));
						if (obRegion != null) {
							reqObj.setRegion(requestDTO.getBodyDetails().get(0).getRegion().trim());

							if (requestDTO.getBodyDetails().get(0).getCountry().trim()
									.equalsIgnoreCase(Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()))) {
								reqObj.setCountry(Long.toString(((IRegion) obRegion).getCountryId().getIdCountry()));
							} else {
								reqObj.setCountry(requestDTO.getBodyDetails().get(0).getCountry());
								System.out.println("Given Region is not Present in the list of Country");
								errors.add("region", new ActionMessage("error.region.field.value"));
							}

						} else {
							errors.add("region", new ActionMessage("error.region.field.value"));
						}
					} catch (Exception e) {
						DefaultLogger.error(this, e.getMessage());
						errors.add("region", new ActionMessage("error.invalid.field.value"));
					}
				}else {
					errors.add("region", new ActionMessage("error.string.field.length.exceeded"));
				}
			} else {
				errors.add("region", new ActionMessage("error.string.mandatory"));
			}
		} else {
			errors.add("country", new ActionMessage("error.string.mandatory"));
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRegion() != null
					&& !requestDTO.getBodyDetails().get(0).getRegion().trim().isEmpty()) {

				if (requestDTO.getBodyDetails().get(0).getState() != null
						&& !requestDTO.getBodyDetails().get(0).getState().trim().isEmpty()) {
					if(!(requestDTO.getBodyDetails().get(0).getState().trim().length()>19)) {
						try {					
							Object objState = masterObj.getObjectforMaster("actualState",
									new Long(requestDTO.getBodyDetails().get(0).getState().trim()));
							if (objState != null) {
								reqObj.setState(requestDTO.getBodyDetails().get(0).getState().trim());

								if (requestDTO.getBodyDetails().get(0).getRegion().trim()
										.equalsIgnoreCase(Long.toString(((IState) objState).getRegionId().getIdRegion()))) {
									reqObj.setRegion(Long.toString(((IState) objState).getRegionId().getIdRegion()));
								} else {
									System.out.println("Given State is not Present in the state list of Region");
									errors.add("state", new ActionMessage("error.state.field.value"));
								}

							} else {
								errors.add("state", new ActionMessage("error.state.field.value"));
							}
						} catch (Exception e) {
							DefaultLogger.error(this, e.getMessage());
							errors.add("state", new ActionMessage("error.invalid.field.value"));
						}
					}else {
						errors.add("state", new ActionMessage("error.string.field.length.exceeded"));
					}
				} else {
					errors.add("state", new ActionMessage("error.string.mandatory"));
				}
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			if (requestDTO.getBodyDetails().get(0).getRegion() != null
					&& !requestDTO.getBodyDetails().get(0).getRegion().trim().isEmpty()) {
				if (requestDTO.getBodyDetails().get(0).getState() != null
						&& !requestDTO.getBodyDetails().get(0).getState().trim().isEmpty()) {
					if (requestDTO.getBodyDetails().get(0).getCity() != null
							&& !requestDTO.getBodyDetails().get(0).getCity().trim().isEmpty()) {
						if(!(requestDTO.getBodyDetails().get(0).getCity().trim().length()>19)) {
							try {
								Object obCity = masterObj.getObjectforMaster("actualCity",
										new Long(requestDTO.getBodyDetails().get(0).getCity().trim()));
								if (obCity != null) {
									reqObj.setCity(requestDTO.getBodyDetails().get(0).getCity().trim());

									if (requestDTO.getBodyDetails().get(0).getState().trim()
											.equalsIgnoreCase(Long.toString(((ICity) obCity).getStateId().getIdState()))) {
										reqObj.setState(Long.toString(((ICity) obCity).getStateId().getIdState()));
									} else {
										System.out.println("Given City is not Present in the state list of State");
										errors.add("city", new ActionMessage("error.city.field.value"));
									}

								} else {
									errors.add("city", new ActionMessage("error.city.field.value"));
								}
							} catch (Exception e) {
								DefaultLogger.error(this, e.getMessage());
								errors.add("city", new ActionMessage("error.invalid.field.value"));
							}
						}else {
							errors.add("city", new ActionMessage("error.string.field.length.exceeded"));
						}
					} else {
						errors.add("city", new ActionMessage("error.string.mandatory"));
					}
				}
			}
		}

		if (requestDTO.getBodyDetails().get(0).getPincode() != null
				&& !requestDTO.getBodyDetails().get(0).getPincode().trim().isEmpty()) {

			if (requestDTO.getBodyDetails().get(0).getPincode().trim().length() > 6) {
				// reqObj.setPincode(requestDTO.getBodyDetails().get(0).getPincode().trim().substring(0,
				// 6));
				errors.add("pinCode", new ActionMessage("error.pinCode.length.exceeded"));
			} else {
				reqObj.setPincode(requestDTO.getBodyDetails().get(0).getPincode().trim());
			}

			Pattern p = Pattern.compile(".*\\D.*");
			Matcher m = p.matcher(requestDTO.getBodyDetails().get(0).getPincode().trim());
			boolean isAlphaNumeric = m.matches();
			if (isAlphaNumeric) {
				errors.add("specialCharacterPostcode", new ActionMessage("error.pincode.numeric.format"));
			}
		} else {
			reqObj.setPincode("");
		}

		if (requestDTO.getBodyDetails().get(0).getEmailId() != null
				&& !requestDTO.getBodyDetails().get(0).getEmailId().trim().isEmpty()) {
			reqObj.setEmailId(requestDTO.getBodyDetails().get(0).getEmailId().trim());
		} else {
			reqObj.setEmailId("");
		}

		if (requestDTO.getBodyDetails().get(0).getFaxStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxStdCode().trim().isEmpty()) {
			reqObj.setFaxStdCode(requestDTO.getBodyDetails().get(0).getFaxStdCode().trim());
		} else {
			reqObj.setFaxStdCode("");
		}

		if (requestDTO.getBodyDetails().get(0).getFaxNumber() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxNumber().trim().isEmpty()) {
			reqObj.setFaxNumber(requestDTO.getBodyDetails().get(0).getFaxNumber().trim());
		} else {
			reqObj.setFaxNumber("");
		}

		if (requestDTO.getBodyDetails().get(0).getTelephoneStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim().isEmpty()) {
			reqObj.setTelephoneStdCode(requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim());
		} else {
			reqObj.setTelephoneStdCode("");
		}

		if (requestDTO.getBodyDetails().get(0).getTelephoneNo() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneNo().trim().isEmpty()) {
			reqObj.setTelephoneNo(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
		} else {
			reqObj.setTelephoneNo("");
		}

		if (requestDTO.getBodyDetails().get(0).getNonFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim().isEmpty()) {
			reqObj.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim());
		} else {
			reqObj.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getDpSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getDpSharePercent().trim().isEmpty()) {
			reqObj.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent().trim());
		} else {
			reqObj.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent());
		}

		reqObj.setStatus("ACTIVE");
//		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
//			reqObj.setYearEndPeriod("31-MAR");
//		}
//
//		if ("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
//			if (requestDTO.getBodyDetails().get(0).getYearEndPeriod() != null
//					&& !requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim().isEmpty()) {
//				reqObj.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim());
//			} else {
//				reqObj.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod());
//			}
//		}

		/*
		 * if("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0)
		 * .getEvent())) {
		 * if(StringUtils.isBlank(requestDTO.getBodyDetails().get(0).getClimsPartyId().
		 * trim())){ errors.add("climsPartyId",new
		 * ActionMessage("error.string.mandatory")); }else{ try{ ICustomerProxy
		 * custProxy = CustomerProxyFactory.getProxy(); //Fetching Party Details and set
		 * to the context ICMSCustomer cust =
		 * custProxy.getCustomerByCIFSource(requestDTO.getBodyDetails().get(0).
		 * getClimsPartyId().trim(),null); if(cust==null || "".equals(cust)){
		 * errors.add("climsPartyId",new ActionMessage("error.invalid.field.value")); }
		 * }catch(CustomerException e){ errors.add("climsPartyId",new
		 * ActionMessage("error.invalid.field.value")); } } }
		 */

		// requestDTO.setErrors(errors);
		reqObj.setUdfList((List<UdfRestRequestDTO>) udfDetailsRestDTOMapper.getUdfRequestDTOWithActualValues(requestDTO,
				reqObj.getEvent(), errors));

		reqObj.setErrors(errors);
		partyBodyReqList.add(reqObj);
		requestDTO.setBodyDetails(partyBodyReqList);
		return requestDTO;

	}
	// End Rest getRequestDTOWithActualValuesRest() method
	// *************************************************************************************************

	private static List fetchRMData(String rmEmpCode) {
		List data = new ArrayList();

		String sql = "select RM_MGR_NAME,(select reg.REGION_NAME from cms_region reg where reg.id= REGION) as region from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"
				+ rmEmpCode + "' and STATUS = 'ACTIVE'";

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
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	private static int fetchMGRCodeCountWithActiveStatus(String mgrCode) {
		// List data = new ArrayList();
		int data = 0;
		String sql = "SELECT COUNT(1) AS CNT FROM CMS_RELATIONSHIP_MGR WHERE RM_MGR_CODE='" + mgrCode
				+ "' and STATUS = 'ACTIVE'";

		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				data = (rs.getInt("CNT"));
				// data.add(rs.getString("REGION"));
			}
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in fetchCpsIdCountWithActiveStatus", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in fetchCpsIdCountWithActiveStatus", ex);
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	private static Long fetchSubLineDataUsingPartyId(String partyId) {
		Long data = 0L;
		String sql = "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID IN (SELECT LLP_LE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID IN (SELECT CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS WHERE facility_cat = 'F19')) AND ( STATUS ='ACTIVE' AND LSP_LE_ID='"
				+ partyId + "')";

		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				data = (rs.getLong("CMS_LE_SUB_PROFILE_ID"));
			}
		} catch (SQLException ex) {
			return data;
		} catch (Exception ex) {
			return data;
		} finally {
			try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public ManualInputCustomerInfoForm getFormFromDTORest(PartyDetailsRestRequestDTO requestDTO) {

		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);
		MasterAccessUtility masterAccessUtilityObj = (MasterAccessUtility) BeanHouse.get("masterAccessUtility");
		// CMSCustomer info
		ManualInputCustomerInfoForm form = new ManualInputCustomerInfoForm();

		// Default Values
		form.setStatus(DEFAULT_STATUS);
		// form.setCycle(DEFAULT_CYCLE);
		// form.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if (requestDTO.getBodyDetails().get(0).getNonFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim().isEmpty()) {
			form.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim());
		} else {
			form.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getDpSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getDpSharePercent().trim().isEmpty()) {
			form.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent().trim());
		} else {
			form.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent());
		}

		if (null != requestDTO.getBodyDetails().get(0).getEvent()
				&& !requestDTO.getBodyDetails().get(0).getEvent().trim().isEmpty()) {
			form.setEvent(requestDTO.getBodyDetails().get(0).getEvent().trim());
		} else {
			form.setEvent(requestDTO.getBodyDetails().get(0).getEvent());
		}

		if (null != requestDTO.getBodyDetails().get(0).getSubLine()
				&& !requestDTO.getBodyDetails().get(0).getSubLine().trim().isEmpty()) {
			form.setSubLine(requestDTO.getBodyDetails().get(0).getSubLine());
			List subLineList = new ArrayList();
			if (requestDTO.getBodyDetails().get(0).getSubLineDetailsList() != null
					&& !requestDTO.getBodyDetails().get(0).getSubLineDetailsList().isEmpty()) {
				for (PartySubLineDetailsRestRequestDTO subLineDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getSubLineDetailsList()) {
					form.setPartyName(subLineDetReqDTO.getSubLinePartyId());
					form.setAmount(subLineDetReqDTO.getGuaranteedAmt());
					subLineList.add(subLineDetReqDTO);
				}
			}
			form.setSublineParty(subLineList);
		} else {
			form.setSubLine(DEFAULT_SUBLINE);
		}

		if (null != requestDTO.getBodyDetails().get(0).getBusinessGroup()
				&& !requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().isEmpty()) {
			form.setBusinessGroup(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim());
		} else {
			form.setBusinessGroup(requestDTO.getBodyDetails().get(0).getBusinessGroup());
		}

		if (null != requestDTO.getBodyDetails().get(0).getBusinessGroup()
				&& !requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().isEmpty()) {
			form.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim());
		} else {
			form.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup());
		}

		if (null != requestDTO.getBodyDetails().get(0).getMainBranch()
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()) {
			form.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch().trim());
		} else {
			form.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch());
		}

		if (null != requestDTO.getBodyDetails().get(0).getMainBranch()
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()
				&& requestDTO.getBodyDetails().get(0).getMainBranch().trim().contains("-")) {
			form.setBranchCode(requestDTO.getBodyDetails().get(0).getMainBranch().trim().split("-")[0]);
		} else {
			form.setBranchCode("");
		}

		// New fields added for Wholesale Rest API
		if (null != requestDTO.getBodyDetails().get(0).getCinLlpin()
				&& !requestDTO.getBodyDetails().get(0).getCinLlpin().trim().isEmpty()) {
			form.setCinLlpin(requestDTO.getBodyDetails().get(0).getCinLlpin().trim());
		} else {
			form.setCinLlpin("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getDateOfIncorporation()
				&& !requestDTO.getBodyDetails().get(0).getDateOfIncorporation().trim().isEmpty()) {
			form.setDateOfIncorporation(requestDTO.getBodyDetails().get(0).getDateOfIncorporation().trim());
		} else {
			form.setDateOfIncorporation("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getCycle()
				&& !requestDTO.getBodyDetails().get(0).getCycle().trim().isEmpty()) {
			form.setCycle(requestDTO.getBodyDetails().get(0).getCycle().trim());
		} else {
			form.setCycle(DEFAULT_CYCLE);
		}
		if (null != requestDTO.getBodyDetails().get(0).getForm6061()
				&& !requestDTO.getBodyDetails().get(0).getForm6061().trim().isEmpty()) {
			form.setForm6061Checked(requestDTO.getBodyDetails().get(0).getForm6061().trim());
		} else {
			form.setForm6061Checked("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getAadharNumber()
				&& !requestDTO.getBodyDetails().get(0).getAadharNumber().trim().isEmpty()) {
			form.setAadharNumber(requestDTO.getBodyDetails().get(0).getAadharNumber().trim());
		} else {
			form.setAadharNumber("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan()
				&& !requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim().isEmpty()) {
			form.setExceptionalCases(requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim());
		} else {
			form.setExceptionalCases("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getiFSCCode()
				&& !requestDTO.getBodyDetails().get(0).getiFSCCode().trim().isEmpty()) {
			form.setiFSCCode(requestDTO.getBodyDetails().get(0).getiFSCCode().trim());
		} else {
			form.setiFSCCode("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getBankName()
				&& !requestDTO.getBodyDetails().get(0).getBankName().trim().isEmpty()) {
			form.setBankName(requestDTO.getBodyDetails().get(0).getBankName().trim());
		} else {
			form.setBankName("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getBankBranchName()
				&& !requestDTO.getBodyDetails().get(0).getBankBranchName().trim().isEmpty()) {
			form.setBankBranchName(requestDTO.getBodyDetails().get(0).getBankBranchName().trim());
		} else {
			form.setBankBranchName("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getCustomerFyClouser()
				&& !requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim().isEmpty()) {
			form.setCustomerFyClouser(requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim());
		} else {
			form.setCustomerFyClouser("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSecondYear()
				&& !requestDTO.getBodyDetails().get(0).getSecondYear().trim().isEmpty()) {
			form.setSecondYear(requestDTO.getBodyDetails().get(0).getSecondYear().trim());
		} else {
			form.setSecondYear("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSecondYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim().isEmpty()) {
			form.setSecondYearTurnover(requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim());
		} else {
			form.setSecondYearTurnover("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr()
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim().isEmpty()) {
			form.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim());
		} else {
			form.setSecondYearTurnoverCurr("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYear()
				&& !requestDTO.getBodyDetails().get(0).getThirdYear().trim().isEmpty()) {
			form.setThirdYear(requestDTO.getBodyDetails().get(0).getThirdYear().trim());
		} else {
			form.setThirdYear("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim().isEmpty()) {
			form.setThirdYearTurnover(requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim());
		} else {
			form.setThirdYearTurnover("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim().isEmpty()) {
			form.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim());
		} else {
			form.setThirdYearTurnoverCurr("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsSPVFunding() != null
				&& !requestDTO.getBodyDetails().get(0).getIsSPVFunding().trim().isEmpty()) {
			form.setIsSPVFunding(requestDTO.getBodyDetails().get(0).getIsSPVFunding().trim());
		}

		if (null != requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure()
				&& !requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim().isEmpty()) {
			form.setIndirectCountryRiskExposure(
					requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim());
		} else {
			form.setIndirectCountryRiskExposure("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getCriCountryName()
				&& !requestDTO.getBodyDetails().get(0).getCriCountryName().trim().isEmpty()) {
			form.setCriCountryName(requestDTO.getBodyDetails().get(0).getCriCountryName().trim());
		} else {
			form.setCriCountryName("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSalesPercentage()
				&& !requestDTO.getBodyDetails().get(0).getSalesPercentage().trim().isEmpty()) {
			form.setSalesPercentage(requestDTO.getBodyDetails().get(0).getSalesPercentage().trim());
		} else {
			form.setSalesPercentage("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsCGTMSE()
				&& !requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim().isEmpty()) {
			form.setIsCGTMSE(requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim());
		} else {
			form.setIsCGTMSE("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsIPRE()
				&& !requestDTO.getBodyDetails().get(0).getIsIPRE().trim().isEmpty()) {
			form.setIsIPRE(requestDTO.getBodyDetails().get(0).getIsIPRE().trim());
		} else {
			form.setIsIPRE("");
		}

		if (requestDTO.getBodyDetails().get(0).getFinanceForAccquisition() != null
				&& !requestDTO.getBodyDetails().get(0).getFinanceForAccquisition().trim().isEmpty()) {
			form.setFinanceForAccquisition(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition())) {
				form.setFacilityApproved(requestDTO.getBodyDetails().get(0).getFacilityApproved());
				form.setFacilityAmount(requestDTO.getBodyDetails().get(0).getFacilityAmount());
				form.setSecurityName(requestDTO.getBodyDetails().get(0).getSecurityName());
				form.setSecurityType(requestDTO.getBodyDetails().get(0).getSecurityType());
				form.setSecurityValue(requestDTO.getBodyDetails().get(0).getSecurityValue());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCompanyType() != null
				&& !requestDTO.getBodyDetails().get(0).getCompanyType().trim().isEmpty()) {
			form.setCompanyType(requestDTO.getBodyDetails().get(0).getCompanyType());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCompanyType())) {
				form.setNameOfHoldingCompany(requestDTO.getBodyDetails().get(0).getNameOfHoldingCompany().trim());
				form.setType(requestDTO.getBodyDetails().get(0).getType());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions() != null
				&& !requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions().trim().isEmpty()) {
			form.setIfBreachWithPrescriptions(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions())) {
				form.setComments(requestDTO.getBodyDetails().get(0).getComments().trim());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCategoryOfFarmer() != null
				&& !requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim().isEmpty()) {
			form.setCategoryOfFarmer(requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim());
			if (requestDTO.getBodyDetails().get(0).getLandHolding() != null
					&& !requestDTO.getBodyDetails().get(0).getLandHolding().trim().isEmpty()) {
				form.setLandHolding(requestDTO.getBodyDetails().get(0).getLandHolding().trim());
			}
		}
		if (null != requestDTO.getBodyDetails().get(0).getCountryOfGuarantor()
				&& !requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim().isEmpty()) {
			form.setCountryOfGuarantor(requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim());
		} else {
			form.setCountryOfGuarantor("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance()
				&& !requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim().isEmpty()) {
			form.setIsAffordableHousingFinance(
					requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim());
		} else {
			form.setIsAffordableHousingFinance("");
		}
		if (requestDTO.getBodyDetails().get(0).getIsInRestrictedList() != null
				&& !requestDTO.getBodyDetails().get(0).getIsInRestrictedList().trim().isEmpty()) {
			form.setIsInRestrictedList(requestDTO.getBodyDetails().get(0).getIsInRestrictedList());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsInRestrictedList())) {
				form.setRestrictedFinancing(requestDTO.getBodyDetails().get(0).getRestrictedFinancing());
			}
		}
		if (requestDTO.getBodyDetails().get(0).getRestrictedIndustries() != null
				&& !requestDTO.getBodyDetails().get(0).getRestrictedIndustries().trim().isEmpty()) {
			form.setRestrictedIndustries(requestDTO.getBodyDetails().get(0).getRestrictedIndustries());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestrictedIndustries())) {
				form.setRestrictedListIndustries(requestDTO.getBodyDetails().get(0).getRestrictedListIndustries());
			}
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent()
				&& !requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim().isEmpty()) {
			form.setIsQualifyingNotesPresent(requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim());
		} else {
			form.setIsQualifyingNotesPresent("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getStateImplications()
				&& !requestDTO.getBodyDetails().get(0).getStateImplications().trim().isEmpty()) {
			form.setStateImplications(requestDTO.getBodyDetails().get(0).getStateImplications().trim());
		} else {
			form.setStateImplications("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase() != null
				&& !requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase().trim().isEmpty()) {
			form.setIsBorrowerInRejectDatabase(requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase())) {
				form.setRejectHistoryReason(requestDTO.getBodyDetails().get(0).getRejectHistoryReason().trim());
			}
		}

		if (null != requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange()
				&& !requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim().isEmpty()) {
			form.setCapitalForCommodityAndExchange(
					requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim());
		} else {
			form.setCapitalForCommodityAndExchange("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare()
				&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim().isEmpty()) {
			form.setIsBrokerTypeShare(requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim());
		} else {
			form.setIsBrokerTypeShare("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity()
				&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim().isEmpty()) {
			form.setIsBrokerTypeCommodity(requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim());
		} else {
			form.setIsBrokerTypeCommodity("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getObjectFinance()
				&& !requestDTO.getBodyDetails().get(0).getObjectFinance().trim().isEmpty()) {
			form.setObjectFinance(requestDTO.getBodyDetails().get(0).getObjectFinance().trim());
		} else {
			form.setObjectFinance("");
		}
		if (requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer() != null
				&& !requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim().isEmpty()) {
			form.setIsCommodityFinanceCustomer(
					requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer().trim());
		}
		if (requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility() != null
				&& !requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility().trim().isEmpty()) {
			form.setRestructuedBorrowerOrFacility(
					requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility())) {
				form.setFacility(requestDTO.getBodyDetails().get(0).getFacility());
				form.setLimitAmount(requestDTO.getBodyDetails().get(0).getLimitAmount());
			}
		}
		if (requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks() != null
				&& !requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks().trim().isEmpty()) {
			form.setConductOfAccountWithOtherBanks(
					requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks());
			if ("classified".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks())) {
				form.setCrilicStatus(requestDTO.getBodyDetails().get(0).getCrilicStatus());
				if (requestDTO.getBodyDetails().get(0).getCrilicStatus() != null
						&& !requestDTO.getBodyDetails().get(0).getCrilicStatus().trim().isEmpty()) {
					form.setComment(requestDTO.getBodyDetails().get(0).getCrilcComment());
				}
			}
		} else {
			form.setConductOfAccountWithOtherBanks("Satisfactory");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSubsidyFlag()
				&& !requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().isEmpty()) {
			form.setSubsidyFlag(requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim());
			
		} else {
			form.setSubsidyFlag("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSubsidyFlag()
				&& !requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().isEmpty()
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getSubsidyFlag())) {
			if (null != requestDTO.getBodyDetails().get(0).getHoldingCompnay()
					&& !requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim().isEmpty()) {
				form.setHoldingCompnay(requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim());
			}
		} else {
			form.setHoldingCompnay("");
		}

		if (requestDTO.getBodyDetails().get(0).getCautionList() != null
				&& !requestDTO.getBodyDetails().get(0).getCautionList().trim().isEmpty()) {
			form.setCautionList(requestDTO.getBodyDetails().get(0).getCautionList());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCautionList())) {
				form.setDateOfCautionList(requestDTO.getBodyDetails().get(0).getDateOfCautionList().trim());
				form.setCompany(requestDTO.getBodyDetails().get(0).getCompany().trim());
				form.setDirectors(requestDTO.getBodyDetails().get(0).getDirectors().trim());
				form.setGroupCompanies(requestDTO.getBodyDetails().get(0).getGroupCompanies().trim());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getDefaultersList() != null
				&& !requestDTO.getBodyDetails().get(0).getDefaultersList().trim().isEmpty()) {
			form.setDefaultersList(requestDTO.getBodyDetails().get(0).getDefaultersList());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getDefaultersList().trim())) {
				form.setRbiDateOfCautionList(requestDTO.getBodyDetails().get(0).getRbiDateOfCautionList().trim());
				form.setRbiCompany(requestDTO.getBodyDetails().get(0).getRbiCompany().trim());
				form.setRbiDirectors(requestDTO.getBodyDetails().get(0).getRbiDirectors().trim());
				form.setRbiGroupCompanies(requestDTO.getBodyDetails().get(0).getRbiGroupCompanies().trim());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate() != null
				&& !requestDTO.getBodyDetails().get(0).getCommericialRealEstate().trim().isEmpty()) {
			form.setCommericialRealEstate(requestDTO.getBodyDetails().get(0).getCommericialRealEstate());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCommericialRealEstate())) {
				form.setCommericialRealEstateValue(requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue());
				form.setCommericialRealEstateResidentialHousing("No");
				form.setResidentialRealEstate("No");
				form.setIndirectRealEstate("No");

			} else {
				if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing() != null
						&& "Yes".equalsIgnoreCase(
								requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing())) {
					form.setCommericialRealEstateResidentialHousing(
							requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing());
					form.setResidentialRealEstate("No");
					form.setIndirectRealEstate("No");
					form.setCommericialRealEstate("No");
				} else if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate() != null
						&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getResidentialRealEstate())) {
					form.setResidentialRealEstate(requestDTO.getBodyDetails().get(0).getResidentialRealEstate());
					form.setCommericialRealEstateResidentialHousing("No");
					form.setIndirectRealEstate("No");
					form.setCommericialRealEstate("No");
				} else if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate() != null
						&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIndirectRealEstate())) {
					form.setIndirectRealEstate(requestDTO.getBodyDetails().get(0).getIndirectRealEstate());
					form.setCommericialRealEstateResidentialHousing("No");
					form.setResidentialRealEstate("No");
					form.setCommericialRealEstate("No");
				}
			}
		}

		if (null != requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo()
				&& !requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim().isEmpty()) {
			form.setBorrowerDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim());
		} else {
			form.setBorrowerDUNSNo("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getPartyConsent()
				&& !requestDTO.getBodyDetails().get(0).getPartyConsent().trim().isEmpty()) {
			form.setPartyConsent(requestDTO.getBodyDetails().get(0).getPartyConsent().trim());
		} else {
			form.setPartyConsent("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getClassActivity1()
				&& !requestDTO.getBodyDetails().get(0).getClassActivity1().trim().isEmpty()) {
			form.setClassActivity1(requestDTO.getBodyDetails().get(0).getClassActivity1().trim());
		} else {
			form.setClassActivity1("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getClassActivity2()
				&& !requestDTO.getBodyDetails().get(0).getClassActivity2().trim().isEmpty()) {
			form.setClassActivity2(requestDTO.getBodyDetails().get(0).getClassActivity2().trim());
		} else {
			form.setClassActivity2("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getRegOffice()
				&& !requestDTO.getBodyDetails().get(0).getRegOffice().trim().isEmpty()) {
			form.setRegOffice(requestDTO.getBodyDetails().get(0).getRegOffice().trim());
			if (requestDTO.getBodyDetails().get(0).getRegOffice().trim().equals("Y")) {
				form.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo());
				form.setRegOfficeCountry(requestDTO.getBodyDetails().get(0).getCountry());
				form.setRegOfficeRegion(requestDTO.getBodyDetails().get(0).getRegion());
				form.setRegOfficeState(requestDTO.getBodyDetails().get(0).getState());
				form.setRegOfficeCity(requestDTO.getBodyDetails().get(0).getCity());
				form.setRegOfficeAddress1(requestDTO.getBodyDetails().get(0).getAddress1());
				form.setRegOfficeAddress2(requestDTO.getBodyDetails().get(0).getAddress2());
				form.setRegOfficeAddress3(requestDTO.getBodyDetails().get(0).getAddress3());
				form.setRegOfficePostCode(requestDTO.getBodyDetails().get(0).getPincode());
				form.setRegOfficeTelephoneNo(requestDTO.getBodyDetails().get(0).getTelephoneNo());
				form.setRegOfficeTelex(requestDTO.getBodyDetails().get(0).getFaxNumber());
				form.setStdCodeOfficeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode());
				form.setStdCodeOfficeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode());
				form.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getEmailId());
			} else {
				form.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo());
				form.setRegOfficeCountry(requestDTO.getBodyDetails().get(0).getRegisteredCountry());
				form.setRegOfficeRegion(requestDTO.getBodyDetails().get(0).getRegisteredRegion());
				form.setRegOfficeState(requestDTO.getBodyDetails().get(0).getRegisteredState());
				form.setRegOfficeCity(requestDTO.getBodyDetails().get(0).getRegisteredCity());
				form.setRegOfficeAddress1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1());
				form.setRegOfficeAddress2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2());
				form.setRegOfficeAddress3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3());
				form.setRegOfficePostCode(requestDTO.getBodyDetails().get(0).getRegisteredPincode());
				form.setRegOfficeTelephoneNo(requestDTO.getBodyDetails().get(0).getRegisteredTelNo());
				form.setRegOfficeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber());
				form.setStdCodeOfficeTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode());
				form.setStdCodeOfficeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode());
				form.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getRegOfficeEmail());
			}
		} else {
			form.setRegOffice("N");
			form.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo());
			form.setRegOfficeCountry(requestDTO.getBodyDetails().get(0).getRegisteredCountry());
			form.setRegOfficeRegion(requestDTO.getBodyDetails().get(0).getRegisteredRegion());
			form.setRegOfficeState(requestDTO.getBodyDetails().get(0).getRegisteredState());
			form.setRegOfficeCity(requestDTO.getBodyDetails().get(0).getRegisteredCity());
			form.setRegOfficeAddress1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1());
			form.setRegOfficeAddress2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2());
			form.setRegOfficeAddress3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3());
			form.setRegOfficePostCode(requestDTO.getBodyDetails().get(0).getRegisteredPincode());
			form.setRegOfficeTelephoneNo(requestDTO.getBodyDetails().get(0).getRegisteredTelNo());
			form.setRegOfficeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber());
			form.setStdCodeOfficeTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode());
			form.setStdCodeOfficeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode());
			form.setRegOfficeEmail(requestDTO.getBodyDetails().get(0).getRegOfficeEmail());
		}

		if (requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus() != null
				&& !requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().isEmpty()) {
			form.setWillfulDefaultStatus(requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus());
			if (requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().equals("DEFAULTER")
					|| requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().equals("1")) {
				form.setDateWillfulDefault(requestDTO.getBodyDetails().get(0).getDateWillfulDefault());
				form.setSuitFilledStatus(requestDTO.getBodyDetails().get(0).getSuitFilledStatus());
				form.setSuitReferenceNo(requestDTO.getBodyDetails().get(0).getSuitReferenceNo());
				form.setSuitAmount(requestDTO.getBodyDetails().get(0).getSuitAmount());
				form.setDateOfSuit(requestDTO.getBodyDetails().get(0).getDateOfSuit());
			}
		}

		if (requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList() != null
				&& !requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList().trim().isEmpty()) {
			form.setIsRBIWilfulDefaultersList(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList())) {
				form.setNameOfBank(requestDTO.getBodyDetails().get(0).getNameOfBank());
				form.setIsDirectorMoreThanOne(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne());
				if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne())) {
					form.setNameOfDirectorsAndCompany(
							requestDTO.getBodyDetails().get(0).getNameOfDirectorsAndCompany());
				} else {
					form.setNameOfDirectorsAndCompany("");
				}
			} else {
				form.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
				form.setNameOfBank("");
			}
		} else {
			form.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			form.setNameOfBank("");
			form.setIsDirectorMoreThanOne("");
			form.setNameOfDirectorsAndCompany("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank()
				&& !requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim().isEmpty()) {
			form.setIsBorrDefaulterWithBank(requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim());
			if (null != requestDTO.getBodyDetails().get(0).getDetailsOfDefault()
					&& !requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim().isEmpty()) {
				form.setDetailsOfDefault(requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim());
			} else {
				form.setDetailsOfDefault("");
			}
			if (null != requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff()
					&& !requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim().isEmpty()) {
				form.setExtOfCompromiseAndWriteoff(
						requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim());
			} else {
				form.setExtOfCompromiseAndWriteoff("");
			}
		} else {
			form.setIsBorrDefaulterWithBank("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsCibilStatusClean() != null
				&& !requestDTO.getBodyDetails().get(0).getIsCibilStatusClean().trim().isEmpty()) {
			form.setIsCibilStatusClean(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean());
			if ("No".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean())) {
				form.setDetailsOfCleanCibil(requestDTO.getBodyDetails().get(0).getDetailsOfCleanCibil());
			} else {
				form.setIsCibilStatusClean("Yes");
				form.setDetailsOfCleanCibil("");
			}
		} else {
			form.setDetailsOfCleanCibil("Yes");
		}
		// +++++++++++++++++++++++++++++

		String rmRegion = "-";

		if (requestDTO.getBodyDetails().get(0).getRelationshipManager() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipManager().trim().isEmpty()) {

			form.setRelationshipMgr(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim());

			try {
				Object relshpObj = masterAccessUtilityObj.getObjectforMaster("actualRelationshipMgr",
						new Long(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim()));
				if (relshpObj != null && !"".equals(relshpObj)) {
					rmRegion = Long.toString(((IRelationshipMgr) relshpObj).getRegion().getIdRegion());
				}
			} catch (Exception e) {
				DefaultLogger.error(this, e.getMessage());
			}

		} else {
			form.setRelationshipMgr(requestDTO.getBodyDetails().get(0).getRelationshipManager());
		}

		if (requestDTO.getBodyDetails().get(0).getRelationshipMgrCode() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipMgrCode().trim().isEmpty()) {
			form.setRelationshipMgrEmpCode(requestDTO.getBodyDetails().get(0).getRelationshipMgrCode());
		}

		if (rmRegion != null && !rmRegion.trim().isEmpty()) {
			form.setRmRegion(rmRegion.trim());
		} else {
			form.setRmRegion(rmRegion);
		}

		if (requestDTO.getBodyDetails().get(0).getEntity() != null
				&& !requestDTO.getBodyDetails().get(0).getEntity().trim().isEmpty()) {
			form.setEntity(requestDTO.getBodyDetails().get(0).getEntity().trim());
		} else {
			form.setEntity(requestDTO.getBodyDetails().get(0).getEntity());
		}
		if (requestDTO.getBodyDetails().get(0).getEntityType() != null
				&& !requestDTO.getBodyDetails().get(0).getEntityType().trim().isEmpty()) {
			form.setEntityType(requestDTO.getBodyDetails().get(0).getEntityType().trim());
		} else {
			form.setEntityType(requestDTO.getBodyDetails().get(0).getEntityType());
		}
		if (requestDTO.getBodyDetails().get(0).getPAN() != null
				&& !requestDTO.getBodyDetails().get(0).getPAN().trim().isEmpty()) {
			form.setPan(requestDTO.getBodyDetails().get(0).getPAN().trim());
		} else {
			form.setPan(requestDTO.getBodyDetails().get(0).getPAN());
		}

		if (requestDTO.getBodyDetails().get(0).getRBIIndustryCode() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim().isEmpty()) {
			form.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim());
		} else {
			form.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode());
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryName() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryName().trim().isEmpty()) {
			form.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName().trim());
		} else {
			form.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName());
		}

		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethod().trim().isEmpty()) {
			form.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod().trim());
		} else {
			form.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim().isEmpty()) {
			form.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim());
		} else {
			form.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim().isEmpty()) {
			form.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim());
		} else {
			form.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim().isEmpty()) {
			form.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim());
		} else {
			form.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getMemoExposure().trim().isEmpty()) {
			form.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure().trim());
		} else {
			form.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getMpbf() != null
				&& !requestDTO.getBodyDetails().get(0).getMpbf().trim().isEmpty()) {
			form.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf().trim());
		} else {
			form.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf());
		}

		if (requestDTO.getBodyDetails().get(0).getPartyName() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyName().trim().isEmpty()) {
			form.setPartyName(requestDTO.getBodyDetails().get(0).getPartyName().trim());
		} else {
			form.setPartyName(requestDTO.getBodyDetails().get(0).getPartyName());
		}

		if (requestDTO.getBodyDetails().get(0).getSegment() != null
				&& !requestDTO.getBodyDetails().get(0).getSegment().trim().isEmpty()) {
			form.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment().trim());
		} else {
			form.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment());
		}

		if (requestDTO.getBodyDetails().get(0).getRelationshipStartDate() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim().isEmpty()) {
			form.setRelationshipStartDate(requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim());
		} else {
			form.setRelationshipStartDate(requestDTO.getBodyDetails().get(0).getRelationshipStartDate());
		}

		// Santosh LEI CR
		if (requestDTO.getBodyDetails().get(0).getLeiExpDate() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiExpDate().toString().trim().isEmpty()) {
			form.setLeiExpDate(requestDTO.getBodyDetails().get(0).getLeiExpDate().trim());
		} else {
			form.setLeiExpDate(requestDTO.getBodyDetails().get(0).getLeiExpDate());
		}

		if (requestDTO.getBodyDetails().get(0).getLeiCode() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiCode().toString().trim().isEmpty()) {
			form.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode().trim());
		} else {
			form.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode());
		}
		// End LEI CR

		if (requestDTO.getBodyDetails().get(0).getPartyName() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyName().trim().isEmpty()) {
			form.setCustomerNameLong(requestDTO.getBodyDetails().get(0).getPartyName().trim());
			form.setCustomerNameShort(requestDTO.getBodyDetails().get(0).getPartyName().trim());
		} else {
			form.setCustomerNameLong(requestDTO.getBodyDetails().get(0).getPartyName());
			form.setCustomerNameShort(requestDTO.getBodyDetails().get(0).getPartyName());
		}

		Double totFundedLimit = (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim())
						: 0d;
		Double totNonFundedLimit = (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim())
						: 0d;
		Double memoExposure = (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getMemoExposure().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getMemoExposure().trim())
						: 0d;

		Double sancLimit = totFundedLimit + totNonFundedLimit + memoExposure;

		if (sancLimit != null) {
			form.setTotalSanctionedLimit(sancLimit.toString().trim());
		}

		if (totFundedLimit != null && !"".equals(totFundedLimit)
				&& requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim())) {
			Double fundedShareLimit = totFundedLimit
					* Double.parseDouble(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim()) / 100;
			form.setFundedShareLimit(fundedShareLimit.toString().trim());
		} else {
			form.setFundedShareLimit("0");
		}

		if (totNonFundedLimit != null && !"".equals(totNonFundedLimit)) {
			Double nonFundedShareLimit = totNonFundedLimit * Double.parseDouble(DEFAULT_NON_FUNDED_SHARE_PERCENT) / 100;
			form.setNonFundedShareLimit(nonFundedShareLimit.toString().trim());
		} else {
			form.setNonFundedShareLimit("0");
		}

		// *********************CRI Info (One-to-one)****************************

		if (requestDTO.getBodyDetails().get(0).getCustomerRAMId() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim().isEmpty()) {
			form.setCustomerRamID(requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim());
		} else {
			form.setCustomerRamID(requestDTO.getBodyDetails().get(0).getCustomerRAMId());
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerAPRCode() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim().isEmpty()) {
			form.setCustomerAprCode(requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim());
		} else {
			form.setCustomerAprCode(requestDTO.getBodyDetails().get(0).getCustomerAPRCode());
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerExtRating() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim().isEmpty()) {
			form.setCustomerExtRating(requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim());
		} else {
			form.setCustomerExtRating(requestDTO.getBodyDetails().get(0).getCustomerExtRating());
		}

		if (requestDTO.getBodyDetails().get(0).getNbfcFlag() != null
				&& !requestDTO.getBodyDetails().get(0).getNbfcFlag().trim().isEmpty()) {
			form.setIsNbfs(requestDTO.getBodyDetails().get(0).getNbfcFlag().trim());

			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getNbfcFlag().trim())) {
				if (requestDTO.getBodyDetails().get(0).getNbfcA() != null
						&& !requestDTO.getBodyDetails().get(0).getNbfcA().trim().isEmpty()) {
					form.setNbfsA(requestDTO.getBodyDetails().get(0).getNbfcA().trim());
				} else {
					form.setNbfsA(requestDTO.getBodyDetails().get(0).getNbfcA());
				}
				if (requestDTO.getBodyDetails().get(0).getNbfcB() != null
						&& !requestDTO.getBodyDetails().get(0).getNbfcB().trim().isEmpty()) {
					form.setNbfsB(requestDTO.getBodyDetails().get(0).getNbfcB().trim());
				} else {
					form.setNbfsB(requestDTO.getBodyDetails().get(0).getNbfcB());
				}
			}
		} else {
			form.setIsNbfs(requestDTO.getBodyDetails().get(0).getNbfcFlag());
		}

		if (requestDTO.getBodyDetails().get(0).getPrioritySector() != null
				&& !requestDTO.getBodyDetails().get(0).getPrioritySector().trim().isEmpty()) {
			form.setPrioritySector(requestDTO.getBodyDetails().get(0).getPrioritySector().trim());
		} else {
			form.setPrioritySector(requestDTO.getBodyDetails().get(0).getPrioritySector());
		}

		if (requestDTO.getBodyDetails().get(0).getMsmeClassification() != null
				&& !requestDTO.getBodyDetails().get(0).getMsmeClassification().trim().isEmpty()) {
			form.setMsmeClassification(requestDTO.getBodyDetails().get(0).getMsmeClassification().trim());
		} else {
			form.setMsmeClassification(requestDTO.getBodyDetails().get(0).getMsmeClassification());
		}

		if (requestDTO.getBodyDetails().get(0).getPermSSICert() != null
				&& !requestDTO.getBodyDetails().get(0).getPermSSICert().trim().isEmpty()) {
			form.setIsPermenentSsiCert(requestDTO.getBodyDetails().get(0).getPermSSICert().trim());
		} else {
			form.setIsPermenentSsiCert(requestDTO.getBodyDetails().get(0).getPermSSICert());
		}

		if (requestDTO.getBodyDetails().get(0).getWeakerSection() != null
				&& !requestDTO.getBodyDetails().get(0).getWeakerSection().trim().isEmpty()) {

			form.setIsWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSection().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getWeakerSection().trim())) {
				if (requestDTO.getBodyDetails().get(0).getWeakerSectionType() != null
						&& !requestDTO.getBodyDetails().get(0).getWeakerSectionType().trim().isEmpty()) {
					form.setWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSectionType().trim());
				} else {
					form.setWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSectionType());
				}

				if (requestDTO.getBodyDetails().get(0).getWeakerSectionValue() != null
						&& !requestDTO.getBodyDetails().get(0).getWeakerSectionValue().trim().isEmpty()) {
					form.setGovtSchemeType(requestDTO.getBodyDetails().get(0).getWeakerSectionValue().trim());
				} else {
					form.setGovtSchemeType(requestDTO.getBodyDetails().get(0).getWeakerSectionValue());
				}
			}
		} else {
			form.setIsWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSection());
		}

		if (requestDTO.getBodyDetails().get(0).getKisanCreditCard() != null
				&& !requestDTO.getBodyDetails().get(0).getKisanCreditCard().trim().isEmpty()) {
			form.setIsKisanCreditCard(requestDTO.getBodyDetails().get(0).getKisanCreditCard().trim());
		} else {
			form.setIsKisanCreditCard(requestDTO.getBodyDetails().get(0).getKisanCreditCard());
		}

		if (requestDTO.getBodyDetails().get(0).getMinorityCommunity() != null
				&& !requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim().isEmpty()) {
			form.setIsMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getMinorityCommunity().trim())) {
				if (requestDTO.getBodyDetails().get(0).getMinorityCommunityType() != null
						&& !requestDTO.getBodyDetails().get(0).getMinorityCommunityType().trim().isEmpty()) {
					form.setMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunityType().trim());
				} else {
					form.setMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunityType());
				}
			}
		} else {
			form.setMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunityType());
			form.setIsMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunity());
		}

		if (requestDTO.getBodyDetails().get(0).getCapitalMarketExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim().isEmpty()) {
			form.setIsCapitalMarketExpos(requestDTO.getBodyDetails().get(0).getCapitalMarketExposure().trim());
		} else {
			form.setIsCapitalMarketExpos(requestDTO.getBodyDetails().get(0).getCapitalMarketExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getRealEstateExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim().isEmpty()) {
			form.setIsRealEstateExpos(requestDTO.getBodyDetails().get(0).getRealEstateExposure().trim());
		} else {
			form.setIsRealEstateExpos(requestDTO.getBodyDetails().get(0).getRealEstateExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getCommodityExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getCommodityExposure().trim().isEmpty()) {
			form.setIsCommoditiesExposer(requestDTO.getBodyDetails().get(0).getCommodityExposure().trim());
		} else {
			form.setIsCommoditiesExposer(requestDTO.getBodyDetails().get(0).getCommodityExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getSensitive() != null
				&& !requestDTO.getBodyDetails().get(0).getSensitive().trim().isEmpty()) {
			form.setIsSensitive(requestDTO.getBodyDetails().get(0).getSensitive().trim());
		} else {
			form.setIsSensitive(requestDTO.getBodyDetails().get(0).getSensitive());
		}

		if (requestDTO.getBodyDetails().get(0).getCommodityName() != null
				&& !requestDTO.getBodyDetails().get(0).getCommodityName().trim().isEmpty()) {
			form.setCommoditiesName(requestDTO.getBodyDetails().get(0).getCommodityName().trim());
		} else {
			form.setCommoditiesName(requestDTO.getBodyDetails().get(0).getCommodityName());
		}

		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentPM() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim().isEmpty()) {
			form.setGrossInvsInPM(requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim());
		} else {
			form.setGrossInvsInPM("0");
		}
		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim().isEmpty()) {
			form.setGrossInvsInEquip(requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim());
		} else {
			form.setGrossInvsInEquip("0");
		}

		if (requestDTO.getBodyDetails().get(0).getPsu() != null
				&& !requestDTO.getBodyDetails().get(0).getPsu().trim().isEmpty()) {
			form.setPsu(requestDTO.getBodyDetails().get(0).getPsu().trim());
		} else {
			form.setPsu(requestDTO.getBodyDetails().get(0).getPsu());
		}

		if (requestDTO.getBodyDetails().get(0).getPercentageOfShareholding() != null
				&& !requestDTO.getBodyDetails().get(0).getPercentageOfShareholding().trim().isEmpty()) {
			form.setPsuPercentage(requestDTO.getBodyDetails().get(0).getPercentageOfShareholding().trim());
		} else {
			form.setPsuPercentage(requestDTO.getBodyDetails().get(0).getPercentageOfShareholding());
		}

		if (requestDTO.getBodyDetails().get(0).getGovtUndertaking() != null
				&& !requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim().isEmpty()) {
			form.setGovtUnderTaking(requestDTO.getBodyDetails().get(0).getGovtUndertaking().trim());
		} else {
			form.setGovtUnderTaking(requestDTO.getBodyDetails().get(0).getGovtUndertaking());
		}

		if (requestDTO.getBodyDetails().get(0).getProjectFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getProjectFinance().trim().isEmpty()) {
			form.setIsProjectFinance(requestDTO.getBodyDetails().get(0).getProjectFinance().trim());
		} else {
			form.setIsProjectFinance(requestDTO.getBodyDetails().get(0).getProjectFinance());
		}

		if (requestDTO.getBodyDetails().get(0).getInfraFinance() != null
				&& !requestDTO.getBodyDetails().get(0).getInfraFinance().trim().isEmpty()) {
			form.setIsInfrastructureFinanace(requestDTO.getBodyDetails().get(0).getInfraFinance().trim());
		} else {
			form.setIsInfrastructureFinanace(requestDTO.getBodyDetails().get(0).getInfraFinance());
		}

		if (requestDTO.getBodyDetails().get(0).getInfraFinanceType() != null
				&& !requestDTO.getBodyDetails().get(0).getInfraFinanceType().trim().isEmpty()) {
			form.setInfrastructureFinanaceType(requestDTO.getBodyDetails().get(0).getInfraFinanceType().trim());
		} else {
			form.setInfrastructureFinanaceType(requestDTO.getBodyDetails().get(0).getInfraFinanceType());
		}

		if (requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable() != null
				&& !requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable().trim().isEmpty()) {
			form.setIsSemsGuideApplicable(requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable().trim());
		} else {
			form.setIsSemsGuideApplicable(requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable());
		}

		if (requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion() != null
				&& !requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim().isEmpty()) {
			form.setIsFailIfcExcluList(requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion().trim());
		} else {
			form.setIsFailIfcExcluList(requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion());
		}

		if (requestDTO.getBodyDetails().get(0).getTufs() != null
				&& !requestDTO.getBodyDetails().get(0).getTufs().trim().isEmpty()) {
			form.setIsTufs(requestDTO.getBodyDetails().get(0).getTufs().trim());
		} else {
			form.setIsTufs(requestDTO.getBodyDetails().get(0).getTufs());
		}

		if (requestDTO.getBodyDetails().get(0).getRBIDefaulterList() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim().isEmpty()) {
			form.setIsRbiDefaulter(requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRBIDefaulterList().trim())) {
				// Change for RBI defaulter Type i.e Company ,Director and Group Companies
				if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany().trim().isEmpty()) {
					form.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany().trim());
				} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors().trim().isEmpty()) {
					form.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors().trim());
				} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies()
						&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies().trim()
								.isEmpty()) {
					form.setRbiDefaulter(
							requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies().trim());
				}
				/*
				 * else{ form.setRbiDefaulter(requestDTO.getBodyDetails().get(0).
				 * getRbiDefaulterListType()); }
				 */
			}
		} else {
			form.setIsRbiDefaulter(requestDTO.getBodyDetails().get(0).getRBIDefaulterList());
		}

		if (requestDTO.getBodyDetails().get(0).getLitigationPending() != null
				&& !requestDTO.getBodyDetails().get(0).getLitigationPending().trim().isEmpty()) {
			form.setIsLitigation(requestDTO.getBodyDetails().get(0).getLitigationPending().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getLitigationPending().trim())) {
				if (requestDTO.getBodyDetails().get(0).getLitigationPendingBy() != null
						&& !requestDTO.getBodyDetails().get(0).getLitigationPendingBy().trim().isEmpty()) {
					form.setLitigationBy(requestDTO.getBodyDetails().get(0).getLitigationPendingBy().trim());
				} else {
					form.setLitigationBy(requestDTO.getBodyDetails().get(0).getLitigationPendingBy());
				}
			}
		} else {
			form.setIsLitigation(requestDTO.getBodyDetails().get(0).getLitigationPending());
		}

		if (requestDTO.getBodyDetails().get(0).getInterestOfDirectors() != null
				&& !requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim().isEmpty()) {
			form.setIsInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getInterestOfDirectors().trim())) {
				if (requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType() != null
						&& !requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType().trim().isEmpty()) {
					form.setInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType().trim());
				} else {
					form.setInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType());
				}
			}
		} else {
			form.setIsInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectors());
		}

		if (requestDTO.getBodyDetails().get(0).getAdverseRemark() != null
				&& !requestDTO.getBodyDetails().get(0).getAdverseRemark().trim().isEmpty()) {
			form.setIsAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemark().trim());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getAdverseRemark().trim())) {
				if (requestDTO.getBodyDetails().get(0).getAdverseRemarkValue() != null
						&& !requestDTO.getBodyDetails().get(0).getAdverseRemarkValue().trim().isEmpty()) {
					form.setAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemarkValue().trim());
				} else {
					form.setAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemarkValue());
				}
			}
		} else {
			form.setIsAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemark());
		}

		if (requestDTO.getBodyDetails().get(0).getAudit() != null
				&& !requestDTO.getBodyDetails().get(0).getAudit().trim().isEmpty()) {
			form.setAuditType(requestDTO.getBodyDetails().get(0).getAudit().trim());
		} else {
			form.setAuditType(requestDTO.getBodyDetails().get(0).getAudit());
		}

		if (requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim().isEmpty()) {
			form.setAvgAnnualTurnover(requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim());
		} else {
			form.setAvgAnnualTurnover(requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover());
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryExposurePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim().isEmpty()) {
			form.setIndustryExposer(requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim());
		} else {
			form.setIndustryExposer(requestDTO.getBodyDetails().get(0).getIndustryExposurePercent());
		}

		form.setIsDirecOtherBank(requestDTO.getBodyDetails().get(0).getIsBorrowerDirector());
		form.setIsPartnerOtherBank(requestDTO.getBodyDetails().get(0).getIsBorrowerPartner());
		form.setIsSubstantialOtherBank(requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank());
		form.setIsHdfcDirecRltv(requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank());
		form.setIsObkDirecRltv(requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman());
		form.setIsPartnerDirecRltv(requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks());
		form.setIsSubstantialRltvHdfcOther(requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank());

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerDirector() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerDirector())) {
			form.setDirecOtherBank(requestDTO.getBodyDetails().get(0).getBorrowerDirectorValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsBorrowerPartner() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerPartner())) {
			form.setPartnerOtherBank(requestDTO.getBodyDetails().get(0).getBorrowerPartnerValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank())) {
			form.setSubstantialOtherBank(requestDTO.getBodyDetails().get(0).getDirectorOfOtherBankValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank())) {
			form.setHdfcDirecRltv(requestDTO.getBodyDetails().get(0).getRelativeOfHDFCBankValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman())) {
			form.setObkDirecRltv(requestDTO.getBodyDetails().get(0).getRelativeOfChairmanValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks())) {
			form.setPartnerDirecRltv(requestDTO.getBodyDetails().get(0).getPartnerRelativeOfBanksValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank())) {
			form.setSubstantialRltvHdfcOther(requestDTO.getBodyDetails().get(0).getShareholderRelativeOfBankValue());
		}

		if (requestDTO.getBodyDetails().get(0).getBackedByGovt() != null
				&& !requestDTO.getBodyDetails().get(0).getBackedByGovt().trim().isEmpty()) {
			form.setIsBackedByGovt(requestDTO.getBodyDetails().get(0).getBackedByGovt().trim());
		} else {
			form.setIsBackedByGovt(requestDTO.getBodyDetails().get(0).getBackedByGovt());
		}

		if (requestDTO.getBodyDetails().get(0).getFirstYear() != null
				&& requestDTO.getBodyDetails().get(0).getFirstYear().trim().isEmpty()) {
			form.setFirstYear(requestDTO.getBodyDetails().get(0).getFirstYear().trim());
		} else {
			form.setFirstYear(requestDTO.getBodyDetails().get(0).getFirstYear());
		}

		if (requestDTO.getBodyDetails().get(0).getFirstYearTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim().isEmpty()) {
			form.setFirstYearTurnover(requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim());
		} else {
			form.setFirstYearTurnover(requestDTO.getBodyDetails().get(0).getFirstYearTurnover());
		}

		if (requestDTO.getBodyDetails().get(0).getTurnoverCurrency() != null
				&& !requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim().isEmpty()) {
			form.setFirstYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
			form.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
			form.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
		} else {
			form.setFirstYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
			form.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
			form.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
		}

		// System info (one-to-many)
		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List sysList = new ArrayList();
			if (requestDTO.getBodyDetails().get(0).getSystemDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getSystemDetReqList().isEmpty()) {
				for (PartySystemDetailsRestRequestDTO systemDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getSystemDetReqList()) {

					if (systemDetReqDTO.getSystem() != null && !systemDetReqDTO.getSystem().trim().isEmpty()) {
						form.setSystem(systemDetReqDTO.getSystem().trim());
					} else {
						form.setSystem(systemDetReqDTO.getSystem());
					}

					if (systemDetReqDTO.getSystemId() != null && !systemDetReqDTO.getSystemId().trim().isEmpty()) {
						if (systemDetReqDTO.getSystemId().trim().length() <= 16) {
							form.setSystemId(systemDetReqDTO.getSystemId().trim());
							form.setSystemCustomerId(systemDetReqDTO.getSystemId().trim());
						}
					} else {
						form.setSystemId(systemDetReqDTO.getSystemId());
						form.setSystemCustomerId(systemDetReqDTO.getSystemId());
					}

					sysList.add(systemDetReqDTO);
				}
			}
			form.setOtherSystem(sysList);
		}

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List venList = new ArrayList();
			if (requestDTO.getBodyDetails().get(0).getVendorDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getVendorDetReqList().isEmpty()) {
				for (PartyVendorDetailsRestRequestDTO vendorDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getVendorDetReqList()) {

					if (vendorDetReqDTO.getVendorName() != null && !vendorDetReqDTO.getVendorName().trim().isEmpty()) {
						form.setVendorName(vendorDetReqDTO.getVendorName().trim());
					} else {
						form.setVendorName(vendorDetReqDTO.getVendorName());
					}

					venList.add(vendorDetReqDTO);
				}
			}
			form.setVendor(venList);
		}

		if ("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			List criFacList = new ArrayList();
			if (requestDTO.getBodyDetails().get(0).getCriFacilityList() != null
					&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().isEmpty()) {
				for (PartycriFacilityDetailsRestRequestDTO criFacDetReqDTO : requestDTO.getBodyDetails().get(0)
						.getCriFacilityList()) {

					if (criFacDetReqDTO.getFacilityFor() != null
							&& !criFacDetReqDTO.getFacilityFor().trim().isEmpty()) {
						form.setFacilityFor(criFacDetReqDTO.getFacilityFor().trim());
					} else {
						form.setFacilityFor(criFacDetReqDTO.getFacilityFor());
					}
					if (criFacDetReqDTO.getFacName() != null && !criFacDetReqDTO.getFacName().trim().isEmpty()) {
						form.setFacilityName(criFacDetReqDTO.getFacName().trim());
					} else {
						form.setFacilityName(criFacDetReqDTO.getFacName());
					}
					if (criFacDetReqDTO.getFacAmt() != null && !criFacDetReqDTO.getFacAmt().trim().isEmpty()) {
						form.setFacilityAmount(criFacDetReqDTO.getFacAmt().trim());
					} else {
						form.setFacilityAmount(criFacDetReqDTO.getFacAmt());
					}

					criFacList.add(criFacDetReqDTO);
				}
			}
			form.setCriFacility(criFacList);
		}

		// Banking Method Info(one-to-many)
		List bankList = new ArrayList();
		if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().isEmpty()) {
			for (PartyBankingMethodDetailsRestRequestDTO bankMthdDetReqDTO : requestDTO.getBodyDetails().get(0)
					.getBankingMethodDetailList()) {

				if (bankMthdDetReqDTO.getLeadNodalFlag() != null
						&& !bankMthdDetReqDTO.getLeadNodalFlag().trim().isEmpty()) {
					form.setNodalLead(bankMthdDetReqDTO.getLeadNodalFlag().trim());
				} else {
					form.setNodalLead(bankMthdDetReqDTO.getLeadNodalFlag());
				}

				if (bankMthdDetReqDTO.getBankType() != null && !bankMthdDetReqDTO.getBankType().trim().isEmpty()) {
					form.setBankName(bankMthdDetReqDTO.getBankType().trim());
				} else {
					form.setBankName(bankMthdDetReqDTO.getBankType());
				}

				if (bankMthdDetReqDTO.getBranchId() != null && !bankMthdDetReqDTO.getBranchId().trim().isEmpty()) {
					form.setBankBranchId(bankMthdDetReqDTO.getBranchId().trim());
				} else {
					form.setBankBranchId(bankMthdDetReqDTO.getBranchId());
				}

				bankList.add(bankMthdDetReqDTO);
			}
		}
		form.setOtherBank(bankList);

		// Director info(one-to-many)
		if (requestDTO.getBodyDetails().get(0).getDirectorDetailList() != null
				&& !requestDTO.getBodyDetails().get(0).getDirectorDetailList().isEmpty()) {
			for (PartyDirectorDetailsRestRequestDTO directorDetReqDTO : requestDTO.getBodyDetails().get(0)
					.getDirectorDetailList()) {

				if (directorDetReqDTO.getDirectorName() != null
						&& !directorDetReqDTO.getDirectorName().trim().isEmpty()) {
					form.setDirectorName(directorDetReqDTO.getDirectorName().trim());
				} else {
					form.setDirectorName(directorDetReqDTO.getDirectorName());
				}
				if (directorDetReqDTO.getRelatedDUNSNo() != null
						&& !directorDetReqDTO.getRelatedDUNSNo().trim().isEmpty()) {
					form.setRelatedDUNSNo(directorDetReqDTO.getRelatedDUNSNo().trim());
				} else {
					form.setRelatedDUNSNo(directorDetReqDTO.getRelatedDUNSNo());
				}
				if (directorDetReqDTO.getDinNo() != null && !directorDetReqDTO.getDinNo().trim().isEmpty()) {
					form.setDinNo(directorDetReqDTO.getDinNo().trim());
				} else {
					form.setDinNo(directorDetReqDTO.getDinNo());
				}

				if (directorDetReqDTO.getRelatedType() != null
						&& !directorDetReqDTO.getRelatedType().trim().isEmpty()) {
					form.setRelatedType(directorDetReqDTO.getRelatedType().trim());
				} else {
					form.setRelatedType(directorDetReqDTO.getRelatedType());
				}

				if (directorDetReqDTO.getRelationship() != null
						&& !directorDetReqDTO.getRelationship().trim().isEmpty()) {
					form.setRelationship(directorDetReqDTO.getRelationship().trim());
				} else {
					form.setRelationship(directorDetReqDTO.getRelationship());
				}

				if (directorDetReqDTO.getDirectorEmailId() != null
						&& !directorDetReqDTO.getDirectorEmailId().trim().isEmpty()) {
					form.setDirectorEmail(directorDetReqDTO.getDirectorEmailId().trim());
				} else {
					form.setDirectorEmail(directorDetReqDTO.getDirectorEmailId());
				}

				if (directorDetReqDTO.getDirectorFaxNo() != null
						&& !directorDetReqDTO.getDirectorFaxNo().trim().isEmpty()) {
					form.setDirectorFax(directorDetReqDTO.getDirectorFaxNo().trim());
				} else {
					form.setDirectorFax(directorDetReqDTO.getDirectorFaxNo());
				}

				if (directorDetReqDTO.getDirectorTelNo() != null
						&& !directorDetReqDTO.getDirectorTelNo().trim().isEmpty()) {
					form.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo().trim());
				} else {
					form.setDirectorTelNo(directorDetReqDTO.getDirectorTelNo());
				}

				if (directorDetReqDTO.getDirectorTelephoneStdCode() != null
						&& !directorDetReqDTO.getDirectorTelephoneStdCode().trim().isEmpty()) {
					form.setDirStdCodeTelNo(directorDetReqDTO.getDirectorTelephoneStdCode().trim());
				} else {
					form.setDirStdCodeTelNo(directorDetReqDTO.getDirectorTelephoneStdCode());
				}

				if (directorDetReqDTO.getDirectorFaxStdCode() != null
						&& !directorDetReqDTO.getDirectorFaxStdCode().trim().isEmpty()) {
					form.setDirStdCodeTelex(directorDetReqDTO.getDirectorFaxStdCode().trim());
				} else {
					form.setDirStdCodeTelex(directorDetReqDTO.getDirectorFaxStdCode());
				}

				if (directorDetReqDTO.getDirectorCountry() != null
						&& !directorDetReqDTO.getDirectorCountry().trim().isEmpty()) {
					form.setDirectorCountry(directorDetReqDTO.getDirectorCountry().trim());
				} else {
					form.setDirectorCountry(directorDetReqDTO.getDirectorCountry());
				}

				if (directorDetReqDTO.getDirectorState() != null
						&& !directorDetReqDTO.getDirectorState().trim().isEmpty()) {
					form.setDirectorState(directorDetReqDTO.getDirectorState().trim());
				} else {
					form.setDirectorState(directorDetReqDTO.getDirectorState());
				}

				if (directorDetReqDTO.getDirectorCity() != null
						&& !directorDetReqDTO.getDirectorCity().trim().isEmpty()) {
					form.setDirectorCity(directorDetReqDTO.getDirectorCity().trim());
				} else {
					form.setDirectorCity(directorDetReqDTO.getDirectorCity());
				}

				if (directorDetReqDTO.getDirectorRegion() != null
						&& !directorDetReqDTO.getDirectorRegion().trim().isEmpty()) {
					form.setDirectorRegion(directorDetReqDTO.getDirectorRegion().trim());
				} else {
					form.setDirectorRegion(directorDetReqDTO.getDirectorRegion());
				}

				if (directorDetReqDTO.getDirectorPincode() != null
						&& !directorDetReqDTO.getDirectorPincode().trim().isEmpty()) {
					form.setDirectorPostCode(directorDetReqDTO.getDirectorPincode().trim());
				} else {
					form.setDirectorPostCode(directorDetReqDTO.getDirectorPincode());
				}

				if (directorDetReqDTO.getDirectorAddr3() != null
						&& !directorDetReqDTO.getDirectorAddr3().trim().isEmpty()) {
					form.setDirectorAddress3(directorDetReqDTO.getDirectorAddr3().trim());
				} else {
					form.setDirectorAddress3(directorDetReqDTO.getDirectorAddr3());
				}

				if (directorDetReqDTO.getDirectorAddr2() != null
						&& !directorDetReqDTO.getDirectorAddr2().trim().isEmpty()) {
					form.setDirectorAddress2(directorDetReqDTO.getDirectorAddr2().trim());
				} else {
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr2());
				}

				if (directorDetReqDTO.getDirectorAddr1() != null
						&& !directorDetReqDTO.getDirectorAddr1().trim().isEmpty()) {
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr1().trim());
				} else {
					form.setDirectorAddress1(directorDetReqDTO.getDirectorAddr1());
				}

				if (directorDetReqDTO.getPercentageOfControl() != null
						&& !directorDetReqDTO.getPercentageOfControl().trim().isEmpty()) {
					form.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl().trim());
				} else {
					form.setPercentageOfControl(directorDetReqDTO.getPercentageOfControl());
				}

				if (directorDetReqDTO.getFullName() != null && !directorDetReqDTO.getFullName().trim().isEmpty()) {
					form.setFullName(directorDetReqDTO.getFullName().trim());
				} else {
					form.setFullName(directorDetReqDTO.getFullName());
				}

				if (directorDetReqDTO.getNamePrefix() != null && !directorDetReqDTO.getNamePrefix().trim().isEmpty()) {
					form.setNamePrefix(directorDetReqDTO.getNamePrefix().trim());
				} else {
					form.setNamePrefix(directorDetReqDTO.getNamePrefix());
				}

				if (directorDetReqDTO.getDirectorPAN() != null
						&& !directorDetReqDTO.getDirectorPAN().trim().isEmpty()) {
					form.setDirectorPan(directorDetReqDTO.getDirectorPAN().trim());
				} else {
					form.setDirectorPan(directorDetReqDTO.getDirectorPAN());
				}

				if (directorDetReqDTO.getBusinessEntityName() != null
						&& !directorDetReqDTO.getBusinessEntityName().trim().isEmpty()) {
					form.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName().trim());
				} else {
					form.setBusinessEntityName(directorDetReqDTO.getBusinessEntityName());
				}

				if (directorDetReqDTO.getDirectorAADHAR() != null
						&& !directorDetReqDTO.getDirectorAADHAR().trim().isEmpty()) {
					form.setDirectorAadhar(directorDetReqDTO.getDirectorAADHAR().trim());
				} else {
					form.setDirectorAadhar(directorDetReqDTO.getDirectorAADHAR());
				}
			}
		}

		// contact info
		form.setContactType("CORPORATE");
		if (requestDTO.getBodyDetails().get(0).getAddress1() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress1().trim().isEmpty()) {
			form.setAddress1(requestDTO.getBodyDetails().get(0).getAddress1().trim());
		} else {
			form.setAddress1(requestDTO.getBodyDetails().get(0).getAddress1());
		}

		if (requestDTO.getBodyDetails().get(0).getAddress2() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress2().trim().isEmpty()) {
			form.setAddress2(requestDTO.getBodyDetails().get(0).getAddress2().trim());
		} else {
			form.setAddress2(requestDTO.getBodyDetails().get(0).getAddress2());
		}

		if (requestDTO.getBodyDetails().get(0).getAddress3() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress3().trim().isEmpty()) {
			form.setAddress3(requestDTO.getBodyDetails().get(0).getAddress3().trim());
		} else {
			form.setAddress3(requestDTO.getBodyDetails().get(0).getAddress3());
		}

		if (requestDTO.getBodyDetails().get(0).getRegion() != null
				&& !requestDTO.getBodyDetails().get(0).getRegion().trim().isEmpty()) {
			form.setRegion(requestDTO.getBodyDetails().get(0).getRegion().trim());
		} else {
			form.setRegion(requestDTO.getBodyDetails().get(0).getRegion());
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			form.setCountry(requestDTO.getBodyDetails().get(0).getCountry().trim());
		} else {
			form.setCountry(requestDTO.getBodyDetails().get(0).getCountry());
		}

		if (requestDTO.getBodyDetails().get(0).getState() != null
				&& !requestDTO.getBodyDetails().get(0).getState().trim().isEmpty()) {
			form.setState(requestDTO.getBodyDetails().get(0).getState().trim());
		} else {
			form.setState(requestDTO.getBodyDetails().get(0).getState());
		}

		if (requestDTO.getBodyDetails().get(0).getCity() != null
				&& !requestDTO.getBodyDetails().get(0).getCity().trim().isEmpty()) {
			form.setCity(requestDTO.getBodyDetails().get(0).getCity().trim());
		} else {
			form.setCity(requestDTO.getBodyDetails().get(0).getCity());
		}

		if (requestDTO.getBodyDetails().get(0).getPincode() != null
				&& !requestDTO.getBodyDetails().get(0).getPincode().trim().isEmpty()) {
			form.setPostcode(requestDTO.getBodyDetails().get(0).getPincode().trim());
		} else {
			form.setPostcode(requestDTO.getBodyDetails().get(0).getPincode());
		}

		if (requestDTO.getBodyDetails().get(0).getEmailId() != null
				&& !requestDTO.getBodyDetails().get(0).getEmailId().trim().isEmpty()) {
			form.setEmail(requestDTO.getBodyDetails().get(0).getEmailId().trim());
		} else {
			form.setEmail(requestDTO.getBodyDetails().get(0).getEmailId());
		}

		if (requestDTO.getBodyDetails().get(0).getFaxNumber() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxNumber().trim().isEmpty()) {
			form.setTelex(requestDTO.getBodyDetails().get(0).getFaxNumber().trim());
		} else {
			form.setTelex(requestDTO.getBodyDetails().get(0).getFaxNumber());
		}

		if (requestDTO.getBodyDetails().get(0).getTelephoneNo() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneNo().trim().isEmpty()) {
			form.setTelephoneNo(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
		} else {
			form.setTelephoneNo(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
		}

		if (requestDTO.getBodyDetails().get(0).getTelephoneStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim().isEmpty()) {
			form.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim());
		} else {
			form.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode());
		}

		if (requestDTO.getBodyDetails().get(0).getFaxStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxStdCode().trim().isEmpty()) {
			form.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode().trim());
		} else {
			form.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode());
		}

		if (requestDTO.getBodyDetails().get(0).getStatus() != null
				&& !requestDTO.getBodyDetails().get(0).getStatus().trim().isEmpty()) {
			form.setStatus(requestDTO.getBodyDetails().get(0).getStatus().trim());
		} else {
			form.setStatus(requestDTO.getBodyDetails().get(0).getStatus());
		}

//		if (requestDTO.getBodyDetails().get(0).getYearEndPeriod() != null
//				&& !requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim().isEmpty()) {
//			form.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim());
//		} else {
//			form.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod());
//		}

		if (null != requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer()
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim().isEmpty()) {
			form.setMultBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim());
		} else {
			form.setMultBankFundBasedHdfcBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer()
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim().isEmpty()) {
			form.setMultBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim());
		} else {
			form.setMultBankFundBasedLeadBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer()
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			form.setMultBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim());
		} else {
			form.setMultBankNonFundBasedHdfcBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer()
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			form.setMultBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim());
		} else {
			form.setMultBankNonFundBasedLeadBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer()
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim().isEmpty()) {
			form.setConsBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim());
		} else {
			form.setConsBankFundBasedHdfcBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer()
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim().isEmpty()) {
			form.setConsBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim());
		} else {
			form.setConsBankFundBasedLeadBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer()
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			form.setConsBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim());
		} else {
			form.setConsBankNonFundBasedHdfcBankPer("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer()
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			form.setConsBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim());
		} else {
			form.setConsBankNonFundBasedLeadBankPer("");
		}

		getCoBorrowerDetailsFormFromDTORest(requestDTO, form);
		udfDetailsRestDTOMapper.getUdfFormFromDTO(requestDTO, form, requestDTO.getBodyDetails().get(0).getEvent());
		return form;
	}

	private static void getCoBorrowerDetailsFormFromDTORest(PartyDetailsRestRequestDTO request,
			ManualInputCustomerInfoForm form) {

		if (request == null || request.getBodyDetails().get(0).getCoBorrowerDetailsList() == null)
			return;

		form.setCoBorrowerDetailsInd(request.getBodyDetails().get(0).getCoBorrowerDetailsInd());

		PartyCoBorrowerDetailsRestRequestDTO coBorrowerRequest = request.getBodyDetails().get(0)
				.getCoBorrowerDetailsList();
		ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
		if (!"Rest_create_customer".equalsIgnoreCase(request.getBodyDetails().get(0).getEvent())) {
			List<CoBorrowerDetailsRequestDTO> coBorrowerList1 = custDAO
					.getCoBorrowerListWS(request.getBodyDetails().get(0).getClimsPartyId());

			if (coBorrowerList1 != null) {

				List<CoBorrowerDetailsForm> coBorrowerDetails = new ArrayList<CoBorrowerDetailsForm>();
				for (CoBorrowerDetailsRequestDTO coBorrower : coBorrowerList1) {

					CoBorrowerDetailsForm coBorrowerForm = new CoBorrowerDetailsForm();

					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
						coBorrowerForm.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
						coBorrowerForm.setCoBorrowerName(coBorrower.getCoBorrowerName());

					coBorrowerForm.setIsInterfaced("Y");

					coBorrowerDetails.add(coBorrowerForm);
				}
				form.setCoBorrowerDetails(coBorrowerDetails);

			}

		} else {
			List<CoBorrowerDetailsRestRequestDTO> coBorrowerList = coBorrowerRequest.getCoBorrowerDetails();

			if (coBorrowerList != null) {
				List<CoBorrowerDetailsForm> coBorrowerDetails = new ArrayList<CoBorrowerDetailsForm>();
				for (CoBorrowerDetailsRestRequestDTO coBorrower : coBorrowerList) {

					CoBorrowerDetailsForm coBorrowerForm = new CoBorrowerDetailsForm();

					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
						coBorrowerForm.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
					if (StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
						coBorrowerForm.setCoBorrowerName(coBorrower.getCoBorrowerName());

					coBorrowerForm.setIsInterfaced("Y");

					coBorrowerDetails.add(coBorrowerForm);
				}
				form.setCoBorrowerDetails(coBorrowerDetails);
			}
		}
	}

	public ICMSCustomer getActualFromDTORest(PartyDetailsRestRequestDTO requestDTO, ICMSCustomer customerInstance)
			throws CMSValidationFault {
		SimpleDateFormat relationshipDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		relationshipDateFormat.setLenient(false);
		SimpleDateFormat relationshipDateFormatForLeiDate = new SimpleDateFormat("dd-MM-yyyy");
		relationshipDateFormatForLeiDate.setLenient(false);
		SimpleDateFormat cautionlistDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		cautionlistDateFormat.setLenient(false);
		SimpleDateFormat cautionDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		cautionDateFormat.setLenient(false);

		ICMSCustomer obCMSCustomerIntance = null;
		if (customerInstance != null) {
			obCMSCustomerIntance = customerInstance;
		} else {
			obCMSCustomerIntance = new OBCMSCustomer();
		}

		ICMSLegalEntity cmsLegalEntity = new OBCMSLegalEntity();

		if (null != requestDTO.getBodyDetails().get(0).getBusinessGroup()
				&& !requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().isEmpty()) {
			obCMSCustomerIntance.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim());
		} else {
			obCMSCustomerIntance.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup());
		}

		if (null != requestDTO.getBodyDetails().get(0).getMainBranch()
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()) {
			obCMSCustomerIntance.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch().trim());
		} else {
			obCMSCustomerIntance.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch());
		}

		if (null != requestDTO.getBodyDetails().get(0).getMainBranch()
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()
				&& requestDTO.getBodyDetails().get(0).getMainBranch().trim().contains("-")) {
			obCMSCustomerIntance.setBranchCode(requestDTO.getBodyDetails().get(0).getMainBranch().split("-")[0]);
		} else {
			obCMSCustomerIntance.setBranchCode("");
		}

		// New fields added for Wholesale Rest API

		if (requestDTO.getBodyDetails().get(0).getStatus() != null
				&& !requestDTO.getBodyDetails().get(0).getStatus().trim().isEmpty()) {
			obCMSCustomerIntance.setStatus(requestDTO.getBodyDetails().get(0).getStatus().trim());
		} else {
			obCMSCustomerIntance.setStatus(requestDTO.getBodyDetails().get(0).getStatus());
		}

//		if (requestDTO.getBodyDetails().get(0).getYearEndPeriod() != null
//				&& !requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim().isEmpty()) {
//			obCMSCustomerIntance.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim());
//		} else {
//			obCMSCustomerIntance.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod());
//		}

		if (requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setMultBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim());
		} else {
			obCMSCustomerIntance.setMultBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setMultBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim());
		} else {
			obCMSCustomerIntance.setMultBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setMultBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim());
		} else {
			obCMSCustomerIntance.setMultBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setMultBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim());
		} else {
			obCMSCustomerIntance.setMultBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setConsBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim());
		} else {
			obCMSCustomerIntance.setConsBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setConsBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim());
		} else {
			obCMSCustomerIntance.setConsBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setConsBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim());
		} else {
			obCMSCustomerIntance.setConsBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			obCMSCustomerIntance.setConsBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim());
		} else {
			obCMSCustomerIntance.setConsBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer());
		}

		if (null != requestDTO.getBodyDetails().get(0).getCinLlpin()
				&& !requestDTO.getBodyDetails().get(0).getCinLlpin().trim().isEmpty()) {
			obCMSCustomerIntance.setCinLlpin(requestDTO.getBodyDetails().get(0).getCinLlpin().trim());
		} else {
			obCMSCustomerIntance.setCinLlpin("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getDateOfIncorporation()
				&& !requestDTO.getBodyDetails().get(0).getDateOfIncorporation().trim().isEmpty()) {
			String sDate1 = requestDTO.getBodyDetails().get(0).getDateOfIncorporation();
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd/MMM/yyyy").parse(sDate1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			obCMSCustomerIntance.setDateOfIncorporation(date1);
		} else {
			obCMSCustomerIntance.setDateOfIncorporation(null);
		}

		if (null != requestDTO.getBodyDetails().get(0).getCycle()
				&& !requestDTO.getBodyDetails().get(0).getCycle().trim().isEmpty()) {
			obCMSCustomerIntance.setCycle(requestDTO.getBodyDetails().get(0).getCycle().trim());
		} else {
			obCMSCustomerIntance.setCycle(DEFAULT_CYCLE);
		}
		char for6061check = '\0';
		if (null != requestDTO.getBodyDetails().get(0).getForm6061()
				&& !requestDTO.getBodyDetails().get(0).getForm6061().trim().isEmpty()) {
			obCMSCustomerIntance.setForm6061checked(requestDTO.getBodyDetails().get(0).getForm6061().trim().charAt(0));
		} else {
			obCMSCustomerIntance.setForm6061checked(for6061check);
		}

		if (null != requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan()
				&& !requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim().isEmpty()) {
			obCMSCustomerIntance
					.setExceptionalCases(requestDTO.getBodyDetails().get(0).getExceptionalCasesSpan().trim());
		} else {
			obCMSCustomerIntance.setExceptionalCases("");
		}

		// +++++++++++++++++++++++++++++

		/*
		 * if(requestDTO.getBodyDetails().get(0).getRelationshipManager()!=null &&
		 * !requestDTO.getBodyDetails().get(0).getRelationshipManager().trim().isEmpty()
		 * ){
		 * obCMSCustomerIntance.setRelationshipMgr(requestDTO.getBodyDetails().get(0).
		 * getRelationshipManager().trim()); }else{
		 * obCMSCustomerIntance.setRelationshipMgr(requestDTO.getBodyDetails().get(0).
		 * getRelationshipManager()); }
		 * 
		 * String rmRegion = "-";
		 * if(requestDTO.getBodyDetails().get(0).getRelationshipManager()!=null &&
		 * !requestDTO.getBodyDetails().get(0).getRelationshipManager().trim().isEmpty()
		 * ){ try{ Object relshpObj =
		 * masterAccessUtilityObj.getMaster("actualRelationshipMgr", new
		 * Long(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim()));
		 * if(relshpObj!=null && !"".equals(relshpObj)){ rmRegion =
		 * Long.toString(((IRelationshipMgr)relshpObj).getRegion().getIdRegion());
		 * }else{ errors.add("relationshipMgr",new
		 * ActionMessage("error.invalid.field.value")); } }catch (Exception e) {
		 * DefaultLogger.error(this, e.getMessage()); errors.add("relationshipMgr",new
		 * ActionMessage("error.invalid.field.value")); } }
		 * 
		 * if(rmRegion!=null && !rmRegion.trim().isEmpty()){
		 * obCMSCustomerIntance.setRmRegion(rmRegion.trim()); }else{
		 * obCMSCustomerIntance.setRmRegion(rmRegion); }
		 */
		// String rmMgrCode =
		// requestDTO.getBodyDetails().get(0).getRelationshipManager();
		// String cpsId = requestDTO.getBodyDetails().get(0).getRelationshipManager();
		// String rmMgrCode = fetchRMDataRmMgrCode(cpsId);
		obCMSCustomerIntance.setRelationshipMgrEmpCode(requestDTO.getBodyDetails().get(0).getRelationshipMgrCode());

		List data = fetchRMData(requestDTO.getBodyDetails().get(0).getRelationshipMgrCode());

		String relationshipMgr = "-";
		String rmRegion = "-";

		if (!data.isEmpty()) {
			relationshipMgr = (String) data.get(0);
			rmRegion = (String) data.get(1);
		}

		requestDTO.getBodyDetails().get(0).setRelationshipManager(relationshipMgr);

		IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO) BeanHouse.get("relationshipMgrDAO");
		if (null != requestDTO.getBodyDetails().get(0).getRelationshipManager()
				&& !"".equals(requestDTO.getBodyDetails().get(0).getRelationshipManager())) {
			// IRelationshipMgr relationshipMgrObj =
			// relationshipMgrDAOImpl.getRelationshipMgrByName(requestDTO.getBodyDetails().get(0).getRelationshipManager());
			IRelationshipMgr relationshipMgrObj = relationshipMgrDAOImpl.getRelationshipMgrByNameAndRMCode(
					requestDTO.getBodyDetails().get(0).getRelationshipManager(),
					requestDTO.getBodyDetails().get(0).getRelationshipMgrCode());
			if (relationshipMgrObj != null) {
				obCMSCustomerIntance.setRelationshipMgr(relationshipMgrObj.getId() + "");
			} else {
				obCMSCustomerIntance.setRelationshipMgr("");
			}
		}
		if (null != rmRegion && !"".equals(rmRegion)) {
			IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(rmRegion);
			if (region != null) {
				obCMSCustomerIntance.setRmRegion(region.getIdRegion() + "");
			} else {
				obCMSCustomerIntance.setRmRegion("");
			}
		}

		if (requestDTO.getBodyDetails().get(0).getEntity() != null
				&& !requestDTO.getBodyDetails().get(0).getEntity().trim().isEmpty()) {
			obCMSCustomerIntance.setEntity(requestDTO.getBodyDetails().get(0).getEntity().trim());
		} else {
			obCMSCustomerIntance.setEntity(requestDTO.getBodyDetails().get(0).getEntity());
		}
		if (requestDTO.getBodyDetails().get(0).getPAN() != null
				&& !requestDTO.getBodyDetails().get(0).getPAN().trim().isEmpty()) {
			obCMSCustomerIntance.setPan(requestDTO.getBodyDetails().get(0).getPAN().trim());
		} else {
			obCMSCustomerIntance.setPan(requestDTO.getBodyDetails().get(0).getPAN());
		}

		if (requestDTO.getBodyDetails().get(0).getRBIIndustryCode() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim().isEmpty()) {
			obCMSCustomerIntance.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim());
		} else {
			obCMSCustomerIntance.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode());
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryName() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryName().trim().isEmpty()) {
			obCMSCustomerIntance.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName().trim());
		} else {
			obCMSCustomerIntance.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName());
		}

		// Financial Details
		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethod().trim().isEmpty()) {
			obCMSCustomerIntance.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod().trim());
		} else {
			obCMSCustomerIntance.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim().isEmpty()) {
			obCMSCustomerIntance.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim());
		} else {
			obCMSCustomerIntance.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim().isEmpty()) {
			obCMSCustomerIntance
					.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim());
		} else {
			obCMSCustomerIntance.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim().isEmpty()) {
			obCMSCustomerIntance
					.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim());
		} else {
			obCMSCustomerIntance.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getMemoExposure().trim().isEmpty()) {
			obCMSCustomerIntance.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure().trim());
		} else {
			obCMSCustomerIntance.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getMpbf() != null
				&& !requestDTO.getBodyDetails().get(0).getMpbf().trim().isEmpty()) {
			obCMSCustomerIntance.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf().trim());
		} else {
			obCMSCustomerIntance.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf());
		}

		if (requestDTO.getBodyDetails().get(0).getPartyName() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyName().trim().isEmpty()) {
			obCMSCustomerIntance.setCustomerNameUpper(requestDTO.getBodyDetails().get(0).getPartyName().trim());
			obCMSCustomerIntance.setCustomerName(requestDTO.getBodyDetails().get(0).getPartyName().trim());
		} else {
			obCMSCustomerIntance.setCustomerNameUpper(requestDTO.getBodyDetails().get(0).getPartyName());
			obCMSCustomerIntance.setCustomerName(requestDTO.getBodyDetails().get(0).getPartyName());
		}

		if (requestDTO.getBodyDetails().get(0).getSegment() != null
				&& !requestDTO.getBodyDetails().get(0).getSegment().trim().isEmpty()) {
			obCMSCustomerIntance.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment().trim());
		} else {
			obCMSCustomerIntance.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment());
		}

		obCMSCustomerIntance.setStatus(DEFAULT_STATUS);
		// obCMSCustomerIntance.setCycle(DEFAULT_CYCLE);
		// obCMSCustomerIntance.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if (requestDTO.getBodyDetails().get(0).getNonFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim().isEmpty()) {
			obCMSCustomerIntance
					.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim());
		} else {
			obCMSCustomerIntance
					.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getDpSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getDpSharePercent().trim().isEmpty()) {
			obCMSCustomerIntance.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent().trim());
		} else {
			obCMSCustomerIntance.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus() != null
				&& !requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().trim().isEmpty()) {
			obCMSCustomerIntance.setWillfulDefaultStatus(requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus());
			if (requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().equals("DEFAULTER")
					|| requestDTO.getBodyDetails().get(0).getWillfulDefaultStatus().equals("1")) {

				if (null != requestDTO.getBodyDetails().get(0).getDateWillfulDefault()
						&& !requestDTO.getBodyDetails().get(0).getDateWillfulDefault().trim().isEmpty()) {
					String sDate1 = requestDTO.getBodyDetails().get(0).getDateWillfulDefault();
					Date dateWillfulDefault = null;
					try {
						dateWillfulDefault = new SimpleDateFormat("dd/MMM/yyyy").parse(sDate1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					obCMSCustomerIntance.setDateWillfulDefault(dateWillfulDefault);
				} else {
					obCMSCustomerIntance.setDateWillfulDefault(null);
				}

				obCMSCustomerIntance.setSuitFilledStatus(requestDTO.getBodyDetails().get(0).getSuitFilledStatus());
				obCMSCustomerIntance.setSuitReferenceNo(requestDTO.getBodyDetails().get(0).getSuitReferenceNo().trim());
				obCMSCustomerIntance.setSuitAmount(requestDTO.getBodyDetails().get(0).getSuitAmount().trim());

				if (null != requestDTO.getBodyDetails().get(0).getDateOfSuit()
						&& !requestDTO.getBodyDetails().get(0).getDateOfSuit().trim().isEmpty()) {
					String sDate1 = requestDTO.getBodyDetails().get(0).getDateOfSuit();
					Date dateOfSuit = null;
					try {
						dateOfSuit = new SimpleDateFormat("dd/MMM/yyyy").parse(sDate1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					obCMSCustomerIntance.setDateOfSuit(dateOfSuit);
				} else {
					obCMSCustomerIntance.setDateOfSuit(null);
				}
			}
		}

		obCMSCustomerIntance.setSubLine(requestDTO.getBodyDetails().get(0).getSubLine().trim());

		Double totFundedLimit = (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim())
						: 0d;
		Double totNonFundedLimit = (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim())
						: 0d;
		Double memoExposure = (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getMemoExposure().trim()))
						? Double.parseDouble(requestDTO.getBodyDetails().get(0).getMemoExposure().trim())
						: 0d;

		Double sancLimit = totFundedLimit + totNonFundedLimit + memoExposure;

		obCMSCustomerIntance.setTotalSanctionedLimit(sancLimit.toString());

		if (totFundedLimit != null && !"".equals(totFundedLimit)
				&& requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !"".equals(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim())) {
			Double fundedShareLimit = totFundedLimit
					* Double.parseDouble(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim()) / 100;
			obCMSCustomerIntance.setFundedShareLimit(fundedShareLimit.toString());
		} else {
			obCMSCustomerIntance.setFundedShareLimit("0");
		}

		if (totNonFundedLimit != null && !"".equals(totNonFundedLimit)) {
			Double nonFundedShareLimit = totNonFundedLimit * Double.parseDouble(DEFAULT_NON_FUNDED_SHARE_PERCENT) / 100;
			obCMSCustomerIntance.setNonFundedShareLimit(nonFundedShareLimit.toString());
		} else {
			obCMSCustomerIntance.setNonFundedShareLimit("0");
		}

		obCMSCustomerIntance.setDomicileCountry("IN");

		try {
			if (requestDTO.getBodyDetails().get(0).getRelationshipStartDate() != null
					&& !requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim().isEmpty()) {
				obCMSCustomerIntance.setCustomerRelationshipStartDate(relationshipDateFormat
						.parse(requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Santosh LEI CR
		try {
			if (requestDTO.getBodyDetails().get(0).getLeiExpDate() != null
					&& !requestDTO.getBodyDetails().get(0).getLeiExpDate().trim().isEmpty()) {
				obCMSCustomerIntance.setLeiExpDate(
						relationshipDateFormat.parse(requestDTO.getBodyDetails().get(0).getLeiExpDate().trim()));
			} else {
				obCMSCustomerIntance.setLeiExpDate(null);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (requestDTO.getBodyDetails().get(0).getLeiCode() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiCode().trim().isEmpty()) {
			obCMSCustomerIntance.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode().trim());
		} else {
			obCMSCustomerIntance.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode());
		}
		// cmsLegalEntity
		if (requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim().isEmpty()) {
			cmsLegalEntity.setMultBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer().trim());
		} else {
			cmsLegalEntity.setMultBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim().isEmpty()) {
			cmsLegalEntity.setMultBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer().trim());
		} else {
			cmsLegalEntity.setMultBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			cmsLegalEntity.setMultBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer().trim());
		} else {
			cmsLegalEntity.setMultBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			cmsLegalEntity.setMultBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer().trim());
		} else {
			cmsLegalEntity.setMultBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getMultBankNonFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim().isEmpty()) {
			cmsLegalEntity.setConsBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer().trim());
		} else {
			cmsLegalEntity.setConsBankFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedLeadBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim().isEmpty()) {
			cmsLegalEntity.setConsBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer().trim());
		} else {
			cmsLegalEntity.setConsBankFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim().isEmpty()) {
			cmsLegalEntity.setConsBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer().trim());
		} else {
			cmsLegalEntity.setConsBankNonFundBasedHdfcBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedHdfcBankPer());
		}
		if (requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer() != null
				&& !requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim().isEmpty()) {
			cmsLegalEntity.setConsBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer().trim());
		} else {
			cmsLegalEntity.setConsBankNonFundBasedLeadBankPer(
					requestDTO.getBodyDetails().get(0).getConsBankNonFundBasedLeadBankPer());
		}

		try {
			if (requestDTO.getBodyDetails().get(0).getLeiExpDate() != null
					&& !requestDTO.getBodyDetails().get(0).getLeiExpDate().trim().isEmpty()) {
				cmsLegalEntity.setLeiExpDate(
						relationshipDateFormat.parse(requestDTO.getBodyDetails().get(0).getLeiExpDate().trim()));
			} else {
				cmsLegalEntity.setLeiExpDate(null);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (requestDTO.getBodyDetails().get(0).getLeiCode() != null
				&& !requestDTO.getBodyDetails().get(0).getLeiCode().trim().isEmpty()) {
			cmsLegalEntity.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode().trim());
		} else {
			cmsLegalEntity.setLeiCode(requestDTO.getBodyDetails().get(0).getLeiCode());
		}
		// LEI CR
		if (requestDTO.getBodyDetails().get(0).getBusinessGroup() != null
				&& !requestDTO.getBodyDetails().get(0).getBusinessGroup().trim().isEmpty()) {
			cmsLegalEntity.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup().trim());
		} else {
			cmsLegalEntity.setPartyGroupName(requestDTO.getBodyDetails().get(0).getBusinessGroup());
		}

//		if (requestDTO.getBodyDetails().get(0).getYearEndPeriod() != null
//				&& !requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim().isEmpty()) {
//			cmsLegalEntity.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod().trim());
//		} else {
//			cmsLegalEntity.setYearEndPeriod(requestDTO.getBodyDetails().get(0).getYearEndPeriod());
//		}

		if (requestDTO.getBodyDetails().get(0).getMainBranch() != null
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()) {
			cmsLegalEntity.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch().trim());
		} else {
			cmsLegalEntity.setMainBranch(requestDTO.getBodyDetails().get(0).getMainBranch());
		}

		if (requestDTO.getBodyDetails().get(0).getMainBranch() != null
				&& !requestDTO.getBodyDetails().get(0).getMainBranch().trim().isEmpty()
				&& requestDTO.getBodyDetails().get(0).getMainBranch().trim().contains("-")) {
			cmsLegalEntity.setBranchCode(requestDTO.getBodyDetails().get(0).getMainBranch().trim().split("-")[0]);
		} else {
			cmsLegalEntity.setBranchCode("");
		}

		if (requestDTO.getBodyDetails().get(0).getRelationshipManager() != null
				&& !requestDTO.getBodyDetails().get(0).getRelationshipManager().trim().isEmpty()) {
			cmsLegalEntity.setRelationshipMgr(requestDTO.getBodyDetails().get(0).getRelationshipManager().trim());
		} else {
			cmsLegalEntity.setRelationshipMgr(requestDTO.getBodyDetails().get(0).getRelationshipManager());
		}

		if (rmRegion != null && !rmRegion.trim().isEmpty()) {
			cmsLegalEntity.setRmRegion(rmRegion.trim());
		} else {
			cmsLegalEntity.setRmRegion(rmRegion);
		}

		if (requestDTO.getBodyDetails().get(0).getEntity() != null
				&& !requestDTO.getBodyDetails().get(0).getEntity().trim().isEmpty()) {
			cmsLegalEntity.setEntity(requestDTO.getBodyDetails().get(0).getEntity().trim());
		} else {
			cmsLegalEntity.setEntity(requestDTO.getBodyDetails().get(0).getEntity());
		}
		if (requestDTO.getBodyDetails().get(0).getPAN() != null
				&& !requestDTO.getBodyDetails().get(0).getPAN().trim().isEmpty()) {
			cmsLegalEntity.setPan(requestDTO.getBodyDetails().get(0).getPAN().trim());
		} else {
			cmsLegalEntity.setPan(requestDTO.getBodyDetails().get(0).getPAN());
		}

		if (requestDTO.getBodyDetails().get(0).getRBIIndustryCode() != null
				&& !requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim().isEmpty()) {
			cmsLegalEntity.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode().trim());
		} else {
			cmsLegalEntity.setRbiIndustryCode(requestDTO.getBodyDetails().get(0).getRBIIndustryCode());
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryName() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryName().trim().isEmpty()) {
			cmsLegalEntity.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName().trim());
		} else {
			cmsLegalEntity.setIndustryName(requestDTO.getBodyDetails().get(0).getIndustryName());
		}

		if (requestDTO.getBodyDetails().get(0).getBankingMethod() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethod().trim().isEmpty()) {
			cmsLegalEntity.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod().trim());
		} else {
			cmsLegalEntity.setBankingMethod(requestDTO.getBodyDetails().get(0).getBankingMethod());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim().isEmpty()) {
			cmsLegalEntity.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit().trim());
		} else {
			cmsLegalEntity.setTotalFundedLimit(requestDTO.getBodyDetails().get(0).getTotalFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit() != null
				&& !requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim().isEmpty()) {
			cmsLegalEntity.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit().trim());
		} else {
			cmsLegalEntity.setTotalNonFundedLimit(requestDTO.getBodyDetails().get(0).getTotalNonFundedLimit());
		}

		if (requestDTO.getBodyDetails().get(0).getFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim().isEmpty()) {
			cmsLegalEntity.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent().trim());
		} else {
			cmsLegalEntity.setFundedSharePercent(requestDTO.getBodyDetails().get(0).getFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getMemoExposure() != null
				&& !requestDTO.getBodyDetails().get(0).getMemoExposure().trim().isEmpty()) {
			cmsLegalEntity.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure().trim());
		} else {
			cmsLegalEntity.setMemoExposure(requestDTO.getBodyDetails().get(0).getMemoExposure());
		}

		if (requestDTO.getBodyDetails().get(0).getMpbf() != null
				&& !requestDTO.getBodyDetails().get(0).getMpbf().trim().isEmpty()) {
			cmsLegalEntity.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf().trim());
		} else {
			cmsLegalEntity.setMpbf(requestDTO.getBodyDetails().get(0).getMpbf());
		}

		if (requestDTO.getBodyDetails().get(0).getSegment() != null
				&& !requestDTO.getBodyDetails().get(0).getSegment().trim().isEmpty()) {
			cmsLegalEntity.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment().trim());
		} else {
			cmsLegalEntity.setCustomerSegment(requestDTO.getBodyDetails().get(0).getSegment());
		}
		if (null != requestDTO.getBodyDetails().get(0).getAadharNumber()
				&& !requestDTO.getBodyDetails().get(0).getAadharNumber().trim().isEmpty()) {
			obCMSCustomerIntance.setAadharNumber(requestDTO.getBodyDetails().get(0).getAadharNumber().trim());
		} else {
			obCMSCustomerIntance.setAadharNumber("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo()
				&& !requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim().isEmpty()) {
			obCMSCustomerIntance.setBorrowerDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim());
		} else {
			obCMSCustomerIntance.setBorrowerDUNSNo("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getPartyConsent()
				&& !requestDTO.getBodyDetails().get(0).getPartyConsent().trim().isEmpty()) {
			obCMSCustomerIntance.setPartyConsent(requestDTO.getBodyDetails().get(0).getPartyConsent().trim());
		} else {
			obCMSCustomerIntance.setPartyConsent("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getClassActivity1()
				&& !requestDTO.getBodyDetails().get(0).getClassActivity1().trim().isEmpty()) {
			obCMSCustomerIntance.setClassActivity1(requestDTO.getBodyDetails().get(0).getClassActivity1().trim());
		} else {
			obCMSCustomerIntance.setClassActivity1("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getClassActivity2()
				&& !requestDTO.getBodyDetails().get(0).getClassActivity2().trim().isEmpty()) {
			obCMSCustomerIntance.setClassActivity2(requestDTO.getBodyDetails().get(0).getClassActivity2().trim());
		} else {
			obCMSCustomerIntance.setClassActivity2("");
		}

		obCMSCustomerIntance
				.setIsRBIWilfulDefaultersList(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList());
		if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRBIWilfulDefaultersList())) {
			obCMSCustomerIntance.setNameOfBank(requestDTO.getBodyDetails().get(0).getNameOfBank());
			obCMSCustomerIntance
					.setIsDirectorMoreThanOne(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsDirectorMoreThanOne())) {
				obCMSCustomerIntance.setNameOfDirectorsAndCompany(
						requestDTO.getBodyDetails().get(0).getNameOfDirectorsAndCompany());
			} else {
				obCMSCustomerIntance.setNameOfDirectorsAndCompany("");
			}
		} else {
			obCMSCustomerIntance.setIsRBIWilfulDefaultersList(DEFAULT_VALUE_FOR_CRI_INFO);
			obCMSCustomerIntance.setNameOfBank("");
			obCMSCustomerIntance.setIsDirectorMoreThanOne("");
			obCMSCustomerIntance.setNameOfDirectorsAndCompany("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank()
				&& !requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim().isEmpty()) {
			obCMSCustomerIntance
					.setIsBorrDefaulterWithBank(requestDTO.getBodyDetails().get(0).getIsBorrDefaulterWithBank().trim());
			if (null != requestDTO.getBodyDetails().get(0).getDetailsOfDefault()
					&& !requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim().isEmpty()) {
				obCMSCustomerIntance
						.setDetailsOfDefault(requestDTO.getBodyDetails().get(0).getDetailsOfDefault().trim());
			} else {
				obCMSCustomerIntance.setDetailsOfDefault("");
			}
			if (null != requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff()
					&& !requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim().isEmpty()) {
				obCMSCustomerIntance.setExtOfCompromiseAndWriteoff(
						requestDTO.getBodyDetails().get(0).getExtOfCompromiseAndWriteoff().trim());
			} else {
				obCMSCustomerIntance.setExtOfCompromiseAndWriteoff("");
			}
		} else {
			obCMSCustomerIntance.setIsBorrDefaulterWithBank("");
			obCMSCustomerIntance.setExtOfCompromiseAndWriteoff("");
			obCMSCustomerIntance.setDetailsOfDefault("");
		}

		obCMSCustomerIntance.setIsCibilStatusClean(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean());
		if (requestDTO.getBodyDetails().get(0).getIsCibilStatusClean() != null
				&& "No".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsCibilStatusClean())) {
			obCMSCustomerIntance.setDetailsOfCleanCibil(requestDTO.getBodyDetails().get(0).getDetailsOfCleanCibil());
		} else {
			obCMSCustomerIntance.setIsCibilStatusClean("Yes");
			obCMSCustomerIntance.setDetailsOfCleanCibil("");
		}

		// cmsLegalEntity.setCycle(DEFAULT_CYCLE);
		if (null != requestDTO.getBodyDetails().get(0).getCycle()
				&& !requestDTO.getBodyDetails().get(0).getCycle().trim().isEmpty()) {
			cmsLegalEntity.setCycle(requestDTO.getBodyDetails().get(0).getCycle().trim());
		} else {
			cmsLegalEntity.setCycle(DEFAULT_CYCLE);
		}
		// cmsLegalEntity.setNonFundedSharePercent(DEFAULT_NON_FUNDED_SHARE_PERCENT);
		if (requestDTO.getBodyDetails().get(0).getNonFundedSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim().isEmpty()) {
			cmsLegalEntity
					.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent().trim());
		} else {
			cmsLegalEntity.setNonFundedSharePercent(requestDTO.getBodyDetails().get(0).getNonFundedSharePercent());
		}

		if (requestDTO.getBodyDetails().get(0).getDpSharePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getDpSharePercent().trim().isEmpty()) {
			cmsLegalEntity.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent().trim());
		} else {
			cmsLegalEntity.setDpSharePercent(requestDTO.getBodyDetails().get(0).getDpSharePercent());
		}

		cmsLegalEntity.setSubLine(requestDTO.getBodyDetails().get(0).getSubLine().trim());

		if ("OPEN".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getSubLine())) {
			ISubline[] subLineArray = new OBSubline[50];

			if (requestDTO.getBodyDetails().get(0).getSubLineDetailsList() != null
					&& !requestDTO.getBodyDetails().get(0).getSubLineDetailsList().isEmpty()) {
				for (int i = 0; i < requestDTO.getBodyDetails().get(0).getSubLineDetailsList().size(); i++) {

					OBSubline obSubLineInstance = new OBSubline();
					if (requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getSubLinePartyId() != null
							&& !requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getSubLinePartyId()
									.trim().isEmpty()) {
						obSubLineInstance.setPartyId(Long.parseLong(
								requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getSubLinePartyId()));
					}

					if (requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getGuaranteedAmt() != null
							&& !requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getGuaranteedAmt()
									.trim().isEmpty()) {
						obSubLineInstance.setAmount(
								requestDTO.getBodyDetails().get(0).getSubLineDetailsList().get(i).getGuaranteedAmt());
					}

					subLineArray[i] = obSubLineInstance;
				}
			}

			cmsLegalEntity.setSublineParty(subLineArray);
		}

		if (requestDTO.getBodyDetails().get(0).getPartyName() != null
				&& !requestDTO.getBodyDetails().get(0).getPartyName().trim().isEmpty()) {
			cmsLegalEntity.setShortName(requestDTO.getBodyDetails().get(0).getPartyName().trim());
			cmsLegalEntity.setLegalName(requestDTO.getBodyDetails().get(0).getPartyName().trim());
		} else {
			cmsLegalEntity.setShortName(requestDTO.getBodyDetails().get(0).getPartyName());
			cmsLegalEntity.setLegalName(requestDTO.getBodyDetails().get(0).getPartyName());
		}

		try {
			if (requestDTO.getBodyDetails().get(0).getRelationshipStartDate() != null
					&& !requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim().isEmpty()) {
				cmsLegalEntity.setRelationshipStartDate(relationshipDateFormat
						.parse(requestDTO.getBodyDetails().get(0).getRelationshipStartDate().trim()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ICriInfo[] criInfoArr = new OBCriInfo[1];
		OBCriInfo obCriInfoInstance = new OBCriInfo();

		if (requestDTO.getBodyDetails().get(0).getCustomerRAMId() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim().isEmpty()) {
			obCriInfoInstance.setCustomerRamID(requestDTO.getBodyDetails().get(0).getCustomerRAMId().trim());
		} else {
			obCriInfoInstance.setCustomerRamID(requestDTO.getBodyDetails().get(0).getCustomerRAMId());
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerAPRCode() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim().isEmpty()) {
			obCriInfoInstance.setCustomerAprCode(requestDTO.getBodyDetails().get(0).getCustomerAPRCode().trim());
		} else {
			obCriInfoInstance.setCustomerAprCode(requestDTO.getBodyDetails().get(0).getCustomerAPRCode());
		}

		if (requestDTO.getBodyDetails().get(0).getCustomerExtRating() != null
				&& !requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim().isEmpty()) {
			obCriInfoInstance.setCustomerExtRating(requestDTO.getBodyDetails().get(0).getCustomerExtRating().trim());
		} else {
			obCriInfoInstance.setCustomerExtRating(requestDTO.getBodyDetails().get(0).getCustomerExtRating());
		}

		obCriInfoInstance.setIsNbfs(requestDTO.getBodyDetails().get(0).getNbfcFlag());
		if (requestDTO.getBodyDetails().get(0).getNbfcFlag() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getNbfcFlag())) {
			obCriInfoInstance.setNbfsA(requestDTO.getBodyDetails().get(0).getNbfcA());
			obCriInfoInstance.setNbfsB(requestDTO.getBodyDetails().get(0).getNbfcB());
		}

		if (null != requestDTO.getBodyDetails().get(0).getCustomerFyClouser()
				&& !requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim().isEmpty()) {
			obCriInfoInstance.setCustomerFyClouser(requestDTO.getBodyDetails().get(0).getCustomerFyClouser().trim());
		} else {
			obCriInfoInstance.setCustomerFyClouser("");
		}
		if (requestDTO.getBodyDetails().get(0).getEntityType() != null
				&& !requestDTO.getBodyDetails().get(0).getEntityType().trim().isEmpty()) {
			obCriInfoInstance.setEntityType(requestDTO.getBodyDetails().get(0).getEntityType().trim());
		} else {
			obCriInfoInstance.setEntityType(requestDTO.getBodyDetails().get(0).getEntityType());
		}

		obCriInfoInstance.setIsPrioritySector(requestDTO.getBodyDetails().get(0).getPrioritySector());
		obCriInfoInstance.setMsmeClassification(requestDTO.getBodyDetails().get(0).getMsmeClassification());
		obCriInfoInstance.setIsPermenentSsiCert(requestDTO.getBodyDetails().get(0).getPermSSICert());
		obCriInfoInstance.setIsWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSection());
		if (requestDTO.getBodyDetails().get(0).getWeakerSection() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getWeakerSection())) {
			obCriInfoInstance.setWeakerSection(requestDTO.getBodyDetails().get(0).getWeakerSectionType());
			obCriInfoInstance.setGovtSchemeType(requestDTO.getBodyDetails().get(0).getWeakerSectionValue());
		}
		obCriInfoInstance.setIsKisanCreditCard(requestDTO.getBodyDetails().get(0).getKisanCreditCard());
		obCriInfoInstance.setIsMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunity());
		if (requestDTO.getBodyDetails().get(0).getMinorityCommunity() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getMinorityCommunity())) {
			obCriInfoInstance.setMinorityCommunity(requestDTO.getBodyDetails().get(0).getMinorityCommunityType());
		}
		obCriInfoInstance.setIsCapitalMarketExpos(requestDTO.getBodyDetails().get(0).getCapitalMarketExposure());
		obCriInfoInstance.setIsRealEstateExpos(requestDTO.getBodyDetails().get(0).getRealEstateExposure());
		obCriInfoInstance.setIsCommoditiesExposer(requestDTO.getBodyDetails().get(0).getCommodityExposure());
		obCriInfoInstance.setIsSensitive(requestDTO.getBodyDetails().get(0).getSensitive());
		obCriInfoInstance.setCommoditiesName(requestDTO.getBodyDetails().get(0).getCommodityName());
		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentPM() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim().isEmpty()) {
			obCriInfoInstance.setGrossInvsInPM(requestDTO.getBodyDetails().get(0).getGrossInvestmentPM().trim());
		} else {
			obCriInfoInstance.setGrossInvsInPM("0");
		}
		if (requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip() != null
				&& !requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim().isEmpty()) {
			obCriInfoInstance.setGrossInvsInEquip(requestDTO.getBodyDetails().get(0).getGrossInvestmentEquip().trim());
		} else {
			obCriInfoInstance.setGrossInvsInEquip("0");
		}
		obCriInfoInstance.setPsu(requestDTO.getBodyDetails().get(0).getPsu());
		obCriInfoInstance.setPsuPercentage(requestDTO.getBodyDetails().get(0).getPercentageOfShareholding());
		obCriInfoInstance.setGovtUnderTaking(requestDTO.getBodyDetails().get(0).getGovtUndertaking());
		obCriInfoInstance.setIsProjectFinance(requestDTO.getBodyDetails().get(0).getProjectFinance());
		obCriInfoInstance.setIsInfrastructureFinanace(requestDTO.getBodyDetails().get(0).getInfraFinance());
		obCriInfoInstance.setInfrastructureFinanaceType(requestDTO.getBodyDetails().get(0).getInfraFinanceType());
		obCriInfoInstance.setIsSemsGuideApplicable(requestDTO.getBodyDetails().get(0).getSEMSGuideApplicable());
		obCriInfoInstance.setIsFailIfcExcluList(requestDTO.getBodyDetails().get(0).getFailsUnderIFCExclusion());
		obCriInfoInstance.setIsTufs(requestDTO.getBodyDetails().get(0).getTufs());
		obCriInfoInstance.setIsRbiDefaulter(requestDTO.getBodyDetails().get(0).getRBIDefaulterList());
		if (requestDTO.getBodyDetails().get(0).getRBIDefaulterList() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRBIDefaulterList())) {
			// obCriInfoInstance.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListType());
			// Change for RBI defaulter Type i.e Company ,Director and Group Companies
			if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany()
					&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany().trim().isEmpty()) {
				obCriInfoInstance.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeCompany());
			} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors()
					&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors().trim().isEmpty()) {
				obCriInfoInstance
						.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeDirectors());
			} else if (null != requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies()
					&& !requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies().trim().isEmpty()) {
				obCriInfoInstance
						.setRbiDefaulter(requestDTO.getBodyDetails().get(0).getRbiDefaulterListTypeGroupCompanies());
			}
		}
		obCriInfoInstance.setIsLitigation(requestDTO.getBodyDetails().get(0).getLitigationPending());
		if (requestDTO.getBodyDetails().get(0).getLitigationPending() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getLitigationPending())) {
			obCriInfoInstance.setLitigationBy(requestDTO.getBodyDetails().get(0).getLitigationPendingBy());
		}
		obCriInfoInstance.setIsInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectors());
		if (requestDTO.getBodyDetails().get(0).getInterestOfDirectors() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getInterestOfDirectors())) {
			obCriInfoInstance.setInterestOfBank(requestDTO.getBodyDetails().get(0).getInterestOfDirectorsType());
		}
		obCriInfoInstance.setIsAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemark());
		if (requestDTO.getBodyDetails().get(0).getAdverseRemark() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getAdverseRemark())) {
			obCriInfoInstance.setAdverseRemark(requestDTO.getBodyDetails().get(0).getAdverseRemarkValue());
		}

		if (requestDTO.getBodyDetails().get(0).getAudit() != null
				&& !requestDTO.getBodyDetails().get(0).getAudit().trim().isEmpty()) {
			obCriInfoInstance.setAuditType(requestDTO.getBodyDetails().get(0).getAudit().trim());
		} else {
			obCriInfoInstance.setAuditType(requestDTO.getBodyDetails().get(0).getAudit());
		}

		if (requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim().isEmpty()) {
			obCriInfoInstance.setAvgAnnualTurnover(requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover().trim());
		} else {
			obCriInfoInstance.setAvgAnnualTurnover(requestDTO.getBodyDetails().get(0).getAvgAnnualTurnover());
		}

		if (requestDTO.getBodyDetails().get(0).getIndustryExposurePercent() != null
				&& !requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim().isEmpty()) {
			obCriInfoInstance
					.setIndustryExposer(requestDTO.getBodyDetails().get(0).getIndustryExposurePercent().trim());
		} else {
			obCriInfoInstance.setIndustryExposer("");
		}
		// obCriInfoInstance.setIndustryExposer(requestDTO.getBodyDetails().get(0).getBusinessGroupExposureLimit());

		obCriInfoInstance.setIsDirecOtherBank(requestDTO.getBodyDetails().get(0).getIsBorrowerDirector());
		obCriInfoInstance.setIsPartnerOtherBank(requestDTO.getBodyDetails().get(0).getIsBorrowerPartner());
		obCriInfoInstance.setIsSubstantialOtherBank(requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank());
		obCriInfoInstance.setIsHdfcDirecRltv(requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank());
		obCriInfoInstance.setIsObkDirecRltv(requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman());
		obCriInfoInstance.setIsPartnerDirecRltv(requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks());
		obCriInfoInstance
				.setIsSubstantialRltvHdfcOther(requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank());

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerDirector() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerDirector())) {
			obCriInfoInstance.setDirecOtherBank(requestDTO.getBodyDetails().get(0).getBorrowerDirectorValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsBorrowerPartner() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerPartner())) {
			obCriInfoInstance.setPartnerOtherBank(requestDTO.getBodyDetails().get(0).getBorrowerPartnerValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsDirectorOfOtherBank())) {
			obCriInfoInstance.setSubstantialOtherBank(requestDTO.getBodyDetails().get(0).getDirectorOfOtherBankValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRelativeOfHDFCBank())) {
			obCriInfoInstance.setHdfcDirecRltv(requestDTO.getBodyDetails().get(0).getRelativeOfHDFCBankValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsRelativeOfChairman())) {
			obCriInfoInstance.setObkDirecRltv(requestDTO.getBodyDetails().get(0).getRelativeOfChairmanValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsPartnerRelativeOfBanks())) {
			obCriInfoInstance.setPartnerDirecRltv(requestDTO.getBodyDetails().get(0).getPartnerRelativeOfBanksValue());
		}
		if (requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsShareholderRelativeOfBank())) {
			obCriInfoInstance.setSubstantialRltvHdfcOther(
					requestDTO.getBodyDetails().get(0).getShareholderRelativeOfBankValue());
		}

		obCriInfoInstance.setIsBackedByGovt(requestDTO.getBodyDetails().get(0).getBackedByGovt());

		if (requestDTO.getBodyDetails().get(0).getFirstYear() != null
				&& !requestDTO.getBodyDetails().get(0).getFirstYear().trim().isEmpty()) {
			obCriInfoInstance.setFirstYear(requestDTO.getBodyDetails().get(0).getFirstYear().trim());
		} else {
			obCriInfoInstance.setFirstYear(requestDTO.getBodyDetails().get(0).getFirstYear());
		}

		if (requestDTO.getBodyDetails().get(0).getFirstYearTurnover() != null
				&& !requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim().isEmpty()) {
			obCriInfoInstance.setFirstYearTurnover(requestDTO.getBodyDetails().get(0).getFirstYearTurnover().trim());
		} else {
			obCriInfoInstance.setFirstYearTurnover(requestDTO.getBodyDetails().get(0).getFirstYearTurnover());
		}

		if (requestDTO.getBodyDetails().get(0).getTurnoverCurrency() != null
				&& !requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim().isEmpty()) {
			obCriInfoInstance.setFirstYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
			obCriInfoInstance
					.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
			obCriInfoInstance.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency().trim());
		} else {
			obCriInfoInstance.setFirstYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
			obCriInfoInstance.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
			obCriInfoInstance.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getTurnoverCurrency());
		}

		if (null != requestDTO.getBodyDetails().get(0).getSecondYear()
				&& !requestDTO.getBodyDetails().get(0).getSecondYear().trim().isEmpty()) {
			obCriInfoInstance.setSecondYear(requestDTO.getBodyDetails().get(0).getSecondYear().trim());
		} else {
			obCriInfoInstance.setSecondYear("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSecondYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim().isEmpty()) {
			obCriInfoInstance.setSecondYearTurnover(requestDTO.getBodyDetails().get(0).getSecondYearTurnover().trim());
		} else {
			obCriInfoInstance.setSecondYearTurnover("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr()
				&& !requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim().isEmpty()) {
			obCriInfoInstance
					.setSecondYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getSecondYearTurnoverCurr().trim());
		} else {
			obCriInfoInstance.setSecondYearTurnoverCurr("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYear()
				&& !requestDTO.getBodyDetails().get(0).getThirdYear().trim().isEmpty()) {
			obCriInfoInstance.setThirdYear(requestDTO.getBodyDetails().get(0).getThirdYear().trim());
		} else {
			obCriInfoInstance.setThirdYear("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnover()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim().isEmpty()) {
			obCriInfoInstance.setThirdYearTurnover(requestDTO.getBodyDetails().get(0).getThirdYearTurnover().trim());
		} else {
			obCriInfoInstance.setThirdYearTurnover("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim().isEmpty()) {
			obCriInfoInstance
					.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim());
		} else {
			obCriInfoInstance.setThirdYearTurnoverCurr("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr()
				&& !requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim().isEmpty()) {
			obCriInfoInstance
					.setThirdYearTurnoverCurr(requestDTO.getBodyDetails().get(0).getThirdYearTurnoverCurr().trim());
		} else {
			obCriInfoInstance.setThirdYearTurnoverCurr("");
		}
		obCriInfoInstance.setIsSPVFunding(requestDTO.getBodyDetails().get(0).getIsSPVFunding());
		if (null != requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure()
				&& !requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim().isEmpty()) {
			obCriInfoInstance.setIndirectCountryRiskExposure(
					requestDTO.getBodyDetails().get(0).getIndirectCountryRiskExposure().trim());
		} else {
			obCriInfoInstance.setIndirectCountryRiskExposure("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getCriCountryName()
				&& !requestDTO.getBodyDetails().get(0).getCriCountryName().trim().isEmpty()) {
			obCriInfoInstance.setCriCountryName(requestDTO.getBodyDetails().get(0).getCriCountryName().trim());
		} else {
			obCriInfoInstance.setCriCountryName("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSalesPercentage()
				&& !requestDTO.getBodyDetails().get(0).getSalesPercentage().trim().isEmpty()) {
			obCriInfoInstance.setSalesPercentage(requestDTO.getBodyDetails().get(0).getSalesPercentage().trim());
		} else {
			obCriInfoInstance.setSalesPercentage("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsCGTMSE()
				&& !requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim().isEmpty()) {
			obCriInfoInstance.setIsCGTMSE(requestDTO.getBodyDetails().get(0).getIsCGTMSE().trim());
		} else {
			obCriInfoInstance.setIsCGTMSE("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsIPRE()
				&& !requestDTO.getBodyDetails().get(0).getIsIPRE().trim().isEmpty()) {
			obCriInfoInstance.setIsIPRE(requestDTO.getBodyDetails().get(0).getIsIPRE().trim());
		} else {
			obCriInfoInstance.setIsIPRE("");
		}
		obCriInfoInstance.setFinanceForAccquisition(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition());
		if (requestDTO.getBodyDetails().get(0).getFinanceForAccquisition() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getFinanceForAccquisition())) {
			obCriInfoInstance.setFacilityApproved(requestDTO.getBodyDetails().get(0).getFacilityApproved());
			obCriInfoInstance.setFacilityAmount(requestDTO.getBodyDetails().get(0).getFacilityAmount());
			obCriInfoInstance.setSecurityName(requestDTO.getBodyDetails().get(0).getSecurityName());
			obCriInfoInstance.setSecurityType(requestDTO.getBodyDetails().get(0).getSecurityType());
			obCriInfoInstance.setSecurityValue(requestDTO.getBodyDetails().get(0).getSecurityValue());
		}
		if (null != requestDTO.getBodyDetails().get(0).getCompanyType()
				&& !requestDTO.getBodyDetails().get(0).getCompanyType().trim().isEmpty()) {
			obCriInfoInstance.setCompanyType(requestDTO.getBodyDetails().get(0).getCompanyType().trim());
		} else {
			obCriInfoInstance.setCompanyType("");
		}
		if (requestDTO.getBodyDetails().get(0).getCompanyType() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCompanyType())) {
			obCriInfoInstance.setNameOfHoldingCompany(requestDTO.getBodyDetails().get(0).getNameOfHoldingCompany());
			obCriInfoInstance.setType(requestDTO.getBodyDetails().get(0).getType());
		}

		if (requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions() != null
				&& !requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions().trim().isEmpty()) {
			obCriInfoInstance
					.setIfBreachWithPrescriptions(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIfBreachWithPrescriptions())) {
				obCriInfoInstance.setComments(requestDTO.getBodyDetails().get(0).getComments().trim());
			}
		}

		obCriInfoInstance.setCategoryOfFarmer(requestDTO.getBodyDetails().get(0).getCategoryOfFarmer());
		if (requestDTO.getBodyDetails().get(0).getCategoryOfFarmer() != null
				&& !requestDTO.getBodyDetails().get(0).getCategoryOfFarmer().trim().isEmpty()) {
			obCriInfoInstance.setLandHolding(requestDTO.getBodyDetails().get(0).getLandHolding());
		}
		if (null != requestDTO.getBodyDetails().get(0).getCountryOfGuarantor()
				&& !requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim().isEmpty()) {
			obCriInfoInstance.setCountryOfGuarantor(requestDTO.getBodyDetails().get(0).getCountryOfGuarantor().trim());
		} else {
			obCriInfoInstance.setCountryOfGuarantor("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance()
				&& !requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim().isEmpty()) {
			obCriInfoInstance.setIsAffordableHousingFinance(
					requestDTO.getBodyDetails().get(0).getIsAffordableHousingFinance().trim());
		} else {
			obCriInfoInstance.setIsAffordableHousingFinance("");
		}
		obCriInfoInstance.setIsInRestrictedList(requestDTO.getBodyDetails().get(0).getIsInRestrictedList());
		if (requestDTO.getBodyDetails().get(0).getIsInRestrictedList() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsInRestrictedList())) {
			obCriInfoInstance.setRestrictedFinancing(requestDTO.getBodyDetails().get(0).getRestrictedFinancing());
		}
		obCriInfoInstance.setRestrictedIndustries(requestDTO.getBodyDetails().get(0).getRestrictedIndustries());
		if (requestDTO.getBodyDetails().get(0).getRestrictedIndustries() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestrictedIndustries())) {
			obCriInfoInstance
					.setRestrictedListIndustries(requestDTO.getBodyDetails().get(0).getRestrictedListIndustries());
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent()
				&& !requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim().isEmpty()) {
			obCriInfoInstance.setIsQualifyingNotesPresent(
					requestDTO.getBodyDetails().get(0).getIsQualifyingNotesPresent().trim());
		} else {
			obCriInfoInstance.setIsQualifyingNotesPresent("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getStateImplications()
				&& !requestDTO.getBodyDetails().get(0).getStateImplications().trim().isEmpty()) {
			obCriInfoInstance.setStateImplications(requestDTO.getBodyDetails().get(0).getStateImplications().trim());
		} else {
			obCriInfoInstance.setStateImplications("");
		}

		if (requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase() != null
				&& !requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase().trim().isEmpty()) {
			obCriInfoInstance
					.setIsBorrowerInRejectDatabase(requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase());
			if ("Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIsBorrowerInRejectDatabase())) {
				obCriInfoInstance
						.setRejectHistoryReason(requestDTO.getBodyDetails().get(0).getRejectHistoryReason().trim());
			}
		}
		if (null != requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange()
				&& !requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim().isEmpty()) {
			obCriInfoInstance.setCapitalForCommodityAndExchange(
					requestDTO.getBodyDetails().get(0).getCapitalForCommodityAndExchange().trim());
		} else {
			obCriInfoInstance.setCapitalForCommodityAndExchange("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare()
				&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim().isEmpty()) {
			obCriInfoInstance.setIsBrokerTypeShare(requestDTO.getBodyDetails().get(0).getIsBrokerTypeShare().trim());
		} else {
			obCriInfoInstance.setIsBrokerTypeShare("");
		}
		if (null != requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity()
				&& !requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim().isEmpty()) {
			obCriInfoInstance
					.setIsBrokerTypeCommodity(requestDTO.getBodyDetails().get(0).getIsBrokerTypeCommodity().trim());
		} else {
			obCriInfoInstance.setIsBrokerTypeCommodity("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getObjectFinance()
				&& !requestDTO.getBodyDetails().get(0).getObjectFinance().trim().isEmpty()) {
			obCriInfoInstance.setObjectFinance(requestDTO.getBodyDetails().get(0).getObjectFinance().trim());
		} else {
			obCriInfoInstance.setObjectFinance("");
		}
		obCriInfoInstance
				.setIsCommodityFinanceCustomer(requestDTO.getBodyDetails().get(0).getIsCommodityFinanceCustomer());
		obCriInfoInstance.setRestructuedBorrowerOrFacility(
				requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility());
		if (requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getRestructuedBorrowerOrFacility())) {
			obCriInfoInstance.setFacility(requestDTO.getBodyDetails().get(0).getFacility());
			obCriInfoInstance.setLimitAmount(requestDTO.getBodyDetails().get(0).getLimitAmount());
		}
		obCriInfoInstance.setConductOfAccountWithOtherBanks(
				requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks());
		if (requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks() != null && "classified"
				.equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getConductOfAccountWithOtherBanks())) {
			obCriInfoInstance.setCrilicStatus(requestDTO.getBodyDetails().get(0).getCrilicStatus());
			if (requestDTO.getBodyDetails().get(0).getCrilicStatus() != null
					&& !requestDTO.getBodyDetails().get(0).getCrilicStatus().trim().isEmpty())
				obCriInfoInstance.setComment(requestDTO.getBodyDetails().get(0).getCrilcComment());
		} else {
			obCriInfoInstance.setConductOfAccountWithOtherBanks("Satisfactory");
		}
		if (null != requestDTO.getBodyDetails().get(0).getSubsidyFlag()
				&& !requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().isEmpty()) {
			obCriInfoInstance.setSubsidyFlag(requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim());
		} else {
			obCriInfoInstance.setSubsidyFlag("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getSubsidyFlag()
				&& !requestDTO.getBodyDetails().get(0).getSubsidyFlag().trim().isEmpty()
				&& "Yes".equals(requestDTO.getBodyDetails().get(0).getSubsidyFlag())) {
			if (null != requestDTO.getBodyDetails().get(0).getHoldingCompnay()
					&& !requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim().isEmpty()) {
				obCriInfoInstance.setHoldingCompnay(requestDTO.getBodyDetails().get(0).getHoldingCompnay().trim());
			}
		} else {
			obCriInfoInstance.setHoldingCompnay("");
		}

		obCriInfoInstance.setCautionList(requestDTO.getBodyDetails().get(0).getCautionList());
		if (requestDTO.getBodyDetails().get(0).getCautionList() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCautionList())) {
			obCriInfoInstance.setDateOfCautionList(requestDTO.getBodyDetails().get(0).getDateOfCautionList());
			obCriInfoInstance.setCompany(requestDTO.getBodyDetails().get(0).getCompany());
			obCriInfoInstance.setDirectors(requestDTO.getBodyDetails().get(0).getDirectors());
			obCriInfoInstance.setGroupCompanies(requestDTO.getBodyDetails().get(0).getGroupCompanies());
		}

		obCriInfoInstance.setDefaultersList(requestDTO.getBodyDetails().get(0).getDefaultersList());
		if (requestDTO.getBodyDetails().get(0).getDefaultersList() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getDefaultersList())) {
			obCriInfoInstance.setRbiDateOfCautionList(requestDTO.getBodyDetails().get(0).getRbiDateOfCautionList());
			obCriInfoInstance.setRbiCompany(requestDTO.getBodyDetails().get(0).getRbiCompany());
			obCriInfoInstance.setRbiDirectors(requestDTO.getBodyDetails().get(0).getRbiDirectors());
			obCriInfoInstance.setRbiGroupCompanies(requestDTO.getBodyDetails().get(0).getRbiGroupCompanies());
		}

		obCriInfoInstance.setCommericialRealEstate(requestDTO.getBodyDetails().get(0).getCommericialRealEstate());
		if (requestDTO.getBodyDetails().get(0).getCommericialRealEstate() != null
				&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getCommericialRealEstate())) {
			obCriInfoInstance
					.setCommericialRealEstateValue(requestDTO.getBodyDetails().get(0).getCommericialRealEstateValue());
			obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
			obCriInfoInstance.setResidentialRealEstate("No");
			obCriInfoInstance.setIndirectRealEstate("No");

		} else {
			if (requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing() != null
					&& "Yes".equalsIgnoreCase(
							requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing())) {
				obCriInfoInstance.setCommericialRealEstateResidentialHousing(
						requestDTO.getBodyDetails().get(0).getCommericialRealEstateResidentialHousing());
				obCriInfoInstance.setResidentialRealEstate("No");
				obCriInfoInstance.setIndirectRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			} else if (requestDTO.getBodyDetails().get(0).getResidentialRealEstate() != null
					&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getResidentialRealEstate())) {
				obCriInfoInstance
						.setResidentialRealEstate(requestDTO.getBodyDetails().get(0).getResidentialRealEstate());
				obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
				obCriInfoInstance.setIndirectRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			} else if (requestDTO.getBodyDetails().get(0).getIndirectRealEstate() != null
					&& "Yes".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getIndirectRealEstate())) {
				obCriInfoInstance.setIndirectRealEstate(requestDTO.getBodyDetails().get(0).getIndirectRealEstate());
				obCriInfoInstance.setCommericialRealEstateResidentialHousing("No");
				obCriInfoInstance.setResidentialRealEstate("No");
				obCriInfoInstance.setCommericialRealEstate("No");
			}
		}

		criInfoArr[0] = obCriInfoInstance;

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			IVendor[] venArray = new OBVendor[50];

			if (requestDTO.getBodyDetails().get(0).getVendorDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getVendorDetReqList().isEmpty()) {
				for (int i = 0; i < requestDTO.getBodyDetails().get(0).getVendorDetReqList().size(); i++) {

					OBVendor obvendorInstance = new OBVendor();
					if (requestDTO.getBodyDetails().get(0).getVendorDetReqList().get(i).getVendorName() != null
							&& !requestDTO.getBodyDetails().get(0).getVendorDetReqList().get(i).getVendorName().trim()
									.isEmpty()) {
						obvendorInstance.setVendorName(
								requestDTO.getBodyDetails().get(0).getVendorDetReqList().get(i).getVendorName().trim());
					} else {
						obvendorInstance.setVendorName(
								requestDTO.getBodyDetails().get(0).getVendorDetReqList().get(i).getVendorName());
					}

					venArray[i] = obvendorInstance;
				}
			}

			cmsLegalEntity.setVendor(venArray);
		} else {
			if (obCMSCustomerIntance.getCMSLegalEntity() != null) {
				cmsLegalEntity.setVendor(obCMSCustomerIntance.getCMSLegalEntity().getVendor());
			}
		}

		if ("Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			ICriFac[] criFacArray = new OBCriFac[50];

			if (requestDTO.getBodyDetails().get(0).getCriFacilityList() != null
					&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().isEmpty()) {
				for (int i = 0; i < requestDTO.getBodyDetails().get(0).getCriFacilityList().size(); i++) {

					OBCriFac obCriFacInstance = new OBCriFac();
					if (requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacilityFor() != null
							&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacilityFor().trim()
									.isEmpty()) {
						obCriFacInstance.setFacilityFor(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacilityFor().trim());
					} else {
						obCriFacInstance.setFacilityFor(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacilityFor());
					}

					if (requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacName() != null
							&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacName().trim()
									.isEmpty()) {
						obCriFacInstance.setFacilityName(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacName().trim());
					} else {
						obCriFacInstance.setFacilityName(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacName());
					}

					if (requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacAmt() != null
							&& !requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacAmt().trim()
									.isEmpty()) {
						obCriFacInstance.setFacilityAmount(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacAmt().trim());
					} else {
						obCriFacInstance.setFacilityAmount(
								requestDTO.getBodyDetails().get(0).getCriFacilityList().get(i).getFacAmt());
					}

					criFacArray[i] = obCriFacInstance;
				}
			}

			cmsLegalEntity.setCriFacList(criFacArray);
		} else {
			if (obCMSCustomerIntance.getCMSLegalEntity() != null) {
				cmsLegalEntity.setCriFacList(obCMSCustomerIntance.getCMSLegalEntity().getCriFacList());
			}
		}

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())
				|| "Rest_update_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			ISystem[] sysArray = new OBSystem[50];

			if (requestDTO.getBodyDetails().get(0).getSystemDetReqList() != null
					&& !requestDTO.getBodyDetails().get(0).getSystemDetReqList().isEmpty()) {
				for (int i = 0; i < requestDTO.getBodyDetails().get(0).getSystemDetReqList().size(); i++) {

					OBSystem obsystemInstance = new OBSystem();
					if (requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystem() != null
							&& !requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystem().trim()
									.isEmpty()) {
						obsystemInstance.setSystem(
								requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystem().trim());
					} else {
						obsystemInstance
								.setSystem(requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystem());
					}

					if (requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystemId() != null
							&& !requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystemId().trim()
									.isEmpty()) {

						if (requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystemId().trim()
								.length() <= 16)
							obsystemInstance.setSystemCustomerId(requestDTO.getBodyDetails().get(0)
									.getSystemDetReqList().get(i).getSystemId().trim());
					} else {
						obsystemInstance.setSystemCustomerId(
								requestDTO.getBodyDetails().get(0).getSystemDetReqList().get(i).getSystemId());
					}

					sysArray[i] = obsystemInstance;
				}
			}

			cmsLegalEntity.setOtherSystem(sysArray);
		} else {
			if (obCMSCustomerIntance.getCMSLegalEntity() != null) {
				cmsLegalEntity.setOtherSystem(obCMSCustomerIntance.getCMSLegalEntity().getOtherSystem());
			}
		}

		IBankingMethod[] bankingMthdArray = new OBBankingMethod[50];

		// Banking Method Info(one-to-many)
		if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList() != null
				&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().isEmpty()) {
			for (int i = 0; i < requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().size(); i++) {

				IBankingMethod bankingMthd = new OBBankingMethod();

				// if(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLeadNodalFlag()!=null
				// &&
				// !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLeadNodalFlag().trim().isEmpty()){
				// bankingMthd.setNodal(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLeadNodalFlag().trim());
				// }else{
				// bankingMthd.setNodal(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLeadNodalFlag());
				// }
				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLead() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLead().trim()
								.isEmpty()) {
					bankingMthd.setLead(
							requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLead().trim());
				} else {
					bankingMthd
							.setLead(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getLead());
				}

				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getNodal() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getNodal().trim()
								.isEmpty()) {
					bankingMthd.setNodal(
							requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getNodal().trim());
				} else {
					bankingMthd.setNodal(
							requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getNodal());
				}

				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getBankType() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getBankType().trim()
								.isEmpty()) {
					bankingMthd.setBankType(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i)
							.getBankType().trim());
				} else {
					bankingMthd.setBankType(
							requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getBankType());
				}

				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getRevisedEmailIds() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getRevisedEmailIds()
								.trim().isEmpty()) {
					bankingMthd.setRevisedEmailIds(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList()
							.get(i).getRevisedEmailIds().trim());
				} else {
					bankingMthd.setRevisedEmailIds("");
				}

				if (requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getBranchId() != null
						&& !requestDTO.getBodyDetails().get(0).getBankingMethodDetailList().get(i).getBranchId().trim()
								.isEmpty()) {
					bankingMthd.setBankId(Long.parseLong(requestDTO.getBodyDetails().get(0).getBankingMethodDetailList()
							.get(i).getBranchId().trim()));
				} else {
					bankingMthd.setBankId(0L);
				}

				bankingMthdArray[i] = bankingMthd;
			}
		}

		IDirector[] directorArr = new OBDirector[10];

		// Director info(one-to-many)
		if (requestDTO.getBodyDetails().get(0).getDirectorDetailList() != null
				&& !requestDTO.getBodyDetails().get(0).getDirectorDetailList().isEmpty()) {
			for (int i = 0; i < requestDTO.getBodyDetails().get(0).getDirectorDetailList().size(); i++) {
				//
				IDirector director = new OBDirector();
				PartyDirectorDetailsRestRequestDTO partyDirectorDetailsRequestDTO = requestDTO.getBodyDetails().get(0)
						.getDirectorDetailList().get(i);

				if (partyDirectorDetailsRequestDTO.getDirectorName() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorName().trim().isEmpty()) {
					director.setDirectorName(partyDirectorDetailsRequestDTO.getDirectorName().trim());
				} else {
					director.setDirectorName(partyDirectorDetailsRequestDTO.getDirectorName());
				}

				if (partyDirectorDetailsRequestDTO.getRelatedDUNSNo() != null
						&& !partyDirectorDetailsRequestDTO.getRelatedDUNSNo().trim().isEmpty()) {
					director.setRelatedDUNSNo(partyDirectorDetailsRequestDTO.getRelatedDUNSNo().trim());
				} else {
					director.setRelatedDUNSNo(partyDirectorDetailsRequestDTO.getRelatedDUNSNo());
				}

				if (partyDirectorDetailsRequestDTO.getDinNo() != null
						&& !partyDirectorDetailsRequestDTO.getDinNo().trim().isEmpty()) {
					director.setDinNo(partyDirectorDetailsRequestDTO.getDinNo().trim());
				} else {
					director.setDinNo(partyDirectorDetailsRequestDTO.getDinNo());
				}
				if (partyDirectorDetailsRequestDTO.getRelatedDUNSNo() != null
						&& !partyDirectorDetailsRequestDTO.getRelatedDUNSNo().trim().isEmpty()) {
					director.setRelatedDUNSNo(partyDirectorDetailsRequestDTO.getRelatedDUNSNo().trim());
				} else {
					director.setRelatedDUNSNo(partyDirectorDetailsRequestDTO.getRelatedDUNSNo());
				}

				if (partyDirectorDetailsRequestDTO.getRelatedType() != null
						&& !partyDirectorDetailsRequestDTO.getRelatedType().trim().isEmpty()) {
					director.setRelatedType(partyDirectorDetailsRequestDTO.getRelatedType().trim());
				} else {
					director.setRelatedType(partyDirectorDetailsRequestDTO.getRelatedType());
				}

				if (partyDirectorDetailsRequestDTO.getRelationship() != null
						&& !partyDirectorDetailsRequestDTO.getRelationship().trim().isEmpty()) {
					director.setRelationship(partyDirectorDetailsRequestDTO.getRelationship().trim());
				} else {
					director.setRelationship(partyDirectorDetailsRequestDTO.getRelationship());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorEmailId() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorEmailId().trim().isEmpty()) {
					director.setDirectorEmail(partyDirectorDetailsRequestDTO.getDirectorEmailId().trim());
				} else {
					director.setDirectorEmail(partyDirectorDetailsRequestDTO.getDirectorEmailId());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorFaxStdCode() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorFaxStdCode().trim().isEmpty()) {
					director.setDirStdCodeTelex(partyDirectorDetailsRequestDTO.getDirectorFaxStdCode().trim());
				} else {
					director.setDirStdCodeTelex(partyDirectorDetailsRequestDTO.getDirectorFaxStdCode());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorFaxNo() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorFaxNo().trim().isEmpty()) {
					director.setDirectorFax(partyDirectorDetailsRequestDTO.getDirectorFaxNo().trim());
				} else {
					director.setDirectorFax(partyDirectorDetailsRequestDTO.getDirectorFaxNo());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode().trim().isEmpty()) {
					director.setDirStdCodeTelNo(partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode().trim());
				} else {
					director.setDirStdCodeTelNo(partyDirectorDetailsRequestDTO.getDirectorTelephoneStdCode());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorTelNo() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorTelNo().trim().isEmpty()) {
					director.setDirectorTelNo(partyDirectorDetailsRequestDTO.getDirectorTelNo().trim());
				} else {
					director.setDirectorTelNo(partyDirectorDetailsRequestDTO.getDirectorTelNo());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorCountry() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorCountry().trim().isEmpty()) {
					director.setDirectorCountry(partyDirectorDetailsRequestDTO.getDirectorCountry().trim());
				} else {
					director.setDirectorCountry(partyDirectorDetailsRequestDTO.getDirectorCountry());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorState() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorState().trim().isEmpty()) {
					director.setDirectorState(partyDirectorDetailsRequestDTO.getDirectorState().trim());
				} else {
					director.setDirectorState(partyDirectorDetailsRequestDTO.getDirectorState());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorCity() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorCity().trim().isEmpty()) {
					director.setDirectorCity(partyDirectorDetailsRequestDTO.getDirectorCity().trim());
				} else {
					director.setDirectorCity(partyDirectorDetailsRequestDTO.getDirectorCity());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorRegion() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorRegion().trim().isEmpty()) {
					director.setDirectorRegion(partyDirectorDetailsRequestDTO.getDirectorRegion().trim());
				} else {
					director.setDirectorRegion(partyDirectorDetailsRequestDTO.getDirectorRegion());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorPincode() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorPincode().trim().isEmpty()) {
					director.setDirectorPostCode(partyDirectorDetailsRequestDTO.getDirectorPincode().trim());
				} else {
					director.setDirectorPostCode(partyDirectorDetailsRequestDTO.getDirectorPincode());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorAddr3() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorAddr3().trim().isEmpty()) {
					director.setDirectorAddress3(partyDirectorDetailsRequestDTO.getDirectorAddr3().trim());
				} else {
					director.setDirectorAddress3(partyDirectorDetailsRequestDTO.getDirectorAddr3());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorAddr2() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorAddr2().trim().isEmpty()) {
					director.setDirectorAddress2(partyDirectorDetailsRequestDTO.getDirectorAddr2().trim());
				} else {
					director.setDirectorAddress2(partyDirectorDetailsRequestDTO.getDirectorAddr2());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorAddr1() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorAddr1().trim().isEmpty()) {
					director.setDirectorAddress1(partyDirectorDetailsRequestDTO.getDirectorAddr1().trim());
				} else {
					director.setDirectorAddress1(partyDirectorDetailsRequestDTO.getDirectorAddr1());
				}

				if (partyDirectorDetailsRequestDTO.getPercentageOfControl() != null
						&& !partyDirectorDetailsRequestDTO.getPercentageOfControl().trim().isEmpty()) {
					director.setPercentageOfControl(partyDirectorDetailsRequestDTO.getPercentageOfControl().trim());
				} else {
					director.setPercentageOfControl(partyDirectorDetailsRequestDTO.getPercentageOfControl());
				}

				if (partyDirectorDetailsRequestDTO.getFullName() != null
						&& !partyDirectorDetailsRequestDTO.getFullName().trim().isEmpty()) {
					director.setFullName(partyDirectorDetailsRequestDTO.getFullName().trim());
				} else {
					director.setFullName(partyDirectorDetailsRequestDTO.getFullName());
				}

				if (partyDirectorDetailsRequestDTO.getNamePrefix() != null
						&& !partyDirectorDetailsRequestDTO.getNamePrefix().trim().isEmpty()) {
					director.setNamePrefix(partyDirectorDetailsRequestDTO.getNamePrefix().trim());
				} else {
					director.setNamePrefix(partyDirectorDetailsRequestDTO.getNamePrefix());
				}

				if (partyDirectorDetailsRequestDTO.getBusinessEntityName() != null
						&& !partyDirectorDetailsRequestDTO.getBusinessEntityName().trim().isEmpty()) {
					director.setBusinessEntityName(partyDirectorDetailsRequestDTO.getBusinessEntityName().trim());
				} else {
					director.setBusinessEntityName(partyDirectorDetailsRequestDTO.getBusinessEntityName());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorPAN() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorPAN().trim().isEmpty()) {
					director.setDirectorPan(partyDirectorDetailsRequestDTO.getDirectorPAN().trim());
				} else {
					director.setDirectorPan(partyDirectorDetailsRequestDTO.getDirectorPAN());
				}

				if (partyDirectorDetailsRequestDTO.getDirectorAADHAR() != null
						&& !partyDirectorDetailsRequestDTO.getDirectorAADHAR().trim().isEmpty()) {
					director.setDirectorAadhar(partyDirectorDetailsRequestDTO.getDirectorAADHAR().trim());
				} else {
					director.setDirectorAadhar(partyDirectorDetailsRequestDTO.getDirectorAADHAR());
				}

				directorArr[i] = director;
			}
		}

		IContact officialAddressContact = new OBContact();
		IContact regOfficialAddressContact = new OBContact();
		IContact officeAddrContact = new OBContact();
		IContact[] address = { officialAddressContact, officeAddrContact, regOfficialAddressContact };

		officialAddressContact.setContactType("CORPORATE");
		if (requestDTO.getBodyDetails().get(0).getAddress1() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress1().trim().isEmpty()) {
			officialAddressContact.setAddressLine1(requestDTO.getBodyDetails().get(0).getAddress1().trim());
		} else {
			officialAddressContact.setAddressLine1(requestDTO.getBodyDetails().get(0).getAddress1());
		}
		if (requestDTO.getBodyDetails().get(0).getAddress2() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress2().trim().isEmpty()) {
			officialAddressContact.setAddressLine2(requestDTO.getBodyDetails().get(0).getAddress2().trim());
		} else {
			officialAddressContact.setAddressLine2(requestDTO.getBodyDetails().get(0).getAddress2());
		}
		if (requestDTO.getBodyDetails().get(0).getAddress3() != null
				&& !requestDTO.getBodyDetails().get(0).getAddress3().trim().isEmpty()) {
			officialAddressContact.setAddressLine3(requestDTO.getBodyDetails().get(0).getAddress3().trim());
		} else {
			officialAddressContact.setAddressLine3(requestDTO.getBodyDetails().get(0).getAddress3());
		}

		if (null != requestDTO.getBodyDetails().get(0).getCountry()
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			officialAddressContact.setCountryCode(requestDTO.getBodyDetails().get(0).getCountry().trim());
		} else {
			officialAddressContact.setCountryCode("");
		}

		if (null != requestDTO.getBodyDetails().get(0).getRegOfficeEmail()
				&& !requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim().isEmpty()) {
			officialAddressContact.setEmailAddress(requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim());
		} else {
			officialAddressContact.setEmailAddress("");
		}

		if (requestDTO.getBodyDetails().get(0).getRegion() != null
				&& !requestDTO.getBodyDetails().get(0).getRegion().trim().isEmpty()) {
			officialAddressContact.setRegion(requestDTO.getBodyDetails().get(0).getRegion().trim());
		} else {
			officialAddressContact.setRegion(requestDTO.getBodyDetails().get(0).getRegion());
		}

		if (requestDTO.getBodyDetails().get(0).getCountry() != null
				&& !requestDTO.getBodyDetails().get(0).getCountry().trim().isEmpty()) {
			officialAddressContact.setCountryCode(requestDTO.getBodyDetails().get(0).getCountry().trim());
		} else {
			officialAddressContact.setCountryCode(requestDTO.getBodyDetails().get(0).getCountry());
		}

		if (requestDTO.getBodyDetails().get(0).getState() != null
				&& !requestDTO.getBodyDetails().get(0).getState().trim().isEmpty()) {
			officialAddressContact.setState(requestDTO.getBodyDetails().get(0).getState().trim());
		} else {
			officialAddressContact.setState(requestDTO.getBodyDetails().get(0).getState());
		}
		if (requestDTO.getBodyDetails().get(0).getCity() != null
				&& !requestDTO.getBodyDetails().get(0).getCity().trim().isEmpty()) {
			officialAddressContact.setCity(requestDTO.getBodyDetails().get(0).getCity().trim());
		} else {
			officialAddressContact.setCity(requestDTO.getBodyDetails().get(0).getCity());
		}

		if (requestDTO.getBodyDetails().get(0).getPincode() != null
				&& !requestDTO.getBodyDetails().get(0).getPincode().trim().isEmpty()) {
			officialAddressContact.setPostalCode(requestDTO.getBodyDetails().get(0).getPincode().trim());
		} else {
			officialAddressContact.setPostalCode(requestDTO.getBodyDetails().get(0).getPincode());
		}
		if (requestDTO.getBodyDetails().get(0).getEmailId() != null
				&& !requestDTO.getBodyDetails().get(0).getEmailId().trim().isEmpty()) {
			officialAddressContact.setEmailAddress(requestDTO.getBodyDetails().get(0).getEmailId().trim());
		} else {
			officialAddressContact.setEmailAddress(requestDTO.getBodyDetails().get(0).getEmailId());
		}
		if (requestDTO.getBodyDetails().get(0).getFaxStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxStdCode().trim().isEmpty()) {
			officialAddressContact.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode().trim());
		} else {
			officialAddressContact.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode());
		}
		if (requestDTO.getBodyDetails().get(0).getFaxNumber() != null
				&& !requestDTO.getBodyDetails().get(0).getFaxNumber().trim().isEmpty()) {
			officialAddressContact.setTelex(requestDTO.getBodyDetails().get(0).getFaxNumber().trim());
		} else {
			officialAddressContact.setTelex(requestDTO.getBodyDetails().get(0).getFaxNumber());
		}
		if (requestDTO.getBodyDetails().get(0).getTelephoneStdCode() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim().isEmpty()) {
			officialAddressContact.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim());
		} else {
			officialAddressContact.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode());
		}
		if (requestDTO.getBodyDetails().get(0).getTelephoneNo() != null
				&& !requestDTO.getBodyDetails().get(0).getTelephoneNo().trim().isEmpty()) {
			officialAddressContact.setTelephoneNumer(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
		} else {
			officialAddressContact.setTelephoneNumer(requestDTO.getBodyDetails().get(0).getTelephoneNo());
		}

		officeAddrContact.setContactType("OFFICE");
		regOfficialAddressContact.setContactType("REGISTERED");

		if (null != requestDTO.getBodyDetails().get(0).getRegOffice()
				&& !requestDTO.getBodyDetails().get(0).getRegOffice().trim().isEmpty()) {
			obCMSCustomerIntance.setRegOffice(requestDTO.getBodyDetails().get(0).getRegOffice().trim());
			if (requestDTO.getBodyDetails().get(0).getRegOffice().trim().equals("Y")) {
				obCMSCustomerIntance.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getBorrowerDUNSNo().trim());
				regOfficialAddressContact.setCountryCode(requestDTO.getBodyDetails().get(0).getCountry().trim());
				regOfficialAddressContact.setRegion(requestDTO.getBodyDetails().get(0).getRegion().trim());
				regOfficialAddressContact.setState(requestDTO.getBodyDetails().get(0).getState().trim());
				regOfficialAddressContact.setCity(requestDTO.getBodyDetails().get(0).getCity().trim());
				regOfficialAddressContact.setAddressLine1(requestDTO.getBodyDetails().get(0).getAddress1().trim());
				regOfficialAddressContact.setAddressLine2(requestDTO.getBodyDetails().get(0).getAddress2().trim());
				regOfficialAddressContact.setAddressLine3(requestDTO.getBodyDetails().get(0).getAddress3().trim());
				regOfficialAddressContact.setPostalCode(requestDTO.getBodyDetails().get(0).getPincode().trim());
				regOfficialAddressContact.setTelephoneNumer(requestDTO.getBodyDetails().get(0).getTelephoneNo().trim());
				regOfficialAddressContact.setTelex(requestDTO.getBodyDetails().get(0).getFaxNumber().trim());
				regOfficialAddressContact
						.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getTelephoneStdCode().trim());
				regOfficialAddressContact.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getFaxStdCode().trim());
				regOfficialAddressContact.setEmailAddress(requestDTO.getBodyDetails().get(0).getEmailId().trim());
			} else {
				obCMSCustomerIntance.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim());
				regOfficialAddressContact
						.setCountryCode(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim());
				regOfficialAddressContact.setRegion(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim());
				regOfficialAddressContact.setState(requestDTO.getBodyDetails().get(0).getRegisteredState().trim());
				regOfficialAddressContact.setCity(requestDTO.getBodyDetails().get(0).getRegisteredCity().trim());
				regOfficialAddressContact
						.setAddressLine1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim());
				regOfficialAddressContact
						.setAddressLine2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim());
				regOfficialAddressContact
						.setAddressLine3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim());
				regOfficialAddressContact
						.setPostalCode(requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
				regOfficialAddressContact
						.setTelephoneNumer(requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim());
				regOfficialAddressContact.setTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim());
				regOfficialAddressContact
						.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim());
				regOfficialAddressContact
						.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim());
				regOfficialAddressContact
						.setEmailAddress(requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim());
			}
		} else {
			obCMSCustomerIntance.setRegOffice("N");
			obCMSCustomerIntance.setRegOfficeDUNSNo(requestDTO.getBodyDetails().get(0).getRegOfficeDUNSNo().trim());
			regOfficialAddressContact.setCountryCode(requestDTO.getBodyDetails().get(0).getRegisteredCountry().trim());
			regOfficialAddressContact.setRegion(requestDTO.getBodyDetails().get(0).getRegisteredRegion().trim());
			regOfficialAddressContact.setState(requestDTO.getBodyDetails().get(0).getRegisteredState().trim());
			regOfficialAddressContact.setCity(requestDTO.getBodyDetails().get(0).getRegisteredCity().trim());
			regOfficialAddressContact.setAddressLine1(requestDTO.getBodyDetails().get(0).getRegisteredAddr1().trim());
			regOfficialAddressContact.setAddressLine2(requestDTO.getBodyDetails().get(0).getRegisteredAddr2().trim());
			regOfficialAddressContact.setAddressLine3(requestDTO.getBodyDetails().get(0).getRegisteredAddr3().trim());
			regOfficialAddressContact.setPostalCode(requestDTO.getBodyDetails().get(0).getRegisteredPincode().trim());
			regOfficialAddressContact.setTelephoneNumer(requestDTO.getBodyDetails().get(0).getRegisteredTelNo().trim());
			regOfficialAddressContact.setTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxNumber().trim());
			regOfficialAddressContact
					.setStdCodeTelNo(requestDTO.getBodyDetails().get(0).getRegisteredTelephoneStdCode().trim());
			regOfficialAddressContact
					.setStdCodeTelex(requestDTO.getBodyDetails().get(0).getRegisteredFaxStdCode().trim());
			regOfficialAddressContact.setEmailAddress(requestDTO.getBodyDetails().get(0).getRegOfficeEmail().trim());
		}

		if ("Rest_create_customer".equalsIgnoreCase(requestDTO.getBodyDetails().get(0).getEvent())) {
			ICMSCustomerUdf cmsUdf = new OBCMSCustomerUdf();
			cmsUdf.setUdf50(DEFAULT_UDF_VALUE);
			ICMSCustomerUdf udfList[] = new ICMSCustomerUdf[1];
			udfList[0] = cmsUdf;
			cmsLegalEntity.setUdfData(udfList);
		} else {
			if (obCMSCustomerIntance.getCMSLegalEntity() != null) {
				cmsLegalEntity.setUdfData(obCMSCustomerIntance.getCMSLegalEntity().getUdfData());
			}
		}

		cmsLegalEntity.setRegisteredAddress(address);
		System.out.println("bankingMthdArray" + bankingMthdArray);
		cmsLegalEntity.setBankList(bankingMthdArray);
		cmsLegalEntity.setCriList(criInfoArr);
		cmsLegalEntity.setDirector(directorArr);

		getCoBorrowerDetailsActualFromDTORest(requestDTO, cmsLegalEntity);

		cmsLegalEntity.setUdfData((ICMSCustomerUdf[]) udfDetailsRestDTOMapper.getUdfActualFromDTO(requestDTO,
				requestDTO.getBodyDetails().get(0).getEvent()));

		obCMSCustomerIntance.setCMSLegalEntity(cmsLegalEntity);

		return obCMSCustomerIntance;
	}

	private static void getCoBorrowerDetailsActualFromDTORest(PartyDetailsRestRequestDTO request,
			ICMSLegalEntity cmsLegalEntity) {

		if (request == null || request.getBodyDetails().get(0).getCoBorrowerDetailsInd() == null)
			return;
		ICustomerDAO custDAO = CustomerDAOFactory.getDAO();

		if (!"Rest_create_customer".equalsIgnoreCase(request.getBodyDetails().get(0).getEvent())) {
			List<CoBorrowerDetailsRestRequestDTO> coBorrowerList1 = custDAO
					.getCoBorrowerListWSRest(request.getBodyDetails().get(0).getClimsPartyId());

			if (coBorrowerList1 != null) {
				List<ICoBorrowerDetails> coBorrowerDetailsNew = new ArrayList<ICoBorrowerDetails>();
				for (CoBorrowerDetailsRestRequestDTO coBorrower1 : coBorrowerList1) {

					ICoBorrowerDetails actual = new OBCoBorrowerDetails();
					System.out.println("coBorrower1.getCoBorrowerLiabId()" + coBorrower1.getCoBorrowerLiabId());

					System.out.println("coBorrower1.getCoBorrowerName()" + coBorrower1.getCoBorrowerName());

					if (StringUtils.isNotBlank(coBorrower1.getCoBorrowerLiabId()))
						actual.setCoBorrowerLiabId(coBorrower1.getCoBorrowerLiabId());
					if (StringUtils.isNotBlank(coBorrower1.getCoBorrowerName()))
						actual.setCoBorrowerName(coBorrower1.getCoBorrowerName());

					actual.setIsInterfaced("Y");

					coBorrowerDetailsNew.add(actual);
				}
				cmsLegalEntity.setCoBorrowerDetails(coBorrowerDetailsNew);

			}

		} else {

			if (ICMSConstant.YES.equals(request.getBodyDetails().get(0).getCoBorrowerDetailsInd())) {
				PartyCoBorrowerDetailsRestRequestDTO coBorrowerRequest = request.getBodyDetails().get(0)
						.getCoBorrowerDetailsList();

				List<CoBorrowerDetailsRestRequestDTO> coBorrowerList = coBorrowerRequest.getCoBorrowerDetails();

				if (coBorrowerList != null) {
					List<ICoBorrowerDetails> coBorrowerDetails = new ArrayList<ICoBorrowerDetails>();
					for (CoBorrowerDetailsRestRequestDTO coBorrower : coBorrowerList) {

						ICoBorrowerDetails actual = new OBCoBorrowerDetails();

						if (StringUtils.isNotBlank(coBorrower.getCoBorrowerLiabId()))
							actual.setCoBorrowerLiabId(coBorrower.getCoBorrowerLiabId());
						if (StringUtils.isNotBlank(coBorrower.getCoBorrowerName()))
							actual.setCoBorrowerName(coBorrower.getCoBorrowerName());

						actual.setIsInterfaced("Y");

						coBorrowerDetails.add(actual);
					}
					cmsLegalEntity.setCoBorrowerDetails(coBorrowerDetails);
				}
			}
		}
	}
}
