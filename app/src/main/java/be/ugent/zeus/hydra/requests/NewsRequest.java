package be.ugent.zeus.hydra.requests;

import com.octo.android.robospice.persistence.DurationInMillis;

import be.ugent.zeus.hydra.models.association.News;

/**
 * Created by feliciaan on 04/02/16.
 */
public class NewsRequest extends AbstractRequest<News> {

    public NewsRequest() {
        super(News.class);
    }

    public String getCacheKey() {
        return "association_news";
    }

    @Override
    protected String getAPIUrl() {
        return DSA_API_URL + "all_news.json";
    }

    @Override
    public long getCacheDuration() {
        return DurationInMillis.ONE_DAY;
    }
}
