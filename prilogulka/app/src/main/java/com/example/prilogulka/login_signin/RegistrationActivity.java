package com.example.prilogulka.login_signin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prilogulka.SharedPreferencesManager;
import com.example.prilogulka.menu.MenuActivity;
import com.example.prilogulka.R;

public class RegistrationActivity extends AppCompatActivity {

// данный класс во многом дублирует Login Activity.

// Переход сюда осуществляется по кнопке регистрация в Login Activity.
// В случае если пользователь ввел ранее не зарегистрированный email
// идет переход к Two Activity где пользоатель заполнит анкету.

    //необходимо добавить в эту цепочку рассылку писем с проверкой email

    // Id to identity READ_CONTACTS permission request.
    // private static final int REQUEST_READ_CONTACTS = 0;


     // A dummy authentication store containing known user names and passwords.
     // TODO: remove after connecting to a real authentication system.
    //private static final String[] DUMMY_CREDENTIALS = new String[]{
            //"foo@example.com:hello", "bar@example.com:world"
    //};

    // Keep track of the login task to ensure we can cancel it if requested.
    //private RegistrationActivity.UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private Button buttonSave;

//    public final String SHARED_PREFERENCES_NAME = "userInfo";
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
    SharedPreferencesManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initUIReference();

        mEmailView.setText(this.getIntent().getStringExtra("email"));
        mPasswordView.setText(this.getIntent().getStringExtra("password"));

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

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(buttonSave.getWindowToken(), 0);

                attemptLogin();
            }
        });

        initSharedPreferenceManager();
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
    }
    private void initSharedPreferenceManager(){
        spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(this);
    }
    public void initUIReference(){
        mEmailView = findViewById(R.id.email_registration);
        emailInputLayout = findViewById(R.id.email_text_input_layout);


        mPasswordView = findViewById(R.id.password_registration);
        passwordInputLayout = findViewById(R.id.password_text_input_layout);

        buttonSave = findViewById(R.id.save);
    }
//    private void initUserInfoStorer(){
//        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }
    private void saveInfoInSharedPreferences(){
        Toast.makeText(this, "Сохранение", Toast.LENGTH_SHORT).show();

        spManager.putStringInSharedPreferences("email", mEmailView.getText().toString());
        spManager.putStringInSharedPreferences("password", mPasswordView.getText().toString());

        spManager.putStringInSharedPreferences("имя", "");
        spManager.putStringInSharedPreferences("фамилия", "");
        spManager.putStringInSharedPreferences("город", "");
        spManager.putStringInSharedPreferences("день рождения", "");
        spManager.putStringInSharedPreferences("месяц рождения", "");
        spManager.putStringInSharedPreferences("год рождения", "");
        spManager.putStringInSharedPreferences("пол", "");
    }
//    public void putStringInSharedPreferences(String key, String stringToPut) {
//        editor.putString(key, stringToPut);
//        editor.commit();
//    }

//    public String getStringFromSharedPreferences(String keyInSharedPreferences) {
//        return sharedPreferences.getString(keyInSharedPreferences, "");
//    }

    // проверка всех элементов экрана преед отправкой формы и входа юзверя
    private void attemptLogin() {

        resetPasswordAndEmailErrors();

        if ( isEmailValid() && isPasswordValid() ) {
            if (isLoginExistsInSharedPreferences()){
                checkExistingUserPassword();
            } else {

                Log.i("REGISTRATION_ACTIVITY", "EMAIL " + mEmailView.getText().toString());
                Log.i("REGISTRATION_ACTIVITY", "PASSWORD " + mPasswordView.getText().toString());

                saveInfoInSharedPreferences();
                welcomeNewUser();
            }
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
    private void welcomeNewUser(){
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
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

    /**
     * @param hintText show any HINT TEXT on the screen
     */
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







