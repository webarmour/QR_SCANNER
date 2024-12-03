package ru.webarmour.qrzxing.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemsDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<ItemDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemDb: ItemDb)

    @Delete
    suspend fun deleteItem(itemDb: ItemDb)

    @Query("SELECT * FROM items WHERE numberQR= :qr")
    suspend fun getProductForMatch(qr: String): ItemDb?

}