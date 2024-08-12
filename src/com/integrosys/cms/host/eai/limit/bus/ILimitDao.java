package com.integrosys.cms.host.eai.limit.bus;

import java.util.List;

import com.integrosys.cms.host.eai.core.IPersistentDao;

/**
 * ORM based DAO to deal with objects of limit modules.
 * 
 * @author Chong Jun Yong
 * @since 13.08.2008
 */
public interface ILimitDao extends IPersistentDao {

	public static final String ACTUAL_LIMIT_ENTITTY_NAME = "actualLimitGeneral";

	public static final String STAGE_LIMIT_ENTITTY_NAME = "stageLimitGeneral";

	/**
	 * Retrieve all account objects based on a list of account id and account no
	 * 
	 * @param accountIdList a list of account Primary Key
	 * @param accountNo account number from host
	 * @param classRequired class type of the object required
	 * @return list of account objects
	 */
	public List retrieveAccountListByAccountIdAndAccountNo(List accountIdList, String accountNo, Class classRequired);

	/**
	 * Given CMS Limit Id and Status, query a list of CMS Account Id in Limit -
	 * Account Map
	 * 
	 * @param cmsLimitId CMS limit id, CMS generated primary key
	 * @param status status of limit - account map
	 * @return a list of account primary key
	 */
	public List retrieveAccountPrimaryKeyListByCmsLimitIdAndStatus(long cmsLimitId, String status, Class classRequired);

	/**
	 * Retrieve limit account map based on the status and limit list
	 * 
	 * @param status status of limit account map
	 * @param limitList list of limit cms id
	 * @param classRequired class type of the objects required
	 * @return list of limit account map
	 */
	public List retrieveLimitAccountMapByStatusAndLimitCmsIdList(String status, List limitCmsIdList, Class classRequired);

	/**
	 * Retrieve all non deleted objects given a list of cms limit id
	 * 
	 * @param limitCmsIdList list of limit cms id
	 * @param limitCmsIdFieldName field name of object to indicate the limit cms
	 *        id
	 * @param statusIndicatorFieldName field name of object to indicate the
	 *        update status
	 * @param classRequired class type of the objects required
	 * @return non deleted limits list
	 */
	public List retrieveNonDeletedObjectsListByLimitCmsIdList(List limitCmsIdList, String limitCmsIdFieldName,
			String statusIndicatorFieldName, Class classRequired);

	public List searchLimitProfileByHostApplicationNumberAndApplicationTypeAndApplicationLawType(String hostAANumber,
			String aaType, String aaLawType, List cmsSubProfileIdList);

	public LimitProfile searchLimitProfileByLosAANumberAndSource(String losAANumber, String applicationSource);

	/**
	 * Retrieve List of Non Deleted Limits belong to the Limit Profile with the
	 * key provided. Only <code>LimitGeneral</code> instance will be retrieved
	 * instead of <code>Limits</code>
	 * @param cmsLimitProfileId CMS Limit Profile / AA internal key
	 * @return list of non deleted Limits
	 */
	public List retrieveLimitsOnlyByCmsLimitProfileId(Long cmsLimitProfileId);

	public List retrieveLimitChargeMapByChargeDetailId(long cmsChargeDetailId, boolean isActual);

	public LimitGeneral retrieveLimitByLOSLimitId(String LOSLimitId);

	/**
	 * Retrieve existing Limit Security Linkage based on the CMS limit and
	 * collateral internal keys
	 * @param cmsLimitId CMS Limit internal key
	 * @param cmsCollateralId CMS Collateral internal key
	 * @param isActual to indicate whether is from actual or staging table
	 * @return existing limit security linkage
	 */
	public LimitsApprovedSecurityMap retrieveLimitApprovedSecurityMapByCmsLimitIdAndCmsCollateralId(long cmsLimitId,
			long cmsCollateralId, boolean isActual);

	/**
	 * Retrieve list of non deleted <tt>LimitsApprovedSecurityMap</tt> based on
	 * the CMS Limit Id provided and Source Ids (optional).
	 * @param cmsLimitId CMS Limit internal key
	 * @param sourceIds source ids of the limit security linkage,
	 *        <b>optional</b>
	 * @return list of <tt>LimitsApprovedSecurityMap</tt> fulfill the criteria
	 *         supplied else empty list will be returned.
	 */
	public List retrieveListOfActualLimitsApprovedSecurityMapByCmsLimitIdAndSourceIds(long cmsLimitId,
			String[] sourceIds);

	/**
	 * Given the limit's product type and facility type to retrieve the account
	 * type for the limit/facility
	 * @param productTypeCode Product Type code
	 * @param facilityTypeCode Facility Type code
	 * @return Account type of the limit or <tt>null</tt> if there is no result
	 *         found
	 */
	public String getAccountTypeByLimitProductTypeAndFacilityType(final String productTypeCode,
			final String facilityTypeCode);
}
