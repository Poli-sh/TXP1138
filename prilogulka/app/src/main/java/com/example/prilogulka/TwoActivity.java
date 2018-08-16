package com.example.prilogulka;


        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

public class TwoActivity extends AppCompatActivity {
    String[] sex = {"жен", "муж"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        EditText editText = findViewById(R.id.data);
        editText.setEnabled(false);
        Spinner spinner = findViewById(R.id.sex);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Пол");
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                ((TextView)parent.getChildAt(0)).setTextSize(20);
//                String selected =  spinner.getSelectedItem().toString();
//                selection.setText(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public void onMyButtonClick(View view) {

        EditText editText = findViewById(R.id.name);
        EditText editText1 = findViewById(R.id.surname);
        EditText editText2 = findViewById(R.id.city);
        EditText editText3 = findViewById(R.id.day);
        EditText editText4 = findViewById(R.id.month);
        EditText editText5 = findViewById(R.id.year);
//        Spinner spinner = findViewById(R.id.sex);


        Button button = findViewById(R.id.btnActTwo);
//        try{

        if (editText.getText().toString().equals("") || editText1.getText().toString().equals("") || editText2.getText().toString().equals("")|| editText3.getText().toString().equals("")||editText4.getText().toString().equals("")||editText5.getText().toString().equals("")) {
            Toast.makeText(this, "Необходимо заполнить пустые поля", Toast.LENGTH_SHORT).show();
            editText.setError("Необходимо заполнить пустые поля");
            editText1.setError("Необходимо заполнить пустые поля");
            editText2.setError("Необходимо заполнить пустые поля");
            editText3.setError("Необходимо заполнить пустые поля");
            editText4.setError("Необходимо заполнить пустые поля");
            editText5.setError("Необходимо заполнить пустые поля");

        } else {
            Toast.makeText(this, "Сохранение", Toast.LENGTH_SHORT).show();
            button.setEnabled(true);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("имя", editText.getText().toString());
            intent.putExtra("фамилия", editText1.getText().toString());
            intent.putExtra("город", editText2.getText().toString());
            intent.putExtra("день рождения", editText3.getText().toString());
            intent.putExtra("месяц рождения", editText4.getText().toString());
            intent.putExtra("год рождения", editText5.getText().toString());

            startActivity(intent);
        }

    }
}


