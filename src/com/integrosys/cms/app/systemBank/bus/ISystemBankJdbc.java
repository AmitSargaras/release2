/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.systemBank.bus;

import java.util.List;

/**
 * 

 @author $Author: Abhijit R $

 */
public interface ISystemBankJdbc {
	List getAllSystemBank () throws SystemBankException;
	
	List listSystemBank(long branchCode)throws SystemBankException;

}
