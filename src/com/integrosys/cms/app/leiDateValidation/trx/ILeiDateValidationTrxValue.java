package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ILeiDateValidationTrxValue extends ICMSTrxValue{

	public ILeiDateValidation getLeiDateValidation();

	public ILeiDateValidation getStagingLeiDateValidation();

	public void setLeiDateValidation(ILeiDateValidation value);

	public void setStagingLeiDateValidation(ILeiDateValidation value);
}
