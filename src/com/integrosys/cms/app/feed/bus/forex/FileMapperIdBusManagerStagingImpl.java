package com.integrosys.cms.app.feed.bus.forex;


/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractForexFeedBusManager {

	
    /**
     * 
     * This method give the entity name of 
     * staging FileMapper table  
     * 
     */
	
	public String getForexFeedEntryFileMapperName() {
        return IForexDao.STAGE_FILE_MAPPER_ID;
    }

	public String getForexFeedGroupEntityName() {
		return IForexDao.ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME;
	}

	public String getForexFeedEntryEntityName() {
		 return IForexDao.ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME;
	}
    
    public IForexFeedGroup updateToWorkingCopy(IForexFeedGroup workingCopy, IForexFeedGroup imageCopy)
            throws ForexFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }







}