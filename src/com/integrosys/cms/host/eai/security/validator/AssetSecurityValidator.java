package com.integrosys.cms.host.eai.security.validator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.AirCraftDetail;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.ChequeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.CommonChargeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.GoldDetail;
import com.integrosys.cms.host.eai.security.bus.asset.PostDatedCheque;
import com.integrosys.cms.host.eai.security.bus.asset.ReceivableDetail;
import com.integrosys.cms.host.eai.security.bus.asset.SpecificChargeDetail;
import com.integrosys.cms.host.eai.security.bus.asset.TradeInInfo;
import com.integrosys.cms.host.eai.security.bus.asset.VehicleDetail;
import com.integrosys.cms.host.eai.security.bus.asset.VesselDetail;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Message validator to validate instance of <tt>AssetSecurity</tt>.
 * @author Chong Jun Yong
 * 
 */
public class AssetSecurityValidator extends SecurityValidator {

	private Map secSubTypeIdAssetTypeMap;

	// AB100, AB101, AB102, AB103, AB109, AB110, AB111
	private String[] securitySubtypeIdsCommonChargeDetailApplicable;

	// AB101, AB102, AB103, AB109, AB110, AB111
	private String[] securitySubtypeIdsSpecificChargeDetailApplicable;

	private String[] securitySubtypeIdsReceivableDetailApplicable;

	private String[] securitySubtypeIdsAircraftDetailApplicable;

	private String[] securitySubtypeIdsGoldDetailApplicable;

	private String[] securitySubtypeIdsVehicleDetailApplicable;

	private String[] securitySubtypeIdsVesselDetailApplicable;

	private String[] securitySubtypeIdsPlantEquipDetailApplicable;

	private String[] securitySubtypeIdsPostDatedChequeDetailApplicable;

	// AB101, AB102
	private String[] securitySubtypeIdsCommonChargePhysicalInspectionStatusApplicable;

	private String[] securitySubtypeIdsSpecificChargePurchasePriceApplicable;

	private String[] securitySubtypeIdsSpecificChargeManufactureYearMandatory;

	private String[] securitySubtypeIdsSpecificChargeMakeMandatory;

	private String[] securitySubtypeIdsSpecificChargeModelMandatory;

	private String[] securitySubtypeIdsSpecificChargeSalesProceedApplicable;

	private String[] securitySubtypeIdsSpecificChargeRegistrationNoMandatory;

	private String[] sourceIdsSpecificChargeRegistrationNoMandatory;

	private String[] securitySubtypeIdsSpecificChargeRegistrationFeeApplicable;

	private String[] sourceIdsSpecificChargeRegistrationFeeApplicable;

	private String[] securitySubtypeIdsSpecificChargeGoodStatusMandatory;

	private String[] securitySubtypeIdsSpecificPublicTransportMandatory;

	private String[] securitySubtypeIdsSpecificChargeRLSerialNoApplicable;

	private String[] securitySubtypeIdsSpecificChargeResidualScrapValueApplicable;

	private String[] securitySubtypeIdsSpecificChargeYardApplicable;

	private String[] securitySubtypeIdsSpecificChargeInsurersApplicable;

	private String[] securitySubtypeIdsSpecificChargeDownPaymentInfoApplicable;

	private String[] sourceIdsSpecificChargeDownPaymentInfoApplicable;

	public void setSecSubTypeIdAssetTypeMap(Map secSubTypeIdAssetTypeMap) {
		this.secSubTypeIdAssetTypeMap = secSubTypeIdAssetTypeMap;
	}

	public void setSecuritySubtypeIdsCommonChargeDetailApplicable(
			String[] securitySubtypeIdsCommonChargeDetailApplicable) {
		this.securitySubtypeIdsCommonChargeDetailApplicable = securitySubtypeIdsCommonChargeDetailApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeDetailApplicable(
			String[] securitySubtypeIdsSpecificChargeDetailApplicable) {
		this.securitySubtypeIdsSpecificChargeDetailApplicable = securitySubtypeIdsSpecificChargeDetailApplicable;
	}

	public void setSecuritySubtypeIdsReceivableDetailApplicable(String[] securitySubtypeIdsReceivableDetailApplicable) {
		this.securitySubtypeIdsReceivableDetailApplicable = securitySubtypeIdsReceivableDetailApplicable;
	}

	public void setSecuritySubtypeIdsAircraftDetailApplicable(String[] securitySubtypeIdsAircraftDetailApplicable) {
		this.securitySubtypeIdsAircraftDetailApplicable = securitySubtypeIdsAircraftDetailApplicable;
	}

	public void setSecuritySubtypeIdsGoldDetailApplicable(String[] securitySubtypeIdsGoldDetailApplicable) {
		this.securitySubtypeIdsGoldDetailApplicable = securitySubtypeIdsGoldDetailApplicable;
	}

	public void setSecuritySubtypeIdsVehicleDetailApplicable(String[] securitySubtypeIdsVehicleDetailApplicable) {
		this.securitySubtypeIdsVehicleDetailApplicable = securitySubtypeIdsVehicleDetailApplicable;
	}

	public void setSecuritySubtypeIdsVesselDetailApplicable(String[] securitySubtypeIdsVesselDetailApplicable) {
		this.securitySubtypeIdsVesselDetailApplicable = securitySubtypeIdsVesselDetailApplicable;
	}

	public void setSecuritySubtypeIdsPlantEquipDetailApplicable(String[] securitySubtypeIdsPlantEquipDetailApplicable) {
		this.securitySubtypeIdsPlantEquipDetailApplicable = securitySubtypeIdsPlantEquipDetailApplicable;
	}

	public void setSecuritySubtypeIdsPostDatedChequeDetailApplicable(
			String[] securitySubtypeIdsPostDatedChequeDetailApplicable) {
		this.securitySubtypeIdsPostDatedChequeDetailApplicable = securitySubtypeIdsPostDatedChequeDetailApplicable;
	}

	public void setSecuritySubtypeIdsCommonChargePhysicalInspectionStatusApplicable(
			String[] securitySubtypeIdsCommonChargePhysicalInspectionStatusApplicable) {
		this.securitySubtypeIdsCommonChargePhysicalInspectionStatusApplicable = securitySubtypeIdsCommonChargePhysicalInspectionStatusApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargePurchasePriceApplicable(
			String[] securitySubtypeIdsSpecificChargePurchasePriceApplicable) {
		this.securitySubtypeIdsSpecificChargePurchasePriceApplicable = securitySubtypeIdsSpecificChargePurchasePriceApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeManufactureYearMandatory(
			String[] securitySubtypeIdsSpecificChargeManufactureYearMandatory) {
		this.securitySubtypeIdsSpecificChargeManufactureYearMandatory = securitySubtypeIdsSpecificChargeManufactureYearMandatory;
	}

	public void setSecuritySubtypeIdsSpecificChargeMakeMandatory(String[] securitySubtypeIdsSpecificChargeMakeMandatory) {
		this.securitySubtypeIdsSpecificChargeMakeMandatory = securitySubtypeIdsSpecificChargeMakeMandatory;
	}

	public void setSecuritySubtypeIdsSpecificChargeModelMandatory(
			String[] securitySubtypeIdsSpecificChargeModelMandatory) {
		this.securitySubtypeIdsSpecificChargeModelMandatory = securitySubtypeIdsSpecificChargeModelMandatory;
	}

	public void setSecuritySubtypeIdsSpecificChargeSalesProceedApplicable(
			String[] securitySubtypeIdsSpecificChargeSalesProceedApplicable) {
		this.securitySubtypeIdsSpecificChargeSalesProceedApplicable = securitySubtypeIdsSpecificChargeSalesProceedApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeRegistrationNoMandatory(
			String[] securitySubtypeIdsSpecificChargeRegistrationNoMandatory) {
		this.securitySubtypeIdsSpecificChargeRegistrationNoMandatory = securitySubtypeIdsSpecificChargeRegistrationNoMandatory;
	}

	public void setSourceIdsSpecificChargeRegistrationNoMandatory(
			String[] sourceIdsSpecificChargeRegistrationNoMandatory) {
		this.sourceIdsSpecificChargeRegistrationNoMandatory = sourceIdsSpecificChargeRegistrationNoMandatory;
	}

	public void setSecuritySubtypeIdsSpecificChargeRegistrationFeeApplicable(
			String[] securitySubtypeIdsSpecificChargeRegistrationFeeApplicable) {
		this.securitySubtypeIdsSpecificChargeRegistrationFeeApplicable = securitySubtypeIdsSpecificChargeRegistrationFeeApplicable;
	}

	public void setSourceIdsSpecificChargeRegistrationFeeApplicable(
			String[] sourceIdsSpecificChargeRegistrationFeeApplicable) {
		this.sourceIdsSpecificChargeRegistrationFeeApplicable = sourceIdsSpecificChargeRegistrationFeeApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeGoodStatusMandatory(
			String[] securitySubtypeIdsSpecificChargeGoodStatusMandatory) {
		this.securitySubtypeIdsSpecificChargeGoodStatusMandatory = securitySubtypeIdsSpecificChargeGoodStatusMandatory;
	}

	public void setSecuritySubtypeIdsSpecificPublicTransportMandatory(
			String[] securitySubtypeIdsSpecificPublicTransportMandatory) {
		this.securitySubtypeIdsSpecificPublicTransportMandatory = securitySubtypeIdsSpecificPublicTransportMandatory;
	}

	public void setSecuritySubtypeIdsSpecificChargeRLSerialNoApplicable(
			String[] securitySubtypeIdsSpecificChargeRLSerialNoApplicable) {
		this.securitySubtypeIdsSpecificChargeRLSerialNoApplicable = securitySubtypeIdsSpecificChargeRLSerialNoApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeResidualScrapValueApplicable(
			String[] securitySubtypeIdsSpecificChargeResidualScrapValueApplicable) {
		this.securitySubtypeIdsSpecificChargeResidualScrapValueApplicable = securitySubtypeIdsSpecificChargeResidualScrapValueApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeYardApplicable(
			String[] securitySubtypeIdsSpecificChargeYardApplicable) {
		this.securitySubtypeIdsSpecificChargeYardApplicable = securitySubtypeIdsSpecificChargeYardApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeInsurersApplicable(
			String[] securitySubtypeIdsSpecificChargeInsurersApplicable) {
		this.securitySubtypeIdsSpecificChargeInsurersApplicable = securitySubtypeIdsSpecificChargeInsurersApplicable;
	}

	public void setSecuritySubtypeIdsSpecificChargeDownPaymentInfoApplicable(
			String[] securitySubtypeIdsSpecificChargeDownPaymentInfoApplicable) {
		this.securitySubtypeIdsSpecificChargeDownPaymentInfoApplicable = securitySubtypeIdsSpecificChargeDownPaymentInfoApplicable;
	}

	public void setSourceIdsSpecificChargeDownPaymentInfoApplicable(
			String[] sourceIdsSpecificChargeDownPaymentInfoApplicable) {
		this.sourceIdsSpecificChargeDownPaymentInfoApplicable = sourceIdsSpecificChargeDownPaymentInfoApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_ASSET.equals(security.getSecurityType().getStandardCodeValue())) {
			AssetSecurity asset = msg.getAssetDetail();

			validator.validateString(asset.getLOSSecurityId(), "AssetDetail - LOSSecurityId", true, 1, 20);

			validator.validateString(asset.getAssetDescription(), "AssetDetail - AssetDescription", false, 0, 250);

			if (ArrayUtils.contains(this.securitySubtypeIdsCommonChargeDetailApplicable, secSubType)) {
				if (asset.getCommonChargeDetail() != null) {
					CommonChargeDetail common = asset.getCommonChargeDetail();
					validator.validateString(common.getPhysicalInspection(), "CommonChargeDetail - PhysicalInspection",
							false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

					if (ICMSConstant.TRUE_VALUE.equals(common.getPhysicalInspection())) {
						validator.validateDate(common.getLastPhysicalInspectionDate(),
								"CommonChargeDetail - LastPhysicalInspectionDate", true);

						validator.validateString(common.getPhysicalInspectionFrequencyUOM(),
								"CommonChargeDetail - PhysicalInspectionFrequencyUOM", true, 1, 1,
								EaiConstantCla.getAllowedValuesFrequencyUnit());

						validator.validateNumber(common.getPhysicalInspectionFrequencyUnit(),
								"CommonChargeDetail - PhysicalInspectionFrequencyUnit", true, 1,
								IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_10);

						if (ArrayUtils.contains(this.securitySubtypeIdsCommonChargePhysicalInspectionStatusApplicable,
								secSubType)) {
							validator.validateString(common.getPhysicalInspectionStatus(),
									"CommonChargeDetail - PhysicalInspectionStatus", true, 1, 40);
						}
					}

					validator.validateString(common.getEnvironmentallyRiskyStatus(),
							"CommonChargeDetail - EnvironmentallyRiskyStatus", false, 0, 1,
							EaiConstantCla.getAllowedValuesYesNo());
					if ((ICMSConstant.TRUE_VALUE).equals(common.getEnvironmentallyRiskyStatus())) {
						validator.validateDate(common.getEnvironmentallyRiskyDate(),
								"CommonChargeDetail - EnvironmentallyRiskyDate", true);
					}

					validator.validateString(common.getEnvironmentallyRiskyRemark(),
							"CommonChargeDetail - EnvironmentallyRiskyRemark", false, 0, 250);
				}
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeDetailApplicable, secSubType)) {
				if (asset.getSpecificChargeDetail() != null) {
					SpecificChargeDetail specific = asset.getSpecificChargeDetail();

					String expectedCategoryCode = (String) this.secSubTypeIdAssetTypeMap.get(secSubType);
					validator.validateStdCode(specific.getAssetType(), source, expectedCategoryCode);

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargePurchasePriceApplicable, secSubType)) {
						validator.validateDoubleDigit(specific.getPurchasePrice(),
								"SpecificChargeDetail - PurchasePrice", false, 17, 2, false);
					}

					boolean manufactureYearMandatory = ArrayUtils.contains(
							this.securitySubtypeIdsSpecificChargeManufactureYearMandatory, secSubType);
					validator.validateNumber(specific.getManufactureYear(), "SpecificChargeDetail - ManufactureYear",
							manufactureYearMandatory, (manufactureYearMandatory ? 1 : 0),
							IEaiConstant.ALLOWED_LONG_VALUE_YEAR_YYYY);

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeMakeMandatory, secSubType)) {
						validator.validateStdCode(specific.getMake(), source, "VEHICLE_BRAND");
					}
					else {
						validator.validateStdCodeAllowNull(specific.getMake(), source, "VEHICLE_BRAND");
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeModelMandatory, secSubType)) {
						validator.validateStdCode(specific.getModel(), source, "MODEL");
					}
					else {
						validator.validateStdCodeAllowNull(specific.getModel(), source, "MODEL");
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeSalesProceedApplicable, secSubType)) {
						validator.validateDoubleDigit(specific.getSalesProceed(),
								"SpecificChargeDetail - SalesProceed", false, 13, 2, false);
					}

					boolean registrationNoMandatory = ArrayUtils.contains(
							this.securitySubtypeIdsSpecificChargeRegistrationNoMandatory, secSubType)
							&& ArrayUtils.contains(this.sourceIdsSpecificChargeRegistrationNoMandatory, source);
					validator.validateString(specific.getRegistrationNo(), "SpecificChargeDetail - RegistrationNo",
							registrationNoMandatory, (registrationNoMandatory ? 1 : 0), 25);

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeRegistrationFeeApplicable, secSubType)
							&& ArrayUtils.contains(this.sourceIdsSpecificChargeRegistrationFeeApplicable, source)) {
						validator.validateDoubleDigit(specific.getRegistrationFee(),
								"SpecificChargeDetail - RegistrationFee", false, 17, 2, false);
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeGoodStatusMandatory, secSubType)) {
						validator.validateStdCode(specific.getGoodStatus(), source, "GOODS_STATUS");
					}
					else {
						validator.validateStdCodeAllowNull(specific.getGoodStatus(), source, "GOODS_STATUS");
					}

					boolean publicTransportMandatory = ArrayUtils.contains(
							this.securitySubtypeIdsSpecificPublicTransportMandatory, secSubType);
					validator.validateString(specific.getPublicTransport(), "SpecificChargeDetail - PublicTransport",
							publicTransportMandatory, (publicTransportMandatory ? 1 : 0), 1);

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeRLSerialNoApplicable, secSubType)) {
						validator.validateString(specific.getRLSerialNo(), "SpecificChargeDetail - RLSerialNo", false,
								0, 20);
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeResidualScrapValueApplicable,
							secSubType)) {
						validator.validateDoubleDigit(specific.getResidualScrapValue(),
								"SpecificChargeDetail - ResidualScrapValue", false, 17, 2, false);
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeYardApplicable, secSubType)) {
						validator.validateString(specific.getYard(), "SpecificChargeDetail - Yard", false, 0, 40);
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeInsurersApplicable, secSubType)) {
						validator.validateStdCodeAllowNull(specific.getInsurers(), source, "INSURER_NAME");
					}

					if (ArrayUtils.contains(this.securitySubtypeIdsSpecificChargeDownPaymentInfoApplicable, secSubType)
							&& ArrayUtils.contains(this.sourceIdsSpecificChargeDownPaymentInfoApplicable, source)) {
						validator.validateDoubleDigit(specific.getDownPaymentTradeIn(),
								"SpecificChargeDetail - DownPaymentTradeIn", false, 13, 2, false);

						validator.validateDoubleDigit(specific.getDownPaymentCash(),
								"SpecificChargeDetail - DownPaymentCash", false, 13, 2, false);

						// check for downpayment trade in of 0.0 value as well.
						if (specific.getDownPaymentTradeIn() != null
								&& specific.getDownPaymentTradeIn().doubleValue() > 0) {
							validator.rejectIfNull(asset.getTradeInInformation(), "AssetDetail - TradeInInfo");

							for (Iterator itr = asset.getTradeInInformation().iterator(); itr.hasNext();) {
								TradeInInfo tradein = (TradeInInfo) itr.next();
								validator.validateStdCode(tradein.getMake(), source, "VEHICLE_BRAND");

								validator.validateStdCode(tradein.getModel(), source, "MODEL");

								validator.validateDoubleDigit(tradein.getTradeInDeposit(),
										"TradeInInfo - TradeInDeposit", true, 17, 2, false);

								if (tradein.getTradeInDeposit().compareTo(
										new BigDecimal(specific.getDownPaymentTradeIn().toString())) != 0) {
									throw new FieldValueNotAllowedException("TradeInInfo - TradeInDeposit", tradein
											.getTradeInDeposit().toString(), new String[] { specific
											.getDownPaymentTradeIn().toString() });
								}

								validator.validateDoubleDigit(tradein.getTradeInValue(), "TradeInInfo - TradeInValue",
										true, 17, 2, false);

								validator.validateString(tradein.getRegistrationNo(),
										"TradeInInfo - TradeInRegistrationNo", true, 1, 25);

								validator.validateNumber(tradein.getYearOfManufacture(),
										"TradeInInfo - TradeInManufactureYear", true, 1,
										IEaiConstant.ALLOWED_LONG_VALUE_YEAR_YYYY);
							}
						}
					}
				}
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsReceivableDetailApplicable, secSubType)) {
				if (asset.getReceivableDetail() != null) {
					ReceivableDetail receivable = asset.getReceivableDetail();
					validator.validateString(receivable.getReceivablesByBank(), "ReceivableDetail - ReceivablesByBank",
							false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

					validator.validateStdCodeAllowNull(receivable.getAccountNoLocation(), source, "40");

					validator.validateString(receivable.getAccountNo(), "ReceivableDetail - AccountNo", false, 0, 20);

					validator.validateStdCodeAllowNull(receivable.getInvoiceType(), source, "INVOICE_TYPE");
				}
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsAircraftDetailApplicable, secSubType)) {
				AirCraftDetail aircraft = asset.getAircraftDetail();

				validator.rejectIfNull(aircraft, "AssetDetail - AirCraftDetail");

				validator.validateString(aircraft.getInsuranceBrokerUnderTake(),
						"AirCraftDetail - InsuranceBrokerUnderTake", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

				validator.validateString(aircraft.getProcessAgent(), "AirCraftDetail - ProcessAgent", false, 0,
						100);

				validator.validateString(aircraft.getProcessAgentCountry(),
						"AirCraftDetail - ProcessAgentCountry", false, 0, 2);

				validator.validateString(aircraft.getSpecialistLegalOpinion(),
						"AirCraftDetail - SpecialistLegalOpinion", false, 0, 15);

				validator.validateStdCodeAllowNull(aircraft.getExportCreditAgency(), source,
						CategoryCodeConstant.ECA);

				validator.validateString(aircraft.getGuarantors(), "AirCraftDetail - Guarantors", false, 0, 100);

				validator.validateString(aircraft.getAircraftSerialNo(), "AirCraftDetail - AircraftSerialNo",
						false, 0, 20);

				validator.validateString(aircraft.getManufacturerName(), "AirCraftDetail - ManufacturerName",
						false, 0, 50);

				validator.validateString(aircraft.getManufactureWarranties(),
						"AirCraftDetail - ManufactureWarranties", false, 0, 50);

				validator.validateString(aircraft.getAssignors(), "AirCraftDetail - Assignors", false, 0, 100);

				validator.validateString(aircraft.getInsureAssignment(), "AirCraftDetail - InsureAssignment",
						false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

				validator.validateDoubleDigit(aircraft.getAmountAssignmentIsure(),
						"AirCraftDetail - AmountAssignmentIsure", false, 15, 2, false);

				validator.validateString(aircraft.getReinsureAssignment(), "AirCraftDetail - ReinsureAssignment",
						false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsGoldDetailApplicable, secSubType)) {
				GoldDetail gold = asset.getGoldDetail();

				validator.rejectIfNull(gold, "AssetDetail - GoldDetail");

				validator.validateStdCodeAllowNull(gold.getGoldGrade(), source,
						CategoryCodeConstant.CATEGORY_GOLD_GRADE);

				validator.validateString(gold.getPurchaseReceiptNo(), "GoldDetail - PurchaseReceiptNo", false, 0, 40);

				validator.validateDoubleDigit(gold.getGoldUnitPrice(), "GoldDetail - GoldUnitPrice", false, 11, 4,
						false);

				validator.validateStdCodeAllowNull(gold.getGoldUnitPriceUOM(), source,
						CategoryCodeConstant.CATEGORY_GOLD_UOM);

				validator.validateDoubleDigit(gold.getGoldWeight(), "GoldDetail - GoldWeight", false, 11, 4, false);

				validator.validateStdCodeAllowNull(gold.getGoldWeightUnit(), source,
						CategoryCodeConstant.CATEGORY_GOLD_UOM);

				validator.validateDoubleDigit(gold.getAuctionPrice(), "GoldDetail - AuctionPrice", false, 11, 4, false);

				validator.validateString(gold.getAuctioneer(), "GoldDetail - Auctioneer", false, 0, 20);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsVehicleDetailApplicable, secSubType)) {
				VehicleDetail vehicle = asset.getVehicleDetail();

				validator.rejectIfNull(vehicle, "AssetDetail - VehicleDetail");

				validator.validateString(vehicle.getChassisNumber(), "VehicleDetail - ChassisNumber", false, 0, 25);

				if (this.validationMandatoryFieldFactory.isMandatoryField(source, VehicleDetail.class, "PBTPBRInd")) {
					validator.validateString(vehicle.getPBTPBRInd(), "VehicleDetail - PBTPBRInd", true, 2, 3,
							new String[] { "PBT", "PBR", "NA" });
					validator.validateStdCode(new StandardCode("PBR_PBT_IND", vehicle.getPBTPBRInd()), source,
							"PBR_PBT_IND");

					if (ArrayUtils.contains(new String[] { "PBT", "PBR" }, vehicle.getPBTPBRInd())) {
						validator.validateNumber(vehicle.getPBTPBRPeriod(), "VehicleDetail - PBTPBRPeriod", true, 1,
								999);
					}
				}

				validator.validateString(vehicle.getLogBookNo(), "VehicleDetail - LogBookNo", false, 0, 50);

				validator.validateStdCode(vehicle.getDealerName(), source, "DEALER");

				validator.validateDoubleDigit(vehicle.getPriceList(), "VehicleDetail - PriceList", false, 17, 2, false);

				validator.validateString(vehicle.getEngineNumber(), "VehicleDetail - EngineNumber", false, 0, 35);

				validator.validateString(vehicle.getTransmissionType(), "VehicleDetail - TransmissionType", false, 0,
						1, new String[] { "A", "M" });

				validator.validateStdCodeAllowNull(vehicle.getEnergySource(), source, "ENERGY_SOURCE");

				validator.validateString(vehicle.getEnergyCapacity(), "VehicleDetail - EnergyCapacity", false, 0, 50);

				validator.validateString(vehicle.getVehicleColour(), "VehicleDetail - VehicleColour", false, 0, 10);

				validator.validateString(vehicle.getEHakMilikNum(), "VehicleDetail - EHakMilikNum", false, 0, 50);

				validator.validateString(vehicle.getYardWarehouseStore(), "VehicleDetail - YardWarehouseStore", false,
						0, 1, new String[] { "Y", "W", "S" });

				validator.validateString(vehicle.getRoadTaxAmtType(), "VehicleDetail - RoadTaxAmtType", false, 0, 1,
						new String[] { "H", "Y" });

				validator.validateDoubleDigit(vehicle.getRoadTaxAmt(), "VehicleDetail - RoadTaxAmt", (vehicle
						.getRoadTaxAmtType() != null), 17, 2, false);

				validator.validateDoubleDigit(vehicle.getFreightCharges(), "VehicleDetail - FreightCharges", false, 15,
						2, false);

				validator.validateString(vehicle.getInvoiceNumber(), "VehicleDetail - InvoiceNo", false, 0, 50);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsVesselDetailApplicable, secSubType)) {
				VesselDetail vessel = asset.getVesselDetail();

				validator.rejectIfNull(vessel, "AssetDetail - VesselDetail");

				validator.validateString(vessel.getVesselName(), "VesselDetail - VesselName", false, 0, 60);

				validator.validateStdCodeAllowNull(vessel.getVesselState(), source, "VESSEL_STATE");

				validator.validateString(vessel.getExpectedOccupancyDate(), "VesselDetail - ExpectedOccupancyDate",
						false, 0, 60);

				validator.validateStdCodeAllowNull(vessel.getOccupancyType(), source, "OCCUP_TYPE");

				validator.validateNumber(vessel.getBuiltYear(), "VesselDetail - BuiltYear", false, 0,
						IEaiConstant.ALLOWED_LONG_VALUE_YEAR_YYYY);

				validator.validateString(vessel.getBuilder(), "VesselDetail - Builder", false, 0, 60);

				validator.validateString(vessel.getMainRegistration(), "VesselDetail - MainRegistration", false, 0, 40);

				validator.validateString(vessel.getLength(), "VesselDetail - Length", false, 0, 40);

				validator.validateString(vessel.getWidth(), "VesselDetail - Width", false, 0, 40);

				validator.validateString(vessel.getDepth(), "VesselDetail - Depth", false, 0, 40);

				validator.validateString(vessel.getDeckLoading(), "VesselDetail - DeckLoading", false, 0, 40);

				validator.validateString(vessel.getDeckWeight(), "VesselDetail - DeckWeight", false, 0, 40);

				validator.validateString(vessel.getSideBoard(), "VesselDetail - SideBoard", false, 0, 40);

				validator.validateString(vessel.getBow(), "VesselDetail - Bow", false, 0, 40);

				validator.validateString(vessel.getDeck(), "VesselDetail - Deck", false, 0, 40);

				validator.validateString(vessel.getDeckThickness(), "VesselDetail - DeckThickness", false, 0, 40);

				validator.validateString(vessel.getBottom(), "VesselDetail - Bottom", false, 0, 40);

				validator.validateString(vessel.getWinchDrive(), "VesselDetail - WinchDrive", false, 0, 40);

				validator.validateString(vessel.getBHP(), "VesselDetail - BHP", false, 0, 40);

				validator.validateString(vessel.getSpeed(), "VesselDetail - Speed", false, 0, 40);

				validator.validateString(vessel.getAnchor(), "VesselDetail - Anchor", false, 0, 40);

				validator.validateString(vessel.getAnchorDrive(), "VesselDetail - AnchorDrive", false, 0, 40);

				validator.validateString(vessel.getClassSociety(), "VesselDetail - ClassSociety", false, 0, 40);

				validator.validateString(vessel.getConstructionCountry(), "VesselDetail - ConstructionCountry", false,
						0, 2);

				validator.validateString(vessel.getConstructionPlace(), "VesselDetail - ConstructionPlace", false, 0,
						40);

				validator.validateString(vessel.getVesselUse(), "VesselDetail - VesselUse", false, 0, 60);

				validator.validateString(vessel.getCharterContractFlag(), "VesselDetail - CharterContractFlag", false,
						0, 1, EaiConstantCla.getAllowedValuesYesNo());

				validator.validateString(vessel.getChartererName(), "VesselDetail - ChartererName", false, 0, 40);

				validator.validateNumber(vessel.getCharterPeriod(), "VesselDetail - CharterPeriod", false, 0, 99999);

				validator.validateStdCodeAllowNull(vessel.getCharterPeriodUnit(), source, "CHARTER_PERIOD_UNIT");

				validator.validateDoubleDigit(vessel.getCharterAmount(), "VesselDetail - CharterAmount", false, 15, 2,
						false);

				validator.validateString(vessel.getCharterCurrency(), "VesselDetail - CharterCurrency", false, 0, 3);

				validator.validateStdCodeAllowNull(vessel.getCharterRate(), source, "CHARTER_RATE_UNIT");

				validator.validateString(vessel.getCharterRateOthers(), "VesselDetail - CharterRateOthers", false, 0,
						40);

				validator.validateString(vessel.getCharterRemarks(), "VesselDetail - CharterRemarks", false, 0, 2000);

				validator.validateString(vessel.getPortRegistration(), "VesselDetail - PortRegistration", false, 0, 2);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsPlantEquipDetailApplicable, secSubType)) {
				if (asset.getPlantEquipDetail() != null) {
					validator.validateString(asset.getPlantEquipDetail().getSerialNo(), "PlantEquipDetail - SerialNo",
							false, 0, 20);

					validator.validateString(asset.getPlantEquipDetail().getInvoiceNo(),
							"PlantEquipDetail - InvoiceNo", false, 0, 50);
				}
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsPostDatedChequeDetailApplicable, secSubType)) {
				PostDatedCheque pdc = asset.getPostDatedCheque();

				validator.rejectIfNull(pdc, "AssetDetail - PostDatedCheque");

				validator.validateString(pdc.getChequeRefNum(), "PostDatedCheque - ChequeRefNum", false, 0, 20);

				validator.validateDoubleDigit(pdc.getInterestRate(), "PostDatedCheque - InterestRate", true, 2, 9,
						false);

				validator.rejectIfNull(asset.getChequeDetail(), "AssetDetail - ChequeDetail");

				for (Iterator itr = asset.getChequeDetail().iterator(); itr.hasNext();) {
					ChequeDetail cheque = (ChequeDetail) itr.next();
					validator.validateString(cheque.getSecurityId(), "ChequeDetail - LOSSecurityId", true, 1, 20);

					validator.validateString(cheque.getLOSChequeId(), "ChequeDetail - LOSChequeId", true, 1, 20);

					if (IEaiConstant.CHANGE_INDICATOR_YES.equals(cheque.getChangeIndicator())
							&& !IEaiConstant.UPDATE_STATUS_IND_INSERT.equals(cheque.getUpdateStatusIndicator())) {
						validator.validateNumber(new Long(cheque.getCmsChequeId()), "ChequeuDetail - CmsChequeId",
								true, 1, IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
					}

					validator.validateString(cheque.getChequeType(), "ChequeDetail - ChequeType", true, 1, 20);

					validator.validateString(cheque.getChequeAmountCurrency(), "ChequeDetail - ChequeAmountCurrency",
							true, 1, 3);

					validator.validateDoubleDigit(cheque.getChequeAmount(), "ChequeDetail - ChequeAmount", true, 15, 2,
							false);

					validator.validateString(cheque.getReceivableByBank(), "ChequeDetail - ReceivableByBank", false, 0,
							1, EaiConstantCla.getAllowedValuesYesNo());

					validator.validateString(cheque.getToCredit(), "ChequeDetail - ToCredit", false, 0, 100);

					validator.validateStdCodeAllowNull(cheque.getBankAcctLocation(), source, "40");

					validator.validateStdCode(cheque.getIssuer(), source, "REIMBURSEMENT_BANK");

					validator.validateString(cheque.getDraweeBank(), "ChequeDetail - DraweeBank", false, 0, 100);

					validator.validateString(cheque.getIssueCountry(), "ChequeDetail - IssueCountry", false, 0, 2);

					validator.validateNumber(cheque.getMargin(), "ChequeDetail - Margin", false, 0, 100);

					validator.validateDoubleDigit(cheque.getValueBeforeMargin(), "ChequeDetail - ValueBeforeMargin",
							false, 15, 2, false);

					validator.validateString(cheque.getCustodianType(), "ChequeDetail - CustodianType", true, 1, 1,
							EaiConstantCla.getAllowedValuesCustodianTypes());

					if (ArrayUtils.contains(EaiConstantCla.getAllowedValuesCustodianTypes(), cheque.getCustodianType())) {
						validator.validateString(cheque.getSecurityCustodian(), "ChequeDetail - SecurityCustodian",
								true, 1, 100);
					}
				}
			}

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, asset.getUpdateStatusIndicator(), asset
						.getChangeIndicator());
			}
		}
	}
}
