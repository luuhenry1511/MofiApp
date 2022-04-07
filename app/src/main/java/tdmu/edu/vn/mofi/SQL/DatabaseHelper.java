package tdmu.edu.vn.mofi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tdmu.edu.vn.mofi.modals.Giaodich;
import tdmu.edu.vn.mofi.modals.Khoantien;
import tdmu.edu.vn.mofi.modals.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Mofi.db";

    private static final String TABLE_USER = "user";
    // Các cột cho bảng user
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    // Tạo bảng user
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    // drop table user
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String TABLE_PHANLOAI= "phanloai";
    //các cột bảng phân loại
    private static final String COLUMN_PL_ID = "pl_id";
    private static final String COLUMN_PL_KHOANTIEN = "pl_khoantien";
    private static final String COLUMN_PHANLOAI = "pl_phanloai";
    private static final String COLUMN_PL_USER = "pl_user";
    // Tạo bảng phân loại
    private String CREATE_PL_TABLE = "CREATE TABLE " + TABLE_PHANLOAI+ "("
            + COLUMN_PL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PL_KHOANTIEN + " TEXT," + COLUMN_PL_USER + " TEXT,"
            + COLUMN_PHANLOAI + " TEXT" + ")";
    // drop table phân loại
    private String DROP_PL_TABLE = "DROP TABLE IF EXISTS " + TABLE_PHANLOAI;
    private static final String TABLE_GIAODICH= "giaodich";
    //các cột bảng giao dịch
    private static final String COLUMN_GD_ID = "gd_id";
    private static final String COLUMN_GD_PHANLOAI = "gd_khoantien";
    private static final String COLUMN_GD_LYDO = "gd_lydo";
    private static final String COLUMN_GD_SOTIEN = "gd_sotien";
    private static final String COLUMN_GD_NGAYGD = "gd_ngaygd";
    private static final String COLUMN_GD_NGAY = "gd_ngay";
    private static final String COLUMN_GD_THANG = "gd_thang";
    private static final String COLUMN_GD_NAM = "gd_nam";
    private static final String COLUMN_GD_USER= "gd_user";
    // Tạo bảng phân loại
    private String CREATE_GIAODICH_TABLE = "CREATE TABLE " + TABLE_GIAODICH+ "("
            + COLUMN_GD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_GD_PHANLOAI + " TEXT,"
            + COLUMN_GD_LYDO + " TEXT," + COLUMN_GD_SOTIEN + " TEXT," + COLUMN_GD_NGAYGD + " TEXT,"
            + COLUMN_GD_NGAY + " TEXT," + COLUMN_GD_THANG + " TEXT," + COLUMN_GD_NAM + " TEXT," + COLUMN_GD_USER + " TEXT" +")";
    // Drop bảng giao dịch
    private String DROP_GIAODICH_TABLE = "DROP TABLE IF EXISTS " + TABLE_GIAODICH;
    /**
     * Constructor
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PL_TABLE);
        db.execSQL(CREATE_GIAODICH_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PL_TABLE);
        db.execSQL(DROP_GIAODICH_TABLE);

        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<User> getAllUser() {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }



    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //-------------------------------------------------------------KHOẢN THU KHOẢN CHI---------------------------------------------------------
    public long themkhoanthuchi(String khoanthukhoanchi, String phanloai, String user) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COLUMN_PL_KHOANTIEN, khoanthukhoanchi);
        cv.put(COLUMN_PHANLOAI, phanloai);
        cv.put(COLUMN_PL_USER, user);

        return db.insert(TABLE_PHANLOAI, null, cv);
    }

    public ArrayList<Khoantien> getKhoantien(String khoantien, String user) {
        ArrayList<Khoantien> names = new ArrayList<>();
        String selectQuery = "SELECT pl_phanloai,pl_khoantien,pl_user FROM phanloai WHERE pl_khoantien= '" + khoantien +
                "'" + " AND "
                + COLUMN_PL_USER + "=" + "'" + user + "' ORDER BY pl_phanloai DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Khoantien kt= new Khoantien();
                kt.setPhanloai(cursor.getString(0));
                kt.setKhoanthukhoanchi(cursor.getString(1));
                kt.setUser(cursor.getString(2));
                names.add(kt);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return names;
    }

    public boolean kiemtra(String khoantien, String kiemtra, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT  COUNT(*) from " + TABLE_PHANLOAI + " where "
                + COLUMN_PHANLOAI+ "=" + "'" + kiemtra + "'" + " AND "
                + COLUMN_PL_KHOANTIEN + "=" + "'" + khoantien + "'" +" AND " + COLUMN_PL_USER + "=" + "'" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        int so = Integer.parseInt(names.get(0));
        if (so == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Lấy giao dịch thuộc về khoản tiền
    public ArrayList<Giaodich> getGDtuKhoantien(String khoantien, String user, String thang) {
        ArrayList<Giaodich> names = new ArrayList<>();
        String selectQuery = "SELECT gd_lydo, gd_sotien, gd_ngaygd, gd_user from giaodich, phanloai where pl_phanloai=gd_khoantien and gd_user='"
                + user+"' and pl_phanloai='"+ khoantien+"' and gd_thang = '"+ thang+"' and gd_nam =strftime('%Y','now') ORDER BY gd_ngaygd DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Giaodich kt= new Giaodich();
                kt.setPhanloai(cursor.getString(0));
                kt.setSotien(cursor.getString(1));
                kt.setTime(cursor.getString(2));
                kt.setUser(cursor.getString(3));
                names.add(kt);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return names;
    }
    //Kiểm tra xem khoản thu có phát sinh gd nào chưa rùi mới xóa
    public boolean kiemtratrckhixoa(String khoantien, String phanloai, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT COUNT(*) from giaodich, phanloai where pl_phanloai=gd_khoantien AND " + COLUMN_PL_USER + "=" + "'" + user + "'" +
                "AND pl_phanloai= '"+ phanloai+ "' AND pl_khoantien='" + khoantien+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        int so = Integer.parseInt(names.get(0));
        if (so == 0) {
            return true;
        } else {
            return false;
        }
    }
    public void Delete(String phanloai, String khoanthukhoanchi, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PHANLOAI + " WHERE " + COLUMN_PHANLOAI
                + "='" + phanloai + "'" + " AND " + COLUMN_PL_KHOANTIEN
                + " = '" + khoanthukhoanchi + "'"+ " AND " + COLUMN_PL_USER
                + " = '" + user + "'");
        db.close();
    }

    // ---------------------------------------------------CÁC GIAO DỊCH-------------------------------------------------------
    public long themgiaodich(String phanloai,
                             String lydo, String sotien, String ngaygiaodich,
                             String ngay, String thang, String nam, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_GD_PHANLOAI, phanloai);
        cv1.put(COLUMN_GD_LYDO, lydo);
        cv1.put(COLUMN_GD_SOTIEN, sotien);
        cv1.put(COLUMN_GD_NGAYGD, ngaygiaodich);
        cv1.put(COLUMN_GD_NGAY, ngay);
        cv1.put(COLUMN_GD_THANG, thang);
        cv1.put(COLUMN_GD_NAM, nam);
        cv1.put(COLUMN_GD_USER, user);
        return db.insert(TABLE_GIAODICH, null, cv1);

    }
    public long updategiaodich(String phanloai,
                             String lydo, String sotien, String ngaygiaodich,
                             String ngay, String thang, String nam, String user, String ngaygdcu, String sotiencu, String lydocu) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(COLUMN_GD_PHANLOAI, phanloai);
        cv1.put(COLUMN_GD_LYDO, lydo);
        cv1.put(COLUMN_GD_SOTIEN, sotien);
        cv1.put(COLUMN_GD_NGAYGD, ngaygiaodich);
        cv1.put(COLUMN_GD_NGAY, ngay);
        cv1.put(COLUMN_GD_THANG, thang);
        cv1.put(COLUMN_GD_NAM, nam);
        return db.update(TABLE_GIAODICH, cv1, "gd_user='"+user+ "' and gd_sotien='"+ sotiencu + "' and gd_ngaygd='"+ ngaygdcu+ "' and gd_lydo='"+ lydocu+"'",null);

    }
    public List<String> getAllNames(String thuchi, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT " + COLUMN_PHANLOAI + " FROM "
                + TABLE_PHANLOAI + " WHERE " + COLUMN_PL_KHOANTIEN + " = "
                + "'" + thuchi + "' and pl_user='"+ user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
    public List<String> getchitietGD(String lydo, String sotien, String ngaygd, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT gd_khoantien, gd_lydo, gd_sotien, gd_ngaygd, pl_khoantien FROM giaodich, phanloai WHERE gd_khoantien=pl_phanloai and " +
                "gd_user='"+ user +"' AND gd_lydo='"+ lydo +"' AND gd_sotien='"+sotien + "' AND gd_ngaygd='"+ ngaygd +"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
    public List<String> getloaiGD(String lydo, int sotien, String ngaygd, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery;
        if (sotien>0){
            selectQuery = "SELECT pl_phanloai FROM giaodich, phanloai WHERE gd_khoantien=pl_phanloai and " +
                    "gd_user='"+ user +"' AND gd_lydo='"+ lydo +"' AND gd_ngaygd='"+ ngaygd+ "' AND pl_khoantien='THU';";
        } else {
            selectQuery = "SELECT pl_phanloai FROM giaodich, phanloai WHERE gd_khoantien=pl_phanloai and " +
                    "gd_user='"+ user +"' AND gd_lydo='"+ lydo +"' AND gd_ngaygd='"+ ngaygd+ "' AND pl_khoantien='CHI';";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
    //xóa GD
    public void DeleteGD(String lydo, String khoantien, String ngaygd, String sotien, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM giaodich WHERE gd_lydo='"
                + lydo + "'" + " AND gd_khoantien='"
                + khoantien + "'"+ " AND gd_ngaygd='"
                 + ngaygd + "' AND gd_sotien='" + sotien+"' AND gd_user='"+ user +"'");
        db.close();
    }
    //kt xem có dg này trc đó hay chưa
    public boolean kiemtragdcotontai(String lydo, String khoantien, String ngaygd, String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT COUNT(*) FROM giaodich, phanloai WHERE gd_khoantien=pl_phanloai and " +
                "gd_user='"+ user +"' AND gd_lydo='"+ lydo +"' AND gd_ngaygd='"+ ngaygd+ "' AND pl_khoantien='"+ khoantien +"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        int so = Integer.parseInt(names.get(0));
        if (so == 0) {
            return false;
        } else {
            return true;
        }
    }
    //---------------------------------------------Page Activity-----------------------------------------------------------------------
    public List<String> Tinhtongtien(String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT sum ( "
                + COLUMN_GD_SOTIEN
                + " ) FROM "
                + TABLE_GIAODICH
                + " where gd_user= '"+ user+ "' and gd_nam =strftime('%Y','now');";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            if (cursor != null & cursor.moveToFirst()) {
                do {
                    names.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return names;
    }
    //TỔNG THU THÁNG
    public List<String> Sumthuthang(String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT sum ( "
                + COLUMN_GD_SOTIEN
                + " ) FROM "
                + TABLE_GIAODICH
                + " where gd_user= '"+ user+ "'and gd_thang = strftime('%m','now') and gd_nam =strftime('%Y','now') and gd_sotien>0;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            if (cursor != null & cursor.moveToFirst()) {
                do {
                    names.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return names;
    }
    //TỔNG CHI THÁNG
    public List<String> Sumchithang(String user) {
        List<String> names = new ArrayList<String>();
        String selectQuery = "SELECT sum ( "
                + COLUMN_GD_SOTIEN
                + " ) FROM "
                + TABLE_GIAODICH
                + " where gd_user= '"+ user+ "'and gd_thang = strftime('%m','now') and gd_nam =strftime('%Y','now') and gd_sotien<0;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            if (cursor != null & cursor.moveToFirst()) {
                do {
                    names.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return names;
    }
    public List<Giaodich> lichsugiaodich(String user) {
        ArrayList<Giaodich> lichsugiaodich = new ArrayList<>();
        String selectQuery = "SELECT gd_ngaygd, gd_sotien, gd_lydo, gd_user FROM giaodich where gd_user= '" + user +"' ORDER BY gd_ngaygd DESC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich contacts = new Giaodich();
                contacts.setTime(cursor.getString(0));
                contacts.setPhanloai(cursor.getString(2));
                contacts.setSotien(cursor.getString(1));
                contacts.setUser(cursor.getString(3));
                lichsugiaodich.add(contacts);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return lichsugiaodich;
    }

    //----------------------------------------------THỐNG KÊ----------------------------------------------------------
    public ArrayList<Giaodich> SumThongketheongay(String phanloai, String user, String ngaygd) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT sum( gd_sotien), pl_khoantien, pl_user FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                + "' and  gd_ngaygd='"+ ngaygd +"' and gd_user='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setSotien(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(1));
                thongke.setUser(cursor.getString(2));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }
    public ArrayList<Giaodich> Thongketheongay(String phanloai, String user, String ngaygd) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT gd_ngaygd, gd_sotien, gd_lydo, gd_user  FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                + "' and gd_ngaygd='"+ ngaygd +"' and gd_user='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setTime(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(2));
                thongke.setSotien(cursor.getString(1));
                thongke.setUser(cursor.getString(3));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }

    public ArrayList<Giaodich> SumThongketheothang(String phanloai, String user, String thang, String nam) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT sum( gd_sotien), pl_khoantien, pl_user FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                + "' and  gd_thang='"+ thang +"' and gd_nam='"+ nam+ "' and gd_user='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setSotien(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(1));
                thongke.setUser(cursor.getString(2));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }
    public ArrayList<Giaodich> Thongketheothang(String phanloai, String user, String thang, String nam) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT gd_ngaygd, gd_sotien, gd_lydo, gd_user  FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                + "' and  gd_thang='"+ thang +"' and gd_nam='"+ nam+ "' and gd_user='"+ user +"' ORDER BY gd_ngaygd DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setTime(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(2));
                thongke.setSotien(cursor.getString(1));
                thongke.setUser(cursor.getString(3));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }

    public ArrayList<Giaodich> SumThongketheonam(String phanloai, String user, String nam) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT sum( gd_sotien), pl_khoantien, pl_user FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                +"' and gd_nam='"+ nam+ "' and gd_user='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setSotien(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(1));
                thongke.setUser(cursor.getString(2));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }

    public ArrayList<Giaodich> Thongketheonam(String phanloai, String user, String nam) {
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT gd_ngaygd, gd_sotien, gd_lydo, gd_user  FROM giaodich,phanloai where gd_user= pl_user and pl_phanloai = gd_khoantien and pl_khoantien='" + phanloai
                 +"' and gd_nam='"+ nam+ "' and gd_user='"+ user +"' ORDER BY gd_ngaygd DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich thongke=new Giaodich();
                thongke.setTime(cursor.getString(0));
                thongke.setPhanloai(cursor.getString(2));
                thongke.setSotien(cursor.getString(1));
                thongke.setUser(cursor.getString(3));
                names.add(thongke);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }
    //-------------------------------------------TRANG INFO---------------------------------------------
    public void updateUser(String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PASSWORD, pass);
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = '"+email+ "'",
                null);
        db.close();
    }
    public ArrayList<Giaodich> laythongtinUser(String email){
        ArrayList<Giaodich> names = new ArrayList<Giaodich>();

        String selectQuery = "SELECT user_name, user_password FROM user where user_email='" + email+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Giaodich ngdung=new Giaodich();
                ngdung.setUser(cursor.getString(0));
                ngdung.setPhanloai(cursor.getString(1));
                names.add(ngdung);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }
    //--------------------------------  QUÊN MẬT KHẨU -----------------------------------------------------------------
    public Boolean Check_Mail_User( String gmail){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where user_email=?", new String[] {gmail});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean updatePasswordUser(String gmail,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("user_password", password);

        long result= db.update("user",values,"user_email = " + "'"+gmail+"'", null);
        if(result==-1) return false;
        else
            return true;
    }
}
