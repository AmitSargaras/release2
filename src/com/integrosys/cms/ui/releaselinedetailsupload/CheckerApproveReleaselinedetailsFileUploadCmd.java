package com.integrosys.cms.ui.releaselinedetailsupload;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBReleaselinedetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMasterJdbc;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.releaselinedetailsupload.proxy.IReleaselinedetailsUploadProxyManager;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveReleaselinedetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public CheckerApproveReleaselinedetailsFileUploadCmd(){		
	}

	private IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy;
	

	public IReleaselinedetailsUploadProxyManager getReleaselinedetailsuploadProxy() {
		return releaselinedetailsuploadProxy;
	}

	public void setReleaselinedetailsuploadProxy(IReleaselinedetailsUploadProxyManager releaselinedetailsuploadProxy) {
		this.releaselinedetailsuploadProxy = releaselinedetailsuploadProxy;
	} 

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"releaselinedetailsList", "java.util.List", SERVICE_SCOPE},
				{ IGlobalConstant.USER, ICommonUser.class.getName(), GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.class.getName(), GLOBAL_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
				
		}
		);
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{"trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue", SERVICE_SCOPE},
				{ "totalUploadedList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
		}
		);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap chkMap = new HashMap();
		
		try {
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ArrayList totalUploadedList=new ArrayList();
			
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			long countPass =0;
			long countFail =0;
			String releaselinedetailsPending="Party doesn't not exist";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList releaselinedetailsList=(ArrayList)map.get("releaselinedetailsList");
//			ArrayList releaselinedetailsErrorList=(ArrayList)map.get("errorList");
			OBFileUpload actualClone = new OBFileUpload();
			OBFileUpload stagingClone = new OBFileUpload();
			OBFileUpload actual = (OBFileUpload)trxValueIn.getFileUpload();
			OBFileUpload staging = (OBFileUpload)trxValueIn.getStagingfileUpload();
			IFileUploadTrxValue trxValueOut=null;
			String remarks = (String) map.get("remarks");
			
			String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			boolean isLoaAuthorizer = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_CHECKER).equals(teamTypeMembershipID) || 
					String.valueOf(ICMSConstant.CPU_MAKER_CHECKER).equals(teamTypeMembershipID);
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String appDate=df.format(applicationDate);
			
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			
	
		
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
//				String refreshStatus = fileUploadJdbc.spUploadTransaction("RELEASELINEDETAILS");
				
				IFileUpload stgFile = trxValueIn.getStagingfileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				trxValueOut = getReleaselinedetailsuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getReferenceID());
				if(null!=releaselinedetailsList && null!=trxValueOut){
				for(int i=0; i<releaselinedetailsList.size(); i++){
					OBReleaselinedetailsFile oBReleaselinedetailsFile = (OBReleaselinedetailsFile)releaselinedetailsList.get(i);
						oBReleaselinedetailsFile.setFileId(fileId);
					totalUploadedList.add(oBReleaselinedetailsFile); 
				}
				}
			
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBReleaselinedetailsFile oBReleaselinedetailsFile=(OBReleaselinedetailsFile)totalUploadedList.get(i);
						if(oBReleaselinedetailsFile.getStatus().equals("PASS")){
							countPass++;
						}else if(oBReleaselinedetailsFile.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBReleaselinedetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					jdbc.createEntireReleaselinedetailsActualFile(batchList);
				}
				
				FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
				ILimitProxy limitProxy = LimitProxyFactory.getProxy();
				ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
				ILimitTrxValue lmtTrxObj = null;
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				String failReason="";
				String passReason="";
				ILimit curLmt = null;
				for (int j = 0; j < totalUploadedList.size(); j++) {
					OBReleaselinedetailsFile obReleaselinedetailsFile  = (OBReleaselinedetailsFile) totalUploadedList.get(j);
					if(obReleaselinedetailsFile.getStatus().equalsIgnoreCase("PASS") && obReleaselinedetailsFile.getUploadStatus().equalsIgnoreCase("Y"))
					{	
						
						
					////////////////////////////////////////////////////////////	

						String facilityID =  String.valueOf(fileUpload.getFacilittId(obReleaselinedetailsFile));
						obReleaselinedetailsFile.setFacilityID(facilityID);
						long lmtProfId = fileUpload.getLimitProfileId(facilityID);
						String lmtProfApprId = String.valueOf(fileUpload.getLimitProfileApprId(obReleaselinedetailsFile.getFacilityID()));//20200408000004568
						
						ILimitProfile profile = limitProxy.getLimitProfile(lmtProfId);  
						
						DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==119==>> profile.getCustomerID()"+ profile.getCustomerID());
						
						
						
						
						lmtTrxObj = proxy.searchLimitByLmtId(lmtProfApprId);
						
						
						curLmt = lmtTrxObj.getLimit();
						
						
						 BigDecimal releasableAmount = new BigDecimal("0");
						 BigDecimal totalReleasedAmount =new BigDecimal("0");
						 boolean releaseAmtChecked = false;
						 DefaultLogger.debug(curLmt," FacilityType ==1===> "+curLmt.getFacilityType() );
						 DefaultLogger.debug(curLmt,"Required Security Coverage [Note :- check is not null/empty then flag = true] ==2===> "+curLmt.getRequiredSecurityCoverage());

							 DefaultLogger.debug(curLmt,"[flag = true] and [Required Security Coverage()/CurrencyCode]==4===> "+ curLmt.getRequiredSecurityCoverage()+" / "+curLmt.getCurrencyCode()+" " );
							 DefaultLogger.debug(curLmt,"[flag = true] and [Required Security Coverage()/CurrencyCode]==4.1===> "+ obReleaselinedetailsFile.getReleaseAmount());
							if(!"".equals(obReleaselinedetailsFile.getReleaseAmount())&& obReleaselinedetailsFile.getReleaseAmount()!=null&&
									!obReleaselinedetailsFile.getReleaseAmount().isEmpty()) {
							releaseAmtChecked = true;
						 	if(curLmt.getReleasableAmount() !=null && !curLmt.getReleasableAmount().trim().equals("")){
						 		releasableAmount = new BigDecimal(curLmt.getReleasableAmount().replaceAll(",", ""));
						 		DefaultLogger.debug(curLmt,"releasableAmount[obReleaselinedetailsFile.getReleasableAmt()] ==7===> "+ releasableAmount);
							}
						 	DefaultLogger.debug(curLmt,"releasableAmount[obReleaselinedetailsFile.getReleasableAmt()] ==7.1===> "+ curLmt.getTotalReleasedAmount());
							if(curLmt.getTotalReleasedAmount()!=null && !curLmt.getTotalReleasedAmount().trim().equals("")){
								String samount  = String.valueOf(fileUpload.getSerialAmt(obReleaselinedetailsFile));
								//totalReleasedAmount = new BigDecimal(curLmt.getTotalReleasedAmount().replaceAll(",", "")).subtract(new BigDecimal(obReleaselinedetailsFile.getReleaseAmount().replaceAll(",", "")));
								//totalReleasedAmount = totalReleasedAmount.add(new BigDecimal(obReleaselinedetailsFile.getReleaseAmount().replaceAll(",", "")));
								totalReleasedAmount = releasableAmount.subtract(releasableAmount.subtract(new BigDecimal(curLmt.getTotalReleasedAmount().replaceAll(",", "")))).subtract(new BigDecimal(samount.replaceAll(",", ""))).add(new BigDecimal(obReleaselinedetailsFile.getReleaseAmount().replaceAll(",", "")));
								DefaultLogger.debug(curLmt,"totalReleasedAmount[curLmt.getTotalReleasedAmount()] ==8===> "+ totalReleasedAmount);
							}
							DefaultLogger.debug(curLmt,"releasableAmount[obReleaselinedetailsFile.getReleasableAmt()] ==7.2===> "+ curLmt.getTotalReleasedAmount());	

							
							if(isLoaAuthorizer) {
								//ICMSCustomer cust = profile!=null?CustomerProxyFactory.getProxy().getCustomer(profile.getCustomerID()):null;
								String customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtTrxObj.getLimitProfileID());
								BigDecimal limitReleaseAmtMaster = null;
								
								ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
								ILimitsOfAuthorityMaster loaMaster = loaMasterJdbc.getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(user.getEmployeeGrade(), customerSegment);
								
//								boolean isLoaMasterExists = true;
//								if(loaMaster == null)
//									isLoaMasterExists = loaMasterJdbc.isLimitsOfAuthorityMasterExistsByEmployeeGrade(user.getEmployeeGrade());
								
								String facilityCode = lmtTrxObj.getStagingLimit() != null? lmtTrxObj.getStagingLimit().getFacilityCode():null;
								IFacilityNewMasterJdbc facMasterJdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
								String isFacMasterOverrideLoa = facMasterJdbc.getLineExcludeFromLoaMasterByFacilityCode(facilityCode);
								
								if(!(ICMSConstant.YES.equals(user.getOverrideExceptionForLoa()) || ICMSConstant.YES.equals(isFacMasterOverrideLoa)) ) {
									
									if(loaMaster != null) {
										limitReleaseAmtMaster = loaMaster.getLimitReleaseAmt();
									
									if(curLmt.getLimitSysXRefs()!=null) {
										for(ILimitSysXRef sysXref : curLmt.getLimitSysXRefs()) {
											if(sysXref.getCustomerSysXRef() != null) {
												ICustomerSysXRef custSysXRef = sysXref.getCustomerSysXRef();
												String liabBranch = custSysXRef.getLiabBranch();
												String lineNo = custSysXRef.getLineNo();
												String serialNo = custSysXRef.getSerialNo();
												String facSysId = custSysXRef.getFacilitySystemID();
												
												if(lineNo != null && lineNo.equals(obReleaselinedetailsFile.getLineNo()) &&
													serialNo != null && serialNo.equals(obReleaselinedetailsFile.getSerialNo()) &&
													facSysId != null && facSysId.equals(obReleaselinedetailsFile.getSystemID())) {
												
													if(StringUtils.isNotBlank(obReleaselinedetailsFile.getReleaseAmount()) &&
															StringUtils.isNotBlank(custSysXRef.getReleasedAmount())) {
														BigDecimal releaseAmtOb = UIUtil.mapStringToBigDecimal(obReleaselinedetailsFile.getReleaseAmount());
														BigDecimal releaseAmtCustSysXRef = UIUtil.mapStringToBigDecimal(custSysXRef.getReleasedAmount());
														
														BigDecimal deltaReleaseAmt = releaseAmtCustSysXRef.subtract(releaseAmtOb).abs();
														
//														if((loaMaster == null && isLoaMasterExists) || (limitReleaseAmtMaster!=null && deltaReleaseAmt!=null && limitReleaseAmtMaster.compareTo(deltaReleaseAmt) == -1)) {
														if(limitReleaseAmtMaster!=null && deltaReleaseAmt!=null && limitReleaseAmtMaster.compareTo(deltaReleaseAmt) == -1) {
															obReleaselinedetailsFile.setFileId(fileId);
															failReason="You are not eligible to authorised this transaction as per LOA master setup";
															obReleaselinedetailsFile.setReason(failReason);
															obReleaselinedetailsFile.setUploadStatus("N");
															obReleaselinedetailsFile.setStatus("FAIL");
															countFail++;
															countPass--;
															}
														}
													}
												}
											}	
										}
									}else {
										if(!(ICMSConstant.YES.equals(user.getOverrideExceptionForLoa()) || ICMSConstant.YES.equals(isFacMasterOverrideLoa))) {
											obReleaselinedetailsFile.setFileId(fileId);
											failReason="You are not eligible to authorised this transaction as per LOA master setup";
											obReleaselinedetailsFile.setReason(failReason);
											obReleaselinedetailsFile.setUploadStatus("N");
											obReleaselinedetailsFile.setStatus("FAIL");
											countFail++;
											countPass--;
										}
									}
								}								
							}
							
							

							if( totalReleasedAmount.compareTo(releasableAmount) > 0){
								DefaultLogger.debug(curLmt,"[Released Amount > releasableAmount] ==9===> "+totalReleasedAmount +" > " +releasableAmount);
									//errors.add("totalReleasedAmount", new ActionMessage("error.amount.not.greaterthan", "Released Amount", "Releasable Amount"));
								obReleaselinedetailsFile.setFileId(fileId);
								failReason="Release Amount is more than Releasable Amount.(At Checker Level)";
								obReleaselinedetailsFile.setReason(failReason);
								obReleaselinedetailsFile.setUploadStatus("N");
								obReleaselinedetailsFile.setStatus("FAIL");
								countFail++;
								countPass--;
								//errorList.add(obReleaselinedetailsFile);
							}
							}
							if(!"FAIL".equals(obReleaselinedetailsFile.getStatus()) && releaseAmtChecked==true ) {
								DefaultLogger.debug(curLmt,"Release amount is not null so I am inside this loop");
								jdbc.updateReleaseAmountStage(obReleaselinedetailsFile);
								jdbc.updateTotalReleasedAmtStage(obReleaselinedetailsFile);
							}
							//This method will be used in case the upload has null release amt
							if(!"FAIL".equals(obReleaselinedetailsFile.getStatus()) && releaseAmtChecked==false ) {
								DefaultLogger.debug(curLmt,"Release amount is null so I am inside this loop");
								jdbc.updateReleaseAmountStageforNull(obReleaselinedetailsFile);
								jdbc.updateTotalReleasedAmtStageforNull(obReleaselinedetailsFile);
								DefaultLogger.debug(curLmt,"updated the stage with the old values");

							}
							
						if(!"FAIL".equals(obReleaselinedetailsFile.getStatus()) ) {
					/*	jdbc.updateReleaseAmountStage(obReleaselinedetailsFile);
						jdbc.updateTotalReleasedAmtStage(obReleaselinedetailsFile);*/
						if(obReleaselinedetailsFile.getExpDate()!= null && !"".equals(obReleaselinedetailsFile.getExpDate())) {
							obReleaselinedetailsFile.setDateOfReset(DateUtil.convertDate(locale, obReleaselinedetailsFile.getExpDate()));
							jdbc.updateLimitExpDate(obReleaselinedetailsFile);
						}else {
							jdbc.updateLimitExpDateforNull(obReleaselinedetailsFile);
						}
					  jdbc.updateReleaseAmountActual(obReleaselinedetailsFile);
					  jdbc.updateTotalReleasedAmtActual(obReleaselinedetailsFile);
					  
					  if(obReleaselinedetailsFile.getExpDate()!= null && !"".equals(obReleaselinedetailsFile.getExpDate())) {
						  obReleaselinedetailsFile.setDateOfReset(DateUtil.convertDate(locale, obReleaselinedetailsFile.getExpDate()));
						  jdbc.updateLimitExpDateActual(obReleaselinedetailsFile);
						}else {
							jdbc.updateLimitExpDateActualforNull(obReleaselinedetailsFile);
						}
					  //Update PSL Flag Value In Stage and Actual
					  if(obReleaselinedetailsFile.getPslFlag() !=null && !"".equals(obReleaselinedetailsFile.getPslFlag())
					    && obReleaselinedetailsFile.getPslValue() != null && !"".equals(obReleaselinedetailsFile.getPslValue()))
					  {
						  jdbc.updatePSLFlagValue(obReleaselinedetailsFile);
						  jdbc.updatePSLFlagValueActual(obReleaselinedetailsFile);
						  
						  System.out.println("_______=========Check psl flag value"+obReleaselinedetailsFile.getPslFlag());
						  System.out.println("_______=========Check psl flag value"+obReleaselinedetailsFile.getPslValue());
					  }
					  
					 
					//Update Rule Id in Stage and Actual
					  if(obReleaselinedetailsFile.getRuleID() !=null && !"".equals(obReleaselinedetailsFile.getRuleID()))
							  {
								  jdbc.updateRuleID(obReleaselinedetailsFile);
								  jdbc.updateRuleIDActual(obReleaselinedetailsFile);
								  System.out.println("_______=========Check psl flag value"+obReleaselinedetailsFile.getRuleID());
							  }
							 
					  
					  
					  obReleaselinedetailsFile.setReason("-");
					  jdbc.updateReleaseLineUploadFile(obReleaselinedetailsFile);
						
						}
						
						if("FAIL".equals(obReleaselinedetailsFile.getStatus())){
							jdbc.updateReleaseLineUploadFile(obReleaselinedetailsFile);
						}
						
						///////////////////////////////////////////////
						
						
					
						
						
				    }   
				}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for  Release line details Upload##################:: ");
				

				
				DefaultLogger.debug(this,"spUpdateReleaselinedetailsUpload started:");
				DefaultLogger.debug(this,"spUpdateReleaselinedetailsUpload finished:");
				
				countFail=totalUploadedList.size()-countPass;
				
				resultMap.put("fileType", "EXCEL");
				resultMap.put("totalUploadedList", totalUploadedList);
				resultMap.put("total", String.valueOf(totalUploadedList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				resultMap.put("trxValueOut", trxValueOut);
		}catch(FileUploadException ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		} catch (TrxParameterException e) {
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
