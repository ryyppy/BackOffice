package logging;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 05.04.12
 * Time: 13:36
 */
public class LoggerNotRegisteredException extends RuntimeException{
    public LoggerNotRegisteredException(String msg){
        super(msg);
    }
}
