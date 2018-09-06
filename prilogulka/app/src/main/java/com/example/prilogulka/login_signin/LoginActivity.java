package com.example.prilogulka.login_signin;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.prilogulka.SharedPreferencesManager;
import com.example.prilogulka.data.User;
import com.example.prilogulka.data_base.UserInfoDataBaseImpl;
import com.example.prilogulka.menu.MenuActivity;
import com.example.prilogulka.R;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public void onRegistrationButtonClick(View view) {
        Button mRegistrationButton = findViewById(R.id.registration);
        mRegistrationButton.setEnabled(true);
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    // storing value
    UserInfoDataBaseImpl userInfoDataBase;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUIReference();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // зачем действие по прослушке изменений
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    hideKeyboard();
                    attemptLogin();

                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                attemptLogin();
            }
        });

    }

    private void initUIReference(){
        mEmailView = findViewById(R.id.email);
        emailInputLayout = findViewById(R.id.email_text_input_layout);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mPasswordView = findViewById(R.id.password);
        passwordInputLayout = findViewById(R.id.password_text_input_layout);

        userInfoDataBase = new UserInfoDataBaseImpl(this);
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
    }

    private void attemptLogin() {
        resetPasswordAndEmailErrors();

        if ( isEmailValid() && isPasswordValid() ) {

            if (isLoginExistsInDataBase())
                checkExistingUserPassword();
            else
                showHint("Вы не зарегистрированы. Нажмите кнопку \"Регистрация\".");
        }
    }

    private void checkExistingUserPassword() {
        if(!equalsPasswordExistingUserAndInputPassword()) {
            showHint(getString(R.string.user_exists_error));
            passwordInputLayout.setError(getString(R.string.error_invalid_password));
            passwordInputLayout.setErrorEnabled(true);
        } else {
            welcomeExistingUser();
        }
    }
    private void welcomeExistingUser() {
        Intent intent = new Intent(this, MenuActivity.class);
        SharedPreferencesManager spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(this);;
        spManager.putStringInSharedPreferences("active_user", mEmailView.getText().toString());
        startActivity(intent);
    }
    private boolean isLoginExistsInDataBase() {
        List<User> userList = userInfoDataBase.findUserInfo("email", mEmailView.getText().toString());
        if (userList.size() == 1) {
            user = userList.get(0);
            Log.i("REGISTRATION_ACTIVITY", user.getEmail());
            return true;
        }
        return false;
    }

    private boolean equalsPasswordExistingUserAndInputPassword() {
        return user.getPassword().equals(mPasswordView.getText().toString());
    }

    private boolean isEmailValid(){
        boolean noErrors = true;
        String email = mEmailView.getText().toString();

        if (email.isEmpty()) {
            emailInputLayout.setError(getString(R.string.error_field_required));
            noErrors = false;
        } else if (!isEmailCorrect(email)) {
            emailInputLayout.setError(getString(R.string.error_invalid_email));
            noErrors = false;
        }

        if (!noErrors){
            emailInputLayout.setErrorEnabled(true);
        }

        return noErrors;
    }

    private boolean isPasswordValid(){
        String password = mPasswordView.getText().toString();
        boolean noErrors = true;

        if (password.isEmpty() || !isPasswordCorrect(password)) {
            passwordInputLayout.setError(getString(R.string.error_invalid_password));

            showHint(getString(R.string.invalid_password_hint));
            noErrors = false;

            passwordInputLayout.setErrorEnabled(true);
        }

        return noErrors;

    }

    void showHint(String hintText){
        final Snackbar snackbar = Snackbar.make(getCurrentFocus(), hintText, Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        TextView snackBarTextView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        snackBarTextView.setSingleLine(false);
        snackbar.setAction("Понятно", new View.OnClickListener(){
            @Override
            public void onClick(View v){
                snackbar.dismiss();
                passwordInputLayout.setErrorEnabled(false);
            }
        });
        snackbar.show();
    }

    private void resetPasswordAndEmailErrors(){
        emailInputLayout.setErrorEnabled(false);
        passwordInputLayout.setErrorEnabled(false);
    }

    private boolean isEmailCorrect(String email) {
        //TODO: Replace this with your own logic
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordCorrect(String password) {
        //TODO: Replace this with your own logic
        //String passwordRegExpression = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
        return password.length() >= 4;
    }
}


