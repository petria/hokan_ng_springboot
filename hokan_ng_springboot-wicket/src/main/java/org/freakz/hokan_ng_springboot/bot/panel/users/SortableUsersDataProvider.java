package org.freakz.hokan_ng_springboot.bot.panel.users;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
@Slf4j
public class SortableUsersDataProvider extends SortableDataProvider<User, String> {

//  private UrlFilter urlFilter = new UrlFilter();

  public SortableUsersDataProvider() {
    setSort("id", SortOrder.ASCENDING);
  }

  @Override
  public Iterator<User> iterator(long first, long count) {
    log.debug("{} -> {} : {}", first, count, getSort());
    List<User> allUrls = Services.getUserService().findAll();
    Collections.sort(allUrls, new UserComparator(getSort()));
    return allUrls.subList((int) first, (int) (first + count)).
        iterator();
  }

  @Override
  public long size() {
    return Services.getUserService().findAll().size();
  }

  @Override
  public IModel<User> model(User user) {
    return new DetachableUserModel(user);
  }

  /*
  @Override
  public UrlFilter getFilterState() {
    return this.urlFilter;
  }

  @Override
  public void setFilterState(UrlFilter urlFilter) {
    this.urlFilter = urlFilter;
  }
*/
  static class UserComparator implements Comparator<User> {

    private SortParam sortParam;

    public UserComparator(SortParam sortParam) {
      this.sortParam = sortParam;
    }

    @Override
    public int compare(User o1, User o2) {
      int compare = 0;
      if (sortParam == null) {
        Long id1 = o1.getId();
        Long id2 = o2.getId();
        compare = id1.compareTo(id2);
      } else {
        if (this.sortParam.getProperty().equals("id")) {
          Long id1 = o1.getId();
          Long id2 = o2.getId();
          compare = id1.compareTo(id2);
        }
/*      else if (this.sortParam.getProperty().equals("url")) {
        compare = o1.getUrl().compareTo(o2.getUrl());
      }*/
        if (sortParam.isAscending()) {
          compare = compare * -1;
        }
      }

      return compare;
    }
  }

}
