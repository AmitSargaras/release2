package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.sql.SQLException;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public interface ISegmentWiseEmailBusManager {
	
	SearchResult getAllSegmentWiseEmail()throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	List getAllSegment()throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	SearchResult getSegmentWiseEmail(String segment)throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	ISegmentWiseEmail createSegmentWiseEmail(ISegmentWiseEmail stagingSegmentWiseEmail)throws SegmentWiseEmailException;
	
	ISegmentWiseEmail getSegmentWiseEmailById(long id) throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public void insertDataToEmailTable(String refId,List emails,String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException;
	
	SearchResult getStageEmail(long id)throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	ISegmentWiseEmail updateSegmentWiseEmail(ISegmentWiseEmail item) 
			throws SegmentWiseEmailException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public void insertDataToActualEmailTable(String refsId,List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException;
	
	public void deleteStageEmailIDs(String refId,List emails,String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException;
	
	public void deleteActualEmailIDs(String refsId,List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException;
	
	ISegmentWiseEmail deleteSegmentWiseEmail(ISegmentWiseEmail stagingSegmentWiseEmail)throws SegmentWiseEmailException;
	
	public long getSegmentId(String segment)throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException;

	ISegmentWiseEmail updateToWorkingCopy(ISegmentWiseEmail workingCopy, ISegmentWiseEmail imageCopy) throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
}
