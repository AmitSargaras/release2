package com.integrosys.cms.ui.tatduration;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.tatduration.proxy.ITatParamProxy;

public class TatDurationCommand extends AbstractCommand 
{
	private ITatParamProxy tatParamProxy;

	public ITatParamProxy getTatParamProxy() 
	{
		return tatParamProxy;
	}

	public void setTatParamProxy(ITatParamProxy tatParamProxy) 
	{
		this.tatParamProxy = tatParamProxy;
	}
}
