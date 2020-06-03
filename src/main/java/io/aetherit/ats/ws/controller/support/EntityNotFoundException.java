package io.aetherit.ats.ws.controller.support;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5110597881569329005L;

    String message;
    
    public EntityNotFoundException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

}
