### Python server

This simple python server demonstrates a stateful server, controlled and observed
by REST and SSE.

It is compatible with the rest of the demo applications (despite it does not expose the testing endpoints).

All the source (including dummy-simulation) is included within one source file: [rest-server.py](rest-server.py). 

To run it, simply use:

``` python rest-server.py```

The server exposes its api on poirt 8080. Further, it comes with a small html+js GUI which is served by the server itself.
Therefore from any browser you should be able to access
[`http://localhost:8080`](http://localhost:8080) and should be able to start playing.

 