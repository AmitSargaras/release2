package com.integrosys.cms.app.feed.bus.mutualfunds;


/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractMutualFundsFeedBusManager {

	
    /**
     * 
     * This method give the entity name of 
     * staging FileMapper table  
     * 
     */
	
	public String getMutualFundsFeedEntryFileMapperName() {
        return IMutualFundsDao.STAGE_FILE_MAPPER_ID;
    }

	public String getMutualFundsFeedGroupEntityName() {
		return IMutualFundsDao.ACTUAL_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME;
	}

	public String getMutualFundsFeedEntryEntityName() {
		 return IMutualFundsDao.ACTUAL_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME;
	}
    
    public IMutualFundsFeedGroup updateToWorkingCopy(IMutualFundsFeedGroup workingCopy, IMutualFundsFeedGroup imageCopy)
            throws MutualFundsFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }







}