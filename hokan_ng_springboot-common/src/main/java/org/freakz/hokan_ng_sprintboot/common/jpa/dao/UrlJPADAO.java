package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Url;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: petria
 * Date: 12/14/13
 * Time: 10:11 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Repository("Url")
@Transactional
public class UrlJPADAO implements UrlDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Url> findUrls(String url, String... nicks) {

    TypedQuery<Url> query;
    if (nicks == null || nicks.length == 0) {
      query = entityManager.createQuery("SELECT url FROM Url url WHERE url.url LIKE :url OR url.urlTitle LIKE :url ORDER BY url.created DESC", Url.class);
    } else {
      List<String> nickList = Arrays.asList(nicks);
      query = entityManager.createQuery("SELECT url FROM Url url WHERE (url.url LIKE :url OR url.urlTitle LIKE :url) AND url.sender IN (:nickList) ORDER BY url.created DESC", Url.class);
      query.setParameter("nickList", nickList);
    }
    query.setParameter("url", "%" + url + "%");
    try {
      return query.getResultList();
    } catch (Exception e) {
      //
    }
    return null;
  }

  @Override
  public Url findUrl(String url, String... nicks) {
    List<Url> urlList = findUrls(url, nicks);
    if (urlList.size() == 1) {
      return urlList.get(0);
    }
    return null;
  }

  @Override
  public Url storeUrl(Url entity) {
    return entityManager.merge(entity);
  }

  @Override
  public Url createUrl(String url, String sender, String channel, Date date) {
    Url entity = new Url(url, sender, channel, date);
    entityManager.persist(entity);
    return entity;
  }

  @Override
  public List findTopSenderByChannel(String channel) {
    Query query = entityManager.createQuery("SELECT url, count(url) FROM Url url WHERE url.channel = :channel GROUP BY url.sender ORDER BY 2 DESC");
    query.setParameter("channel", channel);
    return query.getResultList();
  }

  @Override
  public List findTopSender() {
    Query query = entityManager.createQuery("SELECT url, count(url) FROM Url url GROUP BY url.sender ORDER BY 2 DESC");
    return query.getResultList();
  }

}
