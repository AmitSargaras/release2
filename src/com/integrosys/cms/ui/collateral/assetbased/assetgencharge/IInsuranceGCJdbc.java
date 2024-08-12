package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.IInsuranceHistoryItem;
import com.integrosys.cms.ui.collateral.InsuranceCGException;
import com.integrosys.cms.ui.collateral.InsuranceHistorySearchCriteria;
import com.integrosys.cms.ui.collateral.InsuranceHistorySearchResult;
import com.integrosys.cms.ui.collateral.bus.IInsuranceHistoryReport;

public interface IInsuranceGCJdbc {

	SearchResult getAllInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllCloseInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllActualInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllStageInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllDeleteStageInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllProcessStageInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllRejectStageInsurance (String collateralID)throws InsuranceCGException;
	SearchResult getAllActualCodeInsurance (String collateralID)throws InsuranceCGException;
	public List getAllInsuranceForSec(String collateralId) throws InsuranceCGException ;
	SearchResult getAllDraftStageInsurance(String collateralID)throws InsuranceCGException;
	public int getActualCount(String collateralID)throws InsuranceCGException;
	
	 //Uma Khot::Insurance Deferral maintainance
	SearchResult getAllInsuranceDetails(long collateralID);
	List<String> getInsuranceIdFromChecklist(long checkListID);
	List<String> getInsuranceId(long collateralID);
	Set<String> getInsIdNotPresentInChecklist(long checkListID, long collateralID);
	String updateInsuranceDetailStg(long checklistId) throws Exception;
	
	String updateInsuranceDetail(long checklistId) throws Exception;
	SearchResult getAllInsurancePolicy(long collateralID);
	Set<String> getInsPolicyIdNotPresentInChecklist(long checkListID,long collateralID);
	List<String> getInsurancePolicyId(long collateralID);
	
	String updateChecklistForAllCustomers(String checklistId,String insuranceId) throws Exception;
	String updateChecklistForAllCustomersStg(String checklistId,String insuranceId) throws Exception;
	
	List getRecentInsuranceForCode (String insuranceCode,String collateralId)throws InsuranceCGException;
	
	public InsuranceHistorySearchResult getInsuranceHistory(InsuranceHistorySearchCriteria criteria);
	
	public List<IInsuranceHistoryReport> getFullInsuranceHistory(InsuranceHistorySearchCriteria criteria);
	
	public List<LabelValue> getInsurancePolicyNo(Long collateralId);
}

