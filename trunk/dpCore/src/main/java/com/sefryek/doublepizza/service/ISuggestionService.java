package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Suggestion;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 6, 2012
 * Time: 10:12:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ISuggestionService {
    public static String BEAN_NAME = "suggestionService";

    public List<Suggestion> findAll() throws DAOException, ServiceException;
}
