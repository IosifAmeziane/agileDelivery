package sda.projectManagementTool.projectManagement.service.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException{
    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
