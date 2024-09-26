package pe.edu.upc.trackmyroute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import pe.edu.upc.trackmyroute.ui.theme.TrackMyRouteMobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackMyRouteMobileAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PaymentScreen(
                        onPaymentComplete = { result ->
                            when (result) {
                                is PaymentSheetResult.Completed -> {
                                    // Handle successful payment
                                    showToast("Payment completed successfully")
                                }
                                is PaymentSheetResult.Canceled -> {
                                    // Handle canceled payment
                                    showToast("Payment canceled")
                                }
                                is PaymentSheetResult.Failed -> {
                                    // Handle failed payment
                                    showToast("Payment failed: ${result.error.localizedMessage}")
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun showToast(message: String) {
        // Implement this method to show a toast or snackbar with the message
    }
}

@Composable
fun PaymentScreen(
    onPaymentComplete: (PaymentSheetResult) -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Payment Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expiry Date") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = cvc,
                onValueChange = { cvc = it },
                label = { Text("CVC") },
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                // Here you would typically call Stripe's API to process the payment
                // For this example, we'll just simulate a successful payment
                onPaymentComplete(PaymentSheetResult.Completed)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Pay")
        }
    }
}