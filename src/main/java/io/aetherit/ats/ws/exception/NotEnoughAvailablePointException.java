package io.aetherit.ats.ws.exception;

@SuppressWarnings("serial")
public class NotEnoughAvailablePointException extends AtsWsException {
    public NotEnoughAvailablePointException() {
        super(ErrorCode.NotEnoughAvailablePoint, "Not enough your available point");
    }

    public NotEnoughAvailablePointException(int reservedPoint, int availablePoint) {
        super(ErrorCode.NotEnoughAvailablePoint, "Not enough your available point : " + availablePoint + ", reserved point : " +reservedPoint);
    }
}
