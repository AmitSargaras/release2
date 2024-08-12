package com.integrosys.cms.app.feed.bus.stock;


/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerImpl extends AbstractStockFeedBusManager {

    /**
     * 
     * This method give the entity name of  
     * 
     */
	
	public String getStockFeedEntryFileMapperName() {
        return IStockDao.STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  creditApproval can never be working copy
	 */
    
    public IStockFeedGroup updateToWorkingCopy(IStockFeedGroup workingCopy, IStockFeedGroup imageCopy)
            throws StockFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }


	public String getStockFeedEntryEntityName() {
		 return IStockDao.ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME;
	}


	public String getStockFeedGroupEntityName() {
		 return IStockDao.ACTUAL_STOCK_FEED_GROUP_ENTITY_NAME;
	}


	
}