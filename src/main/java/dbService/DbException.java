package dbService;

/**
 * Created by aleksandr on 23.01.16.
 */
public class DbException extends Exception {
    public DbException(Throwable throwable){
        super(throwable);
    }
}
