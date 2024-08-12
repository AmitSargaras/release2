/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.lad.proxy;

// java

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADBusManager;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankBusManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;
import com.integrosys.cms.app.systemBank.trx.OBSystemBankTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: Abhijit R $<br>
 * 
 * Proxy manager interface declares the methods used by commands
 */
public class LADProxyManagerImpl implements ILADProxyManager {

	
	
	private ILADBusManager ladBusManager;
	
	public ILADBusManager getLadBusManager() {
		return ladBusManager;
	}


	public void setLadBusManager(ILADBusManager ladBusManager) {
		this.ladBusManager = ladBusManager;
	}

	public List getLAD(long limit_profile_id,String stage) throws Exception {
		return getLadBusManager().getLAD( limit_profile_id,stage);
	}
	
	public List getLADSubItemById(long id) throws Exception {
		return getLadBusManager().getLADSubItemById( id);
	}
	 
	
	public List getLADItem(long lad_id) throws Exception {
		return getLadBusManager().getLADItem(lad_id);
	}
	
	public List getLADSubItem(long lad_id) throws Exception {
		return getLadBusManager().getLADSubItem(lad_id);
	}
	
	public ILAD createLAD(ILAD ilad)
	throws Exception {
			if(ilad!=null){
		
		return getLadBusManager().createLAD(ilad);
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
	
	public ILADItem createLADItem(ILADItem ilad)
	throws Exception {
	if(ilad!=null){
		
		return getLadBusManager().createLADItem(ilad);
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
	
	public ILADSubItem createLADSubItem(ILADSubItem ilad)
	throws Exception {
	if(ilad!=null){
		
		return getLadBusManager().createLADSubItem(ilad);
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
	
	public ILAD updateLAD(ILAD ilad)
	throws Exception {
		if(ilad!=null){
			
			 getLadBusManager().updateLAD(ilad);
			return ilad;
			}else{
				throw new Exception("ERROR- Entity name or key is null ");
			}
	}
	
	public void deleteLADItem(long ilad)
	throws Exception {
	if(ilad!=0){
		
		getLadBusManager().deleteLADItem(ilad);
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
	
	public void deleteLADSubItem(long ilad)
	throws Exception {
	if(ilad!=0){
		
		 getLadBusManager().deleteLADSubItem(ilad);
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
}
