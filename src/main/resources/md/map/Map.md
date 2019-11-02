# HashMap&ConcurrentHashMap

### HashMap

#### 1.7

##### 数据结构

> 数组+链表

##### 插入方式

> 使用的是头插法：1.8用单链表进行的纵向延伸，采用头插法容易出现逆序且环形链表死循环问题。

##### 扩容后重新计算数据的存储位置

> 直接使用hash值和需要扩容的二进制数进行&（hash值&length-1），这里就是为什么HashMap的长度一定要是2的次幂的原因，这样length-1能保证数据散落的均匀程度和本身无关，而只与输入数据本身的hash值有关，最大限度减少hash碰撞。
>
> 也就是说，只要输入的hashCode本身是分布均匀的，那hash算法的结果就是均匀的。

##### 先扩容，后插入

> 在并发环境下，扩容时可能造成环状链表，导致get操作时，cpu空转
>
> [环状链表](https://mp.weixin.qq.com/s?__biz=MzI2NjA3NTc4Ng==&mid=2652079766&idx=1&sn=879783e0b0ebf11bf1a5767933d4e61f&chksm=f1748d73c6030465fe6b9b3fa7fc816d4704c91bfe46cb287aefccee459153d3287172d91d23&scene=21#wechat_redirect)

##### HashMap扩容和Rehash

> 影响发生resize的因素有两个：
>
> 1.Capacity
>
> 2.LoadFactor
>
> HashMap负载因子，默认为0.75f
>
> 衡量是否resize的条件：
>
> 1.HashMap.size 》= capacity * loadFactor
>
> 2.产生hash冲突
>
> rehash：创建一个空Entity数组，长度是原数组的两倍，将原来数组上的数据重新hash到新的数组上。

#### 1.8

##### 数据结构

> 数组+链表+红黑树
>
> 当链表的深度到达8的时候，也就是默认阈值，就会自动扩容把链表转成红黑树的数据结构来把复杂度从O(n)变成O(logN)

##### 插入方式

> 使用的是尾插法：加入了红黑树使用尾插法，能够避免出现逆序且链表死循环问题。

##### 扩容后重新计算数据的存储位置

> 使用扩容前的位置+扩容的大小值，不再是7的那种异或的方法，效率更高

##### 先插入，后扩容

> 

为什么红黑树和链表转换的阈值是8

> 统计数据，能够避免频繁地转换，效率也不能太低

##### hash函数

> 扰动函数：右移16位，正好是32bit的一半，将自己的高半区和低半区做异或，就是为了混合原始哈希码的高位和低位，以此来加大低位的随机性，而且混合后参杂了高位的部分特征，这样高位的信息变相地保留下来。

```
//Java 8中的散列值优化函数
static final int hash(Object key) {
int h;
return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);    //key.hashCode()为哈希算法，返回初始哈希值
}
```

![扰动函数](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/扰动函数.png)





#### 哈希表如何解决hash冲突

> 哈希冲突的解决办法：
>
> （1）开放地址法：当冲突发生时，使用某种探测算法在散列表中寻找下一个空的散列地址，一般分为线性探测法，二次探测法，双重散列法。
>
> （2）再哈希法：有多个不同的hash函数，当冲突发生时，使用下一个hash函数。
>
> （3）链地址法：HashMap使用的方法。

![20180905105313470](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/如何解决hash冲突.png)

#### HashMap中String，Integer这样的包装类适合作为key

![key类型](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/key类型.png)

#### HashMap 中的 key若 Object类型， 则需实现哪些方法

![object作为key](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/object作为key.png)

### ConcurrentHashMap

#### 1.7

##### 数据结构

> ReentrantLock+Segment+HashEntry

##### ![ConcurrentHashMap7数据结构](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/ConcurrentHashMap7数据结构.png)



##### 定位Segment

> 将低位和高位再hash，记录高位的属性，否则会导致只要低位一样，就会hash到同一个Segment上，这样会造成哈希冲突非常严重，通过再hash会让每一位都参与到哈希运算中来。

##### Segment数量

> Segment的数量在初始化的时候就确定下来了，之后的rehash只会hash改Segment下的hashEntry[]大小，并不会对整个ConcurrentHashMap进行rehash。

##### GET操作

> get操作不会加锁，除非读到空值才加入重入锁，这是因为get方法需要用到的共享变量都定义成了volatile，保证了在线程之间的可见性，可被多线程读，并且不会读到过期的值，之所以不会读到过期的值，是根据Java内存模型的happen-before原则，对volatile变量的写入操作优于读操作，即使两个线程同时修改和获取volatile变量，get也能够拿到最新的值，这也是用volatile替换锁的经典应用场景。

##### PUT操作

> 当执行put时，会进行第一次key的hash来定位Segment的位置，如果该Segment还未初始化，就通过CAS进行赋值，然后进行第二次hash冲啊uo中，找到对应的hashEntry的位置将数据插入指定的HashEntry位置（链表的尾端）。
>
> 在插入数据的时候，会通过继承ReentrantLock的tryLock（）方法尝试去获取锁，如果获取成功就直接插入对应的位置，如果已经有线程获取该Segment的锁，那当前线程会以自旋的方式继续调用tryLock（），超过指定次数就被挂起，等待唤醒。

##### SIZE操作

> Segment里的全局变量count是一个volatile变量，在多线程场景下，不能认为只要把count相加就可以得到整个ConcurrentHashMap的大小了。因为会存在累加后之前的count存在变化的问题，但是如果使用锁，会把所有的put，remove，clean方法全都锁住，效率很低。
>
> ConcurrentHashMap采用的办法是先尝试2次通过不锁住Segment的方式来统计各个Segment的大小，如果统计过程中，count发生了变化，再采用加锁的方式来统计所有Segment的大小。
>
> 如何判断是否发生了变化呢？
>
> 使用modCount变量，put，remove等操作会将变量加1，所以在统计size的前后需要比较modCount是否发生了变化。

##### CAS实用

> Segment是在第一次插入的时候才初始化的，这里是用CAS来控制实例不被多次实例化



#### 1.8

##### 数据结构

> synchronized+CAS+HashEntry+红黑树
>
> 1.8的数据结构变得更加简单，使用synchronized来进行同步，不需要分段锁的概念，也就不需要Segment这种结构了（1.8还是存在Segment，但是不是用来控制并发），1.8锁的粒度就是HashEntry（首节点）
>
> 可以简单理解为HashMap的数据结构加上并发控制。
>
> 1.8也是使用红黑树来优化链表，基于长度很长的链表的遍历是一个漫长的过程，而红黑树的遍历效率是很快的。

![ConcurrentHashMap8数据结构](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/map/ConcurrentHashMap8数据结构.png)

##### 使用synchronized代替重入锁ReentrantLock

> 1.因为锁粒度降低了，在低粒度的加锁方式下，synchronized效率并不比ReentrantLock差。
>
> 2.JVM团队一直在优化synchronized，并且基于JVM的synchronized优化空间更大。

##### GET操作

> （1）计算hash值，定位到该table索引的位置，如果是首节点符合就直接返回
>
> （2）如果遇到扩容的时候，会调用标志正在扩容节点的find方法，查找该节点，匹配就返回。
>
> （3）如果都不符合，就往下遍历，匹配就返回，否则返回null

##### PUT操作

> （1）如果没有初始化就先调用initTable（）初始化
>
> （2）如果没有hash冲突就直接CAS插入
>
> （3）如果还在进行扩容就先扩容
>
> （4）如果存在hash冲突，就加锁来保证线程安全，一种是链表形式直接遍历到尾端插入，一种是红黑树按照红黑树结构插入
>
> （5）最后一个如果该链表的数量大于阈值8，就要先转成红黑树。
>
> （6）如果添加成功就调用addCount（）方法统计size，并检查是否需要扩容

