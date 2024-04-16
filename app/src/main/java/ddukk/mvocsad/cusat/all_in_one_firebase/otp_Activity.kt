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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class otp_Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp)

        auth = FirebaseAuth.getInstance()

        var storedVerificationId = intent.getStringExtra("verificationId")

        // getting elements using id
        var submitOtp = findViewById<Button>(R.id.submitOtp)
        var enterOtp = findViewById<EditText>(R.id.enterOtp)

        submitOtp.setOnClickListener {
            var otp = enterOtp.text.toString().trim()

            if(otp.isNotEmpty()){

                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }
            else{
                Toast.makeText(this,"enter otp",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
                task->
            if (task.isSuccessful){
                startActivity(Intent(applicationContext,phoneFinishActivity::class.java))
            }
            else{
                if (task.exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this,"Invalid Credential",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}