package com.example.firebase23041909

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class PictureDAO {
//    private var databaseReference: DatabaseReference? = null
    var databaseReference: DatabaseReference? = null
    var storage: FirebaseStorage? = null
    init {
        //실시간 데이터베이스 연결
        val db = FirebaseDatabase.getInstance()
        //user 테이블 생성객체
//        databaseReference = db.getReference("user")
        databaseReference = db.getReference("picture")
        storage = Firebase.storage
    }

    //insert into user values(_,_,_,_)
    fun picutreInsert(pictureData: PictureData?): Task<Void> {
        return databaseReference!!.push().setValue(pictureData)
    }

    //select * from user
    fun picutreSelect(): Query? {
        return databaseReference
    }
}