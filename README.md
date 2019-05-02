# client-server-demo

The purpose of this project is to compare and demonstrate different technologies for client-server communication 
for control system components.

### Pure REST

* - No notification possible

### Spring Webflux

[Server-Sent events](https://en.wikipedia.org/wiki/Server-sent_events) seem to be very useful for many applications: 
* Works nicely out of the box. The browser shows some nice updates immediately.
* Easy for variable number of endpoints
* Seems to reconnect automatically (to be tested for java)

RestController method in spring would look somehow like this:
```java
@GetMapping(value = "/measuredTunes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<Tune> measuredTunes() {
    // whatever here
}
```

The client code in javascript:
```javascript
var source = new EventSource("http://" + location.host + "/measuredTunes");
source.onmessage = e => {
    console.log(e.data);
};
```

* High data rates?

### Websockets

* Good for high data rates
* Endpoints to be known on startup
* Some more in formation for when to use can be found in the [spring docs](https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html#websocket-intro-when-to-use).

### ossgang-properties

* combines REST + websockets.
* particularly useful when setable and getable