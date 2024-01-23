package com.kawcix.databases.mysql;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class SpecificServerDAO {

    static final Pass pass = new Pass();

    private final static String DBURL = pass.getDBURL();
    private final static String DBUSER = pass.getDBUSER();
    private final static String DBPASS = pass.getDBPASS();
    private final static String DBDRIVER = pass.getDBDRIVER();

    private Connection connection;

    private Statement statement;

    private String query;

    private final SQLSpecificServerParser sqlSpecificServerParser;

    public SpecificServerDAO() {
        sqlSpecificServerParser = new SQLSpecificServerParser();
    }

    public void add(SpecificServer specificServer) {

        query = sqlSpecificServerParser.createAddQuery(specificServer);

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 InvocationTargetException | NoSuchMethodException e) {

            e.printStackTrace();
        }
    }

    public boolean serverExistsCheck(SpecificServer specificServer) {
        query = sqlSpecificServerParser.createCheckQuery(specificServer);
        String result = null;

        ResultSet resultSet;

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result = resultSet.getString(1);
            }

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result.equals("1");
    }

    public void update(SpecificServer specificServer) {
        query = sqlSpecificServerParser.createUpdateQuery(specificServer);

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 InvocationTargetException | NoSuchMethodException e) {

            e.printStackTrace();
        }
    }

    public Integer getCooldown(SpecificServer specificServer) {

        query = sqlSpecificServerParser.createGetQuery(specificServer);
        ResultSet resultSet;
        String result = null;

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result == null ? null : Integer.valueOf(result);

    }
}
