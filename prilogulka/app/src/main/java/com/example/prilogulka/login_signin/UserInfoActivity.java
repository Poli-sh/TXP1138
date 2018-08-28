package com.example.prilogulka.login_signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prilogulka.SharedPreferencesManager;
import com.example.prilogulka.menu.MenuActivity;
import com.example.prilogulka.R;

public class UserInfoActivity extends AppCompatActivity {

    String[] sex = {"жен", "муж"};

    SharedPreferencesManager spManager;

    Spinner spinner;
    EditText editName, editSurname, editCity, editDay, editMonth, editYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initializeSpinner();
        initSharedPreferenceManager();
    }

    private void initSharedPreferenceManager(){
        spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(this);
    }

    private void initializeSpinner(){
        spinner = findViewById(R.id.sex_user_info);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Пол");

        setSpinnerItemSelectionListener(spinner);
    }

    private void setSpinnerItemSelectionListener(final Spinner spinner){
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public void onMyButtonClick(View view) {

        initLayoutFields();

        if (hasEmptyField()) {
            showHint(getString(R.string.has_empty_fields));
        } else {
            saveInfoInSharedPreferences();

            Toast.makeText(this, "Добро пожаловать, " +
                    spManager.getStringFromSharedPreferences("имя"), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    private void initLayoutFields() {
        editName = findViewById(R.id.name_user_info);
        editSurname = findViewById(R.id.surname_user_info);
        editCity = findViewById(R.id.city_user_info);
        editDay = findViewById(R.id.day_user_info);
        editMonth = findViewById(R.id.month_user_info);
        editYear = findViewById(R.id.year_user_info);
    }

    private void saveInfoInSharedPreferences(){
        Toast.makeText(this, "Сохранение", Toast.LENGTH_SHORT).show();

        spManager.putStringInSharedPreferences("имя", editName.getText().toString());
        spManager.putStringInSharedPreferences("фамилия", editSurname.getText().toString());
        spManager.putStringInSharedPreferences("город", editCity.getText().toString());
        spManager.putStringInSharedPreferences("день рождения", editDay.getText().toString());
        spManager.putStringInSharedPreferences("месяц рождения", editMonth.getText().toString());
        spManager.putStringInSharedPreferences("год рождения", editYear.getText().toString());
        spManager.putStringInSharedPreferences("пол", spinner.getSelectedItemPosition() + "");

    }
    private boolean hasEmptyField(){
        return isEmpty(editName) || isEmpty(editSurname) || isEmpty(editCity) || isEmpty(editDay)
            && isEmpty(editMonth) ||  isEmpty(editYear);
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
            }
        });
        snackbar.show();
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().equals("");
    }

}