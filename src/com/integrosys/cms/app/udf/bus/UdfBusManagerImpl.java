package com.integrosys.cms.app.udf.bus;

import java.util.Collections;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;

public class UdfBusManagerImpl extends AbstractUdfBusManager implements IUdfBusManager {

	// Spring wired.
	IUdfDao udfDao;
	
	public IUdfDao getUdfDao() {
		return udfDao;
	}

	public void setUdfDao(IUdfDao udfDao) {
		this.udfDao = udfDao;
	}

	// Implemented methods
	public void deleteUdf(IUdf udf) throws UdfException {
		getUdfDao().deleteUdf(udf);
	}

	public List findAllUdfs() throws UdfException {
		List udfList = getUdfDao().findAllUdfs();
		Collections.sort(udfList, new UDFComparator());
		return udfList;
	}

	public IUdf findUdfById(String entityName, long id) throws UdfException {
		return getUdfDao().findUdfById(entityName, id);
	}

	public IUdf insertUdf(IUdf udf) throws UdfException {
		return getUdfDao().insertUdf(udf);
	}

	public IUdf updateUdf(IUdf udf) throws UdfException {
		return getUdfDao().updateUdf(udf);
	}
	public List getUdfSequencesByModuleId (String moduleId)  throws UdfException {
		return getUdfDao().getUdfSequencesByModuleId(moduleId);
	}
	public void freezeUdf(IUdf udf) throws UdfException {
		getUdfDao().freezeUdf(udf);
	}

	public List findUdfByStatus(String entityName, String status) throws UdfException {
		return getUdfDao().findUdfByStatus(entityName, status);
	}
	
	public List getUdfByModuleIdAndStatus (String moduleId, String status)  throws UdfException {
		return getUdfDao().getUdfByModuleIdAndStatus(moduleId, status);
	}

	
//	public IUdf createUdf(String entityName,IUdf udf) throws UdfException {
//		// TODO Auto-generated method stub
//		return getUdfDao().createUdf(entityName, udf);
//	}
//
//	
//	public IUdf updateUdf(String entityName,IUdf udf) throws UdfException, TrxParameterException, TransactionException, ConcurrentUpdateException {
//		// TODO Auto-generated method stub
//		return getUdfDao().updateUdf(entityName, udf);
//	}
//	
	/**
	 * @return WorkingCopy-- updated Udf Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IUdf updateToWorkingCopy(IUdf workingCopy, IUdf imageCopy)
	throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IUdf updated;
		try{
			
			   workingCopy.setModuleId(imageCopy.getModuleId());
		       workingCopy.setModuleName(imageCopy.getModuleName());
		       workingCopy.setFieldName(imageCopy.getFieldName());
		       workingCopy.setFieldTypeId(imageCopy.getFieldTypeId());
		       workingCopy.setFieldType(imageCopy.getFieldType());
		       workingCopy.setOptions(imageCopy.getOptions());
		       workingCopy.setSequence(imageCopy.getSequence());
		       workingCopy.setStatus(imageCopy.getStatus());
		       workingCopy.setMandatory(imageCopy.getMandatory());
		       workingCopy.setNumericLength(imageCopy.getNumericLength());
		      
		  
			updated = updateUdfNew(workingCopy);
			return updateUdfNew(updated);
		}catch (Exception e) {
			throw new UdfException("Error while Copying copy to main file");
		}
	}

	@Override
	public SearchResult getAllUdf() throws UdfException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		 return getUdfJdbc().getAllUdf();
		}


	

//	@Override
//	public IUdf getUdfById(long id)
//			throws UdfException, TrxParameterException, TransactionException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getUdfName() {
		return IUdfDao.ACTUAL_UDF_NAME;
	}
}
