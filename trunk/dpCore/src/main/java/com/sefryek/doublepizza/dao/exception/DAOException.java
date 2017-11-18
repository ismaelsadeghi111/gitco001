package com.sefryek.doublepizza.dao.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 4, 2012
 * Time: 8:23:42 PM
 */
public class DAOException extends Exception {

    private Throwable cause;
    private Class type;

    public DAOException(Throwable cause, Class type, String msg) {
        super(msg);
        this.cause = cause;
        this.type = type;
    }

    public DAOException(String msg) {
        super(msg);
    }

    public String toString() {
        return "DAOException[ " + type + " ]: " + this.getMessage();
    }

    public Throwable getCause() {
        return cause;
    }

    public Class getType() {
        return type;
    }

}
