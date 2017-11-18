package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.SuggestionDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Suggestion;
import com.sefryek.doublepizza.service.ISuggestionService;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 6, 2012
 * Time: 10:15:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuggestionServiceImpl implements ISuggestionService {
    private SuggestionDAO suggestionDAO;

    public void setSuggestionDAO(SuggestionDAO suggestionDAO) {
        this.suggestionDAO = suggestionDAO;
    }

    public List<Suggestion> findAll() throws DAOException, ServiceException {
        return suggestionDAO.findAll();
    }
}
