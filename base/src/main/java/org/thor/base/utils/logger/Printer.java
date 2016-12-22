package org.thor.base.utils.logger;


public interface Printer {

  Printer t(String tag, int methodCount);

  Settings init(String tag);

  Settings getSettings();


  void d(Object object);







  void json(String json);


  void log(int priority, String tag, String message, Throwable throwable);


}
