package com.example.diabetesnote.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import io.realm.Realm;

public class Edit extends AppCompatActivity {

    private Intent editIntent;
    private Bundle bundle;
    private long id;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    private Spinner conditionSpinner;
    private Spinner drugTypeSpinner;
    private TextView dateTextView;
    private TextView hourTextView;
    private EditText amountEditText;
    private EditText foodEditText;
    private EditText detailEditText;
    private EditText consumEditText;
    private Button editButton;
    private Button deleteButton;
    private String dayOfWeek;
    //dateFlag is true when date is not changed
    private boolean dateflag;
    Realm realm;
    String[] conditionList;
    String[] drugTypeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Init();
        getId();
        setViews();
        setButtons();
        setPickers();
    }

    private void Init() {
        editIntent = getIntent();
        bundle = editIntent.getExtras();
        conditionSpinner = findViewById(R.id.edit_spinner_condition);
        drugTypeSpinner = findViewById(R.id.edit_spinner_drug_type);
        dateTextView = findViewById(R.id.edit_edittext_date);
        hourTextView = findViewById(R.id.edit_edittext_hour);
        editButton = findViewById(R.id.edit_button_edit);
        deleteButton = findViewById(R.id.edit_button_remove);
        amountEditText = findViewById(R.id.edit_edittext_amount);
        foodEditText = findViewById(R.id.edit_edittext_food);
        detailEditText = findViewById(R.id.edit_edittext_detail);
        consumEditText = findViewById(R.id.edit_edittext_consum);
        dateflag = true;
        realm = Realm.getDefaultInstance();
    }

    private void getId() {
        if(bundle != null)
            id = bundle.getLong("Id" , -1);
    }

    private void setViews() {
        setSpinners();
        Information info = realm.where(Information.class).equalTo("id" , id).findFirst();
        amountEditText.setText(String.valueOf(info.getAmount()));
        dateTextView.setText(info.getDate());
        hourTextView.setText(info.getHour());
        int conditionIndex = adapter.getPosition(info.getCondition());
        int drugTypeIndex = adapter2.getPosition(info.getDrugType());
        conditionSpinner.setSelection(conditionIndex);
        drugTypeSpinner.setSelection(drugTypeIndex);
        consumEditText.setText(info.getMeasure());
        foodEditText.setText(info.getFood());
        detailEditText.setText(info.getDetail());
    }
    private void setSpinners() {
        conditionList = getResources().getStringArray(R.array.conditionArray);
        adapter = new ArrayAdapter<String>(this,
                R.layout.add_spinner_layout, R.id.spinner_add_text, conditionList);
        conditionSpinner.setAdapter(adapter);
        drugTypeList = getResources().getStringArray(R.array.drugTypeArray);
        adapter2 = new ArrayAdapter<String>(this,
                R.layout.add_spinner_layout, R.id.spinner_add_text , drugTypeList);
        drugTypeSpinner.setAdapter(adapter2);
    }
    private void setButtons() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

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
                        dateflag = false;
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

    private void editData() {
        if (dateTextView.getText().toString().equals("")){
            Toast.makeText(Edit.this, R.string.dateError, Toast.LENGTH_SHORT).show();
        }
        else {
            final Information info = realm.where(Information.class).equalTo("id" , id).findFirst();

            final int amount;
            final String date;
            final String hour;
            final String condition;
            final String drugType;
            final String food;
            final String measure;
            final String detail;
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
            if(dateflag)
                dayOfWeek = info.getDayOfWeek();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    info.setAllForEdit( amount, date, hour, condition, food, drugType, measure, detail, dayOfWeek);
                    realm.copyToRealmOrUpdate(info);
                    Toast.makeText(Edit.this, R.string.information_edited, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private void deleteData(){
        final Information information = realm.where(Information.class).equalTo("id" , id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                information.deleteFromRealm();
                Toast.makeText(Edit.this, R.string.information_deleted, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}