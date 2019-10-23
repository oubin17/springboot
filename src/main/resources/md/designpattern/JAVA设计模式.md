# JAVA设计模式  from 《大话设计模式》

#### 简单工厂模式

> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/factorypattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern1.png)



#### 策略模式

> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/strategypattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern2.png)



![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern3.png)

#### 单一职责原则：

> 就一个类而言，应该仅有一个引起他变化的原因。
>
> 如果一个类承担的指责过多，就等于把这些职责耦合在一起，一个职责的变化可能会削弱或者抑制这个类完成其他职责的能力，这种耦合会导致脆弱的设计，当变化发生时，设计会遭受意想不到的破坏。

#### 开放-封闭原则：

> 软件的实体（类，模块，函数等等）应该可以扩展，但是不可修改。
>
> 对于扩展是开放的，对于修改是封闭的。

#### 依赖倒置原则：

> 高层模块不应该依赖低层模块，两者都应该依赖其抽象；抽象不应该依赖细节，细节应该依赖抽象（High level modules shouldnot depend upon low level modules.Both should depend upon abstractions.Abstractions should not depend upon details. Details should depend upon abstractions）。其核心思想是：要面向接口编程，不要面向实现编程。
>
> 依赖倒置原则是实现开闭原则的重要途径之一，它降低了客户与实现模块之间的耦合。
>
> 由于在软件设计中，细节具有多变性，而抽象层则相对稳定，因此以抽象为基础搭建起来的架构要比以细节为基础搭建起来的架构要稳定得多。这里的抽象指的是接口或者抽象类，而细节是指具体的实现类。  

#### 里氏代换原则：

> 里氏替换原则通俗来讲就是：子类可以扩展父类的功能，但不能改变父类原有的功能。也就是说：子类继承父类时，除添加新的方法完成新增功能外，尽量不要重写父类的方法。
>
> 如果通过重写父类的方法来完成新的功能，这样写起来虽然简单，但是整个继承体系的可复用性会比较差，特别是运用多态比较频繁时，程序运行出错的概率会非常大。
>
> 如果程序违背了里氏替换原则，则继承类的对象在基类出现的地方会出现运行错误。这时其修正方法是：取消原来的继承关系，重新设计它们之间的关系。  

#### 装饰模式：

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern4.png)



![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern5.png)



#### 代理模式：

> 代理模式，为其他对象提供一种代理以控制对这个对象的访问。
>
> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/proxypattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern6.png)



![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern7.png)



#### 工厂方法模式：

> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/factoryfuncpattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern8.png)

> 简单工厂模式的最大优点在于工厂类中包含了必要的逻辑判断，根据客户端的选择条件动态实例化相关的类，对于客户端来说，去除了与具体产品的依赖。就像计算器，你只需要把“+”传给工厂，工厂自动给出了相应的实例，客户端只要去做运算就可以了。
>
> 工厂方法模式实现时，客户端需要决定实例化哪一个工厂来实现运算类，选择判断的问题还是存在的，也就是说，工厂方法把简单的内部逻辑判断移到了客户端代码来进行，你想要加功能，本来是改工厂类的，而现在是修改客户端。
>
> 工厂方法模式克服简单工厂模式违背的开放-封闭原则，保持了封装对象创建过程的有点。

#### 模板方法模式：

> 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern9.png)



#### 迪米特法则：

> 也叫最少知识原则。如果两个类不必彼此直接通信，那么这两个类就不应当发生直接的相互作用，如果其中一个类需要调用另一个类的某一个方法的话，可以通过第三方转发这个调用。

#### 外观模式：

> 为子系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口。

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern10.png)

#### 观察者模式：事件委托机制

> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/observerpatternpattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern11.png)




#### 抽象工厂模式：

> 提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern12.png)

#### 状态模式：

> 当一个对象的内在状态改变时允许改变其行为，这个对象看起来像是改变了其类。
>
> 状态模式主要解决的是当控制 一个对象状态转换的条件表达式过于复杂的情况，把状态的判断逻辑转移到表示不同状态的一系列类当中，可以把复杂的判断逻辑简化。

#### 适配器模式：

> 将一个类的接口转换成客户希望的另一个接口，Adapter模式使得原本接口不兼容而不能一起工作的那些类可以一起工作。

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern13.png)

#### 单例模式：

> 保证一个类仅有一个实例，并提供一个访问它的全局访问点。
>
> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/singleonpattern)

#### 桥接模式：

> 将抽象部分与他的实现部分分离，使他们都可以独立地变化。
>
> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/bridgepattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern14.png)



![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern15.png)

> 实现系统可能有多角度分类，每一种分类都可能有变化，那么就把这种多角度分离出来让它们独立变化，减少他们之间的耦合。

#### 职责链模式：

> 使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系，将这个对象连成一条链，并沿着这条链传递该请求，知道有一个对象处理他为止。
>
> [代码地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/designpattern/responsechainpattern)

![img](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/designpattern/pattern16.png)

> 接收者和发送者都没有对方的明确信息，且链中的对象自己也并不知道链的结构，结果是职责链可简化对象的相互连接，它们仅需保持一个指向其后继者的引用，而不需保持它所有的候选者的应用，大大降低了耦合度。