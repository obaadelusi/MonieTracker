package com.oba.monietracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oba.monietracker.data.models.Category
import com.oba.monietracker.data.models.TransactionRecord
import com.oba.monietracker.data.utlities.Converters

@TypeConverters(Converters::class)
@Database(entities = [TransactionRecord::class, Category::class], version = 6, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    // need reference to the DAO object
    abstract fun transactionRecordDao(): TransactionRecordDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MonieTrackerDB")
                    .addMigrations(MIGRATION_6_7)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // You do this if you don't want to use Room to generate the database.
        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // your migration logic here
                // ex. you can recreate the table
                database.execSQL("DROP TABLE IF EXISTS tbl_transactionRecords")
                database.execSQL(
                    // TODO: Create tbl_transactionRecords schema
                    // transactionMethod TEXT DEFAULT 'credit',
                    """
                        CREATE TABLE IF NOT EXISTS tbl_transactionRecords (
                            id INTEGER PRIMARY KEY,
                            transactionDate TEXT,
                            amount FLOAT,
                            type TEXT,
                            description TEXT,
                            category TEXT,
                            createdAt INTEGER
                        )
                        """
                )

                database.execSQL("DROP TABLE IF EXISTS tbl_categories")
                database.execSQL(
                    // TODO: Create tbl_categories schema
                    """
                        CREATE TABLE IF NOT EXISTS tbl_categories (
                            id INTEGER PRIMARY KEY,
                            name TEXT,
                            description TEXT,
                            imageId TEXT,
                            createdAt INTEGER
                        )
                        """
                )
            }
        }
    }
}