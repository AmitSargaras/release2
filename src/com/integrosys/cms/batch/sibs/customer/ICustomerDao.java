
package com.integrosys.cms.batch.sibs.customer;

import com.integrosys.cms.batch.sibs.customer.ICustomer;
import java.util.List;

/**
 *
 * who: gek phuang
 * Date: 18th Sep 2008
 * Time: 2100hr
 *
 */

public interface ICustomerDao {

    //public ICustomerInfo saveCustomer(ICustomerInfo obInfo);
	public ICustomerInfo saveCustomer(String entityName, ICustomerInfo obInfo);

    public ICustomer fuseCustomer(String entityName, ICustomer obFusion);

    public void saveCustomerInfo(String entityName, List customerUpdateList);

    public void fuseCustomerInfo(String entityName, List customerFusionList);

}

