package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class CanNotFoundWalletException extends AtsWsException {
    public CanNotFoundWalletException() {
        super(ErrorCode.CanNotFoundWallet, "Can not found wallet");
    }

    public CanNotFoundWalletException(String wallet) {
        super(ErrorCode.CanNotFoundWallet, "Can not found wallet : " + wallet);
    }
}
