package com.example.firebase23041909

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class UserDAO {
    private var databaseReference: DatabaseReference? = null
    init {
        //실시간 데이터베이스 연결
        val db = FirebaseDatabase.getInstance()
        //user 테이블 생성객체
        databaseReference = db.getReference("user")
    }

    //insert into user values(_,_,_,_)
    fun fbInsert(user: User?): Task<Void>{
        return databaseReference!!.push().setValue(user)
    }

    //select * from user
    fun userSelect(): Query? {
        return databaseReference
    }

    //update set user set userkey = "~~~",
    fun userUpdate(userKey: String, hashMap: HashMap<String, Any>): Task<Void>{
        return databaseReference!!.child(userKey).updateChildren(hashMap)
    }

    //delete from user where userkey = ?
    fun userDelete(userKey: String): Task<Void>{
        return databaseReference!!.child(userKey).removeValue()
    }
}