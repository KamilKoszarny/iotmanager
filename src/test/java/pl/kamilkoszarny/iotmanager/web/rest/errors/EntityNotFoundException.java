package pl.kamilkoszarny.iotmanager.web.rest.errors;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String entityName) {
        super("Entity: " + entityName + " was not found");
    }
}
