package ru.webarmour.qrzxing.presentation

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.webarmour.qrzxing.data.ItemDb
import ru.webarmour.qrzxing.data.MainDatabase
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    var mainDb: MainDatabase,
) : ViewModel() {

    private val getDao = mainDb.getDao()

    fun deleteItem(itemDb: ItemDb){
        viewModelScope.launch {
            getDao.deleteItem(itemDb)
        }
    }

    fun insertItem(itemDb: ItemDb){
        viewModelScope.launch {
            getDao.insertItem(itemDb)
        }
    }
    fun getAllItems() = getDao.getAllItems()

    fun scanQR(
        launcher: ActivityResultLauncher<ScanOptions>
    ) {
        val scanOptions = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            setBarcodeImageEnabled(false)
            setBeepEnabled(true)
            setPrompt("")
        }
        launcher.launch(scanOptions)
    }

}