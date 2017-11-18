package com.sefryek.doublepizza.scheduler;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 2/3/14
 * Time: 7:03 PM
 */
public abstract class Scheduler  {
    public abstract  void schedule(Runnable task , Date date);

}
