package com.integrosys.cms.app.image.proxy;

import com.integrosys.base.uiinfra.common.AbstractCommand;

public abstract class ImageUploadCommand extends AbstractCommand {

	private IImageUploadProxyManager imageUploadProxyManager;

	/**
	 * @return the imageUploadProxyManager
	 */
	public IImageUploadProxyManager getImageUploadProxyManager() {
		return imageUploadProxyManager;
	}

	/**
	 * @param imageUploadProxyManager the imageUploadProxyManager to set
	 */
	public void setImageUploadProxyManager(
			IImageUploadProxyManager imageUploadProxyManager) {
		this.imageUploadProxyManager = imageUploadProxyManager;
	}

	
}
