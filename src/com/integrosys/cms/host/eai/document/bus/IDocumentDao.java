package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.host.eai.core.IPersistentDao;

/**
 * ORM DAO to interface with Documentation module objects
 * 
 * @author Chong Jun Yong
 * @since 15.08.2008
 */
public interface IDocumentDao extends IPersistentDao {
	/**
	 * Retrieve collateral checklist by internal limit profile id and internal
	 * collateral id, <code>null</code> will be returned if there is no
	 * checklist found.
	 * 
	 * @param cmsLimitProfileId internal limit profile id
	 * @param cmsCollateralId internal collateral id
	 * @return the only checklist found for the collateral, or else null
	 */
	public CheckList retrieveCollateralCheckListByCmsLimitProfileIdAndCmsCollateralId(long cmsLimitProfileId,
			long cmsCollateralId);

	/**
	 * Retrieve the borrower checklist (main borrower, joint borrower, co
	 * borrower) using the internal limit profile id and internal customer id,
	 * <code>null</code> will be returned if there is no checklist found.
	 * 
	 * @param cmsLimitProfileId internal limit profile id
	 * @param cmsCustomerId internal customer id pertaining to the borrower
	 * @param subCategory sub category of the customer, ie, the borrower type
	 * @return the only checklist found for the customer, or else null
	 */
	public CheckList retrieveBorrowerCheckListByCmsLimitProfileIdAndCmsCustomerId(long cmsLimitProfileId,
			long cmsCustomerId, String subCategory);

	/**
	 * Retrieve the pledgor checklist using the internal limit profile id and
	 * internal pledgor id, <code>null</code> will be returned if there is no
	 * checklist found.
	 * 
	 * @param cmsLimitProfileId internal limit profile id
	 * @param cmsPledgorId internal pledgor id
	 * @return the only checklist found for the pledgor, or else null
	 */
	public CheckList retrievePledgorCheckListByCmsLimitProfileIdAndCmsPledgorId(long cmsLimitProfileId,
			long cmsPledgorId);

	/**
	 * Retrieve a series of checklist items belong to the checklist provided the
	 * cms checklist id.
	 * 
	 * @param cmsCheckListId the checklist internal key to retrieve a list of
	 *        checklist item
	 * @return a list of checklist items or null if no items found.
	 */
	public CheckListItem[] retrieveCheckListItemsByCmsCheckListId(long cmsCheckListId);
}
