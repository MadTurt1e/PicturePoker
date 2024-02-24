package game.jdbc.picturepoker;

public class JDBCExecutor {
    public static void main (String[]args){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db", "picturepoker", "postgres", "password");
    }
}
