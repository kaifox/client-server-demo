#ifndef DEMOSERVER_H
#define DEMOSERVER_H

#include <Cutelyst/Application>

using namespace Cutelyst;

class DemoServer : public Application
{
    Q_OBJECT
    CUTELYST_APPLICATION(IID "DemoServer")
public:
    Q_INVOKABLE explicit DemoServer(QObject *parent = nullptr);
    ~DemoServer();

    bool init();
};

#endif //DEMOSERVER_H

