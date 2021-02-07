package pl.kamilkoszarny.iotmanager.service.exceptions;

public class NotYourEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotYourEntityException(Long userId, String entity, Long entityId) {
        super("You cannot access entities not belonging to you! User id: " + userId + ", " + entity + " id: " + entityId);
    }

}
