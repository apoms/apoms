package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundUserTypeException extends AtsWsException {
    public CanNotFoundUserTypeException() {
        super(ErrorCode.CanNotFoundUserType, "Can not found user type");
    }

    public CanNotFoundUserTypeException(String userType) {
        super(ErrorCode.CanNotFoundUserType, "Can not found user type : " + userType);
    }
}
