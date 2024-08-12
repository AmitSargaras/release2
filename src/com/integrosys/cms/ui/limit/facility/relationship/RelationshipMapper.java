package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityRelationship;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class RelationshipMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		IFacilityMaster facilityMasterObj = (IFacilityMaster) inputs.get("facilityMasterObj");
		ILimit limit = (ILimit) inputs.get("limit");
		if (limit == null) {
			limit = facilityMasterObj.getLimit();
		}
		// String currencyCode =
		// limit.getApprovedLimitAmount().getCurrencyCode();
		RelationshipForm form = (RelationshipForm) cForm;
		IFacilityRelationship facilityRelationship = new OBFacilityRelationship();
		facilityRelationship.setCurrencyCode(limit.getApprovedLimitAmount().getCurrencyCode());
		if (facilityRelationship.getCmsLegalEntity() == null) {
			facilityRelationship.setCmsLegalEntity(new OBCMSLegalEntity());
		}

		facilityRelationship.getCmsLegalEntity().setLEReference(form.getLEReference());
		facilityRelationship.getCmsLegalEntity().setLegalName(form.getLegalName());

        //Andy Wong: also set CIF and cust name to relationship attr
        facilityRelationship.setCifNumber(form.getLEReference());
		facilityRelationship.setCustomerName(form.getLegalName());

		facilityRelationship.setAccountRelationshipCategoryCode(CategoryCodeConstant.RELATIONSHIP);
		facilityRelationship.setAccountRelationshipEntryCode(form.getAccountRelationshipEntryCode());
		if (StringUtils.isNotBlank(form.getShareHolderPercentage())) {
			facilityRelationship.setShareHolderPercentage(Double.valueOf(form.getShareHolderPercentage()));
		}
		if ("percentage".equals(form.getGuaranteeFlag())) {
			if (StringUtils.isNotBlank(form.getGuaranteePercentage())) {
				facilityRelationship.setGuaranteePercentage(Double.valueOf(form.getGuaranteePercentage()));
			}
			facilityRelationship.setGuaranteeAmount(null);
		}
		else if ("amount".equals(form.getGuaranteeFlag())) {
			if (StringUtils.isNotBlank(form.getGuaranteeAmount())) {
				facilityRelationship.setGuaranteeAmount(new Amount(UIUtil.mapStringToBigDecimal(form
						.getGuaranteeAmount()), limit.getApprovedLimitAmount().getCurrencyCodeAsObject()));
			}
			facilityRelationship.setGuaranteePercentage(null);
		}
        if (StringUtils.isNotBlank(form.getHostAddressSequenceNumber())) {
            facilityRelationship.setHostAddressSequenceNumber(Long.valueOf(form.getHostAddressSequenceNumber()));
        }
        if (StringUtils.isNotBlank(form.getReceiveMailCode())) {
			facilityRelationship.setReceiveMailCode(Boolean.valueOf("Y".equals(form.getReceiveMailCode())));
		}
		facilityRelationship.setHoldMailCodeCategoryCode(CategoryCodeConstant.HOLD_MAIL);
		facilityRelationship.setHoldMailCodeEntryCode(form.getHoldMailCodeEntryCode());
		if (StringUtils.isNotBlank(form.getNameAssociateWithFacilityOrder())) {
			facilityRelationship.setNameAssociateWithFacilityOrder(Integer.valueOf(form
					.getNameAssociateWithFacilityOrder()));
		}
		if (StringUtils.isNotBlank(form.getNameConjunctionPosition())) {
			facilityRelationship.setNameConjunctionPosition(new Character(form.getNameConjunctionPosition().charAt(0)));
		}
		facilityRelationship.setNameConjunction(form.getNameConjunction());
		if (StringUtils.isNotBlank(form.getProfitRatio())) {
			facilityRelationship.setProfitRatio(Double.valueOf(form.getProfitRatio()));
		}
		if (StringUtils.isNotBlank(form.getDividendRatio())) {
			facilityRelationship.setDividendRatio(Double.valueOf(form.getDividendRatio()));
		}
		return facilityRelationship;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		RelationshipForm form = (RelationshipForm) cForm;
		IFacilityRelationship facilityRelationship = (IFacilityRelationship) obj;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		int decPlaces = 2;
		boolean withCCY = false;
		if (facilityRelationship != null) {
			try {
				form.setLEReference(facilityRelationship.getCmsLegalEntity().getLEReference());
				form.setLegalName(facilityRelationship.getCmsLegalEntity().getLegalName());
				form.setAccountRelationshipEntryCode(facilityRelationship.getAccountRelationshipEntryCode());
				if (facilityRelationship.getShareHolderPercentage() != null) {
					form.setShareHolderPercentage(String.valueOf(facilityRelationship.getShareHolderPercentage()));
				}
				if (facilityRelationship.getGuaranteeAmount() != null) {
					form.setGuaranteeAmount(UIUtil.formatAmount(facilityRelationship.getGuaranteeAmount(), decPlaces,
							locale, withCCY));
					form.setGuaranteePercentage(null);
					form.setGuaranteeFlag("amount");
				}
				if (facilityRelationship.getGuaranteePercentage() != null
						&& facilityRelationship.getGuaranteePercentage().doubleValue() >= 1) {
					form.setGuaranteePercentage(String.valueOf(facilityRelationship.getGuaranteePercentage()));
					form.setGuaranteeAmount(null);
					form.setGuaranteeFlag("percentage");
				}
				if (facilityRelationship.getHostAddressSequenceNumber() != null) {
					form.setHostAddressSequenceNumber(String.valueOf(facilityRelationship
							.getHostAddressSequenceNumber()));
				}
				if (facilityRelationship.getReceiveMailCode() != null) {
					form.setReceiveMailCode(facilityRelationship.getReceiveMailCode().booleanValue() ? "Y" : "N");
				}
				form.setHoldMailCodeEntryCode(facilityRelationship.getHoldMailCodeEntryCode());
				if (facilityRelationship.getNameAssociateWithFacilityOrder() != null) {
					form.setNameAssociateWithFacilityOrder(String.valueOf(facilityRelationship
							.getNameAssociateWithFacilityOrder()));
				}
				if (facilityRelationship.getNameConjunctionPosition() != null) {
					form.setNameConjunctionPosition(String.valueOf(facilityRelationship.getNameConjunctionPosition()));
				}
				form.setNameConjunction(facilityRelationship.getNameConjunction());
				if (facilityRelationship.getProfitRatio() != null) {
					form.setProfitRatio(String.valueOf(facilityRelationship.getProfitRatio()));
				}
				if (facilityRelationship.getDividendRatio() != null) {
					form.setDividendRatio(String.valueOf(facilityRelationship.getDividendRatio()));
				}
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception Caught", e);
				e.printStackTrace();
			}
		}
		return form;
	}
}
