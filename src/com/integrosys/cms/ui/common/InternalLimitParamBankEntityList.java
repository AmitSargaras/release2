package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class InternalLimitParamBankEntityList {
	
	private static Collection iLPBankEntityListLabel;
    private static Collection iLPBankEntityListValue;
    private static HashMap iLPBankEntityListMap;
	
    private static InternalLimitParamBankEntityList thisInstance;

    public synchronized static InternalLimitParamBankEntityList getInstance() {
        if (thisInstance == null) {
            thisInstance = new InternalLimitParamBankEntityList();
        }

        return thisInstance;
    }

    private InternalLimitParamBankEntityList() {

    	iLPBankEntityListLabel = new ArrayList();
    	iLPBankEntityListValue = new ArrayList();
        HashMap tempListMap = new HashMap();
        iLPBankEntityListMap = CommonDataSingleton.getCodeCategoryValueLabelMap(
        		ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE, null, null , ICMSConstant.SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE);
        
        iLPBankEntityListMap.put(ICMSConstant.BANKING_GROUP_CODE, ICMSConstant.BANKING_GROUP_DESC);

        Collection keyvalue = iLPBankEntityListMap.keySet();
        Iterator itr1 = keyvalue.iterator();

        while (itr1.hasNext()) {
            String key = (String)itr1.next();
            tempListMap.put(iLPBankEntityListMap.get(key), key);
            iLPBankEntityListValue.add(iLPBankEntityListMap.get(key));
        }

        String[] tempListValue = (String [])iLPBankEntityListValue.toArray(new String[0]);
        Arrays.sort(tempListValue);
        iLPBankEntityListValue = Arrays.asList(tempListValue);

        for (int i = 0; i < tempListValue.length; i++) {
        	iLPBankEntityListLabel.add(tempListMap.get(tempListValue[i]));
        }

    }

    public Collection getILPBankEntityListID() {
        return iLPBankEntityListLabel;
    }

    public Collection getILPBankEntityListValue() {
        return iLPBankEntityListValue;
    }

    public String getILPBankEntityName(String key) {
        if (!iLPBankEntityListMap.isEmpty()) {
            return (String)iLPBankEntityListMap.get(key);
        }
        return "";
    }


}
