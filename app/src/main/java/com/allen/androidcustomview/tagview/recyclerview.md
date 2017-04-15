###一、需求解析
1、先说下项目需求，不管是好评还是差评下边的Tag标签有不同的展示类型，有的字数多的会单独占一行处理（这边其实也可以扩充，比如说两三个字的可以一行显示三个Tag标签），第一眼看到这个需求准备使用网上的开源库TagLayout去实现，但是尝试了一下后发现其实他们实现的效果同项目要的效果还是有蛮大差距的，可以看到效果图里边是要求文字是居中对齐的，左右对称的。
2、想到使用GridView实现这个功能，定义adapter去实现没问题，问题是什么时候显示一行什么时候显示两行三行并不能确定，毕竟有时候服务端返回的没有类型标示只有tag_name和tag_id，你要根据什么设置类型呐，而且自从recyclerview之后现在要是还用GridView的话岂不是太落伍了。下边就重点介绍一下使用recyclerview实现如图效果。
###二、功能实现
我们知道recyclerview实现GridView效果只需配置一下参数就行了

        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        recycerView.setLayoutManager(layoutManage);
        
可以看到GridLayoutManager需要传递两个参数，一个是上下文对象，另一个是一行显示几列的参数常量，既然这个常量可以指定那么是不是这个值可以去控制呐，答案当然是yes

我们会注意到GridLayoutManager里边有个setSpanSizeLookup方法,本篇的重点就是这个方法（这个方法具体意义大家可以网上搜索，会有很多相关介绍，以及通过它实现一些复杂的布局，再次不做过多讨论）

         layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                     @Override
                     public int getSpanSize(int position) {
                         return 0;
                     }
                 });

其实getSpanSize返回值就是控制每行有几列的，根据这个思路我们不妨试试。因为recyclerview填充数据是根据adapter实现的，我们就把给adapter的数据源同样在setSpanSizeLookup这个方法里边判断一下不就行了吗？
根据这个思路于是有了下面的代码


            /**
             * 如果单个item显示的字数大于指定某个值就显示一列  默认2列
             */
             
             //设置item数据大于多少字只显示一行  默认 超过九个字的程度只显示一列
             
             private static final int MAX = 9;
                 
            
            private int setSpanSize(int position, List<TagBean> listEntities) {
                int count;
                if (listEntities.get(position).getTag_name().length() > MAX) {
                    count = 2;
                } else {
                    count = 1;
                }
        
                return count;
            }
            
            
            layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                 @Override
                                 public int getSpanSize(int position) {
                                     return setSpanSize(position,list);
                                 }
                             });
                             
                             
核心代码就这么多，我们可以根据自己的需求随意定制样式

