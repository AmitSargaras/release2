package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.List;

/**
 * A ORM based Dao interface to be used for Limit Package. Mainly used by
 * facility.
 * 
 * @author Chong Jun Yong
 * @since 02.09.2008
 */
public interface IFacilityDao {

	/** entity name for <b>actual</b> facility master */
	public static final String ACTUAL_FACILITY_MASTER = "actualFacilityMaster";

	/** entity name for <b>stage</b> facility master */
	public static final String STAGE_FACILITY_MASTER = "stageFacilityMaster";

	public IFacilityMaster retrieveFacilityMasterByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create a facility master and return the key generated.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param facilityMaster the object to be created
	 * @return the key generated for the facility master supplied
	 */
	public Serializable createFacilityMaster(String entityName, IFacilityMaster facilityMaster);

	/**
	 * Update a facility master. Might throw specific persistence framework
	 * exception if the object to be updated is not found.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param facilityMaster the object to be updated
	 * @return updated facility master
	 */
	public IFacilityMaster updateFacilityMaster(String entityName, IFacilityMaster facilityMaster);

	/**
	 * Delete a facility master. Might throw specific persistence framework
	 * exception if the object to be deleted is not found.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param facilityMaster the object to be deleted
	 */
	public void deleteFacilityMaster(String entityName, IFacilityMaster facilityMaster);

	/**
	 * Find facility master by supplying corresponding limit cms key.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param cmsLimitId corresponding limit cms key
	 * @return the facility master that belong to the limit with cms key
	 *         supplied, else null will be return.
	 */
	public IFacilityMaster findFacilityMasterByCmsLimitId(String entityName, long cmsLimitId);

	/**
	 * Find facility master by supplying corresponding limit reference.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param limitRef corresponding limit reference
	 * @return the facility master that belong to the limit with reference
	 *         supplied, else null will be return.
	 */
	public IFacilityMaster findFacilityMasterByLimitRef(String entityName, String limitRef);

	/**
	 * Find facility master by supplying list of corresponding limit cms key.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param cmsLimitIdList list of corresponding limit cms key
	 * @return a list of facility master that belong to the list of limit with
	 *         cms key supplied, else empty list will be return.
	 */
	public List findFacilityMasterByCmsLimitIdList(String entityName, List cmsLimitIdList);

	/**
	 * Find facility master by supplying list of corresponding limit reference.
	 * 
	 * @param entityName entity name of facility master, to cater actual and
	 *        staging
	 * @param limitRefList list of corresponding limit reference
	 * @return a list of facility master that belong to the list of limit with
	 *         reference supplied, else empty list will be return.
	 */
	public List findFacilityMasterByLimitRefList(String entityName, List limitRefList);
}
