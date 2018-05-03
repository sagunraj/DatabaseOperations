package np.com.sagunraj.projecthomework;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    public List<DataModule> list = new ArrayList<>();
    public Context context;


    public MyAdapter(MainActivity mainActivity, List<DataModule> data) {
        context = mainActivity;
        list = data;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.activity_view_data, null);
        TextView idTv = view.findViewById(R.id.idTv);
        TextView nameTv = view.findViewById(R.id.nameTv);
        TextView addressTv = view.findViewById(R.id.addressTv);
        TextView emailTv = view.findViewById(R.id.emailTv);
        idTv.setText("ID: "+list.get(i).getId());
        nameTv.setText("Name: "+list.get(i).getName());
        addressTv.setText("Address: "+list.get(i).getAddress());
        emailTv.setText("Email: "+list.get(i).getEmail());

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final View convertView = LayoutInflater.from(context).inflate(R.layout.activity_update_data, null);
                final EditText name, address, phone, email;
                Button updateBtn, deleteBtn, cancelBtn;
                name = convertView.findViewById(R.id.name);
                address = convertView.findViewById(R.id.address);
                phone = convertView.findViewById(R.id.phone);
                email = convertView.findViewById(R.id.email);
                updateBtn = convertView.findViewById(R.id.updateBtn);
                deleteBtn = convertView.findViewById(R.id.deleteBtn);
                cancelBtn = convertView.findViewById(R.id.cancelBtn);

                //show old values in EditText
                name.setText(list.get(i).getName());
                phone.setText(list.get(i).getPhone());
                email.setText(list.get(i).getEmail());
                address.setText(list.get(i).getAddress());
                AlertDialog.Builder alert = new AlertDialog.Builder(context); //creation of a dialog box
                alert.setView(convertView);
                final AlertDialog dialog = alert.create();
                dialog.show();

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataModule m = new DataModule();
                        m.setName(name.getText().toString());
                        m.setEmail(email.getText().toString());
                        m.setPhone(phone.getText().toString());
                        m.setAddress(address.getText().toString());
                        m.setId(list.get(i).getId());

                        MyDatabaseHelper db = new MyDatabaseHelper(context);
                        db.updateRecords(m);
                        MainActivity m1 = (MainActivity) context;
                        m1.onResume();
                        dialog.dismiss();
                        Toast.makeText(context, "Your data have been updated.", Toast.LENGTH_LONG).show();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyDatabaseHelper db = new MyDatabaseHelper(context);
                        db.deleteRecord(list.get(i).getId());
                        MainActivity main = (MainActivity) context; //making an object of MainActivity to access the function in MainActivity
                        main.onResume();
                        dialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                return true;
            }
        });

        return view;
    }
}
