package com.a6.bookshift

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.a6.bookshift.ui.theme.BookShiftTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAGGG", "Inicio del programa")

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", " Integracion de Firebase completa")
        analytics.logEvent("Start_app", bundle)


        /*

        // Esto funciono!!
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword("alemg77@yahoo.com.ar", "12345678")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAGGG", "Exito creado el usuario alemg77@yahoo.com.ar")
                } else {
                    Log.d("TAGGG", " Error creando el usuario alemg77@yahoo.com.ar")
                }
            }
            .addOnFailureListener {
                Log.d("TAGGG", "Error creando el usuario alemg77@yahoo.com.ar")
            }

         */

        val instance = FirebaseAuth.getInstance()

        instance.signInWithEmailAndPassword("alemg77@yahoo.com.ar", "12345678")
            .addOnCompleteListener {
                Log.d("TAGGG", "Nos logeamos con mail y contraseña")
            }
            .addOnFailureListener {
                Log.d("TAGGG2", " Error al logearse con mail y contraseña")
            }


        /*
        val buildGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail()
            .build()



        val mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)

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