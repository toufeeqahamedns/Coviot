package com.example.android.coviot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.coviot.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentChatBinding>(
            inflater, R.layout.fragment_chat, container, false
        )

        binding.sendButton.setOnClickListener{
            Toast.makeText(context, "I'm being clicked", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}