package com.iluwatar.serialized.lob;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.jdom2.JDOMException;


/**
 * Serialized LOB design pattern.
 * Saves a graph of objects by serializing them into a single large object (LOB), which is then stored in a database.
 *
 * The following code will implement this pattern by instantiating two objects; Department and Customer.
 * I will be serializing the department field of a customer into a textual character LOB (CLOB). I have chosen to
 * use XML to perform the serialization. The CLOBs can then be inserted or loaded from the database.
 */
@Slf4j
public final class App {
    private static final String DB_URL = "jdbc:h2:~/test";

    /**
     * Private constructor.
     */
    private App() {}

    /**
     * Program entry point.
     *
     * @param args command line args
     * @throws SQLException  if error occurs
     * @throws IOException   if error occurs
     * @throws JDOMException if error occurs
     */
    public static void main(final String[] args) throws SQLException, IOException, JDOMException {
        // Create database
        final var dataSource = createDataSource();
        deleteSchema(dataSource);
        createSchema(dataSource);

        // Initialize customer and department objects
        var customer = new Customer("customer1", 1);
        var department1 = new Department("department1");
        var department2 = new Department("department2");
        var department3 = new Department("department3");
        department1.getSubsidiaries().add(department2);
        department2.getSubsidiaries().add(department3);
        customer.getDepartments().add(department1);

        // Insert the customer into the database
        var id = customer.insert(dataSource);
        // Load the customer from the database as an XML element
        var element = customer.load(id, dataSource);
        LOGGER.info(Customer.elementToString(customer.departmentsToXMLElement()));
        customer.readDepartments(element);
        LOGGER.info(Customer.elementToString(customer.departmentsToXMLElement()));

        deleteSchema(dataSource);
    }

    /**
     * Helper method for deleting SQL schema
     *
     * @param dataSource Datasource to connect to
     * @throws SQLException if error occurs
     */
    private static void deleteSchema(final DataSource dataSource)
            throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(Customer.DELETE_SCHEMA_SQL);
        }
    }

    /**
     * Helper method for creating SQL schema
     *
     * @param dataSource Datasource to connect to
     * @throws SQLException if error occurs
     */
    private static void createSchema(final DataSource dataSource)
            throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(Customer.CREATE_SCHEMA_SQL);
        }
    }

    /**
     * Helper method for creating SQL data source
     *
     * @return Datasource
     */
    private static DataSource createDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        return dataSource;
    }
}