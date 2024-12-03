package ru.webarmour.qrzxing.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.journeyapps.barcodescanner.ScanContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.webarmour.qrzxing.data.ItemDb
import ru.webarmour.qrzxing.data.MainDatabase
import ru.webarmour.qrzxing.presentation.components.MainScreen
import ru.webarmour.qrzxing.presentation.ui.theme.QRZXingTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainDb: MainDatabase

    private val scanLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val productBtQr = mainDb.getDao().getProductForMatch(result.contents)
                if (productBtQr == null) {
                    mainDb.getDao().insertItem(
                        ItemDb(
                            name = result.formatName,
                            numberQR = result.contents,
                            imagePath = result.barcodeImagePath ?: ""
                        )
                    )
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "Already contains",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val productStateList = viewModel.getAllItems()
                .collectAsState(emptyList())
            QRZXingTheme {
                MainScreen(
                    itemsList = productStateList.value,
                    viewModel = viewModel,
                    onScanClick = {
                        viewModel.scanQR(scanLauncher)
                    }
                )
            }
        }
    }

}
