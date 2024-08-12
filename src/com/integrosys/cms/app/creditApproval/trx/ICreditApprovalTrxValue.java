
package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $govind: sahu $
 * @version $Revision: 1.1 $
 * @since $Date: 2011/04/06 08:10:08 $ Tag: $Name: $
 */
public interface ICreditApprovalTrxValue extends ICMSTrxValue {

	/**
	 * Get the ICreditApproval busines entity
	 * 
	 * @return ICreditApproval
	 */
	public ICreditApproval getCreditApproval();

	/**
	 * Get the staging ICreditApproval business entity
	 * 
	 * @return ICheckList
	 */

	public ICreditApproval getStagingCreditApproval();

	/**
	 * Set the ICreditApproval busines entity
	 * 
	 * @param value is of type ICreditApproval
	 */

	public void setCreditApproval(ICreditApproval value);

	/**
	 * Set the staging ICreditApproval business entity
	 * 
	 * @param value is of type ICreditApproval
	 */

	public void setStagingCreditApproval(ICreditApproval value);
	
	
	/**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID();
	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID);

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID();

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID);
}