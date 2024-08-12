package com.integrosys.cms.app.rbicategory.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
 * @author  Govind.Sahu 
 */
public interface IRbiCategoryBusManager {

		IRbiCategory getRbiCategoryById(long id) throws RbiCategoryException,TrxParameterException,TransactionException;
		
		List searchRbiCategory(String srAlph)throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		List getAllRbiCategoryList()throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		List getRbiIndCodeByNameList(String indName)throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IRbiCategory updateRbiCategory(IRbiCategory rbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IRbiCategory deleteRbiCategory(IRbiCategory rbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IRbiCategory updateToWorkingCopy(IRbiCategory workingCopy, IRbiCategory imageCopy) throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IRbiCategory createRbiCategory(IRbiCategory rbiCategory)throws RbiCategoryException;
		boolean getActualRbiCategory(IRbiCategory rbiCategory)throws RbiCategoryException;
		boolean isIndustryNameApprove(String industryNameId)throws RbiCategoryException;
		public List isRbiCodeCategoryApprove(OBRbiCategory stgObRbiCategory, boolean isEdit, OBRbiCategory actObRbiCategory)throws RbiCategoryException;
}
