package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 5:41:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBTatParamTrxValue extends OBCMSTrxValue implements ITatParamTrxValue {

    private ITatParam tatParam = null;
    private ITatParam stagingTatParam = null;


    /**
     * Default Constructor
     */
    public OBTatParamTrxValue() 
    {
    }

    /**
     * Construct the object based on the tat doc object
     * @param obj - ITatDoc
     */
    public OBTatParamTrxValue(ITatParam obj) 
    {
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct the object based on its parent info
     * @param anICMSTrxValue - ICMSTrxValue
     */
    public OBTatParamTrxValue(ICMSTrxValue anICMSTrxValue) 
    {
        AccessorUtil.copyValue(anICMSTrxValue, this);
    }

	public ITatParam getTatParam() 
	{
		return tatParam;
	}

	public void setTatParam(ITatParam tatParam) 
	{
		this.tatParam = tatParam;
	}

	public ITatParam getStagingTatParam() 
	{
		return stagingTatParam;
	}

	public void setStagingTatParam(ITatParam value) 
	{
		this.stagingTatParam = value;
	}
    
}
