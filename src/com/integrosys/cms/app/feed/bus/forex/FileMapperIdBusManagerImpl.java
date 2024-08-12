package com.integrosys.cms.app.feed.bus.forex;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author govind.sahu
 * Bus Manager Implication for staging CreditApproval
 */
public class FileMapperIdBusManagerImpl extends AbstractForexFeedBusManager {

    /**
     * 
     * This method give the entity name of  
     * 
     */
	
	public String getForexFeedEntryFileMapperName() {
        return IForexDao.STAGE_FILE_MAPPER_ID;
    }

	/**
	 * This method returns exception as staging
	 *  creditApproval can never be working copy
	 */
    
    public IForexFeedGroup updateToWorkingCopy(IForexFeedGroup workingCopy, IForexFeedGroup imageCopy)
            throws ForexFeedGroupException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }


	public String getForexFeedEntryEntityName() {
		 return IForexDao.ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME;
	}


	public String getForexFeedGroupEntityName() {
		 return IForexDao.ACTUAL_FOREX_FEED_GROUP_ENTITY_NAME;
	}


	
}