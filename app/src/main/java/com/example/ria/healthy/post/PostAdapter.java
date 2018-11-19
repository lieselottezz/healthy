package com.example.ria.healthy.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ria.healthy.R;

import java.util.List;

public class PostAdapter extends ArrayAdapter {

    private List<Post> posts;
    private Context context;

    public PostAdapter(@NonNull Context context,
                        int resource,
                        @NonNull List<Post> objects) {
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View postItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_post_item,
                parent,
                false);
        TextView id = postItem.findViewById(R.id.post_item_id);
        TextView title = postItem.findViewById(R.id.post_item_title);
        TextView body = postItem.findViewById(R.id.post_item_body);
        Post row = posts.get(position);
        id.setText(row.getId());
        title.setText(row.getTitle());
        body.setText(row.getBody());
        return postItem;
    }
}
