package shen.pula.istria.viewController;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import shen.pula.istria.MPresenter.UserPresenter;
import shen.pula.istria.R;
import shen.pula.istria.VPresenter.OperationLoginView;
import shen.pula.istria.data_module.User;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.utils.Istria_utils;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetUserInfoResponse;

import static com.baidu.location.h.j.G;
import static com.baidu.location.h.j.t;
import static shen.pula.istria.R.id.userName;
import static shen.pula.istria.viewController.MainActivity.MAINACTIVITY_CODE;

/**
 * Created by dell on 2016/12/27.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, OperationLoginView {
    private Button btn_login;
    private TextView tv_register;
    private TextView tv_error;
    private TextView tv_question;
    private EditText et_tel;
    private EditText et_passWord;
    private UserPresenter userPresenter;
    private MyHandler myHandler = new MyHandler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        userPresenter = new UserPresenter();
        userPresenter.addCheckListener(this);
        return view;
    }

    private void initView(View view) {
        btn_login = (Button) view.findViewById(R.id.btn_login);
        tv_error = (TextView) view.findViewById(R.id.tv_error);
        tv_register = (TextView) view.findViewById(R.id.tv_goRegister);
        et_tel = (EditText) view.findViewById(R.id.et_tel);
        et_passWord = (EditText) view.findViewById(R.id.et_passWord);
        tv_question = (TextView) view.findViewById(R.id.tv_question);
        btn_login.setOnClickListener(this);
        tv_question.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (et_tel.getText().toString() != null && et_passWord.getText().toString() != null && et_tel.getText().toString().length() == 11) {
                    userPresenter.loginCheckAndShow(et_tel.getText().toString(), et_passWord.getText().toString());

                    tv_error.setVisibility(View.GONE);
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), UserInformationActivity.class);
//                    getActivity().startActivityForResult(intent, MAINACTIVITY_CODE);
//                    getActivity().finish();
                } else {
                    tv_error.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_goRegister:
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragmentContainer, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.tv_question:
                Login_QuestionFragment login_questionFragment = new Login_QuestionFragment();
                FragmentTransaction transaction2 = getFragmentManager()
                        .beginTransaction();
                transaction2.replace(R.id.fragmentContainer, login_questionFragment);
                transaction2.addToBackStack(null);
                transaction2.commit();
                break;
        }
    }

    @Override
    public void loginCheck(User user) {
        Message msg = new Message();
        msg.obj = user;
        myHandler.sendMessage(msg);

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            User user = (User) msg.obj;
            if (!user.getuName().equals("false")) {
                Istria_utils.putSP(getActivity(), Istria_utils.KEY_USERNAME, user.getuName());
                Istria_utils.putSP(getActivity(), Istria_utils.KEY_USERID, et_tel.getText().toString());
                Istria_utils.showToast(getResources().getString(R.string.login_successful), getActivity());
                tv_error.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().startActivityForResult(intent, MAINACTIVITY_CODE);
                getActivity().finish();
            } else {
                Istria_utils.showToast(getResources().getString(R.string.login_failed), getActivity());
            }
        }
    }


}
