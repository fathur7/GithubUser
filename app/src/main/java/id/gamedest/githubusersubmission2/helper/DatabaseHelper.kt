package id.gamedest.githubusersubmission2.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_AVATAR
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_COMPANY
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_LOCATION
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_NAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_TYPE
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_USERNAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbuserfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_AVATAR TEXT NOT NULL," +
                " $COLUMN_NAME TEXT NOT NULL," +
                " $COLUMN_USERNAME TEXT NOT NULL," +
                " $COLUMN_TYPE TEXT NOT NULL," +
                " $COLUMN_COMPANY TEXT NOT NULL," +
                " $COLUMN_LOCATION TEXT NOT NULL)"
    }
}