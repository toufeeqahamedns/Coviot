package com.example.android.coviot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.coviot.R
import com.example.android.coviot.database.ChatEntity
import com.example.android.coviot.databinding.BotMessageBinding
import com.example.android.coviot.databinding.UserMessageBinding
import com.example.android.coviot.domain.DataItem
import com.example.android.coviot.viewmodels.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_BOT = 0
private const val ITEM_VIEW_TYPE_USER = 1

class ChatAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(ChatDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addMessages(list: List<DataItem.Chats>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Chats(ChatEntity(message = "Please wait for a while")))
                else -> list.map { DataItem.Chats(ChatEntity(id = it.id, sender = it.sender, message = it.message, sentTime = it.sentTime)) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_BOT -> BotChatViewHolder.from(parent)
            ITEM_VIEW_TYPE_USER -> UserChatViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserChatViewHolder -> {
                holder.userMessageBinding.also {
                    it.chat = getItem(position) as DataItem.Chats
                }
            }
            is BotChatViewHolder -> {
                holder.botMessageBinding.also {
                    it.chat = getItem(position) as DataItem.Chats
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.sender) {
            "user" -> ITEM_VIEW_TYPE_USER
            "bot" -> ITEM_VIEW_TYPE_BOT
            else -> throw ClassCastException("Unknown viewType")
        }
    }
}

class BotChatViewHolder(val botMessageBinding: BotMessageBinding) :
    RecyclerView.ViewHolder(botMessageBinding.root) {

    companion object {
        fun from(parent: ViewGroup): BotChatViewHolder {
            val withDataBinding: BotMessageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.bot_message,
                parent,
                false
            )
            return BotChatViewHolder(withDataBinding)
        }
    }
}

class UserChatViewHolder(val userMessageBinding: UserMessageBinding) :
    RecyclerView.ViewHolder(userMessageBinding.root) {


    companion object {
        fun from(parent: ViewGroup): UserChatViewHolder {
            val withDataBinding: UserMessageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_message,
                parent,
                false
            )
            return UserChatViewHolder(withDataBinding)
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

