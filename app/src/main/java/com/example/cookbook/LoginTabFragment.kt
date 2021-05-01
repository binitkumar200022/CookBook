package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginTabFragment : Fragment() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: ViewGroup =
            inflater.inflate(R.layout.login_tab_fragment, container, false) as ViewGroup

        mAuth = FirebaseAuth.getInstance()

        val email: EditText = root.findViewById(R.id.email)
        val password: EditText = root.findViewById(R.id.pass)
        val forgetPassword: TextView = root.findViewById(R.id.forget_pass)
        val loginButton: Button = root.findViewById(R.id.button)

        email.translationY = 800F
        password.translationY = 800F
        forgetPassword.translationY = 800F
        loginButton.translationY = 800F

        email.alpha = 0F
        password.alpha = 0F
        forgetPassword.alpha = 0F
        loginButton.alpha = 0F

        email.animate().translationY(0F).alpha(1F).setDuration(800).setStartDelay(300).start()
        password.animate().translationY(0F).alpha(1F).setDuration(800).setStartDelay(500).start()
        forgetPassword.animate().translationY(0F).alpha(1F).setDuration(800).setStartDelay(500)
            .start()
        loginButton.animate().translationY(0F).alpha(1F).setDuration(800).setStartDelay(700).start()

        loginButton.setOnClickListener {
            val textEmail = email!!.text.toString()
            val textPassword = password!!.text.toString()
            if (textEmail.isEmpty()) {
                email!!.error = "Please enter your Email ID"
                email!!.requestFocus()
            } else if (textPassword.isEmpty()) {
                password!!.error = "Please enter your password"
                password!!.requestFocus()
            } else if (textEmail.isNotEmpty() && textPassword.isNotEmpty()) {
                mAuth!!.signInWithEmailAndPassword(textEmail, textPassword)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth!!.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed. Try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        return root
    }

    companion object {
        private const val TAG = "Login Status"
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

}