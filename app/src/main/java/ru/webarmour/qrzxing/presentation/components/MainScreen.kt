package ru.webarmour.qrzxing.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.webarmour.qrzxing.data.ItemDb
import ru.webarmour.qrzxing.presentation.MainViewModel


@Composable
fun MainScreen(
    itemsList: List<ItemDb>,
    viewModel: MainViewModel,
    onScanClick: () -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onScanClick()
                }
            ) {
                Text(text = "QR")
            }

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            ItemsScreen(
                items = itemsList,
                viewModel = viewModel
            )

        }

    }
}

@Composable
fun ItemsScreen(
    items: List<ItemDb>,
    viewModel: MainViewModel
) {
    val itemsList = remember { mutableStateListOf<ItemDb>() }

    LaunchedEffect(items) {
        itemsList.clear()
        itemsList.addAll(items)
    }


    Box {
        LazyColumn(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()

        ) {
            items(itemsList, key = { it.id }) { product ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteItem(product)
                        }
                        true
                    },

                )
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {

                    },
                    enableDismissFromStartToEnd = false
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ItemCard(product)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }
    }
}

@Composable
fun ItemCard(
    product: ItemDb,

    ) {
    val context = LocalContext.current


    Card(
        shape = CardDefaults.shape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .clickable {
                if (product.numberQR.contains("https://")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.numberQR))
                    context.startActivity(intent)
                }

            }

    ) {
        Text(
            text = product.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Text(
            text = product.numberQR,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        AsyncImage(
            modifier = Modifier.size(20.dp),
            model = product.numberQR, contentDescription = null
        )
    }


}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onScanClick: () -> Unit,
) {
    onScanClick()
}
