#include "root.h"

using namespace Cutelyst;

Root::Root(QObject *parent) : Controller(parent)
{
}

Root::~Root()
{
}

void Root::index(Context *c)
{
    c->response()->body() = "Welcome to Cutelyst REST Demo!<br/>For the debug GUI, please go to <a href='./static/index.html'>here</a>.";
}

void Root::defaultPage(Context *c)
{
    c->response()->body() = "Page not found!";
    c->response()->setStatus(404);
}

