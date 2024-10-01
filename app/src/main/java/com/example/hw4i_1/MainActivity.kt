package com.example.hw4i_1

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hw4i_1.ui.theme.Hw4i1Theme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        ProductPanesApp()


        }
    }
}
@Parcelize
data class Product(val name: String, val price: String, val description: String) : Parcelable

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C.")
)

@Composable
fun ProductPanesApp() {
    val configuration = LocalConfiguration.current
    var selectedProduct by rememberSaveable { mutableStateOf<Product?>(null) }


    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

            Row(modifier = Modifier.fillMaxSize()) {
                ProductList(
                    products = products,
                    onProductSelected = { selectedProduct = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ProductDetailPane(
                    product = selectedProduct,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Configuration.ORIENTATION_PORTRAIT -> {

            if (selectedProduct == null) {
                ProductList(
                    products = products,
                    onProductSelected = { selectedProduct = it },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ProductDetailPane(
                    product = selectedProduct,
                    modifier = Modifier.fillMaxSize(),
                    onGoBack = { selectedProduct = null } // 点击返回按钮时清除选中的产品
                )
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductSelected: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 列表标题
        Text(
            text = "Product List",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))


        products.forEach { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductSelected(product) }
                    .padding(8.dp),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ProductDetailPane(
    product: Product?,
    modifier: Modifier = Modifier,
    onGoBack: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (product != null) {

            Text(
                text = "Details for ${product.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Price: ${product.price}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description: ${product.description}", fontSize = 16.sp)


            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onGoBack?.invoke() }) {
                Text("Go Back")
            }
        } else {

            Text(
                text = "No product selected",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPanesAppPreview() {
    ProductPanesApp()
}