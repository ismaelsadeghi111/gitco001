package com.sefryek.common;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Apr 29, 2012
 * Time: 1:46:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    private double x;
    private double y;

    public Point(){
        
    }

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
