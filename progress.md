>  不知不觉距离上次写文章已经过去大半个月了，原本计划每周写一篇的想法在坚持几周之后最终还是被生活中各种各样的琐事打乱，无奈中夹杂这对自己的一点失望。
![心痛.jpg](http://upload-images.jianshu.io/upload_images/2057501-2f7d716b6474a6e7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/200)
>当初的愿望实现了吗
事到如今只好祭奠吗
任岁月风干理想再也找不回真的我
抬头仰望着满天星河
那时候陪伴我的那颗
这里的故事你是否还记得

##### 今天给大家带来的是自定义view系列之炫酷的进度条的效果，让你的进度条从此与众不同。
 按照惯例先上效果图，俗话说无图无有真相嘛。


![60%的进度条.png](http://upload-images.jianshu.io/upload_images/2057501-6a56cf5ae61b543f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)


![100%的进度条.png](http://upload-images.jianshu.io/upload_images/2057501-2fef3dc9943f5f99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)


![模拟器的效果没有真机好.gif](http://upload-images.jianshu.io/upload_images/2057501-615ad5fe97faf782.gif?imageMogr2/auto-orient/strip)

我们可以看出来这其实就是一个进度条，此时的你或许心生疑惑，MD一个进度条有什么好说的，无论是官方的ProgressBar的还是GitHub开源的一抓一大把，这有什么好讲的。有这样的想法很正常，毕竟我曾经看别人写的文章的时候也会冒出这样的想法。

![哈哈](http://upload-images.jianshu.io/upload_images/2057501-79d6913926a01388.gif?imageMogr2/auto-orient/strip)

那么我为什么还要写呐，首先并不是我们所有的需求都能在网上找到，其次，即使找到类似的代码修修补补也能用，但对我们的提升并不大，作为一名合格的开发人员只有亲手撸出来的代码用着才踏实嘛，我们是抱着学习的心态看问题，分析问题，并解决它的。

### 说了那么多废话下边就开始步入正题吧
> 如果真是一个简简单单的进度条的话确实没什么好讲的，仔细观察效果图后你会发现其实还是有点内容的，麻雀虽小五脏俱全。

> 这里边有几个点需要说明一下：
①、进度条有动画效果
②、进度条上边有个百分比的样式的绘制
③、百分比tip框跟随进度条移动需要注意的事项

#### 1、带动画的进度条效果
因为我们是自定义view，看到的所有元素都是在onDraw里边绘制出来的，分析进度条效果我们可以分解出几个步骤，先绘制底层百分百进度条（也就是背景色），再绘制真实的进度。
```
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //  tipHeight + progressMarginTop其实是把进度条绘制到百分比tip框的下边
        //  这里只是给出大概的计算，如有需求可以精确计算高度
        //绘制背景
        canvas.drawLine(getPaddingLeft(),
                tipHeight + progressMarginTop,
                getWidth(),
                tipHeight + progressMarginTop,
                bgPaint);
        //绘制真实进度
        canvas.drawLine(getPaddingLeft(),
                tipHeight + progressMarginTop,
                currentProgress,
                tipHeight + progressMarginTop,
                progressPaint);

    }

```
进度条画完之后就是让它动起来，我们使用属性动画试试改变当前进度的值重新绘制就可以了，动画效果我们继续使用ValueAnimator
```
  /**
     * 进度移动动画  通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //这是我们自己的需求进度只显示整数
                textString = formatNum(format2Int(value));
                //百分比的进度转换成view宽度一样的比例
                currentProgress = value * mWidth / 100;
                //进度回调方法
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }               
                invalidate();
            }
        });
        progressAnimator.start();
    }
```
到此带动画的进度条就实现了，一起看一下效果吧

![progress1.gif](http://upload-images.jianshu.io/upload_images/2057501-cac05310af11b961.gif?imageMogr2/auto-orient/strip)

#### 2、绘制上边的百分比布局

![image.png](http://upload-images.jianshu.io/upload_images/2057501-928e96a9e021f9eb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

看到这个百分比的提示框，你会想到如何实现呐，很多人会选择使用图片然后变更图片的位置来达到效果，作为一个有追求的程序猿怎能满足于此呐，自己绘制出来岂不更好。![很强势.jpg](http://upload-images.jianshu.io/upload_images/2057501-0416873e5601def3.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
接下来我们来分析一下如何绘制这个带三角的矩形
> 这里说一下我们实现思路：其实第一次我是准备从起点到终点用过画闭合的线做的，需要计算七个点的坐标，最终能实现，但是这种方法太笨了，根本拿不出手哦
![根据点绘制闭合的线.png](http://upload-images.jianshu.io/upload_images/2057501-bb303270ea674651.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)

******
> 在苦思的时候突然又看了一眼设计图发现这个矩形是带圆角的，我这样绘制闭合的线是达不到圆角效果的，等等，圆角？圆角不是可以通过绘制圆角矩形画出来么，我擦，貌似这是一个不错的思路，赶紧去验证。
![圆角矩形+三角形.png](http://upload-images.jianshu.io/upload_images/2057501-7bf4c15bbd18b17b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/240)

果不其然，堪称完美，先绘制一个圆角矩形，在其下边绘制一个三角形，至于里边的进度数值直接drawText就行了
![嘚瑟.gif](http://upload-images.jianshu.io/upload_images/2057501-7dd12b21adaa8f94.gif?imageMogr2/auto-orient/strip)
```
    /**
     * 绘制进度上边提示百分比的view
     *
     * @param canvas
     */
    private void drawTipView(Canvas canvas) {
        drawRoundRect(canvas);
        drawTriangle(canvas);
    }


    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.set(moveDis, 0, tipWidth + moveDis, tipHeight);
        canvas.drawRoundRect(rectF, roundRectRadius, roundRectRadius, tipPaint);
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        path.moveTo(tipWidth / 2 - triangleHeight + moveDis, tipHeight);
        path.lineTo(tipWidth / 2 + moveDis, tipHeight + triangleHeight);
        path.lineTo(tipWidth / 2 + triangleHeight + moveDis, tipHeight);
        canvas.drawPath(path, tipPaint);
        path.reset();

    }
```

#### 3、计算百分比Tip框的起始位置及移动分析
样式绘制出来接下来就是各种计算了，先来张手绘图凑合着看哈

![手绘图，忽略字迹看内容哈.png](http://upload-images.jianshu.io/upload_images/2057501-ca13ff89043bb230.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)
> 担心图片不清晰就再对图片内容描述一下，重要信息有四个，进度的起始点A和B、tip框的起始点M和N，动画执行过程是这样的：刚开始的时候只有进度条移动，此时tip框是不动的，当进度条到达tip框中间三角形顶点x坐标的时候，tip框跟着进度开始一起移动，当tip框右边界到达整个进度的右边界的时候，tip框停止移动，进度条继续移动一直到终点。

```
 /**
     * 进度移动动画  通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //进度数值只显示整数，我们自己的需求，可以忽略
                textString = formatNum(format2Int(value));
                //把当前百分比进度转化成view宽度对应的比例
                currentProgress = value * mWidth / 100;
                //进度回调方法
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
                //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
                //moveDis是tip框移动的距离
                if (currentProgress >= (tipWidth / 2) &&
                        currentProgress <= (mWidth - tipWidth / 2)) {
                    moveDis = currentProgress - tipWidth / 2;
                }
                invalidate();
            }
        });
        progressAnimator.start();
    }
```
##### 最终实现的效果
![progress.gif](http://upload-images.jianshu.io/upload_images/2057501-615ad5fe97faf782.gif?imageMogr2/auto-orient/strip)
### 写在最后
到此本篇文章终点部分已经结束，按照惯例应该做下总结的，但今天并没有这个打算(毕竟文章里边技术点描述的很清楚了，哈哈😆)。这里就分享一下我的一些看法吧，敲代码其实是一件很枯燥的事，每天面对一大堆字母时间久了或多或少会心烦，那么我们怎样才能保持一个好的状态去coding呐，培养自己的兴趣，兴趣真的很重要，有了兴趣你会把敲代码当做生活的一部分而不仅仅是工作。这样生活才有意义，否则时间久了真的会颓废。


![网络图片，仅供娱乐](http://upload-images.jianshu.io/upload_images/2057501-a2b4eca9a8ddfc04.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)

***
# github源码地址[**传送门**](https://github.com/lygttpod/AndroidCustomView/blob/master/app/src/main/java/com/allen/androidcustomview/widget/HorizontalProgressBar.java)
***
 > 谨以此篇来记录自己项目中遇到的问题，献给需要类似功能的小伙伴们。如果你有好的建议欢迎评论指出，大家一起讨论、学习、进步！