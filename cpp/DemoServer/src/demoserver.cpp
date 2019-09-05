#include "demoserver.h"

#include "root.h"
#include "tunecontroller.h"
#include <Cutelyst/Plugins/StaticSimple/staticsimple.h>

using namespace Cutelyst;

DemoServer::DemoServer(QObject *parent) : Application(parent)
{
}

DemoServer::~DemoServer()
{
}

bool DemoServer::init()
{
    new Root(this);
	new StaticSimple(this);
	new Api(this);

    return true;
}

