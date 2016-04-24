package www.mytest.ru.mcttest.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import www.mytest.ru.mcttest.R;
import www.mytest.ru.mcttest.entity.Result;

/**
 * Created by Eugene on 22.04.2016.
 */
public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder> {

    private List<Result> resultList;
    private Context context;

    public ResultRecyclerViewAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @Override
    public ResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResultRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(resultList.get(position).getArtworkUrl100())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(holder.imageView);
        holder.nameTextView.setText(resultList.get(position).getArtistName());
        holder.trackNameTextView.setText(resultList.get(position).getTrackName());
        Double price = resultList.get(position).getTrackPrice();
        holder.priceTextView.setText((price != null && price > 0) ? price + " $" : "free");

        animate(holder);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public boolean onFailedToRecycleView(ViewHolder holder) {
        return true;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        Animation animation = holder.itemView.getAnimation();
        if(animation != null) {
            animation.cancel();
            holder.itemView.setAlpha(1f);
        }
    }

    public void refreshData(List<Result> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setAlpha(0.2f);
        viewHolder.itemView.setTranslationY(-20);
        ViewCompat.animate(viewHolder.itemView)
                .alpha(1f)
                .translationY(0)
                .setDuration(500)
                .start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView trackNameTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            trackNameTextView = (TextView) itemView.findViewById(R.id.trackNameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
        }
    }
}