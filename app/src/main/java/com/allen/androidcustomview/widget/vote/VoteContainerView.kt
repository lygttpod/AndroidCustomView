package com.allen.androidcustomview.widget.vote

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.allen.androidcustomview.R
import com.allen.androidcustomview.bean.VoteBean
import com.allen.androidcustomview.bean.VoteOption
import kotlinx.android.synthetic.main.widget_vote_layout.view.*
import java.lang.ref.WeakReference

/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/08/01
 *      desc    :
 * </pre>
 */
class VoteContainerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var voteViewHolders: ArrayList<VoteItemViewHolder> = arrayListOf()

    var onVoteClickListener: OnVoteClickListener? = null

    companion object {
        val VOTE_TYPE_MULTIPLE = "multiple"
        val VOTE_TYPE_SINGLE = "single"
    }

    private var optionIds = arrayListOf<Int>()

    private var mData: VoteBean? = null

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.widget_vote_layout, this)

        vote_container_vote_btn.setOnClickListener {
            if (mData == null) return@setOnClickListener
            onVoteClickListener?.onVoteCommitBtnClick(mData, optionIds)
        }
    }

    fun setVoteData(data: VoteBean?) {
        if (data == null || data.options.isNullOrEmpty()) {
            visibility = View.GONE
        } else {
            mData = data
            setVoteTitle(data.title)
            voteViewHolders.clear()
            optionIds.clear()
            setVoteStatus(data)
            setVoteBtnStatus()
            vote_item_ll.removeAllViews()
            data.options?.forEachIndexed { index, voteOption ->
                val viewHolder = onCreateViewHolder()
                viewHolder.bind(index, voteOption, data)
                voteViewHolders.add(viewHolder)
                vote_item_ll.addView(viewHolder.voteView)
            }
        }
    }

    private fun setVoteTitle(title: String?) {
        vote_container_title.text = title ?: ""
    }

    private fun setVoteStatus(vote: VoteBean) {
        val isVoteMulti = vote.choiceType == VOTE_TYPE_MULTIPLE
        val voteResult = vote.sumVoteCount ?: 0
        val voted = vote.voted
        vote_container_vote_btn.visibility = if (voted != true && isVoteMulti) View.VISIBLE else View.GONE
        vote_container_vote_result.visibility = if (voted == true) View.VISIBLE else View.GONE
        setVoteResult("共${voteResult}人参与了投票")
    }

    private fun addOptionIds(id: Int) {
        optionIds.add(id)
    }


    private fun removeOptionIds(id: Int) {
        optionIds.remove(id)
    }

    private fun getOptionIdsSize(): Int {
        return optionIds.size
    }

    private fun setVoteBtnStatus() {
        val clickable = getOptionIdsSize() > 0
        if (clickable) {
            vote_container_vote_btn.setBackgroundResource(R.drawable.shape_bg_clickable)
        } else {
            vote_container_vote_btn.setBackgroundResource(R.drawable.shape_bg_un_clickable)
        }
        vote_container_vote_btn.isClickable = clickable
    }

    fun refreshDataAfterVoteSuccess() {
        vote_container_vote_result.visibility = View.VISIBLE
        vote_container_vote_btn.visibility = View.GONE
        refreshVoteResult()
        startProgressAnim()
    }

    fun refreshDataAfterVoteFailed() {
        voteViewHolders.forEach {
            it.resetDataAfterSingleVoteFailed()
        }
    }

    fun onDestroy() {
        voteViewHolders.forEach {
            it.onVoteDestroy()
        }
    }

    private fun refreshVoteResult() {
        val voteResult = (mData?.sumVoteCount ?: 0)
        setVoteResult("共${(voteResult + 1)}人参与了投票")
    }

    private fun setVoteResult(result: String?) {
        vote_container_vote_result.text = result ?: ""
    }

    private fun startProgressAnim() {
        voteViewHolders.forEach {
            it.setProgress()
        }
    }

    private fun onCreateViewHolder(): VoteItemViewHolder {
        return VoteItemViewHolder(getVoteView(context), this)
    }

    class VoteItemViewHolder(var voteView: VoteView, voteContainerView: VoteContainerView) {

        private val voteContainerViewRef = WeakReference(voteContainerView)

        private fun ref(): VoteContainerView? {
            return voteContainerViewRef.get()
        }

        private var data: VoteOption? = null
        private var mainVote: VoteBean? = null

        var isVoteMulti = true

        fun bind(position: Int, voteOption: VoteOption, mainVote: VoteBean) {
            this.data = voteOption
            this.mainVote = mainVote

            isVoteMulti = mainVote.choiceType == VOTE_TYPE_MULTIPLE

            val voteResultCount = voteOption.showCount ?: 0

            voteView.setVoteIsSelected(voteOption.voted ?: false)
                    .setVoteContent(voteOption.content)
                    .setVoteResultText("${voteResultCount}人").refreshView()

            if (isHaveVoted()) {
                val sum = mainVote?.sumVoteCount ?: 0
                val showCount = data?.showCount ?: 0
                val progress = if (sum == 0) 0f else showCount.toFloat() / sum.toFloat()

                voteView.setProgress(progress)
            }

            voteView.setOnClickListener {
                if (isHaveVoted()) return@setOnClickListener
                if (isVoteMulti) {
                    setMultiChoice(voteView, voteOption)
                    ref()?.onVoteClickListener?.onVoteItemClick(mainVote, data)
                } else {
                    if (ref()?.getOptionIdsSize() ?: 0 > 0) return@setOnClickListener
                    setSingleChoice(voteView, voteOption)
                    ref()?.onVoteClickListener?.onVoteItemClick(mainVote, data)
                }
            }

        }

        private fun isHaveVoted(): Boolean {
            return mainVote?.voted ?: false
        }

        fun setProgress() {
            val sum = mainVote?.sumVoteCount ?: 0
            var showCount = data?.showCount ?: 0
            val realShowCount = if (data?.voted == true) showCount + 1 else showCount
            voteView.setVoteResultText("${realShowCount}人")
            mainVote?.voted = true
            val progress = if (sum == 0) 0f else realShowCount.toFloat() / sum.toFloat()
            voteView.setProgressWithAnim(progress)
        }

        fun resetDataAfterSingleVoteFailed() {
            if (isVoteMulti) return
            data?.voted = false
            ref()?.removeOptionIds(data?.id ?: 0)
            voteView.setVoteIsSelected(data?.voted ?: false).refreshView()
        }

        fun onVoteDestroy() {
            voteView.onDestroy()
        }

        private fun setMultiChoice(voteView: VoteView, voteOption: VoteOption) {
            if (voteOption.voted == true) {
                voteOption.voted = false
                ref()?.removeOptionIds(voteOption.id ?: 0)
            } else {
                val optionsIdSize = ref()?.optionIds?.size ?: 0
                val maxSelect = mainVote?.maxSelect ?: 0
                if (optionsIdSize < maxSelect) {
                    voteOption.voted = true
                    ref()?.addOptionIds(voteOption.id ?: 0)
                } else {
                    Toast.makeText(ref()?.context, "最多可选${maxSelect}个", Toast.LENGTH_SHORT).show()
                }
            }
            ref()?.setVoteBtnStatus()
            voteView.setVoteIsSelected(voteOption.voted ?: false).refreshView()
        }

        private fun setSingleChoice(voteView: VoteView, voteOption: VoteOption) {
            voteOption.voted = true
            ref()?.addOptionIds(voteOption.id ?: 0)
            voteView.setVoteIsSelected(voteOption.voted ?: false).refreshView()
            ref()?.onVoteClickListener?.onVoteCommitBtnClick(mainVote, ref()?.optionIds
                    ?: arrayListOf())
        }

    }

    private fun getVoteView(context: Context): VoteView {
        val voteView = VoteView(context)
        voteView.setVoteTextSize(voteView.sp2px(15))
                .setVoteUncheckedContentTextColor(resources.getColor(R.color.unchecked_content_text_color))
                .setVoteCheckedContentTextColor(resources.getColor(R.color.checked_content_text_color))
                .setVoteUncheckedResultTextColor(resources.getColor(R.color.unchecked_result_text_color))
                .setVoteCheckedResultTextColor(resources.getColor(R.color.checked_result_text_color))
                .setVoteUncheckedProgressColor(resources.getColor(R.color.unchecked_progress_color))
                .setVoteCheckedProgressColor(resources.getColor(R.color.checked_progress_color))
                .setVoteCheckedIcon(resources.getDrawable(R.mipmap.icon_vote_check))
                .setVoteBorderRadius(voteView.dp2px(3f))
                .setVoteBorderColor(resources.getColor(R.color.border_color))
                .setVoteRightIconSize(voteView.dp2px(18f).toInt())
                .setVoteAnimDuration(2000L)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            voteView.clipToOutline = true
            voteView.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, voteView.dp2px(3f))
                }
            }
        }

        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, voteView.dp2px(40f).toInt())
        layoutParams.bottomMargin = voteView.dp2px(12f).toInt()
        voteView.layoutParams = layoutParams
        return voteView
    }


    interface OnVoteClickListener {
        fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>)
        fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?)
    }
}