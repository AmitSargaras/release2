package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 */
public class OBInsuranceCoverageTrxValue extends OBCMSTrxValue implements IInsuranceCoverageTrxValue{

    public  OBInsuranceCoverageTrxValue(){}

    IInsuranceCoverage insuranceCoverage ;
    IInsuranceCoverage stagingInsuranceCoverage ;

    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
    /**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}

	public OBInsuranceCoverageTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the insuranceCoverage
	 */
	public IInsuranceCoverage getInsuranceCoverage() {
		return insuranceCoverage;
	}

	/**
	 * @param insuranceCoverage the insuranceCoverage to set
	 */
	public void setInsuranceCoverage(IInsuranceCoverage insuranceCoverage) {
		this.insuranceCoverage = insuranceCoverage;
	}

	/**
	 * @return the stagingInsuranceCoverage
	 */
	public IInsuranceCoverage getStagingInsuranceCoverage() {
		return stagingInsuranceCoverage;
	}

	/**
	 * @param stagingInsuranceCoverage the stagingInsuranceCoverage to set
	 */
	public void setStagingInsuranceCoverage(
			IInsuranceCoverage stagingInsuranceCoverage) {
		this.stagingInsuranceCoverage = stagingInsuranceCoverage;
	}

}
