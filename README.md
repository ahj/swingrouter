# swingrouter
A web-inspired router and history implementation for Java Swing UI's.

Back-porting some modern Web navigation concepts to old Java Swing applications made we wish for something like the router pattern in modern single-page web applications.

So here is a basic History and Router implementation in java that let's you build an application in a web-like way.
There is a Route annotation that you can tag Controller/View's with.
And then call the Router's redirectTo method to switch views.

The test application adds a back, forward and address bar to give control of the navigation history.

This lets all the navigable pieces of your application be tagged which increases visibility.
It also standardizes the way in which navigation performed.

Simple proof-of-concept.
