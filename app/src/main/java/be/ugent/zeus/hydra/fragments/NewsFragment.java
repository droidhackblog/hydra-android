package be.ugent.zeus.hydra.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.adapters.NewsAdapter;
import be.ugent.zeus.hydra.models.association.News;
import be.ugent.zeus.hydra.requests.NewsRequest;

/**
 * Created by Ellen on 07/04/2016.
 */
public class NewsFragment extends AbstractFragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View layout;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_schamper_articles, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) layout.findViewById(R.id.progressBar);

        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        performLoadActivityRequest();

        return layout;
    }

    private void performLoadActivityRequest() {

        final NewsRequest r = new NewsRequest();
        spiceManager.execute(r, r.getCacheKey(), r.getCacheDuration(), new RequestListener<News>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                showFailureSnackbar();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onRequestSuccess(final News news) {
                adapter.setItems(news);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showFailureSnackbar() {
        Snackbar
                .make(layout, "Oeps! Kon nieuws niet ophalen.", Snackbar.LENGTH_LONG)
                .setAction("Opnieuw proberen", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        performLoadActivityRequest();
                    }
                })
                .show();
    }
}
