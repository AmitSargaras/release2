/*
 * Created on 2007-2-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxyHome;
import com.integrosys.cms.app.manualinput.limit.trx.MILmtTrxHelper;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MILimitUIHelper {
	public SBMILmtProxy getSBMILmtProxy() {
		return (SBMILmtProxy) (BeanController.getEJB(ICMSJNDIConstant.SB_MI_LMT_PROXY_JNDI, SBMILmtProxyHome.class
				.getName()));
	}

	public List deleteItem(List itemOrig, String[] deleteInd) {
		if ((itemOrig != null) && (deleteInd != null)) {
			List tempList = new ArrayList();
			for (int i = 0; i < deleteInd.length; i++) {
				int nextDelInd = Integer.parseInt(deleteInd[i]);
				tempList.add(itemOrig.get(nextDelInd));
			}
			itemOrig.removeAll(tempList);
			tempList.clear();
		}
		return itemOrig;
	}

	public List deleteItem(List itemOrig, String deleteInd) {
		if ((itemOrig != null) && (deleteInd != null)) {
			List tempList = new ArrayList();
			//for (int i = 0; i < deleteInd.length; i++) {
			int nextDelInd = Integer.parseInt(deleteInd);
			tempList.add(itemOrig.get(nextDelInd));
			//}
			itemOrig.removeAll(tempList);
			tempList.clear();
		}
		return itemOrig;
	}

	public ILimitSysXRef getCurWorkingLimitSysXRef(String event, String fromEvent, String index, ILimitTrxValue trxValue) {
		ILimit lmt = null;
		if (EventConstant.EVENT_READ.equals(fromEvent)) {
			lmt = trxValue.getLimit();
			if(lmt==null)
			{
				lmt = trxValue.getStagingLimit();
			}
		}
		else if (("update".equals(fromEvent) && "prepare_update".equals(event)) || ("update".equals(fromEvent) && "prepare_update_ubs".equals(event))
				|| ("update".equals(fromEvent) && "prepare_update_ts".equals(event)) || ("update".equals(fromEvent) && "prepare_update_ts_rejected".equals(event))
				||("update".equals(fromEvent) && "prepare_update_rejected".equals(event)) || ("update".equals(fromEvent) && "prepare_update_ubs_rejected".equals(event))
				|| ("update".equals(fromEvent) && "edit_released_line_details".equals(event))
				|| ("update".equals(fromEvent) && "edit_udf".equals(event))
				|| ("update".equals(fromEvent) && "prepare_edit_covenant_detail".equals(event))
				|| ("update".equals(fromEvent) && "edit_released_line_details_rejected".equals(event))
				|| ("update".equals(fromEvent) && "edit_udf_rejected".equals(event))
				|| ("close".equals(fromEvent) && "prepare_close_ubs".equals(event)) || ("close".equals(fromEvent) && "close_released_line_details".equals(event))
				|| ("close".equals(fromEvent) && "prepare_close_ubs_rejected".equals(event)) || ("close".equals(fromEvent) && "close_released_line_details_rejected".equals(event))
				|| ("updateStatus".equals(fromEvent) && "prepare_updateStatus_ubs".equals(event))|| ("updateStatus".equals(fromEvent) && "updateStatus_released_line_details".equals(event))
				|| ("updateStatus".equals(fromEvent) && "updateStatus_ubs_error".equals(event)) || ("updateStatus".equals(fromEvent) && "updateStatus_udf".equals(event))
				|| ("close".equals(fromEvent) && "prepare_close_ts".equals(event)) || ("close".equals(fromEvent) && "prepare_close_ts_rejected".equals(event)) 
				|| ("updateStatus".equals(fromEvent) && "prepare_updateStatus_ts".equals(event)) || ("updateStatus".equals(fromEvent) && "updateStatus_ts_error".equals(event))) {
			ILimit  lmtAct = trxValue.getLimit();
			String updateBy ="";
			Date updateOn = new Date();
			String createBy ="";
			Date createOn = new Date();
			if (lmtAct != null) {
				ILimitSysXRef[] refArr = lmtAct.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
						if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null){
						//	if(Integer.parseInt(refArr[i].getCustomerSysXRef().getSerialNo()) == index){
							if(index.equals(refArr[i].getCustomerSysXRef().getFacilitySystemID()+refArr[i].getCustomerSysXRef().getSerialNo())){
								createBy = refArr[i].getCustomerSysXRef().getCreatedBy();
								createOn =  refArr[i].getCustomerSysXRef().getCreatedOn();
								updateBy = refArr[i].getCustomerSysXRef().getUpdatedBy();
								updateOn =  refArr[i].getCustomerSysXRef().getUpdatedOn();
							}
						}
					}
				}
			}
			lmt = trxValue.getStagingLimit();
			if (lmt != null) {
				ILimitSysXRef[] refArr = lmt.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
						if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null && null!=refArr[i].getCustomerSysXRef().getFacilitySystemID()){
							//if(Integer.parseInt(refArr[i].getCustomerSysXRef().getSerialNo()) == index){
							if(index.equals(refArr[i].getCustomerSysXRef().getFacilitySystemID()+refArr[i].getCustomerSysXRef().getSerialNo())){
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedBy(createBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedOn(createOn);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedBy(updateBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedOn(updateOn);
								}
								return refArr[i];
							}
						}
					}
				}
			}
		}
		else if ("view".equals(fromEvent)) {
			ILimit  lmtAct = trxValue.getLimit();
			lmt = trxValue.getStagingLimit();
			if(lmt==null)
			{
				lmt = trxValue.getLimit();
			}
			String updateBy ="";
			Date updateOn = new Date();
			String createBy ="";
			Date createOn = new Date();
			if (lmtAct != null) {
				ILimitSysXRef[] refArr = lmtAct.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
							if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null && null!=refArr[i].getCustomerSysXRef().getFacilitySystemID()){
							//if(Integer.parseInt(refArr[i].getCustomerSysXRef().getSerialNo()) == index){
							if(index.equals(refArr[i].getCustomerSysXRef().getFacilitySystemID()+refArr[i].getCustomerSysXRef().getSerialNo())){
								createBy = refArr[i].getCustomerSysXRef().getCreatedBy();
								createOn =  refArr[i].getCustomerSysXRef().getCreatedOn();
								updateBy = refArr[i].getCustomerSysXRef().getUpdatedBy();
								updateOn =  refArr[i].getCustomerSysXRef().getUpdatedOn();
							}
						}
					}
				}
			}
			lmt = trxValue.getStagingLimit();
			if (lmt != null) {
				ILimitSysXRef[] refArr = lmt.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
						if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null && null!=refArr[i].getCustomerSysXRef().getFacilitySystemID()){
						//	if(Integer.parseInt(refArr[i].getCustomerSysXRef().getSerialNo()) == index){
							if(index.equals(refArr[i].getCustomerSysXRef().getFacilitySystemID()+refArr[i].getCustomerSysXRef().getSerialNo())){
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedBy(createBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedOn(createOn);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedBy(updateBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedOn(updateOn);
								}
								return refArr[i];
							}
						}
					}
				}
			}
		}
		else {
			lmt = trxValue.getStagingLimit();
		}
		if (lmt != null) {
			ILimitSysXRef[] refArr = lmt.getLimitSysXRefs();
			if (refArr != null) {
				for(int i=0;i<refArr.length;i++){
					String serialNo=refArr[i].getCustomerSysXRef().getSerialNo();
					if(null==serialNo || serialNo.equals("")) {
						serialNo = refArr[i].getCustomerSysXRef().getHiddenSerialNo();	
						}
					if(refArr[i].getCustomerSysXRef()!=null && serialNo!=null && null!=refArr[i].getCustomerSysXRef().getFacilitySystemID()){
						//if(Integer.parseInt(refArr[i].getCustomerSysXRef().getSerialNo()) == index){
						if(index.equals(refArr[i].getCustomerSysXRef().getFacilitySystemID()+serialNo)){
							return refArr[i];
						}
					}
				}
			}
		}
		return null;
	}

	public ICollateralAllocation getCurWorkingAllocation(String event, String fromEvent, int index,
			ILimitTrxValue trxValue) {
		ILimit lmt = null;
		if (EventConstant.EVENT_READ.equals(fromEvent)) {
			lmt = trxValue.getLimit();
		}
		else {
			lmt = trxValue.getStagingLimit();
		}
		if (lmt != null) {
			ICollateralAllocation[] allocArr = lmt.getCollateralAllocations();
			if (allocArr != null) {
				return allocArr[index];
			}
		}
		return null;
	}

	public Boolean checkSecurityNotReq(ILimit lmt) {
		boolean isClean = false;
		boolean allColClean = true;
		if ((lmt == null) || (lmt.getLimitSecuredType() == null)) {
			return null;
		}

		if ((lmt.getLimitSecuredType() != null)
				&& lmt.getLimitSecuredType().equals(ICMSConstant.LIMIT_SECURED_TYPE_CLEAN)) {
			isClean = true;
		}

		if (lmt != null) {
			ICollateralAllocation[] allocArr = lmt.getCollateralAllocations();

			if (!isClean && ((allocArr == null) || (allocArr.length == 0))) {
				// security is req
				return Boolean.FALSE;
			}

			if ((allocArr != null) && (allocArr.length > 0)) {
				for (int i = 0; i < allocArr.length; i++) {
					ICollateralAllocation nextColAlloc = allocArr[i];
					DefaultLogger.debug(this, "checkSecurityNotReq, nextColAlloc =" + nextColAlloc);
					ICollateral col = nextColAlloc.getCollateral();
					ICollateralSubType subtype = col.getCollateralSubType();
					if (ICMSConstant.HOST_STATUS_DELETE.equals(nextColAlloc.getHostStatus())) {
						continue;
					}
					if (isClean && !subtype.getSubTypeCode().substring(0, 2).equals(ICMSConstant.SECURITY_TYPE_CLEAN)) {
						// security is not req
						return Boolean.TRUE;
					}

					if (!subtype.getSubTypeCode().substring(0, 2).equals(ICMSConstant.SECURITY_TYPE_CLEAN)) {
						allColClean = false;
					}
				}

				if (!isClean && allColClean) {
					// security is req
					return Boolean.FALSE;
				}
			}

		}
		return null;
	}

	// reorder the list according to inner outer limit relationship
	// all inner limits belong to a outer limit will direct follow it
	// besides it will also convert LimitListSummaryItemBase to
	// LimitListSummaryItem
	// which provide various display formating methods
	public List formatLimitListView(List origList, Locale locale) {
		List result = new ArrayList();
		if (origList != null) {
			// first round add all outer limits
			for (int i = 0; i < origList.size(); i++) {
				LimitListSummaryItemBase curItem = (LimitListSummaryItemBase) (origList.get(i));
				if ((curItem.getOuterLimitId() == null) || curItem.getOuterLimitId().trim().equals("")
						|| curItem.getOuterLimitId().trim().equals("0")) {
					result.add(new LimitListSummaryItem(curItem, locale));
				}
			}
			// then add inner limit belongs to the outer limit
			outer: for (int i = 0; i < origList.size(); i++) {
				LimitListSummaryItemBase curItem = (LimitListSummaryItemBase) (origList.get(i));
				if ((curItem.getOuterLimitId() != null) && !(curItem.getOuterLimitId().trim().equals(""))
						&& !(curItem.getOuterLimitId().trim().equals("0"))) {

					for (int j = 0; j < result.size(); j++) {
						LimitListSummaryItem addedItem = (LimitListSummaryItem) (result.get(j));
						String lmtId = addedItem.getCmsLimitId();
						if (lmtId.equals(curItem.getOuterLimitId())) {
							result.add(j + 1, new LimitListSummaryItem(curItem, locale));
							continue outer;
						}
					}
				}
			}
			origList.clear();
		}
		return result;
	}

	public String getOrigBookingCtryDesc(String origBookingCtry) {
		return CountryList.getInstance().getCountryName(origBookingCtry);
	}

	public String getOrigBookingLocDesc(String origBookingCtry, String origBookingLoc) {
		try {
			return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_BKGLOC, null,
					origBookingCtry, origBookingLoc);
		}
		catch (Exception ex) {
		}
		return "";
	}

	public String getFacilityGroupDesc(String facilityGroup) {
		return "";
	}

	public String getFacilityTypeDesc(String facGroup, String agreementType, String prodType) {
		// System.out.println(" inside getProdTypeDesc facGroup : " + facGroup +
		// " & agreementType : " + agreementType + "& prodType : " + prodType);
		if (ICategoryEntryConstant.FACILITY_GRP_BANKING.equals(facGroup)) {
			return CommonDataSingleton.getCodeCategoryLabelByValue(ICategoryEntryConstant.FACILITY_DESCRIPTION,
					prodType);
		}
		else if (ICategoryEntryConstant.FACILITY_GRP_TRADING.equals(facGroup)) {
			// return
			// CommonDataSingleton.getCodeCategoryLabelByValue("FAC_TYPE_TRADE",
			// "GMRA", prodType);
			// System.out.println(
			// " inside getProdTypeDesc CommonDataSingleton.getCodeCategoryLabelByValue(FAC_TYPE_TRADE, prodType) : "
			// +
			// CommonDataSingleton.getCodeCategoryLabelByValue("FAC_TYPE_TRADE",
			// prodType));
			return CommonDataSingleton
			.getCodeCategoryLabelByValue(ICategoryEntryConstant.FACILITY_TYPE_TRADE, prodType);
		}
		return "";
	}

	public String getProductDesc(String value) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(ICategoryEntryConstant.FACILITY_DESCRIPTION, value);
	}

	public String getLmtTenorBasisDesc(String lmtTenorBasis) {
		return CommonDataSingleton.getCodeCategoryLabelByValue("28", lmtTenorBasis);
	}

	public String getHostSystemCountryDisp(String hostSystemCountry) {
		return CountryList.getInstance().getCountryName(hostSystemCountry);
	}

	public String getHostSystemNameDisp(String hostSystemCountry, String hostSystemName) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_SOURCE_SYSTEM, null,
				hostSystemCountry, hostSystemName);
	}

	public String getLoanTypeDisp(String loanType) {
		return CommonDataSingleton.getCodeCategoryLabelByValue("LOAN_TYPE", loanType);
	}

	public String getAccountClassification(Boolean delinqInd) {
		if (delinqInd != null) {
			if (delinqInd.booleanValue()) {
				return "Non-Performing Loan";
			}
			else {
				return "Performing Loan";
			}
		}
		else {
			return "";
		}
	}

	public String getLimitSecuredDesc(String value) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.CATEGORY_CODE_LIMIT_SECURED, value);
	}

	public String getAccountStatusDisp(String accountStatus) {
		return CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.ACCOUNT_STATUS, accountStatus);
	}

	public void setTrxLocation(OBTrxContext ctx, ILimit lmt) {
		IBookingLocation loc = lmt.getBookingLocation();
		if (loc != null) {
			ctx.setTrxCountryOrigin(loc.getCountryCode());
			ctx.setTrxOrganisationOrigin(loc.getOrganisationCode());
		}
	}

	// hide limit-xref list and limit-sec list for MR user
	public static boolean checkShowSublist(HashMap map) {
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean checkLimitSecMappingChanged(ILimitTrxValue trxValue) {
		MILmtTrxHelper helper = new MILmtTrxHelper();
		return helper.checkLimitSecMappingChanged(trxValue);

	}

	public ILimitSysXRef getCurLmtSysXRefUpdateSta(String event, String fromEvent, String sourceRefNo, ILimitTrxValue trxValue) {
		ILimit lmt = null;
		if (("updateStatus".equals(fromEvent) && "updateStatus_ubs_error".equals(event)) ) {
			ILimit  lmtAct = trxValue.getLimit();
			String updateBy ="";
			Date updateOn = new Date();
			String createBy ="";
			Date createOn = new Date();
			if (lmtAct != null) {
				ILimitSysXRef[] refArr = lmtAct.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
						if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null){
							if(sourceRefNo.equals(refArr[i].getCustomerSysXRef().getSourceRefNo())){
								createBy = refArr[i].getCustomerSysXRef().getCreatedBy();
								createOn =  refArr[i].getCustomerSysXRef().getCreatedOn();
								updateBy = refArr[i].getCustomerSysXRef().getUpdatedBy();
								updateOn =  refArr[i].getCustomerSysXRef().getUpdatedOn();
							}
						}
					}
				}
			}
			lmt = trxValue.getStagingLimit();
			if (lmt != null) {
				ILimitSysXRef[] refArr = lmt.getLimitSysXRefs();
				if (refArr != null) {
					for(int i=0;i<refArr.length;i++){
						if(refArr[i].getCustomerSysXRef()!=null && refArr[i].getCustomerSysXRef().getSerialNo()!=null){
							if(sourceRefNo.equals(refArr[i].getCustomerSysXRef().getSourceRefNo())){
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedBy(createBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setCreatedOn(createOn);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedBy(updateBy);
								}
								if(!"".equals(createBy)){
								refArr[i].getCustomerSysXRef().setUpdatedOn(updateOn);
								}
								return refArr[i];
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public void syncBankingArrangementAtLineFromFacility(ILimit stage) {
		if(isReqSyncBankingArrAtLineBySystem(stage.getFacilitySystem())) {
			String bankingArrangement = stage.getBankingArrangement();
			ILimitSysXRef[] lineMaps = stage.getLimitSysXRefs();
			if(lineMaps != null) {
				for(ILimitSysXRef lineMap : lineMaps) {
					ICustomerSysXRef line = lineMap.getCustomerSysXRef();
					line.setBankingArrangement(bankingArrangement);
				}
			}
		}
	}
	
	public boolean isReqSyncBankingArrAtLineBySystem(String system) {
		if("ET".equals(system) || "METG".equals(system)
				|| "UBS-LIMITS".equals(system)) {
			return true;
		}
		return false;
	}
	
}