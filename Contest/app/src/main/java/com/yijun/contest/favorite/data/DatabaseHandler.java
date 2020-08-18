package com.yijun.contest.favorite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nightonke.boommenu.Util;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.utils.Utils;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);      //하드코딩말고 상수로 데이터처리.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. 테이블 생성문 SQLite 문법에 맞게 작성해야 한다.
        // 테이블생성문, mysql 문법 대신 SQLite 문법(varchar 대신 text, int 대신 integer, autoincrement 에 _가 없고 primary key 뒤에 와야함.) => 문법이 다 다름.
        String CREATE_FAVORITE_TABLE = "create table " + Utils.TABLE_NAME +
                "(" + Utils.KEY_ID + " integer primary key autoincrement, " +
                Utils.KEY_IMG + " text, " +
                Utils.KEY_TITLE + " text, " +
                Utils.KEY_ADDRESS + " text, " +
                Utils.KEY_PRICE + " text, " +
                Utils.KEY_TIME + " text, " +
                Utils.KEY_FAVORITE + " boolean )";
        // create table contacts
        // (id integer not null autoincrement primary key,
        // name text,
        // phone_number text )

        // 2. 쿼리 실행
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "drop table " + Utils.TABLE_NAME;
        db.execSQL(DROP_TABLE);

        // 테이블 새로 다시 생성.
        onCreate(db);
    }

    // 주소 저장하는 메소드 : 오버라이딩이 아니라, 우리가 만들어줘야 하는 메소드
    // 여기서부터는 기획에 맞게 데이터베이스에 넣고, 업데이트, 가져오고, 지우고 메소드 만들기
    public void addFavorite(Favorite favorite){
        Log.i("myDB","addFavorite.");
        // 1. 주소를 저장하기 위해서, writable db 를 가져온다.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. db 에 저장하기 위해서는, ContentValues 를 이용한다.
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_IMG, favorite.getImgUrl());
        values.put(Utils.KEY_TITLE, favorite.getTitle());
        values.put(Utils.KEY_ADDRESS, favorite.getAddress());
        values.put(Utils.KEY_PRICE, favorite.getPrice());
        values.put(Utils.KEY_TIME, favorite.getTime());
        values.put(Utils.KEY_FAVORITE, favorite.getIsFavorite());
        // 3. db 에 실제로 저장한다.
        db.insert(Utils.TABLE_NAME, null, values);
        db.close();
        Log.i("myDB","inserted.");
    }

//    // 주소 1개 가져오는 메소드 : 우리가 만들어줘야 하는 메소드.
//    // select * from contacts where id = 3;
//    public Favorite getFavorite (int id){
//
//        // 1. 데이터베이스 가져온다. 조회니까, readable 한 db 로 가져온다.
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // select id, name, phone_number from contacts where id = 3;
//        // 2. 데이터를 섹렉트(조회) 할때는, Cursor 를 이용해야한다.
//        Cursor cursor = db.query(Utils.TABLE_NAME,
//                new String[] {"id", "title", "address", "price", "time", "favorite"},       // String 배열로 조회할컬럼들을 가져옴.
//                Utils.KEY_ID + " = ?", new String[]{String.valueOf(id)},         // = where 절, new 뒤에 부분 = " = ? " 물음표에 들어갈 부분.
//                null, null, null);
//
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//        int selectedId = Integer.parseInt(cursor.getString(0));
//        String selectedTitle = cursor.getString(1);
//        String selectedAddress = cursor.getString(2);
//        String selectedPrice = cursor.getString(3);
//        String selectedTime = cursor.getString(4);
//        String selctedFavorite = cursor.getString(5);
//
//
//        // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
//        Favorite favorite = new Favorite();
//        favorite.setId(selectedId);
//        favorite.setTitle(selectedTitle);
//        favorite.setAddress(selectedAddress);
//        favorite.set
//
//        return contact;
//    }

    // 디비에 저장된 모든 주소록 정보를 불러오는 메소드.
    public ArrayList<Favorite> getAllFavorites(){                 // ArrayList = 배열보다 진화
        // 1. 비어있는 어레이 리스트를 먼저 한개 만든다.
        ArrayList<Favorite> favoriteArrayList = new ArrayList<>();

        // 2. 데이터베이스에 select (조회) 해서,
        String selectAll = "select * from " + Utils.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        // 3. 여러개의 데이터를 루프 돌면서, Contact 클래스에 정보를 하나씩 담고
        if (cursor.moveToFirst()){
            do {
                int selectedId = Integer.parseInt(cursor.getString(0));
                String selectedImg = cursor.getString(1);
                String selectedTitle = cursor.getString(2);
                String selectedAddress = cursor.getString(3);
                String selectedPrice = cursor.getString(4);
                String selectedTime = cursor.getString(5);
                int selectedFavorite = Integer.parseInt(cursor.getString(6));

                // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
                Favorite favorite = new Favorite();
                favorite.setId(selectedId);
                favorite.setImgUrl(selectedImg);
                favorite.setTitle(selectedTitle);
                favorite.setAddress(selectedAddress);
                favorite.setPrice(selectedPrice);
                favorite.setTime(selectedTime);
                favorite.setIsFavorite(selectedFavorite);

                // 4. 위의 빈 어레이리스트에 하나씩 추가를 시킨다.
                favoriteArrayList.add(favorite);

            }while (cursor.moveToNext());
        }
        return favoriteArrayList;

    }

    // 데이터를 업데이트 하는 메서드.
    public int updateFavorite(Favorite favorite){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.KEY_IMG, favorite.getImgUrl());
        values.put(Utils.KEY_TITLE, favorite.getTitle());
        values.put(Utils.KEY_ADDRESS, favorite.getAddress());
        values.put(Utils.KEY_PRICE, favorite.getPrice());
        values.put(Utils.KEY_TIME, favorite.getTime());
        values.put(Utils.KEY_FAVORITE, favorite.getIsFavorite());

        // 데이터베이스 테이블 업데이트.
        // update contacts set title = "홍길동", content = "memo" where id = 3;
        int ret = db.update(Utils.TABLE_NAME,    // 테이블명
                values,     // 업데이트할 값
                Utils.KEY_ID + " = ? ",      // where
                new String[]{String.valueOf(favorite.getId())});     // ?에 들어갈 값
        db.close();
        return ret;
    }

    // 데이터 삭제 메서드.
    public void deleteFavorite(Favorite favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_NAME,      // 테이블명
                Utils.KEY_ID + " = ? ",      // where id = ?
                new String[]{String.valueOf(favorite.getId())});     // ? 에 해당하는 값
        db.close();
    }

    // 테이블에 저장된 주소록 데이터의 전체 갯수를 리턴하는 메소드.
    public int getCount(){
        // select count(*) from contacts;
        String countQuery = "select * from " + Utils.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        db.close();
        return count;
    }

//    // 검색용 메소드 추가
//    public ArrayList<Fa> getLike(String search){
//        // select * from table where id = "id" like "%search%"
////        String searchQuery = "select id, title, content from" + Util.TABLE_NAME + "where content like ? or title like ? ";
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<Contact> memoList = new ArrayList<>();
//        // 메모 검색
////        Cursor cursor = db.query(Util.TABLE_NAME,
////                new String[]{Util.KEY_ID, Util.KEY_TITLE, Util.KEY_MEMO},
////                Util.KEY_MEMO + " like ?", new String[]{"%" + search + "%"},
////                null,null,null);
//        // 제목 검색
//        Cursor cursorTitle = db.query(Util.TABLE_NAME,
//                new String[]{Util.KEY_ID, Util.KEY_TITLE, Util.KEY_MEMO},
//                Util.KEY_MEMO + " like ?", new String[]{"%" + search + "%"},
//                null,null,null);
//        // 더 간단하게 쿼리 짜는 법 168줄이랑 세트
////        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + search + "%"});
//
////        if (cursor.moveToFirst()){
////            do {
////                Log.i("myDB","do while");
////                int selectedId = Integer.parseInt(cursor.getString(0));
////                String selectedTitle = cursor.getString(1);
////                String selectedContent = cursor.getString(2);
////
////                // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
////                Contact memo = new Contact();
////                memo.setId(selectedId);
////                memo.setTitle(selectedTitle);
////                memo.setMemo(selectedContent);
////
////                // 4. 위의 빈 어레이 리스트에 하나씩 추가를 시킨다.
////                memoList.add(memo);
////
////            }while (cursor.moveToNext());
////        }
//            // 제목 검색하는거 였음
//        if (cursorTitle.moveToFirst()){
//            do {
//                Log.i("myDB","do while");
//                int selectedId = Integer.parseInt(cursorTitle.getString(0));
//                String selectedTitle = cursorTitle.getString(1);
//                String selectedContent = cursorTitle.getString(2);
//
//                // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
//                Contact memo = new Contact();
//                memo.setId(selectedId);
//                memo.setTitle(selectedTitle);
//                memo.setMemo(selectedContent);
//
//                // 4. 위의 빈 어레이 리스트에 하나씩 추가를 시킨다.
//                memoList.add(memo);
//
//            }while (cursorTitle.moveToNext());
//        }
//        return memoList;
//    }

}
