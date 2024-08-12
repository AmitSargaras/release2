package com.integrosys.cms.app.ws.security;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;
import com.integrosys.cms.app.ws.jax.common.MasterAccessUtility;

public class SpecificAssetSecurityResponse extends AbstractSecurityResponse {
	
	public static final String SECURITY_TYPE_LBL = "Security Type";
	public static final String SECURITY_SUB_TYPE_LBL = "Security Sub Type";
	public static final String SECURITY_CURRENCY_LBL = "Security Currency";
	public static final String SECURITY_PRIORITY_LBL = "Security Priority";
	public static final String COLLATERAL_CODE_NAME = "Collateral Code Name";
	public static final String TYPE_OF_CHARGE = "Type Of Charge";
	public static final String VALUATION_AMOUNT = "Valuation Amount";
	public static final String DATE_OF_VALUATION = "Date Of Valuation";
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String securityType="-";
		String securitySubType="-";
		String securityCurrency="-";
		String securityPriority="-";
		String collateralCodeName = "-"; 
		String typeOfCharge = "-";
		String valuationAmount = "-";  
		String dateOfValuation = "-";
		
		StringBuilder responseMsgBldr = new StringBuilder();
//		OBSpecificChargeAircraft obSpecificChargeAircraft = (OBSpecificChargeAircraft)iCollateralInstance;
		
		if(iCollateralInstance.getCollateralType()!=null){
			securityType = iCollateralInstance.getCollateralType().getTypeName();
		}
		if(iCollateralInstance.getCollateralSubType()!=null){
			securitySubType = iCollateralInstance.getCollateralSubType().getSubTypeName();
		}
		if(iCollateralInstance.getCurrencyCode()!=null && !iCollateralInstance.getCurrencyCode().isEmpty()){
			securityCurrency = iCollateralInstance.getCurrencyCode().trim();
		}
		if(iCollateralInstance.getSecPriority()!=null && !iCollateralInstance.getSecPriority().isEmpty()){
			securityPriority = iCollateralInstance.getSecPriority().equalsIgnoreCase("Y")?"Primary":"Secondary";
		}
		if(iCollateralInstance.getCollateralCode()!=null && !iCollateralInstance.getCollateralCode().isEmpty()){
			MasterAccessUtility masterAccessUtilityInstance = (MasterAccessUtility)BeanHouse.get("masterAccessUtility");
			
			DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCollateralNewMaster")
					.add(Restrictions.eq("newCollateralCode", iCollateralInstance.getCollateralCode()))
					.add(Restrictions.eq("deprecated", "N"))
					.add(Restrictions.eq("status", "ACTIVE"))
					.add(Restrictions.eq("newCollateralSubType", iCollateralInstance.getCollateralSubType().getSubTypeCode()));
			
			List<Object> objList = masterAccessUtilityInstance.getObjectListBySpecification(criteria);
			if(objList!=null && objList.size()>0){
				collateralCodeName = ((OBCollateralNewMaster)objList.get(0)).getNewCollateralDescription();
			}
		}
		if(iCollateralInstance.getTypeOfChange()!=null && !iCollateralInstance.getTypeOfChange().isEmpty()){
			
			CommonCodeEntryDAO commonCodeEntryDAO = new CommonCodeEntryDAO();
			try {
				typeOfCharge = commonCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(iCollateralInstance.getTypeOfChange(), "TYPE_CHARGE");
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(iCollateralInstance.getValuationAmount()!=null && !iCollateralInstance.getValuationAmount().isEmpty()){
			valuationAmount = iCollateralInstance.getValuationAmount();
		}
		if(iCollateralInstance.getValuationDate()!=null){
			dateOfValuation = sdf.format(iCollateralInstance.getValuationDate());
		}
		
		//Response Message
		responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL, args[0]));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_TYPE_LBL, securityType));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_SUB_TYPE_LBL, securitySubType));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_CURRENCY_LBL, securityCurrency));
		responseMsgBldr.append(setResponseMessageValues(SECURITY_PRIORITY_LBL, securityPriority));
		responseMsgBldr.append(setResponseMessageValues(COLLATERAL_CODE_NAME, collateralCodeName));
		responseMsgBldr.append(setResponseMessageValues(TYPE_OF_CHARGE, typeOfCharge));
		responseMsgBldr.append(setResponseMessageValues(VALUATION_AMOUNT, valuationAmount));
		responseMsgBldr.append(setLastLineMessageInResponse(DATE_OF_VALUATION, dateOfValuation));
		responseMsgBldr.append("\n");
		
		return responseMsgBldr.toString();
	}
	
}

