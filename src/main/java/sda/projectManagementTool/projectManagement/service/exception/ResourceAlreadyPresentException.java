package sda.projectManagementTool.projectManagement.service.exception;

public class ResourceAlreadyPresentException extends RuntimeException{

    public ResourceAlreadyPresentException(String message) {
        super(message);
    }

}
