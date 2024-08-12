/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/constant/ICMSJNDIConstant.java,v 1.137 2006/07/27 04:25:35 jzhan Exp $
 */
package com.integrosys.cms.app.common.constant;

import com.integrosys.base.businfra.constant.BusinfraJNDIConstant;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.EBCheckListItemImageDetailLocalHome;

/**
 * This interface contains JNDI constants in CMS.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.137 $
 * @since $Date: 2006/07/27 04:25:35 $ Tag: $Name: $
 */
public interface ICMSJNDIConstant extends BusinfraJNDIConstant {

	/** JNDIConstant for common collateral entity bean. */
	public static final String EB_COLLATERAL_JNDI = "EBCollateralHome";

	/** JNDIConstant for local collateral entity bean. */
	public static final String EB_COLLATERAL_LOCAL_JNDI = "EBCollateralLocalHome";

	/** JNDIConstant for staging of local collateral entity bean. */
	public static final String EB_COLLATERAL_LOCAL_STAGING_JNDI = "EBCollateralLocalHomeStaging";

	/** JNDIConstant for local valuation entity bean. */
	public static final String EB_COL_VALUATION_LOCAL_JNDI = "EBValuationLocalHome";

	/** JNDIConstant for valuation entity bean. */
	public static final String EB_COL_VALUATION_JNDI = "EBValuationHome";

	/** JNDIConstant for collateral pledgor entity bean. */
	public static final String EB_COL_PLEDGOR_LOCAL_JNDI = "EBCollateralPledgorLocalHome";

	public static final String EB_PLEDGOR_LOCAL_JNDI = "EBPledgorLocalHome";

	public static final String EB_PLEDGOR_STAGING_LOCAL_JNDI = "EBPledgorStagingLocalHome";

	public static final String EB_PLEDGOR_JNDI = "EBPledgorHome";

	public static final String EB_PLEDGOR_STAGING_JNDI = "EBPledgorStagingHome";

	public static final String EB_PLEDGOR_CREDIT_GRADE_JNDI = "EBPledgorCreditGradeHome";

	/** JNDIConstant for collateral limit charge. */
	public static final String EB_COL_LIMITCHARGE_LOCAL_JNDI = "EBLimitChargeLocalHome";

	public static final String EB_INSURANCE_POLICY_LOCAL_JNDI = "EBInsurancePolicyLocalHome";
	
	public static final String EB_SECURITY_COVERAGE_LOCAL_JNDI = "EBSecurityCoverageLocalHome";
	
	public static final String EB_ADDTIONAL_DOCUMENT_FACILITY_DETAILS_LOCAL_JNDI = "EBAddtionalDocumentFacilityDetailsLocalHome";

	public static final String EB_COL_LIMIT_MAP_LOCAL_JNDI = "EBCollateralLimitMapLocalHome";

	public static final String EB_LIMIT_CHARGE_MAP_LOCAL_JNDI = "EBLimitChargeMapLocalHome";

	// JNDIConstants for Commodity Sub Limit
	public static final String EB_SUB_LIMIT_LOCAL_JNDI = "EBCMDSubLimitLocalHome";

	public static final String EB_SUB_LIMIT_STAGING_LOCAL_JNDI = "EBCMDSubLimitStagingLocalHome";

	public static final String EB_SHARE_SECURITY_JNDI = "EBShareSecurityHome";

	/** JNDIConstant for collateral subtype to its local home. */
	public static final String EB_COL_SUBTYPE_LOCAL_JNDI = "EBCollateralSubTypeLocalHome";

	/** JNDIConstant for collateral subtype to its remote home. */
	public static final String EB_COL_SUBTYPE_JNDI = "EBCollateralSubTypeHome";

	public static final String EB_COL_SUBTYPE_STAGING_JNDI = "EBCollateralSubTypeStagingHome";

	/** JNDIConstant for staging of common collateral entity bean. */
	public static final String EB_COLLATERAL_STAGING_JNDI = "EBCollateralStagingHome";

	/** JNDIConstant for collateral parameter. */
	public static final String EB_COLLATERAL_PARAMETER_JNDI = "EBCollateralParameterHome";

	public static final String EB_COLLATERAL_PARAMETER_STAGING_JNDI = "EBCollateralParameterStagingHome";

	/** Local JNDIConstant for staging of valuation entity bean. */
	public static final String EB_COL_VALUATION_STAGING_LOCAL_JNDI = "EBValuationStagingLocalHome";

	public static final String EB_INSURANCE_POLICY_STAGING_LOCAL_JNDI = "EBInsurancePolicyStagingLocalHome";
	
	public static final String EB_SECURITY_COVERAGE_STAGING_LOCAL_JNDI = "EBSecurityCoverageStagingLocalHome";
	
	public static final String EB_ADDTIONAL_DOCUMENT_FACILITY_DETAILS_STAGING_LOCAL_JNDI = "EBAddtionalDocumentFacilityDetailsStagingLocalHome";

	/** Local JNDIConstant for actual valuation. */
	public static final String EB_COL_VALUATION_STAGING_JNDI = "EBValuationStagingHome";

	/** JNDIConstant for staging of collateral pledgor entity bean. */
	public static final String EB_COL_PLEDGOR_STAGING_LOCAL_JNDI = "EBCollateralPledgorStagingLocalHome";

	/** JNDIConstant for collateral limit charge. */
	public static final String EB_COL_LIMITCHARGE_STAGING_LOCAL_JNDI = "EBLimitChargeStagingLocalHome";

	public static final String EB_COL_LIMIT_MAP_STAGING_LOCAL_JNDI = "EBCollateralLimitMapStagingLocalHome";

	public static final String EB_LIMIT_CHARGE_MAP_STAGING_LOCAL_JNDI = "EBLimitChargeMapStagingLocalHome";

	public static final String EB_COL_APPORTIONMENT_JNDI = "EBSecApportionmentHome";

	public static final String EB_COL_APPORTIONMENT_STAGE_JNDI = "EBSecApportionmentStagingHome";

	public static final String EB_COL_APPORTIONMENT_LOCAL_JNDI = "EBSecApportionmentLocalHome";

	public static final String EB_COL_APPORTIONMENT_STAGING_LOCAL_JNDI = "EBSecApportionmentStagingLocalHome";

	/** JNDIConstant for guarantee type. */
	public static final String EB_GUARANTEE_COLLATERAL_JNDI = "EBGuaranteeCollateralHome";

	/** JNDIConstant for asset of type charge. */
	public static final String EB_ASSET_CHARGE_JNDI = "EBChargeCommonHome";

	public static final String EB_ASSET_AIRCRAFT_JNDI = "EBSpecificChargeAircraftHome";

	public static final String EB_ASSET_GOLD_JNDI = "EBSpecificChargeGoldHome";

	public static final String EB_ASSET_PLANT_JNDI = "EBSpecificChargePlantHome";

	public static final String EB_ASSET_VEHICLE_JNDI = "EBSpecificChargeVehicleHome";

	public static final String EB_ASSET_VESSEL_JNDI = "EBVesselHome";

	/** JNDIConstant for asset of type general charge */
	public static final String EB_ASSET_GEN_CHARGE_JNDI = "EBGeneralChargeHome";

	public static final String EB_INSURANCE_INFO_LOCAL_JNDI = "EBInsuranceInfoLocalHome";

	public static final String EB_STOCK_LOCAL_JNDI = "EBStockLocalHome";

	public static final String EB_DEBTOR_LOCAL_JNDI = "EBDebtorLocalHome";

	public static final String EB_FAO_LOCAL_JNDI = "EBFixedAssetOthersLocalHome";
	
	public static final String EB_LEAD_BANK_STOCK_JNDI = "EBLeadBankStockLocalHome";
	
	public static final String EB_LEAD_BANK_STOCK_STAGING_JNDI = "EBLeadBankStockStagingLocalHome";
	
	public static final String EB_INS_STOCK_MAP_LOCAL_JNDI = "EBInsuranceStockMapLocalHome";

	public static final String EB_INS_FAO_MAP_LOCAL_JNDI = "EBInsuranceFaoMapLocalHome";

	/** JNDIConstant for asset of type receivables. */
	public static final String EB_ASSET_RECEIVABLE_JNDI = "EBReceivableCommonHome";

	/** JNDIConstant for asset of type postdated cheque. */
	public static final String EB_ASSET_PDT_CHEQUE_JNDI = "EBAssetPostDatedChequeHome";

	public static final String EB_POST_DATED_CHEQUE_LOCAL_JNDI = "EBPostDatedChequeLocalHome";

	/** JNDIConstant for cash collateral type. */
	public static final String EB_CASH_COLLATERAL_JNDI = "EBCashCollateralHome";

	public static final String EB_CASH_DEPOSIT_LOCAL_JNDI = "EBCashDepositLocalHome";

	public static final String EB_FEEDETAILS_LOCAL_JNDI = "EBFeeDetailsLocalHome";

	public static final String EB_FEEDETAILS_STAGE_LOCAL_JNDI = "EBFeeDetailsStagingLocalHome";

	/** JNDIConstant for collateral of type document. */
	public static final String EB_DOCUMENT_COLLATERAL_JNDI = "EBDocumentCollateralHome";

	/** JNDIConstant for insurance collateral type. */
	public static final String EB_INSURANCE_COLLATERAL_JNDI = "EBInsuranceCollateralHome";

	public static final String EB_INSURANCE_CDS_LOCAL_JNDI = "EBCDSItemLocalHome";

	/** JNDIConstant for property collateral type. */
	public static final String EB_PROPERTY_COLLATERAL_JNDI = "EBPropertyCollateralHome";

	/** JNDIConstant for marketable collateral. */
	public static final String EB_MARKETABLE_COLLATERAL_JNDI = "EBMarketableCollateralHome";

	/** JNDIConstant for commodity collaterals. */
	public static final String EB_COMMODITY_COLLATERAL_JNDI = "EBCommodityCollateralHome";

	public static final String EB_COMMODITY_PRICE_LOCAL_JNDI = "EBCommodityPriceLocalHome";

	public static final String EB_COMMODITY_PRICE_HISTORY_LOCAL_JNDI = "EBCommodityPriceHistoryLocalHome";

	/** JNDIConstant for common equity collateral type. */
	public static final String EB_MARKETABLE_EQUITY_JNDI = "EBEquityCommonHome";

	/** local JNDIConstant for marketable equity entity bean. */
	public static final String EB_MARKETABLE_EQUITY_LOCAL_JNDI = "EBMarketableEquityLocalHome";

	/** local JNDIConstant for marketable commodity. */
	public static final String EB_MARKETABLE_COMMODITY_LOCAL_JNDI = "EBMarketableCommodityLocalHome";

	/** Staging JNDIConstant for guarantee type. */
	public static final String EB_GUARANTEE_COLLATERAL_STAGING_JNDI = "EBGuaranteeCollateralStagingHome";

	/** Staging JNDIConstant for asset of type charge. */
	public static final String EB_ASSET_CHARGE_STAGING_JNDI = "EBChargeCommonStagingHome";

	public static final String EB_ASSET_AIRCRAFT_STAGING_JNDI = "EBSpecificChargeAircraftStagingHome";

	public static final String EB_ASSET_GOLD_STAGING_JNDI = "EBSpecificChargeGoldStagingHome";

	public static final String EB_ASSET_PLANT_STAGING_JNDI = "EBSpecificChargePlantStagingHome";

	public static final String EB_ASSET_VEHICLE_STAGING_JNDI = "EBSpecificChargeVehicleStagingHome";

	public static final String EB_ASSET_VESSEL_STAGING_JNDI = "EBVesselStagingHome";

	/** JNDIConstant for asset of type general charge */
	public static final String EB_ASSET_GEN_CHARGE_STAGING_JNDI = "EBGeneralChargeStagingHome";

	public static final String EB_INSURANCE_INFO_STAGING_LOCAL_JNDI = "EBInsuranceInfoStagingLocalHome";

	public static final String EB_STOCK_STAGING_LOCAL_JNDI = "EBStagingStockLocalHome";

	public static final String EB_DEBTOR_STAGING_LOCAL_JNDI = "EBStagingDebtorLocalHome";

	public static final String EB_FAO_STAGING_LOCAL_JNDI = "EBStagingFixedAssetOthersLocalHome";

	public static final String EB_INS_STOCK_MAP_STAGING_LOCAL_JNDI = "EBInsuranceStockMapStagingLocalHome";

	public static final String EB_INS_FAO_MAP_STAGING_LOCAL_JNDI = "EBInsuranceFaoMapStagingLocalHome";

	/** Staging JNDIConstant for asset of type receivables. */
	public static final String EB_ASSET_RECEIVABLE_STAGING_JNDI = "EBReceivableCommonStagingHome";

	/** Staging JNDIConstant for asset of type postdated cheque. */
	public static final String EB_ASSET_PDT_CHEQUE_STAGING_JNDI = "EBAssetPostDatedChequeStagingHome";

	public static final String EB_POST_DATED_CHEQUE_STAGING_LOCAL_JNDI = "EBPostDatedChequeStagingLocalHome";

	/** Staging JNDIConstant for cash collateral type. */
	public static final String EB_CASH_COLLATERAL_STAGING_JNDI = "EBCashCollateralStagingHome";

	public static final String EB_CASH_DEPOSIT_STAGING_LOCAL_JNDI = "EBCashDepositStagingLocalHome";

	/** Staging JNDIConstant for collateral of type document. */
	public static final String EB_DOCUMENT_COLLATERAL_STAGING_JNDI = "EBDocumentCollateralStagingHome";

	/** Staging JNDIConstant for insurance collateral type. */
	public static final String EB_INSURANCE_COLLATERAL_STAGING_JNDI = "EBInsuranceCollateralStagingHome";

	public static final String EB_INSURANCE_CDS_STAGING_LOCAL_JNDI = "EBCDSItemStagingLocalHome";

	/** Staging JNDIConstant for property collateral type. */
	public static final String EB_PROPERTY_COLLATERAL_STAGING_JNDI = "EBPropertyCollateralStagingHome";

	/** Staging JNDIConstant for marketable collateral. */
	public static final String EB_MARKETABLE_COLLATERAL_STAGING_JNDI = "EBMarketableCollateralStagingHome";

	/** Staging JNDIConstant for commodity collaterals. */
	public static final String EB_COMMODITY_COLLATERAL_STAGING_JNDI = "EBCommodityCollateralStagingHome";

	public static final String EB_COMMODITY_PRICE_STAGING_LOCAL_JNDI = "EBCommodityPriceStagingLocalHome";

	/** Staging JNDIConstant for common equity collateral type. */
	public static final String EB_MARKETABLE_EQUITY_STAGING_JNDI = "EBEquityCommonStagingHome";

	/** Staging Local JNDIConstant for marketable equity entity bean. */
	public static final String EB_MARKETABLE_EQUITY_STAGING_LOCAL_JNDI = "EBMarketableEquityStagingLocalHome";

	/** Staging local JNDIConstant for marketable commodity. */
	public static final String EB_MARKETABLE_COMMODITY_STAGING_LOCAL_JNDI = "EBMarketableCommodityStagingLocalHome";

	/** JNDIConstant for collateral proxy session bean. */
	public static final String SB_COLLATERAL_PROXY_JNDI = "SBCollateralProxyHome";

	/** JNDIConstant for collateral business manager session bean. */
	public static final String SB_COLLATERAL_MGR_JNDI = "SBCollateralBusManagerHome";

	/** JNDIConstant for staging collateral business manager session bean. */
	public static final String SB_COLLATERAL_MGR_STAGING_JNDI = "SBCollateralBusManagerStagingHome";

	/** JNDIConstant for custodian doc entity bean. */
	public static final String EB_CUSTODIAN_DOC_HOME = "EBCustodianDocHome";

	public static final String EB_CUSTODIAN_DOC_LOCAL_HOME = "EBCustodianDocLocalHome";

	public static final String EB_CUSTODIAN_DOC_ITEM_HOME = "EBCustodianDocItemHome";

	/** Path to the custodian doc entity bean. */
	public static final String EB_CUSTODIAN_DOC_HOME_PATH = "com.integrosys.cms.app.custodian.bus.EBCustodianDocHome";

	/** JNDIConstant for staging custodian doc entity bean. */
	public static final String EB_STAGING_CUSTODIAN_DOC_HOME = "EBStagingCustodianDocHome";

	public static final String EB_STAGING_CUSTODIAN_DOC_LOCAL_HOME = "EBStagingCustodianDocLocalHome";

	public static final String EB_STAGING_CUSTODIAN_DOC_ITEM_HOME = "EBStagingCustodianDocItemHome";

	/** Path to the staging custodian doc entity bean. */
	public static final String EB_STAGING_CUSTODIAN_DOC_HOME_PATH = "com.integrosys.cms.app.custodian.bus.EBCustodianDocHome";

	/** JNDIConstant for custodian authz entity bean. */
	public static final String EB_CUSTODIAN_AUTHZ_HOME = "EBCustodianAuthzHome";

	/** Path to the custodian authz entity bean. */
	public static final String EB_CUSTODIAN_AUTHZ_HOME_PATH = "com.integrosys.cms.app.custodian.bus.EBCustodianAuthzHome";

	/** JNDIConstant for custodian bus session bean. */
	public static final String SB_CUSTODIAN_BUS_JNDI = "SBCustodianBusManagerHome";

	/** JNDIConstant for staging custodian bus session bean. */
	public static final String SB_STAGING_CUSTODIAN_BUS_JNDI = "SBStagingCustodianBusManagerHome";

	/** Path to the custodian bus session bean. */
	public static final String SB_CUSTODIAN_BUS_HOME_PATH = "com.integrosys.cms.app.custodian.bus.SBCustodianBusManagerHome";

	/** JNDIConstant for custodian proxy session bean. */
	public static final String SB_CUSTODIAN_PROXY_JNDI = "SBCustodianProxyManagerHome";

	/** JNDIConstant for Event Monitor Controller session bean. */
	public static final String SB_EVENT_MONITOR_CONTROLLER_JNDI = "SBEventMonitorControllerHome";

	/** JNDIConstant for Event Monitor Controller session bean. */
	public static final String SB_EVENT_MONITOR_FIRSTPASS_JNDI = "SBEventMonitorFirstPassHome";

	/** JNDIConstant for Batch Monitor Controller session bean. */
	public static final String SB_BATCH_MONITOR_CONTROLLER_JNDI = "SBBatchMonitorControllerHome";

	/** JNDIConstant for Event Monitor Controller session bean. */
	public static final String SB_EVENT_MANAGER_JNDI = "SBEventManagerHome";

	/** JNDIConstant for Event Monitor - Doc Expiry session bean. */
	public static final String SB_DOC_EXPIRY_JNDI = "SBDocExpiryHome";

	/** Path to the custodian proxy session bean. */
	public static final String SB_CUSTODIAN_PROXY_HOME_PATH = "com.integrosys.cms.app.custodian.proxy.SBCustodianProxyManagerHome";

	/** JNDIConstant for transaction value entity bean. */
	public static final String EB_CMS_TRX_VALUE_HOME = "EBCMSTrxValueHome";

	/** Path to the transaction value entity bean */
	public static final String EB_CMS_TRX_VALUE_HOME_PATH = "com.integrosys.cms.app.transaction.EBCMSTrxValueHome";

	/** JNDIConstant for transaction manager session bean. */
	public static final String SB_CMS_TRX_MGR_HOME = "SBCMSTrxManagerHome";

	/** Path to the transaction manager session bean */
	public static final String SB_CMS_TRX_MGR_HOME_PATH = "com.integrosys.cms.app.transaction.SBCMSTrxManagerHome";

	/** JNDIConstant for SBDataProtection proxy session bean. */
	public static final String SB_DATA_PROTECTION_HOME = "SBDataProtectionHome";

	/** Path to the SBDataProtection proxy home */
	public static final String SB_DATA_PROTECTION_HOME_PATH = "com.integrosys.cms.app.dataprotection.proxy.SBDataProtectionHome";

	/** JNDIConstant for SBCommonManager session bean. */
	public static final String SB_COMMON_MANAGER_JNDI = "SBCommonManagerHome";

	/** JNDIConstant for EBCollateralMetaData entity bean */
	public static final String EB_COLLATERAL_META_DATA_HOME = "EBCollateralMetaDataHome";

	/** Path to the EBCollateralMetaData entity bean */
	public static final String EB_COLLATERAL_META_DATA_HOME_PATH = "com.integrosys.cms.app.dataprotection.bus.EBCollateralMetaDataHome";

	/*********************** Customer JNDI Entries *********************/
	public static final String SB_CUSTOMER_PROXY_JNDI = "SBCustomerProxyHome";

	public static final String SB_CUSTOMER_MGR_JNDI = "SBCustomerManagerHome";

	public static final String SB_CUSTOMER_MGR_JNDI_STAGING = "SBCustomerManagerHomeStaging";

	public static final String EB_CUSTOMER_JNDI = "EBCMSCustomerHome";

	public static final String EB_CUSTOMER_JNDI_STAGING = "EBCMSCustomerHomeStaging";

	public static final String EB_CUSTOMER_LOCAL_JNDI = "EBCMSCustomerLocalHome";

	public static final String EB_CUSTOMER_LOCAL_JNDI_STAGING = "EBCMSCustomerLocalHomeStaging";

	public static final String EB_CUSTOMER_SYS_REF_JNDI = "EBCustomerSysXRefHome";

	public static final String EB_CUSTOMER_SYS_REF_JNDI_STAGING = "EBCustomerSysXRefHomeStaging";

	public static final String EB_CUSTOMER_SYS_REF_LOCAL_JNDI = "EBCustomerSysXRefLocalHome";

	public static final String EB_CUSTOMER_SYS_REF_LOCAL_JNDI_STAGING = "EBCustomerSysXRefLocalHomeStaging";
	
	//Covenant
	
	public static final String EB_LINE_COVENANT_JNDI = "EBLineCovenantHome";

	public static final String EB_LINE_COVENANT_JNDI_STAGING = "EBLineCovenantHomeStaging";

	public static final String EB_LINE_COVENANT_LOCAL_JNDI = "EBLineCovenantLocalHome";

	public static final String EB_LINE_COVENANT_LOCAL_JNDI_STAGING = "EBLineCovenantLocalHomeStaging";

	public static final String EB_LEGAL_ENTITY_JNDI = "EBCMSLegalEntityHome";

	public static final String EB_LEGAL_ENTITY_LOCAL_JNDI = "EBCMSLegalEntityLocalHome";

	public static final String EB_LEGAL_ENTITY_LOCAL_JNDI_STAGING = "EBCMSLegalEntityLocalHomeStaging";

	public static final String EB_REG_ADDRESS_LOCAL_JNDI = "EBRegAddressLocalHome";
	
	public static final String EB_SYSTEM_LOCAL_JNDI = "EBOtherSystemLocalHome";
	
	public static final String EB_LIEN_LOCAL_JNDI = "EBLienLocalHome";
	
	public static final String EB_DIRECTOR_LOCAL_JNDI = "EBDirectorLocalHome";
	
	public static final String EB_VENDOR_LOCAL_JNDI = "EBVendorDetailsLocalHome";
	
	public static final String EB_CO_BORROWER_DETAILS_JNDI = "EBCoBorrowerDetailsLocalHome";
	
	public static final String EB_FACILITY_CO_BORROWER_DETAILS_JNDI = "EBFacilityCoBorrowerDetailsLocalHome";
	
	public static final String EB_SUBLINE_LOCAL_JNDI = "EBSublineLocalHome";
	
	public static final String EB_BANKING_LOCAL_JNDI = "EBBankingMethodLocalHome";

	public static final String EB_REG_ADDRESS_LOCAL_JNDI_STAGING = "EBRegAddressLocalHomeStaging";
	
	public static final String EB_SYSTEM_LOCAL_JNDI_STAGING = "EBOtherSystemLocalHomeStaging";
	
	public static final String EB_LIEN_LOCAL_JNDI_STAGING = "EBLienLocalHomeStaging";
	
	public static final String EB_SYSTEM_DIRECTOR_JNDI_STAGING = "EBDirectorLocalHomeStaging";
	
	public static final String EB_VENDOR_JNDI_STAGING = "EBVendorDetailsLocalHomeStaging";
	
	public static final String EB_CO_BORROWER_DETAILS_STAGING_JNDI = "EBCoBorrowerDetailsStagingLocalHome";
	
	public static final String EB_FACILITY_CO_BORROWER_DETAILS_STAGING_JNDI = "EBFacilityCoBorrowerDetailsStagingLocalHome";

	public static final String EB_SUBLINE_LOCAL_JNDI_STAGING = "EBSublineLocalHomeStaging";
	
	public static final String EB_BANKING_LOCAL_JNDI_STAGING = "EBBankingMethodLocalHomeStaging";

	public static final String EB_OFF_ADDRESS_LOCAL_JNDI = "EBOffAddressLocalHome";

	public static final String EB_OFF_ADDRESS_LOCAL_JNDI_STAGING = "EBOffAddressLocalHomeStaging";

	public static final String EB_CREDIT_GRADE_LOCAL_JNDI = "EBCreditGradeLocalHome";

	public static final String EB_CREDIT_GRADE_LOCAL_JNDI_STAGING = "EBCreditGradeLocalHomeStaging";

	public static final String EB_ISIC_CODE_LOCAL_JNDI = "EBISICCodeLocalHome";

	public static final String EB_ISIC_CODE_LOCAL_JNDI_STAGING = "EBISICCodeLocalHomeStaging";

	public static final String EB_CREDIT_STATUS_LOCAL_JNDI = "EBCreditStatusLocalHome";

	public static final String EB_CREDIT_STATUS_LOCAL_JNDI_STAGING = "EBCreditStatusLocalHomeStaging";

	public static final String EB_KYC_LOCAL_JNDI = "EBKYCLocalHome";

	public static final String EB_KYC_LOCAL_JNDI_STAGING = "EBKYCLocalHomeStaging";

	/*********************** CheckList JNDO Entries *******************/
    public static final String SB_CHECKLIST_TEMPLATE_BUS_JNDI = "SBCheckListTemplateBusManagerHome";

    public static final String SB_STAGING_CHECKLIST_TEMPLATE_BUS_JNDI = "SBStagingCheckListTemplateBusManagerHome";

    public static final String SB_CHECKLIST_TEMPLATE_PROXY_JNDI = "SBCheckListTemplateProxyManagerHome";

    public static final String SB_RECURRENT_BUS_JNDI = "SBRecurrentBusManagerHome";

    public static final String SB_STAGING_RECURRENT_BUS_JNDI = "SBStagingRecurrentBusManagerHome";

    public static final String SB_RECURRENT_PROXY_JNDI = "SBRecurrentProxyManagerHome";

	public static final String SB_CHECKLIST_BUS_JNDI = "SBCheckListBusManagerHome";

	public static final String SB_STAGING_CHECKLIST_BUS_JNDI = "SBStagingCheckListBusManagerHome";

	public static final String SB_CHECKLIST_PROXY_JNDI = "SBCheckListProxyManagerHome";

    public static final String EB_DOCUMENT_JNDI = "EBDocumentHome";

	public static final String EB_RECURRING_DOCUMENT_JNDI = "EBRecurringDocumentHome";

	public static final String EB_DOCUMENT_ITEM_LOCAL_JNDI = "EBDocumentItemLocalHome";

	public static final String EB_DOCUMENT_ITEM_JNDI = "EBDocumentItemHome";

	public static final String EB_TEMPLATE_JNDI = "EBTemplateHome";

	public static final String EB_TEMPLATE_LOCAL_JNDI = "EBTemplateLocalHome";

	public static final String EB_TEMPLATE_ITEM_LOCAL_JNDI = "EBTemplateItemLocalHome";
	
	public static final String EB_DOC_APP_ITEM_LOCAL_JNDI = "EBDocAppItemLocalHome";
	
	public static final String EB_STAGING_DOC_APP_ITEM_LOCAL_JNDI = "EBStgDocAppItemLocalHome";
	
	public static final String EB_ITEM_LOCAL_JNDI = "EBItemLocalHome";

    public static final String EB_DYNAMIC_PROPERTY_LOCAL_JNDI = "EBDynamicPropertyLocalHome";

    public static final String EB_STAGING_DYNAMIC_PROPERTY_LOCAL_JNDI = "EBStagingDynamicPropertyLocalHome";

	public static final String EB_CHECKLIST_JNDI = "EBCheckListHome";

	public static final String EB_CHECKLIST_LOCAL_JNDI = "EBCheckListLocalHome";

	public static final String EB_CHECKLIST_ITEM_LOCAL_JNDI = "EBCheckListItemLocalHome";

	// start for cr-17
	public static final String EB_CHECKLIST_DOCUMENT_SHARE_JNDI = "EBDocumentshareHome";

	public static final String EB_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI = "EBDocumentshareLocalHome";

	public static final String EB_STAGING_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI = "EBStagingDocumentshareLocalHome";

	// End for cr-17
	public static final String EB_CHECKLIST_ITEM_JNDI = "EBCheckListItemHome";

	public static final String EB_RECURRENT_CHECKLIST_JNDI = "EBRecurrentCheckListHome";

	public static final String EB_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI = "EBRecurrentCheckListItemLocalHome";

	public static final String EB_CONVENANT_LOCAL_JNDI = "EBConvenantLocalHome";

	public static final String EB_CONVENANT_SUB_ITEM_LOCAL_JNDI = "EBConvenantSubItemLocalHome";// cr26

	public static final String EB_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI = "EBRecurrentCheckListSubItemLocalHome";

	public static final String EB_STAGING_DOCUMENT_ITEM_JNDI = "EBStagingDocumentItemHome";

	public static final String EB_STAGING_DOCUMENT_ITEM_LOCAL_JNDI = "EBStagingDocumentItemLocalHome";
	
	public static final String EB_STAGING_TEMPLATE_JNDI = "EBStagingTemplateHome";

	public static final String EB_STAGING_TEMPLATE_ITEM_LOCAL_JNDI = "EBStagingTemplateItemLocalHome";

	public static final String EB_STAGING_ITEM_LOCAL_JNDI = "EBItemLocalHome";

	public static final String EB_STAGING_CHECKLIST_JNDI = "EBStagingCheckListHome";

	public static final String EB_STAGING_CHECKLIST_ITEM_LOCAL_JNDI = "EBStagingCheckListItemLocalHome";

	public static final String EB_STAGING_RECURRENT_CHECKLIST_JNDI = "EBStagingRecurrentCheckListHome";

	public static final String EB_STAGING_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI = "EBStagingRecurrentCheckListItemLocalHome";

	public static final String EB_STAGING_CONVENANT_LOCAL_JNDI = "EBStagingConvenantLocalHome";

	public static final String EB_STAGING_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI = "EBStagingRecurrentCheckListSubItemLocalHome";

	public static final String EB_STAGING_CONVENANT_SUB_ITEM_LOCAL_JNDI = "EBStagingConvenantSubItemLocalHome";

	/*********************** Limit Profile and Limits JNDI Entries *********************/
	public static final String SB_LIMIT_PROXY_JNDI = "SBLimitProxyHome";

	public static final String SB_LIMIT_MGR_JNDI = "SBLimitManagerHome";

	public static final String SB_LIMIT_MGR_JNDI_STAGING = "SBLimitManagerHomeStaging";

	public static final String EB_LIMIT_PROFILE_JNDI = "EBLimitProfileHome";

	public static final String EB_LIMIT_PROFILE_JNDI_STAGING = "EBLimitProfileHomeStaging";

	public static final String EB_LIMIT_PROFILE_LOCAL_JNDI = "EBLimitProfileLocalHome";

	public static final String EB_LIMIT_PROFILE_LOCAL_JNDI_STAGING = "EBLimitProfileLocalHomeStaging";

	public static final String EB_LIMIT_JNDI = "EBLimitHome";

	public static final String EB_LIMIT_JNDI_STAGING = "EBLimitHomeStaging";

	public static final String EB_LIMIT_LOCAL_JNDI = "EBLimitLocalHome";

	public static final String EB_LIMIT_LOCAL_JNDI_STAGING = "EBLimitLocalHomeStaging";

	public static final String EB_COBORROWER_LIMIT_JNDI = "EBCoBorrowerLimitHome";

	public static final String EB_COBORROWER_LIMIT_JNDI_STAGING = "EBCoBorrowerLimitHomeStaging";

	public static final String EB_COBORROWER_LIMIT_LOCAL_JNDI = "EBCoBorrowerLimitLocalHome";

	public static final String EB_COBORROWER_LIMIT_LOCAL_JNDI_STAGING = "EBCoBorrowerLimitLocalHomeStaging";

	public static final String EB_COLLATERAL_ALLOCATION_LOCAL_JNDI = "EBCollateralAllocationLocalHome";

	public static final String EB_COLLATERAL_ALLOCATION_LOCAL_JNDI_STAGING = "EBCollateralAllocationLocalHomeStaging";

	public static final String EB_LIMIT_SYS_REF_LOCAL_JNDI = "EBLimitSysXRefLocalHome";

	public static final String EB_LIMIT_SYS_REF_LOCAL_JNDI_STAGING = "EBLimitSysXRefLocalHomeStaging";
	
	//Specific Covenant
	public static final String EB_LIMIT_COVENANT_LOCAL_JNDI = "EBLimitCovenantLocalHome";

	public static final String EB_LIMIT_COVENANT_LOCAL_JNDI_STAGING = "EBLimitCovenantLocalHomeStaging";
	
	public static final String EB_TAT_ENTRY_JNDI = "EBTATEntryHome";

	public static final String EB_TAT_ENTRY_LOCAL_JNDI = "EBTATEntryLocalHome";

	/************************* Feed JNDI entries ******************************/
	public static final String SB_FOREX_FEED_PROXY_JNDI = "SBForexFeedProxyHome";

	public static final String SB_BOND_FEED_PROXY_JNDI = "SBBondFeedProxyHome";

	public static final String SB_STOCK_FEED_PROXY_JNDI = "SBStockFeedProxyHome";

	public static final String SB_UNIT_TRUST_FEED_PROXY_JNDI = "SBUnitTrustFeedProxyHome";

	public static final String SB_STOCK_INDEX_FEED_PROXY_JNDI = "SBStockIndexFeedProxyHome";

	public static final String SB_PROPERTY_INDEX_FEED_PROXY_JNDI = "SBPropertyIndexFeedProxyHome";

	public static final String SB_FOREX_FEED_BUS_MANAGER_JNDI = "SBForexFeedBusManagerHome";

	public static final String SB_FOREX_FEED_BUS_MANAGER_JNDI_STAGING = "SBForexFeedBusManagerHomeStaging";

	public static final String SB_BOND_FEED_BUS_MANAGER_JNDI = "SBBondFeedBusManagerHome";

	public static final String SB_BOND_FEED_BUS_MANAGER_JNDI_STAGING = "SBBondFeedBusManagerHomeStaging";

	public static final String SB_STOCK_FEED_BUS_MANAGER_JNDI = "SBStockFeedBusManagerHome";

	public static final String SB_STOCK_FEED_BUS_MANAGER_JNDI_STAGING = "SBStockFeedBusManagerHomeStaging";

	public static final String SB_UNIT_TRUST_FEED_BUS_MANAGER_JNDI = "SBUnitTrustFeedBusManagerHome";

	public static final String SB_UNIT_TRUST_FEED_BUS_MANAGER_JNDI_STAGING = "SBUnitTrustFeedBusManagerHomeStaging";

	public static final String SB_STOCK_INDEX_FEED_BUS_MANAGER_JNDI = "SBStockIndexFeedBusManagerHome";

	public static final String SB_STOCK_INDEX_FEED_BUS_MANAGER_JNDI_STAGING = "SBStockIndexFeedBusManagerHomeStaging";

	public static final String SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI = "SBPropertyIndexFeedBusManagerHome";

	public static final String SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI_STAGING = "SBPropertyIndexFeedBusManagerHomeStaging";

	public static final String EB_BOND_FEED_ENTRY_JNDI = "EBBondFeedEntryHome";

	public static final String EB_BOND_FEED_GROUP_JNDI = "EBBondFeedGroupHome";

	public static final String EB_BOND_FEED_ENTRY_JNDI_STAGING = "EBBondFeedEntryHomeStaging";

	public static final String EB_BOND_FEED_GROUP_JNDI_STAGING = "EBBondFeedGroupHomeStaging";

	public static final String EB_BOND_FEED_ENTRY_JNDI_HISTORY = "EBBondFeedEntryHomeHistory";

	public static final String EB_BOND_FEED_ENTRY_LOCAL_JNDI = "EBBondFeedEntryLocalHome";

	public static final String EB_BOND_FEED_GROUP_LOCAL_JNDI = "EBBondFeedGroupLocalHome";

	public static final String EB_BOND_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBBondFeedEntryLocalHomeStaging";

	public static final String EB_BOND_FEED_GROUP_LOCAL_JNDI_STAGING = "EBBondFeedGroupLocalHomeStaging";

	public static final String EB_BOND_FEED_ENTRY_LOCAL_JNDI_HISTORY = "EBBondFeedEntryLocalHomeHistory";

	public static final String EB_FOREX_FEED_ENTRY_JNDI = "EBForexFeedEntryHome";

	public static final String EB_FOREX_FEED_GROUP_JNDI = "EBForexFeedGroupHome";

	public static final String EB_FOREX_FEED_ENTRY_JNDI_STAGING = "EBForexFeedEntryHomeStaging";

	public static final String EB_FOREX_FEED_GROUP_JNDI_STAGING = "EBForexFeedGroupHomeStaging";

	public static final String EB_FOREX_FEED_ENTRY_JNDI_HISTORY = "EBForexFeedEntryHomeHistory";

	public static final String EB_FOREX_FEED_ENTRY_LOCAL_JNDI = "EBForexFeedEntryLocalHome";

	public static final String EB_FOREX_FEED_GROUP_LOCAL_JNDI = "EBForexFeedGroupLocalHome";

	public static final String EB_FOREX_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBForexFeedEntryLocalHomeStaging";

	public static final String EB_FOREX_FEED_GROUP_LOCAL_JNDI_STAGING = "EBForexFeedGroupLocalHomeStaging";

	public static final String EB_FOREX_FEED_ENTRY_LOCAL_JNDI_HISTORY = "EBForexFeedEntryLocalHomeHistory";

	public static final String EB_STOCK_FEED_ENTRY_JNDI = "EBStockFeedEntryHome";

	public static final String EB_STOCK_FEED_GROUP_JNDI = "EBStockFeedGroupHome";

	public static final String EB_STOCK_FEED_ENTRY_JNDI_STAGING = "EBStockFeedEntryHomeStaging";

	public static final String EB_STOCK_FEED_GROUP_JNDI_STAGING = "EBStockFeedGroupHomeStaging";

	public static final String EB_STOCK_FEED_ENTRY_JNDI_HISTORY = "EBStockFeedEntryHomeHistory";

	public static final String EB_STOCK_FEED_ENTRY_LOCAL_JNDI = "EBStockFeedEntryLocalHome";

	public static final String EB_STOCK_FEED_GROUP_LOCAL_JNDI = "EBStockFeedGroupLocalHome";

	public static final String EB_STOCK_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBStockFeedEntryLocalHomeStaging";

	public static final String EB_STOCK_FEED_GROUP_LOCAL_JNDI_STAGING = "EBStockFeedGroupLocalHomeStaging";

	public static final String EB_STOCK_FEED_ENTRY_LOCAL_JNDI_HISTORY = "EBStockFeedEntryLocalHomeHistory";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_JNDI = "EBUnitTrustFeedEntryHome";

	public static final String EB_UNIT_TRUST_FEED_GROUP_JNDI = "EBUnitTrustFeedGroupHome";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_JNDI_STAGING = "EBUnitTrustFeedEntryHomeStaging";

	public static final String EB_UNIT_TRUST_FEED_GROUP_JNDI_STAGING = "EBUnitTrustFeedGroupHomeStaging";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_JNDI_HISTORY = "EBUnitTrustFeedEntryHomeHistory";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_LOCAL_JNDI = "EBUnitTrustFeedEntryLocalHome";

	public static final String EB_UNIT_TRUST_FEED_GROUP_LOCAL_JNDI = "EBUnitTrustFeedGroupLocalHome";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBUnitTrustFeedEntryLocalHomeStaging";

	public static final String EB_UNIT_TRUST_FEED_GROUP_LOCAL_JNDI_STAGING = "EBUnitTrustFeedGroupLocalHomeStaging";

	public static final String EB_UNIT_TRUST_FEED_ENTRY_LOCAL_JNDI_HISTORY = "EBUnitTrustFeedEntryLocalHomeHistory";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_JNDI = "EBStockIndexFeedEntryHome";

	public static final String EB_STOCK_INDEX_FEED_GROUP_JNDI = "EBStockIndexFeedGroupHome";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_JNDI_STAGING = "EBStockIndexFeedEntryHomeStaging";

	public static final String EB_STOCK_INDEX_FEED_GROUP_JNDI_STAGING = "EBStockIndexFeedGroupHomeStaging";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_JNDI_HISTORY = "EBStockIndexFeedEntryHomeHistory";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_LOCAL_JNDI = "EBStockIndexFeedEntryLocalHome";

	public static final String EB_STOCK_INDEX_FEED_GROUP_LOCAL_JNDI = "EBStockIndexFeedGroupLocalHome";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBStockIndexFeedEntryLocalHomeStaging";

	public static final String EB_STOCK_INDEX_FEED_GROUP_LOCAL_JNDI_STAGING = "EBStockIndexFeedGroupLocalHomeStaging";

	public static final String EB_STOCK_INDEX_FEED_ENTRY_LOCAL_JNDI_HISTORY = "EBStockIndexFeedEntryLocalHomeHistory";

	public static final String EB_PROPERTY_INDEX_FEED_ENTRY_JNDI = "EBPropertyIndexFeedEntryHome";

	public static final String EB_PROPERTY_INDEX_FEED_GROUP_JNDI = "EBPropertyIndexFeedGroupHome";

	public static final String EB_PROPERTY_INDEX_FEED_ENTRY_JNDI_STAGING = "EBPropertyIndexFeedEntryHomeStaging";

	public static final String EB_PROPERTY_INDEX_FEED_GROUP_JNDI_STAGING = "EBPropertyIndexFeedGroupHomeStaging";

	public static final String EB_PROPERTY_INDEX_FEED_ENTRY_LOCAL_JNDI = "EBPropertyIndexFeedEntryLocalHome";

	public static final String EB_PROPERTY_INDEX_FEED_GROUP_LOCAL_JNDI = "EBPropertyIndexFeedGroupLocalHome";

	public static final String EB_PROPERTY_INDEX_FEED_ENTRY_LOCAL_JNDI_STAGING = "EBPropertyIndexFeedEntryLocalHomeStaging";

	public static final String EB_PROPERTY_INDEX_FEED_GROUP_LOCAL_JNDI_STAGING = "EBPropertyIndexFeedGroupLocalHomeStaging";

	/*********************** FOREX JNDI ENTRIES ********************************/
	public static final String SB_FOREX_MANAGER_JNDI = "SBForexManagerHome";

	/*********************** CC Certificate JNDI Entries *******************/
	public static final String SB_CCCERTIFICATE_PROXY_JNDI = "SBCCCertificateProxyManagerHome";

	public static final String SB_CCCERTIFICATE_BUS_JNDI = "SBCCCertificateBusManagerHome";

	public static final String SB_STAGING_CCCERTIFICATE_BUS_JNDI = "SBStagingCCCertificateBusManagerHome";

	public static final String EB_CCCERTIFICATE_JNDI = "EBCCCertificateHome";

	public static final String EB_CCCERTIFICATE_LOCAL_JNDI = "EBCCCertificateLocalHome";

	public static final String EB_STAGING_CCCERTIFICATE_JNDI = "EBStagingCCCertificateHome";

	public static final String EB_CCC_ITEM_LOCAL_JNDI = "EBCCCertificateItemLocalHome";

	public static final String EB_STAGING_CCC_ITEM_LOCAL_JNDI = "EBStagingCCCertificateItemLocalHome";

	/*********************** SC Certificate JNDI Entries *******************/
	public static final String SB_SCCERTIFICATE_PROXY_JNDI = "SBSCCertificateProxyManagerHome";

	public static final String SB_SCCERTIFICATE_BUS_JNDI = "SBSCCertificateBusManagerHome";

	public static final String SB_STAGING_SCCERTIFICATE_BUS_JNDI = "SBStagingSCCertificateBusManagerHome";

	public static final String EB_SCCERTIFICATE_JNDI = "EBSCCertificateHome";

	public static final String EB_STAGING_SCCERTIFICATE_JNDI = "EBStagingSCCertificateHome";

	public static final String EB_SCC_ITEM_LOCAL_JNDI = "EBSCCertificateItemLocalHome";

	public static final String EB_STAGING_SCC_ITEM_LOCAL_JNDI = "EBStagingSCCertificateItemLocalHome";

	/*********************** Partial SC Certificate JNDI Entries *******************/
	public static final String EB_PSCCERTIFICATE_JNDI = "EBPartialSCCertificateHome";

	public static final String EB_STAGING_PSCCERTIFICATE_JNDI = "EBStagingPartialSCCertificateHome";

	public static final String EB_PSCC_ITEM_LOCAL_JNDI = "EBPartialSCCertificateItemLocalHome";

	public static final String EB_STAGING_PSCC_ITEM_LOCAL_JNDI = "EBStagingPartialSCCertificateItemLocalHome";

	/*********************** DDN JNDI Entries **********************************/
	public static final String SB_DDN_PROXY_JNDI = "SBDDNProxyManagerHome";

	public static final String SB_DDN_BUS_JNDI = "SBDDNBusManagerHome";

	public static final String SB_STAGING_DDN_BUS_JNDI = "SBStagingDDNBusManagerHome";

	public static final String EB_DDN_JNDI = "EBDDNHome";

	public static final String EB_STAGING_DDN_JNDI = "EBStagingDDNHome";

	public static final String EB_DDN_ITEM_LOCAL_JNDI = "EBDDNItemLocalHome";

	public static final String EB_STAGING_DDN_ITEM_LOCAL_JNDI = "EBStagingDDNItemLocalHome";

	/************************ COLLABORATION TASK ************************************/
	public static final String SB_COLLABORATION_PROXY_JNDI = "SBCollaborationTaskProxyManagerHome";

	public static final String SB_COLLABORATION_BUS_JNDI = "SBCollaborationTaskBusManagerHome";

	public static final String SB_STAGING_COLLABORATION_BUS_JNDI = "SBStagingCollaborationTaskBusManagerHome";

	public static final String EB_COLLATERAL_TASK_JNDI = "EBCollateralTaskHome";

	public static final String EB_STAGING_COLLATERAL_TASK_JNDI = "EBStagingCollateralTaskHome";

	public static final String EB_CC_TASK_JNDI = "EBCCTaskHome";

	public static final String EB_STAGING_CC_TASK_JNDI = "EBStagingCCTaskHome";

	/************************ GENERATE REQUEST *******************************/
	public static final String SB_GENERATE_REQUEST_PROXY_JNDI = "SBGenerateRequestProxyManagerHome";

	public static final String SB_GENERATE_REQUEST_BUS_JNDI = "SBGenerateRequestBusManagerHome";

	public static final String SB_STAGING_GENERATE_REQUEST_BUS_JNDI = "SBStagingGenerateRequestBusManagerHome";

	public static final String EB_WAIVER_REQUEST_JNDI = "EBWaiverRequestHome";

	public static final String EB_WAIVER_REQUEST_ITEM_LOCAL_JNDI = "EBWaiverRequestItemLocalHome";

	public static final String EB_STAGING_WAIVER_REQUEST_JNDI = "EBStagingWaiverRequestHome";

	public static final String EB_STAGING_WAIVER_REQUEST_ITEM_LOCAL_JNDI = "EBStagingWaiverRequestItemLocalHome";

	public static final String EB_DEFERRAL_REQUEST_JNDI = "EBDeferralRequestHome";

	public static final String EB_DEFERRAL_REQUEST_ITEM_LOCAL_JNDI = "EBDeferralRequestItemLocalHome";

	public static final String EB_STAGING_DEFERRAL_REQUEST_JNDI = "EBStagingDeferralRequestHome";

	public static final String EB_STAGING_DEFERRAL_REQUEST_ITEM_LOCAL_JNDI = "EBStagingDeferralRequestItemLocalHome";

	/********** report request ***************/
	public static final String SB_REPORT_REQUEST_JNDI = "SBReportRequestManagerHome";

	public static final String EB_REPORT_REQUEST_LOCAL_JNDI = "EBReportRequestLocalHome";

	/********************** Function Access Profile *****************************/
	public static final String SB_ACCESS_PROFILE_JNDI = "SBAccessProfileBusManagerHome";

	/********************** CC Documentation Location *****************************/
	public static final String SB_DOC_LOCATION_PROXY_JNDI = "SBDocumentLocationProxyManagerHome";

	public static final String SB_DOC_LOCATION_BUS_JNDI = "SBDocumentLocationBusManagerHome";

	public static final String SB_STAGING_DOC_LOCATION_BUS_JNDI = "SBStagingDocumentLocationBusManagerHome";

	public static final String EB_CC_DOC_LOCATION_JNDI = "EBCCDocumentLocationHome";

	public static final String EB_STAGING_CC_DOC_LOCATION_JNDI = "EBStagingCCDocumentLocationHome";

	/*************** JNDIConstant for commodity deal. */
	public static final String SB_COMMODITY_DEAL_PROXY_JNDI = "SBCommodityDealProxyHome";

	public static final String SB_COMMODITY_DEAL_MGR_JNDI = "SBCommodityDealBusManagerHome";

	public static final String SB_COMMODITY_DEAL_MGR_STAGING_JNDI = "SBCommodityDealBusManagerStagingHome";

	public static final String EB_COMMODITY_DEAL_LOCAL_JNDI = "EBCommodityDealLocalHome";

	public static final String EB_COMMODITY_DEAL_STAGING_LOCAL_JNDI = "EBCommodityDealStagingLocalHome";

	public static final String EB_COMMODITY_TITLE_DOC_LOCAL_JNDI = "EBCommodityTitleDocumentLocalHome";

	public static final String EB_COMMODITY_TITLE_DOC_STAGING_LOCAL_JNDI = "EBCommodityTitleDocumentStagingLocalHome";

	public static final String EB_FINANCING_DOC_LOCAL_JNDI = "EBFinancingDocLocalHome";

	public static final String EB_FINANCING_DOC_STAGING_LOCAL_JNDI = "EBFinancingDocStagingLocalHome";

	public static final String EB_WAREHOUSE_RECEIPT_LOCAL_JNDI = "EBWarehouseReceiptLocalHome";

	public static final String EB_WAREHOUSE_RECEIPT_STAGING_LOCAL_JNDI = "EBWarehouseReceiptStagingLocalHome";

	public static final String EB_SETTLE_WAREHOUSE_LOCAL_JNDI = "EBSettleWarehouseReceiptLocalHome";

	public static final String EB_SETTLE_WAREHOUSE_STAGING_LOCAL_JNDI = "EBSettleWarehouseReceiptStagingLocalHome";

	public static final String EB_SETTLEMENT_LOCAL_JNDI = "EBSettlementLocalHome";

	public static final String EB_SETTLEMENT_STAGING_LOCAL_JNDI = "EBSettlementStagingLocalHome";

	public static final String EB_RECEIPT_RELEASE_LOCAL_JNDI = "EBReceiptReleaseLocalHome";

	public static final String EB_RECEIPT_RELEASE_STAGING_LOCAL_JNDI = "EBReceiptReleaseStagingLocalHome";

	/********************** Commodity UOM JNDI Entries *****************************/
	public static final String EB_COMMODITY_UOM_LOCAL_JNDI = "EBUnitofMeasureLocalHome";

	public static final String EB_COMMODITY_UOM_STAGING_LOCAL_JNDI = "EBUnitofMeasureStagingLocalHome";

	public static final String EB_CONTRACT_LOCAL_JNDI = "EBContractLocalHome";

	public static final String EB_CONTRACT_STAGING_LOCAL_JNDI = "EBContractStagingLocalHome";

	public static final String EB_APPROVED_COMMODITY_TYPE_LOCAL_JNDI = "EBApprovedCommodityTypeLocalHome";

	public static final String EB_APPROVED_COMMODITY_TYPE_STAGING_LOCAL_JNDI = "EBApprovedCommodityTypeStagingLocalHome";

	public static final String EB_HEDGING_CONTRACT_INFO_LOCAL_JNDI = "EBHedgingContractInfoLocalHome";

	public static final String EB_HEDGING_CONTRACT_INFO_STAGING_LOCAL_JNDI = "EBHedgingContractInfoStagingLocalHome";

	public static final String EB_LIMIT_DETAILS_LOCAL_JNDI = "EBLimitDetailsLocalHome";

	public static final String EB_LIMIT_DETAILS_STAGING_LOCAL_JNDI = "EBLimitDetailsStagingLocalHome";

	public static final String EB_LOAN_AGENCY_LOCAL_JNDI = "EBLoanAgencyLocalHome";

	public static final String EB_LOAN_AGENCY_STAGING_LOCAL_JNDI = "EBLoanAgencyStagingLocalHome";

	public static final String EB_PRECONDITION_LOCAL_JNDI = "EBPreConditionLocalHome";

	public static final String EB_PRECONDITION_STAGING_LOCAL_JNDI = "EBPreConditionStagingLocalHome";

	public static final String EB_BORROWER_LOCAL_JNDI = "EBBorrowerLocalHome";

	public static final String EB_BORROWER_STAGING_LOCAL_JNDI = "EBBorrowerStagingLocalHome";

	public static final String EB_GUARANTOR_LOCAL_JNDI = "EBGuarantorLocalHome";

	public static final String EB_GUARANTOR_STAGING_LOCAL_JNDI = "EBGuarantorStagingLocalHome";

	public static final String EB_SUBLIMIT_LOCAL_JNDI = "EBSubLimitLocalHome";

	public static final String EB_SUBLIMIT_STAGING_LOCAL_JNDI = "EBSubLimitStagingLocalHome";

	public static final String EB_LOANSCHEDULE_LOCAL_JNDI = "EBLoanScheduleLocalHome";

	public static final String EB_LOANSCHEDULE_STAGING_LOCAL_JNDI = "EBLoanScheduleStagingLocalHome";

	public static final String EB_PARTICIPANT_LOCAL_JNDI = "EBParticipantLocalHome";

	public static final String EB_PARTICIPANT_STAGING_LOCAL_JNDI = "EBParticipantStagingLocalHome";

	public static final String EB_LOAN_LIMIT_LOCAL_JNDI = "EBLoanLimitLocalHome";

	public static final String EB_LOAN_LIMIT_STAGING_LOCAL_JNDI = "EBLoanLimitStagingLocalHome";

	public static final String EB_DEAL_CASH_DEPOSIT_LOCAL_JNDI = "EBDealCashDepositLocalHome";

	public static final String EB_DEAL_CASH_DEPOSIT_STAGING_LOCAL_JNDI = "EBDealCashDepositStagingLocalHome";

	public static final String EB_PURCHASE_SALES_LOCAL_JNDI = "EBPurchaseAndSalesDetailsLocalHome";

	public static final String EB_PURCHASE_SALES_STAGING_LOCAL_JNDI = "EBPurchaseAndSalesDetailsStagingLocalHome";

	public static final String EB_HEDGE_PRICE_EXTENSION_LOCAL_JNDI = "EBHedgePriceExtensionLocalHome";

	public static final String EB_HEDGE_PRICE_EXTENSION_STAGING_LOCAL_JNDI = "EBHedgePriceExtensionStagingLocalHome";

	/********************** Diary Item *****************************/
	public static final String SB_DIARY_ITEM_PROXY_JNDI = "SBDiaryItemProxyManagerHome";

	public static final String SB_DIARY_ITEM_BUS_JNDI = "SBDiaryItemBusManagerHome";

	public static final String EB_DIARY_ITEM_JNDI = "EBDiaryItemHome";

	public static final String EB_DIARY_ITEM_LOCAL_JNDI = "EBDiaryItemLocalHome";

	// <!-- Begin NoCollateral -->
	// public static final String EB_NO_COLLATERAL_JNDI="EBNoCollateralHome";
	// public static final String
	// EB_NO_COLLATERAL_STAGING_JNDI="EBNoCollateralHomeStagingHome";

	/** CR CMS-571**Starts *****************/
	/** OTHER Collateral of type Othera. */
	public static final String EB_OTHERS_COLLATERAL_JNDI = "EBOthersCollateralHome";

	public static final String EB_OTHERS_COLLATERAL_STAGING_JNDI = "EBOthersCollateralStagingHome";

	/** CR CMS-571**Starts *****************/

	/********************** CMS Notification ***********************/
	public static final String SB_CMS_NOTIFICATION_PROXY_JNDI = "SBCMSNotificationProxyManagerHome";

	public static final String EB_CMS_NOTIFICATION_LOCAL_JNDI = "EBCMSNotificationLocalBean";

	/** JNDIConstant for collateral ASSETLIFE to its local home. */
	public static final String EB_COL_ASSETLIFE_LOCAL_JNDI = "EBCollateralAssetLifeLocalHome";

	/** JNDIConstant for collateral ASSETLIFE to its remote home. */
	public static final String EB_COL_ASSETLIFE_JNDI = "EBCollateralAssetLifeHome";

	public static final String EB_COL_ASSETLIFE_STAGING_JNDI = "EBCollateralAssetLifeStagingHome";

	/********************** CMS Common Code Type ***********************/
	public static final String SB_COMMON_CODE_TYPE_PROXY_JNDI = "SBCommonCodeTypeProxyManagerHome";

	public static final String EB_COMMON_CODE_TYPE_JNDI = "EBCommonCodeTypeHome";

	public static final String EB_STAGING_COMMON_CODE_TYPE_JNDI = "EBStagingCommonCodeTypeHome";

	public static final String SB_COMMON_CODE_TYPE_BUS_JNDI = "SBCommonCodeTypeBusManagerHome";

	public static final String SB_STAGING_COMMON_CODE_TYPE_BUS_JNDI = "SBStagingCommonCodeTypeBusManagerHome";

	// public static final String EB_COMMON_CODE_TYPE_LOCAL_JNDI =
	// "EBCommonCodeTypeLocalHome";

	/** EJB Constants for Credit Risk Parameters */
	public static final String SB_CREDIT_RISK_PARAM_PROXY_MANAGER_HOME = "SBCreditRiskParamProxyManagerHome";

	public static final String SB_CREDIT_RISK_PARAM_BUS_STAGING_MANAGER_HOME = "SBCreditRiskParamBusManagerHomeStaging";

	/** EJB Constants for common code entries */

	public static final String SB_COMMON_CODE_ENTRIES_JNDI = "SBCommonCodeEntriesProxyManagerHome";

	public static final String EB_COMMON_CODE_ENTRY_HOME = "EBCommonCodeEntryHome"; // EBCommonCodeEntryHome
																					// home

	public static final String EB_COMMON_CODE_ENTRY_STAGE_HOME = "EBCommonCodeEntryStageHome"; // EBCommonCodeEntryStageHome
																								// home

	public static final String SB_COMMON_CODE_MANAGER_ENTRY_HOME = "SBCommonCodeEntryManagerHome"; // SBCommonCodeEntryManagerHome
																									// home

	/** JNDIConstant for interest rate proxy session bean. */
	public static final String SB_INT_RATE_PROXY_JNDI = "SBInterestRateProxyHome";

	/** JNDIConstant for interest rate business manager session bean. */
	public static final String SB_INT_RATE_MGR_JNDI = "SBInterestRateBusManagerHome";

	/** JNDIConstant for staging interest rate business manager session bean. */
	public static final String SB_INT_RATE_MGR_STAGING_JNDI = "SBInterestRateBusManagerStagingHome";

	/** JNDIConstant for interest rate to its local home. */
	public static final String EB_INT_RATE_LOCAL_JNDI = "EBInterestRateLocalHome";

	/** JNDIConstant for interest rate to its remote home. */
	public static final String EB_INT_RATE_JNDI = "EBInterestRateHome";

	/** JNDIConstant for staging interest rate to its remote home. */
	public static final String EB_INT_RATE_STAGING_JNDI = "EBInterestRateStagingHome";

	/********************** Manual Input ****************************************/
	public static final String SB_MI_LMT_PROXY_JNDI = "SBMILmtProxyHome";

	public static final String SB_MI_SEC_PROXY_JNDI = "SBMISecProxyHome";

	public static final String SB_MI_AA_PROXY_JNDI = "SBMIAAProxyHome";

	/********************** Credit Risk Parameter Module ***********************/
	public static final String SB_POLICY_CAP_PROXY_JNDI = "SBPolicyCapProxyManagerHome";

	public static final String SB_POLICY_CAP_BUS_JNDI = "SBPolicyCapBusManagerHome";

	public static final String SB_STAGING_POLICY_CAP_BUS_JNDI = "SBStagingPolicyCapBusManagerHome";

	// public static final String SB_STAGING_POLICY_CAP_BUS_JNDI =
	// "SBPolicyCapBusManagerHome";

	public static final String EB_POLICY_CAP_GROUP_JNDI = "EBPolicyCapGroupHome";

	public static final String EB_STAGING_POLICY_CAP_GROUP_JNDI = "EBStagingPolicyCapGroupHome";

	public static final String EB_POLICY_CAP_GROUP_LOCAL_JNDI = "EBPolicyCapGroupLocalHome";

	public static final String EB_STAGING_POLICY_CAP_GROUP_LOCAL_JNDI = "EBStagingPolicyCapGroupLocalHome";

	public static final String EB_POLICY_CAP_JNDI = "EBPolicyCapHome";

	public static final String EB_STAGING_POLICY_CAP_JNDI = "EBStagingPolicyCapHome";

	public static final String EB_POLICY_CAP_LOCAL_JNDI = "EBPolicyCapLocalHome";

	public static final String EB_STAGING_POLICY_CAP_LOCAL_JNDI = "EBStagingPolicyCapLocalHome";

	/*********************** Contract Financing JNDI Entries **********************************/
	public static final String SB_CONTRACT_FINANCING_PROXY_JNDI = "SBContractFinancingProxyManagerHome";

	public static final String SB_CONTRACT_FINANCING_BUS_JNDI = "SBContractFinancingBusManagerHome";

	public static final String SB_STAGING_CONTRACT_FINANCING_BUS_JNDI = "SBStagingContractFinancingBusManagerHome";

	public static final String EB_CONTRACT_FINANCING_JNDI = "EBContractFinancingHome";

	public static final String EB_STAGING_CONTRACT_FINANCING_JNDI = "EBStagingContractFinancingHome";

	public static final String EB_CF_PAYMENT_LOCAL_JNDI = "EBPaymentLocalHome";

	public static final String EB_CF_FACILITY_TYPE_LOCAL_JNDI = "EBContractFacilityTypeLocalHome";

	public static final String EB_CF_ADVANCE_LOCAL_JNDI = "EBAdvanceLocalHome";

	public static final String EB_CF_TNC_LOCAL_JNDI = "EBTNCLocalHome";

	public static final String EB_CF_FDR_LOCAL_JNDI = "EBFDRLocalHome";

	public static final String EB_STAGING_CF_PAYMENT_LOCAL_JNDI = "EBStagingPaymentLocalHome";

	public static final String EB_STAGING_CF_FACILITY_TYPE_LOCAL_JNDI = "EBStagingContractFacilityTypeLocalHome";

	public static final String EB_STAGING_CF_ADVANCE_LOCAL_JNDI = "EBStagingAdvanceLocalHome";

	public static final String EB_STAGING_CF_TNC_LOCAL_JNDI = "EBStagingTNCLocalHome";

	public static final String EB_STAGING_CF_FDR_LOCAL_JNDI = "EBStagingFDRLocalHome";

	// public static final String EB_STAGING_DDN_ITEM_LOCAL_JNDI =
	// "EBStagingDDNItemLocalHome";

	public static final String EB_SHARE_SECURITY_LOCAL_JNDI = "EBShareSecurityLocalHome";

	public static final String SB_SHARE_SECURITY_MGR_JNDI = "SBShareSecurityManagerHome";

	/*********************** Bridging Loan JNDI Entries **********************************/
	public static final String SB_BRIDGING_LOAN_PROXY_JNDI = "SBBridgingLoanProxyManagerHome";

	public static final String SB_BRIDGING_LOAN_BUS_JNDI = "SBBridgingLoanBusManagerHome";

	public static final String SB_STAGING_BRIDGING_LOAN_BUS_JNDI = "SBStagingBridgingLoanBusManagerHome";

	public static final String EB_BRIDGING_LOAN_JNDI = "EBBridgingLoanHome";

	public static final String EB_BL_PROPERTY_TYPE_LOCAL_JNDI = "EBBLPropertyTypeLocalHome";

	public static final String EB_BL_PROJECT_SCHEDULE_LOCAL_JNDI = "EBBLProjectScheduleLocalHome";

	public static final String EB_BL_DEV_DOC_LOCAL_JNDI = "EBBLDevDocLocalHome";

	public static final String EB_BL_DISBURSEMENT_LOCAL_JNDI = "EBBLDisbursementLocalHome";

	public static final String EB_BL_DISBURSE_DETAIL_LOCAL_JNDI = "EBBLDisbursementDetailLocalHome";

	public static final String EB_BL_SETTLEMENT_LOCAL_JNDI = "EBBLSettlementLocalHome";

	public static final String EB_BL_BUILDUP_LOCAL_JNDI = "EBBLBuildUpLocalHome";

	public static final String EB_BL_SALES_PROCEEDS_LOCAL_JDNI = "EBBLSalesProceedsLocalHome";

	public static final String EB_BL_FDR_LOCAL_JNDI = "EBBLFDRLocalHome";

	public static final String EB_STAGING_BRIDGING_LOAN_JNDI = "EBStagingBridgingLoanHome";

	public static final String EB_STAGING_BL_PROPERTY_TYPE_LOCAL_JNDI = "EBBLStagingPropertyTypeLocalHome";

	public static final String EB_STAGING_BL_PROJECT_SCHEDULE_LOCAL_JNDI = "EBBLStagingProjectScheduleLocalHome";

	public static final String EB_STAGING_BL_DEV_DOC_LOCAL_JNDI = "EBBLStagingDevDocLocalHome";

	public static final String EB_STAGING_BL_DISBURSEMENT_LOCAL_JNDI = "EBBLStagingDisbursementLocalHome";

	public static final String EB_STAGING_BL_DISBURSE_DETAIL_LOCAL_JNDI = "EBBLStagingDisbursementDetailLocalHome";

	public static final String EB_STAGING_BL_SETTLEMENT_LOCAL_JNDI = "EBBLStagingSettlementLocalHome";

	public static final String EB_STAGING_BL_BUILDUP_LOCAL_JNDI = "EBBLStagingBuildUpLocalHome";

	public static final String EB_STAGING_BL_SALES_PROCEEDS_LOCAL_JNDI = "EBBLStagingSalesProceedsLocalHome";

	public static final String EB_STAGING_BL_FDR_LOCAL_JNDI = "EBBLStagingFDRLocalHome";

	/************************* Pre Deal ejb **********************************************/
	public static final String EB_PRE_DEAL_JNDI = "EBPreDealHome";

	public static final String EB_STAGING_PRE_DEAL_JNDI = "EBStagingPreDealHome";

	public static final String SB_PRE_DEAL_PROXY_JNDI = "SBPreDealProxyManagerHome";

	public static final String SB_PRE_DEAL_BUS_JNDI = "SBPreDealBusManagerHome";

	public static final String SB_STAGING_PRE_DEAL_BUS_JNDI = "SBStagingPreDealBusManagerHome";

	public static final String EB_EAR_MARK_JNDI = "EBEarMarkHome";

	public static final String EB_STAGING_EAR_MARK_JNDI = "EBStagingEarMarkHome";

	public static final String EB_EAR_MARK_GROUP_JNDI = "EBEarMarkGroupHome";

	/*********************** Trading Book JNDI Constants **********************************/

	public static final String SB_TRADING_BOOK_PROXY_JNDI = "SBTradingBookProxyHome";

	public static final String SB_TRADING_BOOK_MGR_JNDI = "SBTradingBookBusManagerHome";

	public static final String SB_TRADING_BOOK_MGR_STAGING_JNDI = "SBTradingBookBusManagerStagingHome";

	public static final String EB_TRADING_AGREEMENT_LOCAL_JNDI = "EBTradingAgreementLocalHome";

	public static final String EB_THRESHOLD_RATING_LOCAL_JNDI = "EBThresholdRatingLocalHome";

	public static final String EB_TRADING_AGREEMENT_LOCAL_STAGING_JNDI = "EBTradingAgreementLocalHomeStaging";

	public static final String EB_THRESHOLD_RATING_LOCAL_STAGING_JNDI = "EBThresholdRatingLocalHomeStaging";

	public static final String EB_ISDA_CSA_DEAL_LOCAL_JNDI = "EBISDACSADealLocalHome";

	public static final String EB_ISDA_CSA_DEAL_JNDI = "EBISDACSADealHome";

	public static final String EB_ISDA_CSA_DEAL_STAGING_JNDI = "EBISDACSADealStagingHome";

	public static final String EB_GMRA_DEAL_LOCAL_JNDI = "EBGMRADealLocalHome";

	public static final String EB_GMRA_DEAL_JNDI = "EBGMRADealHome";

	public static final String EB_GMRA_DEAL_STAGING_JNDI = "EBGMRADealStagingHome";

	public static final String EB_DEAL_VALUATION_LOCAL_JNDI = "EBDealValuationLocalHome";

	public static final String EB_DEAL_VALUATION_JNDI = "EBDealValuationHome";

	public static final String EB_DEAL_VALUATION_STAGING_JNDI = "EBDealValuationStagingHome";

	public static final String EB_CASH_MARGIN_LOCAL_JNDI = "EBCashMarginLocalHome";

	public static final String EB_CASH_MARGIN_JNDI = "EBCashMarginHome";

	public static final String EB_CASH_MARGIN_STAGING_JNDI = "EBCashMarginStagingHome";

	public static final String EB_INSTRUMENT_JNDI = "EBInstrumentLocalHome";

	public static final String EB_INSTRUMENT_STAGE_JNDI = "EBInstrumentStageLocalHome";

	/** JNDIConstant for Liquidation bean. */
	public static final String SB_LIQUIDATION_PROXY_JNDI = "SBLiquidationProxyHome";

	public static final String SB_LIQUIDATION_MGR_JNDI = "SBLiquidationBusManagerHome";

	public static final String SB_LIQUIDATION_MGR_STAGING_JNDI = "SBLiquidationBusManagerStagingHome";

	public static final String EB_LIQUIDATION_LOCAL_JNDI = "EBLiquidationLocalHome";

	public static final String EB_LIQUIDATION_JNDI = "EBLiquidationHome";

	public static final String EB_LIQUIDATION_STAGING_JNDI = "EBLiquidationStagingHome";

	public static final String EB_NPL_INFO_STAGING_LOCAL_JNDI = "EBNPLInfoStagingLocalHome";

	public static final String EB_NPL_INFO_STAGING_JNDI = "EBNPLInfoStagingHome";

	public static final String EB_NPL_INFO_JNDI = "EBNPLInfoHome";

	public static final String EB_NPL_INFO_LOCAL_JNDI = "EBNPLInfoLocalHome";

	public static final String EB_RECOVERY_EXPENSE_STAGING_LOCAL_JNDI = "EBRecoveryExpenseStagingLocalHome";

	public static final String EB_RECOVERY_EXPENSE_STAGING_JNDI = "EBRecoveryExpenseStagingHome";

	public static final String EB_RECOVERY_EXPENSE_JNDI = "EBRecoveryExpenseHome";

	public static final String EB_RECOVERY_EXPENSE_LOCAL_JNDI = "EBRecoveryExpenseLocalHome";

	public static final String EB_RECOVERY_INCOME_STAGING_JNDI = "EBRecoveryIncomeStagingHome";

	public static final String EB_RECOVERY_INCOME_JNDI = "EBRecoveryIncomeHome";

	public static final String EB_RECOVERY_INCOME_LOCAL_JNDI = "EBRecoveryIncomeLocalHome";

	public static final String EB_RECOVERY_INCOME_STAGING_LOCAL_JNDI = "EBRecoveryIncomeStagingLocalHome";

	public static final String EB_RECOVERY_STAGING_LOCAL_JNDI = "EBRecoveryStagingLocalHome";

	public static final String EB_RECOVERY_STAGING_JNDI = "EBRecoveryStagingHome";

	public static final String EB_RECOVERY_JNDI = "EBRecoveryHome";

	public static final String EB_RECOVERY_LOCAL_JNDI = "EBRecoveryLocalHome";

	// ************* JNDIConstant for MF template *********************
	public static final String EB_MF_TEMPLATE_JNDI = "EBMFTemplateHome";

	public static final String EB_MF_TEMPLATE_STAGING_JNDI = "EBMFTemplateStagingHome";

	public static final String EB_MF_TEMPLATE_SEC_SUBTYPE_LOCAL_JNDI = "EBMFTemplateSecSubTypeLocalHome";

	public static final String EB_MF_TEMPLATE_SEC_SUBTYPE_LOCAL_STAGING_JNDI = "EBMFTemplateSecSubTypeLocalHomeStaging";

	public static final String EB_MF_ITEM_LOCAL_JNDI = "EBMFItemLocalHome";

	public static final String EB_MF_ITEM_LOCAL_STAGING_JNDI = "EBMFItemLocalHomeStaging";

	public static final String SB_PROPERTY_PARAMETERS_HOME = "SBPropertyParametersHome";

	public static final String SB_STG_PROPERTY_PARAMETERS_HOME = "SBStgPropertyParametersHome";

	// ************* JNDIConstant for MF checklist *********************
	public static final String EB_MF_CHECKLIST_JNDI = "EBMFChecklistHome";

	public static final String EB_MF_CHECKLIST_STAGING_JNDI = "EBMFChecklistStagingHome";

	public static final String EB_MF_CHECKLIST_LOCAL_JNDI = "EBMFChecklistLocalHome";

	public static final String EB_MF_CHECKLIST_ITEM_LOCAL_JNDI = "EBMFChecklistItemLocalHome";

	public static final String EB_MF_CHECKLIST_ITEM_LOCAL_STAGING_JNDI = "EBMFChecklistItemLocalHomeStaging";

	// WLS 17 Jan 2008: Property index module
	// ************* JNDIConstant for MF template *********************
	public static final String EB_PROPERTY_IDX_JNDI = "EBPropertyIdxHome";

	public static final String EB_PROPERTY_IDX_STAGING_JNDI = "EBStgPropertyIdxHome";

	public static final String EB_PROPERTY_IDX_SEC_SUBTYPE_LOCAL_JNDI = "EBPropertyIdxSecSubTypeLocalHome";

	public static final String EB_PROPERTY_IDX_SEC_SUBTYPE_LOCAL_STAGING_JNDI = "EBStgPropertyIdxSecSubTypeLocalHome";

	public static final String EB_PROPERTY_IDX_ITEM_LOCAL_JNDI = "EBPropertyIdxItemLocalHome";

	public static final String EB_PROPERTY_IDX_ITEM_LOCAL_STAGING_JNDI = "EBStgPropertyIdxItemLocalHome";

	public static final String EB_PROPERTY_IDX_DISTRICT_LOCAL_JNDI = "EBPropertyIdxDistrictLocalHome";

	public static final String EB_PROPERTY_IDX_DISTRICT_LOCAL_STAGING_JNDI = "EBStgPropertyIdxDistrictLocalHome";

	public static final String EB_PROPERTY_IDX_MUKIM_LOCAL_JNDI = "EBPropertyIdxMukimLocalHome";

	public static final String EB_PROPERTY_IDX_MUKIM_LOCAL_STAGING_JNDI = "EBStgPropertyIdxMukimLocalHome";

	public static final String EB_PROPERTY_IDX_PROPERTY_TYPE_LOCAL_JNDI = "EBPropertyIdxPropertyTypeLocalHome";

	public static final String EB_PROPERTY_IDX_PROPERTY_TYPE_LOCAL_STAGING_JNDI = "EBStgPropertyIdxPropertyTypeLocalHome";

	public static final String SB_PROPERTY_IDX_HOME = "SBPropertyIdxHome";

	public static final String SB_STG_PROPERTY_IDX_HOME = "SBStgPropertyIdxHome";

	public static final String SB_PROPERTY_IDX_PROXY_JNDI = "SBPropertyIdxProxyManagerHome";


    // ************* JNDIConstant for Tat Doc *********************
    public static final String SB_TAT_DOC_PROXY_JNDI = "SBTatDocProxyManagerHome";

    public static final String SB_TAT_DOC_BUS_JNDI = "SBTatDocBusManagerHome";

    public static final String SB_STAGING_TAT_DOC_BUS_JNDI = "SBStagingTatDocBusManagerHome";

    public static final String EB_TAT_DOC_JNDI = "EBTatDocHome";

    public static final String EB_STAGING_TAT_DOC_JNDI = "EBStagingTatDocHome";

    public static final String EB_TAT_DOC_DRAFT_LOCAL_JNDI = "EBTatDocDraftLocalHome";

    public static final String EB_STAGING_TAT_DOC_DRAFT_LOCAL_JNDI = "EBStagingTatDocDraftLocalHome";

	/*********************** Credit Risk Param - Maintain Country Limit JNDI Constants *********************************`*/

	public static final String SB_COUNTRY_LIMIT_PROXY_JNDI = "SBCountryLimitProxyHome";
	public static final String SB_COUNTRY_LIMIT_MGR_JNDI = "SBCountryLimitBusManagerHome";
    public static final String SB_COUNTRY_LIMIT_MGR_STAGING_JNDI = "SBCountryLimitBusManagerStagingHome";

	public static final String EB_COUNTRY_LIMIT_JNDI = "EBCountryLimitHome";
    public static final String EB_COUNTRY_LIMIT_STAGING_JNDI = "EBCountryLimitStagingHome";
	public static final String EB_COUNTRY_RATING_JNDI = "EBCountryRatingHome";
    public static final String EB_COUNTRY_RATING_STAGING_JNDI = "EBCountryRatingStagingHome";



    // LMS
	/*********************** Exempt Facility JNDI Constants **********************************/

    public static final String SB_EXEMPT_FACILITY_PROXY_JNDI = "SBExemptFacilityProxyHome";
    public static final String SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI = "SBExemptFacilityBusManagerHome";
    public static final String SB_EXEMPT_FACILITY_BUS_MANAGER_JNDI_STAGING = "SBExemptFacilityBusManagerStagingHome";

    public static final String EB_EXEMPT_FACILITY_JNDI_STAGING = "EBExemptFacilityStagingHome";
    public static final String EB_EXEMPT_FACILITY_JNDI = "EBExemptFacilityHome";


	/*********************** Counter Party JNDI Constants **********************************/

    public static final String SB_COUNTERPARTY_DETAILS_PROXY_JNDI = "SBCounterpartyDetailsProxyHome";

    public static final String EB_CCI_COUNTERPARTY_DETAILS_JNDI = "EBCCICounterpartyDetailsHome";
    public static final String EB_CCI_COUNTERPARTY_DETAILS_LOCAL_JNDI = "EBCCICounterpartyDetailsLocalHome";

    public static final String EB_CCI_COUNTERPARTY_DETAILS_STAGING_JNDI = "EBCCICounterpartyDetailsStagingHome";
    public static final String EB_CCI_COUNTERPARTY_DETAILS_LOCAL_STAGING_JNDI = "EBCCICounterpartyDetailsLocalStagingHome";

    public static final String SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI = "SBCCICounterpartyDetailsBusManagerHome";
    public static final String SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_STAGING_JNDI = "SBCCICounterpartyDetailsBusManagerStagingHome";

	public static final String SB_INTERNAL_LIMIT_PROXY_JNDI = "SBInternalLimitProxy";
    public static final String SB_ACTUAL_INTERNAL_LIMIT_JNDI = "SBInternalLimitParameterBusManagerHome";
    public static final String SB_STAGE_INTERNAL_LIMIT_JNDI = "SBStgInternalLimitParameterBusManagerHome";
    public static final String EB_ACTUAL_INTERNAL_LIMIT_JNDI = "EBInternalLimitParameterHome";
    public static final String EB_STAGE_INTERNAL_LIMIT_JNDI = "EBStgInternalLimitParameterHome";

    /**
     * ******************** Customer Group  JNDI Constants *********************************
     */

    public static final String SB_CUST_GRP_IDENTIFIER_PROXY_JNDI = "SBCustGrpIdentifierProxyHome";

    public static final String SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_JNDI = "SBCustGrpIdentifierBusManagerHome";
    public static final String SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_STAGING_JNDI = "SBCustGrpIdentifierBusManagerStagingHome";

    public static final String EB_CUST_GRP_IDENTIFIER_JNDI = "EBCustGrpIdentifierHome";
    // public static final String EB_CUST_GRP_IDENTIFIER_LOCAL_JNDI = "EBCustGrpIdentifierLocalHome";

    public static final String EB_CUST_GRP_IDENTIFIER_STAGING_JNDI = "EBCustGrpIdentifierStagingHome";
    // public static final String EB_CUST_GRP_IDENTIFIER_LOCAL_STAGING_JNDI = "EBCustGrpIdentifierLocalStagingHome";

     // START FOR   GROUP_SUBLIMIT
    public static final String EB_GROUP_SUBLIMIT_JNDI = "EBGroupSubLimitHome";
    public static final String EB_GROUP_SUBLIMIT_LOCAL_JNDI = "EBGroupSubLimitLocalHome";

    public static final String EB_GROUP_SUBLIMIT_STAGING_JNDI = "EBStagingGroupSubLimitHome";
    public static final String EB_GROUP_SUBLIMIT_LOCAL_STAGING_JNDI = "EBStagingGroupSubLimitLocalHome";
   // END FOR   GROUP_SUBLIMIT

   // START FOR   GROUP_SUBLIMIT
    public static final String EB_GROUP_OTRLIMIT_JNDI = "EBGroupOtrLimitHome";
    public static final String EB_GROUP_OTRLIMIT_LOCAL_JNDI = "EBGroupOtrLimitLocalHome";

    public static final String EB_GROUP_OTRLIMIT_STAGING_JNDI = "EBStagingGroupOtrLimitHome";
    public static final String EB_GROUP_OTRLIMIT_LOCAL_STAGING_JNDI = "EBStagingGroupOtrLimitLocalHome";
   // END FOR   GROUP_SUBLIMIT

     // START FOR   GROUP_MEMBER
    public static final String EB_GROUP_MEMBER_JNDI = "EBGroupMemberHome";
    public static final String EB_GROUP_MEMBER_LOCAL_JNDI = "EBGroupMemberLocalHome";

    public static final String EB_GROUP_MEMBER_STAGING_JNDI = "EBStagingGroupMemberHome";
    public static final String EB_GROUP_MEMBER_LOCAL_STAGING_JNDI = "EBStagingGroupMemberLocalHome";
   // END FOR   GROUP_MEMBER


     // START FOR  GROUP_CREDIT_GRADE
    public static final String EB_GROUP_CREDIT_GRADE_JNDI = "EBGroupCreditGradeHome";
    public static final String EB_GROUP_CREDIT_GRADE_LOCAL_JNDI = "EBGroupCreditGradeLocalHome";

    public static final String EB_GROUP_CREDIT_GRADE_STAGING_JNDI = "EBStagingGroupCreditGradeHome";
    public static final String EB_GROUP_CREDIT_GRADE_LOCAL_STAGING_JNDI = "EBStagingGroupCreditGradeLocalHome";
    // eND FOR  GROUP_CREDIT_GRADE

	/*********************** Credit Risk Param - Maintain Exempted Institution For GP5 Exposure JNDI Constants **********************************/

	public static final String SB_EXEMPT_INST_PROXY_JNDI = "SBExemptedInstProxyHome";
	public static final String SB_EXEMPT_INST_MGR_JNDI = "SBExemptedInstBusManagerHome";
    public static final String SB_EXEMPT_INST_MGR_STAGING_JNDI = "SBExemptedInstBusManagerStagingHome";

	public static final String EB_EXEMPT_INST_JNDI = "EBExemptedInstHome";
    public static final String EB_EXEMPT_INST_STAGING_JNDI = "EBExemptedInstStagingHome";

	/*********************** Cust relationship and shareholder JNDI Constants **********************************/

    public static final String SB_CUST_RELNSHIP_PROXY_JNDI = "SBCustRelationshipProxyHome";
    public static final String SB_CUST_RELNSHIP_MGR_JNDI = "SBCustRelationshipBusManagerHome";
    public static final String SB_CUST_RELNSHIP_MGR_STAGING_JNDI = "SBCustRelationshipBusManagerStagingHome";

	public static final String EB_CUST_RELNSHIP_LOCAL_JNDI = "EBCustRelationshipLocalHome";
    public static final String EB_CUST_RELNSHIP_JNDI = "EBCustRelationshipHome";
    public static final String EB_CUST_RELNSHIP_STAGING_JNDI = "EBCustRelationshipStagingHome";

	public static final String EB_SHAREHOLDER_LOCAL_JNDI = "EBCustShareholderLocalHome";
    public static final String EB_SHAREHOLDER_JNDI = "EBCustShareholderHome";
    public static final String EB_SHAREHOLDER_STAGING_JNDI = "EBCustShareholderStagingHome";

	/*********************** Bank Entity Branch Code Param JNDI Constants **********************************/

    public static final String SB_BANK_ENTITY_BRANCH_PROXY_JNDI = "SBBankEntityBranchParamProxyHome";
    public static final String SB_BANK_ENTITY_BRANCH_JNDI = "SBBankEntityBranchParamHome";
    public static final String SB_BANK_ENTITY_BRANCH_STAGING_JNDI = "SBStgBankEntityBranchParamHome";

	public static final String EB_BANK_ENTITY_BRANCH_LOCAL_JNDI = "EBBankEntityBranchParamLocalHome";
    public static final String EB_BANK_ENTITY_BRANCH_JNDI = "EBBankEntityBranchParamHome";
    public static final String EB_BANK_ENTITY_BRANCH_STAGING_JNDI = "EBStgBankEntityBranchParamHome";

    /*********************** Internal Credit Rating Param JNDI Constants **********************************/

    public static final String SB_INTERNAL_CREDIT_RATING_PROXY_JNDI = "SBInternalCreditRatingProxyHome";
    public static final String SB_INTERNAL_CREDIT_RATING_JNDI = "SBInternalCreditRatingBusManagerHome";
    public static final String SB_INTERNAL_CREDIT_RATING_STAGING_JNDI = "SBInternalCreditRatingBusManagerStagingHome";

    public static final String EB_INTERNAL_CREDIT_RATING_JNDI = "EBInternalCreditRatingHome";
    public static final String EB_INTERNAL_CREDIT_RATING_STAGING_JNDI = "EBInternalCreditRatingStagingHome";


	/*********************** Credit Risk Param - Maintain Entity Limit JNDI Constants *************************/

	public static final String SB_ENTITY_LIMIT_PROXY_JNDI = "SBEntityLimitProxyHome";
	public static final String SB_ENTITY_LIMIT_MGR_JNDI = "SBEntityLimitBusManagerHome";
    public static final String SB_ENTITY_LIMIT_MGR_STAGING_JNDI = "SBEntityLimitBusManagerStagingHome";

	public static final String EB_ENTITY_LIMIT_JNDI = "EBEntityLimitHome";
	public static final String EB_ENTITY_LIMIT_LOCAL_JNDI = "EBEntityLimitLocalHome";
    public static final String EB_ENTITY_LIMIT_STAGING_JNDI = "EBEntityLimitStagingHome";
	public static final String EB_ENTITY_LIMIT_STAGING_LOCAL_JNDI = "EBEntityLimitStagingLocalHome";

	 /*********************** Limit Booking JNDI Constants **********************************/
    public static final String SB_LIMIT_BOOKING_PROXY_JNDI = "SBLimitBookingProxyHome";

	public static final String EB_LIMIT_BOOKING_JNDI = "EBLimitBookingHome";
	public static final String EB_LIMIT_BOOKING_STAGING_JNDI = "EBLimitBookingStagingHome";

    public static final String EB_LIMIT_BOOKING_DETAIL_LOCAL_JNDI = "EBLimitBookingDetailLocalHome";
    public static final String EB_LIMIT_BOOKING_DETAIL_LOCAL_STAGING_JNDI = "EBLimitBookingDetailLocalHomeStaging";

	public static final String SB_LIMIT_BOOKING_MGR_JNDI = "SBLimitBookingBusManagerHome";
    public static final String SB_LIMIT_BOOKING_MGR_STAGING_JNDI = "SBLimitBookingBusManagerStagingHome";


    // for exposure
     public static final String EB_CUST_EXPOSURE_JNDI = "EBCustExposureHome";
     public static final String EB_CUST_EXPOSURE_STAGING_JNDI = "EBCustExposureStagingHome";
     public static final String SB_CUST_EXPOSURE_PROXY_JNDI = "SBCustExposureProxyHome";
     public static final String SB_CUST_EXPOSURE_BUS_MANAGER_JNDI = "SBCustExposureBusManagerHome";

     public static final String EB_GROUP_EXPOSURE_JNDI = "EBGroupExposureHome";
     public static final String EB_GROUP_EXPOSURE_STAGING_JNDI = "EBGroupExposureStagingHome";
     public static final String SB_GROUP_EXPOSURE_PROXY_JNDI = "SBGroupExposureProxyHome";
     public static final String SB_GROUP_EXPOSURE_BUS_MANAGER_JNDI = "SBGroupExposureBusManagerHome";

     /*********************** Credit Risk Param - Product Limit Parameter JNDI Constants **********************************/
     public static final String EB_ACTUAL_PRODUCT_PROGRAM_LIMIT_JNDI = "EBProductProgramLimitParameterHome";
     public static final String EB_ACTUAL_PRODUCT_TYPE_LIMIT_LOCAL_JNDI = "EBProductTypeLimitParameterLocalHome";
     public static final String SB_ACTUAL_PRODUCT_LIMIT_BUS_JNDI = "SBProductLimitParameterBusManagerHome";
     public static final String SB_STAGING_PRODUCT_LIMIT_BUS_JNDI = "SBProductLimitParameterBusManagerStagingHome";
     public static final String EB_STAGING_PRODUCT_PROGRAM_LIMIT_JNDI = "EBProductProgramLimitParameterStagingHome";
     public static final String SB_PRODUCT_LIMIT_PROXY_JNDI = "SBProductLimitParameterProxyManagerHome";
     public static final String EB_ACTUAL_PRODUCT_TYPE_LIMIT_JNDI = "EBProductTypeLimitParameterHome";
     
       /************************** DP Module Asset General charge Anil************************************************************/
 	public static final String EB_GENERALCHARGEDETAILS_LOCAL_JNDI = "EBGeneralChargeDetailsLocalHome";
 	public static final String EB_GENERALCHARGEDETAILS_LOCAL_STAGING_JNDI = "EBGeneralChargeDetailsLocalHomeStaging";

 	public static final String EB_GENERALCHARGESTOCKDETAILS_LOCAL_JNDI = "EBGeneralChargeStockDetailsLocalHome";
 	public static final String EB_GENERALCHARGESTOCKDETAILS_LOCAL_STAGING_JNDI = "EBGeneralChargeStockDetailsLocalHomeStaging";
     
     
     public static final String EB_CRI_LOCAL_JNDI = "EBCriInfoLocalHome";
     public static final String EB_CRI_LOCAL_JNDI_STAGING = "EBCriInfoLocalHomeStaging";
     
     public static final String EB_CRI_FAC_LOCAL_JNDI = "EBCriFacLocalHome";
     public static final String EB_CRI_FAC_LOCAL_JNDI_STAGING = "EBCriFacLocalHomeStaging";
     
     public static final String EB_CMS_CUSTOMER_UDF_LOCAL_JNDI = "EBUdfInfoLocalHome";
     public static final String EB_CMS_CUSTOMER_UDF_LOCAL_JNDI_STAGING = "EBUdfInfoLocalHomeStaging";
     
     public static final String EB_LIMIT_PROFILE_UDF_LOCAL_JNDI = "EBLimitProfileUdfLocalHome";
     public static final String EB_LIMIT_PROFILE_UDF_LOCAL_JNDI_STAGING = "EBLimitProfileUdfLocalHomeStaging";
     
     //added by santosh UBS Limit
     public static final String EB_XREF_UDF_LOCAL_JNDI = "EBLimitXRefUdfLocalHome";
     public static final String EB_XREF_UDF_LOCAL_JNDI_STAGING = "EBLimitXRefUdfLocalHomeStaging";
     //end santosh
     
     public static final String EB_XREF_UDF_LOCAL_JNDI2 = "EBLimitXRefUdfLocalHome2";
     public static final String EB_XREF_UDF_LOCAL_JNDI_STAGING2 = "EBLimitXRefUdfLocalHomeStaging2";


     public static final String EB_XREF_COBORROWER_LOCAL_JNDI = "EBLimitXRefCoBorrowerLocalHome";
     public static final String EB_XREF_COBORROWER_LOCAL_JNDI_STAGING = "EBLimitXRefCoBorrowerLocalHomeStaging";

     String EB_CHECKLIST_ITEM_IMAGE_DETAIL_LOCAL_JNDI = "EBCheckListItemImageDetailLocalHome";
     String EB_CHECKLIST_ITEM_IMAGE_DETAIL_LOCAL_JNDI_STAGING = "EBStagingCheckListItemImageDetailLocalHome";
     String EB_LINE_DETAIL_LOCAL_JNDI = "EBLineDetailLocalHome";
     String EB_LINE_DETAIL_LOCAL_JNDI_STAGING = "EBLineDetailLocalHomeStaging";

     String EB_MARKETABLE_EQUITY_LINE_DETAIL_LOCAL_JNDI = "EBMarketableEquityLineDetailLocalHome";
     String EB_MARKETABLE_EQUITY_LINE_DETAIL_LOCAL_JNDI_STAGING = "EBMarketableEquityLineDetailLocalHomeStaging";
}
