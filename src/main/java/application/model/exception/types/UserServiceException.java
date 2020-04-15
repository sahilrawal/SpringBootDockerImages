package application.model.exception.types;

public class UserServiceException extends RuntimeException {


    private static final long serialVersionUID = 5225832470163382794L;

    public UserServiceException(String message){

        super(message);
    }
}
