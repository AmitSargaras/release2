/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.line.covenant.CovenantMappingHelper;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MakerUpdateLmtDetailCmd extends AbstractCommand {
	
	
	
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE }, });

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
		ILimit lmt = (ILimit) (map.get("lmtDetailForm"));
	/*	IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}*/

		Date applicationDate=new Date();
		try {
			DateFormat dtval=new SimpleDateFormat("dd/MM/yyyy");
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			}catch(Exception e) {
				System.out.println("Exception in MakerUpdateLmtDetailCmd.java at line 109=>e=>"+e);
				e.printStackTrace();
			}
			
			
			try {

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			ILimit stgLmt = lmtTrxObj.getStagingLimit();
			/*long id = lmtTrxObj.getCustomerID();
			LabelValueBean bean = new LabelValueBean();
			List lb = (List) getSysID(lmt.getFacilitySystem(), String.valueOf(id));
			if(lb!=null)
			{
				bean = (LabelValueBean) lb.get(0);
			}*/
			
			boolean flag = true;
			if(lmtTrxObj.getLimit() != null )
			{
				String lmtId = (String)lmtTrxObj.getLimit().getLimitRef();
				LimitDAO limitDao = new LimitDAO();
				String trxStatusOfFacUpdateUpload = limitDao.getFacilityUpdateUploadTransactionStatus(lmtId);
				System.out.println("MakerUpdateLmtDetailCmd.java=>trxStatusOfFacUpdateUpload=>"+trxStatusOfFacUpdateUpload);
				if (trxStatusOfFacUpdateUpload.startsWith("PENDING_")) {
					result.put("wip", "wip");
					flag = false;
				}
			}
			
			if(flag == true) {

		if (stgLmt != null) {
			ILimitSysXRef[] refArr = stgLmt.getLimitSysXRefs();
			if (refArr != null) {
				for(int i=0;i<refArr.length;i++){
					ILimitSysXRef limitSysXref = refArr[i];
					limitSysXref.getCustomerSysXRef().setLineNo(lmt.getLineNo());
					limitSysXref.getCustomerSysXRef().setFacilitySystem(lmt.getFacilitySystem());
			//		limitSysXref.getCustomerSysXRef().setFacilitySystemID(bean.getValue());
					
					limitSysXref.getCustomerSysXRef().setCurrency(lmt.getCurrencyCode());
				}
			}
			}
		
		ILimit  lmtAct = lmtTrxObj.getLimit();
		ILimit  lmtStg = lmtTrxObj.getStagingLimit();
		ILimitSysXRef[] refArrStg = null;
		String updateBy ="";
		Date updateOn = new Date();
		String createBy ="";
		Date createOn = new Date();
		if (lmtAct != null) {
			ILimitSysXRef[] refArr = lmtAct.getLimitSysXRefs();
			if (refArr != null && refArr.length>0) {
				for(int i=0;i<refArr.length;i++){
					ICustomerSysXRef objAct = refArr[i].getCustomerSysXRef();
					refArrStg = lmtStg.getLimitSysXRefs();
					for(int j=0;j<refArrStg.length;j++){
						ICustomerSysXRef objStg = refArrStg[j].getCustomerSysXRef();
						if(objStg.getSerialNo().equals(objAct.getSerialNo())){
							objStg.setUpdatedBy(objAct.getUpdatedBy());
							objStg.setUpdatedOn(objAct.getUpdatedOn());
							
							if(null != objStg.getStatus()) {
								  if((("DRAFT").equalsIgnoreCase(lmtTrxObj.getStatus()) || ("REJECTED").equalsIgnoreCase(lmtTrxObj.getStatus())) && objStg.getStatus().contains("PENDING_")) {
								   System.out.println("=======Inside MakerUpdateLmtDetail Cmd at line 159=======line SerialNo() having transaction satus draft/rejected and line status : pending_update is : "+objStg.getSerialNo()+"\n");
								   objStg.setCreatedBy(user.getLoginID());
								   objStg.setCreatedOn(applicationDate);	
								 }
								}

						}else if(objStg.getSerialNo().equals(objAct.getHiddenSerialNo())){
							objStg.setUpdatedBy(objAct.getUpdatedBy());
							objStg.setUpdatedOn(objAct.getUpdatedOn());
							
							if(null != objStg.getStatus()) {
								  if((("DRAFT").equalsIgnoreCase(lmtTrxObj.getStatus()) || ("REJECTED").equalsIgnoreCase(lmtTrxObj.getStatus())) && objStg.getStatus().contains("PENDING_")) {
								   System.out.println("=======Inside MakerUpdateLmtDetail Cmd at line 159=======line SerialNo() having transaction satus draft/rejected and line status : pending_update is : "+objStg.getSerialNo()+"\n");
								   objStg.setCreatedBy(user.getLoginID());
								   objStg.setCreatedOn(applicationDate);	
								 }
								}
						}
						refArrStg[j].setCustomerSysXRef(objStg);
					}
				}
			}// Added By Dayananda on 22-April-2015 || Facility with zero Tranch details update production fix || Starts
			else{
				refArrStg = lmtStg.getLimitSysXRefs();
				if(refArrStg!=null && refArrStg.length>0){
					for(int j=0;j<refArrStg.length;j++){
						ICustomerSysXRef objStg = refArrStg[j].getCustomerSysXRef();
						refArrStg[j].setCustomerSysXRef(objStg);
					}
				}
			}// Added By Dayananda on 22-April-2015 || Facility with zero Tranch details update production fix || Ends
		}else{ // Added By Dayananda on 28-April-2015 || Facility with zero Tranch details update production fix || Starts
			refArrStg = lmtStg.getLimitSysXRefs();
			if(refArrStg!=null && refArrStg.length>0){
				for(int j=0;j<refArrStg.length;j++){
					ICustomerSysXRef objStg = refArrStg[j].getCustomerSysXRef();
					refArrStg[j].setCustomerSysXRef(objStg);
				}
			}// Added By Dayananda on 28-April-2015 || Facility with zero Tranch details update production fix || Ends
		}
		
		lmtStg.setLimitSysXRefs(refArrStg);
		lmtTrxObj.setStagingLimit(lmtStg);
		
		    lmtTrxObj.setStagingLimit(stgLmt);
			MILimitUIHelper helper = new MILimitUIHelper();
			CovenantMappingHelper covHelper= new CovenantMappingHelper();
			helper.setTrxLocation(ctx, lmtTrxObj.getStagingLimit());
			SBMILmtProxy proxy = helper.getSBMILmtProxy();

			Boolean chkResult = helper.checkSecurityNotReq(lmt);
			if ((chkResult != null) && chkResult.booleanValue()) {
				// security is not required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secNotReq"));
			}
			if ((chkResult != null) && !chkResult.booleanValue()) {
				// security is required
				exceptionMap.put("secNotReq", new ActionMessage("error.milimit.secReq"));
			}
			if("Y".equals(lmt.getIsReleased())){
				if(!(lmt.getLimitSysXRefs()!=null && lmt.getLimitSysXRefs().length>0)){
					exceptionMap.put("lineDetailsError", new ActionMessage("error.line.details.mandatory.if.released"));
				}
			}
			
			if(null != lmt.getReleasableAmount() && !"".equals(lmt.getReleasableAmount())) {
			BigDecimal releasableAmount = new BigDecimal(lmt.getReleasableAmount());
			if("Y".equals(lmt.getIsReleased())){
				
				try {
				
				if((lmt.getLimitSysXRefs()!=null && lmt.getLimitSysXRefs().length>0)){
					
					for(int i=0;i<lmt.getLimitSysXRefs().length;i++) {
						System.out.println("MakerUpdateLmtDetailCmd.java.java => 193=>lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()=>"+lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()+"***releasableAmount=>"+releasableAmount+"**lmt.getLimitSysXRefs().length=>"+lmt.getLimitSysXRefs().length);
						if(null != lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount() && !"".equals(lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount())) {
							
							System.out.println("MakerUpdateLmtDetailCmd.java => 195=>lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()=>"+lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()+"***");
							BigDecimal idlAmount =new BigDecimal("0");
							System.out.println("MakerUpdateLmtDetailCmd.java.java => 198=>lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()=>"+lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount()+"**");
							idlAmount =	new BigDecimal(lmt.getLimitSysXRefs()[i].getCustomerSysXRef().getIdlAmount());
							
							System.out.println("MakerUpdateLmtDetailCmd.java.java => 201=>idlAmount=>"+idlAmount+"**");
							
							int flags = releasableAmount.compareTo(idlAmount);
							System.out.println("MakerUpdateLmtDetailCmd.java.java => 204=>flags=>"+flags);
						if(flags == -1) {
							exceptionMap.put("releasableAmount", new ActionMessage("error.amount.not.greaterthan", "IDL Amount", "Releasable Amount"));
						}
						}
					}
					
					//exceptionMap.put("lineDetailsError", new ActionMessage("error.line.details.mandatory.if.released"));
				}
			}catch(Exception e) {
				System.out.println("Exception in MakerUpdateLmtDetailCmd.java.java => 217=>After idlAmount validations");
				e.printStackTrace();
			}
				
			}
			}
			System.out.println("MakerUpdateLmtDetailCmd.java.java => 214=>After idlAmount validations");
			
			if("Y".equals(lmt.getIsSecured())){
				if(!(lmt.getCollateralAllocations()!=null && lmt.getCollateralAllocations().length>0)){
					exceptionMap.put("securityMappingDetailsError", new ActionMessage("error.securityMapping.details.mandatory.if.secured"));
				}
			}
			
			//Stock DP
			boolean isLimitReleaseAmtMoreThanDp = false;
			
			if(!ArrayUtils.isEmpty(lmt.getCollateralAllocations())) {
				for(ICollateralAllocation colAllocation : lmt.getCollateralAllocations()) {
					if(colAllocation.getCollateral() != null && ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE.equals(colAllocation.getCollateral().getSCISubTypeValue()) && 
							ICMSConstant.YES.equals(colAllocation.getCollateral().getSecPriority())){
						BigDecimal calculatedDp = CollateralDAOFactory.getDAO().getCalculatedDpForGCCollateral(colAllocation.getCollateral().getCollateralID());
						String totalReleasedAmtStr = lmt.getTotalReleasedAmount();
						if(StringUtils.isNotBlank(totalReleasedAmtStr)) {
							BigDecimal totalReleasedAmt = MapperUtil.stringToBigDecimal(totalReleasedAmtStr);
							if(totalReleasedAmt != null && calculatedDp !=null ) {
								isLimitReleaseAmtMoreThanDp = totalReleasedAmt.compareTo(calculatedDp)>1;
							}
						}
					}
				}
			}
			
			if(isLimitReleaseAmtMoreThanDp) {
				exceptionMap.put("totalReleasedAmount", new ActionMessage("error.total.released.amt.less.drawing.power"));
			}
			
			if (exceptionMap.size() == 0) {
				String minGrade = LimitsOfAuthorityMasterHelper.getMinimumEmployeeGradeForLOA(lmtTrxObj, null, null);
				lmtTrxObj.setMinEmployeeGrade(minGrade);
				
				helper.syncBankingArrangementAtLineFromFacility(lmtTrxObj.getStagingLimit());
				//to map facility level covenant to line level covenant
				covHelper.mapFacilityCovToLineCov(lmtTrxObj);
				ICMSTrxResult res = proxy.makerUpdateLmtTrx(ctx, lmtTrxObj, false);
				result.put("request.ITrxResult", res);
			}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getSysID(String systemName, String custID) {
		List lbValList = new ArrayList();
		try {
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List sysIDs = proxy.getSystemID(systemName, custID);
			
			
			  Iterator iterator = sysIDs.iterator();
			  while(iterator.hasNext()){
				  String id = iterator.next().toString();
				  LabelValueBean lvBean = new LabelValueBean(id, id);
				lbValList.add(lvBean);
			 }
		
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getVendorDtls(String custID) {
		List lbValList = new ArrayList();
		try {
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			List vendorName = proxy.getVendorDtls(custID);
			
			
			  Iterator iterator = vendorName.iterator();
			  while(iterator.hasNext()){
				  String id = iterator.next().toString();
				  LabelValueBean lvBean = new LabelValueBean(id, id);
				lbValList.add(lvBean);
			 }
		
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
}
