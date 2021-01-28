package com.allen.androidcustomview.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.allen.androidcustomview.R
import kotlinx.android.synthetic.main.activity_clear_screen.*

/**
 * <pre>
 *      author  : Allen
 *      e-mail  : yagang.li@yintech.cn
 *      date    : 2021/1/28
 *      desc    :
 * </pre>
 */
class ClearScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clear_screen)
        clear_screen_container.addClearView(iv_clear_content)
    }
}