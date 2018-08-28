package com.example.prilogulka.login_signin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.prilogulka.SharedPreferencesManager;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {//} implements LoaderCallbacks<Cursor> {

    //немного переработанная Login Activity. Добавлено обращение к SQLite БД с проверкой email/пароль.
    //для чего были созданы 2 вспомогательных класса UserLoginDB и User

    public void onMyButtonClick(View view) {
        Button button = findViewById(R.id.registration);       //регистрация нового пользователя
        button.setEnabled(true);
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

//    Id to identity READ_CONTACTS permission request.
//    private static final int REQUEST_READ_CONTACTS = 0;
//
//    /**
//     A dummy authentication store containing known user names and passwords.
//     TODO: remove after connecting to a real authentication system.
//     */
//    private static final String[] DUMMY_CREDENTIALS = new String[]{
//            "foo@example.com:hello", "bar@example.com:world"
//    };
//
//     Keep track of the login task to ensure we can cancel it if requested.
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;


//    public final String SHARED_PREFERENCES_NAME = "userInfo";
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
    SharedPreferencesManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEmailSignInButton.getWindowToken(), 0);

                attemptLogin();
            }
        });

        initSharedPreferenceManager();
    }

    private void initUIReference(){
        mEmailView = findViewById(R.id.email);
        emailInputLayout = findViewById(R.id.email_text_input_layout);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mPasswordView = findViewById(R.id.password);
        passwordInputLayout = findViewById(R.id.password_text_input_layout);
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
    }
    private void initSharedPreferenceManager(){
        spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(this);
    }
    private void attemptLogin() {
        resetPasswordAndEmailErrors();

        if ( isEmailValid() && isPasswordValid() ) {

            if (isLoginExistsInSharedPreferences())
                checkExistingUserPassword();
            else
                authorizeNewUser();
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
    private void authorizeNewUser(){
        offerToRegister();

    }
    private void welcomeExistingUser() {
        Toast.makeText(this, "Добро пожаловать" +
                spManager.getStringFromSharedPreferences("имя"), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    private boolean isLoginExistsInSharedPreferences() {
        Log.i("REGISTRATION_ACTIVITY", spManager.getStringFromSharedPreferences("email"));
        return !spManager.getStringFromSharedPreferences("email").equals("")
                && spManager.getStringFromSharedPreferences("email").equals(mEmailView.getText().toString());
    }

    private boolean equalsPasswordExistingUserAndInputPassword() {
        return spManager.getStringFromSharedPreferences("password").equals(mPasswordView.getText().toString());
    }

    private void offerToRegister(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы не зарегистрированы")
                .setMessage("Хотите зарегистрироваться с этими данными?")
                .setCancelable(false)
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Да",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                                intent.putExtra("email", mEmailView.getText().toString());
                                intent.putExtra("password", mPasswordView.getText().toString());
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
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

//    private void initUserInfoStorer(){
//        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }
//
//    public String getStringFromSharedPreferences(String keyInSharedPreferences) {
//        return sharedPreferences.getString(keyInSharedPreferences, "");
//    }

}


