### Python client

This simple python client demonstrates tha access to a server, using REST and SSE.

It is compatible with the rest of the demo applications.

All the source is included within one source file: [rest-client.py](rest-client.py). 

To run it, simply use:

``` python rest-client.py```

The client goes per default to `http://localhost:8080`. It is a simple text client, 
which twice sets the standard deviation and then subscribes to the SSE stream, limiting the
amount to 10 updates.

This version uses the following libraries:

* [requests](https://2.python-requests.org/en/master/) for the standard http calls.
* [sseclient-py](https://github.com/mpetazzoni/sseclient) in combination with the requests lib for streaming server sent events.
 