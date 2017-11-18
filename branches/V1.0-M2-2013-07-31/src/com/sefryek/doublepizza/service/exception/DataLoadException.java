package com.sefryek.doublepizza.service.exception;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 17, 2012
 * Time: 4:16:11 PM
 */
public class DataLoadException extends Exception {
    private Throwable cause;
    private Class type;

    public DataLoadException(Throwable cause, Class type, String msg) {
        super(msg);
        this.cause = cause;
        this.type = type;
    }

    public DataLoadException(String msg) {
        super(msg);
    }

    public String toString() {
        return "DAOException[ " + type + " ]: " + this.getMessage() + "\n" + cause.getMessage();
    }

    public Throwable getCause() {
        return cause;
    }

    public Class getType() {
        return type;
    }
}
