package com.sefryek.test.dbremoteconnection;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 1, 2012
 * Time: 12:14:37 PM
 */
import java.sql.*;

/**
 * Microsoft SQL Server JDBC test program
 */
public class SqlserverTest {
    public SqlserverTest() throws Exception {
//        Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://69.159.240.80:2711;databasename=DblCentral;username=WebSQL;password=Double3435009;");
//        Connection connection = DriverManager.getConnection("jdbc:sqlserver://68.71.44.66:2711;databasename=DblCentral;username=sa;password=XxX123;");
//        Connection connection = DriverManager.getConnection("jdbc:sqlserver://192.168.1.14:1433;databasename=DblCentral;username=sa;");
        DriverManager.setLoginTimeout(60);
        System.err.println("DriverManager.getLoginTimeout() = " + DriverManager.getLoginTimeout());

        if (connection != null) {
            Statement st = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("select * from Storemast");
            ResultSetMetaData rsMetaData =  rs.getMetaData();
            for (int i=1; i<=rsMetaData.getColumnCount(); i++) {
                System.out.print(rsMetaData.getColumnName(i) + "\t");
            }
            System.out.print("\n");
            int index = 1;
            while(rs.next()){
                System.out.print(rs.getObject(index++) + "\t");
                if (rsMetaData.getColumnCount() == index){
                    System.out.print("\n");
                    index = 1;
                }

            }
        }
    }
    public static void main (String args[]) throws Exception {
        new SqlserverTest();
//        IOUtils.copyFolder(new File("c:\\slides"), new File("c:\\slides2"));
    }
}