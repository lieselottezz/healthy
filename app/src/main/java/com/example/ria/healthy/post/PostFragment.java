package com.example.ria.healthy.post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ria.healthy.MenuFragment;
import com.example.ria.healthy.R;
import com.example.ria.healthy.sleep.Sleep;
import com.example.ria.healthy.sleep.SleepFormFragment;
import com.example.ria.healthy.utility.Extension;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PostFragment extends Fragment {

    public static final String TAG = "POSTFRAGMENT";

    private ArrayList<Post> posts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        initPost();
    }

    void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.post_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Goto MenuFragment");
                Extension.goTo(getActivity(), new MenuFragment());
            }
        });
    }

    void initPost() {
        posts.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService service = retrofit.create(PostService.class);
        service.listPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                ListView postList = getView().findViewById(R.id.post_list);
                final PostAdapter postAdapter = new PostAdapter(
                        getActivity(),
                        R.layout.fragment_post_item,
                        response.body()
                );
                postList.setAdapter(postAdapter);
                postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Post p = (Post) adapterView.getItemAtPosition(i);
                        Log.d(TAG, p.getId());
                        Bundle bundle = new Bundle();
                        bundle.putString("id", p.getId());
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        CommentFragment cf = new CommentFragment();
                        cf.setArguments(bundle);
                        ft.replace(R.id.main_view, cf);
                        ft.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Extension.toast(getActivity(), t.toString());
            }
        });
    }
}

interface PostService {
    @GET("posts")
    Call<List<Post>> listPosts();
}
