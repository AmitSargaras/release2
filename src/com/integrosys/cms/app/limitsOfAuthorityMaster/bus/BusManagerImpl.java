package com.integrosys.cms.app.limitsOfAuthorityMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class BusManagerImpl extends AbstractBusManager implements IBusManager {

	public String getName() {
		return ILimitsOfAuthorityMasterDao.ACTUAL_NAME;
	}
	
	public ILimitsOfAuthorityMaster updateToWorkingCopy(ILimitsOfAuthorityMaster workingCopy, ILimitsOfAuthorityMaster imageCopy)
	throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ILimitsOfAuthorityMaster updated;
		try{
			workingCopy.setEmployeeGrade(imageCopy.getEmployeeGrade());
			workingCopy.setFacilityCamCovenant(imageCopy.getFacilityCamCovenant());
			workingCopy.setFdAmount(imageCopy.getFdAmount());
			workingCopy.setLimitReleaseAmt(imageCopy.getLimitReleaseAmt());
			workingCopy.setTotalSanctionedLimit(imageCopy.getTotalSanctionedLimit());
			workingCopy.setPropertyValuation(imageCopy.getPropertyValuation());
			workingCopy.setRankingOfSequence(imageCopy.getRankingOfSequence());
			workingCopy.setSblcSecurityOmv(imageCopy.getSblcSecurityOmv());
			workingCopy.setSegment(imageCopy.getSegment());
			workingCopy.setDrawingPower(imageCopy.getDrawingPower());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreatedBy(imageCopy.getCreatedBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			updated = update(workingCopy);
			return update(updated);
		}catch (Exception e) {
			throw new LimitsOfAuthorityMasterException("Error while Copying copy to main file");
		}
	}
	
	public ILimitsOfAuthorityMaster deleteToWorkingCopy(ILimitsOfAuthorityMaster workingCopy, ILimitsOfAuthorityMaster imageCopy)
			throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
				ILimitsOfAuthorityMaster updated;
				try{
					workingCopy.setEmployeeGrade(imageCopy.getEmployeeGrade());
					workingCopy.setFacilityCamCovenant(imageCopy.getFacilityCamCovenant());
					workingCopy.setFdAmount(imageCopy.getFdAmount());
					workingCopy.setLimitReleaseAmt(imageCopy.getLimitReleaseAmt());
					workingCopy.setPropertyValuation(imageCopy.getPropertyValuation());
					workingCopy.setRankingOfSequence(imageCopy.getRankingOfSequence());
					workingCopy.setSblcSecurityOmv(imageCopy.getSblcSecurityOmv());
					workingCopy.setSegment(imageCopy.getSegment());
					workingCopy.setDrawingPower(imageCopy.getDrawingPower());
					workingCopy.setStatus(imageCopy.getStatus());
					workingCopy.setDeprecated(imageCopy.getDeprecated());
					workingCopy.setCreatedBy(imageCopy.getCreatedBy());
					workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
					updated = update(workingCopy);
					return delete(updated);
				}catch (Exception e) {
					throw new LimitsOfAuthorityMasterException("Error while Copying copy to main file");
				}
			}
			
	public SearchResult getAllLimitsOfAuthority() throws LimitsOfAuthorityMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getJdbc().getAllLimitsOfAuthority();
	}
	
}