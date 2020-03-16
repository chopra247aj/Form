package com.example.form;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {
  EditText ename,edob,eemail,efone,eaddress;
  RadioGroup rg;
  Button b1,b2;
  CheckBox c1;
  Spinner spinner;
  AutoCompleteTextView autoCompleteTextView;
    String[] country = { "India", "USA", "China", "Japan", "Other"};
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename=findViewById(R.id.ename);
        edob=findViewById(R.id.edob);
        eemail=findViewById(R.id.eemail);
        efone=findViewById(R.id.efone);
        eaddress=findViewById(R.id.eaddress);
        rg=findViewById(R.id.rg);
        b1=findViewById(R.id.submit);
        c1=findViewById( R.id.c1);
        b2=findViewById(R.id.list1);
        spinner=findViewById(R.id.spinner);
        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        ArrayAdapter<String> aa2=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,language);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(aa2);
        autoCompleteTextView.setTextColor(Color.RED);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1.isChecked()) {

                    String name, email, gender, dob, fone ,address,place;
                    name = ename.getText().toString();
                    email = eemail.getText().toString();
                    fone= efone.getText().toString();
                    dob = edob.getText().toString();
                    place=spinner.getSelectedItem().toString();
                    RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
                    if(rb!=null) {
                        gender = rb.getText().toString();
                        address = eaddress.getText().toString();
                        Toast.makeText(MainActivity.this, "Readed", Toast.LENGTH_SHORT).show();
                        Log.d("Resigt", "Name: " + name);
                        Log.d("Resigt", "Email: " + email);
                        Log.d("Resigt", "Phone: " + fone);
                        Log.d("Resigt", "Address: " + address);
                        Log.d("Resigt", "Gender: " + gender);

                        if (isValid(email)) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("Name : " + name + "\n\n");
                            buffer.append("Email : " + email + "\n\n");
                            buffer.append("Phone : " + fone + "\n\n");
                            buffer.append("DOB : " + dob + "\n\n");
                            buffer.append("Gender : " + gender + "\n\n");
                            buffer.append("Address : " + address + "\n\n");
                            buffer.append("Country : " + place + "\n\n");
                            TextView t1 = findViewById(R.id.show);
                            t1.setText(buffer.toString());
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Email", email);
                            user.put("Phone No", fone);
                            user.put("DOB", dob);
                            user.put("Gender", gender);
                            user.put("Address", address);
                            user.put("Country", place);
                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MainActivity.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Error adding document", Toast.LENGTH_SHORT).show();
                                            //Log.w(TAG, "Error adding document", e);
                                        }
                                    });



                        } else {
                            Toast.makeText(MainActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Choose Gender", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Plz check Terms and Conditions", Toast.LENGTH_SHORT).show();
                }

            }

        });
               b2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent in =new Intent(MainActivity.this,Main2Activity.class);
                       startActivity(in);
                   }
               });
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
        switch(id){
            case R.id.ask:


                break;
            case R.id.item2:
                Toast.makeText(this, "Show Activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this, "View Activity", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
