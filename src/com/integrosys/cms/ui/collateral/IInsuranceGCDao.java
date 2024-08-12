package com.integrosys.cms.ui.collateral;

import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;

public interface IInsuranceGCDao {
	
	static final String ACTUAL_INSURANCE_NAME = "actualInsurance";
	static final String STAGE_INSURANCE_NAME="stageInsurance";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	//static final String STAGE_INSURANCE_POLICY="stageInsurance";
	
	IInsuranceGC createInsurance(String entityName, IInsuranceGC insurance)
	throws InsuranceCGException;
	
	IInsuranceGC updateInsurance(String entityName, IInsuranceGC insurance)
	throws InsuranceCGException;
	
	public String getInsCode();

	IInsurancePolicy createInsurancePolicy(String entityName,IInsurancePolicy newOBInsurancePolicy) throws InsuranceCGException;
	IInsurancePolicy createAndUpdateInsurancePolicy(String entityName,IInsurancePolicy newOBInsurancePolicy) throws InsuranceCGException;
	IInsurancePolicy updateInsurancePolicy(String entityName,IInsurancePolicy newOBInsurancePolicy) throws InsuranceCGException;
}
