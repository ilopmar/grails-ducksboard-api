Grails Ducksboard API
=====================

[![Build Status](https://drone.io/github.com/lmivan/grails-ducksboard-api/status.png)](https://drone.io/github.com/lmivan/grails-ducksboard-api/latest)
[![Still maintained](http://stillmaintained.com/lmivan/grails-ducksboard-api.png)](http://stillmaintained.com/lmivan/grails-ducksboard-api)
[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/lmivan/grails-ducksboard-api/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

This plugin allows to use the push/pull [Ducksboard.com](http://www.ducksboard.com) API easily in your grails application.

Ducksboard is an online dashboard to display all your metrics in real time. It supports a lot of services like Twitter, Google Analitycs, Feedburner, MailChimp, Zendesk,...

After installing the plugin you only have to inject the `ducksboardService` into your controllers, taglib, services,... to use it.

For the moment, the methods implemented are only for the custom widgets. You can get a list of them at [http://dev.ducksboard.com/apidoc/slot-kinds](http://dev.ducksboard.com/apidoc/slot-kinds)

There is a sample project with examples of all the widgets at [https://github.com/lmivan/grails-ducksboard-api-demo](https://github.com/lmivan/grails-ducksboard-api-demo). This sample project includes a [public dashboard](https://public.ducksboard.com/UcD_CtknoMaEDbIJo8Eo/) and an online grails project to interact with the dashboard.

## Warning

In version 0.4 most of the public methods have changed its signature to support all different options for custom widgets, so checkout before update to this version.


## Configuration

You have to include the following properties in your Config.groovy file:

```
ducksboard {
    user = "YOUR-USER-API-KEY"
    password = "NOT-USED"
}
```

The password is not currently used so you can skip this property but we keep it for future use.


## Ducksboard pull api methods

Currently only one pull method is implemented to get the last value of the following widgets: counters, bars, boxes and pins.

```
ducksboardService.pullLongValue(String widgetId)
```

For example, to get the current value of a bar widget:

```
def value = ducksboardService.pullLongValue("938372")
```

## Ducksboard push api methods

### Counters, bars, boxes and pins

![Counter](http://dev.ducksboard.com/apidoc/_images/counter.png)
![Bars](http://dev.ducksboard.com/apidoc/_images/bars.png)
![Boxes](http://dev.ducksboard.com/apidoc/_images/boxes.png)
![Pin](http://dev.ducksboard.com/apidoc/_images/pins.png)

To push a new value of the previous widgets you have to use the following method:

```
ducksboardService.pushLongValue(String widgetId, Long value)
```

For example, with the following call the widget will be updated to 1423

```
ducksboardService.pushLongValue("87463", 1423)
```


### Update values in counters, bars, boxes and pins

To update the value of a counter, bar, box and pin you can use the following method

```
ducksboardService.pushLongDelta(String widgetId, Long delta = 1)
```

For example:

```
// Update the value of the widget 16548
ducksboardService.pushLongDelta("16548")

// Decrement in 10 units the value of the widget "98798"
ducksboardService.pushLongDelta("98798", -10)
```


### Gauges

![Gauges](http://dev.ducksboard.com/apidoc/_images/gauge2.png)

To push a new value of a gauge you have to use the following method:

```
ducksboardService.pushDoubleValue(String widgetId, Double value)
```

For example, you can update the value to 75% with the call

```
ducksboardService.pushDoubleValue("56987", 0.75)
```



### Graphs (intervals of time)

![Absolute](http://dev.ducksboard.com/apidoc/_images/absolute_graph3.png)
![Absolute area](http://dev.ducksboard.com/apidoc/_images/absolute_area_graph.png)
![Stacked](http://dev.ducksboard.com/apidoc/_images/stacked_graph.png)
![Relative](http://dev.ducksboard.com/apidoc/_images/relative_graph3.png)
![Relative area](http://dev.ducksboard.com/apidoc/_images/relative_area_graph.png)

To push values to this widgets you have to use the following method:

```
ducksboardService.pushTimestampValues(String widgetId, List<Long> timestamps, List<Long> values)
```

For example:

```
def timestamps = [1337724000, 1337810400]
def values = [10, 25]

ducksboardService.pushTimestampValues("69871", timestamps, values)
```


### Timelines

![Timeline](http://dev.ducksboard.com/apidoc/_images/timeline.png)

To push values to a timeline widget you can use this method:

```
ducksboardService.pushTimelineValues(String widgetId, String title, String content, String imageUrl, Long timestamp = new Date().time/1000)
```

For example:

```
ducksboardServoce.pushTimelineValues("46587", "error message", "Some details about my error message", "https://app.ducksboard.com/static/img/timeline/red.gif")
```

As you can see, the `timestamp` params is optional and if you don't send it it will use the actual time.


### Leaderboards and Trend-Leaderboards

![Leaderboards](http://dev.ducksboard.com/apidoc/_images/leaderboard.png)
![TrendLeaderboards](http://dev.ducksboard.com/apidoc/_images/leaderboard-trend1.png)

To push values you can use the method:

```
ducksboardService.pushLeaderboardValues(String widgetId, List<String> names, List values)
```

For example:

```
def names = ["Abdul-Jabbar", "Karl Malone", "Michael Jordan", "W. Chamberlain", "Kobe Bryant"]
def values = [[38387, 1560, 24.6], [36928, 1476, 25], [32292, 1072, 30.1], [31419, 1045, 30.1], [29484, 1161, 25.4]]

ducksboardService.pushLeaderboardValues("48978", names, values)
```

### Status Leaderboards

![Status Leaderboards](http://dev.ducksboard.com/apidoc/_images/leaderboard-status.png)

To push values you can use the method:

```
ducksboardService.pushStatusLeaderboardValues(String widgetId, List<String> names, List values, List<LeaderboardStatusValues> status)
```

For example:

```
def names = ["Cleveland", "New York", "Boulder", "Newport"]
def values = [[1928, "96%"], [1232, "84%"], ["--", "--"], [2740, "60%"]]
def status = [LeaderboardStatusValues.GREEN, LeaderboardStatusValues.YELLOW, LeaderboardStatusValues.GRAY, LeaderboardStatusValues.RED]

ducksboardService.pushStatusLeaderboardValues("229812", names, values, status)
```


### Images

![Images](http://dev.ducksboard.com/apidoc/_images/image.png)

To push a new image to a dashboard you have to use this method:

```
ducksboardService.pushImage(String widgetId, File file, String caption = "", Long timestamp = new Date().time/1000)
```

For example:

```
ducksboardService.pushImage("458798", theFile, "The caption")
```


### Status

![Status](http://dev.ducksboard.com/apidoc/_images/status.png)

To push a new status you can use:

```
ducksboardService.pushStatus(String widgetId, StatusValues status, Long timestamp = new Date().time/1000)
```

For example:

```
ducksboardService.pushStatus("12323", StatusValues.WARNING)
```

### Text

![Text](http://dev.ducksboard.com/apidoc/_images/text.png)

To push text to a widget text you have to use this method:

```
ducksboardService.pushText(String widgetId, String text, Long timestamp = new Date().time/1000)
```

For example:

```
ducksboardService.pushText("56987", "Text!\nLorem ipsum dolor sit amet...")
```

### Funnel

![Funnels](http://dev.ducksboard.com/apidoc/_images/funnel.png)

To push values to a funnel widget:

```
ducksboardService.pushFunnel(String widgetId, List<String> names, List<Long> values)
```

For example:

```
def names = ['STEP 1', 'STEP 2', 'STEP 3', 'STEP 4', 'STEP 5', 'STEP 6']
def values = [1600, 1400, 1200, 900, 600, 330]

ducksboardService.pushFunnel("76336", names, values)
```

### Completion

![Completion](http://dev.ducksboard.com/apidoc/_images/completion_gauge.png)

To push new values to a completion widget you can use this method:

```
ducksboardService.pushCompletion(String widgetId, Long minimum, Long maximum, Long value)
```

For example:

```
ducksboardService.pushCompletion("293842", 0, 25000, 19790)
```

Author
------

You can send any question to Iván López: lopez (dot) ivan (at) gmail (dot) com

Collaborations are appreciated :-)


Change Log
----------

* v0.4 - 06/Jul/2013 - Change the signature of the methods to support all custom widgets params and add spock tests.
* v0.3 - 06/Jul/2013 - Add new widgets: status, text, funnel and completion.
* v0.2.2 - 15/Aug/2012 - Add new method to support delta updates and deprecated the old method.
* v0.2.1 - 12/Jul/2012 - Minor changes.
* v0.2 - 12/Jul/2012 - Change Integer values to Long. Add methods to push values to a Leaderboard and a Timeline widget and to push Images.
* v0.1 - 16/Jun/2012 - First version.
