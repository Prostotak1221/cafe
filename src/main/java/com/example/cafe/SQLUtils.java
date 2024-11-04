package com.example.cafe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLUtils {

    private static void handleSQLException(SQLException e) {
        e.printStackTrace();
    }

    public static List<String> getColumnsNames(Connection connection, String tableName) {
        List<String> columnNames = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, null)) {
            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return columnNames;
    }

    public static List<String> getTablesNames(Connection connection) {
        List<String> tableNames = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getTables(null, "dbo", "%", new String[]{"TABLE"})) {
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tableNames;
    }

    public static List<List<String>> getDataFromTable(Connection connection, String tableName) {
        List<List<String>> tableData = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(resultSet.getString(i));
                }
                tableData.add(rowData);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return tableData;
    }

    private static String buildUpdateQuery(Connection connection, String tableName, List<String> columnNames, List<String> rowData, String originalId) {
        List<String> columnValuePairs = new ArrayList<>();
        for (int i = 1; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            String columnValue = rowData.get(i);
            if (isDateTimeColumn(connection, tableName, columnName)) {
                try {
                    // Convert the datetime value to the appropriate format expected by SQL Server
                    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(columnValue);
                    columnValue = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately (e.g., log the error, throw an exception)
                }
            }
            columnValuePairs.add(columnName + " = N'" + columnValue + "'");
        }

        return "UPDATE " + tableName + " SET " +
                String.join(", ", columnValuePairs) +
                " WHERE id = '" + originalId + "'";
    }

    private static String[] getColumnValuePairs(List<String> columnNames, List<String> rowData) {
        return columnNames.subList(1, columnNames.size()).stream()
                .map(col -> col + " = N'" + rowData.get(columnNames.indexOf(col)) + "'")
                .toArray(String[]::new);
    }

    public static void updateDataInTable(Connection connection, String tableName, List<List<String>> newData) {
        try {
            List<String> columnNames = getColumnsNames(connection, tableName);
            for (List<String> rowData : newData) {
                String originalId = rowData.get(0);
                String updateQuery = buildUpdateQuery(connection, tableName, columnNames, rowData, originalId);
                executeUpdateQuery(connection, updateQuery);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void deleteRowsInTable(Connection connection, String tableName, List<List<String>> rowsToRemove) {
        try {
            for (List<String> rowData : rowsToRemove) {
                String originalId = rowData.get(0);
                String deleteQuery = "DELETE FROM " + tableName + " WHERE id = '" + originalId + "'";
                executeUpdateQuery(connection, deleteQuery);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void executeUpdateQuery(Connection connection, String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    private static String buildInsertQuery(Connection connection, String tableName, List<String> columnNames, List<String> rowData) {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            String columnValue = rowData.get(i);
            if (isDateTimeColumn(connection, tableName, columnName)) {
                try {
                    // Convert the datetime value to the appropriate format expected by SQL Server
                    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(columnValue);
                    columnValue = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately (e.g., log the error, throw an exception)
                }
            }
            columns.add(columnName);
            values.add("N'" + columnValue + "'");
        }

        String columnsStr = String.join(", ", columns);
        String valuesStr = String.join(", ", values);

        return "INSERT INTO " + tableName + " (" + columnsStr + ") VALUES (" + valuesStr + ")";
    }

    private static boolean isDateTimeColumn(Connection connection, String tableName, String columnName) {
        try (ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, columnName)) {
            if (resultSet.next()) {
                int dataType = resultSet.getInt("DATA_TYPE");
                return dataType == java.sql.Types.TIMESTAMP || dataType == java.sql.Types.DATE;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    public static void insertRowsInTable(Connection connection, String tableName, List<List<String>> newData) {
        try {
            List<String> columnNames = getColumnsNames(connection, tableName);
            for (List<String> rowData : newData) {
                String insertQuery = buildInsertQuery(connection, tableName, columnNames, rowData);
                executeUpdateQuery(connection, insertQuery);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
}
