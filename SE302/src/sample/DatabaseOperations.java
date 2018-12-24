package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


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
        String url = "jdbc:sqlite:CollectionApp.db";
        String fieldName = null;
        String fieldType = null;
        for (int j = 0; j < fields.size(); j++) {
            //Listeden gelen bütün değerler (VeriTipi-VeriAdi) formatında olduğu için bütün değerleri ayırmam gerekti.
            String fieldString = fields.get(j);
            String[] fieldStrings = fieldString.split("-");
            for (int i = 0; i < fieldStrings.length; i++) {
                if (i == 0) {
                    //Veri tiplerinin sqlite veri tiplerine dönüşümleri.
                    fieldType = fieldStrings[i];
                    if (fieldType.equals("STRING")) {
                        fieldType = "TEXT";
                    } else if (fieldType.equals("INT")) {
                        fieldType = "INTEGER";
                    } else if (fieldType.equals("DOUBLE")) {
                        fieldType = "REAL";
                    }
                }
                if (i == 1) {
                    fieldName = fieldStrings[i];
                }
            }
            //Gelen ilk değerlerle birlikte tablo oluşturuluyor ve gelen ilk değeri tabloya ekliyor.
            if (j == 0) {
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                        + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                        + fieldName + " " + fieldType
                        + ");";

                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                //Bundan sonra gelen verilerin tekrar tablo oluşturması gerekmediği için oluşturulan tablonun update edilmesini sağladım.
                String sql = "ALTER TABLE " + tableName + " Add column " + fieldName + "\t" + fieldType + ";";
                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
//Database üzerinde ki bütün tabloları arşiv adları olarak kullanıcaya göstermemize yaran method.
    public ObservableList<String> takeAllTableName() {
        ObservableList<String> tableNames = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String [] tables = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null,null,"%",tables);
            while (resultSet.next()){
                tableNames.add(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  tableNames;
    }
}

