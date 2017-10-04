package Logic;

public class InvalidXMLInputsException extends Exception {
    private String message;

    public InvalidXMLInputsException(String i_Message) {
        message = i_Message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
