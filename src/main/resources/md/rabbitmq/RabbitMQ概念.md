# RabbitMQ概念

* 概念
* 收发消息过程
* 消费策略
* 高级特性：过期时间，延迟队列
* 消息分发
* 作用
* 传输保障

## 概念

> RabbitMQ是由Erlang语言编写的实现了高级消息队列协议的开源消息代理软件（面向消息的中间件）

![alt ](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-1.png)

Queue：消息队列。消息的载体，每条消息都会被投递到一个或者多个队列中，如果消息无法路由到某个队列，生产者需要相应的配置接收无法路由的消息，否则丢弃。

Producer: 消息生产者，负责将消息投递到Exchange上。

Exchange：消息交换机。负责接收消息生产者传递来的消息，并指定消息按照什么规则路由到哪个队列中。

Consumer：消息消费者，消费由消息队列投递过来的消息。

Binding：将Exchange和Queue按照某种路由规则绑定起来。

RoutingKey：路由关键字，Exchange根据RoutingKey进行消息投递。

Channel：消息通道，也称信道，在客户端的每个连接里可以建立多个channel，每个channel代表一个会话任务。

### Queue

> 多个消费者可以订阅同一个队列，队列中的消息会被平均分摊（Round-Robin轮询）到多个消费者进行处理，而不是每个消费者都收到所有的消息并处理。

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-2.png)

### Exchange

> 交换器类型：fanout，direct，topic，headers。
>
> fanout：它会把发送到该交换器的消息路由到所有与该交换器绑定的队列中。
>
> direct：它会把消息路由到那些BindKey和RoutingKey完全匹配的队列中。
>
> topic：它将消息路由到BindKey与RoutingKey相匹配的队列中，这里有不同的匹配规则：
>
> ![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-3.png)
>
> headers：headers类型的交换器不依赖于路由键的匹配规则来路由消息，而是根据发送的消息内容中headers属性进行匹配。在绑定队列和交换器时制定一组键值对 当发送消息到交换器时，RabbitMQ 会获取到该消息的 headers (也是一个键值对的形式) ，对比其中的键值对是否完全匹配队列和交换器绑定时指定的键值对，如果完全匹配则消息会路由到该队列，否则不会路由到该队列 headers 类型的交换器性能会很差，而且也不实用，基本上不会看到它的存在。

### 连接和信道

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-4.png)

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-5.png)

Connection可以用来创建多个channel，但是Channel实例不能在线程间共享，应用程序应该为每一个线程开辟一个channel，某些情况下channel的操作可以并发运行，但是在其他情况下会导致在网络上出现错误的通信帧交错，同时也会影响发送方确认机制的运行，所以多线程间共享channel实例是非线程安全的。

生产者和消费者都能够使用queueDeclare来声明一个队列，但是如果消费者在同一个信道上订阅了另一个队列，那就无法再声明队列了，必须先取消订阅，然后将信道置为“传输”模式，才能声明队列。

RabbitMQ的消息存储在队列中，交换器的使用并不真正消耗服务器的性能，而队列会。

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-6.png)



## 发送消息

（1）生产者连接到RabbitMQ Broker，建立一个连接Connection，开启一个信道Channel

（2）生产者声明一个交换器，并设置相关属性，比如交换机类型，是否持久化

（3）生产者声明一个队列并设置相关属性，比如是否排他，是否持久化，是否自动删除

（4）生产者通过路由键将交换器和队列绑定起来

（5）生产者发消息到RabbitMQ Broker，其中包含路由键，交换器等信息

（6）相应的交换器根据接收到的路由键查找匹配的队列。

（7）如果找到，则将从生产者发送过来的消息存入相应的队列中。

（8）如果没有找到，则根据生产者配置的属性选择丢弃还是回退给生产者。

（9）关闭信道

（10）关闭连接

## 消息过程

（1）消费者连接到RabbitMQ Broker，建立一个连接，开启一个信道。

（2）消费者向RabbitMQ Broker请求消费相应队列中的消息，可能会设置相应的回调函数

（3）等待RabbitMQ Broker回应并投递相应队列中的消息，消费者接收消息。

（4）消费者确认（ack）接收到的消息

（5）RabbitMQ从队列中删除相应已经被确认的消息

（6）关闭信道

（7）关闭连接

## 消费端的消息消费策略

> RabbitMQ的消费模式分两种：推（push）模式和拉（pull）模式，推模式采用Basic.Consume进行消费，拉模式采用Basic.Get进行消费
>
> Basic.Consume将信道置为接收模式，直到取消队列的订阅为止。在接收模式期间，RabbitMQ会不断地推送消息给消费者，当然推送消息的个数还是会受到Basic.Qos的限制。如果只想从队列获得单条消息而不是持续订阅，建议还是使用Basic.Get进行消费。但是不能将Basic.Get放在一个循环里来代替Basic.Consume，这样会严重影响RabbitMQ的性能，如果要实现高吞吐量，消费者理应使用Basic.Consume方法。

### 消费端的确认与拒绝

> 为了保证消息从队列可靠地达到消费者， RabbitMQ 提供了消息确认机制( message acknowledgement) 消费者在订阅队列时，可以指定 aut oAck 参数，当 autoAck 等于 false时， RabbitMQ 会等待消费者显式地回复确认信号后才从内存(或者磁盘)中移去消息(实质上是先打上删除标记，之后再删除) 。当 autoAck 等于 true 时， RabbitMQ 会自动把发送出去的消息置为确认，然后从内存(或者磁盘)中删除，而不管消费者是否真正地消费到了这些消息
>
> 采用消息确认机制后，只要设置 autoAck 参数为 false ，消费者就有足够的时间处理消息(任务) ，不用担心处理消息过程中消费者进程挂掉后消息丢失的问题 因为 RabbitMQ 会一直等待持有消息直到消费者显式调 Basic.Ack 命令为止。
>
> 当autoAck 参数置为 false ，对于 RabbitMQ 服务端而 ，队列中的消息分成了两个部分：一部分是等待投递给消费者的消息:一部分是己经投递给消费者，但是还没有收到消费者确认信号的消息。 如果 RabbitMQ 直没有收到消费者的确认信号，并且消费此消息的消费者己经断开连接，则 RabbitMQ 会安排该消息重新进入队列，等待投递给下一个消费者，当然也有可能还是原来的那个消费者。
>
> RabbitMQ不会为未确认的消息设置过期时间，它判断此消息是否需要重新投递给消费者的唯一依据是消费该消息的消费者连接是否己经断开，这么设计的原因是 RabbitMQ 允许消费者消费一条消息的时间可以很久很久。

#### 消息拒绝策略：

返回给RabbitMQ，重新存入队列。（2）立即把消息从队列中移除，不会把它发送给新的消费者。

在消费者接收到消息后，如果想明确拒绝当前的消息而不是确认，那么应该怎么做呢?

RabbitMQ 2.0 版本开始引入了 Basic .Reject 这个命令，消费者客户端可以调用与其对应的 channel.basicReject 方法来告诉 RabbitMQ 拒绝这个消息。

如果 requeue 参数设置为 true ，则 RabbitMQ 会重新将这条消息存入队列，以便可以发送给下一个订阅的消费者;如果 requeue 参数设置为 false ，则RabbitMQ立即会把消息从队列中移除，而不会把它发送给新的消费者。

19.mandatory和immediate是channel.basicPublish方法中的两个参数，他们都有当消息传递过程中不可达目的地时将消息返回给生产者的功能。

20.当mandatory参数为true时，交换器无法根据自身的类型和路由键找到一个符合条件的队列，那么RabbitMQ会调用Basic.Return命令将消息返回给生产者。当mandatory参数设置为false时，出现上述情形，则消息直接丢弃。

生产者可以通过添加channel.addReturnListener来添加ReturnListener监听器实现。

## 过期时间（TTL）

> 目前有两种方法设置消息的TTL，一种是通过队列属性设置，队列汇总的消息都有相同的过期时间，另一种是对消息本身进行单独设置，每条消息的TTL可以不同。如果两种方法一起使用，则消息的TTL以两者之间较小的那个数值为准。消息在消息队列汇总的生存时间一旦超过设置的TTL值，就会变成“死信”，消费者将无法再收到该消息（不是绝对的）。
>
> 23.死信队列：当消息在一个队列中变成死信之后，它能够被重新发送到另一个交换器中，这个交换器就是DLX，绑定DLX的队列就称之为死信队列。
>
> 消息变成死信一般由于以下几种情况：
>
> （1）消息被拒绝，并且设置requeue参数为false
>
> （2）消息过期
>
> （3）队列达到最大长度

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-9.png)

## 延迟队列

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-10.png)

![RabbitMQ-11](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-11.png)



## 备份交换器

> 生产者在发消息的时候如果不设置mandatory参数，那么消息在未被路由的情况下将会丢失：如果设置了mandatory参数，那么需要添加ReturnListener的编程逻辑，生产者的代码将变得复杂，如果既不想复杂化生产者的编程逻辑，又不想消息丢失，那么可以使用备份交换器，这样可以将未被路由的消息存储在RabbitMQ中，再在需要的时候去处理这些消息。
>
> 可以通过在声明交换器的时候添加alternate-exchange参数来实现，也可以通过策略（Policy）的方式实现。如果两者同时使用，则前者的优先级更高。

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-7.jpg)

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-8.png)

## 消息分发

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-12.png)

## 消息中间件的作用

> 解耦，冗余，扩展性，削峰，可恢复性，顺序保证，缓冲，异步通信。

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-13.png)

![RabbitMQ-14](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-14.png)

## 消息传输保障

![](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/RabbitMQ-15.png)

