package com.grupo3.proyectoborrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grupo3.proyectoborrador.adicionales.EncryptedSharedPreferencesManager

lateinit var manejadorPreferencias:EncryptedSharedPreferencesManager
lateinit var etEmail: EditText
lateinit var etContrasenia: EditText
lateinit var buttonLogin: Button
lateinit var textViewRegistrarse: TextView
lateinit var checkBoxRecordarme: CheckBox
private lateinit var auth: FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Inicialización de variables
        manejadorPreferencias= EncryptedSharedPreferencesManager(this)
        etEmail = findViewById(R.id.etEmail)
        etContrasenia = findViewById(R.id.etContrasenia)
        buttonLogin = findViewById(R.id.btnLogin)
        textViewRegistrarse = findViewById(R.id.textViewRegistrarse)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme)

        //Inicializacion de las preferencias

        leerDatosDePreferencias()

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Eventos clic


        buttonLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val clave = etContrasenia.text.toString()

            //Validaciones de datos requeridos y formatos
            if(!ValidarDatosRequeridos())
                return@setOnClickListener

            //Guardar datos en preferencias.
            guardarDatosEnPreferencias()

            //Autenticar Firebase
            AutenticarUsuario(email, clave)
        }

        textViewRegistrarse.setOnClickListener{
            val intencion = Intent(this, RegistrarActivity::class.java)
            startActivity(intencion)
        }

    }

    fun AutenticarUsuario(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(EXTRA_LOGIN, "signInWithEmail:success")
                    //Si pasa validación de datos requeridos, ir a pantalla principal
                    val intencion = Intent(this, MenuActivity::class.java)
                    intencion.putExtra(EXTRA_LOGIN, auth.currentUser!!.email)
                    startActivity(intencion)
                    //finish()
                } else {
                    Log.w(EXTRA_LOGIN, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }





    private fun ValidarDatosRequeridos(): Boolean {
        val email = etEmail.text.toString()
        val clave = etContrasenia.text.toString()

        if (email.isEmpty()) {
            etEmail.setError("El email es obligatorio")
            etEmail.requestFocus()
            return false
        }

        if (clave.isEmpty()) {
            etContrasenia.setError("La clave es obligatoria")
            etContrasenia.requestFocus()
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("El email no tiene un formato válido")
            etEmail.requestFocus()
            return false
        }


        if (clave.length < 8) {
            etContrasenia.setError("La clave debería tener al menos 8 caracteres")
            etContrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun leerDatosDePreferencias() {
        val listadoLeido = manejadorPreferencias.ReadInformation()
        if (listadoLeido.first != null) {
            checkBoxRecordarme.isChecked = true
        }
        etEmail.setText(listadoLeido.first)
        etContrasenia.setText(listadoLeido.second)

    }

    private fun guardarDatosEnPreferencias() {
        val email = etEmail.text.toString()
        val clave = etContrasenia.text.toString()

        val listadoAGrabar: Pair<String, String> =
            if (checkBoxRecordarme.isChecked) {
                email to clave
            } else {
                "" to ""
            }
        manejadorPreferencias.SaveInformation(listadoAGrabar)
    }


}