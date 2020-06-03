package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundExchangeStatusException extends AtsWsException {
    public CanNotFoundExchangeStatusException() {
        super(ErrorCode.CanNotFoundExchangeStatus, "Can not found exchange status!!");
    }

    public CanNotFoundExchangeStatusException(String status) {
        super(ErrorCode.CanNotFoundExchangeStatus, "Can not found exchange status : " + status);
    }
}
