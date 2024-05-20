尚品甄选项目，采用B2C模式，使用SpringBoot+SpringCloud微服务架构，采用前后端分离开发模式。项目包含两个系统：后台管理系统 和 前台用户系统（手机端）

## 一、项目功能

### 1、后台管理系统功能

![image-20230918095531708](./assets/image-20230918095531708.png)



### 2、前台用户系统功能

![image-20230918100253882](./assets/image-20230918100253882.png)



## 二、项目技术

### 1、表的关系

#### （1）权限相关的表关系

![image-20230918101317508](./assets/image-20230918101317508.png)

#### （2）商品相关的表关系

* product  商品基本信息表
* product_sku 商品sku表，一个商品包含多个sku
* product_details 商品详情表，商品详情图片



#### （3）订单相关的表关系

* order_info 订单基本信息表
* order_item 订单项表，一个订单里面包含多个订单项



### 2、前端技术

* Element-Admin：Vue3 + Element Plus

* ES6：模板字符串、箭头函数...
* NPM
* Axios



### 3、后端技术

![image-20230918103122032](./assets/image-20230918103122032.png)

![image-20230918103144461](assets\image-20230918103144461.png)





