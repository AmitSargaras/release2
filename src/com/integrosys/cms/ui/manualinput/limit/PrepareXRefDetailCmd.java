/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UDFComparator;
import com.integrosys.cms.app.udf.bus.UdfDaoImpl;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareXRefDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
			
		/*	 { "xrefDetailForm", "java.lang.Object", FORM_SCOPE },*/
				{ "hostSystemCountry", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{"udfLbValList1", "java.lang.String", REQUEST_SCOPE },
				{"udfLbValList1_2", "java.lang.String", REQUEST_SCOPE },
				{"udfAllowed", "java.lang.String", REQUEST_SCOPE },
				{"udfAllowed_2", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "branchAllowed", "java.lang.String", REQUEST_SCOPE },
				{ "productAllowed", "java.lang.String", REQUEST_SCOPE },
				{ "currencyAllowed", "java.lang.String", REQUEST_SCOPE },
				{"releasedAmount", "java.lang.String", REQUEST_SCOPE },
				{"utilizedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{"sendToFile", "java.lang.String", REQUEST_SCOPE },
				{ "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				{"partyScmFlag", "java.lang.String", SERVICE_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "restCoBorrowerList", "java.util.List", REQUEST_SCOPE },
				/*{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },*/
				{"coBorrowerId", "java.lang.String", REQUEST_SCOPE },
				{ "coBorrowerName", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", REQUEST_SCOPE },
				{ "xrefDetailFormObj",OBCustomerSysXRef.class.getName(), SERVICE_SCOPE },
				{ "covenantMap", Map.class.getName(), SERVICE_SCOPE },
				{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			
				{ "curAccount", "com.integrosys.cms.app.customer.bus.ICustomerSysXRef", SERVICE_SCOPE },
			/*	 { "xrefDetailForm", "java.lang.Object", FORM_SCOPE },*/
				{ "sourceSystemCountryList", "java.util.List", REQUEST_SCOPE },
				{ "sourceSystemNameList", "java.util.List", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "subSecurityList", "java.util.List", SERVICE_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				//{ "productMasterList", "java.util.List", SERVICE_SCOPE },
				{ "fccBranchList", "java.util.List", SERVICE_SCOPE },
				{"partyCapitalMarExp", "java.lang.String", SERVICE_SCOPE },
				{"partySegment", "java.lang.String", SERVICE_SCOPE },
				{"udfLbValList", "java.lang.String", SERVICE_SCOPE },
				{"udfLbValList1", "java.lang.String", SERVICE_SCOPE },
				{"udfLbValList_2", "java.lang.String", SERVICE_SCOPE },
				{"udfLbValList1_2", "java.lang.String", SERVICE_SCOPE },
				{"udfAllowed", "java.lang.String", REQUEST_SCOPE },
				{"udfAllowed_2", "java.lang.String", REQUEST_SCOPE },
				{"segment1", "java.lang.String", SERVICE_SCOPE },
				{"currency", "java.lang.String", SERVICE_SCOPE },
				{ "mainLineCodeList", "java.util.List", SERVICE_SCOPE },
				{ "branchAllowedList1", "java.util.List", SERVICE_SCOPE },
				{ "branchAllowedList", "java.util.List", SERVICE_SCOPE },
				{ "productAllowedList1", "java.util.List", SERVICE_SCOPE },
				{ "productAllowedList", "java.util.List", SERVICE_SCOPE },
				{ "currencyAllowedList1", "java.util.List", SERVICE_SCOPE },
				{ "currencyAllowedList", "java.util.List", SERVICE_SCOPE },
				{"limitType", "java.lang.String", SERVICE_SCOPE },
				{"ruleId", "java.lang.String", SERVICE_SCOPE },
				{"internalRemarks", "java.lang.String", SERVICE_SCOPE },
				{"revolvingLine", "java.lang.String", SERVICE_SCOPE },
				{"linecurrency", "java.lang.String", SERVICE_SCOPE },
				{"intradayLimit", "java.lang.String", SERVICE_SCOPE },
				{"currencyRestriction", "java.lang.String", SERVICE_SCOPE },
				{"camDate", "java.util.Date", SERVICE_SCOPE },
				{"camExtentionDate", "java.util.Date", SERVICE_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "subSecurityMap", "java.util.Map", SERVICE_SCOPE },
				{ "fccBranchMap", "java.util.Map", SERVICE_SCOPE },
				{ "mainLineCodeMap", "java.util.Map", SERVICE_SCOPE },
				{ "productListFromProperty", "java.lang.String", SERVICE_SCOPE },
				{ "productIdList", "java.lang.String", SERVICE_SCOPE },
				{ "liabilityId", "java.lang.String", SERVICE_SCOPE },
				{ "guarantee", "java.lang.String", SERVICE_SCOPE },
				{ "partyId", "java.lang.String", SERVICE_SCOPE },
//				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "releaseDate", "java.util.Date", REQUEST_SCOPE },
				{ "dateOfReset", "java.util.Date", REQUEST_SCOPE },
				{ "utilizedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "currency", "java.lang.String", REQUEST_SCOPE },
				{"scmFlag", "java.lang.String", SERVICE_SCOPE },
				{"vendorDtls", "java.lang.String", SERVICE_SCOPE },
				{"partyScmFlag", "java.lang.String", SERVICE_SCOPE },
				{ "adhocLine", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },
				{ "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
				{ "scfStatus", "java.lang.String", SERVICE_SCOPE }, 
				{ "scfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "scfFlag", "java.lang.String", SERVICE_SCOPE },
				{ "requestDate", "java.lang.String", SERVICE_SCOPE },
				{ "responseDate", "java.lang.String", SERVICE_SCOPE },
				{ "radioInterfaceCompleted", "java.lang.String", SERVICE_SCOPE },
				{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
				{"idlApplicableFlagAddFacility", "java.lang.String", REQUEST_SCOPE},
				{"releasableAmtForCheck", "java.lang.String", REQUEST_SCOPE},
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
	AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String event = (String) (map.get("event"));
		HashMap<String,String> covenantMap = (HashMap) map.get("covenantMap");
		try {
			
			MILimitUIHelper helper = new MILimitUIHelper();
			
			String fromEventForLineCov = (String) (map.get("event"));
			String from_event = (String) (map.get("fromEvent"));
			System.out.println("event=="+event+" && from_event=="+from_event);
			String hostSystemCountry = (String) (map.get("hostSystemCountry"));
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			String isLineCreate ="";
			if("prepare_create_ubs".equals(event)) {
				isLineCreate="Yes";
			}
			result.put("isLineCreate", isLineCreate);
//			int index = 0;
			ILimitSysXRef xref=null;
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<LIMIT ID:<>>>>>>>>>>>>>>>>>>>>>>>"+lmtTrxObj.getReferenceID()); //Limit ID, 

		//	List<OBCustomerSysXRef> restCountryList = new ArrayList();
			String scfStatus = "";
			String scfFlag = "";
			String scfErrMsg = "";
			String responseDate="";
			String requestDate="";
			String radioInterfaceCompleted="NotChecked";
			String lmtProfileId = String.valueOf(lmtTrxObj.getLimitProfileID()); //CAM

			CustomerDAO custDao=new CustomerDAO();
			String partyId = custDao.getPartyId(lmtProfileId);
			result.put("partyId",partyId);
			System.out.println("Party Id at prepare xref command "+partyId);
			
			String adhocFacility = null;
			if(null!=lmtTrxObj.getLimit()) {
			if(null!=lmtTrxObj.getLimit().getAdhocFacility()) {
			adhocFacility = lmtTrxObj.getLimit().getAdhocFacility();
			System.out.println("=============================adhoc Facility at line 201 prepare xref command "+adhocFacility);
			}
			}
			result.put("adhocFacility",adhocFacility);
			
			result.put("fromEventForLineCov",fromEventForLineCov);
			String index = "0";
			if (map.get("indexID") != null && !map.get("indexID").equals("")) {
				//index = Integer.parseInt((String) map.get("indexID"));
				index = (String) map.get("indexID");
			}

			String facCat = getRequestData(map, covenantMap, event, "facCat");
			OBCustomerSysXRef curAccount = null;
			
			
			
			String releasedAmount = (String) (map.get("releasedAmount"));
			String utilizedAmount = (String) (map.get("utilizedAmount"));
			String sendToFile = (String) (map.get("sendToFile"));
			
			System.out.println("releasedAmount:"+releasedAmount+" utilizedAmount:"+utilizedAmount);
			if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_CREATE_UBS.equals(event)  
					|| EventConstant.EVENT_ADD_CO_BORROWER_CREATE.equals(event) ||  EventConstant.EVENT_DELETE_CO_BORROWER.equals(event) 
					|| EventConstant.EVENT_PREPARE_CREATE_TS.equals(event) 
					|| EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(event)||EventConstant.EVENT_CREATE_UDF.equals(event)) {
				curAccount = new OBCustomerSysXRef();
			}
			else if(EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event)) {
				curAccount = (OBCustomerSysXRef) map.get("xrefDetailFormObj");
			}
			else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_TS.equals(event)
					|| EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED.equals(event)
					|| EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event) ||EventConstant.EVENT_EDIT_UDF.equals(event) 
					||  EventConstant.EVENT_ADD_CO_BORROWER.equals(event)  || EventConstant.EVENT_DELETE_CO_BORROWER.equals(event)
					||  EventConstant.EVENT_ADD_CO_BORROWER_1.equals(event)  || EventConstant.EVENT_DELETE_CO_BORROWER_1.equals(event)

					
					|| EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(event) ||EventConstant.EVENT_EDIT_UDF_REJECTED.equals(event)
					|| EventConstant.EVENT_PREPARE_CLOSE_UBS.equals(event) || EventConstant.EVENT_CLOSE_UDF.equals(event) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS.equals(event)
					|| EventConstant.EVENT_PREPARE_REOPEN_UBS.equals(event) || EventConstant.EVENT_REOPEN_UDF.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS.equals(event)
					|| EventConstant.EVENT_PREPARE_CLOSE_UBS_REJECTED.equals(event) || EventConstant.EVENT_CLOSE_UDF_REJECTED.equals(event) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED.equals(event)
					|| EventConstant.EVENT_PREPARE_REOPEN_UBS_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_UDF_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED.equals(event)
					|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(event) || EventConstant.EVENT_UPDATE_STATUS_UDF.equals(event) || EventConstant.EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS.equals(event)
					|| EventConstant.EVENT_UPDATE_STATUS_UBS_ERROR.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_STATUS_TS.equals(event)
					) {

				String sourceRefNo=(String) map.get("sourceRefNo");
				DefaultLogger.debug(this, "sourceRefNo:"+sourceRefNo);
				 
				if("updateStatus".equals(from_event) && "updateStatus_ubs_error".equals(event)){
					 xref = helper.getCurLmtSysXRefUpdateSta(event, from_event, sourceRefNo, lmtTrxObj);	
				}
				else{
				 xref = helper.getCurWorkingLimitSysXRef(event, from_event, index, lmtTrxObj);
				}
				
				if(null!=xref) {
					curAccount = (OBCustomerSysXRef) xref.getCustomerSysXRef();
					if (curAccount.getHiddenSerialNo() == null || "".equals(curAccount.getHiddenSerialNo())) {
						curAccount.setHiddenSerialNo(curAccount.getSerialNo());
					}
				}
			}
			
			if(null==curAccount && (EventConstant.EVENT_EDIT_UDF.equals(event)||EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event))) {
				curAccount = (OBCustomerSysXRef) map.get("xrefDetailFormObj");
			}
			
			if(null!=curAccount) {
				curAccount.setFacilitySystem(lmtTrxObj.getStagingLimit().getFacilitySystem());
				curAccount.setLineNo(lmtTrxObj.getStagingLimit().getLineNo());
				curAccount.setCurrency(lmtTrxObj.getStagingLimit().getCurrencyCode());
			//	String lineno=curAccount.setLineNo(lmtTrxObj.getStagingLimit().getLineNo());
				
				if(helper.isReqSyncBankingArrAtLineBySystem(curAccount.getFacilitySystem())) {
					curAccount.setBankingArrangement(lmtTrxObj.getStagingLimit().getBankingArrangement());
				}
				if(EventConstant.EVENT_CLOSE_UDF.equals(event) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS.equals(event)
					|| EventConstant.EVENT_REOPEN_UDF.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS.equals(event)
					||EventConstant.EVENT_CLOSE_UDF_REJECTED.equals(event) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED.equals(event)
					|| EventConstant.EVENT_REOPEN_UDF_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED.equals(event)){
					curAccount.setUtilizedAmount(utilizedAmount);
					curAccount.setReleasedAmount(releasedAmount);
					curAccount.setSendToFile(sendToFile);
//					curAccount.setAvailable(available)
//					curAccount.setCloseFlag("Y");
					
				}
				String idlApplicableFlagAddFacility = "";
				try {
					LimitDAO limitDAO=new LimitDAO();
					String facilityCode = lmtTrxObj.getStagingLimit().getFacilityCode();
					System.out.println("PrepareXRefDetailCmd.java=> line 283=>idlApplicableFlagAddFacility===>"+idlApplicableFlagAddFacility+"..facilityCode=>"+facilityCode);
					idlApplicableFlagAddFacility = limitDAO.getIDLApplicableFlagFacilityMaster(facilityCode);
					System.out.println("PrepareXRefDetailCmd.java=> line 285=>idlApplicableFlagAddFacility===>"+idlApplicableFlagAddFacility+"..facilityCode=>"+facilityCode);
					result.put("idlApplicableFlagAddFacility", idlApplicableFlagAddFacility);
					
					
					String releasableAmtForCheck = lmtTrxObj.getStagingLimit().getReleasableAmount();
					System.out.println("PrepareXRefDetailCmd.java=> line 285=>releasableAmtForCheck===>"+releasableAmtForCheck);
					result.put("releasableAmtForCheck", releasableAmtForCheck);
				}catch(Exception e) {
					System.out.println("Exception PrepareXRefDetailCmd.java=> line 288=>e=>"+e);
					e.printStackTrace();
				}
				
			}
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			result.put("subSecurityList", getSubSecurityList(proxy.getSubSecurityList(lmtTrxObj.getLimitProfileID())));

			result.put("subSecurityMap", getSubSecurityListMap(proxy.getSubSecurityList(lmtTrxObj.getLimitProfileID())));
			
			IFCCBranchDao fccBranchDao = (IFCCBranchDao) BeanHouse.get("fccBranchDao");
			IProductMasterDao productMasterDao = (IProductMasterDao) BeanHouse.get("productMasterDao");


			
			if (EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_TS.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED.equals(event) || 
					EventConstant.EVENT_PREPARE_CREATE_UBS.equals(event)  || EventConstant.EVENT_ADD_CO_BORROWER_CREATE.equals(event)
					 || EventConstant.EVENT_ADD_CO_BORROWER.equals(event) ||  EventConstant.EVENT_DELETE_CO_BORROWER.equals(event) 
					||  EventConstant.EVENT_PREPARE_CREATE_TS.equals(event)
					|| EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(event) || EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event)
					||EventConstant.EVENT_EDIT_UDF.equals(event) 
					|| EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(event)
					||EventConstant.EVENT_EDIT_UDF_REJECTED.equals(event)|| EventConstant.EVENT_CREATE_UDF.equals(event)
					|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(event) || EventConstant.EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS.equals(event)
					||EventConstant.EVENT_UPDATE_STATUS_UDF.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(event) 
					|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_TS.equals(event) || EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event)) {
			long limitProfileID = lmtTrxObj.getLimitProfileID();
			CustomerDAO customerDao = new CustomerDAO();
			String limitProfileIDStr=String.valueOf(limitProfileID);
			String partyCapitalMarExp = customerDao.getPartyCapitalMarExp(limitProfileIDStr);
			String partySegment = customerDao.getPartySegment(limitProfileIDStr);
			String segment1="";
			if(null!=partySegment) {
				segment1=customerDao.getSegment1(partySegment.toUpperCase());
			}
			result.put("partyCapitalMarExp", partyCapitalMarExp);

			result.put("partySegment", partySegment);
			result.put("segment1", segment1);
			
			String subPartyName="";
			String system="";
			String systemId="";
			if("Yes".equals(lmtTrxObj.getStagingLimit().getGuarantee())){
				subPartyName=lmtTrxObj.getStagingLimit().getSubPartyName();
				if(null!=lmtTrxObj.getStagingLimit().getLiabilityID() && !"".equals(lmtTrxObj.getStagingLimit().getLiabilityID())){
				String[] tempLiabId=lmtTrxObj.getStagingLimit().getLiabilityID().split(" - ");
				
				system=tempLiabId[0];
				systemId=tempLiabId[1];
				}
				
			}
			
			List<LabelValueBean> mainLineCodeList = customerDao.getMainLineCode(limitProfileIDStr,subPartyName,system,systemId);
			result.put("mainLineCodeList", mainLineCodeList);
			
			List productAllowedList = productMasterDao.getProductMasterList();
			List productMasterLbValList = new ArrayList();
			try {

				for (int i = 0; i < productAllowedList.size(); i++) {
					IProductMaster productMaster = (IProductMaster) productAllowedList.get(i);
					if (productMaster.getStatus().equals("ACTIVE")) {

						String id = Long.toString(productMaster.getId());
						String val = productMaster.getProductCode();


						LabelValueBean lvBean = new LabelValueBean(val, id);
						productMasterLbValList.add(lvBean);

					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
			
			productAllowedList = CommonUtil.sortDropdown(productMasterLbValList);
			
			String productAllowed=(String) (map.get("productAllowed"));
			List productAllowedList1=new ArrayList();
			if(null==productAllowed ||  "".equals(productAllowed))
				productAllowed=curAccount.getProductAllowed();
			
			if(null!=productAllowed) {
				List<String> items = Arrays.asList(productAllowed.split(","));
           	 	for(int i=0;i<productAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)productAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		productAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	if(null!=productAllowedList1 && productAllowedList1.size()>0)
           	 	productAllowedList.removeAll(productAllowedList1);
           	
			}
			
			List fccBranchList = fccBranchDao.getFccBranchList();
			List fccBranchLbValList = new ArrayList();
			
			Map fccBranchMap=new HashMap();
			try {

				for (int i = 0; i < fccBranchList.size(); i++) {
					IFCCBranch fccBranch = (IFCCBranch) fccBranchList.get(i);
					if (fccBranch.getStatus().equals("ACTIVE")) {

						String id1 = Long.toString(fccBranch.getId());
						String val1 = fccBranch.getBranchCode();

						LabelValueBean lvBean1 = new LabelValueBean(val1, id1);
						fccBranchLbValList.add(lvBean1);
						fccBranchMap.put(id1,val1);
					}
				}
			} catch (Exception ex) {
			}
			fccBranchList = CommonUtil.sortDropdown(fccBranchLbValList);
			
			
			List branchAllowedList = fccBranchDao.getFccBranchList();
			List branchAllowedListTemp=new ArrayList();
			try {

				for (int i = 0; i < branchAllowedList.size(); i++) {
					IFCCBranch fccBranch = (IFCCBranch) branchAllowedList.get(i);
					if (fccBranch.getStatus().equals("ACTIVE")) {

						String id1 = Long.toString(fccBranch.getId());
						String val1 = fccBranch.getBranchCode();

						LabelValueBean lvBean1 = new LabelValueBean(val1, id1);
						branchAllowedListTemp.add(lvBean1);
					}
				}
			} catch (Exception ex) {
			}
			branchAllowedList = CommonUtil.sortDropdown(branchAllowedListTemp);
			
			String branchAllowed=(String) (map.get("branchAllowed"));
			List branchAllowedList1=new ArrayList();
			if(null==branchAllowed || "".equals(branchAllowed))
				branchAllowed=curAccount.getBranchAllowed();
			
			if(null!=branchAllowed) {
				List<String> items = Arrays.asList(branchAllowed.split(","));
           	 	for(int i=0;i<branchAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)branchAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		branchAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	if(null!=branchAllowedList1 && branchAllowedList1.size()>0)
           		branchAllowedList.removeAll(branchAllowedList1);
			}
			
			ILimitDAO dao = LimitDAOFactory.getDAO();
			
			//String limitId=dao.getLimitProfileID(lmtProfileId);
			
			List currencyAllowedList = dao.getCurrencyList();
			String currencyAllowed=(String) (map.get("currencyAllowed"));
			List currencyAllowedList1=new ArrayList();
			if(null==currencyAllowed ||  "".equals(currencyAllowed))
				currencyAllowed=curAccount.getCurrencyAllowed();
			
			if(null!=currencyAllowed) {
				List<String> items = Arrays.asList(currencyAllowed.split(","));
           	 	for(int i=0;i<currencyAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)currencyAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		currencyAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	if(null!=currencyAllowedList1 &&currencyAllowedList1.size()>0)
           	 	currencyAllowedList.removeAll(currencyAllowedList1);
			}
				
			String ruleId = dao.getRuleId(lmtTrxObj.getStagingLimit().getFacilityCode());
			String internalRemarks = dao.getLineDescription(lmtTrxObj.getStagingLimit().getFacilityCode());
			String currencyRestriction = dao.getCurrencyRestriction(lmtTrxObj.getStagingLimit().getFacilityCode());
			String revolvingLine = dao.getRevolvingLine(lmtTrxObj.getStagingLimit().getFacilityCode());
			String linecurrency = dao.getLineCurrency(lmtTrxObj.getStagingLimit().getFacilityCode());
			String intradayLimit = dao.getIntradayLimit(lmtTrxObj.getStagingLimit().getFacilityCode());
			String scmFlag = dao.getScmFlag(lmtTrxObj.getStagingLimit().getFacilityCode());
			String partyScmFlag = dao.getBorrowerScmFlagForAlert(partyId);

			if("ET".equals(lmtTrxObj.getStagingLimit().getFacilitySystem())) {
				String releaseDate = "";
				String dateOfReset = "";
				
				if(EventConstant.EVENT_PREPARE_CREATE_TS.equals(event)) {
					Date camExtentionDate = dao.getCamExtentionDate(limitProfileIDStr);
					Date camDate = dao.getCamDate(limitProfileIDStr);
		 			releaseDate = dateFormater(camDate);
		 			dateOfReset = dateFormater(camExtentionDate);
				}else {
					if(null != lmtTrxObj.getStagingLimit().getLimitSysXRefs()[0].getCustomerSysXRef().getReleaseDate()) {
						releaseDate = dateFormater(lmtTrxObj.getStagingLimit().getLimitSysXRefs()[0].getCustomerSysXRef().getReleaseDate());
					}else{
						Date camDate = dao.getCamDate(limitProfileIDStr);
			 			releaseDate = dateFormater(camDate);
					}
					if(null != lmtTrxObj.getStagingLimit().getLimitSysXRefs()[0].getCustomerSysXRef().getDateOfReset()) {
						dateOfReset = dateFormater(lmtTrxObj.getStagingLimit().getLimitSysXRefs()[0].getCustomerSysXRef().getDateOfReset());
					}else{
						Date camExtentionDate = dao.getCamExtentionDate(limitProfileIDStr);
			 			dateOfReset = dateFormater(camExtentionDate);
					}
				} 			 			
	
	 			result.put("releaseDate", releaseDate);
	 			result.put("dateOfReset", dateOfReset);
			}else{
				Date camExtentionDate = dao.getCamExtentionDate(limitProfileIDStr);
				result.put("camExtentionDate",camExtentionDate);
			}
			
           	 result.put("productAllowedList", productAllowedList);
 			result.put("productAllowedList1", productAllowedList1);
 			result.put("currencyAllowedList", currencyAllowedList);
 			result.put("currencyAllowedList1", currencyAllowedList1);
 			
 			result.put("fccBranchList", fccBranchList);
 			
 			result.put("fccBranchMap", fccBranchMap);

 			Map mainLineCodeMap=new HashMap();
 			for(int m=0;m <mainLineCodeList.size();m++){
 				LabelValueBean lb = mainLineCodeList.get(m);
 				mainLineCodeMap.put(lb.getLabel(), lb.getLabel());
 			}
 			result.put("mainLineCodeMap", mainLineCodeMap);
 			
 			result.put("branchAllowedList", branchAllowedList);
 			result.put("branchAllowedList1", branchAllowedList1);
 			result.put("ruleId",ruleId);
 			result.put("internalRemarks",internalRemarks);
 			result.put("revolvingLine",revolvingLine);
 			result.put("linecurrency",linecurrency);
 			result.put("intradayLimit",intradayLimit);
 			result.put("currencyRestriction",currencyRestriction);
 			result.put("scmFlag", scmFlag);
 			result.put("partyScmFlag", partyScmFlag);
			result.put("currency", curAccount.getCurrency());
 			
 			//240MULCEX
 		//	ResourceBundle bundle = ResourceBundle.getBundle("ofa");
 			String productListFromProperty="";
 			String productIdList="";
 			if(null!=curAccount) {
 				if(null!=curAccount.getLineNo() && !curAccount.getLineNo().isEmpty())
				 productListFromProperty = PropertyManager.getValue(curAccount.getLineNo());
			}
 			
 			if(null==productListFromProperty){
 				productListFromProperty  ="";
 			}
 			if(!productListFromProperty.isEmpty()){
 				String productCodes="";
 				String[] product = productListFromProperty.split(",");
 				for(int i =0;i<product.length; i++){
 					if(i==product.length-1){
 						productCodes=productCodes+"'"+product[i].trim()+"'";	
 					}else{
 					productCodes=productCodes+"'"+product[i].trim()+"',";
 					}
 				}
 				
 				productIdList = dao.getProductIdList(productCodes);
 			}
 			
 			result.put("productIdList",productIdList);
 			result.put("productListFromProperty",productListFromProperty);
			}
			
			String facCoBorrowerLiabIds =  (String) map.get("facCoBorrowerLiabIds");
			System.out.println("facCoBorrowerLiabIds in JAVA CMD:::::"+facCoBorrowerLiabIds);
			result.put("facCoBorrowerLiabIds", facCoBorrowerLiabIds);
			
			//*****************  BBBBBBB SSSSSSS*********************************
			List list = (List)map.get("restCoBorrowerList");
			//		List idlist = (List)map.get("restCoBorrowerIds");
			//System.out.println("list======================="+list);
			String coBorrowerId = (String)map.get("coBorrowerId");
			String coBorrowerName = (String)map.get("coBorrowerName");
					
			System.out.println("@@@@@@@@@@@coBorrowerId+++++++++++++"+coBorrowerId + "& coBorrowerName+++++++++++++++++++"+coBorrowerName);

			//DefaultLogger.debug(this, "Size of list for event edit "+list.size());
			DefaultLogger.debug(this, "in PrepareXrefDetailCmd.java ==720==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 

			if((null == coBorrowerId ) && (null == list || ( "prepare_create".equalsIgnoreCase(event) || "prepare_create_ubs".equalsIgnoreCase(event) )) )
			{
			list = new ArrayList();
			}
			
		
		
			boolean flag = false;
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			List vNames = new ArrayList();
			if(!"prepare_create".equalsIgnoreCase(event)  && !"prepare_create_ubs".equalsIgnoreCase(event)  && !"add_coBorrower_create".equalsIgnoreCase(event) )
			{
			if(lmtTrxObj.getReferenceID()!=null) {
				System.out.println("lmtTrxObj.getReferenceID()==========="+lmtTrxObj.getCustomerID());
				vNames= lmtDao.getRestrictedCoBorrowerForLine(lmtTrxObj.getCustomerID());
				
			}
			}
			if(list!=null && list.size()!=0)
			{	
				
				System.out.println("list.size() ==================="+list.size() );
				if( list.size() >= 5)

				{
					System.out.println("list.size() is greater than 5############# ===================");
				//	HashMap exceptionMap = new HashMap();
				//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.size"));
					ICMSCustomerTrxValue partyGroupTrxValue = null;
					result.put("request.ITrxValue", partyGroupTrxValue);
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
				//	temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
				}
				
				for(int i = 0;i<list.size();i++)
				{
					OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)list.get(i);
					if((null != ven.getCoBorrowerId()  && ven.getCoBorrowerId().equals(coBorrowerId))){
					flag = false;
					}
				}
			}
			
			if(!flag)
			{
			//	if	(EventConstant.EVENT_ADD_CO_BORROWER.equals(event)	|| 	EventConstant.EVENT_ADD_CO_BORROWER_CREATE.equals(event) || 	EventConstant.EVENT_ADD_CO_BORROWER_1.equals(event))
					
				System.out.println("flag::: "+flag);
				if(null !=vNames && vNames.size()!=0)
				{	
					for(int i = 0;i<vNames.size();i++)
					{
						OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)vNames.get(i);
						//	if( null != ven.getCoBorrowerName() &&ven.getCoBorrowerName().equals( coBorrowerName))
						if( null != ven.getCoBorrowerId() &&ven.getCoBorrowerId().equals( coBorrowerId))

							{
							//	HashMap exceptionMap = new HashMap();
							//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.id"));
								ICMSCustomerTrxValue partyGroupTrxValue = null;
								result.put("request.ITrxValue", partyGroupTrxValue);
								temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
							//	temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								return temp;
							}
					}
				}
			
				for(int i = 0;i<list.size();i++)
				{
					OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)list.get(i);
				//	if(null != ven.getCoBorrowerName() && ven.getCoBorrowerName().equals(coBorrowerName))
					if( null != ven.getCoBorrowerId() &&ven.getCoBorrowerId().equals( coBorrowerId))

							{
					//	HashMap exceptionMap = new HashMap();
					//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.id"));
						ICMSCustomerTrxValue partyGroupTrxValue = null;
						result.put("request.ITrxValue", partyGroupTrxValue);
						temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
						//temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return temp;
							}
				}
				
			}
			
			OBLimitXRefCoBorrower value = new OBLimitXRefCoBorrower();
			if(null != coBorrowerName && !"".equals(coBorrowerName)) {
			value.setCoBorrowerId(coBorrowerId);
			value.setCoBorrowerName(coBorrowerName);
			
			list.add(value);
			
			  
			}
			List	idlist = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				OBLimitXRefCoBorrower coborrObj = (OBLimitXRefCoBorrower) list.get(i);
				idlist.add(coborrObj.getCoBorrowerId());
				
			}
			
			String lineCoBorrowerIds = UIUtil.getDelimitedStringFromList(idlist, ",");
			   lineCoBorrowerIds = lineCoBorrowerIds==null ? "" : lineCoBorrowerIds ;
			   System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% lineCoBorrowerIds"+lineCoBorrowerIds);
			result.put("restCoBorrowerList",list);
			result.put("restCoBorrowerIds",lineCoBorrowerIds);
		
			
		//	result.put("restCoBorrowerList",addCoBorrowerToForm(curAccount));
			result.put("limitType", lmtTrxObj.getStagingLimit().getLimitType());
			
			String currency=lmtTrxObj.getStagingLimit().getCurrencyCode();
			result.put("currency", currency);
			
			result.put("facCat", facCat);
			result.put("curAccount", curAccount);
			result.put("xrefDetailForm", curAccount);
			result.put("sourceSystemCountryList", getSourceSystemCountryList());
			if(null!=curAccount) {
				if (hostSystemCountry == null) {
					hostSystemCountry = curAccount.getExternalSysCountry();
				}
				result.put("sourceSystemNameList", getSourceSystemNameList(hostSystemCountry));
			}
			//Start Santosh for ubs limit upload
			//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
			IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
			List udfList = udfDao.getUdfByNonMandatory("3");
			List udfLbValList = new ArrayList();
			List udfLbValList1=new ArrayList();
			if (udfList == null || udfList.size() == 0) {
				System.out.println("No User Defined Fields Defined!");
			}
			else {
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
			}
			String udfAllowed=(String) (map.get("udfAllowed"));
			if(null!=curAccount) {
			if(null==udfAllowed ||  "".equals(udfAllowed))
				 udfAllowed=curAccount.getUdfAllowed();
			}
			if(null!=udfAllowed) {
				List<String> items = Arrays.asList(udfAllowed.split(","));
           	 	for(int i=0;i<udfLbValList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 			udfLbValList1.add(lvBean1);
           	 		}
           	 	}
           	udfLbValList.removeAll(udfLbValList1);
			}
			
		
			List udfList_2 = udfDao.getUdfByNonMandatory("4");
			List udfLbValList_2 = new ArrayList();
			List udfLbValList1_2=new ArrayList();
			if (udfList_2 == null || udfList_2.size() == 0) {
				System.out.println("No User Defined Fields Defined!");
			}
			else {
				int size = udfList_2.size();
				Collections.sort(udfList_2, new UDFComparator());
				IUdf udf;
				String fieldName,id;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList_2.get(i);
					fieldName=udf.getFieldName();
					id=Long.toString(udf.getSequence());
					LabelValueBean lvBean1 = new LabelValueBean(fieldName, id);
					udfLbValList_2.add(lvBean1);
				}
			}
			String udfAllowed_2=(String) (map.get("udfAllowed_2"));
			if(null!=curAccount) {
			if(null==udfAllowed_2 ||  "".equals(udfAllowed_2))
				 udfAllowed_2=curAccount.getUdfAllowed_2();
			}
			if(null!=udfAllowed_2) {
				List<String> items = Arrays.asList(udfAllowed_2.split(","));
           	 	for(int i=0;i<udfLbValList_2.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList_2.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 			udfLbValList1_2.add(lvBean1);
           	 		}
           	 	}
           	udfLbValList_2.removeAll(udfLbValList1_2);
			}
			result.put("udfLbValList", udfLbValList);
			result.put("udfLbValList1", udfLbValList1);
			
			result.put("udfLbValList_2", udfLbValList_2);
			result.put("udfLbValList1_2", udfLbValList1_2);
			
			result.put("sessionCriteria",map.get("sessionCriteria"));
			result.put("utilizedAmount", utilizedAmount); 
			result.put("lmtTrxObj", lmtTrxObj);

			//End Santosh for ubs limit upload
			
			String liabDetails = lmtTrxObj.getStagingLimit().getLiabilityID();
			String guarantee = lmtTrxObj.getStagingLimit().getGuarantee();
			
			if(null==guarantee){
				guarantee="";	
			}
			String dash=" - ";
			String liabilityId="";
			if("Yes".equals(guarantee) && null!=liabDetails){
				if(liabDetails.contains(" - ")){
					String[] split = liabDetails.split(dash);
					 liabilityId=split[1].trim();
				}
			}
			DefaultLogger.debug(this, "liabilityId:"+liabilityId+" guarantee:"+guarantee);
			result.put("liabilityId", liabilityId);
			result.put("guarantee",guarantee);
		
			/*------------SCF LINE------------*/
			
			if (null != curAccount) {
				if(null != curAccount.getHiddenSerialNo() && !curAccount.getHiddenSerialNo().isEmpty())
					DefaultLogger.debug(this,"-------ReadXRefDetailCmd Hidden Serial No------->>"+curAccount.getHiddenSerialNo());
				if(null != curAccount.getSerialNo() && !curAccount.getSerialNo().isEmpty())
					DefaultLogger.debug(this,"-------ReadXRefDetailCmd Serial No------->>"+curAccount.getSerialNo());
			}
			
			List scfStatusList = new ArrayList();
			scfStatusList = lmtDao.getScfStatusForLineById(lmtProfileId, curAccount.getLineNo(), curAccount.getSerialNo());
			if (null != scfStatusList && !scfStatusList.isEmpty()) {
				System.out.println("scfStatusList contains values at PrepareXRefDetailCmd line 832");
				scfFlag = (String) scfStatusList.get(2);
				
				if (null != scfFlag ) {
					System.out.println("scfFlag contains values at PrepareXRefDetailCmd line 836"+ scfFlag);
					
					scfStatus = (String) scfStatusList.get(0);
					
					System.out.println("scfStatus contains values at PrepareXRefDetailCmd line 840"+ scfStatus);
					if (("Y").equalsIgnoreCase(scfFlag)) {
						if (("Success").equalsIgnoreCase(scfStatus)) {
						} else if (("Failed").equalsIgnoreCase(scfStatus) || ("Fail").equalsIgnoreCase(scfStatus)) {
							scfErrMsg = (String) scfStatusList.get(1);
						} else if (("Error").equalsIgnoreCase(scfStatus)) {
							scfStatus= "Fail";
							scfErrMsg = (String) scfStatusList.get(1);
						}
					} else if (("N").equalsIgnoreCase(scfFlag)) {
						if(null != scfStatus && !scfStatus.isEmpty())
							scfStatus = "Stopped";
					}
				}
			} else if (null == scfStatusList  || scfStatusList.isEmpty())  { // non - scm, where scfStatusList.isEmpty()
				System.out.println("PrepareXRefDetailCmd Line 855 -->> setting scfStatus = NA as scfStatusList(STATUS,ERRORMESSAGE for given limitProfileId, line_no, serial_no) is empty/null");
				scfStatus = "NA";
			}

			result.put("scfStatus", scfStatus);
			result.put("scfErrMsg", scfErrMsg);


			/*------------ECBF LINE------------*/
			if (null != xref  && xref.getSID() != -999999999) {

				System.out.println("null != xref  && xref.getSID() != -999999999 contains values at PrepareXRefDetailCmd line 869");
				
				Long sId = xref.getSID(); // ILimitSysXRef.java
				int countSID = custDao.getSIDCount(sId);
				if (countSID > 0) {

					String lineId = custDao.getEcbfLineUsingSID(sId + "");
					int countLineId = custDao.getLineCount(lineId);

					if (countLineId > 0) {
						radioInterfaceCompleted = "Checked";

						List ResReqDate = custDao.getUpdatedResReqUsingLineId(lineId);
						if (null != ResReqDate && !ResReqDate.isEmpty()) {

							String[] ResReqDateValues = (String[]) ResReqDate.get(0);

							responseDate = ResReqDateValues[0];
							requestDate = ResReqDateValues[1];

						}
					}
				}

			}
			result.put("radioInterfaceCompleted", radioInterfaceCompleted);
			result.put("responseDate", responseDate);
			result.put("requestDate", requestDate);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		result.put("sessionCriteria",map.get("sessionCriteria"));
		result.put("fundedAmount", getRequestData(map, covenantMap, event, "fundedAmount"));
		result.put("nonFundedAmount", getRequestData(map, covenantMap, event, "nonFundedAmount"));
		result.put("memoExposer", getRequestData(map, covenantMap, event, "memoExposer"));
		result.put("sanctionedLimit", getRequestData(map, covenantMap, event, "sanctionedLimit"));
		result.put("isCreate", getRequestData(map, covenantMap, event, "isCreate"));
		result.put("inrValue", map.get("inrValue"));
		
		
		
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
		
	
	
	}

	private List getSourceSystemCountryList() {
		List lbValList = new ArrayList();
		List idList = (List) (CountryList.getInstance().getCountryValues());
		List valList = (List) (CountryList.getInstance().getCountryLabels());
		for (int i = 0; i < idList.size(); i++) {
			String id = idList.get(i).toString();
			String val = valList.get(i).toString();
			LabelValueBean lvBean = new LabelValueBean(val, id);
			lbValList.add(lvBean);
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSourceSystemNameList(String country) {
		List lbValList = new ArrayList();
		if ((country != null) && !country.trim().equals("")) {
			HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("ACCT_SOURCE", null, country);
			Object[] keyArr = map.keySet().toArray();
			for (int i = 0; i < keyArr.length; i++) {
				Object nextKey = keyArr[i];
				LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
				lbValList.add(lvBean);
			}
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSubSecurityList(List lst) {
		List lbValList = new ArrayList();
		try {

			for (int i = 0; i < lst.size(); i++) {
				String[] mgnrLst = (String[]) lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1], mgnrLst[0]);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private Map getSubSecurityListMap(List lst) {
		Map lbValMap = new HashMap();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
			lbValMap.put(mgnrLst[0], mgnrLst[1]);
		}
	} catch (Exception ex) {
	}
	return lbValMap;
}
	
	private String dateFormater(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(d.toString());
			System.out.println("Date is: " + date);
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
			String strDate = formatter1.format(date);
			System.out.println("Date Format with MM/dd/yyyy : " + strDate);
			return strDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getRequestData(Map resultMap, Map covenantMap, String event, String key) {
		return EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event)?(String) covenantMap.get(key):(String) resultMap.get(key);
	}
	
	
	private List  addCoBorrowerToForm( OBCustomerSysXRef ob) {

		ILimitXRefCoBorrower[] coBorrowerList = ob.getXRefCoBorrowerData();
		ILimitXRefCoBorrower coBorro;
		List coBorrow= new ArrayList();
		if (coBorrowerList != null && coBorrowerList.length > 0) {
			System.out.println("coBorrowerList.length in Mapper is:: "+coBorrowerList.length);
			
			for(int i=0; i<coBorrowerList.length; i++) {
				OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
			coBorro = coBorrowerList[i];
	    //    form.setUdfId(coBorro.getId());
			form1.setCoBorrowerId(coBorro.getCoBorrowerId());
			form1.setCoBorrowerName(coBorro.getCoBorrowerName());
	 	   
			coBorrow.add(form1);
			//   form.setRestCoBorrowerList(form1);
			}
			
			//   form.setRestCoBorrowerList(coBorrow);
	 			}
		return coBorrow;
	 		}

	

}
