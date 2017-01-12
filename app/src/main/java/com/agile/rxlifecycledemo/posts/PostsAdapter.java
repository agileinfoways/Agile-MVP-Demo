package com.agile.rxlifecycledemo.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agile.rxlifecycledemo.R;
import com.agile.rxlifecycledemo.utils.LongClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by agile-01 on 8/17/2016.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder> {

    private List<PostModel> postModels;
    private LongClickListener<PostModel> longClickListener;
    private LayoutInflater inflater;

    public PostsAdapter(Context context, List<PostModel> postModels) {
        this.postModels = postModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_post, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        final PostModel postModel = postModels.get(position);
        holder.tvTitle.setText(postModel.getTitle());
        holder.tvBody.setText(postModel.getBody());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener == null) return false;
                longClickListener.onLongClick(postModel);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    public void setLongClickListener(LongClickListener<PostModel> longClickListener) {
        this.longClickListener = longClickListener;
    }

    public List<PostModel> getList() {
        return postModels;
    }

    public void add(PostModel postModel) {
        getList().add(0, postModel);
        notifyItemInserted(0);
    }

    public void addAll(List<PostModel> postModels) {
        getList().addAll(0, postModels);
        notifyItemRangeInserted(0, getItemCount() - 1);
    }

    public void remove(PostModel postModel) {
        int indexOfModelToRemove = getList().indexOf(postModel);
        getList().remove(postModel);
        notifyItemRemoved(indexOfModelToRemove);
    }

    public void clear() {
        getList().clear();
        notifyDataSetChanged();
    }

    static class PostHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_body)
        TextView tvBody;

        public PostHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
