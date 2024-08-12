/*
Copyright Integro Technologies Pte Ltd
*/
package com.integrosys.cms.app.creditApproval.bus;


/**
 * @author Govind.Sahu
 * @since 2011/04/05
 */
public class CreditApprovalBusManagerStagingImpl extends AbstractCreditApprovalBusManager {

	public ICreditApproval createCreditApproval(ICreditApproval creditApproval) throws CreditApprovalException {
		creditApproval = getCreditApprovalDao().createCreditApproval(getCreditApprovalStagingEntityName(), creditApproval);

		return getCreditApprovalDao().updateCreditApproval(getCreditApprovalStagingEntityName(), creditApproval);
	
	}
	
	/**
	 * Gets the CreditApproval entry list

	 * @return The CreditApproval entry having CreditApproval details
	 * @throws  on errors.
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode){
		return (boolean)getCreditApprovalDao().getCheckCreditApprovalUniquecode(getCreditApprovalStagingEntityName(),appCode);
	}
	


	public String getCreditApprovalActualEntityName() {
		return ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME;
	}

	public String getCreditApprovalStagingEntityName() {
		return ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME;
	}
	
	public String getCreditApprovalFileMapperName() {
        return ICreditApprovalDao.ACTUAL_STAGE_FILE_MAPPER_ID;
    }

	public ICreditApproval updateToWorkingCopy(ICreditApproval workingCopy, ICreditApproval imageCopy)
	throws CreditApprovalException {
throw new CreditApprovalException("'updateToWorkingCopy' should not be implemented.");
}

}
