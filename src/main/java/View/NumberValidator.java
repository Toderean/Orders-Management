package View;

public class NumberValidator{
    public static boolean isValidNumber(String input) {
        String numberPattern = "^[-+]?\\d+(\\.\\d+)?$";
        return input.matches(numberPattern);
    }
}
