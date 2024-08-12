package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.PortfolioItem;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * EAI Message validator to validate instance of <tt>MarketableSecurity</tt> and
 * <tt>PortfolioItem</tt>
 * @author Chong Jun Yong
 * 
 */
public class MarketableSecurityValidator extends SecurityValidator {
	private Map secSubTypeIdPortfolioItemTypeMap;

	// MS600, MS601, MS605, MS606
	private String[] securitySubtypeIdsStockPortfolio;

	// MS605, MS606
	private String[] securitySubtypeIdsOtherListedStockPortfolio;

	// MS610, MS611
	private String[] securitySubtypeIdsBondPortfolio;

	// MS602, MS603
	private String[] securitySubtypeIdsGovernmentPortfolio;

	// MS607
	private String[] securitySubtypeIdsNonListedPortfolio;

	// MS602
	private String[] securitySubtypeIdsGuaranteeByGovernmentApplicable;

	private String[] sourceIdsGuaranteeByGovernmentApplicable;

	public void setSecSubTypeIdPortfolioItemTypeMap(Map secSubTypeIdPortfolioItemTypeMap) {
		this.secSubTypeIdPortfolioItemTypeMap = secSubTypeIdPortfolioItemTypeMap;
	}

	public void setSecuritySubtypeIdsStockPortfolio(String[] securitySubtypeIdsStockPortfolio) {
		this.securitySubtypeIdsStockPortfolio = securitySubtypeIdsStockPortfolio;
	}

	public void setSecuritySubtypeIdsOtherListedStockPortfolio(String[] securitySubtypeIdsOtherListedStockPortfolio) {
		this.securitySubtypeIdsOtherListedStockPortfolio = securitySubtypeIdsOtherListedStockPortfolio;
	}

	public void setSecuritySubtypeIdsBondPortfolio(String[] securitySubtypeIdsBondPortfolio) {
		this.securitySubtypeIdsBondPortfolio = securitySubtypeIdsBondPortfolio;
	}

	public void setSecuritySubtypeIdsGovernmentPortfolio(String[] securitySubtypeIdsGovernmentPortfolio) {
		this.securitySubtypeIdsGovernmentPortfolio = securitySubtypeIdsGovernmentPortfolio;
	}

	public void setSecuritySubtypeIdsNonListedPortfolio(String[] securitySubtypeIdsNonListedPortfolio) {
		this.securitySubtypeIdsNonListedPortfolio = securitySubtypeIdsNonListedPortfolio;
	}

	public void setSecuritySubtypeIdsGuaranteeByGovernmentApplicable(
			String[] securitySubtypeIdsGuaranteeByGovernmentApplicable) {
		this.securitySubtypeIdsGuaranteeByGovernmentApplicable = securitySubtypeIdsGuaranteeByGovernmentApplicable;
	}

	public void setSourceIdsGuaranteeByGovernmentApplicable(String[] sourceIdsGuaranteeByGovernmentApplicable) {
		this.sourceIdsGuaranteeByGovernmentApplicable = sourceIdsGuaranteeByGovernmentApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();

		if (ICMSConstant.SECURITY_TYPE_MARKETABLE.equals(security.getSecurityType().getStandardCodeValue())) {
			validator.rejectIfNull(msg.getMarketableSecDetail(), "MarketableSecDetail");

			MarketableSecurity sec = msg.getMarketableSecDetail();

			validator.validateString(sec.getLOSSecurityId(), "MarketableSecDetail - LOSSecurityID", true, 1, 20);

			validator.validateDoubleDigit(sec.getCappedPrice(), "MarketableSecDetail - CappedPrice", false, 13, 2,
					false);

			if (ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)
					|| ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)
					|| ArrayUtils.contains(this.securitySubtypeIdsGovernmentPortfolio, secSubType)) {
				validator.validateString(sec.getStockCounterCode(), "MarketableSecDetail - StockCounterCode", false, 0,
						20);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)) {
				validator.validateDoubleDigit(sec.getInterestRate(), "MarketableSecDetail - InterestRate", false, 2, 9,
						false);
			}

			Vector portfolioItems = msg.getPortfolioItems();
			validator.rejectIfNull(portfolioItems, "PortfolioItems");

			for (Iterator itr = portfolioItems.iterator(); itr.hasNext();) {
				PortfolioItem portfolio = (PortfolioItem) itr.next();
				validator.validateString(portfolio.getSecurityId(), "PortfolioItems - LOSSecurityId", true, 1, 20);

				if (IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(portfolio.getUpdateStatusIndicator())
						&& IEaiConstant.CHANGE_INDICATOR_YES.equals(portfolio.getChangeIndicator())) {
					validator.validateNumber(new Long(portfolio.getCMSPortfolioItemId()),
							"PortfolioItems - CMSPortfolioItemId", true, 1, IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
				}

				if (ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsGovernmentPortfolio, secSubType)) {
					validator.validateString(portfolio.getStockCode(), "PortfolioItems - StockCode", false, 0, 20);
				}

				if (ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsNonListedPortfolio, secSubType)) {
					validator.rejectIfNull(portfolio.getType(), "PortfolioItems - Type");

					String expectedCategoryCode = (String) this.secSubTypeIdPortfolioItemTypeMap.get(secSubType);
					validator.validateStdCode(portfolio.getType(), source, expectedCategoryCode);

					validator.validateString(portfolio.getIssuerName(), "PortfolioItems - IssuerName", false, 0, 50);
				}

				validator.validateString(portfolio.getCertificateNumber(), "PortfolioItems - CertificateNumber", false,
						0, 20);

				if (ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsNonListedPortfolio, secSubType)) {
					validator.validateStdCodeAllowNull(portfolio.getNomineeName(), source, "NOMINEE_MARKSEC");

					validator.validateString(portfolio.getBaselCompliantText(), "PortfolioItems - BaselCompliantText",
							false, 0, 1, EaiConstantCla.getAllowedValuesYesNoNa());
				}

				validator
						.validateString(portfolio.getRegisteredName(), "PortfolioItems - RegisteredName", false, 0, 50);

				if (ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)) {
					validator.validateString(portfolio.getRecognizedExchangeFlag(),
							"PortfolioItems - RecognizedExchangeFlag", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

					validator.validateString(portfolio.getCDSNumber(), "PortfolioItems - CDSNumber", false, 0, 50);

					validator.validateString(portfolio.getBlacklistedFlag(), "PortfolioItems - BlackListed", true, 1,
							1, EaiConstantCla.getAllowedValuesYesNo());

					validator.validateString(portfolio.getLocalStockExchangeInd(),
							"PortfolioItems - LocalStockExchangeInd", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

					validator.validateString(portfolio.getIndexName(), "PortfolioItems - IndexName", false, 0, 50);
				}

				validator.validateString(portfolio.getClientCode(), "PortfolioItems - ClientCode", false, 0, 30);

				validator.validateNumber(portfolio.getUnits(), "PortfolioItems - Units", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_10);

				validator.validateDoubleDigit(portfolio.getUnitPrice(), "PortfolioItems - UnitPrice", false, 13, 6,
						false);

				validator.validateDoubleDigit(portfolio.getNominalValue(), "PortfolioItems - NominalValue", false, 15,
						2, false);

				validator.validateString(portfolio.getCustodianType(), "PortfolioItems - CustodianType", true, 1, 1,
						EaiConstantCla.getAllowedValuesCustodianTypes());

				validator.validateString(portfolio.getCustodian(), "PortfolioItems - Custodian", true, 1, 100);

				validator.validateString(portfolio.getIssuerType(), "PortfolioItems - IssuerType", false, 0, 30);

				if (ArrayUtils.contains(this.securitySubtypeIdsStockPortfolio, secSubType)
						|| ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)) {
					validator.validateString(portfolio.getStockExchange(), "PortfolioItems - StockExchange", false, 0,
							50);

					validator.validateString(portfolio.getStockExchangeCountry(),
							"PortfolioItems - StockExchangeCountry", false, 0, 2);
				}

				validator.validateString(portfolio.getISINCode(), "PortfolioItems - ISINCode", false, 0, 30);

				validator.validateString(portfolio.getExchangeControlObtainedFlag(),
						"PortfolioItems - ExchangeControlObtainedFlag", false, 0, 1,
						EaiConstantCla.getAllowedValuesYesNoNa());

				if (ArrayUtils.contains(this.securitySubtypeIdsOtherListedStockPortfolio, secSubType)) {
					validator.validateDoubleDigit(portfolio.getExercisePrice(), "MarketableSecDetail - ExercisePrice",
							false, 15, 2, false);

					validator.validateString(portfolio.getBrokerrName(), "PortfolioItems - BrokerrName", false, 0, 100);
				}

				if (ArrayUtils.contains(this.securitySubtypeIdsBondPortfolio, secSubType)) {
					validator.validateString(portfolio.getLeadManager(), "PortfolioItems - LeadManager", false, 0, 50);

					validator.validateStdCodeAllowNull(portfolio.getSettlementOrganisation(), source,
							CategoryCodeConstant.SETTLEMENT_ORG);

					validator.validateString(portfolio.getBondRating(), "PortfolioItems - BondRating", false, 0, 20);
				}

				if (ArrayUtils.contains(this.securitySubtypeIdsGuaranteeByGovernmentApplicable, secSubType)
						&& ArrayUtils.contains(this.sourceIdsGuaranteeByGovernmentApplicable, source)) {
					validator.validateString(portfolio.getGovernmentGuaranteeFlag(),
							"PortfolioItems - GuaranteeByGovtInd", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

					if (ICMSConstant.TRUE_VALUE.equals(portfolio.getGovernmentGuaranteeFlag())) {
						validator.validateString(portfolio.getGovernmentName(), "PortfolioItems - GovernmentName",
								true, 1, 100);
					}
				}

			}
		}
	}
}
