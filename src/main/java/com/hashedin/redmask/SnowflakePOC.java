package com.hashedin.redmask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class SnowflakePOC {
  private static final String REDSHIFT_DB_URL = "jdbc:snowflake://<-ACCOUNT_NAME->.snowflakecomputing.com/";
  private static final String MASTER_USERNAME = "<---USER NAME-->";
  private static final String MASTER_PASSWORD = "<---USER PASSWORD-->";
  private static final String MASTER_DATABASE = "<---DATABASE NAME-->";
  private static final String SCHEMA_NAME = "<---SCHEMA NAME-->";

  public static void main(String[] args) {
    Connection conn = null;
    Statement stmt = null;
    try {

      Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");

      //Open a connection and define properties.
      System.out.println("Connecting to database...");
      Properties props = new Properties();

      props.setProperty("user", MASTER_USERNAME);
      props.setProperty("password", MASTER_PASSWORD);
      props.setProperty("db", MASTER_DATABASE);
      props.setProperty("schema", SCHEMA_NAME);
      conn = DriverManager.getConnection(REDSHIFT_DB_URL, props);

      //Try a simple query.
      System.out.println("dropping table customer if it exists");
      stmt = conn.createStatement();
      String sql;
      sql = "DROP TABLE IF EXISTS customer CASCADE;";
      stmt.execute(sql);

      sql = "CREATE TABLE customer(\n" +
          "  name text,\n" +
          "  email text,\n" +
          "  age integer,\n" +
          "  DOB date,\n" +
          "  interest float,\n" +
          "  card text\n" +
          ");";
      stmt.execute(sql);

      sql = "insert into customer VALUES ('User Alpha','useralpha@email.com',1,'2019-07-26',5.4,'1234-5679-8723-8789');";
      stmt.execute(sql);

      sql = "insert into customer VALUES ('User Beta','userbeta@email.com',2,'2019-06-25',6.4,'1234-5679-3478-6872');";
      stmt.execute(sql);

      sql = " create or replace view mymaskedcustomer as select string_mask(name,'*',2,1) as name, email, age from customer; ";
      stmt.execute(sql);

      sql = "select * from mymaskedcustomer";
      ResultSet rs = stmt.executeQuery(sql);
      //Get the data from the result set.

      while (rs.next()) {
        //Retrieve two columns.

        String name = rs.getString("NAME");
        String email = rs.getString("EMAIL");
        int age = rs.getInt("AGE");
        //Display values.
        System.out.println(name + ":" + email + "|" + age);
      }
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      //For convenience, handle all errors here.
      ex.printStackTrace();
    } finally {
      //Finally block to close resources.
      try {
        if (stmt != null)
          stmt.close();
      } catch (Exception ex) {
      }// nothing we can do
      try {
        if (conn != null)
          conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("Finished connectivity test.");
  }
}
