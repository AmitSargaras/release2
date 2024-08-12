package com.integrosys.cms.app.checklist.bus.checklistitemimagedetail;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBCheckListItemImageDetailBean implements EntityBean, ICheckListItemImageDetail {

	private static final String SEQUENCE_NAME = "CHK_ITEM_IMAGE_SEQ";
	
	private static final String[] EXCLUDE_METHOD = new String[] { "getCheckListItemImageDetailId", "getCheckListItemId"};

	protected EntityContext context = null;
	
	public EBCheckListItemImageDetailBean () { }
	
	public long getCheckListItemImageDetailId() {
		if(null != getEBCheckListItemImageDetaiId()) {
			return getEBCheckListItemImageDetaiId().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
	
	public void setCheckListItemImageDetailId(long checkListItemImageDetailId) {
		setEBCheckListItemImageDetaiId(checkListItemImageDetailId);
	}
	
	public long getCheckListItemId() {
		if(getCheckListItemIdFK() != null) {
			return getCheckListItemIdFK().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
	public void setCheckListItemId(long checkListItemId) {
		setCheckListItemIdFK(new Long(checkListItemId));
	}
	
	public abstract Long getCheckListItemIdFK();
	
	public abstract void setCheckListItemIdFK(Long checkListItemId);
	
	public ICheckListItemImageDetail getValue() {
		OBCheckListItemImageDetail 	ob = new OBCheckListItemImageDetail();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}
	
	public void setValue(ICheckListItemImageDetail checkListItemImageDetail) {
		AccessorUtil.copyValue(checkListItemImageDetail, this, EXCLUDE_METHOD);
	}
	
	public Long ejbCreate(ICheckListItemImageDetail checkListItemImageDetail)  throws CreateException {
		if (null == checkListItemImageDetail) {
			throw new CreateException("ICheckListItemImageDetail is null!");
		}
		try {
			long checkListItemImageDetailID = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));			
			AccessorUtil.copyValue(checkListItemImageDetail, this, EXCLUDE_METHOD);
			setEBCheckListItemImageDetaiId(Long.valueOf(checkListItemImageDetailID));	
			return Long.valueOf(checkListItemImageDetailID);	
		}
		catch (Exception e) {
			e.printStackTrace();
			context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}
	
	public void ejbPostCreate(ICheckListItemImageDetail checkListItemImageDetail) {	}
	
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}
	
	public void unsetEntityContext() {
		this.context = null;
	}
	
	public abstract Long getEBCheckListItemImageDetaiId();
	public abstract void setEBCheckListItemImageDetaiId(Long checkListItemImageDetailId);
	
	public abstract long getImageId();
	public abstract void setImageId(long checkListItemImageDetailId);
	
	public abstract String getIsSelectedInd();
	public abstract void setIsSelectedInd(String isSelectedInd);
	
	public abstract String getFileName();
	public abstract void setFileName(String fileName);
	
	public abstract String getDocumentDescription();
	public abstract void setDocumentDescription(String documentDescription);
	
	public abstract String getSubFolderName();
	public abstract void setSubFolderName(String subFolderName);
	
	public void ejbActivate() { }

	public void ejbLoad() {	}

	public void ejbPassivate() { }

	public void ejbRemove() { }

	public void ejbStore() { }
}
