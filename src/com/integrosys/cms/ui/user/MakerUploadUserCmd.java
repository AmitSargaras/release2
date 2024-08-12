/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.exolab.castor.xml.Unmarshaller;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.InvalidStatementTypeException;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.ISMSUserErrorLog;
import com.integrosys.cms.app.user.bus.ISMSUserErrorLogDao;
import com.integrosys.cms.app.user.bus.ISMSUserLog;
import com.integrosys.cms.app.user.bus.ISMSUserLogDao;
import com.integrosys.cms.app.user.bus.ISMSUserUploadLogDao;
import com.integrosys.cms.app.user.bus.OBCMSUser;
import com.integrosys.cms.app.user.bus.OBSMSUserErrorLog;
import com.integrosys.cms.app.user.bus.OBSMSUserLog;
import com.integrosys.cms.app.user.bus.OBSMSUserUploadLog;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.datafileparser.Column;
import com.integrosys.cms.batch.common.datafileparser.DataFile;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.bizstructure.app.bus.OBTeamTypeMembership;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.common.transaction.OBCompTrxResult;
import com.integrosys.component.commondata.app.bus.CommonDataDAO;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.OBSearchCommonUser;
import com.integrosys.component.user.app.constant.UserConstant;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;
import com.integrosys.component.user.app.trx.OBCommonUserTrxValue;

/**
 * @author $Author: Archana Command for user upload
 */
public class MakerUploadUserCmd extends AbstractCommand implements
		ICommonEventConstant, ICMSConstant {

	/**
	 * Default Constructor
	 */

	public MakerUploadUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				 {"theOBTrxContext",
				 "com.integrosys.cms.app.transaction.OBTrxContext",
				 FORM_SCOPE},
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "CommonUser", "com.integrosys.cms.app.user.bus.OBCMSUser",
						FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE },
				{ "CommonUser", "com.integrosys.cms.app.user.bus.OBCMSUser",
						FORM_SCOPE },
				{ "errorList", "java.util.HashMap", REQUEST_SCOPE },
				{ "rowCount", "java.lang.String", REQUEST_SCOPE },
				{ "fileUploadPending", "java.lang.String", REQUEST_SCOPE },
				{ "fileCheckSum", "java.lang.String", REQUEST_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
				{ "functionError", "java.lang.String", REQUEST_SCOPE },	
				{ "validRecords", "java.lang.String", REQUEST_SCOPE }	
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	/* (non-Javadoc)
	 * @see com.integrosys.base.uiinfra.common.AbstractCommand#doExecute(java.util.HashMap)
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String strError = "";
		String fileType = "";
		String fileUploadPending = "";
		String fileCheckSum = "";
		boolean createLog= false;
		boolean checkSumRow= true;
		ISMSUserLog userLog = new OBSMSUserLog();
		ISMSUserLogDao userLogDao= (ISMSUserLogDao)BeanHouse.get("SMSUserLog");
		ISMSUserErrorLogDao userErrorLogDao=(ISMSUserErrorLogDao) BeanHouse.get("SMSUserErrorLog");
		ISMSUserUploadLogDao userUploadLogDao=(ISMSUserUploadLogDao) BeanHouse.get("SMSUserUploadLog");
		
		String templatePath = (new BatchResourceFactory()).getTemplateXmlFileName(USER_UPLOAD);
		
		/*InputStream is = getClass().getResourceAsStream(templatePath);
		InputStreamReader read = new InputStreamReader(is);*/
		
		try {
			
			OBCMSUser commonUser = (OBCMSUser) map.get("CommonUser");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			userLog.setUploadFileName(commonUser.getFileUpload().getFileName());
			userLog.setUploadDate(new Date());
			userLog.setFileStatus("ACCEPTED");
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			DataFile dataFile = (DataFile) Unmarshaller.unmarshal(DataFile.class,read);
			Column[] column = dataFile.getHeader().getColumn();
			String remarks = (String) map.get("remarks");
			// check is file type is not then set error page.
			if (!commonUser.getFileUpload().getFileName().toUpperCase().endsWith(".DAT")) {
				fileType = "NOT_DAT";
				strError = "errorEveList";
				userLog.setFileStatus("REJECTED");
				userLog.setErrorCode("NOT_DAT");
				userLog.setErrorDesc("NOT_DAT");
				userLogDao.createSMSUserLog(userLog);
				clearUploadLogTable();
			}// check if any pending files not approved
			else {
				// Read Uploaded File
				ProcessDataFile processDataFile = new ProcessDataFile();
				ArrayList fileResultList = processDataFile.processFile(commonUser
						.getFileUpload(), USER_UPLOAD);
				HashMap errorMap=processDataFile.getErrorList();
				// Check if check-sum FooterCount must meet row Count
				if (processDataFile.isCheckSumFooter()) {
					fileCheckSum = "MISMATCH";
					strError = "errorEveList";
					userLog.setFileStatus("REJECTED");
					userLog.setErrorCode("MISMATCH");
					userLog.setErrorDesc("MISMATCH");
					userLogDao.createSMSUserLog(userLog);
					clearUploadLogTable();
				} else {
					
					/* Perform Validation */
					ArrayList resultList = new ArrayList();
					for (int i = 0; i < fileResultList.size(); i++) {
						HashMap userData = (HashMap) fileResultList.get(i);
						//String [][]errorList = new String[dataFile.getHeader().getColumn().length][4];
						String [][]errorList = new String[fileResultList.size()][4];
						/*
						 * Remove data for arraylist for which the Row checksum
						 * does not match
						 */
						String recordStatus = (String) userData.get(RECORD_STATUS);
						
						if (!checkSumRowCount(userData)) {
							errorList[i][0]="-";
							errorList[i][1]="Row Count Does Not Match";
							errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
							errorList[i][3]=String.valueOf(i);
							errorMap.put(new Integer(i+1), errorList);
							checkSumRow=false;
							fileCheckSum = "MISMATCH_COL";
							break;
						}
						/*
						 * Record needs to be discarded if Branch code is not
						 * present in the system
						 */
						if (!checkBranchCode(userData)) {
							errorList[i][0]=((String)column[4].getName()).toUpperCase();
							errorList[i][1]="Invalid Branch Code";
							errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
							errorList[i][3]=String.valueOf(i);
							errorMap.put(new Integer(i+1), errorList);
							continue;
						}
						
						if ( recordStatus.equalsIgnoreCase(RECORD_STATUS_CHANGE_BRANCH) && ! newCheckBranchCode(userData)) {
							errorList[i][0]=((String)column[5].getName()).toUpperCase();
							errorList[i][1]="Invalid New Branch Code";
							errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
							errorList[i][3]=String.valueOf(i);
							errorMap.put(new Integer(i+1), errorList);
							continue;
						}

						/*
						 * Record needs to be discarded if Region code is not
						 * present in the system
						 */
						// by abhijit R 21/09/11 changes by hdfc
						/*if (!checkRegions(userData, processDataFile
								.getSecondaryDelimiter())) {
							continue;
						}*/
						/*
						 * Record needs to be discarded if Department code code
						 * is not present in the system
						 */
						if(!(recordStatus.equalsIgnoreCase(RECORD_STATUS_DISABLE) || recordStatus.equalsIgnoreCase(RECORD_STATUS_TERMINATE))){
							if (!checkDepartmentCode(userData)) {
								errorList[i][0]=((String)column[6].getName()).toUpperCase();
								errorList[i][1]="Invalid Department Code";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						
						if(recordStatus.equalsIgnoreCase(RECORD_STATUS_MODIFY) || recordStatus.equalsIgnoreCase(RECORD_STATUS_ADD)){
							if (checkUserRole((String)userData.get(USER_ROLE))!=1 ) {
								errorList[i][0]=((String)column[7].getName()).toUpperCase();
								errorList[i][1]="Invalid User Role";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}

						/*
						 * Record needs to be discarded if segment code is not
						 * present in the system
						 */
						//Added Record status=A|E|D|M|C|T   for New CR from HDFC
						if(!recordStatus.equalsIgnoreCase(RECORD_STATUS_ADD)){
							if (!checkUser(userData)) {
								errorList[i][0]=((String)column[2].getName()).toUpperCase();
								errorList[i][1]="User Not Found";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
							
							
							if (!checkUserWIP(userData)) {
								errorList[i][0]="";
								errorList[i][1]="Work In Progress";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}else{
							if (checkUserForAdd(userData)) {
								errorList[i][0]=((String)column[2].getName()).toUpperCase();
								errorList[i][1]="User Already Exist";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						//Added Record status=A|E|D|M|C|T   for New CR from HDFC
						if(recordStatus.equalsIgnoreCase(RECORD_STATUS_DISABLE)){
							if (!((String)userData.get(STATUS)).equals(UserConstant.STATUS_INACTIVE)) {
								errorList[i][0]=((String)column[2].getName()).toUpperCase();
								errorList[i][1]="User Status Not Match With Record Status";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						//Added Record status=A|E|D|M|C|T   for New CR from HDFC
						if(recordStatus.equalsIgnoreCase(RECORD_STATUS_TERMINATE)){
							if (!(((String)userData.get(STATUS)).equals(ICMSConstant.STATUS_TERMINATE) ||
									((String)userData.get(STATUS)).equals(UserConstant.STATUS_INACTIVE)
									)) {
								errorList[i][0]=((String)column[2].getName()).toUpperCase();
								errorList[i][1]="User Status Not Match With Record Status";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						
						
						
						
						if(recordStatus.equalsIgnoreCase(RECORD_STATUS_MODIFY)|| recordStatus.equalsIgnoreCase(RECORD_STATUS_CHANGE_BRANCH)){
							
							
							if (!(checkUserDisable(userData))) {
								errorList[i][0]="STATUS";
								errorList[i][1]="User status is disabled";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						if(recordStatus.equalsIgnoreCase(RECORD_STATUS_ENABLE) 
								|| recordStatus.equalsIgnoreCase(RECORD_STATUS_UNLOCK)){
							
							
							if (!"A".equals((String) userData.get(STATUS))) {
								errorList[i][0]="STATUS";
								errorList[i][1]="User status is disabled";
								errorList[i][2]=(String)userData.get(EMPOLOYEE_CODE);
								errorList[i][3]=String.valueOf(i);
								errorMap.put(new Integer(i+1), errorList);
								continue;
							}
						}
						
						// by abhijit R 21/09/11 changes by hdfc
						/*if (!checkSegments(userData, processDataFile
								.getSecondaryDelimiter())) {
							continue;
						}*/
						resultList.add(userData);
					}
					resultMap.put("validRecords", String.valueOf(resultList.size()));
					// If there no error
					if (processDataFile.isUserMasterValidationStatus()
							&& !processDataFile.isCheckSumFooter()
							&& fileType.equals("")
							&& checkSumRow) {
						for (int i = 0; i < resultList.size(); i++) {
							HashMap userData = (HashMap) resultList.get(i);
							String recordStatus = (String) userData
									.get(RECORD_STATUS);
							CMSUserProxy userProxy = new CMSUserProxy();
							ICommonUserTrxValue userTrxVal = new OBCommonUserTrxValue();
							/* Populate User OB with data */
							OBCommonUser user = new OBCMSUser();
							if (recordStatus.trim().equals(RECORD_STATUS_ADD)) {
								ICommonUser userVal = null;
								try{
									userVal = userProxy
											.getUser(((String) userData
													.get(EMPOLOYEE_CODE)).toUpperCase());
									
								}catch (EntityNotFoundException e) {
									userVal = null;
								} catch (RemoteException e) {
									userVal = null;
								}
								if(userVal != null){
									/* TODO: handle this error condition 
									 * User trying to be added already exists
									 * */
								}
									
							}else{
								try{
									CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
									StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
									criteriaOB.setEmployeeId(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
									criteria.setCriteria(criteriaOB);
									criteria.setNItems(10);
									SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
									java.util.Vector vector=(java.util.Vector)searchResult.getResultList();
									OBSearchCommonUser userVal2=(OBSearchCommonUser) vector.get(0);
								//	ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
									userTrxVal = userProxy.getUserByPK(String.valueOf(userVal2.getUserID()));
									AccessorUtil.copyValue(userTrxVal.getStagingUser(), user);
								}catch (EntityNotFoundException e) {
									/* TODO: handle this error condition 
									 * User trying to be modifiy does not exist
									 * */
									e.printStackTrace();
								} catch (RemoteException e) {
									/* TODO: handle this error condition 
									 * User trying to be modifiy does not exist*/
									e.printStackTrace();
								}
							}
							user.setLoginID(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
							user.setUserName((String) userData.get(EMPOLOYEE_NAME));
							user.setEmployeeID(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
							user.setEjbBranchCode((String) userData.get(BRANCH_CODE));
							if(recordStatus.equalsIgnoreCase(RECORD_STATUS_CHANGE_BRANCH))
								user.setEjbBranchCode((String) userData.get(NEW_BRANCH_CODE));
							if(!(recordStatus.equalsIgnoreCase(RECORD_STATUS_DISABLE) || recordStatus.equalsIgnoreCase(RECORD_STATUS_TERMINATE)))
								user.setDepartment((String) userData.get(DEPARTMENT_CODE));
							// by abhijit R 21/09/11 changes by hdfc
							/*String segmentCodes = (String) userData
									.get(SEGMENT);
							String[] segments = segmentCodes
									.split(processDataFile
											.getSecondaryDelimiter());
							ICommonUserSegment[] userSegments = new ICommonUserSegment[segments.length];

							for (int j = 1; j < segments.length; j++) {
								ICommonUserSegment segment = new OBCommonUserSegment();
								segment.setSegmentCode(segments[j]);
								userSegments[j] = segment;

							}
							user.setUserSegment(userSegments);

							String regionsCode = (String) userData.get(REGION);
							String[] regions = regionsCode
									.split(processDataFile
											.getSecondaryDelimiter());
							ICommonUserRegion[] userRegions = new ICommonUserRegion[regions.length];

							for (int j = 1; j < regions.length; j++) {
								ICommonUserRegion region = new OBCommonUserRegion();
								IRegionDAO regionDao = (IRegionDAO) BeanHouse
										.get("regionDAO");
								region.setRegionCode(String.valueOf(regionDao
										.getRegionByRegionCode(regions[j])
										.getIdRegion()));
								userRegions[j] = region;

							}
							user.setUserRegion(userRegions);*/
							user.setStatus((String) userData.get(STATUS));	
							if(recordStatus.equalsIgnoreCase(RECORD_STATUS_TERMINATE) && (userData.get(STATUS).toString().equalsIgnoreCase("I") || userData.get(STATUS).toString().equalsIgnoreCase("T"))){
								user.setStatus("T");
							}
							if((recordStatus.equalsIgnoreCase(RECORD_STATUS_ADD) || recordStatus.equalsIgnoreCase(RECORD_STATUS_MODIFY)) || 
								(null!=userData.get(USER_ROLE) && !("".equals(userData.get(USER_ROLE))))
								){	
								long teamTypeMembershipId = Long.parseLong((String) userData.get(USER_ROLE));
								OBTeamTypeMembership teamTypeMembership = new OBTeamTypeMembership();
								teamTypeMembership.setMembershipID(teamTypeMembershipId);
								user.setTeamTypeMembership(teamTypeMembership);
							}
							user.setCountry("IN");
							//Added Record status=A|E|D|M|C|T   for New CR from HDFC
							if (recordStatus.trim().equals(RECORD_STATUS_ADD)) {
								OBUserTrxValue trxValue = new OBUserTrxValue();
								ICompTrxResult trxResult =userProxy.makerCreateUser(ctx, trxValue, user);
								resultMap.put("request.ITrxResult", trxResult);
								createLog=true;
							} else if (recordStatus.equalsIgnoreCase(RECORD_STATUS_ENABLE) ||
										recordStatus.equalsIgnoreCase(RECORD_STATUS_DISABLE) || 
										recordStatus.equalsIgnoreCase(RECORD_STATUS_MODIFY) || 
										recordStatus.equalsIgnoreCase(RECORD_STATUS_CHANGE_BRANCH) || 
										recordStatus.equalsIgnoreCase(RECORD_STATUS_TERMINATE) ||
										recordStatus.equalsIgnoreCase(RECORD_STATUS_UNLOCK)) {
								ICompTrxResult trxResult =userProxy.makerUpdateUser(ctx, userTrxVal, user);
								resultMap.put("request.ITrxResult", trxResult);
								
								OBCompTrxResult ob=(OBCompTrxResult)trxResult;
								OBUserTrxValue obuser=((OBUserTrxValue)ob.getTrxValue());
								if("U".equals(recordStatus)) {
									updateUnlock(obuser.getStagingUser().getUserID());
								}
								
								createLog=true;
							} else {
								// Need to handle this error condition
								resultMap.put("functionError", "true");
							}
						}
//						userLogDao.createSMSUserLog(userLog);	
					} else {
						userLog.setFileStatus("REJECTED");
						/* TODO: handle this error condition */
						/* The file is invalid or footer checksum is incorrect, so whole file should be discarded*/
					}
					// If there is a Error
//					if (processDataFile.isValid() == false) {
						userLog.setSuccessfullCount(String.valueOf((processDataFile.getMaxCount()-2)-processDataFile.getErrorList().size()));
						userLog.setUnsuccessfullCount(String.valueOf((processDataFile.getMaxCount()-2)-Integer.parseInt(userLog.getSuccessfullCount())));
						resultMap.put("rowCount", String.valueOf(processDataFile
								.getMaxCount()));
						resultMap.put("errorList", errorMap);
						strError = "errorEveList";
						userLogDao.createSMSUserLog(userLog);
						HashMap errorIdMap= new HashMap();
					for (int iterate = 0; iterate < processDataFile
							.getMaxCount(); iterate++) {
						String[][] errorData = new String[processDataFile
								.getErrorList().size()][4];
						errorData = (String[][]) processDataFile.getErrorList()
								.get(new Integer(iterate));
						
						if (!(errorData == null)) {
							for (int j = 0; j <= errorData.length - 1; j++) {
								if (errorData[j][0] != null) {
									ISMSUserErrorLog userErrorLog = new OBSMSUserErrorLog();
									userErrorLog.setUploadId(userLog
											.getUploadId());
									userErrorLog.setUploadFileName(userLog
											.getUploadFileName());
									userErrorLog.setUploadDate(new Date());
									userErrorLog
											.setRecordCount(errorData[j][3]);
									userErrorLog.setUserId(errorData[j][2]);
									userErrorLog.setErrorCode(errorData[j][1]);
									userErrorLog.setErrorDesc(errorData[j][1]);
									
									errorIdMap.put(errorData[j][2], errorData[j][1]);
									userErrorLogDao
											.createSMSUserErrorLog(userErrorLog);

								}
							}
						}
						
						
						
					}
					
					clearUploadLogTable();
					DefaultLogger.error(this, "#############  cleared upload log tables");
					for (int i = 0; i < fileResultList.size(); i++) {
						DefaultLogger.error(this, "#############  fileResultList.size():"+fileResultList.size());
						HashMap userDataUpload = (HashMap) fileResultList.get(i);
						OBSMSUserUploadLog uploadLog= new OBSMSUserUploadLog();
						uploadLog.setActivity((String)userDataUpload.get(RECORD_STATUS));
						uploadLog.setBranchCode((String)userDataUpload.get(BRANCH_CODE));
						uploadLog.setDeptCode((String)userDataUpload.get(DEPARTMENT_CODE));
						if(errorIdMap.containsKey((String)userDataUpload.get(EMPOLOYEE_CODE))){
							
							uploadLog.setSuccessReject("Reject");
							uploadLog.setRejectReason((String)errorIdMap.get((String)userDataUpload.get(EMPOLOYEE_CODE)));
						}else{
							uploadLog.setSuccessReject("Success");
							uploadLog.setRejectReason("");
						}
						uploadLog.setUploadDate(new Date());
						uploadLog.setUploadFileName(commonUser.getFileUpload().getFileName());
						uploadLog.setUserId((String)userDataUpload.get(EMPOLOYEE_CODE));
						uploadLog.setUserName((String)userDataUpload.get(EMPOLOYEE_NAME));
						uploadLog.setUserRole((String)userDataUpload.get(USER_ROLE));
						uploadLog.setVersionTime(0l);
						DefaultLogger.error(this, "############# creating log:");
						userUploadLogDao.createSMSUserUploadLog(uploadLog);
						
					}
//					}
				}// end else if for footer mismatch
				
			}// end else if for CSV
			
			resultMap.put("fileUploadPending", fileUploadPending);
			resultMap.put("fileCheckSum", fileCheckSum);
			resultMap.put("fileType", fileType);
			resultMap.put("errorEveList", strError);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		/*
		 * catch (TransactionException e) { DefaultLogger.debug(this,
		 * "got exception in doExecute" + e); throw (new
		 * CommandProcessingException(e.getMessage())); }
		 */

	}
	
	public boolean checkSumRowCount(HashMap vvalues) {
		
		// Check Sum = Record Status + Employee Code + Branch Code 
		// Record status=A|E|D|M|C|T Employee code=A9979,Branch Code=0499,
		// 2|E|A9979|Jitendra Khodiar|0499||069||A|574
		
		boolean checkSumRow = false;

		long recordStatus = ((String) vvalues.get(RECORD_STATUS)).charAt(0);
		BigDecimal lgCheckSumRow = new BigDecimal(0);
		BigDecimal lgCheckSumRowCount = new BigDecimal(0);
		String empCode = (String) vvalues.get(EMPOLOYEE_CODE);
		String branchCode = (String) vvalues.get(BRANCH_CODE);
		int empCodeappend = 0;
		int branchCodeappend = 0;
		
		for (int i = 0; i < empCode.length(); i++) {
			empCodeappend += empCode.charAt(i);
		}
		for (int i = 0; i < branchCode.length(); i++) {
			branchCodeappend += branchCode.charAt(i);
		}
		
		lgCheckSumRow = new BigDecimal(recordStatus).add(new BigDecimal(empCodeappend)).add(new BigDecimal(branchCodeappend));
			
		lgCheckSumRowCount = new BigDecimal((String) vvalues.get(ROW_CHECKSUM));
		if (lgCheckSumRow.compareTo(lgCheckSumRowCount)==0) {
			checkSumRow = true;
		}
		return checkSumRow;
	}

	private boolean checkBranchCode(HashMap userData) {
		String branchCode = (String) userData.get(BRANCH_CODE);
		ISystemBankBranchDao systemBankBranchDao = (ISystemBankBranchDao) BeanHouse
				.get("systemBankBranchDao");
		boolean status = systemBankBranchDao.isUniqueCode("SYSTEM_BANK_BRANCH_CODE",branchCode);
		return status;
	}
	private boolean newCheckBranchCode(HashMap userData) {
		String branchCode = (String) userData.get(NEW_BRANCH_CODE);
		ISystemBankBranchDao systemBankBranchDao = (ISystemBankBranchDao) BeanHouse
				.get("systemBankBranchDao");
		boolean status = systemBankBranchDao.isUniqueCode("SYSTEM_BANK_BRANCH_CODE",branchCode);
		return status;
	}
	private boolean checkDepartmentCode(HashMap userData) {
		try {
			boolean status = true;
			String departmentCode = (String) userData.get(DEPARTMENT_CODE);
			CommonDataDAO commonDataDAO = new CommonDataDAO();
			OBCodeCategoryEntry entry = commonDataDAO.getCodeCategoryEntry(
					CategoryCodeConstant.HDFC_DEPARTMENT, departmentCode, true);
			if (entry == null) {
				status = false;
			}

			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	private boolean checkRegions(HashMap userData, String delimiter) {
		boolean status = true;
		String regionsCode = (String) userData.get(REGION);
		String[] regions = regionsCode.split(delimiter);
		int regionCount = Integer.parseInt(regions[0]);
		if (regionCount == regions.length - 1) {
			for (int i = 1; i < regions.length; i++) {
				IRegionDAO regionDao = (IRegionDAO) BeanHouse.get("regionDAO");
				if (!regionDao.isRegionCodeUnique(regions[i]))
					status = false;
			}
		} else {
			status = false;
		}

		return status;
	}

	private boolean checkSegments(HashMap userData, String delimiter) {
		try {
			boolean status = true;
			String segmentsCode = (String) userData.get(SEGMENT);
			String[] segments = segmentsCode.split(delimiter);
			int segmentCount = Integer.parseInt(segments[0]);
			if (segmentCount == segments.length - 1) {
				for (int i = 1; i < segments.length; i++) {
					CommonDataDAO commonDataDAO = new CommonDataDAO();
					OBCodeCategoryEntry entry = commonDataDAO
							.getCodeCategoryEntry(
									CategoryCodeConstant.HDFC_SEGMENT,
									segments[i], true);
					if (entry == null) {
						status = false;
					}
				}
			} else {
				status = false;
			}

			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

	}
	private boolean checkUser(HashMap userData) {
		boolean status =true;
		String recordStatus = (String) userData.get(RECORD_STATUS);
		if(!recordStatus.trim().equals(RECORD_STATUS_ADD)){
		try{
		CMSUserProxy userProxy = new CMSUserProxy();
		ICommonUserTrxValue userTrxVal = new OBCommonUserTrxValue();
//		if(recordStatus.trim().equals(RECORD_STATUS_MODIFY)){
			//ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
			CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
			StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
			criteriaOB.setEmployeeId(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
			criteria.setCriteria(criteriaOB);
			criteria.setNItems(10);
			SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
			
			if(searchResult !=null){
				if(searchResult.getNItems()==0){
				status=false;
				}
			}else{
				status=false;
			}
//		}
		
		}catch (Exception e) {
			status=false;
		e.printStackTrace();
		}
		}
 		return status;
	}
	
	
	private boolean checkUserForAdd(HashMap userData) {
		boolean status =true;
		String recordStatus = (String) userData.get(RECORD_STATUS);
		if(recordStatus.trim().equals(RECORD_STATUS_ADD)){
		try{
		CMSUserProxy userProxy = new CMSUserProxy();
		ICommonUserTrxValue userTrxVal = new OBCommonUserTrxValue();
//		if(recordStatus.trim().equals(RECORD_STATUS_MODIFY)){
			//ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
			CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
			StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
			criteriaOB.setEmployeeId(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
			criteria.setCriteria(criteriaOB);
			criteria.setNItems(10);
			SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
			
			if(searchResult !=null){
				if(searchResult.getNItems()==0){
				status=false;
				}
			}else{
				status=false;
			}
//		}
		
		}catch (Exception e) {
			status=false;
		e.printStackTrace();
		}
		}
 		return status;
	}
	
	private boolean checkUserWIP(HashMap userData) {
		boolean status =true;
		try{
		CMSUserProxy userProxy = new CMSUserProxy();
		ICommonUserTrxValue userTrxVal = new OBCommonUserTrxValue();
		String recordStatus = (String) userData
		.get(RECORD_STATUS);
		if(!(recordStatus.trim().equals(RECORD_STATUS_ADD))){
			CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
			StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
			criteriaOB.setEmployeeId(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
			criteria.setCriteria(criteriaOB);
			criteria.setNItems(10);
			SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
			java.util.Vector vector=(java.util.Vector)searchResult.getResultList();
			OBSearchCommonUser userVal=(OBSearchCommonUser) vector.get(0);
			
			//ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
			userTrxVal = userProxy.getUserByPK(String.valueOf(userVal.getUserID()));
			if(!userTrxVal.getStatus().equals("ACTIVE")){
				status=false;
			}
		}
		}catch (Exception e) {
		e.printStackTrace();
		}
		return status;
	}
	public int checkUserRole(String role) throws SearchDAOException {
		HashMap paramMap = new HashMap();
		DBUtil dbUtil = null;
		String SELECT_ROLE = "select 1 from CMS_TEAM_TYPE_MEMBERSHIP where TEAM_TYPE_MEMBERSHIP_ID=?";
		DefaultLogger.debug(this, "sql is " + SELECT_ROLE+"   ->"+role);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_ROLE);
			dbUtil.setString(1, role.trim());
			ResultSet rs = dbUtil.executeQuery();
			int count = 0;
			if (rs.next()) {
				
				count=rs.getInt("1");
				DefaultLogger.debug(this, "Result for query :"+count);
			}
			return count;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (InvalidStatementTypeException ie) {
			throw new SearchDAOException(ie);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
	
	private boolean checkUserDisable(HashMap userData) {
		boolean status =true;
		try{
		CMSUserProxy userProxy = new CMSUserProxy();
		ICommonUserTrxValue userTrxVal = new OBCommonUserTrxValue();
		String recordStatus = (String) userData
		.get(RECORD_STATUS);
		if(!(recordStatus.trim().equals(RECORD_STATUS_ADD))){
			CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
			StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
			criteriaOB.setEmployeeId(((String) userData.get(EMPOLOYEE_CODE)).toUpperCase());
			criteria.setCriteria(criteriaOB);
			criteria.setNItems(10);
			SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
			java.util.Vector vector=(java.util.Vector)searchResult.getResultList();
			OBSearchCommonUser userVal=(OBSearchCommonUser) vector.get(0);
			
			//ICommonUser userVal = userProxy.getUser((String) userData.get(EMPOLOYEE_CODE));
			userTrxVal = userProxy.getUserByPK(String.valueOf(userVal.getUserID()));
			if(!userTrxVal.getUser().getStatus().equals("A")){
				status=false;
			}
		}
		}catch (Exception e) {
		e.printStackTrace();
		}
		return status;
	}
	public void clearUploadLogTable()throws Exception {
		
		 DBUtil dbUtil;
		 dbUtil = new DBUtil();
		try{
			
				
			String query = " delete  from CMS_SMS_UPLOAD_LOG ";
			                               
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			dbUtil.commit();
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "############# Error in clearing upload log tables",e);
			e.printStackTrace();
			throw new Exception("Error in clearing upload log tables");
		}	
		
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
	
	private void updateUnlock(long userId) {
		String query="UPDATE STAGE_USER SET IS_UNLOCK = 'Y' WHERE USER_ID="+userId+"";
		
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
}
