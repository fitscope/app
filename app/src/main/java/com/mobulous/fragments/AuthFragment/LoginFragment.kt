package com.mobulous.fragments.AuthFragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mobulous.Repo.AuthRepo
import com.mobulous.ViewModelFactory.AuthVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.main.MainActivity
import com.mobulous.fitscope.databinding.FragmentLoginBinding
import com.mobulous.helper.*
import com.mobulous.listner.SubcriptionFromSideMenuListnr
import com.mobulous.pojo.login.loginPost
import com.mobulous.viewModels.AuthViewModel
import com.mobulous.webservices.ApiConstants
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.*


class LoginFragment : Fragment() {
    lateinit var viewModel: AuthViewModel
    lateinit var bin: FragmentLoginBinding
    lateinit var mInterface: ApiInterface
    val args: LoginFragmentArgs by navArgs()
    lateinit var subrptnSideMenuListnr: SubcriptionFromSideMenuListnr
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentLoginBinding.inflate(layoutInflater)
        initView()
        viewModel =
            ViewModelProvider(this@LoginFragment, AuthVMFactory(AuthRepo(mInterface))).get(
                AuthViewModel::class.java
            )

        observer()
        lstrn()

//        lifecycleScope.launch {
//            val job = CoroutineScope(Dispatchers.Default).launch {
//                var random = Random.nextInt(10_000)
//                while (random != 5000 && isActive) {
//                    random = Random.nextInt(10_000)
//                    println("------->$random")
//                }
//
//
//            }
//            println("-before---")
//            delay(1000)
//            job.cancel()
//        }


        return bin.root
    }


//    suspend fun callMe(): List<String> {
//
//        var lst = (1..10).toList()
//        var newLst = ArrayList<Deferred<String>>()
//        coroutineScope {
//            lst.forEach {
//                val name = async {
//                    /*launches coroutine independently*/
//                    getNAme()
//                }
//                newLst.add(name)
//            }
//        }
//
//
//        return newLst.awaitAll()
//
//    }
//
//    suspend fun getNAme(): String {
//        delay(1000)
//        return "HEllow"
//    }

    fun initView() {
        mInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())
    }

    fun observer() {
        lifecycleScope.launch {
            viewModel.loginData.observe(viewLifecycleOwner) {
                Uitls.showProgree(false, requireContext())
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let {
                            if (it.status == 200) {
                                PrefUtils.with(requireContext()).apply {
                                    save(Enums.isLogin.toString(), "true")
                                    save(Enums.UserEmail.toString(), it.data?.user?.email ?: "")
                                    save(Enums.UserName.toString(), it.data?.user?.name ?: "")
                                    save(Enums.UserToken.toString(), it.data?.auth?.token ?: "")
                                    save(Enums.UserID.toString(), it.data?.user?.id ?: "")
                                    Constants.savedUserID = it.data?.user?.id ?: ""
                                }
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                this@LoginFragment.requireActivity()
                                    .showToast("Invalid credentials")
                            }
                        } ?: this@LoginFragment.requireActivity().showToast("Something went wrong.")


                    }
                    is NetworkReponse.Error -> {
                        this@LoginFragment.requireActivity().showToast(it.errorMessage)
                        Uitls.showProgree(false, requireContext())
                    }
                }
            }
        }
    }

    fun lstrn() {
        bin.switch1Login.setOnCheckedChangeListener { _, b ->
            when (b) {
                false -> {
                    bin.edtPassLogin.inputType =
                        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                }
                true -> {
                    bin.edtPassLogin.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
            }
        }
        bin.tvRestpwdLogin.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Constants.RESET_PASSWORD_URL))
            startActivity(intent)
        }
        bin.signupBtnLogin.setOnClickListener {
            if (bin.edtEmailLogin.text.toString().isNotBlank()) {
                if (bin.edtPassLogin.text.toString().isNotBlank()) {
                    requireActivity().hideKeyboard()
                    Uitls.showProgree(true, requireContext())
                    viewModel.login(
                        loginPost(
                            email = bin.edtEmailLogin.text.toString().trim(),
                            password = bin.edtPassLogin.text.toString(),
                            fcmToken = ApiConstants.AccessToken
                        )
                    )
                } else {
                    bin.edtPassLogin.error = "Please enter valid password"
                }
            } else {
                bin.edtEmailLogin.error = "Please enter valid email"
            }

        }
        bin.ivBackLogin.setOnClickListener {
            if (args.isFromWelcomeFrg) {
                Navigation.findNavController(it).navigate(
                    LoginFragmentDirections.actionToWelcomeFragment().actionId,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.welcomeFragment, true)
                        .build()
                )
            } else {
                subrptnSideMenuListnr.onSubcriptionfromSideMenu()
            }


        }

    }

//    fun logincall() {
//        Uitls.showProgree(true, requireContext())
//        val call = mInterface.login(
//            loginPost(
//                email = bin.edtEmailLogin.text.toString(),
//                password = bin.edtPassLogin.text.toString()
//            )
//        )
//
//        call.enqueue(object : Callback<LoginRes> {
//            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
//                Uitls.showProgree(false, requireContext())
//                if (response.body() != null && response.code()
//                        .toString() == ApiConstants.ResCode201
//                ) {

//                } else {
//                    Uitls.onUnSuccessResponse(response.code(), requireContext())
//                }
//            }
//
//            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
//                Uitls.showProgree(false, requireContext())
//                Uitls.handlerError(requireContext(), t)
//            }
//        })
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            subrptnSideMenuListnr = activity as SubcriptionFromSideMenuListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


}