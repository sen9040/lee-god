package com.yijun.contest.moverecord.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yijun.contest.moverecord.model.MoveRecord;
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
        String CREATE_RECORD_TABLE = "create table " + Utils.TABLE_NAME +
                "(" + Utils.KEY_ID + " integer primary key autoincrement, " +
                Utils.KEY_TITLE + " text, " +
                Utils.KEY_ADDRESS + " text, " +
                Utils.KEY_URL + " text, "+
                Utils.KEY_DATE + " text )";
        // create table contacts
        // (id integer not null autoincrement primary key,
        // name text,
        // phone_number text )

        // 2. 쿼리 실행
        db.execSQL(CREATE_RECORD_TABLE);
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
    public void addMoveRecord(MoveRecord moveRecord){
        Log.i("myDB","addMoveRecord.");
        // 1. 주소를 저장하기 위해서, writable db 를 가져온다.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. db 에 저장하기 위해서는, ContentValues 를 이용한다.
        ContentValues values = new ContentValues();
        values.put(Utils.KEY_TITLE, moveRecord.getTitle());
        values.put(Utils.KEY_ADDRESS, moveRecord.getAddress());
        values.put(Utils.KEY_URL, moveRecord.getUrl());
        values.put(Utils.KEY_DATE, moveRecord.getDate());
        // 3. db 에 실제로 저장한다.
        db.insert(Utils.TABLE_NAME, null, values);
        db.close();
        Log.i("myDB","inserted.");
    }

    // 디비에 저장된 모든 주소록 정보를 불러오는 메소드.
    public ArrayList<MoveRecord> getAllRecord(){                 // ArrayList = 배열보다 진화
        // 1. 비어있는 어레이 리스트를 먼저 한개 만든다.
        ArrayList<MoveRecord> moveRecordArrayList = new ArrayList<>();

        // 2. 데이터베이스에 select (조회) 해서,
        String selectAll = "select * from " + Utils.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        // 3. 여러개의 데이터를 루프 돌면서, Contact 클래스에 정보를 하나씩 담고
        if (cursor.moveToFirst()){
            do {
                int selectedId = Integer.parseInt(cursor.getString(0));
                String selectedTitle = cursor.getString(1);
                String selectedAddress = cursor.getString(2);
                String selectedUrl = cursor.getString(3);
                String selectedDate = cursor.getString(4);
                // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
                MoveRecord moveRecord = new MoveRecord();
                moveRecord.setId(selectedId);
                moveRecord.setTitle(selectedTitle);
                moveRecord.setAddress(selectedAddress);
                moveRecord.setUrl(selectedUrl);
                moveRecord.setDate(selectedDate);

                // 4. 위의 빈 어레이리스트에 하나씩 추가를 시킨다.
                moveRecordArrayList.add(moveRecord);

            }while (cursor.moveToNext());
        }
        return moveRecordArrayList;

    }

    // 데이터 삭제 메서드.
    public void deleteRecord(MoveRecord moveRecord){
        Log.i("myDB","deleteMoveRecord.");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_NAME,      // 테이블명
                Utils.KEY_ID + " = ? ",      // where id = ?
                new String[]{String.valueOf(moveRecord.getId())});     // ? 에 해당하는 값
        db.close();
    }

//    // 주소 1개 가져오는 메소드 : 우리가 만들어줘야 하는 메소드.
//    // select * from contacts where id = 3;
//    public Contact getContact (int id){
//
//        // 1. 데이터베이스 가져온다. 조회니까, readable 한 db 로 가져온다.
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // select id, name, phone_number from contacts where id = 3;
//        // 2. 데이터를 섹렉트(조회) 할때는, Cursor 를 이용해야한다.
//        Cursor cursor = db.query(Util.TABLE_NAME,
//                new String[] {"id", "title", "memo"},       // String 배열로 조회할컬럼들을 가져옴.
//                Util.KEY_ID + " = ?", new String[]{String.valueOf(id)},         // = where 절, new 뒤에 부분 = " = ? " 물음표에 들어갈 부분.
//                null, null, null);
//
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//        int selectedId = Integer.parseInt(cursor.getString(0));
//        String selectedTitle = cursor.getString(1);
//        String selectedMemo = cursor.getString(2);
//
//        // db 에서 읽어온 데이터를, 자바 클래스로 처리한다.
//        Contact contact = new Contact();
//        contact.setId(selectedId);
//        contact.setTitle(selectedTitle);
//        contact.setMemo(selectedMemo);
//
//        return contact;
//    }

//    // 데이터를 업데이트 하는 메서드.
//    public int updateContact(Contact contact){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Util.KEY_TITLE, contact.getTitle());
//        values.put(Util.KEY_MEMO, contact.getMemo());
//
//        // 데이터베이스 테이블 업데이트.
//        // update contacts set title = "홍길동", content = "memo" where id = 3;
//        int ret = db.update(Util.TABLE_NAME,    // 테이블명
//                values,     // 업데이트할 값
//                Util.KEY_ID + " = ? ",      // where
//                new String[]{String.valueOf(contact.getId())});     // ?에 들어갈 값
//        db.close();
//        return ret;
//    }
//
//
//    // 테이블에 저장된 주소록 데이터의 전체 갯수를 리턴하는 메소드.
//    public int getCount(){
//        // select count(*) from contacts;
//        String countQuery = "select * from " + Util.TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        int count = cursor.getCount();
//        db.close();
//        return count;
//    }
//
//    // 검색용 메소드 추가
//    public ArrayList<Contact> getLike(String search){
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
//        // 제목 검색하는거 였음
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
