package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.ICashFd;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMasterJdbc;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.OBLimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.OBLimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.component.user.app.bus.ICommonUser;

public class LimitsOfAuthorityMasterHelper implements ILimitsOfAuthorityMasterConstant {
	
	public static HashMap validateLoaMaster(Collection<OBLimitsOfAuthorityMaster> resultList, OBLimitsOfAuthorityMaster limitsOfAuthorityMasterForm,
			HashMap exceptionMap, ILimitsOfAuthorityMaster stagingLoa) {
		
		ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");		
		Map<String, List<OBLimitsOfAuthorityMaster>> loaSegmentMap = null;
		List<OBLimitsOfAuthorityMaster> selectedSegmentLimitsMasters = null;
		
		
		if(resultList != null) {			
			//Initialize map
			loaSegmentMap = new HashMap<String, List<OBLimitsOfAuthorityMaster>>();
			Map<String,Set<String>> loaGradeMap = new HashMap<String, Set<String>>();
			
			for(OBLimitsOfAuthorityMaster limitsMaster : resultList) {
				String curEmpSegment = limitsMaster.getSegment();
				List<OBLimitsOfAuthorityMaster> limitsMasterList = loaSegmentMap.get(curEmpSegment);
				if(limitsMasterList == null) {
					limitsMasterList = new ArrayList<OBLimitsOfAuthorityMaster>();
				}
				limitsMasterList.add(limitsMaster);
				loaSegmentMap.put(curEmpSegment, limitsMasterList);
				
				//
				String curEmpGrade = limitsMaster.getEmployeeGrade();
				Set<String> segments = loaGradeMap.get(curEmpGrade);
				if(segments == null) {
					segments = new HashSet<String>();
				}
				segments.add(limitsMaster.getSegment());
				loaGradeMap.put(curEmpGrade, segments);
			}
			
			selectedSegmentLimitsMasters = loaSegmentMap.get(limitsOfAuthorityMasterForm.getSegment());
			
//			if(loaMasterJdbc.getRankingSeqByActualLoaMaster() != null) {
//				if(limitsOfAuthorityMasterForm.getRankingOfSequence() != null) {
//		    		exceptionMap = validateLoaMasterEmpGrade(exceptionMap, loaMasterJdbc.getRankingSeqByActualLoaMaster(), limitsOfAuthorityMasterForm);
//		    	}
//			}else {
//				if(limitsOfAuthorityMasterForm.getRankingOfSequence() != 1) {
//					exceptionMap.put("employeeGradeError", new ActionMessage("error.lower.emp.grade.not.maintained"));				
//				}	
//			}
			
			if(!CollectionUtils.isEmpty(selectedSegmentLimitsMasters)) {
				for(OBLimitsOfAuthorityMaster loa : selectedSegmentLimitsMasters) {
					if(limitsOfAuthorityMasterForm.getEmployeeGrade().equals(loa.getEmployeeGrade())){
						if(stagingLoa== null || (!(stagingLoa.getEmployeeGrade().equals(limitsOfAuthorityMasterForm.getEmployeeGrade()) ))) {
							exceptionMap.put("employeeGradeError", new ActionMessage("error.duplicate.emp.grade.and.segment"));
							break;
						}
					}
					
				}
			}
			
			if(!CollectionUtils.isEmpty(loaGradeMap)) {
				
				Set<String> segments = loaGradeMap.get(limitsOfAuthorityMasterForm.getEmployeeGrade());
				
				if(!CollectionUtils.isEmpty(segments)) {
					if("ALL".equals(limitsOfAuthorityMasterForm.getSegment()) && !segments.contains("ALL")) {
						exceptionMap.put("segmentError", new ActionMessage("error.segment.all.cannot.be.selected"));
					}
					if(segments.contains("ALL") && !"ALL".equals(limitsOfAuthorityMasterForm.getSegment())) {
						exceptionMap.put("segmentError", new ActionMessage("error.segment.all.already.selected"));
					}
				}
			}
			
			
		}
		
		if(exceptionMap.isEmpty()) {
			
		    if(limitsOfAuthorityMasterForm.getTotalSanctionedLimit()!= null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT );
		    }
		    if(limitsOfAuthorityMasterForm.getLimitReleaseAmt() != null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_LIMIT_RELEASE_AMT );
		    }
		    if(limitsOfAuthorityMasterForm.getPropertyValuation() != null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_PROPERTY_AMT );
		    }
		    if(limitsOfAuthorityMasterForm.getFdAmount() != null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_FD_AMT );
		    }
		    if(limitsOfAuthorityMasterForm.getDrawingPower() != null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_DRAWING_POWER );
		    }
		    if(limitsOfAuthorityMasterForm.getSblcSecurityOmv() != null) {
		    	exceptionMap = validateLoaMasterAmount(exceptionMap, selectedSegmentLimitsMasters, limitsOfAuthorityMasterForm, LOA_MASTER_FIELD_SBLC_SECURITY_OMV );
		    }
		}
		
		return exceptionMap;
	}
	
	private static HashMap validateLoaMasterAmount(HashMap exceptionMap,List<OBLimitsOfAuthorityMaster> resultList, 
			OBLimitsOfAuthorityMaster limitsOfAuthorityMasterForm, String amountType) {
		
		String errorFieldStr = getErrorFieldStr(amountType);
		
		if(!CollectionUtils.isEmpty(resultList)) {
			Collections.sort(resultList, getComparator(amountType));
			
			for(OBLimitsOfAuthorityMaster loaMaster : resultList) {
				
				BigDecimal loaMasterAmount = null;
				BigDecimal userLoaAmount = null;
				
				if(LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT.equals(amountType)) {
					loaMasterAmount = loaMaster.getTotalSanctionedLimit();
					userLoaAmount = limitsOfAuthorityMasterForm.getTotalSanctionedLimit();
				}
				else if(LOA_MASTER_FIELD_LIMIT_RELEASE_AMT.equals(amountType)) {
					loaMasterAmount = loaMaster.getLimitReleaseAmt();
					userLoaAmount = limitsOfAuthorityMasterForm.getLimitReleaseAmt();
				}
				else if(LOA_MASTER_FIELD_PROPERTY_AMT.equals(amountType)) {
					loaMasterAmount = loaMaster.getPropertyValuation();
					userLoaAmount = limitsOfAuthorityMasterForm.getPropertyValuation();
				}
				else if(LOA_MASTER_FIELD_FD_AMT.equals(amountType)) {
					loaMasterAmount = loaMaster.getFdAmount();
					userLoaAmount = limitsOfAuthorityMasterForm.getFdAmount();
				}
				else if(LOA_MASTER_FIELD_DRAWING_POWER.equals(amountType)) {
					loaMasterAmount = loaMaster.getDrawingPower();
					userLoaAmount = limitsOfAuthorityMasterForm.getDrawingPower();
				}
				else if(LOA_MASTER_FIELD_SBLC_SECURITY_OMV.equals(amountType)) {
					loaMasterAmount = loaMaster.getSblcSecurityOmv();
					userLoaAmount = limitsOfAuthorityMasterForm.getSblcSecurityOmv();
				}
				
				if(limitsOfAuthorityMasterForm.getRankingOfSequence() > loaMaster.getRankingOfSequence() && loaMasterAmount != null && userLoaAmount!=null) {
					if(userLoaAmount.compareTo(loaMasterAmount) <0) {
						exceptionMap.put(errorFieldStr, new ActionMessage("error.loa.amt.greater.than",getErrorFieldName(amountType),limitsOfAuthorityMasterForm.getEmployeeGrade(),loaMaster.getSegment(), loaMaster.getEmployeeGrade()));
						break;
					}
				}
				else if(limitsOfAuthorityMasterForm.getRankingOfSequence() < loaMaster.getRankingOfSequence() && loaMasterAmount != null && userLoaAmount!=null) {
					if(userLoaAmount.compareTo(loaMasterAmount) >0) {
						exceptionMap.put(errorFieldStr, new ActionMessage("error.loa.amt.less.than",getErrorFieldName(amountType),limitsOfAuthorityMasterForm.getEmployeeGrade(),loaMaster.getSegment(), loaMaster.getEmployeeGrade()));
						break;
					}
				}
			}
		}
		
		return exceptionMap;
	}

	private static HashMap validateLoaMasterEmpGrade(HashMap exceptionMap,Integer rankingOfSeq, 
			OBLimitsOfAuthorityMaster limitsOfAuthorityMasterForm) {
			if(limitsOfAuthorityMasterForm.getRankingOfSequence() != 1) {
				if(limitsOfAuthorityMasterForm.getRankingOfSequence() != rankingOfSeq+1) {
					exceptionMap.put("employeeGradeError", new ActionMessage("error.lower.emp.grade.not.maintained"));
				}	
			}
		return exceptionMap;
	}

	
	private static String getErrorFieldName(String amountType) {
		String actionErrorKey = "";
		if(LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT.equals(amountType))
			actionErrorKey = "Total Sanctioned Limit";
		else if(LOA_MASTER_FIELD_LIMIT_RELEASE_AMT.equals(amountType))
			actionErrorKey = "Limit Release Amount";
		else if(LOA_MASTER_FIELD_PROPERTY_AMT.equals(amountType))
			actionErrorKey = "Property Valuation Amount";
		else if(LOA_MASTER_FIELD_FD_AMT.equals(amountType))
			actionErrorKey = "FD Amount";
		else if(LOA_MASTER_FIELD_DRAWING_POWER.equals(amountType))
			actionErrorKey = "Drawing Power Amount";
		else if(LOA_MASTER_FIELD_SBLC_SECURITY_OMV.equals(amountType))
			actionErrorKey = "SBLC Security OMV Amount";
		
		return actionErrorKey;
	}

	private static String getErrorFieldStr(String amountType) {
		String errorFieldStr = "";
		if(LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT.equals(amountType))
			errorFieldStr = "totalSanctionedLimitError";
		else if(LOA_MASTER_FIELD_LIMIT_RELEASE_AMT.equals(amountType))
			errorFieldStr = "limitReleaseAmtError";
		else if(LOA_MASTER_FIELD_PROPERTY_AMT.equals(amountType))
			errorFieldStr = "propertyValuationError";
		else if(LOA_MASTER_FIELD_FD_AMT.equals(amountType))
			errorFieldStr = "fdAmountError";
		else if(LOA_MASTER_FIELD_DRAWING_POWER.equals(amountType))
			errorFieldStr = "drawingPowerError";
		else if(LOA_MASTER_FIELD_SBLC_SECURITY_OMV.equals(amountType))
			errorFieldStr = "sblcSecurityOmvError";
		
		return errorFieldStr;
	}
	
	private static Comparator<OBLimitsOfAuthorityMaster> getComparator(String amountType) {
		Comparator returnVal = null;
		if(LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT;
		else if(LOA_MASTER_FIELD_LIMIT_RELEASE_AMT.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_LIMIT_RELEASE_AMT;
		else if(LOA_MASTER_FIELD_PROPERTY_AMT.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_PROPERTY_AMT;
		else if(LOA_MASTER_FIELD_FD_AMT.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_FD_AMT;
		else if(LOA_MASTER_FIELD_DRAWING_POWER.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_DRAWING_POWER;
		else if(LOA_MASTER_FIELD_SBLC_SECURITY_OMV.equals(amountType))
			returnVal = OBLimitsOfAuthorityMaster.Comparators.LOA_MASTER_FIELD_SBLC_SECURITY_OMV;
		
		return returnVal;
	}

	
	public static List populateRankingOfSequence() {
		
		String rankingOfSeqStr = PropertyManager.getValue("employee.grade.ranking.sequence", "15");
		int rankingOfSeq = Integer.valueOf(rankingOfSeqStr);
		
		List lbValList = new ArrayList();
		for (int i = 1; i <= rankingOfSeq; i++) {
			LabelValueBean lvBean = new LabelValueBean(String.valueOf(i), String.valueOf(i));
			lbValList.add(lvBean);
		}
		return lbValList;
	}
	
	public static List<String> populateRankingOfSequenceMap() {
		Map<Integer,String> rankingMap = new TreeMap<Integer,String>();
		
		String rankingE1 = PropertyManager.getValue("default.employee.grade.ranking.E1", "1");
		String rankingE2 = PropertyManager.getValue("default.employee.grade.ranking.E2", "2");
		String rankingE3 = PropertyManager.getValue("default.employee.grade.ranking.E3", "3");
		String rankingE4 = PropertyManager.getValue("default.employee.grade.ranking.E4", "4");
		String rankingD1 = PropertyManager.getValue("default.employee.grade.ranking.D1", "5");
		String rankingD2 = PropertyManager.getValue("default.employee.grade.ranking.D2", "6");
		String rankingD3 = PropertyManager.getValue("default.employee.grade.ranking.D3", "7");
		String rankingD4 = PropertyManager.getValue("default.employee.grade.ranking.D4", "8");
		String rankingD5 = PropertyManager.getValue("default.employee.grade.ranking.D5", "9");
		String rankingC1 = PropertyManager.getValue("default.employee.grade.ranking.C1", "10");
		String rankingC2 = PropertyManager.getValue("default.employee.grade.ranking.C2", "11");
		String rankingGH = PropertyManager.getValue("default.employee.grade.ranking.GH", "12");
		
		rankingMap.put(Integer.valueOf(rankingE1), "E1");
		rankingMap.put(Integer.valueOf(rankingE2), "E2");
		rankingMap.put(Integer.valueOf(rankingE3), "E3");
		rankingMap.put(Integer.valueOf(rankingE4), "E4");
		rankingMap.put(Integer.valueOf(rankingD1), "D1");
		rankingMap.put(Integer.valueOf(rankingD2), "D2");
		rankingMap.put(Integer.valueOf(rankingD3), "D3");
		rankingMap.put(Integer.valueOf(rankingD4), "D4");
		rankingMap.put(Integer.valueOf(rankingD5), "D5");
		rankingMap.put(Integer.valueOf(rankingC1), "C1");
		rankingMap.put(Integer.valueOf(rankingC2), "C2");
		rankingMap.put(Integer.valueOf(rankingGH), "GH");
		
		List values = Arrays.asList(rankingMap.values().toArray());
		
		return values;
	}
	
	
	public static String getMinimumEmployeeGradeForLOA(ICMSTrxValue itrxValue, OBCMSCustomer customer, String calculatedDP) {
		String minEmpGrade = null;
		try {
			ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
			HashMap loaMap = new HashMap();
			if(itrxValue != null && itrxValue instanceof ICollateralTrxValue) {
				
				String lmtProfileIdStr = CollateralDAOFactory.getDAO().getCustomerLimitProfileIDByCollateralID(((ICollateralTrxValue)itrxValue).getCollateral().getCollateralID());
				
				long lmtProfileId = 0L;
				if(StringUtils.isNotBlank(lmtProfileIdStr)){
					lmtProfileId = Long.parseLong(lmtProfileIdStr);
				}
				//	String customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtProfileId);
				String customerSegment="";
				if(lmtProfileId != 0) {
					 customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtProfileId);
				} else {
					customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByCustomerId(((ICollateralTrxValue)itrxValue).getCustomerID());
				}				
				//FD
				if(((ICollateralTrxValue) itrxValue).getCollateral() instanceof ICashFd){
					ICashFd stagingCashfd = (OBCashFd)((ICollateralTrxValue) itrxValue).getStagingCollateral();
					ICashFd actualCashfd = (OBCashFd)((ICollateralTrxValue) itrxValue).getCollateral();
					List<BigDecimal> fdAmtList = new ArrayList<BigDecimal>();
					Map<Long, BigDecimal > fdRefIdAmountMap = new HashMap<Long, BigDecimal>();
					
					if(actualCashfd != null && actualCashfd.getDepositInfo() != null) {
						for(ICashDeposit depInfo : actualCashfd.getDepositInfo()) {
							if(depInfo.getDepositAmount() != null) {
								BigDecimal localAmt = CommonUtil.convertToBaseCcy(depInfo.getDepositAmount());
								fdRefIdAmountMap.put(depInfo.getRefID(), localAmt);
							}
						}
					}
					
					if(stagingCashfd.getDepositInfo() != null) {
						for(ICashDeposit depInfo : stagingCashfd.getDepositInfo()) {
							if(depInfo.getDepositAmount() !=null) {
								BigDecimal localAmt = CommonUtil.convertToBaseCcy(depInfo.getDepositAmount());
								BigDecimal actualAmt = fdRefIdAmountMap.get(depInfo.getRefID());
								
								if(localAmt !=null && ((actualAmt != null && actualAmt.compareTo(localAmt) != 0) || actualAmt == null))
									fdAmtList.add(depInfo.getDepositAmount().getAmountAsBigDecimal());
							}
						}
					}
					
					BigDecimal maxFdAmt = fdAmtList.size()>0 ? Collections.max(fdAmtList) : null;
					
					loaMap.put(LOA_MASTER_FIELD_FD_AMT, maxFdAmt);
					loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
					minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
					
				}
				
				//Asset Based General Charge Drawing Power
				else if (((ICollateralTrxValue) itrxValue).getCollateral() instanceof IGeneralCharge) {
					
					if(StringUtils.isNotBlank(calculatedDP)) {
						BigDecimal calculatedDPGeneralCharge = UIUtil.mapStringToBigDecimal(calculatedDP);
						loaMap.put(LOA_MASTER_FIELD_DRAWING_POWER, calculatedDPGeneralCharge);
						loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
						minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
					}
				}
				
				//cmv
				else if("GT402".equals(((ICollateralTrxValue) itrxValue).getStagingCollateral().getCollateralSubType().getSubTypeCode())) {
					if(((ICollateralTrxValue) itrxValue).getStagingCollateral().getCMV() != null) {
						BigDecimal securityOmv = ((ICollateralTrxValue) itrxValue).getStagingCollateral().getCMV().getAmountAsBigDecimal();
						
						loaMap.put(LOA_MASTER_FIELD_SBLC_SECURITY_OMV, securityOmv);
						loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
						minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
						
					}
				}
				
				//Property
				else if (((ICollateralTrxValue) itrxValue).getCollateral() instanceof IPropertyCollateral) {
					IPropertyCollateral propertyColl = (IPropertyCollateral) ((ICollateralTrxValue) itrxValue).getStagingCollateral();
					
					BigDecimal totalPropertyV1 = propertyColl.getTotalPropertyAmount_v1() != null?propertyColl.getTotalPropertyAmount_v1().getAmountAsBigDecimal(): null;
					BigDecimal totalPropertyV2 = propertyColl.getTotalPropertyAmount_v2() != null?propertyColl.getTotalPropertyAmount_v2().getAmountAsBigDecimal(): null;
					BigDecimal totalPropertyV3 = propertyColl.getTotalPropertyAmount_v3() != null?propertyColl.getTotalPropertyAmount_v3().getAmountAsBigDecimal(): null;
					
					BigDecimal propertyValuationAmt = null;
					
					propertyValuationAmt = totalPropertyV1 != null ?totalPropertyV1 : (totalPropertyV2 != null? totalPropertyV2: null);
					
					if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 == null) {
						propertyValuationAmt = totalPropertyV1.min(totalPropertyV2);
					}
					else if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 != null ) {
						propertyValuationAmt = (totalPropertyV1.min(totalPropertyV2)).min(totalPropertyV3);
					}
					
					if(propertyValuationAmt!=null) {
						loaMap.put(LOA_MASTER_FIELD_PROPERTY_AMT, propertyValuationAmt);
						loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
						minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
					}
				}
			}
			else if(itrxValue != null && itrxValue instanceof ILimitTrxValue) {
				ILimitSysXRef[] stagingRefArr = ((ILimitTrxValue) itrxValue).getStagingLimit()!=null? ((ILimitTrxValue) itrxValue).getStagingLimit().getLimitSysXRefs(): null;
				ILimitSysXRef[] actualRefArr = ((ILimitTrxValue) itrxValue).getLimit() != null ? ((ILimitTrxValue) itrxValue).getLimit().getLimitSysXRefs() : null;

				String customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(itrxValue.getLimitProfileID());
				
				List<Long> stagingSids = new ArrayList<Long>();
				Map<Long, String> stagingReleaseAmtMap = new HashMap<Long, String>();
				long newLineCount = 0;
				if(stagingRefArr != null) {
					for(ILimitSysXRef stagingXRef : stagingRefArr) {
						long sId = ICMSConstant.LONG_INVALID_VALUE != stagingXRef.getSID()? stagingXRef.getSID() : ++newLineCount;
						stagingSids.add(sId);
						stagingReleaseAmtMap.put(sId, stagingXRef.getCustomerSysXRef().getReleasedAmount());
					}	
				}
				
				
				List<Long> updatedSids = new ArrayList<Long>();
				List<BigDecimal> deltaReleaseAmtList = new ArrayList<BigDecimal>();
				
				if(actualRefArr != null) {
					for(ILimitSysXRef actualXRef : actualRefArr) {
						if(stagingSids.contains(actualXRef.getSID())){
							String stagingReleaseAmtStr = stagingReleaseAmtMap.get(actualXRef.getSID());
							String actualReleaseAmtStr = actualXRef.getCustomerSysXRef().getReleasedAmount();
							
							BigDecimal stagingReleaseAmt = UIUtil.mapStringToBigDecimal(stagingReleaseAmtStr);
							BigDecimal actualReleaseAmt = UIUtil.mapStringToBigDecimal(actualReleaseAmtStr);
							
							BigDecimal deltaReleaseAmt = stagingReleaseAmt.subtract(actualReleaseAmt).abs();
							
							deltaReleaseAmtList.add(deltaReleaseAmt);
							updatedSids.add(actualXRef.getSID());
						}
					}
				}
				
				
				stagingSids.removeAll(updatedSids);
				if(stagingSids.size()>0) {
					for(Long stagingSid : stagingSids) {
						String stagingReleaseAmtStr = stagingReleaseAmtMap.get(stagingSid);
						BigDecimal stagingReleaseAmt = UIUtil.mapStringToBigDecimal(stagingReleaseAmtStr);
						deltaReleaseAmtList.add(stagingReleaseAmt);
					}
				}
				
				BigDecimal maxDeltaReleaseAmt = deltaReleaseAmtList.size()>0 ? Collections.max(deltaReleaseAmtList) : null;
				
				if(maxDeltaReleaseAmt!=null) {
					loaMap.put(LOA_MASTER_FIELD_LIMIT_RELEASE_AMT, maxDeltaReleaseAmt);
					loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
					minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
				}
				
			}
			else if(customer != null ) {
				String totalSanctionedLimitStr = customer.getTotalSanctionedLimit();
				String customerSegment = customer.getCustomerSegment();
				
				if(StringUtils.isNotBlank(totalSanctionedLimitStr)) {
					BigDecimal totalSanctionedLimit = UIUtil.mapStringToBigDecimal(totalSanctionedLimitStr);

					if(totalSanctionedLimit!=null) {
						loaMap.put(LOA_MASTER_FIELD_TOTAL_SANCTIONED_LMT, totalSanctionedLimit);
						loaMap.put(LOA_MASTER_CUSTOMER_SEGMENT, customerSegment);
						minEmpGrade = loaMasterJdbc.getMinimumEmployeeGrade(loaMap);
					}
				}
				
			}
		}
		catch (Exception e) {
			DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in getMinimumEmployeeGradeForLOA :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		
		return minEmpGrade;
	}
	
	public static HashMap validateLoaMasterFieldsForLimits(HashMap exceptionMap, ICommonUser user, String isCamCovenantVerified, ILimitTrxValue lmtTrxObj) {

		HashMap returnMap = new HashMap();
		
		try {
			ILimitSysXRef[] stagingRefArr = lmtTrxObj.getStagingLimit()!=null? lmtTrxObj.getStagingLimit().getLimitSysXRefs(): null;
			ILimitSysXRef[] actualRefArr = lmtTrxObj.getLimit() != null ? lmtTrxObj.getLimit().getLimitSysXRefs() : null;
			String customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtTrxObj.getLimitProfileID());
			
			BigDecimal limitReleaseAmtMaster = null;
			ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
			ILimitsOfAuthorityMaster loaMaster = loaMasterJdbc.getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(user.getEmployeeGrade(), customerSegment);
			
			String facilityCode = lmtTrxObj.getStagingLimit() != null? lmtTrxObj.getStagingLimit().getFacilityCode():null;
			IFacilityNewMasterJdbc facMasterJdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
			String isFacMasterOverrideLoa = facMasterJdbc.getLineExcludeFromLoaMasterByFacilityCode(facilityCode);
			
			DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits for customer : "+lmtTrxObj.getLegalName()+" | trx Id"+lmtTrxObj.getTransactionID() +
					" | Facility Id :"+(lmtTrxObj.getLimit()!=null?lmtTrxObj.getLimit().getLimitRef() : "-") +" | isFacMasterOverrideLoa:"+isFacMasterOverrideLoa + 
					" | User overrideLoa :"+user.getOverrideExceptionForLoa() + " | loaMaster found :"+(loaMaster!=null) + " | isCamCovenantVerified: "+isCamCovenantVerified);
			System.out.println( "In validateLoaMasterFieldsForLimits  for customer : "+lmtTrxObj.getLegalName()+" | trx Id "+lmtTrxObj.getTransactionID() +
					" | Facility Id :"+(lmtTrxObj.getLimit()!=null?lmtTrxObj.getLimit().getLimitRef() : "-") +" | isFacMasterOverrideLoa:"+isFacMasterOverrideLoa + 
					" | User overrideLoa :"+user.getOverrideExceptionForLoa() + " | loaMaster found :"+(loaMaster!=null) + " | isCamCovenantVerified: "+isCamCovenantVerified);
			
			if(loaMaster!= null) {
				limitReleaseAmtMaster = loaMaster.getLimitReleaseAmt();
				Long stagingLoaMasterId = loaMasterJdbc.getStagingReferenceByActualLoaMaster(loaMaster.getId());
				
				DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits | limitReleaseAmtMaster amt :"+String.valueOf(limitReleaseAmtMaster));
				
				List<Long> stagingSids = new ArrayList<Long>();
				Map<Long, String> stagingReleaseAmtMap = new HashMap<Long, String>();
				Map<Long, BigDecimal> stagingDeltaAmtSidMap = new HashMap<Long, BigDecimal>();
				long newLineCount = 0;
				if(stagingRefArr != null) {
					for(ILimitSysXRef stagingXRef : stagingRefArr) {
						long sId = ICMSConstant.LONG_INVALID_VALUE != stagingXRef.getSID()? stagingXRef.getSID() : ++newLineCount;
						stagingSids.add(sId);
						stagingReleaseAmtMap.put(sId, stagingXRef.getCustomerSysXRef().getReleasedAmount());
					}
				}
				
				List<Long> updatedSids = new ArrayList<Long>();
				List<BigDecimal> deltaReleaseAmtList = new ArrayList<BigDecimal>();
				
				if(actualRefArr != null) {
					for(ILimitSysXRef actualXRef : actualRefArr) {
						if(stagingSids.contains(actualXRef.getSID())){
							String stagingReleaseAmtStr = stagingReleaseAmtMap.get(actualXRef.getSID());
							String actualReleaseAmtStr = actualXRef.getCustomerSysXRef().getReleasedAmount();
							
							BigDecimal stagingReleaseAmt = UIUtil.mapStringToBigDecimal(stagingReleaseAmtStr);
							BigDecimal actualReleaseAmt = UIUtil.mapStringToBigDecimal(actualReleaseAmtStr);
							BigDecimal deltaReleaseAmt = stagingReleaseAmt.subtract(actualReleaseAmt).abs();
							
							deltaReleaseAmtList.add(deltaReleaseAmt);
							updatedSids.add(actualXRef.getSID());
							stagingDeltaAmtSidMap.put(actualXRef.getSID(), deltaReleaseAmt);
						}
					}
				}
				
				stagingSids.removeAll(updatedSids);
				if(stagingSids.size()>0) {
					for(Long stagingSid : stagingSids) {
						String stagingReleaseAmtStr = stagingReleaseAmtMap.get(stagingSid);
						BigDecimal stagingReleaseAmt = UIUtil.mapStringToBigDecimal(stagingReleaseAmtStr);
						deltaReleaseAmtList.add(stagingReleaseAmt);
						stagingDeltaAmtSidMap.put(stagingSid, stagingReleaseAmt);
					}
				}
				
				BigDecimal maxDeltaReleaseAmt = deltaReleaseAmtList.size()>0 ? Collections.max(deltaReleaseAmtList) : null;
				
				DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits | maxDeltaReleaseAmt amt :"+
						String.valueOf(maxDeltaReleaseAmt)+" | deltaReleaseAmtList : "+deltaReleaseAmtList+ " | stagingDeltaAmtSidMap: "+stagingDeltaAmtSidMap);
				
				ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = new OBLimitsOfAuthorityMasterTrxLog();
				obLimitsOfAuthorityMasterTrxLog.setMakerDateTime(lmtTrxObj.getTransactionDate());
				obLimitsOfAuthorityMasterTrxLog.setMakerId(lmtTrxObj.getLoginId());
				obLimitsOfAuthorityMasterTrxLog.setStagingLoaMasterReferenceId(stagingLoaMasterId);
				obLimitsOfAuthorityMasterTrxLog.setTrxLimitReleaseAmt(maxDeltaReleaseAmt);
				obLimitsOfAuthorityMasterTrxLog.setIsExceptionalUser(user.getOverrideExceptionForLoa());
				obLimitsOfAuthorityMasterTrxLog.setUserId(user.getUserName());
				
				if(!(ICMSConstant.YES.equals(user.getOverrideExceptionForLoa()) || ICMSConstant.YES.equals(isFacMasterOverrideLoa))) {
					//LOA Validation
					for (Map.Entry<Long, BigDecimal> entry : stagingDeltaAmtSidMap.entrySet()) {
						BigDecimal deltaReleaseAmt = entry.getValue();
						Long sid = entry.getKey();
						
						if(limitReleaseAmtMaster!=null && deltaReleaseAmt!=null && limitReleaseAmtMaster.compareTo(deltaReleaseAmt) == -1) {
							String loaMasterLimitReleaseAmtError = sid+"loaMasterLimitReleaseAmtError";
							exceptionMap.put(loaMasterLimitReleaseAmtError, new ActionMessage("error.loa.master.not.eligible.to.authorize"));
						}
					}
					
					if(exceptionMap.isEmpty() && StringUtils.isBlank(isCamCovenantVerified)) {
						String camCovenant = loaMaster.getFacilityCamCovenant();
						
						DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits | camCovenant :"+camCovenant);
						
						if(ICMSConstant.YES.equals(camCovenant)) {
							exceptionMap.put("loaMasterCamCovenantError", new ActionMessage("error.loa.master.not.eligible.to.authorize.cam.covenant"));
						}
					}
				}
				
				returnMap.put("obLimitsOfAuthorityMasterTrxLog", obLimitsOfAuthorityMasterTrxLog);
			}else {
				if(!(ICMSConstant.YES.equals(user.getOverrideExceptionForLoa()) || ICMSConstant.YES.equals(isFacMasterOverrideLoa))) {
					exceptionMap.put("notAllowedError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
				}	
			}
			
			DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
			System.out.println("In validateLoaMasterFieldsForLimits with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
			
			returnMap.put("exceptionMap", exceptionMap);
		}
		catch (Exception e) {
			DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in validateLoaMasterFieldsForLimits :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		
		
		return returnMap;
	}
	
	public static void logLimitsOfAuthorityTrxData(ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog) {
		ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
		loaMasterJdbc.insertIntoLoaMasterLog(obLimitsOfAuthorityMasterTrxLog);
	}

	public static ILimitsOfAuthorityMasterTrxLog prepareObLimitsOfAuthorityMasterTrxLogLimits(ICMSTrxResult res, ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog) {
		try {
			obLimitsOfAuthorityMasterTrxLog.setTrxHistoryId(Long.valueOf(((OBLimitTrxValue)res.getTrxValue()).getTransactionID()));
		}
		catch (Exception e) {
			DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in prepareObLimitsOfAuthorityMasterTrxLogLimits :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		obLimitsOfAuthorityMasterTrxLog.setCheckerDateTime(res.getTrxValue().getTransactionDate());
		obLimitsOfAuthorityMasterTrxLog.setCheckerId(((OBLimitTrxValue)res.getTrxValue()).getLoginId());
		return obLimitsOfAuthorityMasterTrxLog;
	}
	
	public static ILimitsOfAuthorityMasterTrxLog prepareObLimitsOfAuthorityMasterTrxLogCollateral(ICollateralTrxValue returnValue, ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog) {
		obLimitsOfAuthorityMasterTrxLog.setTrxHistoryId(Long.valueOf(returnValue.getCurrentTrxHistoryID()));
		obLimitsOfAuthorityMasterTrxLog.setCheckerDateTime(returnValue.getTransactionDate());
		obLimitsOfAuthorityMasterTrxLog.setCheckerId(returnValue.getLoginId());
		return obLimitsOfAuthorityMasterTrxLog;
	}

	public static Map validateLOAMasterFieldsCollateral(ICollateralTrxValue itrxValue, ICommonUser curUser,
			String calculatedDPGeneralChargeStr, HashMap exceptionMap) {
		
		HashMap returnMap = new HashMap();
		
		try {
			String lmtProfileIdStr = StringUtils.EMPTY;
			if(itrxValue.getCollateral() != null)
				lmtProfileIdStr = CollateralDAOFactory.getDAO().getCustomerLimitProfileIDByCollateralID(itrxValue.getCollateral().getCollateralID());
			else
				lmtProfileIdStr = CollateralDAOFactory.getDAO().getCustomerLimitProfileIDByStagingCollateralID(itrxValue.getStagingCollateral().getCollateralID());
			
			long lmtProfileId = 0L;
			if(StringUtils.isNotBlank(lmtProfileIdStr)){
				lmtProfileId = Long.parseLong(lmtProfileIdStr);
			}
			//	String customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtProfileId);
			String customerSegment="";
			if(lmtProfileId != 0) {
				 customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByLimitProfileId(lmtProfileId);
			} else {
				customerSegment = CustomerDAOFactory.getDAO().getCustomerSegmentByCustomerId(((ICollateralTrxValue)itrxValue).getCustomerID());
			}
			ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
			ILimitsOfAuthorityMaster loaMaster = loaMasterJdbc.getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(curUser.getEmployeeGrade(), customerSegment);
			
			DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLoaMasterFieldsForLimits for customer : "+itrxValue.getLegalName()+" | trx Id : "+itrxValue.getTransactionID() +
					" | Collateral Id :"+(itrxValue.getCollateral()!=null?itrxValue.getCollateral().getCollateralID() : "-") + 
					" | User overrideLoa :"+curUser.getOverrideExceptionForLoa() + " | loaMaster found :"+(loaMaster!=null));
			System.out.println("In validateLoaMasterFieldsForLimits for customer : "+itrxValue.getLegalName()+" | trx Id : "+itrxValue.getTransactionID() +
					" | Collateral Id :"+(itrxValue.getCollateral()!=null?itrxValue.getCollateral().getCollateralID() : "-") +
					" | User overrideLoa :"+curUser.getOverrideExceptionForLoa() + " | loaMaster found :"+(loaMaster!=null));
			
			
			if(loaMaster != null) {
				BigDecimal fdAmtMaster = loaMaster.getFdAmount();
				BigDecimal drawingPowerMaster = loaMaster.getDrawingPower();
				BigDecimal sblcSecurityOmvMaster = loaMaster.getSblcSecurityOmv();
				BigDecimal propertyValuationMaster = loaMaster.getPropertyValuation();
				
				DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | fdAmtMaster :"+fdAmtMaster +
						" | drawingPowerMaster : "+ drawingPowerMaster+ " | sblcSecurityOmvMaster : "+sblcSecurityOmvMaster+ " | propertyValuationMaster : "+propertyValuationMaster);
				
				
				Long stagingLoaMasterId = loaMasterJdbc.getStagingReferenceByActualLoaMaster(loaMaster.getId());
				
				BigDecimal maxDepositAmount = null;
				BigDecimal calculatedDPGeneralCharge = null;
				BigDecimal securityOmv = null;
				BigDecimal propertyValuationAmt = null;
				
				//LOA Validation
				//FD
				if(itrxValue.getCollateral() instanceof ICashFd){
					ICashFd stagingCashfd = (OBCashFd)itrxValue.getStagingCollateral();
					ICashFd actualCashfd = (OBCashFd)itrxValue.getCollateral();
					List<BigDecimal> depositAmountList = new ArrayList<BigDecimal>();
					
					Map<Long, BigDecimal > fdRefIdAmountMap = new HashMap<Long, BigDecimal>();
					
					if(actualCashfd != null && actualCashfd.getDepositInfo() != null) {
						for(ICashDeposit depInfo : actualCashfd.getDepositInfo()) {
							if(depInfo.getDepositAmount() != null) {
								BigDecimal localAmt = CommonUtil.convertToBaseCcy(depInfo.getDepositAmount());
								fdRefIdAmountMap.put(depInfo.getRefID(), localAmt);
							}
						}
					}
					
					Map<Long, BigDecimal > fdInvalidLoaMap = new HashMap<Long, BigDecimal>();
					if(stagingCashfd.getDepositInfo() != null && fdAmtMaster != null) {
						for(ICashDeposit depInfo : stagingCashfd.getDepositInfo()) {
							if(depInfo.getDepositAmount()!=null && depInfo.getDepositAmount().getAmountAsBigDecimal()!=null) {
								BigDecimal localAmt = CommonUtil.convertToBaseCcy(depInfo.getDepositAmount());
								BigDecimal actualAmt = fdRefIdAmountMap.get(depInfo.getRefID());
								
								DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(),"In validateLOAMasterFieldsCollateral For depInfo " +depInfo.getDepositRefNo()+
										" | actualAmt :"+actualAmt+ " | localAmt"+localAmt);
								
								if(localAmt !=null && ((actualAmt != null && actualAmt.compareTo(localAmt) != 0) || actualAmt == null) && 
										 fdAmtMaster.compareTo(localAmt) == -1) {
									
									fdInvalidLoaMap.put(depInfo.getRefID(), localAmt);
								}
								else if(localAmt != null){
									depositAmountList.add(localAmt);
								}
							}
						}
						
						if(depositAmountList.size()>0 )
							maxDepositAmount = Collections.max(depositAmountList);
						
						DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | FD | maxDepositAmount :"+
								maxDepositAmount+ " | depositAmountList :"+depositAmountList+ " | fdRefIdAmountMap : "+fdRefIdAmountMap);
						
						if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
							for (Map.Entry<Long, BigDecimal> entry : fdInvalidLoaMap.entrySet()) {
								BigDecimal depositAmt = entry.getValue();
								Long refId = entry.getKey();
								
								if(depositAmt!=null) {
									String loaMasterLimitReleaseAmtError = refId+"loaMasterError";
									exceptionMap.put(loaMasterLimitReleaseAmtError, new ActionMessage("error.loa.master.not.eligible.to.authorize"));
								}
							}	
						}
						
					}
					
				}
				//Asset Based General Charge Drawing Power
				else if (itrxValue.getCollateral() instanceof IGeneralCharge && StringUtils.isNotBlank(calculatedDPGeneralChargeStr)) {
					calculatedDPGeneralCharge = UIUtil.mapStringToBigDecimal(calculatedDPGeneralChargeStr);
					
					DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | calculatedDPGeneralCharge : "+calculatedDPGeneralCharge +
							" | drawingPowerMaster : "+drawingPowerMaster);
					
					if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
						if(drawingPowerMaster!=null && drawingPowerMaster.compareTo(calculatedDPGeneralCharge) == -1) {
							exceptionMap.put("loaMasterError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
						}
					}
					
				}
				
				//cmv
				else if("GT402".equals(itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode())) {
					if(itrxValue.getStagingCollateral().getCMV() != null) {
						

						BigDecimal actualSecurityOmv = itrxValue.getCollateral()!= null ? CommonUtil.convertToBaseCcy(itrxValue.getCollateral().getCMV()) : null;
						securityOmv = CommonUtil.convertToBaseCcy(itrxValue.getStagingCollateral().getCMV());
						
						DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | actualSecurityOmv : "+actualSecurityOmv +
								" | securityOmv : "+securityOmv + " | sblcSecurityOmvMaster : "+sblcSecurityOmvMaster);
						
						if(actualSecurityOmv == null || (actualSecurityOmv != null && securityOmv != null && actualSecurityOmv.compareTo(securityOmv) != 0)) {
							if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
								if(securityOmv != null && sblcSecurityOmvMaster!=null && sblcSecurityOmvMaster.compareTo(securityOmv) == -1) {
									exceptionMap.put("loaMasterError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
								}
							}
						}
						
					}
				}
				
				//Property
				else if (itrxValue.getCollateral() instanceof IPropertyCollateral) {
					IPropertyCollateral propertyColl = (IPropertyCollateral) itrxValue.getStagingCollateral();
					
					BigDecimal totalPropertyV1 = propertyColl.getTotalPropertyAmount_v1() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v1()): null;
					BigDecimal totalPropertyV2 = propertyColl.getTotalPropertyAmount_v2() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v2()): null;
					BigDecimal totalPropertyV3 = propertyColl.getTotalPropertyAmount_v3() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v3()): null;
					
					DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | totalPropertyV1 : "+totalPropertyV1 +
							" | totalPropertyV2 : "+totalPropertyV2 + " | totalPropertyV3 : "+totalPropertyV3);
					
					try {
						
						propertyValuationAmt = totalPropertyV1 != null ?totalPropertyV1 : (totalPropertyV2 != null? totalPropertyV2: null);
						
						if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 == null) {
							propertyValuationAmt = totalPropertyV1.min(totalPropertyV2);
						}
						else if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 != null ) {
							propertyValuationAmt = (totalPropertyV1.min(totalPropertyV2)).min(totalPropertyV3);
						}
							
					}
					catch (Exception e) {
						DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in validateLOAMasterFieldsCollateral :: "+e.getMessage(), e);
						e.printStackTrace();
						throw e;
					}
					
					DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | propertyValuationAmt : "+propertyValuationAmt+ 
							" | propertyValuationMaster : "+propertyValuationMaster);
					
					if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
						String loaMasterError = "";
						if(totalPropertyV1 != null && totalPropertyV1.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV1Error";
						}
						else if(totalPropertyV2 != null && totalPropertyV2.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV2Error";
						}
						else if(totalPropertyV3 != null && totalPropertyV3.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV3Error";
						}
						
						if(propertyValuationMaster!=null && propertyValuationMaster.compareTo(propertyValuationAmt) == -1) {
							exceptionMap.put(loaMasterError, new ActionMessage("error.loa.master.not.eligible.to.authorize"));
						}
					}
					
				}
				
				if(maxDepositAmount!=null || calculatedDPGeneralCharge!= null || securityOmv != null || propertyValuationAmt!=null) {
					OBLimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = new OBLimitsOfAuthorityMasterTrxLog();
					obLimitsOfAuthorityMasterTrxLog.setMakerDateTime(itrxValue.getTransactionDate());
					obLimitsOfAuthorityMasterTrxLog.setMakerId(itrxValue.getLoginId());
					obLimitsOfAuthorityMasterTrxLog.setStagingLoaMasterReferenceId(stagingLoaMasterId);
					obLimitsOfAuthorityMasterTrxLog.setTrxFdAmount(maxDepositAmount);
					obLimitsOfAuthorityMasterTrxLog.setTrxDrawingPower(calculatedDPGeneralCharge);
					obLimitsOfAuthorityMasterTrxLog.setTrxSblcSecurityOmv(securityOmv);
					obLimitsOfAuthorityMasterTrxLog.setTrxPropertyValuation(propertyValuationAmt);
					obLimitsOfAuthorityMasterTrxLog.setIsExceptionalUser(curUser.getOverrideExceptionForLoa());
					obLimitsOfAuthorityMasterTrxLog.setUserId(curUser.getUserName());
					returnMap.put("obLimitsOfAuthorityMasterTrxLog", obLimitsOfAuthorityMasterTrxLog);
				}
			}else {
				
				if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
					exceptionMap.put("notAllowedError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
				}	
				BigDecimal propertyValuationAmt = null;
				BigDecimal propertyValuationMaster = null;
				if (itrxValue.getCollateral() instanceof IPropertyCollateral) {
					IPropertyCollateral propertyColl = (IPropertyCollateral) itrxValue.getStagingCollateral();
					
					BigDecimal totalPropertyV1 = propertyColl.getTotalPropertyAmount_v1() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v1()): null;
					BigDecimal totalPropertyV2 = propertyColl.getTotalPropertyAmount_v2() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v2()): null;
					BigDecimal totalPropertyV3 = propertyColl.getTotalPropertyAmount_v3() != null? CommonUtil.convertToBaseCcy(propertyColl.getTotalPropertyAmount_v3()): null;
					
					DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral | totalPropertyV1 : "+totalPropertyV1 +
							" | totalPropertyV2 : "+totalPropertyV2 + " | totalPropertyV3 : "+totalPropertyV3);
					
					try {
						
						propertyValuationAmt = totalPropertyV1 != null ?totalPropertyV1 : (totalPropertyV2 != null? totalPropertyV2: null);
						
						if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 == null) {
							propertyValuationAmt = totalPropertyV1.min(totalPropertyV2);
						}
						else if(totalPropertyV1 != null && totalPropertyV2 != null && totalPropertyV3 != null ) {
							propertyValuationAmt = (totalPropertyV1.min(totalPropertyV2)).min(totalPropertyV3);
						}
							
					}
					catch (Exception e) {
						DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in validateLOAMasterFieldsCollateral :: "+e.getMessage(), e);
						e.printStackTrace();
						throw e;
					}
										
					if( !ICMSConstant.YES.equals(curUser.getOverrideExceptionForLoa())) {
						String loaMasterError = "";
						if(totalPropertyV1 != null && totalPropertyV1.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV1Error";
						}
						else if(totalPropertyV2 != null && totalPropertyV2.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV2Error";
						}
						else if(totalPropertyV3 != null && totalPropertyV3.compareTo(propertyValuationAmt)==0) {
							loaMasterError = "loaMasterTotalPropertyV3Error";
						}
						
						if(propertyValuationMaster==null) {
							exceptionMap.put(loaMasterError, new ActionMessage("error.loa.master.not.eligible.to.authorize"));
						}
					}
					
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in validateLOAMasterFieldsCollateral :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		
		DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCollateral with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
		System.out.println("In validateLOAMasterFieldsCollateral with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
		
		
		returnMap.put("exceptionMap", exceptionMap);
		return returnMap;
	}

	public static Map validateLOAMasterFieldsCustomer(ICMSCustomerTrxValue trxValueIn, ICommonUser user, String customerSegment, HashMap exceptionMap, String sanctionedAmtUpdateFlag) {
		
		HashMap returnMap = new HashMap();
		
		ICMSCustomer stageCustomer = (OBCMSCustomer) trxValueIn.getStagingCustomer();
		ICMSCustomer actualCustomer = (OBCMSCustomer) trxValueIn.getCustomer();
		
		try {
			ILimitsOfAuthorityMasterJdbc loaMasterJdbc =  (ILimitsOfAuthorityMasterJdbc) BeanHouse.get("limitsOfAuthorityMasterJdbc");
			ILimitsOfAuthorityMaster loaMaster = null;
			
			String totalSanctionedLimitStr = stageCustomer.getTotalSanctionedLimit();
			String actualTotalSanctionedLimitStr = actualCustomer.getTotalSanctionedLimit();
			
			DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer for customer : "+trxValueIn.getLegalName()+" | trx Id: "+trxValueIn.getTransactionID() +
					" | Customer  :"+trxValueIn.getCustomerName() + "| Limit prof id : "+ trxValueIn.getLimitProfileID() + 
					" | User overrideLoa :"+(user !=null?user.getOverrideExceptionForLoa():StringUtils.EMPTY) + " | loaMaster found :"+(loaMaster!=null)+ 
					" | totalSanctionedLimitStr : "+totalSanctionedLimitStr + " | actualTotalSanctionedLimitStr  :"+actualTotalSanctionedLimitStr + 
					" | sanctionedAmtUpdateFlag : "+sanctionedAmtUpdateFlag + " | customerSegment : "+customerSegment  + " | User EmployeeGrade() : "+(user !=null?user.getEmployeeGrade():StringUtils.EMPTY));
			System.out.println( "In validateLOAMasterFieldsCustomer for customer : "+trxValueIn.getLegalName()+" | trx Id: "+trxValueIn.getTransactionID() +
					" | Customer  :"+trxValueIn.getCustomerName() + "| Limit prof id : "+ trxValueIn.getLimitProfileID() + 
					" | User overrideLoa :"+(user !=null?user.getOverrideExceptionForLoa():StringUtils.EMPTY) + " | loaMaster found :"+(loaMaster!=null)+ 
					" | totalSanctionedLimitStr : "+totalSanctionedLimitStr + " | actualTotalSanctionedLimitStr  :"+actualTotalSanctionedLimitStr + 
					" | sanctionedAmtUpdateFlag : "+sanctionedAmtUpdateFlag + " | customerSegment : "+customerSegment  + " | User EmployeeGrade() : "+(user !=null?user.getEmployeeGrade():StringUtils.EMPTY));
			
			
			BigDecimal totalSanctionedLimit = null;
			BigDecimal stagingTotalSanctionedLimit = StringUtils.isNotBlank(totalSanctionedLimitStr)?UIUtil.mapStringToBigDecimal(totalSanctionedLimitStr):null;
			BigDecimal actualTotalSanctionedLimit = StringUtils.isNotBlank(actualTotalSanctionedLimitStr)?UIUtil.mapStringToBigDecimal(actualTotalSanctionedLimitStr):null;
			
			
			boolean isSanctionedAmtChanged = Boolean.FALSE;
			if(actualTotalSanctionedLimit == null || (stagingTotalSanctionedLimit != null && actualTotalSanctionedLimit != null && 
					stagingTotalSanctionedLimit.compareTo(actualTotalSanctionedLimit)!=0 )) {
				isSanctionedAmtChanged = Boolean.TRUE;
				totalSanctionedLimit = stagingTotalSanctionedLimit;
			}
			
			DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer for :: trx Id"+trxValueIn.getTransactionID() +
					" | isSanctionedAmtChanged: "+isSanctionedAmtChanged + " | totalSanctionedLimit : "+totalSanctionedLimit+ " | sanctionedAmtUpdateFlag : "+sanctionedAmtUpdateFlag);
			
			if(isSanctionedAmtChanged) {
				if(ICMSConstant.YES.equals(sanctionedAmtUpdateFlag)) {

					List<ILimitsOfAuthorityMaster> validLoaMasters = 
							loaMasterJdbc.getValidLimitsOfAuthorityMasterByCustomerSegmentAndTotalSanctionedLimit(customerSegment, totalSanctionedLimit);
					
					if(validLoaMasters != null) {
						for(ILimitsOfAuthorityMaster validLoaMaster : validLoaMasters) {
							
							DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer loop validLoaMaster user grade : "+validLoaMaster.getEmployeeGrade());
							
							if(user != null && StringUtils.isNotBlank(user.getEmployeeGrade()) && user.getEmployeeGrade().equals(validLoaMaster.getEmployeeGrade())) {
								loaMaster = validLoaMaster;
								break;
							}
						}
						
						DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer loaMaster id is : "+(loaMaster!=null?loaMaster.getId():"-"));
						
						if(loaMaster != null) {
							Long stagingLoaMasterId = loaMasterJdbc.getStagingReferenceByActualLoaMaster(loaMaster.getId());
							
							OBLimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = new OBLimitsOfAuthorityMasterTrxLog();
							obLimitsOfAuthorityMasterTrxLog.setMakerDateTime(trxValueIn.getTransactionDate());
							obLimitsOfAuthorityMasterTrxLog.setMakerId(trxValueIn.getLoginId());
							obLimitsOfAuthorityMasterTrxLog.setStagingLoaMasterReferenceId(stagingLoaMasterId);
							obLimitsOfAuthorityMasterTrxLog.setTrxTotalSanctionedLimit(totalSanctionedLimit);
							obLimitsOfAuthorityMasterTrxLog.setIsExceptionalUser(user.getOverrideExceptionForLoa());
							obLimitsOfAuthorityMasterTrxLog.setUserId(user.getUserName());
							returnMap.put("obLimitsOfAuthorityMasterTrxLog", obLimitsOfAuthorityMasterTrxLog);
						}else {
							exceptionMap.put("notAllowedError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));					
						}
					}	
				}
				else {
					loaMaster = loaMasterJdbc.getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(user.getEmployeeGrade(), customerSegment);
					
					if(loaMaster != null) {
						BigDecimal totalSanctionedLimitMaster = loaMaster.getTotalSanctionedLimit();

						Long stagingLoaMasterId = loaMasterJdbc.getStagingReferenceByActualLoaMaster(loaMaster.getId());
						
						DefaultLogger.debug(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer :: totalSanctionedLimitMaster :"+totalSanctionedLimitMaster+
								" | totalSanctionedLimit : "+totalSanctionedLimit);
						
						
						if(!ICMSConstant.YES.equals(user.getOverrideExceptionForLoa())) {
							if(totalSanctionedLimitMaster!=null && totalSanctionedLimitMaster.compareTo(totalSanctionedLimit) == -1) {
								exceptionMap.put("loaMasterError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
							}
						}
						
						OBLimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = new OBLimitsOfAuthorityMasterTrxLog();
						obLimitsOfAuthorityMasterTrxLog.setMakerDateTime(trxValueIn.getTransactionDate());
						obLimitsOfAuthorityMasterTrxLog.setMakerId(trxValueIn.getLoginId());
						obLimitsOfAuthorityMasterTrxLog.setStagingLoaMasterReferenceId(stagingLoaMasterId);
						obLimitsOfAuthorityMasterTrxLog.setTrxTotalSanctionedLimit(totalSanctionedLimit);
						obLimitsOfAuthorityMasterTrxLog.setIsExceptionalUser(user.getOverrideExceptionForLoa());
						obLimitsOfAuthorityMasterTrxLog.setUserId(user.getUserName());
						returnMap.put("obLimitsOfAuthorityMasterTrxLog", obLimitsOfAuthorityMasterTrxLog);
					}else {
						if(!ICMSConstant.YES.equals(user.getOverrideExceptionForLoa())) {
							exceptionMap.put("notAllowedError", new ActionMessage("error.loa.master.not.eligible.to.authorize"));
						}	
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(LimitsOfAuthorityMasterHelper.class.getName(), "Exception caught in validateLOAMasterFieldsCustomer :: "+e.getMessage(), e);
			e.printStackTrace();
		}
		
		DefaultLogger.info(LimitsOfAuthorityMasterHelper.class.getName(), "In validateLOAMasterFieldsCustomer with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
		System.out.println("In validateLOAMasterFieldsCustomer with exceptionMap size : "+exceptionMap.size()+" | exceptionMap : "+exceptionMap);
		
		returnMap.put("exceptionMap", exceptionMap);
		return returnMap;
	}

	public static ILimitsOfAuthorityMasterTrxLog prepareObLimitsOfAuthorityMasterTrxLogCustomer(ICMSCustomerTrxValue trxValueOut, ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog) {
		obLimitsOfAuthorityMasterTrxLog.setTrxHistoryId(Long.valueOf(trxValueOut.getCurrentTrxHistoryID()));
		obLimitsOfAuthorityMasterTrxLog.setCheckerDateTime(trxValueOut.getTransactionDate());
		obLimitsOfAuthorityMasterTrxLog.setCheckerId(trxValueOut.getLoginId());
		return obLimitsOfAuthorityMasterTrxLog;
	}

}
