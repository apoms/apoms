package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundTrxException extends AtsWsException {
    public CanNotFoundTrxException() {
        super(ErrorCode.CanNotFoundTrx, "Can not found trx");
    }

    public CanNotFoundTrxException(String trxId) {
        super(ErrorCode.CanNotFoundTrx, "Can not found trx-id : " + trxId);
    }
}
