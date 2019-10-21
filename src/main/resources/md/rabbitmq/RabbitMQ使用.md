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

![deadlineexchange](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/rabbitmq/deadlineexchange.png)

[Spring Boot结合RabbitMQ死信队列代码示例](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/modelexample/amqp/mqconfig/delayqueue)

## 消费端和服务端配置

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

