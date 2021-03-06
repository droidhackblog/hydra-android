package be.ugent.zeus.hydra.recyclerviewholder.home;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.activities.Hydra;
import be.ugent.zeus.hydra.adapters.HomeCardAdapter;
import be.ugent.zeus.hydra.models.cards.HomeCard;
import be.ugent.zeus.hydra.models.cards.RestoMenuCard;
import be.ugent.zeus.hydra.models.resto.RestoMeal;
import be.ugent.zeus.hydra.models.resto.RestoMenu;
import be.ugent.zeus.hydra.utils.DateUtils;

/**
 * Created by feliciaan on 06/04/16.
 */
public class RestoCardViewHolder extends AbstractViewHolder {
    private TextView title;
    private TableLayout tl;


    public RestoCardViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.category_text);
        tl = (TableLayout) itemView.findViewById(R.id.cardTableLayout);
    }

    @Override
    public void populate(HomeCard card) {
        if (card.getCardType() != HomeCardAdapter.HomeType.RESTO) {
            return; //TODO: report errors
        }

        final RestoMenuCard menuCard = (RestoMenuCard) card;
        final RestoMenu menu = menuCard.getRestoMenu();

        title.setText(DateUtils.getFriendlyDate(menu.getDate()));

        if (menu.isOpen()) {
            populateOpen(menu);
        } else {
            populateClosed();
        }

        // click listener
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open resto fragment
                if ( v.getContext() instanceof Hydra) {
                    Hydra activity = (Hydra) v.getContext();
                    activity.changeFragment(2); // TODO: replace this by more robust way!
                }
            }
        });
    }

    private void populateOpen(RestoMenu menu) {
        tl.setColumnStretchable(2, true);
        tl.removeAllViews();

        for (RestoMeal meal : menu.getMeals()) {
            TableRow tr = new TableRow(itemView.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tr.setPadding(0, 4, 0, 4);
            tr.setLayoutParams(lp);

            // set correct image according to kind
            ImageView imageView = new ImageView(itemView.getContext());
            switch (meal.getKind()) {
                case "soup":
                    imageView.setImageResource(R.drawable.soep);
                    break;
                case "meat":
                    imageView.setImageResource(R.drawable.vlees);
                    break;
                case "fish":
                    imageView.setImageResource(R.drawable.vis);
                    break;
                case "vegetarian":
                    imageView.setImageResource(R.drawable.vegi);
                    break;
                default:
                    imageView.setImageResource(R.drawable.soep);
            }

            imageView.setPadding(0,5,0,0);

            TextView tvCenter = new TextView(itemView.getContext());
            tvCenter.setPadding(25, 0, 0, 0);
            tvCenter.setLayoutParams(lp);
            tvCenter.setText(meal.getName());
            tvCenter.setTextColor(Color.parseColor("#122b44"));
            TextView tvRight = new TextView(itemView.getContext());
            tvRight.setLayoutParams(lp);
            tvRight.setText(meal.getPrice());
            tvRight.setTextColor(Color.parseColor("#122b44"));
            tvRight.setGravity(Gravity.RIGHT);

            tr.addView(imageView);
            tr.addView(tvCenter);
            tr.addView(tvRight);

            tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    private void populateClosed() {
        tl.removeAllViews();

        TableRow tr = new TableRow(itemView.getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        tr.setPadding(0, 4, 0, 4);
        tr.setLayoutParams(lp);

        TextView textView = new TextView(itemView.getContext());
        textView.setPadding(25, 0, 0, 0);
        textView.setLayoutParams(lp);
        textView.setText("sorry, gesloten");
        textView.setTextColor(Color.parseColor("#122b44"));
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        tr.addView(textView);
        tl.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
    }
}
