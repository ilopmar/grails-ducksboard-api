grails-ducksboard-api
=====================

This plugin allows to use the push/pull [Ducksboard.com](http://www.ducksboard.com) API easily in your grails application.

Ducksboard is an online dashboard to display all your metrics in real time. It supports a lot of services like Twitter, Google Analitycs, Feedburner, MailChimp, Zendesk,...

After installing the plugin you only have to inject the `ducksboardService` into your controllers, taglib, services,... to use it.

For the moment, the methods implemented are only for the custom widgets. You can get a list of them at http://dev.ducksboard.com/apidoc/slot-kinds/

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


Author
------

You can send any question to Iván López: lopez (dot) ivan (at) gmail (dot) com

Collaborations are appreciated :-)


Change Log
----------  

v0.2 - 12/Jul/2012 - Changed Integer values to Long.
                     Added method to push values to a Leaderboard widget 
v0.1 - 16/Jun/2012 - First version