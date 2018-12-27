package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseOperations {
    private String url = "jdbc:sqlite:CollectionApp.db";

    public void createNewDatabase() {
        /*
        Program ilk çalıştığında çalışacak method eğer database yüklü değilse kullanıcın bilgisayarında database oluşturacak.
        Eklenecek:Kullanıcının bilgisayarında databasenin bulunacağı dosya yolu oluşturulacak.Program bu dosya yolunda database oluşturacak.
        */
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(String tableName, ObservableList<String> fields) {
        String fieldName = null;
        //String fieldType = null;
        Connection conn = conn();
        for (int j = 0; j < fields.size(); j++) {
            //Listeden gelen bütün değerler (VeriTipi-VeriAdi) formatında olduğu için bütün değerleri ayırmam gerekti.
            String fieldString = fields.get(j);
            String[] fieldStrings = fieldString.split("-");
            for (int i = 0; i < fieldStrings.length; i++) {
                /*
                                if (i == 0) {
                    Veri tiplerinin sqlite veri tiplerine dönüşümleri.
                    fieldType = fieldStrings[i];
                    if (fieldType.equals("STRING")) {
                        fieldType = "TEXT";
                    } else if (fieldType.equals("INT")) {
                        fieldType = "INTEGER";
                    } else if (fieldType.equals("DOUBLE")) {
                        fieldType = "REAL";
                    }
                }
                */
                if (i == 1) {
                    fieldName = fieldStrings[i];
                }
            }
            //Gelen ilk değerlerle birlikte tablo oluşturuluyor ve gelen ilk değeri tabloya ekliyor.
            if (j == 0) {
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                        + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                        + fieldName + " TEXT"
                        + ");";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } else {
                conn = conn();
                //Bundan sonra gelen verilerin tekrar tablo oluşturması gerekmediği için oluşturulan tablonun update edilmesini sağladım.
                String sql = "ALTER TABLE " + tableName + " Add column " + fieldName + "\t" + "TEXT;";
                try (
                        Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    //Database üzerinde ki bütün tabloları arşiv adları olarak kullanıcaya göstermemize yarayan method.
    public ObservableList<String> takeAllTableName() {
        ObservableList<String> tableNames = FXCollections.observableArrayList();
        Connection conn = conn();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] tables = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null, null, "%", tables);
            while (resultSet.next()) {
                tableNames.add(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return tableNames;
    }

    public void deleteTable(String tableName) {
        Connection conn = conn();
        String sql = "DROP TABLE IF EXISTS '" + tableName + "'";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.commit();
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    public void addNewColumn(String tableName, ArrayList<String> newColumns) {
        for (int i = 0; i < newColumns.size(); i++) {
            Connection connection = conn();
            String sql = "ALTER TABLE " + tableName + " Add column " + newColumns.get(i) + "\t" + "TEXT;";
            try {
                Statement stmt = connection.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
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

    public ArrayList<String> newColumns(String tableName, ObservableList<String> fields) {
        Connection connection = conn();
        ArrayList<String> newColumns = new ArrayList<>();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            for (int i = 0; i < fields.size(); i++) {
                ResultSet resultSet = databaseMetaData.getColumns(null, null, tableName, fields.get(i));
                if (!resultSet.next()) {
                    newColumns.add(fields.get(i));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return newColumns;
    }
    public void changeTableName(String oldName,String newName){
        Connection connection = conn();
        String sql = "ALTER TABLE " + oldName+ " RENAME TO " + newName + ";";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
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

