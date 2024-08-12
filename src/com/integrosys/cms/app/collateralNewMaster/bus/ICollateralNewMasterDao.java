package com.integrosys.cms.app.collateralNewMaster.bus;

import java.io.Serializable;
/**
 * @author  Abhijit R. 
 */
public interface ICollateralNewMasterDao {

	static final String ACTUAL_COLLATERAL_NEW_MASTER_NAME = "actualCollateralNewMaster";
	static final String STAGE_COLLATERAL_NEW_MASTER_NAME = "stageCollateralNewMaster";

	ICollateralNewMaster getCollateralNewMaster(String entityName, Serializable key)throws CollateralNewMasterException;
	ICollateralNewMaster updateCollateralNewMaster(String entityName, ICollateralNewMaster item)throws CollateralNewMasterException;
	ICollateralNewMaster deleteCollateralNewMaster(String entityName, ICollateralNewMaster item);
	ICollateralNewMaster load(String entityName,long id)throws CollateralNewMasterException;
	
	ICollateralNewMaster createCollateralNewMaster(String entityName, ICollateralNewMaster collateralNewMaster)
	throws CollateralNewMasterException;

	public boolean isCollateraNameUnique(String collateralName);
	public boolean isDuplicateRecord(String cpsId);
	public String getInsuranceByCode(String collateralCode);
}
