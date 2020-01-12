# simple-aop
A Simple AOP implement
一个简单的AOP实现
用了2.5个下午 使用cglib实现的一个自娱自乐的aop 还有很多不完善

#### 说明
@Invader 相当于 AOP中的 @Aspect
@Top 相当于 AOP中的 @Before
@Bottom 相当于AOP中的 @After
@Sandwich 相当于AOP中的 @Around
以上注解的value 是切面方法的名称(全称 不含参数 未做详细的根据参数不同进行细分)
ProcessTarget的作用类似于JoinPoint

### 代码
Application为入口类
annotation包下:
@Invader 声明切面
相当于 AOP中的 @Aspect

@Top 声明前置方法
相当于 AOP中的 @Before

@Bottom 声明后置方法
相当于AOP中的 @After

@Sandwich
相当于AOP中的 @Around

core包下:
InvaderInitialization 搜索所有切面
InvaderWrapper 处理切面方法
ProcessTarget 目标方法及参数的包装
ProxyFactory cglib的代理类创建以及方法拦截器处理

invaders包下:
FirstInvader 切面定义

model包下:
MyStar 被代理的类
Star MyStar的接口(不要这个接口也没关系)

utils包下:
ClassUtils 根据包名获取此包下的所有Class 此方法直接从网上抄来的 懒得手写了 作者信息看此类的最下面