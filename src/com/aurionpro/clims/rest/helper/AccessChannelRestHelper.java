package com.aurionpro.clims.rest.helper;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AccessChannelRestHelper implements IAccessChannelForRestAPI{
	ResourceBundle bundle = ResourceBundle.getBundle("rest_api");
	public boolean accessToChannel(String dtoChannelName,String accessChannel) {
		boolean flag = false;
		String channelName = bundle.getString(dtoChannelName);
		if(channelName != null) {
			String accessChannelString = bundle.getString(accessChannel);
			List<String> channelList = Arrays.asList(accessChannelString.split(","));
			if(channelList.contains(channelName.substring(channelName.lastIndexOf(".")+1))) {
				flag = true;
			}
		}
		return flag;
	}
}
