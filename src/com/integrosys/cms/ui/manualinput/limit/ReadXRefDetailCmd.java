/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UDFComparator;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadXRefDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "lineDetailId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				{ "xrefDetailFormObj",OBCustomerSysXRef.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ "xrefDetailForm", "java.lang.Object", FORM_SCOPE }, 
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "lineDetailId", "java.lang.String", REQUEST_SCOPE },
				{"viewSecurityMap","java.util.Map",REQUEST_SCOPE},
				{"udfLbValList1", "java.lang.String", SERVICE_SCOPE }, 
				{"udfLbValList1_2", "java.lang.String", SERVICE_SCOPE }, 
				 {"viewSecurityMap","java.util.Map",REQUEST_SCOPE},
				 { "branchAllowedList1", "java.util.List", SERVICE_SCOPE },
				 { "productAllowedList1", "java.util.List", SERVICE_SCOPE },
				 { "currencyAllowedList1", "java.util.List", SERVICE_SCOPE },
				 { "xReferenceId", "java.lang.String", REQUEST_SCOPE },
				 { "lmtId", "java.lang.String", REQUEST_SCOPE },
				 { "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				 { "status", "java.lang.String", REQUEST_SCOPE },
				 { "branchAllowedList", "java.util.List", SERVICE_SCOPE },
				 { "productAllowedList", "java.util.List", SERVICE_SCOPE },
				 { "currencyAllowedList", "java.util.List", SERVICE_SCOPE },
				 { "udfLbValList", "java.util.List", SERVICE_SCOPE },
				 { "udfLbValList_2", "java.util.List", SERVICE_SCOPE },
				 { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "partyId", "java.lang.String", SERVICE_SCOPE },
				{ "scfStatus", "java.lang.String", SERVICE_SCOPE }, 
				{ "scfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "scfFlag", "java.lang.String", SERVICE_SCOPE },
				{ "requestDate", "java.lang.String", SERVICE_SCOPE },
				{ "responseDate", "java.lang.String", SERVICE_SCOPE },
				{ "radioInterfaceCompleted", "java.lang.String", SERVICE_SCOPE },
				 { "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				 { "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
				 { "xRefForLineCov", ILimitSysXRef.class.getName() , SERVICE_SCOPE },
				 
				 
		};
		}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			MILimitUIHelper helper = new MILimitUIHelper();
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			ILimitProfile lmtProfile = (ILimitProfile) map.get("_CMS_UI_GLOBAL_CONSTANT_LIMITPROFILE_OBJ");			
			String lmtProfileId = String.valueOf(lmtTrxObj.getLimitProfileID()); 
			CustomerDAO custDao = new CustomerDAO();
			String partyId = custDao.getPartyId(lmtProfileId);
			String scfStatus = "";
			String scfFlag = "";
			String scfErrMsg = "";
			String responseDate="";
			String requestDate="";
			String radioInterfaceCompleted="NotChecked";
			
			String event = (String) map.get("event");
			//int index = Integer.parseInt((String) map.get("indexID"));
			
			String index =(String) map.get("indexID");
			String lineDetailId = (String) map.get("lineDetailId"); //LINE ID
			// from_event will be "read" at maker view xref list 
			// from_event will be "process" at checker review xref list
			// from_event will be "close" at maker close rejected xref detail
			// from_event will be null at maker update xref list
			// only for "read" case, xref should be populated from the actual
			// object
			// for the other 3 cases xref should be populated from the staging
			// object
			String from_event = (String) map.get("fromEvent");
			result.put("from_event", from_event);
			String sourceRefNo=(String) map.get("sourceRefNo");
			System.out.println("sourceRefNo:"+sourceRefNo);
			ILimitSysXRef xref=null;
			ICustomerSysXRef account = null;
			
				
			if(EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event)) {
				account = (ICustomerSysXRef) map.get("xrefDetailFormObj");
			}
			else {
				if ("updateStatus".equals(from_event) && "updateStatus_ubs_error".equals(event)) {
					xref = helper.getCurLmtSysXRefUpdateSta(event, from_event, sourceRefNo, lmtTrxObj);
				} else {
					xref = helper.getCurWorkingLimitSysXRef(event, from_event, index, lmtTrxObj);
				}
				account = xref.getCustomerSysXRef();
				result.put("xRefForLineCov", xref);
			}
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Event "+event + " from_event "+from_event+ " index "+index+ " lmttrxobj "+lmtTrxObj);
			System.out.println("xref "+xref);
			
//			System.out.println("xref cust sys "+xref.getCustomerSysXRef());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			OBCustomerSysXRef curAccount = null;
			if(null!=xref) {
				curAccount = (OBCustomerSysXRef) xref.getCustomerSysXRef();
				if (curAccount.getHiddenSerialNo() == null || "".equals(curAccount.getHiddenSerialNo())) {
					curAccount.setHiddenSerialNo(curAccount.getSerialNo());
				}
			}
			
			long refernceId =  account.getXRefID();
			System.out.println("refernceId "+refernceId);
			IFCCBranchDao fccBranchDao = (IFCCBranchDao) BeanHouse.get("fccBranchDao");
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
				
				ex.printStackTrace();
			}
			
		/*	ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			List vNames = new ArrayList();
			if(lmtTrxObj.getReferenceID()!=null) {
				System.out.println("lmtTrxObj.getReferenceID()==========="+lmtTrxObj.getReferenceID());
				vNames= lmtDao.getRestrictedCoBorrowerForLine(lmtTrxObj.getReferenceID());
			
				result.put("restCoBorrowerList",vNames);
			}*/
			
			branchAllowedList = CommonUtil.sortDropdown(branchAllowedListTemp);
			result.put("branchAllowedList", branchAllowedList);
			
			String branchAllowed=(String) (map.get("branchAllowed"));
			List branchAllowedList1=new ArrayList();
			if(null==branchAllowed)
				branchAllowed=account.getBranchAllowed();
			
			if(null!=branchAllowed) {
				List<String> items = Arrays.asList(branchAllowed.split(","));
           	 	for(int i=0;i<branchAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)branchAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		branchAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	
           	
			}
			result.put("branchAllowedList1", branchAllowedList1);
			
			IProductMasterDao productMasterDao = (IProductMasterDao) BeanHouse.get("productMasterDao");
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
				ex.printStackTrace();
			}
			
			productAllowedList = CommonUtil.sortDropdown(productMasterLbValList);
			result.put("productAllowedList", productAllowedList);
			
			String productAllowed=(String) (map.get("productAllowed"));
			List productAllowedList1=new ArrayList();
			if(null==productAllowed)
				productAllowed=account.getProductAllowed();
			
			if(null!=productAllowed) {
				List<String> items = Arrays.asList(productAllowed.split(","));
           	 	for(int i=0;i<productAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)productAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		productAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	/*if(null!=productAllowedList1 && productAllowedList1.size()>0)
           	 	productAllowedList.removeAll(productAllowedList1);*/
           	
			}
			
			ILimitDAO dao = LimitDAOFactory.getDAO();
			List currencyAllowedList = dao.getCurrencyList();
			result.put("currencyAllowedList", currencyAllowedList);
		
			String currencyAllowed=(String) (map.get("currencyAllowed"));
			List currencyAllowedList1=new ArrayList();
			if(null==currencyAllowed)
				currencyAllowed=account.getCurrencyAllowed();
			
			if(null!=currencyAllowed) {
				List<String> items = Arrays.asList(currencyAllowed.split(","));
           	 	for(int i=0;i<currencyAllowedList.size();i++) {
           	 		LabelValueBean lvBean=(LabelValueBean)currencyAllowedList.get(i);
           	 		if(items.contains(lvBean.getValue())) {
           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
           	 		currencyAllowedList1.add(lvBean1);
           	 
           	 		}
           	 	}
           	 	/*if(null!=currencyAllowedList1 &&currencyAllowedList1.size()>0)
           	 	currencyAllowedList.removeAll(currencyAllowedList1);*/
			}
		
			System.out.println("limitId:"+(String) map.get("limitId")+" lmtId:"+(String) map.get("lmtId"));
			String lmtID = (String) (map.get("limitId"));
			if(map.get("lmtId") != null){
						lmtID = (String) (map.get("lmtId")); //Shiv 231111
					}
									
           	result.put("productAllowedList", productAllowedList);
 			result.put("productAllowedList1", productAllowedList1);
 			result.put("currencyAllowedList1", currencyAllowedList1);
 			result.put("restCoBorrowerList",fetchCoBorrowerFromOb(curAccount));
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			result.put("viewSecurityMap", getSubSecurityList(proxy.getSubSecurityList(lmtTrxObj.getLimitProfileID())));
			result.put("xrefDetailForm", account);
			result.put("event", event);
			result.put("fromEventForLineCov", event);
			result.put("lineDetailId", lineDetailId);
			result.put("xReferenceId", refernceId);
			result.put("indexID", String.valueOf(index));
			result.put("lmtId", lmtID);
			result.put("sourceRefNo", sourceRefNo);
			result.put("lmtTrxObj", lmtTrxObj);
			
			//start santosh ubs limit
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
			String udfAllowed=account.getUdfAllowed();
				if(null!=udfAllowed) {
					List<String> items = Arrays.asList(udfAllowed.split(","));
	           	 	for(int i=0;i<udfLbValList.size();i++) {
	           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList.get(i);
	           	 		if(items.contains(lvBean.getValue())) {
	           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
	           	 			udfLbValList1.add(lvBean1);
	           	 		}
	           	 	}
	           //	result.put("udfLbValList1", udfLbValList1);
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
				String udfAllowed_2=account.getUdfAllowed_2();
				System.out.println("udfAllowed_2:::::::::: "+udfAllowed_2);
					if(null!=udfAllowed_2) {
						List<String> items = Arrays.asList(udfAllowed_2.split(","));
		           	 	for(int i=0;i<udfLbValList_2.size();i++) {
		           	 		LabelValueBean lvBean=(LabelValueBean)udfLbValList_2.get(i);
		           	 		if(items.contains(lvBean.getValue())) {
		           	 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
		           	 			udfLbValList1_2.add(lvBean1);
		           	 		}
		           	 	}
		           //	result.put("udfLbValList1", udfLbValList1);
					}
				result.put("udfLbValList1", udfLbValList1);
				result.put("udfLbValList", udfLbValList);
				
				result.put("udfLbValList1_2", udfLbValList1_2);
				result.put("udfLbValList_2", udfLbValList_2);
				
				result.put("sessionCriteria",map.get("sessionCriteria"));
				result.put("status", account.getStatus());
			//end santosh
				
				
				
			
			/*------------SCF LINE------------*/
		//	List scfStatusList = custDao.getScfAndEcbfStatusById(ICMSConstant.SCF, ICMSConstant.LINE, partyId);

			List scfStatusList = new ArrayList();
			
			if (null != curAccount) {
				if(null != curAccount.getHiddenSerialNo() && !curAccount.getHiddenSerialNo().isEmpty())
					DefaultLogger.debug(this,"-------ReadXRefDetailCmd Hidden Serial No------->>"+curAccount.getHiddenSerialNo());
				if(null != curAccount.getSerialNo() && !curAccount.getSerialNo().isEmpty())
					DefaultLogger.debug(this,"-------ReadXRefDetailCmd Serial No------->>"+curAccount.getSerialNo());
			}
			
			if(null!=curAccount) {
				scfStatusList=dao.getScfStatusForLineById(lmtProfileId, curAccount.getLineNo(), curAccount.getSerialNo());
				}else {
					if(null!=account) {
						scfStatusList=dao.getScfStatusForLineById(lmtProfileId, account.getLineNo(), account.getSerialNo());
					}
				}
				System.out.println("scfStatusList"+scfStatusList);
				
			if (null != scfStatusList && !scfStatusList.isEmpty()) {
				
				scfFlag = (String) scfStatusList.get(2);
				if (null != scfFlag ) {
					System.out.println("scfFlag contains values at ReadXRefDetailCmd line 413" + scfFlag);
					
					scfStatus = (String) scfStatusList.get(0);
					System.out.println("scfStatus at ReadXRefDetailCmd line 416" + scfStatus);
					
					if (("Y").equalsIgnoreCase(scfFlag)) {
						if (("Success").equalsIgnoreCase(scfStatus)) {
						} else if (("Failed").equalsIgnoreCase(scfStatus) || ("Fail").equalsIgnoreCase(scfStatus)) {
							scfErrMsg = (String) scfStatusList.get(1);
						} else if (("Error").equalsIgnoreCase(scfStatus)) {
							scfErrMsg = (String) scfStatusList.get(1);
						}
					} else if (("N").equalsIgnoreCase(scfFlag)) {
						
						if(null != scfStatus && !scfStatus.isEmpty()) {
							scfStatus = "Stopped";
						}
					}
				}
			} else if (null == scfStatusList  || scfStatusList.isEmpty())  { // non - scm, where scfStatusList.isEmpty()
				System.out.println("ReadXRefDetailCmd Line 434 -->> setting scfStatus = NA as scfStatusList(no  STATUS,ERRORMESSAGE for given limitProfileId and line_no and serial_no) is empty/null");
				scfStatus = "NA";
			}

			result.put("scfStatus", scfStatus);
			result.put("scfErrMsg", scfErrMsg);

			/*------------ECBF LINE------------*/
			if (null != xref  && xref.getSID() != -999999999) {

				Long sId = xref.getSID(); // ILimitSysXRef.java
				int countSID = custDao.getSIDCount(sId);
				if (countSID > 0) {

					String lineId = custDao.getEcbfLineUsingSID(sId.toString());
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
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	private Map getSubSecurityList(List lst) {
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
	
	private List  fetchCoBorrowerFromOb( OBCustomerSysXRef ob) {

		List coBorrow= new ArrayList();
		ILimitXRefCoBorrower[] coBorrowerList = ob!=null?ob.getXRefCoBorrowerData():null;
		ILimitXRefCoBorrower coBorro;
		
		if (coBorrowerList != null && coBorrowerList.length > 0) {
			System.out.println("coBorrowerList.length in ReadXRefDetailCmd.java is:: "+coBorrowerList.length);
			
			for(int i=0; i<coBorrowerList.length; i++) {
			OBLimitXRefCoBorrower form1 = new OBLimitXRefCoBorrower();
			coBorro = coBorrowerList[i];
			form1.setCoBorrowerId(coBorro.getCoBorrowerId());
			form1.setCoBorrowerName(coBorro.getCoBorrowerName());
	 	   
			coBorrow.add(form1);
			}
		}
		return coBorrow;
	 		}

	
	/*private String dateFormater(Date d) {
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
	}*/
	
}
