package com.sefryek.doublepizza.service.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 4, 2012
 * Time: 8:23:42 PM
  */
public class ServiceException extends Exception {

    private Throwable cause;
    private Class type;

    public ServiceException(Throwable cause,Class type,String msg){
        super(msg);
        this.cause = cause;
        this.type= type;
    }

    public String toString(){
        return "ServiceException[ "+type+" ]"
//                + "\n" + cause.toString()
                ;
    }

    public Throwable getCause() {
        return cause;
    }

    public Class getType() {
        return type;
    }
}
