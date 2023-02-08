package com.mitocode.marketappmitocodegrupo2.presentation.account

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.data.model.CreateAccountRequest
import com.mitocode.marketappmitocodegrupo2.databinding.DialogTermsBinding
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentRegisterAccountBinding
import com.mitocode.marketappmitocodegrupo2.domain.model.Gender
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterAccountFragment : Fragment() {

    private lateinit var binding : FragmentRegisterAccountBinding

    //Suscribir el viewModel
    private val viewModel : AccountViewModel by viewModels()

    private var genders : List<Gender> = listOf()
    private var codeGender : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterAccountBinding.bind(view)

        events()
        observers()


    }

    private fun observers() {

        viewModel.state.observe(viewLifecycleOwner){
            updateUI(it)
        }

    }

    private fun updateUI(state: AccountState?) {

        state?.isLoading?.let{ condition ->
            if(condition) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        state?.error?.let { messageError ->
            requireContext().toast(messageError)
        }

        state?.genders?.let { gendersList ->
            genders = gendersList
            populateGenders(genders)
        }

        state?.user?.let { user ->
            requireContext().toast("Bienvenido ${user.names}")
            //TODO Navegar a la pantalla de categorias
        }



    }

    private fun populateGenders(genders: List<Gender>) = with(binding) {

        //android.R.layout.simple_spinner_item : Default
        spGender.setAdapter(ArrayAdapter(requireContext(),R.layout.item_spinner_gender,genders))

    }

    private fun events() = with(binding) {

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        btnCreateAccount.setOnClickListener {

            val names = edtNames.text.toString()
            val lastName = edtLastName.text.toString()
            val email = edtEmail.text.toString()
            val phone = edtPhone.text.toString()
            val numberDoc = edtNumberDoc.text.toString()
            val password = edtPassword.text.toString()

            if(names.isEmpty()){
                tilNames.error = "Debe ingrear sus nombres"
                return@setOnClickListener
            }

            if(lastName.isEmpty()){
                tilNames.error = "Debe ingrear sus apellidos"
                return@setOnClickListener
            }


            viewModel.createAccount(CreateAccountRequest(names,lastName,email,password,phone,codeGender,numberDoc))

        }

        spGender.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, i ->
            codeGender = genders[position].code
        }

        chkTerms.setOnClickListener {
            showDialog().show()
        }


    }

    private fun showDialog() : AlertDialog{

        val binding = DialogTermsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        builder.setView(binding.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        binding.btnOk.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

}