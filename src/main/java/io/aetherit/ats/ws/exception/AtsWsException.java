package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class AtsWsException extends RuntimeException {
    private ErrorCode code;
    
    public AtsWsException(ErrorCode code, String message) {
        super(message);
        
        this.code = code;
    }
    
    public ErrorCode getErrorCode() {
        return this.code;
    }
}