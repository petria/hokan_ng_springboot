package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Url;

import java.util.Date;
import java.util.List;

/**
 * User: petria
 * Date: 12/14/13
 * Time: 10:11 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface UrlDAO {

  Url findUrl(String url, String... nicks);

  List<Url> findUrls(String url, String... nicks);

  Url storeUrl(Url entity);

  Url createUrl(String url, String sender, String channel, Date date);

  List findTopSenderByChannel(String channel);

  List findTopSender();

}
