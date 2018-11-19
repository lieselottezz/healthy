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

public class CommentAdapter extends ArrayAdapter {

    private List<Comment> comments;
    private Context context;

    public CommentAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.comments = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_comment_item,
                parent,
                false);
        TextView postId = commentItem.findViewById(R.id.comment_item_post_id);
        TextView id = commentItem.findViewById(R.id.comment_item_id);
        TextView body = commentItem.findViewById(R.id.comment_item_body);
        TextView name = commentItem.findViewById(R.id.comment_item_name);
        TextView email = commentItem.findViewById(R.id.comment_item_email);
        Comment row = comments.get(position);
        postId.setText(row.getPostId());
        id.setText(row.getId());
        body.setText(row.getBody());
        name.setText(row.getName());
        email.setText(row.getEmail());
        return commentItem;
    }
}
