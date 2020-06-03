package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundTrxHashException extends AtsWsException {
    public CanNotFoundTrxHashException() {
        super(ErrorCode.CanNotFoundTrxHash, "Can not found transaction Hash");
    }

    public CanNotFoundTrxHashException(String wallet) {
        super(ErrorCode.CanNotFoundTrxHash, "Can not found transaction Hash : " + wallet);
    }
}
