package cqu.edu.cn.ssosystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTools {
    public static ArrayList<String> getStrings(ResultSet rs, String getItem) throws SQLException {
        ArrayList<String> resList = new ArrayList<String >();
        while(rs.next()){
            resList.add(rs.getString(getItem));
        }
        return resList;
    }
}
