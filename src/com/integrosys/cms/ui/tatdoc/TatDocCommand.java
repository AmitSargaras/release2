package com.integrosys.cms.ui.tatdoc;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;

public class TatDocCommand extends AbstractCommand {
	private ITatDocProxy tatDocProxy;

	public ITatDocProxy getTatDocProxy() {
		return tatDocProxy;
	}

	public void setTatDocProxy(ITatDocProxy tatDocProxy) {
		this.tatDocProxy = tatDocProxy;
	}
}
