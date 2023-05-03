# 2023May-Day-assessment

app：对标（模仿）知乎日报的迷乎日报

红岩网校2023年寒假考核项目

## app的简要介绍

这个app是按照知乎日报的app的样式制作的。

# 很抱歉没有考虑到屏幕尺寸的问题，布局写的有点问题（只是以我的屏幕尺寸为参照！）

主要的界面如图所示：

1.![c2392c237e1fad05b29544844a3dded](https://user-images.githubusercontent.com/117186626/235841428-7ae0f433-7971-42dd-bc73-2f4bdfd8da3e.jpg)


2.![6260eab6a50d52ee1b2bf2957024181](https://user-images.githubusercontent.com/117186626/235841447-b1c27759-e518-4237-96fb-4e245b3bd116.jpg)


3.![1b1aaf709b45b9343457e4bb7181aae](https://user-images.githubusercontent.com/117186626/235841460-353a26a1-a7fc-4c74-ab9d-f61b06cbabd5.jpg)


4.![a5310f3036ef499b2ec5589288968c3](https://user-images.githubusercontent.com/117186626/235841475-7648ee5d-708c-4d83-8a40-2d0024479300.jpg)


5.![8de56a8e66c71197116384a1e82e277](https://user-images.githubusercontent.com/117186626/235841492-fad4e0b6-9727-40b0-a0c2-d1d6c7449dcd.jpg)


6.![eb4c0ae1d616320a9f18e23bb9f6cd7](https://user-images.githubusercontent.com/117186626/235841504-4e259084-3e7a-4bc7-a1d9-ca26f7a64e8a.jpg)

7.![9d8abbc976a6325d42efcf054a8148c](https://user-images.githubusercontent.com/117186626/235841599-3b680d4b-46a3-41b2-b664-0c1c7a15c48f.jpg)

## app的功能展示：

1.实现了上拉进行刷新
![1683093702107](https://user-images.githubusercontent.com/117186626/235842196-b6c155f9-3dbc-4d0f-959a-04a850bb8c9c.gif)


2.实现了下拉加载
![1683093809688](https://user-images.githubusercontent.com/117186626/235842390-cd16e2ce-6f7d-4662-af2f-af4e068ac82c.gif)

3.实现了左右滑动
![1683093936297](https://user-images.githubusercontent.com/117186626/235842980-445b2f0b-5dfa-45f4-81ac-ba0be90f297f.gif)

4.实现了黑夜模式（有坑，不要轻易点击）
上面有图。
5.使用viewpager2展示topnews，实现滑动
（未完全实现轮播图，只能转动一次）
![1683094160770](https://user-images.githubusercontent.com/117186626/235843075-d106ac2d-bc94-4c97-8131-4ea717b4ea01.gif)

6.实现了，新闻浏览界面的返回，查看评论区，分享功能
![1683094297968](https://user-images.githubusercontent.com/117186626/235843353-8a3dd011-2a12-4689-8e36-35bbeb999bd3.gif)


## app主要功能的实现思路

### 1.viewpager2 进行轮播图的转换（不完善）

我们在xml文件中单独配置了viewpager2，让后实现他的adapter，我们采用了listAdapter，在Adapter中接受的类型为<Story>l类型对象，在binder中进行数据的绑定，接着在mainActivity中进行相关的注册，把livedata中的数据传入。

### 2.上拉刷新功能的实现

这里我们使用了swiperefreshlayout的布局，并且里面嵌套了vp2和rv（有坑）。并且在主函数中调用了它的setOnRefreshListener方法，在里面实现逻辑，代码展示如下。

应为我们使用的是listAdapter，里面有一个回调函数，会帮助我们更新数据，我们只需要调用adapter的notifyDataSetChanged（）方法，通知adapter数据已经更新了。

```kotlin
myViewModel.rnTopStorySendQuest()
adapter.notifyDataSetChanged()
myViewModel.rnRecentStoryQuest()
rvAdapter.notifyDataSetChanged()
mBinding.swipeRefresh.isRefreshing = false
```

### 3.下滑刷新功能的实现

下滑刷新功能的话，我们主要在ui界面中进行recyclerview的下滑，所以我们应该先看recyclerview的工作，recyclerview的工作就是把网络请求储存在livedata中的数据中的每一个的有关信息拿到，进行绑定，以列表的形式展示出来，具体的adapter我才用的跟vp2一模一样，只不过是接受的对象类型稍微改变一下，在binder中进行数据的绑定，（再次之前要inflate一个item）。这样在我们使用recyclerview进行数据列表的展示之后，我们就可以调用recyclerview的addOnScrollListener，对我们的滑动进行监听，并且有以下逻辑判断，

就是判断我们到达了底部的时候，关键就来了。

```kotlin
val layoutManager = recyclerView.layoutManager as LinearLayoutManager
val totalItemCount = layoutManager.itemCount
val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
if (totalItemCount - 1 == lastVisibleItemPosition&& !recyclerView.canScrollVertically(1)) {

```

![image-20230503132011055](C:\Users\29364\AppData\Roaming\Typora\typora-user-images\image-20230503132011055.png)

我们在接口文档中可以看到有一个变量，那就是时间，所以我们在viewmodel中重写了一个网络请求的方法，在配置api的时候，加入了

```kotlin
@GET("api/4/news/before/{time}")
fun getBeforeNews(@Path("time")time:String): Call<RecentNewsData<Story>>
```

让后的话每次下拉刷新的时候，计算出相应的时间变量传入即可，最后还要调用我们的notifyDataSetChanged（）方法。

时间变量的计算采用有关的函数，每次刷新减少一天即可。

### 4.新闻的浏览和左右翻页

在这里我用了一个新的activity来专门的对新闻进行浏览。

同样的，在layout中我们写一个vp2还有一个Linnerlayout把下面的几个按钮配置上去，vp2的item里单独写一个webview，接着的数据绑定和声明跟上面的一样，adapter也是使用的listAdapter。关键在于如何获得MainActivity中的livedata数据。我采用了适合轻量储存数据的，把我们得到的livedata，转换为json代码传入，在当前activity拿到并且转换回来，使用了livedata的观察，进行submit。那么怎么获得我们点击是哪个呢？我用了一个不明智的方法，在我们点击列表的adapter的binder中，传入了一个uri的参数到新闻打开的activity里面，让后把livedata的每个uri数据重新弄到arrylist数组里面，index of position，让后在

```kotlin
mBinding.detailViewpager2.setCurrentItem(position)
```

但是在activity中实现，消耗性能，而且打开新闻有延迟。（每次都得重新遍历。）

### 5.新闻浏览界面的返回，查看评论区，分享功能的实现

这些按钮的话我们在新闻查看xml中下面布局，在新闻的activity中实现每个按钮的点击事件，分享的话调用系统的方法，查看评论区，我又重新new了一个activity（不理智的行为），里面放recyclerview。具体逻辑跟上面的相似。

### 6.黑夜模式，

当点开主UI右上角的用户按钮是，切换到一个新的activity，布局界面伪登录，点击我们的睡眠图标，会采用黑夜模式，代码如下：



```kotlin
mBinding.imNight.setOnClickListener {
    Toast.makeText(this, "功能还在完善中", Toast.LENGTH_SHORT).show()
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> {
            // 切换到夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.putBoolean("my_bool_key", true)
            editor.apply()

        }
        Configuration.UI_MODE_NIGHT_YES -> {
            // 切换到白天模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putBoolean("my_bool_key", false)
        }
    }
}
```

不过在此之前，我们需要在res-colors-下新建一个xml（night）文件夹，与白天模式下的xml对比，把我们需要改变的颜色改变，并且在

```kotlin
<style name="Theme.MyApplication" parent="Theme.AppCompat.DayNight.NoActionBar">
```

一定要继承Theme.AppCompat.DayNight。安卓系统官方的声明

## 技术亮点

### 使用了viewmodel

把网络请求放在了viewmodel中，网络请求使用retrofit实现，请求得到的数据储存在livedata里面。viewmodel的好处之一就是帮我们保存数据，livedata持有被外界观察的能力。我们app在旋转屏幕的时候，不会出现界面重启的现象。

### 使用了retrofit

### 使用了livedata

### 使用了databinding

### 使用了轻量化的储存SharedPreferences

## 心得体会

一开始，我觉得自己啥都没学会，但是当我一个一个实现自己的目标的时候，自己的思路也在慢慢打开，初步了解了adapter，一些ui控件，jetpack部分组建的使用情况。最后也是通过自己的方法实现了左右滑动中数据共享的问题（方法很不理智！）

## 有待提高的地方

在这次的项目中，我犯了一个比较严重的问题，那就是，过多的使用了activity，没有使用fragment，而且很多的逻辑实现，监听事件都放在了activity中，这是致命的，及其影响了我们app的性能！！！我们的app在浏览新闻的时候，向下滑动不流畅。app过于臃肿

主界面的vp2没有在我们滑动的时候也滑动！具体的原理还没有搞清楚，应该是出现了事件被拦截了。

轮播图没做好，只能在activity onStart的时候转动一次，也紧紧是转动了一次，估计是外面嵌套了swiperefreshlayout，时间拦截？具体的原理还没有搞懂

缺少fragment的应用，自己对fragment的学习程度不够大。第一行代码要重新看一遍fragment，详细了解fragment的使用方法。







