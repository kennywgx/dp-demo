# dp-demo
## TODO:
- [ ] redis做二级缓存

## [generator-deepexi-spring-cloud](https://github.com/deepexi/generator-deepexi-spring-cloud)使用文档
[CHANGELOG](./CHANGELOG.md)

- [快速开始](/1.docs/guides/quickly_start.md)
- [使用参考文档](/1.docs/guides/reference.md)
- [开发参考文档](/1.docs/guides/dev_reference.md)

## 截图
- `GET` /v1/user/接口权限拦截：未登录
 ![unauthenticated user](/1.docs/images/unauthenticated.png)

- `UPDATE` /v1/user/{userId}/role接口权限拦截：角色拦截
 ![unauthenticated user](/1.docs/images/no_role.png)

- `GET` /v1/user/接口权限拦截：权限拦截
 ![unauthenticated user](/1.docs/images/no_permission.png)
 
- `GET` /v1/user/接口权限拦截：正常调用
 ![unauthenticated user](/1.docs/images/normal.png)