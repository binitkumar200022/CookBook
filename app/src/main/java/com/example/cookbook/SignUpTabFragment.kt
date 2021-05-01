package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpTabFragment : Fragment() {

    private var mAuth: FirebaseAuth? = null
    private var userRef: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: ViewGroup =
            inflater.inflate(R.layout.signup_tab_fragment, container, false) as ViewGroup

        mAuth = FirebaseAuth.getInstance()
        userRef = FirebaseDatabase.getInstance().reference.child("users")

        val email: EditText = root.findViewById(R.id.email)
        val userName: EditText = root.findViewById(R.id.user_name)
        val password: EditText = root.findViewById(R.id.pass)
        val confirmPassword: EditText = root.findViewById(R.id.pass_confirm)
        val signUpButton: Button = root.findViewById(R.id.button)

        signUpButton.setOnClickListener {
            val textEmail = email!!.text.toString()
            val textUserName = userName!!.text.toString()
            val textPassword = password!!.text.toString()
            val textConfirmPassword = confirmPassword!!.text.toString()
            if (textEmail.isEmpty()) {
                email!!.error = "Email ID is required!"
                email!!.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                email!!.error = "Please enter a valid Email ID"
                email!!.requestFocus()
            } else if (textUserName.isEmpty()) {
                userName!!.error = "User Name is required!"
                userName!!.requestFocus()
            } else if (textPassword.isEmpty()) {
                password!!.error = "Password is required!"
                password!!.requestFocus()
            } else if (textPassword.length < 6) {
                password!!.error = "Min password length should be 6 characters"
                password!!.requestFocus()
            } else if (textConfirmPassword.isEmpty()) {
                confirmPassword!!.error = "Please re-enter the password!"
                confirmPassword!!.requestFocus()
            } else if (textConfirmPassword != textPassword) {
                confirmPassword!!.error = "Password does not match, CHECK!"
                confirmPassword!!.requestFocus()
            } else if (textEmail.isNotEmpty() && textUserName.isNotEmpty() && textPassword.isNotEmpty() && textConfirmPassword.isNotEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(textEmail, textPassword)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign Up success and user logged in, update UI with the signed-in user's information
                            Log.d(TAG, "signUpWithEmail:success")
                            val user = mAuth!!.currentUser
                            val userID = user.uid
                            val thisUserRef = userRef!!.child(userID);

                            val userInfoMap: HashMap<String, Any> = HashMap()
                            userInfoMap["Email"] = textEmail
                            userInfoMap["UserName"] = textUserName
                            userInfoMap["UID"] = userID
                            thisUserRef.updateChildren(userInfoMap)

                            //Logging In the user
                            updateUI(user)
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "signUpWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Sign Up failed. Try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        return root
    }

    companion object {
        private const val TAG = "SignUp Status"
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