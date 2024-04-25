package DataObject;

import org.mindrot.jbcrypt.BCrypt;
public class PasswordHashRepository {

    public HashedPasswordWithSalt hashPassword(String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        return new HashedPasswordWithSalt(hashedPassword, salt);
    }
    public HashedPasswordWithSalt hashPassword(String password,String salt) {
        String hashedPassword = BCrypt.hashpw(password, salt);
        return new HashedPasswordWithSalt(hashedPassword, salt);
    }
    public static class HashedPasswordWithSalt {
        private final String hashedPassword;
        private final String salt;

        public HashedPasswordWithSalt(String hashedPassword, String salt) {
            this.hashedPassword = hashedPassword;
            this.salt = salt;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public String getSalt() {
            return salt;
        }
    }
}
