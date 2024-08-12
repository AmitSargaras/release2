package com.integrosys.cms.ui.diaryitem;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;

public abstract class DiaryItemsCommand extends AbstractCommand {

	private IDiaryItemProxyManager diaryItemProxyManager;

	public IDiaryItemProxyManager getDiaryItemProxyManager() {
		return diaryItemProxyManager;
	}

	public void setDiaryItemProxyManager(IDiaryItemProxyManager diaryItemProxyManager) {
		this.diaryItemProxyManager = diaryItemProxyManager;
	}

}
