package com.allen.androidcustomview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.allen.androidcustomview.R;
import com.allen.androidcustomview.adapter.HoverAdapter;
import com.allen.androidcustomview.bean.UserBean;
import com.allen.androidcustomview.utils.CharacterParser;
import com.allen.androidcustomview.utils.PinyinComparator;
import com.allen.androidcustomview.widget.HoverItemDecoration;
import com.allen.androidcustomview.widget.IndexView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/04/16
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class HoverItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IndexView indexView;
    private TextView showTextDialog;

    private HoverAdapter adapter;

    private List<UserBean> userBeans = new ArrayList<>();

    private String[] names = new String[]{"阿妹", "打黑牛", "张三", "李四", "王五", "田鸡", "孙五"};

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_item);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();


        userBeans = filledData(getData());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        indexView = (IndexView) findViewById(R.id.index_view);
        showTextDialog = (TextView) findViewById(R.id.show_text_dialog);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //一行代码实现吸顶悬浮的效果
        recyclerView.addItemDecoration(new HoverItemDecoration(this, new HoverItemDecoration.BindItemTextCallback() {
            @Override
            public String getItemText(int position) {
                //悬浮的信息
                return userBeans.get(position).getSortLetters();
            }
        }));

        adapter = new HoverAdapter(userBeans);

        recyclerView.setAdapter(adapter);

        initIndexView();
    }

    /**
     * 初始化右边字幕索引view
     */
    private void initIndexView() {
        indexView.setShowTextDialog(showTextDialog);
        indexView.setOnTouchingLetterChangedListener(new IndexView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String letter) {
                // 该字母首次出现的位置
                int position = getPositionForSection(letter);
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                    layoutManager.setStackFromEnd(false);
                }
            }
        });
    }

    public int getPositionForSection(String section) {
        for (int i = 0; i < userBeans.size(); i++) {
            String sortStr = userBeans.get(i).getSortLetters();
            if (sortStr.equals(section)) {
                return i;
            }
        }
        return -1;
    }

    private List<UserBean> getData() {
        List<UserBean> userBeans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            UserBean userBean = new UserBean();
            userBean.setUserName(names[i % 7]);
            userBeans.add(userBean);
        }

        return userBeans;
    }

    private List<UserBean> filledData(List<UserBean> sortList) {

        for (int i = 0; i < sortList.size(); i++) {

            if ("".equals(sortList.get(i).getUserName())) {
                sortList.get(i).setSortLetters("#");
            } else {
                // 汉字转换成拼音
                String pinyin = characterParser.getSelling(sortList.get(i).getUserName());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    sortList.get(i).setSortLetters(sortString.toUpperCase());
                } else {
                    sortList.get(i).setSortLetters("#");
                }
            }

        }

        // 根据a-z进行排序源数据
        Collections.sort(sortList, pinyinComparator);

        return sortList;
    }

}
