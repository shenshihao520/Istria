package shen.pula.istria.MPresenter;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.VPresenter.OperationPollutionView;
import shen.pula.istria.VPresenter.OperationWaningView;
import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_source.PollutionDataSource;
import shen.pula.istria.data_source.SuggestionDataSource;
import shen.pula.istria.data_source.TaskManager;
import shen.pula.istria.data_source.WarningDataSource;
import shen.pula.istria.webService.GetWarningInfoResponse;

/**
 * Created by dell on 2016/12/19.
 */

public class PollutionPresenter {
    OperationPollutionView operationPollutionView;
    OperationWaningView operationWaningView;
    TaskManager taskManager;
    TaskManager suggestionM;
    TaskManager warningManager;
    String city = "beijing";

    public PollutionPresenter() {

    }

    public void addPollutionListener(OperationPollutionView op) {
        this.taskManager = new TaskManager(new PollutionDataSource());
        this.suggestionM = new TaskManager(new SuggestionDataSource());
        this.operationPollutionView = op;
    }

    public void addWarningListener(OperationWaningView op) {
        warningManager = new TaskManager(new WarningDataSource());
        this.operationWaningView = op;
    }

    /**
     * 开始变换污染情况UI
     * 这里使用了Lambda表达式
     */
    public void getPollutionDataAndShow() {
        Observable.just(city)
                .subscribeOn(Schedulers.io())
                .map((String s) -> taskManager.getPollution(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Pollution p) -> operationPollutionView.showPollution(p));
    }

    /**
     * 建议情况获取数据改变UI
     */
    public void getSuggestionDataAndShow() {
        Observable.just(city)
                .subscribeOn(Schedulers.io())
                .map((String s) -> suggestionM.getSuggestionDir(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Suggestion_out s) -> operationPollutionView.showSuggestion(s));
    }

    public void getWarningDataAndShow() {
        Observable.just(city)
                .subscribeOn(Schedulers.io())
                .map((String s) -> warningManager.getWarning(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((GetWarningInfoResponse p) -> operationWaningView.showWarning(p));
    }
}
