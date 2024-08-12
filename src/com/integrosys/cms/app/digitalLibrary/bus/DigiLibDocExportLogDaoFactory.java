package com.integrosys.cms.app.digitalLibrary.bus;

import com.integrosys.base.techinfra.context.BeanHouse;

public class DigiLibDocExportLogDaoFactory {
	public static DigiLibDocExportLogDao getDigiLibDocExportLogDao() {
		return (DigiLibDocExportLogDao) BeanHouse.get("digiLibDocExportLogDao");
	}
}
