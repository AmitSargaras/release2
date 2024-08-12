package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class ComponentTrxControllerFactory implements ITrxControllerFactory {
	 private ITrxController componentReadController;

	    private ITrxController componentTrxController;

	    private ITrxController componentInsertFileTrxController;

	    private ITrxController readComponentInsertFileTrxController;

	   

		public ITrxController getComponentReadController() {
			return componentReadController;
		}

		public void setComponentReadController(ITrxController componentReadController) {
			this.componentReadController = componentReadController;
		}

		public ITrxController getComponentTrxController() {
			return componentTrxController;
		}

		public void setComponentTrxController(ITrxController componentTrxController) {
			this.componentTrxController = componentTrxController;
		}

		public ITrxController getComponentInsertFileTrxController() {
			return componentInsertFileTrxController;
		}

		public void setComponentInsertFileTrxController(
				ITrxController componentInsertFileTrxController) {
			this.componentInsertFileTrxController = componentInsertFileTrxController;
		}

		public ITrxController getReadComponentInsertFileTrxController() {
			return readComponentInsertFileTrxController;
		}

		public void setReadComponentInsertFileTrxController(
				ITrxController readComponentInsertFileTrxController) {
			this.readComponentInsertFileTrxController = readComponentInsertFileTrxController;
		}

		public ComponentTrxControllerFactory() {
	        super();
	    }

	    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
	        if (isReadOperation(param.getAction())) 
	        
	        {
	            return getComponentReadController();
	        }else if(param.getAction().equals(ICMSConstant.ACTION_MAKER_FILE_INSERT) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER) ||
	        		param.getAction().equals(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER)){
	        	return getComponentInsertFileTrxController();
	        }else if(param.getAction().equals(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER) || param.getAction().equals(ICMSConstant.ACTION_CHECKER_FILE_MASTER)) {
	        	return getComponentInsertFileTrxController();
	    	}else if(param.getAction().equals(ICMSConstant.ACTION_READ_FILE_INSERT)){
	        	return getReadComponentInsertFileTrxController();
	        }
	        return getComponentTrxController();
	    }

	    private boolean isReadOperation(String anAction) {
	        if ((anAction.equals(ICMSConstant.ACTION_READ_COMPONENT)) ||
	                (anAction.equals(ICMSConstant.ACTION_READ_COMPONENT_ID))) {
	            return true;
	        }
	        return false;
	    }

}
