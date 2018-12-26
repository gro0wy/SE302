package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemOperations {
    private String url = "jdbc:sqlite:CollectionApp.db";

    public ArrayList<String> getColumnNames(String tableName) {
        ArrayList<String> columnNames = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Connection connection = conn();
        Statement statement;
        ResultSetMetaData resultSetMetaData;
        ResultSet resultSet;
        int columnNumber;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSetMetaData = resultSet.getMetaData();
            columnNumber = resultSetMetaData.getColumnCount();
            for (int i = 2; i <= columnNumber; i++) {
                columnNames.add(resultSetMetaData.getColumnName(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return columnNames;
        }
    }
/*
 public ArrayList<String> getColumnType(String tableName) {
        ArrayList<String> columnTypes = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Statement statement;
        ResultSetMetaData resultSetMetaData;
        ResultSet resultSet;
        Connection connection = conn();
        int columnNumber;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSetMetaData = resultSet.getMetaData();
            columnNumber = resultSetMetaData.getColumnCount();
            for (int i = 2; i <= columnNumber; i++) {
                columnTypes.add(resultSetMetaData.getColumnTypeName(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return columnTypes;
        }
    }
*/
    public void insertItem(String tableName, ArrayList<String> userInputs) {
        String value=null;
        for (int i = 0; i < userInputs.size(); i++) {
            Connection connection = conn();
            if (i == 0) {
                value = userInputs.get(i);
                String sql = "INSERT INTO " + tableName + "(" + getColumnNames(tableName).get(i) + ") values(\"" + userInputs.get(i) + "\");";
                try (
                        Statement stmt = connection.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            else{
                String sql = "update "+tableName+" set "+getColumnNames(tableName).get(i)+"=\""+userInputs.get(i)+"\" where "+getColumnNames(tableName).get(0)+"=\""+value+"\"";
                try (
                        Statement stmt = connection.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }
    private Connection conn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        }
        return conn;
    }
}

