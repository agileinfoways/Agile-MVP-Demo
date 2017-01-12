package com.agile.rxlifecycledemo.posts;

import android.util.Log;

import com.agile.rxlifecycledemo.api.RestClient;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by agile-01 on 8/17/2016.
 */
public class PostsPresenter implements PostsMVP.Presenter {
    private static final String TAG = "PostsPresenter";
    private PostsMVP.View postsView;

    public PostsPresenter(PostsMVP.View postsView) {
        this.postsView = postsView;
    }

    @Override
    public Observable<List<PostModel>> getPosts() {
        postsView.showSnakeBar("Getting All Posts");
        postsView.showLoader();
        return RestClient.getWebServices()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PostModel> addPost(PostModel postModel) {
        postsView.showSnakeBar("Adding New Post");
        postsView.showLoader();
        return RestClient.getWebServices()
                .addPost(postModel.getUserId(), postModel.getTitle(), postModel.getBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PostModel> deletePost(PostModel postModel) {
        postsView.showSnakeBar("Deleting Post : " + postModel.getTitle());
        postsView.showLoader();
        return RestClient.getWebServices()
                .deletePost(postModel.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //region observers

    //observer for get posts api
    public Observer<List<PostModel>> getPostsObserver() {
        return new Observer<List<PostModel>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                postsView.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                postsView.handleError(e);
            }

            @Override
            public void onNext(List<PostModel> postModels) {
                Log.d(TAG, "onNext() called with: " + "postModels = [" + postModels + "]");
                //dispatching callback to MVP view about list of post has been got
                postsView.onGotPosts(postModels);
            }
        };
    }

    public Observer<PostModel> addPostsObserver() {
        return new Observer<PostModel>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                //need to hide loader and scroll to first position
                postsView.hideLoader();
                postsView.scrollListToPosition(0);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                postsView.handleError(e);
            }

            @Override
            public void onNext(PostModel postModel) {
                Log.d(TAG, "onNext() called with: " + "postModel = [" + postModel + "]");
                //dispatching callback to MVP view about post has been added
                postsView.onAddedPost(postModel);
            }
        };
    }

    public Observer<PostModel> deletePostObserver() {
        return new Observer<PostModel>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
                postsView.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                postsView.handleError(e);
            }

            @Override
            public void onNext(PostModel postModel) {
                Log.d(TAG, "onNext() called with: " + "postModel = [" + postModel + "]");
                //dispatching callback to MVP view about post has been deleted
                postsView.onDeletedPost(postModel);
            }
        };
    }
    //endregion
}
