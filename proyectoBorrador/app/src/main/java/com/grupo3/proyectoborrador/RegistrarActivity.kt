package com.grupo3.proyectoborrador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var buttonRegistrar: Button
private lateinit var etEmailR: EditText
private lateinit var etContraseniaR: EditText
private lateinit var etContrasenia2R: EditText

class RegistrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        //inicializar variables
        buttonRegistrar = findViewById(R.id.btnRegistrar)
        auth = Firebase.auth
        etEmailR=findViewById(R.id.etEmailR)
        etContraseniaR=findViewById(R.id.etContraseniaR)
        etContrasenia2R=findViewById(R.id.etContrasenia2R)

        //listener
        buttonRegistrar.setOnClickListener{
            val email = etEmailR.text.toString()
            val clave = etContraseniaR.text.toString()

            if(!ValidarDatosRequeridos())
                return@setOnClickListener

            SignUpNewUser(email,clave)
        }

    }


    fun SignUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(EXTRA_LOGIN, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "New user saved.",
                        Toast.LENGTH_SHORT).show()
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(EXTRA_LOGIN, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
}

private fun ValidarDatosRequeridos(): Boolean {
    val email = etEmailR.text.toString()
    val clave = etContraseniaR.text.toString()
    val clave2 = etContrasenia2R.text.toString()

    if (email.isEmpty()) {
        etEmailR.error = "El email es obligatorio"
        etEmailR.requestFocus()
        return false
    }

    if (clave.isEmpty()) {
        etContraseniaR.error = "La clave es obligatoria"
        etContraseniaR.requestFocus()
        return false
    }

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        etEmailR.error = "El email debe tener un formato valido"
        etEmailR.requestFocus()
        return false
    }


    if (clave.length < 8) {
        etContraseniaR.error = "La clave debe tener al menos 8 caracteres"
        etContraseniaR.requestFocus()
        return false
    }

    if (clave2 != clave) {
        etContrasenia2R.error = "Las claves deben coincidir"
        etContrasenia2R.requestFocus()
        return false
    }

    return true
}