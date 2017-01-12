package com.agile.rxlifecycledemo.posts;

import java.util.List;

import rx.Observable;

/**
 * Created by agile-01 on 8/17/2016.
 * this works as a packager for model, view and presenter.
 */
public interface PostsMVP {

    interface Model {

        long getUserId();

        void setUserId(long userId);

        long getId();

        void setId(long id);

        String getTitle();

        void setTitle(String title);

        String getBody();

        void setBody(String body);
    }

    interface View {

        void loadPosts();

        void initComponents();

        void showSnakeBar(String message);

        void showLoader();

        void hideLoader();

        void handleError(Throwable e);

        void scrollListToPosition(int position);

        void handleClickOnAddPost();

        void handleClickOnDeletePost(PostModel postModel);

        void onGotPosts(List<PostModel> postModels);

        void onAddedPost(PostModel postModel);

        void onDeletedPost(PostModel postModel);
    }

    interface Presenter {

        Observable<List<PostModel>> getPosts();

        Observable<PostModel> addPost(PostModel postModel);

        Observable<PostModel> deletePost(PostModel postModel);
    }
}
