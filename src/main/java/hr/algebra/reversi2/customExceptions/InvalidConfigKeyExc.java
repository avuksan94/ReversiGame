package hr.algebra.reversi2.customExceptions;

public class InvalidConfigKeyExc extends RuntimeException{
    public InvalidConfigKeyExc() {
    }

    public InvalidConfigKeyExc(String message) {
        super(message);
    }

    public InvalidConfigKeyExc(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfigKeyExc(Throwable cause) {
        super(cause);
    }

    public InvalidConfigKeyExc(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
