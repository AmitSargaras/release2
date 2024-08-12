package com.integrosys.cms.ui.bonddetailsupload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBBondDetailsFile;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileDetails;
import com.integrosys.cms.batch.common.filereader.ProcessDataFileUploadHelper;
import com.integrosys.cms.ui.bonddetailsupload.proxy.IBondDetailsUploadProxyManager;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class FileUploadCommand extends AbstractCommand implements ICommonEventConstant, IBondDetailsUploadConstants {

	private IBondDetailsUploadProxyManager bondDetailsuploadProxy;
	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");

	public IBondDetailsUploadProxyManager getBondDetailsuploadProxy() {
		return bondDetailsuploadProxy;
	}

	public void setBondDetailsuploadProxy(IBondDetailsUploadProxyManager bondDetailsuploadProxy) {
		this.bondDetailsuploadProxy = bondDetailsuploadProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE },
			{ SESSION_TRX_OBJ, OBTrxContext.class.getName(), FORM_SCOPE },
			{ BOND_DETAILS_UPLOAD_FORM, OBBondDetailsUpload.class.getName(), FORM_SCOPE },
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_TRX_VALUE_OUT, IFileUploadTrxValue.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_UPLOADED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ SESSION_TOTAL_FAILED_LIST, List.class.getName(),SERVICE_SCOPE },
			{ "total", String.class.getName(), REQUEST_SCOPE },
			{ "correct", String.class.getName(), REQUEST_SCOPE },
			{ "fail", String.class.getName(), REQUEST_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		HashMap<String, ActionMessage> exceptionMap = new HashMap<String, ActionMessage>();
		
		IBondDetailsUpload formObj = (IBondDetailsUpload) map.get(BOND_DETAILS_UPLOAD_FORM);
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		
		List<OBBondDetailsFile> totalUploadedList = new ArrayList<OBBondDetailsFile>();
		List<OBBondDetailsFile> totalFailedList = new ArrayList<OBBondDetailsFile>();
		
		if (formObj.getFileUpload().getFileName().isEmpty()) {
			exceptionMap.put("fileuploadError",	new ActionMessage("label.global.file.empty"));
			
		} else if (!formObj.getFileUpload().getFileName().endsWith(".xls") && !formObj.getFileUpload().getFileName().endsWith(".XLS")
				&& !formObj.getFileUpload().getFileName().endsWith(".XLSX")	&& !formObj.getFileUpload().getFileName().endsWith(".xlsx")) {
			exceptionMap.put("fileuploadError",	new ActionMessage("label.global.file.excelformat"));
		} else {
			OBTrxContext ctx = (OBTrxContext) map.get(SESSION_TRX_OBJ);
			ctx.setCustomer(null);
			ctx.setLimitProfile(null);
			try {
					IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
					
					ProcessDataFileDetails dataFile = new ProcessDataFileDetails();
					List<List<Cell>> resultList = dataFile.processFile(formObj.getFileUpload(), BOND_DETAILS_UPLOAD);
					
					DefaultLogger.info(this, "Total no of data processed from uploaded excel sheet: " + resultList.size());
					if(resultList.size() > 0) {
						boolean isTypeXlsx = formObj.getFileUpload().getFileName().endsWith(".xlsx") || formObj.getFileUpload().getFileName().endsWith(".XLSX");
						
						List<List<String>> dataList = new ArrayList<List<String>>();
						
						List<String> partyIds = new ArrayList<String>();
						List<String> srcSecIds = new ArrayList<String>();
						
						for(int h = 1; h < resultList.size(); h++) {
							List<Cell> result = resultList.get(h);
							List<String> data = new ArrayList<String>();
							boolean isPresent = false;
							for(int i = 0; i < result.size(); i++) {
								Cell cell = result.get(i);
								String cellValue = ProcessDataFileUploadHelper.getCellDataByType(cell, isTypeXlsx);
								if(cellValue != null) {
									if(i == 0) {
										partyIds.add(cellValue);
									}
									if(i == 1) {
										if(StringUtils.isNumeric(cellValue))
											srcSecIds.add(cellValue);
									}
									isPresent = true;
								}
								data.add(cellValue);
							}
							if(isPresent) {
								int idx = data.size();
								while(idx < 6) {
									data.add(null);
									idx++;
								}
								dataList.add(data);
							}	
						}
						
						DefaultLogger.info(this, "Populating Data from excel in a format for data validation");
						
						Map<String, Boolean> existsPartyIds = fileUploadJdbc.existsPartyIds(partyIds);
						Map<String, ICollateral> existssrcSecIds = fileUploadJdbc.existsSecurityIds(srcSecIds);
						DefaultLogger.info(this, "Total no of valid parties exists which are extracted from excel: " + existsPartyIds.size());
						DefaultLogger.info(this, "Total no of valid security id exists which are extracted from excel: " + existssrcSecIds.size());
						
						DefaultLogger.info(this, "Starting data validation for individual record extracted from excel");
						StringBuffer errorMapForLogging = new StringBuffer("");
						int idx = 1;
						for(List<String> datas : dataList) {
							OBBondDetailsFile obj = new OBBondDetailsFile();
							obj.setPartyId(datas.get(0) == null ? "" : datas.get(0));
							obj.setSourceSecurityId(datas.get(1) == null ? null : datas.get(1));
							obj.setSecuritySubType(datas.get(2) == null ? "" : datas.get(2));
							obj.setBondCode(datas.get(3) == null ? "" : datas.get(3));
							obj.setNoOfUnits(datas.get(4) == null ? "" : datas.get(4));
							obj.setInterest(datas.get(5) == null ? "" : datas.get(5));
							
							StringBuffer failReason= new StringBuffer("");
							boolean checkRequired = true;
							
							if(AbstractCommonMapper.isEmptyOrNull(obj.getPartyId())) {
								failReason.append("Failed because Party Id is empty.");
								checkRequired = false;
							} else {
								if(!existsPartyIds.containsKey(obj.getPartyId())) {
									failReason.append("Failed because Party Id is not present in the system.");
									checkRequired = false;
								} else {
									Boolean isActive  = existsPartyIds.get(obj.getPartyId());
									if(!isActive) {
										failReason.append("Failed because Party Id is not active.");
										checkRequired = false;
									}
								}
							}
							
							if(AbstractCommonMapper.isEmptyOrNull(obj.getSourceSecurityId())) {
								failReason.append("Failed because Source Security Id is empty.");
								checkRequired = false;
							} else {
								if(!existssrcSecIds.containsKey(obj.getSourceSecurityId())) {
									failReason.append("Failed because Source Security Id is not present in the system.");
									checkRequired = false;
								} else {
									ICollateral col  = existssrcSecIds.get(obj.getSourceSecurityId());
									if(!ICMSConstant.ACTIVE.equals(col.getStatus())) {
										failReason.append("Failed because Source Security is not active.");
										checkRequired = false;
									}
									if(!ICMSConstant.COLTYPE_MAR_BONDS_LOCAL.equals(col.getSourceSecuritySubType())) {
										failReason.append("Failed because Source Security is not a sub-type of Bond.");
										checkRequired = false;
									}
								}
							}
							
							if(checkRequired) {
								if(!fileUploadJdbc.doesSecurityBelongsToParty(obj.getPartyId(), Long.valueOf(obj.getSourceSecurityId()))){
									failReason.append("Failed because Security doesn't belongs to the party.");
								}
							}
							
							if(AbstractCommonMapper.isEmptyOrNull(obj.getSecuritySubType())) {
								failReason.append("Failed because Security Sub type is empty.");
							} else {
								if(!BOND.equals(obj.getSecuritySubType())) {
									failReason.append("Failed because Security Sub type was not Bond.");
								}
							}
							
							if(AbstractCommonMapper.isEmptyOrNull(obj.getBondCode())) {
								failReason.append("Failed because Bond code is empty.");
							} else {
								IBondFeedEntry feed = bondFeedProxy.getBondFeedEntry(obj.getBondCode());
								if(feed == null) {
									failReason.append("Failed because Bond Code is invalid.");
								}
							}
							
							if(AbstractCommonMapper.isEmptyOrNull(obj.getNoOfUnits())) {
								failReason.append("Failed because Number Of Units is empty.");
							} else {
								String errorCode = Validator.checkNumber(obj.getNoOfUnits(), false, 0, 9999999999d,0,locale);
								if(!Validator.ERROR_NONE.equals(errorCode)) {
									if(Validator.ERROR_FORMAT.equals(errorCode))
										failReason.append("Failed because Number Of Units should only have numbers.");
									else if(Validator.ERROR_LESS_THAN.equals(errorCode) || Validator.ERROR_GREATER_THAN.equals(errorCode))
										failReason.append("Failed because Number Of Units should be between 0 and 9999999999");
									else if(Validator.ERROR_DECIMAL_EXCEEDED.equals(errorCode)) {
										try {
										String nou = obj.getNoOfUnits();
										int decimal = Integer.parseInt(nou.substring(nou.indexOf(".") > 0 ? nou.indexOf(".") + 1 : 0));
										if(decimal > 0) {
											failReason.append("Failed because no decimal placing is allowed for Number Of Units.");
										}
										}catch(NumberFormatException nfe) {
											failReason.append("Failed because Number Of Units should only have numbers.");
										}
									}
								}
							}
							
							if(!AbstractCommonMapper.isEmptyOrNull(obj.getInterest())) {
								String errorCode = UIValidator.checkNumber(obj.getInterest(), false, 0.0, Double.parseDouble("99.9999"), 5, locale);
								if(!Validator.ERROR_NONE.equals(errorCode)) {
									if(Validator.ERROR_FORMAT.equals(errorCode)) {
										failReason.append("Failed because Interest should only have numbers.");
									}
									else if(Validator.ERROR_LESS_THAN.equals(errorCode) || Validator.ERROR_GREATER_THAN.equals(errorCode)) {
										failReason.append("Failed because Interest should be between 0 and 99");
									}else if(ICMSUIConstant.VALIDATION_NUMBER_EXCEED_ERR.equals(errorCode)) {
										failReason.append("Failed because Interest exceeded 4 decimals.");
									}
								}
							
							}
							
							String reason = failReason.toString();
							obj.setUploadedBy(ctx.getUser().getLoginID());
							obj.setUploadDate(new Date());
							
							if(AbstractCommonMapper.isEmptyOrNull(reason)) {
								obj.setUploadStatus(ICMSConstant.YES);
								obj.setStatus(ICMSConstant.PASS);
								totalUploadedList.add(obj);
								errorMapForLogging.append(idx + "={ Pass }");
							} else {
								obj.setReason(reason);
								obj.setUploadStatus(ICMSConstant.NO);
								obj.setStatus(ICMSConstant.FAIL);
								totalFailedList.add(obj);
								errorMapForLogging.append(idx + "={ Fail:[" + reason + "] }");
							}
							idx++;
						}
						DefaultLogger.info(this, "Validation result for extracted data: " + errorMapForLogging.toString());
						
						IFileUpload fileObj = new OBFileUpload();
						fileObj.setFileType(ICMSConstant.BOND_DETAILS_UPLOAD);
						fileObj.setUploadBy(ctx.getUser().getLoginID());
						fileObj.setUploadTime(DateUtil.getDate());
						fileObj.setFileName(formObj.getFileUpload().getFileName());
						fileObj.setTotalRecords(String.valueOf(resultList.size()));
						
						DefaultLogger.info(this, "Creating stage record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD);
						IFileUploadTrxValue trxValueOut = getBondDetailsuploadProxy().makerCreateFile(ctx, fileObj);
						DefaultLogger.info(this, "Created stage record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD);
						
						if(trxValueOut != null) {
							for(OBBondDetailsFile pass : totalUploadedList) {
								pass.setFileId(Long.valueOf(trxValueOut.getStagingReferenceID()));
							}
							for(OBBondDetailsFile fail : totalFailedList) {
								fail.setFileId(Long.valueOf(trxValueOut.getStagingReferenceID()));
							}
						}
					
						DefaultLogger.info(this, "Storing passed and failed list into database in batch");
						int batchSize = 200;
						for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
							List<OBBondDetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size()
									? totalUploadedList.size(): j + batchSize);
							fileUploadJdbc.createEntireBondDetailsFile(batchList, false);
						}
					
						for (int j = 0; j < totalFailedList.size(); j += batchSize) {
							List<OBBondDetailsFile> batchList = totalFailedList.subList(j,j + batchSize > totalFailedList.size()
									? totalFailedList.size(): j + batchSize);
							fileUploadJdbc.createEntireBondDetailsFile(batchList, false);
						}
					}
					
				} catch (FileUploadException e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Exception occurred while creating stage record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD);
					throw new CommandProcessingException("Exception occurred while creating stage record for file upload of type " + ICMSConstant.BOND_DETAILS_UPLOAD, e);
				} catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Exception occurred while processing the file");
					throw new CommandProcessingException("Exception occurred while processing the file", e);
				}
		}
		
		DefaultLogger.info(this, "Total record successfully uploaded : " + totalUploadedList.size() + ", total record failed: " + totalFailedList.size());
		
		resultMap.put(SESSION_TRX_VALUE_OUT, null);
		resultMap.put(SESSION_TOTAL_UPLOADED_LIST, totalUploadedList);
		resultMap.put(SESSION_TOTAL_FAILED_LIST, totalFailedList);
		resultMap.put("total", String.valueOf(totalUploadedList.size() + totalFailedList.size()));
		resultMap.put("correct", String.valueOf(totalUploadedList.size()));
		resultMap.put("fail", String.valueOf(totalFailedList.size()));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
}
