package be.ugent.zeus.hydra.fragments.settings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.design.widget.Snackbar;
import android.view.View;
import be.ugent.zeus.hydra.HydraApplication;
import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.models.association.Association;
import be.ugent.zeus.hydra.models.association.Associations;
import be.ugent.zeus.hydra.requests.AssociationsRequest;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rien Maertens
 * @since 16/02/2016.
 *
 *
SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
boolean sf = sharedPrefs.getBoolean("pref_association_checkbox", false);
 */
public class ActivityFragment extends PreferenceFragment {
    protected SpiceManager spiceManager = new SpiceManager(GsonSpringAndroidSpiceService.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.activities);

        performRequest();
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        HydraApplication happ = (HydraApplication) getActivity().getApplication();
        happ.sendScreenName("settings");
    }

    private void addPreferencesFromRequest(final Associations assocations) {
        Set<String> set = new HashSet<>();
        PreferenceScreen target = (PreferenceScreen) findPreference("associationPrefListScreen");

        if(assocations!=null) {

            for (int i = 0; i < assocations.size(); i++) {
                Association asso = assocations.get(i);
                PreferenceCategory parentCategory;
                if(!set.contains(asso.getParentAssociation())){
                    parentCategory = new PreferenceCategory(target.getContext());
                    parentCategory.setKey(asso.getParentAssociation());
                    parentCategory.setTitle(asso.getParentAssociation());
                    target.addPreference(parentCategory);
                    set.add(asso.getParentAssociation());
                }
                parentCategory = (PreferenceCategory) findPreference(asso.getParentAssociation());
                CheckBoxPreference checkBoxPreference = new CheckBoxPreference(target.getContext());
                checkBoxPreference.setKey(asso.getName());
                checkBoxPreference.setChecked(false);
                checkBoxPreference.setTitle(asso.getName());
                parentCategory.addPreference(checkBoxPreference);

            }

        }
    }

    private void performRequest() {
        final AssociationsRequest r = new AssociationsRequest();
        spiceManager.execute(r, r.getCacheKey(), r.getCacheDuration(), new RequestListener<Associations>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                showFailureSnackbar();
            }

            @Override
            public void onRequestSuccess(final Associations assocations) {
                Collections.sort(assocations, new Comparator<Association>() {
                    @Override
                    public int compare(Association lhs, Association rhs) {
                        return lhs.getName().compareToIgnoreCase(rhs.getName());
                    }
                });
                addPreferencesFromRequest(assocations);
            }
        });
    }

    private void showFailureSnackbar() {
        if(getView()!=null) {
            Snackbar
                    .make(getView(), "Oeps! Kon verenigingen niet ophalen.", Snackbar.LENGTH_LONG)
                    .setAction("Opnieuw proberen", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            performRequest();
                        }
                    })
                    .show();
        }else{
            //cry
        }
    }
}

