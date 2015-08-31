package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.SearchReplace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 31.8.2015.
 *
 */
public interface SearchReplaceRepository extends JpaRepository<SearchReplace, Long> {

  List<SearchReplace> findByTheSearch(String search);

}
