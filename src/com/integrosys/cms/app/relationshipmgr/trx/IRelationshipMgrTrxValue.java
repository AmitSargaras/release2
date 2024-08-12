package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 */
public interface IRelationshipMgrTrxValue extends ICMSTrxValue{
	
	/**
	 * @return the relationshipMgr
	 */
	public IRelationshipMgr getRelationshipMgr() ;

	/**
	 * @param relationshipMgr the relationshipMgr to set
	 */
	public void setRelationshipMgr(IRelationshipMgr relationshipMgr);

	/**
	 * @return the stagingRelationshipMgr
	 */
	public IRelationshipMgr getStagingRelationshipMgr() ;

	/**
	 * @param stagingRelationshipMgr the stagingRelationshipMgr to set
	 */
	public void setStagingRelationshipMgr(IRelationshipMgr stagingRelationshipMgr) ;
	
	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);
}
