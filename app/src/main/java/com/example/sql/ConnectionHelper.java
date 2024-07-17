package com.example.sql;

import android.util.Log;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHelper {
    private String ip = "172.1.1.0";
    private String database = "AndroidSTDB";
    private String uname = "sa";
    private String pass = "test129";
    private String port = "1433";

    public Single<String> connectToDatabase() {
        return Single.fromCallable(() -> {
            String result = "";
            Connection connection = null;
            String ConnectionURL;

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" +
                        "databasename=" + database + ";" +
                        "user=" + uname + ";" +
                        "password=" + pass + ";";
                connection = DriverManager.getConnection(ConnectionURL);

                if (connection != null) {
                    String query = "SELECT * FROM results";  // SQL 쿼리 작성
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        // 데이터를 가져와서 result 문자열에 추가
                        result += rs.getString("result_column") + "\n";
                    }
                    rs.close();
                    stmt.close();
                }
            } catch (SQLException se) {
                Log.e("SQL Error", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("Class Error", e.getMessage());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException se) {
                        Log.e("SQL Close Error", se.getMessage());
                    }
                }
            }

            return result;
        }).subscribeOn(Schedulers.io());
    }
}
