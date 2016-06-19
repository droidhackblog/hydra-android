package be.ugent.zeus.hydra.recyclerviewholder.home;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.ugent.zeus.hydra.R;
import be.ugent.zeus.hydra.adapters.HomeCardAdapter;
import be.ugent.zeus.hydra.models.cards.HomeCard;
import be.ugent.zeus.hydra.models.cards.SchamperCard;
import be.ugent.zeus.hydra.models.schamper.Article;
import be.ugent.zeus.hydra.utils.DateUtils;

/**
 * Created by feliciaan on 17/06/16.
 */
public class SchamperViewHolder extends AbstractViewHolder {
    private TextView title;
    private TextView date;
    private TextView author;
    private ImageView image;
    private WebView webView;

    public SchamperViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
        author = (TextView) itemView.findViewById(R.id.author);
        image = (ImageView) itemView.findViewById(R.id.image);
        webView = (WebView) itemView.findViewById(R.id.webView);
    }

    @Override
    public void populate(HomeCard card) {
        if (card.getCardType() != HomeCardAdapter.HomeType.SCHAMPER) {
            return; //TODO: report errors
        }

        final SchamperCard schamperCard = (SchamperCard) card;
        final Article article = schamperCard.getArticle();

        title.setText(article.getTitle());
        date.setText(DateUtils.relativeDateString(article.getPubDate(), itemView.getContext()));
        author.setText(article.getAuthor());

        webView.setVisibility(View.GONE);

        Picasso.with(this.itemView.getContext()).load(article.getImage()).into(image);

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.getVisibility() != View.VISIBLE) {
                    loadText(article);
                    webView.setVisibility(View.VISIBLE);
                } else {
                    webView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadText(Article article) {
        //TODO: change URL to something in the schamper folder on the API
        String text = "<html><head><link rel=\"stylesheet\" href=\"https://zeus.UGent.be/hydra/api/2.0/info/schamper.css\" " +
                "type=\"text/css\"></head><body>" + article.getText() + "</body></html>";
        webView.loadData(text, "text/html; charset=utf-8", "utf-8");
    }
}
