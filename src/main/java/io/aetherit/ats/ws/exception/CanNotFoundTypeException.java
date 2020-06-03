package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundTypeException extends AtsWsException {
    public CanNotFoundTypeException() {
        super(ErrorCode.CanNotFoundTrx, "Can not found type");
    }

    public CanNotFoundTypeException(String type) {
        super(ErrorCode.CanNotFoundType, "Can not found type : " + type);
    }
}
