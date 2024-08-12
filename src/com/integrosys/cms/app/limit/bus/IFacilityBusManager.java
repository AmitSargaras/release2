package com.integrosys.cms.app.limit.bus;

import java.util.List;

/**
 * Business Manager used by facility module, normally interface with ORM or JDBC
 * based DAO to retrieve the business related objects.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public interface IFacilityBusManager {
	/**
	 * Retrieve facility master using primary key supplied
	 * 
	 * @param primaryKey primary key of facility master
	 * @return facility master match the key supplied else null will be
	 *         returned.
	 */
	public IFacilityMaster retrieveFacilityMasterByPrimaryKey(long primaryKey);

	/**
	 * Retrieve facility master using limit cms key supplied
	 * 
	 * @param cmsLimitId limit cms key
	 * @return facility master that it's limit match the limit cms key supplied,
	 *         else null will be returned.
	 */
	public IFacilityMaster retrieveFacilityMasterByCmsLimitId(long cmsLimitId);

	/**
	 * Retrieve list of facility master using limit profile cms key supplied
	 * 
	 * @param cmsLimitProfileId limit profile cms key
	 * @return list of facility master that it's limit belong to the limit
	 *         profile match the limit profile cms key supplied.
	 */
	public List retrieveListOfFacilityMasterByCmsLimitProfileId(long cmsLimitProfileId);

	/**
	 * Create facility master supplied
	 * 
	 * @param facilityMaster facility master going to be created
	 * @return facility master created with the key set.
	 */
	public IFacilityMaster createFacilityMaster(IFacilityMaster facilityMaster);

	/**
	 * Update facility master supplied
	 * 
	 * @param facilityMaster facility master going to be updated
	 * @return updated facility master
	 */
	public IFacilityMaster updateFacilityMaster(IFacilityMaster facilityMaster);

	/**
	 * Remove facility master supplied
	 * 
	 * @param facilityMaster facility master going to be removed
	 * @return facility master itself
	 */
	public IFacilityMaster deleteFacilityMaster(IFacilityMaster facilityMaster);

	/**
	 * Update or create working copy of facility master using image copy of
	 * facility master supplied. Normally update actual using staging copy.
	 * 
	 * @param workingCopy the current working copy of facility master, normally
	 *        the object in actual table, or null if need to create a new one
	 *        using imageCopy
	 * @param imageCopy the image copy of facility master, normally the object
	 *        in staging table.
	 * @return updated/created working copy of facility master.
	 */
	public IFacilityMaster updateToWorkingCopy(IFacilityMaster workingCopy, IFacilityMaster imageCopy);

	/**
	 * Get the product group based on product type
	 * @param productType
	 * @return
	 */
	public String getProductGroupByProductCode(String productType);

	/**
	 * Get the dealer product flag based on product type
	 * @param productType
	 * @return
	 */
	public String getDealerProductFlagByProductCode(String productType);

	/**
	 * Get the revolving flag based on facility code from HOST_FACILITY_TYPE
	 * table
	 * @param facilityCode
	 * @return
	 */
	public String getRevolvingFlagByFacilityCode(String facilityCode);

	/**
	 * Get the concept Code based on product type
	 * @param productType
	 * @return
	 */
	public String getConceptCodeByProductCode(String productType);

	/**
	 * Get the account type based on the facility code, mainly for Sibs
	 * validation
	 * @param facilityCode facility code of the facility
	 * @return the account type
	 */
	public String getAccountTypeByFacilityCode(String facilityCode);
}
