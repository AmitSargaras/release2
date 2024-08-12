package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.checklist.bus.EBDocumentshareLocal;
import com.integrosys.cms.app.checklist.bus.IShareDoc;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

public interface EBDynamicPropertyLocalHome extends EJBLocalHome {

	public EBDynamicPropertyLocal create(Long docItemID, IDynamicProperty object) throws CreateException;

	public EBDynamicPropertyLocal findByPrimaryKey(Long dynPropId) throws FinderException;

}
