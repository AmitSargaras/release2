package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author dattatray.thorat
 * Maker Close operation to remove  delete rejected by checker
 */

public class MakerCloseRejectedDeleteValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_VALUATION_AMOUNT_AND_RATING;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedDeleteValuationAmountAndRatingOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        IValuationAmountAndRatingTrxValue trxValue = super.getValuationAmountAndRatingTrxValue (anITrxValue);
        OBValuationAmountAndRating st = (OBValuationAmountAndRating) trxValue.getStagingValuationAmountAndRating();
        String vals = st.getValuationAmount();
        vals = UIUtil.removeComma(vals);
        vals = vals.replace(".00", "");
//		obItem.setValuationAmount(form.getValuationAmount());
        st.setValuationAmount(vals);
        
        trxValue = updateValuationAmountAndRatingTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
