# Spring Data JPA

### 项目JPA继承体系

![JPA继承结构](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/jpadesign/JPA继承结构.jpg))

* PagingAndSortingRepository：使接口具有分页和排序的能力。
* JpaRepository：继承PagingAndSortingRepository，并提供基础增删改查的接口。
* MongoRepository：继承PagingAndSortingRepository，提供MongoDB增删改查接口。
* CustomRepository：自定义仓库，继承PagingAndSortingRepository，提供一些通用的默认方法，比如严格获取对象的方法，当无法获取对象时直接抛异常。

```java
@NoRepositoryBean
public interface CustomRepository<T extends BaseDomain<I>, I extends Serializable> extends PagingAndSortingRepository<T, I> {

    /**
     * 提供默认方法，严格获取对象
     *
     * @param id
     * @return
     */
    default T strictFind(I id) {
        T t = this.findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;
    }
}
```

* CustomService：持有CustomRepository对象，具备CustomRepository接口提供的能力，Service层的service对象继承该类，这样可以使得servier具有对基础对象操作的能力，而不用注入Repository对象。

```java
public class CustomService<T extends BaseDomain<I>, I extends Serializable> {

    @Autowired
    private CustomRepository<T, I> customRepository;

    /**
     * 严格获取
     *
     * @param id
     * @return
     */
    protected T strictFind(I id) {
        T t = findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;
    }

    private T findOne(I id) {
        return customRepository.findOne(id);
    }
}
```

* DomainRepository：用户自定义仓库，继承JpaRepository接口和用户自定义CustomRepository接口。在定义实体类的Repository对象时需要继承该接口。
* SimpleJpaRepository：修改JPA提供的默认JpaRepository接口实现类，该类实现了JpaRepository接口的方法。
* SimpleMongoRepository：修改JPA提供的默认MongoRepository接口实现类。
* CustomJpaRepositoryImpl:继承SimpleJpaRepository类，实现CustomRepository接口，用于覆盖Jpa的默认实现类。

```java
public class CustomJpaRepositoryImpl<T extends BaseDomain<I>, I extends Serializable> extends SimpleJpaRepository<T, I>
        implements CustomRepository<T, I>{

    private final EntityManager entityManager;

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     */
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
    
    @Override
    public T strictFind(I id) {
        T t = findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;

    }
}
```

* CustomMongoRepositoryImpl：覆盖JPA中MongoDB仓库实现类。

```java
public class CustomMongoRepositoryImpl<T extends BaseDomain<I>, I extends Serializable> extends SimpleMongoRepository<T, I>
        implements CustomRepository<T,I> {

    private final MongoOperations mongoOperations;
    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link }.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public CustomMongoRepositoryImpl(MongoEntityInformation<T, I> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Override
    public T strictFind(I id) {
        return null;

    }
}
```

### 代码地址

[项目使用JPA继承结构地址](https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/common/base)

#### JPA官方文档

[官方文档](https://spring.io/projects/spring-data-jpa)



![基础类继承结构](https://github.com/oubin17/springboot/blob/master/src/main/resources/images/jpadesign/基础类继承结构.png) )

* BaseDomain：抽象类，提供5个基础属性(省略get set方法)

```java
@NoRepositoryBean
@MappedSuperclass
@EntityListeners(BaseDomainListener.class)
public abstract class BaseDomain<I extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(generator = "id")
    @Column(length = 36)
    private I id;

    /**
     * 创建时间
     */
    @JsonProperty("create_at")
    @CreatedDate
    @Column(updatable = false, nullable = false, columnDefinition = "timestamp not null")
    @Field("create_time")
    private Long createTime;

    /**
     * 创建人
     */
    @JsonProperty("create_by")
    @CreatedBy
    @Column(updatable = false)
    @Field("create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @JsonProperty("last_modified_at")
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp not null")
    @Field("last_modified_time")
    private Long lastModifiedTime;

    /**
     * 修改人
     */
    @JsonProperty("last_modified_by")
    @LastModifiedBy
    @Field("last_modified_by")
    private String lastModifiedBy;
}
```

* BaseStateDomain：新增状态属性

```java
@MappedSuperclass
public abstract class BaseStateDomain<I extends Serializable> extends BaseDomain<I> {

    /**
     * 用于记录状态
     */
    @JsonProperty("state")
    @Field("state")
    @Column
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
```

* BaseTenantDomain：新增租户属性

```java
@MappedSuperclass
public class BaseTenantDomain<I extends Serializable> extends BaseDomain<I> {

    /**
     * 用于租户隔离
     */
    @Column(updatable = false)
    private Long tenant;

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }
}
```

### 代码地址

[地址]( https://github.com/oubin17/springboot/tree/master/src/main/java/com/ob/common/base/domain )