package com.example.ninwa.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by ninwa on 30/3/2559.
 */
public class DBHelper extends SQLiteOpenHelper {
    public final Context myContext;
    static SQLiteDatabase sqLiteDatabase;
    public static final int DATABASE_VERSION = 1;
    private static String DB_DIR = "/data/data/dictionary/";
    //private static String DB_DIR = "/data/data/com.example.ninwa.dictionary/databases/";
    private static String DB_NAME = "lexitron.sqlite";
    private static String DB_PATH = "";

    private static String TAG = "DataBaseHelper";
    public static final String DICTIONARY_TABLE_NAME = "eng2thai";
    public static final String DICTIONARY_COLUMN_ID = "id";
    public static final String DICTIONARY_COLUMN_WORDENG = "esearch";
    public static final String DICTIONARY_COLUMN_WORDTHAI = "tentry";
    public static final String DICTIONARY_COLUMN_CATEGORY = "ecat";
    public static final String DICTIONARY_COLUMN_ETHAI = "ethai";
    public static final String DICTIONARY_COLUMN_ESYN = "esyn";
    public static final String DICTIONARY_COLUMN_EANT = "eant";

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;


    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        sqLiteDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return sqLiteDatabase != null;
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS eng2thai");
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM eng2thai WHERE id=" + id, null);

        return res;
    }

    public int numberOfRow() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numberRows = (int) DatabaseUtils.queryNumEntries(db, DICTIONARY_TABLE_NAME);
        return numberRows;
    }

    public ArrayList<String> getAllThai() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM eng2thai", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_WORDTHAI)));
            res.moveToNext();
        }
        return arrayList;

    }


    public ArrayList<String> getAllWords() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT DISTINCT esearch FROM eng2thai", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_WORDENG)));
            res.moveToNext();
        }
        return arrayList;


    }

    public ArrayList<String> getWords(String words) {


        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT DISTINCT esearch FROM eng2thai WHERE esearch LIKE '" + words + "%' ORDER BY esearch ASC", null);
        res.moveToFirst();


        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_WORDENG)));
            res.moveToNext();
        }

        return arrayList;
    }

    public ArrayList<String> getDisplaylist(String word) {


        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM eng2thai WHERE esearch LIKE '" + word + "%' ORDER BY esearch ASC", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_WORDENG)));
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_WORDTHAI)));
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_CATEGORY)));
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_ETHAI)));
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_ESYN)));
            arrayList.add(res.getString(res.getColumnIndex(DICTIONARY_COLUMN_EANT)));
            res.moveToNext();
        }

        return arrayList;

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
