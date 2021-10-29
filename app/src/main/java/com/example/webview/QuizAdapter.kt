package com.example.webview

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private lateinit var list: ArrayList<Questions>
    private lateinit var click: OnItemClick
    private var counterScore = 0

    fun setList(list: ArrayList<Questions>, click: OnItemClick) {
        this.click = click
        this.list = arrayListOf()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false), click,counterScore
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size


    inner class ViewHolder(itemView: View, var click: OnItemClick, counterScore: Int) :
        RecyclerView.ViewHolder(itemView) {

        private var title = itemView.findViewById<TextView>(R.id.quizTv)
        private var score = itemView.findViewById<TextView>(R.id.scorePointTv)
        private var btnFirst = itemView.findViewById<Button>(R.id.firstBtn)
        private var secondBtn = itemView.findViewById<Button>(R.id.secondBtn)
        private var thirdBtn = itemView.findViewById<Button>(R.id.thirdBtn)
        private var fourBtn = itemView.findViewById<Button>(R.id.fourBtn)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun onBind(questions: Questions) {
            clearViews()
            score.text = counterScore.toString()
            title.text = questions.title
            btnFirst.text = questions.answers[0]
            btnFirst.setOnClickListener {
                checkAnswer(it as Button, questions.correct)
                click.onClick(adapterPosition)
            }
            secondBtn.text = questions.answers[1]
            secondBtn.setOnClickListener {
                checkAnswer(it as Button, questions.correct)
                click.onClick(adapterPosition)
            }
            thirdBtn.text = questions.answers[2]
            thirdBtn.setOnClickListener {
                checkAnswer(it as Button, questions.correct)
                click.onClick(adapterPosition)
            }
            fourBtn.text = questions.answers[3]
            fourBtn.setOnClickListener {
                checkAnswer(it as Button, questions.correct)
                click.onClick(adapterPosition)
            }
        }

        private fun clearViews() {
            btnFirst.setBackgroundResource(R.drawable.bg_btn)
            secondBtn.setBackgroundResource(R.drawable.bg_btn)
            thirdBtn.setBackgroundResource(R.drawable.bg_btn)
            fourBtn.setBackgroundResource(R.drawable.bg_btn)
        }

        fun checkAnswer(btn: Button, answer: String) {
            if (btn.text.equals(answer)) {
                btn.setBackgroundResource(R.drawable.bg_correct_btn)
                counterScore += 100
            } else {
                btn.setBackgroundResource(R.drawable.bg_incorrect_btn)
                val timer = object : CountDownTimer(800, 800) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        showCorrectBtn(answer)
                    }
                }
                timer.start()
            }
        }

        fun showCorrectBtn(answer: String) {
            if (btnFirst.text.equals(answer)) {
                btnFirst.setBackgroundResource(R.drawable.bg_correct_btn)

            }
            if (secondBtn.text.equals(answer)) {
                secondBtn.setBackgroundResource(R.drawable.bg_correct_btn)

            }
            if (thirdBtn.text.equals(answer)) {
                thirdBtn.setBackgroundResource(R.drawable.bg_correct_btn)

            }
            if (fourBtn.text.equals(answer)) {
                fourBtn.setBackgroundResource(R.drawable.bg_correct_btn)

            }
        }

    }

    interface OnItemClick {
        fun onClick(adapterPosition: Int)
    }

}