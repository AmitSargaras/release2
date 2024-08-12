package com.integrosys.cms.app.collateralrocandinsurance.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface ICollateralRocDao {

	static final String ACTUAL_COLLATERAL_ROC_NAME = "actualCollateralRoc";
	static final String STAGE_COLLATERAL_ROC_NAME = "stageCollateralRoc";
	
	ICollateralRoc getCollateralRoc(String entityName, Serializable key)throws CollateralRocException;
	ICollateralRoc updateCollateralRoc(String entityName, ICollateralRoc item)throws CollateralRocException;
	ICollateralRoc createCollateralRoc(String entityName, ICollateralRoc collateralRoc)
			throws CollateralRocException;
	
	public List getCollateralCodeList()throws CollateralRocException;
	
	public List getComponentCodeList()throws CollateralRocException;
	
	public List getPropertyTypeList()throws CollateralRocException;
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode);
	
	SearchResult getSearchedCollateralRoc(String searchBy,String searchText)throws CollateralRocException;
}
