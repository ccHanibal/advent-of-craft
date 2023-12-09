package day08;

public class PasswordValidator {
    public boolean verify(String password) {
        if (password.length() < 8)
            return false;

        if (!password.matches(".*[A-Z].*"))
            return false;

        if (!password.matches(".*[a-z].*"))
            return false;

        if (!password.matches(".*\\d.*"))
            return false;

        if (!password.matches(".*[.*#@$%&].*"))
            return false;

        if (!password.matches("[A-za-z\\d.*#@$%&]+"))
            return false;

        return true;
    }
}
