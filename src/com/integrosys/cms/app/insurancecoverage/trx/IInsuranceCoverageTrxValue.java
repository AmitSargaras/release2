package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 */
public interface IInsuranceCoverageTrxValue extends ICMSTrxValue{
	
	/**
	 * @return the insuranceCoverage
	 */
	public IInsuranceCoverage getInsuranceCoverage() ;

	/**
	 * @param insuranceCoverage the insuranceCoverage to set
	 */
	public void setInsuranceCoverage(IInsuranceCoverage insuranceCoverage);

	/**
	 * @return the stagingInsuranceCoverage
	 */
	public IInsuranceCoverage getStagingInsuranceCoverage() ;

	/**
	 * @param stagingInsuranceCoverage the stagingInsuranceCoverage to set
	 */
	public void setStagingInsuranceCoverage(IInsuranceCoverage stagingInsuranceCoverage) ;
	
	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);
}
