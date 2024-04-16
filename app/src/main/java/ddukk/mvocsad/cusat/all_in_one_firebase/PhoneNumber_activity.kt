package ddukk.mvocsad.cusat.all_in_one_firebase

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumber_activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")


    lateinit var auth : FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_phone_number)




        auth = FirebaseAuth.getInstance()

        var generate_otp = findViewById<Button>(R.id.generate_otp)

        generate_otp.setOnClickListener {
            loginph()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                startActivity(Intent(applicationContext, phoneFinishActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.e("TAG", "verification failed:${e.message}")
                Toast.makeText(
                    applicationContext,
                    "verification failed:${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")

                storedVerificationId = verificationId
                resendToken = token
                var intent = Intent(applicationContext, otp_Activity::class.java)
                intent.putExtra("verificationId", verificationId)
                startActivity(intent)
            }
        }


    }

    private fun loginph() {
        var enter_mobile_number = findViewById<EditText>(R.id.enter_mobile_number)
        var number = enter_mobile_number.text.toString().trim()

        if (number.isNotEmpty()) {
            number = "+91" + number
            sendVerification(number)
        }
    }

    private fun sendVerification(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}