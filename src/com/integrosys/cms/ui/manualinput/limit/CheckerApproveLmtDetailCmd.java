/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf2;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.email.notification.bus.ICustomerNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.IEmailNotification;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationService;
import com.integrosys.cms.app.email.notification.bus.OBCustomerNotificationDetail;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.json.command.PrepareSendReceiveLineCommand;
import com.integrosys.cms.app.json.dao.ScmLineDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UDFComparator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckerApproveLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.class.getName(), GLOBAL_SCOPE }, 
				{"isCamCovenantVerified", String.class.getName(), REQUEST_SCOPE},
				{ "isDelete", "java.lang.String", REQUEST_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
		
		String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);

		boolean isLoaAuthorizer = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_CHECKER).equals(teamTypeMembershipID) || 
				String.valueOf(ICMSConstant.CPU_MAKER_CHECKER).equals(teamTypeMembershipID);
		
		String isCamCovenantVerified = (String) map.get("isCamCovenantVerified");

		ILimitDAO dao2 = LimitDAOFactory.getDAO();
        List<String> linePrimaryId =new ArrayList<String>();

		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			MILimitUIHelper helper = new MILimitUIHelper();
			String isDelete = (String) (map.get("isDelete"));
			if (!AbstractCommonMapper.isEmptyOrNull(isDelete)) {
				lmtTrxObj.getStagingLimit().setLimitStatus("DELETED");
			}
			
			//limit covenant
			String stgReferenceId = lmtTrxObj.getStagingReferenceID();
			String stg = "stgTable";
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			if(lmtTrxObj.getStagingLimit().getLimitCovenant() != null && lmtTrxObj.getStagingLimit().getLimitCovenant().length==0){
				ILimitCovenant[] covStg = lmtDao.getCovenantData(stgReferenceId,stg);
				lmtTrxObj.getStagingLimit().setLimitCovenant(covStg);
			}
			
			DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==88==>>***=>");
			ILimitSysXRef[] refArrNew = lmtTrxObj.getStagingLimit().getLimitSysXRefs();

			ILimitSysXRef[] refArr = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
			
			ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = null;
			if(isLoaAuthorizer) {
				if(refArr!=null) {
					Map loaValidationMap = LimitsOfAuthorityMasterHelper.validateLoaMasterFieldsForLimits(exceptionMap, user, isCamCovenantVerified, lmtTrxObj);
					exceptionMap = (HashMap) loaValidationMap.get("exceptionMap");
					obLimitsOfAuthorityMasterTrxLog = (ILimitsOfAuthorityMasterTrxLog) loaValidationMap.get("obLimitsOfAuthorityMasterTrxLog");
				}	
			}
			ILimit  lmtStg = lmtTrxObj.getStagingLimit();
			ILimit  lmtActual = lmtTrxObj.getLimit();
			if(null != lmtStg ) {
				
			
			if("Y".equals(lmtStg.getIsReleased())){
				if((null != lmtStg.getLimitSysXRefs()  && lmtStg.getLimitSysXRefs().length>0)){
					if(null != lmtActual) {
						if(null != lmtActual.getReleasableAmount() && !"".equals(lmtActual.getReleasableAmount())) {
					BigDecimal releasableAmount = new BigDecimal(lmtActual.getReleasableAmount());
					for(int i=0;i<lmtStg.getLimitSysXRefs().length;i++) {
						if(null != lmtStg.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount() ) {
							BigDecimal idlAmount =	new BigDecimal(lmtStg.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount());
							int flags = releasableAmount.compareTo(idlAmount);
						if(flags == -1) {
							exceptionMap.put("releasableAmountChecker", new ActionMessage("error.amount.not.greaterthan", "IDL Amount", "Actual Releasable Amount"));
						}
						}
					}
					}
					}
					//exceptionMap.put("lineDetailsError", new ActionMessage("error.line.details.mandatory.if.released"));
					
					Locale locale = Locale.getDefault();
					String limitProfileId = String.valueOf(lmtTrxObj.getLimitProfileID());
					 LimitDAO limitDAO=new LimitDAO();
					 
					 if(null != refArr) {
						 for(int i=0;i<refArr.length;i++) {
							 OBCustomerSysXRef xrefForm = (OBCustomerSysXRef) refArr[i].getCustomerSysXRef();
					 if(null != xrefForm.getIdlExpiryDate() && !"".equals(xrefForm.getIdlExpiryDate())) {
					 String camExtensionDate = limitDAO.getCamExtensionDateMethod(limitProfileId);
					 System.out.println("CheckerApproveLmtDetailCmd.java=>camExtensionDate=>"+camExtensionDate+"......xrefForm.getIdlExpiryDate()=>"+xrefForm.getIdlExpiryDate());
					String idlExpiryDate1 =  (DateUtil.formatDate(locale, xrefForm.getIdlExpiryDate()));
								Date d2 = DateUtil.convertDate(locale, idlExpiryDate1);
								Date d1 = DateUtil.convertDate(locale, camExtensionDate);
								int a = d1.compareTo(d2);
								if (a < 0) {
									exceptionMap.put("idlexpirydateerrorchecker", new ActionMessage("error.date.compareDate.greater",
											"IDL Expiry Date" ,"Extension Date" ));
								}
					 }
						 }
					 }
				}
			}}
			
			
			
			if(!exceptionMap.isEmpty()) {
				result.put("request.ITrxResult", null);
				temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
				return temp;
			}
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==103==>>applicationDate=>"+applicationDate);
			
			List<Long> lineId=new ArrayList<Long>();
			
			List<Long> lineIdPending=new ArrayList<Long>();
			
			List<Long> lineIdWithSucessStatus=new ArrayList<Long>();
			List<Long> lineIdWithRejectStatus=new ArrayList<Long>();
			String successSourceRefNo="";
			String rejectedSourceRefNo="";
			
			List<Long> lineIdNotToSend=new ArrayList<Long>();
			
			List<Long> lineIdUpdateStatus=new ArrayList<Long>();
			String colSubtype = "";
			String stockdocMonth = "";
			String stockdocYear = "";
			String collateralId = "";
			String collateralIdForDelete = "";
			long collateralIdForDeleteLong = 0;
			ILimitDAO daoLmt = LimitDAOFactory.getDAO();
			
			String actualCollateralId = "";
			long actualCollateralIdLong = 0;
			String actCollatId = "";
			String facilityLmtId = "";
			
			if(null != lmtTrxObj.getLimit()) {
			ICollateralAllocation[] colsActual = lmtTrxObj.getLimit().getCollateralAllocations();
			
			if(null != colsActual) {
			for(int i=0;i<colsActual.length;i++) {
				if(null != colsActual[i].getCollateral()) {
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==229==>>colsActual[i].getCollateral().getSourceSecuritySubType()=>"+colsActual[i].getCollateral().getSourceSecuritySubType());
				if ("AB100".equals(colsActual[i].getCollateral().getSourceSecuritySubType())) {
				long colId = colsActual[i].getCollateral().getCollateralID();
				 actCollatId = Long.toString(colId);
				String statusOfCol = colsActual[i].getHostStatus();
				DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==234==>>statusOfCol : "+statusOfCol+" ... actCollatId=>"+actCollatId);
				if("I".equals(statusOfCol)) {
					facilityLmtId = colsActual[i].getSourceLmtId();//Facility Id
					actualCollateralId = actCollatId;
					actualCollateralIdLong = colId;
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==239==>>facilityLmtId : "+facilityLmtId+" ... actualCollateralId=>"+actualCollateralId+" .. actualCollateralIdLong=>"+actualCollateralIdLong);
				}
			}
			}
			}
		}}
			
			String statusDelete = "";
			
			ICollateralAllocation[] cols = lmtTrxObj.getStagingLimit().getCollateralAllocations();
			if(null != cols) {
				for(int i=0;i<cols.length;i++) {
					
					if(null != cols[i].getCollateral()) {
						DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==251==>>cols[i].getCollateral().getSourceSecuritySubType()=>"+cols[i].getCollateral().getSourceSecuritySubType()+"...cols.length=>"+cols.length);
				if ( ("AB100".equals(cols[i].getCollateral().getSourceSecuritySubType()))) {
					long colId = cols[i].getCollateral().getCollateralID();
					collateralId = Long.toString(colId);
					colSubtype = cols[i].getCollateral().getSCISubTypeValue();
					statusDelete = cols[i].getHostStatus();
					if("D".equals(statusDelete) && "".equals(stockdocMonth) && "".equals(stockdocYear)) {
						stockdocMonth = "";
						stockdocYear = "";
						collateralIdForDelete = collateralId;
						collateralIdForDeleteLong = colId;
					}else {
						stockdocMonth = daoLmt.getStockDocMonthByColId(collateralId);
						stockdocYear = daoLmt.getStockDocYearByColId(collateralId);
					}
					
					 DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==251==>>collateralId=>"+collateralId+"...colSubtype=>"+colSubtype+"...stockdocMonth=>"+stockdocMonth+"...stockdocYear=>"+stockdocYear+"..i=>"+i+"...statusDelete=>"+statusDelete);
//					if("D".equals(statusDelete)) {
//						stockdocMonth = "";
//						stockdocYear = "";
//						collateralIdForDelete = collateralId;
//						collateralIdForDeleteLong = colId;
//					}
					//break;
				}
				}
				}
			}
			
			
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			ArrayList xrefIdList = new ArrayList();;
			
			if(!"".equals(actualCollateralId)) {
				if(actualCollateralId.equals(collateralId)) {
					
				}else {
					collateralIdForDelete = actualCollateralId;
					collateralIdForDeleteLong = actualCollateralIdLong;
				}
			}
			
			System.out.println("actualCollateralId=>"+actualCollateralId+" ** collateralId=>"+collateralId+" ** collateralIdForDelete=>"+collateralIdForDelete+" ** xrefIdList=>"+xrefIdList);

			
		
			
			System.out.println("CheckerApproveLmtDetailCmd.java=>user.getLoginID()=>"+user.getLoginID());
			if (null != refArr) {
				for (int i = 0; i < refArr.length; i++) {
					ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj
							.getCustomerSysXRef();
					DefaultLogger.debug(this, "134 - XREF OBJECT DATA ========================="+xref);
					DefaultLogger.debug(this, "135 - xref.getUpdatedOn() OBJECT DATA ========================="+xref.getUpdatedOn());
					DefaultLogger.debug(this, "136 - xref.getCreatedOn() OBJECT DATA ========================="+xref.getCreatedOn());
					if (xref.getUpdatedOn() != null) {
						if (xref.getCreatedOn() != null) {
							if (xref.getCreatedOn().after(xref.getUpdatedOn())) {
								xref.setUpdatedBy(user.getLoginID());
								xref.setUpdatedOn(applicationDate);
							}
						}
					} else {
						xref.setUpdatedBy(user.getLoginID());
						xref.setUpdatedOn(applicationDate);
					}
					if(null != xref.getStatus() && !"".equals(xref.getStatus())) {
						if(xref.getStatus().startsWith("PENDING_")) {
							xref.setCheckerIdNew(user.getLoginID());
							xref.setUpdatedOn(applicationDate);							
						}
					}
					
					if("PENDING_UPDATE".equals(xref.getStatus()) && "Y".equals(xref.getSendToFile())){
						lineId.add(refArr[i].getSID());
					}else if ("PENDING_UPDATE".equals(xref.getStatus()) && "N".equals(xref.getSendToFile())){
						lineIdNotToSend.add(refArr[i].getSID());
					}else if (ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xref.getStatus()) || ICMSConstant.FCUBS_STATUS_PENDING_REJECTED.equals(xref.getStatus())){
						lineIdUpdateStatus.add(refArr[i].getSID());
					}
					
					if("PENDING".equals(xref.getStatus())){
						ILimitDAO dao = LimitDAOFactory.getDAO();
						
						String statusFromFcubs = dao.getStatusFromFcubs(xref.getSourceRefNo());
						if(!statusFromFcubs.isEmpty() && !statusFromFcubs.equals(xref.getStatus())){
							if(statusFromFcubs.equals(ICMSConstant.FCUBS_STATUS_SUCCESS)){
							lineIdWithSucessStatus.add(refArr[i].getSID());
							successSourceRefNo="'"+xref.getSourceRefNo()+"',"+successSourceRefNo;
							}
							else{
							lineIdWithRejectStatus.add(refArr[i].getSID());	
							rejectedSourceRefNo="'"+xref.getSourceRefNo()+"',"+rejectedSourceRefNo;
							}
						}else{
							lineIdPending.add(refArr[i].getSID());
							if(!"UBS-LIMITS".equals(xref.getFacilitySystem()) && !"FCUBS-LIMITS".equals(xref.getFacilitySystem())) {
								xref.setSendToCore("Y");
							}
						}
					}
					if("BAHRAIN".equals(xref.getFacilitySystem()) || "HONGKONG".equals(xref.getFacilitySystem()) || "GIFTCITY".equals(xref.getFacilitySystem())) {

						//Prod Issue 692
						ILimitDAO dao1 = LimitDAOFactory.getDAO();
						if(null != xref.getStatus() && !"".equals(xref.getStatus())) {
							if(xref.getStatus().startsWith("PENDING")) {
								dao1.updateStageLineCheckerIdNewDetails(xref,user.getLoginID(),applicationDate);
							}								
						}
							
						
						//
						
						

						if("PENDING".equals(xref.getStatus())) {
						xref.setStatus(ICMSConstant.FCUBS_STATUS_SUCCESS);
						}
						/*if("BAHRAIN".equals(xref.getFacilitySystem())) {
							xref.setLiabBranch("001");
						}
						if("HONGKONG".equals(xref.getFacilitySystem())) {
							xref.setLiabBranch("101");
						}
						if("GIFTCITY".equals(xref.getFacilitySystem())) {
							xref.setLiabBranch("301");
						}*/
					}
					//SCM Interface
					

					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==760==>>"+lmtTrxObj.getLimitProfileID());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==761==>>"+lmtTrxObj.getCustomerID());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==762==>>"+lmtTrxObj.getStatus());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==763==>>"+lmtTrxObj.getStagingReferenceID());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==764==>>"+lmtTrxObj.getLimitProfileReferenceNumber());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==765==>>"+xref.getSerialNo());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==766==>>"+xref.getLineNo());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==767==>>"+xref.getHiddenSerialNo());
					DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==768==>>"+xref.getXRefID());
					//DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==769==>>"+lmtTrxObj.getLimit().getLimitID());
					IJsInterfaceLog log = new OBJsInterfaceLog();
					ScmLineDao scmLineDao = (ScmLineDao)BeanHouse.get("scmLineDao");
					long limitProfId = 0L;
					try {
					limitProfId = lmtTrxObj.getLimitProfileID();
					}catch(Exception e) {
						DefaultLogger.debug(this, "Exception occured while setting the value for profile Id "+e);
						limitProfId = 0L;
						System.out.println("Exception occured while setting the value for profile Id "+e);
						e.printStackTrace();
					}
					String limitProfileId = null;
					if(limitProfId!=0) {
						limitProfileId = Long.toString(limitProfId);
					}
					long  lineid = 0L;
					try {
					lineid =xref.getXRefID();
					}catch(Exception e) {
						DefaultLogger.debug(this, "Exception occured while setting the value for xrefId "+e);
						lineid = 0L;
					}
					String xrefId = null;
					if(lineid!=0) {
						xrefId = Long.toString(lineid);
					}
					long customerId = 0L;
					try {
					customerId = lmtTrxObj.getCustomerID();
					}catch(Exception e) {
						DefaultLogger.debug(this, "Exception occured while setting the value for customer "+e);
						customerId = 0L;
					}
					String custId = null;
					if(customerId!=0) {
						custId = Long.toString(customerId);
					}
					long lmtID = 0L;
					try {
					lmtID = lmtTrxObj.getLimit().getLimitID();
					}catch(Exception e ) {
						DefaultLogger.debug(this, "Exception occured while setting the value for limitId "+e);
						lmtID = 0L;
					}
				    String limitId = null;	
				    if(lmtID!=0) {
				    	limitId = Long.toString(lmtID);
					}
					String mainScmFlag = scmLineDao.getScmFlagfromStg(xrefId);
					String stgScmFlag = scmLineDao.getScmFlagfromMain(limitProfileId, xref.getLineNo(), xref.getHiddenSerialNo());
					
					try {
						if (ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xref.getStatus())) {
						PrepareSendReceiveLineCommand scmWsCall = new PrepareSendReceiveLineCommand();
						log.setModuleName("RELEASE LINE");
						log.setIs_udf_upload("N");
						if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							DefaultLogger.debug(this, "Inside if because both are Yes "+mainScmFlag+" "+stgScmFlag);
							scmWsCall.scmWebServiceCall( xrefId,limitProfileId,limitId,custId,log);
						}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							DefaultLogger.debug(this, "Inside if because Stg is Yes "+mainScmFlag+" "+stgScmFlag);
							scmWsCall.scmWebServiceCall( xrefId,limitProfileId,limitId,custId,log);
						}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
							DefaultLogger.debug(this, "Inside if because Stg is No.Need to send once "+mainScmFlag+" "+stgScmFlag);
							scmWsCall.scmWebServiceCall( xrefId,limitProfileId,limitId,custId,log);
						}else {
							DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
						}
						}
						
					}catch(Exception e) {
						DefaultLogger.debug(this, "error in SCM webservice "+e);
					}
					DefaultLogger.debug(this, "Set Stock Month and Year in CheckerApproveLmtDetailCmd ==460==>>collateralId=>"+stockdocMonth+"....stockdocYear=>"+stockdocYear);
					xref.setStockDocMonthForLmt(stockdocMonth);
					xref.setStockDocYearForLmt(stockdocYear);


					obj.setCustomerSysXRef(xref);
					refArr[i] = obj;
				}
			}
			
			String dateFormat = "yyMMdd";
			SimpleDateFormat s=new SimpleDateFormat(dateFormat);
			String date = s.format(d);
			
			
			ILimitSysXRef[] limitSysXRefs = null!=lmtTrxObj.getLimit()?lmtTrxObj.getLimit().getLimitSysXRefs():null;
			DefaultLogger.debug(this, "171 - limitSysXRefs OBJECT DATA ========================="+limitSysXRefs);
			ILimitDAO dao1 = LimitDAOFactory.getDAO();
			
			//Prod Issue 692
			ILimitSysXRef[] limitSysXRefs2 = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
			if(null!=limitSysXRefs2){
				System.out.println("limitSysXRefs2.length=>"+limitSysXRefs2.length+"** Going for dao1.updateStageLineCheckerIdNewDetails(xref,user.getLoginID()) ");
				for (int i = 0; i < limitSysXRefs2.length; i++) {
					ILimitSysXRef obj = (ILimitSysXRef) limitSysXRefs2[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
					if(null != xref.getStatus() && !"".equals(xref.getStatus())) {
						if(xref.getStatus().startsWith("PENDING_")) {
							dao1.updateStageLineCheckerIdNewDetails(xref,user.getLoginID(),applicationDate);
						}
					}
				}
			}
			//
			
			

			if(lineId.size()>0){
				
				if (null != refArr) {
					for (int i = 0; i < refArr.length; i++) {
						for(int j=0;j<lineId.size();j++){
						  long longValue = lineId.get(j).longValue();
						if(longValue==refArr[i].getSID()){
						ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
						ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
						String tempSourceRefNo="";
						 tempSourceRefNo=""+dao1.generateSourceSeqNo();
						 HashMap countryList=dao1.getCountryCodeList();
						 int len=tempSourceRefNo.length();
						 String concatZero="";
						if(null!=tempSourceRefNo && len!=5){
							
							for(int m=5;m>len;m--){
								concatZero="0"+concatZero;
							}

						}
						tempSourceRefNo=concatZero+tempSourceRefNo;
						
						String sorceRefNo=ICMSConstant.FCUBS_CAD+date+tempSourceRefNo;	
						DefaultLogger.debug(this, "198 - sorceRefNo OBJECT DATA ========================="+sorceRefNo);
						xref.setSourceRefNo(sorceRefNo);
						xref.setStatus(ICMSConstant.FCUBS_STATUS_PENDING);
						
						if("BAHRAIN".equals(xref.getFacilitySystem()) || "HONGKONG".equals(xref.getFacilitySystem()) || "GIFTCITY".equals(xref.getFacilitySystem())
								|| "METAGRID".equals(xref.getFacilitySystem())) {
							xref.setStatus(ICMSConstant.FCUBS_STATUS_SUCCESS);
							linePrimaryId.add(String.valueOf(xref.getXRefID()));
							}
						
						xref.setSendToCore("N");
						//if actual xref is not null
						if(null!=limitSysXRefs){
							for (int k = 0; k < limitSysXRefs.length; k++) {
								//if send to file is yes i.e line needs to stp with core
								if(longValue==limitSysXRefs[k].getSID()){
									if(!(ICMSConstant.FCUBSLIMIT_ACTION_REOPEN.equals(xref.getAction()) || ICMSConstant.FCUBSLIMIT_ACTION_CLOSE.equals(xref.getAction()))){
									
								ILimitSysXRef obj2 = (ILimitSysXRef) limitSysXRefs[k];
								ICustomerSysXRef xref2 = (ICustomerSysXRef) obj2.getCustomerSysXRef();
								
								if(ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xref2.getAction())){
								if(null!=xref2.getCoreStpRejectedReason() && !xref2.getCoreStpRejectedReason().isEmpty()){
									if(null!=xref.getSegment()){
										xref.setSegment1Flag(ICMSConstant.FCUBS_ADD);
										}
										if(null!=xref.getPrioritySector() && null!=xref.getIsPrioritySector()){
										xref.setPrioritySectorFlag(ICMSConstant.FCUBS_ADD);
										}
										if(null!=xref.getIsCapitalMarketExposer()){
											xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_ADD);
											}
										if(null!=xref.getEstateType() && (null!=xref.getIsRealEstateExposer() &&"Yes".equals(xref.getIsRealEstateExposer()) )){
											xref.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
											}
										
										if("Yes".equals(xref.getIsRealEstateExposer()) && "Commercial Real estate".equals(xref.getEstateType())){
											xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
											}
										if(null!=xref.getUncondiCancl()){
											xref.setUncondiCanclFlag(ICMSConstant.FCUBS_ADD);
											}
										
										   	xref.setBranchAllowedFlag(getAddFlag(xref.getBranchAllowed()));
											xref.setProductAllowedFlag(getAddFlag(xref.getProductAllowed()));
											xref.setCurrencyAllowedFlag(getAddFlag(xref.getCurrencyAllowed()));
											xref.setLimitRestrictionFlag(getAddFlag(xref.getLimitRestriction()));
											
											//line covenant flag set to Add in the existing xref list
											ILineCovenant[] lineCovArr = xref.getLineCovenant();
											
											if(lineCovArr != null && lineCovArr.length>0 ) {
												ILineCovenant singleCov = null;
												int singleCovIndex = 0;
												
												List<String> countryRestrictionFlagList = new ArrayList<String>();
												List<String> currencyRestrictionFlagList = new ArrayList<String>();
												List<String> bankRestrictionFlagList = new ArrayList<String>();
												List<String> drawerFlagList = new ArrayList<String>();
												List<String> draweeFlagList = new ArrayList<String>();
												List<String> beneFlagList = new ArrayList<String>();
												List<String> goodsRestrictionFlagList = new ArrayList<String>();
												
												for(int l=0;l<xref.getLineCovenant().length; l++) {
													ILineCovenant cov = xref.getLineCovenant()[l];
													if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
														singleCov = cov;
														singleCovIndex = l;
													}
													else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
														beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
													}
													else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
														draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
													}
													else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
														drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
													}
													else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
															goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
													}
													else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
														bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
													}
													else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
														countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
													}
													else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
														currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
													}
												}
												
												String incoTermFlag =null;
												if(ICMSConstant.YES.equals(singleCov.getRunningAccountReqd())) {
													incoTermFlag=singleCov.getIncoTerm()+"|"+singleCov.getIncoTermMarginPercent();
												}
														
												String countryRestrictionFlag = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
														StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):null;
												String currencyRestrictionFlag =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
														StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):null;
														
												String bankRestrictionFlag =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
														StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):null;
												String drawerFlag =  !CollectionUtils.isEmpty(drawerFlagList)? 
														StringUtils.join(drawerFlagList.toArray(new String[0]),","):null;
												String draweeFlag =  !CollectionUtils.isEmpty(draweeFlagList)? 
														StringUtils.join(draweeFlagList.toArray(new String[0]),","):null;
												String beneFlag =  !CollectionUtils.isEmpty(beneFlagList)? 
														StringUtils.join(beneFlagList.toArray(new String[0]),","):null;
												String goodsRestrictionFlag =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
														StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):null;
												
												if(StringUtils.isNotBlank(countryRestrictionFlag)) {
													singleCov.setCountryRestrictionFlag(getAddFlag(countryRestrictionFlag));
												}
												if(StringUtils.isNotBlank(currencyRestrictionFlag)) {
													singleCov.setCurrencyRestrictionFlag(getAddFlag(currencyRestrictionFlag));
												}
												if(StringUtils.isNotBlank(bankRestrictionFlag)) {
													singleCov.setBankRestrictionFlag(getAddFlag(bankRestrictionFlag));
												}
												if(StringUtils.isNotBlank(drawerFlag)) {
													singleCov.setDrawerFlag(getAddFlag(drawerFlag));
												}
												if(StringUtils.isNotBlank(draweeFlag)) {
													singleCov.setDraweeFlag(getAddFlag(draweeFlag));
												}
												if(StringUtils.isNotBlank(beneFlag)) {
													singleCov.setBeneficiaryFlag(getAddFlag(beneFlag));
												}
												if(StringUtils.isNotBlank(goodsRestrictionFlag)) {
													singleCov.setGoodsRestrictionFlag(getAddFlag(goodsRestrictionFlag));
												}
												if(StringUtils.isNotBlank(incoTermFlag)) {
													singleCov.setRunningAccountFlag(getAddFlag(incoTermFlag));
												}
												
												lineCovArr[singleCovIndex] = singleCov; 
												xref.setLineCovenant(lineCovArr);
											}
											
											break;	
								}
								}else if(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY.equals(xref2.getAction())){
									DefaultLogger.debug(this, "240 - xref OBJECT DATA ========================="+xref);
									DefaultLogger.debug(this, "241 - xref2 OBJECT DATA ========================="+xref2);
									
									DefaultLogger.debug(this, "243 - xref2.getCoreStpRejectedReason OBJECT DATA ========================="+xref2.getCoreStpRejectedReason());
									DefaultLogger.debug(this, "244 - xref2.getSegment OBJECT DATA ========================="+xref2.getSegment());
									
									
									if(null!=xref2.getCoreStpRejectedReason() && !xref2.getCoreStpRejectedReason().isEmpty()){

									if(null!=xref.getSegment()){
										if(!xref.getSegment().equals(xref2.getSegment())){
											xref.setSegment1Flag(ICMSConstant.FCUBS_MODIFY);
												}
											}
									DefaultLogger.debug(this, "254 - xref2 OBJECT DATA ========================="+xref2.getIsPrioritySector());
									DefaultLogger.debug(this, "255 - xref2 OBJECT DATA ========================="+xref2.getPrioritySector());
									if(null!=xref.getIsPrioritySector()){
										if(!xref.getIsPrioritySector().equals(xref2.getIsPrioritySector())){
											xref.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
												}else if(null != xref.getPrioritySector()){
													if(!xref.getPrioritySector().equals(xref2.getPrioritySector())){
														xref.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
													}
												}
											}
									DefaultLogger.debug(this, "265 - xref2.getIsCapitalMarketExposer() OBJECT DATA ========================="+xref2.getIsCapitalMarketExposer());
									if(null!=xref.getIsCapitalMarketExposer()){
										if(!xref.getIsCapitalMarketExposer().equals(xref2.getIsCapitalMarketExposer())){
											xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_MODIFY);
												}
											}
									DefaultLogger.debug(this, "271 - xref2.getEstateType() OBJECT DATA ========================="+xref2.getEstateType());
									DefaultLogger.debug(this, "272 - xref.getIsRealEstateExposer() OBJECT DATA ========================="+xref.getIsRealEstateExposer());
									if(null!=xref.getIsRealEstateExposer()){
										if(!xref.getIsRealEstateExposer().equals(xref2.getIsRealEstateExposer())){
											DefaultLogger.debug(this, "275 - xref.getIsRealEstateExposer() OBJECT DATA ========================="+xref.getIsRealEstateExposer());
											if("No".equals(xref.getIsRealEstateExposer())){
												xref.setEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
												xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
												DefaultLogger.debug(this, "279 - No.equals(xref.getIsRealEstateExposer()) OBJECT DATA =========================");
											}else if("Yes".equals(xref.getIsRealEstateExposer())){
												xref.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
												if("Commercial Real estate".equals(xref.getEstateType())){
													xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
												}
												}
											}else{
												if("Yes".equals(xref.getIsRealEstateExposer())){
													if(null != xref.getEstateType() && !xref.getEstateType().equals(xref2.getEstateType())){
													DefaultLogger.debug(this, "289 - xref.getEstateType().equals(xref2.getEstateType() OBJECT DATA =========================");
													xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
													if("Commercial Real estate".equals(xref.getEstateType())){
														xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
													}
													else if ( "Commercial Real estate".equals(xref2.getEstateType()) &&
															null==xref.getCommRealEstateType() && null!=xref2.getCommRealEstateType()){
														xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
													}
												}else if (null != xref.getEstateType() && xref.getEstateType().equals(xref2.getEstateType())
														&& "Commercial Real estate".equals(xref.getEstateType())){
													if(null!=xref.getCommRealEstateType() && !xref.getCommRealEstateType().equals(xref2.getCommRealEstateType())){
														xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
													}
													
												}
												}
											}
									}
//									if(null!=xref.getEstateType()){
//										if(!xref.getEstateType().equals(xref2.getEstateType())){
//											xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
//												}else if (null!=xref.getCommRealEstateType()){
//													if(!xref.getCommRealEstateType().equals(xref2.getCommRealEstateType())){
//														xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
//													}
//													
//												}
//											}
									DefaultLogger.debug(this, "318 - xref2.getUncondiCancl() OBJECT DATA ========================="+xref2.getUncondiCancl());
									if(null!=xref.getUncondiCancl()){
										if(!xref.getUncondiCancl().equals(xref2.getUncondiCancl())){
											xref.setUncondiCanclFlag(ICMSConstant.FCUBS_MODIFY);
												}
											}
									xref.setBranchAllowedFlag(getModifyFlag(null==xref2.getBranchAllowed()?"":xref2.getBranchAllowed(),
											null==xref.getBranchAllowed()?"":xref.getBranchAllowed(),xref.getBranchAllowedFlag()));
									
									xref.setProductAllowedFlag(getModifyFlag(null==xref2.getProductAllowed()?"":xref2.getProductAllowed(),
											null==xref.getProductAllowed()?"":xref.getProductAllowed(),xref.getProductAllowedFlag()));
									xref.setCurrencyAllowedFlag(getModifyFlag(null==xref2.getCurrencyAllowed()?"":xref2.getCurrencyAllowed(),
											null==xref.getCurrencyAllowed()?"":xref.getCurrencyAllowed(),xref.getCurrencyAllowedFlag()));
									xref.setLimitRestrictionFlag(getModifyFlag(null==xref2.getLimitRestriction()?"":xref2.getLimitRestriction(),
											null==xref.getLimitRestriction()?"":xref.getLimitRestriction(),xref.getLimitRestrictionFlag()));
									
									//Line covenant set to Modify - when acion is modify
									//Line covenant get actual object details to compare with stage
									String countryRestrictionFlagAct="";
									String currencyRestrictionFlagAct="";
									String bankRestrictionFlagAct="";
									String drawerFlagAct="";
									String draweeFlagAct="";
									String beneFlagAct="";
									String goodsRestrictionFlagAct="";
									String incoTermFlagAct ="";
									
									ILineCovenant[] lineCovActualArr = xref2.getLineCovenant();
									if(lineCovActualArr != null && lineCovActualArr.length>0 ) {
										
										List<String> countryRestrictionFlagList = new ArrayList<String>();
										List<String> currencyRestrictionFlagList = new ArrayList<String>();
										List<String> bankRestrictionFlagList = new ArrayList<String>();
										List<String> drawerFlagList = new ArrayList<String>();
										List<String> draweeFlagList = new ArrayList<String>();
										List<String> beneFlagList = new ArrayList<String>();
										List<String> goodsRestrictionFlagList = new ArrayList<String>();
										
										for(int l=0;l<xref2.getLineCovenant().length; l++) {
											ILineCovenant cov = xref2.getLineCovenant()[l];
											if(ICMSConstant.YES.equals(cov.getBeneInd())) {
												beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
												draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
												drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
													goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
												bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
												countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
												currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
												if(ICMSConstant.YES.equals(cov.getRunningAccountReqd())) {
													incoTermFlagAct=cov.getIncoTerm()+"|"+cov.getIncoTermMarginPercent();
												}
											}
										}
										
										countryRestrictionFlagAct = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
												StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):"";
										currencyRestrictionFlagAct =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
												StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):"";
										bankRestrictionFlagAct =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
												StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):"";
										drawerFlagAct =  !CollectionUtils.isEmpty(drawerFlagList)? 
												StringUtils.join(drawerFlagList.toArray(new String[0]),","):"";
										draweeFlagAct =  !CollectionUtils.isEmpty(draweeFlagList)? 
												StringUtils.join(draweeFlagList.toArray(new String[0]),","):"";
										beneFlagAct =  !CollectionUtils.isEmpty(beneFlagList)? 
												StringUtils.join(beneFlagList.toArray(new String[0]),","):"";
										goodsRestrictionFlagAct =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
												StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):"";
									}
									
									//Line covenant - set stage object
									ILineCovenant[] lineCovStageArr = xref.getLineCovenant();
									if(lineCovStageArr != null && lineCovStageArr.length>0 ) {
										ILineCovenant singleCov = null;
										int singleCovIndex = 0;
										
										List<String> countryRestrictionFlagList = new ArrayList<String>();
										List<String> currencyRestrictionFlagList = new ArrayList<String>();
										List<String> bankRestrictionFlagList = new ArrayList<String>();
										List<String> drawerFlagList = new ArrayList<String>();
										List<String> draweeFlagList = new ArrayList<String>();
										List<String> beneFlagList = new ArrayList<String>();
										List<String> goodsRestrictionFlagList = new ArrayList<String>();
										
										for(int l=0;l<xref.getLineCovenant().length; l++) {
											ILineCovenant cov = xref.getLineCovenant()[l];
											if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
												singleCov = cov;
												singleCovIndex = l;
											}
											else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
												beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
												draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
												drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
													goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
												bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
												countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
											}
											else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
												currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
											}
										}
										
										String incoTermFlag =null;
										if(ICMSConstant.YES.equals(singleCov.getRunningAccountReqd())) {
											incoTermFlag=singleCov.getIncoTerm()+"|"+singleCov.getIncoTermMarginPercent();
										}
										
										String countryRestrictionFlag = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
												StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):null;
										String currencyRestrictionFlag =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
												StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):null;
										String bankRestrictionFlag =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
												StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):null;
										String drawerFlag =  !CollectionUtils.isEmpty(drawerFlagList)? 
												StringUtils.join(drawerFlagList.toArray(new String[0]),","):null;
										String draweeFlag =  !CollectionUtils.isEmpty(draweeFlagList)? 
												StringUtils.join(draweeFlagList.toArray(new String[0]),","):null;
										String beneFlag =  !CollectionUtils.isEmpty(beneFlagList)? 
												StringUtils.join(beneFlagList.toArray(new String[0]),","):null;
										String goodsRestrictionFlag =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
												StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):null;
										
												if(StringUtils.isNotBlank(countryRestrictionFlag)) {
													singleCov.setCountryRestrictionFlag(getFlag(countryRestrictionFlagAct,countryRestrictionFlag));
												}else if(StringUtils.isNotBlank(countryRestrictionFlagAct)){
													countryRestrictionFlag="";
													singleCov.setCountryRestrictionFlag(getFlag(countryRestrictionFlagAct,countryRestrictionFlag));
												}
												
												if(StringUtils.isNotBlank(currencyRestrictionFlag)) {
													singleCov.setCurrencyRestrictionFlag(getFlag(currencyRestrictionFlagAct,currencyRestrictionFlag));
												}else if(StringUtils.isNotBlank(currencyRestrictionFlagAct)) {
													currencyRestrictionFlag="";
													singleCov.setCurrencyRestrictionFlag(getFlag(currencyRestrictionFlagAct,currencyRestrictionFlag));
												}
												
												if(StringUtils.isNotBlank(bankRestrictionFlag)) {
													singleCov.setBankRestrictionFlag(getFlag(bankRestrictionFlagAct,bankRestrictionFlag));
												}else if(StringUtils.isNotBlank(bankRestrictionFlagAct)) {
													bankRestrictionFlag="";
													singleCov.setBankRestrictionFlag(getFlag(bankRestrictionFlagAct,bankRestrictionFlag));
												}
												
												if(StringUtils.isNotBlank(drawerFlag)) {
													singleCov.setDrawerFlag(getFlag(drawerFlagAct,drawerFlag));
												}else if(StringUtils.isNotBlank(drawerFlagAct)) {
													drawerFlag="";
													singleCov.setDrawerFlag(getFlag(drawerFlagAct,drawerFlag));
												}
												
												if(StringUtils.isNotBlank(draweeFlag)) {
													singleCov.setDraweeFlag(getFlag(draweeFlagAct,draweeFlag));
												}else if(StringUtils.isNotBlank(draweeFlagAct)) {
													draweeFlag="";
													singleCov.setDraweeFlag(getFlag(draweeFlagAct,draweeFlag));
												}
												
												if(StringUtils.isNotBlank(beneFlag)) {
													singleCov.setBeneficiaryFlag(getFlag(beneFlagAct,beneFlag));
												}else if(StringUtils.isNotBlank(beneFlagAct)) {
													beneFlag="";
													singleCov.setBeneficiaryFlag(getFlag(beneFlagAct,beneFlag));
												}
												
												if(StringUtils.isNotBlank(goodsRestrictionFlag)) {
													singleCov.setGoodsRestrictionFlag(getFlag(goodsRestrictionFlagAct,goodsRestrictionFlag));
												}else if(StringUtils.isNotBlank(goodsRestrictionFlagAct)) {
													goodsRestrictionFlag="";
													singleCov.setGoodsRestrictionFlag(getFlag(goodsRestrictionFlagAct,goodsRestrictionFlag));
												}
												
												if(StringUtils.isNotBlank(singleCov.getRunningAccountReqd())) {
													singleCov.setRunningAccountFlag(getFlag(incoTermFlagAct,incoTermFlag));
												}else if(StringUtils.isNotBlank(incoTermFlagAct)) {
													incoTermFlag="";
													singleCov.setRunningAccountFlag(getFlag(incoTermFlagAct,incoTermFlag));
												}
										
										lineCovStageArr[singleCovIndex] = singleCov; 
										xref.setLineCovenant(lineCovStageArr);
									}
									
									
									break;	
								}
								}
								else{
								if(null!=xref.getSegment()){
									if(!xref.getSegment().equals(xref2.getSegment())){
										xref.setSegment1Flag(ICMSConstant.FCUBS_MODIFY);
											}
										}
								if(null!=xref.getIsPrioritySector()){
									if(!xref.getIsPrioritySector().equals(xref2.getIsPrioritySector())){
										xref.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
											}else if(null != xref.getPrioritySector()){
												if(!xref.getPrioritySector().equals(xref2.getPrioritySector())){
													xref.setPrioritySectorFlag(ICMSConstant.FCUBS_MODIFY);
												}
											}
										}
								
								if(null!=xref.getIsCapitalMarketExposer()){
									if(!xref.getIsCapitalMarketExposer().equals(xref2.getIsCapitalMarketExposer())){
										xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_MODIFY);
											}
										}
								
								if(null!=xref.getIsRealEstateExposer()){
									if(!xref.getIsRealEstateExposer().equals(xref2.getIsRealEstateExposer())){
										if("No".equals(xref.getIsRealEstateExposer())){
											xref.setEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
											xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
										}else if("Yes".equals(xref.getIsRealEstateExposer())){
											xref.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
											if("Commercial Real estate".equals(xref.getEstateType())){
												xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
											}
											}
										}else{
											if("Yes".equals(xref.getIsRealEstateExposer())){
												if(null != xref.getEstateType() && !xref.getEstateType().equals(xref2.getEstateType())){
												xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
												if("Commercial Real estate".equals(xref.getEstateType())){
													xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
												}
											else if ( "Commercial Real estate".equals(xref2.getEstateType()) &&
												null==xref.getCommRealEstateType() && null!=xref2.getCommRealEstateType()){
													xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_DELETE);
												}
											}else if (null != xref.getEstateType() && xref.getEstateType().equals(xref2.getEstateType())
													&& "Commercial Real estate".equals(xref.getEstateType())){
												if(null!=xref.getCommRealEstateType() && !xref.getCommRealEstateType().equals(xref2.getCommRealEstateType())){
													xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
												}
												
											}
											}
										}
								}
								
//								if(null!=xref.getEstateType()){
//									if(!xref.getEstateType().equals(xref2.getEstateType())){
//										xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
//											}else if (null!=xref.getCommRealEstateType()){
//												if(!xref.getCommRealEstateType().equals(xref2.getCommRealEstateType())){
//													xref.setEstateTypeFlag(ICMSConstant.FCUBS_MODIFY);
//												}
//												
//											}
//										}
								if(null!=xref.getUncondiCancl()){
									if(!xref.getUncondiCancl().equals(xref2.getUncondiCancl())){
										xref.setUncondiCanclFlag(ICMSConstant.FCUBS_MODIFY);
											}
										}
								
								xref.setBranchAllowedFlag(getFlag(null==xref2.getBranchAllowed()?"":xref2.getBranchAllowed(),
										null==xref.getBranchAllowed()?"":xref.getBranchAllowed()));
								
								xref.setProductAllowedFlag(getFlag(null==xref2.getProductAllowed()?"":xref2.getProductAllowed(),
										null==xref.getProductAllowed()?"":xref.getProductAllowed()));
								xref.setCurrencyAllowedFlag(getFlag(null==xref2.getCurrencyAllowed()?"":xref2.getCurrencyAllowed(),
										null==xref.getCurrencyAllowed()?"":xref.getCurrencyAllowed()));
								xref.setLimitRestrictionFlag(getFlag(null==xref2.getLimitRestriction()?"":xref2.getLimitRestriction(),
										null==xref.getLimitRestriction()?"":xref.getLimitRestriction()));
								
								//Line covenant set to Modify - when action is other than new and modify
								//Line covenant - get actual object details to compare with stage
								String countryRestrictionFlagAct="";
								String currencyRestrictionFlagAct="";
								String bankRestrictionFlagAct="";
								String drawerFlagAct="";
								String draweeFlagAct="";
								String beneFlagAct="";
								String goodsRestrictionFlagAct="";
								String incoTermFlagAct ="";
								
								ILineCovenant[] lineCovActualArr = xref2.getLineCovenant();
								if(lineCovActualArr != null && lineCovActualArr.length>0 ) {
									
									List<String> countryRestrictionFlagList = new ArrayList<String>();
									List<String> currencyRestrictionFlagList = new ArrayList<String>();
									List<String> bankRestrictionFlagList = new ArrayList<String>();
									List<String> drawerFlagList = new ArrayList<String>();
									List<String> draweeFlagList = new ArrayList<String>();
									List<String> beneFlagList = new ArrayList<String>();
									List<String> goodsRestrictionFlagList = new ArrayList<String>();
									
									for(int l=0;l<xref2.getLineCovenant().length; l++) {
										ILineCovenant cov = xref2.getLineCovenant()[l];
										if(ICMSConstant.YES.equals(cov.getBeneInd())) {
											beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
											draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
											drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
												goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
											bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
											countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
											currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
											if(ICMSConstant.YES.equals(cov.getRunningAccountReqd())) {
												incoTermFlagAct=cov.getIncoTerm()+"|"+cov.getIncoTermMarginPercent();
											}
										}
									}
									
									countryRestrictionFlagAct = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
											StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):"";
									currencyRestrictionFlagAct =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
											StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):"";
									bankRestrictionFlagAct =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
											StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):"";
									drawerFlagAct =  !CollectionUtils.isEmpty(drawerFlagList)? 
											StringUtils.join(drawerFlagList.toArray(new String[0]),","):"";
									draweeFlagAct =  !CollectionUtils.isEmpty(draweeFlagList)? 
											StringUtils.join(draweeFlagList.toArray(new String[0]),","):"";
									beneFlagAct =  !CollectionUtils.isEmpty(beneFlagList)? 
											StringUtils.join(beneFlagList.toArray(new String[0]),","):"";
									goodsRestrictionFlagAct =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
											StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):"";
								}
								
								//Line covenant - set stage object
								ILineCovenant[] lineCovStageArr = xref.getLineCovenant();
								if(lineCovStageArr != null && lineCovStageArr.length>0 ) {
									ILineCovenant singleCov = null;
									int singleCovIndex = 0;
									
									List<String> countryRestrictionFlagList = new ArrayList<String>();
									List<String> currencyRestrictionFlagList = new ArrayList<String>();
									List<String> bankRestrictionFlagList = new ArrayList<String>();
									List<String> drawerFlagList = new ArrayList<String>();
									List<String> draweeFlagList = new ArrayList<String>();
									List<String> beneFlagList = new ArrayList<String>();
									List<String> goodsRestrictionFlagList = new ArrayList<String>();
									
									for(int l=0;l<xref.getLineCovenant().length; l++) {
										ILineCovenant cov = xref.getLineCovenant()[l];
										if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
											singleCov = cov;
											singleCovIndex = l;
										}
										else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
											beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
											draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
											drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
												goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
											bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
											countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
											currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
										}
									}
									
									String incoTermFlag =null;
									if(ICMSConstant.YES.equals(singleCov.getRunningAccountReqd())) {
										incoTermFlag=singleCov.getIncoTerm()+"|"+singleCov.getIncoTermMarginPercent();
									}
									
									String countryRestrictionFlag = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
											StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):null;
									String currencyRestrictionFlag =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
											StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):null;
									String bankRestrictionFlag =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
											StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):null;
									String drawerFlag =  !CollectionUtils.isEmpty(drawerFlagList)? 
											StringUtils.join(drawerFlagList.toArray(new String[0]),","):null;
									String draweeFlag =  !CollectionUtils.isEmpty(draweeFlagList)? 
											StringUtils.join(draweeFlagList.toArray(new String[0]),","):null;
									String beneFlag =  !CollectionUtils.isEmpty(beneFlagList)? 
											StringUtils.join(beneFlagList.toArray(new String[0]),","):null;
									String goodsRestrictionFlag =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
											StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):null;
									
									if(StringUtils.isNotBlank(countryRestrictionFlag)) {
										singleCov.setCountryRestrictionFlag(getFlag(countryRestrictionFlagAct,countryRestrictionFlag));
									}else if(StringUtils.isNotBlank(countryRestrictionFlagAct)){
										countryRestrictionFlag="";
										singleCov.setCountryRestrictionFlag(getFlag(countryRestrictionFlagAct,countryRestrictionFlag));
									}
									
									if(StringUtils.isNotBlank(currencyRestrictionFlag)) {
										singleCov.setCurrencyRestrictionFlag(getFlag(currencyRestrictionFlagAct,currencyRestrictionFlag));
									}else if(StringUtils.isNotBlank(currencyRestrictionFlagAct)) {
										currencyRestrictionFlag="";
										singleCov.setCurrencyRestrictionFlag(getFlag(currencyRestrictionFlagAct,currencyRestrictionFlag));
									}
									
									if(StringUtils.isNotBlank(bankRestrictionFlag)) {
										singleCov.setBankRestrictionFlag(getFlag(bankRestrictionFlagAct,bankRestrictionFlag));
									}else if(StringUtils.isNotBlank(bankRestrictionFlagAct)) {
										bankRestrictionFlag="";
										singleCov.setBankRestrictionFlag(getFlag(bankRestrictionFlagAct,bankRestrictionFlag));
									}
									
									if(StringUtils.isNotBlank(drawerFlag)) {
										singleCov.setDrawerFlag(getFlag(drawerFlagAct,drawerFlag));
									}else if(StringUtils.isNotBlank(drawerFlagAct)) {
										drawerFlag="";
										singleCov.setDrawerFlag(getFlag(drawerFlagAct,drawerFlag));
									}
									
									if(StringUtils.isNotBlank(draweeFlag)) {
										singleCov.setDraweeFlag(getFlag(draweeFlagAct,draweeFlag));
									}else if(StringUtils.isNotBlank(draweeFlagAct)) {
										draweeFlag="";
										singleCov.setDraweeFlag(getFlag(draweeFlagAct,draweeFlag));
									}
									
									if(StringUtils.isNotBlank(beneFlag)) {
										singleCov.setBeneficiaryFlag(getFlag(beneFlagAct,beneFlag));
									}else if(StringUtils.isNotBlank(beneFlagAct)) {
										beneFlag="";
										singleCov.setBeneficiaryFlag(getFlag(beneFlagAct,beneFlag));
									}
									
									if(StringUtils.isNotBlank(goodsRestrictionFlag)) {
										singleCov.setGoodsRestrictionFlag(getFlag(goodsRestrictionFlagAct,goodsRestrictionFlag));
									}else if(StringUtils.isNotBlank(goodsRestrictionFlagAct)) {
										goodsRestrictionFlag="";
										singleCov.setGoodsRestrictionFlag(getFlag(goodsRestrictionFlagAct,goodsRestrictionFlag));
									}
									
									if(StringUtils.isNotBlank(singleCov.getRunningAccountReqd())) {
										singleCov.setRunningAccountFlag(getFlag(incoTermFlagAct,incoTermFlag));
									}else if(StringUtils.isNotBlank(incoTermFlagAct)) {
										incoTermFlag="";
										singleCov.setRunningAccountFlag(getFlag(incoTermFlagAct,incoTermFlag));
									}
									
									lineCovStageArr[singleCovIndex] = singleCov; 
									xref.setLineCovenant(lineCovStageArr);
								}
								
								break;
								}
									}
							}else if(k==(limitSysXRefs.length-1)){
								//lines not to send with core
								if(null!=xref.getSegment()){
								xref.setSegment1Flag(ICMSConstant.FCUBS_ADD);
								}
								if(null!=xref.getPrioritySector() && null!=xref.getIsPrioritySector()){
								xref.setPrioritySectorFlag(ICMSConstant.FCUBS_ADD);
								}
								if(null!=xref.getIsCapitalMarketExposer()){
									xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_ADD);
									}
								if(null!=xref.getEstateType() && (null!=xref.getIsRealEstateExposer() &&"Yes".equals(xref.getIsRealEstateExposer()) )){
									xref.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
									}
								
								if("Yes".equals(xref.getIsRealEstateExposer()) && "Commercial Real estate".equals(xref.getEstateType())){
									xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
									}
								
								if(null!=xref.getUncondiCancl()){
									xref.setUncondiCanclFlag(ICMSConstant.FCUBS_ADD);
									}
								
								if(null!=xref.getBranchAllowed()){
									xref.setBranchAllowedFlag(getFlag("",xref.getBranchAllowed()));
									}
								if(null!=xref.getProductAllowed()){
									xref.setProductAllowedFlag(getFlag("",xref.getProductAllowed()));
									}
								if(null!=xref.getCurrencyAllowed()){
									xref.setCurrencyAllowedFlag(getFlag("",xref.getCurrencyAllowed()));
									}
								if(null!=xref.getLimitRestriction()){
									xref.setLimitRestrictionFlag(getFlag("",xref.getLimitRestriction()));
									}
								
								//line covenant changes - lines not send to core
								ILineCovenant[] lineCovArr = xref.getLineCovenant();
								
								if(lineCovArr != null && lineCovArr.length>0 ) {
									ILineCovenant singleCov = null;
									int singleCovIndex = 0;
									
									List<String> countryRestrictionFlagList = new ArrayList<String>();
									List<String> currencyRestrictionFlagList = new ArrayList<String>();
									List<String> bankRestrictionFlagList = new ArrayList<String>();
									List<String> drawerFlagList = new ArrayList<String>();
									List<String> draweeFlagList = new ArrayList<String>();
									List<String> beneFlagList = new ArrayList<String>();
									List<String> goodsRestrictionFlagList = new ArrayList<String>();
									
									for(int l=0;l<xref.getLineCovenant().length; l++) {
										ILineCovenant cov = xref.getLineCovenant()[l];
										if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
											singleCov = cov;
											singleCovIndex = l;
										}
										else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
											beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
											draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
											drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
												goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
											bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
											countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
										}
										else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
											currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
										}
									}
									
									String incoTermFlag =null;
									if(ICMSConstant.YES.equals(singleCov.getRunningAccountReqd())) {
										incoTermFlag=singleCov.getIncoTerm()+"|"+singleCov.getIncoTermMarginPercent();
									}
									
									String countryRestrictionFlag = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
											StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):null;
									String currencyRestrictionFlag =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
											StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):null;
									String bankRestrictionFlag =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
											StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):null;
									String drawerFlag =  !CollectionUtils.isEmpty(drawerFlagList)? 
											StringUtils.join(drawerFlagList.toArray(new String[0]),","):null;
									String draweeFlag =  !CollectionUtils.isEmpty(draweeFlagList)? 
											StringUtils.join(draweeFlagList.toArray(new String[0]),","):null;
									String beneFlag =  !CollectionUtils.isEmpty(beneFlagList)? 
											StringUtils.join(beneFlagList.toArray(new String[0]),","):null;
									String goodsRestrictionFlag =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
											StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):null;
									
									if(StringUtils.isNotBlank(countryRestrictionFlag)) {
										singleCov.setCountryRestrictionFlag(getFlag("",countryRestrictionFlag));
									}
									if(StringUtils.isNotBlank(currencyRestrictionFlag)) {
										singleCov.setCurrencyRestrictionFlag(getFlag("",currencyRestrictionFlag));
									}
									if(StringUtils.isNotBlank(bankRestrictionFlag)) {
										singleCov.setBankRestrictionFlag(getFlag("",bankRestrictionFlag));
									}
									if(StringUtils.isNotBlank(drawerFlag)) {
										singleCov.setDrawerFlag(getFlag("",drawerFlag));
									}
									if(StringUtils.isNotBlank(draweeFlag)) {
										singleCov.setDraweeFlag(getFlag("",draweeFlag));
									}
									if(StringUtils.isNotBlank(beneFlag)) {
										singleCov.setBeneficiaryFlag(getFlag("",beneFlag));
									}
									if(StringUtils.isNotBlank(goodsRestrictionFlag)) {
										singleCov.setGoodsRestrictionFlag(getFlag("",goodsRestrictionFlag));
									}
									if(StringUtils.isNotBlank(singleCov.getRunningAccountReqd())) {
										singleCov.setRunningAccountFlag(getFlag("",incoTermFlag));
									}
									
									lineCovArr[singleCovIndex] = singleCov; 
									xref.setLineCovenant(lineCovArr);
								}
								
								
								break;
							}
							}
						}else{
							//if actual xref is null
							if(null!=xref.getSegment()){
							xref.setSegment1Flag(ICMSConstant.FCUBS_ADD);
							}
							if(null!=xref.getPrioritySector() && null!=xref.getIsPrioritySector()){
							xref.setPrioritySectorFlag(ICMSConstant.FCUBS_ADD);
							}
							if(null!=xref.getIsCapitalMarketExposer()){
								xref.setIsCapitalMarketExposerFlag(ICMSConstant.FCUBS_ADD);
								}
							if(null!=xref.getEstateType() && (null!=xref.getIsRealEstateExposer() &&"Yes".equals(xref.getIsRealEstateExposer()) )){
								xref.setEstateTypeFlag(ICMSConstant.FCUBS_ADD);
								}
							
							if("Yes".equals(xref.getIsRealEstateExposer()) && "Commercial Real estate".equals(xref.getEstateType())){
								xref.setCommRealEstateTypeFlag(ICMSConstant.FCUBS_ADD);
								}
							
							if(null!=xref.getUncondiCancl()){
								xref.setUncondiCanclFlag(ICMSConstant.FCUBS_ADD);
								}
							
							if(null!=xref.getBranchAllowed()){
								xref.setBranchAllowedFlag(getFlag("",xref.getBranchAllowed()));
								}
							if(null!=xref.getProductAllowed()){
								xref.setProductAllowedFlag(getFlag("",xref.getProductAllowed()));
								}
							if(null!=xref.getCurrencyAllowed()){
								xref.setCurrencyAllowedFlag(getFlag("",xref.getCurrencyAllowed()));
								}
							if(null!=xref.getLimitRestriction()){
								xref.setLimitRestrictionFlag(getFlag("",xref.getLimitRestriction()));
								}

							
							//line covenant changes - actual xref is null i.e. this is the 1st line to add
							ILineCovenant[] lineCovArr = xref.getLineCovenant();
							
							if(lineCovArr != null && lineCovArr.length>0 ) {
								ILineCovenant singleCov = null;
								int singleCovIndex = 0;
								
								List<String> countryRestrictionFlagList = new ArrayList<String>();
								List<String> currencyRestrictionFlagList = new ArrayList<String>();
								List<String> bankRestrictionFlagList = new ArrayList<String>();
								List<String> drawerFlagList = new ArrayList<String>();
								List<String> draweeFlagList = new ArrayList<String>();
								List<String> beneFlagList = new ArrayList<String>();
								List<String> goodsRestrictionFlagList = new ArrayList<String>();
								
								for(int k=0;k<xref.getLineCovenant().length; k++) {
									ILineCovenant cov = xref.getLineCovenant()[k];
									if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
										singleCov = cov;
										singleCovIndex = k;
									}
									else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
										beneFlagList.add(cov.getBeneCustId()+"|"+cov.getBeneAmount());
									}
									else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
										draweeFlagList.add(cov.getDraweeCustId()+"|"+cov.getDraweeAmount());
									}
									else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
										drawerFlagList.add(cov.getDrawerCustId()+"|"+cov.getDrawerAmount());
									}
									else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
											goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
										bankRestrictionFlagList.add(cov.getRestrictedBank()+"|"+cov.getRestrictedBankAmount());
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
										countryRestrictionFlagList.add(countryList.get(cov.getRestrictedCountryname())+"|"+cov.getRestrictedAmount());
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
										currencyRestrictionFlagList.add(cov.getRestrictedCurrency()+"|"+cov.getRestrictedCurrencyAmount());
									}
								}
								
								String incoTermFlag =null;
								if(ICMSConstant.YES.equals(singleCov.getRunningAccountReqd())) {
									incoTermFlag=singleCov.getIncoTerm()+"|"+singleCov.getIncoTermMarginPercent();
								}
								
								String countryRestrictionFlag = !CollectionUtils.isEmpty(countryRestrictionFlagList)? 
										StringUtils.join(countryRestrictionFlagList.toArray(new String[0]),","):null;
								String currencyRestrictionFlag =  !CollectionUtils.isEmpty(currencyRestrictionFlagList)?
										StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]),","):null;
								String bankRestrictionFlag =  !CollectionUtils.isEmpty(bankRestrictionFlagList)?
										StringUtils.join(bankRestrictionFlagList.toArray(new String[0]),","):null;
								String drawerFlag =  !CollectionUtils.isEmpty(drawerFlagList)? 
										StringUtils.join(drawerFlagList.toArray(new String[0]),","):null;
								String draweeFlag =  !CollectionUtils.isEmpty(draweeFlagList)? 
										StringUtils.join(draweeFlagList.toArray(new String[0]),","):null;
								String beneFlag =  !CollectionUtils.isEmpty(beneFlagList)? 
										StringUtils.join(beneFlagList.toArray(new String[0]),","):null;
								String goodsRestrictionFlag =  !CollectionUtils.isEmpty(goodsRestrictionFlagList)?
										StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]),","):null;
								
								if(StringUtils.isNotBlank(countryRestrictionFlag)) {
									singleCov.setCountryRestrictionFlag(getFlag("",countryRestrictionFlag));
								}
								if(StringUtils.isNotBlank(currencyRestrictionFlag)) {
									singleCov.setCurrencyRestrictionFlag(getFlag("",currencyRestrictionFlag));
								}
								if(StringUtils.isNotBlank(bankRestrictionFlag)) {
									singleCov.setBankRestrictionFlag(getFlag("",bankRestrictionFlag));
								}
								if(StringUtils.isNotBlank(drawerFlag)) {
									singleCov.setDrawerFlag(getFlag("",drawerFlag));
								}
								if(StringUtils.isNotBlank(draweeFlag)) {
									singleCov.setDraweeFlag(getFlag("",draweeFlag));
								}
								if(StringUtils.isNotBlank(beneFlag)) {
									singleCov.setBeneficiaryFlag(getFlag("",beneFlag));
								}
								if(StringUtils.isNotBlank(goodsRestrictionFlag)) {
									singleCov.setGoodsRestrictionFlag(getFlag("",goodsRestrictionFlag));
								}
								if(StringUtils.isNotBlank(singleCov.getRunningAccountReqd())) {
									singleCov.setRunningAccountFlag(getFlag("",incoTermFlag));
								}
								
								lineCovArr[singleCovIndex] = singleCov; 
								xref.setLineCovenant(lineCovArr);
							}
						
						}
						obj.setCustomerSysXRef(xref);
						refArr[i] = obj;
						}
					}
					}
				}
			}
			DefaultLogger.debug(this, "499 OBJECT DATA ============ before method - updateLineDetForNotToSend");
			updateLineDetForNotToSend(lineIdNotToSend,refArr,dao1,date);
			//Update the flags and source reference No and status in staging:
			DefaultLogger.debug(this, "4502 OBJECT DATA ============ before method - updateUpdateStatusLine");
			updateUpdateStatusLine(lineIdUpdateStatus,refArr,dao1,date);
			updateUpdateStatusLine2(lineIdUpdateStatus,refArr,dao1,date);
			DefaultLogger.debug(this, "in CheckerApproveLmtDetailCmd ==120==>>applicationDate=>"+applicationDate);
			lmtTrxObj.getStagingLimit().setLimitSysXRefs(refArr);
			
			//Covenant update
			
			
			helper.setTrxLocation(ctx, lmtTrxObj.getStagingLimit());
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			//added by santosh
			updateUdfFlags(lmtTrxObj);
			//end santosh
			
		    updateUdfFlags_2(lmtTrxObj);
			
			ICMSTrxResult res =null;
			
			//Start SCOD Deferral
			String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
			System.out.println("scodLineNoList......."+scodLineNoList);
			Date scodStage = lmtTrxObj.getStagingLimit().getScodDate();
			boolean isEquals=true;
			try {
				SimpleDateFormat sdformat = new SimpleDateFormat("dd/MMM/yyyy");
				if(lmtTrxObj.getLimit()!=null && lmtTrxObj.getLimit().getScodDate()!=null) {
					Date actual = sdformat.parse(sdformat.format(lmtTrxObj.getLimit().getScodDate()));
					Date stage = sdformat.parse(sdformat.format(lmtTrxObj.getStagingLimit().getScodDate()));	
					if(!(stage.compareTo(actual) > 0)) {
						isEquals=false;
					}
				}
			}catch(Exception ex) {
				System.out.println("Exception......."+ex);
			}
			System.out.println("isEquals......."+isEquals);
			
			boolean scodB = false;
			if(scodLineNoList != null && !scodLineNoList.equals("")) {
			String[] scodlinelist = scodLineNoList.split(",");
			for(int i=0; i< scodlinelist.length ; i++ ) {
				if(lmtTrxObj.getStagingLimit().getLineNo() !=null && lmtTrxObj.getStagingLimit().getLineNo().equals(scodlinelist[i])
						&& !lmtTrxObj.getStagingLimit().getLineNo().isEmpty() && scodStage!=null) {
					scodB = true;
				}
			}
			}
			if(!(lmtTrxObj.getStagingLimit().getLineNo()==null || lmtTrxObj.getStagingLimit().getLineNo().isEmpty()) 
					&& scodB 
					&& isEquals) {
				
				System.out.println("Before calling generateDeferralsForSCOD.......");
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime(scodStage);
				cal2.setTime(new Date());
				
				ICheckListProxyManager checklistProxyManager = CheckListProxyManagerFactory.getCheckListProxyManager();
				ICheckListTemplateProxyManager checklistTemplateProxyManager = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
				ILimit lmt=lmtTrxObj.getStagingLimit();
				//long limitID = getReferenceId(lmtTrxObj.getTransactionID());
				String secType = lmt.getFacilityCode();  //FAC0000682 for facility id=20200311000004470 (i.e. facility code in facility master) 
				String secSubType = lmt.getFacilityCode(); //FAC0000682 for facility id=20200311000004470 (i.e. facility code in facility master)
				//long limitProfileID = limit.getLimitProfileID();
				ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(lmtTrxObj.getStagingLimit().getLimitProfileID(), lmtTrxObj.getStagingLimit().getLimitID());
				owner.setCollateralID(lmtTrxObj.getStagingLimit().getLimitID());
				owner.setLimitProfileID(lmtTrxObj.getStagingLimit().getLimitProfileID());
				ICheckList checkList = null;
				checkList = checklistProxyManager.getDefaultFacilityCheckList(owner,"IN", secType, secSubType, "", "", "");
				
				
				
				if(checkList == null) {
					exceptionMap.put("limitRemarksError", new ActionMessage("error.facDocError.remark"));
				}else {
					 res = proxy.checkerApproveLmtTrx(ctx, lmtTrxObj);
				if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
					if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
							&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=2 && "Initial".equals(limit.getCamType())) {
						GenerateDeferralsForSCOD.generateDeferralsForSCOD(ctx, lmtTrxObj, limit, new Date(), res);
					} else if(cal2.get(Calendar.MONTH) <= cal1.get(Calendar.MONTH) 
							&& (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH))<=1 
							&& ("Interim".equals(limit.getCamType()) || "Annual".equals(limit.getCamType()))) {
						GenerateDeferralsForSCOD.generateDeferralsForSCOD(ctx, lmtTrxObj, limit, new Date(), res);
					} 
				} 
				System.out.println("After calling generateDeferralsForSCOD.......");
			}
			}else {
				 dao1.clearMainCovenant(lmtTrxObj.getReferenceID());
				 res = proxy.checkerApproveLmtTrx(ctx, lmtTrxObj);
			}
			//End SCOD Deferral
			
			if(res != null) {
				
				if(obLimitsOfAuthorityMasterTrxLog != null) {
					obLimitsOfAuthorityMasterTrxLog = LimitsOfAuthorityMasterHelper.prepareObLimitsOfAuthorityMasterTrxLogLimits(res, obLimitsOfAuthorityMasterTrxLog);
					LimitsOfAuthorityMasterHelper.logLimitsOfAuthorityTrxData(obLimitsOfAuthorityMasterTrxLog);
				}
			
			if(successSourceRefNo.endsWith(",")){
				successSourceRefNo=successSourceRefNo.substring(0, successSourceRefNo.length()-1);
			}
			
			if(rejectedSourceRefNo.endsWith(",")){
				rejectedSourceRefNo=rejectedSourceRefNo.substring(0, rejectedSourceRefNo.length()-1);
			}
			
			String system = lmtTrxObj.getStagingLimit().getFacilitySystem();
			if(!successSourceRefNo.isEmpty()){
				if("ET".equals(system)){
					Map<String, String> successPSRMap = dao1.getSuccessPSRMap(successSourceRefNo);
					if(successPSRMap.size()>0){
						dao1.updatePSRLineDetails(successSourceRefNo,successPSRMap,ICMSConstant.FCUBS_STATUS_SUCCESS);
					}
				}else{
					Map<String, String> successFcubsMap = dao1.getSuccessFcubsMap(successSourceRefNo);
					if(successFcubsMap.size()>0){
						dao1.updateLineDetails(successSourceRefNo,successFcubsMap,ICMSConstant.FCUBS_STATUS_SUCCESS);
						dao1.clearUDFFields(successSourceRefNo);
					}
				}
			}
			
			if(!rejectedSourceRefNo.isEmpty()){
				if("ET".equals(system)){
					Map<String, String> rejectPSRMap = dao1.getRejectedPSRMap(rejectedSourceRefNo);
					if(rejectPSRMap.size()>0){
						dao1.updatePSRLineDetails(rejectedSourceRefNo,rejectPSRMap,ICMSConstant.FCUBS_STATUS_REJECTED);
						}
					}else{				
						Map<String, String> rejectFcubsMap = dao1.getRejectedFcubsMap(rejectedSourceRefNo);
						if(rejectFcubsMap.size()>0){
							dao1.updateLineDetails(rejectedSourceRefNo,rejectFcubsMap,ICMSConstant.FCUBS_STATUS_REJECTED);
						}
					}
			}
//			ICollateralAllocation[] cols = lmtTrxObj.getStagingLimit().getCollateralAllocations();
//			long colId = cols[0].getCollateral().getCollateralID();
//			String colSubtype = cols[0].getCollateral().getSCISubTypeValue();
			//Update the flags and source reference No and status in staging:
//			ILimitSysXRef[] limitSysXRefs2 = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
			if(null!=limitSysXRefs2){
				for (int i = 0; i < limitSysXRefs2.length; i++) {
					for(int j=0;j<lineId.size();j++){
					  long longValue = lineId.get(j).longValue();
					if(longValue==limitSysXRefs2[i].getSID()){
					ILimitSysXRef obj = (ILimitSysXRef) limitSysXRefs2[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
						dao1.updateStageLineDetails(xref.getSourceRefNo(),xref.getSegment1Flag(),xref.getPrioritySectorFlag(),xref.getEstateTypeFlag(),xref.getIsCapitalMarketExposerFlag(),
								xref.getUncondiCanclFlag(),String.valueOf(xref.getXRefID()),xref.getBranchAllowedFlag(),xref.getProductAllowedFlag(),xref.getCurrencyAllowedFlag(),xref.getLimitRestrictionFlag(),xref.getSendToCore() ,xref.getCommRealEstateTypeFlag());
					}
					}
				}
			}
			
			if(null!=limitSysXRefs2){
				for (int i = 0; i < limitSysXRefs2.length; i++) {
					for(int j=0;j<lineIdPending.size();j++){
					  long longValue = lineIdPending.get(j).longValue();
					if(longValue==limitSysXRefs2[i].getSID()){
					ILimitSysXRef obj = (ILimitSysXRef) limitSysXRefs2[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
						dao1.updateStageSendToCore(String.valueOf(xref.getXRefID()),xref.getSendToCore());
					}
					}
				}
			}
			
			if(null!=limitSysXRefs2){
				for (int i = 0; i < limitSysXRefs2.length; i++) {
					for(int j=0;j<lineIdNotToSend.size();j++){
					  long longValue = lineIdNotToSend.get(j).longValue();
					if(longValue==limitSysXRefs2[i].getSID()){
					ILimitSysXRef obj = (ILimitSysXRef) limitSysXRefs2[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
					dao1.updateStageLineDetails(xref.getSourceRefNo(),xref.getSegment1Flag(),xref.getPrioritySectorFlag(),xref.getEstateTypeFlag(),xref.getIsCapitalMarketExposerFlag(),
							xref.getUncondiCanclFlag(),String.valueOf(xref.getXRefID()),xref.getBranchAllowedFlag(),xref.getProductAllowedFlag(),xref.getCurrencyAllowedFlag(),xref.getLimitRestrictionFlag(),
							xref.getSendToCore(),xref.getStatus(),xref.getAction(),xref.getCoreStpRejectedReason(),xref.getUdfDelete(),xref.getCommRealEstateTypeFlag(),xref.getUdfDelete_2());
					}
					}
				}
			}
			
			if(null!=limitSysXRefs2){
				for (int i = 0; i < limitSysXRefs2.length; i++) {
					for(int j=0;j<lineIdUpdateStatus.size();j++){
					  long longValue = lineIdUpdateStatus.get(j).longValue();
					if(longValue==limitSysXRefs2[i].getSID()){
					ILimitSysXRef obj = (ILimitSysXRef) limitSysXRefs2[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
					dao1.updateStageLineDetails(xref.getSourceRefNo(),xref.getSegment1Flag(),xref.getPrioritySectorFlag(),xref.getEstateTypeFlag(),xref.getIsCapitalMarketExposerFlag(),
							xref.getUncondiCanclFlag(),String.valueOf(xref.getXRefID()),xref.getBranchAllowedFlag(),xref.getProductAllowedFlag(),xref.getCurrencyAllowedFlag(),xref.getLimitRestrictionFlag(),
							xref.getSendToCore(),xref.getStatus(),xref.getAction(),xref.getCoreStpRejectedReason(),xref.getUdfDelete(),xref.getCommRealEstateTypeFlag(),xref.getUdfDelete_2());
					
					dao1.updateFCUBSDataLogDuringCoreDown(xref.getSourceRefNo(),xref.getSerialNo(),xref.getCoreStpRejectedReason(),xref.getStatus());
					}
					}
				}
			}
			
			generateRecurrentCheckList(ctx, lmtTrxObj);
			generateInterestRateNotification(lmtTrxObj);
			
			//Uma:Start:Prod issue: To add masterIdlist for facility checklist
			if("PENDING_UPDATE".equals(lmtTrxObj.getStatus())){
			if(null!= lmtTrxObj.getReferenceID()){
				String facilityCode = lmtTrxObj.getStagingLimit().getFacilityCode();
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				String masterListId = dao.getMasterListId(facilityCode);
				if(null!=masterListId){
				 dao.updateCheckListMasterlistId(masterListId,lmtTrxObj.getReferenceID());
				}
			   }
			}
			//Uma:End:Prod issue: To add masterIdlist for facility checklist
			//Start Santosh UBS_LIMIT UPLOAD
			/*if(null!=lmtTrxObj.getLimit() && null!=lmtTrxObj.getStagingLimit())
			{
				String oldCurrency=lmtTrxObj.getLimit().getCurrencyCode();
				String newCurrency=lmtTrxObj.getStagingLimit().getCurrencyCode();
			
				if(!oldCurrency.equals(newCurrency)) {
					try {
						//updateLineCurrency(lmtTrxObj.getLimitProfileID(),newCurrency);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}*/
			//End Santosh UBS_LIMIT UPLOAD
			}
			
			DefaultLogger.debug(this, "Before checking collateralIdForDelete is empty=>"+collateralIdForDelete);
			System.out.println("Before checking collateralIdForDelete is empty=>"+collateralIdForDelete);
			// for general charge security delete = change facility line stockdocmonth and stockdocyear to empty
			if(!"".equals(collateralIdForDelete)) {
				System.out.println("Inside If Condition Security Delete General Charge => CheckerApproveLmtDetailCmd.java=> Security Delink from Facility.=>collateralIdForDelete=>"+collateralIdForDelete+" **facilityLmtId=>"+facilityLmtId);
				DefaultLogger.debug(this, "Inside If Condition Security Delete General Charge => CheckerApproveLmtDetailCmd.java=> Security Delink from Facility.=>collateralIdForDelete=>"+collateralIdForDelete+" **facilityLmtId=>"+facilityLmtId);
				FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
//				xrefIdList = collateralDAO.getXrefIdList(collateralIdForDeleteLong);
				
				xrefIdList = collateralDAO.getXrefIdListFromLmtId(facilityLmtId);
				System.out.println("00000000********** xrefIdList=>"+xrefIdList);
				DefaultLogger.debug(this, "00000000********** xrefIdList=>"+xrefIdList);

				ArrayList sourceRefNoList = new ArrayList();
				
				stockdocMonth = "";
				stockdocYear = "";
				if(xrefIdList != null ) {
					
					for(int i=0;i<xrefIdList.size();i++) {
						String sourceRefNo=fileUpload.generateSourceRefNo();
						sourceRefNoList.add(sourceRefNo);
					}
					DefaultLogger.debug(this, "Going For updateStatusSuccessToPendingThroughFacility =>stockdocMonth: "+stockdocMonth+"...stockdocYear: "+stockdocYear);
				collateralDAO.updateStatusSuccessToPendingThroughFacility(xrefIdList,sourceRefNoList,stockdocMonth,stockdocYear);
				collateralDAO.updateStagingStatusSuccessToPendingThroughFacility(xrefIdList,sourceRefNoList,stockdocMonth,stockdocYear);
				System.out.println("Complete update status from SUCCESS to PENDING in table SCI_LSP_SYS_XREF.CheckerApproveLmtDetailCmd.java at Security Delink. xrefIdList.size()=>"+xrefIdList.size());
			}}
			
			
			
			result.put("request.ITrxResult", res);
			
				if(res != null) {
				ILimitTrxValue lmtTrxObjNew = (ILimitTrxValue)res.getTrxValue();
//				ILimitSysXRef[] actualRefArrNew = null!=lmtTrxObjNew.getLimit()?lmtTrxObjNew.getLimit().getLimitSysXRefs():null;
				ILimitSysXRef[] actualRefArrNew = lmtTrxObjNew.getLimit().getLimitSysXRefs();
				
		
				String borrowId= "";
				String borrowname= "";
				ILimitXRefCoBorrower[] coborroObj=null;	

				for(int i=0; i< actualRefArrNew.length; i++) {
					String sourceRefId=actualRefArrNew[i].getCustomerSysXRef().getSourceRefNo();
					long cmsSID=actualRefArrNew[i].getSID();
				
					String xrefId1= dao1.getActualLineXrefIdNew(cmsSID);

				if (null != refArrNew) {
						for (int j = 0; j < refArrNew.length; j++) {
										
										if (actualRefArrNew[i].getSID() == refArrNew[j].getSID()) {
									 coborroObj= refArrNew[j].getCustomerSysXRef().getXRefCoBorrowerData();
											
									
									}
								}
						}
										
					if(null != coborroObj ) {
						for (int j = 0; j < coborroObj.length; j++) {
						
							borrowId= coborroObj[j].getCoBorrowerId();
							 borrowname= coborroObj[j].getCoBorrowerName();
							dao1.insertLineCoBorrowers(xrefId1,borrowId,borrowname);
						}	
					}
				}	
			    }
				
				//negative securityCoverage fix
				if(null != lmtTrxObj.getLimit()) {
					ICollateralAllocation[] colsActual = lmtTrxObj.getLimit().getCollateralAllocations();
					if(null != colsActual) {
					for(int i=0;i<colsActual.length;i++) {
					long collateralIdActual =	colsActual[i].getCollateral().getCollateralID();
					String limitIdActual = colsActual[i].getSourceLmtId();
					
					System.out.println("limitIdActual : "+limitIdActual+", collateralIdActual : "+collateralIdActual);
					List securityCoverageByCollateralId =	collateralDAO.securityCoverageByCollateralId(collateralIdActual,limitIdActual);
				//	int securityCoverageActual = (Integer)securityCoverageByCollateralId.get(0);
					int securityCoverageStagging = (Integer)securityCoverageByCollateralId.get(1);
				    long chargeIdActual = (Long)securityCoverageByCollateralId.get(2);
				    long chargeIdstagging = (Long)securityCoverageByCollateralId.get(3);
/*					System.out.println("Inside CheckerApproveLmtDetailCmd *****1818********stagging charge id : "+chargeIdstagging+
			", Stagging securityCoverage : "+securityCoverageStagging+", Actual charge id : "+chargeIdActual+" Actual securityCoverage : "+securityCoverageActual);*/
                if(securityCoverageStagging!=0) {
				if(securityCoverageStagging<0) {
					collateralDAO.updateSecurityCoverage(chargeIdActual,Math.abs(securityCoverageStagging));
					collateralDAO.updateSecurityCoverageStg(chargeIdstagging,Math.abs(securityCoverageStagging));
				}else {
					collateralDAO.updateSecurityCoverage(chargeIdActual,securityCoverageStagging);
				}
                }
					}
				}}
				
  				if(linePrimaryId!=null) {
	            for(int i=0;i<linePrimaryId.size();i++) {
                  dao2.updateStageLineStatus(linePrimaryId.get(i));
	            }
                  }

		}
		catch (Exception ex) {
			System.out.println("Exception in CheckerApproveLmtDetailCmd.java=>ex=>"+ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		DefaultLogger.debug(this, "Done ..  Going out of CheckerApproveLmtDetailCmd.java");
		System.out.println("Done ..  Going out of CheckerApproveLmtDetailCmd.java");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}


	private void updateLineDetForNotToSend(List<Long> lineIdNotToSend, ILimitSysXRef[] refArr, ILimitDAO dao1, String date) {
		if(lineIdNotToSend.size()>0){
			
			if (null != refArr) {
				for (int i = 0; i < refArr.length; i++) {
					for(int j=0;j<lineIdNotToSend.size();j++){
					  long longValue = lineIdNotToSend.get(j).longValue();
					if(longValue==refArr[i].getSID()){
					ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
					ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
					String tempSourceRefNo="";
					 tempSourceRefNo=""+dao1.generateSourceSeqNo();
					 int len=tempSourceRefNo.length();
					 String concatZero="";
					if(null!=tempSourceRefNo && len!=5){
						
						for(int m=5;m>len;m--){
							concatZero="0"+concatZero;
						}

					}
					tempSourceRefNo=concatZero+tempSourceRefNo;
					
					String sorceRefNo=ICMSConstant.FCUBS_CAD+date+tempSourceRefNo;	
					xref.setSourceRefNo(sorceRefNo);
					xref.setStatus(ICMSConstant.FCUBS_STATUS_SUCCESS);
					xref.setSendToCore("N");
					
					xref.setSegment1Flag("");
					xref.setPrioritySectorFlag("");
					xref.setIsCapitalMarketExposerFlag("");
					xref.setEstateTypeFlag("");
					xref.setCommRealEstateTypeFlag("");
					xref.setUncondiCanclFlag("");
					xref.setBranchAllowedFlag("");
					xref.setProductAllowedFlag("");
					xref.setCurrencyAllowedFlag("");
					xref.setLimitRestrictionFlag("");
					xref.setAction("");
					xref.setCoreStpRejectedReason("");
					if("BAHRAIN".equals(xref.getFacilitySystem())) {
						xref.setLiabBranch("001");
					}
					if("HONGKONG".equals(xref.getFacilitySystem())) {
						xref.setLiabBranch("101");
					}
					if("GIFTCITY".equals(xref.getFacilitySystem())) {
						xref.setLiabBranch("301");
					}					
					xref.setUdfDelete("");
					
					//line covenant changes
					ILineCovenant[] lineCovArr = xref.getLineCovenant();
					
					if(lineCovArr != null && lineCovArr.length>0 ) {
						ILineCovenant singleCov = null;
						int singleCovIndex = 0;
						
						for(int l=0;l<xref.getLineCovenant().length; l++) {
							ILineCovenant cov = xref.getLineCovenant()[l];
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								singleCov = cov;
								singleCovIndex = l;
							}
						}
						singleCov.setCountryRestrictionFlag(StringUtils.EMPTY);
						singleCov.setCurrencyRestrictionFlag(StringUtils.EMPTY);
						singleCov.setBankRestrictionFlag(StringUtils.EMPTY);
						singleCov.setDrawerFlag(StringUtils.EMPTY);
						singleCov.setDraweeFlag(StringUtils.EMPTY);
						singleCov.setBeneficiaryFlag(StringUtils.EMPTY);
						singleCov.setGoodsRestrictionFlag(StringUtils.EMPTY);
						singleCov.setRunningAccountFlag(StringUtils.EMPTY);
						
						lineCovArr[singleCovIndex] = singleCov; 
						xref.setLineCovenant(lineCovArr);
					}
					
					ILimitXRefUdf[] xRefUdfData = xref.getXRefUdfData();
					if(null!=xRefUdfData){
						xRefUdfData[0].setUdf1_Flag("");
						xRefUdfData[0].setUdf2_Flag("");
						xRefUdfData[0].setUdf3_Flag("");
						xRefUdfData[0].setUdf4_Flag("");
						xRefUdfData[0].setUdf5_Flag("");
						xRefUdfData[0].setUdf6_Flag("");
						xRefUdfData[0].setUdf7_Flag("");
						xRefUdfData[0].setUdf8_Flag("");
						xRefUdfData[0].setUdf9_Flag("");
						
						xRefUdfData[0].setUdf10_Flag("");
						xRefUdfData[0].setUdf11_Flag("");
						xRefUdfData[0].setUdf12_Flag("");
						xRefUdfData[0].setUdf13_Flag("");
						xRefUdfData[0].setUdf14_Flag("");
						xRefUdfData[0].setUdf15_Flag("");
						xRefUdfData[0].setUdf16_Flag("");
						xRefUdfData[0].setUdf17_Flag("");
						xRefUdfData[0].setUdf18_Flag("");
						xRefUdfData[0].setUdf19_Flag("");
						
						xRefUdfData[0].setUdf20_Flag("");
						xRefUdfData[0].setUdf21_Flag("");
						xRefUdfData[0].setUdf22_Flag("");
						xRefUdfData[0].setUdf23_Flag("");
						xRefUdfData[0].setUdf24_Flag("");
						xRefUdfData[0].setUdf25_Flag("");
						xRefUdfData[0].setUdf26_Flag("");
						xRefUdfData[0].setUdf27_Flag("");
						xRefUdfData[0].setUdf28_Flag("");
						xRefUdfData[0].setUdf29_Flag("");
						
						xRefUdfData[0].setUdf30_Flag("");
						xRefUdfData[0].setUdf31_Flag("");
						xRefUdfData[0].setUdf32_Flag("");
						xRefUdfData[0].setUdf33_Flag("");
						xRefUdfData[0].setUdf34_Flag("");
						xRefUdfData[0].setUdf35_Flag("");
						xRefUdfData[0].setUdf36_Flag("");
						xRefUdfData[0].setUdf37_Flag("");
						xRefUdfData[0].setUdf38_Flag("");
						xRefUdfData[0].setUdf39_Flag("");
						
						xRefUdfData[0].setUdf40_Flag("");
						xRefUdfData[0].setUdf41_Flag("");
						xRefUdfData[0].setUdf42_Flag("");
						xRefUdfData[0].setUdf43_Flag("");
						xRefUdfData[0].setUdf44_Flag("");
						xRefUdfData[0].setUdf45_Flag("");
						xRefUdfData[0].setUdf46_Flag("");
						xRefUdfData[0].setUdf47_Flag("");
						xRefUdfData[0].setUdf48_Flag("");
						xRefUdfData[0].setUdf49_Flag("");
						
						xRefUdfData[0].setUdf50_Flag("");
						xRefUdfData[0].setUdf51_Flag("");
						xRefUdfData[0].setUdf52_Flag("");
						xRefUdfData[0].setUdf53_Flag("");
						xRefUdfData[0].setUdf54_Flag("");
						xRefUdfData[0].setUdf55_Flag("");
						xRefUdfData[0].setUdf56_Flag("");
						xRefUdfData[0].setUdf57_Flag("");
						xRefUdfData[0].setUdf58_Flag("");
						xRefUdfData[0].setUdf59_Flag("");
						
						xRefUdfData[0].setUdf60_Flag("");
						xRefUdfData[0].setUdf61_Flag("");
						xRefUdfData[0].setUdf62_Flag("");
						xRefUdfData[0].setUdf63_Flag("");
						xRefUdfData[0].setUdf64_Flag("");
						xRefUdfData[0].setUdf65_Flag("");
						xRefUdfData[0].setUdf66_Flag("");
						xRefUdfData[0].setUdf67_Flag("");
						xRefUdfData[0].setUdf68_Flag("");
						xRefUdfData[0].setUdf69_Flag("");
						
						xRefUdfData[0].setUdf70_Flag("");
						xRefUdfData[0].setUdf71_Flag("");
						xRefUdfData[0].setUdf72_Flag("");
						xRefUdfData[0].setUdf73_Flag("");
						xRefUdfData[0].setUdf74_Flag("");
						xRefUdfData[0].setUdf75_Flag("");
						xRefUdfData[0].setUdf76_Flag("");
						xRefUdfData[0].setUdf77_Flag("");
						xRefUdfData[0].setUdf78_Flag("");
						xRefUdfData[0].setUdf79_Flag("");
						
						xRefUdfData[0].setUdf80_Flag("");
						xRefUdfData[0].setUdf81_Flag("");
						xRefUdfData[0].setUdf82_Flag("");
						xRefUdfData[0].setUdf83_Flag("");
						xRefUdfData[0].setUdf84_Flag("");
						xRefUdfData[0].setUdf85_Flag("");
						xRefUdfData[0].setUdf86_Flag("");
						xRefUdfData[0].setUdf87_Flag("");
						xRefUdfData[0].setUdf88_Flag("");
						xRefUdfData[0].setUdf89_Flag("");
						
						xRefUdfData[0].setUdf90_Flag("");
						xRefUdfData[0].setUdf91_Flag("");
						xRefUdfData[0].setUdf92_Flag("");
						xRefUdfData[0].setUdf93_Flag("");
						xRefUdfData[0].setUdf94_Flag("");
						xRefUdfData[0].setUdf95_Flag("");
						xRefUdfData[0].setUdf96_Flag("");
						xRefUdfData[0].setUdf97_Flag("");
						xRefUdfData[0].setUdf98_Flag("");
						xRefUdfData[0].setUdf99_Flag("");
						xRefUdfData[0].setUdf100_Flag("");
						
						xRefUdfData[0].setUdf101_Flag("");
						xRefUdfData[0].setUdf102_Flag("");
						xRefUdfData[0].setUdf103_Flag("");
						xRefUdfData[0].setUdf104_Flag("");
						xRefUdfData[0].setUdf105_Flag("");						
						xRefUdfData[0].setUdf106_Flag("");
						xRefUdfData[0].setUdf107_Flag("");
						xRefUdfData[0].setUdf108_Flag("");
						xRefUdfData[0].setUdf109_Flag("");
						xRefUdfData[0].setUdf110_Flag("");
						xRefUdfData[0].setUdf111_Flag("");						
						xRefUdfData[0].setUdf112_Flag("");
						xRefUdfData[0].setUdf113_Flag("");
						xRefUdfData[0].setUdf114_Flag("");
						xRefUdfData[0].setUdf115_Flag("");
						
					}
					
					ILimitXRefUdf2[] xRefUdfData2 = xref.getXRefUdfData2();
					if(null!=xRefUdfData2){
						
						xRefUdfData2[0].setUdf116_Flag("");
						xRefUdfData2[0].setUdf117_Flag("");
						xRefUdfData2[0].setUdf118_Flag("");
						xRefUdfData2[0].setUdf119_Flag("");
						
						xRefUdfData2[0].setUdf120_Flag("");
						xRefUdfData2[0].setUdf121_Flag("");
						xRefUdfData2[0].setUdf122_Flag("");
						xRefUdfData2[0].setUdf123_Flag("");
						xRefUdfData2[0].setUdf124_Flag("");
						xRefUdfData2[0].setUdf125_Flag("");
						xRefUdfData2[0].setUdf126_Flag("");
						xRefUdfData2[0].setUdf127_Flag("");
						xRefUdfData2[0].setUdf128_Flag("");
						xRefUdfData2[0].setUdf129_Flag("");
						
						xRefUdfData2[0].setUdf130_Flag("");
						xRefUdfData2[0].setUdf131_Flag("");
						xRefUdfData2[0].setUdf132_Flag("");
						xRefUdfData2[0].setUdf133_Flag("");
						xRefUdfData2[0].setUdf134_Flag("");
						xRefUdfData2[0].setUdf135_Flag("");
						xRefUdfData2[0].setUdf136_Flag("");
						xRefUdfData2[0].setUdf137_Flag("");
						xRefUdfData2[0].setUdf138_Flag("");
						xRefUdfData2[0].setUdf139_Flag("");
						
						xRefUdfData2[0].setUdf140_Flag("");
						xRefUdfData2[0].setUdf141_Flag("");
						xRefUdfData2[0].setUdf142_Flag("");
						xRefUdfData2[0].setUdf143_Flag("");
						xRefUdfData2[0].setUdf144_Flag("");
						xRefUdfData2[0].setUdf145_Flag("");
						xRefUdfData2[0].setUdf146_Flag("");
						xRefUdfData2[0].setUdf147_Flag("");
						xRefUdfData2[0].setUdf148_Flag("");
						xRefUdfData2[0].setUdf149_Flag("");
						
						xRefUdfData2[0].setUdf150_Flag("");
						xRefUdfData2[0].setUdf151_Flag("");
						xRefUdfData2[0].setUdf152_Flag("");
						xRefUdfData2[0].setUdf153_Flag("");
						xRefUdfData2[0].setUdf154_Flag("");
						xRefUdfData2[0].setUdf155_Flag("");
						xRefUdfData2[0].setUdf156_Flag("");
						xRefUdfData2[0].setUdf157_Flag("");
						xRefUdfData2[0].setUdf158_Flag("");
						xRefUdfData2[0].setUdf159_Flag("");
						
						xRefUdfData2[0].setUdf160_Flag("");
						xRefUdfData2[0].setUdf161_Flag("");
						xRefUdfData2[0].setUdf162_Flag("");
						xRefUdfData2[0].setUdf163_Flag("");
						xRefUdfData2[0].setUdf164_Flag("");
						xRefUdfData2[0].setUdf165_Flag("");
						xRefUdfData2[0].setUdf166_Flag("");
						xRefUdfData2[0].setUdf167_Flag("");
						xRefUdfData2[0].setUdf168_Flag("");
						xRefUdfData2[0].setUdf169_Flag("");
						
						xRefUdfData2[0].setUdf170_Flag("");
						xRefUdfData2[0].setUdf171_Flag("");
						xRefUdfData2[0].setUdf172_Flag("");
						xRefUdfData2[0].setUdf173_Flag("");
						xRefUdfData2[0].setUdf174_Flag("");
						xRefUdfData2[0].setUdf175_Flag("");
						xRefUdfData2[0].setUdf176_Flag("");
						xRefUdfData2[0].setUdf177_Flag("");
						xRefUdfData2[0].setUdf178_Flag("");
						xRefUdfData2[0].setUdf179_Flag("");
						
						xRefUdfData2[0].setUdf180_Flag("");
						xRefUdfData2[0].setUdf181_Flag("");
						xRefUdfData2[0].setUdf182_Flag("");
						xRefUdfData2[0].setUdf183_Flag("");
						xRefUdfData2[0].setUdf184_Flag("");
						xRefUdfData2[0].setUdf185_Flag("");
						xRefUdfData2[0].setUdf186_Flag("");
						xRefUdfData2[0].setUdf187_Flag("");
						xRefUdfData2[0].setUdf188_Flag("");
						xRefUdfData2[0].setUdf189_Flag("");
						
						xRefUdfData2[0].setUdf190_Flag("");
						xRefUdfData2[0].setUdf191_Flag("");
						xRefUdfData2[0].setUdf192_Flag("");
						xRefUdfData2[0].setUdf193_Flag("");
						xRefUdfData2[0].setUdf194_Flag("");
						xRefUdfData2[0].setUdf195_Flag("");
						xRefUdfData2[0].setUdf196_Flag("");
						xRefUdfData2[0].setUdf197_Flag("");
						xRefUdfData2[0].setUdf198_Flag("");
						xRefUdfData2[0].setUdf199_Flag("");
						xRefUdfData2[0].setUdf200_Flag("");
						
						xRefUdfData2[0].setUdf201_Flag("");
						xRefUdfData2[0].setUdf202_Flag("");
						xRefUdfData2[0].setUdf203_Flag("");
						xRefUdfData2[0].setUdf204_Flag("");
						xRefUdfData2[0].setUdf205_Flag("");						
						xRefUdfData2[0].setUdf206_Flag("");
						xRefUdfData2[0].setUdf207_Flag("");
						xRefUdfData2[0].setUdf208_Flag("");
						xRefUdfData2[0].setUdf209_Flag("");
						xRefUdfData2[0].setUdf210_Flag("");
						xRefUdfData2[0].setUdf211_Flag("");						
						xRefUdfData2[0].setUdf212_Flag("");
						xRefUdfData2[0].setUdf213_Flag("");
						xRefUdfData2[0].setUdf214_Flag("");
						xRefUdfData2[0].setUdf215_Flag("");
						
					}
					obj.setCustomerSysXRef(xref);
					refArr[i] = obj;
					}
					}
				}
				}
				}
		
		
	}

	private void generateInterestRateNotification(ILimitTrxValue lmtTrxObj) {
		ILimit stagingLimit = lmtTrxObj.getStagingLimit();
		String partyId=String.valueOf(lmtTrxObj.getCustomerID());
		if("Y".equals(stagingLimit.getIsReleased())){
		ILimitSysXRef[] limitSysXRefs = stagingLimit.getLimitSysXRefs();
		//retrieve it from general param
		IGeneralParamProxy generalParamProxy  =(IGeneralParamProxy)BeanHouse.get("generalParamProxy");
		IGeneralParamEntry generalParamEntry = generalParamProxy.getGeneralParamEntryByParamCodeActual(IGeneralParamEntry.PARAM_CODE_BASE_INTREST_RATE);
		
		double baseIntrestRate=0;
		if(generalParamEntry!=null){
			baseIntrestRate= Double.parseDouble(generalParamEntry.getParamValue());
		}
		boolean isNotificationGenerated=false;
		if(limitSysXRefs!=null && limitSysXRefs.length>0){
			for (int i = 0; i < limitSysXRefs.length; i++) {
					if(!isNotificationGenerated){
						ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
						ICustomerSysXRef customerSysXRef = iLimitSysXRef.getCustomerSysXRef();
						double intrestRate=0;
						if("fixed".equals(customerSysXRef.getInterestRateType())){
							if(customerSysXRef.getIntRateFix()!=null && !"".equals(customerSysXRef.getIntRateFix())){
								intrestRate = Double.parseDouble(customerSysXRef.getIntRateFix());
							}
						}else if("floating".equals(customerSysXRef.getInterestRateType())){
							if(customerSysXRef.getIntRateFloatingMargin()!=null && !"".equals(customerSysXRef.getIntRateFloatingMargin())){
								if("-".equals(customerSysXRef.getIntRateFloatingMarginFlag())){
									intrestRate =Double.parseDouble(customerSysXRef.getIntRateFloatingRange()) - Double.parseDouble(customerSysXRef.getIntRateFloatingMargin());
								}else if("+".equals(customerSysXRef.getIntRateFloatingMarginFlag())){
									intrestRate =Double.parseDouble(customerSysXRef.getIntRateFloatingRange()) + Double.parseDouble(customerSysXRef.getIntRateFloatingMargin());
								}
							}
						}
						
						
						if(intrestRate<baseIntrestRate){
							IEmailNotificationService service=(IEmailNotificationService)BeanHouse.get("emailNotificationService");
								ICustomerNotificationDetail noticationDetail= new OBCustomerNotificationDetail();
								ICustomerProxy proxy = CustomerProxyFactory.getProxy();
								ICMSCustomer customer = proxy.getCustomerByLimitProfileId(stagingLimit.getLimitProfileID());
								noticationDetail.setPartyId(partyId);
								if(customer != null)
									noticationDetail.setPartyName(customer.getCustomerName());
//								DefaultLogger.debug(this, "Creating notification"+partyId);
								IEmailNotification createNotification = service.createNotification("NOT0011", noticationDetail);
								DefaultLogger.debug(this, "Notification Created"+createNotification.getNotifcationId());
								isNotificationGenerated=true;
							}
					}
				}
			}
		}		
	}

	private void generateRecurrentCheckList(OBTrxContext ctx,
			ILimitTrxValue lmtTrxObj) {
		ILimit stagingLimit = lmtTrxObj.getStagingLimit();
		
		if("Y".equals(stagingLimit.getIsDP()) && ("WORKING CAPITAL".equalsIgnoreCase(stagingLimit.getPurpose()) || "WORKING_CAPITAL".equalsIgnoreCase(stagingLimit.getPurpose())) ){
			IItem item = new OBDocumentItem();
			item.setItemDesc("Stock Statement");
			IRecurrentCheckListItem recurrentCheckListItem=new OBRecurrentCheckListItem();
			recurrentCheckListItem.setItem(item);
			recurrentCheckListItem.setDocType("DP");
			
			
			long limitProfileID = lmtTrxObj.getLimitProfileID();
			long subProfileId = lmtTrxObj.getCustomerID();
			DefaultLogger.debug(this, "limitProfileID  ==>"+limitProfileID+"  subProfileId  ==>"+subProfileId);
			IRecurrentCheckList recurrentCheckList;
			IRecurrentProxyManager recurrentProxyManager=(IRecurrentProxyManager) BeanHouse.get("recurrentProxy");
			
			try {
				IRecurrentCheckListTrxValue checkListTrxVal = recurrentProxyManager.getRecurrentCheckListTrx(limitProfileID, subProfileId);
				
				if(checkListTrxVal==null){
					recurrentCheckList= new OBRecurrentCheckList(lmtTrxObj.getLimitProfileID(), lmtTrxObj.getCustomerID());
					recurrentCheckList.addItem(recurrentCheckListItem);
					checkListTrxVal= recurrentProxyManager.makerCreateCheckListWithoutApproval(ctx, recurrentCheckList);
					DefaultLogger.debug(this, "CheckList Created");				
				}else{
					recurrentCheckList=checkListTrxVal.getCheckList();
					IRecurrentCheckListItem[] checkListItemList = recurrentCheckList.getCheckListItemList();
					boolean addStockStatement= true;
					for (int i = 0; i < checkListItemList.length; i++) {
						IRecurrentCheckListItem iRecurrentCheckListItem = checkListItemList[i];
						if("Stock Statement".equals(iRecurrentCheckListItem.getItemDesc())){
							addStockStatement=false;	
						}
						
					}
					if(addStockStatement){
					recurrentCheckList.addItem(recurrentCheckListItem);
					checkListTrxVal = recurrentProxyManager.makerUpdateCheckListWithoutApproval(ctx,checkListTrxVal, recurrentCheckList);
					DefaultLogger.debug(this, "CheckList Updated");				
					}
				}
				
			} catch (RecurrentException e1) {
				e1.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	//added by santosh
	private void updateUdfFlags(ILimitTrxValue lmtTrxObj){
		
		ILimitSysXRef[] refArr = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
		ILimitSysXRef[] actualRefArr = null!=lmtTrxObj.getLimit()?lmtTrxObj.getLimit().getLimitSysXRefs():null;
		String actualUdfAllowed=null;
		ICustomerSysXRef actualXref=null;
		if (null != refArr) {
			for (int i = 0; i < refArr.length; i++) {
				ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
				ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
				
				
				
				if(!(ICMSConstant.FCUBSLIMIT_ACTION_REOPEN.equals(xref.getAction()) || ICMSConstant.FCUBSLIMIT_ACTION_CLOSE.equals(xref.getAction()))){
				if(null!=actualRefArr){
				DefaultLogger.debug(this, "in UPDATEUDF starting for loop");
				for(int j=0 ;j < actualRefArr.length ; j++){
					DefaultLogger.debug(this, "in UPDATEUDF actual obj sid is"+actualRefArr[j].getSID());
					DefaultLogger.debug(this, "in UPDATEUDF staging obj sid is "+refArr[i].getSID());
					if(actualRefArr[j].getSID()== refArr[i].getSID()){
						ILimitSysXRef actualObj = (ILimitSysXRef) actualRefArr[j];
						DefaultLogger.debug(this, "in UPDATEUDF actual obj"+actualRefArr[j].getSID());
						DefaultLogger.debug(this, "in UPDATEUDF staging obj"+refArr[i].getSID());
						actualXref = (ICustomerSysXRef) actualObj.getCustomerSysXRef();
						DefaultLogger.debug(this, "in UPDATEUDF actual udf allowed"+actualXref.getUdfAllowed());
						DefaultLogger.debug(this, "in UPDATEUDF staging udf allowed"+refArr[i].getCustomerSysXRef().getUdfAllowed());
						actualUdfAllowed=actualXref.getUdfAllowed();
						break;
						
					}
					
					else 
					{
						actualUdfAllowed = null;
	
					}
						
				}
				
				}
				//add udf label
				setUdfLabel(xref);
				
				//for add flag
				if(ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xref.getAction())){
				String stagingUdfAllowed=xref.getUdfAllowed();

				DefaultLogger.debug(this, "in UPDATEUDF staging udf is "+stagingUdfAllowed);
				DefaultLogger.debug(this, "in UPDATEUDF actual udf is "+actualUdfAllowed);

					if(null!=actualUdfAllowed && null!=stagingUdfAllowed) {
						List<String> actualUdfAllowedList = Arrays.asList(actualUdfAllowed.split(","));
						List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
				
						if(actualUdfAllowedList.size()>stagingUdfAllowedList.size()) {
							for(int k=0;k<stagingUdfAllowedList.size();k++) {
								if(!actualUdfAllowedList.contains(stagingUdfAllowedList.get(k))) {
									updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
								}
							}
						} else {
							for(int k=0;k<stagingUdfAllowedList.size();k++) {
								if(!actualUdfAllowedList.contains(stagingUdfAllowedList.get(k))) {
									updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
								}
							}
						}
					}
					else {
						if(null==actualUdfAllowed && null!=stagingUdfAllowed) {
							List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
							for(int k=0;k<stagingUdfAllowedList.size();k++) {
									updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
							}
						}
					}
					
					
						if(null!=xref.getCoreStpRejectedReason() && !xref.getCoreStpRejectedReason().isEmpty()){
						if(null!=stagingUdfAllowed){
						List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
						for(int k=0;k<stagingUdfAllowedList.size();k++) {
								updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
						}
						}
						
						
					}
					
				}
					//for mandatory udf
					//updateMandatoryUdfAddFlag(xref);
					//end mandatory udf 
					
				//for delete flag
				String udfDelete=xref.getUdfDelete();
				String allUDFDelete = null;
				if(udfDelete!=null && udfDelete!=""){
					if(null!=actualXref && null!=actualXref.getUdfDelete()){
						if(udfDelete.contains(actualXref.getUdfDelete())) {
							allUDFDelete=udfDelete;
						}
						else {
						allUDFDelete = udfDelete+","+actualXref.getUdfDelete();
						xref.setUdfDelete(allUDFDelete);
						actualXref.setUdfDelete(allUDFDelete);
						}
					}
					else
						allUDFDelete = udfDelete;
					updateUdfDeleteFlag(allUDFDelete,xref,actualXref);
					}
				
				
				
				
				if(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY.equals(xref.getAction())){
					if(null!=xref.getCoreStpRejectedReason() && !xref.getCoreStpRejectedReason().isEmpty()){
						
						if(null==actualXref.getXRefUdfData()&&null!=xref.getXRefUdfData()) {
							String stagingUdfAllowed=xref.getUdfAllowed();
							if(null!=stagingUdfAllowed){
								List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
								for(int k=0;k<stagingUdfAllowedList.size();k++) {
										updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
								}
							}
						}
						else
							updateUdfModifyForRejectionCase(actualXref,xref);
						
					}
					else {
						if(null==actualXref.getXRefUdfData()&&null!=xref.getXRefUdfData()) {
							String stagingUdfAllowed=xref.getUdfAllowed();
							if(null!=stagingUdfAllowed){
								List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
								for(int k=0;k<stagingUdfAllowedList.size();k++) {
										updateUdfAddFlag(stagingUdfAllowedList.get(k),xref);
								}
							}
						}
						else
							updateUdfModifyFlag(actualXref,xref);
					}
				}
				
				obj.setCustomerSysXRef(xref);
				refArr[i] = obj;
			}
			}
		}
		lmtTrxObj.getStagingLimit().setLimitSysXRefs(refArr);	
	}
	

	private void updateUdfFlags_2(ILimitTrxValue lmtTrxObj){
		
	ILimitSysXRef[] refArr = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
	ILimitSysXRef[] actualRefArr = null!=lmtTrxObj.getLimit()?lmtTrxObj.getLimit().getLimitSysXRefs():null;
	String actualUdfAllowed=null;
	ICustomerSysXRef actualXref=null;
	if (null != refArr) {
		for (int i = 0; i < refArr.length; i++) {
			ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
			ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
			
			
			
			if(!(ICMSConstant.FCUBSLIMIT_ACTION_REOPEN.equals(xref.getAction()) || ICMSConstant.FCUBSLIMIT_ACTION_CLOSE.equals(xref.getAction()))){
			if(null!=actualRefArr){
			DefaultLogger.debug(this, "in UPDATEUDF starting for loop");
			for(int j=0 ;j < actualRefArr.length ; j++){
				DefaultLogger.debug(this, "in UPDATEUDF actual obj sid is"+actualRefArr[j].getSID());
				DefaultLogger.debug(this, "in UPDATEUDF staging obj sid is "+refArr[i].getSID());
				if(actualRefArr[j].getSID()== refArr[i].getSID()){
					ILimitSysXRef actualObj = (ILimitSysXRef) actualRefArr[j];
					DefaultLogger.debug(this, "in UPDATEUDF actual obj"+actualRefArr[j].getSID());
					DefaultLogger.debug(this, "in UPDATEUDF staging obj"+refArr[i].getSID());
					actualXref = (ICustomerSysXRef) actualObj.getCustomerSysXRef();
					DefaultLogger.debug(this, "in UPDATEUDF actual udf allowed"+actualXref.getUdfAllowed_2());
					DefaultLogger.debug(this, "in UPDATEUDF staging udf allowed"+refArr[i].getCustomerSysXRef().getUdfAllowed_2());
					actualUdfAllowed=actualXref.getUdfAllowed_2();
					break;
					
				}
				
				else 
				{
					actualUdfAllowed = null;

				}
					
			}
			
			}
			//add udf label
			setUdfLabel_2(xref);
			
			//for add flag
			if(ICMSConstant.FCUBSLIMIT_ACTION_NEW.equals(xref.getAction())){
			String stagingUdfAllowed=xref.getUdfAllowed_2();

			DefaultLogger.debug(this, "in UPDATEUDF staging udf is "+stagingUdfAllowed);
			DefaultLogger.debug(this, "in UPDATEUDF actual udf is "+actualUdfAllowed);

				if(null!=actualUdfAllowed && null!=stagingUdfAllowed) {
					List<String> actualUdfAllowedList = Arrays.asList(actualUdfAllowed.split(","));
					List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
			
					if(actualUdfAllowedList.size()>stagingUdfAllowedList.size()) {
						for(int k=0;k<stagingUdfAllowedList.size();k++) {
							if(!actualUdfAllowedList.contains(stagingUdfAllowedList.get(k))) {
								updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
							}
						}
					} else {
						for(int k=0;k<stagingUdfAllowedList.size();k++) {
							if(!actualUdfAllowedList.contains(stagingUdfAllowedList.get(k))) {
								updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
							}
						}
					}
				}
				else {
					if(null==actualUdfAllowed && null!=stagingUdfAllowed) {
						List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
						for(int k=0;k<stagingUdfAllowedList.size();k++) {
								updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
						}
					}
				}
				
				
					if(null!=xref.getCoreStpRejectedReason() && !xref.getCoreStpRejectedReason().isEmpty()){
					if(null!=stagingUdfAllowed){
					List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
					for(int k=0;k<stagingUdfAllowedList.size();k++) {
							updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
					}
					}
					
					
				}
				
			}
				//for mandatory udf
				//updateMandatoryUdfAddFlag(xref);
				//end mandatory udf 
				
			//for delete flag
			String udfDelete=xref.getUdfDelete_2();
			String allUDFDelete = null;
			if(udfDelete!=null && udfDelete!=""){
				if(null!=actualXref && null!=actualXref.getUdfDelete_2()){
					if(udfDelete.contains(actualXref.getUdfDelete_2())) {
						allUDFDelete=udfDelete;
					}
					else {
				//	allUDFDelete = udfDelete+","+actualXref.getUdfDelete_2();
					allUDFDelete = udfDelete;
					xref.setUdfDelete_2(allUDFDelete);
					actualXref.setUdfDelete_2(allUDFDelete);
					}
				}
				else
					allUDFDelete = udfDelete;
				updateUdfDeleteFlag_2(allUDFDelete,xref,actualXref);
				}
	
			if(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY.equals(xref.getAction())){
				if(null!=xref.getCoreStpRejectedReason() && !xref.getCoreStpRejectedReason().isEmpty()){
					
					if(null==actualXref.getXRefUdfData2()&&null!=xref.getXRefUdfData2()) {
						String stagingUdfAllowed=xref.getUdfAllowed_2();
						if(null!=stagingUdfAllowed){
							List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
							for(int k=0;k<stagingUdfAllowedList.size();k++) {
									updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
							}
						}
					}
					else
						updateUdfModifyForRejectionCase2(actualXref,xref);
					
				}
				else {
					if(null==actualXref.getXRefUdfData2()&&null!=xref.getXRefUdfData2()) {
						String stagingUdfAllowed=xref.getUdfAllowed_2();
						if(null!=stagingUdfAllowed){
							List<String> stagingUdfAllowedList = Arrays.asList(stagingUdfAllowed.split(","));
							for(int k=0;k<stagingUdfAllowedList.size();k++) {
								updateUdfAddFlag_2(stagingUdfAllowedList.get(k),xref);
							}
						}
					}
					else
						updateUdfModifyFlag2(actualXref,xref);
				}
			}
			
			obj.setCustomerSysXRef(xref);
			refArr[i] = obj;
		}
		}
	}
	lmtTrxObj.getStagingLimit().setLimitSysXRefs(refArr);	
}
	private void updateMandatoryUdfAddFlag(ICustomerSysXRef xref){
		//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
		List udfList = udfDao.getUdfByMandatory("3");
		List udfLbValList = new ArrayList();
		
		List udfList_2 = udfDao.getUdfByMandatory("4");
		List udfLbValList_2 = new ArrayList();
		
		String id="";
		if(udfList!=null || udfList.size() != 0)
		{
			int size = udfList.size();
			Collections.sort(udfList, new UDFComparator());
			IUdf udf;
			for (int j=0; j<size; j++) {
				udf = (IUdf) udfList.get(j);
				id=Long.toString(udf.getSequence());
				updateUdfAddFlag(id,xref);
			}
		}
		
		if( null != udfList_2 ||  0  != udfList_2.size())
		{
			int size = udfList_2.size();
			Collections.sort(udfList_2, new UDFComparator());
			IUdf udf;
			for (int j=0; j<size; j++) {
				udf = (IUdf) udfList.get(j);
				id=Long.toString(udf.getSequence()+115);
				updateUdfAddFlag_2(id,xref);
			}
		}
	}
	private void updateUdfDeleteFlag(String udfDelete,ICustomerSysXRef stagingXref,ICustomerSysXRef actualXref) {
		
		ILimitXRefUdf udfData[]=stagingXref.getXRefUdfData();
		//ILimitXRefUdf actualUdfData[]=actualXref.getXRefUdfData();
		ILimitXRefUdf actualUdfData[] = null;
		if(null != actualXref && null != actualXref.getXRefUdfData() ) {
		 actualUdfData =actualXref.getXRefUdfData();
		}
		String udfDeleteArray[]=udfDelete.split(",");
		if(null!=actualUdfData){
		for(int i=0;i<udfDeleteArray.length;i++) {
			switch (Integer.parseInt(udfDeleteArray[i])) {
				case 1:udfData[0].setUdf1_Flag("D"); udfData[0].setUdf1_Value(actualUdfData[0].getUdf1_Value());udfData[0].setUdf1_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 2:udfData[0].setUdf2_Flag("D"); udfData[0].setUdf2_Value(actualUdfData[0].getUdf2_Value());udfData[0].setUdf2_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 3:udfData[0].setUdf3_Flag("D"); udfData[0].setUdf3_Value(actualUdfData[0].getUdf3_Value());udfData[0].setUdf3_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 4:udfData[0].setUdf4_Flag("D"); udfData[0].setUdf4_Value(actualUdfData[0].getUdf4_Value());udfData[0].setUdf4_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 5:udfData[0].setUdf5_Flag("D"); udfData[0].setUdf5_Value(actualUdfData[0].getUdf5_Value());udfData[0].setUdf5_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 6:udfData[0].setUdf6_Flag("D"); udfData[0].setUdf6_Value(actualUdfData[0].getUdf6_Value());udfData[0].setUdf6_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 7:udfData[0].setUdf7_Flag("D"); udfData[0].setUdf7_Value(actualUdfData[0].getUdf7_Value());udfData[0].setUdf7_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 8:udfData[0].setUdf8_Flag("D"); udfData[0].setUdf8_Value(actualUdfData[0].getUdf8_Value());udfData[0].setUdf8_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 9:udfData[0].setUdf9_Flag("D"); udfData[0].setUdf9_Value(actualUdfData[0].getUdf9_Value());udfData[0].setUdf9_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 10:udfData[0].setUdf10_Flag("D"); udfData[0].setUdf10_Value(actualUdfData[0].getUdf10_Value());udfData[0].setUdf10_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 11:udfData[0].setUdf11_Flag("D"); udfData[0].setUdf11_Value(actualUdfData[0].getUdf11_Value());udfData[0].setUdf11_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 12:udfData[0].setUdf12_Flag("D"); udfData[0].setUdf12_Value(actualUdfData[0].getUdf12_Value());udfData[0].setUdf12_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 13:udfData[0].setUdf13_Flag("D"); udfData[0].setUdf13_Value(actualUdfData[0].getUdf13_Value());udfData[0].setUdf13_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 14:udfData[0].setUdf14_Flag("D"); udfData[0].setUdf14_Value(actualUdfData[0].getUdf14_Value());udfData[0].setUdf14_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 15:udfData[0].setUdf15_Flag("D"); udfData[0].setUdf15_Value(actualUdfData[0].getUdf15_Value());udfData[0].setUdf15_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 16:udfData[0].setUdf16_Flag("D"); udfData[0].setUdf16_Value(actualUdfData[0].getUdf16_Value());udfData[0].setUdf16_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 17:udfData[0].setUdf17_Flag("D"); udfData[0].setUdf17_Value(actualUdfData[0].getUdf17_Value());udfData[0].setUdf17_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 18:udfData[0].setUdf18_Flag("D"); udfData[0].setUdf18_Value(actualUdfData[0].getUdf18_Value());udfData[0].setUdf18_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 19:udfData[0].setUdf19_Flag("D"); udfData[0].setUdf19_Value(actualUdfData[0].getUdf19_Value());udfData[0].setUdf19_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 20:udfData[0].setUdf20_Flag("D"); udfData[0].setUdf20_Value(actualUdfData[0].getUdf20_Value());udfData[0].setUdf20_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 21:udfData[0].setUdf21_Flag("D"); udfData[0].setUdf21_Value(actualUdfData[0].getUdf21_Value());udfData[0].setUdf21_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 22:udfData[0].setUdf22_Flag("D"); udfData[0].setUdf22_Value(actualUdfData[0].getUdf22_Value());udfData[0].setUdf22_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 23:udfData[0].setUdf23_Flag("D"); udfData[0].setUdf23_Value(actualUdfData[0].getUdf23_Value());udfData[0].setUdf23_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 24:udfData[0].setUdf24_Flag("D"); udfData[0].setUdf24_Value(actualUdfData[0].getUdf24_Value());udfData[0].setUdf24_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 25:udfData[0].setUdf25_Flag("D"); udfData[0].setUdf25_Value(actualUdfData[0].getUdf25_Value());udfData[0].setUdf25_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 26:udfData[0].setUdf26_Flag("D"); udfData[0].setUdf26_Value(actualUdfData[0].getUdf26_Value());udfData[0].setUdf26_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 27:udfData[0].setUdf27_Flag("D"); udfData[0].setUdf27_Value(actualUdfData[0].getUdf27_Value());udfData[0].setUdf27_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 28:udfData[0].setUdf28_Flag("D"); udfData[0].setUdf28_Value(actualUdfData[0].getUdf28_Value());udfData[0].setUdf28_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 29:udfData[0].setUdf29_Flag("D"); udfData[0].setUdf29_Value(actualUdfData[0].getUdf29_Value());udfData[0].setUdf29_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 30:udfData[0].setUdf30_Flag("D"); udfData[0].setUdf30_Value(actualUdfData[0].getUdf30_Value());udfData[0].setUdf30_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 31:udfData[0].setUdf31_Flag("D"); udfData[0].setUdf31_Value(actualUdfData[0].getUdf31_Value());udfData[0].setUdf31_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 32:udfData[0].setUdf32_Flag("D"); udfData[0].setUdf32_Value(actualUdfData[0].getUdf32_Value());udfData[0].setUdf32_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 33:udfData[0].setUdf33_Flag("D"); udfData[0].setUdf33_Value(actualUdfData[0].getUdf33_Value());udfData[0].setUdf33_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 34:udfData[0].setUdf34_Flag("D"); udfData[0].setUdf34_Value(actualUdfData[0].getUdf34_Value());udfData[0].setUdf34_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 35:udfData[0].setUdf35_Flag("D"); udfData[0].setUdf35_Value(actualUdfData[0].getUdf35_Value());udfData[0].setUdf35_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 36:udfData[0].setUdf36_Flag("D"); udfData[0].setUdf36_Value(actualUdfData[0].getUdf36_Value());udfData[0].setUdf36_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 37:udfData[0].setUdf37_Flag("D"); udfData[0].setUdf37_Value(actualUdfData[0].getUdf37_Value());udfData[0].setUdf37_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 38:udfData[0].setUdf38_Flag("D"); udfData[0].setUdf38_Value(actualUdfData[0].getUdf38_Value());udfData[0].setUdf38_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 39:udfData[0].setUdf39_Flag("D"); udfData[0].setUdf39_Value(actualUdfData[0].getUdf39_Value());udfData[0].setUdf39_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 40:udfData[0].setUdf40_Flag("D"); udfData[0].setUdf40_Value(actualUdfData[0].getUdf40_Value());udfData[0].setUdf40_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 41:udfData[0].setUdf41_Flag("D"); udfData[0].setUdf41_Value(actualUdfData[0].getUdf41_Value());udfData[0].setUdf41_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 42:udfData[0].setUdf42_Flag("D"); udfData[0].setUdf42_Value(actualUdfData[0].getUdf42_Value());udfData[0].setUdf42_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 43:udfData[0].setUdf43_Flag("D"); udfData[0].setUdf43_Value(actualUdfData[0].getUdf43_Value());udfData[0].setUdf43_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 44:udfData[0].setUdf44_Flag("D"); udfData[0].setUdf44_Value(actualUdfData[0].getUdf44_Value());udfData[0].setUdf44_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 45:udfData[0].setUdf45_Flag("D"); udfData[0].setUdf45_Value(actualUdfData[0].getUdf45_Value());udfData[0].setUdf45_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 46:udfData[0].setUdf46_Flag("D"); udfData[0].setUdf46_Value(actualUdfData[0].getUdf46_Value());udfData[0].setUdf46_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 47:udfData[0].setUdf47_Flag("D"); udfData[0].setUdf47_Value(actualUdfData[0].getUdf47_Value());udfData[0].setUdf47_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 48:udfData[0].setUdf48_Flag("D"); udfData[0].setUdf48_Value(actualUdfData[0].getUdf48_Value());udfData[0].setUdf48_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 49:udfData[0].setUdf49_Flag("D"); udfData[0].setUdf49_Value(actualUdfData[0].getUdf49_Value());udfData[0].setUdf49_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 50:udfData[0].setUdf50_Flag("D"); udfData[0].setUdf50_Value(actualUdfData[0].getUdf50_Value());udfData[0].setUdf50_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 51:udfData[0].setUdf51_Flag("D"); udfData[0].setUdf51_Value(actualUdfData[0].getUdf51_Value());udfData[0].setUdf51_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 52:udfData[0].setUdf52_Flag("D"); udfData[0].setUdf52_Value(actualUdfData[0].getUdf52_Value());udfData[0].setUdf52_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 53:udfData[0].setUdf53_Flag("D"); udfData[0].setUdf53_Value(actualUdfData[0].getUdf53_Value());udfData[0].setUdf53_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 54:udfData[0].setUdf54_Flag("D"); udfData[0].setUdf54_Value(actualUdfData[0].getUdf54_Value());udfData[0].setUdf54_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 55:udfData[0].setUdf55_Flag("D"); udfData[0].setUdf55_Value(actualUdfData[0].getUdf55_Value());udfData[0].setUdf55_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 56:udfData[0].setUdf56_Flag("D"); udfData[0].setUdf56_Value(actualUdfData[0].getUdf56_Value());udfData[0].setUdf56_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 57:udfData[0].setUdf57_Flag("D"); udfData[0].setUdf57_Value(actualUdfData[0].getUdf57_Value());udfData[0].setUdf57_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 58:udfData[0].setUdf58_Flag("D"); udfData[0].setUdf58_Value(actualUdfData[0].getUdf58_Value());udfData[0].setUdf58_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 59:udfData[0].setUdf59_Flag("D"); udfData[0].setUdf59_Value(actualUdfData[0].getUdf59_Value());udfData[0].setUdf59_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 60:udfData[0].setUdf60_Flag("D"); udfData[0].setUdf60_Value(actualUdfData[0].getUdf60_Value());udfData[0].setUdf60_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 61:udfData[0].setUdf61_Flag("D"); udfData[0].setUdf61_Value(actualUdfData[0].getUdf61_Value());udfData[0].setUdf61_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 62:udfData[0].setUdf62_Flag("D"); udfData[0].setUdf62_Value(actualUdfData[0].getUdf62_Value());udfData[0].setUdf62_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 63:udfData[0].setUdf63_Flag("D"); udfData[0].setUdf63_Value(actualUdfData[0].getUdf63_Value());udfData[0].setUdf63_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 64:udfData[0].setUdf64_Flag("D"); udfData[0].setUdf64_Value(actualUdfData[0].getUdf64_Value());udfData[0].setUdf64_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 65:udfData[0].setUdf65_Flag("D"); udfData[0].setUdf65_Value(actualUdfData[0].getUdf65_Value());udfData[0].setUdf65_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 66:udfData[0].setUdf66_Flag("D"); udfData[0].setUdf66_Value(actualUdfData[0].getUdf66_Value());udfData[0].setUdf66_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 67:udfData[0].setUdf67_Flag("D"); udfData[0].setUdf67_Value(actualUdfData[0].getUdf67_Value());udfData[0].setUdf67_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 68:udfData[0].setUdf68_Flag("D"); udfData[0].setUdf68_Value(actualUdfData[0].getUdf68_Value());udfData[0].setUdf68_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 69:udfData[0].setUdf69_Flag("D"); udfData[0].setUdf69_Value(actualUdfData[0].getUdf69_Value());udfData[0].setUdf69_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 70:udfData[0].setUdf70_Flag("D"); udfData[0].setUdf70_Value(actualUdfData[0].getUdf70_Value());udfData[0].setUdf70_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 71:udfData[0].setUdf71_Flag("D"); udfData[0].setUdf71_Value(actualUdfData[0].getUdf71_Value());udfData[0].setUdf71_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 72:udfData[0].setUdf72_Flag("D"); udfData[0].setUdf72_Value(actualUdfData[0].getUdf72_Value());udfData[0].setUdf72_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 73:udfData[0].setUdf73_Flag("D"); udfData[0].setUdf73_Value(actualUdfData[0].getUdf73_Value());udfData[0].setUdf73_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 74:udfData[0].setUdf74_Flag("D"); udfData[0].setUdf74_Value(actualUdfData[0].getUdf74_Value());udfData[0].setUdf74_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 75:udfData[0].setUdf75_Flag("D"); udfData[0].setUdf75_Value(actualUdfData[0].getUdf75_Value());udfData[0].setUdf75_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 76:udfData[0].setUdf76_Flag("D"); udfData[0].setUdf76_Value(actualUdfData[0].getUdf76_Value());udfData[0].setUdf76_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 77:udfData[0].setUdf77_Flag("D"); udfData[0].setUdf77_Value(actualUdfData[0].getUdf77_Value());udfData[0].setUdf77_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 78:udfData[0].setUdf78_Flag("D"); udfData[0].setUdf78_Value(actualUdfData[0].getUdf78_Value());udfData[0].setUdf78_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 79:udfData[0].setUdf79_Flag("D"); udfData[0].setUdf79_Value(actualUdfData[0].getUdf79_Value());udfData[0].setUdf79_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 80:udfData[0].setUdf80_Flag("D"); udfData[0].setUdf80_Value(actualUdfData[0].getUdf80_Value());udfData[0].setUdf80_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 81:udfData[0].setUdf81_Flag("D"); udfData[0].setUdf81_Value(actualUdfData[0].getUdf81_Value());udfData[0].setUdf81_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 82:udfData[0].setUdf82_Flag("D"); udfData[0].setUdf82_Value(actualUdfData[0].getUdf82_Value());udfData[0].setUdf82_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 83:udfData[0].setUdf83_Flag("D"); udfData[0].setUdf83_Value(actualUdfData[0].getUdf83_Value());udfData[0].setUdf83_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 84:udfData[0].setUdf84_Flag("D"); udfData[0].setUdf84_Value(actualUdfData[0].getUdf84_Value());udfData[0].setUdf84_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 85:udfData[0].setUdf85_Flag("D"); udfData[0].setUdf85_Value(actualUdfData[0].getUdf85_Value());udfData[0].setUdf85_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 86:udfData[0].setUdf86_Flag("D"); udfData[0].setUdf86_Value(actualUdfData[0].getUdf86_Value());udfData[0].setUdf86_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 87:udfData[0].setUdf87_Flag("D"); udfData[0].setUdf87_Value(actualUdfData[0].getUdf87_Value());udfData[0].setUdf87_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 88:udfData[0].setUdf88_Flag("D"); udfData[0].setUdf88_Value(actualUdfData[0].getUdf88_Value());udfData[0].setUdf88_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 89:udfData[0].setUdf89_Flag("D"); udfData[0].setUdf89_Value(actualUdfData[0].getUdf89_Value());udfData[0].setUdf89_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 90:udfData[0].setUdf90_Flag("D"); udfData[0].setUdf90_Value(actualUdfData[0].getUdf90_Value());udfData[0].setUdf90_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 91:udfData[0].setUdf91_Flag("D"); udfData[0].setUdf91_Value(actualUdfData[0].getUdf91_Value());udfData[0].setUdf91_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 92:udfData[0].setUdf92_Flag("D"); udfData[0].setUdf92_Value(actualUdfData[0].getUdf92_Value());udfData[0].setUdf92_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 93:udfData[0].setUdf93_Flag("D"); udfData[0].setUdf93_Value(actualUdfData[0].getUdf93_Value());udfData[0].setUdf93_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 94:udfData[0].setUdf94_Flag("D"); udfData[0].setUdf94_Value(actualUdfData[0].getUdf94_Value());udfData[0].setUdf94_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 95:udfData[0].setUdf95_Flag("D"); udfData[0].setUdf95_Value(actualUdfData[0].getUdf95_Value());udfData[0].setUdf95_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 96:udfData[0].setUdf96_Flag("D"); udfData[0].setUdf96_Value(actualUdfData[0].getUdf96_Value());udfData[0].setUdf96_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 97:udfData[0].setUdf97_Flag("D"); udfData[0].setUdf97_Value(actualUdfData[0].getUdf97_Value());udfData[0].setUdf97_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 98:udfData[0].setUdf98_Flag("D"); udfData[0].setUdf98_Value(actualUdfData[0].getUdf98_Value());udfData[0].setUdf98_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 99:udfData[0].setUdf99_Flag("D"); udfData[0].setUdf99_Value(actualUdfData[0].getUdf99_Value());udfData[0].setUdf99_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 100:udfData[0].setUdf100_Flag("D"); udfData[0].setUdf100_Value(actualUdfData[0].getUdf100_Value());udfData[0].setUdf100_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				case 101:udfData[0].setUdf101_Flag("D"); udfData[0].setUdf101_Value(actualUdfData[0].getUdf101_Value());udfData[0].setUdf101_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 102:udfData[0].setUdf102_Flag("D"); udfData[0].setUdf102_Value(actualUdfData[0].getUdf102_Value());udfData[0].setUdf102_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 103:udfData[0].setUdf103_Flag("D"); udfData[0].setUdf103_Value(actualUdfData[0].getUdf103_Value());udfData[0].setUdf103_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 104:udfData[0].setUdf104_Flag("D"); udfData[0].setUdf104_Value(actualUdfData[0].getUdf104_Value());udfData[0].setUdf104_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 105:udfData[0].setUdf105_Flag("D"); udfData[0].setUdf105_Value(actualUdfData[0].getUdf105_Value());udfData[0].setUdf105_Label(getUdfLabel(udfDeleteArray[i])); break;

				case 106:udfData[0].setUdf106_Flag("D"); udfData[0].setUdf106_Value(actualUdfData[0].getUdf106_Value());udfData[0].setUdf106_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 107:udfData[0].setUdf107_Flag("D"); udfData[0].setUdf107_Value(actualUdfData[0].getUdf107_Value());udfData[0].setUdf107_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 108:udfData[0].setUdf108_Flag("D"); udfData[0].setUdf108_Value(actualUdfData[0].getUdf108_Value());udfData[0].setUdf108_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 109:udfData[0].setUdf109_Flag("D"); udfData[0].setUdf109_Value(actualUdfData[0].getUdf109_Value());udfData[0].setUdf109_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 110:udfData[0].setUdf110_Flag("D"); udfData[0].setUdf110_Value(actualUdfData[0].getUdf110_Value());udfData[0].setUdf110_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 111:udfData[0].setUdf110_Flag("D"); udfData[0].setUdf111_Value(actualUdfData[0].getUdf111_Value());udfData[0].setUdf111_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 112:udfData[0].setUdf112_Flag("D"); udfData[0].setUdf112_Value(actualUdfData[0].getUdf112_Value());udfData[0].setUdf112_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 113:udfData[0].setUdf113_Flag("D"); udfData[0].setUdf113_Value(actualUdfData[0].getUdf113_Value());udfData[0].setUdf113_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 114:udfData[0].setUdf114_Flag("D"); udfData[0].setUdf114_Value(actualUdfData[0].getUdf114_Value());udfData[0].setUdf114_Label(getUdfLabel(udfDeleteArray[i])); break;
				case 115:udfData[0].setUdf115_Flag("D"); udfData[0].setUdf115_Value(actualUdfData[0].getUdf115_Value());udfData[0].setUdf115_Label(getUdfLabel(udfDeleteArray[i])); break;
				
				default:break;
			}
		}
		}
	}
	
	private void updateUdfDeleteFlag_2(String udfDelete,ICustomerSysXRef stagingXref,ICustomerSysXRef actualXref) {
		
		ILimitXRefUdf2 udfData[]=stagingXref.getXRefUdfData2();
		ILimitXRefUdf2 actualUdfData[] = null;
		if(null != actualXref && null != actualXref.getXRefUdfData2() ) {
		 actualUdfData =actualXref.getXRefUdfData2();
		}
		String udfDeleteArray[]=udfDelete.split(",");
		if(null!=actualUdfData){
		for(int i=0;i<udfDeleteArray.length;i++) {
			switch (Integer.parseInt(udfDeleteArray[i])) {
			case 1:udfData[0].setUdf116_Flag("D"); udfData[0].setUdf116_Value(actualUdfData[0].getUdf116_Value());udfData[0].setUdf116_Label(getUdfLabel_2(udfDeleteArray[i])); break;

			case 2:udfData[0].setUdf117_Flag("D"); udfData[0].setUdf117_Value(actualUdfData[0].getUdf117_Value());udfData[0].setUdf117_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 3:udfData[0].setUdf118_Flag("D"); udfData[0].setUdf118_Value(actualUdfData[0].getUdf118_Value());udfData[0].setUdf118_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 4:udfData[0].setUdf119_Flag("D"); udfData[0].setUdf119_Value(actualUdfData[0].getUdf119_Value());udfData[0].setUdf119_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 5:udfData[0].setUdf120_Flag("D"); udfData[0].setUdf120_Value(actualUdfData[0].getUdf120_Value());udfData[0].setUdf120_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 6:udfData[0].setUdf121_Flag("D"); udfData[0].setUdf121_Value(actualUdfData[0].getUdf121_Value());udfData[0].setUdf121_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 7:udfData[0].setUdf122_Flag("D"); udfData[0].setUdf122_Value(actualUdfData[0].getUdf122_Value());udfData[0].setUdf122_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 8:udfData[0].setUdf123_Flag("D"); udfData[0].setUdf123_Value(actualUdfData[0].getUdf123_Value());udfData[0].setUdf123_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 9:udfData[0].setUdf124_Flag("D"); udfData[0].setUdf124_Value(actualUdfData[0].getUdf124_Value());udfData[0].setUdf124_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 10:udfData[0].setUdf125_Flag("D"); udfData[0].setUdf125_Value(actualUdfData[0].getUdf125_Value());udfData[0].setUdf125_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 11:udfData[0].setUdf126_Flag("D"); udfData[0].setUdf126_Value(actualUdfData[0].getUdf126_Value());udfData[0].setUdf126_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 12:udfData[0].setUdf127_Flag("D"); udfData[0].setUdf127_Value(actualUdfData[0].getUdf127_Value());udfData[0].setUdf127_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 13:udfData[0].setUdf128_Flag("D"); udfData[0].setUdf128_Value(actualUdfData[0].getUdf128_Value());udfData[0].setUdf128_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 14:udfData[0].setUdf129_Flag("D"); udfData[0].setUdf129_Value(actualUdfData[0].getUdf129_Value());udfData[0].setUdf129_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 15:udfData[0].setUdf130_Flag("D"); udfData[0].setUdf130_Value(actualUdfData[0].getUdf130_Value());udfData[0].setUdf130_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 16:udfData[0].setUdf131_Flag("D"); udfData[0].setUdf131_Value(actualUdfData[0].getUdf131_Value());udfData[0].setUdf131_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 17:udfData[0].setUdf132_Flag("D"); udfData[0].setUdf132_Value(actualUdfData[0].getUdf132_Value());udfData[0].setUdf132_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 18:udfData[0].setUdf133_Flag("D"); udfData[0].setUdf133_Value(actualUdfData[0].getUdf133_Value());udfData[0].setUdf133_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 19:udfData[0].setUdf134_Flag("D"); udfData[0].setUdf134_Value(actualUdfData[0].getUdf134_Value());udfData[0].setUdf134_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 20:udfData[0].setUdf135_Flag("D"); udfData[0].setUdf135_Value(actualUdfData[0].getUdf135_Value());udfData[0].setUdf135_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 21:udfData[0].setUdf136_Flag("D"); udfData[0].setUdf136_Value(actualUdfData[0].getUdf136_Value());udfData[0].setUdf136_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 22:udfData[0].setUdf137_Flag("D"); udfData[0].setUdf137_Value(actualUdfData[0].getUdf137_Value());udfData[0].setUdf137_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 23:udfData[0].setUdf138_Flag("D"); udfData[0].setUdf138_Value(actualUdfData[0].getUdf138_Value());udfData[0].setUdf138_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 24:udfData[0].setUdf139_Flag("D"); udfData[0].setUdf139_Value(actualUdfData[0].getUdf139_Value());udfData[0].setUdf139_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 25:udfData[0].setUdf140_Flag("D"); udfData[0].setUdf140_Value(actualUdfData[0].getUdf140_Value());udfData[0].setUdf140_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 26:udfData[0].setUdf141_Flag("D"); udfData[0].setUdf141_Value(actualUdfData[0].getUdf141_Value());udfData[0].setUdf141_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 27:udfData[0].setUdf142_Flag("D"); udfData[0].setUdf142_Value(actualUdfData[0].getUdf142_Value());udfData[0].setUdf142_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 28:udfData[0].setUdf143_Flag("D"); udfData[0].setUdf143_Value(actualUdfData[0].getUdf143_Value());udfData[0].setUdf143_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 29:udfData[0].setUdf144_Flag("D"); udfData[0].setUdf144_Value(actualUdfData[0].getUdf144_Value());udfData[0].setUdf144_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 30:udfData[0].setUdf145_Flag("D"); udfData[0].setUdf145_Value(actualUdfData[0].getUdf145_Value());udfData[0].setUdf145_Label(getUdfLabel_2(udfDeleteArray[i])); break;
		
			case 31:udfData[0].setUdf146_Flag("D"); udfData[0].setUdf146_Value(actualUdfData[0].getUdf146_Value());udfData[0].setUdf146_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 32:udfData[0].setUdf147_Flag("D"); udfData[0].setUdf147_Value(actualUdfData[0].getUdf147_Value());udfData[0].setUdf147_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 33:udfData[0].setUdf148_Flag("D"); udfData[0].setUdf148_Value(actualUdfData[0].getUdf148_Value());udfData[0].setUdf148_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 34:udfData[0].setUdf149_Flag("D"); udfData[0].setUdf149_Value(actualUdfData[0].getUdf149_Value());udfData[0].setUdf149_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 35:udfData[0].setUdf150_Flag("D"); udfData[0].setUdf150_Value(actualUdfData[0].getUdf150_Value());udfData[0].setUdf150_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 36:udfData[0].setUdf151_Flag("D"); udfData[0].setUdf151_Value(actualUdfData[0].getUdf151_Value());udfData[0].setUdf151_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 37:udfData[0].setUdf152_Flag("D"); udfData[0].setUdf152_Value(actualUdfData[0].getUdf152_Value());udfData[0].setUdf152_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 38:udfData[0].setUdf153_Flag("D"); udfData[0].setUdf153_Value(actualUdfData[0].getUdf153_Value());udfData[0].setUdf153_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 39:udfData[0].setUdf154_Flag("D"); udfData[0].setUdf154_Value(actualUdfData[0].getUdf154_Value());udfData[0].setUdf154_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 40:udfData[0].setUdf155_Flag("D"); udfData[0].setUdf155_Value(actualUdfData[0].getUdf155_Value());udfData[0].setUdf155_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 41:udfData[0].setUdf156_Flag("D"); udfData[0].setUdf156_Value(actualUdfData[0].getUdf156_Value());udfData[0].setUdf156_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 42:udfData[0].setUdf157_Flag("D"); udfData[0].setUdf157_Value(actualUdfData[0].getUdf157_Value());udfData[0].setUdf157_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 43:udfData[0].setUdf158_Flag("D"); udfData[0].setUdf158_Value(actualUdfData[0].getUdf158_Value());udfData[0].setUdf158_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 44:udfData[0].setUdf159_Flag("D"); udfData[0].setUdf159_Value(actualUdfData[0].getUdf159_Value());udfData[0].setUdf159_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 45:udfData[0].setUdf160_Flag("D"); udfData[0].setUdf160_Value(actualUdfData[0].getUdf160_Value());udfData[0].setUdf160_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 46:udfData[0].setUdf161_Flag("D"); udfData[0].setUdf161_Value(actualUdfData[0].getUdf161_Value());udfData[0].setUdf161_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 47:udfData[0].setUdf162_Flag("D"); udfData[0].setUdf162_Value(actualUdfData[0].getUdf162_Value());udfData[0].setUdf162_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 48:udfData[0].setUdf163_Flag("D"); udfData[0].setUdf163_Value(actualUdfData[0].getUdf163_Value());udfData[0].setUdf163_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 49:udfData[0].setUdf164_Flag("D"); udfData[0].setUdf164_Value(actualUdfData[0].getUdf164_Value());udfData[0].setUdf164_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 50:udfData[0].setUdf165_Flag("D"); udfData[0].setUdf165_Value(actualUdfData[0].getUdf165_Value());udfData[0].setUdf165_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 51:udfData[0].setUdf166_Flag("D"); udfData[0].setUdf166_Value(actualUdfData[0].getUdf166_Value());udfData[0].setUdf166_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 52:udfData[0].setUdf167_Flag("D"); udfData[0].setUdf167_Value(actualUdfData[0].getUdf167_Value());udfData[0].setUdf167_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 53:udfData[0].setUdf168_Flag("D"); udfData[0].setUdf168_Value(actualUdfData[0].getUdf168_Value());udfData[0].setUdf168_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 54:udfData[0].setUdf169_Flag("D"); udfData[0].setUdf169_Value(actualUdfData[0].getUdf169_Value());udfData[0].setUdf169_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 55:udfData[0].setUdf170_Flag("D"); udfData[0].setUdf170_Value(actualUdfData[0].getUdf170_Value());udfData[0].setUdf170_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 56:udfData[0].setUdf171_Flag("D"); udfData[0].setUdf171_Value(actualUdfData[0].getUdf171_Value());udfData[0].setUdf171_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 57:udfData[0].setUdf172_Flag("D"); udfData[0].setUdf172_Value(actualUdfData[0].getUdf172_Value());udfData[0].setUdf172_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 58:udfData[0].setUdf173_Flag("D"); udfData[0].setUdf173_Value(actualUdfData[0].getUdf173_Value());udfData[0].setUdf173_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 59:udfData[0].setUdf174_Flag("D"); udfData[0].setUdf174_Value(actualUdfData[0].getUdf174_Value());udfData[0].setUdf174_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 60:udfData[0].setUdf175_Flag("D"); udfData[0].setUdf175_Value(actualUdfData[0].getUdf175_Value());udfData[0].setUdf175_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 61:udfData[0].setUdf176_Flag("D"); udfData[0].setUdf176_Value(actualUdfData[0].getUdf176_Value());udfData[0].setUdf176_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 62:udfData[0].setUdf177_Flag("D"); udfData[0].setUdf177_Value(actualUdfData[0].getUdf177_Value());udfData[0].setUdf177_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 63:udfData[0].setUdf178_Flag("D"); udfData[0].setUdf178_Value(actualUdfData[0].getUdf178_Value());udfData[0].setUdf178_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 64:udfData[0].setUdf179_Flag("D"); udfData[0].setUdf179_Value(actualUdfData[0].getUdf179_Value());udfData[0].setUdf179_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 65:udfData[0].setUdf180_Flag("D"); udfData[0].setUdf180_Value(actualUdfData[0].getUdf180_Value());udfData[0].setUdf180_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 66:udfData[0].setUdf181_Flag("D"); udfData[0].setUdf181_Value(actualUdfData[0].getUdf181_Value());udfData[0].setUdf181_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 67:udfData[0].setUdf182_Flag("D"); udfData[0].setUdf182_Value(actualUdfData[0].getUdf182_Value());udfData[0].setUdf182_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 68:udfData[0].setUdf183_Flag("D"); udfData[0].setUdf183_Value(actualUdfData[0].getUdf183_Value());udfData[0].setUdf183_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 69:udfData[0].setUdf184_Flag("D"); udfData[0].setUdf184_Value(actualUdfData[0].getUdf184_Value());udfData[0].setUdf184_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 70:udfData[0].setUdf185_Flag("D"); udfData[0].setUdf185_Value(actualUdfData[0].getUdf185_Value());udfData[0].setUdf185_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 71:udfData[0].setUdf186_Flag("D"); udfData[0].setUdf186_Value(actualUdfData[0].getUdf186_Value());udfData[0].setUdf186_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 72:udfData[0].setUdf187_Flag("D"); udfData[0].setUdf187_Value(actualUdfData[0].getUdf187_Value());udfData[0].setUdf187_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 73:udfData[0].setUdf188_Flag("D"); udfData[0].setUdf188_Value(actualUdfData[0].getUdf188_Value());udfData[0].setUdf188_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 74:udfData[0].setUdf189_Flag("D"); udfData[0].setUdf189_Value(actualUdfData[0].getUdf189_Value());udfData[0].setUdf189_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
			case 75:udfData[0].setUdf190_Flag("D"); udfData[0].setUdf190_Value(actualUdfData[0].getUdf190_Value());udfData[0].setUdf190_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 76:udfData[0].setUdf191_Flag("D"); udfData[0].setUdf191_Value(actualUdfData[0].getUdf191_Value());udfData[0].setUdf191_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 77:udfData[0].setUdf192_Flag("D"); udfData[0].setUdf192_Value(actualUdfData[0].getUdf192_Value());udfData[0].setUdf192_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 78:udfData[0].setUdf193_Flag("D"); udfData[0].setUdf193_Value(actualUdfData[0].getUdf193_Value());udfData[0].setUdf193_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 79:udfData[0].setUdf194_Flag("D"); udfData[0].setUdf194_Value(actualUdfData[0].getUdf194_Value());udfData[0].setUdf194_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 80:udfData[0].setUdf195_Flag("D"); udfData[0].setUdf195_Value(actualUdfData[0].getUdf195_Value());udfData[0].setUdf195_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 81:udfData[0].setUdf196_Flag("D"); udfData[0].setUdf196_Value(actualUdfData[0].getUdf196_Value());udfData[0].setUdf196_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 82:udfData[0].setUdf197_Flag("D"); udfData[0].setUdf197_Value(actualUdfData[0].getUdf197_Value());udfData[0].setUdf197_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 83:udfData[0].setUdf198_Flag("D"); udfData[0].setUdf198_Value(actualUdfData[0].getUdf198_Value());udfData[0].setUdf198_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 84:udfData[0].setUdf199_Flag("D"); udfData[0].setUdf199_Value(actualUdfData[0].getUdf199_Value());udfData[0].setUdf199_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			case 85:udfData[0].setUdf200_Flag("D"); udfData[0].setUdf200_Value(actualUdfData[0].getUdf200_Value());udfData[0].setUdf200_Label(getUdfLabel_2(udfDeleteArray[i])); break;
			
	            case 86:udfData[0].setUdf201_Flag("D"); udfData[0].setUdf201_Value(actualUdfData[0].getUdf201_Value());udfData[0].setUdf201_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 87:udfData[0].setUdf202_Flag("D"); udfData[0].setUdf202_Value(actualUdfData[0].getUdf202_Value());udfData[0].setUdf202_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 88:udfData[0].setUdf203_Flag("D"); udfData[0].setUdf203_Value(actualUdfData[0].getUdf203_Value());udfData[0].setUdf203_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 89:udfData[0].setUdf204_Flag("D"); udfData[0].setUdf204_Value(actualUdfData[0].getUdf204_Value());udfData[0].setUdf204_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 90:udfData[0].setUdf205_Flag("D"); udfData[0].setUdf205_Value(actualUdfData[0].getUdf205_Value());udfData[0].setUdf205_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 91:udfData[0].setUdf206_Flag("D"); udfData[0].setUdf206_Value(actualUdfData[0].getUdf206_Value());udfData[0].setUdf206_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 92:udfData[0].setUdf207_Flag("D"); udfData[0].setUdf207_Value(actualUdfData[0].getUdf207_Value());udfData[0].setUdf207_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 93:udfData[0].setUdf208_Flag("D"); udfData[0].setUdf208_Value(actualUdfData[0].getUdf208_Value());udfData[0].setUdf208_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 94:udfData[0].setUdf209_Flag("D"); udfData[0].setUdf209_Value(actualUdfData[0].getUdf209_Value());udfData[0].setUdf209_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				
				case 95:udfData[0].setUdf210_Flag("D"); udfData[0].setUdf210_Value(actualUdfData[0].getUdf210_Value());udfData[0].setUdf210_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 96:udfData[0].setUdf211_Flag("D"); udfData[0].setUdf211_Value(actualUdfData[0].getUdf211_Value());udfData[0].setUdf211_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 97:udfData[0].setUdf212_Flag("D"); udfData[0].setUdf212_Value(actualUdfData[0].getUdf212_Value());udfData[0].setUdf212_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 98:udfData[0].setUdf213_Flag("D"); udfData[0].setUdf213_Value(actualUdfData[0].getUdf213_Value());udfData[0].setUdf213_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 99:udfData[0].setUdf214_Flag("D"); udfData[0].setUdf214_Value(actualUdfData[0].getUdf214_Value());udfData[0].setUdf214_Label(getUdfLabel_2(udfDeleteArray[i])); break;
				case 100:udfData[0].setUdf215_Flag("D"); udfData[0].setUdf215_Value(actualUdfData[0].getUdf215_Value());udfData[0].setUdf215_Label(getUdfLabel_2(udfDeleteArray[i])); break;
										
				default:break;
			}
		}
		}
	}
	private String getUdfLabel(String sequence) {
		//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
	//	List udfList = udfDao.getUdfByModuleIdAndStatus("3", "Freezed");
		
		List udfList = udfDao.getUdfByModuleIdAndStatus("3", "ACTIVE");
		List udfLbValList = new ArrayList();
		
		if(udfList!=null || udfList.size() != 0)
		{
			int size = udfList.size();
			Collections.sort(udfList, new UDFComparator());
			IUdf udf;
			String fieldName,id;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList.get(i);
					fieldName=udf.getFieldName();
					id=Long.toString(udf.getSequence());
					LabelValueBean lvBean1 = new LabelValueBean(fieldName, id);
					udfLbValList.add(lvBean1);
				}
				for(int i=0;i<udfLbValList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList.get(i);
           	 		if(sequence.equals(lvBean.getValue())) {
           	 			return lvBean.getLabel();
           	 		}
           	 	}			
		}
		return null;
	}
	
	private String getUdfLabel_2(String sequence) {
		//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
		List udfList_2 = udfDao.getUdfByModuleIdAndStatus("4", "ACTIVE");
		List udfLbValList_2 = new ArrayList();
		
		if( null !=udfList_2 || 0 != udfList_2.size())
		{
			int size = udfList_2.size();
			Collections.sort(udfList_2, new UDFComparator());
			IUdf udf;
			String fieldName,id;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList_2.get(i);
					fieldName=udf.getFieldName();
					id=Long.toString(udf.getSequence()+115);
					LabelValueBean lvBean1 = new LabelValueBean(fieldName, id);
					udfLbValList_2.add(lvBean1);
				}
				for(int i=0;i<udfLbValList_2.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList_2.get(i);
           	 	int intSequance= 0;
              	 intSequance=Integer.parseInt(sequence)+115;
       		String	Newsequence= String.valueOf(intSequance);
           	 		if(Newsequence.equals(lvBean.getValue())) {
           	 			return lvBean.getLabel();
           	 		}
           	 	}			
		}
		return null;
	}
	private void updateUdfAddFlag(String addUdf, ICustomerSysXRef stagingXref) {
		ILimitXRefUdf udfData[]=stagingXref.getXRefUdfData();
		DefaultLogger.debug(this, "in UPDATEUDF staging udf id "+stagingXref.getXRefID());
		DefaultLogger.debug(this, "in UPDATEUDF staging addudf no "+addUdf);
		DefaultLogger.debug(this, "in UPDATEUDF staging integer addudf no "+Integer.parseInt(addUdf));
		switch (Integer.parseInt(addUdf)) {
		case 1:udfData[0].setUdf1_Flag("A");udfData[0].setUdf1_Label(getUdfLabel(addUdf)); break;
		case 2:udfData[0].setUdf2_Flag("A");udfData[0].setUdf2_Label(getUdfLabel(addUdf)); break;
		case 3:udfData[0].setUdf3_Flag("A");udfData[0].setUdf3_Label(getUdfLabel(addUdf)); break;
		case 4:udfData[0].setUdf4_Flag("A");udfData[0].setUdf4_Label(getUdfLabel(addUdf)); break;
		case 5:udfData[0].setUdf5_Flag("A");udfData[0].setUdf5_Label(getUdfLabel(addUdf)); break;
		case 6:udfData[0].setUdf6_Flag("A");udfData[0].setUdf6_Label(getUdfLabel(addUdf)); break;
		case 7:udfData[0].setUdf7_Flag("A");udfData[0].setUdf7_Label(getUdfLabel(addUdf)); break;
		case 8:udfData[0].setUdf8_Flag("A");udfData[0].setUdf8_Label(getUdfLabel(addUdf)); break;
		case 9:udfData[0].setUdf9_Flag("A");udfData[0].setUdf9_Label(getUdfLabel(addUdf)); break;
		
		case 10:udfData[0].setUdf10_Flag("A");udfData[0].setUdf10_Label(getUdfLabel(addUdf)); break;
		case 11:udfData[0].setUdf11_Flag("A");udfData[0].setUdf11_Label(getUdfLabel(addUdf)); break;
		case 12:udfData[0].setUdf12_Flag("A");udfData[0].setUdf12_Label(getUdfLabel(addUdf)); break;
		case 13:udfData[0].setUdf13_Flag("A");udfData[0].setUdf13_Label(getUdfLabel(addUdf)); break;
		case 14:udfData[0].setUdf14_Flag("A");udfData[0].setUdf14_Label(getUdfLabel(addUdf)); break;
		case 15:udfData[0].setUdf15_Flag("A");udfData[0].setUdf15_Label(getUdfLabel(addUdf)); break;
		case 16:udfData[0].setUdf16_Flag("A");udfData[0].setUdf16_Label(getUdfLabel(addUdf)); break;
		case 17:udfData[0].setUdf17_Flag("A");udfData[0].setUdf17_Label(getUdfLabel(addUdf)); break;
		case 18:udfData[0].setUdf18_Flag("A");udfData[0].setUdf18_Label(getUdfLabel(addUdf)); break;
		case 19:udfData[0].setUdf19_Flag("A");udfData[0].setUdf19_Label(getUdfLabel(addUdf)); break;
		
		case 20:udfData[0].setUdf20_Flag("A");udfData[0].setUdf20_Label(getUdfLabel(addUdf)); break;
		case 21:udfData[0].setUdf21_Flag("A");udfData[0].setUdf21_Label(getUdfLabel(addUdf)); break;
		case 22:udfData[0].setUdf22_Flag("A");udfData[0].setUdf22_Label(getUdfLabel(addUdf)); break;
		case 23:udfData[0].setUdf23_Flag("A");udfData[0].setUdf23_Label(getUdfLabel(addUdf)); break;
		case 24:udfData[0].setUdf24_Flag("A");udfData[0].setUdf24_Label(getUdfLabel(addUdf)); break;
		case 25:udfData[0].setUdf25_Flag("A");udfData[0].setUdf25_Label(getUdfLabel(addUdf)); break;
		case 26:udfData[0].setUdf26_Flag("A");udfData[0].setUdf26_Label(getUdfLabel(addUdf)); break;
		case 27:udfData[0].setUdf27_Flag("A");udfData[0].setUdf27_Label(getUdfLabel(addUdf)); break;
		case 28:udfData[0].setUdf28_Flag("A");udfData[0].setUdf28_Label(getUdfLabel(addUdf)); break;
		case 29:udfData[0].setUdf29_Flag("A");udfData[0].setUdf29_Label(getUdfLabel(addUdf)); break;
		
		case 30:udfData[0].setUdf30_Flag("A");udfData[0].setUdf30_Label(getUdfLabel(addUdf)); break;
		case 31:udfData[0].setUdf31_Flag("A");udfData[0].setUdf31_Label(getUdfLabel(addUdf)); break;
		case 32:udfData[0].setUdf32_Flag("A");udfData[0].setUdf32_Label(getUdfLabel(addUdf)); break;
		case 33:udfData[0].setUdf33_Flag("A");udfData[0].setUdf33_Label(getUdfLabel(addUdf)); break;
		case 34:udfData[0].setUdf34_Flag("A");udfData[0].setUdf34_Label(getUdfLabel(addUdf)); break;
		case 35:udfData[0].setUdf35_Flag("A");udfData[0].setUdf35_Label(getUdfLabel(addUdf)); break;
		case 36:udfData[0].setUdf36_Flag("A");udfData[0].setUdf36_Label(getUdfLabel(addUdf)); break;
		case 37:udfData[0].setUdf37_Flag("A");udfData[0].setUdf37_Label(getUdfLabel(addUdf)); break;
		case 38:udfData[0].setUdf38_Flag("A");udfData[0].setUdf38_Label(getUdfLabel(addUdf)); break;
		case 39:udfData[0].setUdf39_Flag("A");udfData[0].setUdf39_Label(getUdfLabel(addUdf)); break;
		
		case 40:udfData[0].setUdf40_Flag("A");udfData[0].setUdf40_Label(getUdfLabel(addUdf)); break;
		case 41:udfData[0].setUdf41_Flag("A");udfData[0].setUdf41_Label(getUdfLabel(addUdf)); break;
		case 42:udfData[0].setUdf42_Flag("A");udfData[0].setUdf42_Label(getUdfLabel(addUdf)); break;
		case 43:udfData[0].setUdf43_Flag("A");udfData[0].setUdf43_Label(getUdfLabel(addUdf)); break;
		case 44:udfData[0].setUdf44_Flag("A");udfData[0].setUdf44_Label(getUdfLabel(addUdf)); break;
		case 45:udfData[0].setUdf45_Flag("A");udfData[0].setUdf45_Label(getUdfLabel(addUdf)); break;
		case 46:udfData[0].setUdf46_Flag("A");udfData[0].setUdf46_Label(getUdfLabel(addUdf)); break;
		case 47:udfData[0].setUdf47_Flag("A");udfData[0].setUdf47_Label(getUdfLabel(addUdf)); break;
		case 48:udfData[0].setUdf48_Flag("A");udfData[0].setUdf48_Label(getUdfLabel(addUdf)); break;
		case 49:udfData[0].setUdf49_Flag("A");udfData[0].setUdf49_Label(getUdfLabel(addUdf)); break;
		
		case 50:udfData[0].setUdf50_Flag("A");udfData[0].setUdf50_Label(getUdfLabel(addUdf)); break;
		case 51:udfData[0].setUdf51_Flag("A");udfData[0].setUdf51_Label(getUdfLabel(addUdf)); break;
		case 52:udfData[0].setUdf52_Flag("A");udfData[0].setUdf52_Label(getUdfLabel(addUdf)); break;
		case 53:udfData[0].setUdf53_Flag("A");udfData[0].setUdf53_Label(getUdfLabel(addUdf)); break;
		case 54:udfData[0].setUdf54_Flag("A");udfData[0].setUdf54_Label(getUdfLabel(addUdf)); break;
		case 55:udfData[0].setUdf55_Flag("A");udfData[0].setUdf55_Label(getUdfLabel(addUdf)); break;
		case 56:udfData[0].setUdf56_Flag("A");udfData[0].setUdf56_Label(getUdfLabel(addUdf)); break;
		case 57:udfData[0].setUdf57_Flag("A");udfData[0].setUdf57_Label(getUdfLabel(addUdf)); break;
		case 58:udfData[0].setUdf58_Flag("A");udfData[0].setUdf58_Label(getUdfLabel(addUdf)); break;
		case 59:udfData[0].setUdf59_Flag("A");udfData[0].setUdf59_Label(getUdfLabel(addUdf)); break;
		
		case 60:udfData[0].setUdf60_Flag("A");udfData[0].setUdf60_Label(getUdfLabel(addUdf)); break;
		case 61:udfData[0].setUdf61_Flag("A");udfData[0].setUdf61_Label(getUdfLabel(addUdf)); break;
		case 62:udfData[0].setUdf62_Flag("A");udfData[0].setUdf62_Label(getUdfLabel(addUdf)); break;
		case 63:udfData[0].setUdf63_Flag("A");udfData[0].setUdf63_Label(getUdfLabel(addUdf)); break;
		case 64:udfData[0].setUdf64_Flag("A");udfData[0].setUdf64_Label(getUdfLabel(addUdf)); break;
		case 65:udfData[0].setUdf65_Flag("A");udfData[0].setUdf65_Label(getUdfLabel(addUdf)); break;
		case 66:udfData[0].setUdf66_Flag("A");udfData[0].setUdf66_Label(getUdfLabel(addUdf)); break;
		case 67:udfData[0].setUdf67_Flag("A");udfData[0].setUdf67_Label(getUdfLabel(addUdf)); break;
		case 68:udfData[0].setUdf68_Flag("A");udfData[0].setUdf68_Label(getUdfLabel(addUdf)); break;
		case 69:udfData[0].setUdf69_Flag("A");udfData[0].setUdf69_Label(getUdfLabel(addUdf)); break;
		
		case 70:udfData[0].setUdf70_Flag("A");udfData[0].setUdf70_Label(getUdfLabel(addUdf)); break;
		case 71:udfData[0].setUdf71_Flag("A");udfData[0].setUdf71_Label(getUdfLabel(addUdf)); break;
		case 72:udfData[0].setUdf72_Flag("A");udfData[0].setUdf72_Label(getUdfLabel(addUdf)); break;
		case 73:udfData[0].setUdf73_Flag("A");udfData[0].setUdf73_Label(getUdfLabel(addUdf)); break;
		case 74:udfData[0].setUdf74_Flag("A");udfData[0].setUdf74_Label(getUdfLabel(addUdf)); break;
		case 75:udfData[0].setUdf75_Flag("A");udfData[0].setUdf75_Label(getUdfLabel(addUdf)); break;
		case 76:udfData[0].setUdf76_Flag("A");udfData[0].setUdf76_Label(getUdfLabel(addUdf)); break;
		case 77:udfData[0].setUdf77_Flag("A");udfData[0].setUdf77_Label(getUdfLabel(addUdf)); break;
		case 78:udfData[0].setUdf78_Flag("A");udfData[0].setUdf78_Label(getUdfLabel(addUdf)); break;
		case 79:udfData[0].setUdf79_Flag("A");udfData[0].setUdf79_Label(getUdfLabel(addUdf)); break;
		
		case 80:udfData[0].setUdf80_Flag("A");udfData[0].setUdf80_Label(getUdfLabel(addUdf)); break;
		case 81:udfData[0].setUdf81_Flag("A");udfData[0].setUdf81_Label(getUdfLabel(addUdf)); break;
		case 82:udfData[0].setUdf82_Flag("A");udfData[0].setUdf82_Label(getUdfLabel(addUdf)); break;
		case 83:udfData[0].setUdf83_Flag("A");udfData[0].setUdf83_Label(getUdfLabel(addUdf)); break;
		case 84:udfData[0].setUdf84_Flag("A");udfData[0].setUdf84_Label(getUdfLabel(addUdf)); break;
		case 85:udfData[0].setUdf85_Flag("A");udfData[0].setUdf85_Label(getUdfLabel(addUdf)); break;
		case 86:udfData[0].setUdf86_Flag("A");udfData[0].setUdf86_Label(getUdfLabel(addUdf)); break;
		case 87:udfData[0].setUdf87_Flag("A");udfData[0].setUdf87_Label(getUdfLabel(addUdf)); break;
		case 88:udfData[0].setUdf88_Flag("A");udfData[0].setUdf88_Label(getUdfLabel(addUdf)); break;
		case 89:udfData[0].setUdf89_Flag("A");udfData[0].setUdf89_Label(getUdfLabel(addUdf)); break;
		
		case 90:udfData[0].setUdf90_Flag("A");udfData[0].setUdf90_Label(getUdfLabel(addUdf)); break;
		case 91:udfData[0].setUdf91_Flag("A");udfData[0].setUdf91_Label(getUdfLabel(addUdf)); break;
		case 92:udfData[0].setUdf92_Flag("A");udfData[0].setUdf92_Label(getUdfLabel(addUdf)); break;
		case 93:udfData[0].setUdf93_Flag("A");udfData[0].setUdf93_Label(getUdfLabel(addUdf)); break;
		case 94:udfData[0].setUdf94_Flag("A");udfData[0].setUdf94_Label(getUdfLabel(addUdf)); break;
		case 95:udfData[0].setUdf95_Flag("A");udfData[0].setUdf95_Label(getUdfLabel(addUdf)); break;
		case 96:udfData[0].setUdf96_Flag("A");udfData[0].setUdf96_Label(getUdfLabel(addUdf)); break;
		case 97:udfData[0].setUdf97_Flag("A");udfData[0].setUdf97_Label(getUdfLabel(addUdf)); break;
		case 98:udfData[0].setUdf98_Flag("A");udfData[0].setUdf98_Label(getUdfLabel(addUdf)); break;
		case 99:udfData[0].setUdf99_Flag("A");udfData[0].setUdf99_Label(getUdfLabel(addUdf)); break;
		case 100:udfData[0].setUdf100_Flag("A");udfData[0].setUdf100_Label(getUdfLabel(addUdf)); break;
		
		case 101:udfData[0].setUdf101_Flag("A");udfData[0].setUdf101_Label(getUdfLabel(addUdf)); break;
		case 102:udfData[0].setUdf102_Flag("A");udfData[0].setUdf102_Label(getUdfLabel(addUdf)); break;
		case 103:udfData[0].setUdf103_Flag("A");udfData[0].setUdf103_Label(getUdfLabel(addUdf)); break;
		case 104:udfData[0].setUdf104_Flag("A");udfData[0].setUdf104_Label(getUdfLabel(addUdf)); break;
		case 105:udfData[0].setUdf105_Flag("A");udfData[0].setUdf105_Label(getUdfLabel(addUdf)); break;
		case 106:udfData[0].setUdf106_Flag("A");udfData[0].setUdf106_Label(getUdfLabel(addUdf)); break;
		case 107:udfData[0].setUdf107_Flag("A");udfData[0].setUdf107_Label(getUdfLabel(addUdf)); break;
		case 108:udfData[0].setUdf108_Flag("A");udfData[0].setUdf108_Label(getUdfLabel(addUdf)); break;
		case 109:udfData[0].setUdf109_Flag("A");udfData[0].setUdf109_Label(getUdfLabel(addUdf)); break;
		case 110:udfData[0].setUdf110_Flag("A");udfData[0].setUdf110_Label(getUdfLabel(addUdf)); break;
		case 111:udfData[0].setUdf111_Flag("A");udfData[0].setUdf111_Label(getUdfLabel(addUdf)); break;
		case 112:udfData[0].setUdf112_Flag("A");udfData[0].setUdf112_Label(getUdfLabel(addUdf)); break;
		case 113:udfData[0].setUdf113_Flag("A");udfData[0].setUdf113_Label(getUdfLabel(addUdf)); break;
		case 114:udfData[0].setUdf114_Flag("A");udfData[0].setUdf114_Label(getUdfLabel(addUdf)); break;
		case 115:udfData[0].setUdf115_Flag("A");udfData[0].setUdf115_Label(getUdfLabel(addUdf)); break;
			
		default:break;
		}
	}
	
	private void updateUdfAddFlag_2(String addUdf, ICustomerSysXRef stagingXref) {
		ILimitXRefUdf2 udfData[]=stagingXref.getXRefUdfData2();
		DefaultLogger.debug(this, "in UPDATEUDF staging udf id "+stagingXref.getXRefID());
		DefaultLogger.debug(this, "in UPDATEUDF staging addudf no "+addUdf);
		DefaultLogger.debug(this, "in UPDATEUDF staging integer addudf no "+Integer.parseInt(addUdf));
		switch (Integer.parseInt(addUdf)) {
        
		
		case 1:udfData[0].setUdf116_Flag("A");udfData[0].setUdf116_Label(getUdfLabel_2(addUdf)); break;
		case 2:udfData[0].setUdf117_Flag("A");udfData[0].setUdf117_Label(getUdfLabel_2(addUdf)); break;
		case 3:udfData[0].setUdf118_Flag("A");udfData[0].setUdf118_Label(getUdfLabel_2(addUdf)); break;
		case 4:udfData[0].setUdf119_Flag("A");udfData[0].setUdf119_Label(getUdfLabel_2(addUdf)); break;
		case 5:udfData[0].setUdf120_Flag("A");udfData[0].setUdf120_Label(getUdfLabel_2(addUdf)); break;
		case 6:udfData[0].setUdf121_Flag("A");udfData[0].setUdf121_Label(getUdfLabel_2(addUdf)); break;
		case 7:udfData[0].setUdf122_Flag("A");udfData[0].setUdf122_Label(getUdfLabel_2(addUdf)); break;
		case 8:udfData[0].setUdf123_Flag("A");udfData[0].setUdf123_Label(getUdfLabel_2(addUdf)); break;
		case 9:udfData[0].setUdf124_Flag("A");udfData[0].setUdf124_Label(getUdfLabel_2(addUdf)); break;
		case 10:udfData[0].setUdf125_Flag("A");udfData[0].setUdf125_Label(getUdfLabel_2(addUdf)); break;
		
		case 11:udfData[0].setUdf126_Flag("A");udfData[0].setUdf126_Label(getUdfLabel_2(addUdf)); break;
		case 12:udfData[0].setUdf127_Flag("A");udfData[0].setUdf127_Label(getUdfLabel_2(addUdf)); break;
		case 13:udfData[0].setUdf128_Flag("A");udfData[0].setUdf128_Label(getUdfLabel_2(addUdf)); break;
		case 14:udfData[0].setUdf129_Flag("A");udfData[0].setUdf129_Label(getUdfLabel_2(addUdf)); break;
		case 15:udfData[0].setUdf130_Flag("A");udfData[0].setUdf130_Label(getUdfLabel_2(addUdf)); break;
		case 16:udfData[0].setUdf131_Flag("A");udfData[0].setUdf131_Label(getUdfLabel_2(addUdf)); break;
		case 17:udfData[0].setUdf132_Flag("A");udfData[0].setUdf132_Label(getUdfLabel_2(addUdf)); break;
		case 18:udfData[0].setUdf133_Flag("A");udfData[0].setUdf133_Label(getUdfLabel_2(addUdf)); break;
		case 19:udfData[0].setUdf134_Flag("A");udfData[0].setUdf134_Label(getUdfLabel_2(addUdf)); break;
		case 20:udfData[0].setUdf135_Flag("A");udfData[0].setUdf135_Label(getUdfLabel_2(addUdf)); break;
		case 21:udfData[0].setUdf136_Flag("A");udfData[0].setUdf136_Label(getUdfLabel_2(addUdf)); break;
		case 22:udfData[0].setUdf137_Flag("A");udfData[0].setUdf137_Label(getUdfLabel_2(addUdf)); break;
		case 23:udfData[0].setUdf138_Flag("A");udfData[0].setUdf138_Label(getUdfLabel_2(addUdf)); break;
		case 24:udfData[0].setUdf139_Flag("A");udfData[0].setUdf139_Label(getUdfLabel_2(addUdf)); break;
		case 25:udfData[0].setUdf140_Flag("A");udfData[0].setUdf140_Label(getUdfLabel_2(addUdf)); break;
		case 26:udfData[0].setUdf141_Flag("A");udfData[0].setUdf141_Label(getUdfLabel_2(addUdf)); break;
		case 27:udfData[0].setUdf142_Flag("A");udfData[0].setUdf142_Label(getUdfLabel_2(addUdf)); break;
		case 28:udfData[0].setUdf143_Flag("A");udfData[0].setUdf143_Label(getUdfLabel_2(addUdf)); break;
		case 29:udfData[0].setUdf144_Flag("A");udfData[0].setUdf144_Label(getUdfLabel_2(addUdf)); break;
		case 30:udfData[0].setUdf145_Flag("A");udfData[0].setUdf145_Label(getUdfLabel_2(addUdf)); break;
		
		case 31:udfData[0].setUdf146_Flag("A");udfData[0].setUdf146_Label(getUdfLabel_2(addUdf)); break;
		case 32:udfData[0].setUdf147_Flag("A");udfData[0].setUdf147_Label(getUdfLabel_2(addUdf)); break;
		case 33:udfData[0].setUdf148_Flag("A");udfData[0].setUdf148_Label(getUdfLabel_2(addUdf)); break;
		case 34:udfData[0].setUdf149_Flag("A");udfData[0].setUdf149_Label(getUdfLabel_2(addUdf)); break;
		case 35:udfData[0].setUdf150_Flag("A");udfData[0].setUdf150_Label(getUdfLabel_2(addUdf)); break;
		case 36:udfData[0].setUdf151_Flag("A");udfData[0].setUdf151_Label(getUdfLabel_2(addUdf)); break;
		case 37:udfData[0].setUdf152_Flag("A");udfData[0].setUdf152_Label(getUdfLabel_2(addUdf)); break;
		case 38:udfData[0].setUdf153_Flag("A");udfData[0].setUdf153_Label(getUdfLabel_2(addUdf)); break;
		case 39:udfData[0].setUdf154_Flag("A");udfData[0].setUdf154_Label(getUdfLabel_2(addUdf)); break;
		case 40:udfData[0].setUdf155_Flag("A");udfData[0].setUdf155_Label(getUdfLabel_2(addUdf)); break;
		case 41:udfData[0].setUdf156_Flag("A");udfData[0].setUdf156_Label(getUdfLabel_2(addUdf)); break;
		case 42:udfData[0].setUdf157_Flag("A");udfData[0].setUdf157_Label(getUdfLabel_2(addUdf)); break;
		case 43:udfData[0].setUdf158_Flag("A");udfData[0].setUdf158_Label(getUdfLabel_2(addUdf)); break;
		case 44:udfData[0].setUdf159_Flag("A");udfData[0].setUdf159_Label(getUdfLabel_2(addUdf)); break;
		case 45:udfData[0].setUdf160_Flag("A");udfData[0].setUdf160_Label(getUdfLabel_2(addUdf)); break;
		case 46:udfData[0].setUdf161_Flag("A");udfData[0].setUdf161_Label(getUdfLabel_2(addUdf)); break;
		case 47:udfData[0].setUdf162_Flag("A");udfData[0].setUdf162_Label(getUdfLabel_2(addUdf)); break;
		case 48:udfData[0].setUdf163_Flag("A");udfData[0].setUdf163_Label(getUdfLabel_2(addUdf)); break;
		case 49:udfData[0].setUdf164_Flag("A");udfData[0].setUdf164_Label(getUdfLabel_2(addUdf)); break;
		case 50:udfData[0].setUdf165_Flag("A");udfData[0].setUdf165_Label(getUdfLabel_2(addUdf)); break;
		
		case 51:udfData[0].setUdf166_Flag("A");udfData[0].setUdf166_Label(getUdfLabel_2(addUdf)); break;
		case 52:udfData[0].setUdf167_Flag("A");udfData[0].setUdf167_Label(getUdfLabel_2(addUdf)); break;
		case 53:udfData[0].setUdf168_Flag("A");udfData[0].setUdf168_Label(getUdfLabel_2(addUdf)); break;
		case 54:udfData[0].setUdf169_Flag("A");udfData[0].setUdf169_Label(getUdfLabel_2(addUdf)); break;
		case 55:udfData[0].setUdf170_Flag("A");udfData[0].setUdf170_Label(getUdfLabel_2(addUdf)); break;
		case 56:udfData[0].setUdf171_Flag("A");udfData[0].setUdf171_Label(getUdfLabel_2(addUdf)); break;
		case 57:udfData[0].setUdf172_Flag("A");udfData[0].setUdf172_Label(getUdfLabel_2(addUdf)); break;
		case 58:udfData[0].setUdf173_Flag("A");udfData[0].setUdf173_Label(getUdfLabel_2(addUdf)); break;
		case 59:udfData[0].setUdf174_Flag("A");udfData[0].setUdf174_Label(getUdfLabel_2(addUdf)); break;
		case 60:udfData[0].setUdf175_Flag("A");udfData[0].setUdf175_Label(getUdfLabel_2(addUdf)); break;
		case 61:udfData[0].setUdf176_Flag("A");udfData[0].setUdf176_Label(getUdfLabel_2(addUdf)); break;
		case 62:udfData[0].setUdf177_Flag("A");udfData[0].setUdf177_Label(getUdfLabel_2(addUdf)); break;
		case 63:udfData[0].setUdf178_Flag("A");udfData[0].setUdf178_Label(getUdfLabel_2(addUdf)); break;
		case 64:udfData[0].setUdf179_Flag("A");udfData[0].setUdf179_Label(getUdfLabel_2(addUdf)); break;
		case 65:udfData[0].setUdf180_Flag("A");udfData[0].setUdf180_Label(getUdfLabel_2(addUdf)); break;
		case 66:udfData[0].setUdf181_Flag("A");udfData[0].setUdf181_Label(getUdfLabel_2(addUdf)); break;
		case 67:udfData[0].setUdf182_Flag("A");udfData[0].setUdf182_Label(getUdfLabel_2(addUdf)); break;
		case 68:udfData[0].setUdf183_Flag("A");udfData[0].setUdf183_Label(getUdfLabel_2(addUdf)); break;
		case 69:udfData[0].setUdf184_Flag("A");udfData[0].setUdf184_Label(getUdfLabel_2(addUdf)); break;
		case 70:udfData[0].setUdf185_Flag("A");udfData[0].setUdf185_Label(getUdfLabel_2(addUdf)); break;
		case 71:udfData[0].setUdf186_Flag("A");udfData[0].setUdf186_Label(getUdfLabel_2(addUdf)); break;
		case 72:udfData[0].setUdf187_Flag("A");udfData[0].setUdf187_Label(getUdfLabel_2(addUdf)); break;
		case 73:udfData[0].setUdf188_Flag("A");udfData[0].setUdf188_Label(getUdfLabel_2(addUdf)); break;
		case 74:udfData[0].setUdf189_Flag("A");udfData[0].setUdf189_Label(getUdfLabel_2(addUdf)); break;
		case 75:udfData[0].setUdf190_Flag("A");udfData[0].setUdf190_Label(getUdfLabel_2(addUdf)); break;
		case 76:udfData[0].setUdf191_Flag("A");udfData[0].setUdf191_Label(getUdfLabel_2(addUdf)); break;
		case 77:udfData[0].setUdf192_Flag("A");udfData[0].setUdf192_Label(getUdfLabel_2(addUdf)); break;
		case 78:udfData[0].setUdf193_Flag("A");udfData[0].setUdf193_Label(getUdfLabel_2(addUdf)); break;
		case 79:udfData[0].setUdf194_Flag("A");udfData[0].setUdf194_Label(getUdfLabel_2(addUdf)); break;
		case 80:udfData[0].setUdf195_Flag("A");udfData[0].setUdf195_Label(getUdfLabel_2(addUdf)); break;
		case 81:udfData[0].setUdf196_Flag("A");udfData[0].setUdf196_Label(getUdfLabel_2(addUdf)); break;
		case 82:udfData[0].setUdf197_Flag("A");udfData[0].setUdf197_Label(getUdfLabel_2(addUdf)); break;
		case 83:udfData[0].setUdf198_Flag("A");udfData[0].setUdf198_Label(getUdfLabel_2(addUdf)); break;
		case 84:udfData[0].setUdf199_Flag("A");udfData[0].setUdf199_Label(getUdfLabel_2(addUdf)); break;
		case 85:udfData[0].setUdf200_Flag("A");udfData[0].setUdf200_Label(getUdfLabel_2(addUdf)); break;
		
		case 86:udfData[0].setUdf201_Flag("A");udfData[0].setUdf201_Label(getUdfLabel_2(addUdf)); break;
		case 87:udfData[0].setUdf202_Flag("A");udfData[0].setUdf202_Label(getUdfLabel_2(addUdf)); break;
		case 88:udfData[0].setUdf203_Flag("A");udfData[0].setUdf203_Label(getUdfLabel_2(addUdf)); break;
		case 89:udfData[0].setUdf204_Flag("A");udfData[0].setUdf204_Label(getUdfLabel_2(addUdf)); break;
		case 90:udfData[0].setUdf205_Flag("A");udfData[0].setUdf205_Label(getUdfLabel_2(addUdf)); break;
		case 91:udfData[0].setUdf206_Flag("A");udfData[0].setUdf206_Label(getUdfLabel_2(addUdf)); break;
		case 92:udfData[0].setUdf207_Flag("A");udfData[0].setUdf207_Label(getUdfLabel_2(addUdf)); break;
		case 93:udfData[0].setUdf208_Flag("A");udfData[0].setUdf208_Label(getUdfLabel_2(addUdf)); break;
		case 94:udfData[0].setUdf209_Flag("A");udfData[0].setUdf209_Label(getUdfLabel_2(addUdf)); break;
		case 95:udfData[0].setUdf210_Flag("A");udfData[0].setUdf210_Label(getUdfLabel_2(addUdf)); break;
		case 96:udfData[0].setUdf211_Flag("A");udfData[0].setUdf211_Label(getUdfLabel_2(addUdf)); break;
		case 97:udfData[0].setUdf212_Flag("A");udfData[0].setUdf212_Label(getUdfLabel_2(addUdf)); break;
		case 98:udfData[0].setUdf213_Flag("A");udfData[0].setUdf213_Label(getUdfLabel_2(addUdf)); break;
		case 99:udfData[0].setUdf214_Flag("A");udfData[0].setUdf214_Label(getUdfLabel_2(addUdf)); break;
		case 100:udfData[0].setUdf215_Flag("A");udfData[0].setUdf215_Label(getUdfLabel_2(addUdf)); break;
		
			
		default:break;
		}
	}
	
	
	private void updateUdfModifyFlag(ICustomerSysXRef actualXref,ICustomerSysXRef stagingXref) {
		ILimitXRefUdf udfData[]=stagingXref.getXRefUdfData();
		ILimitXRefUdf actualUdfData[]=actualXref.getXRefUdfData();
		
		if(null!=udfData && null!=actualUdfData) {
			if(null!=udfData[0].getUdf1_Value()&&null!=actualUdfData[0].getUdf1_Value()&& !udfData[0].getUdf1_Value().equals(actualUdfData[0].getUdf1_Value()))
				udfData[0].setUdf1_Flag("M");
			else if(null==udfData[0].getUdf1_Flag() && null!=udfData[0].getUdf1_Value()&& null==actualUdfData[0].getUdf1_Value()&& !udfData[0].getUdf1_Value().equals(actualUdfData[0].getUdf1_Value()))
				udfData[0].setUdf1_Flag("A");
			if(null!=udfData[0].getUdf2_Value()&&null!=actualUdfData[0].getUdf2_Value()&& !udfData[0].getUdf2_Value().equals(actualUdfData[0].getUdf2_Value()))
				udfData[0].setUdf2_Flag("M");
			else if(null==udfData[0].getUdf2_Flag() && null!=udfData[0].getUdf2_Value()&& null==actualUdfData[0].getUdf2_Value()&& !udfData[0].getUdf2_Value().equals(actualUdfData[0].getUdf1_Value()))
				udfData[0].setUdf1_Flag("A");
			if(null!=udfData[0].getUdf3_Value()&&null!=actualUdfData[0].getUdf3_Value()&& !udfData[0].getUdf3_Value().equals(actualUdfData[0].getUdf3_Value()))
				udfData[0].setUdf3_Flag("M");
			else if(null==udfData[0].getUdf3_Flag() && null!=udfData[0].getUdf3_Value()&& null==actualUdfData[0].getUdf3_Value()&& !udfData[0].getUdf3_Value().equals(actualUdfData[0].getUdf3_Value()))
				udfData[0].setUdf3_Flag("A");
			if(null!=udfData[0].getUdf4_Value()&&null!=actualUdfData[0].getUdf4_Value()&& !udfData[0].getUdf4_Value().equals(actualUdfData[0].getUdf4_Value()))
				udfData[0].setUdf4_Flag("M");
			else if(null==udfData[0].getUdf4_Flag() && null!=udfData[0].getUdf4_Value()&& null==actualUdfData[0].getUdf4_Value()&& !udfData[0].getUdf4_Value().equals(actualUdfData[0].getUdf4_Value()))
				udfData[0].setUdf4_Flag("A");
			if(null!=udfData[0].getUdf5_Value()&&null!=actualUdfData[0].getUdf5_Value()&& !udfData[0].getUdf5_Value().equals(actualUdfData[0].getUdf5_Value()))
				udfData[0].setUdf5_Flag("M");
			else if(null==udfData[0].getUdf5_Flag() && null!=udfData[0].getUdf5_Value()&& null==actualUdfData[0].getUdf5_Value()&& !udfData[0].getUdf5_Value().equals(actualUdfData[0].getUdf5_Value()))
				udfData[0].setUdf5_Flag("A");
			if(null!=udfData[0].getUdf6_Value()&&null!=actualUdfData[0].getUdf6_Value()&& !udfData[0].getUdf6_Value().equals(actualUdfData[0].getUdf6_Value()))
				udfData[0].setUdf6_Flag("M");
			else if(null==udfData[0].getUdf6_Flag() && null!=udfData[0].getUdf6_Value()&& null==actualUdfData[0].getUdf6_Value()&& !udfData[0].getUdf6_Value().equals(actualUdfData[0].getUdf6_Value()))
				udfData[0].setUdf6_Flag("A");
			if(null!=udfData[0].getUdf7_Value()&&null!=actualUdfData[0].getUdf7_Value()&& !udfData[0].getUdf7_Value().equals(actualUdfData[0].getUdf7_Value()))
				udfData[0].setUdf7_Flag("M");
			else if(null==udfData[0].getUdf7_Flag() && null!=udfData[0].getUdf7_Value()&& null==actualUdfData[0].getUdf7_Value()&& !udfData[0].getUdf7_Value().equals(actualUdfData[0].getUdf7_Value()))
				udfData[0].setUdf7_Flag("A");
			if(null!=udfData[0].getUdf8_Value()&&null!=actualUdfData[0].getUdf8_Value()&& !udfData[0].getUdf8_Value().equals(actualUdfData[0].getUdf8_Value()))
				udfData[0].setUdf8_Flag("M");
			else if(null==udfData[0].getUdf8_Flag() && null!=udfData[0].getUdf8_Value()&& null==actualUdfData[0].getUdf8_Value()&& !udfData[0].getUdf8_Value().equals(actualUdfData[0].getUdf8_Value()))
				udfData[0].setUdf8_Flag("A");
			if(null!=udfData[0].getUdf9_Value()&&null!=actualUdfData[0].getUdf9_Value()&& !udfData[0].getUdf9_Value().equals(actualUdfData[0].getUdf9_Value()))
				udfData[0].setUdf9_Flag("M");
			else if(null==udfData[0].getUdf9_Flag() && null!=udfData[0].getUdf9_Value()&& null==actualUdfData[0].getUdf9_Value()&& !udfData[0].getUdf9_Value().equals(actualUdfData[0].getUdf9_Value()))
				udfData[0].setUdf9_Flag("A");
			if(null!=udfData[0].getUdf10_Value()&&null!=actualUdfData[0].getUdf10_Value()&& !udfData[0].getUdf10_Value().equals(actualUdfData[0].getUdf10_Value()))
				udfData[0].setUdf10_Flag("M");
			else if(null==udfData[0].getUdf10_Flag() && null!=udfData[0].getUdf10_Value()&& null==actualUdfData[0].getUdf10_Value()&& !udfData[0].getUdf10_Value().equals(actualUdfData[0].getUdf10_Value()))
				udfData[0].setUdf10_Flag("A");
			if(null!=udfData[0].getUdf11_Value()&&null!=actualUdfData[0].getUdf11_Value()&& !udfData[0].getUdf11_Value().equals(actualUdfData[0].getUdf11_Value()))
				udfData[0].setUdf11_Flag("M");
			else if(null==udfData[0].getUdf11_Flag() && null!=udfData[0].getUdf11_Value()&& null==actualUdfData[0].getUdf11_Value()&& !udfData[0].getUdf11_Value().equals(actualUdfData[0].getUdf11_Value()))
				udfData[0].setUdf11_Flag("A");
			if(null!=udfData[0].getUdf12_Value()&&null!=actualUdfData[0].getUdf12_Value()&& !udfData[0].getUdf12_Value().equals(actualUdfData[0].getUdf12_Value()))
				udfData[0].setUdf12_Flag("M");
			else if(null==udfData[0].getUdf12_Flag() && null!=udfData[0].getUdf12_Value()&& null==actualUdfData[0].getUdf12_Value()&& !udfData[0].getUdf12_Value().equals(actualUdfData[0].getUdf12_Value()))
				udfData[0].setUdf12_Flag("A");
			if(null!=udfData[0].getUdf13_Value()&&null!=actualUdfData[0].getUdf13_Value()&& !udfData[0].getUdf13_Value().equals(actualUdfData[0].getUdf13_Value()))
				udfData[0].setUdf13_Flag("M");
			else if(null==udfData[0].getUdf13_Flag() && null!=udfData[0].getUdf13_Value()&& null==actualUdfData[0].getUdf13_Value()&& !udfData[0].getUdf13_Value().equals(actualUdfData[0].getUdf13_Value()))
				udfData[0].setUdf13_Flag("A");
			
			if(null!=udfData[0].getUdf14_Value()&&null!=actualUdfData[0].getUdf14_Value()&& !udfData[0].getUdf14_Value().equals(actualUdfData[0].getUdf14_Value()))
				udfData[0].setUdf14_Flag("M");
			else if(null==udfData[0].getUdf14_Flag() && null!=udfData[0].getUdf14_Value()&& null==actualUdfData[0].getUdf14_Value()&& !udfData[0].getUdf14_Value().equals(actualUdfData[0].getUdf14_Value()))
				udfData[0].setUdf14_Flag("A");
			if(null!=udfData[0].getUdf15_Value()&&null!=actualUdfData[0].getUdf15_Value()&& !udfData[0].getUdf15_Value().equals(actualUdfData[0].getUdf15_Value()))
				udfData[0].setUdf15_Flag("M");
			else if(null==udfData[0].getUdf15_Flag() && null!=udfData[0].getUdf15_Value()&& null==actualUdfData[0].getUdf15_Value()&& !udfData[0].getUdf15_Value().equals(actualUdfData[0].getUdf15_Value()))
				udfData[0].setUdf15_Flag("A");
			if(null!=udfData[0].getUdf16_Value()&&null!=actualUdfData[0].getUdf16_Value()&& !udfData[0].getUdf16_Value().equals(actualUdfData[0].getUdf16_Value()))
				udfData[0].setUdf16_Flag("M");
			else if(null==udfData[0].getUdf16_Flag() && null!=udfData[0].getUdf16_Value()&& null==actualUdfData[0].getUdf16_Value()&& !udfData[0].getUdf16_Value().equals(actualUdfData[0].getUdf16_Value()))
				udfData[0].setUdf16_Flag("A");
			if(null!=udfData[0].getUdf17_Value()&&null!=actualUdfData[0].getUdf17_Value()&& !udfData[0].getUdf17_Value().equals(actualUdfData[0].getUdf17_Value()))
				udfData[0].setUdf17_Flag("M");
			else if(null==udfData[0].getUdf17_Flag() && null!=udfData[0].getUdf17_Value()&& null==actualUdfData[0].getUdf17_Value()&& !udfData[0].getUdf17_Value().equals(actualUdfData[0].getUdf17_Value()))
				udfData[0].setUdf17_Flag("A");
			if(null!=udfData[0].getUdf18_Value()&&null!=actualUdfData[0].getUdf18_Value()&& !udfData[0].getUdf18_Value().equals(actualUdfData[0].getUdf18_Value()))
				udfData[0].setUdf18_Flag("M");
			else if(null==udfData[0].getUdf18_Flag() && null!=udfData[0].getUdf18_Value()&& null==actualUdfData[0].getUdf18_Value()&& !udfData[0].getUdf18_Value().equals(actualUdfData[0].getUdf18_Value()))
				udfData[0].setUdf18_Flag("A");
			if(null!=udfData[0].getUdf19_Value()&&null!=actualUdfData[0].getUdf19_Value()&& !udfData[0].getUdf19_Value().equals(actualUdfData[0].getUdf19_Value()))
				udfData[0].setUdf19_Flag("M");
			else if(null==udfData[0].getUdf19_Flag() && null!=udfData[0].getUdf19_Value()&& null==actualUdfData[0].getUdf19_Value()&& !udfData[0].getUdf19_Value().equals(actualUdfData[0].getUdf19_Value()))
				udfData[0].setUdf19_Flag("A");
			if(null!=udfData[0].getUdf20_Value()&&null!=actualUdfData[0].getUdf20_Value()&& !udfData[0].getUdf20_Value().equals(actualUdfData[0].getUdf20_Value()))
				udfData[0].setUdf20_Flag("M");
				else if(null==udfData[0].getUdf20_Flag() && null!=udfData[0].getUdf20_Value()&& null==actualUdfData[0].getUdf20_Value()&& !udfData[0].getUdf20_Value().equals(actualUdfData[0].getUdf20_Value()))
				udfData[0].setUdf20_Flag("A");
				if(null!=udfData[0].getUdf21_Value()&&null!=actualUdfData[0].getUdf21_Value()&& !udfData[0].getUdf21_Value().equals(actualUdfData[0].getUdf21_Value()))
				udfData[0].setUdf21_Flag("M");
				else if(null==udfData[0].getUdf21_Flag() && null!=udfData[0].getUdf21_Value()&& null==actualUdfData[0].getUdf21_Value()&& !udfData[0].getUdf21_Value().equals(actualUdfData[0].getUdf21_Value()))
				udfData[0].setUdf21_Flag("A");
				if(null!=udfData[0].getUdf22_Value()&&null!=actualUdfData[0].getUdf22_Value()&& !udfData[0].getUdf22_Value().equals(actualUdfData[0].getUdf22_Value()))
				udfData[0].setUdf22_Flag("M");
				else if(null==udfData[0].getUdf22_Flag() && null!=udfData[0].getUdf22_Value()&& null==actualUdfData[0].getUdf22_Value()&& !udfData[0].getUdf22_Value().equals(actualUdfData[0].getUdf22_Value()))
				udfData[0].setUdf22_Flag("A");
				if(null!=udfData[0].getUdf23_Value()&&null!=actualUdfData[0].getUdf23_Value()&& !udfData[0].getUdf23_Value().equals(actualUdfData[0].getUdf23_Value()))
				udfData[0].setUdf23_Flag("M");
				else if(null==udfData[0].getUdf23_Flag() && null!=udfData[0].getUdf23_Value()&& null==actualUdfData[0].getUdf23_Value()&& !udfData[0].getUdf23_Value().equals(actualUdfData[0].getUdf23_Value()))
				udfData[0].setUdf23_Flag("A");
				if(null!=udfData[0].getUdf24_Value()&&null!=actualUdfData[0].getUdf24_Value()&& !udfData[0].getUdf24_Value().equals(actualUdfData[0].getUdf24_Value()))
				udfData[0].setUdf24_Flag("M");
				else if(null==udfData[0].getUdf24_Flag() && null!=udfData[0].getUdf24_Value()&& null==actualUdfData[0].getUdf24_Value()&& !udfData[0].getUdf24_Value().equals(actualUdfData[0].getUdf24_Value()))
				udfData[0].setUdf24_Flag("A");
				if(null!=udfData[0].getUdf25_Value()&&null!=actualUdfData[0].getUdf25_Value()&& !udfData[0].getUdf25_Value().equals(actualUdfData[0].getUdf25_Value()))
				udfData[0].setUdf25_Flag("M");
				else if(null==udfData[0].getUdf25_Flag() && null!=udfData[0].getUdf25_Value()&& null==actualUdfData[0].getUdf25_Value()&& !udfData[0].getUdf25_Value().equals(actualUdfData[0].getUdf25_Value()))
				udfData[0].setUdf25_Flag("A");
				if(null!=udfData[0].getUdf26_Value()&&null!=actualUdfData[0].getUdf26_Value()&& !udfData[0].getUdf26_Value().equals(actualUdfData[0].getUdf26_Value()))
				udfData[0].setUdf26_Flag("M");
				else if(null==udfData[0].getUdf26_Flag() && null!=udfData[0].getUdf26_Value()&& null==actualUdfData[0].getUdf26_Value()&& !udfData[0].getUdf26_Value().equals(actualUdfData[0].getUdf26_Value()))
				udfData[0].setUdf26_Flag("A");
				if(null!=udfData[0].getUdf27_Value()&&null!=actualUdfData[0].getUdf27_Value()&& !udfData[0].getUdf27_Value().equals(actualUdfData[0].getUdf27_Value()))
				udfData[0].setUdf27_Flag("M");
				else if(null==udfData[0].getUdf27_Flag() && null!=udfData[0].getUdf27_Value()&& null==actualUdfData[0].getUdf27_Value()&& !udfData[0].getUdf27_Value().equals(actualUdfData[0].getUdf27_Value()))
				udfData[0].setUdf27_Flag("A");
				if(null!=udfData[0].getUdf28_Value()&&null!=actualUdfData[0].getUdf28_Value()&& !udfData[0].getUdf28_Value().equals(actualUdfData[0].getUdf28_Value()))
				udfData[0].setUdf21_Flag("M");
				else if(null==udfData[0].getUdf28_Flag() && null!=udfData[0].getUdf28_Value()&& null==actualUdfData[0].getUdf28_Value()&& !udfData[0].getUdf28_Value().equals(actualUdfData[0].getUdf28_Value()))
				udfData[0].setUdf28_Flag("A");
				if(null!=udfData[0].getUdf29_Value()&&null!=actualUdfData[0].getUdf29_Value()&& !udfData[0].getUdf29_Value().equals(actualUdfData[0].getUdf29_Value()))
				udfData[0].setUdf29_Flag("M");
				else if(null==udfData[0].getUdf29_Flag() && null!=udfData[0].getUdf29_Value()&& null==actualUdfData[0].getUdf29_Value()&& !udfData[0].getUdf29_Value().equals(actualUdfData[0].getUdf29_Value()))
				udfData[0].setUdf29_Flag("A");
				if(null!=udfData[0].getUdf30_Value()&&null!=actualUdfData[0].getUdf30_Value()&& !udfData[0].getUdf30_Value().equals(actualUdfData[0].getUdf30_Value()))
				udfData[0].setUdf30_Flag("M");
				else if(null==udfData[0].getUdf30_Flag() && null!=udfData[0].getUdf30_Value()&& null==actualUdfData[0].getUdf30_Value()&& !udfData[0].getUdf30_Value().equals(actualUdfData[0].getUdf30_Value()))
				udfData[0].setUdf30_Flag("A");
				if(null!=udfData[0].getUdf31_Value()&&null!=actualUdfData[0].getUdf31_Value()&& !udfData[0].getUdf31_Value().equals(actualUdfData[0].getUdf31_Value()))
				udfData[0].setUdf31_Flag("M");
				else if(null==udfData[0].getUdf31_Flag() && null!=udfData[0].getUdf31_Value()&& null==actualUdfData[0].getUdf31_Value()&& !udfData[0].getUdf31_Value().equals(actualUdfData[0].getUdf31_Value()))
				udfData[0].setUdf31_Flag("A");
				if(null!=udfData[0].getUdf32_Value()&&null!=actualUdfData[0].getUdf32_Value()&& !udfData[0].getUdf32_Value().equals(actualUdfData[0].getUdf32_Value()))
				udfData[0].setUdf32_Flag("M");
				else if(null==udfData[0].getUdf32_Flag() && null!=udfData[0].getUdf32_Value()&& null==actualUdfData[0].getUdf32_Value()&& !udfData[0].getUdf32_Value().equals(actualUdfData[0].getUdf32_Value()))
				udfData[0].setUdf32_Flag("A");
				if(null!=udfData[0].getUdf33_Value()&&null!=actualUdfData[0].getUdf33_Value()&& !udfData[0].getUdf33_Value().equals(actualUdfData[0].getUdf33_Value()))
				udfData[0].setUdf33_Flag("M");
				else if(null==udfData[0].getUdf33_Flag() && null!=udfData[0].getUdf33_Value()&& null==actualUdfData[0].getUdf33_Value()&& !udfData[0].getUdf33_Value().equals(actualUdfData[0].getUdf33_Value()))
				udfData[0].setUdf33_Flag("A");
				if(null!=udfData[0].getUdf34_Value()&&null!=actualUdfData[0].getUdf34_Value()&& !udfData[0].getUdf34_Value().equals(actualUdfData[0].getUdf34_Value()))
				udfData[0].setUdf34_Flag("M");
				else if(null==udfData[0].getUdf34_Flag() && null!=udfData[0].getUdf34_Value()&& null==actualUdfData[0].getUdf34_Value()&& !udfData[0].getUdf34_Value().equals(actualUdfData[0].getUdf34_Value()))
				udfData[0].setUdf34_Flag("A");
				if(null!=udfData[0].getUdf35_Value()&&null!=actualUdfData[0].getUdf35_Value()&& !udfData[0].getUdf35_Value().equals(actualUdfData[0].getUdf35_Value()))
				udfData[0].setUdf35_Flag("M");
				else if(null==udfData[0].getUdf35_Flag() && null!=udfData[0].getUdf35_Value()&& null==actualUdfData[0].getUdf35_Value()&& !udfData[0].getUdf35_Value().equals(actualUdfData[0].getUdf35_Value()))
				udfData[0].setUdf35_Flag("A");
				if(null!=udfData[0].getUdf36_Value()&&null!=actualUdfData[0].getUdf36_Value()&& !udfData[0].getUdf36_Value().equals(actualUdfData[0].getUdf36_Value()))
				udfData[0].setUdf36_Flag("M");
				else if(null==udfData[0].getUdf36_Flag() && null!=udfData[0].getUdf36_Value()&& null==actualUdfData[0].getUdf36_Value()&& !udfData[0].getUdf36_Value().equals(actualUdfData[0].getUdf36_Value()))
				udfData[0].setUdf36_Flag("A");
				if(null!=udfData[0].getUdf37_Value()&&null!=actualUdfData[0].getUdf37_Value()&& !udfData[0].getUdf37_Value().equals(actualUdfData[0].getUdf37_Value()))
				udfData[0].setUdf37_Flag("M");
				else if(null==udfData[0].getUdf37_Flag() && null!=udfData[0].getUdf37_Value()&& null==actualUdfData[0].getUdf37_Value()&& !udfData[0].getUdf37_Value().equals(actualUdfData[0].getUdf37_Value()))
				udfData[0].setUdf37_Flag("A");
				if(null!=udfData[0].getUdf38_Value()&&null!=actualUdfData[0].getUdf38_Value()&& !udfData[0].getUdf38_Value().equals(actualUdfData[0].getUdf38_Value()))
				udfData[0].setUdf31_Flag("M");
				else if(null==udfData[0].getUdf38_Flag() && null!=udfData[0].getUdf38_Value()&& null==actualUdfData[0].getUdf38_Value()&& !udfData[0].getUdf38_Value().equals(actualUdfData[0].getUdf38_Value()))
				udfData[0].setUdf38_Flag("A");
				if(null!=udfData[0].getUdf39_Value()&&null!=actualUdfData[0].getUdf39_Value()&& !udfData[0].getUdf39_Value().equals(actualUdfData[0].getUdf39_Value()))
				udfData[0].setUdf39_Flag("M");
				else if(null==udfData[0].getUdf39_Flag() && null!=udfData[0].getUdf39_Value()&& null==actualUdfData[0].getUdf39_Value()&& !udfData[0].getUdf39_Value().equals(actualUdfData[0].getUdf39_Value()))
				udfData[0].setUdf39_Flag("A");
				
				if(null!=udfData[0].getUdf40_Value()&&null!=actualUdfData[0].getUdf40_Value()&& !udfData[0].getUdf40_Value().equals(actualUdfData[0].getUdf40_Value()))
				udfData[0].setUdf40_Flag("M");
				else if(null==udfData[0].getUdf40_Flag() && null!=udfData[0].getUdf40_Value()&& null==actualUdfData[0].getUdf40_Value()&& !udfData[0].getUdf40_Value().equals(actualUdfData[0].getUdf40_Value()))
				udfData[0].setUdf40_Flag("A");
				if(null!=udfData[0].getUdf41_Value()&&null!=actualUdfData[0].getUdf41_Value()&& !udfData[0].getUdf41_Value().equals(actualUdfData[0].getUdf41_Value()))
				udfData[0].setUdf41_Flag("M");
				else if(null==udfData[0].getUdf41_Flag() && null!=udfData[0].getUdf41_Value()&& null==actualUdfData[0].getUdf41_Value()&& !udfData[0].getUdf41_Value().equals(actualUdfData[0].getUdf41_Value()))
				udfData[0].setUdf41_Flag("A");
				if(null!=udfData[0].getUdf42_Value()&&null!=actualUdfData[0].getUdf42_Value()&& !udfData[0].getUdf42_Value().equals(actualUdfData[0].getUdf42_Value()))
				udfData[0].setUdf42_Flag("M");
				else if(null==udfData[0].getUdf42_Flag() && null!=udfData[0].getUdf42_Value()&& null==actualUdfData[0].getUdf42_Value()&& !udfData[0].getUdf42_Value().equals(actualUdfData[0].getUdf42_Value()))
				udfData[0].setUdf42_Flag("A");
				if(null!=udfData[0].getUdf43_Value()&&null!=actualUdfData[0].getUdf43_Value()&& !udfData[0].getUdf43_Value().equals(actualUdfData[0].getUdf43_Value()))
				udfData[0].setUdf43_Flag("M");
				else if(null==udfData[0].getUdf43_Flag() && null!=udfData[0].getUdf43_Value()&& null==actualUdfData[0].getUdf43_Value()&& !udfData[0].getUdf43_Value().equals(actualUdfData[0].getUdf43_Value()))
				udfData[0].setUdf43_Flag("A");
				if(null!=udfData[0].getUdf44_Value()&&null!=actualUdfData[0].getUdf44_Value()&& !udfData[0].getUdf44_Value().equals(actualUdfData[0].getUdf44_Value()))
				udfData[0].setUdf44_Flag("M");
				else if(null==udfData[0].getUdf44_Flag() && null!=udfData[0].getUdf44_Value()&& null==actualUdfData[0].getUdf44_Value()&& !udfData[0].getUdf44_Value().equals(actualUdfData[0].getUdf44_Value()))
				udfData[0].setUdf44_Flag("A");
				if(null!=udfData[0].getUdf45_Value()&&null!=actualUdfData[0].getUdf45_Value()&& !udfData[0].getUdf45_Value().equals(actualUdfData[0].getUdf45_Value()))
				udfData[0].setUdf45_Flag("M");
				else if(null==udfData[0].getUdf45_Flag() && null!=udfData[0].getUdf45_Value()&& null==actualUdfData[0].getUdf45_Value()&& !udfData[0].getUdf45_Value().equals(actualUdfData[0].getUdf45_Value()))
				udfData[0].setUdf45_Flag("A");
				if(null!=udfData[0].getUdf46_Value()&&null!=actualUdfData[0].getUdf46_Value()&& !udfData[0].getUdf46_Value().equals(actualUdfData[0].getUdf46_Value()))
				udfData[0].setUdf46_Flag("M");
				else if(null==udfData[0].getUdf46_Flag() && null!=udfData[0].getUdf46_Value()&& null==actualUdfData[0].getUdf46_Value()&& !udfData[0].getUdf46_Value().equals(actualUdfData[0].getUdf46_Value()))
				udfData[0].setUdf46_Flag("A");
				if(null!=udfData[0].getUdf47_Value()&&null!=actualUdfData[0].getUdf47_Value()&& !udfData[0].getUdf47_Value().equals(actualUdfData[0].getUdf47_Value()))
				udfData[0].setUdf47_Flag("M");
				else if(null==udfData[0].getUdf47_Flag() && null!=udfData[0].getUdf47_Value()&& null==actualUdfData[0].getUdf47_Value()&& !udfData[0].getUdf47_Value().equals(actualUdfData[0].getUdf47_Value()))
				udfData[0].setUdf47_Flag("A");
				if(null!=udfData[0].getUdf48_Value()&&null!=actualUdfData[0].getUdf48_Value()&& !udfData[0].getUdf48_Value().equals(actualUdfData[0].getUdf48_Value()))
				udfData[0].setUdf41_Flag("M");
				else if(null==udfData[0].getUdf48_Flag() && null!=udfData[0].getUdf48_Value()&& null==actualUdfData[0].getUdf48_Value()&& !udfData[0].getUdf48_Value().equals(actualUdfData[0].getUdf48_Value()))
				udfData[0].setUdf48_Flag("A");
				if(null!=udfData[0].getUdf49_Value()&&null!=actualUdfData[0].getUdf49_Value()&& !udfData[0].getUdf49_Value().equals(actualUdfData[0].getUdf49_Value()))
				udfData[0].setUdf49_Flag("M");
				else if(null==udfData[0].getUdf49_Flag() && null!=udfData[0].getUdf49_Value()&& null==actualUdfData[0].getUdf49_Value()&& !udfData[0].getUdf49_Value().equals(actualUdfData[0].getUdf49_Value()))
				udfData[0].setUdf49_Flag("A");
				if(null!=udfData[0].getUdf50_Value()&&null!=actualUdfData[0].getUdf50_Value()&& !udfData[0].getUdf50_Value().equals(actualUdfData[0].getUdf50_Value()))
				udfData[0].setUdf50_Flag("M");
				else if(null==udfData[0].getUdf50_Flag() && null!=udfData[0].getUdf50_Value()&& null==actualUdfData[0].getUdf50_Value()&& !udfData[0].getUdf50_Value().equals(actualUdfData[0].getUdf50_Value()))
				udfData[0].setUdf50_Flag("A");
				if(null!=udfData[0].getUdf51_Value()&&null!=actualUdfData[0].getUdf51_Value()&& !udfData[0].getUdf51_Value().equals(actualUdfData[0].getUdf51_Value()))
				udfData[0].setUdf51_Flag("M");
				else if(null==udfData[0].getUdf51_Flag() && null!=udfData[0].getUdf51_Value()&& null==actualUdfData[0].getUdf51_Value()&& !udfData[0].getUdf51_Value().equals(actualUdfData[0].getUdf51_Value()))
				udfData[0].setUdf51_Flag("A");
				if(null!=udfData[0].getUdf52_Value()&&null!=actualUdfData[0].getUdf52_Value()&& !udfData[0].getUdf52_Value().equals(actualUdfData[0].getUdf52_Value()))
				udfData[0].setUdf52_Flag("M");
				else if(null==udfData[0].getUdf52_Flag() && null!=udfData[0].getUdf52_Value()&& null==actualUdfData[0].getUdf52_Value()&& !udfData[0].getUdf52_Value().equals(actualUdfData[0].getUdf52_Value()))
				udfData[0].setUdf52_Flag("A");
				if(null!=udfData[0].getUdf53_Value()&&null!=actualUdfData[0].getUdf53_Value()&& !udfData[0].getUdf53_Value().equals(actualUdfData[0].getUdf53_Value()))
				udfData[0].setUdf53_Flag("M");
				else if(null==udfData[0].getUdf53_Flag() && null!=udfData[0].getUdf53_Value()&& null==actualUdfData[0].getUdf53_Value()&& !udfData[0].getUdf53_Value().equals(actualUdfData[0].getUdf53_Value()))
				udfData[0].setUdf53_Flag("A");
				if(null!=udfData[0].getUdf54_Value()&&null!=actualUdfData[0].getUdf54_Value()&& !udfData[0].getUdf54_Value().equals(actualUdfData[0].getUdf54_Value()))
				udfData[0].setUdf54_Flag("M");
				else if(null==udfData[0].getUdf54_Flag() && null!=udfData[0].getUdf54_Value()&& null==actualUdfData[0].getUdf54_Value()&& !udfData[0].getUdf54_Value().equals(actualUdfData[0].getUdf54_Value()))
				udfData[0].setUdf54_Flag("A");
				if(null!=udfData[0].getUdf55_Value()&&null!=actualUdfData[0].getUdf55_Value()&& !udfData[0].getUdf55_Value().equals(actualUdfData[0].getUdf55_Value()))
				udfData[0].setUdf55_Flag("M");
				else if(null==udfData[0].getUdf55_Flag() && null!=udfData[0].getUdf55_Value()&& null==actualUdfData[0].getUdf55_Value()&& !udfData[0].getUdf55_Value().equals(actualUdfData[0].getUdf55_Value()))
				udfData[0].setUdf55_Flag("A");
				if(null!=udfData[0].getUdf56_Value()&&null!=actualUdfData[0].getUdf56_Value()&& !udfData[0].getUdf56_Value().equals(actualUdfData[0].getUdf56_Value()))
				udfData[0].setUdf56_Flag("M");
				else if(null==udfData[0].getUdf56_Flag() && null!=udfData[0].getUdf56_Value()&& null==actualUdfData[0].getUdf56_Value()&& !udfData[0].getUdf56_Value().equals(actualUdfData[0].getUdf56_Value()))
				udfData[0].setUdf56_Flag("A");
				if(null!=udfData[0].getUdf57_Value()&&null!=actualUdfData[0].getUdf57_Value()&& !udfData[0].getUdf57_Value().equals(actualUdfData[0].getUdf57_Value()))
				udfData[0].setUdf57_Flag("M");
				else if(null==udfData[0].getUdf57_Flag() && null!=udfData[0].getUdf57_Value()&& null==actualUdfData[0].getUdf57_Value()&& !udfData[0].getUdf57_Value().equals(actualUdfData[0].getUdf57_Value()))
				udfData[0].setUdf57_Flag("A");
				if(null!=udfData[0].getUdf58_Value()&&null!=actualUdfData[0].getUdf58_Value()&& !udfData[0].getUdf58_Value().equals(actualUdfData[0].getUdf58_Value()))
				udfData[0].setUdf51_Flag("M");
				else if(null==udfData[0].getUdf58_Flag() && null!=udfData[0].getUdf58_Value()&& null==actualUdfData[0].getUdf58_Value()&& !udfData[0].getUdf58_Value().equals(actualUdfData[0].getUdf58_Value()))
				udfData[0].setUdf58_Flag("A");
				if(null!=udfData[0].getUdf59_Value()&&null!=actualUdfData[0].getUdf59_Value()&& !udfData[0].getUdf59_Value().equals(actualUdfData[0].getUdf59_Value()))
				udfData[0].setUdf59_Flag("M");
				else if(null==udfData[0].getUdf59_Flag() && null!=udfData[0].getUdf59_Value()&& null==actualUdfData[0].getUdf59_Value()&& !udfData[0].getUdf59_Value().equals(actualUdfData[0].getUdf59_Value()))
				udfData[0].setUdf59_Flag("A");
				if(null!=udfData[0].getUdf60_Value()&&null!=actualUdfData[0].getUdf60_Value()&& !udfData[0].getUdf60_Value().equals(actualUdfData[0].getUdf60_Value()))
				udfData[0].setUdf60_Flag("M");
				else if(null==udfData[0].getUdf60_Flag() && null!=udfData[0].getUdf60_Value()&& null==actualUdfData[0].getUdf60_Value()&& !udfData[0].getUdf60_Value().equals(actualUdfData[0].getUdf60_Value()))
				udfData[0].setUdf60_Flag("A");
				if(null!=udfData[0].getUdf61_Value()&&null!=actualUdfData[0].getUdf61_Value()&& !udfData[0].getUdf61_Value().equals(actualUdfData[0].getUdf61_Value()))
				udfData[0].setUdf61_Flag("M");
				else if(null==udfData[0].getUdf61_Flag() && null!=udfData[0].getUdf61_Value()&& null==actualUdfData[0].getUdf61_Value()&& !udfData[0].getUdf61_Value().equals(actualUdfData[0].getUdf61_Value()))
				udfData[0].setUdf61_Flag("A");
				if(null!=udfData[0].getUdf62_Value()&&null!=actualUdfData[0].getUdf62_Value()&& !udfData[0].getUdf62_Value().equals(actualUdfData[0].getUdf62_Value()))
				udfData[0].setUdf62_Flag("M");
				else if(null==udfData[0].getUdf62_Flag() && null!=udfData[0].getUdf62_Value()&& null==actualUdfData[0].getUdf62_Value()&& !udfData[0].getUdf62_Value().equals(actualUdfData[0].getUdf62_Value()))
				udfData[0].setUdf62_Flag("A");
				if(null!=udfData[0].getUdf63_Value()&&null!=actualUdfData[0].getUdf63_Value()&& !udfData[0].getUdf63_Value().equals(actualUdfData[0].getUdf63_Value()))
				udfData[0].setUdf63_Flag("M");
				else if(null==udfData[0].getUdf63_Flag() && null!=udfData[0].getUdf63_Value()&& null==actualUdfData[0].getUdf63_Value()&& !udfData[0].getUdf63_Value().equals(actualUdfData[0].getUdf63_Value()))
				udfData[0].setUdf63_Flag("A");
				if(null!=udfData[0].getUdf64_Value()&&null!=actualUdfData[0].getUdf64_Value()&& !udfData[0].getUdf64_Value().equals(actualUdfData[0].getUdf64_Value()))
				udfData[0].setUdf64_Flag("M");
				else if(null==udfData[0].getUdf64_Flag() && null!=udfData[0].getUdf64_Value()&& null==actualUdfData[0].getUdf64_Value()&& !udfData[0].getUdf64_Value().equals(actualUdfData[0].getUdf64_Value()))
				udfData[0].setUdf64_Flag("A");
				if(null!=udfData[0].getUdf65_Value()&&null!=actualUdfData[0].getUdf65_Value()&& !udfData[0].getUdf65_Value().equals(actualUdfData[0].getUdf65_Value()))
				udfData[0].setUdf65_Flag("M");
				else if(null==udfData[0].getUdf65_Flag() && null!=udfData[0].getUdf65_Value()&& null==actualUdfData[0].getUdf65_Value()&& !udfData[0].getUdf65_Value().equals(actualUdfData[0].getUdf65_Value()))
				udfData[0].setUdf65_Flag("A");
				if(null!=udfData[0].getUdf66_Value()&&null!=actualUdfData[0].getUdf66_Value()&& !udfData[0].getUdf66_Value().equals(actualUdfData[0].getUdf66_Value()))
				udfData[0].setUdf66_Flag("M");
				else if(null==udfData[0].getUdf66_Flag() && null!=udfData[0].getUdf66_Value()&& null==actualUdfData[0].getUdf66_Value()&& !udfData[0].getUdf66_Value().equals(actualUdfData[0].getUdf66_Value()))
				udfData[0].setUdf66_Flag("A");
				if(null!=udfData[0].getUdf67_Value()&&null!=actualUdfData[0].getUdf67_Value()&& !udfData[0].getUdf67_Value().equals(actualUdfData[0].getUdf67_Value()))
				udfData[0].setUdf67_Flag("M");
				else if(null==udfData[0].getUdf67_Flag() && null!=udfData[0].getUdf67_Value()&& null==actualUdfData[0].getUdf67_Value()&& !udfData[0].getUdf67_Value().equals(actualUdfData[0].getUdf67_Value()))
				udfData[0].setUdf67_Flag("A");
				if(null!=udfData[0].getUdf68_Value()&&null!=actualUdfData[0].getUdf68_Value()&& !udfData[0].getUdf68_Value().equals(actualUdfData[0].getUdf68_Value()))
				udfData[0].setUdf61_Flag("M");
				else if(null==udfData[0].getUdf68_Flag() && null!=udfData[0].getUdf68_Value()&& null==actualUdfData[0].getUdf68_Value()&& !udfData[0].getUdf68_Value().equals(actualUdfData[0].getUdf68_Value()))
				udfData[0].setUdf68_Flag("A");
				if(null!=udfData[0].getUdf69_Value()&&null!=actualUdfData[0].getUdf69_Value()&& !udfData[0].getUdf69_Value().equals(actualUdfData[0].getUdf69_Value()))
				udfData[0].setUdf69_Flag("M");
				else if(null==udfData[0].getUdf69_Flag() && null!=udfData[0].getUdf69_Value()&& null==actualUdfData[0].getUdf69_Value()&& !udfData[0].getUdf69_Value().equals(actualUdfData[0].getUdf69_Value()))
				udfData[0].setUdf69_Flag("A");
				
				if(null!=udfData[0].getUdf70_Value()&&null!=actualUdfData[0].getUdf70_Value()&& !udfData[0].getUdf70_Value().equals(actualUdfData[0].getUdf70_Value()))
				udfData[0].setUdf70_Flag("M");
				else if(null==udfData[0].getUdf70_Flag() && null!=udfData[0].getUdf70_Value()&& null==actualUdfData[0].getUdf70_Value()&& !udfData[0].getUdf70_Value().equals(actualUdfData[0].getUdf70_Value()))
				udfData[0].setUdf70_Flag("A");
				if(null!=udfData[0].getUdf71_Value()&&null!=actualUdfData[0].getUdf71_Value()&& !udfData[0].getUdf71_Value().equals(actualUdfData[0].getUdf71_Value()))
				udfData[0].setUdf71_Flag("M");
				else if(null==udfData[0].getUdf71_Flag() && null!=udfData[0].getUdf71_Value()&& null==actualUdfData[0].getUdf71_Value()&& !udfData[0].getUdf71_Value().equals(actualUdfData[0].getUdf71_Value()))
				udfData[0].setUdf71_Flag("A");
				if(null!=udfData[0].getUdf72_Value()&&null!=actualUdfData[0].getUdf72_Value()&& !udfData[0].getUdf72_Value().equals(actualUdfData[0].getUdf72_Value()))
				udfData[0].setUdf72_Flag("M");
				else if(null==udfData[0].getUdf72_Flag() && null!=udfData[0].getUdf72_Value()&& null==actualUdfData[0].getUdf72_Value()&& !udfData[0].getUdf72_Value().equals(actualUdfData[0].getUdf72_Value()))
				udfData[0].setUdf72_Flag("A");
				if(null!=udfData[0].getUdf73_Value()&&null!=actualUdfData[0].getUdf73_Value()&& !udfData[0].getUdf73_Value().equals(actualUdfData[0].getUdf73_Value()))
				udfData[0].setUdf73_Flag("M");
				else if(null==udfData[0].getUdf73_Flag() && null!=udfData[0].getUdf73_Value()&& null==actualUdfData[0].getUdf73_Value()&& !udfData[0].getUdf73_Value().equals(actualUdfData[0].getUdf73_Value()))
				udfData[0].setUdf73_Flag("A");
				if(null!=udfData[0].getUdf74_Value()&&null!=actualUdfData[0].getUdf74_Value()&& !udfData[0].getUdf74_Value().equals(actualUdfData[0].getUdf74_Value()))
				udfData[0].setUdf74_Flag("M");
				else if(null==udfData[0].getUdf74_Flag() && null!=udfData[0].getUdf74_Value()&& null==actualUdfData[0].getUdf74_Value()&& !udfData[0].getUdf74_Value().equals(actualUdfData[0].getUdf74_Value()))
				udfData[0].setUdf74_Flag("A");
				if(null!=udfData[0].getUdf75_Value()&&null!=actualUdfData[0].getUdf75_Value()&& !udfData[0].getUdf75_Value().equals(actualUdfData[0].getUdf75_Value()))
				udfData[0].setUdf75_Flag("M");
				else if(null==udfData[0].getUdf75_Flag() && null!=udfData[0].getUdf75_Value()&& null==actualUdfData[0].getUdf75_Value()&& !udfData[0].getUdf75_Value().equals(actualUdfData[0].getUdf75_Value()))
				udfData[0].setUdf75_Flag("A");
				if(null!=udfData[0].getUdf76_Value()&&null!=actualUdfData[0].getUdf76_Value()&& !udfData[0].getUdf76_Value().equals(actualUdfData[0].getUdf76_Value()))
				udfData[0].setUdf76_Flag("M");
				else if(null==udfData[0].getUdf76_Flag() && null!=udfData[0].getUdf76_Value()&& null==actualUdfData[0].getUdf76_Value()&& !udfData[0].getUdf76_Value().equals(actualUdfData[0].getUdf76_Value()))
				udfData[0].setUdf76_Flag("A");
				if(null!=udfData[0].getUdf77_Value()&&null!=actualUdfData[0].getUdf77_Value()&& !udfData[0].getUdf77_Value().equals(actualUdfData[0].getUdf77_Value()))
				udfData[0].setUdf77_Flag("M");
				else if(null==udfData[0].getUdf77_Flag() && null!=udfData[0].getUdf77_Value()&& null==actualUdfData[0].getUdf77_Value()&& !udfData[0].getUdf77_Value().equals(actualUdfData[0].getUdf77_Value()))
				udfData[0].setUdf77_Flag("A");
				if(null!=udfData[0].getUdf78_Value()&&null!=actualUdfData[0].getUdf78_Value()&& !udfData[0].getUdf78_Value().equals(actualUdfData[0].getUdf78_Value()))
				udfData[0].setUdf71_Flag("M");
				else if(null==udfData[0].getUdf78_Flag() && null!=udfData[0].getUdf78_Value()&& null==actualUdfData[0].getUdf78_Value()&& !udfData[0].getUdf78_Value().equals(actualUdfData[0].getUdf78_Value()))
				udfData[0].setUdf78_Flag("A");
				if(null!=udfData[0].getUdf79_Value()&&null!=actualUdfData[0].getUdf79_Value()&& !udfData[0].getUdf79_Value().equals(actualUdfData[0].getUdf79_Value()))
				udfData[0].setUdf79_Flag("M");
				else if(null==udfData[0].getUdf79_Flag() && null!=udfData[0].getUdf79_Value()&& null==actualUdfData[0].getUdf79_Value()&& !udfData[0].getUdf79_Value().equals(actualUdfData[0].getUdf79_Value()))
				udfData[0].setUdf79_Flag("A");
				
				if(null!=udfData[0].getUdf80_Value()&&null!=actualUdfData[0].getUdf80_Value()&& !udfData[0].getUdf80_Value().equals(actualUdfData[0].getUdf80_Value()))
				udfData[0].setUdf80_Flag("M");
				else if(null==udfData[0].getUdf80_Flag() && null!=udfData[0].getUdf80_Value()&& null==actualUdfData[0].getUdf80_Value()&& !udfData[0].getUdf80_Value().equals(actualUdfData[0].getUdf80_Value()))
				udfData[0].setUdf80_Flag("A");
				if(null!=udfData[0].getUdf81_Value()&&null!=actualUdfData[0].getUdf81_Value()&& !udfData[0].getUdf81_Value().equals(actualUdfData[0].getUdf81_Value()))
				udfData[0].setUdf81_Flag("M");
				else if(null==udfData[0].getUdf81_Flag() && null!=udfData[0].getUdf81_Value()&& null==actualUdfData[0].getUdf81_Value()&& !udfData[0].getUdf81_Value().equals(actualUdfData[0].getUdf81_Value()))
				udfData[0].setUdf81_Flag("A");
				if(null!=udfData[0].getUdf82_Value()&&null!=actualUdfData[0].getUdf82_Value()&& !udfData[0].getUdf82_Value().equals(actualUdfData[0].getUdf82_Value()))
				udfData[0].setUdf82_Flag("M");
				else if(null==udfData[0].getUdf82_Flag() && null!=udfData[0].getUdf82_Value()&& null==actualUdfData[0].getUdf82_Value()&& !udfData[0].getUdf82_Value().equals(actualUdfData[0].getUdf82_Value()))
				udfData[0].setUdf82_Flag("A");
				if(null!=udfData[0].getUdf83_Value()&&null!=actualUdfData[0].getUdf83_Value()&& !udfData[0].getUdf83_Value().equals(actualUdfData[0].getUdf83_Value()))
				udfData[0].setUdf83_Flag("M");
				else if(null==udfData[0].getUdf83_Flag() && null!=udfData[0].getUdf83_Value()&& null==actualUdfData[0].getUdf83_Value()&& !udfData[0].getUdf83_Value().equals(actualUdfData[0].getUdf83_Value()))
				udfData[0].setUdf83_Flag("A");
				if(null!=udfData[0].getUdf84_Value()&&null!=actualUdfData[0].getUdf84_Value()&& !udfData[0].getUdf84_Value().equals(actualUdfData[0].getUdf84_Value()))
				udfData[0].setUdf84_Flag("M");
				else if(null==udfData[0].getUdf84_Flag() && null!=udfData[0].getUdf84_Value()&& null==actualUdfData[0].getUdf84_Value()&& !udfData[0].getUdf84_Value().equals(actualUdfData[0].getUdf84_Value()))
				udfData[0].setUdf84_Flag("A");
				if(null!=udfData[0].getUdf85_Value()&&null!=actualUdfData[0].getUdf85_Value()&& !udfData[0].getUdf85_Value().equals(actualUdfData[0].getUdf85_Value()))
				udfData[0].setUdf85_Flag("M");
				else if(null==udfData[0].getUdf85_Flag() && null!=udfData[0].getUdf85_Value()&& null==actualUdfData[0].getUdf85_Value()&& !udfData[0].getUdf85_Value().equals(actualUdfData[0].getUdf85_Value()))
				udfData[0].setUdf85_Flag("A");
				if(null!=udfData[0].getUdf86_Value()&&null!=actualUdfData[0].getUdf86_Value()&& !udfData[0].getUdf86_Value().equals(actualUdfData[0].getUdf86_Value()))
				udfData[0].setUdf86_Flag("M");
				else if(null==udfData[0].getUdf86_Flag() && null!=udfData[0].getUdf86_Value()&& null==actualUdfData[0].getUdf86_Value()&& !udfData[0].getUdf86_Value().equals(actualUdfData[0].getUdf86_Value()))
				udfData[0].setUdf86_Flag("A");
				if(null!=udfData[0].getUdf87_Value()&&null!=actualUdfData[0].getUdf87_Value()&& !udfData[0].getUdf87_Value().equals(actualUdfData[0].getUdf87_Value()))
				udfData[0].setUdf87_Flag("M");
				else if(null==udfData[0].getUdf87_Flag() && null!=udfData[0].getUdf87_Value()&& null==actualUdfData[0].getUdf87_Value()&& !udfData[0].getUdf87_Value().equals(actualUdfData[0].getUdf87_Value()))
				udfData[0].setUdf87_Flag("A");
				if(null!=udfData[0].getUdf88_Value()&&null!=actualUdfData[0].getUdf88_Value()&& !udfData[0].getUdf88_Value().equals(actualUdfData[0].getUdf88_Value()))
				udfData[0].setUdf81_Flag("M");
				else if(null==udfData[0].getUdf88_Flag() && null!=udfData[0].getUdf88_Value()&& null==actualUdfData[0].getUdf88_Value()&& !udfData[0].getUdf88_Value().equals(actualUdfData[0].getUdf88_Value()))
				udfData[0].setUdf88_Flag("A");
				if(null!=udfData[0].getUdf89_Value()&&null!=actualUdfData[0].getUdf89_Value()&& !udfData[0].getUdf89_Value().equals(actualUdfData[0].getUdf89_Value()))
				udfData[0].setUdf89_Flag("M");
				else if(null==udfData[0].getUdf89_Flag() && null!=udfData[0].getUdf89_Value()&& null==actualUdfData[0].getUdf89_Value()&& !udfData[0].getUdf89_Value().equals(actualUdfData[0].getUdf89_Value()))
				udfData[0].setUdf89_Flag("A");
				
				if(null!=udfData[0].getUdf90_Value()&&null!=actualUdfData[0].getUdf90_Value()&& !udfData[0].getUdf90_Value().equals(actualUdfData[0].getUdf90_Value()))
				udfData[0].setUdf90_Flag("M");
				else if(null==udfData[0].getUdf90_Flag() && null!=udfData[0].getUdf90_Value()&& null==actualUdfData[0].getUdf90_Value()&& !udfData[0].getUdf90_Value().equals(actualUdfData[0].getUdf90_Value()))
				udfData[0].setUdf90_Flag("A");
				if(null!=udfData[0].getUdf91_Value()&&null!=actualUdfData[0].getUdf91_Value()&& !udfData[0].getUdf91_Value().equals(actualUdfData[0].getUdf91_Value()))
				udfData[0].setUdf91_Flag("M");
				else if(null==udfData[0].getUdf91_Flag() && null!=udfData[0].getUdf91_Value()&& null==actualUdfData[0].getUdf91_Value()&& !udfData[0].getUdf91_Value().equals(actualUdfData[0].getUdf91_Value()))
				udfData[0].setUdf91_Flag("A");
				if(null!=udfData[0].getUdf92_Value()&&null!=actualUdfData[0].getUdf92_Value()&& !udfData[0].getUdf92_Value().equals(actualUdfData[0].getUdf92_Value()))
				udfData[0].setUdf92_Flag("M");
				else if(null==udfData[0].getUdf92_Flag() && null!=udfData[0].getUdf92_Value()&& null==actualUdfData[0].getUdf92_Value()&& !udfData[0].getUdf92_Value().equals(actualUdfData[0].getUdf92_Value()))
				udfData[0].setUdf92_Flag("A");
				if(null!=udfData[0].getUdf93_Value()&&null!=actualUdfData[0].getUdf93_Value()&& !udfData[0].getUdf93_Value().equals(actualUdfData[0].getUdf93_Value()))
				udfData[0].setUdf93_Flag("M");
				else if(null==udfData[0].getUdf93_Flag() && null!=udfData[0].getUdf93_Value()&& null==actualUdfData[0].getUdf93_Value()&& !udfData[0].getUdf93_Value().equals(actualUdfData[0].getUdf93_Value()))
				udfData[0].setUdf93_Flag("A");
				if(null!=udfData[0].getUdf94_Value()&&null!=actualUdfData[0].getUdf94_Value()&& !udfData[0].getUdf94_Value().equals(actualUdfData[0].getUdf94_Value()))
				udfData[0].setUdf94_Flag("M");
				else if(null==udfData[0].getUdf94_Flag() && null!=udfData[0].getUdf94_Value()&& null==actualUdfData[0].getUdf94_Value()&& !udfData[0].getUdf94_Value().equals(actualUdfData[0].getUdf94_Value()))
				udfData[0].setUdf94_Flag("A");
				if(null!=udfData[0].getUdf95_Value()&&null!=actualUdfData[0].getUdf95_Value()&& !udfData[0].getUdf95_Value().equals(actualUdfData[0].getUdf95_Value()))
				udfData[0].setUdf95_Flag("M");
				else if(null==udfData[0].getUdf95_Flag() && null!=udfData[0].getUdf95_Value()&& null==actualUdfData[0].getUdf95_Value()&& !udfData[0].getUdf95_Value().equals(actualUdfData[0].getUdf95_Value()))
				udfData[0].setUdf95_Flag("A");
				if(null!=udfData[0].getUdf96_Value()&&null!=actualUdfData[0].getUdf96_Value()&& !udfData[0].getUdf96_Value().equals(actualUdfData[0].getUdf96_Value()))
				udfData[0].setUdf96_Flag("M");
				else if(null==udfData[0].getUdf96_Flag() && null!=udfData[0].getUdf96_Value()&& null==actualUdfData[0].getUdf96_Value()&& !udfData[0].getUdf96_Value().equals(actualUdfData[0].getUdf96_Value()))
				udfData[0].setUdf96_Flag("A");
				if(null!=udfData[0].getUdf97_Value()&&null!=actualUdfData[0].getUdf97_Value()&& !udfData[0].getUdf97_Value().equals(actualUdfData[0].getUdf97_Value()))
				udfData[0].setUdf97_Flag("M");
				else if(null==udfData[0].getUdf97_Flag() && null!=udfData[0].getUdf97_Value()&& null==actualUdfData[0].getUdf97_Value()&& !udfData[0].getUdf97_Value().equals(actualUdfData[0].getUdf97_Value()))
				udfData[0].setUdf97_Flag("A");
				if(null!=udfData[0].getUdf98_Value()&&null!=actualUdfData[0].getUdf98_Value()&& !udfData[0].getUdf98_Value().equals(actualUdfData[0].getUdf98_Value()))
				udfData[0].setUdf91_Flag("M");
				else if(null==udfData[0].getUdf98_Flag() && null!=udfData[0].getUdf98_Value()&& null==actualUdfData[0].getUdf98_Value()&& !udfData[0].getUdf98_Value().equals(actualUdfData[0].getUdf98_Value()))
				udfData[0].setUdf98_Flag("A");
				if(null!=udfData[0].getUdf99_Value()&&null!=actualUdfData[0].getUdf99_Value()&& !udfData[0].getUdf99_Value().equals(actualUdfData[0].getUdf99_Value()))
				udfData[0].setUdf99_Flag("M");
				else if(null==udfData[0].getUdf99_Flag() && null!=udfData[0].getUdf99_Value()&& null==actualUdfData[0].getUdf99_Value()&& !udfData[0].getUdf99_Value().equals(actualUdfData[0].getUdf99_Value()))
				udfData[0].setUdf99_Flag("A");
				if(null!=udfData[0].getUdf100_Value()&&null!=actualUdfData[0].getUdf100_Value()&& !udfData[0].getUdf100_Value().equals(actualUdfData[0].getUdf100_Value()))
					udfData[0].setUdf100_Flag("M");
				else if(null==udfData[0].getUdf100_Flag() && null!=udfData[0].getUdf100_Value()&& null==actualUdfData[0].getUdf100_Value()&& !udfData[0].getUdf100_Value().equals(actualUdfData[0].getUdf100_Value()))
					udfData[0].setUdf100_Flag("A");
				
				if(null!=udfData[0].getUdf101_Value()&&null!=actualUdfData[0].getUdf101_Value()&& !udfData[0].getUdf101_Value().equals(actualUdfData[0].getUdf101_Value()))
					udfData[0].setUdf101_Flag("M");
				else if(null==udfData[0].getUdf101_Flag() && null!=udfData[0].getUdf101_Value()&& null==actualUdfData[0].getUdf101_Value()&& !udfData[0].getUdf101_Value().equals(actualUdfData[0].getUdf101_Value()))
					udfData[0].setUdf101_Flag("A");
				
							
				if(null!=udfData[0].getUdf102_Value()&&null!=actualUdfData[0].getUdf102_Value()&& !udfData[0].getUdf102_Value().equals(actualUdfData[0].getUdf102_Value()))
					udfData[0].setUdf102_Flag("M");
				else if(null==udfData[0].getUdf102_Flag() && null!=udfData[0].getUdf102_Value()&& null==actualUdfData[0].getUdf102_Value()&& !udfData[0].getUdf102_Value().equals(actualUdfData[0].getUdf102_Value()))
					udfData[0].setUdf102_Flag("A");
				
				if(null!=udfData[0].getUdf103_Value()&&null!=actualUdfData[0].getUdf103_Value()&& !udfData[0].getUdf103_Value().equals(actualUdfData[0].getUdf103_Value()))
					udfData[0].setUdf103_Flag("M");
				else if(null==udfData[0].getUdf103_Flag() && null!=udfData[0].getUdf103_Value()&& null==actualUdfData[0].getUdf103_Value()&& !udfData[0].getUdf103_Value().equals(actualUdfData[0].getUdf103_Value()))
					udfData[0].setUdf103_Flag("A");

				if(null!=udfData[0].getUdf104_Value()&&null!=actualUdfData[0].getUdf104_Value()&& !udfData[0].getUdf104_Value().equals(actualUdfData[0].getUdf104_Value()))
					udfData[0].setUdf104_Flag("M");
				else if(null==udfData[0].getUdf104_Flag() && null!=udfData[0].getUdf104_Value()&& null==actualUdfData[0].getUdf104_Value()&& !udfData[0].getUdf104_Value().equals(actualUdfData[0].getUdf104_Value()))
					udfData[0].setUdf104_Flag("A");

				if(null!=udfData[0].getUdf105_Value()&&null!=actualUdfData[0].getUdf105_Value()&& !udfData[0].getUdf105_Value().equals(actualUdfData[0].getUdf105_Value()))
					udfData[0].setUdf105_Flag("M");
				else if(null==udfData[0].getUdf105_Flag() && null!=udfData[0].getUdf105_Value()&& null==actualUdfData[0].getUdf105_Value()&& !udfData[0].getUdf105_Value().equals(actualUdfData[0].getUdf105_Value()))
					udfData[0].setUdf105_Flag("A");
				
				if(null!=udfData[0].getUdf106_Value()&&null!=actualUdfData[0].getUdf106_Value()&& !udfData[0].getUdf106_Value().equals(actualUdfData[0].getUdf106_Value()))
					udfData[0].setUdf106_Flag("M");
				else if(null==udfData[0].getUdf106_Flag() && null!=udfData[0].getUdf106_Value()&& null==actualUdfData[0].getUdf106_Value()&& !udfData[0].getUdf106_Value().equals(actualUdfData[0].getUdf106_Value()))
					udfData[0].setUdf106_Flag("A");

				if(null!=udfData[0].getUdf107_Value()&&null!=actualUdfData[0].getUdf107_Value()&& !udfData[0].getUdf107_Value().equals(actualUdfData[0].getUdf107_Value()))
					udfData[0].setUdf107_Flag("M");
				else if(null==udfData[0].getUdf107_Flag() && null!=udfData[0].getUdf107_Value()&& null==actualUdfData[0].getUdf107_Value()&& !udfData[0].getUdf107_Value().equals(actualUdfData[0].getUdf107_Value()))
					udfData[0].setUdf107_Flag("A");

				if(null!=udfData[0].getUdf108_Value()&&null!=actualUdfData[0].getUdf108_Value()&& !udfData[0].getUdf108_Value().equals(actualUdfData[0].getUdf108_Value()))
					udfData[0].setUdf108_Flag("M");
				else if(null==udfData[0].getUdf108_Flag() && null!=udfData[0].getUdf108_Value()&& null==actualUdfData[0].getUdf108_Value()&& !udfData[0].getUdf108_Value().equals(actualUdfData[0].getUdf108_Value()))
					udfData[0].setUdf108_Flag("A");

				if(null!=udfData[0].getUdf109_Value()&&null!=actualUdfData[0].getUdf109_Value()&& !udfData[0].getUdf109_Value().equals(actualUdfData[0].getUdf109_Value()))
					udfData[0].setUdf109_Flag("M");
				else if(null==udfData[0].getUdf109_Flag() && null!=udfData[0].getUdf109_Value()&& null==actualUdfData[0].getUdf109_Value()&& !udfData[0].getUdf109_Value().equals(actualUdfData[0].getUdf109_Value()))
					udfData[0].setUdf109_Flag("A");

				if(null!=udfData[0].getUdf110_Value()&&null!=actualUdfData[0].getUdf110_Value()&& !udfData[0].getUdf110_Value().equals(actualUdfData[0].getUdf110_Value()))
					udfData[0].setUdf110_Flag("M");
				else if(null==udfData[0].getUdf110_Flag() && null!=udfData[0].getUdf110_Value()&& null==actualUdfData[0].getUdf110_Value()&& !udfData[0].getUdf110_Value().equals(actualUdfData[0].getUdf110_Value()))
					udfData[0].setUdf110_Flag("A");


				if(null!=udfData[0].getUdf111_Value()&&null!=actualUdfData[0].getUdf111_Value()&& !udfData[0].getUdf111_Value().equals(actualUdfData[0].getUdf111_Value()))
					udfData[0].setUdf111_Flag("M");
				else if(null==udfData[0].getUdf111_Flag() && null!=udfData[0].getUdf111_Value()&& null==actualUdfData[0].getUdf111_Value()&& !udfData[0].getUdf111_Value().equals(actualUdfData[0].getUdf111_Value()))
					udfData[0].setUdf111_Flag("A");

				if(null!=udfData[0].getUdf112_Value()&&null!=actualUdfData[0].getUdf112_Value()&& !udfData[0].getUdf112_Value().equals(actualUdfData[0].getUdf112_Value()))
					udfData[0].setUdf112_Flag("M");
				else if(null==udfData[0].getUdf112_Flag() && null!=udfData[0].getUdf112_Value()&& null==actualUdfData[0].getUdf112_Value()&& !udfData[0].getUdf112_Value().equals(actualUdfData[0].getUdf112_Value()))
					udfData[0].setUdf112_Flag("A");

				if(null!=udfData[0].getUdf113_Value()&&null!=actualUdfData[0].getUdf113_Value()&& !udfData[0].getUdf113_Value().equals(actualUdfData[0].getUdf113_Value()))
					udfData[0].setUdf113_Flag("M");
				else if(null==udfData[0].getUdf113_Flag() && null!=udfData[0].getUdf113_Value()&& null==actualUdfData[0].getUdf113_Value()&& !udfData[0].getUdf113_Value().equals(actualUdfData[0].getUdf113_Value()))
					udfData[0].setUdf113_Flag("A");

				if(null!=udfData[0].getUdf114_Value()&&null!=actualUdfData[0].getUdf114_Value()&& !udfData[0].getUdf114_Value().equals(actualUdfData[0].getUdf114_Value()))
					udfData[0].setUdf114_Flag("M");
				else if(null==udfData[0].getUdf114_Flag() && null!=udfData[0].getUdf114_Value()&& null==actualUdfData[0].getUdf114_Value()&& !udfData[0].getUdf114_Value().equals(actualUdfData[0].getUdf114_Value()))
					udfData[0].setUdf114_Flag("A");

				if(null!=udfData[0].getUdf115_Value()&&null!=actualUdfData[0].getUdf115_Value()&& !udfData[0].getUdf115_Value().equals(actualUdfData[0].getUdf115_Value()))
					udfData[0].setUdf115_Flag("M");
				else if(null==udfData[0].getUdf115_Flag() && null!=udfData[0].getUdf115_Value()&& null==actualUdfData[0].getUdf115_Value()&& !udfData[0].getUdf115_Value().equals(actualUdfData[0].getUdf115_Value()))
					udfData[0].setUdf115_Flag("A");
			
		}		
	}
	
	private void updateUdfModifyFlag2(ICustomerSysXRef actualXref,ICustomerSysXRef stagingXref) {
		ILimitXRefUdf2 udfData[]=stagingXref.getXRefUdfData2();
		ILimitXRefUdf2 actualUdfData[]=actualXref.getXRefUdfData2();
		if(null!=udfData && null!=actualUdfData) {
			
			 if(null!=udfData[0].getUdf116_Value()&&null!=actualUdfData[0].getUdf116_Value()&& !udfData[0].getUdf116_Value().equals(actualUdfData[0].getUdf116_Value()))
				  	udfData[0].setUdf116_Flag("M");
				  else if(null==udfData[0].getUdf116_Flag() && null!=udfData[0].getUdf116_Value()&& null==actualUdfData[0].getUdf116_Value()&& !udfData[0].getUdf116_Value().equals(actualUdfData[0].getUdf116_Value()))
				  	udfData[0].setUdf116_Flag("A");
				  if(null!=udfData[0].getUdf117_Value()&&null!=actualUdfData[0].getUdf117_Value()&& !udfData[0].getUdf117_Value().equals(actualUdfData[0].getUdf117_Value()))
				  	udfData[0].setUdf117_Flag("M");
				  else if(null==udfData[0].getUdf117_Flag() && null!=udfData[0].getUdf117_Value()&& null==actualUdfData[0].getUdf117_Value()&& !udfData[0].getUdf117_Value().equals(actualUdfData[0].getUdf117_Value()))
				  	udfData[0].setUdf117_Flag("A");
				  if(null!=udfData[0].getUdf118_Value()&&null!=actualUdfData[0].getUdf118_Value()&& !udfData[0].getUdf118_Value().equals(actualUdfData[0].getUdf118_Value()))
				  	udfData[0].setUdf118_Flag("M");
				  else if(null==udfData[0].getUdf118_Flag() && null!=udfData[0].getUdf118_Value()&& null==actualUdfData[0].getUdf118_Value()&& !udfData[0].getUdf118_Value().equals(actualUdfData[0].getUdf118_Value()))
				  	udfData[0].setUdf118_Flag("A");
				  if(null!=udfData[0].getUdf119_Value()&&null!=actualUdfData[0].getUdf119_Value()&& !udfData[0].getUdf119_Value().equals(actualUdfData[0].getUdf119_Value()))
				  	udfData[0].setUdf119_Flag("M");
				  else if(null==udfData[0].getUdf119_Flag() && null!=udfData[0].getUdf119_Value()&& null==actualUdfData[0].getUdf119_Value()&& !udfData[0].getUdf119_Value().equals(actualUdfData[0].getUdf119_Value()))
				  	udfData[0].setUdf119_Flag("A");
				  if(null!=udfData[0].getUdf120_Value()&&null!=actualUdfData[0].getUdf120_Value()&& !udfData[0].getUdf120_Value().equals(actualUdfData[0].getUdf120_Value()))
				  	udfData[0].setUdf120_Flag("M");
				  	else if(null==udfData[0].getUdf120_Flag() && null!=udfData[0].getUdf120_Value()&& null==actualUdfData[0].getUdf120_Value()&& !udfData[0].getUdf120_Value().equals(actualUdfData[0].getUdf120_Value()))
				  	udfData[0].setUdf120_Flag("A");
				  	if(null!=udfData[0].getUdf121_Value()&&null!=actualUdfData[0].getUdf121_Value()&& !udfData[0].getUdf121_Value().equals(actualUdfData[0].getUdf121_Value()))
				  	udfData[0].setUdf121_Flag("M");
				  	else if(null==udfData[0].getUdf121_Flag() && null!=udfData[0].getUdf121_Value()&& null==actualUdfData[0].getUdf121_Value()&& !udfData[0].getUdf121_Value().equals(actualUdfData[0].getUdf121_Value()))
				  	udfData[0].setUdf121_Flag("A");
				  	if(null!=udfData[0].getUdf122_Value()&&null!=actualUdfData[0].getUdf122_Value()&& !udfData[0].getUdf122_Value().equals(actualUdfData[0].getUdf122_Value()))
				  	udfData[0].setUdf122_Flag("M");
				  	else if(null==udfData[0].getUdf122_Flag() && null!=udfData[0].getUdf122_Value()&& null==actualUdfData[0].getUdf122_Value()&& !udfData[0].getUdf122_Value().equals(actualUdfData[0].getUdf122_Value()))
				  	udfData[0].setUdf122_Flag("A");
				  	if(null!=udfData[0].getUdf123_Value()&&null!=actualUdfData[0].getUdf123_Value()&& !udfData[0].getUdf123_Value().equals(actualUdfData[0].getUdf123_Value()))
				  	udfData[0].setUdf123_Flag("M");
				  	else if(null==udfData[0].getUdf123_Flag() && null!=udfData[0].getUdf123_Value()&& null==actualUdfData[0].getUdf123_Value()&& !udfData[0].getUdf123_Value().equals(actualUdfData[0].getUdf123_Value()))
				  	udfData[0].setUdf123_Flag("A");
				  	if(null!=udfData[0].getUdf124_Value()&&null!=actualUdfData[0].getUdf124_Value()&& !udfData[0].getUdf124_Value().equals(actualUdfData[0].getUdf124_Value()))
				  	udfData[0].setUdf124_Flag("M");
				  	else if(null==udfData[0].getUdf124_Flag() && null!=udfData[0].getUdf124_Value()&& null==actualUdfData[0].getUdf124_Value()&& !udfData[0].getUdf124_Value().equals(actualUdfData[0].getUdf124_Value()))
				  	udfData[0].setUdf124_Flag("A");
				  	if(null!=udfData[0].getUdf125_Value()&&null!=actualUdfData[0].getUdf125_Value()&& !udfData[0].getUdf125_Value().equals(actualUdfData[0].getUdf125_Value()))
				  	udfData[0].setUdf125_Flag("M");
				  	else if(null==udfData[0].getUdf125_Flag() && null!=udfData[0].getUdf125_Value()&& null==actualUdfData[0].getUdf125_Value()&& !udfData[0].getUdf125_Value().equals(actualUdfData[0].getUdf125_Value()))
				  	udfData[0].setUdf125_Flag("A");
				  	if(null!=udfData[0].getUdf126_Value()&&null!=actualUdfData[0].getUdf126_Value()&& !udfData[0].getUdf126_Value().equals(actualUdfData[0].getUdf126_Value()))
				  	udfData[0].setUdf126_Flag("M");
				  	else if(null==udfData[0].getUdf126_Flag() && null!=udfData[0].getUdf126_Value()&& null==actualUdfData[0].getUdf126_Value()&& !udfData[0].getUdf126_Value().equals(actualUdfData[0].getUdf126_Value()))
				  	udfData[0].setUdf126_Flag("A");
				  	if(null!=udfData[0].getUdf127_Value()&&null!=actualUdfData[0].getUdf127_Value()&& !udfData[0].getUdf127_Value().equals(actualUdfData[0].getUdf127_Value()))
				  	udfData[0].setUdf127_Flag("M");
				  	else if(null==udfData[0].getUdf127_Flag() && null!=udfData[0].getUdf127_Value()&& null==actualUdfData[0].getUdf127_Value()&& !udfData[0].getUdf127_Value().equals(actualUdfData[0].getUdf127_Value()))
				  	udfData[0].setUdf127_Flag("A");
				  	if(null!=udfData[0].getUdf128_Value()&&null!=actualUdfData[0].getUdf128_Value()&& !udfData[0].getUdf128_Value().equals(actualUdfData[0].getUdf128_Value()))
				  	udfData[0].setUdf121_Flag("M");
				  	else if(null==udfData[0].getUdf128_Flag() && null!=udfData[0].getUdf128_Value()&& null==actualUdfData[0].getUdf128_Value()&& !udfData[0].getUdf128_Value().equals(actualUdfData[0].getUdf128_Value()))
				  	udfData[0].setUdf128_Flag("A");
				  	if(null!=udfData[0].getUdf129_Value()&&null!=actualUdfData[0].getUdf129_Value()&& !udfData[0].getUdf129_Value().equals(actualUdfData[0].getUdf129_Value()))
				  	udfData[0].setUdf129_Flag("M");
				  	else if(null==udfData[0].getUdf129_Flag() && null!=udfData[0].getUdf129_Value()&& null==actualUdfData[0].getUdf129_Value()&& !udfData[0].getUdf129_Value().equals(actualUdfData[0].getUdf129_Value()))
				  	udfData[0].setUdf129_Flag("A");
				  	if(null!=udfData[0].getUdf130_Value()&&null!=actualUdfData[0].getUdf130_Value()&& !udfData[0].getUdf130_Value().equals(actualUdfData[0].getUdf130_Value()))
				  	udfData[0].setUdf130_Flag("M");
				  	else if(null==udfData[0].getUdf130_Flag() && null!=udfData[0].getUdf130_Value()&& null==actualUdfData[0].getUdf130_Value()&& !udfData[0].getUdf130_Value().equals(actualUdfData[0].getUdf130_Value()))
				  	udfData[0].setUdf130_Flag("A");
				  	if(null!=udfData[0].getUdf131_Value()&&null!=actualUdfData[0].getUdf131_Value()&& !udfData[0].getUdf131_Value().equals(actualUdfData[0].getUdf131_Value()))
				  	udfData[0].setUdf131_Flag("M");
				  	else if(null==udfData[0].getUdf131_Flag() && null!=udfData[0].getUdf131_Value()&& null==actualUdfData[0].getUdf131_Value()&& !udfData[0].getUdf131_Value().equals(actualUdfData[0].getUdf131_Value()))
				  	udfData[0].setUdf131_Flag("A");
				  	if(null!=udfData[0].getUdf132_Value()&&null!=actualUdfData[0].getUdf132_Value()&& !udfData[0].getUdf132_Value().equals(actualUdfData[0].getUdf132_Value()))
				  	udfData[0].setUdf132_Flag("M");
				  	else if(null==udfData[0].getUdf132_Flag() && null!=udfData[0].getUdf132_Value()&& null==actualUdfData[0].getUdf132_Value()&& !udfData[0].getUdf132_Value().equals(actualUdfData[0].getUdf132_Value()))
				  	udfData[0].setUdf132_Flag("A");
				  	if(null!=udfData[0].getUdf133_Value()&&null!=actualUdfData[0].getUdf133_Value()&& !udfData[0].getUdf133_Value().equals(actualUdfData[0].getUdf133_Value()))
				  	udfData[0].setUdf133_Flag("M");
				  	else if(null==udfData[0].getUdf133_Flag() && null!=udfData[0].getUdf133_Value()&& null==actualUdfData[0].getUdf133_Value()&& !udfData[0].getUdf133_Value().equals(actualUdfData[0].getUdf133_Value()))
				  	udfData[0].setUdf133_Flag("A");
				  	if(null!=udfData[0].getUdf134_Value()&&null!=actualUdfData[0].getUdf134_Value()&& !udfData[0].getUdf134_Value().equals(actualUdfData[0].getUdf134_Value()))
				  	udfData[0].setUdf134_Flag("M");
				  	else if(null==udfData[0].getUdf134_Flag() && null!=udfData[0].getUdf134_Value()&& null==actualUdfData[0].getUdf134_Value()&& !udfData[0].getUdf134_Value().equals(actualUdfData[0].getUdf134_Value()))
				  	udfData[0].setUdf134_Flag("A");
				  	if(null!=udfData[0].getUdf135_Value()&&null!=actualUdfData[0].getUdf135_Value()&& !udfData[0].getUdf135_Value().equals(actualUdfData[0].getUdf135_Value()))
				  	udfData[0].setUdf135_Flag("M");
				  	else if(null==udfData[0].getUdf135_Flag() && null!=udfData[0].getUdf135_Value()&& null==actualUdfData[0].getUdf135_Value()&& !udfData[0].getUdf135_Value().equals(actualUdfData[0].getUdf135_Value()))
				  	udfData[0].setUdf135_Flag("A");
				  	if(null!=udfData[0].getUdf136_Value()&&null!=actualUdfData[0].getUdf136_Value()&& !udfData[0].getUdf136_Value().equals(actualUdfData[0].getUdf136_Value()))
				  	udfData[0].setUdf136_Flag("M");
				  	else if(null==udfData[0].getUdf136_Flag() && null!=udfData[0].getUdf136_Value()&& null==actualUdfData[0].getUdf136_Value()&& !udfData[0].getUdf136_Value().equals(actualUdfData[0].getUdf136_Value()))
				  	udfData[0].setUdf136_Flag("A");
				  	if(null!=udfData[0].getUdf137_Value()&&null!=actualUdfData[0].getUdf137_Value()&& !udfData[0].getUdf137_Value().equals(actualUdfData[0].getUdf137_Value()))
				  	udfData[0].setUdf137_Flag("M");
				  	else if(null==udfData[0].getUdf137_Flag() && null!=udfData[0].getUdf137_Value()&& null==actualUdfData[0].getUdf137_Value()&& !udfData[0].getUdf137_Value().equals(actualUdfData[0].getUdf137_Value()))
				  	udfData[0].setUdf137_Flag("A");
				  	if(null!=udfData[0].getUdf138_Value()&&null!=actualUdfData[0].getUdf138_Value()&& !udfData[0].getUdf138_Value().equals(actualUdfData[0].getUdf138_Value()))
				  	udfData[0].setUdf131_Flag("M");
				  	else if(null==udfData[0].getUdf138_Flag() && null!=udfData[0].getUdf138_Value()&& null==actualUdfData[0].getUdf138_Value()&& !udfData[0].getUdf138_Value().equals(actualUdfData[0].getUdf138_Value()))
				  	udfData[0].setUdf138_Flag("A");
				  	if(null!=udfData[0].getUdf139_Value()&&null!=actualUdfData[0].getUdf139_Value()&& !udfData[0].getUdf139_Value().equals(actualUdfData[0].getUdf139_Value()))
				  	udfData[0].setUdf139_Flag("M");
				  	else if(null==udfData[0].getUdf139_Flag() && null!=udfData[0].getUdf139_Value()&& null==actualUdfData[0].getUdf139_Value()&& !udfData[0].getUdf139_Value().equals(actualUdfData[0].getUdf139_Value()))
				  	udfData[0].setUdf139_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf140_Value()&&null!=actualUdfData[0].getUdf140_Value()&& !udfData[0].getUdf140_Value().equals(actualUdfData[0].getUdf140_Value()))
				  	udfData[0].setUdf140_Flag("M");
				  	else if(null==udfData[0].getUdf140_Flag() && null!=udfData[0].getUdf140_Value()&& null==actualUdfData[0].getUdf140_Value()&& !udfData[0].getUdf140_Value().equals(actualUdfData[0].getUdf140_Value()))
				  	udfData[0].setUdf140_Flag("A");
				  	if(null!=udfData[0].getUdf141_Value()&&null!=actualUdfData[0].getUdf141_Value()&& !udfData[0].getUdf141_Value().equals(actualUdfData[0].getUdf141_Value()))
				  	udfData[0].setUdf141_Flag("M");
				  	else if(null==udfData[0].getUdf141_Flag() && null!=udfData[0].getUdf141_Value()&& null==actualUdfData[0].getUdf141_Value()&& !udfData[0].getUdf141_Value().equals(actualUdfData[0].getUdf141_Value()))
				  	udfData[0].setUdf141_Flag("A");
				  	if(null!=udfData[0].getUdf142_Value()&&null!=actualUdfData[0].getUdf142_Value()&& !udfData[0].getUdf142_Value().equals(actualUdfData[0].getUdf142_Value()))
				  	udfData[0].setUdf142_Flag("M");
				  	else if(null==udfData[0].getUdf142_Flag() && null!=udfData[0].getUdf142_Value()&& null==actualUdfData[0].getUdf142_Value()&& !udfData[0].getUdf142_Value().equals(actualUdfData[0].getUdf142_Value()))
				  	udfData[0].setUdf142_Flag("A");
				  	if(null!=udfData[0].getUdf143_Value()&&null!=actualUdfData[0].getUdf143_Value()&& !udfData[0].getUdf143_Value().equals(actualUdfData[0].getUdf143_Value()))
				  	udfData[0].setUdf143_Flag("M");
				  	else if(null==udfData[0].getUdf143_Flag() && null!=udfData[0].getUdf143_Value()&& null==actualUdfData[0].getUdf143_Value()&& !udfData[0].getUdf143_Value().equals(actualUdfData[0].getUdf143_Value()))
				  	udfData[0].setUdf143_Flag("A");
				  	if(null!=udfData[0].getUdf144_Value()&&null!=actualUdfData[0].getUdf144_Value()&& !udfData[0].getUdf144_Value().equals(actualUdfData[0].getUdf144_Value()))
				  	udfData[0].setUdf144_Flag("M");
				  	else if(null==udfData[0].getUdf144_Flag() && null!=udfData[0].getUdf144_Value()&& null==actualUdfData[0].getUdf144_Value()&& !udfData[0].getUdf144_Value().equals(actualUdfData[0].getUdf144_Value()))
				  	udfData[0].setUdf144_Flag("A");
				  	if(null!=udfData[0].getUdf145_Value()&&null!=actualUdfData[0].getUdf145_Value()&& !udfData[0].getUdf145_Value().equals(actualUdfData[0].getUdf145_Value()))
				  	udfData[0].setUdf145_Flag("M");
				  	else if(null==udfData[0].getUdf145_Flag() && null!=udfData[0].getUdf145_Value()&& null==actualUdfData[0].getUdf145_Value()&& !udfData[0].getUdf145_Value().equals(actualUdfData[0].getUdf145_Value()))
				  	udfData[0].setUdf145_Flag("A");
				  	if(null!=udfData[0].getUdf146_Value()&&null!=actualUdfData[0].getUdf146_Value()&& !udfData[0].getUdf146_Value().equals(actualUdfData[0].getUdf146_Value()))
				  	udfData[0].setUdf146_Flag("M");
				  	else if(null==udfData[0].getUdf146_Flag() && null!=udfData[0].getUdf146_Value()&& null==actualUdfData[0].getUdf146_Value()&& !udfData[0].getUdf146_Value().equals(actualUdfData[0].getUdf146_Value()))
				  	udfData[0].setUdf146_Flag("A");
				  	if(null!=udfData[0].getUdf147_Value()&&null!=actualUdfData[0].getUdf147_Value()&& !udfData[0].getUdf147_Value().equals(actualUdfData[0].getUdf147_Value()))
				  	udfData[0].setUdf147_Flag("M");
				  	else if(null==udfData[0].getUdf147_Flag() && null!=udfData[0].getUdf147_Value()&& null==actualUdfData[0].getUdf147_Value()&& !udfData[0].getUdf147_Value().equals(actualUdfData[0].getUdf147_Value()))
				  	udfData[0].setUdf147_Flag("A");
				  	if(null!=udfData[0].getUdf148_Value()&&null!=actualUdfData[0].getUdf148_Value()&& !udfData[0].getUdf148_Value().equals(actualUdfData[0].getUdf148_Value()))
				  	udfData[0].setUdf141_Flag("M");
				  	else if(null==udfData[0].getUdf148_Flag() && null!=udfData[0].getUdf148_Value()&& null==actualUdfData[0].getUdf148_Value()&& !udfData[0].getUdf148_Value().equals(actualUdfData[0].getUdf148_Value()))
				  	udfData[0].setUdf148_Flag("A");
				  	if(null!=udfData[0].getUdf149_Value()&&null!=actualUdfData[0].getUdf149_Value()&& !udfData[0].getUdf149_Value().equals(actualUdfData[0].getUdf149_Value()))
				  	udfData[0].setUdf149_Flag("M");
				  	else if(null==udfData[0].getUdf149_Flag() && null!=udfData[0].getUdf149_Value()&& null==actualUdfData[0].getUdf149_Value()&& !udfData[0].getUdf149_Value().equals(actualUdfData[0].getUdf149_Value()))
				  	udfData[0].setUdf149_Flag("A");
				  	if(null!=udfData[0].getUdf150_Value()&&null!=actualUdfData[0].getUdf150_Value()&& !udfData[0].getUdf150_Value().equals(actualUdfData[0].getUdf150_Value()))
				  	udfData[0].setUdf150_Flag("M");
				  	else if(null==udfData[0].getUdf150_Flag() && null!=udfData[0].getUdf150_Value()&& null==actualUdfData[0].getUdf150_Value()&& !udfData[0].getUdf150_Value().equals(actualUdfData[0].getUdf150_Value()))
				  	udfData[0].setUdf150_Flag("A");
				  	if(null!=udfData[0].getUdf151_Value()&&null!=actualUdfData[0].getUdf151_Value()&& !udfData[0].getUdf151_Value().equals(actualUdfData[0].getUdf151_Value()))
				  	udfData[0].setUdf151_Flag("M");
				  	else if(null==udfData[0].getUdf151_Flag() && null!=udfData[0].getUdf151_Value()&& null==actualUdfData[0].getUdf151_Value()&& !udfData[0].getUdf151_Value().equals(actualUdfData[0].getUdf151_Value()))
				  	udfData[0].setUdf151_Flag("A");
				  	if(null!=udfData[0].getUdf152_Value()&&null!=actualUdfData[0].getUdf152_Value()&& !udfData[0].getUdf152_Value().equals(actualUdfData[0].getUdf152_Value()))
				  	udfData[0].setUdf152_Flag("M");
				  	else if(null==udfData[0].getUdf152_Flag() && null!=udfData[0].getUdf152_Value()&& null==actualUdfData[0].getUdf152_Value()&& !udfData[0].getUdf152_Value().equals(actualUdfData[0].getUdf152_Value()))
				  	udfData[0].setUdf152_Flag("A");
				  	if(null!=udfData[0].getUdf153_Value()&&null!=actualUdfData[0].getUdf153_Value()&& !udfData[0].getUdf153_Value().equals(actualUdfData[0].getUdf153_Value()))
				  	udfData[0].setUdf153_Flag("M");
				  	else if(null==udfData[0].getUdf153_Flag() && null!=udfData[0].getUdf153_Value()&& null==actualUdfData[0].getUdf153_Value()&& !udfData[0].getUdf153_Value().equals(actualUdfData[0].getUdf153_Value()))
				  	udfData[0].setUdf153_Flag("A");
				  	if(null!=udfData[0].getUdf154_Value()&&null!=actualUdfData[0].getUdf154_Value()&& !udfData[0].getUdf154_Value().equals(actualUdfData[0].getUdf154_Value()))
				  	udfData[0].setUdf154_Flag("M");
				  	else if(null==udfData[0].getUdf154_Flag() && null!=udfData[0].getUdf154_Value()&& null==actualUdfData[0].getUdf154_Value()&& !udfData[0].getUdf154_Value().equals(actualUdfData[0].getUdf154_Value()))
				  	udfData[0].setUdf154_Flag("A");
				  	if(null!=udfData[0].getUdf155_Value()&&null!=actualUdfData[0].getUdf155_Value()&& !udfData[0].getUdf155_Value().equals(actualUdfData[0].getUdf155_Value()))
				  	udfData[0].setUdf155_Flag("M");
				  	else if(null==udfData[0].getUdf155_Flag() && null!=udfData[0].getUdf155_Value()&& null==actualUdfData[0].getUdf155_Value()&& !udfData[0].getUdf155_Value().equals(actualUdfData[0].getUdf155_Value()))
				  	udfData[0].setUdf155_Flag("A");
				  	if(null!=udfData[0].getUdf156_Value()&&null!=actualUdfData[0].getUdf156_Value()&& !udfData[0].getUdf156_Value().equals(actualUdfData[0].getUdf156_Value()))
				  	udfData[0].setUdf156_Flag("M");
				  	else if(null==udfData[0].getUdf156_Flag() && null!=udfData[0].getUdf156_Value()&& null==actualUdfData[0].getUdf156_Value()&& !udfData[0].getUdf156_Value().equals(actualUdfData[0].getUdf156_Value()))
				  	udfData[0].setUdf156_Flag("A");
				  	if(null!=udfData[0].getUdf157_Value()&&null!=actualUdfData[0].getUdf157_Value()&& !udfData[0].getUdf157_Value().equals(actualUdfData[0].getUdf157_Value()))
				  	udfData[0].setUdf157_Flag("M");
				  	else if(null==udfData[0].getUdf157_Flag() && null!=udfData[0].getUdf157_Value()&& null==actualUdfData[0].getUdf157_Value()&& !udfData[0].getUdf157_Value().equals(actualUdfData[0].getUdf157_Value()))
				  	udfData[0].setUdf157_Flag("A");
				  	if(null!=udfData[0].getUdf158_Value()&&null!=actualUdfData[0].getUdf158_Value()&& !udfData[0].getUdf158_Value().equals(actualUdfData[0].getUdf158_Value()))
				  	udfData[0].setUdf151_Flag("M");
				  	else if(null==udfData[0].getUdf158_Flag() && null!=udfData[0].getUdf158_Value()&& null==actualUdfData[0].getUdf158_Value()&& !udfData[0].getUdf158_Value().equals(actualUdfData[0].getUdf158_Value()))
				  	udfData[0].setUdf158_Flag("A");
				  	if(null!=udfData[0].getUdf159_Value()&&null!=actualUdfData[0].getUdf159_Value()&& !udfData[0].getUdf159_Value().equals(actualUdfData[0].getUdf159_Value()))
				  	udfData[0].setUdf159_Flag("M");
				  	else if(null==udfData[0].getUdf159_Flag() && null!=udfData[0].getUdf159_Value()&& null==actualUdfData[0].getUdf159_Value()&& !udfData[0].getUdf159_Value().equals(actualUdfData[0].getUdf159_Value()))
				  	udfData[0].setUdf159_Flag("A");
				  	if(null!=udfData[0].getUdf160_Value()&&null!=actualUdfData[0].getUdf160_Value()&& !udfData[0].getUdf160_Value().equals(actualUdfData[0].getUdf160_Value()))
				  	udfData[0].setUdf160_Flag("M");
				  	else if(null==udfData[0].getUdf160_Flag() && null!=udfData[0].getUdf160_Value()&& null==actualUdfData[0].getUdf160_Value()&& !udfData[0].getUdf160_Value().equals(actualUdfData[0].getUdf160_Value()))
				  	udfData[0].setUdf160_Flag("A");
				  	if(null!=udfData[0].getUdf161_Value()&&null!=actualUdfData[0].getUdf161_Value()&& !udfData[0].getUdf161_Value().equals(actualUdfData[0].getUdf161_Value()))
				  	udfData[0].setUdf161_Flag("M");
				  	else if(null==udfData[0].getUdf161_Flag() && null!=udfData[0].getUdf161_Value()&& null==actualUdfData[0].getUdf161_Value()&& !udfData[0].getUdf161_Value().equals(actualUdfData[0].getUdf161_Value()))
				  	udfData[0].setUdf161_Flag("A");
				  	if(null!=udfData[0].getUdf162_Value()&&null!=actualUdfData[0].getUdf162_Value()&& !udfData[0].getUdf162_Value().equals(actualUdfData[0].getUdf162_Value()))
				  	udfData[0].setUdf162_Flag("M");
				  	else if(null==udfData[0].getUdf162_Flag() && null!=udfData[0].getUdf162_Value()&& null==actualUdfData[0].getUdf162_Value()&& !udfData[0].getUdf162_Value().equals(actualUdfData[0].getUdf162_Value()))
				  	udfData[0].setUdf162_Flag("A");
				  	if(null!=udfData[0].getUdf163_Value()&&null!=actualUdfData[0].getUdf163_Value()&& !udfData[0].getUdf163_Value().equals(actualUdfData[0].getUdf163_Value()))
				  	udfData[0].setUdf163_Flag("M");
				  	else if(null==udfData[0].getUdf163_Flag() && null!=udfData[0].getUdf163_Value()&& null==actualUdfData[0].getUdf163_Value()&& !udfData[0].getUdf163_Value().equals(actualUdfData[0].getUdf163_Value()))
				  	udfData[0].setUdf163_Flag("A");
				  	if(null!=udfData[0].getUdf164_Value()&&null!=actualUdfData[0].getUdf164_Value()&& !udfData[0].getUdf164_Value().equals(actualUdfData[0].getUdf164_Value()))
				  	udfData[0].setUdf164_Flag("M");
				  	else if(null==udfData[0].getUdf164_Flag() && null!=udfData[0].getUdf164_Value()&& null==actualUdfData[0].getUdf164_Value()&& !udfData[0].getUdf164_Value().equals(actualUdfData[0].getUdf164_Value()))
				  	udfData[0].setUdf164_Flag("A");
				  	if(null!=udfData[0].getUdf165_Value()&&null!=actualUdfData[0].getUdf165_Value()&& !udfData[0].getUdf165_Value().equals(actualUdfData[0].getUdf165_Value()))
				  	udfData[0].setUdf165_Flag("M");
				  	else if(null==udfData[0].getUdf165_Flag() && null!=udfData[0].getUdf165_Value()&& null==actualUdfData[0].getUdf165_Value()&& !udfData[0].getUdf165_Value().equals(actualUdfData[0].getUdf165_Value()))
				  	udfData[0].setUdf165_Flag("A");
				  	if(null!=udfData[0].getUdf166_Value()&&null!=actualUdfData[0].getUdf166_Value()&& !udfData[0].getUdf166_Value().equals(actualUdfData[0].getUdf166_Value()))
				  	udfData[0].setUdf166_Flag("M");
				  	else if(null==udfData[0].getUdf166_Flag() && null!=udfData[0].getUdf166_Value()&& null==actualUdfData[0].getUdf166_Value()&& !udfData[0].getUdf166_Value().equals(actualUdfData[0].getUdf166_Value()))
				  	udfData[0].setUdf166_Flag("A");
				  	if(null!=udfData[0].getUdf167_Value()&&null!=actualUdfData[0].getUdf167_Value()&& !udfData[0].getUdf167_Value().equals(actualUdfData[0].getUdf167_Value()))
				  	udfData[0].setUdf167_Flag("M");
				  	else if(null==udfData[0].getUdf167_Flag() && null!=udfData[0].getUdf167_Value()&& null==actualUdfData[0].getUdf167_Value()&& !udfData[0].getUdf167_Value().equals(actualUdfData[0].getUdf167_Value()))
				  	udfData[0].setUdf167_Flag("A");
				  	if(null!=udfData[0].getUdf168_Value()&&null!=actualUdfData[0].getUdf168_Value()&& !udfData[0].getUdf168_Value().equals(actualUdfData[0].getUdf168_Value()))
				  	udfData[0].setUdf161_Flag("M");
				  	else if(null==udfData[0].getUdf168_Flag() && null!=udfData[0].getUdf168_Value()&& null==actualUdfData[0].getUdf168_Value()&& !udfData[0].getUdf168_Value().equals(actualUdfData[0].getUdf168_Value()))
				  	udfData[0].setUdf168_Flag("A");
				  	if(null!=udfData[0].getUdf169_Value()&&null!=actualUdfData[0].getUdf169_Value()&& !udfData[0].getUdf169_Value().equals(actualUdfData[0].getUdf169_Value()))
				  	udfData[0].setUdf169_Flag("M");
				  	else if(null==udfData[0].getUdf169_Flag() && null!=udfData[0].getUdf169_Value()&& null==actualUdfData[0].getUdf169_Value()&& !udfData[0].getUdf169_Value().equals(actualUdfData[0].getUdf169_Value()))
				  	udfData[0].setUdf169_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf170_Value()&&null!=actualUdfData[0].getUdf170_Value()&& !udfData[0].getUdf170_Value().equals(actualUdfData[0].getUdf170_Value()))
				  	udfData[0].setUdf170_Flag("M");
				  	else if(null==udfData[0].getUdf170_Flag() && null!=udfData[0].getUdf170_Value()&& null==actualUdfData[0].getUdf170_Value()&& !udfData[0].getUdf170_Value().equals(actualUdfData[0].getUdf170_Value()))
				  	udfData[0].setUdf170_Flag("A");
				  	if(null!=udfData[0].getUdf171_Value()&&null!=actualUdfData[0].getUdf171_Value()&& !udfData[0].getUdf171_Value().equals(actualUdfData[0].getUdf171_Value()))
				  	udfData[0].setUdf171_Flag("M");
				  	else if(null==udfData[0].getUdf171_Flag() && null!=udfData[0].getUdf171_Value()&& null==actualUdfData[0].getUdf171_Value()&& !udfData[0].getUdf171_Value().equals(actualUdfData[0].getUdf171_Value()))
				  	udfData[0].setUdf171_Flag("A");
				  	if(null!=udfData[0].getUdf172_Value()&&null!=actualUdfData[0].getUdf172_Value()&& !udfData[0].getUdf172_Value().equals(actualUdfData[0].getUdf172_Value()))
				  	udfData[0].setUdf172_Flag("M");
				  	else if(null==udfData[0].getUdf172_Flag() && null!=udfData[0].getUdf172_Value()&& null==actualUdfData[0].getUdf172_Value()&& !udfData[0].getUdf172_Value().equals(actualUdfData[0].getUdf172_Value()))
				  	udfData[0].setUdf172_Flag("A");
				  	if(null!=udfData[0].getUdf173_Value()&&null!=actualUdfData[0].getUdf173_Value()&& !udfData[0].getUdf173_Value().equals(actualUdfData[0].getUdf173_Value()))
				  	udfData[0].setUdf173_Flag("M");
				  	else if(null==udfData[0].getUdf173_Flag() && null!=udfData[0].getUdf173_Value()&& null==actualUdfData[0].getUdf173_Value()&& !udfData[0].getUdf173_Value().equals(actualUdfData[0].getUdf173_Value()))
				  	udfData[0].setUdf173_Flag("A");
				  	if(null!=udfData[0].getUdf174_Value()&&null!=actualUdfData[0].getUdf174_Value()&& !udfData[0].getUdf174_Value().equals(actualUdfData[0].getUdf174_Value()))
				  	udfData[0].setUdf174_Flag("M");
				  	else if(null==udfData[0].getUdf174_Flag() && null!=udfData[0].getUdf174_Value()&& null==actualUdfData[0].getUdf174_Value()&& !udfData[0].getUdf174_Value().equals(actualUdfData[0].getUdf174_Value()))
				  	udfData[0].setUdf174_Flag("A");
				  	if(null!=udfData[0].getUdf175_Value()&&null!=actualUdfData[0].getUdf175_Value()&& !udfData[0].getUdf175_Value().equals(actualUdfData[0].getUdf175_Value()))
				  	udfData[0].setUdf175_Flag("M");
				  	else if(null==udfData[0].getUdf175_Flag() && null!=udfData[0].getUdf175_Value()&& null==actualUdfData[0].getUdf175_Value()&& !udfData[0].getUdf175_Value().equals(actualUdfData[0].getUdf175_Value()))
				  	udfData[0].setUdf175_Flag("A");
				  	if(null!=udfData[0].getUdf176_Value()&&null!=actualUdfData[0].getUdf176_Value()&& !udfData[0].getUdf176_Value().equals(actualUdfData[0].getUdf176_Value()))
				  	udfData[0].setUdf176_Flag("M");
				  	else if(null==udfData[0].getUdf176_Flag() && null!=udfData[0].getUdf176_Value()&& null==actualUdfData[0].getUdf176_Value()&& !udfData[0].getUdf176_Value().equals(actualUdfData[0].getUdf176_Value()))
				  	udfData[0].setUdf176_Flag("A");
				  	if(null!=udfData[0].getUdf177_Value()&&null!=actualUdfData[0].getUdf177_Value()&& !udfData[0].getUdf177_Value().equals(actualUdfData[0].getUdf177_Value()))
				  	udfData[0].setUdf177_Flag("M");
				  	else if(null==udfData[0].getUdf177_Flag() && null!=udfData[0].getUdf177_Value()&& null==actualUdfData[0].getUdf177_Value()&& !udfData[0].getUdf177_Value().equals(actualUdfData[0].getUdf177_Value()))
				  	udfData[0].setUdf177_Flag("A");
				  	if(null!=udfData[0].getUdf178_Value()&&null!=actualUdfData[0].getUdf178_Value()&& !udfData[0].getUdf178_Value().equals(actualUdfData[0].getUdf178_Value()))
				  	udfData[0].setUdf171_Flag("M");
				  	else if(null==udfData[0].getUdf178_Flag() && null!=udfData[0].getUdf178_Value()&& null==actualUdfData[0].getUdf178_Value()&& !udfData[0].getUdf178_Value().equals(actualUdfData[0].getUdf178_Value()))
				  	udfData[0].setUdf178_Flag("A");
				  	if(null!=udfData[0].getUdf179_Value()&&null!=actualUdfData[0].getUdf179_Value()&& !udfData[0].getUdf179_Value().equals(actualUdfData[0].getUdf179_Value()))
				  	udfData[0].setUdf179_Flag("M");
				  	else if(null==udfData[0].getUdf179_Flag() && null!=udfData[0].getUdf179_Value()&& null==actualUdfData[0].getUdf179_Value()&& !udfData[0].getUdf179_Value().equals(actualUdfData[0].getUdf179_Value()))
				  	udfData[0].setUdf179_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf180_Value()&&null!=actualUdfData[0].getUdf180_Value()&& !udfData[0].getUdf180_Value().equals(actualUdfData[0].getUdf180_Value()))
				  	udfData[0].setUdf180_Flag("M");
				  	else if(null==udfData[0].getUdf180_Flag() && null!=udfData[0].getUdf180_Value()&& null==actualUdfData[0].getUdf180_Value()&& !udfData[0].getUdf180_Value().equals(actualUdfData[0].getUdf180_Value()))
				  	udfData[0].setUdf180_Flag("A");
				  	if(null!=udfData[0].getUdf181_Value()&&null!=actualUdfData[0].getUdf181_Value()&& !udfData[0].getUdf181_Value().equals(actualUdfData[0].getUdf181_Value()))
				  	udfData[0].setUdf181_Flag("M");
				  	else if(null==udfData[0].getUdf181_Flag() && null!=udfData[0].getUdf181_Value()&& null==actualUdfData[0].getUdf181_Value()&& !udfData[0].getUdf181_Value().equals(actualUdfData[0].getUdf181_Value()))
				  	udfData[0].setUdf181_Flag("A");
				  	if(null!=udfData[0].getUdf182_Value()&&null!=actualUdfData[0].getUdf182_Value()&& !udfData[0].getUdf182_Value().equals(actualUdfData[0].getUdf182_Value()))
				  	udfData[0].setUdf182_Flag("M");
				  	else if(null==udfData[0].getUdf182_Flag() && null!=udfData[0].getUdf182_Value()&& null==actualUdfData[0].getUdf182_Value()&& !udfData[0].getUdf182_Value().equals(actualUdfData[0].getUdf182_Value()))
				  	udfData[0].setUdf182_Flag("A");
				  	if(null!=udfData[0].getUdf183_Value()&&null!=actualUdfData[0].getUdf183_Value()&& !udfData[0].getUdf183_Value().equals(actualUdfData[0].getUdf183_Value()))
				  	udfData[0].setUdf183_Flag("M");
				  	else if(null==udfData[0].getUdf183_Flag() && null!=udfData[0].getUdf183_Value()&& null==actualUdfData[0].getUdf183_Value()&& !udfData[0].getUdf183_Value().equals(actualUdfData[0].getUdf183_Value()))
				  	udfData[0].setUdf183_Flag("A");
				  	if(null!=udfData[0].getUdf184_Value()&&null!=actualUdfData[0].getUdf184_Value()&& !udfData[0].getUdf184_Value().equals(actualUdfData[0].getUdf184_Value()))
				  	udfData[0].setUdf184_Flag("M");
				  	else if(null==udfData[0].getUdf184_Flag() && null!=udfData[0].getUdf184_Value()&& null==actualUdfData[0].getUdf184_Value()&& !udfData[0].getUdf184_Value().equals(actualUdfData[0].getUdf184_Value()))
				  	udfData[0].setUdf184_Flag("A");
				  	if(null!=udfData[0].getUdf185_Value()&&null!=actualUdfData[0].getUdf185_Value()&& !udfData[0].getUdf185_Value().equals(actualUdfData[0].getUdf185_Value()))
				  	udfData[0].setUdf185_Flag("M");
				  	else if(null==udfData[0].getUdf185_Flag() && null!=udfData[0].getUdf185_Value()&& null==actualUdfData[0].getUdf185_Value()&& !udfData[0].getUdf185_Value().equals(actualUdfData[0].getUdf185_Value()))
				  	udfData[0].setUdf185_Flag("A");
				  	if(null!=udfData[0].getUdf186_Value()&&null!=actualUdfData[0].getUdf186_Value()&& !udfData[0].getUdf186_Value().equals(actualUdfData[0].getUdf186_Value()))
				  	udfData[0].setUdf186_Flag("M");
				  	else if(null==udfData[0].getUdf186_Flag() && null!=udfData[0].getUdf186_Value()&& null==actualUdfData[0].getUdf186_Value()&& !udfData[0].getUdf186_Value().equals(actualUdfData[0].getUdf186_Value()))
				  	udfData[0].setUdf186_Flag("A");
				  	if(null!=udfData[0].getUdf187_Value()&&null!=actualUdfData[0].getUdf187_Value()&& !udfData[0].getUdf187_Value().equals(actualUdfData[0].getUdf187_Value()))
				  	udfData[0].setUdf187_Flag("M");
				  	else if(null==udfData[0].getUdf187_Flag() && null!=udfData[0].getUdf187_Value()&& null==actualUdfData[0].getUdf187_Value()&& !udfData[0].getUdf187_Value().equals(actualUdfData[0].getUdf187_Value()))
				  	udfData[0].setUdf187_Flag("A");
				  	if(null!=udfData[0].getUdf188_Value()&&null!=actualUdfData[0].getUdf188_Value()&& !udfData[0].getUdf188_Value().equals(actualUdfData[0].getUdf188_Value()))
				  	udfData[0].setUdf181_Flag("M");
				  	else if(null==udfData[0].getUdf188_Flag() && null!=udfData[0].getUdf188_Value()&& null==actualUdfData[0].getUdf188_Value()&& !udfData[0].getUdf188_Value().equals(actualUdfData[0].getUdf188_Value()))
				  	udfData[0].setUdf188_Flag("A");
				  	if(null!=udfData[0].getUdf189_Value()&&null!=actualUdfData[0].getUdf189_Value()&& !udfData[0].getUdf189_Value().equals(actualUdfData[0].getUdf189_Value()))
				  	udfData[0].setUdf189_Flag("M");
				  	else if(null==udfData[0].getUdf189_Flag() && null!=udfData[0].getUdf189_Value()&& null==actualUdfData[0].getUdf189_Value()&& !udfData[0].getUdf189_Value().equals(actualUdfData[0].getUdf189_Value()))
				  	udfData[0].setUdf189_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf190_Value()&&null!=actualUdfData[0].getUdf190_Value()&& !udfData[0].getUdf190_Value().equals(actualUdfData[0].getUdf190_Value()))
				  	udfData[0].setUdf190_Flag("M");
				  	else if(null==udfData[0].getUdf190_Flag() && null!=udfData[0].getUdf190_Value()&& null==actualUdfData[0].getUdf190_Value()&& !udfData[0].getUdf190_Value().equals(actualUdfData[0].getUdf190_Value()))
				  	udfData[0].setUdf190_Flag("A");
				  	if(null!=udfData[0].getUdf191_Value()&&null!=actualUdfData[0].getUdf191_Value()&& !udfData[0].getUdf191_Value().equals(actualUdfData[0].getUdf191_Value()))
				  	udfData[0].setUdf191_Flag("M");
				  	else if(null==udfData[0].getUdf191_Flag() && null!=udfData[0].getUdf191_Value()&& null==actualUdfData[0].getUdf191_Value()&& !udfData[0].getUdf191_Value().equals(actualUdfData[0].getUdf191_Value()))
				  	udfData[0].setUdf191_Flag("A");
				  	if(null!=udfData[0].getUdf192_Value()&&null!=actualUdfData[0].getUdf192_Value()&& !udfData[0].getUdf192_Value().equals(actualUdfData[0].getUdf192_Value()))
				  	udfData[0].setUdf192_Flag("M");
				  	else if(null==udfData[0].getUdf192_Flag() && null!=udfData[0].getUdf192_Value()&& null==actualUdfData[0].getUdf192_Value()&& !udfData[0].getUdf192_Value().equals(actualUdfData[0].getUdf192_Value()))
				  	udfData[0].setUdf192_Flag("A");
				  	if(null!=udfData[0].getUdf193_Value()&&null!=actualUdfData[0].getUdf193_Value()&& !udfData[0].getUdf193_Value().equals(actualUdfData[0].getUdf193_Value()))
				  	udfData[0].setUdf193_Flag("M");
				  	else if(null==udfData[0].getUdf193_Flag() && null!=udfData[0].getUdf193_Value()&& null==actualUdfData[0].getUdf193_Value()&& !udfData[0].getUdf193_Value().equals(actualUdfData[0].getUdf193_Value()))
				  	udfData[0].setUdf193_Flag("A");
				  	if(null!=udfData[0].getUdf194_Value()&&null!=actualUdfData[0].getUdf194_Value()&& !udfData[0].getUdf194_Value().equals(actualUdfData[0].getUdf194_Value()))
				  	udfData[0].setUdf194_Flag("M");
				  	else if(null==udfData[0].getUdf194_Flag() && null!=udfData[0].getUdf194_Value()&& null==actualUdfData[0].getUdf194_Value()&& !udfData[0].getUdf194_Value().equals(actualUdfData[0].getUdf194_Value()))
				  	udfData[0].setUdf194_Flag("A");
				  	if(null!=udfData[0].getUdf195_Value()&&null!=actualUdfData[0].getUdf195_Value()&& !udfData[0].getUdf195_Value().equals(actualUdfData[0].getUdf195_Value()))
				  	udfData[0].setUdf195_Flag("M");
				  	else if(null==udfData[0].getUdf195_Flag() && null!=udfData[0].getUdf195_Value()&& null==actualUdfData[0].getUdf195_Value()&& !udfData[0].getUdf195_Value().equals(actualUdfData[0].getUdf195_Value()))
				  	udfData[0].setUdf195_Flag("A");
				  	if(null!=udfData[0].getUdf196_Value()&&null!=actualUdfData[0].getUdf196_Value()&& !udfData[0].getUdf196_Value().equals(actualUdfData[0].getUdf196_Value()))
				  	udfData[0].setUdf196_Flag("M");
				  	else if(null==udfData[0].getUdf196_Flag() && null!=udfData[0].getUdf196_Value()&& null==actualUdfData[0].getUdf196_Value()&& !udfData[0].getUdf196_Value().equals(actualUdfData[0].getUdf196_Value()))
				  	udfData[0].setUdf196_Flag("A");
				  	if(null!=udfData[0].getUdf197_Value()&&null!=actualUdfData[0].getUdf197_Value()&& !udfData[0].getUdf197_Value().equals(actualUdfData[0].getUdf197_Value()))
				  	udfData[0].setUdf197_Flag("M");
				  	else if(null==udfData[0].getUdf197_Flag() && null!=udfData[0].getUdf197_Value()&& null==actualUdfData[0].getUdf197_Value()&& !udfData[0].getUdf197_Value().equals(actualUdfData[0].getUdf197_Value()))
				  	udfData[0].setUdf197_Flag("A");
				  	if(null!=udfData[0].getUdf198_Value()&&null!=actualUdfData[0].getUdf198_Value()&& !udfData[0].getUdf198_Value().equals(actualUdfData[0].getUdf198_Value()))
				  	udfData[0].setUdf191_Flag("M");
				  	else if(null==udfData[0].getUdf198_Flag() && null!=udfData[0].getUdf198_Value()&& null==actualUdfData[0].getUdf198_Value()&& !udfData[0].getUdf198_Value().equals(actualUdfData[0].getUdf198_Value()))
				  	udfData[0].setUdf198_Flag("A");
				  	if(null!=udfData[0].getUdf199_Value()&&null!=actualUdfData[0].getUdf199_Value()&& !udfData[0].getUdf199_Value().equals(actualUdfData[0].getUdf199_Value()))
				  	udfData[0].setUdf199_Flag("M");
				  	else if(null==udfData[0].getUdf199_Flag() && null!=udfData[0].getUdf199_Value()&& null==actualUdfData[0].getUdf199_Value()&& !udfData[0].getUdf199_Value().equals(actualUdfData[0].getUdf199_Value()))
				  	udfData[0].setUdf199_Flag("A");
				  	 
				  	if(null!=udfData[0].getUdf200_Value()&&null!=actualUdfData[0].getUdf200_Value()&& !udfData[0].getUdf200_Value().equals(actualUdfData[0].getUdf200_Value()))
				  		udfData[0].setUdf200_Flag("M");
				  	else if(null==udfData[0].getUdf200_Flag() && null!=udfData[0].getUdf200_Value()&& null==actualUdfData[0].getUdf200_Value()&& !udfData[0].getUdf200_Value().equals(actualUdfData[0].getUdf200_Value()))
				  		udfData[0].setUdf200_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf201_Value()&&null!=actualUdfData[0].getUdf201_Value()&& !udfData[0].getUdf201_Value().equals(actualUdfData[0].getUdf201_Value()))
				  		udfData[0].setUdf201_Flag("M");
				  	else if(null==udfData[0].getUdf201_Flag() && null!=udfData[0].getUdf201_Value()&& null==actualUdfData[0].getUdf201_Value()&& !udfData[0].getUdf201_Value().equals(actualUdfData[0].getUdf201_Value()))
				  		udfData[0].setUdf201_Flag("A");
				  	
				    	
				  	if(null!=udfData[0].getUdf202_Value()&&null!=actualUdfData[0].getUdf202_Value()&& !udfData[0].getUdf202_Value().equals(actualUdfData[0].getUdf202_Value()))
				  		udfData[0].setUdf202_Flag("M");
				  	else if(null==udfData[0].getUdf202_Flag() && null!=udfData[0].getUdf202_Value()&& null==actualUdfData[0].getUdf202_Value()&& !udfData[0].getUdf202_Value().equals(actualUdfData[0].getUdf202_Value()))
				  		udfData[0].setUdf202_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf203_Value()&&null!=actualUdfData[0].getUdf203_Value()&& !udfData[0].getUdf203_Value().equals(actualUdfData[0].getUdf203_Value()))
				  		udfData[0].setUdf203_Flag("M");
				  	else if(null==udfData[0].getUdf203_Flag() && null!=udfData[0].getUdf203_Value()&& null==actualUdfData[0].getUdf203_Value()&& !udfData[0].getUdf203_Value().equals(actualUdfData[0].getUdf203_Value()))
				  		udfData[0].setUdf203_Flag("A");

				  	if(null!=udfData[0].getUdf204_Value()&&null!=actualUdfData[0].getUdf204_Value()&& !udfData[0].getUdf204_Value().equals(actualUdfData[0].getUdf204_Value()))
				  		udfData[0].setUdf204_Flag("M");
				  	else if(null==udfData[0].getUdf204_Flag() && null!=udfData[0].getUdf204_Value()&& null==actualUdfData[0].getUdf204_Value()&& !udfData[0].getUdf204_Value().equals(actualUdfData[0].getUdf204_Value()))
				  		udfData[0].setUdf204_Flag("A");

				  	if(null!=udfData[0].getUdf205_Value()&&null!=actualUdfData[0].getUdf205_Value()&& !udfData[0].getUdf205_Value().equals(actualUdfData[0].getUdf205_Value()))
				  		udfData[0].setUdf205_Flag("M");
				  	else if(null==udfData[0].getUdf205_Flag() && null!=udfData[0].getUdf205_Value()&& null==actualUdfData[0].getUdf205_Value()&& !udfData[0].getUdf205_Value().equals(actualUdfData[0].getUdf205_Value()))
				  		udfData[0].setUdf205_Flag("A");
				  	
				  	if(null!=udfData[0].getUdf206_Value()&&null!=actualUdfData[0].getUdf206_Value()&& !udfData[0].getUdf206_Value().equals(actualUdfData[0].getUdf206_Value()))
				  		udfData[0].setUdf206_Flag("M");
				  	else if(null==udfData[0].getUdf206_Flag() && null!=udfData[0].getUdf206_Value()&& null==actualUdfData[0].getUdf206_Value()&& !udfData[0].getUdf206_Value().equals(actualUdfData[0].getUdf206_Value()))
				  		udfData[0].setUdf206_Flag("A");

				  	if(null!=udfData[0].getUdf207_Value()&&null!=actualUdfData[0].getUdf207_Value()&& !udfData[0].getUdf207_Value().equals(actualUdfData[0].getUdf207_Value()))
				  		udfData[0].setUdf207_Flag("M");
				  	else if(null==udfData[0].getUdf207_Flag() && null!=udfData[0].getUdf207_Value()&& null==actualUdfData[0].getUdf207_Value()&& !udfData[0].getUdf207_Value().equals(actualUdfData[0].getUdf207_Value()))
				  		udfData[0].setUdf207_Flag("A");

				  	if(null!=udfData[0].getUdf208_Value()&&null!=actualUdfData[0].getUdf208_Value()&& !udfData[0].getUdf208_Value().equals(actualUdfData[0].getUdf208_Value()))
				  		udfData[0].setUdf208_Flag("M");
				  	else if(null==udfData[0].getUdf208_Flag() && null!=udfData[0].getUdf208_Value()&& null==actualUdfData[0].getUdf208_Value()&& !udfData[0].getUdf208_Value().equals(actualUdfData[0].getUdf208_Value()))
				  		udfData[0].setUdf208_Flag("A");

				  	if(null!=udfData[0].getUdf209_Value()&&null!=actualUdfData[0].getUdf209_Value()&& !udfData[0].getUdf209_Value().equals(actualUdfData[0].getUdf209_Value()))
				  		udfData[0].setUdf209_Flag("M");
				  	else if(null==udfData[0].getUdf209_Flag() && null!=udfData[0].getUdf209_Value()&& null==actualUdfData[0].getUdf209_Value()&& !udfData[0].getUdf209_Value().equals(actualUdfData[0].getUdf209_Value()))
				  		udfData[0].setUdf209_Flag("A");

				  	if(null!=udfData[0].getUdf210_Value()&&null!=actualUdfData[0].getUdf210_Value()&& !udfData[0].getUdf210_Value().equals(actualUdfData[0].getUdf210_Value()))
				  		udfData[0].setUdf210_Flag("M");
				  	else if(null==udfData[0].getUdf210_Flag() && null!=udfData[0].getUdf210_Value()&& null==actualUdfData[0].getUdf210_Value()&& !udfData[0].getUdf210_Value().equals(actualUdfData[0].getUdf210_Value()))
				  		udfData[0].setUdf210_Flag("A");


				  	if(null!=udfData[0].getUdf211_Value()&&null!=actualUdfData[0].getUdf211_Value()&& !udfData[0].getUdf211_Value().equals(actualUdfData[0].getUdf211_Value()))
				  		udfData[0].setUdf211_Flag("M");
				  	else if(null==udfData[0].getUdf211_Flag() && null!=udfData[0].getUdf211_Value()&& null==actualUdfData[0].getUdf211_Value()&& !udfData[0].getUdf211_Value().equals(actualUdfData[0].getUdf211_Value()))
				  		udfData[0].setUdf211_Flag("A");

				  	if(null!=udfData[0].getUdf212_Value()&&null!=actualUdfData[0].getUdf212_Value()&& !udfData[0].getUdf212_Value().equals(actualUdfData[0].getUdf212_Value()))
				  		udfData[0].setUdf212_Flag("M");
				  	else if(null==udfData[0].getUdf212_Flag() && null!=udfData[0].getUdf212_Value()&& null==actualUdfData[0].getUdf212_Value()&& !udfData[0].getUdf212_Value().equals(actualUdfData[0].getUdf212_Value()))
				  		udfData[0].setUdf212_Flag("A");

				  	if(null!=udfData[0].getUdf213_Value()&&null!=actualUdfData[0].getUdf213_Value()&& !udfData[0].getUdf213_Value().equals(actualUdfData[0].getUdf213_Value()))
				  		udfData[0].setUdf213_Flag("M");
				  	else if(null==udfData[0].getUdf213_Flag() && null!=udfData[0].getUdf213_Value()&& null==actualUdfData[0].getUdf213_Value()&& !udfData[0].getUdf213_Value().equals(actualUdfData[0].getUdf213_Value()))
				  		udfData[0].setUdf213_Flag("A");

				  	if(null!=udfData[0].getUdf214_Value()&&null!=actualUdfData[0].getUdf214_Value()&& !udfData[0].getUdf214_Value().equals(actualUdfData[0].getUdf214_Value()))
				  		udfData[0].setUdf214_Flag("M");
				  	else if(null==udfData[0].getUdf214_Flag() && null!=udfData[0].getUdf214_Value()&& null==actualUdfData[0].getUdf214_Value()&& !udfData[0].getUdf214_Value().equals(actualUdfData[0].getUdf214_Value()))
				  		udfData[0].setUdf214_Flag("A");

				  	if(null!=udfData[0].getUdf215_Value()&&null!=actualUdfData[0].getUdf215_Value()&& !udfData[0].getUdf215_Value().equals(actualUdfData[0].getUdf215_Value()))
				  		udfData[0].setUdf215_Flag("M");
				  	else if(null==udfData[0].getUdf215_Flag() && null!=udfData[0].getUdf215_Value()&& null==actualUdfData[0].getUdf215_Value()&& !udfData[0].getUdf215_Value().equals(actualUdfData[0].getUdf215_Value()))
				  		udfData[0].setUdf215_Flag("A");
				  
				  
	}
	}
	private void setUdfLabel(ICustomerSysXRef stagingXref){
		ILimitXRefUdf udfData[]=stagingXref.getXRefUdfData();
		String udfAllowedList[] = null;
		if(null!=udfData){
			
			String udfAllowed =stagingXref.getUdfAllowed();
			//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
			IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
			List udfList = udfDao.getUdfByMandatory("3");
			List udfLbValList = new ArrayList();
			String id="";
			if(udfList!=null && udfList.size() != 0)
			{
				int size = udfList.size();
				Collections.sort(udfList, new UDFComparator());
				IUdf udf;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList.get(i);
					if("".equals(id))
						id=Long.toString(udf.getSequence());
					else
						id=id+","+Long.toString(udf.getSequence());
				}
			}
			
			if(!"".equalsIgnoreCase(id)){
				if(null==udfAllowed)
					udfAllowed=id;
				else
					udfAllowed=udfAllowed+","+id;
			}
			if(null!=udfAllowed) {
				udfAllowedList=udfAllowed.split(",");
			
			for(int i=0;i<udfAllowedList.length;i++) {
				switch (Integer.parseInt(udfAllowedList[i])) {
					case 1:udfData[0].setUdf1_Label(getUdfLabel(udfAllowedList[i])); break;
					case 2:udfData[0].setUdf2_Label(getUdfLabel(udfAllowedList[i])); break;
					case 3:udfData[0].setUdf3_Label(getUdfLabel(udfAllowedList[i])); break;
					case 4:udfData[0].setUdf4_Label(getUdfLabel(udfAllowedList[i])); break;
					case 5:udfData[0].setUdf5_Label(getUdfLabel(udfAllowedList[i])); break;
					case 6:udfData[0].setUdf6_Label(getUdfLabel(udfAllowedList[i])); break;
					case 7:udfData[0].setUdf7_Label(getUdfLabel(udfAllowedList[i])); break;
					case 8:udfData[0].setUdf8_Label(getUdfLabel(udfAllowedList[i])); break;
					case 9:udfData[0].setUdf9_Label(getUdfLabel(udfAllowedList[i])); break;
					case 10:udfData[0].setUdf10_Label(getUdfLabel(udfAllowedList[i])); break;
					case 11:udfData[0].setUdf11_Label(getUdfLabel(udfAllowedList[i])); break;
					case 12:udfData[0].setUdf12_Label(getUdfLabel(udfAllowedList[i])); break;
					case 13:udfData[0].setUdf13_Label(getUdfLabel(udfAllowedList[i])); break;
					case 14:udfData[0].setUdf14_Label(getUdfLabel(udfAllowedList[i])); break;
					case 15:udfData[0].setUdf15_Label(getUdfLabel(udfAllowedList[i])); break;
					case 16:udfData[0].setUdf16_Label(getUdfLabel(udfAllowedList[i])); break;
					case 17:udfData[0].setUdf17_Label(getUdfLabel(udfAllowedList[i])); break;
					case 18:udfData[0].setUdf18_Label(getUdfLabel(udfAllowedList[i])); break;
					case 19:udfData[0].setUdf19_Label(getUdfLabel(udfAllowedList[i])); break;
					case 20:udfData[0].setUdf20_Label(getUdfLabel(udfAllowedList[i])); break;
					case 21:udfData[0].setUdf21_Label(getUdfLabel(udfAllowedList[i])); break;
					case 22:udfData[0].setUdf22_Label(getUdfLabel(udfAllowedList[i])); break;
					case 23:udfData[0].setUdf23_Label(getUdfLabel(udfAllowedList[i])); break;
					case 24:udfData[0].setUdf24_Label(getUdfLabel(udfAllowedList[i])); break;
					case 25:udfData[0].setUdf25_Label(getUdfLabel(udfAllowedList[i])); break;
					case 26:udfData[0].setUdf26_Label(getUdfLabel(udfAllowedList[i])); break;
					case 27:udfData[0].setUdf27_Label(getUdfLabel(udfAllowedList[i])); break;
					case 28:udfData[0].setUdf28_Label(getUdfLabel(udfAllowedList[i])); break;
					case 29:udfData[0].setUdf29_Label(getUdfLabel(udfAllowedList[i])); break;
					case 30:udfData[0].setUdf30_Label(getUdfLabel(udfAllowedList[i])); break;
					case 31:udfData[0].setUdf31_Label(getUdfLabel(udfAllowedList[i])); break;
					case 32:udfData[0].setUdf32_Label(getUdfLabel(udfAllowedList[i])); break;
					case 33:udfData[0].setUdf33_Label(getUdfLabel(udfAllowedList[i])); break;
					case 34:udfData[0].setUdf34_Label(getUdfLabel(udfAllowedList[i])); break;
					case 35:udfData[0].setUdf35_Label(getUdfLabel(udfAllowedList[i])); break;
					case 36:udfData[0].setUdf36_Label(getUdfLabel(udfAllowedList[i])); break;
					case 37:udfData[0].setUdf37_Label(getUdfLabel(udfAllowedList[i])); break;
					case 38:udfData[0].setUdf38_Label(getUdfLabel(udfAllowedList[i])); break;
					case 39:udfData[0].setUdf39_Label(getUdfLabel(udfAllowedList[i])); break;
					case 40:udfData[0].setUdf40_Label(getUdfLabel(udfAllowedList[i])); break;
					case 41:udfData[0].setUdf41_Label(getUdfLabel(udfAllowedList[i])); break;
					case 42:udfData[0].setUdf42_Label(getUdfLabel(udfAllowedList[i])); break;
					case 43:udfData[0].setUdf43_Label(getUdfLabel(udfAllowedList[i])); break;
					case 44:udfData[0].setUdf44_Label(getUdfLabel(udfAllowedList[i])); break;
					case 45:udfData[0].setUdf45_Label(getUdfLabel(udfAllowedList[i])); break;
					case 46:udfData[0].setUdf46_Label(getUdfLabel(udfAllowedList[i])); break;
					case 47:udfData[0].setUdf47_Label(getUdfLabel(udfAllowedList[i])); break;
					case 48:udfData[0].setUdf48_Label(getUdfLabel(udfAllowedList[i])); break;
					case 49:udfData[0].setUdf49_Label(getUdfLabel(udfAllowedList[i])); break;
					case 50:udfData[0].setUdf50_Label(getUdfLabel(udfAllowedList[i])); break;
					case 51:udfData[0].setUdf51_Label(getUdfLabel(udfAllowedList[i])); break;
					case 52:udfData[0].setUdf52_Label(getUdfLabel(udfAllowedList[i])); break;
					case 53:udfData[0].setUdf53_Label(getUdfLabel(udfAllowedList[i])); break;
					case 54:udfData[0].setUdf54_Label(getUdfLabel(udfAllowedList[i])); break;
					case 55:udfData[0].setUdf55_Label(getUdfLabel(udfAllowedList[i])); break;
					case 56:udfData[0].setUdf56_Label(getUdfLabel(udfAllowedList[i])); break;
					case 57:udfData[0].setUdf57_Label(getUdfLabel(udfAllowedList[i])); break;
					case 58:udfData[0].setUdf58_Label(getUdfLabel(udfAllowedList[i])); break;
					case 59:udfData[0].setUdf59_Label(getUdfLabel(udfAllowedList[i])); break;
					case 60:udfData[0].setUdf60_Label(getUdfLabel(udfAllowedList[i])); break;
					case 61:udfData[0].setUdf61_Label(getUdfLabel(udfAllowedList[i])); break;
					case 62:udfData[0].setUdf62_Label(getUdfLabel(udfAllowedList[i])); break;
					case 63:udfData[0].setUdf63_Label(getUdfLabel(udfAllowedList[i])); break;
					case 64:udfData[0].setUdf64_Label(getUdfLabel(udfAllowedList[i])); break;
					case 65:udfData[0].setUdf65_Label(getUdfLabel(udfAllowedList[i])); break;
					case 66:udfData[0].setUdf66_Label(getUdfLabel(udfAllowedList[i])); break;
					case 67:udfData[0].setUdf67_Label(getUdfLabel(udfAllowedList[i])); break;
					case 68:udfData[0].setUdf68_Label(getUdfLabel(udfAllowedList[i])); break;
					case 69:udfData[0].setUdf69_Label(getUdfLabel(udfAllowedList[i])); break;
					case 70:udfData[0].setUdf70_Label(getUdfLabel(udfAllowedList[i])); break;
					case 71:udfData[0].setUdf71_Label(getUdfLabel(udfAllowedList[i])); break;
					case 72:udfData[0].setUdf72_Label(getUdfLabel(udfAllowedList[i])); break;
					case 73:udfData[0].setUdf73_Label(getUdfLabel(udfAllowedList[i])); break;
					case 74:udfData[0].setUdf74_Label(getUdfLabel(udfAllowedList[i])); break;
					case 75:udfData[0].setUdf75_Label(getUdfLabel(udfAllowedList[i])); break;
					case 76:udfData[0].setUdf76_Label(getUdfLabel(udfAllowedList[i])); break;
					case 77:udfData[0].setUdf77_Label(getUdfLabel(udfAllowedList[i])); break;
					case 78:udfData[0].setUdf78_Label(getUdfLabel(udfAllowedList[i])); break;
					case 79:udfData[0].setUdf79_Label(getUdfLabel(udfAllowedList[i])); break;
					case 80:udfData[0].setUdf80_Label(getUdfLabel(udfAllowedList[i])); break;
					case 81:udfData[0].setUdf81_Label(getUdfLabel(udfAllowedList[i])); break;
					case 82:udfData[0].setUdf82_Label(getUdfLabel(udfAllowedList[i])); break;
					case 83:udfData[0].setUdf83_Label(getUdfLabel(udfAllowedList[i])); break;
					case 84:udfData[0].setUdf84_Label(getUdfLabel(udfAllowedList[i])); break;
					case 85:udfData[0].setUdf85_Label(getUdfLabel(udfAllowedList[i])); break;
					case 86:udfData[0].setUdf86_Label(getUdfLabel(udfAllowedList[i])); break;
					case 87:udfData[0].setUdf87_Label(getUdfLabel(udfAllowedList[i])); break;
					case 88:udfData[0].setUdf88_Label(getUdfLabel(udfAllowedList[i])); break;
					case 89:udfData[0].setUdf89_Label(getUdfLabel(udfAllowedList[i])); break;
					case 90:udfData[0].setUdf90_Label(getUdfLabel(udfAllowedList[i])); break;
					case 91:udfData[0].setUdf91_Label(getUdfLabel(udfAllowedList[i])); break;
					case 92:udfData[0].setUdf92_Label(getUdfLabel(udfAllowedList[i])); break;
					case 93:udfData[0].setUdf93_Label(getUdfLabel(udfAllowedList[i])); break;
					case 94:udfData[0].setUdf94_Label(getUdfLabel(udfAllowedList[i])); break;
					case 95:udfData[0].setUdf95_Label(getUdfLabel(udfAllowedList[i])); break;
					case 96:udfData[0].setUdf96_Label(getUdfLabel(udfAllowedList[i])); break;
					case 97:udfData[0].setUdf97_Label(getUdfLabel(udfAllowedList[i])); break;
					case 98:udfData[0].setUdf98_Label(getUdfLabel(udfAllowedList[i])); break;
					case 99:udfData[0].setUdf99_Label(getUdfLabel(udfAllowedList[i])); break;
					case 100:udfData[0].setUdf100_Label(getUdfLabel(udfAllowedList[i])); break;
					
					case 101:udfData[0].setUdf101_Label(getUdfLabel(udfAllowedList[i])); break;
					case 102:udfData[0].setUdf102_Label(getUdfLabel(udfAllowedList[i])); break;
					case 103:udfData[0].setUdf103_Label(getUdfLabel(udfAllowedList[i])); break;
					case 104:udfData[0].setUdf104_Label(getUdfLabel(udfAllowedList[i])); break;
					case 105:udfData[0].setUdf105_Label(getUdfLabel(udfAllowedList[i])); break;
					case 106:udfData[0].setUdf106_Label(getUdfLabel(udfAllowedList[i])); break;
					case 107:udfData[0].setUdf107_Label(getUdfLabel(udfAllowedList[i])); break;
					case 108:udfData[0].setUdf108_Label(getUdfLabel(udfAllowedList[i])); break;
					case 109:udfData[0].setUdf109_Label(getUdfLabel(udfAllowedList[i])); break;
					case 110:udfData[0].setUdf110_Label(getUdfLabel(udfAllowedList[i])); break;
					case 111:udfData[0].setUdf111_Label(getUdfLabel(udfAllowedList[i])); break;
					case 112:udfData[0].setUdf112_Label(getUdfLabel(udfAllowedList[i])); break;
					case 113:udfData[0].setUdf113_Label(getUdfLabel(udfAllowedList[i])); break;
					case 114:udfData[0].setUdf114_Label(getUdfLabel(udfAllowedList[i])); break;
					case 115:udfData[0].setUdf115_Label(getUdfLabel(udfAllowedList[i])); break;
					
				}
			}
			}
		}
	}
	//end santosh
	
	private void setUdfLabel_2(ICustomerSysXRef stagingXref){

	ILimitXRefUdf2 udfData[]=stagingXref.getXRefUdfData2();
	String udfAllowedList[] = null;
	if(null!=udfData){
		
		String udfAllowed =stagingXref.getUdfAllowed_2();
		//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
		List udfList = udfDao.getUdfByMandatory("4");
		List udfLbValList = new ArrayList();
		String id="";
		if(udfList!=null && udfList.size() != 0)
		{
			int size = udfList.size();
			Collections.sort(udfList, new UDFComparator());
			IUdf udf;
			for (int i=0; i<size; i++) {
				udf = (IUdf) udfList.get(i);
				if("".equals(id))
					id=Long.toString(udf.getSequence()+115);
				else
					id=id+","+Long.toString(udf.getSequence()+115);
			}
		}
		
		if(!"".equalsIgnoreCase(id)){
			if(null==udfAllowed)
				udfAllowed=id;
			else
				udfAllowed=udfAllowed+","+id;
		}
		if(null!=udfAllowed) {
			udfAllowedList=udfAllowed.split(",");
		
		for(int i=0;i<udfAllowedList.length;i++) {
			switch (Integer.parseInt(udfAllowedList[i])) {
			
			case 1:udfData[0].setUdf116_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 2:udfData[0].setUdf117_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 3:udfData[0].setUdf118_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 4:udfData[0].setUdf119_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 5:udfData[0].setUdf120_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 6:udfData[0].setUdf121_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 7:udfData[0].setUdf122_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 8:udfData[0].setUdf123_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 9:udfData[0].setUdf124_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 10:udfData[0].setUdf125_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 11:udfData[0].setUdf126_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 12:udfData[0].setUdf127_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 13:udfData[0].setUdf128_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 14:udfData[0].setUdf129_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 15:udfData[0].setUdf130_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 16:udfData[0].setUdf131_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 17:udfData[0].setUdf132_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 18:udfData[0].setUdf133_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 19:udfData[0].setUdf134_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 20:udfData[0].setUdf135_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 21:udfData[0].setUdf136_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 22:udfData[0].setUdf137_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 23:udfData[0].setUdf138_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 24:udfData[0].setUdf139_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 25:udfData[0].setUdf140_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 26:udfData[0].setUdf141_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 27:udfData[0].setUdf142_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 28:udfData[0].setUdf143_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 29:udfData[0].setUdf144_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 30:udfData[0].setUdf145_Label(getUdfLabel_2(udfAllowedList[i])); break;
			
			case 31:udfData[0].setUdf146_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 32:udfData[0].setUdf147_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 33:udfData[0].setUdf148_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 34:udfData[0].setUdf149_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 35:udfData[0].setUdf150_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 36:udfData[0].setUdf151_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 37:udfData[0].setUdf152_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 38:udfData[0].setUdf153_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 39:udfData[0].setUdf154_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 40:udfData[0].setUdf155_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 41:udfData[0].setUdf156_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 42:udfData[0].setUdf157_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 43:udfData[0].setUdf158_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 44:udfData[0].setUdf159_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 45:udfData[0].setUdf160_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 46:udfData[0].setUdf161_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 47:udfData[0].setUdf162_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 48:udfData[0].setUdf163_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 49:udfData[0].setUdf164_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 50:udfData[0].setUdf165_Label(getUdfLabel_2(udfAllowedList[i])); break;
			
			case 51:udfData[0].setUdf166_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 52:udfData[0].setUdf167_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 53:udfData[0].setUdf168_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 54:udfData[0].setUdf169_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 55:udfData[0].setUdf170_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 56:udfData[0].setUdf171_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 57:udfData[0].setUdf172_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 58:udfData[0].setUdf173_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 59:udfData[0].setUdf174_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 60:udfData[0].setUdf175_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 61:udfData[0].setUdf176_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 62:udfData[0].setUdf177_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 63:udfData[0].setUdf178_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 64:udfData[0].setUdf179_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 65:udfData[0].setUdf180_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 66:udfData[0].setUdf181_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 67:udfData[0].setUdf182_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 68:udfData[0].setUdf183_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 69:udfData[0].setUdf184_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 70:udfData[0].setUdf185_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 71:udfData[0].setUdf186_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 72:udfData[0].setUdf187_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 73:udfData[0].setUdf188_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 74:udfData[0].setUdf189_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 75:udfData[0].setUdf190_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 76:udfData[0].setUdf191_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 77:udfData[0].setUdf192_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 78:udfData[0].setUdf193_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 79:udfData[0].setUdf194_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 80:udfData[0].setUdf195_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 81:udfData[0].setUdf196_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 82:udfData[0].setUdf197_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 83:udfData[0].setUdf198_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 84:udfData[0].setUdf199_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 85:udfData[0].setUdf200_Label(getUdfLabel_2(udfAllowedList[i])); break;

                        case 86:udfData[0].setUdf201_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 87:udfData[0].setUdf202_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 88:udfData[0].setUdf203_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 89:udfData[0].setUdf204_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 90:udfData[0].setUdf205_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 91:udfData[0].setUdf206_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 92:udfData[0].setUdf207_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 93:udfData[0].setUdf208_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 94:udfData[0].setUdf209_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 95:udfData[0].setUdf210_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 96:udfData[0].setUdf211_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 97:udfData[0].setUdf212_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 98:udfData[0].setUdf213_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 99:udfData[0].setUdf214_Label(getUdfLabel_2(udfAllowedList[i])); break;
			case 100:udfData[0].setUdf215_Label(getUdfLabel_2(udfAllowedList[i])); break;
			
	
				  
			}
		}
		}
	}
}
	private String getFlag(String act,String stg) {
//		String L1 = "20171128000000017,20171128000000019,20171128000000016";
//		String L2 = "20171128000000017,20171128000000016,20171128000000019";
		String l3 = "";
		
		if(!act.equalsIgnoreCase(stg)){
			
			System.out.println("List l1 is ");
			String[] L11 = act.split(",");
			String[] L12 = stg.split(",");
			for(String string : L11){
				
				if(!stg.contains(string)){
					
					l3 = l3+string+"#D,";
				}
			}
			for(String string : L12){
				
				if(!act.contains(string)){
					
					l3 = l3+string+"#A,";
				}
			}
			
		}
		if(!l3.isEmpty())
			l3=l3.substring(0,l3.length()-1);
		
		return l3.trim();
	}
	
	private String getAddFlag(String  stg) {
			String l3 = "";
		
		if(null!=stg){
			String[] L12 = stg.split(",");
			for(String string : L12){
				
				l3 = l3+string+"#A,";
				
			}
		}
		if(!l3.isEmpty())
			l3=l3.substring(0,l3.length()-1);
		
		return l3.trim();
	}
	
	private String getModifyFlag(String act,String  stg,String stgFlag) {
		DefaultLogger.debug(this, "1914 - getModifyFlag ========================= act:"+act+" ====stg:"+stg+" ====stgFlag:"+stgFlag);
		String l3 = "";
		if(act.equalsIgnoreCase(stg)){
		return stgFlag;
		}else if (!act.equalsIgnoreCase(stg)){
			
		DefaultLogger.debug(this, "1920 - getModifyFlag ========================= act.split");	
		String[] L11 = act.split(",");
		DefaultLogger.debug(this, "1922 - getModifyFlag ========================= stg.split");
		String[] L12 = stg.split(",");
		if(null != L11 && !"".equals(L11)){
		for(String string : L11){
			if(stg.contains(string)){
				if(null != stgFlag){
				String[] L13=stgFlag.split(",");
				for(String string2 : L13){
					if(string2.contains(string+"#")){
						l3=l3+string2+",";
						break;
					}
				}
				}
			
			}
			
		}
		}
		for(String string : L12){
			if(!act.contains(string)){
				l3 = l3+string+"#A,";
			}
			
		}
	}
	if(!l3.isEmpty())
		l3=l3.substring(0,l3.length()-1);
	
	return l3;
		
}
	//Start Santosh UBS_LIMIT UPLOAD
		private void updateLineCurrency(long limitProfileID,String newCurrency) throws Exception{
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String facilitySystem = "'"+bundle.getString("fcubs.systemName")+"','"+bundle.getString("ubs.systemName")+"'";
			DBUtil dbUtil = null;
			
			String sql ="update SCI_LSP_SYS_XREF line set line.CURRENCY=? where line.CMS_LSP_SYS_XREF_ID IN"
					+ " (select map.CMS_LSP_SYS_XREF_ID from SCI_LSP_lmts_xref_map map where map.cms_lsp_appr_lmts_id IN"
					+ " (select limit.cms_lsp_appr_lmts_id from sci_lsp_appr_lmts limit where limit.cms_limit_profile_id='"+limitProfileID+"' AND limit.facility_system IN ("+facilitySystem+")))";
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				dbUtil.setString(1, newCurrency);
				dbUtil.executeUpdate();
				dbUtil.commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
			finally {
				if(null!=dbUtil )
					dbUtil.close();
			}
		}
		//End Santosh UBS_LIMIT UPLOAD

		
		
		
		private void updateUdfModifyForRejectionCase(ICustomerSysXRef actualXref,ICustomerSysXRef stagingXref) {
			ILimitXRefUdf udfData[]=stagingXref.getXRefUdfData();
			ILimitXRefUdf actualUdfData[]=actualXref.getXRefUdfData();
			
			if(null!=udfData && null!=actualUdfData) {
				
				
				if(null!=actualUdfData[0].getUdf1_Flag() && null != udfData[0].getUdf1_Value() && null != actualUdfData[0].getUdf1_Value())
					udfData[0].setUdf1_Flag(actualUdfData[0].getUdf1_Flag());
				else if(null==actualUdfData[0].getUdf1_Flag() && null!=actualUdfData[0].getUdf1_Value() && null != udfData[0].getUdf1_Value() && !udfData[0].getUdf1_Value().equals(actualUdfData[0].getUdf1_Value()))
					udfData[0].setUdf1_Flag("M");
				else if(null==actualUdfData[0].getUdf1_Flag() && null==actualUdfData[0].getUdf1_Value() && null!=udfData[0].getUdf1_Value())
					udfData[0].setUdf1_Flag("A");
				else if(null!=actualUdfData[0].getUdf1_Flag())
					udfData[0].setUdf1_Flag(actualUdfData[0].getUdf1_Flag());
					
					if(null!=actualUdfData[0].getUdf2_Flag() && null != udfData[0].getUdf2_Value() && null != actualUdfData[0].getUdf2_Value())
					udfData[0].setUdf2_Flag(actualUdfData[0].getUdf2_Flag());
				else if(null==actualUdfData[0].getUdf2_Flag() && null!=actualUdfData[0].getUdf2_Value() && null != udfData[0].getUdf2_Value() && !udfData[0].getUdf2_Value().equals(actualUdfData[0].getUdf2_Value()))
					udfData[0].setUdf2_Flag("M");
				else if(null==actualUdfData[0].getUdf2_Flag() && null==actualUdfData[0].getUdf2_Value() && null!=udfData[0].getUdf2_Value())
					udfData[0].setUdf2_Flag("A");
					else if(null!=actualUdfData[0].getUdf2_Flag())
					udfData[0].setUdf2_Flag(actualUdfData[0].getUdf2_Flag());
					
					
					if(null!=actualUdfData[0].getUdf3_Flag() && null != udfData[0].getUdf3_Value() && null != actualUdfData[0].getUdf3_Value())
					udfData[0].setUdf3_Flag(actualUdfData[0].getUdf3_Flag());
				else if(null==actualUdfData[0].getUdf3_Flag() && null!=actualUdfData[0].getUdf3_Value() && null != udfData[0].getUdf3_Value() && !udfData[0].getUdf3_Value().equals(actualUdfData[0].getUdf3_Value()))
					udfData[0].setUdf3_Flag("M");
				else if(null==actualUdfData[0].getUdf3_Flag() && null==actualUdfData[0].getUdf3_Value() && null!=udfData[0].getUdf3_Value())
					udfData[0].setUdf3_Flag("A");
					else if(null!=actualUdfData[0].getUdf3_Flag())
					udfData[0].setUdf3_Flag(actualUdfData[0].getUdf3_Flag());
					
					
					if(null!=actualUdfData[0].getUdf4_Flag() && null != udfData[0].getUdf4_Value() && null != actualUdfData[0].getUdf4_Value())
					udfData[0].setUdf4_Flag(actualUdfData[0].getUdf4_Flag());
				else if(null==actualUdfData[0].getUdf4_Flag() && null!=actualUdfData[0].getUdf4_Value() && null != udfData[0].getUdf4_Value() && !udfData[0].getUdf4_Value().equals(actualUdfData[0].getUdf4_Value()))
					udfData[0].setUdf4_Flag("M");
				else if(null==actualUdfData[0].getUdf4_Flag() && null==actualUdfData[0].getUdf4_Value() && null!=udfData[0].getUdf4_Value())
					udfData[0].setUdf4_Flag("A");
					else if(null!=actualUdfData[0].getUdf4_Flag())
					udfData[0].setUdf4_Flag(actualUdfData[0].getUdf4_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf5_Flag() && null != udfData[0].getUdf5_Value() && null != actualUdfData[0].getUdf5_Value())
					udfData[0].setUdf5_Flag(actualUdfData[0].getUdf5_Flag());
				else if(null==actualUdfData[0].getUdf5_Flag() && null!=actualUdfData[0].getUdf5_Value() && null != udfData[0].getUdf5_Value() && !udfData[0].getUdf5_Value().equals(actualUdfData[0].getUdf5_Value()))
					udfData[0].setUdf5_Flag("M");
				else if(null==actualUdfData[0].getUdf5_Flag() && null==actualUdfData[0].getUdf5_Value() && null!=udfData[0].getUdf5_Value())
					udfData[0].setUdf5_Flag("A");
					else if(null!=actualUdfData[0].getUdf5_Flag())
					udfData[0].setUdf5_Flag(actualUdfData[0].getUdf5_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf6_Flag() && null != udfData[0].getUdf6_Value() && null != actualUdfData[0].getUdf6_Value())
					udfData[0].setUdf6_Flag(actualUdfData[0].getUdf6_Flag());
				else if(null==actualUdfData[0].getUdf6_Flag() && null!=actualUdfData[0].getUdf6_Value() && null != udfData[0].getUdf6_Value() && !udfData[0].getUdf6_Value().equals(actualUdfData[0].getUdf6_Value()))
					udfData[0].setUdf6_Flag("M");
				else if(null==actualUdfData[0].getUdf6_Flag() && null==actualUdfData[0].getUdf6_Value() && null!=udfData[0].getUdf6_Value())
					udfData[0].setUdf6_Flag("A");
					else if(null!=actualUdfData[0].getUdf6_Flag())
					udfData[0].setUdf6_Flag(actualUdfData[0].getUdf6_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf7_Flag() && null != udfData[0].getUdf7_Value() && null != actualUdfData[0].getUdf7_Value() )
					udfData[0].setUdf7_Flag(actualUdfData[0].getUdf7_Flag());
				else if(null==actualUdfData[0].getUdf7_Flag() && null!=actualUdfData[0].getUdf7_Value() && null != udfData[0].getUdf7_Value() && !udfData[0].getUdf7_Value().equals(actualUdfData[0].getUdf7_Value()))
					udfData[0].setUdf7_Flag("M");
				else if(null==actualUdfData[0].getUdf7_Flag() && null==actualUdfData[0].getUdf7_Value() && null!=udfData[0].getUdf7_Value())
					udfData[0].setUdf7_Flag("A");
					else if(null!=actualUdfData[0].getUdf7_Flag())
					udfData[0].setUdf7_Flag(actualUdfData[0].getUdf7_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf8_Flag() && null != udfData[0].getUdf8_Value() && null != actualUdfData[0].getUdf8_Value() )
					udfData[0].setUdf8_Flag(actualUdfData[0].getUdf8_Flag());
				else if(null==actualUdfData[0].getUdf8_Flag() && null!=actualUdfData[0].getUdf8_Value() && null != udfData[0].getUdf8_Value() && !udfData[0].getUdf8_Value().equals(actualUdfData[0].getUdf8_Value()))
					udfData[0].setUdf8_Flag("M");
				else if(null==actualUdfData[0].getUdf8_Flag() && null==actualUdfData[0].getUdf8_Value() && null!=udfData[0].getUdf8_Value())
					udfData[0].setUdf8_Flag("A");
					else if(null!=actualUdfData[0].getUdf8_Flag())
					udfData[0].setUdf8_Flag(actualUdfData[0].getUdf8_Flag());
					
					
					if(null!=actualUdfData[0].getUdf9_Flag() && null != udfData[0].getUdf9_Value() && null != actualUdfData[0].getUdf9_Value())
					udfData[0].setUdf9_Flag(actualUdfData[0].getUdf9_Flag());
				else if(null==actualUdfData[0].getUdf9_Flag() && null!=actualUdfData[0].getUdf9_Value() && null != udfData[0].getUdf9_Value() && !udfData[0].getUdf9_Value().equals(actualUdfData[0].getUdf9_Value()))
					udfData[0].setUdf9_Flag("M");
				else if(null==actualUdfData[0].getUdf9_Flag() && null==actualUdfData[0].getUdf9_Value() && null!=udfData[0].getUdf9_Value())
					udfData[0].setUdf9_Flag("A");
					else if(null!=actualUdfData[0].getUdf9_Flag())
					udfData[0].setUdf9_Flag(actualUdfData[0].getUdf9_Flag());
					
				
					
					
					if(null!=actualUdfData[0].getUdf10_Flag() && null != udfData[0].getUdf10_Value() && null != actualUdfData[0].getUdf10_Value())
						udfData[0].setUdf10_Flag(actualUdfData[0].getUdf10_Flag());
					else if(null==actualUdfData[0].getUdf10_Flag() && null!=actualUdfData[0].getUdf10_Value() && null != udfData[0].getUdf10_Value() && !udfData[0].getUdf10_Value().equals(actualUdfData[0].getUdf10_Value()))
						udfData[0].setUdf10_Flag("M");
					else if(null==actualUdfData[0].getUdf10_Flag() && null==actualUdfData[0].getUdf10_Value() && null!=udfData[0].getUdf10_Value())
						udfData[0].setUdf10_Flag("A");
					else if(null!=actualUdfData[0].getUdf10_Flag() && null==actualUdfData[0].getUdf10_Value() && null==udfData[0].getUdf10_Value())
						udfData[0].setUdf10_Flag("D");


					if(null!=actualUdfData[0].getUdf10_Flag() && null != udfData[0].getUdf10_Value() && null != actualUdfData[0].getUdf10_Value())
						udfData[0].setUdf10_Flag(actualUdfData[0].getUdf10_Flag());
					else if(null==actualUdfData[0].getUdf10_Flag() && null!=actualUdfData[0].getUdf10_Value() && null != udfData[0].getUdf10_Value() && !udfData[0].getUdf10_Value().equals(actualUdfData[0].getUdf10_Value()))
						udfData[0].setUdf10_Flag("M");
					else if(null==actualUdfData[0].getUdf10_Flag() && null==actualUdfData[0].getUdf10_Value() && null!=udfData[0].getUdf10_Value())
						udfData[0].setUdf10_Flag("A");
					else if(null!=actualUdfData[0].getUdf10_Flag())
						udfData[0].setUdf10_Flag(actualUdfData[0].getUdf10_Flag());




if(null!=actualUdfData[0].getUdf11_Flag() && null != udfData[0].getUdf11_Value() && null != actualUdfData[0].getUdf11_Value())
					udfData[0].setUdf11_Flag(actualUdfData[0].getUdf11_Flag());
				else if(null==actualUdfData[0].getUdf11_Flag() && null!=actualUdfData[0].getUdf11_Value() && null != udfData[0].getUdf11_Value() && !udfData[0].getUdf11_Value().equals(actualUdfData[0].getUdf11_Value()))
					udfData[0].setUdf11_Flag("M");
				else if(null==actualUdfData[0].getUdf11_Flag() && null==actualUdfData[0].getUdf11_Value() && null!=udfData[0].getUdf11_Value())
					udfData[0].setUdf11_Flag("A");
				else if(null!=actualUdfData[0].getUdf11_Flag())
					udfData[0].setUdf11_Flag(actualUdfData[0].getUdf11_Flag());
					
					if(null!=actualUdfData[0].getUdf12_Flag() && null != udfData[0].getUdf12_Value() && null != actualUdfData[0].getUdf12_Value())
					udfData[0].setUdf12_Flag(actualUdfData[0].getUdf12_Flag());
				else if(null==actualUdfData[0].getUdf12_Flag() && null!=actualUdfData[0].getUdf12_Value() && null != udfData[0].getUdf12_Value() && !udfData[0].getUdf12_Value().equals(actualUdfData[0].getUdf12_Value()))
					udfData[0].setUdf12_Flag("M");
				else if(null==actualUdfData[0].getUdf12_Flag() && null==actualUdfData[0].getUdf12_Value() && null!=udfData[0].getUdf12_Value())
					udfData[0].setUdf12_Flag("A");
					else if(null!=actualUdfData[0].getUdf12_Flag())
					udfData[0].setUdf12_Flag(actualUdfData[0].getUdf12_Flag());
					
					
					if(null!=actualUdfData[0].getUdf13_Flag() && null != udfData[0].getUdf13_Value() && null != actualUdfData[0].getUdf13_Value())
					udfData[0].setUdf13_Flag(actualUdfData[0].getUdf13_Flag());
				else if(null==actualUdfData[0].getUdf13_Flag() && null!=actualUdfData[0].getUdf13_Value() && null != udfData[0].getUdf13_Value() && !udfData[0].getUdf13_Value().equals(actualUdfData[0].getUdf13_Value()))
					udfData[0].setUdf13_Flag("M");
				else if(null==actualUdfData[0].getUdf13_Flag() && null==actualUdfData[0].getUdf13_Value() && null!=udfData[0].getUdf13_Value())
					udfData[0].setUdf13_Flag("A");
					else if(null!=actualUdfData[0].getUdf13_Flag())
					udfData[0].setUdf13_Flag(actualUdfData[0].getUdf13_Flag());
					
					
					if(null!=actualUdfData[0].getUdf14_Flag() && null != udfData[0].getUdf14_Value() && null != actualUdfData[0].getUdf14_Value())
					udfData[0].setUdf14_Flag(actualUdfData[0].getUdf14_Flag());
				else if(null==actualUdfData[0].getUdf14_Flag() && null!=actualUdfData[0].getUdf14_Value() && null != udfData[0].getUdf14_Value() && !udfData[0].getUdf14_Value().equals(actualUdfData[0].getUdf14_Value()))
					udfData[0].setUdf14_Flag("M");
				else if(null==actualUdfData[0].getUdf14_Flag() && null==actualUdfData[0].getUdf14_Value() && null!=udfData[0].getUdf14_Value())
					udfData[0].setUdf14_Flag("A");
					else if(null!=actualUdfData[0].getUdf14_Flag())
					udfData[0].setUdf14_Flag(actualUdfData[0].getUdf14_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf15_Flag() && null != udfData[0].getUdf15_Value() && null != actualUdfData[0].getUdf15_Value())
					udfData[0].setUdf15_Flag(actualUdfData[0].getUdf15_Flag());
				else if(null==actualUdfData[0].getUdf15_Flag() && null!=actualUdfData[0].getUdf15_Value() && null != udfData[0].getUdf15_Value() && !udfData[0].getUdf15_Value().equals(actualUdfData[0].getUdf15_Value()))
					udfData[0].setUdf15_Flag("M");
				else if(null==actualUdfData[0].getUdf15_Flag() && null==actualUdfData[0].getUdf15_Value() && null!=udfData[0].getUdf15_Value())
					udfData[0].setUdf15_Flag("A");
					else if(null!=actualUdfData[0].getUdf15_Flag())
					udfData[0].setUdf15_Flag(actualUdfData[0].getUdf15_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf16_Flag() && null != udfData[0].getUdf16_Value() && null != actualUdfData[0].getUdf16_Value())
					udfData[0].setUdf16_Flag(actualUdfData[0].getUdf16_Flag());
				else if(null==actualUdfData[0].getUdf16_Flag() && null!=actualUdfData[0].getUdf16_Value() && null != udfData[0].getUdf16_Value() && !udfData[0].getUdf16_Value().equals(actualUdfData[0].getUdf16_Value()))
					udfData[0].setUdf16_Flag("M");
				else if(null==actualUdfData[0].getUdf16_Flag() && null==actualUdfData[0].getUdf16_Value() && null!=udfData[0].getUdf16_Value())
					udfData[0].setUdf16_Flag("A");
					else if(null!=actualUdfData[0].getUdf16_Flag())
					udfData[0].setUdf16_Flag(actualUdfData[0].getUdf16_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf17_Flag() && null != udfData[0].getUdf17_Value() && null != actualUdfData[0].getUdf17_Value() )
					udfData[0].setUdf17_Flag(actualUdfData[0].getUdf17_Flag());
				else if(null==actualUdfData[0].getUdf17_Flag() && null!=actualUdfData[0].getUdf17_Value() && null != udfData[0].getUdf17_Value() && !udfData[0].getUdf17_Value().equals(actualUdfData[0].getUdf17_Value()))
					udfData[0].setUdf17_Flag("M");
				else if(null==actualUdfData[0].getUdf17_Flag() && null==actualUdfData[0].getUdf17_Value() && null!=udfData[0].getUdf17_Value())
					udfData[0].setUdf17_Flag("A");
					else if(null!=actualUdfData[0].getUdf17_Flag())
					udfData[0].setUdf17_Flag(actualUdfData[0].getUdf17_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf18_Flag() && null != udfData[0].getUdf18_Value() && null != actualUdfData[0].getUdf18_Value() )
					udfData[0].setUdf18_Flag(actualUdfData[0].getUdf18_Flag());
				else if(null==actualUdfData[0].getUdf18_Flag() && null!=actualUdfData[0].getUdf18_Value() && null != udfData[0].getUdf18_Value() && !udfData[0].getUdf18_Value().equals(actualUdfData[0].getUdf18_Value()))
					udfData[0].setUdf18_Flag("M");
				else if(null==actualUdfData[0].getUdf18_Flag() && null==actualUdfData[0].getUdf18_Value() && null!=udfData[0].getUdf18_Value())
					udfData[0].setUdf18_Flag("A");
					else if(null!=actualUdfData[0].getUdf18_Flag())
					udfData[0].setUdf18_Flag(actualUdfData[0].getUdf18_Flag());
					
					
					if(null!=actualUdfData[0].getUdf19_Flag() && null != udfData[0].getUdf19_Value() && null != actualUdfData[0].getUdf19_Value())
					udfData[0].setUdf19_Flag(actualUdfData[0].getUdf19_Flag());
				else if(null==actualUdfData[0].getUdf19_Flag() && null!=actualUdfData[0].getUdf19_Value() && null != udfData[0].getUdf19_Value() && !udfData[0].getUdf19_Value().equals(actualUdfData[0].getUdf19_Value()))
					udfData[0].setUdf19_Flag("M");
				else if(null==actualUdfData[0].getUdf19_Flag() && null==actualUdfData[0].getUdf19_Value() && null!=udfData[0].getUdf19_Value())
					udfData[0].setUdf19_Flag("A");
					else if(null!=actualUdfData[0].getUdf19_Flag())
					udfData[0].setUdf19_Flag(actualUdfData[0].getUdf19_Flag());
					
				
					
					if(null!=actualUdfData[0].getUdf20_Flag() && null != udfData[0].getUdf20_Value() && null != actualUdfData[0].getUdf20_Value())
						udfData[0].setUdf20_Flag(actualUdfData[0].getUdf20_Flag());
					else if(null==actualUdfData[0].getUdf20_Flag() && null!=actualUdfData[0].getUdf20_Value() && null != udfData[0].getUdf20_Value() && !udfData[0].getUdf20_Value().equals(actualUdfData[0].getUdf20_Value()))
						udfData[0].setUdf20_Flag("M");
					else if(null==actualUdfData[0].getUdf20_Flag() && null==actualUdfData[0].getUdf20_Value() && null!=udfData[0].getUdf20_Value())
						udfData[0].setUdf20_Flag("A");
					else if(null!=actualUdfData[0].getUdf20_Flag())
						udfData[0].setUdf20_Flag(actualUdfData[0].getUdf20_Flag());




if(null!=actualUdfData[0].getUdf21_Flag() && null != udfData[0].getUdf21_Value() && null != actualUdfData[0].getUdf21_Value())
					udfData[0].setUdf21_Flag(actualUdfData[0].getUdf21_Flag());
				else if(null==actualUdfData[0].getUdf21_Flag() && null!=actualUdfData[0].getUdf21_Value() && null != udfData[0].getUdf21_Value() && !udfData[0].getUdf21_Value().equals(actualUdfData[0].getUdf21_Value()))
					udfData[0].setUdf21_Flag("M");
				else if(null==actualUdfData[0].getUdf21_Flag() && null==actualUdfData[0].getUdf21_Value() && null!=udfData[0].getUdf21_Value())
					udfData[0].setUdf21_Flag("A");
				else if(null!=actualUdfData[0].getUdf21_Flag())
					udfData[0].setUdf21_Flag(actualUdfData[0].getUdf21_Flag());
					
					if(null!=actualUdfData[0].getUdf22_Flag() && null != udfData[0].getUdf22_Value() && null != actualUdfData[0].getUdf22_Value())
					udfData[0].setUdf22_Flag(actualUdfData[0].getUdf22_Flag());
				else if(null==actualUdfData[0].getUdf22_Flag() && null!=actualUdfData[0].getUdf22_Value() && null != udfData[0].getUdf22_Value() && !udfData[0].getUdf22_Value().equals(actualUdfData[0].getUdf22_Value()))
					udfData[0].setUdf22_Flag("M");
				else if(null==actualUdfData[0].getUdf22_Flag() && null==actualUdfData[0].getUdf22_Value() && null!=udfData[0].getUdf22_Value())
					udfData[0].setUdf22_Flag("A");
					else if(null!=actualUdfData[0].getUdf22_Flag())
					udfData[0].setUdf22_Flag(actualUdfData[0].getUdf22_Flag());
					
					
					if(null!=actualUdfData[0].getUdf23_Flag() && null != udfData[0].getUdf23_Value() && null != actualUdfData[0].getUdf23_Value())
					udfData[0].setUdf23_Flag(actualUdfData[0].getUdf23_Flag());
				else if(null==actualUdfData[0].getUdf23_Flag() && null!=actualUdfData[0].getUdf23_Value() && null != udfData[0].getUdf23_Value() && !udfData[0].getUdf23_Value().equals(actualUdfData[0].getUdf23_Value()))
					udfData[0].setUdf23_Flag("M");
				else if(null==actualUdfData[0].getUdf23_Flag() && null==actualUdfData[0].getUdf23_Value() && null!=udfData[0].getUdf23_Value())
					udfData[0].setUdf23_Flag("A");
					else if(null!=actualUdfData[0].getUdf23_Flag())
					udfData[0].setUdf23_Flag(actualUdfData[0].getUdf23_Flag());
					
					
					if(null!=actualUdfData[0].getUdf24_Flag() && null != udfData[0].getUdf24_Value() && null != actualUdfData[0].getUdf24_Value())
					udfData[0].setUdf24_Flag(actualUdfData[0].getUdf24_Flag());
				else if(null==actualUdfData[0].getUdf24_Flag() && null!=actualUdfData[0].getUdf24_Value() && null != udfData[0].getUdf24_Value() && !udfData[0].getUdf24_Value().equals(actualUdfData[0].getUdf24_Value()))
					udfData[0].setUdf24_Flag("M");
				else if(null==actualUdfData[0].getUdf24_Flag() && null==actualUdfData[0].getUdf24_Value() && null!=udfData[0].getUdf24_Value())
					udfData[0].setUdf24_Flag("A");
					else if(null!=actualUdfData[0].getUdf24_Flag())
					udfData[0].setUdf24_Flag(actualUdfData[0].getUdf24_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf25_Flag() && null != udfData[0].getUdf25_Value() && null != actualUdfData[0].getUdf25_Value())
					udfData[0].setUdf25_Flag(actualUdfData[0].getUdf25_Flag());
				else if(null==actualUdfData[0].getUdf25_Flag() && null!=actualUdfData[0].getUdf25_Value() && null != udfData[0].getUdf25_Value() && !udfData[0].getUdf25_Value().equals(actualUdfData[0].getUdf25_Value()))
					udfData[0].setUdf25_Flag("M");
				else if(null==actualUdfData[0].getUdf25_Flag() && null==actualUdfData[0].getUdf25_Value() && null!=udfData[0].getUdf25_Value())
					udfData[0].setUdf25_Flag("A");
					else if(null!=actualUdfData[0].getUdf25_Flag())
					udfData[0].setUdf25_Flag(actualUdfData[0].getUdf25_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf26_Flag() && null != udfData[0].getUdf26_Value() && null != actualUdfData[0].getUdf26_Value())
					udfData[0].setUdf26_Flag(actualUdfData[0].getUdf26_Flag());
				else if(null==actualUdfData[0].getUdf26_Flag() && null!=actualUdfData[0].getUdf26_Value() && null != udfData[0].getUdf26_Value() && !udfData[0].getUdf26_Value().equals(actualUdfData[0].getUdf26_Value()))
					udfData[0].setUdf26_Flag("M");
				else if(null==actualUdfData[0].getUdf26_Flag() && null==actualUdfData[0].getUdf26_Value() && null!=udfData[0].getUdf26_Value())
					udfData[0].setUdf26_Flag("A");
					else if(null!=actualUdfData[0].getUdf26_Flag())
					udfData[0].setUdf26_Flag(actualUdfData[0].getUdf26_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf27_Flag() && null != udfData[0].getUdf27_Value() && null != actualUdfData[0].getUdf27_Value() )
					udfData[0].setUdf27_Flag(actualUdfData[0].getUdf27_Flag());
				else if(null==actualUdfData[0].getUdf27_Flag() && null!=actualUdfData[0].getUdf27_Value() && null != udfData[0].getUdf27_Value() && !udfData[0].getUdf27_Value().equals(actualUdfData[0].getUdf27_Value()))
					udfData[0].setUdf27_Flag("M");
				else if(null==actualUdfData[0].getUdf27_Flag() && null==actualUdfData[0].getUdf27_Value() && null!=udfData[0].getUdf27_Value())
					udfData[0].setUdf27_Flag("A");
					else if(null!=actualUdfData[0].getUdf27_Flag())
					udfData[0].setUdf27_Flag(actualUdfData[0].getUdf27_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf28_Flag() && null != udfData[0].getUdf28_Value() && null != actualUdfData[0].getUdf28_Value() )
					udfData[0].setUdf28_Flag(actualUdfData[0].getUdf28_Flag());
				else if(null==actualUdfData[0].getUdf28_Flag() && null!=actualUdfData[0].getUdf28_Value() && null != udfData[0].getUdf28_Value() && !udfData[0].getUdf28_Value().equals(actualUdfData[0].getUdf28_Value()))
					udfData[0].setUdf28_Flag("M");
				else if(null==actualUdfData[0].getUdf28_Flag() && null==actualUdfData[0].getUdf28_Value() && null!=udfData[0].getUdf28_Value())
					udfData[0].setUdf28_Flag("A");
					else if(null!=actualUdfData[0].getUdf28_Flag())
					udfData[0].setUdf28_Flag(actualUdfData[0].getUdf28_Flag());
					
					
					if(null!=actualUdfData[0].getUdf29_Flag() && null != udfData[0].getUdf29_Value() && null != actualUdfData[0].getUdf29_Value())
					udfData[0].setUdf29_Flag(actualUdfData[0].getUdf29_Flag());
				else if(null==actualUdfData[0].getUdf29_Flag() && null!=actualUdfData[0].getUdf29_Value() && null != udfData[0].getUdf29_Value() && !udfData[0].getUdf29_Value().equals(actualUdfData[0].getUdf29_Value()))
					udfData[0].setUdf29_Flag("M");
				else if(null==actualUdfData[0].getUdf29_Flag() && null==actualUdfData[0].getUdf29_Value() && null!=udfData[0].getUdf29_Value())
					udfData[0].setUdf29_Flag("A");
					else if(null!=actualUdfData[0].getUdf29_Flag())
					udfData[0].setUdf29_Flag(actualUdfData[0].getUdf29_Flag());
					
				
					
					if(null!=actualUdfData[0].getUdf30_Flag() && null != udfData[0].getUdf30_Value() && null != actualUdfData[0].getUdf30_Value())
						udfData[0].setUdf30_Flag(actualUdfData[0].getUdf30_Flag());
					else if(null==actualUdfData[0].getUdf30_Flag() && null!=actualUdfData[0].getUdf30_Value() && null != udfData[0].getUdf30_Value() && !udfData[0].getUdf30_Value().equals(actualUdfData[0].getUdf30_Value()))
						udfData[0].setUdf30_Flag("M");
					else if(null==actualUdfData[0].getUdf30_Flag() && null==actualUdfData[0].getUdf30_Value() && null!=udfData[0].getUdf30_Value())
						udfData[0].setUdf30_Flag("A");
					else if(null!=actualUdfData[0].getUdf30_Flag())
						udfData[0].setUdf30_Flag(actualUdfData[0].getUdf30_Flag());




if(null!=actualUdfData[0].getUdf31_Flag() && null != udfData[0].getUdf31_Value() && null != actualUdfData[0].getUdf31_Value())
					udfData[0].setUdf31_Flag(actualUdfData[0].getUdf31_Flag());
				else if(null==actualUdfData[0].getUdf31_Flag() && null!=actualUdfData[0].getUdf31_Value() && null != udfData[0].getUdf31_Value() && !udfData[0].getUdf31_Value().equals(actualUdfData[0].getUdf31_Value()))
					udfData[0].setUdf31_Flag("M");
				else if(null==actualUdfData[0].getUdf31_Flag() && null==actualUdfData[0].getUdf31_Value() && null!=udfData[0].getUdf31_Value())
					udfData[0].setUdf31_Flag("A");
				else if(null!=actualUdfData[0].getUdf31_Flag())
					udfData[0].setUdf31_Flag(actualUdfData[0].getUdf31_Flag());
					
					if(null!=actualUdfData[0].getUdf32_Flag() && null != udfData[0].getUdf32_Value() && null != actualUdfData[0].getUdf32_Value())
					udfData[0].setUdf32_Flag(actualUdfData[0].getUdf32_Flag());
				else if(null==actualUdfData[0].getUdf32_Flag() && null!=actualUdfData[0].getUdf32_Value() && null != udfData[0].getUdf32_Value() && !udfData[0].getUdf32_Value().equals(actualUdfData[0].getUdf32_Value()))
					udfData[0].setUdf32_Flag("M");
				else if(null==actualUdfData[0].getUdf32_Flag() && null==actualUdfData[0].getUdf32_Value() && null!=udfData[0].getUdf32_Value())
					udfData[0].setUdf32_Flag("A");
					else if(null!=actualUdfData[0].getUdf32_Flag())
					udfData[0].setUdf32_Flag(actualUdfData[0].getUdf32_Flag());
					
					
					if(null!=actualUdfData[0].getUdf33_Flag() && null != udfData[0].getUdf33_Value() && null != actualUdfData[0].getUdf33_Value())
					udfData[0].setUdf33_Flag(actualUdfData[0].getUdf33_Flag());
				else if(null==actualUdfData[0].getUdf33_Flag() && null!=actualUdfData[0].getUdf33_Value() && null != udfData[0].getUdf33_Value() && !udfData[0].getUdf33_Value().equals(actualUdfData[0].getUdf33_Value()))
					udfData[0].setUdf33_Flag("M");
				else if(null==actualUdfData[0].getUdf33_Flag() && null==actualUdfData[0].getUdf33_Value() && null!=udfData[0].getUdf33_Value())
					udfData[0].setUdf33_Flag("A");
					else if(null!=actualUdfData[0].getUdf33_Flag())
					udfData[0].setUdf33_Flag(actualUdfData[0].getUdf33_Flag());
					
					
					if(null!=actualUdfData[0].getUdf34_Flag() && null != udfData[0].getUdf34_Value() && null != actualUdfData[0].getUdf34_Value())
					udfData[0].setUdf34_Flag(actualUdfData[0].getUdf34_Flag());
				else if(null==actualUdfData[0].getUdf34_Flag() && null!=actualUdfData[0].getUdf34_Value() && null != udfData[0].getUdf34_Value() && !udfData[0].getUdf34_Value().equals(actualUdfData[0].getUdf34_Value()))
					udfData[0].setUdf34_Flag("M");
				else if(null==actualUdfData[0].getUdf34_Flag() && null==actualUdfData[0].getUdf34_Value() && null!=udfData[0].getUdf34_Value())
					udfData[0].setUdf34_Flag("A");
					else if(null!=actualUdfData[0].getUdf34_Flag())
					udfData[0].setUdf34_Flag(actualUdfData[0].getUdf34_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf35_Flag() && null != udfData[0].getUdf35_Value() && null != actualUdfData[0].getUdf35_Value())
					udfData[0].setUdf35_Flag(actualUdfData[0].getUdf35_Flag());
				else if(null==actualUdfData[0].getUdf35_Flag() && null!=actualUdfData[0].getUdf35_Value() && null != udfData[0].getUdf35_Value() && !udfData[0].getUdf35_Value().equals(actualUdfData[0].getUdf35_Value()))
					udfData[0].setUdf35_Flag("M");
				else if(null==actualUdfData[0].getUdf35_Flag() && null==actualUdfData[0].getUdf35_Value() && null!=udfData[0].getUdf35_Value())
					udfData[0].setUdf35_Flag("A");
					else if(null!=actualUdfData[0].getUdf35_Flag())
					udfData[0].setUdf35_Flag(actualUdfData[0].getUdf35_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf36_Flag() && null != udfData[0].getUdf36_Value() && null != actualUdfData[0].getUdf36_Value())
					udfData[0].setUdf36_Flag(actualUdfData[0].getUdf36_Flag());
				else if(null==actualUdfData[0].getUdf36_Flag() && null!=actualUdfData[0].getUdf36_Value() && null != udfData[0].getUdf36_Value() && !udfData[0].getUdf36_Value().equals(actualUdfData[0].getUdf36_Value()))
					udfData[0].setUdf36_Flag("M");
				else if(null==actualUdfData[0].getUdf36_Flag() && null==actualUdfData[0].getUdf36_Value() && null!=udfData[0].getUdf36_Value())
					udfData[0].setUdf36_Flag("A");
					else if(null!=actualUdfData[0].getUdf36_Flag())
					udfData[0].setUdf36_Flag(actualUdfData[0].getUdf36_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf37_Flag() && null != udfData[0].getUdf37_Value() && null != actualUdfData[0].getUdf37_Value() )
					udfData[0].setUdf37_Flag(actualUdfData[0].getUdf37_Flag());
				else if(null==actualUdfData[0].getUdf37_Flag() && null!=actualUdfData[0].getUdf37_Value() && null != udfData[0].getUdf37_Value() && !udfData[0].getUdf37_Value().equals(actualUdfData[0].getUdf37_Value()))
					udfData[0].setUdf37_Flag("M");
				else if(null==actualUdfData[0].getUdf37_Flag() && null==actualUdfData[0].getUdf37_Value() && null!=udfData[0].getUdf37_Value())
					udfData[0].setUdf37_Flag("A");
					else if(null!=actualUdfData[0].getUdf37_Flag())
					udfData[0].setUdf37_Flag(actualUdfData[0].getUdf37_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf38_Flag() && null != udfData[0].getUdf38_Value() && null != actualUdfData[0].getUdf38_Value() )
					udfData[0].setUdf38_Flag(actualUdfData[0].getUdf38_Flag());
				else if(null==actualUdfData[0].getUdf38_Flag() && null!=actualUdfData[0].getUdf38_Value() && null != udfData[0].getUdf38_Value() && !udfData[0].getUdf38_Value().equals(actualUdfData[0].getUdf38_Value()))
					udfData[0].setUdf38_Flag("M");
				else if(null==actualUdfData[0].getUdf38_Flag() && null==actualUdfData[0].getUdf38_Value() && null!=udfData[0].getUdf38_Value())
					udfData[0].setUdf38_Flag("A");
					else if(null!=actualUdfData[0].getUdf38_Flag())
					udfData[0].setUdf38_Flag(actualUdfData[0].getUdf38_Flag());
					
					
					if(null!=actualUdfData[0].getUdf39_Flag() && null != udfData[0].getUdf39_Value() && null != actualUdfData[0].getUdf39_Value())
					udfData[0].setUdf39_Flag(actualUdfData[0].getUdf39_Flag());
				else if(null==actualUdfData[0].getUdf39_Flag() && null!=actualUdfData[0].getUdf39_Value() && null != udfData[0].getUdf39_Value() && !udfData[0].getUdf39_Value().equals(actualUdfData[0].getUdf39_Value()))
					udfData[0].setUdf39_Flag("M");
				else if(null==actualUdfData[0].getUdf39_Flag() && null==actualUdfData[0].getUdf39_Value() && null!=udfData[0].getUdf39_Value())
					udfData[0].setUdf39_Flag("A");
					else if(null!=actualUdfData[0].getUdf39_Flag())
					udfData[0].setUdf39_Flag(actualUdfData[0].getUdf39_Flag());
					
				
					if(null!=actualUdfData[0].getUdf40_Flag() && null != udfData[0].getUdf40_Value() && null != actualUdfData[0].getUdf40_Value())
						udfData[0].setUdf40_Flag(actualUdfData[0].getUdf40_Flag());
					else if(null==actualUdfData[0].getUdf40_Flag() && null!=actualUdfData[0].getUdf40_Value() && null != udfData[0].getUdf40_Value() && !udfData[0].getUdf40_Value().equals(actualUdfData[0].getUdf40_Value()))
						udfData[0].setUdf40_Flag("M");
					else if(null==actualUdfData[0].getUdf40_Flag() && null==actualUdfData[0].getUdf40_Value() && null!=udfData[0].getUdf40_Value())
						udfData[0].setUdf40_Flag("A");
					else if(null!=actualUdfData[0].getUdf40_Flag())
						udfData[0].setUdf40_Flag(actualUdfData[0].getUdf40_Flag());




if(null!=actualUdfData[0].getUdf41_Flag() && null != udfData[0].getUdf41_Value() && null != actualUdfData[0].getUdf41_Value())
					udfData[0].setUdf41_Flag(actualUdfData[0].getUdf41_Flag());
				else if(null==actualUdfData[0].getUdf41_Flag() && null!=actualUdfData[0].getUdf41_Value() && null != udfData[0].getUdf41_Value() && !udfData[0].getUdf41_Value().equals(actualUdfData[0].getUdf41_Value()))
					udfData[0].setUdf41_Flag("M");
				else if(null==actualUdfData[0].getUdf41_Flag() && null==actualUdfData[0].getUdf41_Value() && null!=udfData[0].getUdf41_Value())
					udfData[0].setUdf41_Flag("A");
				else if(null!=actualUdfData[0].getUdf41_Flag())
					udfData[0].setUdf41_Flag(actualUdfData[0].getUdf41_Flag());
					
					if(null!=actualUdfData[0].getUdf42_Flag() && null != udfData[0].getUdf42_Value() && null != actualUdfData[0].getUdf42_Value())
					udfData[0].setUdf42_Flag(actualUdfData[0].getUdf42_Flag());
				else if(null==actualUdfData[0].getUdf42_Flag() && null!=actualUdfData[0].getUdf42_Value() && null != udfData[0].getUdf42_Value() && !udfData[0].getUdf42_Value().equals(actualUdfData[0].getUdf42_Value()))
					udfData[0].setUdf42_Flag("M");
				else if(null==actualUdfData[0].getUdf42_Flag() && null==actualUdfData[0].getUdf42_Value() && null!=udfData[0].getUdf42_Value())
					udfData[0].setUdf42_Flag("A");
					else if(null!=actualUdfData[0].getUdf42_Flag())
					udfData[0].setUdf42_Flag(actualUdfData[0].getUdf42_Flag());
					
					
					if(null!=actualUdfData[0].getUdf43_Flag() && null != udfData[0].getUdf43_Value() && null != actualUdfData[0].getUdf43_Value())
					udfData[0].setUdf43_Flag(actualUdfData[0].getUdf43_Flag());
				else if(null==actualUdfData[0].getUdf43_Flag() && null!=actualUdfData[0].getUdf43_Value() && null != udfData[0].getUdf43_Value() && !udfData[0].getUdf43_Value().equals(actualUdfData[0].getUdf43_Value()))
					udfData[0].setUdf43_Flag("M");
				else if(null==actualUdfData[0].getUdf43_Flag() && null==actualUdfData[0].getUdf43_Value() && null!=udfData[0].getUdf43_Value())
					udfData[0].setUdf43_Flag("A");
					else if(null!=actualUdfData[0].getUdf43_Flag())
					udfData[0].setUdf43_Flag(actualUdfData[0].getUdf43_Flag());
					
					
					if(null!=actualUdfData[0].getUdf44_Flag() && null != udfData[0].getUdf44_Value() && null != actualUdfData[0].getUdf44_Value())
					udfData[0].setUdf44_Flag(actualUdfData[0].getUdf44_Flag());
				else if(null==actualUdfData[0].getUdf44_Flag() && null!=actualUdfData[0].getUdf44_Value() && null != udfData[0].getUdf44_Value() && !udfData[0].getUdf44_Value().equals(actualUdfData[0].getUdf44_Value()))
					udfData[0].setUdf44_Flag("M");
				else if(null==actualUdfData[0].getUdf44_Flag() && null==actualUdfData[0].getUdf44_Value() && null!=udfData[0].getUdf44_Value())
					udfData[0].setUdf44_Flag("A");
					else if(null!=actualUdfData[0].getUdf44_Flag())
					udfData[0].setUdf44_Flag(actualUdfData[0].getUdf44_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf45_Flag() && null != udfData[0].getUdf45_Value() && null != actualUdfData[0].getUdf45_Value())
					udfData[0].setUdf45_Flag(actualUdfData[0].getUdf45_Flag());
				else if(null==actualUdfData[0].getUdf45_Flag() && null!=actualUdfData[0].getUdf45_Value() && null != udfData[0].getUdf45_Value() && !udfData[0].getUdf45_Value().equals(actualUdfData[0].getUdf45_Value()))
					udfData[0].setUdf45_Flag("M");
				else if(null==actualUdfData[0].getUdf45_Flag() && null==actualUdfData[0].getUdf45_Value() && null!=udfData[0].getUdf45_Value())
					udfData[0].setUdf45_Flag("A");
					else if(null!=actualUdfData[0].getUdf45_Flag())
					udfData[0].setUdf45_Flag(actualUdfData[0].getUdf45_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf46_Flag() && null != udfData[0].getUdf46_Value() && null != actualUdfData[0].getUdf46_Value())
					udfData[0].setUdf46_Flag(actualUdfData[0].getUdf46_Flag());
				else if(null==actualUdfData[0].getUdf46_Flag() && null!=actualUdfData[0].getUdf46_Value() && null != udfData[0].getUdf46_Value() && !udfData[0].getUdf46_Value().equals(actualUdfData[0].getUdf46_Value()))
					udfData[0].setUdf46_Flag("M");
				else if(null==actualUdfData[0].getUdf46_Flag() && null==actualUdfData[0].getUdf46_Value() && null!=udfData[0].getUdf46_Value())
					udfData[0].setUdf46_Flag("A");
					else if(null!=actualUdfData[0].getUdf46_Flag())
					udfData[0].setUdf46_Flag(actualUdfData[0].getUdf46_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf47_Flag() && null != udfData[0].getUdf47_Value() && null != actualUdfData[0].getUdf47_Value() )
					udfData[0].setUdf47_Flag(actualUdfData[0].getUdf47_Flag());
				else if(null==actualUdfData[0].getUdf47_Flag() && null!=actualUdfData[0].getUdf47_Value() && null != udfData[0].getUdf47_Value() && !udfData[0].getUdf47_Value().equals(actualUdfData[0].getUdf47_Value()))
					udfData[0].setUdf47_Flag("M");
				else if(null==actualUdfData[0].getUdf47_Flag() && null==actualUdfData[0].getUdf47_Value() && null!=udfData[0].getUdf47_Value())
					udfData[0].setUdf47_Flag("A");
					else if(null!=actualUdfData[0].getUdf47_Flag())
					udfData[0].setUdf47_Flag(actualUdfData[0].getUdf47_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf48_Flag() && null != udfData[0].getUdf48_Value() && null != actualUdfData[0].getUdf48_Value() )
					udfData[0].setUdf48_Flag(actualUdfData[0].getUdf48_Flag());
				else if(null==actualUdfData[0].getUdf48_Flag() && null!=actualUdfData[0].getUdf48_Value() && null != udfData[0].getUdf48_Value() && !udfData[0].getUdf48_Value().equals(actualUdfData[0].getUdf48_Value()))
					udfData[0].setUdf48_Flag("M");
				else if(null==actualUdfData[0].getUdf48_Flag() && null==actualUdfData[0].getUdf48_Value() && null!=udfData[0].getUdf48_Value())
					udfData[0].setUdf48_Flag("A");
					else if(null!=actualUdfData[0].getUdf48_Flag())
					udfData[0].setUdf48_Flag(actualUdfData[0].getUdf48_Flag());
					
					
					if(null!=actualUdfData[0].getUdf49_Flag() && null != udfData[0].getUdf49_Value() && null != actualUdfData[0].getUdf49_Value())
					udfData[0].setUdf49_Flag(actualUdfData[0].getUdf49_Flag());
				else if(null==actualUdfData[0].getUdf49_Flag() && null!=actualUdfData[0].getUdf49_Value() && null != udfData[0].getUdf49_Value() && !udfData[0].getUdf49_Value().equals(actualUdfData[0].getUdf49_Value()))
					udfData[0].setUdf49_Flag("M");
				else if(null==actualUdfData[0].getUdf49_Flag() && null==actualUdfData[0].getUdf49_Value() && null!=udfData[0].getUdf49_Value())
					udfData[0].setUdf49_Flag("A");
					else if(null!=actualUdfData[0].getUdf49_Flag())
					udfData[0].setUdf49_Flag(actualUdfData[0].getUdf49_Flag());
					
				
					if(null!=actualUdfData[0].getUdf50_Flag() && null != udfData[0].getUdf50_Value() && null != actualUdfData[0].getUdf50_Value())
						udfData[0].setUdf50_Flag(actualUdfData[0].getUdf50_Flag());
					else if(null==actualUdfData[0].getUdf50_Flag() && null!=actualUdfData[0].getUdf50_Value() && null != udfData[0].getUdf50_Value() && !udfData[0].getUdf50_Value().equals(actualUdfData[0].getUdf50_Value()))
						udfData[0].setUdf50_Flag("M");
					else if(null==actualUdfData[0].getUdf50_Flag() && null==actualUdfData[0].getUdf50_Value() && null!=udfData[0].getUdf50_Value())
						udfData[0].setUdf50_Flag("A");
					else if(null!=actualUdfData[0].getUdf50_Flag())
						udfData[0].setUdf50_Flag(actualUdfData[0].getUdf50_Flag());




if(null!=actualUdfData[0].getUdf51_Flag() && null != udfData[0].getUdf51_Value() && null != actualUdfData[0].getUdf51_Value())
					udfData[0].setUdf51_Flag(actualUdfData[0].getUdf51_Flag());
				else if(null==actualUdfData[0].getUdf51_Flag() && null!=actualUdfData[0].getUdf51_Value() && null != udfData[0].getUdf51_Value() && !udfData[0].getUdf51_Value().equals(actualUdfData[0].getUdf51_Value()))
					udfData[0].setUdf51_Flag("M");
				else if(null==actualUdfData[0].getUdf51_Flag() && null==actualUdfData[0].getUdf51_Value() && null!=udfData[0].getUdf51_Value())
					udfData[0].setUdf51_Flag("A");
				else if(null!=actualUdfData[0].getUdf51_Flag())
					udfData[0].setUdf51_Flag(actualUdfData[0].getUdf51_Flag());
					
					if(null!=actualUdfData[0].getUdf52_Flag() && null != udfData[0].getUdf52_Value() && null != actualUdfData[0].getUdf52_Value())
					udfData[0].setUdf52_Flag(actualUdfData[0].getUdf52_Flag());
				else if(null==actualUdfData[0].getUdf52_Flag() && null!=actualUdfData[0].getUdf52_Value() && null != udfData[0].getUdf52_Value() && !udfData[0].getUdf52_Value().equals(actualUdfData[0].getUdf52_Value()))
					udfData[0].setUdf52_Flag("M");
				else if(null==actualUdfData[0].getUdf52_Flag() && null==actualUdfData[0].getUdf52_Value() && null!=udfData[0].getUdf52_Value())
					udfData[0].setUdf52_Flag("A");
					else if(null!=actualUdfData[0].getUdf52_Flag())
					udfData[0].setUdf52_Flag(actualUdfData[0].getUdf52_Flag());
					
					
					if(null!=actualUdfData[0].getUdf53_Flag() && null != udfData[0].getUdf53_Value() && null != actualUdfData[0].getUdf53_Value())
					udfData[0].setUdf53_Flag(actualUdfData[0].getUdf53_Flag());
				else if(null==actualUdfData[0].getUdf53_Flag() && null!=actualUdfData[0].getUdf53_Value() && null != udfData[0].getUdf53_Value() && !udfData[0].getUdf53_Value().equals(actualUdfData[0].getUdf53_Value()))
					udfData[0].setUdf53_Flag("M");
				else if(null==actualUdfData[0].getUdf53_Flag() && null==actualUdfData[0].getUdf53_Value() && null!=udfData[0].getUdf53_Value())
					udfData[0].setUdf53_Flag("A");
					else if(null!=actualUdfData[0].getUdf53_Flag())
					udfData[0].setUdf53_Flag(actualUdfData[0].getUdf53_Flag());
					
					
					if(null!=actualUdfData[0].getUdf54_Flag() && null != udfData[0].getUdf54_Value() && null != actualUdfData[0].getUdf54_Value())
					udfData[0].setUdf54_Flag(actualUdfData[0].getUdf54_Flag());
				else if(null==actualUdfData[0].getUdf54_Flag() && null!=actualUdfData[0].getUdf54_Value() && null != udfData[0].getUdf54_Value() && !udfData[0].getUdf54_Value().equals(actualUdfData[0].getUdf54_Value()))
					udfData[0].setUdf54_Flag("M");
				else if(null==actualUdfData[0].getUdf54_Flag() && null==actualUdfData[0].getUdf54_Value() && null!=udfData[0].getUdf54_Value())
					udfData[0].setUdf54_Flag("A");
					else if(null!=actualUdfData[0].getUdf54_Flag())
					udfData[0].setUdf54_Flag(actualUdfData[0].getUdf54_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf55_Flag() && null != udfData[0].getUdf55_Value() && null != actualUdfData[0].getUdf55_Value())
					udfData[0].setUdf55_Flag(actualUdfData[0].getUdf55_Flag());
				else if(null==actualUdfData[0].getUdf55_Flag() && null!=actualUdfData[0].getUdf55_Value() && null != udfData[0].getUdf55_Value() && !udfData[0].getUdf55_Value().equals(actualUdfData[0].getUdf55_Value()))
					udfData[0].setUdf55_Flag("M");
				else if(null==actualUdfData[0].getUdf55_Flag() && null==actualUdfData[0].getUdf55_Value() && null!=udfData[0].getUdf55_Value())
					udfData[0].setUdf55_Flag("A");
					else if(null!=actualUdfData[0].getUdf55_Flag())
					udfData[0].setUdf55_Flag(actualUdfData[0].getUdf55_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf56_Flag() && null != udfData[0].getUdf56_Value() && null != actualUdfData[0].getUdf56_Value())
					udfData[0].setUdf56_Flag(actualUdfData[0].getUdf56_Flag());
				else if(null==actualUdfData[0].getUdf56_Flag() && null!=actualUdfData[0].getUdf56_Value() && null != udfData[0].getUdf56_Value() && !udfData[0].getUdf56_Value().equals(actualUdfData[0].getUdf56_Value()))
					udfData[0].setUdf56_Flag("M");
				else if(null==actualUdfData[0].getUdf56_Flag() && null==actualUdfData[0].getUdf56_Value() && null!=udfData[0].getUdf56_Value())
					udfData[0].setUdf56_Flag("A");
					else if(null!=actualUdfData[0].getUdf56_Flag())
					udfData[0].setUdf56_Flag(actualUdfData[0].getUdf56_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf57_Flag() && null != udfData[0].getUdf57_Value() && null != actualUdfData[0].getUdf57_Value() )
					udfData[0].setUdf57_Flag(actualUdfData[0].getUdf57_Flag());
				else if(null==actualUdfData[0].getUdf57_Flag() && null!=actualUdfData[0].getUdf57_Value() && null != udfData[0].getUdf57_Value() && !udfData[0].getUdf57_Value().equals(actualUdfData[0].getUdf57_Value()))
					udfData[0].setUdf57_Flag("M");
				else if(null==actualUdfData[0].getUdf57_Flag() && null==actualUdfData[0].getUdf57_Value() && null!=udfData[0].getUdf57_Value())
					udfData[0].setUdf57_Flag("A");
					else if(null!=actualUdfData[0].getUdf57_Flag())
					udfData[0].setUdf57_Flag(actualUdfData[0].getUdf57_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf58_Flag() && null != udfData[0].getUdf58_Value() && null != actualUdfData[0].getUdf58_Value() )
					udfData[0].setUdf58_Flag(actualUdfData[0].getUdf58_Flag());
				else if(null==actualUdfData[0].getUdf58_Flag() && null!=actualUdfData[0].getUdf58_Value() && null != udfData[0].getUdf58_Value() && !udfData[0].getUdf58_Value().equals(actualUdfData[0].getUdf58_Value()))
					udfData[0].setUdf58_Flag("M");
				else if(null==actualUdfData[0].getUdf58_Flag() && null==actualUdfData[0].getUdf58_Value() && null!=udfData[0].getUdf58_Value())
					udfData[0].setUdf58_Flag("A");
					else if(null!=actualUdfData[0].getUdf58_Flag())
					udfData[0].setUdf58_Flag(actualUdfData[0].getUdf58_Flag());
					
					
					if(null!=actualUdfData[0].getUdf59_Flag() && null != udfData[0].getUdf59_Value() && null != actualUdfData[0].getUdf59_Value())
					udfData[0].setUdf59_Flag(actualUdfData[0].getUdf59_Flag());
				else if(null==actualUdfData[0].getUdf59_Flag() && null!=actualUdfData[0].getUdf59_Value() && null != udfData[0].getUdf59_Value() && !udfData[0].getUdf59_Value().equals(actualUdfData[0].getUdf59_Value()))
					udfData[0].setUdf59_Flag("M");
				else if(null==actualUdfData[0].getUdf59_Flag() && null==actualUdfData[0].getUdf59_Value() && null!=udfData[0].getUdf59_Value())
					udfData[0].setUdf59_Flag("A");
					else if(null!=actualUdfData[0].getUdf59_Flag())
					udfData[0].setUdf59_Flag(actualUdfData[0].getUdf59_Flag());
					
				
					if(null!=actualUdfData[0].getUdf60_Flag() && null != udfData[0].getUdf60_Value() && null != actualUdfData[0].getUdf60_Value())
						udfData[0].setUdf60_Flag(actualUdfData[0].getUdf60_Flag());
					else if(null==actualUdfData[0].getUdf60_Flag() && null!=actualUdfData[0].getUdf60_Value() && null != udfData[0].getUdf60_Value() && !udfData[0].getUdf60_Value().equals(actualUdfData[0].getUdf60_Value()))
						udfData[0].setUdf60_Flag("M");
					else if(null==actualUdfData[0].getUdf60_Flag() && null==actualUdfData[0].getUdf60_Value() && null!=udfData[0].getUdf60_Value())
						udfData[0].setUdf60_Flag("A");
					else if(null!=actualUdfData[0].getUdf60_Flag())
						udfData[0].setUdf60_Flag(actualUdfData[0].getUdf60_Flag());




if(null!=actualUdfData[0].getUdf61_Flag() && null != udfData[0].getUdf61_Value() && null != actualUdfData[0].getUdf61_Value())
					udfData[0].setUdf61_Flag(actualUdfData[0].getUdf61_Flag());
				else if(null==actualUdfData[0].getUdf61_Flag() && null!=actualUdfData[0].getUdf61_Value() && null != udfData[0].getUdf61_Value() && !udfData[0].getUdf61_Value().equals(actualUdfData[0].getUdf61_Value()))
					udfData[0].setUdf61_Flag("M");
				else if(null==actualUdfData[0].getUdf61_Flag() && null==actualUdfData[0].getUdf61_Value() && null!=udfData[0].getUdf61_Value())
					udfData[0].setUdf61_Flag("A");
				else if(null!=actualUdfData[0].getUdf61_Flag())
					udfData[0].setUdf61_Flag(actualUdfData[0].getUdf61_Flag());
					
					if(null!=actualUdfData[0].getUdf62_Flag() && null != udfData[0].getUdf62_Value() && null != actualUdfData[0].getUdf62_Value())
					udfData[0].setUdf62_Flag(actualUdfData[0].getUdf62_Flag());
				else if(null==actualUdfData[0].getUdf62_Flag() && null!=actualUdfData[0].getUdf62_Value() && null != udfData[0].getUdf62_Value() && !udfData[0].getUdf62_Value().equals(actualUdfData[0].getUdf62_Value()))
					udfData[0].setUdf62_Flag("M");
				else if(null==actualUdfData[0].getUdf62_Flag() && null==actualUdfData[0].getUdf62_Value() && null!=udfData[0].getUdf62_Value())
					udfData[0].setUdf62_Flag("A");
					else if(null!=actualUdfData[0].getUdf62_Flag())
					udfData[0].setUdf62_Flag(actualUdfData[0].getUdf62_Flag());
					
					
					if(null!=actualUdfData[0].getUdf63_Flag() && null != udfData[0].getUdf63_Value() && null != actualUdfData[0].getUdf63_Value())
					udfData[0].setUdf63_Flag(actualUdfData[0].getUdf63_Flag());
				else if(null==actualUdfData[0].getUdf63_Flag() && null!=actualUdfData[0].getUdf63_Value() && null != udfData[0].getUdf63_Value() && !udfData[0].getUdf63_Value().equals(actualUdfData[0].getUdf63_Value()))
					udfData[0].setUdf63_Flag("M");
				else if(null==actualUdfData[0].getUdf63_Flag() && null==actualUdfData[0].getUdf63_Value() && null!=udfData[0].getUdf63_Value())
					udfData[0].setUdf63_Flag("A");
					else if(null!=actualUdfData[0].getUdf63_Flag())
					udfData[0].setUdf63_Flag(actualUdfData[0].getUdf63_Flag());
					
					
					if(null!=actualUdfData[0].getUdf64_Flag() && null != udfData[0].getUdf64_Value() && null != actualUdfData[0].getUdf64_Value())
					udfData[0].setUdf64_Flag(actualUdfData[0].getUdf64_Flag());
				else if(null==actualUdfData[0].getUdf64_Flag() && null!=actualUdfData[0].getUdf64_Value() && null != udfData[0].getUdf64_Value() && !udfData[0].getUdf64_Value().equals(actualUdfData[0].getUdf64_Value()))
					udfData[0].setUdf64_Flag("M");
				else if(null==actualUdfData[0].getUdf64_Flag() && null==actualUdfData[0].getUdf64_Value() && null!=udfData[0].getUdf64_Value())
					udfData[0].setUdf64_Flag("A");
					else if(null!=actualUdfData[0].getUdf64_Flag())
					udfData[0].setUdf64_Flag(actualUdfData[0].getUdf64_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf65_Flag() && null != udfData[0].getUdf65_Value() && null != actualUdfData[0].getUdf65_Value())
					udfData[0].setUdf65_Flag(actualUdfData[0].getUdf65_Flag());
				else if(null==actualUdfData[0].getUdf65_Flag() && null!=actualUdfData[0].getUdf65_Value() && null != udfData[0].getUdf65_Value() && !udfData[0].getUdf65_Value().equals(actualUdfData[0].getUdf65_Value()))
					udfData[0].setUdf65_Flag("M");
				else if(null==actualUdfData[0].getUdf65_Flag() && null==actualUdfData[0].getUdf65_Value() && null!=udfData[0].getUdf65_Value())
					udfData[0].setUdf65_Flag("A");
					else if(null!=actualUdfData[0].getUdf65_Flag())
					udfData[0].setUdf65_Flag(actualUdfData[0].getUdf65_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf66_Flag() && null != udfData[0].getUdf66_Value() && null != actualUdfData[0].getUdf66_Value())
					udfData[0].setUdf66_Flag(actualUdfData[0].getUdf66_Flag());
				else if(null==actualUdfData[0].getUdf66_Flag() && null!=actualUdfData[0].getUdf66_Value() && null != udfData[0].getUdf66_Value() && !udfData[0].getUdf66_Value().equals(actualUdfData[0].getUdf66_Value()))
					udfData[0].setUdf66_Flag("M");
				else if(null==actualUdfData[0].getUdf66_Flag() && null==actualUdfData[0].getUdf66_Value() && null!=udfData[0].getUdf66_Value())
					udfData[0].setUdf66_Flag("A");
					else if(null!=actualUdfData[0].getUdf66_Flag())
					udfData[0].setUdf66_Flag(actualUdfData[0].getUdf66_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf67_Flag() && null != udfData[0].getUdf67_Value() && null != actualUdfData[0].getUdf67_Value() )
					udfData[0].setUdf67_Flag(actualUdfData[0].getUdf67_Flag());
				else if(null==actualUdfData[0].getUdf67_Flag() && null!=actualUdfData[0].getUdf67_Value() && null != udfData[0].getUdf67_Value() && !udfData[0].getUdf67_Value().equals(actualUdfData[0].getUdf67_Value()))
					udfData[0].setUdf67_Flag("M");
				else if(null==actualUdfData[0].getUdf67_Flag() && null==actualUdfData[0].getUdf67_Value() && null!=udfData[0].getUdf67_Value())
					udfData[0].setUdf67_Flag("A");
					else if(null!=actualUdfData[0].getUdf67_Flag())
					udfData[0].setUdf67_Flag(actualUdfData[0].getUdf67_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf68_Flag() && null != udfData[0].getUdf68_Value() && null != actualUdfData[0].getUdf68_Value() )
					udfData[0].setUdf68_Flag(actualUdfData[0].getUdf68_Flag());
				else if(null==actualUdfData[0].getUdf68_Flag() && null!=actualUdfData[0].getUdf68_Value() && null != udfData[0].getUdf68_Value() && !udfData[0].getUdf68_Value().equals(actualUdfData[0].getUdf68_Value()))
					udfData[0].setUdf68_Flag("M");
				else if(null==actualUdfData[0].getUdf68_Flag() && null==actualUdfData[0].getUdf68_Value() && null!=udfData[0].getUdf68_Value())
					udfData[0].setUdf68_Flag("A");
					else if(null!=actualUdfData[0].getUdf68_Flag())
					udfData[0].setUdf68_Flag(actualUdfData[0].getUdf68_Flag());
					
					
					if(null!=actualUdfData[0].getUdf69_Flag() && null != udfData[0].getUdf69_Value() && null != actualUdfData[0].getUdf69_Value())
					udfData[0].setUdf69_Flag(actualUdfData[0].getUdf69_Flag());
				else if(null==actualUdfData[0].getUdf69_Flag() && null!=actualUdfData[0].getUdf69_Value() && null != udfData[0].getUdf69_Value() && !udfData[0].getUdf69_Value().equals(actualUdfData[0].getUdf69_Value()))
					udfData[0].setUdf69_Flag("M");
				else if(null==actualUdfData[0].getUdf69_Flag() && null==actualUdfData[0].getUdf69_Value() && null!=udfData[0].getUdf69_Value())
					udfData[0].setUdf69_Flag("A");
					else if(null!=actualUdfData[0].getUdf69_Flag())
					udfData[0].setUdf69_Flag(actualUdfData[0].getUdf69_Flag());
					
					if(null!=actualUdfData[0].getUdf70_Flag() && null != udfData[0].getUdf70_Value() && null != actualUdfData[0].getUdf70_Value())
						udfData[0].setUdf70_Flag(actualUdfData[0].getUdf70_Flag());
					else if(null==actualUdfData[0].getUdf70_Flag() && null!=actualUdfData[0].getUdf70_Value() && null != udfData[0].getUdf70_Value() && !udfData[0].getUdf70_Value().equals(actualUdfData[0].getUdf70_Value()))
						udfData[0].setUdf70_Flag("M");
					else if(null==actualUdfData[0].getUdf70_Flag() && null==actualUdfData[0].getUdf70_Value() && null!=udfData[0].getUdf70_Value())
						udfData[0].setUdf70_Flag("A");
					else if(null!=actualUdfData[0].getUdf70_Flag())
						udfData[0].setUdf70_Flag(actualUdfData[0].getUdf70_Flag());




if(null!=actualUdfData[0].getUdf71_Flag() && null != udfData[0].getUdf71_Value() && null != actualUdfData[0].getUdf71_Value())
					udfData[0].setUdf71_Flag(actualUdfData[0].getUdf71_Flag());
				else if(null==actualUdfData[0].getUdf71_Flag() && null!=actualUdfData[0].getUdf71_Value() && null != udfData[0].getUdf71_Value() && !udfData[0].getUdf71_Value().equals(actualUdfData[0].getUdf71_Value()))
					udfData[0].setUdf71_Flag("M");
				else if(null==actualUdfData[0].getUdf71_Flag() && null==actualUdfData[0].getUdf71_Value() && null!=udfData[0].getUdf71_Value())
					udfData[0].setUdf71_Flag("A");
				else if(null!=actualUdfData[0].getUdf71_Flag())
					udfData[0].setUdf71_Flag(actualUdfData[0].getUdf71_Flag());
					
					if(null!=actualUdfData[0].getUdf72_Flag() && null != udfData[0].getUdf72_Value() && null != actualUdfData[0].getUdf72_Value())
					udfData[0].setUdf72_Flag(actualUdfData[0].getUdf72_Flag());
				else if(null==actualUdfData[0].getUdf72_Flag() && null!=actualUdfData[0].getUdf72_Value() && null != udfData[0].getUdf72_Value() && !udfData[0].getUdf72_Value().equals(actualUdfData[0].getUdf72_Value()))
					udfData[0].setUdf72_Flag("M");
				else if(null==actualUdfData[0].getUdf72_Flag() && null==actualUdfData[0].getUdf72_Value() && null!=udfData[0].getUdf72_Value())
					udfData[0].setUdf72_Flag("A");
					else if(null!=actualUdfData[0].getUdf72_Flag())
					udfData[0].setUdf72_Flag(actualUdfData[0].getUdf72_Flag());
					
					
					if(null!=actualUdfData[0].getUdf73_Flag() && null != udfData[0].getUdf73_Value() && null != actualUdfData[0].getUdf73_Value())
					udfData[0].setUdf73_Flag(actualUdfData[0].getUdf73_Flag());
				else if(null==actualUdfData[0].getUdf73_Flag() && null!=actualUdfData[0].getUdf73_Value() && null != udfData[0].getUdf73_Value() && !udfData[0].getUdf73_Value().equals(actualUdfData[0].getUdf73_Value()))
					udfData[0].setUdf73_Flag("M");
				else if(null==actualUdfData[0].getUdf73_Flag() && null==actualUdfData[0].getUdf73_Value() && null!=udfData[0].getUdf73_Value())
					udfData[0].setUdf73_Flag("A");
					else if(null!=actualUdfData[0].getUdf73_Flag())
					udfData[0].setUdf73_Flag(actualUdfData[0].getUdf73_Flag());
					
					
					if(null!=actualUdfData[0].getUdf74_Flag() && null != udfData[0].getUdf74_Value() && null != actualUdfData[0].getUdf74_Value())
					udfData[0].setUdf74_Flag(actualUdfData[0].getUdf74_Flag());
				else if(null==actualUdfData[0].getUdf74_Flag() && null!=actualUdfData[0].getUdf74_Value() && null != udfData[0].getUdf74_Value() && !udfData[0].getUdf74_Value().equals(actualUdfData[0].getUdf74_Value()))
					udfData[0].setUdf74_Flag("M");
				else if(null==actualUdfData[0].getUdf74_Flag() && null==actualUdfData[0].getUdf74_Value() && null!=udfData[0].getUdf74_Value())
					udfData[0].setUdf74_Flag("A");
					else if(null!=actualUdfData[0].getUdf74_Flag())
					udfData[0].setUdf74_Flag(actualUdfData[0].getUdf74_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf75_Flag() && null != udfData[0].getUdf75_Value() && null != actualUdfData[0].getUdf75_Value())
					udfData[0].setUdf75_Flag(actualUdfData[0].getUdf75_Flag());
				else if(null==actualUdfData[0].getUdf75_Flag() && null!=actualUdfData[0].getUdf75_Value() && null != udfData[0].getUdf75_Value() && !udfData[0].getUdf75_Value().equals(actualUdfData[0].getUdf75_Value()))
					udfData[0].setUdf75_Flag("M");
				else if(null==actualUdfData[0].getUdf75_Flag() && null==actualUdfData[0].getUdf75_Value() && null!=udfData[0].getUdf75_Value())
					udfData[0].setUdf75_Flag("A");
					else if(null!=actualUdfData[0].getUdf75_Flag())
					udfData[0].setUdf75_Flag(actualUdfData[0].getUdf75_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf76_Flag() && null != udfData[0].getUdf76_Value() && null != actualUdfData[0].getUdf76_Value())
					udfData[0].setUdf76_Flag(actualUdfData[0].getUdf76_Flag());
				else if(null==actualUdfData[0].getUdf76_Flag() && null!=actualUdfData[0].getUdf76_Value() && null != udfData[0].getUdf76_Value() && !udfData[0].getUdf76_Value().equals(actualUdfData[0].getUdf76_Value()))
					udfData[0].setUdf76_Flag("M");
				else if(null==actualUdfData[0].getUdf76_Flag() && null==actualUdfData[0].getUdf76_Value() && null!=udfData[0].getUdf76_Value())
					udfData[0].setUdf76_Flag("A");
					else if(null!=actualUdfData[0].getUdf76_Flag())
					udfData[0].setUdf76_Flag(actualUdfData[0].getUdf76_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf77_Flag() && null != udfData[0].getUdf77_Value() && null != actualUdfData[0].getUdf77_Value() )
					udfData[0].setUdf77_Flag(actualUdfData[0].getUdf77_Flag());
				else if(null==actualUdfData[0].getUdf77_Flag() && null!=actualUdfData[0].getUdf77_Value() && null != udfData[0].getUdf77_Value() && !udfData[0].getUdf77_Value().equals(actualUdfData[0].getUdf77_Value()))
					udfData[0].setUdf77_Flag("M");
				else if(null==actualUdfData[0].getUdf77_Flag() && null==actualUdfData[0].getUdf77_Value() && null!=udfData[0].getUdf77_Value())
					udfData[0].setUdf77_Flag("A");
					else if(null!=actualUdfData[0].getUdf77_Flag())
					udfData[0].setUdf77_Flag(actualUdfData[0].getUdf77_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf78_Flag() && null != udfData[0].getUdf78_Value() && null != actualUdfData[0].getUdf78_Value() )
					udfData[0].setUdf78_Flag(actualUdfData[0].getUdf78_Flag());
				else if(null==actualUdfData[0].getUdf78_Flag() && null!=actualUdfData[0].getUdf78_Value() && null != udfData[0].getUdf78_Value() && !udfData[0].getUdf78_Value().equals(actualUdfData[0].getUdf78_Value()))
					udfData[0].setUdf78_Flag("M");
				else if(null==actualUdfData[0].getUdf78_Flag() && null==actualUdfData[0].getUdf78_Value() && null!=udfData[0].getUdf78_Value())
					udfData[0].setUdf78_Flag("A");
					else if(null!=actualUdfData[0].getUdf78_Flag())
					udfData[0].setUdf78_Flag(actualUdfData[0].getUdf78_Flag());
					
					
					if(null!=actualUdfData[0].getUdf79_Flag() && null != udfData[0].getUdf79_Value() && null != actualUdfData[0].getUdf79_Value())
					udfData[0].setUdf79_Flag(actualUdfData[0].getUdf79_Flag());
				else if(null==actualUdfData[0].getUdf79_Flag() && null!=actualUdfData[0].getUdf79_Value() && null != udfData[0].getUdf79_Value() && !udfData[0].getUdf79_Value().equals(actualUdfData[0].getUdf79_Value()))
					udfData[0].setUdf79_Flag("M");
				else if(null==actualUdfData[0].getUdf79_Flag() && null==actualUdfData[0].getUdf79_Value() && null!=udfData[0].getUdf79_Value())
					udfData[0].setUdf79_Flag("A");
					else if(null!=actualUdfData[0].getUdf79_Flag())
					udfData[0].setUdf79_Flag(actualUdfData[0].getUdf79_Flag());
					
				
					if(null!=actualUdfData[0].getUdf80_Flag() && null != udfData[0].getUdf80_Value() && null != actualUdfData[0].getUdf80_Value())
						udfData[0].setUdf80_Flag(actualUdfData[0].getUdf80_Flag());
					else if(null==actualUdfData[0].getUdf80_Flag() && null!=actualUdfData[0].getUdf80_Value() && null != udfData[0].getUdf80_Value() && !udfData[0].getUdf80_Value().equals(actualUdfData[0].getUdf80_Value()))
						udfData[0].setUdf80_Flag("M");
					else if(null==actualUdfData[0].getUdf80_Flag() && null==actualUdfData[0].getUdf80_Value() && null!=udfData[0].getUdf80_Value())
						udfData[0].setUdf80_Flag("A");
					else if(null!=actualUdfData[0].getUdf80_Flag())
						udfData[0].setUdf80_Flag(actualUdfData[0].getUdf80_Flag());




if(null!=actualUdfData[0].getUdf81_Flag() && null != udfData[0].getUdf81_Value() && null != actualUdfData[0].getUdf81_Value())
					udfData[0].setUdf81_Flag(actualUdfData[0].getUdf81_Flag());
				else if(null==actualUdfData[0].getUdf81_Flag() && null!=actualUdfData[0].getUdf81_Value() && null != udfData[0].getUdf81_Value() && !udfData[0].getUdf81_Value().equals(actualUdfData[0].getUdf81_Value()))
					udfData[0].setUdf81_Flag("M");
				else if(null==actualUdfData[0].getUdf81_Flag() && null==actualUdfData[0].getUdf81_Value() && null!=udfData[0].getUdf81_Value())
					udfData[0].setUdf81_Flag("A");
				else if(null!=actualUdfData[0].getUdf81_Flag())
					udfData[0].setUdf81_Flag(actualUdfData[0].getUdf81_Flag());
					
					if(null!=actualUdfData[0].getUdf82_Flag() && null != udfData[0].getUdf82_Value() && null != actualUdfData[0].getUdf82_Value())
					udfData[0].setUdf82_Flag(actualUdfData[0].getUdf82_Flag());
				else if(null==actualUdfData[0].getUdf82_Flag() && null!=actualUdfData[0].getUdf82_Value() && null != udfData[0].getUdf82_Value() && !udfData[0].getUdf82_Value().equals(actualUdfData[0].getUdf82_Value()))
					udfData[0].setUdf82_Flag("M");
				else if(null==actualUdfData[0].getUdf82_Flag() && null==actualUdfData[0].getUdf82_Value() && null!=udfData[0].getUdf82_Value())
					udfData[0].setUdf82_Flag("A");
					else if(null!=actualUdfData[0].getUdf82_Flag())
					udfData[0].setUdf82_Flag(actualUdfData[0].getUdf82_Flag());
					
					
					if(null!=actualUdfData[0].getUdf83_Flag() && null != udfData[0].getUdf83_Value() && null != actualUdfData[0].getUdf83_Value())
					udfData[0].setUdf83_Flag(actualUdfData[0].getUdf83_Flag());
				else if(null==actualUdfData[0].getUdf83_Flag() && null!=actualUdfData[0].getUdf83_Value() && null != udfData[0].getUdf83_Value() && !udfData[0].getUdf83_Value().equals(actualUdfData[0].getUdf83_Value()))
					udfData[0].setUdf83_Flag("M");
				else if(null==actualUdfData[0].getUdf83_Flag() && null==actualUdfData[0].getUdf83_Value() && null!=udfData[0].getUdf83_Value())
					udfData[0].setUdf83_Flag("A");
					else if(null!=actualUdfData[0].getUdf83_Flag())
					udfData[0].setUdf83_Flag(actualUdfData[0].getUdf83_Flag());
					
					
					if(null!=actualUdfData[0].getUdf84_Flag() && null != udfData[0].getUdf84_Value() && null != actualUdfData[0].getUdf84_Value())
					udfData[0].setUdf84_Flag(actualUdfData[0].getUdf84_Flag());
				else if(null==actualUdfData[0].getUdf84_Flag() && null!=actualUdfData[0].getUdf84_Value() && null != udfData[0].getUdf84_Value() && !udfData[0].getUdf84_Value().equals(actualUdfData[0].getUdf84_Value()))
					udfData[0].setUdf84_Flag("M");
				else if(null==actualUdfData[0].getUdf84_Flag() && null==actualUdfData[0].getUdf84_Value() && null!=udfData[0].getUdf84_Value())
					udfData[0].setUdf84_Flag("A");
					else if(null!=actualUdfData[0].getUdf84_Flag())
					udfData[0].setUdf84_Flag(actualUdfData[0].getUdf84_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf85_Flag() && null != udfData[0].getUdf85_Value() && null != actualUdfData[0].getUdf85_Value())
					udfData[0].setUdf85_Flag(actualUdfData[0].getUdf85_Flag());
				else if(null==actualUdfData[0].getUdf85_Flag() && null!=actualUdfData[0].getUdf85_Value() && null != udfData[0].getUdf85_Value() && !udfData[0].getUdf85_Value().equals(actualUdfData[0].getUdf85_Value()))
					udfData[0].setUdf85_Flag("M");
				else if(null==actualUdfData[0].getUdf85_Flag() && null==actualUdfData[0].getUdf85_Value() && null!=udfData[0].getUdf85_Value())
					udfData[0].setUdf85_Flag("A");
					else if(null!=actualUdfData[0].getUdf85_Flag())
					udfData[0].setUdf85_Flag(actualUdfData[0].getUdf85_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf86_Flag() && null != udfData[0].getUdf86_Value() && null != actualUdfData[0].getUdf86_Value())
					udfData[0].setUdf86_Flag(actualUdfData[0].getUdf86_Flag());
				else if(null==actualUdfData[0].getUdf86_Flag() && null!=actualUdfData[0].getUdf86_Value() && null != udfData[0].getUdf86_Value() && !udfData[0].getUdf86_Value().equals(actualUdfData[0].getUdf86_Value()))
					udfData[0].setUdf86_Flag("M");
				else if(null==actualUdfData[0].getUdf86_Flag() && null==actualUdfData[0].getUdf86_Value() && null!=udfData[0].getUdf86_Value())
					udfData[0].setUdf86_Flag("A");
					else if(null!=actualUdfData[0].getUdf86_Flag())
					udfData[0].setUdf86_Flag(actualUdfData[0].getUdf86_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf87_Flag() && null != udfData[0].getUdf87_Value() && null != actualUdfData[0].getUdf87_Value() )
					udfData[0].setUdf87_Flag(actualUdfData[0].getUdf87_Flag());
				else if(null==actualUdfData[0].getUdf87_Flag() && null!=actualUdfData[0].getUdf87_Value() && null != udfData[0].getUdf87_Value() && !udfData[0].getUdf87_Value().equals(actualUdfData[0].getUdf87_Value()))
					udfData[0].setUdf87_Flag("M");
				else if(null==actualUdfData[0].getUdf87_Flag() && null==actualUdfData[0].getUdf87_Value() && null!=udfData[0].getUdf87_Value())
					udfData[0].setUdf87_Flag("A");
					else if(null!=actualUdfData[0].getUdf87_Flag())
					udfData[0].setUdf87_Flag(actualUdfData[0].getUdf87_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf88_Flag() && null != udfData[0].getUdf88_Value() && null != actualUdfData[0].getUdf88_Value() )
					udfData[0].setUdf88_Flag(actualUdfData[0].getUdf88_Flag());
				else if(null==actualUdfData[0].getUdf88_Flag() && null!=actualUdfData[0].getUdf88_Value() && null != udfData[0].getUdf88_Value() && !udfData[0].getUdf88_Value().equals(actualUdfData[0].getUdf88_Value()))
					udfData[0].setUdf88_Flag("M");
				else if(null==actualUdfData[0].getUdf88_Flag() && null==actualUdfData[0].getUdf88_Value() && null!=udfData[0].getUdf88_Value())
					udfData[0].setUdf88_Flag("A");
					else if(null!=actualUdfData[0].getUdf88_Flag())
					udfData[0].setUdf88_Flag(actualUdfData[0].getUdf88_Flag());
					
					
					if(null!=actualUdfData[0].getUdf89_Flag() && null != udfData[0].getUdf89_Value() && null != actualUdfData[0].getUdf89_Value())
					udfData[0].setUdf89_Flag(actualUdfData[0].getUdf89_Flag());
				else if(null==actualUdfData[0].getUdf89_Flag() && null!=actualUdfData[0].getUdf89_Value() && null != udfData[0].getUdf89_Value() && !udfData[0].getUdf89_Value().equals(actualUdfData[0].getUdf89_Value()))
					udfData[0].setUdf89_Flag("M");
				else if(null==actualUdfData[0].getUdf89_Flag() && null==actualUdfData[0].getUdf89_Value() && null!=udfData[0].getUdf89_Value())
					udfData[0].setUdf89_Flag("A");
					else if(null!=actualUdfData[0].getUdf89_Flag())
					udfData[0].setUdf89_Flag(actualUdfData[0].getUdf89_Flag());
					
					if(null!=actualUdfData[0].getUdf90_Flag() && null != udfData[0].getUdf90_Value() && null != actualUdfData[0].getUdf90_Value())
						udfData[0].setUdf90_Flag(actualUdfData[0].getUdf90_Flag());
					else if(null==actualUdfData[0].getUdf90_Flag() && null!=actualUdfData[0].getUdf90_Value() && null != udfData[0].getUdf90_Value() && !udfData[0].getUdf90_Value().equals(actualUdfData[0].getUdf90_Value()))
						udfData[0].setUdf90_Flag("M");
					else if(null==actualUdfData[0].getUdf90_Flag() && null==actualUdfData[0].getUdf90_Value() && null!=udfData[0].getUdf90_Value())
						udfData[0].setUdf90_Flag("A");
					else if(null!=actualUdfData[0].getUdf90_Flag())
						udfData[0].setUdf90_Flag(actualUdfData[0].getUdf90_Flag());




if(null!=actualUdfData[0].getUdf91_Flag() && null != udfData[0].getUdf91_Value() && null != actualUdfData[0].getUdf91_Value())
					udfData[0].setUdf91_Flag(actualUdfData[0].getUdf91_Flag());
				else if(null==actualUdfData[0].getUdf91_Flag() && null!=actualUdfData[0].getUdf91_Value() && null != udfData[0].getUdf91_Value() && !udfData[0].getUdf91_Value().equals(actualUdfData[0].getUdf91_Value()))
					udfData[0].setUdf91_Flag("M");
				else if(null==actualUdfData[0].getUdf91_Flag() && null==actualUdfData[0].getUdf91_Value() && null!=udfData[0].getUdf91_Value())
					udfData[0].setUdf91_Flag("A");
				else if(null!=actualUdfData[0].getUdf91_Flag())
					udfData[0].setUdf91_Flag(actualUdfData[0].getUdf91_Flag());
					
					if(null!=actualUdfData[0].getUdf92_Flag() && null != udfData[0].getUdf92_Value() && null != actualUdfData[0].getUdf92_Value())
					udfData[0].setUdf92_Flag(actualUdfData[0].getUdf92_Flag());
				else if(null==actualUdfData[0].getUdf92_Flag() && null!=actualUdfData[0].getUdf92_Value() && null != udfData[0].getUdf92_Value() && !udfData[0].getUdf92_Value().equals(actualUdfData[0].getUdf92_Value()))
					udfData[0].setUdf92_Flag("M");
				else if(null==actualUdfData[0].getUdf92_Flag() && null==actualUdfData[0].getUdf92_Value() && null!=udfData[0].getUdf92_Value())
					udfData[0].setUdf92_Flag("A");
					else if(null!=actualUdfData[0].getUdf92_Flag())
					udfData[0].setUdf92_Flag(actualUdfData[0].getUdf92_Flag());
					
					
					if(null!=actualUdfData[0].getUdf93_Flag() && null != udfData[0].getUdf93_Value() && null != actualUdfData[0].getUdf93_Value())
					udfData[0].setUdf93_Flag(actualUdfData[0].getUdf93_Flag());
				else if(null==actualUdfData[0].getUdf93_Flag() && null!=actualUdfData[0].getUdf93_Value() && null != udfData[0].getUdf93_Value() && !udfData[0].getUdf93_Value().equals(actualUdfData[0].getUdf93_Value()))
					udfData[0].setUdf93_Flag("M");
				else if(null==actualUdfData[0].getUdf93_Flag() && null==actualUdfData[0].getUdf93_Value() && null!=udfData[0].getUdf93_Value())
					udfData[0].setUdf93_Flag("A");
					else if(null!=actualUdfData[0].getUdf93_Flag())
					udfData[0].setUdf93_Flag(actualUdfData[0].getUdf93_Flag());
					
					
					if(null!=actualUdfData[0].getUdf94_Flag() && null != udfData[0].getUdf94_Value() && null != actualUdfData[0].getUdf94_Value())
					udfData[0].setUdf94_Flag(actualUdfData[0].getUdf94_Flag());
				else if(null==actualUdfData[0].getUdf94_Flag() && null!=actualUdfData[0].getUdf94_Value() && null != udfData[0].getUdf94_Value() && !udfData[0].getUdf94_Value().equals(actualUdfData[0].getUdf94_Value()))
					udfData[0].setUdf94_Flag("M");
				else if(null==actualUdfData[0].getUdf94_Flag() && null==actualUdfData[0].getUdf94_Value() && null!=udfData[0].getUdf94_Value())
					udfData[0].setUdf94_Flag("A");
					else if(null!=actualUdfData[0].getUdf94_Flag())
					udfData[0].setUdf94_Flag(actualUdfData[0].getUdf94_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf95_Flag() && null != udfData[0].getUdf95_Value() && null != actualUdfData[0].getUdf95_Value())
					udfData[0].setUdf95_Flag(actualUdfData[0].getUdf95_Flag());
				else if(null==actualUdfData[0].getUdf95_Flag() && null!=actualUdfData[0].getUdf95_Value() && null != udfData[0].getUdf95_Value() && !udfData[0].getUdf95_Value().equals(actualUdfData[0].getUdf95_Value()))
					udfData[0].setUdf95_Flag("M");
				else if(null==actualUdfData[0].getUdf95_Flag() && null==actualUdfData[0].getUdf95_Value() && null!=udfData[0].getUdf95_Value())
					udfData[0].setUdf95_Flag("A");
					else if(null!=actualUdfData[0].getUdf95_Flag())
					udfData[0].setUdf95_Flag(actualUdfData[0].getUdf95_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf96_Flag() && null != udfData[0].getUdf96_Value() && null != actualUdfData[0].getUdf96_Value())
					udfData[0].setUdf96_Flag(actualUdfData[0].getUdf96_Flag());
				else if(null==actualUdfData[0].getUdf96_Flag() && null!=actualUdfData[0].getUdf96_Value() && null != udfData[0].getUdf96_Value() && !udfData[0].getUdf96_Value().equals(actualUdfData[0].getUdf96_Value()))
					udfData[0].setUdf96_Flag("M");
				else if(null==actualUdfData[0].getUdf96_Flag() && null==actualUdfData[0].getUdf96_Value() && null!=udfData[0].getUdf96_Value())
					udfData[0].setUdf96_Flag("A");
					else if(null!=actualUdfData[0].getUdf96_Flag())
					udfData[0].setUdf96_Flag(actualUdfData[0].getUdf96_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf97_Flag() && null != udfData[0].getUdf97_Value() && null != actualUdfData[0].getUdf97_Value() )
					udfData[0].setUdf97_Flag(actualUdfData[0].getUdf97_Flag());
				else if(null==actualUdfData[0].getUdf97_Flag() && null!=actualUdfData[0].getUdf97_Value() && null != udfData[0].getUdf97_Value() && !udfData[0].getUdf97_Value().equals(actualUdfData[0].getUdf97_Value()))
					udfData[0].setUdf97_Flag("M");
				else if(null==actualUdfData[0].getUdf97_Flag() && null==actualUdfData[0].getUdf97_Value() && null!=udfData[0].getUdf97_Value())
					udfData[0].setUdf97_Flag("A");
					else if(null!=actualUdfData[0].getUdf97_Flag())
					udfData[0].setUdf97_Flag(actualUdfData[0].getUdf97_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf98_Flag() && null != udfData[0].getUdf98_Value() && null != actualUdfData[0].getUdf98_Value() )
					udfData[0].setUdf98_Flag(actualUdfData[0].getUdf98_Flag());
				else if(null==actualUdfData[0].getUdf98_Flag() && null!=actualUdfData[0].getUdf98_Value() && null != udfData[0].getUdf98_Value() && !udfData[0].getUdf98_Value().equals(actualUdfData[0].getUdf98_Value()))
					udfData[0].setUdf98_Flag("M");
				else if(null==actualUdfData[0].getUdf98_Flag() && null==actualUdfData[0].getUdf98_Value() && null!=udfData[0].getUdf98_Value())
					udfData[0].setUdf98_Flag("A");
					else if(null!=actualUdfData[0].getUdf98_Flag())
					udfData[0].setUdf98_Flag(actualUdfData[0].getUdf98_Flag());
					
					
					if(null!=actualUdfData[0].getUdf99_Flag() && null != udfData[0].getUdf99_Value() && null != actualUdfData[0].getUdf99_Value())
					udfData[0].setUdf99_Flag(actualUdfData[0].getUdf99_Flag());
				else if(null==actualUdfData[0].getUdf99_Flag() && null!=actualUdfData[0].getUdf99_Value() && null != udfData[0].getUdf99_Value() && !udfData[0].getUdf99_Value().equals(actualUdfData[0].getUdf99_Value()))
					udfData[0].setUdf99_Flag("M");
				else if(null==actualUdfData[0].getUdf99_Flag() && null==actualUdfData[0].getUdf99_Value() && null!=udfData[0].getUdf99_Value())
					udfData[0].setUdf99_Flag("A");
					else if(null!=actualUdfData[0].getUdf99_Flag())
					udfData[0].setUdf99_Flag(actualUdfData[0].getUdf99_Flag());
					
				
					
				if(null!=actualUdfData[0].getUdf100_Flag() && null != udfData[0].getUdf100_Value() && null != actualUdfData[0].getUdf100_Value())
							udfData[0].setUdf100_Flag(actualUdfData[0].getUdf100_Flag());
						else if(null==actualUdfData[0].getUdf100_Flag() && null!=actualUdfData[0].getUdf100_Value() && null != udfData[0].getUdf100_Value() && !udfData[0].getUdf100_Value().equals(actualUdfData[0].getUdf100_Value()))
							udfData[0].setUdf100_Flag("M");
						else if(null==actualUdfData[0].getUdf100_Flag() && null==actualUdfData[0].getUdf100_Value() && null!=udfData[0].getUdf100_Value())
							udfData[0].setUdf100_Flag("A");
						else if(null!=actualUdfData[0].getUdf100_Flag())
							udfData[0].setUdf100_Flag(actualUdfData[0].getUdf100_Flag());
						
					
				if(null!=actualUdfData[0].getUdf101_Flag() && null != udfData[0].getUdf101_Value() && null != actualUdfData[0].getUdf101_Value())
					udfData[0].setUdf101_Flag(actualUdfData[0].getUdf101_Flag());
				else if(null==actualUdfData[0].getUdf101_Flag() && null!=actualUdfData[0].getUdf101_Value() && null != udfData[0].getUdf101_Value() && !udfData[0].getUdf101_Value().equals(actualUdfData[0].getUdf101_Value()))
					udfData[0].setUdf101_Flag("M");
				else if(null==actualUdfData[0].getUdf101_Flag() && null==actualUdfData[0].getUdf101_Value() && null!=udfData[0].getUdf101_Value())
					udfData[0].setUdf101_Flag("A");
				else if(null!=actualUdfData[0].getUdf101_Flag())
					udfData[0].setUdf101_Flag(actualUdfData[0].getUdf101_Flag());
				

				if(null!=actualUdfData[0].getUdf102_Flag() && null != udfData[0].getUdf102_Value() && null != actualUdfData[0].getUdf102_Value())
					udfData[0].setUdf102_Flag(actualUdfData[0].getUdf102_Flag());
				else if(null==actualUdfData[0].getUdf102_Flag() && null!=actualUdfData[0].getUdf102_Value() && null != udfData[0].getUdf102_Value() && !udfData[0].getUdf102_Value().equals(actualUdfData[0].getUdf102_Value()))
					udfData[0].setUdf102_Flag("M");
				else if(null==actualUdfData[0].getUdf102_Flag() && null==actualUdfData[0].getUdf102_Value() && null!=udfData[0].getUdf102_Value())
					udfData[0].setUdf102_Flag("A");
				else if(null!=actualUdfData[0].getUdf102_Flag())
					udfData[0].setUdf102_Flag(actualUdfData[0].getUdf102_Flag());
				

				if(null!=actualUdfData[0].getUdf103_Flag() && null != udfData[0].getUdf103_Value() && null != actualUdfData[0].getUdf103_Value())
					udfData[0].setUdf103_Flag(actualUdfData[0].getUdf103_Flag());
				else if(null==actualUdfData[0].getUdf103_Flag() && null!=actualUdfData[0].getUdf103_Value() && null != udfData[0].getUdf103_Value() && !udfData[0].getUdf103_Value().equals(actualUdfData[0].getUdf103_Value()))
					udfData[0].setUdf103_Flag("M");
				else if(null==actualUdfData[0].getUdf103_Flag() && null==actualUdfData[0].getUdf103_Value() && null!=udfData[0].getUdf103_Value())
					udfData[0].setUdf103_Flag("A");
				else if(null!=actualUdfData[0].getUdf103_Flag())
					udfData[0].setUdf103_Flag(actualUdfData[0].getUdf103_Flag());
				

				if(null!=actualUdfData[0].getUdf104_Flag() && null != udfData[0].getUdf104_Value() && null != actualUdfData[0].getUdf104_Value())
					udfData[0].setUdf104_Flag(actualUdfData[0].getUdf104_Flag());
				else if(null==actualUdfData[0].getUdf104_Flag() && null!=actualUdfData[0].getUdf104_Value() && null != udfData[0].getUdf104_Value() && !udfData[0].getUdf104_Value().equals(actualUdfData[0].getUdf104_Value()))
					udfData[0].setUdf104_Flag("M");
				else if(null==actualUdfData[0].getUdf104_Flag() && null==actualUdfData[0].getUdf104_Value() && null!=udfData[0].getUdf104_Value())
					udfData[0].setUdf104_Flag("A");
				else if(null!=actualUdfData[0].getUdf104_Flag())
					udfData[0].setUdf104_Flag(actualUdfData[0].getUdf104_Flag());
				

				if(null!=actualUdfData[0].getUdf105_Flag() && null != udfData[0].getUdf105_Value() && null != actualUdfData[0].getUdf105_Value())
					udfData[0].setUdf105_Flag(actualUdfData[0].getUdf105_Flag());
				else if(null==actualUdfData[0].getUdf105_Flag() && null!=actualUdfData[0].getUdf105_Value() && null != udfData[0].getUdf105_Value() && !udfData[0].getUdf105_Value().equals(actualUdfData[0].getUdf105_Value()))
					udfData[0].setUdf105_Flag("M");
				else if(null==actualUdfData[0].getUdf105_Flag() && null==actualUdfData[0].getUdf105_Value() && null!=udfData[0].getUdf105_Value())
					udfData[0].setUdf105_Flag("A");
				else if(null!=actualUdfData[0].getUdf105_Flag())
					udfData[0].setUdf105_Flag(actualUdfData[0].getUdf105_Flag());
											
				if(null!=actualUdfData[0].getUdf106_Flag() && null != udfData[0].getUdf106_Value() && null != actualUdfData[0].getUdf106_Value())
					udfData[0].setUdf106_Flag(actualUdfData[0].getUdf106_Flag());
				else if(null==actualUdfData[0].getUdf106_Flag() && null!=actualUdfData[0].getUdf106_Value() && null != udfData[0].getUdf106_Value() && !udfData[0].getUdf106_Value().equals(actualUdfData[0].getUdf106_Value()))
					udfData[0].setUdf106_Flag("M");
				else if(null==actualUdfData[0].getUdf106_Flag() && null==actualUdfData[0].getUdf106_Value() && null!=udfData[0].getUdf106_Value())
					udfData[0].setUdf106_Flag("A");
				else if(null!=actualUdfData[0].getUdf106_Flag())
					udfData[0].setUdf106_Flag(actualUdfData[0].getUdf106_Flag());
							
		if(null!=actualUdfData[0].getUdf107_Flag() && null != udfData[0].getUdf107_Value() && null != actualUdfData[0].getUdf107_Value())
					udfData[0].setUdf107_Flag(actualUdfData[0].getUdf107_Flag());
				else if(null==actualUdfData[0].getUdf107_Flag() && null!=actualUdfData[0].getUdf107_Value() && null != udfData[0].getUdf107_Value() && !udfData[0].getUdf107_Value().equals(actualUdfData[0].getUdf107_Value()))
					udfData[0].setUdf107_Flag("M");
				else if(null==actualUdfData[0].getUdf107_Flag() && null==actualUdfData[0].getUdf107_Value() && null!=udfData[0].getUdf107_Value())
					udfData[0].setUdf107_Flag("A");
				else if(null!=actualUdfData[0].getUdf107_Flag())
					udfData[0].setUdf107_Flag(actualUdfData[0].getUdf107_Flag());
							
		if(null!=actualUdfData[0].getUdf108_Flag() && null != udfData[0].getUdf108_Value() && null != actualUdfData[0].getUdf108_Value())
					udfData[0].setUdf108_Flag(actualUdfData[0].getUdf108_Flag());
				else if(null==actualUdfData[0].getUdf108_Flag() && null!=actualUdfData[0].getUdf108_Value() && null != udfData[0].getUdf108_Value() && !udfData[0].getUdf108_Value().equals(actualUdfData[0].getUdf108_Value()))
					udfData[0].setUdf108_Flag("M");
				else if(null==actualUdfData[0].getUdf108_Flag() && null==actualUdfData[0].getUdf108_Value() && null!=udfData[0].getUdf108_Value())
					udfData[0].setUdf108_Flag("A");
				else if(null!=actualUdfData[0].getUdf108_Flag())
					udfData[0].setUdf108_Flag(actualUdfData[0].getUdf108_Flag());
							
		if(null!=actualUdfData[0].getUdf109_Flag() && null != udfData[0].getUdf109_Value() && null != actualUdfData[0].getUdf109_Value())
					udfData[0].setUdf109_Flag(actualUdfData[0].getUdf109_Flag());
				else if(null==actualUdfData[0].getUdf109_Flag() && null!=actualUdfData[0].getUdf109_Value() && null != udfData[0].getUdf109_Value() && !udfData[0].getUdf109_Value().equals(actualUdfData[0].getUdf109_Value()))
					udfData[0].setUdf109_Flag("M");
				else if(null==actualUdfData[0].getUdf109_Flag() && null==actualUdfData[0].getUdf109_Value() && null!=udfData[0].getUdf109_Value())
					udfData[0].setUdf109_Flag("A");
				else if(null!=actualUdfData[0].getUdf109_Flag())
					udfData[0].setUdf109_Flag(actualUdfData[0].getUdf109_Flag());
							
		if(null!=actualUdfData[0].getUdf110_Flag() && null != udfData[0].getUdf110_Value() && null != actualUdfData[0].getUdf110_Value())
					udfData[0].setUdf110_Flag(actualUdfData[0].getUdf110_Flag());
				else if(null==actualUdfData[0].getUdf110_Flag() && null!=actualUdfData[0].getUdf110_Value() && null != udfData[0].getUdf110_Value() && !udfData[0].getUdf110_Value().equals(actualUdfData[0].getUdf110_Value()))
					udfData[0].setUdf110_Flag("M");
				else if(null==actualUdfData[0].getUdf110_Flag() && null==actualUdfData[0].getUdf110_Value() && null!=udfData[0].getUdf110_Value())
					udfData[0].setUdf110_Flag("A");
				else if(null!=actualUdfData[0].getUdf110_Flag())
					udfData[0].setUdf110_Flag(actualUdfData[0].getUdf110_Flag());
							
		if(null!=actualUdfData[0].getUdf111_Flag() && null != udfData[0].getUdf111_Value() && null != actualUdfData[0].getUdf111_Value())
					udfData[0].setUdf111_Flag(actualUdfData[0].getUdf111_Flag());
				else if(null==actualUdfData[0].getUdf111_Flag() && null!=actualUdfData[0].getUdf111_Value() && null != udfData[0].getUdf111_Value() && !udfData[0].getUdf111_Value().equals(actualUdfData[0].getUdf111_Value()))
					udfData[0].setUdf111_Flag("M");
				else if(null==actualUdfData[0].getUdf111_Flag() && null==actualUdfData[0].getUdf111_Value() && null!=udfData[0].getUdf111_Value())
					udfData[0].setUdf111_Flag("A");
				else if(null!=actualUdfData[0].getUdf111_Flag())
					udfData[0].setUdf111_Flag(actualUdfData[0].getUdf111_Flag());
							
		if(null!=actualUdfData[0].getUdf112_Flag() && null != udfData[0].getUdf112_Value() && null != actualUdfData[0].getUdf112_Value())
					udfData[0].setUdf112_Flag(actualUdfData[0].getUdf112_Flag());
				else if(null==actualUdfData[0].getUdf112_Flag() && null!=actualUdfData[0].getUdf112_Value() && null != udfData[0].getUdf112_Value() && !udfData[0].getUdf112_Value().equals(actualUdfData[0].getUdf112_Value()))
					udfData[0].setUdf112_Flag("M");
				else if(null==actualUdfData[0].getUdf112_Flag() && null==actualUdfData[0].getUdf112_Value() && null!=udfData[0].getUdf112_Value())
					udfData[0].setUdf112_Flag("A");
				else if(null!=actualUdfData[0].getUdf112_Flag())
					udfData[0].setUdf112_Flag(actualUdfData[0].getUdf112_Flag());
							
		if(null!=actualUdfData[0].getUdf113_Flag() && null != udfData[0].getUdf113_Value() && null != actualUdfData[0].getUdf113_Value())
					udfData[0].setUdf113_Flag(actualUdfData[0].getUdf113_Flag());
				else if(null==actualUdfData[0].getUdf113_Flag() && null!=actualUdfData[0].getUdf113_Value() && null != udfData[0].getUdf113_Value() && !udfData[0].getUdf113_Value().equals(actualUdfData[0].getUdf113_Value()))
					udfData[0].setUdf113_Flag("M");
				else if(null==actualUdfData[0].getUdf113_Flag() && null==actualUdfData[0].getUdf113_Value() && null!=udfData[0].getUdf113_Value())
					udfData[0].setUdf113_Flag("A");
				else if(null!=actualUdfData[0].getUdf113_Flag())
					udfData[0].setUdf113_Flag(actualUdfData[0].getUdf113_Flag());
	
		if(null!=actualUdfData[0].getUdf114_Flag() && null != udfData[0].getUdf114_Value() && null != actualUdfData[0].getUdf114_Value())
					udfData[0].setUdf114_Flag(actualUdfData[0].getUdf114_Flag());
				else if(null==actualUdfData[0].getUdf114_Flag() && null!=actualUdfData[0].getUdf114_Value() && null != udfData[0].getUdf114_Value() && !udfData[0].getUdf114_Value().equals(actualUdfData[0].getUdf114_Value()))
					udfData[0].setUdf114_Flag("M");
				else if(null==actualUdfData[0].getUdf114_Flag() && null==actualUdfData[0].getUdf114_Value() && null!=udfData[0].getUdf114_Value())
					udfData[0].setUdf114_Flag("A");
				else if(null!=actualUdfData[0].getUdf114_Flag())
					udfData[0].setUdf114_Flag(actualUdfData[0].getUdf114_Flag());
							
		if(null!=actualUdfData[0].getUdf115_Flag() && null != udfData[0].getUdf115_Value() && null != actualUdfData[0].getUdf115_Value())
					udfData[0].setUdf115_Flag(actualUdfData[0].getUdf115_Flag());
				else if(null==actualUdfData[0].getUdf115_Flag() && null!=actualUdfData[0].getUdf115_Value() && null != udfData[0].getUdf115_Value() && !udfData[0].getUdf115_Value().equals(actualUdfData[0].getUdf115_Value()))
					udfData[0].setUdf115_Flag("M");
				else if(null==actualUdfData[0].getUdf115_Flag() && null==actualUdfData[0].getUdf115_Value() && null!=udfData[0].getUdf115_Value())
					udfData[0].setUdf115_Flag("A");
				else if(null!=actualUdfData[0].getUdf115_Flag())
					udfData[0].setUdf115_Flag(actualUdfData[0].getUdf115_Flag());
							
		
						
					
						
										
						
			}		
		}

				private void updateUdfModifyForRejectionCase2(ICustomerSysXRef actualXref,ICustomerSysXRef stagingXref) {
			ILimitXRefUdf2 udfData[]=stagingXref.getXRefUdfData2();
			ILimitXRefUdf2 actualUdfData[]=actualXref.getXRefUdfData2();

			if(null!=udfData && null!=actualUdfData) {
				
				if(null!=actualUdfData[0].getUdf116_Flag() && null != udfData[0].getUdf116_Value() && null != actualUdfData[0].getUdf116_Value())
					udfData[0].setUdf116_Flag(actualUdfData[0].getUdf116_Flag());
				else if(null==actualUdfData[0].getUdf116_Flag() && null!=actualUdfData[0].getUdf116_Value() && null != udfData[0].getUdf116_Value() && !udfData[0].getUdf116_Value().equals(actualUdfData[0].getUdf116_Value()))
					udfData[0].setUdf116_Flag("M");
				else if(null==actualUdfData[0].getUdf116_Flag() && null==actualUdfData[0].getUdf116_Value() && null!=udfData[0].getUdf116_Value())
					udfData[0].setUdf116_Flag("A");
					else if(null!=actualUdfData[0].getUdf116_Flag())
					udfData[0].setUdf116_Flag(actualUdfData[0].getUdf116_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf117_Flag() && null != udfData[0].getUdf117_Value() && null != actualUdfData[0].getUdf117_Value() )
					udfData[0].setUdf117_Flag(actualUdfData[0].getUdf117_Flag());
				else if(null==actualUdfData[0].getUdf117_Flag() && null!=actualUdfData[0].getUdf117_Value() && null != udfData[0].getUdf117_Value() && !udfData[0].getUdf117_Value().equals(actualUdfData[0].getUdf117_Value()))
					udfData[0].setUdf117_Flag("M");
				else if(null==actualUdfData[0].getUdf117_Flag() && null==actualUdfData[0].getUdf117_Value() && null!=udfData[0].getUdf117_Value())
					udfData[0].setUdf117_Flag("A");
					else if(null!=actualUdfData[0].getUdf117_Flag())
					udfData[0].setUdf117_Flag(actualUdfData[0].getUdf117_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf118_Flag() && null != udfData[0].getUdf118_Value() && null != actualUdfData[0].getUdf118_Value() )
					udfData[0].setUdf118_Flag(actualUdfData[0].getUdf118_Flag());
				else if(null==actualUdfData[0].getUdf118_Flag() && null!=actualUdfData[0].getUdf118_Value() && null != udfData[0].getUdf118_Value() && !udfData[0].getUdf118_Value().equals(actualUdfData[0].getUdf118_Value()))
					udfData[0].setUdf118_Flag("M");
				else if(null==actualUdfData[0].getUdf118_Flag() && null==actualUdfData[0].getUdf118_Value() && null!=udfData[0].getUdf118_Value())
					udfData[0].setUdf118_Flag("A");
					else if(null!=actualUdfData[0].getUdf118_Flag())
					udfData[0].setUdf118_Flag(actualUdfData[0].getUdf118_Flag());
					
					
					if(null!=actualUdfData[0].getUdf119_Flag() && null != udfData[0].getUdf119_Value() && null != actualUdfData[0].getUdf119_Value())
					udfData[0].setUdf119_Flag(actualUdfData[0].getUdf119_Flag());
				else if(null==actualUdfData[0].getUdf119_Flag() && null!=actualUdfData[0].getUdf119_Value() && null != udfData[0].getUdf119_Value() && !udfData[0].getUdf119_Value().equals(actualUdfData[0].getUdf119_Value()))
					udfData[0].setUdf119_Flag("M");
				else if(null==actualUdfData[0].getUdf119_Flag() && null==actualUdfData[0].getUdf119_Value() && null!=udfData[0].getUdf119_Value())
					udfData[0].setUdf119_Flag("A");
					else if(null!=actualUdfData[0].getUdf119_Flag())
					udfData[0].setUdf119_Flag(actualUdfData[0].getUdf119_Flag());
					
				
					
					if(null!=actualUdfData[0].getUdf120_Flag() && null != udfData[0].getUdf120_Value() && null != actualUdfData[0].getUdf120_Value())
						udfData[0].setUdf120_Flag(actualUdfData[0].getUdf120_Flag());
					else if(null==actualUdfData[0].getUdf120_Flag() && null!=actualUdfData[0].getUdf120_Value() && null != udfData[0].getUdf120_Value() && !udfData[0].getUdf120_Value().equals(actualUdfData[0].getUdf120_Value()))
						udfData[0].setUdf120_Flag("M");
					else if(null==actualUdfData[0].getUdf120_Flag() && null==actualUdfData[0].getUdf120_Value() && null!=udfData[0].getUdf120_Value())
						udfData[0].setUdf120_Flag("A");
					else if(null!=actualUdfData[0].getUdf120_Flag())
						udfData[0].setUdf120_Flag(actualUdfData[0].getUdf120_Flag());




if(null!=actualUdfData[0].getUdf121_Flag() && null != udfData[0].getUdf121_Value() && null != actualUdfData[0].getUdf121_Value())
					udfData[0].setUdf121_Flag(actualUdfData[0].getUdf121_Flag());
				else if(null==actualUdfData[0].getUdf121_Flag() && null!=actualUdfData[0].getUdf121_Value() && null != udfData[0].getUdf121_Value() && !udfData[0].getUdf121_Value().equals(actualUdfData[0].getUdf121_Value()))
					udfData[0].setUdf121_Flag("M");
				else if(null==actualUdfData[0].getUdf121_Flag() && null==actualUdfData[0].getUdf121_Value() && null!=udfData[0].getUdf121_Value())
					udfData[0].setUdf121_Flag("A");
				else if(null!=actualUdfData[0].getUdf121_Flag())
					udfData[0].setUdf121_Flag(actualUdfData[0].getUdf121_Flag());
					
					if(null!=actualUdfData[0].getUdf122_Flag() && null != udfData[0].getUdf122_Value() && null != actualUdfData[0].getUdf122_Value())
					udfData[0].setUdf122_Flag(actualUdfData[0].getUdf122_Flag());
				else if(null==actualUdfData[0].getUdf122_Flag() && null!=actualUdfData[0].getUdf122_Value() && null != udfData[0].getUdf122_Value() && !udfData[0].getUdf122_Value().equals(actualUdfData[0].getUdf122_Value()))
					udfData[0].setUdf122_Flag("M");
				else if(null==actualUdfData[0].getUdf122_Flag() && null==actualUdfData[0].getUdf122_Value() && null!=udfData[0].getUdf122_Value())
					udfData[0].setUdf122_Flag("A");
					else if(null!=actualUdfData[0].getUdf122_Flag())
					udfData[0].setUdf122_Flag(actualUdfData[0].getUdf122_Flag());
					
					
					if(null!=actualUdfData[0].getUdf123_Flag() && null != udfData[0].getUdf123_Value() && null != actualUdfData[0].getUdf123_Value())
					udfData[0].setUdf123_Flag(actualUdfData[0].getUdf123_Flag());
				else if(null==actualUdfData[0].getUdf123_Flag() && null!=actualUdfData[0].getUdf123_Value() && null != udfData[0].getUdf123_Value() && !udfData[0].getUdf123_Value().equals(actualUdfData[0].getUdf123_Value()))
					udfData[0].setUdf123_Flag("M");
				else if(null==actualUdfData[0].getUdf123_Flag() && null==actualUdfData[0].getUdf123_Value() && null!=udfData[0].getUdf123_Value())
					udfData[0].setUdf123_Flag("A");
					else if(null!=actualUdfData[0].getUdf123_Flag())
					udfData[0].setUdf123_Flag(actualUdfData[0].getUdf123_Flag());
					
					
					if(null!=actualUdfData[0].getUdf124_Flag() && null != udfData[0].getUdf124_Value() && null != actualUdfData[0].getUdf124_Value())
					udfData[0].setUdf124_Flag(actualUdfData[0].getUdf124_Flag());
				else if(null==actualUdfData[0].getUdf124_Flag() && null!=actualUdfData[0].getUdf124_Value() && null != udfData[0].getUdf124_Value() && !udfData[0].getUdf124_Value().equals(actualUdfData[0].getUdf124_Value()))
					udfData[0].setUdf124_Flag("M");
				else if(null==actualUdfData[0].getUdf124_Flag() && null==actualUdfData[0].getUdf124_Value() && null!=udfData[0].getUdf124_Value())
					udfData[0].setUdf124_Flag("A");
					else if(null!=actualUdfData[0].getUdf124_Flag())
					udfData[0].setUdf124_Flag(actualUdfData[0].getUdf124_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf125_Flag() && null != udfData[0].getUdf125_Value() && null != actualUdfData[0].getUdf125_Value())
					udfData[0].setUdf125_Flag(actualUdfData[0].getUdf125_Flag());
				else if(null==actualUdfData[0].getUdf125_Flag() && null!=actualUdfData[0].getUdf125_Value() && null != udfData[0].getUdf125_Value() && !udfData[0].getUdf125_Value().equals(actualUdfData[0].getUdf125_Value()))
					udfData[0].setUdf125_Flag("M");
				else if(null==actualUdfData[0].getUdf125_Flag() && null==actualUdfData[0].getUdf125_Value() && null!=udfData[0].getUdf125_Value())
					udfData[0].setUdf125_Flag("A");
					else if(null!=actualUdfData[0].getUdf125_Flag())
					udfData[0].setUdf125_Flag(actualUdfData[0].getUdf125_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf126_Flag() && null != udfData[0].getUdf126_Value() && null != actualUdfData[0].getUdf126_Value())
					udfData[0].setUdf126_Flag(actualUdfData[0].getUdf126_Flag());
				else if(null==actualUdfData[0].getUdf126_Flag() && null!=actualUdfData[0].getUdf126_Value() && null != udfData[0].getUdf126_Value() && !udfData[0].getUdf126_Value().equals(actualUdfData[0].getUdf126_Value()))
					udfData[0].setUdf126_Flag("M");
				else if(null==actualUdfData[0].getUdf126_Flag() && null==actualUdfData[0].getUdf126_Value() && null!=udfData[0].getUdf126_Value())
					udfData[0].setUdf126_Flag("A");
					else if(null!=actualUdfData[0].getUdf126_Flag())
					udfData[0].setUdf126_Flag(actualUdfData[0].getUdf126_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf127_Flag() && null != udfData[0].getUdf127_Value() && null != actualUdfData[0].getUdf127_Value() )
					udfData[0].setUdf127_Flag(actualUdfData[0].getUdf127_Flag());
				else if(null==actualUdfData[0].getUdf127_Flag() && null!=actualUdfData[0].getUdf127_Value() && null != udfData[0].getUdf127_Value() && !udfData[0].getUdf127_Value().equals(actualUdfData[0].getUdf127_Value()))
					udfData[0].setUdf127_Flag("M");
				else if(null==actualUdfData[0].getUdf127_Flag() && null==actualUdfData[0].getUdf127_Value() && null!=udfData[0].getUdf127_Value())
					udfData[0].setUdf127_Flag("A");
					else if(null!=actualUdfData[0].getUdf127_Flag())
					udfData[0].setUdf127_Flag(actualUdfData[0].getUdf127_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf128_Flag() && null != udfData[0].getUdf128_Value() && null != actualUdfData[0].getUdf128_Value() )
					udfData[0].setUdf128_Flag(actualUdfData[0].getUdf128_Flag());
				else if(null==actualUdfData[0].getUdf128_Flag() && null!=actualUdfData[0].getUdf128_Value() && null != udfData[0].getUdf128_Value() && !udfData[0].getUdf128_Value().equals(actualUdfData[0].getUdf128_Value()))
					udfData[0].setUdf128_Flag("M");
				else if(null==actualUdfData[0].getUdf128_Flag() && null==actualUdfData[0].getUdf128_Value() && null!=udfData[0].getUdf128_Value())
					udfData[0].setUdf128_Flag("A");
					else if(null!=actualUdfData[0].getUdf128_Flag())
					udfData[0].setUdf128_Flag(actualUdfData[0].getUdf128_Flag());
					
					
					if(null!=actualUdfData[0].getUdf129_Flag() && null != udfData[0].getUdf129_Value() && null != actualUdfData[0].getUdf129_Value())
					udfData[0].setUdf129_Flag(actualUdfData[0].getUdf129_Flag());
				else if(null==actualUdfData[0].getUdf129_Flag() && null!=actualUdfData[0].getUdf129_Value() && null != udfData[0].getUdf129_Value() && !udfData[0].getUdf129_Value().equals(actualUdfData[0].getUdf129_Value()))
					udfData[0].setUdf129_Flag("M");
				else if(null==actualUdfData[0].getUdf129_Flag() && null==actualUdfData[0].getUdf129_Value() && null!=udfData[0].getUdf129_Value())
					udfData[0].setUdf129_Flag("A");
					else if(null!=actualUdfData[0].getUdf129_Flag())
					udfData[0].setUdf129_Flag(actualUdfData[0].getUdf129_Flag());
					
				
					
					if(null!=actualUdfData[0].getUdf130_Flag() && null != udfData[0].getUdf130_Value() && null != actualUdfData[0].getUdf130_Value())
						udfData[0].setUdf130_Flag(actualUdfData[0].getUdf130_Flag());
					else if(null==actualUdfData[0].getUdf130_Flag() && null!=actualUdfData[0].getUdf130_Value() && null != udfData[0].getUdf130_Value() && !udfData[0].getUdf130_Value().equals(actualUdfData[0].getUdf130_Value()))
						udfData[0].setUdf130_Flag("M");
					else if(null==actualUdfData[0].getUdf130_Flag() && null==actualUdfData[0].getUdf130_Value() && null!=udfData[0].getUdf130_Value())
						udfData[0].setUdf130_Flag("A");
					else if(null!=actualUdfData[0].getUdf130_Flag())
						udfData[0].setUdf130_Flag(actualUdfData[0].getUdf130_Flag());




if(null!=actualUdfData[0].getUdf131_Flag() && null != udfData[0].getUdf131_Value() && null != actualUdfData[0].getUdf131_Value())
					udfData[0].setUdf131_Flag(actualUdfData[0].getUdf131_Flag());
				else if(null==actualUdfData[0].getUdf131_Flag() && null!=actualUdfData[0].getUdf131_Value() && null != udfData[0].getUdf131_Value() && !udfData[0].getUdf131_Value().equals(actualUdfData[0].getUdf131_Value()))
					udfData[0].setUdf131_Flag("M");
				else if(null==actualUdfData[0].getUdf131_Flag() && null==actualUdfData[0].getUdf131_Value() && null!=udfData[0].getUdf131_Value())
					udfData[0].setUdf131_Flag("A");
				else if(null!=actualUdfData[0].getUdf131_Flag())
					udfData[0].setUdf131_Flag(actualUdfData[0].getUdf131_Flag());
					
					if(null!=actualUdfData[0].getUdf132_Flag() && null != udfData[0].getUdf132_Value() && null != actualUdfData[0].getUdf132_Value())
					udfData[0].setUdf132_Flag(actualUdfData[0].getUdf132_Flag());
				else if(null==actualUdfData[0].getUdf132_Flag() && null!=actualUdfData[0].getUdf132_Value() && null != udfData[0].getUdf132_Value() && !udfData[0].getUdf132_Value().equals(actualUdfData[0].getUdf132_Value()))
					udfData[0].setUdf132_Flag("M");
				else if(null==actualUdfData[0].getUdf132_Flag() && null==actualUdfData[0].getUdf132_Value() && null!=udfData[0].getUdf132_Value())
					udfData[0].setUdf132_Flag("A");
					else if(null!=actualUdfData[0].getUdf132_Flag())
					udfData[0].setUdf132_Flag(actualUdfData[0].getUdf132_Flag());
					
					
					if(null!=actualUdfData[0].getUdf133_Flag() && null != udfData[0].getUdf133_Value() && null != actualUdfData[0].getUdf133_Value())
					udfData[0].setUdf133_Flag(actualUdfData[0].getUdf133_Flag());
				else if(null==actualUdfData[0].getUdf133_Flag() && null!=actualUdfData[0].getUdf133_Value() && null != udfData[0].getUdf133_Value() && !udfData[0].getUdf133_Value().equals(actualUdfData[0].getUdf133_Value()))
					udfData[0].setUdf133_Flag("M");
				else if(null==actualUdfData[0].getUdf133_Flag() && null==actualUdfData[0].getUdf133_Value() && null!=udfData[0].getUdf133_Value())
					udfData[0].setUdf133_Flag("A");
					else if(null!=actualUdfData[0].getUdf133_Flag())
					udfData[0].setUdf133_Flag(actualUdfData[0].getUdf133_Flag());
					
					
					if(null!=actualUdfData[0].getUdf134_Flag() && null != udfData[0].getUdf134_Value() && null != actualUdfData[0].getUdf134_Value())
					udfData[0].setUdf134_Flag(actualUdfData[0].getUdf134_Flag());
				else if(null==actualUdfData[0].getUdf134_Flag() && null!=actualUdfData[0].getUdf134_Value() && null != udfData[0].getUdf134_Value() && !udfData[0].getUdf134_Value().equals(actualUdfData[0].getUdf134_Value()))
					udfData[0].setUdf134_Flag("M");
				else if(null==actualUdfData[0].getUdf134_Flag() && null==actualUdfData[0].getUdf134_Value() && null!=udfData[0].getUdf134_Value())
					udfData[0].setUdf134_Flag("A");
					else if(null!=actualUdfData[0].getUdf134_Flag())
					udfData[0].setUdf134_Flag(actualUdfData[0].getUdf134_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf135_Flag() && null != udfData[0].getUdf135_Value() && null != actualUdfData[0].getUdf135_Value())
					udfData[0].setUdf135_Flag(actualUdfData[0].getUdf135_Flag());
				else if(null==actualUdfData[0].getUdf135_Flag() && null!=actualUdfData[0].getUdf135_Value() && null != udfData[0].getUdf135_Value() && !udfData[0].getUdf135_Value().equals(actualUdfData[0].getUdf135_Value()))
					udfData[0].setUdf135_Flag("M");
				else if(null==actualUdfData[0].getUdf135_Flag() && null==actualUdfData[0].getUdf135_Value() && null!=udfData[0].getUdf135_Value())
					udfData[0].setUdf135_Flag("A");
					else if(null!=actualUdfData[0].getUdf135_Flag())
					udfData[0].setUdf135_Flag(actualUdfData[0].getUdf135_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf136_Flag() && null != udfData[0].getUdf136_Value() && null != actualUdfData[0].getUdf136_Value())
					udfData[0].setUdf136_Flag(actualUdfData[0].getUdf136_Flag());
				else if(null==actualUdfData[0].getUdf136_Flag() && null!=actualUdfData[0].getUdf136_Value() && null != udfData[0].getUdf136_Value() && !udfData[0].getUdf136_Value().equals(actualUdfData[0].getUdf136_Value()))
					udfData[0].setUdf136_Flag("M");
				else if(null==actualUdfData[0].getUdf136_Flag() && null==actualUdfData[0].getUdf136_Value() && null!=udfData[0].getUdf136_Value())
					udfData[0].setUdf136_Flag("A");
					else if(null!=actualUdfData[0].getUdf136_Flag())
					udfData[0].setUdf136_Flag(actualUdfData[0].getUdf136_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf137_Flag() && null != udfData[0].getUdf137_Value() && null != actualUdfData[0].getUdf137_Value() )
					udfData[0].setUdf137_Flag(actualUdfData[0].getUdf137_Flag());
				else if(null==actualUdfData[0].getUdf137_Flag() && null!=actualUdfData[0].getUdf137_Value() && null != udfData[0].getUdf137_Value() && !udfData[0].getUdf137_Value().equals(actualUdfData[0].getUdf137_Value()))
					udfData[0].setUdf137_Flag("M");
				else if(null==actualUdfData[0].getUdf137_Flag() && null==actualUdfData[0].getUdf137_Value() && null!=udfData[0].getUdf137_Value())
					udfData[0].setUdf137_Flag("A");
					else if(null!=actualUdfData[0].getUdf137_Flag())
					udfData[0].setUdf137_Flag(actualUdfData[0].getUdf137_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf138_Flag() && null != udfData[0].getUdf138_Value() && null != actualUdfData[0].getUdf138_Value() )
					udfData[0].setUdf138_Flag(actualUdfData[0].getUdf138_Flag());
				else if(null==actualUdfData[0].getUdf138_Flag() && null!=actualUdfData[0].getUdf138_Value() && null != udfData[0].getUdf138_Value() && !udfData[0].getUdf138_Value().equals(actualUdfData[0].getUdf138_Value()))
					udfData[0].setUdf138_Flag("M");
				else if(null==actualUdfData[0].getUdf138_Flag() && null==actualUdfData[0].getUdf138_Value() && null!=udfData[0].getUdf138_Value())
					udfData[0].setUdf138_Flag("A");
					else if(null!=actualUdfData[0].getUdf138_Flag())
					udfData[0].setUdf138_Flag(actualUdfData[0].getUdf138_Flag());
					
					
					if(null!=actualUdfData[0].getUdf139_Flag() && null != udfData[0].getUdf139_Value() && null != actualUdfData[0].getUdf139_Value())
					udfData[0].setUdf139_Flag(actualUdfData[0].getUdf139_Flag());
				else if(null==actualUdfData[0].getUdf139_Flag() && null!=actualUdfData[0].getUdf139_Value() && null != udfData[0].getUdf139_Value() && !udfData[0].getUdf139_Value().equals(actualUdfData[0].getUdf139_Value()))
					udfData[0].setUdf139_Flag("M");
				else if(null==actualUdfData[0].getUdf139_Flag() && null==actualUdfData[0].getUdf139_Value() && null!=udfData[0].getUdf139_Value())
					udfData[0].setUdf139_Flag("A");
					else if(null!=actualUdfData[0].getUdf139_Flag())
					udfData[0].setUdf139_Flag(actualUdfData[0].getUdf139_Flag());
					
				
					if(null!=actualUdfData[0].getUdf140_Flag() && null != udfData[0].getUdf140_Value() && null != actualUdfData[0].getUdf140_Value())
						udfData[0].setUdf140_Flag(actualUdfData[0].getUdf140_Flag());
					else if(null==actualUdfData[0].getUdf140_Flag() && null!=actualUdfData[0].getUdf140_Value() && null != udfData[0].getUdf140_Value() && !udfData[0].getUdf140_Value().equals(actualUdfData[0].getUdf140_Value()))
						udfData[0].setUdf140_Flag("M");
					else if(null==actualUdfData[0].getUdf140_Flag() && null==actualUdfData[0].getUdf140_Value() && null!=udfData[0].getUdf140_Value())
						udfData[0].setUdf140_Flag("A");
					else if(null!=actualUdfData[0].getUdf140_Flag())
						udfData[0].setUdf140_Flag(actualUdfData[0].getUdf140_Flag());




if(null!=actualUdfData[0].getUdf141_Flag() && null != udfData[0].getUdf141_Value() && null != actualUdfData[0].getUdf141_Value())
					udfData[0].setUdf141_Flag(actualUdfData[0].getUdf141_Flag());
				else if(null==actualUdfData[0].getUdf141_Flag() && null!=actualUdfData[0].getUdf141_Value() && null != udfData[0].getUdf141_Value() && !udfData[0].getUdf141_Value().equals(actualUdfData[0].getUdf141_Value()))
					udfData[0].setUdf141_Flag("M");
				else if(null==actualUdfData[0].getUdf141_Flag() && null==actualUdfData[0].getUdf141_Value() && null!=udfData[0].getUdf141_Value())
					udfData[0].setUdf141_Flag("A");
				else if(null!=actualUdfData[0].getUdf141_Flag())
					udfData[0].setUdf141_Flag(actualUdfData[0].getUdf141_Flag());
					
					if(null!=actualUdfData[0].getUdf142_Flag() && null != udfData[0].getUdf142_Value() && null != actualUdfData[0].getUdf142_Value())
					udfData[0].setUdf142_Flag(actualUdfData[0].getUdf142_Flag());
				else if(null==actualUdfData[0].getUdf142_Flag() && null!=actualUdfData[0].getUdf142_Value() && null != udfData[0].getUdf142_Value() && !udfData[0].getUdf142_Value().equals(actualUdfData[0].getUdf142_Value()))
					udfData[0].setUdf142_Flag("M");
				else if(null==actualUdfData[0].getUdf142_Flag() && null==actualUdfData[0].getUdf142_Value() && null!=udfData[0].getUdf142_Value())
					udfData[0].setUdf142_Flag("A");
					else if(null!=actualUdfData[0].getUdf142_Flag())
					udfData[0].setUdf142_Flag(actualUdfData[0].getUdf142_Flag());
					
					
					if(null!=actualUdfData[0].getUdf143_Flag() && null != udfData[0].getUdf143_Value() && null != actualUdfData[0].getUdf143_Value())
					udfData[0].setUdf143_Flag(actualUdfData[0].getUdf143_Flag());
				else if(null==actualUdfData[0].getUdf143_Flag() && null!=actualUdfData[0].getUdf143_Value() && null != udfData[0].getUdf143_Value() && !udfData[0].getUdf143_Value().equals(actualUdfData[0].getUdf143_Value()))
					udfData[0].setUdf143_Flag("M");
				else if(null==actualUdfData[0].getUdf143_Flag() && null==actualUdfData[0].getUdf143_Value() && null!=udfData[0].getUdf143_Value())
					udfData[0].setUdf143_Flag("A");
					else if(null!=actualUdfData[0].getUdf143_Flag())
					udfData[0].setUdf143_Flag(actualUdfData[0].getUdf143_Flag());
					
					
					if(null!=actualUdfData[0].getUdf144_Flag() && null != udfData[0].getUdf144_Value() && null != actualUdfData[0].getUdf144_Value())
					udfData[0].setUdf144_Flag(actualUdfData[0].getUdf144_Flag());
				else if(null==actualUdfData[0].getUdf144_Flag() && null!=actualUdfData[0].getUdf144_Value() && null != udfData[0].getUdf144_Value() && !udfData[0].getUdf144_Value().equals(actualUdfData[0].getUdf144_Value()))
					udfData[0].setUdf144_Flag("M");
				else if(null==actualUdfData[0].getUdf144_Flag() && null==actualUdfData[0].getUdf144_Value() && null!=udfData[0].getUdf144_Value())
					udfData[0].setUdf144_Flag("A");
					else if(null!=actualUdfData[0].getUdf144_Flag())
					udfData[0].setUdf144_Flag(actualUdfData[0].getUdf144_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf145_Flag() && null != udfData[0].getUdf145_Value() && null != actualUdfData[0].getUdf145_Value())
					udfData[0].setUdf145_Flag(actualUdfData[0].getUdf145_Flag());
				else if(null==actualUdfData[0].getUdf145_Flag() && null!=actualUdfData[0].getUdf145_Value() && null != udfData[0].getUdf145_Value() && !udfData[0].getUdf145_Value().equals(actualUdfData[0].getUdf145_Value()))
					udfData[0].setUdf145_Flag("M");
				else if(null==actualUdfData[0].getUdf145_Flag() && null==actualUdfData[0].getUdf145_Value() && null!=udfData[0].getUdf145_Value())
					udfData[0].setUdf145_Flag("A");
					else if(null!=actualUdfData[0].getUdf145_Flag())
					udfData[0].setUdf145_Flag(actualUdfData[0].getUdf145_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf146_Flag() && null != udfData[0].getUdf146_Value() && null != actualUdfData[0].getUdf146_Value())
					udfData[0].setUdf146_Flag(actualUdfData[0].getUdf146_Flag());
				else if(null==actualUdfData[0].getUdf146_Flag() && null!=actualUdfData[0].getUdf146_Value() && null != udfData[0].getUdf146_Value() && !udfData[0].getUdf146_Value().equals(actualUdfData[0].getUdf146_Value()))
					udfData[0].setUdf146_Flag("M");
				else if(null==actualUdfData[0].getUdf146_Flag() && null==actualUdfData[0].getUdf146_Value() && null!=udfData[0].getUdf146_Value())
					udfData[0].setUdf146_Flag("A");
					else if(null!=actualUdfData[0].getUdf146_Flag())
					udfData[0].setUdf146_Flag(actualUdfData[0].getUdf146_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf147_Flag() && null != udfData[0].getUdf147_Value() && null != actualUdfData[0].getUdf147_Value() )
					udfData[0].setUdf147_Flag(actualUdfData[0].getUdf147_Flag());
				else if(null==actualUdfData[0].getUdf147_Flag() && null!=actualUdfData[0].getUdf147_Value() && null != udfData[0].getUdf147_Value() && !udfData[0].getUdf147_Value().equals(actualUdfData[0].getUdf147_Value()))
					udfData[0].setUdf147_Flag("M");
				else if(null==actualUdfData[0].getUdf147_Flag() && null==actualUdfData[0].getUdf147_Value() && null!=udfData[0].getUdf147_Value())
					udfData[0].setUdf147_Flag("A");
					else if(null!=actualUdfData[0].getUdf147_Flag())
					udfData[0].setUdf147_Flag(actualUdfData[0].getUdf147_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf148_Flag() && null != udfData[0].getUdf148_Value() && null != actualUdfData[0].getUdf148_Value() )
					udfData[0].setUdf148_Flag(actualUdfData[0].getUdf148_Flag());
				else if(null==actualUdfData[0].getUdf148_Flag() && null!=actualUdfData[0].getUdf148_Value() && null != udfData[0].getUdf148_Value() && !udfData[0].getUdf148_Value().equals(actualUdfData[0].getUdf148_Value()))
					udfData[0].setUdf148_Flag("M");
				else if(null==actualUdfData[0].getUdf148_Flag() && null==actualUdfData[0].getUdf148_Value() && null!=udfData[0].getUdf148_Value())
					udfData[0].setUdf148_Flag("A");
					else if(null!=actualUdfData[0].getUdf148_Flag())
					udfData[0].setUdf148_Flag(actualUdfData[0].getUdf148_Flag());
					
					
					if(null!=actualUdfData[0].getUdf149_Flag() && null != udfData[0].getUdf149_Value() && null != actualUdfData[0].getUdf149_Value())
					udfData[0].setUdf149_Flag(actualUdfData[0].getUdf149_Flag());
				else if(null==actualUdfData[0].getUdf149_Flag() && null!=actualUdfData[0].getUdf149_Value() && null != udfData[0].getUdf149_Value() && !udfData[0].getUdf149_Value().equals(actualUdfData[0].getUdf149_Value()))
					udfData[0].setUdf149_Flag("M");
				else if(null==actualUdfData[0].getUdf149_Flag() && null==actualUdfData[0].getUdf149_Value() && null!=udfData[0].getUdf149_Value())
					udfData[0].setUdf149_Flag("A");
					else if(null!=actualUdfData[0].getUdf149_Flag())
					udfData[0].setUdf149_Flag(actualUdfData[0].getUdf149_Flag());
					
				
					if(null!=actualUdfData[0].getUdf150_Flag() && null != udfData[0].getUdf150_Value() && null != actualUdfData[0].getUdf150_Value())
						udfData[0].setUdf150_Flag(actualUdfData[0].getUdf150_Flag());
					else if(null==actualUdfData[0].getUdf150_Flag() && null!=actualUdfData[0].getUdf150_Value() && null != udfData[0].getUdf150_Value() && !udfData[0].getUdf150_Value().equals(actualUdfData[0].getUdf150_Value()))
						udfData[0].setUdf150_Flag("M");
					else if(null==actualUdfData[0].getUdf150_Flag() && null==actualUdfData[0].getUdf150_Value() && null!=udfData[0].getUdf150_Value())
						udfData[0].setUdf150_Flag("A");
					else if(null!=actualUdfData[0].getUdf150_Flag())
						udfData[0].setUdf150_Flag(actualUdfData[0].getUdf150_Flag());




if(null!=actualUdfData[0].getUdf151_Flag() && null != udfData[0].getUdf151_Value() && null != actualUdfData[0].getUdf151_Value())
					udfData[0].setUdf151_Flag(actualUdfData[0].getUdf151_Flag());
				else if(null==actualUdfData[0].getUdf151_Flag() && null!=actualUdfData[0].getUdf151_Value() && null != udfData[0].getUdf151_Value() && !udfData[0].getUdf151_Value().equals(actualUdfData[0].getUdf151_Value()))
					udfData[0].setUdf151_Flag("M");
				else if(null==actualUdfData[0].getUdf151_Flag() && null==actualUdfData[0].getUdf151_Value() && null!=udfData[0].getUdf151_Value())
					udfData[0].setUdf151_Flag("A");
				else if(null!=actualUdfData[0].getUdf151_Flag())
					udfData[0].setUdf151_Flag(actualUdfData[0].getUdf151_Flag());
					
					if(null!=actualUdfData[0].getUdf152_Flag() && null != udfData[0].getUdf152_Value() && null != actualUdfData[0].getUdf152_Value())
					udfData[0].setUdf152_Flag(actualUdfData[0].getUdf152_Flag());
				else if(null==actualUdfData[0].getUdf152_Flag() && null!=actualUdfData[0].getUdf152_Value() && null != udfData[0].getUdf152_Value() && !udfData[0].getUdf152_Value().equals(actualUdfData[0].getUdf152_Value()))
					udfData[0].setUdf152_Flag("M");
				else if(null==actualUdfData[0].getUdf152_Flag() && null==actualUdfData[0].getUdf152_Value() && null!=udfData[0].getUdf152_Value())
					udfData[0].setUdf152_Flag("A");
					else if(null!=actualUdfData[0].getUdf152_Flag())
					udfData[0].setUdf152_Flag(actualUdfData[0].getUdf152_Flag());
					
					
					if(null!=actualUdfData[0].getUdf153_Flag() && null != udfData[0].getUdf153_Value() && null != actualUdfData[0].getUdf153_Value())
					udfData[0].setUdf153_Flag(actualUdfData[0].getUdf153_Flag());
				else if(null==actualUdfData[0].getUdf153_Flag() && null!=actualUdfData[0].getUdf153_Value() && null != udfData[0].getUdf153_Value() && !udfData[0].getUdf153_Value().equals(actualUdfData[0].getUdf153_Value()))
					udfData[0].setUdf153_Flag("M");
				else if(null==actualUdfData[0].getUdf153_Flag() && null==actualUdfData[0].getUdf153_Value() && null!=udfData[0].getUdf153_Value())
					udfData[0].setUdf153_Flag("A");
					else if(null!=actualUdfData[0].getUdf153_Flag())
					udfData[0].setUdf153_Flag(actualUdfData[0].getUdf153_Flag());
					
					
					if(null!=actualUdfData[0].getUdf154_Flag() && null != udfData[0].getUdf154_Value() && null != actualUdfData[0].getUdf154_Value())
					udfData[0].setUdf154_Flag(actualUdfData[0].getUdf154_Flag());
				else if(null==actualUdfData[0].getUdf154_Flag() && null!=actualUdfData[0].getUdf154_Value() && null != udfData[0].getUdf154_Value() && !udfData[0].getUdf154_Value().equals(actualUdfData[0].getUdf154_Value()))
					udfData[0].setUdf154_Flag("M");
				else if(null==actualUdfData[0].getUdf154_Flag() && null==actualUdfData[0].getUdf154_Value() && null!=udfData[0].getUdf154_Value())
					udfData[0].setUdf154_Flag("A");
					else if(null!=actualUdfData[0].getUdf154_Flag())
					udfData[0].setUdf154_Flag(actualUdfData[0].getUdf154_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf155_Flag() && null != udfData[0].getUdf155_Value() && null != actualUdfData[0].getUdf155_Value())
					udfData[0].setUdf155_Flag(actualUdfData[0].getUdf155_Flag());
				else if(null==actualUdfData[0].getUdf155_Flag() && null!=actualUdfData[0].getUdf155_Value() && null != udfData[0].getUdf155_Value() && !udfData[0].getUdf155_Value().equals(actualUdfData[0].getUdf155_Value()))
					udfData[0].setUdf155_Flag("M");
				else if(null==actualUdfData[0].getUdf155_Flag() && null==actualUdfData[0].getUdf155_Value() && null!=udfData[0].getUdf155_Value())
					udfData[0].setUdf155_Flag("A");
					else if(null!=actualUdfData[0].getUdf155_Flag())
					udfData[0].setUdf155_Flag(actualUdfData[0].getUdf155_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf156_Flag() && null != udfData[0].getUdf156_Value() && null != actualUdfData[0].getUdf156_Value())
					udfData[0].setUdf156_Flag(actualUdfData[0].getUdf156_Flag());
				else if(null==actualUdfData[0].getUdf156_Flag() && null!=actualUdfData[0].getUdf156_Value() && null != udfData[0].getUdf156_Value() && !udfData[0].getUdf156_Value().equals(actualUdfData[0].getUdf156_Value()))
					udfData[0].setUdf156_Flag("M");
				else if(null==actualUdfData[0].getUdf156_Flag() && null==actualUdfData[0].getUdf156_Value() && null!=udfData[0].getUdf156_Value())
					udfData[0].setUdf156_Flag("A");
					else if(null!=actualUdfData[0].getUdf156_Flag())
					udfData[0].setUdf156_Flag(actualUdfData[0].getUdf156_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf157_Flag() && null != udfData[0].getUdf157_Value() && null != actualUdfData[0].getUdf157_Value() )
					udfData[0].setUdf157_Flag(actualUdfData[0].getUdf157_Flag());
				else if(null==actualUdfData[0].getUdf157_Flag() && null!=actualUdfData[0].getUdf157_Value() && null != udfData[0].getUdf157_Value() && !udfData[0].getUdf157_Value().equals(actualUdfData[0].getUdf157_Value()))
					udfData[0].setUdf157_Flag("M");
				else if(null==actualUdfData[0].getUdf157_Flag() && null==actualUdfData[0].getUdf157_Value() && null!=udfData[0].getUdf157_Value())
					udfData[0].setUdf157_Flag("A");
					else if(null!=actualUdfData[0].getUdf157_Flag())
					udfData[0].setUdf157_Flag(actualUdfData[0].getUdf157_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf158_Flag() && null != udfData[0].getUdf158_Value() && null != actualUdfData[0].getUdf158_Value() )
					udfData[0].setUdf158_Flag(actualUdfData[0].getUdf158_Flag());
				else if(null==actualUdfData[0].getUdf158_Flag() && null!=actualUdfData[0].getUdf158_Value() && null != udfData[0].getUdf158_Value() && !udfData[0].getUdf158_Value().equals(actualUdfData[0].getUdf158_Value()))
					udfData[0].setUdf158_Flag("M");
				else if(null==actualUdfData[0].getUdf158_Flag() && null==actualUdfData[0].getUdf158_Value() && null!=udfData[0].getUdf158_Value())
					udfData[0].setUdf158_Flag("A");
					else if(null!=actualUdfData[0].getUdf158_Flag())
					udfData[0].setUdf158_Flag(actualUdfData[0].getUdf158_Flag());
					
					
					if(null!=actualUdfData[0].getUdf159_Flag() && null != udfData[0].getUdf159_Value() && null != actualUdfData[0].getUdf159_Value())
					udfData[0].setUdf159_Flag(actualUdfData[0].getUdf159_Flag());
				else if(null==actualUdfData[0].getUdf159_Flag() && null!=actualUdfData[0].getUdf159_Value() && null != udfData[0].getUdf159_Value() && !udfData[0].getUdf159_Value().equals(actualUdfData[0].getUdf159_Value()))
					udfData[0].setUdf159_Flag("M");
				else if(null==actualUdfData[0].getUdf159_Flag() && null==actualUdfData[0].getUdf159_Value() && null!=udfData[0].getUdf159_Value())
					udfData[0].setUdf159_Flag("A");
					else if(null!=actualUdfData[0].getUdf159_Flag())
					udfData[0].setUdf159_Flag(actualUdfData[0].getUdf159_Flag());
					
				
					if(null!=actualUdfData[0].getUdf160_Flag() && null != udfData[0].getUdf160_Value() && null != actualUdfData[0].getUdf160_Value())
						udfData[0].setUdf160_Flag(actualUdfData[0].getUdf160_Flag());
					else if(null==actualUdfData[0].getUdf160_Flag() && null!=actualUdfData[0].getUdf160_Value() && null != udfData[0].getUdf160_Value() && !udfData[0].getUdf160_Value().equals(actualUdfData[0].getUdf160_Value()))
						udfData[0].setUdf160_Flag("M");
					else if(null==actualUdfData[0].getUdf160_Flag() && null==actualUdfData[0].getUdf160_Value() && null!=udfData[0].getUdf160_Value())
						udfData[0].setUdf160_Flag("A");
					else if(null!=actualUdfData[0].getUdf160_Flag())
						udfData[0].setUdf160_Flag(actualUdfData[0].getUdf160_Flag());




if(null!=actualUdfData[0].getUdf161_Flag() && null != udfData[0].getUdf161_Value() && null != actualUdfData[0].getUdf161_Value())
					udfData[0].setUdf161_Flag(actualUdfData[0].getUdf161_Flag());
				else if(null==actualUdfData[0].getUdf161_Flag() && null!=actualUdfData[0].getUdf161_Value() && null != udfData[0].getUdf161_Value() && !udfData[0].getUdf161_Value().equals(actualUdfData[0].getUdf161_Value()))
					udfData[0].setUdf161_Flag("M");
				else if(null==actualUdfData[0].getUdf161_Flag() && null==actualUdfData[0].getUdf161_Value() && null!=udfData[0].getUdf161_Value())
					udfData[0].setUdf161_Flag("A");
				else if(null!=actualUdfData[0].getUdf161_Flag())
					udfData[0].setUdf161_Flag(actualUdfData[0].getUdf161_Flag());
					
					if(null!=actualUdfData[0].getUdf162_Flag() && null != udfData[0].getUdf162_Value() && null != actualUdfData[0].getUdf162_Value())
					udfData[0].setUdf162_Flag(actualUdfData[0].getUdf162_Flag());
				else if(null==actualUdfData[0].getUdf162_Flag() && null!=actualUdfData[0].getUdf162_Value() && null != udfData[0].getUdf162_Value() && !udfData[0].getUdf162_Value().equals(actualUdfData[0].getUdf162_Value()))
					udfData[0].setUdf162_Flag("M");
				else if(null==actualUdfData[0].getUdf162_Flag() && null==actualUdfData[0].getUdf162_Value() && null!=udfData[0].getUdf162_Value())
					udfData[0].setUdf162_Flag("A");
					else if(null!=actualUdfData[0].getUdf162_Flag())
					udfData[0].setUdf162_Flag(actualUdfData[0].getUdf162_Flag());
					
					
					if(null!=actualUdfData[0].getUdf163_Flag() && null != udfData[0].getUdf163_Value() && null != actualUdfData[0].getUdf163_Value())
					udfData[0].setUdf163_Flag(actualUdfData[0].getUdf163_Flag());
				else if(null==actualUdfData[0].getUdf163_Flag() && null!=actualUdfData[0].getUdf163_Value() && null != udfData[0].getUdf163_Value() && !udfData[0].getUdf163_Value().equals(actualUdfData[0].getUdf163_Value()))
					udfData[0].setUdf163_Flag("M");
				else if(null==actualUdfData[0].getUdf163_Flag() && null==actualUdfData[0].getUdf163_Value() && null!=udfData[0].getUdf163_Value())
					udfData[0].setUdf163_Flag("A");
					else if(null!=actualUdfData[0].getUdf163_Flag())
					udfData[0].setUdf163_Flag(actualUdfData[0].getUdf163_Flag());
					
					
					if(null!=actualUdfData[0].getUdf164_Flag() && null != udfData[0].getUdf164_Value() && null != actualUdfData[0].getUdf164_Value())
					udfData[0].setUdf164_Flag(actualUdfData[0].getUdf164_Flag());
				else if(null==actualUdfData[0].getUdf164_Flag() && null!=actualUdfData[0].getUdf164_Value() && null != udfData[0].getUdf164_Value() && !udfData[0].getUdf164_Value().equals(actualUdfData[0].getUdf164_Value()))
					udfData[0].setUdf164_Flag("M");
				else if(null==actualUdfData[0].getUdf164_Flag() && null==actualUdfData[0].getUdf164_Value() && null!=udfData[0].getUdf164_Value())
					udfData[0].setUdf164_Flag("A");
					else if(null!=actualUdfData[0].getUdf164_Flag())
					udfData[0].setUdf164_Flag(actualUdfData[0].getUdf164_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf165_Flag() && null != udfData[0].getUdf165_Value() && null != actualUdfData[0].getUdf165_Value())
					udfData[0].setUdf165_Flag(actualUdfData[0].getUdf165_Flag());
				else if(null==actualUdfData[0].getUdf165_Flag() && null!=actualUdfData[0].getUdf165_Value() && null != udfData[0].getUdf165_Value() && !udfData[0].getUdf165_Value().equals(actualUdfData[0].getUdf165_Value()))
					udfData[0].setUdf165_Flag("M");
				else if(null==actualUdfData[0].getUdf165_Flag() && null==actualUdfData[0].getUdf165_Value() && null!=udfData[0].getUdf165_Value())
					udfData[0].setUdf165_Flag("A");
					else if(null!=actualUdfData[0].getUdf165_Flag())
					udfData[0].setUdf165_Flag(actualUdfData[0].getUdf165_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf166_Flag() && null != udfData[0].getUdf166_Value() && null != actualUdfData[0].getUdf166_Value())
					udfData[0].setUdf166_Flag(actualUdfData[0].getUdf166_Flag());
				else if(null==actualUdfData[0].getUdf166_Flag() && null!=actualUdfData[0].getUdf166_Value() && null != udfData[0].getUdf166_Value() && !udfData[0].getUdf166_Value().equals(actualUdfData[0].getUdf166_Value()))
					udfData[0].setUdf166_Flag("M");
				else if(null==actualUdfData[0].getUdf166_Flag() && null==actualUdfData[0].getUdf166_Value() && null!=udfData[0].getUdf166_Value())
					udfData[0].setUdf166_Flag("A");
					else if(null!=actualUdfData[0].getUdf166_Flag())
					udfData[0].setUdf166_Flag(actualUdfData[0].getUdf166_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf167_Flag() && null != udfData[0].getUdf167_Value() && null != actualUdfData[0].getUdf167_Value() )
					udfData[0].setUdf167_Flag(actualUdfData[0].getUdf167_Flag());
				else if(null==actualUdfData[0].getUdf167_Flag() && null!=actualUdfData[0].getUdf167_Value() && null != udfData[0].getUdf167_Value() && !udfData[0].getUdf167_Value().equals(actualUdfData[0].getUdf167_Value()))
					udfData[0].setUdf167_Flag("M");
				else if(null==actualUdfData[0].getUdf167_Flag() && null==actualUdfData[0].getUdf167_Value() && null!=udfData[0].getUdf167_Value())
					udfData[0].setUdf167_Flag("A");
					else if(null!=actualUdfData[0].getUdf167_Flag())
					udfData[0].setUdf167_Flag(actualUdfData[0].getUdf167_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf168_Flag() && null != udfData[0].getUdf168_Value() && null != actualUdfData[0].getUdf168_Value() )
					udfData[0].setUdf168_Flag(actualUdfData[0].getUdf168_Flag());
				else if(null==actualUdfData[0].getUdf168_Flag() && null!=actualUdfData[0].getUdf168_Value() && null != udfData[0].getUdf168_Value() && !udfData[0].getUdf168_Value().equals(actualUdfData[0].getUdf168_Value()))
					udfData[0].setUdf168_Flag("M");
				else if(null==actualUdfData[0].getUdf168_Flag() && null==actualUdfData[0].getUdf168_Value() && null!=udfData[0].getUdf168_Value())
					udfData[0].setUdf168_Flag("A");
					else if(null!=actualUdfData[0].getUdf168_Flag())
					udfData[0].setUdf168_Flag(actualUdfData[0].getUdf168_Flag());
					
					
					if(null!=actualUdfData[0].getUdf169_Flag() && null != udfData[0].getUdf169_Value() && null != actualUdfData[0].getUdf169_Value())
					udfData[0].setUdf169_Flag(actualUdfData[0].getUdf169_Flag());
				else if(null==actualUdfData[0].getUdf169_Flag() && null!=actualUdfData[0].getUdf169_Value() && null != udfData[0].getUdf169_Value() && !udfData[0].getUdf169_Value().equals(actualUdfData[0].getUdf169_Value()))
					udfData[0].setUdf169_Flag("M");
				else if(null==actualUdfData[0].getUdf169_Flag() && null==actualUdfData[0].getUdf169_Value() && null!=udfData[0].getUdf169_Value())
					udfData[0].setUdf169_Flag("A");
					else if(null!=actualUdfData[0].getUdf169_Flag())
					udfData[0].setUdf169_Flag(actualUdfData[0].getUdf169_Flag());
					
					if(null!=actualUdfData[0].getUdf170_Flag() && null != udfData[0].getUdf170_Value() && null != actualUdfData[0].getUdf170_Value())
						udfData[0].setUdf170_Flag(actualUdfData[0].getUdf170_Flag());
					else if(null==actualUdfData[0].getUdf170_Flag() && null!=actualUdfData[0].getUdf170_Value() && null != udfData[0].getUdf170_Value() && !udfData[0].getUdf170_Value().equals(actualUdfData[0].getUdf170_Value()))
						udfData[0].setUdf170_Flag("M");
					else if(null==actualUdfData[0].getUdf170_Flag() && null==actualUdfData[0].getUdf170_Value() && null!=udfData[0].getUdf170_Value())
						udfData[0].setUdf170_Flag("A");
					else if(null!=actualUdfData[0].getUdf170_Flag())
						udfData[0].setUdf170_Flag(actualUdfData[0].getUdf170_Flag());




if(null!=actualUdfData[0].getUdf171_Flag() && null != udfData[0].getUdf171_Value() && null != actualUdfData[0].getUdf171_Value())
					udfData[0].setUdf171_Flag(actualUdfData[0].getUdf171_Flag());
				else if(null==actualUdfData[0].getUdf171_Flag() && null!=actualUdfData[0].getUdf171_Value() && null != udfData[0].getUdf171_Value() && !udfData[0].getUdf171_Value().equals(actualUdfData[0].getUdf171_Value()))
					udfData[0].setUdf171_Flag("M");
				else if(null==actualUdfData[0].getUdf171_Flag() && null==actualUdfData[0].getUdf171_Value() && null!=udfData[0].getUdf171_Value())
					udfData[0].setUdf171_Flag("A");
				else if(null!=actualUdfData[0].getUdf171_Flag())
					udfData[0].setUdf171_Flag(actualUdfData[0].getUdf171_Flag());
					
					if(null!=actualUdfData[0].getUdf172_Flag() && null != udfData[0].getUdf172_Value() && null != actualUdfData[0].getUdf172_Value())
					udfData[0].setUdf172_Flag(actualUdfData[0].getUdf172_Flag());
				else if(null==actualUdfData[0].getUdf172_Flag() && null!=actualUdfData[0].getUdf172_Value() && null != udfData[0].getUdf172_Value() && !udfData[0].getUdf172_Value().equals(actualUdfData[0].getUdf172_Value()))
					udfData[0].setUdf172_Flag("M");
				else if(null==actualUdfData[0].getUdf172_Flag() && null==actualUdfData[0].getUdf172_Value() && null!=udfData[0].getUdf172_Value())
					udfData[0].setUdf172_Flag("A");
					else if(null!=actualUdfData[0].getUdf172_Flag())
					udfData[0].setUdf172_Flag(actualUdfData[0].getUdf172_Flag());
					
					
					if(null!=actualUdfData[0].getUdf173_Flag() && null != udfData[0].getUdf173_Value() && null != actualUdfData[0].getUdf173_Value())
					udfData[0].setUdf173_Flag(actualUdfData[0].getUdf173_Flag());
				else if(null==actualUdfData[0].getUdf173_Flag() && null!=actualUdfData[0].getUdf173_Value() && null != udfData[0].getUdf173_Value() && !udfData[0].getUdf173_Value().equals(actualUdfData[0].getUdf173_Value()))
					udfData[0].setUdf173_Flag("M");
				else if(null==actualUdfData[0].getUdf173_Flag() && null==actualUdfData[0].getUdf173_Value() && null!=udfData[0].getUdf173_Value())
					udfData[0].setUdf173_Flag("A");
					else if(null!=actualUdfData[0].getUdf173_Flag())
					udfData[0].setUdf173_Flag(actualUdfData[0].getUdf173_Flag());
					
					
					if(null!=actualUdfData[0].getUdf174_Flag() && null != udfData[0].getUdf174_Value() && null != actualUdfData[0].getUdf174_Value())
					udfData[0].setUdf174_Flag(actualUdfData[0].getUdf174_Flag());
				else if(null==actualUdfData[0].getUdf174_Flag() && null!=actualUdfData[0].getUdf174_Value() && null != udfData[0].getUdf174_Value() && !udfData[0].getUdf174_Value().equals(actualUdfData[0].getUdf174_Value()))
					udfData[0].setUdf174_Flag("M");
				else if(null==actualUdfData[0].getUdf174_Flag() && null==actualUdfData[0].getUdf174_Value() && null!=udfData[0].getUdf174_Value())
					udfData[0].setUdf174_Flag("A");
					else if(null!=actualUdfData[0].getUdf174_Flag())
					udfData[0].setUdf174_Flag(actualUdfData[0].getUdf174_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf175_Flag() && null != udfData[0].getUdf175_Value() && null != actualUdfData[0].getUdf175_Value())
					udfData[0].setUdf175_Flag(actualUdfData[0].getUdf175_Flag());
				else if(null==actualUdfData[0].getUdf175_Flag() && null!=actualUdfData[0].getUdf175_Value() && null != udfData[0].getUdf175_Value() && !udfData[0].getUdf175_Value().equals(actualUdfData[0].getUdf175_Value()))
					udfData[0].setUdf175_Flag("M");
				else if(null==actualUdfData[0].getUdf175_Flag() && null==actualUdfData[0].getUdf175_Value() && null!=udfData[0].getUdf175_Value())
					udfData[0].setUdf175_Flag("A");
					else if(null!=actualUdfData[0].getUdf175_Flag())
					udfData[0].setUdf175_Flag(actualUdfData[0].getUdf175_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf176_Flag() && null != udfData[0].getUdf176_Value() && null != actualUdfData[0].getUdf176_Value())
					udfData[0].setUdf176_Flag(actualUdfData[0].getUdf176_Flag());
				else if(null==actualUdfData[0].getUdf176_Flag() && null!=actualUdfData[0].getUdf176_Value() && null != udfData[0].getUdf176_Value() && !udfData[0].getUdf176_Value().equals(actualUdfData[0].getUdf176_Value()))
					udfData[0].setUdf176_Flag("M");
				else if(null==actualUdfData[0].getUdf176_Flag() && null==actualUdfData[0].getUdf176_Value() && null!=udfData[0].getUdf176_Value())
					udfData[0].setUdf176_Flag("A");
					else if(null!=actualUdfData[0].getUdf176_Flag())
					udfData[0].setUdf176_Flag(actualUdfData[0].getUdf176_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf177_Flag() && null != udfData[0].getUdf177_Value() && null != actualUdfData[0].getUdf177_Value() )
					udfData[0].setUdf177_Flag(actualUdfData[0].getUdf177_Flag());
				else if(null==actualUdfData[0].getUdf177_Flag() && null!=actualUdfData[0].getUdf177_Value() && null != udfData[0].getUdf177_Value() && !udfData[0].getUdf177_Value().equals(actualUdfData[0].getUdf177_Value()))
					udfData[0].setUdf177_Flag("M");
				else if(null==actualUdfData[0].getUdf177_Flag() && null==actualUdfData[0].getUdf177_Value() && null!=udfData[0].getUdf177_Value())
					udfData[0].setUdf177_Flag("A");
					else if(null!=actualUdfData[0].getUdf177_Flag())
					udfData[0].setUdf177_Flag(actualUdfData[0].getUdf177_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf178_Flag() && null != udfData[0].getUdf178_Value() && null != actualUdfData[0].getUdf178_Value() )
					udfData[0].setUdf178_Flag(actualUdfData[0].getUdf178_Flag());
				else if(null==actualUdfData[0].getUdf178_Flag() && null!=actualUdfData[0].getUdf178_Value() && null != udfData[0].getUdf178_Value() && !udfData[0].getUdf178_Value().equals(actualUdfData[0].getUdf178_Value()))
					udfData[0].setUdf178_Flag("M");
				else if(null==actualUdfData[0].getUdf178_Flag() && null==actualUdfData[0].getUdf178_Value() && null!=udfData[0].getUdf178_Value())
					udfData[0].setUdf178_Flag("A");
					else if(null!=actualUdfData[0].getUdf178_Flag())
					udfData[0].setUdf178_Flag(actualUdfData[0].getUdf178_Flag());
					
					
					if(null!=actualUdfData[0].getUdf179_Flag() && null != udfData[0].getUdf179_Value() && null != actualUdfData[0].getUdf179_Value())
					udfData[0].setUdf179_Flag(actualUdfData[0].getUdf179_Flag());
				else if(null==actualUdfData[0].getUdf179_Flag() && null!=actualUdfData[0].getUdf179_Value() && null != udfData[0].getUdf179_Value() && !udfData[0].getUdf179_Value().equals(actualUdfData[0].getUdf179_Value()))
					udfData[0].setUdf179_Flag("M");
				else if(null==actualUdfData[0].getUdf179_Flag() && null==actualUdfData[0].getUdf179_Value() && null!=udfData[0].getUdf179_Value())
					udfData[0].setUdf179_Flag("A");
					else if(null!=actualUdfData[0].getUdf179_Flag())
					udfData[0].setUdf179_Flag(actualUdfData[0].getUdf179_Flag());
					
				
					if(null!=actualUdfData[0].getUdf180_Flag() && null != udfData[0].getUdf180_Value() && null != actualUdfData[0].getUdf180_Value())
						udfData[0].setUdf180_Flag(actualUdfData[0].getUdf180_Flag());
					else if(null==actualUdfData[0].getUdf180_Flag() && null!=actualUdfData[0].getUdf180_Value() && null != udfData[0].getUdf180_Value() && !udfData[0].getUdf180_Value().equals(actualUdfData[0].getUdf180_Value()))
						udfData[0].setUdf180_Flag("M");
					else if(null==actualUdfData[0].getUdf180_Flag() && null==actualUdfData[0].getUdf180_Value() && null!=udfData[0].getUdf180_Value())
						udfData[0].setUdf180_Flag("A");
					else if(null!=actualUdfData[0].getUdf180_Flag())
						udfData[0].setUdf180_Flag(actualUdfData[0].getUdf180_Flag());




if(null!=actualUdfData[0].getUdf181_Flag() && null != udfData[0].getUdf181_Value() && null != actualUdfData[0].getUdf181_Value())
					udfData[0].setUdf181_Flag(actualUdfData[0].getUdf181_Flag());
				else if(null==actualUdfData[0].getUdf181_Flag() && null!=actualUdfData[0].getUdf181_Value() && null != udfData[0].getUdf181_Value() && !udfData[0].getUdf181_Value().equals(actualUdfData[0].getUdf181_Value()))
					udfData[0].setUdf181_Flag("M");
				else if(null==actualUdfData[0].getUdf181_Flag() && null==actualUdfData[0].getUdf181_Value() && null!=udfData[0].getUdf181_Value())
					udfData[0].setUdf181_Flag("A");
				else if(null!=actualUdfData[0].getUdf181_Flag())
					udfData[0].setUdf181_Flag(actualUdfData[0].getUdf181_Flag());
					
					if(null!=actualUdfData[0].getUdf182_Flag() && null != udfData[0].getUdf182_Value() && null != actualUdfData[0].getUdf182_Value())
					udfData[0].setUdf182_Flag(actualUdfData[0].getUdf182_Flag());
				else if(null==actualUdfData[0].getUdf182_Flag() && null!=actualUdfData[0].getUdf182_Value() && null != udfData[0].getUdf182_Value() && !udfData[0].getUdf182_Value().equals(actualUdfData[0].getUdf182_Value()))
					udfData[0].setUdf182_Flag("M");
				else if(null==actualUdfData[0].getUdf182_Flag() && null==actualUdfData[0].getUdf182_Value() && null!=udfData[0].getUdf182_Value())
					udfData[0].setUdf182_Flag("A");
					else if(null!=actualUdfData[0].getUdf182_Flag())
					udfData[0].setUdf182_Flag(actualUdfData[0].getUdf182_Flag());
					
					
					if(null!=actualUdfData[0].getUdf183_Flag() && null != udfData[0].getUdf183_Value() && null != actualUdfData[0].getUdf183_Value())
					udfData[0].setUdf183_Flag(actualUdfData[0].getUdf183_Flag());
				else if(null==actualUdfData[0].getUdf183_Flag() && null!=actualUdfData[0].getUdf183_Value() && null != udfData[0].getUdf183_Value() && !udfData[0].getUdf183_Value().equals(actualUdfData[0].getUdf183_Value()))
					udfData[0].setUdf183_Flag("M");
				else if(null==actualUdfData[0].getUdf183_Flag() && null==actualUdfData[0].getUdf183_Value() && null!=udfData[0].getUdf183_Value())
					udfData[0].setUdf183_Flag("A");
					else if(null!=actualUdfData[0].getUdf183_Flag())
					udfData[0].setUdf183_Flag(actualUdfData[0].getUdf183_Flag());
					
					
					if(null!=actualUdfData[0].getUdf184_Flag() && null != udfData[0].getUdf184_Value() && null != actualUdfData[0].getUdf184_Value())
					udfData[0].setUdf184_Flag(actualUdfData[0].getUdf184_Flag());
				else if(null==actualUdfData[0].getUdf184_Flag() && null!=actualUdfData[0].getUdf184_Value() && null != udfData[0].getUdf184_Value() && !udfData[0].getUdf184_Value().equals(actualUdfData[0].getUdf184_Value()))
					udfData[0].setUdf184_Flag("M");
				else if(null==actualUdfData[0].getUdf184_Flag() && null==actualUdfData[0].getUdf184_Value() && null!=udfData[0].getUdf184_Value())
					udfData[0].setUdf184_Flag("A");
					else if(null!=actualUdfData[0].getUdf184_Flag())
					udfData[0].setUdf184_Flag(actualUdfData[0].getUdf184_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf185_Flag() && null != udfData[0].getUdf185_Value() && null != actualUdfData[0].getUdf185_Value())
					udfData[0].setUdf185_Flag(actualUdfData[0].getUdf185_Flag());
				else if(null==actualUdfData[0].getUdf185_Flag() && null!=actualUdfData[0].getUdf185_Value() && null != udfData[0].getUdf185_Value() && !udfData[0].getUdf185_Value().equals(actualUdfData[0].getUdf185_Value()))
					udfData[0].setUdf185_Flag("M");
				else if(null==actualUdfData[0].getUdf185_Flag() && null==actualUdfData[0].getUdf185_Value() && null!=udfData[0].getUdf185_Value())
					udfData[0].setUdf185_Flag("A");
					else if(null!=actualUdfData[0].getUdf185_Flag())
					udfData[0].setUdf185_Flag(actualUdfData[0].getUdf185_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf186_Flag() && null != udfData[0].getUdf186_Value() && null != actualUdfData[0].getUdf186_Value())
					udfData[0].setUdf186_Flag(actualUdfData[0].getUdf186_Flag());
				else if(null==actualUdfData[0].getUdf186_Flag() && null!=actualUdfData[0].getUdf186_Value() && null != udfData[0].getUdf186_Value() && !udfData[0].getUdf186_Value().equals(actualUdfData[0].getUdf186_Value()))
					udfData[0].setUdf186_Flag("M");
				else if(null==actualUdfData[0].getUdf186_Flag() && null==actualUdfData[0].getUdf186_Value() && null!=udfData[0].getUdf186_Value())
					udfData[0].setUdf186_Flag("A");
					else if(null!=actualUdfData[0].getUdf186_Flag())
					udfData[0].setUdf186_Flag(actualUdfData[0].getUdf186_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf187_Flag() && null != udfData[0].getUdf187_Value() && null != actualUdfData[0].getUdf187_Value() )
					udfData[0].setUdf187_Flag(actualUdfData[0].getUdf187_Flag());
				else if(null==actualUdfData[0].getUdf187_Flag() && null!=actualUdfData[0].getUdf187_Value() && null != udfData[0].getUdf187_Value() && !udfData[0].getUdf187_Value().equals(actualUdfData[0].getUdf187_Value()))
					udfData[0].setUdf187_Flag("M");
				else if(null==actualUdfData[0].getUdf187_Flag() && null==actualUdfData[0].getUdf187_Value() && null!=udfData[0].getUdf187_Value())
					udfData[0].setUdf187_Flag("A");
					else if(null!=actualUdfData[0].getUdf187_Flag())
					udfData[0].setUdf187_Flag(actualUdfData[0].getUdf187_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf188_Flag() && null != udfData[0].getUdf188_Value() && null != actualUdfData[0].getUdf188_Value() )
					udfData[0].setUdf188_Flag(actualUdfData[0].getUdf188_Flag());
				else if(null==actualUdfData[0].getUdf188_Flag() && null!=actualUdfData[0].getUdf188_Value() && null != udfData[0].getUdf188_Value() && !udfData[0].getUdf188_Value().equals(actualUdfData[0].getUdf188_Value()))
					udfData[0].setUdf188_Flag("M");
				else if(null==actualUdfData[0].getUdf188_Flag() && null==actualUdfData[0].getUdf188_Value() && null!=udfData[0].getUdf188_Value())
					udfData[0].setUdf188_Flag("A");
					else if(null!=actualUdfData[0].getUdf188_Flag())
					udfData[0].setUdf188_Flag(actualUdfData[0].getUdf188_Flag());
					
					
					if(null!=actualUdfData[0].getUdf189_Flag() && null != udfData[0].getUdf189_Value() && null != actualUdfData[0].getUdf189_Value())
					udfData[0].setUdf189_Flag(actualUdfData[0].getUdf189_Flag());
				else if(null==actualUdfData[0].getUdf189_Flag() && null!=actualUdfData[0].getUdf189_Value() && null != udfData[0].getUdf189_Value() && !udfData[0].getUdf189_Value().equals(actualUdfData[0].getUdf189_Value()))
					udfData[0].setUdf189_Flag("M");
				else if(null==actualUdfData[0].getUdf189_Flag() && null==actualUdfData[0].getUdf189_Value() && null!=udfData[0].getUdf189_Value())
					udfData[0].setUdf189_Flag("A");
					else if(null!=actualUdfData[0].getUdf189_Flag())
					udfData[0].setUdf189_Flag(actualUdfData[0].getUdf189_Flag());
					
					if(null!=actualUdfData[0].getUdf190_Flag() && null != udfData[0].getUdf190_Value() && null != actualUdfData[0].getUdf190_Value())
						udfData[0].setUdf190_Flag(actualUdfData[0].getUdf190_Flag());
					else if(null==actualUdfData[0].getUdf190_Flag() && null!=actualUdfData[0].getUdf190_Value() && null != udfData[0].getUdf190_Value() && !udfData[0].getUdf190_Value().equals(actualUdfData[0].getUdf190_Value()))
						udfData[0].setUdf190_Flag("M");
					else if(null==actualUdfData[0].getUdf190_Flag() && null==actualUdfData[0].getUdf190_Value() && null!=udfData[0].getUdf190_Value())
						udfData[0].setUdf190_Flag("A");
					else if(null!=actualUdfData[0].getUdf190_Flag())
						udfData[0].setUdf190_Flag(actualUdfData[0].getUdf190_Flag());




               if(null!=actualUdfData[0].getUdf191_Flag() && null != udfData[0].getUdf191_Value() && null != actualUdfData[0].getUdf191_Value())
					udfData[0].setUdf191_Flag(actualUdfData[0].getUdf191_Flag());
				else if(null==actualUdfData[0].getUdf191_Flag() && null!=actualUdfData[0].getUdf191_Value() && null != udfData[0].getUdf191_Value() && !udfData[0].getUdf191_Value().equals(actualUdfData[0].getUdf191_Value()))
					udfData[0].setUdf191_Flag("M");
				else if(null==actualUdfData[0].getUdf191_Flag() && null==actualUdfData[0].getUdf191_Value() && null!=udfData[0].getUdf191_Value())
					udfData[0].setUdf191_Flag("A");
				else if(null!=actualUdfData[0].getUdf191_Flag())
					udfData[0].setUdf191_Flag(actualUdfData[0].getUdf191_Flag());
					
					if(null!=actualUdfData[0].getUdf192_Flag() && null != udfData[0].getUdf192_Value() && null != actualUdfData[0].getUdf192_Value())
					udfData[0].setUdf192_Flag(actualUdfData[0].getUdf192_Flag());
				else if(null==actualUdfData[0].getUdf192_Flag() && null!=actualUdfData[0].getUdf192_Value() && null != udfData[0].getUdf192_Value() && !udfData[0].getUdf192_Value().equals(actualUdfData[0].getUdf192_Value()))
					udfData[0].setUdf192_Flag("M");
				else if(null==actualUdfData[0].getUdf192_Flag() && null==actualUdfData[0].getUdf192_Value() && null!=udfData[0].getUdf192_Value())
					udfData[0].setUdf192_Flag("A");
					else if(null!=actualUdfData[0].getUdf192_Flag())
					udfData[0].setUdf192_Flag(actualUdfData[0].getUdf192_Flag());
					
					
					if(null!=actualUdfData[0].getUdf193_Flag() && null != udfData[0].getUdf193_Value() && null != actualUdfData[0].getUdf193_Value())
					udfData[0].setUdf193_Flag(actualUdfData[0].getUdf193_Flag());
				else if(null==actualUdfData[0].getUdf193_Flag() && null!=actualUdfData[0].getUdf193_Value() && null != udfData[0].getUdf193_Value() && !udfData[0].getUdf193_Value().equals(actualUdfData[0].getUdf193_Value()))
					udfData[0].setUdf193_Flag("M");
				else if(null==actualUdfData[0].getUdf193_Flag() && null==actualUdfData[0].getUdf193_Value() && null!=udfData[0].getUdf193_Value())
					udfData[0].setUdf193_Flag("A");
					else if(null!=actualUdfData[0].getUdf193_Flag())
					udfData[0].setUdf193_Flag(actualUdfData[0].getUdf193_Flag());
					
					
					if(null!=actualUdfData[0].getUdf194_Flag() && null != udfData[0].getUdf194_Value() && null != actualUdfData[0].getUdf194_Value())
					udfData[0].setUdf194_Flag(actualUdfData[0].getUdf194_Flag());
				else if(null==actualUdfData[0].getUdf194_Flag() && null!=actualUdfData[0].getUdf194_Value() && null != udfData[0].getUdf194_Value() && !udfData[0].getUdf194_Value().equals(actualUdfData[0].getUdf194_Value()))
					udfData[0].setUdf194_Flag("M");
				else if(null==actualUdfData[0].getUdf194_Flag() && null==actualUdfData[0].getUdf194_Value() && null!=udfData[0].getUdf194_Value())
					udfData[0].setUdf194_Flag("A");
					else if(null!=actualUdfData[0].getUdf194_Flag())
					udfData[0].setUdf194_Flag(actualUdfData[0].getUdf194_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf195_Flag() && null != udfData[0].getUdf195_Value() && null != actualUdfData[0].getUdf195_Value())
					udfData[0].setUdf195_Flag(actualUdfData[0].getUdf195_Flag());
				else if(null==actualUdfData[0].getUdf195_Flag() && null!=actualUdfData[0].getUdf195_Value() && null != udfData[0].getUdf195_Value() && !udfData[0].getUdf195_Value().equals(actualUdfData[0].getUdf195_Value()))
					udfData[0].setUdf195_Flag("M");
				else if(null==actualUdfData[0].getUdf195_Flag() && null==actualUdfData[0].getUdf195_Value() && null!=udfData[0].getUdf195_Value())
					udfData[0].setUdf195_Flag("A");
					else if(null!=actualUdfData[0].getUdf195_Flag())
					udfData[0].setUdf195_Flag(actualUdfData[0].getUdf195_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf196_Flag() && null != udfData[0].getUdf196_Value() && null != actualUdfData[0].getUdf196_Value())
					udfData[0].setUdf196_Flag(actualUdfData[0].getUdf196_Flag());
				else if(null==actualUdfData[0].getUdf196_Flag() && null!=actualUdfData[0].getUdf196_Value() && null != udfData[0].getUdf196_Value() && !udfData[0].getUdf196_Value().equals(actualUdfData[0].getUdf196_Value()))
					udfData[0].setUdf196_Flag("M");
				else if(null==actualUdfData[0].getUdf196_Flag() && null==actualUdfData[0].getUdf196_Value() && null!=udfData[0].getUdf196_Value())
					udfData[0].setUdf196_Flag("A");
					else if(null!=actualUdfData[0].getUdf196_Flag())
					udfData[0].setUdf196_Flag(actualUdfData[0].getUdf196_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf197_Flag() && null != udfData[0].getUdf197_Value() && null != actualUdfData[0].getUdf197_Value() )
					udfData[0].setUdf197_Flag(actualUdfData[0].getUdf197_Flag());
				else if(null==actualUdfData[0].getUdf197_Flag() && null!=actualUdfData[0].getUdf197_Value() && null != udfData[0].getUdf197_Value() && !udfData[0].getUdf197_Value().equals(actualUdfData[0].getUdf197_Value()))
					udfData[0].setUdf197_Flag("M");
				else if(null==actualUdfData[0].getUdf197_Flag() && null==actualUdfData[0].getUdf197_Value() && null!=udfData[0].getUdf197_Value())
					udfData[0].setUdf197_Flag("A");
					else if(null!=actualUdfData[0].getUdf197_Flag())
					udfData[0].setUdf197_Flag(actualUdfData[0].getUdf197_Flag());
					
					
					
					if(null!=actualUdfData[0].getUdf198_Flag() && null != udfData[0].getUdf198_Value() && null != actualUdfData[0].getUdf198_Value() )
					udfData[0].setUdf198_Flag(actualUdfData[0].getUdf198_Flag());
				else if(null==actualUdfData[0].getUdf198_Flag() && null!=actualUdfData[0].getUdf198_Value() && null != udfData[0].getUdf198_Value() && !udfData[0].getUdf198_Value().equals(actualUdfData[0].getUdf198_Value()))
					udfData[0].setUdf198_Flag("M");
				else if(null==actualUdfData[0].getUdf198_Flag() && null==actualUdfData[0].getUdf198_Value() && null!=udfData[0].getUdf198_Value())
					udfData[0].setUdf198_Flag("A");
					else if(null!=actualUdfData[0].getUdf198_Flag())
					udfData[0].setUdf198_Flag(actualUdfData[0].getUdf198_Flag());
					
					
					if(null!=actualUdfData[0].getUdf199_Flag() && null != udfData[0].getUdf199_Value() && null != actualUdfData[0].getUdf199_Value())
					udfData[0].setUdf199_Flag(actualUdfData[0].getUdf199_Flag());
				else if(null==actualUdfData[0].getUdf199_Flag() && null!=actualUdfData[0].getUdf199_Value() && null != udfData[0].getUdf199_Value() && !udfData[0].getUdf199_Value().equals(actualUdfData[0].getUdf199_Value()))
					udfData[0].setUdf199_Flag("M");
				else if(null==actualUdfData[0].getUdf199_Flag() && null==actualUdfData[0].getUdf199_Value() && null!=udfData[0].getUdf199_Value())
					udfData[0].setUdf199_Flag("A");
					else if(null!=actualUdfData[0].getUdf199_Flag())
					udfData[0].setUdf199_Flag(actualUdfData[0].getUdf199_Flag());
					
			
					if(null!=actualUdfData[0].getUdf200_Flag() && null != udfData[0].getUdf200_Value() && null != actualUdfData[0].getUdf200_Value())
						udfData[0].setUdf200_Flag(actualUdfData[0].getUdf200_Flag());
					else if(null==actualUdfData[0].getUdf200_Flag() && null!=actualUdfData[0].getUdf200_Value() && null != udfData[0].getUdf200_Value() && !udfData[0].getUdf200_Value().equals(actualUdfData[0].getUdf200_Value()))
						udfData[0].setUdf200_Flag("M");
					else if(null==actualUdfData[0].getUdf200_Flag() && null==actualUdfData[0].getUdf200_Value() && null!=udfData[0].getUdf200_Value())
						udfData[0].setUdf200_Flag("A");
					else if(null!=actualUdfData[0].getUdf200_Flag())
						udfData[0].setUdf200_Flag(actualUdfData[0].getUdf200_Flag());
					
				
			if(null!=actualUdfData[0].getUdf201_Flag() && null != udfData[0].getUdf201_Value() && null != actualUdfData[0].getUdf201_Value())
				udfData[0].setUdf201_Flag(actualUdfData[0].getUdf201_Flag());
			else if(null==actualUdfData[0].getUdf201_Flag() && null!=actualUdfData[0].getUdf201_Value() && null != udfData[0].getUdf201_Value() && !udfData[0].getUdf201_Value().equals(actualUdfData[0].getUdf201_Value()))
				udfData[0].setUdf201_Flag("M");
			else if(null==actualUdfData[0].getUdf201_Flag() && null==actualUdfData[0].getUdf201_Value() && null!=udfData[0].getUdf201_Value())
				udfData[0].setUdf201_Flag("A");
			else if(null!=actualUdfData[0].getUdf201_Flag())
				udfData[0].setUdf201_Flag(actualUdfData[0].getUdf201_Flag());
			

			if(null!=actualUdfData[0].getUdf202_Flag() && null != udfData[0].getUdf202_Value() && null != actualUdfData[0].getUdf202_Value())
				udfData[0].setUdf202_Flag(actualUdfData[0].getUdf202_Flag());
			else if(null==actualUdfData[0].getUdf202_Flag() && null!=actualUdfData[0].getUdf202_Value() && null != udfData[0].getUdf202_Value() && !udfData[0].getUdf202_Value().equals(actualUdfData[0].getUdf202_Value()))
				udfData[0].setUdf202_Flag("M");
			else if(null==actualUdfData[0].getUdf202_Flag() && null==actualUdfData[0].getUdf202_Value() && null!=udfData[0].getUdf202_Value())
				udfData[0].setUdf202_Flag("A");
			else if(null!=actualUdfData[0].getUdf202_Flag())
				udfData[0].setUdf202_Flag(actualUdfData[0].getUdf202_Flag());
			

			if(null!=actualUdfData[0].getUdf203_Flag() && null != udfData[0].getUdf203_Value() && null != actualUdfData[0].getUdf203_Value())
				udfData[0].setUdf203_Flag(actualUdfData[0].getUdf203_Flag());
			else if(null==actualUdfData[0].getUdf203_Flag() && null!=actualUdfData[0].getUdf203_Value() && null != udfData[0].getUdf203_Value() && !udfData[0].getUdf203_Value().equals(actualUdfData[0].getUdf203_Value()))
				udfData[0].setUdf203_Flag("M");
			else if(null==actualUdfData[0].getUdf203_Flag() && null==actualUdfData[0].getUdf203_Value() && null!=udfData[0].getUdf203_Value())
				udfData[0].setUdf203_Flag("A");
			else if(null!=actualUdfData[0].getUdf203_Flag())
				udfData[0].setUdf203_Flag(actualUdfData[0].getUdf203_Flag());
			

			if(null!=actualUdfData[0].getUdf204_Flag() && null != udfData[0].getUdf204_Value() && null != actualUdfData[0].getUdf204_Value())
				udfData[0].setUdf204_Flag(actualUdfData[0].getUdf204_Flag());
			else if(null==actualUdfData[0].getUdf204_Flag() && null!=actualUdfData[0].getUdf204_Value() && null != udfData[0].getUdf204_Value() && !udfData[0].getUdf204_Value().equals(actualUdfData[0].getUdf204_Value()))
				udfData[0].setUdf204_Flag("M");
			else if(null==actualUdfData[0].getUdf204_Flag() && null==actualUdfData[0].getUdf204_Value() && null!=udfData[0].getUdf204_Value())
				udfData[0].setUdf204_Flag("A");
			else if(null!=actualUdfData[0].getUdf204_Flag())
				udfData[0].setUdf204_Flag(actualUdfData[0].getUdf204_Flag());
			

			if(null!=actualUdfData[0].getUdf205_Flag() && null != udfData[0].getUdf205_Value() && null != actualUdfData[0].getUdf205_Value())
				udfData[0].setUdf205_Flag(actualUdfData[0].getUdf205_Flag());
			else if(null==actualUdfData[0].getUdf205_Flag() && null!=actualUdfData[0].getUdf205_Value() && null != udfData[0].getUdf205_Value() && !udfData[0].getUdf205_Value().equals(actualUdfData[0].getUdf205_Value()))
				udfData[0].setUdf205_Flag("M");
			else if(null==actualUdfData[0].getUdf205_Flag() && null==actualUdfData[0].getUdf205_Value() && null!=udfData[0].getUdf205_Value())
				udfData[0].setUdf205_Flag("A");
			else if(null!=actualUdfData[0].getUdf205_Flag())
				udfData[0].setUdf205_Flag(actualUdfData[0].getUdf205_Flag());
										
			if(null!=actualUdfData[0].getUdf206_Flag() && null != udfData[0].getUdf206_Value() && null != actualUdfData[0].getUdf206_Value())
				udfData[0].setUdf206_Flag(actualUdfData[0].getUdf206_Flag());
			else if(null==actualUdfData[0].getUdf206_Flag() && null!=actualUdfData[0].getUdf206_Value() && null != udfData[0].getUdf206_Value() && !udfData[0].getUdf206_Value().equals(actualUdfData[0].getUdf206_Value()))
				udfData[0].setUdf206_Flag("M");
			else if(null==actualUdfData[0].getUdf206_Flag() && null==actualUdfData[0].getUdf206_Value() && null!=udfData[0].getUdf206_Value())
				udfData[0].setUdf206_Flag("A");
			else if(null!=actualUdfData[0].getUdf206_Flag())
				udfData[0].setUdf206_Flag(actualUdfData[0].getUdf206_Flag());
						
	if(null!=actualUdfData[0].getUdf207_Flag() && null != udfData[0].getUdf207_Value() && null != actualUdfData[0].getUdf207_Value())
				udfData[0].setUdf207_Flag(actualUdfData[0].getUdf207_Flag());
			else if(null==actualUdfData[0].getUdf207_Flag() && null!=actualUdfData[0].getUdf207_Value() && null != udfData[0].getUdf207_Value() && !udfData[0].getUdf207_Value().equals(actualUdfData[0].getUdf207_Value()))
				udfData[0].setUdf207_Flag("M");
			else if(null==actualUdfData[0].getUdf207_Flag() && null==actualUdfData[0].getUdf207_Value() && null!=udfData[0].getUdf207_Value())
				udfData[0].setUdf207_Flag("A");
			else if(null!=actualUdfData[0].getUdf207_Flag())
				udfData[0].setUdf207_Flag(actualUdfData[0].getUdf207_Flag());
						
	if(null!=actualUdfData[0].getUdf208_Flag() && null != udfData[0].getUdf208_Value() && null != actualUdfData[0].getUdf208_Value())
				udfData[0].setUdf208_Flag(actualUdfData[0].getUdf208_Flag());
			else if(null==actualUdfData[0].getUdf208_Flag() && null!=actualUdfData[0].getUdf208_Value() && null != udfData[0].getUdf208_Value() && !udfData[0].getUdf208_Value().equals(actualUdfData[0].getUdf208_Value()))
				udfData[0].setUdf208_Flag("M");
			else if(null==actualUdfData[0].getUdf208_Flag() && null==actualUdfData[0].getUdf208_Value() && null!=udfData[0].getUdf208_Value())
				udfData[0].setUdf208_Flag("A");
			else if(null!=actualUdfData[0].getUdf208_Flag())
				udfData[0].setUdf208_Flag(actualUdfData[0].getUdf208_Flag());
						
	if(null!=actualUdfData[0].getUdf209_Flag() && null != udfData[0].getUdf209_Value() && null != actualUdfData[0].getUdf209_Value())
				udfData[0].setUdf209_Flag(actualUdfData[0].getUdf209_Flag());
			else if(null==actualUdfData[0].getUdf209_Flag() && null!=actualUdfData[0].getUdf209_Value() && null != udfData[0].getUdf209_Value() && !udfData[0].getUdf209_Value().equals(actualUdfData[0].getUdf209_Value()))
				udfData[0].setUdf209_Flag("M");
			else if(null==actualUdfData[0].getUdf209_Flag() && null==actualUdfData[0].getUdf209_Value() && null!=udfData[0].getUdf209_Value())
				udfData[0].setUdf209_Flag("A");
			else if(null!=actualUdfData[0].getUdf209_Flag())
				udfData[0].setUdf209_Flag(actualUdfData[0].getUdf209_Flag());
						
	if(null!=actualUdfData[0].getUdf210_Flag() && null != udfData[0].getUdf210_Value() && null != actualUdfData[0].getUdf210_Value())
				udfData[0].setUdf210_Flag(actualUdfData[0].getUdf210_Flag());
			else if(null==actualUdfData[0].getUdf210_Flag() && null!=actualUdfData[0].getUdf210_Value() && null != udfData[0].getUdf210_Value() && !udfData[0].getUdf210_Value().equals(actualUdfData[0].getUdf210_Value()))
				udfData[0].setUdf210_Flag("M");
			else if(null==actualUdfData[0].getUdf210_Flag() && null==actualUdfData[0].getUdf210_Value() && null!=udfData[0].getUdf210_Value())
				udfData[0].setUdf210_Flag("A");
			else if(null!=actualUdfData[0].getUdf210_Flag())
				udfData[0].setUdf210_Flag(actualUdfData[0].getUdf210_Flag());
						
	if(null!=actualUdfData[0].getUdf211_Flag() && null != udfData[0].getUdf211_Value() && null != actualUdfData[0].getUdf211_Value())
				udfData[0].setUdf211_Flag(actualUdfData[0].getUdf211_Flag());
			else if(null==actualUdfData[0].getUdf211_Flag() && null!=actualUdfData[0].getUdf211_Value() && null != udfData[0].getUdf211_Value() && !udfData[0].getUdf211_Value().equals(actualUdfData[0].getUdf211_Value()))
				udfData[0].setUdf211_Flag("M");
			else if(null==actualUdfData[0].getUdf211_Flag() && null==actualUdfData[0].getUdf211_Value() && null!=udfData[0].getUdf211_Value())
				udfData[0].setUdf211_Flag("A");
			else if(null!=actualUdfData[0].getUdf211_Flag())
				udfData[0].setUdf211_Flag(actualUdfData[0].getUdf211_Flag());
						
	if(null!=actualUdfData[0].getUdf212_Flag() && null != udfData[0].getUdf212_Value() && null != actualUdfData[0].getUdf212_Value())
				udfData[0].setUdf212_Flag(actualUdfData[0].getUdf212_Flag());
			else if(null==actualUdfData[0].getUdf212_Flag() && null!=actualUdfData[0].getUdf212_Value() && null != udfData[0].getUdf212_Value() && !udfData[0].getUdf212_Value().equals(actualUdfData[0].getUdf212_Value()))
				udfData[0].setUdf212_Flag("M");
			else if(null==actualUdfData[0].getUdf212_Flag() && null==actualUdfData[0].getUdf212_Value() && null!=udfData[0].getUdf212_Value())
				udfData[0].setUdf212_Flag("A");
			else if(null!=actualUdfData[0].getUdf212_Flag())
				udfData[0].setUdf212_Flag(actualUdfData[0].getUdf212_Flag());
						
	if(null!=actualUdfData[0].getUdf213_Flag() && null != udfData[0].getUdf213_Value() && null != actualUdfData[0].getUdf213_Value())
				udfData[0].setUdf213_Flag(actualUdfData[0].getUdf213_Flag());
			else if(null==actualUdfData[0].getUdf213_Flag() && null!=actualUdfData[0].getUdf213_Value() && null != udfData[0].getUdf213_Value() && !udfData[0].getUdf213_Value().equals(actualUdfData[0].getUdf213_Value()))
				udfData[0].setUdf213_Flag("M");
			else if(null==actualUdfData[0].getUdf213_Flag() && null==actualUdfData[0].getUdf213_Value() && null!=udfData[0].getUdf213_Value())
				udfData[0].setUdf213_Flag("A");
			else if(null!=actualUdfData[0].getUdf213_Flag())
				udfData[0].setUdf213_Flag(actualUdfData[0].getUdf213_Flag());

	if(null!=actualUdfData[0].getUdf214_Flag() && null != udfData[0].getUdf214_Value() && null != actualUdfData[0].getUdf214_Value())
				udfData[0].setUdf214_Flag(actualUdfData[0].getUdf214_Flag());
			else if(null==actualUdfData[0].getUdf214_Flag() && null!=actualUdfData[0].getUdf214_Value() && null != udfData[0].getUdf214_Value() && !udfData[0].getUdf214_Value().equals(actualUdfData[0].getUdf214_Value()))
				udfData[0].setUdf214_Flag("M");
			else if(null==actualUdfData[0].getUdf214_Flag() && null==actualUdfData[0].getUdf214_Value() && null!=udfData[0].getUdf214_Value())
				udfData[0].setUdf214_Flag("A");
			else if(null!=actualUdfData[0].getUdf214_Flag())
				udfData[0].setUdf214_Flag(actualUdfData[0].getUdf214_Flag());
						
	if(null!=actualUdfData[0].getUdf215_Flag() && null != udfData[0].getUdf215_Value() && null != actualUdfData[0].getUdf215_Value())
				udfData[0].setUdf215_Flag(actualUdfData[0].getUdf215_Flag());
			else if(null==actualUdfData[0].getUdf215_Flag() && null!=actualUdfData[0].getUdf215_Value() && null != udfData[0].getUdf215_Value() && !udfData[0].getUdf215_Value().equals(actualUdfData[0].getUdf215_Value()))
				udfData[0].setUdf215_Flag("M");
			else if(null==actualUdfData[0].getUdf215_Flag() && null==actualUdfData[0].getUdf215_Value() && null!=udfData[0].getUdf215_Value())
				udfData[0].setUdf215_Flag("A");
			else if(null!=actualUdfData[0].getUdf215_Flag())
				udfData[0].setUdf215_Flag(actualUdfData[0].getUdf215_Flag());
						
	
			}
		}
		
		private void updateUpdateStatusLine(List<Long> lineIdUpdateStatus, ILimitSysXRef[] refArr, ILimitDAO dao1, String date) {
			
			if(lineIdUpdateStatus.size()>0){
				DefaultLogger.debug(this, "3061 OBJECT DATA ============ inside if(lineIdUpdateStatus.size()>0 ");
				if (null != refArr) {
					for (int i = 0; i < refArr.length; i++) {
						for(int j=0;j<lineIdUpdateStatus.size();j++){
						  long longValue = lineIdUpdateStatus.get(j).longValue();
						if(longValue==refArr[i].getSID()){
						ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
						ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
						DefaultLogger.debug(this, "3069 OBJECT DATA ============ "+xref.getStatus());
						if(ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xref.getStatus())){
							xref.setStatus(ICMSConstant.FCUBS_STATUS_SUCCESS);
							

							xref.setSendToCore("N");
							
							xref.setSegment1Flag("");
							xref.setPrioritySectorFlag("");
							xref.setIsCapitalMarketExposerFlag("");
							xref.setEstateTypeFlag("");
							xref.setCommRealEstateTypeFlag("");
							xref.setUncondiCanclFlag("");
							xref.setBranchAllowedFlag("");
							xref.setProductAllowedFlag("");
							xref.setCurrencyAllowedFlag("");
							xref.setLimitRestrictionFlag("");
							xref.setAction("");
							xref.setCoreStpRejectedReason("");
							
							xref.setUdfDelete("");
							

							//line covenant changes
							ILineCovenant[] lineCovArr = xref.getLineCovenant();
							
							if(lineCovArr != null && lineCovArr.length>0 ) {
								ILineCovenant singleCov = null;
								int singleCovIndex = 0;
								
								for(int l=0;l<xref.getLineCovenant().length; l++) {
									ILineCovenant cov = xref.getLineCovenant()[l];
									if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
										singleCov = cov;
										singleCovIndex = l;
									}
								}
								singleCov.setCountryRestrictionFlag(StringUtils.EMPTY);
								singleCov.setCurrencyRestrictionFlag(StringUtils.EMPTY);
								singleCov.setBankRestrictionFlag(StringUtils.EMPTY);
								singleCov.setDrawerFlag(StringUtils.EMPTY);
								singleCov.setDraweeFlag(StringUtils.EMPTY);
								singleCov.setBeneficiaryFlag(StringUtils.EMPTY);
								singleCov.setGoodsRestrictionFlag(StringUtils.EMPTY);
								singleCov.setRunningAccountFlag(StringUtils.EMPTY);
								
								lineCovArr[singleCovIndex] = singleCov; 
								xref.setLineCovenant(lineCovArr);
							}
							
							ILimitXRefUdf[] xRefUdfData = xref.getXRefUdfData();
							if(null!=xRefUdfData){
								xRefUdfData[0].setUdf1_Flag("");
								xRefUdfData[0].setUdf2_Flag("");
								xRefUdfData[0].setUdf3_Flag("");
								xRefUdfData[0].setUdf4_Flag("");
								xRefUdfData[0].setUdf5_Flag("");
								xRefUdfData[0].setUdf6_Flag("");
								xRefUdfData[0].setUdf7_Flag("");
								xRefUdfData[0].setUdf8_Flag("");
								xRefUdfData[0].setUdf9_Flag("");
								
								xRefUdfData[0].setUdf10_Flag("");
								xRefUdfData[0].setUdf11_Flag("");
								xRefUdfData[0].setUdf12_Flag("");
								xRefUdfData[0].setUdf13_Flag("");
								xRefUdfData[0].setUdf14_Flag("");
								xRefUdfData[0].setUdf15_Flag("");
								xRefUdfData[0].setUdf16_Flag("");
								xRefUdfData[0].setUdf17_Flag("");
								xRefUdfData[0].setUdf18_Flag("");
								xRefUdfData[0].setUdf19_Flag("");
								
								xRefUdfData[0].setUdf20_Flag("");
								xRefUdfData[0].setUdf21_Flag("");
								xRefUdfData[0].setUdf22_Flag("");
								xRefUdfData[0].setUdf23_Flag("");
								xRefUdfData[0].setUdf24_Flag("");
								xRefUdfData[0].setUdf25_Flag("");
								xRefUdfData[0].setUdf26_Flag("");
								xRefUdfData[0].setUdf27_Flag("");
								xRefUdfData[0].setUdf28_Flag("");
								xRefUdfData[0].setUdf29_Flag("");
								
								xRefUdfData[0].setUdf30_Flag("");
								xRefUdfData[0].setUdf31_Flag("");
								xRefUdfData[0].setUdf32_Flag("");
								xRefUdfData[0].setUdf33_Flag("");
								xRefUdfData[0].setUdf34_Flag("");
								xRefUdfData[0].setUdf35_Flag("");
								xRefUdfData[0].setUdf36_Flag("");
								xRefUdfData[0].setUdf37_Flag("");
								xRefUdfData[0].setUdf38_Flag("");
								xRefUdfData[0].setUdf39_Flag("");
								
								xRefUdfData[0].setUdf40_Flag("");
								xRefUdfData[0].setUdf41_Flag("");
								xRefUdfData[0].setUdf42_Flag("");
								xRefUdfData[0].setUdf43_Flag("");
								xRefUdfData[0].setUdf44_Flag("");
								xRefUdfData[0].setUdf45_Flag("");
								xRefUdfData[0].setUdf46_Flag("");
								xRefUdfData[0].setUdf47_Flag("");
								xRefUdfData[0].setUdf48_Flag("");
								xRefUdfData[0].setUdf49_Flag("");
								
								xRefUdfData[0].setUdf50_Flag("");
								xRefUdfData[0].setUdf51_Flag("");
								xRefUdfData[0].setUdf52_Flag("");
								xRefUdfData[0].setUdf53_Flag("");
								xRefUdfData[0].setUdf54_Flag("");
								xRefUdfData[0].setUdf55_Flag("");
								xRefUdfData[0].setUdf56_Flag("");
								xRefUdfData[0].setUdf57_Flag("");
								xRefUdfData[0].setUdf58_Flag("");
								xRefUdfData[0].setUdf59_Flag("");
								
								xRefUdfData[0].setUdf60_Flag("");
								xRefUdfData[0].setUdf61_Flag("");
								xRefUdfData[0].setUdf62_Flag("");
								xRefUdfData[0].setUdf63_Flag("");
								xRefUdfData[0].setUdf64_Flag("");
								xRefUdfData[0].setUdf65_Flag("");
								xRefUdfData[0].setUdf66_Flag("");
								xRefUdfData[0].setUdf67_Flag("");
								xRefUdfData[0].setUdf68_Flag("");
								xRefUdfData[0].setUdf69_Flag("");
								
								xRefUdfData[0].setUdf70_Flag("");
								xRefUdfData[0].setUdf71_Flag("");
								xRefUdfData[0].setUdf72_Flag("");
								xRefUdfData[0].setUdf73_Flag("");
								xRefUdfData[0].setUdf74_Flag("");
								xRefUdfData[0].setUdf75_Flag("");
								xRefUdfData[0].setUdf76_Flag("");
								xRefUdfData[0].setUdf77_Flag("");
								xRefUdfData[0].setUdf78_Flag("");
								xRefUdfData[0].setUdf79_Flag("");
								
								xRefUdfData[0].setUdf80_Flag("");
								xRefUdfData[0].setUdf81_Flag("");
								xRefUdfData[0].setUdf82_Flag("");
								xRefUdfData[0].setUdf83_Flag("");
								xRefUdfData[0].setUdf84_Flag("");
								xRefUdfData[0].setUdf85_Flag("");
								xRefUdfData[0].setUdf86_Flag("");
								xRefUdfData[0].setUdf87_Flag("");
								xRefUdfData[0].setUdf88_Flag("");
								xRefUdfData[0].setUdf89_Flag("");
								
								xRefUdfData[0].setUdf90_Flag("");
								xRefUdfData[0].setUdf91_Flag("");
								xRefUdfData[0].setUdf92_Flag("");
								xRefUdfData[0].setUdf93_Flag("");
								xRefUdfData[0].setUdf94_Flag("");
								xRefUdfData[0].setUdf95_Flag("");
								xRefUdfData[0].setUdf96_Flag("");
								xRefUdfData[0].setUdf97_Flag("");
								xRefUdfData[0].setUdf98_Flag("");
								xRefUdfData[0].setUdf99_Flag("");
								xRefUdfData[0].setUdf100_Flag("");
								
								xRefUdfData[0].setUdf101_Flag("");
								xRefUdfData[0].setUdf102_Flag("");
								xRefUdfData[0].setUdf103_Flag("");
								xRefUdfData[0].setUdf104_Flag("");
								xRefUdfData[0].setUdf105_Flag("");
								xRefUdfData[0].setUdf106_Flag("");
								xRefUdfData[0].setUdf107_Flag("");
								xRefUdfData[0].setUdf108_Flag("");
								xRefUdfData[0].setUdf109_Flag("");
								xRefUdfData[0].setUdf110_Flag("");
								xRefUdfData[0].setUdf111_Flag("");
								xRefUdfData[0].setUdf112_Flag("");
								xRefUdfData[0].setUdf113_Flag("");
								xRefUdfData[0].setUdf114_Flag("");
								xRefUdfData[0].setUdf115_Flag("");
							
						}
						}else if(ICMSConstant.FCUBS_STATUS_PENDING_REJECTED.equals(xref.getStatus())){
							xref.setStatus(ICMSConstant.FCUBS_STATUS_REJECTED);
							xref.setSendToCore("N");
						}
						
						obj.setCustomerSysXRef(xref);
						refArr[i] = obj;
						}
						}
					}
					}
					}
		}
		
		//UDF- 2
	private void updateUpdateStatusLine2(List<Long> lineIdUpdateStatus, ILimitSysXRef[] refArr, ILimitDAO dao1, String date) {
			
			if(lineIdUpdateStatus.size()>0){
				DefaultLogger.debug(this, "3061 OBJECT DATA ============ inside if(lineIdUpdateStatus.size()>0 ");
				if (null != refArr) {
					for (int i = 0; i < refArr.length; i++) {
						for(int j=0;j<lineIdUpdateStatus.size();j++){
						  long longValue = lineIdUpdateStatus.get(j).longValue();
						if(longValue==refArr[i].getSID()){
						ILimitSysXRef obj = (ILimitSysXRef) refArr[i];
						ICustomerSysXRef xref = (ICustomerSysXRef) obj.getCustomerSysXRef();
						DefaultLogger.debug(this, "3069 OBJECT DATA ============ "+xref.getStatus());
						if(ICMSConstant.FCUBS_STATUS_PENDING_SUCCESS.equals(xref.getStatus())){
							xref.setStatus(ICMSConstant.FCUBS_STATUS_SUCCESS);
							

							xref.setSendToCore("N");
							
							xref.setSegment1Flag("");
							xref.setPrioritySectorFlag("");
							xref.setIsCapitalMarketExposerFlag("");
							xref.setEstateTypeFlag("");
							xref.setCommRealEstateTypeFlag("");
							xref.setUncondiCanclFlag("");
							xref.setBranchAllowedFlag("");
							xref.setProductAllowedFlag("");
							xref.setCurrencyAllowedFlag("");
							xref.setLimitRestrictionFlag("");
							xref.setAction("");
							xref.setCoreStpRejectedReason("");
							
							xref.setUdfDelete_2("");
							
							ILimitXRefUdf2[] xRefUdfData = xref.getXRefUdfData2();
							if(null!=xRefUdfData){
								
								xRefUdfData[0].setUdf116_Flag("");
								xRefUdfData[0].setUdf117_Flag("");
								xRefUdfData[0].setUdf118_Flag("");
								xRefUdfData[0].setUdf119_Flag("");
								
								xRefUdfData[0].setUdf120_Flag("");
								xRefUdfData[0].setUdf121_Flag("");
								xRefUdfData[0].setUdf122_Flag("");
								xRefUdfData[0].setUdf123_Flag("");
								xRefUdfData[0].setUdf124_Flag("");
								xRefUdfData[0].setUdf125_Flag("");
								xRefUdfData[0].setUdf126_Flag("");
								xRefUdfData[0].setUdf127_Flag("");
								xRefUdfData[0].setUdf128_Flag("");
								xRefUdfData[0].setUdf129_Flag("");
								
								xRefUdfData[0].setUdf130_Flag("");
								xRefUdfData[0].setUdf131_Flag("");
								xRefUdfData[0].setUdf132_Flag("");
								xRefUdfData[0].setUdf133_Flag("");
								xRefUdfData[0].setUdf134_Flag("");
								xRefUdfData[0].setUdf135_Flag("");
								xRefUdfData[0].setUdf136_Flag("");
								xRefUdfData[0].setUdf137_Flag("");
								xRefUdfData[0].setUdf138_Flag("");
								xRefUdfData[0].setUdf139_Flag("");
								
								xRefUdfData[0].setUdf140_Flag("");
								xRefUdfData[0].setUdf141_Flag("");
								xRefUdfData[0].setUdf142_Flag("");
								xRefUdfData[0].setUdf143_Flag("");
								xRefUdfData[0].setUdf144_Flag("");
								xRefUdfData[0].setUdf145_Flag("");
								xRefUdfData[0].setUdf146_Flag("");
								xRefUdfData[0].setUdf147_Flag("");
								xRefUdfData[0].setUdf148_Flag("");
								xRefUdfData[0].setUdf149_Flag("");
								
								xRefUdfData[0].setUdf150_Flag("");
								xRefUdfData[0].setUdf151_Flag("");
								xRefUdfData[0].setUdf152_Flag("");
								xRefUdfData[0].setUdf153_Flag("");
								xRefUdfData[0].setUdf154_Flag("");
								xRefUdfData[0].setUdf155_Flag("");
								xRefUdfData[0].setUdf156_Flag("");
								xRefUdfData[0].setUdf157_Flag("");
								xRefUdfData[0].setUdf158_Flag("");
								xRefUdfData[0].setUdf159_Flag("");
								
								xRefUdfData[0].setUdf160_Flag("");
								xRefUdfData[0].setUdf161_Flag("");
								xRefUdfData[0].setUdf162_Flag("");
								xRefUdfData[0].setUdf163_Flag("");
								xRefUdfData[0].setUdf164_Flag("");
								xRefUdfData[0].setUdf165_Flag("");
								xRefUdfData[0].setUdf166_Flag("");
								xRefUdfData[0].setUdf167_Flag("");
								xRefUdfData[0].setUdf168_Flag("");
								xRefUdfData[0].setUdf169_Flag("");
								
								xRefUdfData[0].setUdf170_Flag("");
								xRefUdfData[0].setUdf171_Flag("");
								xRefUdfData[0].setUdf172_Flag("");
								xRefUdfData[0].setUdf173_Flag("");
								xRefUdfData[0].setUdf174_Flag("");
								xRefUdfData[0].setUdf175_Flag("");
								xRefUdfData[0].setUdf176_Flag("");
								xRefUdfData[0].setUdf177_Flag("");
								xRefUdfData[0].setUdf178_Flag("");
								xRefUdfData[0].setUdf179_Flag("");
								
								xRefUdfData[0].setUdf180_Flag("");
								xRefUdfData[0].setUdf181_Flag("");
								xRefUdfData[0].setUdf182_Flag("");
								xRefUdfData[0].setUdf183_Flag("");
								xRefUdfData[0].setUdf184_Flag("");
								xRefUdfData[0].setUdf185_Flag("");
								xRefUdfData[0].setUdf186_Flag("");
								xRefUdfData[0].setUdf187_Flag("");
								xRefUdfData[0].setUdf188_Flag("");
								xRefUdfData[0].setUdf189_Flag("");
								
								xRefUdfData[0].setUdf190_Flag("");
								xRefUdfData[0].setUdf191_Flag("");
								xRefUdfData[0].setUdf192_Flag("");
								xRefUdfData[0].setUdf193_Flag("");
								xRefUdfData[0].setUdf194_Flag("");
								xRefUdfData[0].setUdf195_Flag("");
								xRefUdfData[0].setUdf196_Flag("");
								xRefUdfData[0].setUdf197_Flag("");
								xRefUdfData[0].setUdf198_Flag("");
								xRefUdfData[0].setUdf199_Flag("");
								xRefUdfData[0].setUdf200_Flag("");
								
								xRefUdfData[0].setUdf201_Flag("");
								xRefUdfData[0].setUdf202_Flag("");
								xRefUdfData[0].setUdf203_Flag("");
								xRefUdfData[0].setUdf204_Flag("");
								xRefUdfData[0].setUdf205_Flag("");
								xRefUdfData[0].setUdf206_Flag("");
								xRefUdfData[0].setUdf207_Flag("");
								xRefUdfData[0].setUdf208_Flag("");
								xRefUdfData[0].setUdf209_Flag("");
								xRefUdfData[0].setUdf210_Flag("");
								xRefUdfData[0].setUdf211_Flag("");
								xRefUdfData[0].setUdf212_Flag("");
								xRefUdfData[0].setUdf213_Flag("");
								xRefUdfData[0].setUdf214_Flag("");
								xRefUdfData[0].setUdf215_Flag("");
							
								
								
							
						}
						}else if(ICMSConstant.FCUBS_STATUS_PENDING_REJECTED.equals(xref.getStatus())){
							xref.setStatus(ICMSConstant.FCUBS_STATUS_REJECTED);
							xref.setSendToCore("N");
						}
						
						obj.setCustomerSysXRef(xref);
						refArr[i] = obj;
						}
						}
					}
					}
					}
		}
		
	
		private static long getReferenceId(String trxId) {
			String referenceId="-999999999";
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			referenceId=dao.getReferenceId(trxId);
			return Long.parseLong(referenceId);
		}
}
