package com.allen.androidcustomview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.allen.androidcustomview.R
import com.allen.androidcustomview.adapter.ItemAnimAdapter
import com.allen.androidcustomview.anim.RotateXItemAnimation
import com.allen.androidcustomview.anim.RotateYItemAnimation
import com.allen.androidcustomview.anim.ScaleItemAnimation
import com.allen.androidcustomview.anim.SlideItemAnimation
import kotlinx.android.synthetic.main.activity_recycler_view_item_anim.*
import kotlin.random.Random

/**
 * <pre>
 *      @author : Allen
 *      e-mail  : lygttpod@163.com
 *      date    : 2019/05/11
 *      desc    :
 * </pre>
 */
class RecyclerViewItemAnimActivity : AppCompatActivity() {

    var adapter: ItemAnimAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_item_anim)
        initListener()
        initView()
        initData()
    }

    private fun initView() {
        adapter = ItemAnimAdapter(arrayListOf())
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = adapter
        recycler_view.itemAnimator = DefaultItemAnimator()
    }

    private fun initListener() {
        normal_btn.setOnClickListener { setReverseLayout(false) }
        reverse_btn.setOnClickListener { setReverseLayout(true) }
        scale_btn.setOnClickListener { setItemAnimation(ScaleItemAnimation()) }
        slide_btn.setOnClickListener { setItemAnimation(SlideItemAnimation()) }
        rotate_x_btn.setOnClickListener { setItemAnimation(RotateXItemAnimation()) }
        rotate_y_btn.setOnClickListener { setItemAnimation(RotateYItemAnimation()) }

        add_btn.setOnClickListener {
            adapter?.addData(0, getItemData())
            (recycler_view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
        }

        remove_btn.setOnClickListener {
            if (adapter?.data?.size ?: 0 > 0) {
                adapter?.remove(0)
                (recycler_view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
            }
        }
    }

    private fun setReverseLayout(reverseLayout: Boolean) {
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout)
        adapter?.data?.clear()
        adapter?.notifyDataSetChanged()
    }

    private fun setItemAnimation(itemAnimation: RecyclerView.ItemAnimator) {
        adapter?.data?.clear()
        adapter?.notifyDataSetChanged()
        recycler_view.itemAnimator = itemAnimation
    }

    private fun initData(): ArrayList<String> {
        val list: ArrayList<String> = arrayListOf()
        list.add("人生如戏，全靠演技")
        list.add("年轻就是资本")
        list.add("我的一颗眼泪掉进了海洋，当我找到它的那一天就是我停止爱你的那一天")
        list.add("你若一直在，我便一直爱")
        list.add("路，跪着也要走完")
        list.add("美丽的彩虹就像一座七彩的桥一样高挂在雨后的天空")
        list.add("留情不留命，留命伤感情")
        list.add("宽容就是在别人和自己意见不一致时也不要勉强")
        list.add("那些曾经以为念念不忘的事情，就在我们念念不忘的过程里，被我们遗忘了")
        list.add("朝花夕拾捡的是枯萎")
        list.add("黄绢幼妇，其土老人")
        list.add("要有最朴素的生活，与最遥远的梦想，即使明日天寒地冻，路远马亡")
        list.add("不是路不平，而是你不行")
        return list
    }

    private fun getItemData(): String {
        return initData()[Random.nextInt(100) / 10 + 1]
    }
}