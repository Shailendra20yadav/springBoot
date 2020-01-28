package com.sk.spring.umgmt.exception;

public class ApplicationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
    private final String  errCode;
    private Object[] args;

    public ApplicationException() {
        super();
        this.errCode =null;
    }

    public ApplicationException(final String errCode) {
        this.errCode = errCode;
    }

    public ApplicationException(final String errCode, Object... args) {
        this.args = args;
        this.errCode = errCode;
    }

    public ApplicationException(final Throwable throwable) {
        super(throwable);
        this.errCode =null;
    }

    public ApplicationException(final Throwable throwable, final String errCode) {
        super(throwable);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
    
    public Object[] getArgs() {
        return args;
    }
    
}
