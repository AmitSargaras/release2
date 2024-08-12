package com.integrosys.cms.app.segmentWiseEmail.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public class SegmentWiseEmailBusManagerStagingImpl extends AbstractSegmentWiseEmailBusManager{

	@Override
	public String getSegmentWiseEmailName() {
		return ISegmentWiseEmailDao.STAGE_SEGMENT_WISE_EMAIL_NAME;
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public ISegmentWiseEmail updateToWorkingCopy(ISegmentWiseEmail workingCopy, ISegmentWiseEmail imageCopy)
            throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

}
