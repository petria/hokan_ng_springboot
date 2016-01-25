package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.SearchReplace;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.SearchReplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Petri Airio on 31.8.2015.
 *
 */
@Service
public class SearchReplaceRepositoryService implements SearchReplaceService {

  @Autowired
  private SearchReplaceRepository repository;

  @Override
  @Transactional(readOnly = true)
  public SearchReplace addSearchReplace(String sender, String search, String replace) {
    SearchReplace searchReplace = new SearchReplace(sender, search, replace);
    return repository.save(searchReplace);
  }

  @Override
  @Transactional(readOnly = false)
  public List<SearchReplace> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteAll() {
    repository.deleteAll();
  }

  @Override
  @Transactional(readOnly = false)
  public void delete(SearchReplace sr) {
    repository.delete(sr);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SearchReplace> findByTheSearch(String search) {
    return repository.findByTheSearch(search);
  }

  @Override
  @Transactional(readOnly = true)
  public SearchReplace findOne(long id) {
    return repository.findOne(id);
  }

}
