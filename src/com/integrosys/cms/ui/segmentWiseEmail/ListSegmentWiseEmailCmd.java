package com.integrosys.cms.ui.segmentWiseEmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.proxy.ISegmentWiseEmailProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListSegmentWiseEmailCmd extends AbstractCommand implements ICommonEventConstant {

	private ISegmentWiseEmailProxyManager segmentWiseEmailProxy;

	public ISegmentWiseEmailProxyManager getSegmentWiseEmailProxy() {
		return segmentWiseEmailProxy;
	}

	public void setSegmentWiseEmailProxy(ISegmentWiseEmailProxyManager segmentWiseEmailProxy) {
		this.segmentWiseEmailProxy = segmentWiseEmailProxy;
	}

	/**
	 * Default Constructor
	 */
	public ListSegmentWiseEmailCmd() {
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "segmentWiseEmailList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "segmentList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "segmentMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String startIndex = (String) map.get("startIndex");
		int stindex = 0;

		try {
			SearchResult segmentWiseEmailList = new SearchResult();
			ArrayList segmentList=null;
			String event = (String) (map.get("event"));
			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);

			segmentWiseEmailList = (SearchResult) getSegmentWiseEmailProxy().getAllActualSegmentWiseEmail();
			List list=(List) segmentWiseEmailList.getResultList();
			segmentList= (ArrayList) getSegmentWiseEmailProxy().getAllSegment();
			
			HashMap<String,Integer> hm=new HashMap<String,Integer>();
			for(int i=0;i<segmentList.size();i++) {
				int count=0;
				for(int j=0;j<list.size();j++) {
					ISegmentWiseEmail ob=(ISegmentWiseEmail) list.get(j);
					if(segmentList.get(i).equals(ob.getSegment())) {
						count++;
					}
				}
				hm.put(segmentList.get(i).toString(), count);
			}
			
			if (StringUtils.isBlank(startIndex)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					startIndex = String.valueOf(stindex);
					resultMap.put("startIndex", startIndex);

				} else {
					stindex = Integer.parseInt(globalStartIndex);
					startIndex = globalStartIndex;
					resultMap.put("startIndex", startIndex);
				}
			} else {
				stindex = Integer.parseInt(startIndex);
				resultMap.put("startIndex", startIndex);
			}

			resultMap.put("segmentWiseEmailList", segmentWiseEmailList);
			resultMap.put("segmentList", segmentList);
			resultMap.put("segmentMap", hm);

		} catch (SegmentWiseEmailException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
