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

import com.example.prilogulka.menu.MenuActivity;
import com.example.prilogulka.R;

public class RegistrationActivity extends AppCompatActivity {//  implements LoaderManager.LoaderCallbacks<Cursor> {

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
    private View mProgressView;
    private View mLoginFormView;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private Button buttonSave;

    //private User myUser;
    public static String SHARED_PREFERENCES_NAME = "userInfo";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.
        mEmailView = findViewById(R.id.email_registration);
        emailInputLayout = findViewById(R.id.email_text_input_layout);

        //populateAutoComplete();

        mPasswordView = findViewById(R.id.password_registration);
        passwordInputLayout = findViewById(R.id.password_text_input_layout);
        buttonSave = findViewById(R.id.save);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // зачем действие по прослушке изменений
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);

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

                Log.i("REGISTRATION_ACTIVITY", "BUTTON_SAVE pushed");
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        initUserInfoStorer();
    }

    private void initUserInfoStorer(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void saveInfoInSharedPreferences(){
        Toast.makeText(this, "Сохранение", Toast.LENGTH_SHORT).show();

        putStringInSharedPreferences("email", mEmailView.getText().toString());
        putStringInSharedPreferences("password", mPasswordView.getText().toString());

        putStringInSharedPreferences("имя", "");
        putStringInSharedPreferences("фамилия", "");
        putStringInSharedPreferences("город", "");
        putStringInSharedPreferences("день рождения", "");
        putStringInSharedPreferences("месяц рождения", "");
        putStringInSharedPreferences("год рождения", "");
        putStringInSharedPreferences("пол", "");
    }
    public void putStringInSharedPreferences(String key, String stringToPut) {
        editor.putString(key, stringToPut);
        editor.commit();
    }
    public String getStringFromSharedPreferences(String keyInSharedPreferences) {
        return sharedPreferences.getString(keyInSharedPreferences, "");
    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }


    // Callback received when a permissions request has been completed.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }

    // проверка всех элементов экрана преед отправкой формы и входа юзверя
    private void attemptLogin() {
        //if (mAuthTask != null) {
        //    return;
        //}

        resetPasswordAndEmailErrors();

        if ( isEmailValid() && isPasswordValid() ) {
            //showProgress(true);

            if (isLoginExistsInSharedPreferences()){
                checkExistingUserPassword();
            } else {

                Log.i("REGISTRATION_ACTIVITY", "EMAIL " + mEmailView.getText().toString());
                Log.i("REGISTRATION_ACTIVITY", "PASSWORD " + mPasswordView.getText().toString());

                saveInfoInSharedPreferences();
                welcomeNewUser();
                /**
                 * TODO: решить вопрос с наличием юзверя на разных телефонах
                 * Логика сохранять в бд связку IMEI-email-password чтобы добиться однозначной авторизации
                 *
                 * Проблемы:
                 * -- сменил телефон, почта та же
                 * -- сменил почту, телефон тот же
                 */
            }
//            mAuthTask = new RegistrationActivity.UserLoginTask(mEmailView.getText().toString(),
//                    mPasswordView.getText().toString(), this);
//            mAuthTask.execute((Void) null);
        }
    }

    private void checkExistingUserPassword() {
        if(!equalsPasswordExistingUserAndInputPassword()) {
            //showProgress(false);
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
                getStringFromSharedPreferences("имя"), Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    private boolean isLoginExistsInSharedPreferences() {
        Log.i("REGISTRATION_ACTIVITY", getStringFromSharedPreferences("email"));
        return !getStringFromSharedPreferences("email").equals("")
                && getStringFromSharedPreferences("email").equals(mEmailView.getText().toString());
    }

    private boolean equalsPasswordExistingUserAndInputPassword() {
        return getStringFromSharedPreferences("password").equals(mPasswordView.getText().toString());
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
     * @param hintTetx show any HINT TEXT on the screen
     */
    void showHint(String hintTetx){
        final Snackbar snackbar = Snackbar.make(getCurrentFocus(), hintTetx, Snackbar.LENGTH_INDEFINITE);
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

    //Shows the progress UI and hides the login form.
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//             The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//
//        return new CursorLoader(this,
//                 Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), RegistrationActivity.ProfileQuery.PROJECTION,
//
//                 Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                 Show primary email addresses first. Note that there won't be
//                  a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(RegistrationActivity.ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//
//    }
//
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }
//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(RegistrationActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }
//
//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }
//
//
//     Represents an asynchronous login/registration task used to authenticate the user.
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//        private final Context mContext;
//
//        UserLoginTask(String email, String password, RegistrationActivity context) {
//            mEmail = email;
//            mPassword = password;
//            mContext = context;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//
//            UserLoginDB dbTools = null;
//             TODO: attempt authentication against a network service.
//
//            try {
//                dbTools = new UserLoginDB(mContext);
//                myUser = dbTools.getUser(mEmail);
//                if (myUser.userId > 0) {
//                     Account exists, check password.
//                    return myUser.password.equals(mPassword);
//                } else {
//                    myUser.password = mPassword;
//                    return true;
//                }
//            } finally {
//                if (dbTools != null)
//                    dbTools.close();
//            }
//
//             if no previous checks are true return false;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//            if (success) {
//                if (myUser.userId > 0) {
//                    finish();
//                    Intent intent = new Intent(RegistrationActivity.this, UserInfoActivity.class);
//                    startActivity(intent);
//                } else {
//                    UserLoginDB dbTools = null;
//                    try {
//                        finish();
//                        dbTools = new UserLoginDB(mContext);
//                        myUser = dbTools.insertUser(myUser);
//                        Intent intent = new Intent(RegistrationActivity.this, UserInfoActivity.class);
//                        startActivity(intent);
//                    } finally {
//                        if (dbTools != null) dbTools.close();
//                    }
//
//                }
//            } else {
//                showHint(getString(R.string.registered_user_error));
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
//
}







