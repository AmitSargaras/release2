package com.integrosys.cms.ui.pincodemapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerSavePincodeMappingCmd extends AbstractCommand implements ICommonEventConstant {

	private IPincodeMappingProxyManager pincodeMappingProxy;

	public IPincodeMappingProxyManager getPincodeMappingProxy() {
		return pincodeMappingProxy;
	}

	public void setPincodeMappingProxy(IPincodeMappingProxyManager pincodeMappingProxy) {
		this.pincodeMappingProxy = pincodeMappingProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerSavePincodeMappingCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "IPincodeMappingTrxValue", "com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "pincodeMappingObj", "com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping", FORM_SCOPE }

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "stateList", "java.util.List", REQUEST_SCOPE }
				
		});
	}
	
	 /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		try {
			String event = (String) map.get("event");
			OBPincodeMapping pincodeMapping = (OBPincodeMapping) map.get("pincodeMappingObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IPincodeMappingTrxValue trxValueIn = (IPincodeMappingTrxValue)map.get("IPincodeMappingTrxValue");
			IPincodeMappingTrxValue trxValueOut = new OBPincodeMappingTrxValue();
			 if(event.equals("maker_create_draft_state_pincode_mapping")){
				trxValueOut = getPincodeMappingProxy().makerCreateSavePincodeMapping(ctx, pincodeMapping);
				
			 }
			else if(event.equals("maker_edit_draft_state_pincode_mapping")){
				trxValueOut = getPincodeMappingProxy().makerEditSaveUpdatePincodeMapping(ctx, trxValueIn, pincodeMapping);		
			}else{
			
			trxValueOut = getPincodeMappingProxy().makerSavePincodeMapping(ctx,pincodeMapping);
			}
			 
			 resultMap.put("stateList", getStateList());
			 resultMap.put("request.ITrxValue", trxValueOut);
		}catch (PincodeMappingException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
    }
    
    private List getStateList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getPincodeMappingProxy().getStateList();

			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				if (state.getStatus().equals("ACTIVE")) {
					String id = Long.toString(state.getIdState());
					String val = state.getStateName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
