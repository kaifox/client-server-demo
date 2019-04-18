# client-server-demo

The purpose of this project is to compare and demonstrate different technologies for client-server communication 
for control system components.

### Pure REST

* - No notification possible

### Spring Webflux

* Long polling: Works nicely out of the box. Simple on the client side...
* Easy for variable number of endpoints
* High data rates?
* Reconnection?

### Websockets

* Good for high data rates
* Endpoints to be known on startup
* Some more in formation for when to use can be found in the [spring docs](https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html#websocket-intro-when-to-use).

### ossgang-properties

* combines REST + websockets.
* particularly useful when setable and getable