package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class BadRequestException extends AtsWsException {
    public BadRequestException(String msg) {
        super(ErrorCode.BadRequest, msg);
    }
}
