package com.fuusy.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fuusy.home.R
import com.fuusy.home.bean.QuestionList

class DailyQuestionAdapter : RecyclerView.Adapter<DailyQuestionAdapter.QuestionViewHolder>() {

    lateinit var questionList: List<QuestionList>

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_question, parent, false)
        return QuestionViewHolder(view)
    }


    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}