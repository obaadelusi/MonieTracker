package com.oba.monietracker.data.db

import android.util.Log
import com.oba.monietracker.data.models.Category
import com.oba.monietracker.data.models.TransactionRecord
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * The App database manager.
 * @param database The database.
 */
class AppDataManager(private val database: AppDatabase) {
    init {
        GlobalScope.launch {
            val records = database.transactionRecordDao().getAllTransactionRecords()

            if(records.isEmpty()) {
                database.transactionRecordDao().deleteAllTransactions()
                database.categoryDao().deleteAllCategories()

                val catJob = GlobalScope.launch {
                    storeInitCategories()
                }
                catJob.join()

                val tranJob = GlobalScope.launch {
                    storeInitTransactions()
                }
                tranJob.join()
            }
        }
    }

    // init categories data
    private val _categories = listOf(
        Category(name = "Uncategorized",
            "Items have no category", "wWZzXlDpMog"),
        Category(name = "Income - Job",
            "Income from full- part-time jobs", "lCPhGxs7pww"),
        Category(name = "Income - Business",
            "Income from side businesses", "6dW3xyQvcYE"),
        Category(name = "Housing",
            "Rent, utilities and home maintenance", "r3WAWU5Fi5Q"),
        Category(name = "Groceries",
            "Food, fruits, snacks and everything nice.", "ivfp_yxZuYQ"),
        Category(name = "Taxes",
            "Tax expenses and returns", "4hKX5IJJnSs"),
        Category(name = "Entertainment",
            "Fun outings, subscriptions, and games.", "5cAMGyNGHYg"),
    )

    /**
     * Get local categories and save to the database.
     * @param database The database.
     */
    private suspend fun storeInitCategories() {
        // save data in the local db
        val job = GlobalScope.launch {
            saveCategoriesToDatabase(_categories)
        }
        job.join()
        Log.i("Mgr-StoreCategories", "Done -> $_categories")
    }

    /**
     * Get local transaction records and save to the database.
     * @param db The database.
     */
    private suspend fun storeInitTransactions() {
        // init transaction records data
        val _transactionRecords = mutableListOf(
            TransactionRecord("18 Apr 2024", 42.15,
                "expense", "Gas - My Car", _categories[0]),
            TransactionRecord("2 Apr 2024", 2300.00,
                "income", "Profit - Cooking Biz", _categories[2]),
            TransactionRecord("26 Mar 2024", 14.55,
                "expense", "Subscription - YT Premium", _categories[6]),
            TransactionRecord("24 Mar 2024", 1444.60,
                "income", "Tax return - CRA", _categories[5]),
            TransactionRecord("15 Mar 2024", 1130.39,
                "income", "Pay - CargoJet (Part-time)", _categories[1]),
            TransactionRecord("16 Mar 2024", 230.52,
                "expense", "Groceries - Walmart", _categories[4]),
            TransactionRecord("3 Mar 2024", 125.92,
                "expense", "Gift - Mom", _categories[0]),
            TransactionRecord("1 Mar 2024", 650.00,
                "expense", "Rent & Utilities - Princess St", _categories[3]),

        )

        // 1. get cat name from each t in tRecords.
        // 2. get db category from t name
        // 3. change cat in t to category from db

        _transactionRecords.forEach { t ->
            var category = Category()
            val getJob = GlobalScope.launch {
                category = database.categoryDao().getCategoryByName(t.category?.name!!)
            }
            getJob.join()
            t.category = category
        }

        val saveJob = GlobalScope.launch {
            saveTransactionRecordsToDatabase(_transactionRecords)
        }
        saveJob.join()
        Log.i("Mgr-StoreTransactions", "$_transactionRecords")
    }

    // on click actions
    /**
     * Get all categories from a database.
     * @return All categories.
     */
    suspend fun getAllCategories(): List<Category> {
        var catList = listOf<Category>()

        val allCatJob = GlobalScope.launch {
            catList = database.categoryDao().getAllCategories()
        }
        allCatJob.join()

        // Log.i("Mgr-GetAllCat", "AllCat -> $catList")
        return catList
    }

    /**
     * Get all transaction records.
     * @return List of transaction records.
     */
    suspend fun getAllTransactionRecords(): List<TransactionRecord> {
        var tranList = mutableListOf<TransactionRecord>()

        val tranJob = GlobalScope.launch {
            tranList = database.transactionRecordDao().getAllTransactionRecords()
        }
        tranJob.join()

        Log.i("Mgr-GetAllRecords", "AllRecords -> $tranList")
        return tranList
    }

    /**
     * Gets all transactions for specified date.
     * @param date The specified date.
     * @return List of transaction records.
     */
    suspend fun getTransactionsByDate(date: String): List<TransactionRecord> {
        var transactions = mutableListOf<TransactionRecord>()
        val newDate = "%$date%"

        val job = GlobalScope.launch {
            transactions = database.transactionRecordDao().getTransactionsByDate(newDate)
        }
        job.join()

        return transactions
    }

    /**
     * Gets a transaction by specified id.
     * @param id The specified id.
     * @return List of transaction records.
     */
    fun getTransactionById(id: String): TransactionRecord {
        val newId = "$id".toInt()
        val t = database.transactionRecordDao().getTransactionRecordById(newId)

//        Log.i("Mgr-GetRecordById", "One Record -> $t")
        return t
    }

    /**
     * Delete a transaction record from the database.
     */
    fun deleteTransaction(transaction: TransactionRecord) {
        val t = database.transactionRecordDao().deleteTransactionRecord(transaction)

        Log.i("Mgr-GetRecordById", "One Record -> $t")
    }

    /**
     * Saves a new category to the database.
     * @param category The new category.
     */
    suspend fun saveCategory(category: Category) {
        database.categoryDao().insertOneCategory(category)
    }

    /**
     * Saves a new transaction record to the database.
     * @param transaction The new transaction record.
     */
    suspend fun saveTransaction(transaction: TransactionRecord) {
        val dbCat = database.categoryDao()
            .getCategoryByName("${transaction.category?.name}")
        transaction.category = dbCat

        database.transactionRecordDao().insertOneTransactionRecord(transaction)
    }

    /***
     * Updates a transaction.
     * @param transaction The transaction.
     */
    suspend fun updateTransaction(transaction: TransactionRecord) {
        database.transactionRecordDao().updateTransactionRecord(transaction)
    }

    // Dao implementations
    private suspend fun saveCategoriesToDatabase(data: List<Category>
    ) {
        database.categoryDao().insertAllCategories(data)
    }
    private suspend fun saveTransactionRecordsToDatabase(data: List<TransactionRecord>
    ) {
        database.transactionRecordDao().insertAllTransactionRecords(data)
    }
}