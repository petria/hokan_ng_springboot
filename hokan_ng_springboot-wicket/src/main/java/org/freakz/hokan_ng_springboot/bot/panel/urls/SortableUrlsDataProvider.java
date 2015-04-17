package org.freakz.hokan_ng_springboot.bot.panel.urls;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
@Slf4j
public class SortableUrlsDataProvider extends SortableDataProvider<Url, String> implements IFilterStateLocator<UrlFilter> {

  private UrlFilter urlFilter = new UrlFilter();

  public SortableUrlsDataProvider() {
    setSort("id", SortOrder.ASCENDING);
  }

  @Override
  public Iterator<Url> iterator(long first, long count) {
    log.debug("{} -> {} : {}", first, count, getSort());
    List<Url> allUrls = Services.getUrlLoggerService().findAll();
    Collections.sort(allUrls, new UrlComparator(getSort()));
    return allUrls.subList((int) first, (int) (first + count)).
        iterator();
  }

  @Override
  public long size() {
    return Services.getUrlLoggerService().findAll().size();
  }

  @Override
  public IModel<Url> model(Url url) {
    return new DetachableUrlModel(url);
  }

  @Override
  public UrlFilter getFilterState() {
    return this.urlFilter;
  }

  @Override
  public void setFilterState(UrlFilter urlFilter) {
    this.urlFilter = urlFilter;
  }

  static class UrlComparator implements Comparator<Url> {

    private SortParam sortParam;
    public UrlComparator(SortParam sortParam) {
      this.sortParam = sortParam;
    }

    @Override
    public int compare(Url o1, Url o2) {
      int compare = 0;
      if (sortParam == null) {
        Long id1 = o1.getId();
        Long id2 = o2.getId();
        compare = id1.compareTo(id2);
      }

      if (this.sortParam.getProperty().equals("id")) {
        Long id1 = o1.getId();
        Long id2 = o2.getId();
        compare = id1.compareTo(id2);
      } else if (this.sortParam.getProperty().equals("url")) {
        compare = o1.getUrl().compareTo(o2.getUrl());
      }
      if (sortParam.isAscending()) {
        compare = compare * -1;
      }
      return compare;
    }
  }

}
