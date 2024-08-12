package com.integrosys.cms.app.ws.security;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntryDAO;

public class PropertySecurityResponse extends AbstractSecurityResponse {
	
	public static final String SALE_PURCHASE_AGREEMENT_VALUE_LBL = "Sale Purchase Agreement Value";
	public static final String SALE_PURCHASE_AGREEMENT_DATE_LBL = "Sale Purchase Agreement Date";
	public static final String MORTGAGE_CREATION_DATE_LBL = "Mortgage Creation Date";
	public static final String TYPE_OF_MORTGAGE_LBL = "Type Of Mortgage";
	public static final String OWNER_NAME_DETAIL_LBL = "Owner Name Detail";
	public static final String PROPERTY_COMPLETION_STATUS_LBL = "Property Completion Status";
	public static final String PHYSICAL_INSPECTION_LBL = "Physical Inspection";
	public static final String LAST_PHYSICAL_INSPECTION_DATE_LBL = "Last Physical Inspection Date";
	public static final String VALUATION_DATE_LBL = "Valuation Date";
	public static final String PROPERTY_TYPE_LBL = "Property Type";
	public static final String SELF_OCCUPIED_OR_RELEASED_LBL = "Self Occupied Or Released";
	public static final String LAND_AREA_LBL = "Land Area";
	public static final String BUILT_UP_AREA_LBL = "Built Up Area";
	public static final String PROPERTY_ADDRESS_LBL = "Property Address";
	public static final String PROPERTY_DESCRIPTION_LBL = "Property Description";
	public static final String TOTAL_PROPERTY_AMOUNT_LBL = "Total Property Amount";
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		
		String salePurchaseAgreementValue = "-";
		String salePurchaseAgreementDate = "-";
		String mortgageCreationDate = "-";
		String typeOfMortgage = "-";
		String ownerNameDetail = "-";
		String propertyCompletionStatus = "-";
		String physicalInspection = "-";
		String lastPhysicalInspectionDate = "-";
		String valuationDate = "-";
		String propertyType = "-";
		String selfOccupiedOrleased = "-";//To Do : What value should get displayed for this?
		String landArea = "-";
		String builtUpArea = "-";
		String propertyAddress = "-";
		String propertyDescription = "-";
		String totalPropertyAmount = "0.00";
		
		StringBuilder responseMsgBldr = new StringBuilder();
		
		OBPropertyCollateral obPropertyCollateral = (OBPropertyCollateral)iCollateralInstance;
		
		if(obPropertyCollateral.getSalePurchaseValue()!=null){
			salePurchaseAgreementValue = Double.toString(obPropertyCollateral.getSalePurchaseValue().getAmount());
		}
		if(obPropertyCollateral.getSalePurchaseDate()!=null){
			salePurchaseAgreementDate = sdf.format(obPropertyCollateral.getSalePurchaseDate());
			mortgageCreationDate = salePurchaseAgreementDate;
		}
		if(obPropertyCollateral.getDevGrpCo()!=null && !obPropertyCollateral.getDevGrpCo().isEmpty()){
			ownerNameDetail = obPropertyCollateral.getDevGrpCo();
		}
		
		propertyCompletionStatus = Character.toString(obPropertyCollateral.getPropertyCompletionStatus());
		if(StringUtils.isBlank(propertyCompletionStatus.trim())){
			propertyCompletionStatus = "-";
		}
		
		physicalInspection = obPropertyCollateral.getIsPhysicalInspection()?"Yes":"No";
		
		if(obPropertyCollateral.getLastPhysicalInspectDate()!=null){
			lastPhysicalInspectionDate = sdf.format(obPropertyCollateral.getLastPhysicalInspectDate());
		}
		if(obPropertyCollateral.getValuationDate()!=null){
			valuationDate = sdf.format(obPropertyCollateral.getValuationDate());
		}
		
		String landAreaUnit = "-";
		String builtUpAreaUnit = "-";
		
		CommonCodeEntryDAO cmnCodeEntryDAO =new CommonCodeEntryDAO();
		try {
			landAreaUnit = cmnCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obPropertyCollateral.getLandAreaUOM(), "AREA_UOM");
			builtUpAreaUnit = cmnCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obPropertyCollateral.getBuiltupAreaUOM(), "AREA_UOM");
			if(obPropertyCollateral.getPropertyType()!=null && !obPropertyCollateral.getPropertyType().isEmpty()){
				propertyType = cmnCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obPropertyCollateral.getPropertyType(), "PROPERTY_TYPE");
			}
			if(obPropertyCollateral.getTypeOfMargage()!=null && !obPropertyCollateral.getTypeOfMargage().isEmpty()){
				typeOfMortgage = cmnCodeEntryDAO.getEntryNameByEntrycodeAndCategoryCode(obPropertyCollateral.getTypeOfMargage(), "MORTGAGE_TYPE");
			}
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		landArea = Double.toString(obPropertyCollateral.getLandArea())+SPACE+ landAreaUnit;
		builtUpArea = Double.toString(obPropertyCollateral.getBuiltupArea())+SPACE + builtUpAreaUnit;
		
		if(obPropertyCollateral.getPropertyAddress()!=null && !obPropertyCollateral.getPropertyAddress().isEmpty()){
			StringBuilder stb = new StringBuilder(); 
			stb.append(obPropertyCollateral.getPropertyAddress());
			stb.append(obPropertyCollateral.getPropertyAddress2()!=null?"\n"+obPropertyCollateral.getPropertyAddress2():"");
			stb.append(obPropertyCollateral.getPropertyAddress3()!=null?"\n"+obPropertyCollateral.getPropertyAddress3():"");
			stb.append(obPropertyCollateral.getPropertyAddress4()!=null?"\n"+obPropertyCollateral.getPropertyAddress4():"");
			stb.append(obPropertyCollateral.getPropertyAddress5()!=null?"\n"+obPropertyCollateral.getPropertyAddress5():"");
			stb.append(obPropertyCollateral.getPropertyAddress6()!=null?"\n"+obPropertyCollateral.getPropertyAddress6():"");
			propertyAddress = stb.toString();
		}
		
		if(obPropertyCollateral.getDescription()!=null && !obPropertyCollateral.getDescription().isEmpty()){
			propertyDescription = obPropertyCollateral.getDescription();
		}
		if(obPropertyCollateral.getTotalPropertyAmount()!=null){
			totalPropertyAmount = obPropertyCollateral.getTotalPropertyAmount().getAmountAsBigDecimal().toString();
		}
		
		//Response Message
		responseMsgBldr.append(setResponseMessageValues(SOURCE_SECURITY_ID_LBL,args[0]));
		responseMsgBldr.append(setResponseMessageValues(SALE_PURCHASE_AGREEMENT_VALUE_LBL,salePurchaseAgreementValue ));
		responseMsgBldr.append(setResponseMessageValues(SALE_PURCHASE_AGREEMENT_DATE_LBL,salePurchaseAgreementDate));
		responseMsgBldr.append(setResponseMessageValues(MORTGAGE_CREATION_DATE_LBL,mortgageCreationDate));
		responseMsgBldr.append(setResponseMessageValues(TYPE_OF_MORTGAGE_LBL,typeOfMortgage));
		responseMsgBldr.append(setResponseMessageValues(OWNER_NAME_DETAIL_LBL,ownerNameDetail));
		responseMsgBldr.append(setResponseMessageValues(PROPERTY_COMPLETION_STATUS_LBL,propertyCompletionStatus));
		responseMsgBldr.append(setResponseMessageValues(PHYSICAL_INSPECTION_LBL,physicalInspection));
		responseMsgBldr.append(setResponseMessageValues(LAST_PHYSICAL_INSPECTION_DATE_LBL,lastPhysicalInspectionDate));
		responseMsgBldr.append(setResponseMessageValues(VALUATION_DATE_LBL,valuationDate));
		responseMsgBldr.append(setResponseMessageValues(PROPERTY_TYPE_LBL,propertyType));
		responseMsgBldr.append(setResponseMessageValues(SELF_OCCUPIED_OR_RELEASED_LBL ,selfOccupiedOrleased));
		responseMsgBldr.append(setResponseMessageValues(LAND_AREA_LBL,landArea));
		responseMsgBldr.append(setResponseMessageValues(BUILT_UP_AREA_LBL,builtUpArea));
		responseMsgBldr.append(setResponseMessageValues(PROPERTY_ADDRESS_LBL,propertyAddress));
		responseMsgBldr.append(setResponseMessageValues(PROPERTY_DESCRIPTION_LBL,propertyDescription));
		responseMsgBldr.append(setLastLineMessageInResponse(TOTAL_PROPERTY_AMOUNT_LBL,totalPropertyAmount));
		responseMsgBldr.append("\n");

		return responseMsgBldr.toString();
	}
	
}
