package com.integrosys.cms.ui.limit.facility.bnmcodes;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityBnmCodes;
import com.integrosys.cms.app.limit.bus.OBFacilityBnmCodes;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class BNMCodesMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		BNMCodesForm form = (BNMCodesForm) cForm;
		IFacilityMaster facilityMasterObj = (IFacilityMaster) inputs.get("facilityMasterObj");
		if (facilityMasterObj == null) {
			facilityMasterObj = new OBFacilityMaster();
		}
		if (facilityMasterObj.getFacilityBnmCodes() == null) {
			facilityMasterObj.setFacilityBnmCodes(new OBFacilityBnmCodes());
		}
		facilityMasterObj.getFacilityBnmCodes().setIndustryCodeCategoryCode(CategoryCodeConstant.BNM_INDUSTRY);
		facilityMasterObj.getFacilityBnmCodes().setIndustryCodeEntryCode(form.getIndustryCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setSectorCodeCategoryCode(CategoryCodeConstant.CUSTOMER_TYPE);
		facilityMasterObj.getFacilityBnmCodes().setSectorCodeEntryCode(form.getSectorCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setStateCodeCategoryCode(CategoryCodeConstant.BNM_STATE);
		facilityMasterObj.getFacilityBnmCodes().setStateCodeEntryCode(form.getStateCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setBumiOrNrccCodeCategoryCode(CategoryCodeConstant.BNM_BUMI_NRCC);
		facilityMasterObj.getFacilityBnmCodes().setBumiOrNrccCodeEntryCode(form.getBumiOrNrccCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setSmallScaleCodeCodeCategoryCode(CategoryCodeConstant.BNM_SML_SCALE);
		facilityMasterObj.getFacilityBnmCodes().setSmallScaleCodeCodeEntryCode(form.getSmallScaleCodeCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setPrescribedRateCodeCategoryCode(CategoryCodeConstant.BNM_PRSCB_RT);
		facilityMasterObj.getFacilityBnmCodes().setPrescribedRateCodeEntryCode(form.getPrescribedRateCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setRelationshipCodeCategoryCode(CategoryCodeConstant.BNM_RELSHIP);
		facilityMasterObj.getFacilityBnmCodes().setRelationshipCodeEntryCode(form.getRelationshipCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setGoodsFinancedCodeOneCategoryCode(
				CategoryCodeConstant.BNM_GOOD_FINANCE_1);
		facilityMasterObj.getFacilityBnmCodes().setGoodsFinancedCodeOneEntryCode(
				form.getGoodsFinancedCodeOneEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setGoodsFinancedCodeTwoCategoryCode(
				CategoryCodeConstant.BNM_GOOD_FINANCE_2);
		facilityMasterObj.getFacilityBnmCodes().setGoodsFinancedCodeTwoEntryCode(
				form.getGoodsFinancedCodeTwoEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setExemptedCodeCategoryCode(CategoryCodeConstant.BNM_EXMPT_CODE);
		facilityMasterObj.getFacilityBnmCodes().setExemptedCodeEntryCode(form.getExemptedCodeEntryCode());
		facilityMasterObj.getFacilityBnmCodes().setBnmTierSeqCategoryCode(CategoryCodeConstant.BNM_STATE);
		if (StringUtils.isNotBlank(form.getHostTierSequenceNumber())) {
			facilityMasterObj.getFacilityBnmCodes().setHostTierSequenceNumber(
					Long.valueOf(form.getHostTierSequenceNumber()));
		}
		facilityMasterObj.getFacilityBnmCodes().setPurposeCodeCategoryCode(CategoryCodeConstant.BNM_PURPOSE);
		facilityMasterObj.getFacilityBnmCodes().setPurposeCodeEntryCode(form.getPurposeCodeEntryCode());
		if (StringUtils.isNotBlank(form.getExemptedCodeIndicator())) {
			facilityMasterObj.getFacilityBnmCodes().setExemptedCodeIndicator(
					new Character(form.getExemptedCodeIndicator().charAt(0)));
		}
		return facilityMasterObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		BNMCodesForm form = (BNMCodesForm) cForm;
		IFacilityBnmCodes bnmCodes = (IFacilityBnmCodes) obj;
		if (obj != null) {
			form.setIndustryCodeEntryCode(bnmCodes.getIndustryCodeEntryCode());
			form.setSectorCodeEntryCode(bnmCodes.getSectorCodeEntryCode());
			form.setStateCodeEntryCode(bnmCodes.getStateCodeEntryCode());
			form.setBumiOrNrccCodeEntryCode(bnmCodes.getBumiOrNrccCodeEntryCode());
			form.setSmallScaleCodeCodeEntryCode(bnmCodes.getSmallScaleCodeCodeEntryCode());
			form.setPrescribedRateCodeEntryCode(bnmCodes.getPrescribedRateCodeEntryCode());
			form.setRelationshipCodeEntryCode(bnmCodes.getRelationshipCodeEntryCode());
			form.setGoodsFinancedCodeOneEntryCode(bnmCodes.getGoodsFinancedCodeOneEntryCode());
			form.setGoodsFinancedCodeTwoEntryCode(bnmCodes.getGoodsFinancedCodeTwoEntryCode());
			form.setExemptedCodeEntryCode(bnmCodes.getExemptedCodeEntryCode());
			if (bnmCodes.getHostTierSequenceNumber() != null) {
				form.setHostTierSequenceNumber(String.valueOf(bnmCodes.getHostTierSequenceNumber()));
			}
			form.setPurposeCodeEntryCode(bnmCodes.getPurposeCodeEntryCode());
			form.setExemptedCodeIndicator(String.valueOf(bnmCodes.getExemptedCodeIndicator()));
		}
		return form;
	}
}
