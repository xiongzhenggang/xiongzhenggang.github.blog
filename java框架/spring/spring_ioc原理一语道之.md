http://www.zhihu.com/question/21346206/answer/101789659

来源：知乎

* <p>原理其实就是通过反射解析类及其类的各种信息，包括构造器、方法及其参数，属性。然后将其封装成bean定义信息类、constructor信息类、method信息类、property信息类，最终放在一个map里，也就是所谓的container、池等等，其实就是个map。当写好配置文件，启动项目后，框架会先按照你的配置文件找到那个要scan的包，然后解析包里面的所有类，找到所有含有@bean，@service等注解的类，利用反射解析它们，包括解析构造器，方法，属性等等，然后封装成各种信息类放到一个map里。每当你需要一个bean的时候，框架就会从container找是不是有这个类的定义啊？如果找到则通过构造器new出来（这就是控制反转，不用你new,框架帮你new），再在这个类找是不是有要注入的属性或者方法，比如标有@autowired的属性，如果有则还是到container找对应的解析类，new出对象，并通过之前解析出来的信息类找到setter方法，然后用该方法注入对象（这就是依赖注入）。
</p>
	如果其中有一个类container里没找到，则抛出异常，比如常见的spring无法找到该类定义，无法wire的异常。还有就是嵌套bean则用了一下递归，container会放到servletcontext里面，每次request从servletcontext找这个container即可，不用多次解析类定义。如果bean的scope是singleton，则会重用这个bean不再重新创建，将这个bean放到一个map里，每次用都先从这个map里面找。如果scope是session，则该bean会放到session里面。仅此而已。
