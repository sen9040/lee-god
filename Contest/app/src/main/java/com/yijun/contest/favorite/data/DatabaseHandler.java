package com.yijun.contest.favorite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yijun.contest.favorite.model.Favorite;
import com.yijun.contest.favorite.util.Util;


import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. 테이블 생성문 SQLite 문법에 맞게 작성해야 한다.
        String CREATE_FAVOTIE_TABLE = "create table " +
                Util.TABLE_NAME + "(" +
                Util.KEY_ID + " integer not null primary key autoincrement," +
                Util.KEY_TITLE + " text, " +
                Util.KEY_ADDRESS + " text, "+
                Util.KEY_IMG + " text )";


        // 2. 쿼리 실행
        db.execSQL(CREATE_FAVOTIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "drop table " + Util.TABLE_NAME;
        db.execSQL(DROP_TABLE);

        // 테이블 새로 다시 생성.
        onCreate(db);
    }

    // 주소 저장하는 메소드 : 오버라이딩이 아니라, 우리가 만들어줘야 하는 메소드
    public void addContact(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_TITLE, favorite.getTitle());
        values.put(Util.KEY_ADDRESS, favorite.getAddress());
        values.put(Util.KEY_IMG, favorite.getImg());
        // 3. db에 실제로 저장한다.
        db.insert(Util.TABLE_NAME, null, values);
        db.close();
        Log.i("myDB", "inserted.");
    }

    // 주소 1개 가져오는 메소드 : 우리가 만들어줘야 하는 메소드.

    public Favorite getContact(int id) {

        // 1. 데이터베이스 가져온다. 조회니까, readable 한 db로 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        // select id, name, phone_number from contacts where id = 3;
        // 2. 데이터를 셀렉트(조회) 할때는, Cursor 를 이용해야 한다.
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{"id", "title", "address","img"},
                Util.KEY_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        int selectedId = Integer.parseInt(cursor.getString(0));
        String selectedTitle = cursor.getString(1);
        String selectedAddress = cursor.getString(2);
        String selectedImg = cursor.getString(3);

        // db에서 읽어온 데이터를, 자바 클래스로 처리한다.
        Favorite favorite = new Favorite();
        favorite.setId(selectedId);
        favorite.setTitle(selectedTitle);
        favorite.setAddress(selectedAddress);
        favorite.setImg(selectedImg);

        return favorite;
    }

    // 디비에 저장된 모든 주소록 정보를 불러오는 메소드.
    public ArrayList<Favorite> getAllFavorite() {
        // 1. 비어 있는 어레이 리스트를 먼저 한개 만든다.
        ArrayList<Favorite> favoriteList = new ArrayList<>();

        // 2. 데이터베이스에 select (조회) 해서,
        String selectAll = "select * from " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        // 3. 여러개의 데이터를 루프 돌면서, Contact 클래스에 정보를 하나씩 담고
        if (cursor.moveToFirst()) {
            do {
                int selectedId = Integer.parseInt(cursor.getString(0));
                String selectedTitle = cursor.getString(1);
                String selectedAddress = cursor.getString(2);
                String selectedImg = cursor.getString(3);
                // db에서 읽어온 데이터를, 자바 클래스로 처리한다.
                Favorite favorite = new Favorite();
                favorite.setId(selectedId);
                favorite.setTitle(selectedTitle);
                favorite.setAddress(selectedAddress);
                favorite.setImg(selectedImg);

                // 4. 위의 빈 어레이리스트에 하나씩 추가를 시킨다.
                favoriteList.add(favorite);

            } while (cursor.moveToNext());
        }
        return favoriteList;
    }

    // 데이터를 업데이트 하는 메서드.
    public int updateFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_TITLE, favorite.getTitle());
        values.put(Util.KEY_ADDRESS, favorite.getAddress());
        values.put(Util.KEY_IMG, favorite.getImg());

        // 데이터베이스 테이블 업데이트.
        // update contacts set name="홍길동", phone="010-2222-2222" where id = 3;
        int ret = db.update(Util.TABLE_NAME,    // 테이블명
                values,     // 업데이트할 값
                Util.KEY_ID + " = ?",   // where
                new String[]{String.valueOf(favorite.getId())}); // ? 에 들어갈 값
        return ret;
    }

    // 데이터 삭제 메서드
    public void deletetFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,  // 테이블 명
                Util.KEY_ID + " = ?",   // where id = ?
                new String[]{String.valueOf(favorite.getId())});  // ? 에 해당하는 값.
        db.close();
    }

    // 테이블에 저장된 주소록 데이터의 전체 객수를 리턴하는 메소드.
    public int getCount() {
        // select count(*) from countacts;
        String countQuery = "select * from " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        db.close();
        return count;
    }
//검색용 메소드 추가
    public ArrayList<Favorite> getLike(String search) {
        //select * from table where id = "id" like "%search%"
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Favorite> favoriteList = new ArrayList<>();

//        Cursor cursor = db.query(Util.TABLE_NAME,
//                new String[]{Util.KEY_ID, Util.KEY_TITLE, Util.KEY_MEMO},
//                Util.KEY_MEMO + " like ?", new String[]{"%" + search + "%"},
//                null, null, null);
        Cursor cursor1 = db.query(Util.TABLE_NAME,
           new String[]{Util.KEY_ID, Util.KEY_TITLE, Util.KEY_ADDRESS, Util.KEY_IMG},
                Util.KEY_TITLE + " like ?", new String[]{"%" + search + "%"},
                null, null, null);

//        if (cursor.moveToFirst()) {
//            do {
//                Log.i("myDB", "do while");
//                int selectedId = Integer.parseInt(cursor.getString(0));
//                String selectedTitle = cursor.getString(1);
//                String selectedContent = cursor.getString(2);
//
//                // db 에서 읽어온 데이터를 , 자바 클래스로 처리한다.
//                Contact memo = new Contact();
//                memo.setId(selectedId);
//                memo.setTitle(selectedTitle);
//                memo.setMemo(selectedContent);
//
//                // 4. 위의 빈 에레이 리스트에 하나씪 추가를 시킨다.
//                memoList.add(memo);
//
//            } while (cursor.moveToNext());
//        }
        if (cursor1.moveToFirst()) {
            do {
                Log.i("myDB", "do while");
                int selectedId = Integer.parseInt(cursor1.getString(0));
                String selectedTitle = cursor1.getString(1);
                String selectedAddress = cursor1.getString(2);
                String selectedImg = cursor1.getString(3);

                // db 에서 읽어온 데이터를 , 자바 클래스로 처리한다.
                Favorite favorite = new Favorite();
                favorite.setId(selectedId);
                favorite.setTitle(selectedTitle);
                favorite.setAddress(selectedAddress);
                favorite.setImg(selectedImg);

                // 4. 위의 빈 에레이 리스트에 하나씪 추가를 시킨다.
                favoriteList.add(favorite);

            } while (cursor1.moveToNext());
        }
        return favoriteList;

    }
}
