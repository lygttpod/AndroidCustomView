### 今天开始记录工作中遇到的需要实现的动画效果实现自定义view动画，后期会有一些列动画设计思路的文章。

> 在这里分享的是设计实现思路，仅供学习使用，让大家拿到稍微复杂点的动画的时候要知道该如何去一步步分解实现，而不是抱怨
下边就先来看看设计需要的效果图及我们最终实现的效果图，毕竟有图有真相嘛！


![99.gif](http://upload-images.jianshu.io/upload_images/2057501-d710bca1e166fbc4.gif?imageMogr2/auto-orient/strip)


##### 其实我刚拿到设计图的时候心想，MD直接给一张gif图不就行了何必这个麻烦呐，随后冷静下来之后（其实就是抱怨之后）想想作为一名Android开发者总不能什么动画都依赖设计师吧，那样的话会显得我们开发者没什么卵用啊，说不定还会被设计师鄙视哦，于是就开始了动画分析及实现之旅。
******
### 通过这个gif动画我们分析出动画过程的实质：
> 一个长方形（或者是圆角长方形）逐渐过渡成为两边是半圆的长方形，于此同时长方形两边向中间靠拢最终形成一个圆，然后圆上升一定高度，最后在圆里边画出对勾(✔).整个动画分解的其实就是这几个部分，那么我们该如何实现呐，不要捉急，继续往下看。

## 第一步：我们要先画出一个圆角矩形吧
```
    /**
     * 绘制带圆角的矩形
     *
     * @param canvas 画布
     */
      private void draw_oval_to_circle(Canvas canvas) {
      
              //这里是对矩形的位置大小的设置
              rectf.left = two_circle_distance;
              rectf.top = 0;
              rectf.right = width - two_circle_distance;
              rectf.bottom = height;
      
              //画圆角矩形
              canvas.drawRoundRect(rectf, circleAngle, circleAngle, paint);
      
          }
          
```

##### 圆角矩形绘制完成之后就是改变圆角半径的大小使其两边形成半圆的效果，那么怎么才能让他成为半圆呐，来看看一张图，若要绘制成半圆效果，那么这个圆的直径就是view自身的高度，那么这个圆的半径就是height/2

![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-741dbdfc3aa6c568.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```

    /**
     * 设置矩形过度圆角矩形的动画
     */
    private void set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

```
添加动画之后的效果如下

![button_1.gif](http://upload-images.jianshu.io/upload_images/2057501-e863b04adb9f2440.gif?imageMogr2/auto-orient/strip)


## 第二步：当矩形两边都是半圆之后就要处理使其向中间靠拢逐渐形成一个圆，那么问题又来了，需要向中间移动多少呐，并且怎么移动才能使两边都想中间聚拢呐
##### 下边来看一张图分析一下

![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-4f0ded1fc136ae56.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 有图可知移动的距离是(width-height)/2,然后在写一个动画让其改变距离最终两个半圆靠拢在一起形成圆

```
    /**
     * 设置圆角矩形过度到圆的动画
     * default_two_circle_distance = (w-h)/2
     */
    private void set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();

                //在靠拢的过程中设置文字的透明度，使文字逐渐消失的效果
                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;

                textPaint.setAlpha(alpha);

                invalidate();
            }
        });
    }
 ```
完成上边代码后再来看下效果


![button_2.gif](http://upload-images.jianshu.io/upload_images/2057501-917b6211e11ba7b9.gif?imageMogr2/auto-orient/strip)


## 第三步：让圆上移移动距离。这个移动很好实现,直接改变Y轴方法的坐标就行了，这个很简单就直接看代码吧
```
    /**
     * 设置view上移的动画
     */
    private void set_move_to_up_animation() {
        final float curTranslationY = this.getTranslationY();
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, curTranslationY - move_distance);
        animator_move_to_up.setDuration(duration);
        animator_move_to_up.setInterpolator(new AccelerateDecelerateInterpolator());
    }
    
```

## 第四步：在圆中绘制一个对勾，而且是带动画的对勾，让对勾以动画的形式慢慢绘制出来
如果对相关API不熟悉的话不知道会怎么去实现呐，或许你会想通过绘制线的方式，在对勾起点开始不断改变移动点的坐标进行绘制，那么怎么获取这些点的坐标呐，这里我们使用Path和DashPathEffect两个方法实现，对DashPathEffect不了解的小伙伴可以去查一下文档哦
> DashPathEffect这个类的作用就是将Path的线段虚线化。
构造函数为DashPathEffect(float[] intervals, float offset)，其中intervals为虚线的ON和OFF数组，该数组的length必须大于等于2，phase为绘制时的偏移量。

##### 我们先拿到对勾的path路径在对其改变偏移量加上DashPathEffect就能实现动态绘制对勾的效果了，那么怎么计算对勾的起点折点和终点的坐标呐，在网上找了一个不错的图片，如果你的设计师直接把位置给你标明的很详细的话你就省了这些自己计算的麻烦

![对勾的绘制位置.jpg](http://upload-images.jianshu.io/upload_images/2057501-ca86ac42f1ec4b81.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ```
   /**
     * 绘制对勾     
     * 下边计算比例是参考网上一些例子加上自己一步一步尝试的出来的比例，仅供参考
     * 如果条件允许最好还是让设计师给你标明一下比例哦！
     */
    private void initOk() {
        //对勾的路径
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);

        pathMeasure = new PathMeasure(path, true);

    }


```
```
    /**
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();

                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
    }
```
再来看效果

![绘制对勾.gif](http://upload-images.jianshu.io/upload_images/2057501-673df7e71c2bccba.gif?imageMogr2/auto-orient/strip)


至此动画分解都已完成，但是机智的你应该已经发现问题了，就是感觉动画播放衔接的不是很好，那么接下来我们就处理这个问题，回到最初的效果图上，矩形变圆角和缩放成圆形是同时进行的，那么我们有什么办法可以实现动画同时播放呐，哈哈，身为老司机的想必已经知道了使用AnimatorSet，他可以播放动画集、顺序播放等，那么我们就开始处理吧
> 我们让矩形变圆角和矩形往中间缩放同时进行，然后圆在上移，最后绘制对勾

```
        animatorSet
                .play(animator_move_to_up)
                .before(animator_draw_ok)
                .after(animator_rect_to_square)
                .after(animator_rect_to_angle);
```

最终奉上我们自己一步一步完整实现的效果图

![button_animation.gif](http://upload-images.jianshu.io/upload_images/2057501-0d1119721429bf71.gif?imageMogr2/auto-orient/strip)

## 总结：看到这里是不是觉得这样的动画实现起来也不是很复杂嘛，也许你会觉得这样的动画没什么技术含量，实现起来真的没什么难度，何必再此大做文章呐，其实我这里也只是个抛砖引玉的作用，提供一种学习方法，也许今天我们遇到的只是一个简单的动画，可明天如果需要我们去做更复杂的动画呐，我们该怎么处理，怎么分析，怎么实现呐。只要我们把自己的需求分析拆解，把复杂的步骤简单化，分布实现在组合到一起就可以实现自己想要的效果（你要知道炫酷的电影特效也是一帧一帧动画合成的哦）。

