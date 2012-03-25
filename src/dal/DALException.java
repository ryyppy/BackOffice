package dal;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 25.03.12
 * Time: 22:11
 */
public class DALException extends RuntimeException{
    public DALException(String msg, Exception e){
        super(msg, e);
    }

    public DALException(String msg){
        this(msg, null);
    }
}
