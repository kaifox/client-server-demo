### Creating a new cutelyst app on windows with qtcreator

## Preconditions 

* scoop installed
* cmake installed

# Installing cutelyst
* Clone the cutelyst repo
* create a build dir next to it (e.g. cutelyst-build)
* within cutelyst-build, execute:
```
cmake ../cutelyst
cmake --build . --config Release
cmake --build . --target INSTALL --config Release
```


```
cutelyst2 --create-app DemoServer
cd DemoServer/build
cmake .. 
cmake --build . --target ALL_BUILD --config Release 
```

run the server by
```
cutelyst2 -r --server --app-file $PWD/src/Release/DemoServer.dll --server-port 8080
```

Then go in the browser to 
```
http://localhost:8080
```