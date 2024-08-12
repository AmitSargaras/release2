/*
 * 
 */
package com.integrosys.base.techinfra.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;


// TODO: Auto-generated Javadoc
/**
 * The Class ResponseWrapper.
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    /** The bos. */
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    /** The writer. */
    private PrintWriter writer = new PrintWriter(bos);
    
    /** The id. */
    private long id;

    /**
     * Instantiates a new response wrapper.
     *
     * @param requestId the request id
     * @param response the response
     */
    public ResponseWrapper(Long requestId, HttpServletResponse response) {
        super(response);
        this.id = requestId;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getResponse()
     */
    @Override
    public ServletResponse getResponse() {
        return this;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getOutputStream()
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            private TeeOutputStream tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

            @Override
            public void write(int b) throws IOException {
            	tee.write(b);
            	
            }

			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			/*public void setWriteListener(WriteListener arg0) {
				// TODO Auto-generated method stub
				
			}*/
        };
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    
//    public PrintWriter getWriter() throws IOException {
//        return new TeePrintWriter(super.getWriter(), writer);
//    }

    /**
     * To byte array.
     *
     * @return the byte[]
     */
    public byte[] toByteArray(){
        return bos.toByteArray();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
}
