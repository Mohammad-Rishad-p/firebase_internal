//package ddukk.mvocsad.cusat.all_in_one_firebase
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.auth.PhoneAuthProvider
//
//class jetpack_activity : ComponentActivity() {
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        auth = FirebaseAuth.getInstance()
//        setContent {
//            OtpScreen()
//        }
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//                startActivity(Intent(applicationContext, phoneFinishActivity::class.java))
//            } else {
//                if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                    Toast.makeText(this, "Invalid Credential", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun OtpScreen() {
//    val context = LocalContext.current
//    val storedVerificationId = context.intent.getStringExtra("verificationId")
//    val enterOtpValue = remember { mutableStateOf(TextFieldValue()) }
//
//    Surface(color = MaterialTheme.colors.background) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Enter OTP")
//            Spacer(modifier = Modifier.height(16.dp))
//            androidx.compose.material.TextField(
//                value = enterOtpValue.value,
//                onValueChange = { enterOtpValue.value = it },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("Enter OTP") }
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = {
//                val otp = enterOtpValue.value.text.trim()
//                if (otp.isNotEmpty()) {
//                    val credential: PhoneAuthCredential =
//                        PhoneAuthProvider.getCredential(storedVerificationId.toString(), otp)
//                    (context as? otp_Activity)?.signInWithPhoneAuthCredential(credential)
//                } else {
//                    Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
//                }
//            }) {
//                Text(text = "Submit OTP")
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MaterialTheme {
//        OtpScreen()
//    }
//}