package com.integrosys.cms.app.feed.bus.bond;


/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerImpl extends AbstractBondFeedBusManager {

    /**
     * 
     * This method give the entity name of  
     * 
     */
	
	public String getBondFeedEntryFileMapperName() {
        return IBondDao.STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  creditApproval can never be working copy
	 */
    
    public IBondFeedGroup updateToWorkingCopy(IBondFeedGroup workingCopy, IBondFeedGroup imageCopy)
            throws BondFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }


	public String getBondFeedEntryEntityName() {
		 return IBondDao.ACTUAL_BOND_FEED_ENTRY_ENTITY_NAME;
	}


	public String getBondFeedGroupEntityName() {
		 return IBondDao.ACTUAL_BOND_FEED_GROUP_ENTITY_NAME;
	}


	
}