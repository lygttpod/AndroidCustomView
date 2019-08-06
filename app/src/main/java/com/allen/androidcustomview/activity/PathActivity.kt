package com.allen.androidcustomview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.allen.androidcustomview.R
import kotlinx.android.synthetic.main.activity_path.*

/**
 * <pre>
 *      @author : Allen
 *      date    : 2019/07/23
 *      desc    :
 * </pre>
 */
class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)
        start_btn.setOnClickListener {
            car_anim_view.startAnim()
        }
    }

}