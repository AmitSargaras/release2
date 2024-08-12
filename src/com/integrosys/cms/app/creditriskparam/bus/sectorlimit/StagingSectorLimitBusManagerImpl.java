package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

public class StagingSectorLimitBusManagerImpl extends SectorLimitBusManagerImpl {

	public String getMainSectorLimitEntityName() {
		return ISectorLimitDao.STAGING_MAIN_SECTOR_LIMIT_PARAM;
	}

    public String getSubSectorLimitEntityName() {
		return ISectorLimitDao.STAGING_SUB_SECTOR_LIMIT_PARAM;
	}

    public String getEcoSectorLimitEntityName() {
		return ISectorLimitDao.STAGING_ECO_SECTOR_LIMIT_PARAM;
	}

}