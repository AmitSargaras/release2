package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;

public interface ILimitsOfAuthorityMasterJdbc {
	SearchResult getAllLimitsOfAuthority() throws LimitsOfAuthorityMasterException;
	
	ILimitsOfAuthorityMaster getLimitsOfAuthorityMasterByEmployeeGradeAndSegment(String employeeGrade, String segment);
	
	List<ILimitsOfAuthorityMaster> getValidLimitsOfAuthorityMasterByCustomerSegmentAndTotalSanctionedLimit(String segment, BigDecimal amount);
	
	String getMinimumEmployeeGrade(Map loaMap);
	
	Long getStagingReferenceByActualLoaMaster(Long masterId);
	
	void insertIntoLoaMasterLog(ILimitsOfAuthorityMasterTrxLog loaTrx);
	
	boolean isLimitsOfAuthorityMasterExistsByEmployeeGrade(String employeeGrade);
	
	Integer getRankingSeqByActualLoaMaster();
}