package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */
public class TatParamTrxControllerFactory implements ITrxControllerFactory 
{
    private ITrxController tatParamReadController;
    private ITrxController tatParamTrxController;

    public TatParamTrxControllerFactory() 
	{
        super();
    }
    
    public ITrxController getTatParamReadController() 
    {
		return tatParamReadController;
	}

	public void setTatParamReadController(ITrxController tatParamReadController) 
	{
		this.tatParamReadController = tatParamReadController;
	}

	public ITrxController getTatParamTrxController() 
	{
		return tatParamTrxController;
	}

	public void setTatParamTrxController(ITrxController tatParamTrxController) 
	{
		this.tatParamTrxController = tatParamTrxController;
	}

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException
    {
        if (isReadOperation(param.getAction())) 
        {
            return getTatParamReadController();
        }
        
        return getTatParamTrxController();
    }

    private boolean isReadOperation(String anAction)
    {
        if ((anAction.equals(ITatParamConstant.ACTION_READ_TAT_PARAM)) ||
                (anAction.equals(ITatParamConstant.ACTION_READ_TAT_PARAM_ID))) 
            return true;

        return false;
    }
}
