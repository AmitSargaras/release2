package com.integrosys.cms.batch.sibs.creditapplication;

import java.util.List;

/*
 * date : 29 sep08
 * who : gp loh
 * 
 */
		
public interface ICreditAppDao {

    public ICreditApplication saveCreditAppODTL(String entityName, ICreditApplicationODTL obCAODTL);

    public ICreditApplication saveCreditAppNplClosedAcc(String entityName, ICreditApplicationNplClosedAcc obNplClosedAcc);

    public void saveCreditAppODTLList(final String entityName, final List creditAppODTLList);

    public void saveCreditAppNplClosedAccList(final String entityName, final List creditAppNplClosedAccList);
}
