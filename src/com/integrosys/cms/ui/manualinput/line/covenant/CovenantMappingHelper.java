package com.integrosys.cms.ui.manualinput.line.covenant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class CovenantMappingHelper implements ILmtCovenantConstants{

	public void mapFacilityCovToLineCov(ILimitTrxValue lmtTrxObj) {

		ILimit lmtAct = lmtTrxObj.getLimit();
		ILimit stgLmt = lmtTrxObj.getStagingLimit();
		ILimitSysXRef[] refArrStg = null;
		ILimitCovenant[] lmtCovStg = null;
		ILimitCovenant[] lmtCovAct = null;
		ILineCovenant[] lineCovStg = null;

		if (lmtAct != null) {
			// Actual limit is not null - EDIT FACILITY
			if (stgLmt != null && checkSystem(stgLmt.getFacilitySystem())) {

				lmtCovAct = lmtAct.getLimitCovenant();
				lmtCovStg = stgLmt.getLimitCovenant();
				
				if(lmtCovStg!=null && !ArrayUtils.isEmpty(lmtCovStg)) {
					//Stage limit should not be null
					compareActualStageData(lmtAct,stgLmt,lmtCovAct,lmtCovStg,refArrStg,lineCovStg);
				}
			}
		} else {
			// Actual limit is null - ADD FACILITY - No need to compare for this case as line will be in under pending_update 
			//compareActualStageData(lmtAct,stgLmt,lmtCovAct,lmtCovStg,refArrStg,lineCovStg);
		}
	}

	private boolean checkSystem(String system) {

		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem = bundle.getString("ubs.systemName");

		if (fcubsSystem.equals(system) || ubsSystem.equals(system)) {
			return true;
		}
		return false;
	}

	public static Map compareLmtCovenant(ILimitCovenant[] lmtCovAct, ILimitCovenant[] lmtCovStg,boolean isStageActual) {

		Map covMap = new HashMap();
		ILimitCovenant actualSingleCov = null;
		ILimitCovenant stageSingleCov = null;
		ILimitCovenant[] lmtCovTemp = null;

		ILimitCovenant tempCountryCov = new OBLimitCovenant();
		ILimitCovenant tempDrawerCov = new OBLimitCovenant();
		ILimitCovenant tempDraweeCov = new OBLimitCovenant();
		ILimitCovenant tempBeneCov = new OBLimitCovenant();
		ILimitCovenant tempGoodsCov = new OBLimitCovenant();
		ILimitCovenant tempCurrencyCov = new OBLimitCovenant();
		ILimitCovenant tempBankCov = new OBLimitCovenant();
		ILimitCovenant tempSingleCov = new OBLimitCovenant();

		// get actual limit covenant data
		String countryRestrictionFlagAct = "";
		String currencyRestrictionFlagAct = "";
		String bankRestrictionFlagAct = "";
		String drawerFlagAct = "";
		String draweeFlagAct = "";
		String beneFlagAct = "";
		String goodsRestrictionFlagAct = "";

		String countryRestrictionFlagStg = "";
		String currencyRestrictionFlagStg = "";
		String bankRestrictionFlagStg = "";
		String drawerFlagStg = "";
		String draweeFlagStg = "";
		String beneFlagStg = "";
		String goodsRestrictionFlagStg = "";

		List<String> countryRestrictionFlagList = new ArrayList<String>();
		List<String> currencyRestrictionFlagList = new ArrayList<String>();
		List<String> bankRestrictionFlagList = new ArrayList<String>();
		List<String> drawerFlagList = new ArrayList<String>();
		List<String> draweeFlagList = new ArrayList<String>();
		List<String> beneFlagList = new ArrayList<String>();
		List<String> goodsRestrictionFlagList = new ArrayList<String>();

		if(lmtCovAct != null && !ArrayUtils.isEmpty(lmtCovAct)) {
			for (int i = 0; i < lmtCovAct.length; i++) {
				ILimitCovenant cov = lmtCovAct[i];

				if (ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
					actualSingleCov = cov;
				} else if (ICMSConstant.YES.equals(cov.getBeneInd())) {
					beneFlagList.add(cov.getBeneName() + "|" + cov.getBeneAmount().replace(",", "") + "|" + cov.getBeneCustId() + "|" + cov.getBeneCustName());
				} else if (ICMSConstant.YES.equals(cov.getDraweeInd())) {
					draweeFlagList.add(cov.getDraweeName() + "|" + cov.getDraweeAmount().replace(",", "") + "|" + cov.getDraweeCustId() + "|" + cov.getDraweeCustName());
				} else if (ICMSConstant.YES.equals(cov.getDrawerInd())) {
					drawerFlagList.add(cov.getDrawerName() + "|" + cov.getDrawerAmount().replace(",", "") + "|" + cov.getDrawerCustId() + "|" + cov.getDrawerCustName());
				} else if (ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
					goodsRestrictionFlagList.add(cov.getGoodsRestrictionComboCode());
				} else if (ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
					bankRestrictionFlagList.add(cov.getRestrictedBank() + "|" + cov.getRestrictedBankAmount().replace(",", ""));
				} else if (ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
					countryRestrictionFlagList.add((cov.getRestrictedCountryname()) + "|" + cov.getRestrictedAmount().replace(",", ""));
				} else if (ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
					currencyRestrictionFlagList.add(cov.getRestrictedCurrency() + "|" + cov.getRestrictedCurrencyAmount().replace(",", ""));
				}
			}
		}

		countryRestrictionFlagAct = !CollectionUtils.isEmpty(countryRestrictionFlagList)
				? StringUtils.join(countryRestrictionFlagList.toArray(new String[0]), ",")
				: "";
		currencyRestrictionFlagAct = !CollectionUtils.isEmpty(currencyRestrictionFlagList)
				? StringUtils.join(currencyRestrictionFlagList.toArray(new String[0]), ",")
				: "";
		bankRestrictionFlagAct = !CollectionUtils.isEmpty(bankRestrictionFlagList)
				? StringUtils.join(bankRestrictionFlagList.toArray(new String[0]), ",")
				: "";
		drawerFlagAct = !CollectionUtils.isEmpty(drawerFlagList)
				? StringUtils.join(drawerFlagList.toArray(new String[0]), ",")
				: "";
		draweeFlagAct = !CollectionUtils.isEmpty(draweeFlagList)
				? StringUtils.join(draweeFlagList.toArray(new String[0]), ",")
				: "";
		beneFlagAct = !CollectionUtils.isEmpty(beneFlagList)
				? StringUtils.join(beneFlagList.toArray(new String[0]), ",")
				: "";
		goodsRestrictionFlagAct = !CollectionUtils.isEmpty(goodsRestrictionFlagList)
				? StringUtils.join(goodsRestrictionFlagList.toArray(new String[0]), ",")
				: "";

		// get stage limit covenant data
		List<String> countryRestrictionFlagListStage = new ArrayList<String>();
		List<String> currencyRestrictionFlagListStage = new ArrayList<String>();
		List<String> bankRestrictionFlagListStage = new ArrayList<String>();
		List<String> drawerFlagListStage = new ArrayList<String>();
		List<String> draweeFlagListStage = new ArrayList<String>();
		List<String> beneFlagListStage = new ArrayList<String>();
		List<String> goodsRestrictionFlagListStage = new ArrayList<String>();

		for (int j = 0; j < lmtCovStg.length; j++) {
			ILimitCovenant cov = lmtCovStg[j];

			if (ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
				stageSingleCov = cov;
			} else if (ICMSConstant.YES.equals(cov.getBeneInd())) {
				beneFlagListStage.add(cov.getBeneName() + "|" + cov.getBeneAmount().replace(",", "") + "|" + cov.getBeneCustId() + "|" + cov.getBeneCustName());
			} else if (ICMSConstant.YES.equals(cov.getDraweeInd())) {
				draweeFlagListStage.add(cov.getDraweeName() + "|" + cov.getDraweeAmount().replace(",", "") + "|" + cov.getDraweeCustId() + "|" + cov.getDraweeCustName());
			} else if (ICMSConstant.YES.equals(cov.getDrawerInd())) {
				drawerFlagListStage.add(cov.getDrawerName() + "|" + cov.getDrawerAmount().replace(",", "") + "|" + cov.getDrawerCustId() + "|" + cov.getDrawerCustName());
			} else if (ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
				goodsRestrictionFlagListStage.add(cov.getGoodsRestrictionComboCode());
			} else if (ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
				bankRestrictionFlagListStage.add(cov.getRestrictedBank() + "|" + cov.getRestrictedBankAmount().replace(",", ""));
			} else if (ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
				countryRestrictionFlagListStage.add((cov.getRestrictedCountryname()) + "|" + cov.getRestrictedAmount().replace(",", ""));
			} else if (ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
				currencyRestrictionFlagListStage.add(cov.getRestrictedCurrency() + "|" + cov.getRestrictedCurrencyAmount().replace(",", ""));
			}
		}
		countryRestrictionFlagStg = !CollectionUtils.isEmpty(countryRestrictionFlagListStage)
				? StringUtils.join(countryRestrictionFlagListStage.toArray(new String[0]), ",")
				: "";
		currencyRestrictionFlagStg = !CollectionUtils.isEmpty(currencyRestrictionFlagListStage)
				? StringUtils.join(currencyRestrictionFlagListStage.toArray(new String[0]), ",")
				: "";
		bankRestrictionFlagStg = !CollectionUtils.isEmpty(bankRestrictionFlagListStage)
				? StringUtils.join(bankRestrictionFlagListStage.toArray(new String[0]), ",")
				: "";
		drawerFlagStg = !CollectionUtils.isEmpty(drawerFlagListStage)
				? StringUtils.join(drawerFlagListStage.toArray(new String[0]), ",")
				: "";
		draweeFlagStg = !CollectionUtils.isEmpty(draweeFlagListStage)
				? StringUtils.join(draweeFlagListStage.toArray(new String[0]), ",")
				: "";
		beneFlagStg = !CollectionUtils.isEmpty(beneFlagListStage)
				? StringUtils.join(beneFlagListStage.toArray(new String[0]), ",")
				: "";
		goodsRestrictionFlagStg = !CollectionUtils.isEmpty(goodsRestrictionFlagListStage)
				? StringUtils.join(goodsRestrictionFlagListStage.toArray(new String[0]), ",")
				: "";

		// set multi list covenant data
		if (!countryRestrictionFlagAct.equalsIgnoreCase(countryRestrictionFlagStg)) {
			List<OBLineCovenant> restCountryAddListForLine = getAddFlag(countryRestrictionFlagAct, countryRestrictionFlagStg,COUNTRY_RESTRICTION);
			List<OBLineCovenant> restCountryDeleteListForLine = getDeleteFlag(countryRestrictionFlagAct, countryRestrictionFlagStg,COUNTRY_RESTRICTION);
			covMap.put(REST_COUNTRY_ADD_LIST_FOR_LINE, restCountryAddListForLine);
			covMap.put(REST_COUNTRY_DELETE_LIST_FOR_LINE, restCountryDeleteListForLine);
		}
		if (!currencyRestrictionFlagAct.equalsIgnoreCase(currencyRestrictionFlagStg)) {
			List<OBLineCovenant> restCurrencyAddListForLine = getAddFlag(currencyRestrictionFlagAct, currencyRestrictionFlagStg,CURRENCY_RESTRICTION);
			List<OBLineCovenant> restCurrencyDeleteListForLine = getDeleteFlag(currencyRestrictionFlagAct, currencyRestrictionFlagStg,CURRENCY_RESTRICTION);
			covMap.put(REST_CURRENCY_ADD_LIST_FOR_LINE, restCurrencyAddListForLine);
			covMap.put(REST_CURRENCY_DELETE_LIST_FOR_LINE, restCurrencyDeleteListForLine);
		}
		if (!bankRestrictionFlagAct.equalsIgnoreCase(bankRestrictionFlagStg)) {
			List<OBLineCovenant> restBankAddListForLine = getAddFlag(bankRestrictionFlagAct, bankRestrictionFlagStg,BANK_RESTRICTION);
			List<OBLineCovenant> restBankDeleteListForLine = getDeleteFlag(bankRestrictionFlagAct, bankRestrictionFlagStg,BANK_RESTRICTION);
			covMap.put(REST_BANK_ADD_LIST_FOR_LINE, restBankAddListForLine);
			covMap.put(REST_BANK_DELETE_LIST_FOR_LINE, restBankDeleteListForLine);
		}
		if (!drawerFlagAct.equalsIgnoreCase(drawerFlagStg)) {
			List<OBLineCovenant> restDrawerAddListForLine = getAddFlag(drawerFlagAct, drawerFlagStg,DRAWER_RESTRICTION);
			List<OBLineCovenant> restDrawerDeleteListForLine = getDeleteFlag(drawerFlagAct, drawerFlagStg,DRAWER_RESTRICTION);
			covMap.put(REST_DRAWER_ADD_LIST_FOR_LINE, restDrawerAddListForLine);
			covMap.put(REST_DRAWER_DELETE_LIST_FOR_LINE, restDrawerDeleteListForLine);
		}
		if (!draweeFlagAct.equalsIgnoreCase(draweeFlagStg)) {
			List<OBLineCovenant> restDraweeAddListForLine = getAddFlag(draweeFlagAct, draweeFlagStg,DRAWEE_RESTRICTION);
			List<OBLineCovenant> restDraweeDeleteListForLine = getDeleteFlag(draweeFlagAct, draweeFlagStg,DRAWEE_RESTRICTION);
			covMap.put(REST_DRAWEE_ADD_LIST_FOR_LINE, restDraweeAddListForLine);
			covMap.put(REST_DRAWEE_DELETE_LIST_FOR_LINE, restDraweeDeleteListForLine);
		}
		if (!beneFlagAct.equalsIgnoreCase(beneFlagStg)) {
			List<OBLineCovenant> restBeneAddListForLine = getAddFlag(beneFlagAct, beneFlagStg,BENE_RESTRICTION);
			List<OBLineCovenant> restBeneDeleteListForLine = getDeleteFlag(beneFlagAct, beneFlagStg,BENE_RESTRICTION);
			covMap.put(REST_BENE_ADD_LIST_FOR_LINE, restBeneAddListForLine);
			covMap.put(REST_BENE_DELETE_LIST_FOR_LINE, restBeneDeleteListForLine);
		}
		if (!goodsRestrictionFlagAct.equalsIgnoreCase(goodsRestrictionFlagStg)) {
			List<OBLineCovenant> restGoodsAddListForLine = getAddFlag(goodsRestrictionFlagAct, goodsRestrictionFlagStg,GOODS_RESTRICTION);
			List<OBLineCovenant> restGoodsDeleteListForLine = getDeleteFlag(goodsRestrictionFlagAct, goodsRestrictionFlagStg,GOODS_RESTRICTION);
			covMap.put(REST_GOODS_ADD_LIST_FOR_LINE, restGoodsAddListForLine);
			covMap.put(REST_GOODS_DELETE_LIST_FOR_LINE, restGoodsDeleteListForLine);
		}
		
		// set single covennt fields details 
		if(isStageActual) {
			//if actual and stage covenant is not null
			tempSingleCov=setTempForActualStageSingleCovenant(actualSingleCov,stageSingleCov,tempSingleCov);
		}else {
			//if actual covenant is null
			tempSingleCov=setTempForStageSingleCovenant(stageSingleCov,tempSingleCov);
		}
		
		covMap.put(SINGLE_COV_FOR_LINE, tempSingleCov);
		return covMap;
	}
	
	public static ILimitCovenant setTempForActualStageSingleCovenant(ILimitCovenant actualSingleCov, ILimitCovenant stageSingleCov, ILimitCovenant tempSingleCov) {
		
		tempSingleCov.setSingleCovenantInd(ICMSConstant.YES);
		tempSingleCov.setIsNewEntry(ICMSConstant.NO);
		
		if (actualSingleCov.getCovenantReqd()!=null && !actualSingleCov.getCovenantReqd().equals(stageSingleCov.getCovenantReqd())) {
			tempSingleCov.setCovenantReqd(stageSingleCov.getCovenantReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getCountryRestrictionReqd()!=null && !actualSingleCov.getCountryRestrictionReqd().equals(stageSingleCov.getCountryRestrictionReqd())) {
			tempSingleCov.setCountryRestrictionReqd(stageSingleCov.getCountryRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getDrawerReqd()!=null && !actualSingleCov.getDrawerReqd().equals(stageSingleCov.getDrawerReqd())) {
			tempSingleCov.setDrawerReqd(stageSingleCov.getDrawerReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getDraweeReqd()!=null && !actualSingleCov.getDraweeReqd().equals(stageSingleCov.getDraweeReqd())) {
			tempSingleCov.setDraweeReqd(stageSingleCov.getDraweeReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getBeneficiaryReqd()!=null && !actualSingleCov.getBeneficiaryReqd().equals(stageSingleCov.getBeneficiaryReqd())) {
			tempSingleCov.setBeneficiaryReqd(stageSingleCov.getBeneficiaryReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getCombinedTenorReqd()!=null && !actualSingleCov.getCombinedTenorReqd().equals(stageSingleCov.getCombinedTenorReqd())) {
			tempSingleCov.setCombinedTenorReqd(stageSingleCov.getCombinedTenorReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getRunningAccountReqd()!=null && !actualSingleCov.getRunningAccountReqd().equals(stageSingleCov.getRunningAccountReqd())) {
			tempSingleCov.setRunningAccountReqd(stageSingleCov.getRunningAccountReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getSellDownReqd()!=null && !actualSingleCov.getSellDownReqd().equals(stageSingleCov.getSellDownReqd())) {
			tempSingleCov.setSellDownReqd(stageSingleCov.getSellDownReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getLastAvailableDateReqd()!=null && !actualSingleCov.getLastAvailableDateReqd().equals(stageSingleCov.getLastAvailableDateReqd())) {
			tempSingleCov.setLastAvailableDateReqd(stageSingleCov.getLastAvailableDateReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getMoratoriumReqd()!=null && !actualSingleCov.getMoratoriumReqd().equals(stageSingleCov.getMoratoriumReqd())) {
			tempSingleCov.setMoratoriumReqd(stageSingleCov.getMoratoriumReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getGoodsRestrictionReqd()!=null && !actualSingleCov.getGoodsRestrictionReqd().equals(stageSingleCov.getGoodsRestrictionReqd())) {
			tempSingleCov.setGoodsRestrictionReqd(stageSingleCov.getGoodsRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getCurrencyRestrictionReqd()!=null && !actualSingleCov.getCurrencyRestrictionReqd().equals(stageSingleCov.getCurrencyRestrictionReqd())) {
			tempSingleCov.setCurrencyRestrictionReqd(stageSingleCov.getCurrencyRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getBankRestrictionReqd()!=null && !actualSingleCov.getBankRestrictionReqd().equals(stageSingleCov.getBankRestrictionReqd())) {
			tempSingleCov.setBankRestrictionReqd(stageSingleCov.getBankRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getBuyersRatingReqd()!=null && !actualSingleCov.getBuyersRatingReqd().equals(stageSingleCov.getBuyersRatingReqd())) {
			tempSingleCov.setBuyersRatingReqd(stageSingleCov.getBuyersRatingReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getEcgcCoverReqd()!=null && !actualSingleCov.getEcgcCoverReqd().equals(stageSingleCov.getEcgcCoverReqd())) {
			tempSingleCov.setEcgcCoverReqd(stageSingleCov.getEcgcCoverReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		// compare single covenant value
		if (actualSingleCov.getMaxCombinedTenor()!=null && !actualSingleCov.getMaxCombinedTenor().equals(stageSingleCov.getMaxCombinedTenor())) {
			tempSingleCov.setMaxCombinedTenor(stageSingleCov.getMaxCombinedTenor());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getPreshipmentLinkage()!=null && !actualSingleCov.getPreshipmentLinkage().equals(stageSingleCov.getPreshipmentLinkage())) {
			tempSingleCov.setPreshipmentLinkage(stageSingleCov.getPreshipmentLinkage());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getPostShipmentLinkage()!=null && !actualSingleCov.getPostShipmentLinkage().equals(stageSingleCov.getPostShipmentLinkage())) {
			tempSingleCov.setPostShipmentLinkage(stageSingleCov.getPostShipmentLinkage());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getRunningAccount()!=null && !actualSingleCov.getRunningAccount().equals(stageSingleCov.getRunningAccount())) {
			tempSingleCov.setRunningAccount(stageSingleCov.getRunningAccount());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getOrderBackedbylc()!=null && !actualSingleCov.getOrderBackedbylc().equals(stageSingleCov.getOrderBackedbylc())) {
			tempSingleCov.setOrderBackedbylc(stageSingleCov.getOrderBackedbylc());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getIncoTerm()!=null && !actualSingleCov.getIncoTerm().equals(stageSingleCov.getIncoTerm())) {
			tempSingleCov.setIncoTerm(stageSingleCov.getIncoTerm());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getIncoTermMarginPercent()!=null && !actualSingleCov.getIncoTermMarginPercent().equals(stageSingleCov.getIncoTermMarginPercent())) {
			tempSingleCov.setIncoTermMarginPercent(stageSingleCov.getIncoTermMarginPercent());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getIncoTermDesc()!=null && !actualSingleCov.getIncoTermDesc().equals(stageSingleCov.getIncoTermDesc())) {
			tempSingleCov.setIncoTermDesc(stageSingleCov.getIncoTermDesc());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getModuleCode()!=null && !actualSingleCov.getModuleCode().equals(stageSingleCov.getModuleCode())) {
			tempSingleCov.setModuleCode(stageSingleCov.getModuleCode());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getCommitmentTenor()!=null && !actualSingleCov.getCommitmentTenor().equals(stageSingleCov.getCommitmentTenor())) {
			tempSingleCov.setCommitmentTenor(stageSingleCov.getCommitmentTenor());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getSellDown()!=null && !actualSingleCov.getSellDown().equals(stageSingleCov.getSellDown())) {
			tempSingleCov.setSellDown(stageSingleCov.getSellDown());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getLastAvailableDate()!=null && !actualSingleCov.getLastAvailableDate().equals(stageSingleCov.getLastAvailableDate())) {
			tempSingleCov.setLastAvailableDate(stageSingleCov.getLastAvailableDate());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getMoratoriumPeriod()!=null && !actualSingleCov.getMoratoriumPeriod().equals(stageSingleCov.getMoratoriumPeriod())) {
			tempSingleCov.setMoratoriumPeriod(stageSingleCov.getMoratoriumPeriod());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getEmiFrequency()!=null && !actualSingleCov.getEmiFrequency().equals(stageSingleCov.getEmiFrequency())) {
			tempSingleCov.setEmiFrequency(stageSingleCov.getEmiFrequency());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getNoOfInstallments()!=null && !actualSingleCov.getNoOfInstallments().equals(stageSingleCov.getNoOfInstallments())) {
			tempSingleCov.setNoOfInstallments(stageSingleCov.getNoOfInstallments());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getBuyersRating()!=null && !actualSingleCov.getBuyersRating().equals(stageSingleCov.getBuyersRating())) {
			tempSingleCov.setBuyersRating(stageSingleCov.getBuyersRating());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getAgencyMaster()!=null && !actualSingleCov.getAgencyMaster().equals(stageSingleCov.getAgencyMaster())) {
			tempSingleCov.setAgencyMaster(stageSingleCov.getAgencyMaster());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getRatingMaster()!=null && !actualSingleCov.getRatingMaster().equals(stageSingleCov.getRatingMaster())) {
			tempSingleCov.setRatingMaster(stageSingleCov.getRatingMaster());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (actualSingleCov.getEcgcCover()!=null && !actualSingleCov.getEcgcCover().equals(stageSingleCov.getEcgcCover())) {
			tempSingleCov.setEcgcCover(stageSingleCov.getEcgcCover());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		
		return tempSingleCov;
	}
	
	public static ILimitCovenant setTempForStageSingleCovenant(ILimitCovenant stageSingleCov, ILimitCovenant tempSingleCov) {
		
		if (stageSingleCov.getCovenantReqd()!=null) {
			tempSingleCov.setCovenantReqd(stageSingleCov.getCovenantReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getCountryRestrictionReqd()!=null) {
			tempSingleCov.setCountryRestrictionReqd(stageSingleCov.getCountryRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getDrawerReqd()!=null) {
			tempSingleCov.setDrawerReqd(stageSingleCov.getDrawerReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getDraweeReqd()!=null) {
			tempSingleCov.setDraweeReqd(stageSingleCov.getDraweeReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getBeneficiaryReqd()!=null) {
			tempSingleCov.setBeneficiaryReqd(stageSingleCov.getBeneficiaryReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getCombinedTenorReqd()!=null) {
			tempSingleCov.setCombinedTenorReqd(stageSingleCov.getCombinedTenorReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getRunningAccountReqd()!=null) {
			tempSingleCov.setRunningAccountReqd(stageSingleCov.getRunningAccountReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getSellDownReqd()!=null) {
			tempSingleCov.setSellDownReqd(stageSingleCov.getSellDownReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getLastAvailableDateReqd()!=null) {
			tempSingleCov.setLastAvailableDateReqd(stageSingleCov.getLastAvailableDateReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getMoratoriumReqd()!=null) {
			tempSingleCov.setMoratoriumReqd(stageSingleCov.getMoratoriumReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getGoodsRestrictionReqd()!=null) {
			tempSingleCov.setGoodsRestrictionReqd(stageSingleCov.getGoodsRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getCurrencyRestrictionReqd()!=null) {
			tempSingleCov.setCurrencyRestrictionReqd(stageSingleCov.getCurrencyRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getBankRestrictionReqd()!=null) {
			tempSingleCov.setBankRestrictionReqd(stageSingleCov.getBankRestrictionReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getBuyersRatingReqd()!=null) {
			tempSingleCov.setBuyersRatingReqd(stageSingleCov.getBuyersRatingReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getEcgcCoverReqd()!=null) {
			tempSingleCov.setEcgcCoverReqd(stageSingleCov.getEcgcCoverReqd());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		// compare single covenant value
		if (stageSingleCov.getMaxCombinedTenor()!=null) {
			tempSingleCov.setMaxCombinedTenor(stageSingleCov.getMaxCombinedTenor());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getPreshipmentLinkage()!=null) {
			tempSingleCov.setPreshipmentLinkage(stageSingleCov.getPreshipmentLinkage());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getPostShipmentLinkage()!=null) {
			tempSingleCov.setPostShipmentLinkage(stageSingleCov.getPostShipmentLinkage());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getRunningAccount()!=null) {
			tempSingleCov.setRunningAccount(stageSingleCov.getRunningAccount());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getOrderBackedbylc()!=null) {
			tempSingleCov.setOrderBackedbylc(stageSingleCov.getOrderBackedbylc());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getIncoTerm()!=null) {
			tempSingleCov.setIncoTerm(stageSingleCov.getIncoTerm());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getIncoTermMarginPercent()!=null) {
			tempSingleCov.setIncoTermMarginPercent(stageSingleCov.getIncoTermMarginPercent());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getIncoTermDesc()!=null) {
			tempSingleCov.setIncoTermDesc(stageSingleCov.getIncoTermDesc());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getModuleCode()!=null) {
			tempSingleCov.setModuleCode(stageSingleCov.getModuleCode());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getCommitmentTenor()!=null) {
			tempSingleCov.setCommitmentTenor(stageSingleCov.getCommitmentTenor());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getSellDown()!=null) {
			tempSingleCov.setSellDown(stageSingleCov.getSellDown());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getLastAvailableDate()!=null) {
			tempSingleCov.setLastAvailableDate(stageSingleCov.getLastAvailableDate());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getMoratoriumPeriod()!=null) {
			tempSingleCov.setMoratoriumPeriod(stageSingleCov.getMoratoriumPeriod());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getEmiFrequency()!=null) {
			tempSingleCov.setEmiFrequency(stageSingleCov.getEmiFrequency());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getNoOfInstallments()!=null) {
			tempSingleCov.setNoOfInstallments(stageSingleCov.getNoOfInstallments());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getBuyersRating()!=null) {
			tempSingleCov.setBuyersRating(stageSingleCov.getBuyersRating());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getAgencyMaster()!=null) {
			tempSingleCov.setAgencyMaster(stageSingleCov.getAgencyMaster());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getRatingMaster()!=null) {
			tempSingleCov.setRatingMaster(stageSingleCov.getRatingMaster());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		if (stageSingleCov.getEcgcCover()!=null) {
			tempSingleCov.setEcgcCover(stageSingleCov.getEcgcCover());
			tempSingleCov.setIsNewEntry(ICMSConstant.YES);
		}
		
		return tempSingleCov;
	}

	public static List<OBLineCovenant> getAddFlag(String act, String stg, String flag) {
		List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();
		
		if (!isEmptyOrNull(stg) && !act.equalsIgnoreCase(stg)) {
			String[] stgStrList = null;
			if(null!=stg && stg.contains(",")){
				stgStrList = stg.split(",");
			}
			else if(null != stg && !"".equalsIgnoreCase(stg)){
				stgStrList = new String[1];
				stgStrList[0] = stg;
			}
			
			for (String stgString : stgStrList) {
				if (!act.contains(stgString)) {
					String[] splitArray = stgString.split("\\|");
					OBLineCovenant value = new OBLineCovenant();
					
					if(flag.equalsIgnoreCase(COUNTRY_RESTRICTION)) {
						value.setRestrictedCountryname(splitArray[0].toString().trim());
						value.setRestrictedAmount(splitArray[1].toString().trim());
						value.setRestrictedCountryInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(CURRENCY_RESTRICTION)) {
						value.setRestrictedCurrency(splitArray[0].toString().trim());
						value.setRestrictedCurrencyAmount(splitArray[1].toString().trim());
						value.setRestrictedCurrencyInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(BANK_RESTRICTION)) {
						value.setRestrictedBank(splitArray[0].toString().trim());
						value.setRestrictedBankAmount(splitArray[1].toString().trim());
						value.setRestrictedBankInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(GOODS_RESTRICTION)) {
						value.setGoodsRestrictionParentCode(splitArray[0].toString().trim());
						value.setGoodsRestrictionChildCode(splitArray[1].toString().trim());
						value.setGoodsRestrictionSubChildCode(splitArray[2].toString().trim());
						value.setGoodsRestrictionComboCode(stgString.trim());
						value.setGoodsRestrictionInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(DRAWER_RESTRICTION)) {
						value.setDrawerName(splitArray[0].toString().trim());
						value.setDrawerAmount(splitArray[1].toString().trim());
						value.setDrawerCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setDrawerCustName(splitArray[3].toString().trim());
						else
							value.setDrawerCustName("");
						value.setDrawerInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(DRAWEE_RESTRICTION)) {
						value.setDraweeName(splitArray[0].toString().trim());
						value.setDraweeAmount(splitArray[1].toString().trim());
						value.setDraweeCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setDraweeCustName(splitArray[3].toString().trim());
						else
							value.setDraweeCustName("");
						value.setDraweeInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(BENE_RESTRICTION)) {
						value.setBeneName(splitArray[0].toString().trim());
						value.setBeneAmount(splitArray[1].toString().trim());
						value.setBeneCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setBeneCustName(splitArray[3].toString().trim());
						else
							value.setBeneCustName("");
						value.setBeneInd(ICMSConstant.YES);
					}
					
					covenants.add(value);
				}
			}
		}
		return covenants;
	}

	public static List<OBLineCovenant> getDeleteFlag(String act, String stg, String flag) {
		List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();
		
		if (!isEmptyOrNull(act) && !act.equalsIgnoreCase(stg)) {
			
			String[] actStrList=null;
			
			if(null!=act && act.contains(",")) {
				actStrList = act.split(",");
			}
			else if(null != act && !"".equalsIgnoreCase(act)) {
				actStrList=new String[1];
				actStrList[0]=act;
			}

			for (String actString : actStrList) {
				if (!stg.contains(actString)) {
					
					String[] splitArray = actString.split("\\|");
					OBLineCovenant value = new OBLineCovenant();
					
					if(flag.equalsIgnoreCase(COUNTRY_RESTRICTION)) {
						value.setRestrictedCountryname(splitArray[0].toString().trim());
						value.setRestrictedAmount(splitArray[1].toString().trim());
						value.setRestrictedCountryInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(CURRENCY_RESTRICTION)) {
						value.setRestrictedCurrency(splitArray[0].toString().trim());
						value.setRestrictedCurrencyAmount(splitArray[1].toString().trim());
						value.setRestrictedCurrencyInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(BANK_RESTRICTION)) {
						value.setRestrictedBank(splitArray[0].toString().trim());
						value.setRestrictedBankAmount(splitArray[1].toString().trim());
						value.setRestrictedBankInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(GOODS_RESTRICTION)) {
						value.setGoodsRestrictionParentCode(splitArray[0].toString().trim());
						value.setGoodsRestrictionChildCode(splitArray[1].toString().trim());
						value.setGoodsRestrictionSubChildCode(splitArray[2].toString().trim());
						value.setGoodsRestrictionComboCode(actString.trim());
						value.setGoodsRestrictionInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(DRAWER_RESTRICTION)) {
						value.setDrawerName(splitArray[0].toString().trim());
						value.setDrawerAmount(splitArray[1].toString().trim());
						value.setDrawerCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setDrawerCustName(splitArray[3].toString().trim());
						else
							value.setDrawerCustName("");
						value.setDrawerInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(DRAWEE_RESTRICTION)) {
						value.setDraweeName(splitArray[0].toString().trim());
						value.setDraweeAmount(splitArray[1].toString().trim());
						value.setDraweeCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setDraweeCustName(splitArray[3].toString().trim());
						else
							value.setDraweeCustName("");
						value.setDraweeInd(ICMSConstant.YES);
					}else if(flag.equalsIgnoreCase(BENE_RESTRICTION)) {
						value.setBeneName(splitArray[0].toString().trim());
						value.setBeneAmount(splitArray[1].toString().trim());
						value.setBeneCustId(splitArray[2].toString().trim());
						if(splitArray.length>3)
							value.setBeneCustName(splitArray[3].toString().trim());
						else
							value.setBeneCustName("");
						value.setBeneInd(ICMSConstant.YES);
					}
					covenants.add(value);
				}
			}
		}

		return covenants;
	}

	public static boolean setSingleCovData(ILimitCovenant lmtCov, ILineCovenant lineCov, boolean isLineUpdated ) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

		if (!isEmptyOrNull(lmtCov.getCovenantReqd())) {
			lineCov.setCovenantReqd(lmtCov.getCovenantReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getCountryRestrictionReqd())) {
			lineCov.setCountryRestrictionReqd(lmtCov.getCountryRestrictionReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getDraweeReqd())) {
			lineCov.setDraweeReqd(lmtCov.getDraweeReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getDrawerReqd())) {
			lineCov.setDrawerReqd(lmtCov.getDrawerReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getBeneficiaryReqd())) {
			lineCov.setBeneficiaryReqd(lmtCov.getBeneficiaryReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getGoodsRestrictionReqd())) {
			lineCov.setGoodsRestrictionReqd(lmtCov.getGoodsRestrictionReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getCurrencyRestrictionReqd())) {
			lineCov.setCurrencyRestrictionReqd(lmtCov.getCurrencyRestrictionReqd());
			isLineUpdated=true;
		}
		if (!isEmptyOrNull(lmtCov.getBankRestrictionReqd())) {
			lineCov.setBankRestrictionReqd(lmtCov.getBankRestrictionReqd());
			isLineUpdated=true;
		}
		
		//For Combined Tenor
		if (!isEmptyOrNull(lmtCov.getCombinedTenorReqd())) {
			lineCov.setCombinedTenorReqd(lmtCov.getCombinedTenorReqd());
			lineCov.setMaxCombinedTenor(lmtCov.getMaxCombinedTenor());
			lineCov.setPreshipmentLinkage(lmtCov.getPreshipmentLinkage());
			lineCov.setPostShipmentLinkage(lmtCov.getPostShipmentLinkage());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getMaxCombinedTenor()) && !isEmptyOrNull(lineCov.getMaxCombinedTenor())) {
				lineCov.setMaxCombinedTenor(lmtCov.getMaxCombinedTenor());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getPreshipmentLinkage()) && !isEmptyOrNull(lineCov.getPreshipmentLinkage())) {
				lineCov.setPreshipmentLinkage(lmtCov.getPreshipmentLinkage());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getPostShipmentLinkage()) && !isEmptyOrNull(lineCov.getPostShipmentLinkage())) {
				lineCov.setPostShipmentLinkage(lmtCov.getPostShipmentLinkage());
				isLineUpdated=true;
			}
		}
		//For Running Account(EPC)
		if (!isEmptyOrNull(lmtCov.getRunningAccountReqd())) {
			lineCov.setRunningAccountReqd(lmtCov.getRunningAccountReqd());
			lineCov.setRunningAccount(lmtCov.getRunningAccount());
			lineCov.setOrderBackedbylc(lmtCov.getOrderBackedbylc());
			lineCov.setIncoTerm(lmtCov.getIncoTerm());
			lineCov.setIncoTermMarginPercent(lmtCov.getIncoTermMarginPercent());
			lineCov.setIncoTermDesc(lmtCov.getIncoTermDesc());
			lineCov.setModuleCode(lmtCov.getModuleCode());
			lineCov.setCommitmentTenor(lmtCov.getCommitmentTenor());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getRunningAccount()) && !isEmptyOrNull(lineCov.getRunningAccount())) {
				lineCov.setRunningAccount(lmtCov.getRunningAccount());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getOrderBackedbylc()) && !isEmptyOrNull(lineCov.getOrderBackedbylc())) {
				lineCov.setOrderBackedbylc(lmtCov.getOrderBackedbylc());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getIncoTerm()) && !isEmptyOrNull(lineCov.getIncoTerm())) {
				lineCov.setIncoTerm(lmtCov.getIncoTerm());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getIncoTermMarginPercent()) && !isEmptyOrNull(lineCov.getIncoTermMarginPercent())) {
				lineCov.setIncoTermMarginPercent(lmtCov.getIncoTermMarginPercent());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getIncoTermDesc()) && !isEmptyOrNull(lineCov.getIncoTermDesc())) {
				lineCov.setIncoTermDesc(lmtCov.getIncoTermDesc());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getModuleCode()) && !isEmptyOrNull(lineCov.getModuleCode())) {
				lineCov.setModuleCode(lmtCov.getModuleCode());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getCommitmentTenor()) && !isEmptyOrNull(lineCov.getCommitmentTenor())) {
				lineCov.setCommitmentTenor(lmtCov.getCommitmentTenor());
				isLineUpdated=true;
			}
		}
		//For Sell Down
		if (!isEmptyOrNull(lmtCov.getSellDownReqd())) {
			lineCov.setSellDownReqd(lmtCov.getSellDownReqd());
			lineCov.setSellDown(lmtCov.getSellDown());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getSellDown())&&!isEmptyOrNull(lineCov.getSellDown())) {
				lineCov.setSellDown(lmtCov.getSellDown());
				isLineUpdated=true;
			}
		}
		//For Last Available Date
		if (!isEmptyOrNull(lmtCov.getLastAvailableDateReqd())) {
			lineCov.setLastAvailableDateReqd(lmtCov.getLastAvailableDateReqd());
			isLineUpdated=true;
			try {
				if (lmtCov.getLastAvailableDate() != null && !"".equals(lmtCov.getLastAvailableDate())) {
					lineCov.setLastAvailableDate(sdf.parse(sdf.format(lmtCov.getLastAvailableDate())));
					isLineUpdated=true;
				}
			} catch (ParseException e) {
				System.out.println("setSingleCovData() getting exception in parsing date " + e.getMessage());
				e.printStackTrace();
			}
		}else {
			try {
				if (lmtCov.getLastAvailableDate() != null && !"".equals(lmtCov.getLastAvailableDate()) 
						&& lineCov.getLastAvailableDate() != null && !"".equals(lineCov.getLastAvailableDate())) {
					lineCov.setLastAvailableDate(sdf.parse(sdf.format(lmtCov.getLastAvailableDate())));
					isLineUpdated=true;
				}
			} catch (ParseException e) {
				System.out.println("setSingleCovData() getting exception in parsing date " + e.getMessage());
				e.printStackTrace();
			}
		}
		//For Moratorium Period(In Months)
		if (!isEmptyOrNull(lmtCov.getMoratoriumReqd())) {
			lineCov.setMoratoriumReqd(lmtCov.getMoratoriumReqd());
			lineCov.setMoratoriumPeriod(lmtCov.getMoratoriumPeriod());
			lineCov.setEmiFrequency(lmtCov.getEmiFrequency());
			lineCov.setNoOfInstallments(lmtCov.getNoOfInstallments());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getMoratoriumPeriod()) && !isEmptyOrNull(lineCov.getMoratoriumPeriod())) {
				lineCov.setMoratoriumPeriod(lmtCov.getMoratoriumPeriod());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getEmiFrequency()) && !isEmptyOrNull(lineCov.getEmiFrequency())) {
				lineCov.setEmiFrequency(lmtCov.getEmiFrequency());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getNoOfInstallments()) && !isEmptyOrNull(lineCov.getNoOfInstallments())) {
				lineCov.setNoOfInstallments(lmtCov.getNoOfInstallments());
				isLineUpdated=true;
			}
		}
		//For Buyers Rating
		if (!isEmptyOrNull(lmtCov.getBuyersRatingReqd())) {
			lineCov.setBuyersRatingReqd(lmtCov.getBuyersRatingReqd());
			lineCov.setBuyersRating(lmtCov.getBuyersRating());
			lineCov.setAgencyMaster(lmtCov.getAgencyMaster());
			lineCov.setRatingMaster(lmtCov.getRatingMaster());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getBuyersRating()) && !isEmptyOrNull(lineCov.getBuyersRating())) {
				lineCov.setBuyersRating(lmtCov.getBuyersRating());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getAgencyMaster()) && !isEmptyOrNull(lineCov.getAgencyMaster())) {
				lineCov.setAgencyMaster(lmtCov.getAgencyMaster());
				isLineUpdated=true;
			}
			if (!isEmptyOrNull(lmtCov.getRatingMaster()) && !isEmptyOrNull(lineCov.getRatingMaster())) {
				lineCov.setRatingMaster(lmtCov.getRatingMaster());
				isLineUpdated=true;
			}
		}
		//For ECGC Cover
		if (!isEmptyOrNull(lmtCov.getEcgcCoverReqd())) {
			lineCov.setEcgcCoverReqd(lmtCov.getEcgcCoverReqd());
			lineCov.setEcgcCover(lmtCov.getEcgcCover());
			isLineUpdated=true;
		}else {
			if (!isEmptyOrNull(lmtCov.getEcgcCover()) && !isEmptyOrNull(lineCov.getEcgcCover())) {
				lineCov.setEcgcCover(lmtCov.getEcgcCover());
				isLineUpdated=true;
			}
		}
		
		return isLineUpdated;
	}

	private static boolean isEmptyOrNull(String aString) {
		if (aString == null) {
			return true;
		}

		return aString.trim().equals("");
	}
	
	private void compareActualStageData(ILimit lmtAct, ILimit stgLmt, ILimitCovenant[] lmtCovAct, ILimitCovenant[] lmtCovStg, ILimitSysXRef[] refArrStg, ILineCovenant[] lineCovStg) {
		
		if (lmtCovAct != null && !ArrayUtils.isEmpty(lmtCovAct)) {
			// compare actual limit covenant with stage limit covenant
			Map covMap = compareLmtCovenant(lmtCovAct, lmtCovStg, true);
			setLineCovenantData(covMap, lmtAct, stgLmt, lmtCovAct, lmtCovStg, refArrStg, lineCovStg);

		} else {
			// copy all the stage limit covenants to line covenants as actual limit covenants are null
			Map covMap = compareLmtCovenant(lmtCovAct, lmtCovStg, false);
			setLineCovenantData(covMap, lmtAct, stgLmt, lmtCovAct, lmtCovStg, refArrStg, lineCovStg);
			
		}
	}
	
	private void setLineCovenantData(Map covMap, ILimit lmtAct, ILimit stgLmt, ILimitCovenant[] lmtCovAct, ILimitCovenant[] lmtCovStg, ILimitSysXRef[] refArrStg, ILineCovenant[] lineCovStg) {
		ILimitCovenant lmtCov = (OBLimitCovenant) covMap.get(SINGLE_COV_FOR_LINE);
		
		List<OBLineCovenant> restCountryAddListForLine = (List<OBLineCovenant>) covMap.get(REST_COUNTRY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restCountryDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_COUNTRY_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restCurrencyAddListForLine = (List<OBLineCovenant>) covMap.get(REST_CURRENCY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restCurrencyDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_CURRENCY_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restBankAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BANK_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBankDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_BANK_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restGoodsAddListForLine = (List<OBLineCovenant>) covMap.get(REST_GOODS_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restGoodsDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_GOODS_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restDrawerAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWER_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDrawerDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWER_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restDraweeAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWEE_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDraweeDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWEE_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restBeneAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BENE_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBeneDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_BENE_DELETE_LIST_FOR_LINE);
		
		//Need to check here if actual limit and stage limit are same?
		if((lmtCov!=null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry()))
				||!CollectionUtils.isEmpty(restCountryAddListForLine)
				||!CollectionUtils.isEmpty(restCountryDeleteListForLine)
				||!CollectionUtils.isEmpty(restCurrencyAddListForLine)
				||!CollectionUtils.isEmpty(restCurrencyDeleteListForLine)
				||!CollectionUtils.isEmpty(restBankAddListForLine)
				||!CollectionUtils.isEmpty(restBankDeleteListForLine)
				||!CollectionUtils.isEmpty(restGoodsAddListForLine)
				||!CollectionUtils.isEmpty(restGoodsDeleteListForLine)
				||!CollectionUtils.isEmpty(restDrawerAddListForLine)
				||!CollectionUtils.isEmpty(restDrawerDeleteListForLine)
				||!CollectionUtils.isEmpty(restDraweeAddListForLine)
				||!CollectionUtils.isEmpty(restDraweeDeleteListForLine)
				||!CollectionUtils.isEmpty(restBeneAddListForLine)
				||!CollectionUtils.isEmpty(restBeneDeleteListForLine)
				) {
			
			refArrStg = stgLmt.getLimitSysXRefs();
			if(refArrStg!=null)
			{
				// Iterate all the lines and update those lines which are in SUCCESS/REJECT status
				for (int j = 0; j < refArrStg.length; j++) {
					// having at least one line
					ICustomerSysXRef xrefStgObj = refArrStg[j].getCustomerSysXRef();
					ILineCovenant lineCov = null;
					boolean isLineUpdated=false;

					// Update the cov for SUCCESS/REJECT line status
					if ("SUCCESS".equals(xrefStgObj.getStatus()) || "REJECTED".equals(xrefStgObj.getStatus())) {
						lineCovStg = xrefStgObj.getLineCovenant();
						// Update only If Line covenant is not null
						if (lineCovStg != null && lineCovStg.length != 0) {
							List<OBLineCovenant> restCountryListForLine =new ArrayList();
							List<OBLineCovenant> restCurrencyListForLine = new ArrayList();
							List<OBLineCovenant> restBankListForLine = new ArrayList();
							List<OBLineCovenant> restDrawerListForLine = new ArrayList();
							List<OBLineCovenant> restDraweeListForLine = new ArrayList();
							List<OBLineCovenant> restBeneListForLine = new ArrayList();
							List<OBLineCovenant> restGoodsRestrictionListForLine = new ArrayList();

							for (ILineCovenant cov : lineCovStg) {
								if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
									lineCov=cov;
								}
								else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
									restBeneListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
									restDraweeListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
									restDrawerListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
									restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
									restBankListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
									restCountryListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
									restCurrencyListForLine.add((OBLineCovenant) cov);
								}
							}

							//Below logic is for replacing line level cov with limit level cov
							List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();

							//Replace Single covenant values
							if(lmtCov!=null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry())) {
								isLineUpdated=setSingleCovData(lmtCov, lineCov,isLineUpdated);
								covenants.add((OBLineCovenant) lineCov);
							}else {
								covenants.add((OBLineCovenant) lineCov);
							}

							//Replace added/deleted Country restriction values
							if(!CollectionUtils.isEmpty(restCountryDeleteListForLine) && !CollectionUtils.isEmpty(restCountryListForLine)) {
								restCountryListForLine.removeAll(restCountryDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restCountryAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getCountryRestrictionReqd())) {
									restCountryListForLine.addAll(restCountryAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getCountryRestrictionReqd())) {
									//This is for new covenant added with restriction and its values
									restCountryListForLine.addAll(restCountryAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restCountryListForLine)) {
								covenants.addAll(restCountryListForLine);
							}

							//Replace added/deleted Currency restriction values
							if(!CollectionUtils.isEmpty(restCurrencyDeleteListForLine) && !CollectionUtils.isEmpty(restCurrencyListForLine)) {
								restCurrencyListForLine.removeAll(restCurrencyDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restCurrencyAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getCurrencyRestrictionReqd())) {
									restCurrencyListForLine.addAll(restCurrencyAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getCurrencyRestrictionReqd())) {
									//This is for new covenant added with restriction and its values
									restCurrencyListForLine.addAll(restCurrencyAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restCurrencyListForLine)) {
								covenants.addAll(restCurrencyListForLine);
							}

							//Replace added/deleted Bank restriction values
							if(!CollectionUtils.isEmpty(restBankDeleteListForLine) && !CollectionUtils.isEmpty(restBankListForLine)) {
								restBankListForLine.removeAll(restBankDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restBankAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getBankRestrictionReqd())) {
									restBankListForLine.addAll(restBankAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getBankRestrictionReqd())) {
									//This is for new covenant added with restriction and its values
									restBankListForLine.addAll(restBankAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restBankListForLine)) {
								covenants.addAll(restBankListForLine);
							}

							//Replace added/deleted Goods restriction values
							if(!CollectionUtils.isEmpty(restGoodsDeleteListForLine) && !CollectionUtils.isEmpty(restGoodsRestrictionListForLine)) {
								restGoodsRestrictionListForLine.removeAll(restGoodsDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restGoodsAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getGoodsRestrictionReqd())) {
									restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getGoodsRestrictionReqd())) {
									//This is for new covenant added with restriction and its values
									restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restGoodsRestrictionListForLine)) {
								covenants.addAll(restGoodsRestrictionListForLine);
							}

							//Replace added/deleted Drawer restriction values
							if(!CollectionUtils.isEmpty(restDrawerDeleteListForLine) && !CollectionUtils.isEmpty(restDrawerListForLine)) {
								restDrawerListForLine.removeAll(restDrawerDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restDrawerAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getDrawerReqd())) {
									restDrawerListForLine.addAll(restDrawerAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getDrawerReqd())) {
									//This is for new covenant added with restriction and its values
									restDrawerListForLine.addAll(restDrawerAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restDrawerListForLine)) {
								covenants.addAll(restDrawerListForLine);
							}

							//Replace added/deleted Drawee restriction values
							if(!CollectionUtils.isEmpty(restDraweeDeleteListForLine) && !CollectionUtils.isEmpty(restDraweeListForLine)) {
								restDraweeListForLine.removeAll(restDraweeDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restDraweeAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getDraweeReqd())) {
									restDraweeListForLine.addAll(restDraweeAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getDraweeReqd())) {
									//This is for new covenant added with restriction and its values
									restDraweeListForLine.addAll(restDraweeAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restDraweeListForLine)) {
								covenants.addAll(restDraweeListForLine);
							}

							//Replace added/deleted Beneficiary restriction values
							if(!CollectionUtils.isEmpty(restBeneDeleteListForLine) && !CollectionUtils.isEmpty(restBeneListForLine)) {
								restBeneListForLine.removeAll(restBeneDeleteListForLine);
								isLineUpdated=true;
							}
							if(!CollectionUtils.isEmpty(restBeneAddListForLine)) {
								//check restriction required should not be null at line level
								if(!isEmptyOrNull(lineCov.getBeneficiaryReqd())) {
									restBeneListForLine.addAll(restBeneAddListForLine);
									isLineUpdated=true;
								}else if(!isEmptyOrNull(lmtCov.getBeneficiaryReqd())) {
									//This is for new covenant added with restriction and its values
									restBeneListForLine.addAll(restBeneAddListForLine);
									isLineUpdated=true;
								}
							}
							if(!CollectionUtils.isEmpty(restBeneListForLine)) {
								covenants.addAll(restBeneListForLine);
							}

							//Check if line needs to be updated or not
							if(isLineUpdated) {
								xrefStgObj.setLineCovenant((ILineCovenant[]) covenants.toArray(new ILineCovenant[0]));
								xrefStgObj.setStatus("PENDING_UPDATE");
								xrefStgObj.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);
								refArrStg[j].setCustomerSysXRef(xrefStgObj);
							}
						}
					}
				}

				stgLmt.setLimitSysXRefs(refArrStg);
			}
		}
	}
	
	public static ILineCovenant[] copyLimitCovenantToLineCovenant(Map covMap, ILineCovenant[] lineCovStg) {

		ILineCovenant lineCov = new OBLineCovenant(); ;
		List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();
		ILimitCovenant lmtCov = (OBLimitCovenant) covMap.get(SINGLE_COV_FOR_LINE);

		List<OBLineCovenant> restCountryAddListForLine = (List<OBLineCovenant>) covMap.get(REST_COUNTRY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restCurrencyAddListForLine = (List<OBLineCovenant>) covMap.get(REST_CURRENCY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBankAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BANK_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restGoodsAddListForLine = (List<OBLineCovenant>) covMap.get(REST_GOODS_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDrawerAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWER_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDraweeAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWEE_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBeneAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BENE_ADD_LIST_FOR_LINE);

		// Need to check here if actual limit and stage limit are same?
		if ((lmtCov != null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry()))
				|| !CollectionUtils.isEmpty(restCountryAddListForLine)
				|| !CollectionUtils.isEmpty(restCurrencyAddListForLine)
				|| !CollectionUtils.isEmpty(restBankAddListForLine) 
				|| !CollectionUtils.isEmpty(restGoodsAddListForLine)
				|| !CollectionUtils.isEmpty(restDrawerAddListForLine)
				|| !CollectionUtils.isEmpty(restDraweeAddListForLine)
				|| !CollectionUtils.isEmpty(restBeneAddListForLine)) {

			List<OBLineCovenant> restCountryListForLine = new ArrayList();
			List<OBLineCovenant> restCurrencyListForLine = new ArrayList();
			List<OBLineCovenant> restBankListForLine = new ArrayList();
			List<OBLineCovenant> restDrawerListForLine = new ArrayList();
			List<OBLineCovenant> restDraweeListForLine = new ArrayList();
			List<OBLineCovenant> restBeneListForLine = new ArrayList();
			List<OBLineCovenant> restGoodsRestrictionListForLine = new ArrayList();

			for (ILineCovenant cov : lineCovStg) {
				if (ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
					lineCov = cov;
				} else if (ICMSConstant.YES.equals(cov.getBeneInd())) {
					restBeneListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getDraweeInd())) {
					restDraweeListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getDrawerInd())) {
					restDrawerListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
					restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
					restBankListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
					restCountryListForLine.add((OBLineCovenant) cov);
				} else if (ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
					restCurrencyListForLine.add((OBLineCovenant) cov);
				}
			}

			// Replace Single covenant values
			if (lmtCov != null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry())) {
				lineCov.setSingleCovenantInd(ICMSConstant.YES);
				setSingleCovData(lmtCov, lineCov, true);
				covenants.add((OBLineCovenant) lineCov);
			} else {
				covenants.add((OBLineCovenant) lineCov);
			}

			// Replace added/deleted Country restriction values
			if (!CollectionUtils.isEmpty(restCountryAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getCountryRestrictionReqd())) {
					restCountryListForLine.addAll(restCountryAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getCountryRestrictionReqd())) {
					// This is for new covenant added with restriction and its values
					restCountryListForLine.addAll(restCountryAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restCountryListForLine)) {
				covenants.addAll(restCountryListForLine);
			}

			// Replace added/deleted Currency restriction values
			if (!CollectionUtils.isEmpty(restCurrencyAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getCurrencyRestrictionReqd())) {
					restCurrencyListForLine.addAll(restCurrencyAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getCurrencyRestrictionReqd())) {
					// This is for new covenant added with restriction and its values
					restCurrencyListForLine.addAll(restCurrencyAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restCurrencyListForLine)) {
				covenants.addAll(restCurrencyListForLine);
			}

			// Replace added/deleted Bank restriction values
			if (!CollectionUtils.isEmpty(restBankAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getBankRestrictionReqd())) {
					restBankListForLine.addAll(restBankAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getBankRestrictionReqd())) {
					// This is for new covenant added with restriction and its values
					restBankListForLine.addAll(restBankAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restBankListForLine)) {
				covenants.addAll(restBankListForLine);
			}

			// Replace added/deleted Goods restriction values
			if (!CollectionUtils.isEmpty(restGoodsAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getGoodsRestrictionReqd())) {
					restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getGoodsRestrictionReqd())) {
					// This is for new covenant added with restriction and its values
					restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restGoodsRestrictionListForLine)) {
				covenants.addAll(restGoodsRestrictionListForLine);
			}

			// Replace added/deleted Drawer restriction values
			if (!CollectionUtils.isEmpty(restDrawerAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getDrawerReqd())) {
					restDrawerListForLine.addAll(restDrawerAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getDrawerReqd())) {
					// This is for new covenant added with restriction and its values
					restDrawerListForLine.addAll(restDrawerAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restDrawerListForLine)) {
				covenants.addAll(restDrawerListForLine);
			}

			// Replace added/deleted Drawee restriction values
			if (!CollectionUtils.isEmpty(restDraweeAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getDraweeReqd())) {
					restDraweeListForLine.addAll(restDraweeAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getDraweeReqd())) {
					// This is for new covenant added with restriction and its values
					restDraweeListForLine.addAll(restDraweeAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restDraweeListForLine)) {
				covenants.addAll(restDraweeListForLine);
			}

			// Replace added/deleted Beneficiary restriction values
			if (!CollectionUtils.isEmpty(restBeneAddListForLine)) {
				// check restriction required should not be null at line level
				if (!isEmptyOrNull(lineCov.getBeneficiaryReqd())) {
					restBeneListForLine.addAll(restBeneAddListForLine);
				} else if (!isEmptyOrNull(lmtCov.getBeneficiaryReqd())) {
					// This is for new covenant added with restriction and its values
					restBeneListForLine.addAll(restBeneAddListForLine);
				}
			}
			if (!CollectionUtils.isEmpty(restBeneListForLine)) {
				covenants.addAll(restBeneListForLine);
			}
		}
		return (OBLineCovenant[]) covenants.toArray(new OBLineCovenant[0]);
	}
	
	public ILimitSysXRef setLimitCovenantDataForSpecificLine(Map covMap,ILimitSysXRef refArrStg) {
		
		ILineCovenant[] lineCovStg = null;
		ILimitCovenant lmtCov = (OBLimitCovenant) covMap.get(SINGLE_COV_FOR_LINE);
		
		List<OBLineCovenant> restCountryAddListForLine = (List<OBLineCovenant>) covMap.get(REST_COUNTRY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restCountryDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_COUNTRY_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restCurrencyAddListForLine = (List<OBLineCovenant>) covMap.get(REST_CURRENCY_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restCurrencyDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_CURRENCY_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restBankAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BANK_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBankDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_BANK_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restGoodsAddListForLine = (List<OBLineCovenant>) covMap.get(REST_GOODS_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restGoodsDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_GOODS_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restDrawerAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWER_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDrawerDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWER_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restDraweeAddListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWEE_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restDraweeDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_DRAWEE_DELETE_LIST_FOR_LINE);
		
		List<OBLineCovenant> restBeneAddListForLine = (List<OBLineCovenant>) covMap.get(REST_BENE_ADD_LIST_FOR_LINE);
		List<OBLineCovenant> restBeneDeleteListForLine = (List<OBLineCovenant>) covMap.get(REST_BENE_DELETE_LIST_FOR_LINE);
		
		//Need to check here if actual limit and stage limit are same?
		if((lmtCov!=null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry()))
				||!CollectionUtils.isEmpty(restCountryAddListForLine)
				||!CollectionUtils.isEmpty(restCountryDeleteListForLine)
				||!CollectionUtils.isEmpty(restCurrencyAddListForLine)
				||!CollectionUtils.isEmpty(restCurrencyDeleteListForLine)
				||!CollectionUtils.isEmpty(restBankAddListForLine)
				||!CollectionUtils.isEmpty(restBankDeleteListForLine)
				||!CollectionUtils.isEmpty(restGoodsAddListForLine)
				||!CollectionUtils.isEmpty(restGoodsDeleteListForLine)
				||!CollectionUtils.isEmpty(restDrawerAddListForLine)
				||!CollectionUtils.isEmpty(restDrawerDeleteListForLine)
				||!CollectionUtils.isEmpty(restDraweeAddListForLine)
				||!CollectionUtils.isEmpty(restDraweeDeleteListForLine)
				||!CollectionUtils.isEmpty(restBeneAddListForLine)
				||!CollectionUtils.isEmpty(restBeneDeleteListForLine)
				) {
			
			//refArrStg = stgLmt.getLimitSysXRefs();
			
			// Iterate all the lines and update those lines which are in SUCCESS/REJECT status
			/*for (int j = 0; j < refArrStg.length; j++) {*/
				// having at least one line
				ICustomerSysXRef xrefStgObj = refArrStg.getCustomerSysXRef();
				ILineCovenant lineCov = null;
				boolean isLineUpdated=false;

				// Update the cov for SUCCESS/REJECT line status
				if ("SUCCESS".equals(xrefStgObj.getStatus()) || "REJECTED".equals(xrefStgObj.getStatus())) {
					lineCovStg = xrefStgObj.getLineCovenant();
					// Update only If Line covenant is not null
					if (lineCovStg != null && lineCovStg.length != 0) {
						List<OBLineCovenant> restCountryListForLine =new ArrayList();
						List<OBLineCovenant> restCurrencyListForLine = new ArrayList();
						List<OBLineCovenant> restBankListForLine = new ArrayList();
						List<OBLineCovenant> restDrawerListForLine = new ArrayList();
						List<OBLineCovenant> restDraweeListForLine = new ArrayList();
						List<OBLineCovenant> restBeneListForLine = new ArrayList();
						List<OBLineCovenant> restGoodsRestrictionListForLine = new ArrayList();

						for (ILineCovenant cov : lineCovStg) {
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								lineCov=cov;
							}
							else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
								restBeneListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
								restDraweeListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
								restDrawerListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
								restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
								restBankListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
								restCountryListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
								restCurrencyListForLine.add((OBLineCovenant) cov);
							}
						}
						
						//Below logic is for replacing line level cov with limit level cov
						List<OBLineCovenant> covenants = new ArrayList<OBLineCovenant>();
						
						//Replace Single covenant values
						if(lmtCov!=null && ICMSConstant.YES.equals(lmtCov.getIsNewEntry())) {
							isLineUpdated=setSingleCovData(lmtCov, lineCov,isLineUpdated);
							covenants.add((OBLineCovenant) lineCov);
						}else {
							covenants.add((OBLineCovenant) lineCov);
						}
						
						//Replace added/deleted Country restriction values
						if(!CollectionUtils.isEmpty(restCountryDeleteListForLine) && !CollectionUtils.isEmpty(restCountryListForLine)) {
							restCountryListForLine.removeAll(restCountryDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restCountryAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getCountryRestrictionReqd())) {
								restCountryListForLine.addAll(restCountryAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getCountryRestrictionReqd())) {
								//This is for new covenant added with restriction and its values
								restCountryListForLine.addAll(restCountryAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restCountryListForLine)) {
							covenants.addAll(restCountryListForLine);
						}
						
						//Replace added/deleted Currency restriction values
						if(!CollectionUtils.isEmpty(restCurrencyDeleteListForLine) && !CollectionUtils.isEmpty(restCurrencyListForLine)) {
							restCurrencyListForLine.removeAll(restCurrencyDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restCurrencyAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getCurrencyRestrictionReqd())) {
								restCurrencyListForLine.addAll(restCurrencyAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getCurrencyRestrictionReqd())) {
								//This is for new covenant added with restriction and its values
								restCurrencyListForLine.addAll(restCurrencyAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restCurrencyListForLine)) {
							covenants.addAll(restCurrencyListForLine);
						}
						
						//Replace added/deleted Bank restriction values
						if(!CollectionUtils.isEmpty(restBankDeleteListForLine) && !CollectionUtils.isEmpty(restBankListForLine)) {
							restBankListForLine.removeAll(restBankDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restBankAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getBankRestrictionReqd())) {
								restBankListForLine.addAll(restBankAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getBankRestrictionReqd())) {
								//This is for new covenant added with restriction and its values
								restBankListForLine.addAll(restBankAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restBankListForLine)) {
							covenants.addAll(restBankListForLine);
						}
						
						//Replace added/deleted Goods restriction values
						if(!CollectionUtils.isEmpty(restGoodsDeleteListForLine) && !CollectionUtils.isEmpty(restGoodsRestrictionListForLine)) {
							restGoodsRestrictionListForLine.removeAll(restGoodsDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restGoodsAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getGoodsRestrictionReqd())) {
								restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getGoodsRestrictionReqd())) {
								//This is for new covenant added with restriction and its values
								restGoodsRestrictionListForLine.addAll(restGoodsAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restGoodsRestrictionListForLine)) {
							covenants.addAll(restGoodsRestrictionListForLine);
						}
						
						//Replace added/deleted Drawer restriction values
						if(!CollectionUtils.isEmpty(restDrawerDeleteListForLine) && !CollectionUtils.isEmpty(restDrawerListForLine)) {
							restDrawerListForLine.removeAll(restDrawerDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restDrawerAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getDrawerReqd())) {
								restDrawerListForLine.addAll(restDrawerAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getDrawerReqd())) {
								//This is for new covenant added with restriction and its values
								restDrawerListForLine.addAll(restDrawerAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restDrawerListForLine)) {
							covenants.addAll(restDrawerListForLine);
						}
						
						//Replace added/deleted Drawee restriction values
						if(!CollectionUtils.isEmpty(restDraweeDeleteListForLine) && !CollectionUtils.isEmpty(restDraweeListForLine)) {
							restDraweeListForLine.removeAll(restDraweeDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restDraweeAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getDraweeReqd())) {
								restDraweeListForLine.addAll(restDraweeAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getDraweeReqd())) {
								//This is for new covenant added with restriction and its values
								restDraweeListForLine.addAll(restDraweeAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restDraweeListForLine)) {
							covenants.addAll(restDraweeListForLine);
						}
						
						//Replace added/deleted Beneficiary restriction values
						if(!CollectionUtils.isEmpty(restBeneDeleteListForLine) && !CollectionUtils.isEmpty(restBeneListForLine)) {
							restBeneListForLine.removeAll(restBeneDeleteListForLine);
							isLineUpdated=true;
						}
						if(!CollectionUtils.isEmpty(restBeneAddListForLine)) {
							//check restriction required should not be null at line level
							if(!isEmptyOrNull(lineCov.getBeneficiaryReqd())) {
								restBeneListForLine.addAll(restBeneAddListForLine);
								isLineUpdated=true;
							}else if(!isEmptyOrNull(lmtCov.getBeneficiaryReqd())) {
								//This is for new covenant added with restriction and its values
								restBeneListForLine.addAll(restBeneAddListForLine);
								isLineUpdated=true;
							}
						}
						if(!CollectionUtils.isEmpty(restBeneListForLine)) {
							covenants.addAll(restBeneListForLine);
						}
						
						//Check if line needs to be updated or not
						if(isLineUpdated) {
							xrefStgObj.setLineCovenant((ILineCovenant[]) covenants.toArray(new ILineCovenant[0]));
						/*	xrefStgObj.setStatus("PENDING_UPDATE");
							xrefStgObj.setAction(ICMSConstant.FCUBSLIMIT_ACTION_MODIFY);*/
							refArrStg.setCustomerSysXRef(xrefStgObj);
						}
					}
				}
			//}
			/*stgLmt.setLimitSysXRefs(refArrStg);*/
		}
		return refArrStg;
	}
}
