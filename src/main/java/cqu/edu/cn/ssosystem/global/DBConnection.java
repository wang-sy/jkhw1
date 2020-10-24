package cqu.edu.cn.ssosystem.global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CQU_SSO_SYSTEM";
    private static final String USER = "root";
    private static final String PASSWORD = "Wangsy-1990085";
    private Connection conn = null;
    public DBConnection() throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2. 获得数据库连接
        this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConn() {
        return conn;
    }
}
