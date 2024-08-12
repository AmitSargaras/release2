package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingletonListener;

/**
 * <p>
 * Web command to reload valuation profile singleton using the listener, need to
 * provide collateral subtype instance in order to reload the singleton
 * correctly.
 * <p>
 * As noticed, this command is reloading as per one collateral subtype, so, we
 * can create multiple instance of this web command for multiple collateral
 * subtype.
 * @author Chong Jun Yong
 * @see ValuationProfileSingletonListener
 * @see com.integrosys.cms.app.collateral.bus.valuation.support.ValuationProfileSingleton
 */
public class ReloadValuationProfileSingletonCommand extends AbstractCommand {

	private ValuationProfileSingletonListener valuationProfileSingletonListener;

	private ICollateralSubType collateralSubType;

	public ReloadValuationProfileSingletonCommand(ValuationProfileSingletonListener valuationProfileSingletonListener,
			ICollateralSubType collateralSubType) {
		this.valuationProfileSingletonListener = valuationProfileSingletonListener;
		this.collateralSubType = collateralSubType;
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();

		this.valuationProfileSingletonListener.reloadSingleton(this.collateralSubType);

		returnMap.put(COMMAND_RESULT_MAP, new HashMap(0));
		returnMap.put(COMMAND_EXCEPTION_MAP, new HashMap(0));
		return returnMap;
	}

}
