package sda.projectManagementTool.projectManagement.service.exception;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException(String errorMessage) {
        super(errorMessage);
    }
}
