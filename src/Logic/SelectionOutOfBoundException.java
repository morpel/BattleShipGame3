package Logic;

public class SelectionOutOfBoundException extends Exception {
    private String message;
    private String maxSelect;
    private String minSelect;

    public SelectionOutOfBoundException(String i_maxSelect, String i_minSelect) {
        maxSelect = i_maxSelect;
        minSelect = i_minSelect;
        message = String.format("Please select from the range <%s-%s>\n", maxSelect, minSelect);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
