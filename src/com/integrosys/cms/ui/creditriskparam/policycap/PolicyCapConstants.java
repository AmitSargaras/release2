package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

public class PolicyCapConstants {

	protected static final HashMap DEFAULT_MAX_PRICE_CAP_MAP = new HashMap();

	protected static final HashMap FI_SHARES_EXEMPTED_MAP = new HashMap();

	static {
		// Common_Code_Category_Entry BOARD_TYPE
		DEFAULT_MAX_PRICE_CAP_MAP.put("1", "15"); // 001 KLCI Component Stock
		DEFAULT_MAX_PRICE_CAP_MAP.put("2", "12"); // 002 Main Board (excluding
													// KLCI Component Stock)
		DEFAULT_MAX_PRICE_CAP_MAP.put("3", "2"); // 003 Others Board
		DEFAULT_MAX_PRICE_CAP_MAP.put("4", "1"); // 004 MESDAQ
		//DEFAULT_MAX_PRICE_CAP_MAP.put("005", "1"); // 005 Main Board (excluding
													// STI Component Stock)
		//DEFAULT_MAX_PRICE_CAP_MAP.put("006", "1"); // 006 SESDAQ
		//DEFAULT_MAX_PRICE_CAP_MAP.put("007", "1"); // 007 STI Component Stock

		FI_SHARES_EXEMPTED_MAP.put("4", "Yes"); // 004 MESDAQ is exempted
	}

}
