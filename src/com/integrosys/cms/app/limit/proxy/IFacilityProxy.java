package com.integrosys.cms.app.limit.proxy;

import java.util.List;

import com.integrosys.cms.app.limit.bus.FacilityException;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Service bean to be interfaced by UI or other App layer to either Bus or Trx
 * package.
 * 
 * @author Chong Jun Yong
 * @author Andy Wong
 * @since 03.09.2008
 */
public interface IFacilityProxy {
	/**
	 * Maker create facility master, facility master to be created should reside
	 * in staging facility master of trx value supplied
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object that having staging facility
	 *        master to be created
	 * @return created trx value with every key information populated, such as
	 *         transaction id, from state, etc.
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue makerCreateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * Maker update facility master, either update draft facility master or
	 * update existing facility master. Updated facility master should reside in
	 * staging facility master of trx value supplied
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object that having staging facility
	 *        master to be created and also basic transaction info, such
	 *        transaction id.
	 * @return updated transaction value with staging reference id changed, as
	 *         well as status
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue makerUpdateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * Maker save facility master, either save from ND state or save from
	 * rejected create/update facility master. This will create a new copy in
	 * staging using the staging facility master in trx value supplied.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object that having staging facility
	 *        master to be created
	 * @return created/updated transaction value with updated staging reference
	 *         id, status, etc.
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue makerSaveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * <p>
	 * Maker close facility master transaction from rejected or draft copy.
	 * 
	 * <p>
	 * Draft copy that there is no approved transaction before will make the
	 * transaction closed forever, and next transaction for the same facility
	 * master required to create a new transaction.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated
	 * @return updated transaction value object with status changed only
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue makerCloseFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * Checker reject facility master transaction from create or update, result
	 * in different state of reject.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated
	 * @return updated transaction value object with status changed only
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue checkerRejectFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * Checker approve facility master creation or update, actual copy will be
	 * mirrored from staging copy in the trx value supplied.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated, most important is the actual (can be null) and
	 *        staging facility master
	 * @return updated transaction value with state changed, and reference id
	 *         populated (for creation)
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue checkerApproveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * Retrieve list of basic facility master information as well as basic
	 * corresponding limit info by supplying limit profile id
	 * 
	 * @param cmsLimitProfileId limit profile cms key
	 * @return list of facility master with basic information as well as
	 *         corresponding limit info
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public List retrieveListOfFacilityMasterByLimitProfileId(long cmsLimitProfileId) throws FacilityException;

	/**
	 * To retrieve transaction value using the transaction id supplied,
	 * <code>null</code> will be returned if there is no transaction value
	 * object found
	 * 
	 * @param transactionId transaction id of facility master
	 * @return transaction value that match the transaction id supplied
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue retrieveFacilityMasterTransactionByTransactionId(String transactionId)
			throws FacilityException;

	/**
	 * To retrieve transaction value using the facility master cms key supplied
	 * 
	 * @param cmsFacilityMasterId cms key of facility master
	 * @return transaction value which reference id match cms key of facility
	 *         master supplied
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue retrieveFacilityMasterTransactionById(long cmsFacilityMasterId) throws FacilityException;

	/**
	 * To retrieve transaction value using the staging facility master cms key
	 * supplied
	 * 
	 * @param cmsFacilityMasterId cms key of staging facility master
	 * @return transaction value which staging reference id match cms key of
	 *         facility master supplied
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue retrieveFacilityMasterTransactionByStagingId(long cmsFacilityMasterId)
			throws FacilityException;

	/**
	 * To retrieve transaction value using limit cms key supplied, using this
	 * limit cms key, should able to retrieve corresponding facility master.
	 * Then using the facility master cms key, to retrieve transaction value
	 * 
	 * @param cmsLimitId cms key of limit
	 * @return transaction value which reference id match cms key of facility
	 *         master which is back by limit having the cms limit id supplied
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue retrieveFacilityMasterTransactionByCmsLimitId(long cmsLimitId) throws FacilityException;

	/**
	 * System approve Stp facility master creation, update or delete, actual
	 * copy will be mirrored from staging copy in the trx value supplied.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated, most important is the actual (can be null) and
	 *        staging facility master
	 * @return updated transaction value with state changed, and reference id
	 *         populated (for creation)
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue systemApproveFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * System update Stp facility master to pending retry due to host technical
	 * error
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated, most important is the actual (can be null) and
	 *        staging facility master
	 * @return updated transaction value with state changed, and reference id
	 *         populated (for creation)
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue systemUpdateFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

	/**
	 * System reject Stp facility master transaction from create, update or
	 * deletion, result in different state of reject.
	 * 
	 * @param context trx context information, storing information such as limit
	 *        profile
	 * @param trxValue transaction value object contains every information that
	 *        will be updated
	 * @return updated transaction value object with status changed only
	 * @throws FacilityException if there is any error occured in bus or trx
	 *         package.
	 */
	public IFacilityTrxValue systemRejectFacilityMaster(ITrxContext context, IFacilityTrxValue trxValue)
			throws FacilityException;

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
}