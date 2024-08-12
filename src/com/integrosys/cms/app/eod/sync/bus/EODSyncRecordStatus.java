/**
 * 
 */
package com.integrosys.cms.app.eod.sync.bus;

/**
 * This class declares the possible record status for eod sync masters.
 * 
 * @author anil.pandey
 * @createdOn Oct 8, 2014 5:29:40 PM
 *
 */
public enum EODSyncRecordStatus {
	
	INSERT_SENT,
	UPDATE_SENT,
	DELETE_SENT,
	INSERT_FAILED,
	UPDATE_FAILED,
	DELETE_FAILED,
	SUCCESSFUL,
	FAILED
}
