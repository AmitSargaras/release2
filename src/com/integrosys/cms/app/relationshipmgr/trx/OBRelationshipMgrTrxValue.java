package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 */
public class OBRelationshipMgrTrxValue extends OBCMSTrxValue implements IRelationshipMgrTrxValue{

    public  OBRelationshipMgrTrxValue(){}

    IRelationshipMgr relationshipMgr ;
    IRelationshipMgr stagingRelationshipMgr ;
    
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

	public OBRelationshipMgrTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the relationshipMgr
	 */
	public IRelationshipMgr getRelationshipMgr() {
		return relationshipMgr;
	}

	/**
	 * @param relationshipMgr the relationshipMgr to set
	 */
	public void setRelationshipMgr(IRelationshipMgr relationshipMgr) {
		this.relationshipMgr = relationshipMgr;
	}

	/**
	 * @return the stagingRelationshipMgr
	 */
	public IRelationshipMgr getStagingRelationshipMgr() {
		return stagingRelationshipMgr;
	}

	/**
	 * @param stagingRelationshipMgr the stagingRelationshipMgr to set
	 */
	public void setStagingRelationshipMgr(IRelationshipMgr stagingRelationshipMgr) {
		this.stagingRelationshipMgr = stagingRelationshipMgr;
	}

}
