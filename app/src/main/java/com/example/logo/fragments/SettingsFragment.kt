package com.example.logo.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.logo.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout


class SettingsFragment : Fragment() {

    var isSettingsFragment: Boolean? = null

    private var EXPAND_AVATAR_SIZE: Float = 5F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var horizontalToolbarAvatarMargin: Float = 0F
    private lateinit var toolbar: Toolbar
    private lateinit var collapsing_toolbar: CollapsingToolbarLayout
    private lateinit var appBarLayout: AppBarLayout
    private var cashCollapseState: Pair<Int, Int>? = null

    private lateinit var mEditText: EditText
    private lateinit var card1: CardView
    private lateinit var card2: CardView
    private lateinit var card3: CardView

    private lateinit var background: FrameLayout
    private lateinit var mImageViewPlant: ImageView
    private lateinit var mImageViewLamp: ImageView
    private lateinit var mImageViewTools: ImageView
    private lateinit var mImageViewBuilds: ImageView
    private lateinit var mImageView1: ImageView
    private lateinit var mImageView2: ImageView
    private lateinit var mImageView3: ImageView
    private lateinit var mImageView4: ImageView
    private lateinit var mImageView5: ImageView
    private lateinit var mImageView6: ImageView

    /**/
    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)



        EXPAND_AVATAR_SIZE = resources.getDimension(R.dimen.default_expanded_image_size)
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.activity_margin)
        /* collapsingAvatarContainer = findViewById(R.id.stuff_container)*/
        collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar)
        appBarLayout = view.findViewById(R.id.app_bar_layout)
        toolbar = view.findViewById(R.id.anim_toolbar)
        mEditText = view.findViewById(R.id.et_search_beauty)
        mImageViewPlant = view.findViewById(R.id.iv_plant)
        mImageViewLamp = view.findViewById(R.id.iv_lamp)
        mImageViewTools = view.findViewById(R.id.iv_tools)
        mImageViewBuilds = view.findViewById(R.id.iv_builds)
        mImageView1 = view.findViewById(R.id.iv_1)
        mImageView2 = view.findViewById(R.id.iv_2)
        mImageView3 = view.findViewById(R.id.iv_3)
        mImageView4 = view.findViewById(R.id.iv_5)
        mImageView5 = view.findViewById(R.id.iv_6)
        mImageView6 = view.findViewById(R.id.iv_7)

        Glide.with(activity!!).load(R.drawable.photo1).into(mImageViewPlant)
        Glide.with(activity!!).load(R.drawable.photo2).into(mImageViewLamp)
        Glide.with(activity!!).load(R.drawable.photo3).into(mImageViewTools)
        Glide.with(activity!!).load(R.drawable.photo4).into(mImageViewBuilds)
        Glide.with(activity!!).load(R.drawable.photo5).into(mImageView1)
        Glide.with(activity!!).load(R.drawable.photo6).into(mImageView2)
        Glide.with(activity!!).load(R.drawable.photo6).into(mImageView3)
        Glide.with(activity!!).load(R.drawable.photo7).into(mImageView4)
        Glide.with(activity!!).load(R.drawable.photo8).into(mImageView5)
        Glide.with(activity!!).load(R.drawable.photo9).into(mImageView6)

        background = view.findViewById(R.id.fl_background)
        card1 = view.findViewById(R.id.btn_search)
        card2 = view.findViewById(R.id.card_search)
        card3 = view.findViewById(R.id.card_strix)


        //(activity as MainActivity?)!!.setSupportActionBar(toolbar)
        toolbar.title = ""

        (toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                if (isCalculated.not()) {
                    avatarAnimateStartPointY =
                        Math.abs((appBarLayout.height - (EXPAND_AVATAR_SIZE + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
                    avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
                    verticalToolbarAvatarMargin = (toolbar.height - COLLAPSE_IMAGE_SIZE) * 2

                    isCalculated = true
                }

                updateViews(Math.abs(i / appBarLayout.totalScrollRange.toFloat()))
            })



        return view
    }

    private fun updateViews(offset: Float) {


        when {
            offset < SWITCH_BOUND -> Pair(TO_EXPANDED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            else -> Pair(TO_COLLAPSED, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
        }.apply {
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED -> {
                            /* set avatar on start position (center of parent frame layout)*/
                            //ivUserAvatar.translationX = 0F
                            /**/
                            background.setBackgroundColor(
                                ContextCompat.getColor(
                                    activity?.applicationContext!!,
                                    R.color.green

                                )
                            )
                            /* hide top titles on toolbar*/


                            card2.visibility = View.VISIBLE
                            card3.visibility = View.VISIBLE

                        }
                        TO_COLLAPSED -> background.apply {
                            alpha = 0F
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    activity?.applicationContext!!,
                                    R.color.white
                                )
                            )
                            card3.visibility = View.INVISIBLE
                            card2.visibility = View.INVISIBLE.apply {

                                card2.animate().setDuration(500).alpha(5f)
                            }

                            animate().setDuration(500).alpha(5F)
                            card1.cardElevation = 0F

                        }
                    }
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }

            /* Collapse avatar img*/
            card1.apply {
                when {
                    offset > avatarAnimateStartPointY -> {
                        val avatarCollapseAnimateOffset =
                            (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                        val avatarSize =
                            EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset


                        this.translationX =
                            (((appBarLayout.width - horizontalToolbarAvatarMargin - avatarSize) / -15) * avatarCollapseAnimateOffset)
                        this.translationY =
                            ((((toolbar.height) * 3) + verticalToolbarAvatarMargin - avatarSize) / 12) * avatarCollapseAnimateOffset


                    }
                    else -> this.layoutParams.also {
                        if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                            this.layoutParams = it
                        }
                        translationX = 0f
                    }
                }
            }
        }
    }


    companion object {
        const val SWITCH_BOUND = 0.8f
        const val TO_EXPANDED = 0
        const val TO_COLLAPSED = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }
}

