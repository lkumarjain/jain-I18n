Jain-I18n
=========

`JainI18N` is a pure server-side extension to a great Vaadin framework, providing extra collection of common use cases usually used in almost any vaadin-based web application. There are couple of libraries available to support these features, but either they are missing some of the use cases or they need component registration logic to be written by the developer. This library aims to help you save development time by reusing pre-defined components :

◆ I18N (Internationalization) support :<br/>
	`I18N` support makes your application international compliant by using pure Java messaging API (i. e.  resource bundle). This library almost support all the Vaadin components.<br/>
<br/>
This library require few configuration to provide `I18N` support in your application<br/>
✔ Use `I18NUI` instead of the UI.<br/>
✔ Use `I18NWindow` instead of Window.<br/>
<br/>
This library also allows you to defining your own `I18NComponentHandler` for a specific component.<br/>
✔ Register/De-register your component handler by calling register/deRegister static method of I18NComponentHandlerFactory in initialize method of ApplicationUI class.<br/>


◆ I18N (Internationalization) change listener :<br/>
	Allows you to customize component data and other attributes on local change.<br/>
✔ You can achieve this functionality in the application by implementing `I18NChangeListener`  interface on your component.<br/>


◆ Component Initialization :<br/>
	Allows you to initialize component after creation similar to post construct call back in EJB. This initialization happens during `I18N` component registration API.<br/>
✔ You can achieve this functionality by annotating your initialization method by `@JNIComponentInit` annotation.<br/>


◆ Image Component :<br/>
	Allows you to upload image with instant view in the form.<br/>
✔ You can achieve this by adding `JImage` component in your application.<br/>


◆ Login event listener :<br/>
	Allows you to call a method in your component after login event accures.<br/>
✔ You can achieve this functionality by implementing `JNILoginListenr` on your component.<br/>
✔ This requires you to call onLogin static method of the `JLoginHandler` by passing UI object.<br/>


◆ Observer Design Pattern :<br/>
	Allows you to add observers and fire an event to call these observable methods.<br/>
✔ Observers defined in each component will be called after firing an event in from the application.<br/>
✔ You can define observable method by annotating `@JNIObserver` annotation.<br/>


◆ Action / Menu bar :<br/>
	Allows you to create action/menu bar with or without grouping by annotations.<br/>
✔ You can achieve this functionality by annotating your action method with `@JNAction` annotation.<br/>
✔ Also, you can group your actions by annotating  action provider component or class by annotating them with `@JNGroups` or `@JNGroup` annotations.<br/>
✔ This also allows you to add security to all these action methods to control visibility. You can achieve security by providing a security provider which should implemnet `JNISecured`.<br/>
✔ Also allows you to add confirmation dialog for any action.
