package com.a6.bookshift

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.a6.bookshift.ui.theme.BookShiftTheme
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : ComponentActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAGGG", "Inicio del programa")

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", " Integracion de Firebase completa")
        analytics.logEvent("Start_app", bundle)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build())
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(TAG, e.localizedMessage)
            }


        /*
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

         */

        /*
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
         */

        /*
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
                //do stuff here
            }
        }

        startForResult.launch(googleSignInClient.signInIntent)
         */

        setContent {
            BookShiftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d(TAG, "Got ID token.")
                        }
                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d(TAG, "Got password.")
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d(TAG, "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    // ...
                }
            }
        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            Log.d("TAGGG", "onActivity Resutl")

            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sing in success, now auth with Firebase
                Log.d("TAGGG", "Login in google... ")
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d("TAGGG", "Error logeandose en Google ")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->

                Log.d("TAGGG", "Nos logeamosen Firebase!!!")

                val firebaseUser = firebaseAuth.currentUser

                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d("TAGGG", "Nuevo usuario")
                } else {
                    Log.d("TAGGG", "Usuario existente")
                }
            }
            .addOnFailureListener {
                Log.d("TAGGG", "Error login in firebase ***")
            }

    }

     */

    companion object {
        const val RC_SIGN_IN = 999
        const val REQ_ONE_TAP = 998
        const val TAG = "TAGGG"
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookShiftTheme {
        Column {
            Greeting(name = "Hola mundo")
            Greeting("Android")
        }
    }
}