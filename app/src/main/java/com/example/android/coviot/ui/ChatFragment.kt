package com.example.android.coviot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.coviot.R
import com.example.android.coviot.databinding.FragmentChatBinding
import com.example.android.coviot.viewmodels.ChatViewModel
import com.example.android.coviot.viewmodels.ChatViewModelFactory

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    lateinit var chatbox: EditText
    lateinit var sendButton: ImageButton

//    private val viewModel: ChatViewModel by lazy {
        // Need non-null activity

//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentChatBinding>(
            inflater, R.layout.fragment_chat, container, false
        )

        chatbox = binding.chatbox
        sendButton = binding.sendButton

        val application = requireNotNull(this.activity).application

        // Need viewModelFactory
        val viewModelFactory = ChatViewModelFactory(application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        sendButton.setOnClickListener{
            viewModel.query()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}