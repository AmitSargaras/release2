package com.integrosys.cms.app.ws.security;

import java.sql.SQLException;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;

public class PlantMachinerySecurityResponse extends AbstractSecurityResponse {
	
	public static final String SECURITY_TYPE_LBL = "Security Type";
	public static final String SECURITY_SUB_TYPE_LBL = "Security Sub Type";
	public static final String VALUATION_AMOUNT = "Valuation Amount";
	public static final String DATE_OF_VALUATION = "Valuation Date";
	public static final String TYPE_OF_CHARGE = "Type Of Charge";
	public static final String PHYSICAL_INSPECTION = "Physical Inspection";
	public static final String FREQUENCY = "Frequency";
	public static final String NEXT_PHYSICAL_INSPECTION_DUE_ON = "Next Physical Inspection Due On";
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String securityType="-";
		String securitySubType="-";
		String valuationAmount = "-";  
		String dateOfValuation = "-";
		String typeOfCharge = "-";
		String physicalInspection = "-";
		String frequency = "-";
		String nextPhysicalInspectionDueOn = "-";
		
		StringBuilder responseMsgBldr = new StringBuilder();
		OBSpecificChargePlant obSpecificChargePlant = (OBSpecificChargePlant)iCollateralInstance;
		CommonCodeEntryDAO commonCodeEntryDAO = new CommonCodeEntryDAO();
		
		if(iCollateralInstance.getCollateralType()!=null){
			securityType = iCollateralInstance.getCollateralType().getTypeName();
		}
		if(iCollateralInstance.getCollateralSubType()!=null){
			securitySubType = iCollateralInstance.getCollateralSubType().getSubTypeName();
		}
		if(iCollateralInstance.getValuationAmount()!=null && !iCollateralInstance.getValuationAmount().isEmpty()){
			valuationAmount = iCollateralInstance.getValuationAmount();
		}
		if(iCollateralInstance.getValuationDate()!=null){
			dateOfValuation = sdf.format(iCollateralInstance.getValuationDate());
		}
		if(iCollateralInstance.getTypeOfChange()!=null && !iCollateralInstance.getTypeOfChange().isEmpty()){
			
			try {
				typeOfCharge = commonCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(iCollateralInstance.getTypeOfChange(), "TYPE_CHARGE");
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		physicalInspection = obSpecificChargePlant.getIsPhysicalInspection()?"Yes":"No";
		
		if(obSpecificChargePlant.getPhysicalInspectionFreqUnit()!=null && !obSpecificChargePlant.getPhysicalInspectionFreqUnit().isEmpty()){
			try {
				frequency = commonCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obSpecificChargePlant.getPhysicalInspectionFreqUnit(),"FREQUENCY");
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(obSpecificChargePlant.getNextPhysicalInspectDate()!=null){
			nextPhysicalInspectionDueOn = sdf.format(obSpecificChargePlant.getNextPhysicalInspectDate());
		}
		
		//Response Message
		responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_TYPE_LBL, securityType));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_SUB_TYPE_LBL, securitySubType));
		responseMsgBldr.append(setResponseMessageValues(VALUATION_AMOUNT, valuationAmount));
		responseMsgBldr.append(setResponseMessageValues(DATE_OF_VALUATION, dateOfValuation));
		responseMsgBldr.append(setResponseMessageValues(TYPE_OF_CHARGE, typeOfCharge));
		responseMsgBldr.append(setResponseMessageValues(PHYSICAL_INSPECTION, physicalInspection));
		responseMsgBldr.append(setResponseMessageValues(FREQUENCY, frequency));
		responseMsgBldr.append(setLastLineMessageInResponse(NEXT_PHYSICAL_INSPECTION_DUE_ON, nextPhysicalInspectionDueOn));
		responseMsgBldr.append("\n");
		
		return responseMsgBldr.toString();
	}
	
}

