package com.androidkotlin.armoireapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidkotlin.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class LivingFragment : Fragment() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_living, container, false)
        val textviewname = v.findViewById<TextView>(R.id.name_profile_living)
        val textviewemail= v.findViewById<TextView>(R.id.email_profile_living)
        auth = FirebaseAuth.getInstance()
        return v
    }

}



