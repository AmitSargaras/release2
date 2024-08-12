package com.integrosys.cms.app.ws.security;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;

public class PDCSecurityResponse extends AbstractSecurityResponse {
	
	public static final String SECURITY_TYPE_LBL = "Security Type";
	public static final String SECURITY_SUB_TYPE_LBL = "Security Sub Type";
	public static final String MONITORING_FREQ_OF_COLLATERALS_LBL = "Monitoring Freq. Of Collaterals";
	public static final String CHEQUE_NUMBER_LBL = "Cheque Number";
	public static final String CHEQUE_AMOUNT_LBL = "Cheque Amount";
	public static final String TOTAL_NO_OF_CHEQUE_LBL = "Total No. Of Cheque";
	public static final String MATURITY_DATE_LBL = "Maturity Date";

	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String securityType="-";
		String securitySubType="-";
		String monitoringFrequencyOfCollaterals="-";
		String chequeNumber="-";
		String chequeAmount = "-";  
		String totalNumberOfCheque = "0";
		String maturityDate = "-";
		
		StringBuilder responseMsgBldr = new StringBuilder();
		OBAssetPostDatedCheque obAssetPostDatedCheque = (OBAssetPostDatedCheque)iCollateralInstance;
		
		if(iCollateralInstance.getCollateralType()!=null){
			securityType = iCollateralInstance.getCollateralType().getTypeName();
		}
		if(iCollateralInstance.getCollateralSubType()!=null){
			securitySubType = iCollateralInstance.getCollateralSubType().getSubTypeName();
		}
		if(iCollateralInstance.getMonitorFrequency()!=null && !iCollateralInstance.getMonitorFrequency().isEmpty()){
			CommonCodeEntryDAO commonCodeEntryDAO = new CommonCodeEntryDAO();
			try {
				monitoringFrequencyOfCollaterals = commonCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(iCollateralInstance.getMonitorFrequency(), "FREQUENCY");
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(obAssetPostDatedCheque.getPostDatedCheques()!=null && obAssetPostDatedCheque.getPostDatedCheques().length>0){
			
			OBPostDatedCheque[] postDatedChequesArray = (OBPostDatedCheque[])obAssetPostDatedCheque.getPostDatedCheques();
			
			for(OBPostDatedCheque obPostDatedCheque : postDatedChequesArray){
				
				if(obPostDatedCheque.getChequeNumber()!=null && !obPostDatedCheque.getChequeNumber().isEmpty()){
					chequeNumber = StringUtils.leftPad(obPostDatedCheque.getChequeNumber(), 6, '0');
				}
				if(obPostDatedCheque.getChequeAmount()!=null){
					chequeAmount = obPostDatedCheque.getChequeAmount().getAmountAsBigDecimal().toString();
				}
				if(obPostDatedCheque.getMaturityDate()!=null){
					maturityDate = sdf.format(obPostDatedCheque.getMaturityDate());
				}
				
				Long checkSeriesDiff = (obPostDatedCheque.getChequeNoTo() - obPostDatedCheque.getChequeNoFrom())+1;
				
				if(checkSeriesDiff!=null){
					totalNumberOfCheque = checkSeriesDiff.toString();
				}
				
				responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
				responseMsgBldr.append(setResponseMessageValues(SECURITY_TYPE_LBL, securityType));
				responseMsgBldr.append(setResponseMessageValues(SECURITY_SUB_TYPE_LBL, securitySubType));
				responseMsgBldr.append(setResponseMessageValues(MONITORING_FREQ_OF_COLLATERALS_LBL, monitoringFrequencyOfCollaterals));
				responseMsgBldr.append(setResponseMessageValues(CHEQUE_NUMBER_LBL, chequeNumber));
				responseMsgBldr.append(setResponseMessageValues(CHEQUE_AMOUNT_LBL, chequeAmount));
				responseMsgBldr.append(setResponseMessageValues(TOTAL_NO_OF_CHEQUE_LBL, totalNumberOfCheque));
				responseMsgBldr.append(setLastLineMessageInResponse(MATURITY_DATE_LBL, maturityDate));
				responseMsgBldr.append("\n");
			}
		}else{
			responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
			responseMsgBldr.append(setResponseMessageValues(SECURITY_TYPE_LBL, securityType));
			responseMsgBldr.append(setResponseMessageValues(SECURITY_SUB_TYPE_LBL, securitySubType));
			responseMsgBldr.append(setResponseMessageValues(MONITORING_FREQ_OF_COLLATERALS_LBL, monitoringFrequencyOfCollaterals));
			responseMsgBldr.append(setResponseMessageValues(CHEQUE_NUMBER_LBL, chequeNumber));
			responseMsgBldr.append(setResponseMessageValues(CHEQUE_AMOUNT_LBL, chequeAmount));
			responseMsgBldr.append(setResponseMessageValues(TOTAL_NO_OF_CHEQUE_LBL, totalNumberOfCheque));
			responseMsgBldr.append(setLastLineMessageInResponse(MATURITY_DATE_LBL, maturityDate));
			responseMsgBldr.append("\n");
		}
		return responseMsgBldr.toString();
	}
	
}

