package com.example.diabetesnote.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabetesnote.DataModel.Information;
import com.example.diabetesnote.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

public class Add extends AppCompatActivity {
    private Spinner conditionSpinner;
    private Spinner drugTypeSpinner;
    private TextView dateTextView;
    private TextView hourTextView;
    private EditText amountEditText;
    private EditText foodEditText;
    private EditText detailEditText;
    private EditText consumEditText;

    private Button addButton;
    private String dayOfWeek;
    Realm realm;
    String[] conditionList;
    String[] drugTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Init();
        setSpinners();
        setPickers();
        setButtons();
    }


    private void Init() {
        conditionSpinner = findViewById(R.id.add_spinner_condition);
        drugTypeSpinner = findViewById(R.id.add_spinner_drug_type);
        dateTextView = findViewById(R.id.add_edittext_date);
        hourTextView = findViewById(R.id.add_edittext_hour);
        addButton = findViewById(R.id.add_button_add);
        amountEditText = findViewById(R.id.add_edittext_amount);
        foodEditText = findViewById(R.id.add_edittext_food);
        detailEditText = findViewById(R.id.add_edittext_detail);
        consumEditText = findViewById(R.id.add_edittext_consum);

        realm = Realm.getDefaultInstance();
    }

    private void setSpinners() {
        conditionList = getResources().getStringArray(R.array.conditionArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.add_spinner_layout, R.id.spinner_add_text, conditionList);
        conditionSpinner.setAdapter(adapter);
        drugTypeList = getResources().getStringArray(R.array.drugTypeArray);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.add_spinner_layout, R.id.spinner_add_text , drugTypeList);
        drugTypeSpinner.setAdapter(adapter2);
    }

    private void setPickers() {
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        dateTextView.setText("" + year + " / " + (monthOfYear+1) + " / " + dayOfMonth);
                        PersianCalendar pc = new PersianCalendar();
                        pc.setPersianDate(year , monthOfYear , dayOfMonth);
                        dayOfWeek = pc.getPersianWeekDayName();
                    }
                    }, now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay());
                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(getFragmentManager(), "datePicker");
            }
        });
        hourTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar now = new PersianCalendar();
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                        hourTextView.setText(hourOfDay + " : " + minute);
                    }
                } ,hour , minutes , true );
                timePickerDialog.setThemeDark(true);
                timePickerDialog.show(getFragmentManager() , "timePicker");

            }
        });
    }

    private void setButtons() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    private void addData() {

        if (dateTextView.getText().toString().equals("")){
            Toast.makeText(Add.this, R.string.dateError, Toast.LENGTH_SHORT).show();
        }
        else {
            final Information information = new Information();
            int amount;
            String date;
            String hour;
            String condition;
            String drugType;
            String food;
            String measure;
            String detail;

            Number currentId = realm.where(Information.class).max("id");
            long nextId = 0;
            if (currentId == null) nextId = 1;
            else nextId = (long) currentId + 1;
            if (amountEditText.getText().toString().equals("")) {
                amount = 0;
            } else {
                amount = Integer.parseInt(amountEditText.getText().toString());
            }
            if (hourTextView.getText().toString().equals("")) {
                hour = "-";
            } else {
                hour = hourTextView.getText().toString();
            }
            date = dateTextView.getText().toString();
            condition = conditionSpinner.getSelectedItem().toString();
            drugType = drugTypeSpinner.getSelectedItem().toString();
            if (foodEditText.getText().toString().equals("")) {
                food = "-";
            } else {
                food = foodEditText.getText().toString();
            }
            if (consumEditText.getText().toString().equals("")) {
                measure = "-";
            } else {
                measure = consumEditText.getText().toString();
            }
            if (detailEditText.getText().toString().equals("")) {
                detail = "-";
            } else {
                detail = detailEditText.getText().toString();
            }
            information.setAll(nextId, amount, date, hour, condition, food, drugType, measure, detail, dayOfWeek);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(information);
                    Toast.makeText(Add.this, R.string.information_registered, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}