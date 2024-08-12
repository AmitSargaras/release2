package com.integrosys.cms.app.limit.bus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract implementation of {@link IFacilityBusManager}, to provide routine to
 * interface with ORM and JDBC based DAO.
 * 
 * <p>
 * For ORM based DAO, it needs entity name to differentiate whether to actual or
 * staging table for the same object class. So, for concreate implementation
 * class, needs only to implements {@link #getFacilityMasterEntityName()},
 * normally implemented by actual and staging bus manager.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 * @see IFacilityDao
 * @see IFacilityJdbc
 */
public abstract class AbstractFacilityBusManager implements IFacilityBusManager {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IFacilityDao facilityDao;

	private IFacilityJdbc facilityJdbc;

	/**
	 * @return the facilityDao
	 */
	public IFacilityDao getFacilityDao() {
		return facilityDao;
	}

	/**
	 * @return the facilityJdbc
	 */
	public IFacilityJdbc getFacilityJdbc() {
		return facilityJdbc;
	}

	/**
	 * @param facilityDao the facilityDao to set
	 */
	public void setFacilityDao(IFacilityDao facilityDao) {
		this.facilityDao = facilityDao;
	}

	/**
	 * @param facilityJdbc the facilityJdbc to set
	 */
	public void setFacilityJdbc(IFacilityJdbc facilityJdbc) {
		this.facilityJdbc = facilityJdbc;
	}

	public IFacilityMaster createFacilityMaster(IFacilityMaster facilityMaster) {
		Long key = (Long) getFacilityDao().createFacilityMaster(getFacilityMasterEntityName(), facilityMaster);
		return getFacilityDao().retrieveFacilityMasterByPrimaryKey(getFacilityMasterEntityName(), key);
	}

	public IFacilityMaster deleteFacilityMaster(IFacilityMaster facilityMaster) {
		getFacilityDao().deleteFacilityMaster(getFacilityMasterEntityName(), facilityMaster);

		return facilityMaster;

	}

	public IFacilityMaster retrieveFacilityMasterByCmsLimitId(long cmsLimitId) {
		IFacilityMaster facilityMaster = getFacilityDao().findFacilityMasterByCmsLimitId(getFacilityMasterEntityName(),
				cmsLimitId);
		ILimit limit = getFacilityJdbc().getBasicLimitInfoByCmsLimitId(cmsLimitId, true);
		if (facilityMaster != null) {
			facilityMaster.setLimit(limit);
		}

		return facilityMaster;
	}

	public IFacilityMaster retrieveFacilityMasterByPrimaryKey(long primaryKey) {
		IFacilityMaster facilityMaster = getFacilityDao().retrieveFacilityMasterByPrimaryKey(
				getFacilityMasterEntityName(), new Long(primaryKey));

		if (facilityMaster != null && facilityMaster.getLimit() != null) {
			ILimit limit = getFacilityJdbc()
					.getBasicLimitInfoByCmsLimitId(facilityMaster.getLimit().getLimitID(), true);
			facilityMaster.setLimit(limit);
		}

		return facilityMaster;
	}

	public List retrieveListOfFacilityMasterByCmsLimitProfileId(long cmsLimitProfileId) {
		return getFacilityJdbc().getListOfBasicFacilityMasterInfoByLimitProfileId(cmsLimitProfileId, true);
	}

	public IFacilityMaster updateFacilityMaster(IFacilityMaster facilityMaster) {
		getFacilityJdbc().updateLimitInfo(facilityMaster);
		return getFacilityDao().updateFacilityMaster(getFacilityMasterEntityName(), facilityMaster);
	}

	/**
	 * to be implemented by actual and staging bus manager to provide entity
	 * name for ORM based DAO.
	 * 
	 * @return the entity name of IFacilityMaster
	 */
	protected abstract String getFacilityMasterEntityName();

	/**
	 * Get product group based on product type
	 * @param productType
	 * @return
	 */
	public String getProductGroupByProductCode(String productType) {
		return getFacilityJdbc().getProductGroupByProductCode(productType);
	};

	/**
	 * Get dealer product flag based on product type
	 * @param productType
	 * @return
	 */
	public String getDealerProductFlagByProductCode(String productType) {
		return getFacilityJdbc().getDealerProductFlagByProductCode(productType);
	};

	/**
	 * Get the revolving flag based on facility code from HOST_FACILITY_TYPE
	 * table
	 * @param facilityCode
	 * @return
	 */
	public String getRevolvingFlagByFacilityCode(String facilityCode) {
		return getFacilityJdbc().getRevolvingFlagByFacilityCode(facilityCode);
	};

	/**
	 * Get concept code based on product type
	 * @param productType
	 * @return
	 */
	public String getConceptCodeByProductCode(String productType) {
		return getFacilityJdbc().getConceptCodeByProductCode(productType);
	};

	public String getAccountTypeByFacilityCode(String facilityCode) {
		return getFacilityJdbc().getAccountTypeByFacilityCode(facilityCode);
	}

}
