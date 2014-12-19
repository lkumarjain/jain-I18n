Jain-I18n
=========
<pre>
JainI18N is a pure server-side extension to a great Vaadin framework, providing extra collection of common use cases usually used in almost any vaadin-based web application. There are couple of libraries available to support these features, but either they are missing some of the use cases or they need component registration logic to be written by the developer. This library aims to help you save development time by reusing pre-defined components :

◆ I18N (Internationalization) support :
	I18N support makes your application international compliant by using pure Java messaging API (i. e.  resource bundle). This library almost support all the Vaadin components.  

This library require few configuration to provide I18N support in your application
✔ Use I18NUI instead of the UI.
✔ Use I18NWindow instead of Window.
	
This library also allows you to defining your own I18NComponentHandler for a specific component.
✔ Register/De-register your component handler by calling register/deRegister static method of I18NComponentHandlerFactory in initialize method of ApplicationUI class.

◆ I18N (Internationalization) change listener : 
	Allows you to customize component data and other attributes on local change.
✔ You can achieve this functionality in the application by implementing I18NChangeListener  interface on your component.

◆ Component Initialization :
	Allows you to initialize component after creation similar to post construct call back in EJB. This initialization happens during I18N component registration API.
✔ You can achieve this functionality by annotating your initialization method by @JNIComponentInit annotation.

◆ Image Component :
	Allows you to upload image with instant view in the form.
✔ You can achieve this by adding JImage component in your application.

◆ Login event listener :
	Allows you to call a method in your component after login event accures.
✔ You can achieve this functionality by implementing JNILoginListenr on your component.
✔ This requires you to call onLogin static method of the JLoginHandler by passing UI object.

◆ Observer Design Pattern :
	Allows you to add observers and fire an event to call these observable methods.
✔ Observers defined in each component will be called after firing an event in from the application.
✔ You can define observable method by annotating @JNIObserver annotation.

◆ Action / Menu bar :
	Allows you to create action/menu bar with or without grouping by annotations.
✔ You can achieve this functionality by annotating your action method with @JNAction annotation.
✔ Also, you can group your actions by annotating  action provider component or class by annotating them with @JNGroups or @JNGroup annotations.
✔ This also allows you to add security to all these action methods to control visibility. You can achieve security by providing a security provider which should implemnet JNISecured.
✔ Also allows you to add confirmation dialog for any action.
</pre>
