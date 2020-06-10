package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class UserPhoneDuplicationException extends AtsWsException {
    public UserPhoneDuplicationException() {
        super(ErrorCode.UserPhoneDuplication,  "User phone number duplication");
    }

    public UserPhoneDuplicationException(String phoneNo) {
        super(ErrorCode.UserPhoneDuplication, "User phone number duplication in excel file : " + phoneNo);
    }
}
