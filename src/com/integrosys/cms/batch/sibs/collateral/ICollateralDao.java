/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import java.util.List;

/**
 * @author User
 * @date 02 oct 08 1908hr
 *
 */

public interface ICollateralDao {

    public ICollateralSMF saveSharesMarginFinancing(String entityName, ICollateralSMF obCollSMF);

    public ICollateralFD saveFdMaturity(String entityName, ICollateralFD obCollFD);

    public void saveCollateralSMFList(final String entityName, final List fdList);

    public void saveCollateralFDList(final String entityName, final List smfList);
}
