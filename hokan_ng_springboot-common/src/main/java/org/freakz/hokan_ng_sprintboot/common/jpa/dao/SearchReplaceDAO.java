package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.SearchReplace;

import java.util.List;

/**
 * Created by pairio on 28.5.2014.
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
public interface SearchReplaceDAO {

  SearchReplace addSearchReplace(String owner, String search, String replace) throws HokanDAOException;

  SearchReplace getSearchReplace(long id) throws HokanDAOException;

  List<SearchReplace> getSearchReplaces() throws HokanDAOException;

  List<SearchReplace> findSearchReplaces(String search) throws HokanDAOException;

  void deleteSearchReplace(SearchReplace replace) throws HokanDAOException;

  void deleteSearchReplace(String search) throws HokanDAOException;

  void deleteSearchReplaces() throws HokanDAOException;

}
