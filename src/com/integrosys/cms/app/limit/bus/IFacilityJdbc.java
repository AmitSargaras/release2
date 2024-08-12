package com.integrosys.cms.app.limit.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;

/**
 * Dao using JDBC routine, mainly used by Facility.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public interface IFacilityJdbc {
	/**
	 * Retrieve a list of limit cms key by supplying limit profile cms key
	 * 
	 * @param cmsLimitProfileId limit profile cms key
	 * @param isFromActualTable whether is retrieve from actual table or staging
	 *        table (if false)
	 * @return a list of limit cms key else empty list will be returned if there
	 *         is no one found.
	 */
	public List getListOfCmsLimitIdByCmsLimitProfileId(long cmsLimitProfileId, boolean isFromActualTable);

	/**
	 * Retrieve a list of limit cms key by supplying limit profile reference
	 * number
	 * 
	 * @param limitProfileRef limit profile reference number
	 * @param isFromActualTable whether is retrieve from actual table or staging
	 *        table (if false)
	 * @return a list of limit cms key else empty list will be returned if there
	 *         is no one found.
	 */
	public List getListOfCmsLimitIdByLimitProfileReference(String limitProfileRef, boolean isFromActualTable);

	/**
	 * Retrieve a list of limit cms key by supplying limit profile group
	 * reference number
	 * 
	 * @param limitProfileGrpRef limit profile group reference number
	 * @param isFromActualTable whether is retrieve from actual table or staging
	 *        table (if false)
	 * @return a list of limit cms key else empty list will be returned if there
	 *         is no one found.
	 */
	public List getListOfCmsLimitIdByLimitProfileGroupReference(String limitProfileGrpRef, boolean isFromActualTable);

	/**
	 * <p>
	 * Return basic limit info by supplying limit cms key. Basic info excluse
	 * association details, more for displaying purpose.
	 * 
	 * <p>
	 * Basic Info:
	 * <ul>
	 * <li>Limit Reference
	 * <li>Limit Profile Reference Number
	 * <li>Facility Code
	 * <li>Facility Sequence
	 * <li>Product Type
	 * <li>Facility Type
	 * <li>Booking Location (country and branch)
	 * <li>Approved Amount
	 * <li>Limit Tenor (Term)
	 * </ul>
	 * 
	 * @param cmsLimitId limit cms key
	 * @param isFromActualTable whether is retrieve from actual table or staging
	 *        table (if false)
	 * @return the limit with basic info else null will be returned
	 */
	public ILimit getBasicLimitInfoByCmsLimitId(long cmsLimitId, boolean isFromActualTable);

	/**
	 * <p>
	 * Return list of facility master having basic info by supplying limit
	 * profile cms key. Basic info excluse association details, more for
	 * displaying purpose.
	 * 
	 * <p>
	 * Basic Info:
	 * <ul>
	 * <li>Limit Reference
	 * <li>Facility Code
	 * <li>Facility Sequence
	 * <li>Product Type
	 * <li>Approved Amount
	 * </ul>
	 * 
	 * @param cmsLimitProfileId
	 * @param isFromActualTable
	 * @return
	 */

	public List getListOfBasicFacilityMasterInfoByLimitProfileId(long cmsLimitProfileId, boolean isFromActualTable);

	/**
	 * Update the limit using facility master info
	 * 
	 * @param facilityMaster the facility master to be used to update limit info
	 */
	public void updateLimitInfo(IFacilityMaster facilityMaster);

	/**
	 * Based on the reference ids (cms limit id) to retrieve the transaction
	 * status and the stp ready indicator. The id will be used to retrieve
	 * transaction of limit, and limit transaction id will be the trx reference
	 * id of facility transaction.
	 * 
	 * @param referenceIds the reference ids, which is the cms limit id
	 * @return a map which key is the cms limit id, value is the instance of
	 *         {@link StpTrxStatusReadyIndicator}
	 */
	public Map retrieveTrxStatusByRefIds(Long[] referenceIds);

	/**
	 * Update or insert Stp Ready get triggered from creation/update of
	 * facility. And the stp ready status will be used later, eg, to display the
	 * stp ready status.
	 * 
	 * @param transactionId transaction id for the facility transaction
	 * @param isStpReady indicate whether the facility is ready to be stp
	 */
	public void updateOrInsertStpReadyStatus(String transactionId, boolean isStpReady);

	/**
	 * Get the product group based on product type, mainly for Sibs validation
	 * @param productType
	 * @return
	 */
	public String getProductGroupByProductCode(String productType);

	/**
	 * Get the dealer product flag based on product type, mainly for Sibs
	 * validation
	 * @param productType
	 * @return
	 */
	public String getDealerProductFlagByProductCode(String productType);

	/**
	 * Get the revolving flag based on facility code, mainly for Sibs validation
	 * from HOST_FACILITY_TYPE table
	 * @param facilityCode
	 * @return
	 */
	public String getRevolvingFlagByFacilityCode(String facilityCode);

	/**
	 * To retrieve the facility general info which is <b>cancel</b> or
	 * <b>reject</b>, only the basic info which be retrieved, such as facility
	 * status, the cancel/reject date and it's code.
	 * @param cmsFacilityMasterId the cms facility master internal key
	 * @return facility general info ready to be used for cancel reject case.
	 */
	public IFacilityGeneral retrieveCancelAndRejectFacilityGeneralInfoByCmsFacilityMasterId(
			final long cmsFacilityMasterId);

	/**
	 * Get the concept code based on product type, mainly for Sibs validation
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

    /**
     * Check if acf no have been used in particular application (AA)
     * @param cmsLimitId
     * @param AANum
     * @param acfNo
     * @return true - acf No exists in application; false - otherwise
     */
    public boolean isAcfNoExists(long cmsLimitId, String AANum, String acfNo);
    
    
    List getFacDetailBySecurityId(long securityId);
}
