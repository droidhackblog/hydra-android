package be.ugent.zeus.hydra.requests;

import com.octo.android.robospice.persistence.DurationInMillis;

import be.ugent.zeus.hydra.models.association.Activities;

/**
 * Created by feliciaan on 27/01/16.
 */
public class ActivitiesRequest extends AbstractRequest<Activities> {

    public ActivitiesRequest() {
        super(Activities.class);
    }

    public String getCacheKey() {
        return "all_activities.json";
    }

    @Override
    protected String getAPIUrl() {
        return DSA_API_URL + "all_activities.json";
    }

    @Override
    public long getCacheDuration() {
        return DurationInMillis.ONE_MINUTE * 15;
    }
}
