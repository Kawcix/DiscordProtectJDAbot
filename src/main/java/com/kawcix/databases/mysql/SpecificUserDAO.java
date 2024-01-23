package com.kawcix.databases.mysql;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpecificUserDAO {

    static final Pass pass = new Pass();

    private final static String DBURL = pass.getDBURL();
    private final static String DBUSER = pass.getDBUSER();
    private final static String DBPASS = pass.getDBPASS();
    private final static String DBDRIVER = pass.getDBDRIVER();

    private Connection connection;

    private Statement statement;

    private String query;

    private final SQLSpecificUserParser sqlSpecificUserParser;

    public SpecificUserDAO() {
        sqlSpecificUserParser = new SQLSpecificUserParser();
    }

    public void add(SpecificUser specificUser) {
        query = sqlSpecificUserParser.createAddQuery(specificUser);

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

    public void delete(SpecificUser specificUser) {

        query = sqlSpecificUserParser.createDeleteQuery(specificUser);

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void update(SpecificUser specificUser) {
        query = sqlSpecificUserParser.createUpdateQuery(specificUser);

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean usersExistsCheck(SpecificUser specificUser) {
        query = sqlSpecificUserParser.createCheckQuery(specificUser);
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

    public String getValue(SpecificUser specificUser, SQLSpecificUserParser.userField userField) {
        query = sqlSpecificUserParser.createGetValueQuery(specificUser, userField);
        String result = null;

        try {
            Class.forName(DBDRIVER).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result = resultSet.getString(1);
            }

            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException |
                 NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<SpecificUser> getUsersDataOnServer(String serverId)
    {
        List<SpecificUser> specificUserList = new ArrayList<>();
        query = "SELECT * FROM users WHERE server_id="+serverId;
        try (
                // Tworzenie połączenia z bazą danych
                Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
                // Tworzenie obiektu PreparedStatement
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                // Wykonanie zapytania i pobranie wyników
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Przetwarzanie wyników zapytania
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                long server_Id = resultSet.getLong("server_id");
                String userId = resultSet.getString("user_id");
                Integer offenceLimit = resultSet.getInt("offence_limit");
                Integer channelDeleteLimit = resultSet.getInt("channel_delete_limit");
                Integer pingOffenceLimit = resultSet.getInt("ping_offence_limit");



                if(offenceLimit == 0) {
                    offenceLimit = null;
                }

                if ( channelDeleteLimit == 0 )
                {
                    channelDeleteLimit = null;
                }

                if ( pingOffenceLimit == 0)
                {
                    pingOffenceLimit = null;
                }



                SpecificUser specificUser = new SpecificUserBuilder()
                        .withUser_id(userId)
                        .withServer_id(String.valueOf(server_Id))
                        .withPingOffence(pingOffenceLimit)
                        .withOffence(offenceLimit)
                        .withChannelDeleteOffence(channelDeleteLimit)
                        .build();

                specificUserList.add(specificUser);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return specificUserList;
    }

}



