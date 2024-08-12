package com.integrosys.cms.app.segmentWiseEmail.proxy;

import java.sql.SQLException;
import java.util.List;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public interface ISegmentWiseEmailProxyManager {

	public SearchResult getAllActualSegmentWiseEmail() throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public List getAllSegment() throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public SearchResult getSegmentWiseEmail(String segment) throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public ISegmentWiseEmailTrxValue makerCreateSegmentWiseEmail(ITrxContext ctx, ISegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public ISegmentWiseEmailTrxValue getSegmentWiseEmailByTrxID(String aTrxID) throws SegmentWiseEmailException,TransactionException,CommandProcessingException;

	public void insertDataToEmailTable(String refId,List emails,String segment)throws 
	 SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException;
	
	public SearchResult getStageEmail(long id) throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public ISegmentWiseEmailTrxValue checkerApproveSegmentWiseEmail(ITrxContext anITrxContext, ISegmentWiseEmailTrxValue anISegmentWiseEmailTrxValue) 
			throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public ISegmentWiseEmailTrxValue checkerRejectSegmentWiseEmail(ITrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn)
			throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public void insertDataToActualEmailTable(String refsId,List<OBSegmentWiseEmail> objList)throws 
	 SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException;
	
	public ISegmentWiseEmailTrxValue makerCloseRejectedSegmentWiseEmail(ITrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn)
			throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public void deleteStageEmailIDs(String refId,List emails,String segment)throws 
	 SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException;
	
	public void deleteActualEmailIDs(String refsId,List<OBSegmentWiseEmail> objList)throws 
	 SegmentWiseEmailException, TrxParameterException, TransactionException, SQLException;
	
	public long getSegmentId(String segment)throws SegmentWiseEmailException, TrxParameterException, 
	TransactionException,SQLException;
	
	public ISegmentWiseEmailTrxValue getSegmentWiseEmailTrxValue(long segmentId)
			throws SegmentWiseEmailException,TransactionException,CommandProcessingException;
	
	public ISegmentWiseEmailTrxValue makerUpdateSegmentWiseEmail(OBTrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn,
			OBSegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public ISegmentWiseEmailTrxValue makerUpdateRejectedSegmentWiseEmail(OBTrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn,
			OBSegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public ISegmentWiseEmailTrxValue makerCreateRejectedSegmentWiseEmail(ITrxContext ctx, ISegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;

	public ISegmentWiseEmailTrxValue makerDeleteSegmentWiseEmail(OBTrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn,
			OBSegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;
	
	public ISegmentWiseEmailTrxValue makerDeleteRejectedSegmentWiseEmail(OBTrxContext ctx, ISegmentWiseEmailTrxValue trxValueIn,
			OBSegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException,TrxParameterException,TransactionException;

}
