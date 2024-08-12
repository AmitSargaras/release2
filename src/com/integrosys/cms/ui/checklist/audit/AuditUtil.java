/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.audit;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.checklist.bus.ICheckListAudit;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative.ICreditDerivative;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.collateral.CommonNatureOfCharge;

/**
 * @author $Author: wltan $<br>
 * @version $Revision:
 * @since $Date: 2004/07/12 Tag: $Name: $
 */
public class AuditUtil {

	private static Locale locale = Locale.US;

	public static HashMap getSecurityInfo(ICheckListAudit colAuditList) {

		HashMap pSecurityInfo = new HashMap();

		try {

			String typeCode = colAuditList.getCollateral().getCollateralType().getTypeCode();
			if (ICMSConstant.SECURITY_TYPE_MARKETABLE.equals(typeCode)) {

				getSecurityMT(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(typeCode)) {

				getSecurityPT(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_COMMODITY.equals(typeCode)) {

				getSecurityCO(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_INSURANCE.equals(typeCode)) {

				getSecurityIN(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_CASH.equals(typeCode)) {

				getSecurityCS(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_GUARANTEE.equals(typeCode)) {

				getSecurityGT(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_ASSET.equals(typeCode)) {

				getSecurityAB(colAuditList, pSecurityInfo);

			}
			else if (ICMSConstant.SECURITY_TYPE_DOCUMENT.equals(typeCode)) {

				pSecurityInfo.put("COLDESC", "-");
				pSecurityInfo.put("CHARGE", "-");
			}
			formatString(pSecurityInfo);

		}
		catch (Exception ex) {
			DefaultLogger.error("AuditUtil.getSecurityInfo", "Exception in AuditUtil::::" + ex.toString());

		}

		return pSecurityInfo;
	}

	private static void getSecurityMT(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		int chargeCount = 0;
		int equityCount = 0;
		String desc = "";
		StringBuffer strBuffer = new StringBuffer();

		IMarketableCollateral collateralOB = (IMarketableCollateral) colAuditList.getCollateral();
		// Get Particulars Of Collateral
		IMarketableEquity[] marketableEquities = collateralOB.getEquityList();
		if (marketableEquities != null) {
			equityCount = marketableEquities.length;
		}
		if (equityCount > 0) {
			for (int i = 0; i < equityCount; i++) {

				strBuffer.append(marketableEquities[i].getRegisteredName() == null ? "-" : marketableEquities[i]
						.getRegisteredName());
				strBuffer.append(", ");
				strBuffer.append(marketableEquities[i].getNoOfUnits());
				strBuffer.append(" units");
				strBuffer.append(" ");
			}
			desc = strBuffer.toString();
		}
		pSecurityInfo.put("COLDESC", desc);

		// Get Particulars of Charge
		String chargeCCY = collateralOB.getCurrencyCode();
		ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
		String chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_MARKETABLE, chargeCCY);
		pSecurityInfo.put("CHARGE", chargeInfo);

	}

	private static void getSecurityPT(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";

		IPropertyCollateral collateralOB = (IPropertyCollateral) colAuditList.getCollateral();
		// Get Particulars Of Collateral
		desc = collateralOB.getDescription();
		pSecurityInfo.put("COLDESC", desc);

		// Get Particulars of Charge
		ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
		String chargeCCY = collateralOB.getCurrencyCode();
		String chargeInfo = getChargeInfoWithRank(limitCharges, ICMSConstant.SECURITY_TYPE_PROPERTY, chargeCCY);
		pSecurityInfo.put("CHARGE", chargeInfo);

	}

	private static void getSecurityCO(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";
		StringBuffer strBuffer = new StringBuffer();

		ICommodityCollateral collateralOB = (ICommodityCollateral) colAuditList.getCollateral();
		// Get Particulars Of Collateral
		int apprCommodityCount = 0;
		IApprovedCommodityType[] apprCommodityTypes = collateralOB.getApprovedCommodityTypes();
		if (apprCommodityTypes != null) {
			apprCommodityCount = apprCommodityTypes.length;
		}
		if (apprCommodityCount > 0) {
			for (int i = 0; i < apprCommodityCount; i++) {
				long commonRefLong = apprCommodityTypes[i].getCommonRef();
				String commonRef = String.valueOf(commonRefLong);
				commonRef = ((commonRef == null) || commonRef.equals("")) ? "-" : commonRef;
				strBuffer.append(commonRef);
				strBuffer.append(",");
			}
			desc = strBuffer.toString();
		}
		pSecurityInfo.put("COLDESC", desc);

		// Get Particulars of Charge
		String chargeCCY = collateralOB.getCurrencyCode();
		ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
		String chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_COMMODITY, chargeCCY);
		pSecurityInfo.put("CHARGE", chargeInfo);
	}

	private static void getSecurityIN(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";
		String chargeInfo = "";
		String subTypeCode = colAuditList.getCollateral().getCollateralSubType().getSubTypeCode();

		if (ICMSConstant.COLTYPE_INS_CR_DEFAULT_SWAPS.equals(subTypeCode)) {

			ICreditDefaultSwaps collateralOB = (ICreditDefaultSwaps) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			desc = collateralOB.getDescription();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_INSURANCE, chargeCCY);

		}
		else if (ICMSConstant.COLTYPE_INS_CR_DERIVATIVE.equals(subTypeCode)) {

			ICreditDerivative collateralOB = (ICreditDerivative) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			desc = collateralOB.getDescription();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_INSURANCE, chargeCCY);

		}
		else if (ICMSConstant.COLTYPE_INS_CR_INS.equals(subTypeCode)) {

			ICreditInsurance collateralOB = (ICreditInsurance) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			desc = collateralOB.getDescription();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_INSURANCE, chargeCCY);

		}
		else if (ICMSConstant.COLTYPE_INS_KEYMAN_INS.equals(subTypeCode)) {

			IKeymanInsurance collateralOB = (IKeymanInsurance) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			desc = collateralOB.getInsuranceType();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_INSURANCE, chargeCCY);
		}

		pSecurityInfo.put("CHARGE", chargeInfo);

	}

	private static void getSecurityCS(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";
		StringBuffer strBuffer = new StringBuffer();

		ICashCollateral collateralOB = (ICashCollateral) colAuditList.getCollateral();
		// Get Particulars Of Collateral
		int depositCount = 0;
		ICashDeposit[] cashDeposits = collateralOB.getDepositInfo();
		if (cashDeposits != null) {
			depositCount = cashDeposits.length;
		}
		if (depositCount > 0) {
			for (int i = 0; i < depositCount; i++) {
				try {
					Amount depositAmt = cashDeposits[i].getDepositAmount();
					// String depositAmtStr =
					// String.valueOf(depositAmt.getAmountAsDouble());
					String depositAmtStr = MapperUtil.mapDoubleToString(depositAmt.getAmountAsDouble(), 3, locale);
					String ccyCode = cashDeposits[i].getDepositCcyCode();
					strBuffer.append(ccyCode);
					strBuffer.append(" ");
					strBuffer.append(depositAmtStr);
					strBuffer.append(",");
				}
				catch (Exception e) {
				}
			}
			desc = strBuffer.toString();
		}
		pSecurityInfo.put("COLDESC", desc);

		// Get Particulars of Charge
		strBuffer = new StringBuffer();
		String chargeCCY = collateralOB.getCurrencyCode();
		ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
		ILimitCharge objLimit = null;
		if ((limitCharges != null) && (limitCharges.length > 0) && (limitCharges[0] != null)) {
			objLimit = limitCharges[0];
			String chargeAmount = "";
			if (objLimit.getChargeAmount() != null) {
				// chargeAmount=String.valueOf(objLimit.getChargeAmount().
				// getAmountAsDouble());
				chargeAmount = MapperUtil.mapDoubleToString(objLimit.getChargeAmount().getAmountAsDouble(), 3, locale);
			}
			if (chargeAmount != null) {
				strBuffer.append(chargeCCY);
				strBuffer.append(" ");
				strBuffer.append(chargeAmount);
			}
		}
		pSecurityInfo.put("CHARGE", strBuffer.toString());

	}

	private static void getSecurityGT(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";
		String temp = "";
		StringBuffer strBuffer = new StringBuffer();

		IGuaranteeCollateral collateralOB = (IGuaranteeCollateral) colAuditList.getCollateral();
		// Get Particulars Of Collateral
		strBuffer.append(collateralOB.getDescription() == null ? "-" : collateralOB.getDescription());
		strBuffer.append(", ");
		strBuffer.append(collateralOB.getReferenceNo() == null ? "-" : "Ref. " + collateralOB.getReferenceNo());
		strBuffer.append(", ");
		Date guaranteeDate = collateralOB.getGuaranteeDate();
		if (guaranteeDate != null) {
			temp = DateUtil.formatDate("d MMM yyyy", guaranteeDate);
		}
		strBuffer.append(temp == null ? "" : temp);
		desc = strBuffer.toString();
		pSecurityInfo.put("COLDESC", desc);

		// Get Particulars of Charge
		strBuffer = new StringBuffer();
		String chargeCCY = collateralOB.getCurrencyCode();
		ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
		ILimitCharge objLimit = null;
		if ((limitCharges != null) && (limitCharges.length > 0) && (limitCharges[0] != null)) {
			objLimit = limitCharges[0];
			String chargeAmount = "";
			if (objLimit.getChargeAmount() != null) {
				// chargeAmount=String.valueOf(objLimit.getChargeAmount().
				// getAmountAsDouble());
				chargeAmount = MapperUtil.mapDoubleToString(objLimit.getChargeAmount().getAmountAsDouble(), 3, locale);
			}
			if (chargeAmount != null) {
				strBuffer.append(chargeCCY);
				strBuffer.append(" ");
				strBuffer.append(chargeAmount);
			}
		}
		pSecurityInfo.put("CHARGE", strBuffer.toString());

	}

	private static void getSecurityAB(ICheckListAudit colAuditList, HashMap pSecurityInfo) {

		String desc = "";
		String chargeInfo = "";
		String chargeCurrency = "";
		StringBuffer strBuffer = new StringBuffer();

		String subTypeCode = colAuditList.getCollateral().getCollateralSubType().getSubTypeCode();

		if (ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE.equals(subTypeCode)
				|| ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS.equals(subTypeCode)
				|| ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(subTypeCode)
				|| ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(subTypeCode)
				|| ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT.equals(subTypeCode)) {

			IChargeCommon collateralOB = (IChargeCommon) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			desc = collateralOB.getRemarks();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfoWithRank(limitCharges, ICMSConstant.SECURITY_TYPE_ASSET, chargeCCY);
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {

			IAssetPostDatedCheque collateralOB = (IAssetPostDatedCheque) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			int chequeCount = 0;
			IPostDatedCheque[] postDatedCheques = collateralOB.getPostDatedCheques();
			if (postDatedCheques != null) {
				chequeCount = postDatedCheques.length;
			}
			if (chequeCount > 0) {
				for (int i = 0; i < chequeCount; i++) {
					try {
						Amount chequeAmt = postDatedCheques[i].getChequeAmount();
						// String chequeAmtStr =
						// String.valueOf(chequeAmt.getAmountAsDouble());
						String chequeAmtStr = MapperUtil.mapDoubleToString(chequeAmt.getAmountAsDouble(), 3, locale);
						String ccyCode = postDatedCheques[i].getChequeCcyCode();
						strBuffer.append(ccyCode);
						strBuffer.append(" ");
						strBuffer.append(chequeAmt);
						strBuffer.append(" ");
					}
					catch (Exception e) {
					}
				}
				desc = strBuffer.toString();
			}
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_ASSET, chargeCCY);
		}
		else if (subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_GEN_AGENT)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_OPEN)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_AGENT)
				|| subTypeCode.equals(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_NOAGENT)) {

			IReceivableCommon collateralOB = (IReceivableCommon) colAuditList.getCollateral();
			// Get Particulars Of Collateral
			chargeCurrency = collateralOB.getCurrencyCode();
			chargeCurrency = ((chargeCurrency == null) || chargeCurrency.equals("")) ? "-" : chargeCurrency;
			String invoiceType = collateralOB.getInvoiceType();
			invoiceType = ((invoiceType == null) || invoiceType.equals("")) ? "-" : invoiceType;
			strBuffer.append(chargeCurrency);
			strBuffer.append(" ");
			strBuffer.append(invoiceType);
			strBuffer.append(" ");
			desc = strBuffer.toString();
			pSecurityInfo.put("COLDESC", desc);

			// Get Particulars of Charge
			String chargeCCY = collateralOB.getCurrencyCode();
			ILimitCharge[] limitCharges = collateralOB.getLimitCharges();
			chargeInfo = getChargeInfo(limitCharges, ICMSConstant.SECURITY_TYPE_ASSET, chargeCCY);
		}

		pSecurityInfo.put("CHARGE", chargeInfo);

	}

	private static String getChargeInfo(ILimitCharge[] limitCharges, String secSubType, String chargeCCY) {

		StringBuffer strBuffer = new StringBuffer();

		ILimitCharge objLimit = null;
		if ((limitCharges != null) && (limitCharges.length > 0) && (limitCharges[0] != null)) {
			objLimit = limitCharges[0];

			String chargeAmount = "";
			if (objLimit.getChargeAmount() != null) {
				// chargeAmount=String.valueOf(objLimit.getChargeAmount().
				// getAmountAsDouble());
				chargeAmount = MapperUtil.mapDoubleToString(objLimit.getChargeAmount().getAmountAsDouble(), 3, locale);
			}
			String chargeNature = CommonNatureOfCharge.getNatureOfChargeDescription(secSubType, objLimit
					.getNatureOfCharge());
			chargeNature = chargeNature == null ? "-" : chargeNature;
			if (chargeAmount != null) {
				strBuffer.append(chargeCCY);
				strBuffer.append(" ");
				strBuffer.append(chargeAmount);
				strBuffer.append(", ");
			}
			strBuffer.append(chargeNature);
		}

		return strBuffer.toString();

	}

	private static String getChargeInfoWithRank(ILimitCharge[] limitCharges, String secSubType, String chargeCCY) {

		int chargeCount = 0;
		String chargeCurrency = "";
		String chargeNature = "";
		String chargeRank = "";
		String chargeAmount = "";
		StringBuffer strBuffer = new StringBuffer();

		// chargeCCY
		if (limitCharges != null) {
			chargeCount = limitCharges.length;
		}
		if (chargeCount > 0) {
			for (int i = 1; i <= chargeCount; i++) // prints the charge ranked
													// 'i'
			{
				// iterates through entire list looking for the charge ranked
				// 'i', coz it may not be in order
				for (int j = 0; j < chargeCount; j++) {
					if ((limitCharges[j].getSecurityRank() == i) || (limitCharges[j].getSecurityRank() == 0)) {
						// charge rank
						chargeRank = CheckListHelper.formatSecurityRank(limitCharges[j]);
						strBuffer.append(chargeRank);
						strBuffer.append(", ");

						// charge amount
						if (limitCharges[j].getChargeAmount() != null) {
							// chargeAmount=String.valueOf(limitCharges[j].
							// getChargeAmount().getAmountAsDouble());
							chargeAmount = MapperUtil.mapDoubleToString(limitCharges[j].getChargeAmount()
									.getAmountAsDouble(), 3, locale);
						}
						if (chargeAmount != null) {
							strBuffer.append(chargeCCY);
							strBuffer.append(" ");
							strBuffer.append(chargeAmount);
							strBuffer.append(", ");
						}

						// charge nature
						if (limitCharges[j].getNatureOfCharge() != null) {
							chargeNature = CommonNatureOfCharge.getNatureOfChargeDescription(secSubType,
									limitCharges[j].getNatureOfCharge());
							chargeNature = chargeNature == null ? "-" : chargeNature;
						}
						else {
							chargeNature = "-";
						}
						strBuffer.append(chargeNature);
						strBuffer.append(",");

					}

				}
			}
		}

		return strBuffer.toString();
	}

	private static void formatString(HashMap pSecurityInfo) {

		if (pSecurityInfo != null) {

			if (pSecurityInfo.get("COLDESC") != null) {

				String colDesc = (String) pSecurityInfo.get("COLDESC");
				colDesc = colDesc.replace(',', ' ');
				colDesc = colDesc.replace('-', ' ');
				if (colDesc.trim().length() == 0) {
					pSecurityInfo.put("COLDESC", "-");
				}
			}
			if (pSecurityInfo.get("CHARGE") != null) {

				String chargeInfo = (String) pSecurityInfo.get("CHARGE");
				chargeInfo = chargeInfo.replace(',', ' ');
				chargeInfo = chargeInfo.replace('-', ' ');
				if (chargeInfo.trim().length() == 0) {
					pSecurityInfo.put("CHARGE", "-");
				}
			}
		}

	}

}
