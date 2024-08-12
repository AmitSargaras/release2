package com.integrosys.cms.host.eai.limit.support;

import java.util.List;
import java.util.StringTokenizer;

import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.EAICustomerMessageException;
import com.integrosys.cms.host.eai.support.ReflectionUtils;

public class CommonHelper implements IEaiConstant {

	public long getToken(String key, int item) throws EAICustomerMessageException {
		StringTokenizer st = new StringTokenizer(key, DELIMITER);
		// for trx key
		st.nextToken();
		String result = null;
		int i = 1;
		while (st.hasMoreTokens()) {
			result = st.nextToken();
			if (i == item) {
				return Long.parseLong(result);
			}
			i++;
		}
		throw new EAICustomerMessageException("invalid token requested :" + item + ", number of token is :" + i
				+ ", Key String : (" + key + ")");

	}

	public String getTokenString(String key, int item) throws EAICustomerMessageException {
		StringTokenizer st = new StringTokenizer(key, DELIMITER);
		// for trx key
		st.nextToken();
		String result = null;
		int i = 1;
		while (st.hasMoreTokens()) {
			result = st.nextToken();
			if (i == item) {
				return result;
			}
			i++;
		}
		throw new EAICustomerMessageException("invalid token requested :" + item + ", number of token is :" + i
				+ ", Key String : (" + key + ")");

	}

	/**
	 * To copy on the required fields in the case of variation.<br>
	 * @param source Object<br>
	 * @param target Object<br>
	 * @param properties List of properties to be copied<br>
	 * @return Object (target object which has been copied)
	 */
	public Object copyVariationProperties(Object source, Object target, List properties) {
		ReflectionUtils.copyValuesByProperties(source, target, properties);
		return target;
	}

}
