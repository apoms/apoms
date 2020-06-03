package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class NotAcceptableIdException extends AtsWsException {
    public NotAcceptableIdException(String id) {
        super(ErrorCode.NotAcceptableId, "Not acceptable id : " + id);
    }
}
