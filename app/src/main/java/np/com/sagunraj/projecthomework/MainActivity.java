package np.com.sagunraj.projecthomework;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView totalrecords;
private Button addBtn;
private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalrecords = findViewById(R.id.records);
        addBtn = findViewById(R.id.addBtn);
        lv = findViewById(R.id.lv);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_add_data, null); //inflation of a smaller view into the larger view
                final EditText name = convertView.findViewById(R.id.name);
                final EditText address = convertView.findViewById(R.id.address);
                final EditText phone = convertView.findViewById(R.id.phone);
                final EditText email = convertView.findViewById(R.id.email);
                Button saveBtn = convertView.findViewById(R.id.saveBtn);
                Button cancelBtn = convertView.findViewById(R.id.cancelBtn);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this); //creation of a dialog box
                alert.setView(convertView);
                final AlertDialog dialog = alert.create();
                dialog.show();

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataModule m = new DataModule();
                        m.setName(name.getText().toString());
                        m.setAddress(address.getText().toString());
                        m.setEmail(email.getText().toString());
                        m.setPhone(phone.getText().toString());

                        MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);
                        db.addRecords(m);

                        Toast.makeText(MainActivity.this, "The records have been added successfully.", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                        onResume();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<DataModule> data = new ArrayList<>();
        MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);
        data = db.readRecords();
        totalrecords.setText("Total records = "+data.size());
        lv.setAdapter(new MyAdapter(MainActivity.this, data));
    }
}
