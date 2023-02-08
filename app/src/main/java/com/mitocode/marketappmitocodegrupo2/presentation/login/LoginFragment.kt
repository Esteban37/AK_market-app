package com.mitocode.marketappmitocodegrupo2.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.core.BaseFragment
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentLoginBinding
import com.mitocode.marketappmitocodegrupo2.presentation.MenuHostActivity
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.mitocode.marketappmitocodegrupo2.worker.UploadWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    //fragment_login = FragmentLogin + Binding

    //private var binding : FragmentLoginBinding? = null
    private lateinit var binding : FragmentLoginBinding

    //Suscribir el viewmodel
    val viewModel : LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        init()
        events()
        observers()

    }

    private fun init() {

        //val uploadWorkRequest: WorkRequest =
        //    OneTimeWorkRequestBuilder<UploadWorker>()
        //        .build()

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<UploadWorker>(
                17,TimeUnit.MINUTES).build()

        WorkManager.getInstance(requireContext().applicationContext).enqueue(uploadWorkRequest)

    }

    private fun observers() {
        /*viewModel._isLoading.observe(viewLifecycleOwner){ isLoading ->
            if(isLoading) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        viewModel._error.observe(viewLifecycleOwner){ error ->
            requireContext().toast(error)
        }

        viewModel._user.observe(viewLifecycleOwner){ userRemote ->
            requireContext().toast("Bienvenido ${userRemote.names}")
        }*/

        viewModel.state.observe(viewLifecycleOwner){ state ->
            updateUI(state)
        }

    }

    private fun updateUI(state: LoginState?) {

        state?.isLoading?.let{ condition ->
            if(condition) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        state?.error?.let { messageError ->
            requireContext().toast(messageError)
        }

        state?.user?.let { user ->
            requireContext().toast("Bienvenido ${user.names}")
            val intent = Intent(requireContext(),MenuHostActivity::class.java)
            startActivity(intent)
        }

    }

    //scope functions with
    private fun events() = with(binding) {
        btnSignIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            viewModel.signIn(email,password)
        }

        tvCreateAccount.setOnClickListener {
            navigateToAction(R.id.action_loginFragment_to_registerAccountFragment)
            //Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerAccountFragment)
        }





    }


}


//RETROFIT
/*val request = Api.build().signIn(LoginRequest(email,password))
request.enqueue(object : Callback<WrappedResponse<UserRemote>> {
    override fun onResponse(call: Call<WrappedResponse<UserRemote>>, response: Response<WrappedResponse<UserRemote>>) {

        if(response.isSuccessful){

            val loginResponse = response.body()
            loginResponse?.data?.let { userRemote ->
                println("${loginResponse.message} ${userRemote.names}")

            }


        }else{
            requireContext().toast(response.message())
        }

    }

    override fun onFailure(call: Call<WrappedResponse<UserRemote>>, t: Throwable) {
        requireContext().toast(t.message.toString())
    }

})*/

//RETROFIT - COROUTINES
//SCOPE
/*GlobalScope.launch(Dispatchers.Main) {

    //Muestres progress
    progressBar.visibility = View.VISIBLE

    val response = withContext(Dispatchers.IO){
        Api.build().signIn(LoginRequest(email,password))
    }
    if(response.isSuccessful){
        val loginResponse = response.body()
        loginResponse?.data?.let { userRemote ->
            println("${loginResponse.message} ${userRemote.names}")
        }
    }else{
        requireContext().toast(response.message())
    }

    progressBar.visibility = View.GONE

}*/

/*
   //GUARDAR
   val preferences = requireContext().getSharedPreferences("PREFERENCES_1",0).edit()
        preferences.putString("SALUDO","HOLA")
        preferences.putString("SALUDO2","HOLA2")
        preferences.putString("SALUDO3","HOLA3")
        preferences.apply()

   //RECUPERAR
        val preferences2 = requireContext().getSharedPreferences("PREFERENCES_1",0)
        val valor = preferences2.getString("SALUDO2","")


 */
