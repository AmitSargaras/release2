package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */
 
public interface IDirectorMasterTrxValue  extends ICMSTrxValue {

    public IDirectorMaster getDirectorMaster();

    public IDirectorMaster getStagingDirectorMaster();

    public void setDirectorMaster(IDirectorMaster value);

    public void setStagingDirectorMaster(IDirectorMaster value);
}
