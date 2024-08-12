package com.integrosys.cms.app.geography.state.bus;

import com.integrosys.cms.ui.geography.state.StateDAOImpl;

public class StateDaoFactory {
	
	public static IStateDAO getStateDao(){
		return new StateDAOImpl();
	}


}
