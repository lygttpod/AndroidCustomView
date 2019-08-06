package com.allen.androidcustomview.widget.vote

import android.view.View
import android.view.ViewGroup
import com.allen.androidcustomview.bean.VoteBean
import com.allen.androidcustomview.bean.VoteOption
import java.lang.ref.WeakReference

/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/08/03
 *      desc    :
 * </pre>
 */
class VoteLayoutAdapter(private val viewGroup: ViewGroup) {

    private var viewHolders = mutableListOf<VoteViewHolder>()

    var onVoteClickListener: OnVoteClickListener? = null

    fun setData(vote: ArrayList<VoteBean>?) {
        viewGroup.removeAllViews()
        viewHolders.clear()
        if (vote == null || vote.size <= 0) {
            viewGroup.visibility = View.GONE
        } else {
            viewGroup.visibility = View.VISIBLE
            val size = vote.size
            for (i in 0 until size) {
                val viewHolder = onCreateViewHolder(viewGroup, i)
                viewHolder.bind(vote[i])
                viewHolders.add(viewHolder)
                viewGroup.addView(viewHolder.voteContainerView)
            }
        }
    }

    fun refreshDataAfterVotedSuccess(position: Int) {
        viewHolders[position].voteContainerView.refreshDataAfterVoteSuccess()
    }

    fun refreshDataAfterVotedFailed(position: Int) {
        viewHolders[position].voteContainerView.refreshDataAfterVoteFailed()
    }

    fun onDestroy() {
        viewHolders.forEach {
            it.voteContainerView.onDestroy()
        }
    }

    private fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): VoteViewHolder {
        val view = VoteContainerView(viewGroup.context)
        return VoteViewHolder(view, this, position)
    }

    class VoteViewHolder(view: VoteContainerView, adapter: VoteLayoutAdapter, var position: Int) {

        private val adapterRef = WeakReference(adapter)
        private fun ref(): VoteLayoutAdapter? {
            return adapterRef.get()
        }

        var voteContainerView = view
        private var mMainVote: VoteBean? = null

        fun bind(mainVote: VoteBean) {
            mMainVote = mainVote
            voteContainerView.setVoteData(mainVote)
            voteContainerView.onVoteClickListener = object : VoteContainerView.OnVoteClickListener {
                override fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>) {
                    ref()?.onVoteClickListener?.onVoteCommitBtnClick(mainVote, optionIds, position)
                }

                override fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?) {
                    ref()?.onVoteClickListener?.onVoteItemClick(mainVote, voteOption, position)
                }

            }
        }

    }

    interface OnVoteClickListener {
        fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>, position: Int)
        fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?, position: Int)
    }
}