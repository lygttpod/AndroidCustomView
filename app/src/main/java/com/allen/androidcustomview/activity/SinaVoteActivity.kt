package com.allen.androidcustomview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.allen.androidcustomview.R
import com.allen.androidcustomview.bean.VoteBean
import com.allen.androidcustomview.bean.VoteOption
import com.allen.androidcustomview.data.getMockData
import com.allen.androidcustomview.widget.vote.VoteLayoutAdapter
import kotlinx.android.synthetic.main.activity_sina_vote.*

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : yagang.li@yintech.cn
 *      date    : 2019/08/06
 *      desc    :
 * </pre>
 */
class SinaVoteActivity : AppCompatActivity(), VoteLayoutAdapter.OnVoteClickListener {

    var voteLayoutAdapter: VoteLayoutAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sina_vote)
        voteLayoutAdapter = VoteLayoutAdapter(vote_ll)
        voteLayoutAdapter?.setData(getMockData())
        voteLayoutAdapter?.onVoteClickListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        voteLayoutAdapter?.onDestroy()
    }

    override fun onVoteCommitBtnClick(mainVote: VoteBean?, optionIds: ArrayList<Int>, position: Int) {
        voteLayoutAdapter?.refreshDataAfterVotedSuccess(position)
    }

    override fun onVoteItemClick(mainVote: VoteBean?, voteOption: VoteOption?, position: Int) {
    }

}