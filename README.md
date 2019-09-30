# dao-utils

Utility library for working with Hibernate and Temporal data. 
This library uses some code and ideas taken from the 
[DAO Fusion](http://opensource.anasoft.com/daofusion-site/index.html) 
project which provided an excellent basis for creating DAOs. 
Unfortunately the project has not been updated for ten years and a 
number of the APIs used are now deprecated and the dependencies for 
the project are now considerably out of date.

The PersistentEntityDAO interface provides the following methods:

```java
public interface PersistentEntityDAO<T extends Persistable<I>, I extends Serializable> {

  Class<T> getEntityClass();
  
  <S extends T> Optional<S> get(I id, Class<S> targetEntityClass);

  Optional<T> get(I id);

  <S extends T> List<S> getAll(Class<S> targetEntityClass);

  List<T> getAll();

  <S extends T> S saveOrUpdate(S entity);

  void delete(T entity);

  <S extends T> void delete(I id, Class<S> targetEntityClass);

  void delete(I id);

  <S extends T> int deleteAll(Class<S> targetEntityClass);

  int deleteAll();

  void refresh(T entity);

  <S extends T> long countAll(Class<S> targetEntityClass);

  long countAll();
}
```

To create a DAO of a given type, simply extend from the ```AbstractPersistentEntityDao``` class and 
override the ```getEntityClass``` method:

```java
public class UserDao extends AbstractPersistentEntityDao<ImmutableUserEntity,Long> {
  public UserDao(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Class<ImmutableUserEntity> getEntityClass() {
    return ImmutableUserEntity.class;
  }
}
```

## Temporal DAOs and Repositories

Temporal filtering uses AOP to apply the hibernate filter. Because of this, 
any DAOs or Repositories must be a Spring managed Bean in order to make the aspect work.

Define a filter called "effectivityFilter" with a parameter called "effective" and 
a type of "timestamp". This should be defined at either at class level, or in 
package-info.java if using across multiple entities.

```java
@FilterDef(name="effectivityFilter", parameters={@ParamDef(name="effective", type="timestamp")})
```

Next, add the filter to the temporal entity.

```java
@Entity
@Table(name = "USER")
@AttributeOverride(name="id", column = @Column(name="USER_ID"))
@Filter(name=EFFECTIVITY_FILTER, condition=":effective between start and end")
public class ImmutableUserEntity extends ImmutablePersistentTimestampTemporalEntity implements UserEntity<Long> {
  private String firstName;
  private String secondName;

  // Getters and setters removed...
}
```

You can create your own DAO by extending from AbstractPersistentTemporalEntityDao:
```java
public class UserDao extends AbstractPersistentEntityDao<ImmutableUserEntity,Long> {
  public UserDao(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Class<ImmutableUserEntity> getEntityClass() {
    return ImmutableUserEntity.class;
  }
}
```

```java
@Configuration
public class Config {
  @Bean
  public AbstractPersistentTemporalEntityDao<ImmutableUserEntity> getImmutableUserEntityDao(EntityManager entityManager) {
    return new AbstractPersistentTemporalEntityDao<Long,ImmutableUserEntity>(entityManager) {
      @Override
      public Class<ImmutableUserEntity> getEntityClass() {
        return ImmutableUserEntity.class;
      }
    };
  }
}
```

Or simply override a Spring Repository instance and apply the @DateTimeEffectivityAware to the methods you whish to apply filtering to:

```java
@Repository
public interface ImmutableUserTemporalRepository extends CrudRepository<ImmutableUserEntity, Long> {
  @Override
  @DateTimeEffectivityAware
  Iterable<ImmutableUserEntity> findAll();
}
```

