package com.fran.horoscapp.ui.luck

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.fran.horoscapp.R
import com.fran.horoscapp.databinding.FragmentHoroscopeBinding
import com.fran.horoscapp.databinding.FragmentLuckBinding
import com.fran.horoscapp.ui.core.listener.OnSwipeTouchListener
import com.fran.horoscapp.ui.provider.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class LuckFragment : Fragment() {


    private var _binding: FragmentLuckBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var  randomCardProvider: RandomCardProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        preparePrediction()
        initListeners()
    }

    private fun preparePrediction() {
        val currentLuck = randomCardProvider.getLucky()
        currentLuck?.let {luck->
            val currentPrediction=getString(luck.text)
            binding.tvLucky.text=getString(luck.text)
            binding.ivLuckyCard.setImageResource(luck.image)
            binding.tvShare.setOnClickListener{
                shareResult(getString(luck.text))
            }
        }
    }

    private fun shareResult(prediction:String) {
        val sendIntent:Intent=Intent().apply {
            action=Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,prediction)
            type="text/plain"
        }

        val shareIntent=Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
    }

    private fun initListeners() {
        binding.ivRulette.setOnClickListener {
            spinRoulette()
        }
        binding.ivRulette.setOnTouchListener(object :OnSwipeTouchListener(requireContext()){
            override fun onSwipeRight() {
                spinRoulette()
            }
            override fun onSwipeLeft() {
                spinRoulette()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root

    }


    private fun spinRoulette() {

        val random = Random
        val degrees = random.nextInt(1440) + 360
        val animator =
            ObjectAnimator.ofFloat(binding.ivRulette, View.ROTATION, 0f, degrees.toFloat())
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()
    }

    private fun slideCard() {
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.reverse.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                growCard()
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
        binding.reverse.startAnimation(slideUpAnimation)

    }

    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.reverse.isVisible = false
                showPremonitionView()

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

        binding.reverse.startAnimation(growAnimation)
    }

    private fun showPremonitionView() {
        val disappearAnimation = AlphaAnimation(1.0f, 0.0f)
        disappearAnimation.duration = 200

        val appearAnimation = AlphaAnimation(0.0f, 1.0f)
        appearAnimation.duration = 1000

        disappearAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.preview.isVisible = false
                binding.prediction.isVisible = true

            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })



        binding.preview.startAnimation(disappearAnimation)
        binding.prediction.startAnimation(appearAnimation)

    }
}