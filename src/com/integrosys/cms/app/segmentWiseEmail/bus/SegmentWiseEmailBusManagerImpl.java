package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public class SegmentWiseEmailBusManagerImpl extends AbstractSegmentWiseEmailBusManager implements ISegmentWiseEmailBusManager{

	/**
	 * @return List of all authorized SegmentWiseEmail
	 */
	public SearchResult getAllSegmentWiseEmail()
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getSegmentWiseEmailJdbc().getAllSegmentWiseEmail();
	}
	
	//getAllSegment 
	public List getAllSegment()
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		return getSegmentWiseEmailJdbc().getAllSegment();
	}
	
	public SearchResult getSegmentWiseEmail(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		return getSegmentWiseEmailJdbc().getSegmentWiseEmail(segment);
	}

	@Override
	public String getSegmentWiseEmailName() {
		return ISegmentWiseEmailDao.ACTUAL_SEGMENT_WISE_EMAIL_NAME;
	}
	
	public long getSegmentId(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		return getSegmentWiseEmailJdbc().getSegmentId(segment);
	}
	
	public ISegmentWiseEmail updateToWorkingCopy(ISegmentWiseEmail workingCopy, ISegmentWiseEmail imageCopy)
	throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ISegmentWiseEmail updated;
		try{
			workingCopy.setSegment(imageCopy.getSegment());
			workingCopy.setStatus(imageCopy.getStatus());
			updated = updateSegmentWiseEmail(workingCopy);
			return updateSegmentWiseEmail(updated);
		}catch (Exception e) {
			throw new ProductMasterException("Error while Copying copy to main file");
		}
	}
}
