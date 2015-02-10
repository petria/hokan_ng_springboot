package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.jpa.entity.SearchReplace;

import java.util.List;

/**
 * Created by pairio on 28.5.2014.
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
public interface SearchReplaceService {

  SearchReplace addSearchReplace(String owner, String search, String replace);

  List<SearchReplace> findSearchReplaces(String search);

  SearchReplace getSearchReplace(long id);

  List<SearchReplace> getSearchReplaces();

  void deleteSearchReplaces();

  void deleteSearchReplace(SearchReplace replace);

}
