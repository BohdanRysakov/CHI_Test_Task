package com.example.chi_test_task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chi_test_task.databinding.UserItemBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    val userList = ArrayList<User>()
    class UserHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = UserItemBinding.bind(item)
        fun bind(user : User) = with(binding){
            textViewAge.text = user.age.toString()
            textViewName.text = user.name
            isStudentCheckBox.isActivated= user.isStudent
            isStudentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                user.isStudent = isChecked
            }

            isStudentCheckBox.isChecked = user.isStudent

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)

        return UserHolder(view)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUser(user : User){
        userList.add(user)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(users : List<User>){
        userList.addAll(users)
        notifyDataSetChanged()
    }
}