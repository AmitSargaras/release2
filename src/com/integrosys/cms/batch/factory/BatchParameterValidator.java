package com.integrosys.cms.batch.factory;

import java.util.Map;

import com.integrosys.cms.batch.InvalidParameterBatchJobException;

/**
 * Validator to be subclasses by individual module. Mainly to validate the
 * parameters that required for the batch job before actual execution of batch
 * job start.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface BatchParameterValidator {
	/**
	 * Validate parameters in key value pair form
	 * 
	 * @param context key is the parameter key, value is the parameter value
	 * @throws InvalidParameterBatchJobException if there is any missing
	 *         parameters.
	 */
	public void validate(Map context) throws InvalidParameterBatchJobException;
}
