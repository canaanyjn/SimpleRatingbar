SimpleRatingBar is a simple android ratingBar. It's implemented by use path to draw rate, in order 
to easily control the rating bar's height and width in different size mobile.

Getting started
----
in your module's build.gradle

```
 compile 'com.canaan.library:ratingBarLibrary:0.1'
```

Usage
----

In yout layout file 


    <com.canaan.library.SimpleRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:rating="1.7"
        app:starColor="#aacc11"
        app:starHeight="40dp"
        app:starWidth="40dp"
        app:starSpace="10"
        app:stepSize="0.5"
        app:isStarClicable="true"
        app:numStar="5"
    />


