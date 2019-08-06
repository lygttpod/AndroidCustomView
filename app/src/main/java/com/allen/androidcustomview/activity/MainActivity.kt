package com.allen.androidcustomview.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.allen.androidcustomview.R
import com.allen.androidcustomview.adapter.MainAdapter
import com.allen.androidcustomview.bean.TypeBean
import com.allen.androidcustomview.helper.DragViewHelper
import com.allen.androidcustomview.tagview.TagActivity
import com.allen.androidcustomview.widget.SuperDividerItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), BaseQuickAdapter.OnItemClickListener {

    private var adapter: MainAdapter? = null

    private val typeBeans = ArrayList<TypeBean>()

    private val data: List<TypeBean>
        get() {
            typeBeans.add(TypeBean("气泡漂浮动画", 0))
            typeBeans.add(TypeBean("波浪动画--贝塞尔曲线实现", 1))
            typeBeans.add(TypeBean("波浪动画--正余弦函数实现", 2))
            typeBeans.add(TypeBean("水波（雷达）扩散效果", 3))
            typeBeans.add(TypeBean("RecyclerView实现另类的Tag标签", 4))
            typeBeans.add(TypeBean("按钮自定义动画", 5))
            typeBeans.add(TypeBean("自定义支付密码输入框", 6))
            typeBeans.add(TypeBean("自定义进度条", 7))
            typeBeans.add(TypeBean("使用的带动画的view", 8))
            typeBeans.add(TypeBean("粘性小球", 9))
            typeBeans.add(TypeBean("banner", 10))
            typeBeans.add(TypeBean("吸顶效果--一行代码实现", 11))
            typeBeans.add(TypeBean("揭露动画", 12))
            typeBeans.add(TypeBean("支付宝首页效果", 13))
            typeBeans.add(TypeBean("RecyclerView的item动画", 14))
            typeBeans.add(TypeBean("路径path动画", 15))
            typeBeans.add(TypeBean("仿新浪投票控件", 16))
            return typeBeans
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(data)
        adapter!!.onItemClickListener = this
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(SuperDividerItemDecoration.Builder(this)
                .build())
        recycler_view.adapter = adapter

        DragViewHelper.addDragView(this,
                root_view,
                "网络图片地址",
                defaultImgResId = R.mipmap.ic_camera_3x,
                onClick = {
                    Toast.makeText(this, "点击事件", Toast.LENGTH_SHORT).show()
                })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (typeBeans[position].type) {
            0 -> startActivity(Intent(this@MainActivity, BubbleViewActivity::class.java))
            1 -> startActivity(Intent(this@MainActivity, WaveByBezierActivity::class.java))
            2 -> startActivity(Intent(this@MainActivity, WaveBySinCosActivity::class.java))
            3 -> startActivity(Intent(this@MainActivity, RadarActivity::class.java))
            4 -> startActivity(Intent(this@MainActivity, TagActivity::class.java))
            5 -> startActivity(Intent(this@MainActivity, AnimationBtnActivity::class.java))
            6 -> startActivity(Intent(this@MainActivity, PayPsdViewActivity::class.java))
            7 -> startActivity(Intent(this@MainActivity, ProgressBarActivity::class.java))
            8 -> startActivity(Intent(this@MainActivity, AnimationViewActivity::class.java))
            9 -> startActivity(Intent(this@MainActivity, DragBallActivity::class.java))
            10 -> startActivity(Intent(this@MainActivity, BannerActivity::class.java))
            11 -> startActivity(Intent(this@MainActivity, HoverItemActivity::class.java))
            12 -> startActivity(Intent(this@MainActivity, RevealAnimationActivity::class.java))
            13 -> startActivity(Intent(this@MainActivity, AliPayHomeActivity::class.java))
            14 -> startActivity(Intent(this@MainActivity, RecyclerViewItemAnimActivity::class.java))
            15 -> startActivity(Intent(this@MainActivity, PathActivity::class.java))
            16 -> startActivity(Intent(this@MainActivity, SinaVoteActivity::class.java))
        }
    }
}
