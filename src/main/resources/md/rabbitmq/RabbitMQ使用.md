# RabbitMQ演示

## 交换器类型

- Direct Exchange
- Fanout Exchange
- Topic Exchange
- Headers Exchanges

#### Direct Exchange

```
消息路由到那些BindKey和RoutingKey完全匹配的队列中。
```

![direct](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/direct.png)

[代码示例](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/amqp/mqconfig/directexchange)

#### Fanout Exchange

```
把发送到该交换器的消息路由到所有与该交换器绑定的队列中
```

[代码示例](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/amqp/mqconfig/fanoutexchange)

#### Topic Exchange

```
将消息路由到BindKey与RoutingKey相匹配的队列中
```

![topic](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/topic.png)

[代码示例](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/amqp/mqconfig/topicexchange)

#### Headers Exchanges

```
不处理路由键。而是根据发送的消息内容中的headers属性进行匹配。在绑定Queue与Exchange时指定一组键值对；当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时指定的键值对进行匹配；如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，键值对的值可以是任何类型。而fanout，direct，topic 的路由键都需要要字符串形式的。

匹配规则x-match有下列两种类型：

x-match = all ：表示所有的键值对都匹配才能接受到消息

x-match = any ：表示只要有键值对匹配就能接受到消息
```

### 死信交换机

```
利用RabbitMQ的过期时间（TTL）和死信队列（DLX实现）
过期时间（TTL）：目前有两种方法设置消息的TTL，一种是通过队列属性设置，队列汇总的消息都有相同的过期时间，另一种是对消息本身进行单独设置，每条消息的TTL可以不同。如果两种方法一起使用，则消息的TTL以两者之间较小的那个数值为准。消息在消息队列汇总的生存时间一旦超过设置的TTL值，就会变成“死信”，消费者将无法再收到该消息（不是绝对的）。
死信队列（DLX）：当消息在一个队列中变成死信之后，它能够被重新发送到另一个交换器中，这个交换器就是DLX，绑定DLX的队列就称之为死信队列。
消息变成死信一般由于以下几种情况：
（1）消息被拒绝，并且设置requeue参数为false
（2）消息过期
（3）队列达到最大长度
```

![deadline](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/deadline.png)

![deadlineexchange](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/deadlineexchange.png)

[Spring Boot结合RabbitMQ死信队列代码示例](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/amqp/mqconfig/delayqueue)

## 高级特性

#### 如何保障消息100%投递成功？

> * 保障消息成功发出
> * 保障MQ节点成功接收
> * 发送短收到MQ节点（Broker）确认应答
> * 完善消息进行补偿机制

消息补偿机制：在发送消息的时候，将消息持久化到数据库中，并给这个消息设置一个状态（未发送，发送中，已到达），当消息状态发生了变化，需要对消息状态做变更，针对没有到达的消息做一个轮询操作，重新发送，对轮询次数也需要做限制，确保消息成功发送。

![消息可靠性投递](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/消息可靠性投递.png)

> UpStream Service：生产端
>
> DownStream Service：消费端
>
> CallBack Service：回调服务

* step1：业务消息入库后，第一次消息发送。
* step2：同样在消息入库成功后，发送第二次消息，这两条消息是同时发送的，第二条消息是延迟检查，可设置2min，5min延迟发送。
* step3：消费端监听指定队列。
* step4：消费端处理完消息后，内部生成新的消息send confirm，投递到MQ Broker。
* step5：CallBack Service回调监听服务监听MQ Broker，如果收到DownStream Service发送的消息，则可以确认消息发送成功，执行消息存储到MSG DB。
* step6：Check Detail检查监听step2延迟投递的消息，此时两个监听的队列不是同一个。收到延迟消息后，CallBack Service检查MSG DB，如果发现之前的消息已经投递成功，则不需要做其他事情，如果检查发现失败，则进行补偿，主动发送RPC通信，通知上游生产端重新发送消息。

> 目的：少做了一个DB存储，关注点并不是百分百投递成功，而是性能

#### 幂等性保障

> 在高并发情况下，有大量的消息到达MQ，消费端需要去消费大量的消息，这样情况下，难免出现消息的重复投递，网络闪断等。如果不做幂等，就会出现消息的重复消息。消费端的重复消息实际上并不是主要问题，问题是如何保障重复消费下的幂等性。消费端实现幂等，就意味着，即使重复消费，也不影响实际的结果。

##### 唯一ID+指纹码机制

> * 利用数据库主键去重，保证唯一性
> * SELECT COUNT(1) FROM T_ABC WHERE ID = 唯一ID + 指纹码，如果没有，则说明消息未被消费。
> * 好处：实现简单
> * 坏处：高并发情况下数据库写入的性能瓶颈
> * 解决方案：分库分表，减少数据库压力

##### Redis原子特性实现

最简单使用Redis的自增。

> - 使用Redis进行幂等，需要考虑的问题。
> - 第一：我们是否需要数据落库，如果落库的话，关键解决的问题是数据库和缓存如何做到原子性？ 加事务不行，Redis和数据库的事务不是同一个，无法保证同时成功同时失败。大家有什么更好的方案呢？
> - 第二：如果不进行落库，那么都存储到缓存中，如何设置定时同步的策略？ 怎么做到缓存数据的稳定性？

## Confirm确认消息和Return消息机制

> 生产者投递消息之后，如果Broker收到消息，就会给生产者一个应答
>
> 生产者接收应答，确认消息是否正常到达Broker，这种方式也是消息的可靠性投递的核心保障
>
> Return主要是用于key路由不到指定的队列，因此是一个错误的消息。MQ Broker提供了这种Return机制，将这些路由不可达的消息重新发送给生产端，生产端需要设置Return Listener去接收这些不可达的消息。

![消息确认](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/消息确认.png)

![Return消息机制](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/Return消息机制.png)

#### RabbitMQ消息确认和消息路由不可达

```java
@Slf4j
@Component
public class RabbitProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
private final RabbitTemplate rabbitTemplate;

/**
 * mandatory：设置为true，监听器会接收到路由不可达的消息，如果是false，broker端直接删除该消息
 *
 * @param rabbitTemplate
 */
@Autowired
public RabbitProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
    this.rabbitTemplate.setConfirmCallback(this);
    this.rabbitTemplate.setReturnCallback(this);
    this.rabbitTemplate.setMandatory(true);
}

/**
 * id + 时间戳 全局唯一  用于ack保证唯一一条消息 这里先用uuid表示
 *
 * @param message
 */
public void sendMsg(Object message) {
    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
    rabbitTemplate.convertAndSend(JsonUtil.toJson(message), correlationData);
}

/**
 * 向交换机发送message
 *
 * @param message
 * @param exchange
 * @param routingKey
 */
public void sendMsg(String exchange, String routingKey, Object message) {
    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
    rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
}

/**
 * 消息成功发送到broker时回调的方法
 *
 * @param correlationData
 * @param ack
 * @param cause
 */
@Override
public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    log.info("回调ID:{}", correlationData);
    if (ack) {
        log.info("消息成功发送到broker");
    } else {
        log.error("消息发送至broker失败{}", cause);
    }
}

/**
 * 当根据路由键无法路由到队列的时候回调的方法
 *
 * @param message
 * @param replyCode
 * @param replyText
 * @param exchange
 * @param routingKey
 */
@Override
public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    log.info("消息不可达...");
    log.info("return message:{}, exchange:{}, routingKey:{}, replyCode:{}, replyText:{}",
            message.toString(),
            exchange,
            routingKey,
            replyCode,
            replyText);
}
}
```
#### RabbitMQ消费端限流

```
/**
 * 消费端设置属性
 *
 * @param connectionFactory
 * @return
 */
@Bean
public SimpleRabbitListenerContainerFactory customerContainerFactory(ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
    simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
    //手动确认消息
    simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    //设置消息预取的数量
    simpleRabbitListenerContainerFactory.setPrefetchCount(1);
    return simpleRabbitListenerContainerFactory;
}
```