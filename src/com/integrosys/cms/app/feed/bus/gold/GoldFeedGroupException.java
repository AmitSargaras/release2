package com.integrosys.cms.app.feed.bus.gold;

import com.integrosys.base.techinfra.exception.OFAException;

public class GoldFeedGroupException extends OFAException {
	
	public GoldFeedGroupException () {
		super();
	}
	
	public GoldFeedGroupException (String msg) {
		super(msg);
	}
	
	public GoldFeedGroupException (Throwable t) {
		super(t);
	}
	
	public GoldFeedGroupException (String msg, Throwable t) {
		super(msg, t);
	}
}
