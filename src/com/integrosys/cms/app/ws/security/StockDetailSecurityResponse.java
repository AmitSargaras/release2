package com.integrosys.cms.app.ws.security;

import java.sql.SQLException;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.chktemplate.bus.DocumentDAO;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;

public class StockDetailSecurityResponse extends AbstractSecurityResponse {
	
	public static final String STATEMENT_NAME_LBL = "Statement Name";
	public static final String LOCATION_LBL = "Location";
	public static final String HDFC_BANK_SHARE_LBL = "HDFC Bank Share";
	public static final String DRAWING_POWER_LBL = "Drawing Power";
	public static final String LOANABLE_VALUE_LBL = "Loanable Value";

	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String statementName="-";
		String location="ALL";
		String hdfcBankShare="-";
		String drawingPower="-";
		String loanableValue = "-";  
		
		StringBuilder responseMsgBldr = new StringBuilder();
		OBGeneralCharge obGeneralCharge = (OBGeneralCharge)iCollateralInstance;
		
		IGeneralChargeDetails[] generalChargeDetailsArray = (IGeneralChargeDetails[])obGeneralCharge.getGeneralChargeDetails();
		
		if(generalChargeDetailsArray!=null && generalChargeDetailsArray.length>0){
			for(IGeneralChargeDetails generalChargeDetails : generalChargeDetailsArray){
				
				DocumentDAO documentDAO = new DocumentDAO();
				if(generalChargeDetails.getDocCode()!=null && !generalChargeDetails.getDocCode().isEmpty()){
					try {
						String docDesc = documentDAO.getDocumentDescByDocCode(generalChargeDetails.getDocCode());
						if(docDesc!=null  && !docDesc.isEmpty()){
							statementName = docDesc;
						}
					} catch (DBConnectionException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(generalChargeDetails.getCalculatedDP()!=null && !generalChargeDetails.getCalculatedDP().isEmpty()){
					drawingPower = generalChargeDetails.getCalculatedDP();
				}
//				if(generalChargeDetails.getFundedShare()!=null && !generalChargeDetails.getFundedShare().isEmpty()){
//					hdfcBankShare = generalChargeDetails.getFundedShare();	
//				}
				
				//Uma Khot:Cam upload and Dp field calculation CR
				if(generalChargeDetails.getDpShare()!=null && !generalChargeDetails.getDpShare().isEmpty()){
					hdfcBankShare = generalChargeDetails.getDpShare();	
				}
				
				IGeneralChargeStockDetails[] generalChargeStockDetailsArray = (IGeneralChargeStockDetails[])generalChargeDetails.getGeneralChargeStockDetails();
				MasterAccessUtility masterAccessUtilityObj = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
				
				if(generalChargeStockDetailsArray!=null && generalChargeStockDetailsArray.length>0){
					Double currentAsset = 0d;
					Double currentLiabilities = 0d;
					
					Object objCity = masterAccessUtilityObj.getMaster("actualCity", new Long(generalChargeStockDetailsArray[0].getLocationId()));
					
					if(objCity!=null){
						location = ((ICity)objCity).getCityName();
					}
					
					for(int i=0 ; i < generalChargeStockDetailsArray.length ; i++){
						if("CurrentAsset".equalsIgnoreCase(generalChargeStockDetailsArray[i].getStockType())){
							currentAsset = Double.parseDouble(generalChargeStockDetailsArray[i].getLonable());
						}
						
						if("CurrentLiabilities".equalsIgnoreCase(generalChargeStockDetailsArray[i].getStockType())){
							currentLiabilities = Double.parseDouble(generalChargeStockDetailsArray[i].getLonable());
						}
						
					}
					Double differece = currentAsset-currentLiabilities;
					loanableValue = differece.toString();
				}
				
				responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
				responseMsgBldr.append(setResponseMessageValues(STATEMENT_NAME_LBL, statementName));
				responseMsgBldr.append(setResponseMessageValues(LOCATION_LBL, location));
				responseMsgBldr.append(setResponseMessageValues(HDFC_BANK_SHARE_LBL, hdfcBankShare));
				responseMsgBldr.append(setResponseMessageValues(DRAWING_POWER_LBL, drawingPower));
				responseMsgBldr.append(setLastLineMessageInResponse(LOANABLE_VALUE_LBL, loanableValue));
				responseMsgBldr.append("\n");
			}
		}else{
			responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
			responseMsgBldr.append(setResponseMessageValues(STATEMENT_NAME_LBL, statementName));
			responseMsgBldr.append(setResponseMessageValues(LOCATION_LBL, location));
			responseMsgBldr.append(setResponseMessageValues(HDFC_BANK_SHARE_LBL, hdfcBankShare));
			responseMsgBldr.append(setResponseMessageValues(DRAWING_POWER_LBL, drawingPower));
			responseMsgBldr.append(setLastLineMessageInResponse(LOANABLE_VALUE_LBL, loanableValue));
		}
		return responseMsgBldr.toString();
	}
	
}

