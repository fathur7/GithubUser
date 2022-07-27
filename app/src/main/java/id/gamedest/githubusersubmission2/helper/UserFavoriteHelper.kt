package id.gamedest.githubusersubmission2.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_NAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.COLUMN_USERNAME
import id.gamedest.githubusersubmission2.data.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME

class UserFavoriteHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase = databaseHelper.writableDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC",
            null)
    }

    fun queryByUsername(username: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "${BaseColumns._ID} = ?", arrayOf(id))
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE,"$COLUMN_USERNAME = '$username'", null)
    }

    fun checkUsername(username: String): Boolean {
        val cursor = database.query(DATABASE_TABLE,
            null, "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null)
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var cursorIndex = 0
            while (cursor.moveToNext()) cursorIndex++
            Log.d(TAG, "username found: $cursorIndex")
        }
        cursor.close()
        return check
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserFavoriteHelper? = null
        fun getInstance(context: Context): UserFavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserFavoriteHelper(context)
        }
        private val TAG = UserFavoriteHelper::class.java.simpleName
    }
}