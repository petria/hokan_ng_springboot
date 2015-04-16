package org.freakz.hokan_ng_springboot.bot.panel.urls;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class SortableUrlsDataProvider extends SortableDataProvider<Url, String> implements IFilterStateLocator<UrlFilter> {

  private UrlFilter urlFilter = new UrlFilter();

  public SortableUrlsDataProvider() {
    setSort("created", SortOrder.ASCENDING);
  }

  @Override
  public Iterator<Url> iterator(long first, long count) {
    List<Url> allUrls = Services.getUrlLoggerService().findAll();

    return allUrls.subList((int)first, (int)(first + count)).
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
    return null;
  }

  @Override
  public void setFilterState(UrlFilter urlFilter) {

  }
}
