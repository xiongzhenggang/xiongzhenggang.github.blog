## 编译openjdk 参考《深入了解java虚拟机》
1. 依赖脚本 ，安装需要的相关软件（可以使用其他镜像源）
```sh
sudo apt-get install build-essential gawk m4 libasound2-dev libcups2-dev libxrender-dev xorg-dev xutils-dev x11proto-print-dev binutils libmotif3 libmotif-dev ant libxt-dev libxtst-dev  
```
  * 首先去 这里 http://download.java.net/openjdk/jdk7/ 下载OpenJDK7的源码zip包



2. 简要介绍下OpenJDK7中的目录

    hotspot: 放有OpenJDK Hotspot Virtual Machine 的源代码和make文件

    langtools: 放有OpenJDK 的javac 等工具的源代码和make文件

    cobra: 放有OpenJDK Cobra 的源代码和make文件

    jaxws: 放有OpenJDK JAXWS 的相关信息和make文件, 具体的源代码再编译时会下载

    jaxp: 放有OpenJDK JAXP 相关信息和make文件,具体的源代码再编译时会下载

    jdk: 放有OpenJDK runtime libraries 的源代码和make文件

    直接OpenJDK 目录下有整个OpenJDK 的make文件

3. 检查系统的工具版本，以下配置基于OpenJDK7

    3.1 GNU make 3.81或更高版本

    3.2 ANT 1.7.1或更高版本

    3.3 Sun 的BootstrapJDK6 update14或更高版本（虽然有些奇怪，但是因为编译这些Java代码需要一个可用的JDK）

    3.4 GNU gcc 4.3或更高版本

    3.5 ZIP 2.2或更高版本

    3.6 FreeType 2.3或更高版本 （下载地址http://download.savannah.gnu.org/releases/freetype/  ,下载后解压）

    3.7 Advanced Linux Sound Architecture（ALSA） 0.9.1或更高版本(下载地址http://www.alsa-project.org/main/index.php/Download)



4. 如果怕麻烦可以使用相关命令来安装依赖包

    Fedora: yum-builddep java-1.6.0-openjdk

    Debian:  aptitude build-dep openjdk-6

  Ubuntu: sudo aptitude build-dep openjdk-6

  其它具体的可以参考OpenJDK中的README-builds.html



5. 环境工具设置好后了运行以下命令以设置或取消环境变量
```sh
    export LANG=C

    export ALT_BOOTDIR=/usr/java/jdk1.6.0_30/

    export ALLOW_DOWNLOADS=true

    export USE_PRECOMPILED_HEADER=true

    export SKIP_DEBUG_BUILD=false

    export SKIP_FASTDEBUG_BUILD=true

    export DEBUG_NAME=debug

    unset CLASSPATH

    unset JAVA_HOME
```


6. 到OpenJDK目录下运行 make sanity 进行检测 如果输出如下证明OpenJDK 编译环境检测没有问题

```xml   
    PREVIOUS_RELEASE_IMAGE =

          ALT_PREVIOUS_RELEASE_IMAGE =

       Sanity check passed.

```   
   如果是下面这样证明 环境依然有问题, 按照提示的WARNING 和 ERROR进行修改 直到检测通过

```xml      
ERROR: Your JAVA_HOME environment variable is set.  This will

              most likely cause the build to fail.  Please unset it

               and start your build again.

       Exiting because of the above error(s).



       make: *** [post-sanity] Error 1

```

7. 检测通过了在OpenJDK目录下运行

  make clean

  make



8. 注意事项

  8.1 保持所有命令是在同一个用户下运行的而且这个用户么还有JAVA_HOME 等环境变量

      这样编译程序才能正确的找到环境变量和不会有权限冲突

  8.2 OpenJDK 编译可能需要些时间,不要慌张

  8.3 要有ALLOW_DOWNLOADS=true。否则编译jaxp的时候会出错，因为jaxp的源代码是临时下载的

  8.3 OpenJDK 目录下的README-builds.html网页文档可以提供很多帮助.



9. 编译完成

  -- Build times ----------

  Target debug_build

  Start 2012-02-18 21:09:17

  End   2012-02-18 21:54:06

  00:01:04 corba

  00:20:53 hotspot

  00:03:57 jaxp

  00:04:14 jaxws

  00:14:15 jdk

  00:00:26 langtools

  00:44:49 TOTAL

  在OpenJDK目录下有个build文件夹是编译的output文件夹

  azrael@ubuntu :~/Tech/openjdk$ cd build/

  azrael@ubuntu :~/Tech/openjdk/build$ ll

  total 16

  drwxr-xr-x  4 azrael azrael 4096 Feb 18 20:37 ./

  drwxr-xr-x 12 azrael azrael 4096 Feb 18 20:35 ../

  drwxr-xr-x 27 azrael azrael 4096 Feb 18 21:53 linux-i586/

  drwxr-xr-x 26 azrael azrael 4096 Feb 18 21:08 linux-i586-debug/

  azrael@ubuntu :~/Tech/openjdk/build/linux-i586/bin$ ./java -version

  openjdk version "1.7.0-internal-debug"

  OpenJDK Runtime Environment (build 1.7.0-internal-debug-azrael_2012_02_18_20_37-b00)





### 下面就是解决make出现的各种问题和解决方案：

 

1. ERROR: You do not have access to valid Cups header files.

需要安装cpus的dev包

sudo apt-get install libcups2-dev

2. ERROR: The version of ant being used is older than the required version of '1.7.1'. The version of ant found was ''.

这说明没有装ant

sudo apt-get install ant

3. ERROR: FreeType version 2.3.0 or higher is required.



安装freetype的dev包

sudo apt-get install libfreetype6-dev

4. ERROR: You seem to not have installed ALSA 0.9.1 or higher.

不需要从ALSA官网下载alsa-dev和alsa-drive， ubuntu提供包的

sudo apt-get install libasound2-dev

5. ERROR:
```
 echo "*** This OS is not supported:" 'uname -a'; exit 1;
```
很奇怪的错误，anyway，注释掉hotspot/make/linux/Makefile里面的checkOS check_os_version:

```xml
#ifeq ($(DISABLE_HOTSPOT_OS_VERSION_CHECK)$(EMPTY_IF_NOT_SUPPORTED),)

# $(QUIETLY) >&2 echo "*** This OS is not supported:" `uname -a`; exit 1;

#endif
```


Update: 最好的办法是在make参数后面添加 DISABLE_HOTSPOT_OS_VERSION_CHECK=OK 即可

 

6. ERROR: error: "__LEAF" redefined [-Werror]



这个是已知的bug(http://hg.openjdk.java.net/hsx/hotspot-comp/hotspot/rev/a6eef545f1a2), 在hopspot下打入该patch(http://hg.openjdk.java.net/hsx/hotspot-comp/hotspot/raw-rev/a6eef545f1a2)即可



7. ERROR error: converting ‘false’ to pointer type ‘methodOop’ [-Werror=conversion-null]



这个的问题是把 false 转换成 NULL的时候出错了

同样在hotspot下 打入该 patch(http://hg.openjdk.java.net/hsx/hotspot-rt/hotspot/raw-rev/f457154eee8b)

 

8. ERROR gcc: error: unrecognized command line option '-mimpure-text'

这个-mimpure-text是gcc给Solaris的编译选项，所以注释掉即可

文件在./jdk/make/common/shared/Compiler-gcc.gmk +70



9. ERROR undefined reference to 'snd_pcm_format_**'



folow this link Build openjdk in Ubuntu 11.10

 

在jdk/make/javax/sound/jsoundalsa/Makefile 里面

10. echo "**NOTICE** Dtrace support disabled: "/usr/include/sys/sdt.h not found""

**NOTICE** Dtrace support disabled: /usr/include/sys/sdt.h not found

make[6]: Leaving directory `/home/xzg/advance/JVM/jdkBuild/openjdk_7/build/hotspot/outputdir/linux_amd64_compiler2/product'

All done.

make[5]: Leaving directory `/home/xzg/advance/JVM/jdkBuild/openjdk_7/build/hotspot/outputdir/linux_amd64_compiler2/product'

cd linux_amd64_compiler2/product && ./test_gamma

./gamma: relocation error: /usr/lib/jvm/java-6-openjdk-amd64/jre/lib/amd64/libjava.so: symbol JVM_FindClassFromCaller, version SUNWprivate_1.1 not defined in file libjvm.so with link time reference



删除hotspot/make/linux/Makefile中所有的test_gamma ( 即删掉所有的&& ./test_gamma)

11. Error: time is more than 10 years from present: 1136059200000

java.lang.RuntimeException: time is more than 10 years from present: 1136059200000

	at build.tools.generatecurrencydata.GenerateCurrencyData.makeSpecialCaseEntry(GenerateCurrencyData.java:285)

	at build.tools.generatecurrencydata.GenerateCurrencyData.buildMainAndSpecialCaseTables(GenerateCurrencyData.java:225)

	at build.tools.generatecurrencydata.GenerateCurrencyData.main(GenerateCurrencyData.java:154)

make[4]: *** [/home/xzg/advance/JVM/jdkBuild/openjdk_7/build/lib/currency.data] Error 1





time is more than 10 years from present: 1136059200000

 将CurrencyData.properties内的时间改为10年内

jdk/src/share/classes/java/util/CurrencyData.properties

 解决办法

 修改CurrencyData.properties（路径：jdk/src/share/classes/java/util/CurrencyData.properties）

修改108行

AZ=AZM;2015-12-31-20-00-00;AZN

修改381行

MZ=MZM;2015-06-30-22-00-00;MZN

修改443行

RO=ROL;2015-06-30-21-00-00;RON

修改535行

TR=TRL;2015-12-31-22-00-00;TRY

修改561行

VE=VEB;2015-01-01-04-00-00;VEF



==========================================================编译成功如下

#-- Build times ----------

Target all_product_build

Start 2017-03-01 03:04:56

End   2017-03-01 03:52:02

00:00:05 corba

00:00:07 hotspot

00:00:02 jaxp

00:00:04 jaxws

00:46:45 jdk

00:00:03 langtools

00:47:06 TOTAL

-------------------------

make[1]: Leaving directory `/home/xzg/Downloads/openjdk'


