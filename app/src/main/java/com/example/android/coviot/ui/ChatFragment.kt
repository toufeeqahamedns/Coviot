package com.example.android.coviot.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.coviot.R
import com.example.android.coviot.databinding.FragmentChatBinding
import com.example.android.coviot.viewmodels.ChatViewModel
import com.example.android.coviot.viewmodels.ChatViewModelFactory

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    private lateinit var viewModel: ChatViewModel

    private lateinit var chatAdapter: ChatAdapter

    lateinit var messageList: RecyclerView
    lateinit var chatbox: EditText
    lateinit var sendButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentChatBinding>(
            inflater, R.layout.fragment_chat, container, false
        )

        binding.lifecycleOwner = this

        chatbox = binding.chatbox
        sendButton = binding.sendButton
        messageList = binding.messageList

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ChatViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        chatAdapter = ChatAdapter(viewModel)

        messageList.apply {
            layoutManager = LinearLayoutManager(context).also { it.stackFromEnd = true }
            adapter = chatAdapter
        }


        sendButton.setOnClickListener {
            if (chatbox.text.toString().trim().isEmpty()) {
                Toast.makeText(context, "Please enter something!! ", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendMessage(chatbox.text.toString().trim())
                chatbox.text.clear()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chats.observe(viewLifecycleOwner, { chats ->
            chats?.apply {
                if (chats.isEmpty()) {
                    viewModel.welcomeMessage()
                } else {
                    chatAdapter.addMessages(chats)
                }
                messageList.scrollToPosition(chatAdapter.itemCount)
            }
        })
    }
}