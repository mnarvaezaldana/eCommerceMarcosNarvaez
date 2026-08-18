package com.yucatancorp.ecommercemarcosnarvaez.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yucatancorp.ecommercemarcosnarvaez.presentation.ProductsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val history by viewModel.searchHistory.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChanged,
            label = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = viewModel::search,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }

        if (history.isNotEmpty()) {

            Text(
                text = "Búsquedas recientes",
                style = MaterialTheme.typography.titleMedium
            )

            history
                .take(5)
                .forEach { query ->

                    TextButton(
                        onClick = {
                            viewModel.onQueryChanged(query)
                        }
                    ) {
                        Text(query)
                    }
                }

            TextButton(
                onClick = viewModel::clearSearchHistory
            ) {
                Text("Borrar historial")
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {

            items(
                items = state.products,
                key = { product ->
                    product.offerId
                }
            ) { product ->

                ProductItem(
                    product = product
                )
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    LaunchedEffect(state.currentPage) {
        if (state.currentPage == 1 && state.products.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo
                .visibleItemsInfo
                .lastOrNull()
                ?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == null) {
                    return@collect
                }
                val totalItems = listState.layoutInfo.totalItemsCount
                if (totalItems > 0 && lastVisibleIndex >= totalItems - 3) {
                    viewModel.loadNextPage()
                }
            }
    }
}