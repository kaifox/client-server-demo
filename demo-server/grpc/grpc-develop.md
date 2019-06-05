

```
npm init
npm install watchify
npm install browserify
npm install grpc-web
npm install google-protobuf
```


to get files reloaded automatically
``` 
watchify ui.js -o bundle.js
```

To generate web stubs:
* [generate-web.bat](../../demo-commons/generate-web.bat)


To run the envoy proxy:

* Install docker
* from this dir run `docker build -t envoy:v1 .` (envoy config copied from [here](https://raw.githubusercontent.com/envoyproxy/envoy/master/examples/grpc-bridge/config/s2s-grpc-envoy.yamlhttps://raw.githubusercontent.com/envoyproxy/envoy/master/examples/grpc-bridge/config/s2s-grpc-envoy.yaml) ... no real clue what that does ;-) 
* docker run -d --name envoy -p 9901:9901 -p 10000:10000 envoy:v1


Why all that?

* https://grpc.io/blog/state-of-grpc-web/
* gRPC needs a proxy to convert between gRPC web and a native protocol. https://github.com/grpc/grpc-web/issues/347
* If no proxy is used, one gets an error like this on the server: `io.netty.handler.codec.http2.Http2Exception: Unexpected HTTP/1.x request: OPTIONS /demo.TuneService/GetMeasuredTune` 
* https://stackoverflow.com/questions/44877606/is-grpchttp-2-faster-than-rest-with-http-2