package com.example.registerPage

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.FirebaseViewModel
import com.example.phoneauthfirebasemvvm.R
import com.example.phoneauthfirebasemvvm.databinding.FragmentRegisterBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var binding: FragmentRegisterBinding

    private var param2: String? = null


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

    ): View {


        binding = FragmentRegisterBinding.inflate(layoutInflater)

        return binding.root

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhone)

        val getNumbers: String = binding.countryCodePicker.fullNumberWithPlus.replace(" ", "")


            binding.btnSendSms.setOnClickListener {

                firebaseViewModel.setUserNumber(binding.editTextPhone.text.toString(), getNumbers)

                firebaseViewModel.phoneVerification(context as Activity)

                firebaseViewModel.liveData.observe(viewLifecycleOwner, {

                    val bundle = bundleOf("verificationID" to it)
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_registerFragment_to_phoneVerifyFragment, bundle)
                })
            }
    }
}
