package np.com.sagunraj.projecthomework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "mydatabase"; //database name
    public MyDatabaseHelper(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS datatable(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, address VARCHAR, phone VARCHAR, email VARCHAR)"); //datatable is table name
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS datatable");
        onCreate(sqLiteDatabase);
    }

    public void addRecords(DataModule m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", m.getName());
        cv.put("address", m.getAddress());
        cv.put("email", m.getEmail());
        cv.put("phone", m.getPhone());
        db.insert("datatable", null, cv);
        db.close();
    }

    public List<DataModule> readRecords() {
        List<DataModule> myData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datatable", null);
        if(cursor.getCount()!=0){
            if(cursor.moveToFirst()){
                do{
                    DataModule m = new DataModule();
                    m.setId(cursor.getInt(0));
                    m.setName(cursor.getString(cursor.getColumnIndex("name"))); //or use cursor.getString(1);
                    m.setAddress(cursor.getString(2));
                    m.setPhone(cursor.getString(3));
                    m.setEmail(cursor.getString(4));
                    myData.add(m);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return myData;
    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM datatable WHERE id="+id);
        db.close();
    }

    public void updateRecords(DataModule m) {
        SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("name", m.getName());
       cv.put("address", m.getAddress());
       cv.put("email", m.getEmail());
       cv.put("phone", m.getPhone());
       db.update("datatable", cv, "id="+m.getId(), null);
       db.close();
    }
}
