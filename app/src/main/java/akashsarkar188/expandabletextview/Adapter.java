package akashsarkar188.expandabletextview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Doc> list;

    public Adapter(Context context, ArrayList<Doc> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.display_card_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doc model = list.get(position);

        holder.journal.setText(model.getJournal());
        holder.essin.setText(model.getEissn());
        holder.researchType.setText(model.getArticleType());
        holder.AuthorList.setText(model.getAuthorDisplay().toString().replace("]","").replace("[",""));
        if (model.getAbstract().toString().replace("]","").replace("[","").trim().isEmpty()){
            holder.abstractData.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.abstractData.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            holder.abstractData.setText("No Data");
        } else {
            holder.abstractData.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.abstractData.setText(model.getAbstract().toString().replace("]","").replace("[","").trim());
        }

        holder.abstractData.setOnClickListener(view -> cycleTextViewExpansion(holder.abstractData));
        holder.publicationDate.setText(model.getPublicationDate());
        holder.title.setText(model.getTitleDisplay());
    }

    private void cycleTextViewExpansion(TextView tv) {
        try {
            int collapsedMaxLines = 4;
            int duration = (tv.getLineCount() - collapsedMaxLines) * 15;
            if (tv.getLineCount() > 4) {
                if (tv.getMaxLines() == collapsedMaxLines) {
                    tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, context.getResources().getDrawable(R.drawable.up_arrow));
                } else {
                    tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, context.getResources().getDrawable(R.drawable.down_arrow));
                }
            }
            ObjectAnimator animation = ObjectAnimator.ofInt(tv,
                    "maxLines",
                    tv.getMaxLines() == collapsedMaxLines ? tv.getLineCount() : collapsedMaxLines);
            animation.setDuration(duration).start();
        } catch (Exception e) {
            Log.e("CollapseTextView", "cycleTextViewExpansion: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView journal, essin, researchType, title, AuthorList, abstractData, publicationDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            journal = itemView.findViewById(R.id.journal);
            essin = itemView.findViewById(R.id.essin);
            researchType = itemView.findViewById(R.id.researchType);
            title = itemView.findViewById(R.id.title);
            AuthorList = itemView.findViewById(R.id.AuthorList);
            abstractData = itemView.findViewById(R.id.abstractData);
            publicationDate = itemView.findViewById(R.id.publicationDate);

        }
    }
}
