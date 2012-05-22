package config;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 17.04.12
 * Time: 13:25
 */
public class ConfigException extends Exception {
    public ConfigException(String msg, Exception e){
        super(msg, e);
    }

    public ConfigException(String msg){
        super(msg);
    }
}
