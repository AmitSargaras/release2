package com.integrosys.cms.app.feed.bus.stock;


/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerStagingImpl extends AbstractStockFeedBusManager {

	
    /**
     * 
     * This method give the entity name of 
     * staging FileMapper table  
     * 
     */
	
	public String getStockFeedEntryFileMapperName() {
        return IStockDao.STAGE_FILE_MAPPER_ID;
    }

	public String getStockFeedGroupEntityName() {
		return IStockDao.ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME;
	}

	public String getStockFeedEntryEntityName() {
		 return IStockDao.ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME;
	}
    
    public IStockFeedGroup updateToWorkingCopy(IStockFeedGroup workingCopy, IStockFeedGroup imageCopy)
            throws StockFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }







}