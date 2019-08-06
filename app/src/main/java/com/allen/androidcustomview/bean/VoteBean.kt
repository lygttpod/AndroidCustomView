package com.allen.androidcustomview.bean


/**
 * <pre>
 *      @author : Allen
 *      e-mail  : yagang.li@yintech.cn
 *      date    : 2019/08/01
 *      desc    :
 * </pre>
 */

class VoteBean(val id: Int = 0,
               val title: String?,
               val choiceType: String?,
               val maxSelect: Int?,
               var voted: Boolean?,
               val sumVoteCount: Int?,
               val options: ArrayList<VoteOption>?
)

data class VoteOption(var id: Int?,
                      var content: String?,
                      var voteId: Int?,
                      var showCount: Int?,
                      var voted: Boolean?)