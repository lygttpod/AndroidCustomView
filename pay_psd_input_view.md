>  开始阅读本篇文章之前先来说一下使用场景吧，我们知道如今移动支付已经占据我们日常支付的90%的份额，以微信支付和支付宝支付为主，也越来越多的APP开始添加支付模块，不管使用哪种支付有一个步骤是少不了的，那就是输入支付密码(指纹支付再此就不做讨论了哦)，所以今天来给大家带来一篇自定义支付密码输入框的设计和实现方式，同时记录自己工作中遇到的问题及解决办法。

##### 按照惯例我们先看看微信和支付宝支付密码输入框的样式吧

![微信支付密码](http://upload-images.jianshu.io/upload_images/2057501-3f88acf3ff12f333.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)

![支付宝支付密码](http://upload-images.jianshu.io/upload_images/2057501-19692affea7ac536.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)



##### 看到这样的效果相信很多开发者第一反应就是先网上搜一下看看有没有现成的（哈哈，我也不例外哦），因为这都是简单的一些view不涉及动画所以网上相关例子还是很多的，我这边总结了一下大致可以分为一下几类

1、通过布局的方式：
  > 在布局里边放置6个EditView,在每个输入框中间再放置一个view用于设置中间分割线，每个EditVIew只允许输入一个字符，然后对每个进行监听，一个密码输入完之后让另一个EditView获取焦点，以此类推就可以大致实现图中的效果了（这样确实可以实现，实现起来也很简单，但是代码量不少，而且这种方式是不是显得逼格不够高或者没有逼格呐）
 
 2、完全自定义view（继承View）：
  > 这个就稍微复杂一点，大致流程是，先监听触摸事件，按下时弹出键盘，然后对软键盘进行监听，获取每次点击键盘对应的字符串，然后在onDraw方法里边画6个圆，在绘制外边框，然后是中间的分割线。这里边有个问题就是每次都要对软键盘进行监听取值等一系列操作，加上Android机型众多整不好哪块软键盘就出问题了呐。（虽然有逼格，但是不实用哦）
![欲步又止](http://upload-images.jianshu.io/upload_images/2057501-c44b427f30d67465.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)


 3、继承自EditView实现自定义view:
  > 大致流程和上一种差不多，不过我们不需要对软键盘进行处理了,少了很多繁琐及兼容性的操作，同时又不失逼格，哈哈。
 
 
##### 看到以上三种实现方式想必你大概已经知道我们要使用哪种方式实现了，没错就是集成EditView的自定义view,这样我们还可以使用很多EditView的属性哦
 
 # 开发前先整理一下实现步骤：
 > 1、绘制外边框（可以是直角也可以是圆角，设计师要什么我们就给他什么）
2、绘制密码之间的分割线（竖线）
3、绘制实心圆代替输入的字符
4、对输入字符进行监听，便于扩展处理
5、实现一些常用的外部接口方法调用

# 具体实现
##### 1、绘制外边框：
###### 要想绘制边框我们首先要知道view的宽高，通过onSizeChanged方法去初始化宽高等数据，然后绘制圆角矩形（默认让他矩形显示直接传入圆角半径为0即可）
```

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2; //第一个圆心的x坐标
        startY = h / 2; //第一个圆心的y坐标

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }
```
```
RectF rectF = new RectF()
rectF.set(0, 0, width, height);

canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);
```
##### 2、绘制密码之间的分割线：
###### 既然是分割线肯定是等均分的，假设我们的密码最大输入maxCount=6，那么我们只需画5个分割线就可以了，分割线坐标的计算

![计算分割线的起点和终点的坐标](http://upload-images.jianshu.io/upload_images/2057501-c036f62a54567431.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
通过循环画出每个分割线
for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }
```
完成这一步我们先运行一下看看边框效果吧

![模拟器上运行效果](http://upload-images.jianshu.io/upload_images/2057501-ffc8650ab8bd526a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 3、绘制实心圆代替输入的字符：
这里需要监听EditView的输入，重写onTextChanged方法获取输入字符的长度,然后计算每个圆圆心的坐标位置
```
        //第一个圆心的x坐标
        startX = w / maxCount / 2;
        //第一个圆心的y坐标
        startY = h / 2;

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    radius,
                    circlePaint);
        }
    }
```
写到这里的时候是不是感觉样式问题已经完成的差不多了，运行以来输入几个字符串一看，MD出问题了(看图说话)
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-c874ff85c2765f6d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
从图中可以看出是绘制了相应的实心圆，但是自带的底部线、光标、字符还在，要是拿这个去交差绝逼会被产品骂死。
![产品的内心独白](http://upload-images.jianshu.io/upload_images/2057501-5c9272f1fafc688c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)
出现这个问题肯定是代码的问题喽，我们根据问题去一个一个解决，首先给view设置一个透明的背景色，然后隐藏光标，再跑一下看看
```
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
```
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-2eb902a15e6f185b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这次底部的线和光标都见了，但是输入的字符还在，这又是什么问题？？？我们明明重写了onDraw方法，怎么还会出现原来的字符呐，等等。。。对啊，我们只是重写，他肯定还有自己的方法，我们只要把EditView内部重绘的方法干掉不就行了，想到这里喜出望外，拿跟辣条先压压惊，在ondraw方法中这样做
```
    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的话会默认绘制输入的文字
       // super.onDraw(canvas);
    }

你没看错，就是这一行代码注释掉就ok，
至于是为什么你肯定知道，
不注释的话在我们重写之前他已经调用了内部方法
去绘制输入的字符了，
我们在重写后虽然我们的方法生效了，
但它的方法也生效了哦。
```
此时压抑不住内心的小激动赶紧运行起来看看（哈哈，完美解决问题）
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-3ddeb0b87582796a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

至此主要功能已经完成，剩下的需要去封装一些方法供外部调用，我这里已经封装几个方法，我们知道这样设置支付密码的页面一般有两个：一个设置密码，一个重新设置密码，按照正常的逻辑我们去监听这个密码输入框，输入密码之后进行比较看是否相等就完事了，为了方便以后使用不要每次自己再去写一大堆监听方法，我们直接在内部封装好是不是对以后使用起来更方便一点呐
```
   /**
     * 密码比较监听
     */
    public interface onPasswordListener {
        void onDifference();
        void onEqual(String psd);
    }

    //使用者需要调用的方法
    public void setComparePassword(String comparePassword, onPasswordListener listener) {
        mComparePassword = comparePassword;
        mListener = listener;
    }
```
这里就直接上代码了,代码通俗易懂
```
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.toString().length();

        if (mComparePassword != null && textLength == maxCount) {
            if (TextUtils.equals(mComparePassword, getPasswordString())) {
                mListener.onEqual(getPasswordString());
            } else {
                mListener.onDifference();
            }
        }
        invalidate();

    }
```

实际使用中我们这样设置(是不是瞬间感觉用的过程简单了很多)
```
        passwordInputView.setComparePassword("123456", new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference() {
                // TODO: 2017/5/7   和上次输入的密码不一致  做相应的业务逻辑处理 
                Toast.makeText(MainActivity.this,"两次密码输入不同",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼 
                Toast.makeText(MainActivity.this,"密码相同"+psd,Toast.LENGTH_SHORT).show();

            }
        });
```

## 文章到此本应该结束了，可是我们UI设计师给出的效果图不是这样子的，不按常理出牌（心中顿时飘过一万只草泥马）
![草泥马](http://upload-images.jianshu.io/upload_images/2057501-03c297d8cb4eaaa5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)


来看看我们的效果图
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-0b4ffe583ec6b1e4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)

![大哥，算了算了，还是去给他实现一下吧](http://upload-images.jianshu.io/upload_images/2057501-34d96808da101a95.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)

他不按常量出牌，不过这也是他们一贯的作风，既然他们要这样的效果那我们就去做喽，整个流程还是一样的，唯一的不同就是外边框和密码之间的分割线变成了底部间断的线，这肯定难不倒我们啦，不就是画六条线吗，每根线的起点终点坐标和上边圆心左边计算差不多，就不多描述了看代码最实在
```
    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {

        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }
```

项目至此完美收工，看看效果吧
![MD你要的效果给你.png](http://upload-images.jianshu.io/upload_images/2057501-5966bec7cb34dd21.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/480)
# 最后总结
 > 以上微信支付密码和我们这种现实效果我都封装在PayPsdInputView中了，可以根据需求切换不同的样式，
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/2057501-8b24b3524f0cd749.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)
如果以后还要其他的支付密码输入的样式的话同样会添加进来的，目的只有一个--------->下次开发省时省力。
![两种样式供你选择](http://upload-images.jianshu.io/upload_images/2057501-3ca764c315dcdea2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)


 ## 番外篇
 > 我相信看到这里肯定有一部分小伙伴会说MDZZ,这不就是简单的画矩形、画圆、画线吗，有什么好写的，谁都会做。我想说的是你们说的没错，涉及的知识点是很简单，但是不要忘了，麻雀虽小五腑俱全，真正你去一行一行敲的时候你会发现有很多不曾注意过的问题都会浮出水面。只是单纯的会几个知识点其实没什么卵用，把所学知识点运用起来重组成一个功能模块的时候你才算真正的掌握。
![来，老表，抽根烟，平复一下暴躁的心情](http://upload-images.jianshu.io/upload_images/2057501-0c1557eb6c1b9cac.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/320)


***

 > 谨以此篇来记录自己项目中遇到的问题，献给需要类似功能的小伙伴们。如果你有好的建议欢迎评论指出，大家一起讨论、学习、进步！