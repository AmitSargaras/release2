package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;

import java.util.List;

public interface IProductLimitDao {
	
	//commenting 'PRODUCT_TYPE_EXCLUDE_METHOD' and 'PRODUCT_PROGRAM_EXCLUDE_METHOD' as it is not used in code
   // public static final String[] PRODUCT_TYPE_EXCLUDE_METHOD = new String[] { "getId", "getProductProgramId", "getCmsRefId"};
  //  public static final String[] PRODUCT_PROGRAM_EXCLUDE_METHOD = new String[] { "getId", "getProductTypeList" };
	/**
	 * entity name for OB stored in actual table
	 */
	public static final String PRODUCT_PROGRAM_LIMIT_PARAM = "productProgramLimitParameter";
    public static final String PRODUCT_TYPE_LIMIT_PARAM = "productTypeLimitParameter";

    	/**
	 * entity name for OB stored in stage table
	 */
	public static final String STAGING_PRODUCT_PROGRAM_LIMIT_PARAM = "stagingProductProgramLimitParameter";
    public static final String STAGING_PRODUCT_TYPE_LIMIT_PARAM = "stagingProductTypeLimitParameter";

    public List findAll(String entityName)throws ProductLimitException;

    public Object findByRefCode(String entityName, String sectorCode)throws ProductLimitException;

    public Object findByPrimaryKey(String entityName, Long id);

    public IProductProgramLimitParameter createLimit(String entityName,IProductProgramLimitParameter sectorLimit) throws ProductLimitException;

    public Long updateLimit(String entityName,IProductProgramLimitParameter sectorLimit) throws ProductLimitException;

    public void checkVersionMismatch(String entityName, IProductProgramLimitParameter productLimit, Long verstionTime) throws VersionMismatchException;
}