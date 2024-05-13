package com.example.campusmarketplace

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.campusmarketplace.databinding.FragmentChatItemBinding
import com.example.campusmarketplace.model.Chat
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter internal constructor (
    private val context: Context,
    private val userID: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

        private var chat = emptyList<Chat>()

    inner class ChatViewHolder(private val binding: FragmentChatItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, current: Chat, userID: String) {

            binding.messageTextView.text = current.content

//            Log.d("ChatAdapter", "Sender ID: ${current.senderId}")
//            Log.d("ChatAdapter", "User ID: $userID")

            // Check if the message is sended by current user then modify the design of UI
            if (current.senderId == userID) {
                val layoutParams = binding.messageTextView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                binding.messageTextView.layoutParams = layoutParams
                binding.messageTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.messageTextView.setBackgroundResource(R.drawable.rounded_rectangle_primary)
            }
            binding.timeTextView.text = convertTimestampToDateTime(current.timestamp)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = FragmentChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val current = chat[position]
        holder.bind(context, current, userID)
    }

    override fun getItemCount() = chat.size

    internal fun setChat(chat: List<Chat>) {
        this.chat = chat
        notifyDataSetChanged()
    }

    fun convertTimestampToDateTime(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(date)
    }

}