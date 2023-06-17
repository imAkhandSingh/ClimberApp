package com.example.climberapp.storage

import android.content.Context
import com.example.climberapp.models.User


class SharedPrefManager private constructor(private val mCtx: Context) {

    val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("firstName", null)!!,
                sharedPreferences.getString("lastName", null)!!,
                sharedPreferences.getString("email", null)!!,
                sharedPreferences.getString("password", null)!!,
                sharedPreferences.getLong("devId", -1)
            )
        }


    fun saveUser(user: User) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", user.id)
        editor.putString("firstName", user.firstName)
        editor.putString("lastName", user.lastName)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putLong("devId", user.devId)

        editor.apply()
    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun putString(key: String?, value: String?) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String?, defValue: String?): String? {
        return sharedPreferences.getString(key, defValue)
    }


    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}