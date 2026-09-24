# 背单词软件

* 使用语言:Java
* 编码格式用:UTF-8
* IDE:IDEA
* 项目管理工具:Gradle (ps:也就是用libgdx框架)
* 仿照有道写的豆腐渣,用的有道的icon

#### **开始日期:2018-12-3号**
1. 2018-12-3号 创建框架,测试下git -- Zzzxb
2. 2018-12-6号 增加选项，睡醒明天再写，好好摸索下。 -- Zzzxb
3. 2018-12-7号 重写选项栏 -- Zzzxb
4. 2018-12-8号 选项卡完成，接下来选项卡显示内容 -- Zzzxb
5. 2018-12-9号 查单词功能基本ok，剩下背单词和设置选项了。晚安! -- Zzzxb
6. 2018-12-9号 背单词功能差不多了，明天设置界面，后天稍微重构一下. -- Zzzxb
7. 2018-12-11号 采用libgdx写个界面真闹心,mybatis找不到路径,用JDBC吧. -- Zzzxb
8. 2018-12-12好 Gradle路径问题已经修复，继续使用MyBatis，是真滴麻烦。--Sdh
9. 2018-12-13号 采用json能查找1000多个单词了，显示7天的每日一句,等有时间了再改吧
10. 2018-12-13号 忘了更新项目了写完后更新的过,只有read文件合并了一下，其它东西东西完美丢失，没办法只能重写一遍了.更改仓库传到我fork的仓库了，
mybatis在我电脑上用不成,我也打不开项目，等需要的时候我提交下分支就行了. -- Zzzxb
11. 2018-12-13号 用Json查单词整好了,用Json的话找到我fork你的仓库下载下来我就不提交了，话把词库更新了就能用了，背单词的话仿照着WordJson文件和character.json文件再写一个添加进去替换了下optionTwoPage类中的WordJson名字就能用了。界面哪里不行的话等3月份我再改改,现在这个只用更新词库就行了。 -- Zzzxb
#### **不完整Demo结束日期:2018-12-13号** -- Zzzxb

* 初次用libgdx写东西不知道该怎么写单词软件，本来想从数据库中查找的可是路径总是不对。
知识或应对方法了再好好写东西，不然东拼西凑的，根豆腐渣工程一样。一戳就塌方了。
![1](https://github.com/ssssdh/Dictionary/blob/master/core/assets/1.png)
![2](https://github.com/ssssdh/Dictionary/blob/master/core/assets/2.png)

---

#### **2026-09-24 修复: 让它在 M 系列芯片的 Mac 上重新跑起来** -- Zzzxb

换电脑之后一直报这个错:

```
java.lang.UnsatisfiedLinkError: Can't load library: .../T/libgdxzzzxb/a4fe9f6c/libgdx.dylib
```

原因是 libGDX 1.9.8 自带的 macOS 原生库是 **32 位 i386** 的(用 `lipo -info` 一看就知道),
现在的 macOS 加上 Apple Silicon 早就完全不支持 32 位了, 所以这个 dylib 怎么都加载不进去,
跟代码一点关系都没有。要动的地方:

1. libGDX `1.9.8` -> `1.14.1`, 后端从 `gdx-backend-lwjgl`(LWJGL 2, 根本没有 arm64 原生库)
   换成 `gdx-backend-lwjgl3`(里面自带 `lwjgl natives-macos-arm64`)
2. Gradle `4.6` -> `8.10`; `compile` -> `implementation`; 编译目标用 Java 8(和 libGDX 自身的字节码
   一致, 这样 IDEA 里项目 SDK 是 1.8 还是终端默认的 21 都不会再报 "无效的源发行版")
3. `DesktopLauncher` 改成 `Lwjgl3Application` + `Lwjgl3ApplicationConfiguration`
4. macOS 上 GLFW 必须跑在进程的第一个线程, 运行时要带 `-XstartOnFirstThread`
   (Gradle 的 run/debug 任务里已经按系统自动加上了)

怎么运行:

```bash
./gradlew :desktop:run     # 直接运行
./gradlew :desktop:debug   # 调试运行, 再连 localhost:5005
./gradlew :desktop:dist    # 打包成 desktop/build/libs/Dictionary-1.0.jar
java -XstartOnFirstThread -jar desktop/build/libs/Dictionary-1.0.jar   # macOS 上跑 jar 也得带这个参数
```

> 在 IDEA 里直接点 `DesktopLauncher` 那个绿三角运行的话, 要在 Run Configuration 的
> VM options 里填上 `-XstartOnFirstThread`, 不然会报 `GLFW may only be used on the main thread`。
> 顺便建议把 IDEA 的 Project SDK 和 Gradle JVM 都设成 21。 -- Zzzxb
