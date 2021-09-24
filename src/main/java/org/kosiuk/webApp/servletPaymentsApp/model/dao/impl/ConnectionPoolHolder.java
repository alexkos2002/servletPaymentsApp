package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl;

import javax.sql.DataSource;
import java.util.ResourceBundle;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPoolHolder {

    private static volatile DataSource dataSource;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("db/database");

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(resourceBundle.getString("database.url"));
                    ds.setUsername(resourceBundle.getString("database.username"));
                    ds.setPassword(resourceBundle.getString("database.password"));
                    ds.setDriverClassName(resourceBundle.getString("database.driver"));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }

}
