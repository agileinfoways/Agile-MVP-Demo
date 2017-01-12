package com.agile.rxlifecycledemo.posts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.agile.rxlifecycledemo.R;
import com.agile.rxlifecycledemo.utils.LongClickListener;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by agile-01 on 8/17/2016.
 * <p/>
 * activity for listing of random posts
 * - first, list of posts will be loaded
 * - on add button click - random post will be generated and added to the list
 * - on long click on post - post well be deleted
 * - used server is temporary, so posts can be deleted directly from server and errors might occur
 * <p/>
 * <p/>
 * demo for RxLifeCycle:
 * api observables will only subscribe observers until some activity life cycle event defined
 * for ex, if we need api response until activity is destroyed, RxLifeCycle will compose api call
 * only until onDestroy() is called
 */
public class PostsActivity extends RxAppCompatActivity implements PostsMVP.View, LongClickListener<PostModel> {

    @Bind(R.id.rvPosts)
    RecyclerView rvPosts;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fabAddPost)
    FloatingActionButton fabAddPost;

    private static final String TAG = "PostsActivity";

    private PostsPresenter postsPresenter;
    private ProgressDialog progressDialog;
    private PostsAdapter postsAdapter;

    //lifecycle transformers for apis - they will be returned from bindUntilEvent(...) methods from RxLifeCycle
    private LifecycleTransformer<List<PostModel>> getPostsLifecycleTransformer;
    private LifecycleTransformer<PostModel> addPostLifecycleTransformer, deletePostLifeCycleTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        initComponents();
        loadPosts();
    }

    @Override
    public void initComponents() {
        postsPresenter = new PostsPresenter(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_posts));
        progressDialog.setCancelable(false);

        toolbar.setTitle(getString(R.string.app_name));

        //setting adapter
        List<PostModel> postModels = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, postModels);
        postsAdapter.setLongClickListener(this);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postsAdapter);

        //binding lifecycle transformers to activity lifecycle states
        //when this is composed by some observable - that observable will only subscribe observer until activity onDestroy() is called
        getPostsLifecycleTransformer = bindUntilEvent(ActivityEvent.DESTROY);
        addPostLifecycleTransformer = bindUntilEvent(ActivityEvent.DESTROY);
        deletePostLifeCycleTransformer = bindUntilEvent(ActivityEvent.DESTROY);
    }


    @Override
    public void loadPosts() {
        //calling get posts api and composing until activity destroys
        Observable<List<PostModel>> getPostsApiObservable = postsPresenter.getPosts();
        getPostsApiObservable
                .compose(getPostsLifecycleTransformer)
                .subscribe(postsPresenter.getPostsObserver());
    }

    @OnClick(R.id.fabAddPost)
    public void onClick() {
        handleClickOnAddPost();
    }

    @Override
    public void onLongClick(PostModel model) {
        handleClickOnDeletePost(model);
    }

    @Override
    public void showSnakeBar(String message) {
        Snackbar.make(fabAddPost, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoader() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void handleError(Throwable e) {
        e.printStackTrace();
        //hiding loader and showing error message whenever error occurs
        hideLoader();
        showSnakeBar(getString(R.string.unknown_error_occured));
    }

    @Override
    public void scrollListToPosition(int position) {
        rvPosts.scrollToPosition(position);
    }

    @Override
    public void handleClickOnAddPost() {
        //generating post with random data and calling add post api
        PostModel postModel = new PostModel();
        postModel.setUserId(new Random(2).nextLong());
        postModel.setTitle(RandomStringUtils.randomAlphabetic(10));
        postModel.setBody(RandomStringUtils.randomAlphabetic(50));

        //composing api observable until activity destroys
        Observable<PostModel> addPostApiObservable = postsPresenter.addPost(postModel);
        addPostApiObservable
                .compose(addPostLifecycleTransformer)
                .subscribe(postsPresenter.addPostsObserver());
    }

    @Override
    public void handleClickOnDeletePost(PostModel postModel) {
        //on long click calling delete post api
        //and composing api observable until activity destroys
        Observable<PostModel> deletePostApiObservable = postsPresenter.deletePost(postModel);
        deletePostApiObservable
                .compose(deletePostLifeCycleTransformer)
                .subscribe(postsPresenter.deletePostObserver());
    }


    @Override
    public void onGotPosts(List<PostModel> postModels) {
        postsAdapter.addAll(postModels);
    }

    @Override
    public void onAddedPost(PostModel postModel) {
        postsAdapter.add(postModel);
    }

    @Override
    public void onDeletedPost(PostModel postModel) {
        postsAdapter.remove(postModel);
    }

    @Override
    protected void onDestroy() {
        postsAdapter.setLongClickListener(null);
        super.onDestroy();
    }
}
