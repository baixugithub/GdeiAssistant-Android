package edu.gdei.gdeiassistant.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.gdei.gdeiassistant.Presenter.LoginPresenter;
import edu.gdei.gdeiassistant.R;
import edu.gdei.gdeiassistant.View.Dialog.ProgressDialogCreator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginUsername;

    private EditText loginPassword;

    private Button loginButton;

    private TextView loginAgreement;

    private TextView loginPrivacyPolicy;

    private Dialog dialog;

    private long exitTime = 0;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        InitView();
        //配置加载Presenter
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        //移除所有的回调和消息，防止内存泄露
        loginPresenter.RemoveCallBacksAndMessages();
    }

    /**
     * 初始化控件
     */
    private void InitView() {
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        loginAgreement = findViewById(R.id.loginAgreement);
        loginPrivacyPolicy = findViewById(R.id.loginPrivacyPolicy);
        SetOnclickEvent();
    }

    private void SetOnclickEvent() {
        loginButton.setOnClickListener(this);
        loginAgreement.setOnClickListener(this);
        loginPrivacyPolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                //登录
                loginPresenter.UserLogin(loginUsername.getText().toString(), loginPassword.getText().toString());
                break;

            case R.id.loginAgreement:
                //用户协议
                loginPresenter.ShowAgreement();
                break;

            case R.id.loginPrivacyPolicy:
                //隐私政策
                loginPresenter.ShowPrivacyPolicy();
                break;
        }
    }

    /**
     * 显示Toast提示
     *
     * @param message
     */
    public void ShowToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度条
     */
    public void ShowProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialogCreator().GetProgressDialogCreator(this);
            dialog.show();
        } else {
            dialog.show();
        }
    }

    /**
     * 隐藏进度条
     */
    public void HideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 防止用户意外返回键退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finishAffinity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
