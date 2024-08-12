/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.userrole;

import java.util.Map;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPin;

/**
 * @author ravi
 * @author Chong Jun Yong
 * @since 2003/08/07
 */
public abstract class BizStructureIPinAction extends CommonAction implements IPin {

	private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	/**
	 * to retrieve the command instance getting from the repositary stored in
	 * this action, the command instance is backed the short name of the
	 * command.
	 * 
	 * @param name the command short name
	 * @return the command instance paired by the short name supplied
	 */
	protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		if (command == null) {
			throw new IllegalArgumentException("failed to lookup command class using name [" + name
					+ "], is it injected correctly ?");
		}

		return command;
	}
}