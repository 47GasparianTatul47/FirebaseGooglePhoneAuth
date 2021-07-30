package com.example.firstPage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.FirebaseViewModel
import com.example.phoneauthfirebasemvvm.R
import com.example.phoneauthfirebasemvvm.databinding.FragmentFirstPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters

    companion object {
        private const val RC_SIGN_IN = 120

    }

    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentFirstPageBinding
    private lateinit var firebaseViewModel: FirebaseViewModel

    lateinit var googleSignInClient: GoogleSignInClient

// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstPageBinding.inflate(layoutInflater)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)

        binding.signIn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_firstPageFragment_to_registerFragment)
        }

        binding.signInWithGoogle.setOnClickListener {
            binding.signInWithGoogle.isClickable = false
            signIn()

        }



        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        firebaseViewModel.onActivityResult(
            context as Activity, requestCode, data,
            RC_SIGN_IN
        )

        firebaseViewModel.idTokenliveData.observe(viewLifecycleOwner, {
            if (it == "1") {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_firstPageFragment_to_userGooglePage)
            }
        })
    }
}