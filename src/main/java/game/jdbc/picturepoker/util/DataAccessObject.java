package game.jdbc.picturepoker.util;

import java.sql.*;

public abstract class DataAccessObject <T extends DataTransferObject> {

    protected final Connection connection;
    protected final static String LAST_VAL = "SELECT last_value FROM ";
    protected final static String PLAYER_SEQUENCE = "player_seq";

    public DataAccessObject(Connection connection) {
        super();
        this.connection = connection;
    }

    public abstract T findById(long dto);

    public abstract T create(T dto);

    public abstract T update_long(String attribute, long value, T dto);

    public abstract T update_string(String attribute, String data, T dto);
}