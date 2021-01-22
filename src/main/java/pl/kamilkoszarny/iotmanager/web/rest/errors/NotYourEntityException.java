package pl.kamilkoszarny.iotmanager.web.rest.errors;

public class NotYourEntityException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NotYourEntityException() {
        super(ErrorConstants.NOT_YOUR_ENTITY_TYPE, "Not your entity", "entity", "notYourEntity");
    }
}
