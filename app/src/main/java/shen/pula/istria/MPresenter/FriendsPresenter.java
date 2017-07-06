package shen.pula.istria.MPresenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.VPresenter.OperationFriendsView;
import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_source.FriendsSubmitDataSource;
import shen.pula.istria.data_source.TaskManager;
import shen.pula.istria.viewController.FriendsActivity;
import shen.pula.istria.viewController.FriendsAdapter;

/**
 * Created by dell on 2016/12/30.
 */

public class FriendsPresenter {
    OperationFriendsView operationFriendsView;
    TaskManager friendsSubmitManager;
    String date = "12-30" ;
    public FriendsPresenter()
    {

    }
    public void addFriendsSubmitListener(OperationFriendsView operationFriendsView)
    {
        this.friendsSubmitManager = new TaskManager(new FriendsSubmitDataSource());
        this.operationFriendsView = operationFriendsView;
    }
    public void getFriendSubmitAndShow()
    {
        Observable.just(date)
                .subscribeOn(Schedulers.io())
                .map((String s) ->  friendsSubmitManager.getFriendsSubmit(s) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((ArrayList<FriendsSubmit> s) -> operationFriendsView.showFriendsSubmit(s));
    }

    public void simulateLoadMoreData(FriendsAdapter mFriendsAdapter, ArrayList<FriendsSubmit> mFriendsSubmits) {
        Observable
                .timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map((Long l) -> {
                            friendsSubmitManager.loadFriendsMoreData(mFriendsSubmits);
                            mFriendsAdapter.notifyDataSetChanged();
//                    mFriendsAdapter.n
                            return null;
                        }
                )
                .subscribe();
    }
}
