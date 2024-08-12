package com.integrosys.cms.ui.profile;

import com.integrosys.base.uiinfra.common.IPage;

public class Page implements IPage {
	/**
	 * forward name in structs-config.xml
	 */
	private String forwardName = null;

	/**
	 * This method contains a String array of keys which are to be present when
	 * we want to display the page.
	 * 
	 * @param event of type String
	 * @return One-dimesnional String Array
	 */
	public String[] getPageDiscriptor(String event) {
		return new String[0];
	}

	/**
	 * This method return a String whch is used to determine next page
	 * 
	 * @return String
	 */
	public String getPageReference() {
		return forwardName;
	}

	/**
	 * This method sets the PageReference for a page object with a String
	 * 
	 * @param forwardName of type String
	 */
	public void setPageReference(String forwardName) {
		this.forwardName = forwardName;
	}

}