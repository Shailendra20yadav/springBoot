/**
 * 
 */
package com.sk.spring.umgmt.exception;

/**
 * @author shailendra.kum
 *
 */
public class ExternalException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    private String errCode;
    private Object[] args;

    public ExternalException() {
        super();
    }

    public ExternalException(final String errCode) {
        this.errCode = errCode;
    }

    public ExternalException(final String errCode, Object... args) {
        this.args = args;
        this.errCode = errCode;
    }

    // TODO: need to think if we really need this.
    public ExternalException(final Throwable throwable) {
        super(throwable);
    }

    public ExternalException(final Throwable throwable, final String errCode) {
        super(throwable);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
    
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object[] getArgs() {
        return args;
    }
    
    public void setArgs(Object[] args) {
        this.args = args;
    }

}
