package dbService.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 23.01.16.
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
