grails-ducksboard-api
=====================

This plugin allows to use the push/pull [Ducksboard.com](http://www.ducksboard.com) API easily in your grails application.

Ducksboard is an online dashboard to display all your metrics in real time. It supports a lot of services like Twitter, Google Analitycs, Feedburner, MailChimp, Zendesk,...

After installing the plugin you only have to inject the `ducksboardService` into your controllers, taglib, services,... to use it.

For the moment, the methods implemented are only for the custom widgets. You can get a list of them at http://dev.ducksboard.com/apidoc/slot-kinds/

There is a sample project with examples of all the widgets at [https://github.com/lmivan/grails-ducksboard-api-demo](https://github.com/lmivan/grails-ducksboard-api-demo). This sample project includes a public dashboard and a online grails project to interact with the dashboard.

Custom widgets
--------------
![Counter](http://dev.ducksboard.com/apidoc/_images/counter.png)

![Gauges](http://dev.ducksboard.com/apidoc/_images/gauge2.png)

![Bars](http://dev.ducksboard.com/apidoc/_images/bars.png)

![Boxes](http://dev.ducksboard.com/apidoc/_images/boxes.png)

![Pin](http://dev.ducksboard.com/apidoc/_images/pins.png)

![Absolute graph](http://dev.ducksboard.com/apidoc/_images/absolute_graph3.png)

![Relative graph](http://dev.ducksboard.com/apidoc/_images/relative_graph3.png)

![Leaderboards](http://dev.ducksboard.com/apidoc/_images/leaderboard.png)

![Timeline](http://dev.ducksboard.com/apidoc/_images/timeline.png)

![Images](http://dev.ducksboard.com/apidoc/_images/image.png)

Configuration
-------------

You have to include the following properties in your Config.groovy file:

    ducksboard {
        user = "YOUR-USER-API-KEY"
        password = "NOT-USED"
    }

The password is not currently used so you can skip this property but we keep it for future use.


Ducksboard pull api methods
---------------------------

Currently only one pull method is implemented:
* `pullLongValue(widgetId)`: This method is used to get the last value of the following widgets: counters, bars, boxes and pins.


Ducksboard push api methods
---------------------------

The following methods are implemented:
* `pushLongValue(String widgetId, Long value)`: This method is used to push a new long value to update the following widgets: counters, bars, boxes and pins.
* `pushDoubleValue(String widgetId, Double value)`: This method is used to push a new double value to update the gauge widgets.
* `incrementLongValue(String widgetId, Long increment = 1)`: This method is user to increment (or decrease) the value of the following widgets: counters, bars, boxes and pins.
* `pushTimestampValues(String widgetId, List list)`: This method is used to push new values to a absolute or relative graph. The `list` param has to be a list of maps with the keys `timestamp` and `value`. You can find a detailed explication at http://dev.ducksboard.com/apidoc/slot-kinds/#absolute-graphs
* `pushLeaderboardValues(String widgetId, List list)`: This method is used to push new values to a leaderboard widget. The `list` param has to be a list of maps with the keys `name` and `values`. You can find a detailed explication at http://dev.ducksboard.com/apidoc/slot-kinds/#leaderboards
* `pushTimelineValues(String widgetId, Map map)`: This method is used to push a new value to a timeline widget. The `map` param contains the following params `timeline` and `value` (with `title`, `image`, `content` and optionally `link`). You can find a detailed explication at http://dev.ducksboard.com/apidoc/slot-kinds/#timelines
* `pushImage(String widgetId, Map map)`: This method is used to push an image to an image widget. The `map` param contains the following params `timeline` and `value` (with `source`, base64 encoded image, and `caption`). You can find a detailed explication at http://dev.ducksboard.com/apidoc/slot-kinds/#images
* `pushImage(String widgetId, File file)`: This method is used to push an image to an image widget. The `file` param points to the image we want to push. With this method it is not possible to and the caption to the picture.
  


Author
------

You can send any question to Iván López: lopez (dot) ivan (at) gmail (dot) com

Collaborations are appreciated :-)


Change Log
----------  

* v0.2 - 12/Jul/2012 - Changed Integer values to Long. Added methods to push values to a Leaderboard and a Timeline widget and to push Images 
* v0.1 - 16/Jun/2012 - First version