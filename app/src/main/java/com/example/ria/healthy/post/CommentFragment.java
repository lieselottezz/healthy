package com.example.ria.healthy.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.ria.healthy.MenuFragment;
import com.example.ria.healthy.R;
import com.example.ria.healthy.utility.Extension;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class CommentFragment extends Fragment {

    public static final String TAG = "COMMENTFRAGMENT";

    private String id;
    private ArrayList<Comment> comments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initComment();
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.comment_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Goto PostFragment");
                Extension.goTo(getActivity(), new PostFragment());
            }
        });
    }

    void initComment() {
        comments.clear();
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            Log.d(TAG, id);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentService service = retrofit.create(CommentService.class);
        service.listComments(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                ListView commentList = getView().findViewById(R.id.comment_list);
                final CommentAdapter commentAdapter = new CommentAdapter(
                        getActivity(),
                        R.layout.fragment_comment_item,
                        response.body()
                );
                commentList.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Extension.toast(getActivity(), t.toString());
            }
        });
    }
}

interface CommentService {
    @GET("posts/{id}/comments")
    Call<List<Comment>> listComments(@Path("id") String id);
}
