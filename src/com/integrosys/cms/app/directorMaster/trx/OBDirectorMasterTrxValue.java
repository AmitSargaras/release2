package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public class OBDirectorMasterTrxValue extends OBCMSTrxValue implements IDirectorMasterTrxValue{

    public  OBDirectorMasterTrxValue(){}

    IDirectorMaster directorMaster ;
    IDirectorMaster stagingDirectorMaster ;

    public OBDirectorMasterTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IDirectorMaster getDirectorMaster() {
		return directorMaster;
	}

	public void setDirectorMaster(IDirectorMaster directorMaster) {
		this.directorMaster = directorMaster;
	}

	public IDirectorMaster getStagingDirectorMaster() {
		return stagingDirectorMaster;
	}

	public void setStagingDirectorMaster(IDirectorMaster stagingDirectorMaster) {
		this.stagingDirectorMaster = stagingDirectorMaster;
	}
    
   

}
