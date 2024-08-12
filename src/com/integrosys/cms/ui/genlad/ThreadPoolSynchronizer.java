package com.integrosys.cms.ui.genlad;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class ThreadPoolSynchronizer {
	
	
	
	  private static volatile ThreadPoolSynchronizer instance = null;
	  
	    // private constructor
	    private ThreadPoolSynchronizer() {
	    }
	 
	    public static ThreadPoolSynchronizer getInstance() {
	        if (instance == null) {
	            synchronized (ThreadPoolSynchronizer.class) {
	                // Double check
	                if (instance == null) {
	                    instance = new ThreadPoolSynchronizer();
	                }
	            }
	        }
	        return instance;
	    }
	

	    
	    
	    public static ExecutorService getExecutorService(){
	    	
	    	ExecutorService executor = Executors.newFixedThreadPool(PropertyManager
					.getInt("threadpool.size"));// creating a pool
			return executor;
	    }
}
