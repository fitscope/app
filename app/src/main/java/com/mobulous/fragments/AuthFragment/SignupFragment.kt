package com.mobulous.fragments.AuthFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mobulous.Repo.AuthRepo
import com.mobulous.ViewModelFactory.AuthVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.main.MainActivity
import com.mobulous.fitscope.databinding.FragmentSignupBinding
import com.mobulous.helper.*
import com.mobulous.listner.SubcriptionFromSideMenuListnr
import com.mobulous.pojo.signUp.SignupPost
import com.mobulous.viewModels.AuthViewModel
import com.mobulous.webservices.ApiConstants
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class SignupFragment : Fragment() {
    lateinit var viewmodel: AuthViewModel
    lateinit var bin: FragmentSignupBinding
    lateinit var mInterface: ApiInterface
    lateinit var subscrptnListnr: SubcriptionFromSideMenuListnr
    val args: SignupFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSignupBinding.inflate(layoutInflater)
        initView()
        lstrn()
        observer()
        return bin.root
    }

    fun observer() {
        viewmodel.signUpData.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { res ->
                            if (res.status == 200) {
                                PrefUtils.with(requireContext()).apply {
                                    save(Enums.isLogin.toString(), "true")
                                    save(
                                        Enums.UserEmail.toString(),
                                        res.data?.user?.email ?: ""
                                    )
                                    save(
                                        Enums.UserName.toString(),
                                        res.data?.user?.name ?: ""
                                    )
                                    save(
                                        Enums.UserToken.toString(),
                                        res.data?.auth?.token ?: ""
                                    )
                                    save(Enums.UserID.toString(), res.data?.user?.id ?: "")
                                    Constants.savedUserID = res.data?.user?.id ?: ""
                                }
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                            } else {
                                requireActivity().showToast(res.message.toString())
                            }
                        }
                    }
                    is NetworkReponse.Error -> {

                    }
                    else -> {}
                }

            } ?: requireActivity().showToast("Something went wrong")

        })
    }

    fun initView() {
        mInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())

        viewmodel =
            ViewModelProvider(this@SignupFragment, AuthVMFactory(AuthRepo(mInterface))).get(
                AuthViewModel::class.java
            )
    }

    fun lstrn() {
        bin.switch1.setOnCheckedChangeListener { _, b ->
            when (b) {
                false -> {
                    bin.edtPassSignUp.inputType =
                        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                }
                true -> {
                    bin.edtPassSignUp.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
            }
        }
        bin.signupBtnSignUp.setOnClickListener {
            if (args.isFromWelcomeFrg) {
                if (bin.edtNameSignUp.text.toString().isNotBlank()) {
                    if (bin.edtEmailSignUp.text.toString().isNotBlank()) {
                        if (bin.edtPassSignUp.text.toString().isNotBlank()) {
                            viewmodel.createUser(
                                SignupPost(
                                    name = bin.edtNameSignUp.text.toString(),
                                    email = bin.edtEmailSignUp.text.toString(),
                                    password = bin.edtPassSignUp.text.toString(),
                                    fcmToken = ApiConstants.AccessToken
                                )
                            )
                        } else {
                            bin.edtPassSignUp.error = "Invalid password"
                        }
                    } else {
                        bin.edtEmailSignUp.error = "Invalid email"
                    }
                } else {
                    bin.edtNameSignUp.error = "Invalid name"
                }
                return@setOnClickListener
            }
            subscrptnListnr.onSubcriptionfromSideMenu()
        }
        bin.ivBackSignUp.setOnClickListener {
            Navigation.findNavController(it).navigate(
                SignupFragmentDirections.actionSignupFragmentToSubscriptionFragment().actionId,
                null,
                NavOptions.Builder().setPopUpTo(R.id.subscriptionFragment, true).build()
            )
        }
    }

//    fun signUpCall() {
//        Uitls.showProgree(true, requireContext())
//        val call = mInterface.signup(
//            SignupPost(
//                name = bin.edtNameSignUp.text.toString(),
//                email = bin.edtEmailSignUp.text.toString(),
//                password = bin.edtPassSignUp.text.toString()
//            )
//        )
//        call.enqueue(object : Callback<SignUpRes> {
//            override fun onResponse(call: Call<SignUpRes>, response: Response<SignUpRes>) {
//                Uitls.showProgree(false, requireContext())
//                if (response.body() != null && response.code()
//                        .toString() == ApiConstants.ResCode200
//                ) {
//                    PrefUtils.with(requireContext()).apply {
//                        save(Enums.isLogin.toString(), "true")
//                        save(Enums.UserEmail.toString(), response.body()!!.user?.email ?: "")
//                        save(Enums.UserName.toString(), response.body()!!.user?.name ?: "")
//                        save(Enums.UserToken.toString(), response.body()!!.auth?.token ?: "")
//                        save(Enums.UserID.toString(), response.body()!!.user?.id ?: "")
//                    }
//                    startActivity(Intent(requireContext(), MainActivity::class.java))
//                } else {
//                    Uitls.showToast(
//                        requireContext(),
//                        if (response.body() != null) response.body()!!.message ?: getString(
//                            R.string.no_able_to_process_api
//                        ) else getString(
//                            R.string.no_able_to_process_api
//                        )
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<SignUpRes>, t: Throwable) {
//                Uitls.showProgree(false, requireContext())
//                Uitls.handlerError(requireContext(), t)
//            }
//        })
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            subscrptnListnr = activity as SubcriptionFromSideMenuListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


}