package shen.pula.istria.VPresenter;

import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_module.Weather;

/**
 * Created by dell on 2016/12/21.
 * 进行对污染界面改变
 */

public interface OperationPollutionView {

    void showPollution(Pollution pollution);

    void showSuggestion(Suggestion_out suggestion_out);
}
