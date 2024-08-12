package com.integrosys.cms.app.eod.camquarter;

/**
 * @author uma.khot
 */

public interface ICamQuarterDao{
	 String generateCamDetailExtract() throws Exception;
	 String generateCamQuarterExtract() throws Exception;
}