package com.integrosys.cms.host.eai.security.sharedsecurity.validator;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.security.sharedsecurity.EAISharedSecurityHelper;
import com.integrosys.cms.host.eai.security.sharedsecurity.bus.SharedSecuritySearch;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * EAI Message Validator for Shared Security
 * 
 * @author Iwan Satria
 * @author Chong Jun Yong
 * @since 1.1
 */
public class SharedSecurityValidator implements IEaiMessageValidator {

	public SharedSecurityValidator() {
	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		SharedSecuritySearch ss = EAISharedSecurityHelper.getInstance().retrieveSharedSecuritySearch(scimessage);
		EaiValidationHelper validator = EaiValidationHelper.getInstance();
		String source = scimessage.getMsgHeader().getSource();

		if (ss == null) {
			throw new MandatoryFieldMissingException("SharedSecuritySearch");
		}
		else {
			validator.validateStdCode(ss.getSecurityType(), source, "31");

			validator.validateStdCode(ss.getSecuritySubType(), source, "54");

			validator.validateString(ss.getCountry(), "Country", true, 1, 2);

			validator.validateString(ss.getPledgorLegalName(), "PledgorLegalName", false, 1, 100);

			validator.validateString(ss.getIdNumber(), "IdNumber", false, 1, 100);

			if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(ss.getSecurityType().getStandardCodeValue())) {
				if (ss.getProperty() == null) {
					throw new MandatoryFieldMissingException("Property");
				}

				if (ss.getProperty().getTitleNumberPrefix() == null) {
					throw new MandatoryFieldMissingException("Property.TitleNumberPrefix");
				}
			}
			else if (ICMSConstant.SECURITY_TYPE_ASSET.equals(ss.getSecurityType().getStandardCodeValue())) {
				if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(ss.getSecuritySubType().getStandardCodeValue())) {
					if (ss.getVehicle() == null) {
						throw new MandatoryFieldMissingException("AssetVehicle");
					}
					else {
						if (ss.getVehicle().getVehicleType() != null) {
							validator.validateStdCode(ss.getVehicle().getVehicleType(), source,
									CategoryCodeConstant.VEHICLE_TYPE);
						}

						validator.validateString(ss.getVehicle().getRegistrationNo(), "RegistrationNo", false, 1, 25);
						validator.validateString(ss.getVehicle().getChassisNumber(), "ChassisNumber", false, 1, 25);
						validator.validateString(ss.getVehicle().getEngineNumber(), "EngineNumber", false, 1, 35);
					}
				}
				else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT.equals(ss.getSecuritySubType()
						.getStandardCodeValue())) {
					if (ss.getAircraft() == null) {
						throw new MandatoryFieldMissingException("AssetAircraft");
					}
					else {
						if (ss.getAircraft().getAircraftType() != null) {
							validator.validateStdCode(ss.getAircraft().getAircraftType(), source,
									CategoryCodeConstant.AIRCRAFT_TYPE);
						}
						if (ss.getAircraft().getManufactureYear() != null) {
							validator.validateLong("ManufactureYear",
									ss.getAircraft().getManufactureYear().longValue(), 1000, 9999);
						}
					}
				}
				else if (ICMSConstant.COLTYPE_ASSET_VESSEL.equals(ss.getSecuritySubType().getStandardCodeValue())) {
					if (ss.getVessel() == null) {
						throw new MandatoryFieldMissingException("AssetVessel");
					}
					else {
						if (ss.getVessel().getVesselType() != null) {
							StandardCode vesselType = ss.getVessel().getVesselType();
							validator.validateStdCode(vesselType, source, CategoryCodeConstant.VESSEL_TYPE);
						}
						validator.validateNumber(ss.getVessel().getBuiltYear(), "BuiltYear", false, 1000, 9999);
						validator.validateString(ss.getVessel().getPortRegistration(), "PortRegistration", false, 1, 2);
					}
				}
				else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(ss.getSecuritySubType()
						.getStandardCodeValue())) {
					if (ss.getPlantEquip() == null) {
						throw new MandatoryFieldMissingException("AssetPlantEquip");
					}
					else {
						if (ss.getPlantEquip().getPlantEquipType() != null) {
							validator.validateStdCode(ss.getPlantEquip().getPlantEquipType(), source,
									CategoryCodeConstant.PLANT_EQUIP_TYPE);
						}

						if (ss.getPlantEquip().getManufactureYear() != null) {
							validator.validateLong("ManufactureYear", ss.getPlantEquip().getManufactureYear()
									.longValue(), 1000, 9999);
						}

						validator.validateString(ss.getPlantEquip().getSerialNo(), "SerialNo", false, 1, 20);
					}
				}
			}
		}
	}
}
