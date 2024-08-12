package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;

import java.io.Serializable;
import java.util.List;

public interface ISectorLimitDao {

	/**
	 * entity name for OB stored in actual table
	 */
	public static final String MAIN_SECTOR_LIMIT_PARAM = "mainSectorLimitParameter";
    public static final String SUB_SECTOR_LIMIT_PARAM = "subSectorLimitParameter";
    public static final String ECO_SECTOR_LIMIT_PARAM = "ecoSectorLimitParameter";

    	/**
	 * entity name for OB stored in stage table
	 */
	public static final String STAGING_MAIN_SECTOR_LIMIT_PARAM = "stagingMainSectorLimitParameter";
    public static final String STAGING_SUB_SECTOR_LIMIT_PARAM = "stagingSubSectorLimitParameter";
    public static final String STAGING_ECO_SECTOR_LIMIT_PARAM = "stagingEcoSectorLimitParameter";

    public List findAll(String entityName)throws SectorLimitException;

    public List findBySectorCode(String entityName, String sectorCode)throws SectorLimitException;

    public List getAllSectorLimit(String entityName) throws SectorLimitException;

    public List getAllSubSectorLimit(String entityName) throws SectorLimitException;

    public List getAllEcoSectorLimit(String entityName) throws SectorLimitException;

    public Object findByPrimaryKey(String entityName, Long id);

    public IMainSectorLimitParameter getLimitById(String entityName, long id) throws SectorLimitException;

    public IMainSectorLimitParameter getMainSectorLimitBySectorCode(String entityName,String code) throws SectorLimitException;

    public ISubSectorLimitParameter getSubSectorLimitBySectorCode(String entityName,String code) throws SectorLimitException;

    public IEcoSectorLimitParameter getEcoSectorLimitBySectorCode(String entityName,String code) throws SectorLimitException;

    public IMainSectorLimitParameter createLimit(String entityName,IMainSectorLimitParameter sectorLimit) throws SectorLimitException;

    public IMainSectorLimitParameter updateLimit(String entityName,IMainSectorLimitParameter sectorLimit) throws SectorLimitException;
}