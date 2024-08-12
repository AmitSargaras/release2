package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISectorLimitDao;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitBusManagerImpl;

public class StagingProductLimitBusManagerImpl extends ProductLimitBusManagerImpl {

	public String getProductProgramLimitEntityName() {
		return IProductLimitDao.STAGING_PRODUCT_PROGRAM_LIMIT_PARAM;
	}

    public String getProductTypeLimitEntityName() {
		return IProductLimitDao.STAGING_PRODUCT_TYPE_LIMIT_PARAM;
	}

}