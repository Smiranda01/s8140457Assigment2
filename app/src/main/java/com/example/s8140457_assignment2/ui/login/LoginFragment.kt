package com.example.s8140457_assignment2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s8140457_assignment2.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _b: FragmentLoginBinding? = null
    private val b get() = _b!!
    private val vm: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentLoginBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        b.btnLogin.setOnClickListener {
            val u = b.etUsername.text.toString()
            val p = b.etPassword.text.toString()
            if (u.isBlank() || p.isBlank()) {
                Toast.makeText(requireContext(), "Enter username and student ID", Toast.LENGTH_SHORT).show()
            } else {
                vm.login(u, p)
            }
        }

        vm.keypass.observe(viewLifecycleOwner) { key ->
            key?.let {
                val action = LoginFragmentDirections.actionLoginToDashboard(it)
                findNavController().navigate(action)
            }
        }
        vm.error.observe(viewLifecycleOwner) { msg ->
            msg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
