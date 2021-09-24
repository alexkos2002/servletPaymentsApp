package org.kosiuk.webApp.servletPaymentsApp.model.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used to parse object from ResultSet
 *
 * @param <T> object that will be mapped
 */
public interface ObjectMapper<T> {

    /**
     * Extract object from result set
     */
    T extractFromResultSet(ResultSet rs) throws SQLException;

}

