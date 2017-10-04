package Logic;

public class NotXMLFileException extends Exception {
    private String message;

    public NotXMLFileException() {
        message = String.format("Please enter only XML files");
    }

    @Override
    public String getMessage() {
        return message;
    }
}

