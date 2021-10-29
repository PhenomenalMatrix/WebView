package com.example.webview

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webview.data.QstData
import com.example.webview.databinding.FragmentQuizBinding
import androidx.recyclerview.widget.LinearSnapHelper

import androidx.recyclerview.widget.SnapHelper
import kotlin.concurrent.timer


class QuizFragment : Fragment(), QuizAdapter.OnItemClick {

    private var binding: FragmentQuizBinding? = null
    private lateinit var adapter : QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerQuiz?.stopScroll()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding?.recyclerQuiz)
        binding?.menuCont?.get(0)?.setOnClickListener {
            initRecycler()
            binding?.menuCont?.visibility = View.GONE
            binding?.quizCont?.visibility = View.VISIBLE
        }
        binding?.menuCont?.get(1)?.setOnClickListener {
            requireActivity().finish()
        }
        binding?.quizCont?.get(1)?.setOnClickListener {
            initRecycler()
            binding?.quizCont?.visibility = View.GONE
            binding?.menuCont?.visibility = View.VISIBLE
        }
    }

    fun initRecycler(){
        adapter = QuizAdapter()
        adapter.setList(QstData().getList(), this)
        binding?.recyclerQuiz?.adapter = adapter
        binding?.recyclerQuiz?.layoutManager =  object : LinearLayoutManager(context, HORIZONTAL,false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(adapterPosition: Int) {
        val timer = object: CountDownTimer(1500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                binding?.recyclerQuiz?.scrollToPosition(adapterPosition+1)
            }
        }
        timer.start()
    }

}