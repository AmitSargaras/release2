package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public abstract class AbstractSegmentWiseEmailBusManager implements ISegmentWiseEmailBusManager{

	private ISegmentWiseEmailDao segmentWiseEmailDao;
	private ISegmentWiseEmailJdbc segmentWiseEmailJdbc;
	
	public ISegmentWiseEmailDao getSegmentWiseEmailDao() {
		return segmentWiseEmailDao;
	}

	public void setSegmentWiseEmailDao(ISegmentWiseEmailDao segmentWiseEmailDao) {
		this.segmentWiseEmailDao = segmentWiseEmailDao;
	}

	public ISegmentWiseEmailJdbc getSegmentWiseEmailJdbc() {
		return segmentWiseEmailJdbc;
	}

	public void setSegmentWiseEmailJdbc(ISegmentWiseEmailJdbc segmentWiseEmailJdbc) {
		this.segmentWiseEmailJdbc = segmentWiseEmailJdbc;
	}

	public abstract String getSegmentWiseEmailName();
	
	@Override
	public SearchResult getAllSegmentWiseEmail()
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getSegmentWiseEmailJdbc().getAllSegmentWiseEmail();
	}

	@Override
	public List getAllSegment()
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getSegmentWiseEmailJdbc().getAllSegment();
	}

	@Override
	public SearchResult getSegmentWiseEmail(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		return getSegmentWiseEmailJdbc().getSegmentWiseEmail(segment);
	}
	
	/**
	 @return SegmentWiseEmail Object after create
	 * 
	 */

	public ISegmentWiseEmail createSegmentWiseEmail(ISegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException {
		if (!(segmentWiseEmail == null)) {
			return getSegmentWiseEmailDao().createSegmentWiseEmail(getSegmentWiseEmailName(), segmentWiseEmail);
		} else {
			throw new SegmentWiseEmailException(
					"ERROR- SegmentWiseEmail object   is null. ");
		}
	}
	
	/**
	 @return ProductMaster Object after update
	 * 
	 */

	public ISegmentWiseEmail updateSegmentWiseEmail(ISegmentWiseEmail item)
			throws SegmentWiseEmailException, TrxParameterException,
			TransactionException {
		try {
			return getSegmentWiseEmailDao().updateSegmentWiseEmail(
					getSegmentWiseEmailName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new SegmentWiseEmailException("current SegmentWiseEmail");
		}
	}
	/**
	  * @return Particular SegmentWiseEmail according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ISegmentWiseEmail getSegmentWiseEmailById(long id)
			throws SegmentWiseEmailException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getSegmentWiseEmailDao().getSegmentWiseEmail(getSegmentWiseEmailName(), new Long(id));
		} else {
			throw new SegmentWiseEmailException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public void insertDataToEmailTable(String refId,List emails,String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException {
		 getSegmentWiseEmailJdbc().insertDataToEmailTable(refId,emails,segment);
	}
	
	public SearchResult getStageEmail(long id)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException {
		 return getSegmentWiseEmailJdbc().getStageEmail(id);
	}
	
	public void insertDataToActualEmailTable(String refsId,List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException {
		 getSegmentWiseEmailJdbc().insertDataToActualEmailTable(refsId,objList);
	}
	
	public void deleteStageEmailIDs(String refId,List emails,String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException {
		 getSegmentWiseEmailJdbc().deleteStageEmailIDs(refId,emails,segment);
	}
	
	public void deleteActualEmailIDs(String refsId,List<OBSegmentWiseEmail> objList)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException {
		 getSegmentWiseEmailJdbc().deleteActualEmailIDs(refsId,objList);
	}
	
	/**
	 @return SegmentWiseEmail Object after create
	 * 
	 */

	public ISegmentWiseEmail deleteSegmentWiseEmail(ISegmentWiseEmail segmentWiseEmail)throws SegmentWiseEmailException {
		if (!(segmentWiseEmail == null)) {
			return getSegmentWiseEmailDao().deleteSegmentWiseEmail(getSegmentWiseEmailName(), segmentWiseEmail);
		} else {
			throw new SegmentWiseEmailException(
					"ERROR- SegmentWiseEmail object   is null. ");
		}
	}
	
	public long getSegmentId(String segment)
			throws SegmentWiseEmailException, TrxParameterException, TransactionException,SQLException {
		 return getSegmentWiseEmailJdbc().getSegmentId(segment);
	}

}
