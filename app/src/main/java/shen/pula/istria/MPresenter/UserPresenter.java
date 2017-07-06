package shen.pula.istria.MPresenter;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.VPresenter.OperationLoginView;
import shen.pula.istria.VPresenter.OperationUserView;
import shen.pula.istria.data_module.User;
import shen.pula.istria.data_source.LoginCheckOperation;
import shen.pula.istria.data_source.TaskManager;
import shen.pula.istria.data_source.UserDataSource;

import static shen.pula.istria.R.id.userName;

/**
 * Created by dell on 2017/1/23.
 */

public class UserPresenter {
    OperationUserView operationUserView;
    OperationLoginView operationLoginView;
    TaskManager taskManager ;
    public UserPresenter ()
    {
    }
    public void addUserListener(OperationUserView operationUserView)
    {
        taskManager = new TaskManager(new UserDataSource());
        this.operationUserView = operationUserView;
    }
    public void addCheckListener(OperationLoginView operationLoginView)
    {
        taskManager = new TaskManager(new LoginCheckOperation());
        this.operationLoginView = operationLoginView;
    }
    public void loginCheckAndShow(String userID,String passWord)
    {
        User post = new User();
        post.setuID(userID);
        post.setPassWord(passWord);
        Observable.just(post)
                .subscribeOn(Schedulers.io())
                .map((User s) ->  taskManager.checkLogin(s) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((User u) -> operationLoginView.loginCheck(u));
    }
    public void getUserAndShow(String userName)
    {
        Observable.just(userName)
                .subscribeOn(Schedulers.io())
                .map((String s) ->  taskManager.getUser(s) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((User u) -> operationUserView.showUser(u));
    }
}
