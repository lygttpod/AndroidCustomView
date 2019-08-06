package com.allen.androidcustomview.data

import com.allen.androidcustomview.bean.VoteBean
import com.allen.androidcustomview.bean.VoteOption
import kotlin.random.Random

/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/08/06
 *      desc    :
 * </pre>
 */

fun getMockData(): ArrayList<VoteBean> {
    val list: ArrayList<VoteBean> = arrayListOf()
    for (i in 0..1)
        list.add(getVoteBeanData(i))
    return list
}

private fun getVoteBeanData(index: Int): VoteBean {
    val voteTitle: String = when (index) {
        0 -> "哪吒票房能否突破30亿(多选)"
        1 -> "你觉得谁最火呢？(单选)"
        else -> ""
    }
    return VoteBean(11, voteTitle, if (index == 0) "multiple" else "single", 2, false, Random.nextInt(10000, 20000), getVoteOptionsDatas(index))
}

private fun getVoteOptionsDatas(index: Int): java.util.ArrayList<VoteOption>? {
    var list: ArrayList<VoteOption> = arrayListOf()
    for (i in 0..3)
        list.add(getVoteOptionData(index, i))
    return list

}

private fun getVoteOptionData(index: Int, i: Int): VoteOption {

    val voteContent: String = when (i) {
        0 -> if (index == 0) "当然可以" else "蔡徐坤"
        1 -> if (index == 0) "估计不能" else "肖战"
        2 -> if (index == 0) "拭目以待" else "李现"
        3 -> if (index == 0) "保持中立" else "邓伦"
        else -> ""
    }
    return VoteOption(i, voteContent, i, Random.nextInt(6666,8888), false)
}