package com.integrosys.cms.app.ws.facade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefUdf;
import com.integrosys.cms.app.ecbf.limit.IECBFLimitInterfaceLog;
import com.integrosys.cms.app.ecbf.limit.IECBFLimitInterfaceLogDAO;
import com.integrosys.cms.app.ecbf.limit.OBECBFLimitInterfaceLog;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchJdbc;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.dto.ECBFLimitRequestDTO;
import com.integrosys.cms.app.ws.dto.ECBFLimitResponseDTO;
import com.integrosys.cms.app.ws.dto.UdfDetailLimitRequestDTO;
import com.integrosys.cms.app.ws.jax.common.CMSException;
import com.integrosys.cms.app.ws.jax.common.JAXBTransformer;
import com.integrosys.cms.app.ws.jax.common.Transformer;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

public class ECBFLimitServiceFacade {
	
	private static final String ADD_ACTION = "A";
	private static final String UPDATE_ACTION = "U";
	
	enum EstateType{
		C{
			@Override
			public String toString() {
				return "Commercial Real estate";
			}
		},R{
			@Override
			public String toString() {
				return "Residential real Estate";
			}
		},I{
			@Override
			public String toString() {
				return "Indirect real estate";
			}
		}
	}
	
	public ECBFLimitResponseDTO syncECBFLimitDetails(ECBFLimitRequestDTO requestDTO) {
		long startTime = System.currentTimeMillis();
		printOut("Request received from ECBF for line/limit detail sync up in CLIMS ", "INFO", null);
		
		IECBFLimitInterfaceLog log = null;
		ECBFLimitResponseDTO responseDTO = null;
		IECBFLimitInterfaceLogDAO logDAO = (IECBFLimitInterfaceLogDAO) BeanHouse.get("ecbfLimitInterfaceLogDAO"); 
		try{	
			log = new OBECBFLimitInterfaceLog(requestDTO);
			log.setRequestDateTime(DateUtil.now().getTime());
			
			responseDTO = new ECBFLimitResponseDTO();

			printOut("Checking for mandatory field validation", "INFO", null);
			
			String remarksStatus = validateMandatoryFieldFromRequest(requestDTO);
		
			String maxLengthValidation = validateFieldLengthFromRequest(requestDTO, log);
			
			log.setRequestMessage(dtoToXML(requestDTO));
			
			normalizeData(requestDTO, log);
			
			if(requestDTO.getPanNumber() != null && requestDTO.getPanNumber().length() > 0)
				responseDTO.setPanNumber(requestDTO.getPanNumber());
			
			if (remarksStatus.length() > 0) {
				remarksStatus = remarksStatus.substring(0, remarksStatus.length() - 2);
				
//				printOut("Mandatory field [" + remarksStatus + "] needed." + responseDTO.getPanNumber() != null ? " for Pan number: "
//				+ responseDTO.getPanNumber() : "", "INFO", null);
				throw new CMSException(remarksStatus + (remarksStatus.split(", ").length > 1 ? " fields are" : " field is") + " mandatory.");
			}else if(maxLengthValidation.length() > 0) { 
				maxLengthValidation = maxLengthValidation.substring(0, maxLengthValidation.length() - 2);

//				printOut(maxLengthValidation + (maxLengthValidation.split(", ").length > 1 ? " fields" : " field") + " exceeded max expected length for Pan number: " + responseDTO.getPanNumber(), "INFO", null);
				throw new CMSException(maxLengthValidation + (maxLengthValidation.split(", ").length > 1 ? " fields" : " field") + " exceeded max expected length.");
			}else {
				
				validateFieldDataFromRequest(requestDTO, log);
				
				validateUDFDataFromRequest(requestDTO, log);
				
				ILimitJdbc limitJdbc = (ILimitJdbc) BeanHouse.get("limitJdbc");
				Map<String, Object> paramMap = new HashMap<String, Object>();
				String action = "";
				String condition = "";
				 if(isAdd(requestDTO.getAction()))
				{
					paramMap.put("FACMST.line_number", requestDTO.getLineCode());
					action = ADD_ACTION;
					condition = "Line code [" + requestDTO.getLineCode() + "] and ";
				}
				else if(isUpdate(requestDTO.getAction())){
					paramMap.put("linkage.cms_lsp_lmts_xref_map_id", requestDTO.getLineId());
					action = UPDATE_ACTION;
					condition = "Line id [" + requestDTO.getLineId() + "] and ";
				}
				condition += "Pan number [" + requestDTO.getPanNumber() + "]";
				paramMap.put("PARTY.pan", requestDTO.getPanNumber());
				 
				Map<String, String> details = limitJdbc.getDetailsForECBFLimit(paramMap, action);
				
				if(details.size() == 0) {
					throw new CMSException("Facility not found at CLIMS based on " + condition +  " received from ECBF Limit/Line details");
				}
				
				String limitIdStr = details.get("LMT_ID");
				log.setLmtId(limitIdStr);
				if(limitIdStr == null || limitIdStr.length() == 0) {
					throw new CMSException("Facility not found at CLIMS based on " + condition +  " received from ECBF Limit/Line details");
				}
				
				String partyId = details.get("PARTY_ID");
				log.setPartyId(partyId);
				if(partyId == null || partyId.length() == 0) {
					throw new CMSException("Party not found at CLIMS based on pan number received from ECBF Limit/Line details");
				}
				
				String lmtStatus = details.get("LMT_STATUS");
				
				if(!ICMSConstant.ACTIVE.equals(lmtStatus)) {
					throw new CMSException("Facility is pending at CLIMS");
				}
				
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				
				ILimitTrxValue lmtTrxObj = proxy.searchLimitByLmtId(limitIdStr);
				ILimit limit = lmtTrxObj.getLimit();
				ILimitSysXRef[] limitSysXRef = limit.getLimitSysXRefs();
				ILimitSysXRef sysRef = null;
				ICustomerSysXRef xref = null;
				
				int idx = 0;
				
				boolean systemIdExistsForParty = limitJdbc.isSystemExistsForParty(partyId, requestDTO.getBorrowerCustomerID());
				
				if(!systemIdExistsForParty) {
					throw new CMSException("Borrower customer id [" + requestDTO.getBorrowerCustomerID() 
					+ "] is not maintained for " + requestDTO.getPanNumber() + "[" + partyId  +"] at CLIMS");
				}
				
				Date applicationDate = DateUtil.getDate();
				if(isAdd(requestDTO.getAction())) {
					limit.setIsReleased("Y");
					
					if(limitSysXRef == null || limitSysXRef.length == 0) {
						limitSysXRef = new ILimitSysXRef[1]; 
					}else {
						for(ILimitSysXRef limitSysXRefItm : limitSysXRef) {
							ICustomerSysXRef xrefItm = limitSysXRefItm.getCustomerSysXRef();
							if(xrefItm != null && requestDTO.getBorrowerCustomerID().equals(xrefItm.getFacilitySystemID())
									&& xrefItm.getLineNo().equals(requestDTO.getLineCode())) {
								String msg = "Line with line code " + requestDTO.getLineCode() 
								+ " and borrower customer id " + requestDTO.getBorrowerCustomerID();
								if(ICMSConstant.FCUBS_STATUS_PENDING.equals(xrefItm.getStatus())){
									throw new CMSException(msg + " is pending at CLIMS");
								}
								else if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(xrefItm.getStatus())
										&& ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xrefItm.getAction())){
									throw new CMSException(msg + " is in rejected state at CLIMS");
								}
								
							}
						}

						idx = limitSysXRef.length;
						ILimitSysXRef[] newLimitSysXRef = new ILimitSysXRef[limitSysXRef.length + 1]; 
						System.arraycopy(limitSysXRef, 0, newLimitSysXRef, 0, limitSysXRef.length);
						limitSysXRef = newLimitSysXRef;
					}

					sysRef = (ILimitSysXRef) new OBLimitSysXRef();
					xref = (ICustomerSysXRef) new OBCustomerSysXRef();
					
					String date = new SimpleDateFormat("yyMMdd").format(applicationDate);
					ILimitDAO dao = LimitDAOFactory.getDAO();
					String tempSourceRefNo = "";
					tempSourceRefNo = "" + dao.generateSourceSeqNo();
					int len = tempSourceRefNo.length();
					String concatZero = "";
					if (null != tempSourceRefNo && len != 5) {
						for (int m = 5; m > len; m--) {
							concatZero = "0" + concatZero;
						}
					}
					tempSourceRefNo = concatZero + tempSourceRefNo;
					
					String sorceRefNo = ICMSConstant.FCUBS_CAD + date + tempSourceRefNo;
					
					String defaultSerialno = PropertyManager.getValue("ecbf.line.web.service.default.serial.no","1000");

					xref.setSourceRefNo(sorceRefNo);
					xref.setCreatedOn(applicationDate);
					xref.setCreatedBy("SYSTEM");
					xref.setSendToCore("N");
					xref.setSerialNo(defaultSerialno);
					xref.setHiddenSerialNo(defaultSerialno);
					xref.setFacilitySystem(limit.getFacilitySystem());
					xref.setCurrency(limit.getCurrencyCode());
					xref.setExternalSystemCodeNum(ICMSConstant.CATEGORY_SOURCE_SYSTEM);
					xref.setUploadStatus("N");
					xref.setCloseFlag("N");
					xref.setCurrencyRestriction(dao.getCurrencyRestriction(limit.getFacilityCode()));
					xref.setAction(ICMSConstant.FCUBSLIMIT_ACTION_NEW);
					xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_ADD);
					xref.setPrioritySectorFlag(ICMSConstant.FCUBS_ADD);
					xref.setUncondiCanclFlag(ICMSConstant.FCUBS_ADD);
					xref.setSendToFile("Y");
					xref.setIntradayLimitFlag(dao.getIntradayLimit(limit.getFacilityCode()));
					xref.setDayLightLimitApproved("No");
					xref.setDateOfReset(dao.getCamExtentionDate(String.valueOf(lmtTrxObj.getLimitProfileID())));
					xref.setUtilizedAmount("0");
					xref.setInterestRateType("fixed");
					xref.setIntRateFix("0");
					
					String defaultSegment = PropertyManager.getValue("ecbf.stp.default.segment");
					
					String segment = limitJdbc.findSegment1ByPartyId(partyId);
					
					if(segment !=null) {
						xref.setSegment1Flag(ICMSConstant.FCUBS_ADD);
						xref.setSegment(segment);
					}
					else if(StringUtils.isNotBlank(defaultSegment)) {
						segment = limitJdbc.getDefaultSegment1CodeValue(defaultSegment);
						
						if(segment !=null) {
							xref.setSegment1Flag(ICMSConstant.FCUBS_ADD);
							xref.setSegment(segment);
						}
						else {
							throw new CMSException("No default Segment1 attached for " + partyId);
						}
					}
					else {
						throw new CMSException("No Segment1 attached for " + partyId);
					}
				}else if(isUpdate(requestDTO.getAction())){
					boolean found = false;
					long xRefID = Long.valueOf(requestDTO.getLineId());
					for(ILimitSysXRef limitSysXRefItm : limitSysXRef) {
						if(limitSysXRefItm.getXRefID() == xRefID) {
							xref = limitSysXRefItm.getCustomerSysXRef();
							sysRef = limitSysXRefItm;
							found = true;
							break;
						}
						idx++;
					}
					if(!found) {
						throw new CMSException("Limit/Line details doesn't exists with line id: " + xRefID);
					}
					
					if(ICMSConstant.FCUBS_STATUS_PENDING.equals(xref.getStatus())){
						throw new CMSException("Line [" + xref.getLineNo()  + "] is pending at CLIMS");
					}else if(ICMSConstant.FCUBS_STATUS_REJECTED.equals(xref.getStatus())){
						throw new CMSException("Line [" + xref.getLineNo()  + "] is in rejected state at CLIMS");
					}
					
					xref.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
				}
				
//				printOut("Starting sync up with field received from ECBF Limit/Line details with CLIMS for Pan number: " 
//				+ responseDTO.getPanNumber(), "INFO", null);
				
				validateAmountFieldFromRequest(requestDTO, limit.getCurrencyCode());
				
				syncLimitDetail(xref, requestDTO, log);
				
				syncUdf(xref, requestDTO, log);
				
				xref.setUpdatedOn(DateUtil.getDate());
				xref.setUpdatedBy("SYSTEM");
				
				sysRef.setCustomerSysXRef(xref);
				limitSysXRef[idx] = sysRef;
				
				limit.setReleasableAmount(requestDTO.getDocumentationAmount().replaceAll(",", ""));
				limit.setLimitSysXRefs(limitSysXRef);
				
				BigDecimal sanctionedAmt = new BigDecimal(limit.getRequiredSecurityCoverage());
				BigDecimal releasableAmount = new BigDecimal(limit.getReleasableAmount());
				BigDecimal totalReleasedAmt = new BigDecimal(0);
				
				for(ILimitSysXRef limitSysXRefItm : limitSysXRef) {
					if(limitSysXRefItm.getCustomerSysXRef().getReleasedAmount() != null) {
						BigDecimal lineReleaseAmt = new BigDecimal(limitSysXRefItm.getCustomerSysXRef().getReleasedAmount().replaceAll(",", ""));
						totalReleasedAmt = totalReleasedAmt.add(lineReleaseAmt);
					}
				}
				
				if(totalReleasedAmt.compareTo(releasableAmount) > 0) {
					throw new CMSException("Released Amount cannot be greater than Documentation Amount");
				}

				limit.setTotalReleasedAmount(UIUtil.formatBigDecimalToStr(totalReleasedAmt));
				
				if(releasableAmount.compareTo(sanctionedAmt) > 0) {
					throw new CMSException("Documentation Amount can't be greater than Sanctioned Amount");
				}

				lmtTrxObj.setStatus("ACTIVE");
				lmtTrxObj.setFromState("ACTIVE");
				lmtTrxObj.setStagingLimit(limit);
								
				printOut("Starting Facility maker checker approve for synced Limit/Line details", "INFO", null);
				
				long lineId = makerCheckerApprove(lmtTrxObj, requestDTO);
				
//				printOut("Facility maker checker approve completed successfully for Pan number: " + responseDTO.getPanNumber(), "INFO", null);
				
				String actionVal = "action";
				if(isAdd(requestDTO.getAction()))
					actionVal = "inserted";
				else if(isUpdate(requestDTO.getAction()))
					actionVal = "updated";
				
//				printOut("Line/Limit details " + actionVal + " successfully for Pan Number " + requestDTO.getPanNumber() 
//				+ " with line id [" + lineId + "]", "INFO", null);
				responseDTO.setStatus("S");
				responseDTO.setLineId(String.valueOf(lineId));
				
				log.setStatus("S");
				log.setLineId(String.valueOf(lineId));
			}
		}catch (LimitException ex) {
			ex.printStackTrace();
			logError(log, responseDTO, WebServiceStatusCode.SERVER_ERROR, ex);
		} catch (RemoteException ex) {
			ex.printStackTrace();
			logError(log, responseDTO, WebServiceStatusCode.SERVER_ERROR, ex);
		}catch (ParseException ex) {
			ex.printStackTrace();
			logError(log, responseDTO, WebServiceStatusCode.SERVER_ERROR, ex);
		}catch(CMSException ex) {
			logError(log, responseDTO, WebServiceStatusCode.VALIDATION_ERROR, ex);
		}catch (Exception ex) {
			ex.printStackTrace();
			logError(log, responseDTO, WebServiceStatusCode.SERVER_ERROR, ex);
		}
		
		log.setResponseMessage(dtoToXML(responseDTO));
		log.setResponseDateTime(DateUtil.now().getTime());
		
		try {
			logDAO.createOrUpdateInterfaceLog(log);
		} catch (Exception ex) {
			ex.printStackTrace();
			logError(log, responseDTO, WebServiceStatusCode.SERVER_ERROR, ex);
		}
		
		long stopTime = System.currentTimeMillis();
		printOut("Total time required for the process:" + (stopTime - startTime) + " ms", "INFO", null);
		
		return responseDTO;
	}
	
	private String validateMandatoryFieldFromRequest(ECBFLimitRequestDTO requestDTO) {
		StringBuffer remarksStatus = new StringBuffer("");

		if (StringUtils.isEmpty(requestDTO.getFinalLimitReleasable())) {
			remarksStatus.append("Final Limit Releasable, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getDocumentationAmount())) {
			remarksStatus.append("Documentation Amount, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getCapitalMarketExposure())) {
			remarksStatus.append("Capital Market Exposure, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getRealEstateExposure())) {
			remarksStatus.append("Real Estate exposure, ");
		}else if("Y".equals(requestDTO.getRealEstateExposure())){
			if (StringUtils.isEmpty(requestDTO.getEstateType())) {
				remarksStatus.append("Estate Type, ");
			}else {
				if(EstateType.C.name().equals(requestDTO.getEstateType())) {
					if (StringUtils.isEmpty(requestDTO.getCommEstateType())) {
						remarksStatus.append("Commercial Estate Type, ");
					}
				}
			}
		}
	
		if (StringUtils.isEmpty(requestDTO.getRuleID())) {
			remarksStatus.append("Rule ID, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getPriorityFlag())) {
			remarksStatus.append("Priority flag , ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getPriority())) {
			remarksStatus.append("Priority, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getUncondiCanclCommitment())) {
			remarksStatus.append("UNCONDI CANCL COMMITMNET, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getBorrowerCustomerID())) {
			remarksStatus.append("Borrower Customer ID, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getLiabilityBranch())) {
			remarksStatus.append("Liability Branch, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getAvailable())) {
			remarksStatus.append("Available, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getLineCode())) {
			remarksStatus.append("Line Code, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getBorrowerAdditionDate())) {
			remarksStatus.append("Borrower Addition Date, ");
		}
	
		if (StringUtils.isEmpty(requestDTO.getRemarks())) {
			remarksStatus.append("Remarks, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getPanNumber())) {
			remarksStatus.append("Pan Number, ");
		}
		
		if (StringUtils.isEmpty(requestDTO.getAction())) {
			remarksStatus.append("Action, ");
		}else {
			if(isUpdate(requestDTO.getAction())) {
				if (StringUtils.isEmpty(requestDTO.getLineId()) || "0".equals(requestDTO.getLineId())) {
					remarksStatus.append("LineId, ");
				}
			}
		}
		
		return remarksStatus.toString();
	}
	
	private String validateFieldLengthFromRequest(ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) {
		StringBuffer msg = new StringBuffer("");
		
		if(isUpdate(requestDTO.getAction()) && exceededMaxValue(requestDTO.getLineId(), 19)) {
			msg.append("Line id, ");
			log.setLineId(requestDTO.getLineId().substring(0, 19));
		}
		
		if(exceededMaxValue(requestDTO.getRevolvingLine(), 1)) {
			msg.append("Revolving Line, ");
			log.setRevolvingLine(requestDTO.getRevolvingLine().substring(0, 1));
		}

		if(exceededMaxValue(requestDTO.getCapitalMarketExposure(), 1)) {
			msg.append("Capital Market Exposure, ");
			log.setCapitalMarketExposure(requestDTO.getCapitalMarketExposure().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getRealEstateExposure(), 1)) {
			msg.append("Real Estate Exposure, ");
			log.setRealEstateExposure(requestDTO.getRealEstateExposure().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getEstateType(), 1)) {
			msg.append("Estate Type, ");
			log.setEstateType(requestDTO.getEstateType().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getCommEstateType(), 100)) {
			msg.append("Commercial Estate Type, ");
			log.setCommEstateType(requestDTO.getCommEstateType().substring(0, 100));
		}
		
		if(exceededMaxValue(requestDTO.getRuleID(), 100)) {
			msg.append("Rule ID, ");
			log.setRuleID(requestDTO.getRuleID().substring(0, 100));
		}
		
		if(exceededMaxValue(requestDTO.getPriorityFlag(), 1)) {
			msg.append("Priority Flag, ");
			log.setPriorityFlag(requestDTO.getPriorityFlag().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getPriority(), 100)) {
			msg.append("Priority, ");
			log.setPriority(requestDTO.getPriority().substring(0, 100));
		}
		
		if(exceededMaxValue(requestDTO.getUncondiCanclCommitment(), 100)) {
			msg.append("UNCONDI CANCL COMMITMNET, ");
			log.setUncondiCanclCommitment(requestDTO.getUncondiCanclCommitment().substring(0, 100));
		}
		
		if(exceededMaxValue(requestDTO.getBorrowerCustomerID(), 50)) {
			msg.append("Borrower Customer ID, ");
			log.setBorrowerCustomerID(requestDTO.getBorrowerCustomerID().substring(0, 50));
		}
		
		if(exceededMaxValue(requestDTO.getLiabilityBranch(), 100)) {
			msg.append("Liability Branch, ");
			log.setLiabilityBranch(requestDTO.getLiabilityBranch().substring(0, 100));
		}
		
		if(exceededMaxValue(requestDTO.getAvailable(), 1)) {
			msg.append("Available, ");
			log.setAvailable(requestDTO.getAvailable().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getLineCode(), 300)) {
			msg.append("Line Code, ");
			log.setLineCode(requestDTO.getLineCode().substring(0, 300));
		}
		
		if(exceededMaxValue(requestDTO.getBorrowerAdditionDate(), 10)) {
			msg.append("Borrower Addition Date, ");
			log.setBorrowerAdditionDate(requestDTO.getBorrowerAdditionDate().substring(0, 10));
		}
		
		if(exceededMaxValue(requestDTO.getFreeze(), 1)) {
			msg.append("Freeze, ");
			log.setFreeze(requestDTO.getFreeze().substring(0, 1));
		}
		
		if(exceededMaxValue(requestDTO.getRemarks(), 1000)) {
			msg.append("Remarks, ");
			log.setRemarks(requestDTO.getRemarks().substring(0, 1000));
		}
		
		if(exceededMaxValue(requestDTO.getPanNumber(), 40)) {
			msg.append("Pan Number, ");
			log.setPanNumber(requestDTO.getPanNumber().substring(0, 40));
		}
		
		if(exceededMaxValue(requestDTO.getAction(), 1)) {
			msg.append("Action, ");
			log.setAction(requestDTO.getAction().substring(0, 1));
		}
		
		String amt = null;
		if((amt = checkAmtLength(requestDTO.getDocumentationAmount(), 24)) != null) {
			msg.append("Documentation Amount, ");
			log.setDocumentationAmount(amt);
		}
		
		if((amt = checkAmtLength(requestDTO.getFinalLimitReleasable(), 24)) != null) {
			msg.append("Final Limit Releasable, ");
			log.setFinalLimitReleasable(amt);
		}
		
		return msg.toString();
	}
	
	private String checkAmtLength(String value, int length) {
		if(StringUtils.isNotEmpty(value)) {
			String amt = value;
			amt = amt.replaceAll(",", "");
			value = amt;
			amt = amt.replaceAll("\\.", "");
			if(exceededMaxValue(amt, length)) {
				return value.substring(0, length);
			}
		}
		
		return null;
	}
	
	private boolean exceededMaxValue(String value, int maxLen) {
		return value != null && value.length() > maxLen;
	}
	
	private void validateAmountFieldFromRequest(ECBFLimitRequestDTO requestDTO, String currencyCode) {
		Locale locale = Locale.getDefault();
	
		String errorCode = null;
		currencyCode = currencyCode == null ? IGlobalConstant.DEFAULT_CURRENCY : currencyCode;
	
		String docAmt = requestDTO.getDocumentationAmount().replaceAll(",", "");
		if (!(errorCode = Validator.checkAmount(docAmt, true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, currencyCode, locale))
				.equals(Validator.ERROR_NONE)) {
			throw new CMSException(validationMsgBasedOnCodeAmt("Documentation Amount", errorCode, "0" , IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		}else if(!(ASSTValidator.isValidDecimalNumberWithComma((requestDTO.getDocumentationAmount().replaceAll(",", ""))))) {
			throw new CMSException("Documentation Amount should not contain special character(s)");
		}
		
		String finalLimitReleasable = requestDTO.getFinalLimitReleasable().replaceAll(",", "");
		if (!(errorCode = Validator.checkAmount(finalLimitReleasable, true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, currencyCode, locale))
				.equals(Validator.ERROR_NONE)) {
			throw new CMSException(validationMsgBasedOnCodeAmt("Final Limit Releasable", errorCode,  "0" , IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		}else if(!(ASSTValidator.isValidDecimalNumberWithComma((requestDTO.getFinalLimitReleasable().replaceAll(",", ""))))) {
			throw new CMSException("Final Limit Releasable should not contain special character(s)");
		}
		
		BigDecimal docAmtBg = new BigDecimal(docAmt);
		BigDecimal finalLimitReleasableBg = new BigDecimal(finalLimitReleasable);
		
		if(finalLimitReleasableBg.compareTo(docAmtBg) > 0) {
			throw new CMSException("Final Limit Releasable can't be greater than Documentation Amount");
		}
	}
	
	private String validationMsgBasedOnCodeAmt(String field, String code, String min, String max) {
		if(Validator.ERROR_FORMAT.equals(code)) {
			return field + " should contain only numeric characters";
		}
		
		if(Validator.ERROR_DECIMAL_EXCEEDED.equals(code)) {
			return field + " exceeded decimal point";
		}
		
		if(Validator.ERROR_LESS_THAN.equals(code) || Validator.ERROR_GREATER_THAN.equals(code)) {
			return field + " valid range is between " + min + " and " + max;
		}
		
		return field + " is not in expected format";
	}
	
	private void validateFieldDataFromRequest(ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) {
		List<String> yesNoList = Arrays.asList("Y", "N");
		
		if(StringUtils.isNotEmpty(requestDTO.getAction()) 
				&& !(isAdd(requestDTO.getAction()) || isUpdate(requestDTO.getAction()))) {
				throw new CMSException("Action must be A (Add) or U (Update)");
		}
		
		if(isUpdate(requestDTO.getAction()) && StringUtils.isNotEmpty(requestDTO.getLineId())
				&& !StringUtils.isNumeric(log.getLineId())) {
			throw new CMSException("Line id must contain number only");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getRevolvingLine()) && !yesNoList.contains(requestDTO.getRevolvingLine())) {
			throw new CMSException("Revolving Line value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getCapitalMarketExposure()) && !yesNoList.contains(requestDTO.getCapitalMarketExposure())) {
			throw new CMSException("Capital Market Exposure value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getRealEstateExposure()) && !yesNoList.contains(requestDTO.getRealEstateExposure())) {
			throw new CMSException("Real Estate Exposure value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getEstateType())) {
			if(!(EstateType.C.name().equals(requestDTO.getEstateType()) ||
					EstateType.R.name().equals(requestDTO.getEstateType())
					||EstateType.I.name().equals(requestDTO.getEstateType()))) {
				throw new CMSException("Estate Type value must be C or R or I");
			}
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getPriorityFlag()) && !yesNoList.contains(requestDTO.getPriorityFlag())) {
			throw new CMSException("Priority Flag value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getAvailable()) && !yesNoList.contains(requestDTO.getAvailable())) {
			throw new CMSException("Available value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getFreeze()) && !yesNoList.contains(requestDTO.getFreeze())) {
			throw new CMSException("Freeze value must be Y or N");
		}
		
		if(StringUtils.isNotEmpty(requestDTO.getBorrowerAdditionDate())){ 
			Date date = null;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(requestDTO.getBorrowerAdditionDate());
				String actualDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
				if(!requestDTO.getBorrowerAdditionDate().equals(actualDate)) {
					throw new Exception("Borrower Addition Date must be a valid date and in dd/MM/yyyy format");
				}
			}catch(Exception ex) {
				throw new CMSException("Borrower Addition Date must be a valid date and in dd/MM/yyyy format");
			}
			if(date.after(DateUtil.getDate())) {
				throw new CMSException("Borrower Addition Date can not be Future Date.");
			}
		}
		
		if(ASSTValidator.isValidInternalRemarks(requestDTO.getRemarks())) {
			throw new CMSException("Remarks field contains special character(s)");
		}
	}
	
	private void validateUDFDataFromRequest(ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) {
		if(requestDTO.isUdfListNotEmpty()) {
			StringBuffer labels = new StringBuffer("");
			for(UdfDetailLimitRequestDTO udf : requestDTO.getUdfList().getUdf()) {
				String error = udf.getMandatoryErrors();
				if(error != null) {
					throw new CMSException(error);
				}
				error = udf.getLengthErrors();
				if(error != null) {
					throw new CMSException(error);
				}
				error = udf.getDataErrors();
				if(error != null) {
					throw new CMSException(error);
				}
				
				IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
				IUdf udfItem = udfDao.getUdfByModuleIdAndSequence("3", udf.getName());
				if(udfItem == null) {
					throw new CMSException("Udf name [" + udf.getName() + "] is not maintained at CLIMS");
				}
				error = udf.getExtraDataErrors(udfItem);
				if(error != null) {
					throw new CMSException(error);
				}
				
				labels.append(udfItem.getFieldName() + ",");
				
				udf.setLabel(udfItem.getFieldName());
			}
			
			if(labels.length() > 0) {
				log.setUdfNames(labels.substring(0, labels.length() - 1));
				if(log.getUdfNames().length() > 4000) {
					log.setUdfNames(log.getUdfNames().substring(0, 4000));
				}
			}
		}
	}
	
	private void normalizeData(ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) {
		
		if(StringUtils.isNotEmpty(requestDTO.getAction())) {
			if(isAdd(requestDTO.getAction()))
				log.setAction("Add");
			else if(isUpdate(requestDTO.getAction()))
				log.setAction("Update");
		}
		
		if (StringUtils.isEmpty(requestDTO.getRevolvingLine())) {
			requestDTO.setRevolvingLine("Y");
			log.setRevolvingLine("Y");
		}
	
		if("Y".equals(requestDTO.getRealEstateExposure())){
			if (StringUtils.isNotEmpty(requestDTO.getEstateType())) {
				String estateType = null;
				if(EstateType.C.name().equals(requestDTO.getEstateType())) {
					estateType = EstateType.C.toString();
				}
				else if(EstateType.R.name().equals(requestDTO.getEstateType())) {
					estateType = EstateType.R.toString();
				}else if(EstateType.I.name().equals(requestDTO.getEstateType())) {
					estateType = EstateType.I.toString();
				}
				log.setEstateTypeValue(estateType);
			}
		}
	
		if (StringUtils.isEmpty(requestDTO.getFreeze())) {
			requestDTO.setFreeze("Y");
			log.setFreeze("Y");
		}
		
		if(requestDTO.isUdfListNotEmpty()){
			StringBuffer udfSequence = new StringBuffer("");
			StringBuffer udfValue = new StringBuffer("");
			for(UdfDetailLimitRequestDTO udf : requestDTO.getUdfList().getUdf()) {
				if(StringUtils.isNotEmpty(udf.getName())){
					udfSequence.append(udf.getName() + ",");
				}
				if(StringUtils.isNotEmpty(udf.getValue())){
					udfValue.append(udf.getValue() + ",");
				}
			}
			if(udfSequence.length() > 0) {
				log.setUdfSequences(udfSequence.substring(0, udfSequence.length() - 1));
				if(log.getUdfSequences().length() > 4000) {
					log.setUdfSequences(log.getUdfSequences().substring(0, 4000));
				}
			}
			if(udfValue.length() > 0) {
				log.setUdfValues(udfValue.substring(0, udfValue.length() - 1));
				if(log.getUdfValues().length() > 4000) {
					log.setUdfValues(log.getUdfValues().substring(0, 4000));
				}
			}
		}
		
	}
	
	private void syncLimitDetail(ICustomerSysXRef xref, ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) throws Exception {
		ILimitJdbc limitJdbc = (ILimitJdbc) BeanHouse.get("limitJdbc");

		ICommonCodeEntry ruleIdCC = limitJdbc.getEntryByCodeAndCategory(requestDTO.getRuleID(), CategoryCodeConstant.NPA_RULE_ID);
		ICommonCodeEntry uncondiCanclCommitmentCC = limitJdbc.getEntryByCodeAndCategory(requestDTO.getUncondiCanclCommitment(),
				CategoryCodeConstant.UNCONDI_CANCL_COMMITMENT);
		ICommonCodeEntry priorityCC = limitJdbc.getEntryByCodeAndCategory(requestDTO.getPriority(), 
					"Y".equals(requestDTO.getPriorityFlag()) ? 
							CategoryCodeConstant.CommonCode_PRIORITY_SECTOR_Y : CategoryCodeConstant.CommonCode_PRIORITY_SECTOR);
		
		ICommonCodeEntry commEstateType = null;
		String estateType = null;
		
		if("Y".equals(requestDTO.getRealEstateExposure())) {
			if(EstateType.C.name().equals(requestDTO.getEstateType())) {
				estateType = EstateType.C.toString();
				commEstateType = limitJdbc.getEntryByCodeAndCategory(requestDTO.getCommEstateType(),
						CategoryCodeConstant.CommonCode_COMMERCIAL_REAL_ESTATE);
				if(commEstateType == null) {
					throw new CMSException("Code sent from ECBF for Commercial Estate Type doesn't exists at CLIMS");
				}
				if(isAdd(requestDTO.getAction()))
					xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
			}else if(EstateType.R.name().equals(requestDTO.getEstateType())) {
				estateType = EstateType.R.toString();
			}else if(EstateType.I.name().equals(requestDTO.getEstateType())) {
				estateType = EstateType.I.toString();
			}else {
				throw new CMSException("Code sent from ECBF for Estate Type is incorrect");
			}
		}
		
		String fields = "";
		if(ruleIdCC == null)
			fields += "Rule Id [" + requestDTO.getRuleID() +"], ";
		if(uncondiCanclCommitmentCC == null)
			fields += "UncondiCanclCommitment [" + requestDTO.getUncondiCanclCommitment() +"], ";
		if(priorityCC == null)
			fields += "Priority [" + requestDTO.getPriority() +"], ";
		if(fields.length() > 0)
			throw new CMSException("Code sent from ECBF for " + fields.substring(0, fields.length() - 2) +" doesn't exists at CLIMS");
		
		IFCCBranchJdbc branchJdbc = (IFCCBranchJdbc) BeanHouse.get("fccBranchJdbc");
		IFCCBranch branch = null;
		try {
			branch = branchJdbc.findBranchByBranchCode(requestDTO.getLiabilityBranch());
		} catch (Exception e) {
			e.printStackTrace();
			throw new CMSException("Liability branch code [" + requestDTO.getLiabilityBranch() + "] sent from ECBF doesn't exists at CLIMS", e);
		}
		
		log.setLiabilityBranchValue(branch.getBranchCode());
		xref.setLiabBranch(String.valueOf(branch.getId()));
		
		xref.setStatus(ICMSConstant.FCUBS_STATUS_PENDING);
		xref.setIsCapitalMarketExposer(returnYesOrNo(requestDTO.getCapitalMarketExposure()));
		xref.setIsRealEstateExposer(returnYesOrNo(requestDTO.getRealEstateExposure()));
	
		log.setEstateTypeValue(estateType);
		xref.setEstateType(estateType);
		
		if(commEstateType != null) {
			log.setCommEstateTypeValue(commEstateType.getEntryName());
			xref.setCommRealEstateType(requestDTO.getCommEstateType());
		}
		
		xref.setRuleId(ruleIdCC.getEntryCode());
		log.setRuleIDValue(ruleIdCC.getEntryName());
		
		xref.setIsPrioritySector(returnYesOrNo(requestDTO.getPriorityFlag()));
		
		xref.setPrioritySector(priorityCC.getEntryCode());
		log.setPriorityValue(priorityCC.getEntryName());
		
		xref.setUncondiCancl(uncondiCanclCommitmentCC.getEntryCode());
		log.setUncondiCanclCommitmentValue(uncondiCanclCommitmentCC.getEntryName());
		
		xref.setLiabilityId(requestDTO.getBorrowerCustomerID());
		xref.setAvailable(returnYesOrNo(requestDTO.getAvailable()));
		xref.setLineNo(requestDTO.getLineCode());
		xref.setFacilitySystemID(requestDTO.getBorrowerCustomerID());
		xref.setLimitStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(requestDTO.getBorrowerAdditionDate()));
		xref.setFreeze(returnYesOrNo(requestDTO.getFreeze()));
		xref.setInternalRemarks(requestDTO.getRemarks());
		xref.setReleasedAmount(requestDTO.getFinalLimitReleasable().replaceAll(",", ""));
		xref.setRevolvingLine(returnYesOrNo(requestDTO.getRevolvingLine()));
	}
	
	private void syncUdf(ICustomerSysXRef xref, ECBFLimitRequestDTO requestDTO, IECBFLimitInterfaceLog log) {
		if(requestDTO.isUdfListNotEmpty()) {
			ILimitXRefUdf udfData[] = null;
			ILimitXRefUdf udfDataItem = null;
			Set<String> sequenceSet = new HashSet<String>();
			if(isUpdate(requestDTO.getAction()) 
					&& StringUtils.isNotEmpty(xref.getUdfAllowed())
					&& xref.getXRefUdfData() != null && xref.getXRefUdfData().length > 0) {
				udfData = xref.getXRefUdfData();
				udfDataItem = xref.getXRefUdfData()[0];
			}else {
				udfData = new ILimitXRefUdf[1];
				udfDataItem = new OBLimitXRefUdf();
				udfData[0] = udfDataItem;
			}
			
			for(UdfDetailLimitRequestDTO udfDto : requestDTO.getUdfList().getUdf()) {
				boolean isAdded = sequenceSet.add(udfDto.getName());

				if(!isAdded) {
					throw new CMSException("Duplicate UDF name " + udfDto.getName() + " present in request");
				}
				
				String property = "udf" + udfDto.getName() + "_Label";
				setter(udfDataItem, property, udfDto.getLabel());
				
				property = "udf" + udfDto.getName() + "_Flag";
				setter(udfDataItem, property,isAdd(requestDTO.getAction()) ? "A" : "U");
				
				property = "udf" + udfDto.getName() + "_Value";
				setter(udfDataItem, property, udfDto.getValue());
	
			}
			
			if(isUpdate(requestDTO.getAction()) 
					&& StringUtils.isNotEmpty(xref.getUdfAllowed())
					&& xref.getXRefUdfData() != null && xref.getXRefUdfData().length > 0) {
				String[] udfAlloweds = xref.getUdfAllowed().split(",");
				for(String udfAllowed : udfAlloweds) {
					sequenceSet.add(udfAllowed.trim());
				}
				
			}
			
			xref.setXRefUdfData(udfData);
			StringBuffer udfAllowed = new StringBuffer("");
			for(String sequence : sequenceSet) {
				udfAllowed.append( sequence +  ",");
			}
			if(udfAllowed.length() > 0)
				xref.setUdfAllowed(udfAllowed.substring(0, udfAllowed.length() - 1));
		}
	}
	
	private void setter(Object bean, String property, Object value) {
		try {
			PropertyUtils.setProperty(bean, property, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	private long makerCheckerApprove(ILimitTrxValue lmtTrxObj, ECBFLimitRequestDTO requestDTO) throws LimitException, RemoteException {
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		
		ICMSTrxResult res = proxy.createSubmitFacility(null, lmtTrxObj, false);
		
		ILimitTrxValue trxValue = proxy.searchLimitByTrxId(res.getTrxValue().getTransactionID());
		ILimitSysXRef[] sysXRef = trxValue.getLimit().getLimitSysXRefs();
		long id = 0;
		for(ILimitSysXRef sysXRefItm : sysXRef) {
			boolean found = isUpdate(requestDTO.getAction()) ? 
					sysXRefItm.getXRefID() == Long.parseLong(requestDTO.getLineId()) 
					: ((sysXRefItm.getCustomerSysXRef().getLineNo().equals(requestDTO.getLineCode())
							&& requestDTO.getBorrowerCustomerID().equals(sysXRefItm.getCustomerSysXRef().getFacilitySystemID()))
							|| sysXRefItm.getCustomerSysXRef().getSerialNo().equals("1"));
			if(found) {
				id = sysXRefItm.getXRefID();
				break;
			}
		}
		
		return id;
	}
	
	private void logError(IECBFLimitInterfaceLog log, ECBFLimitResponseDTO responseDTO, WebServiceStatusCode errorCode, Exception ex) {
		log.setErrorCode(errorCode.name());
		responseDTO.setErrorCode(errorCode.name());

		String errorMsg = ex.getMessage() != null ? ex.getMessage() :
			(errorCode.equals(WebServiceStatusCode.SERVER_ERROR) ? WebServiceStatusCode.SERVER_ERROR.message 
					: WebServiceStatusCode.VALIDATION_ERROR.message);
		log.setErrorMessage(errorMsg);
		responseDTO.setErrorMessage(errorMsg);

		responseDTO.setStatus("F");
		log.setStatus("F");

		printOut("Sync up failed! Exception caught while processing with error message: " + errorMsg, "ERROR", ex);
	}
	
	private String dtoToXML(Object dto) {
		if(dto == null)
			return "";
		String msg = null;
		try {
			MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			Transformer transformer = new JAXBTransformer();
			SOAPMessage message = factory.createMessage();
			SOAPBody body = message.getSOAPBody();
			transformer.dtoToXML(dto, body);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			
			return baos.toString();
		} catch (SOAPException e) {
			e.printStackTrace();
			printOut("Exception caught while coverting to xml with error: " + e.getMessage(),
					"ERROR", e);
			msg = "";
		} catch (IOException e) {
			e.printStackTrace();
			printOut("Exception caught while coverting to xml with error: " + e.getMessage(),
					"ERROR", e);
			msg = "";
		}
		
		return msg;
	}
	
	private void printOut(String value, String type, Exception ex) {
		if(type.equals("INFO"))
			DefaultLogger.info(this, value);
		if(type.equals("DEBUG"))
			DefaultLogger.debug(this, value);
		if(type.equals("ERROR"))
			DefaultLogger.error(value, ex);
	}
	
	private static boolean isAdd(String action) {
		return ADD_ACTION.equals(action);
	}
	
	private static boolean isUpdate(String action) {
		return UPDATE_ACTION.equals(action);
	}
	
	private static String returnYesOrNo(String val) {
		if("Y".equals(val)) {
			return "Yes";
		}
		
		if("N".equals(val)) {
			return "No";
		}
		return null;
	}
}