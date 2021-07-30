package com.example.registerGoogle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.phoneauthfirebasemvvm.R
import com.example.phoneauthfirebasemvvm.databinding.FragmentUserGooglePageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserGooglePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserGooglePage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: FragmentUserGooglePageBinding
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserGooglePageBinding.inflate(layoutInflater)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)


        binding.idUser.text = "Id User --> ${currentUser?.uid}"
        binding.emailUser.text = "Email User --> ${currentUser?.email}"
        binding.nameUser.text = currentUser?.displayName

        Picasso.with(context)
            .load(currentUser?.photoUrl)
            .error(R.drawable.fire_icon)
            .into(binding.profileImage);



        binding.signOut.setOnClickListener {

            googleSignInClient.signOut().addOnCompleteListener{
                Toast.makeText(context, "Sign Out", Toast.LENGTH_SHORT).show()
            }

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_userGooglePage_to_firstPageFragment)


        }


        return binding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserGooglePage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserGooglePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}