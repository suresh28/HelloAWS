package com.aws.sample.exception;

public class DownloadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112318131345L;

	public DownloadException()
	{
		super();
	}
	
	public DownloadException(String msg)
	{
		super(msg);		
	}
	
	public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public DownloadException(Throwable cause) {
        super(cause);
    }
}
