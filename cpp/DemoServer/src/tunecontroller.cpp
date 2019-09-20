#include "tunecontroller.h"
#include <QSettings>
#include <QJsonArray>
#include <QJsonObject>
#include <QDebug>

using namespace Cutelyst;

Api::Api(QObject *parent) : Controller(parent)
{
}

Api::~Api()
{
}

void Api::index(Context *c)
{
    c->response()->body() = "Matched Controller::Api in Api.";
}

void Api::measuredTune(Context* c)
{
	Tune tune = simulator->nextValue();

	QJsonObject json;
	json.insert("value", QJsonValue::fromVariant(tune.getValue()));
	json.insert("error", QJsonValue::fromVariant(tune.getError()));
	
	c->response()->setJsonObjectBody(json);
}


void Api::standardDev(Context* c, const QString& stdDev)
{
	if (c->request()->isGet()) {
		qDebug() << "GET stdDev";
		c->response()->body() = QByteArray::fromStdString( std::to_string(simulator->getStdDev()));
		return;
	}
	if (c->request()->isPost()) {
		qDebug() << "POST stdDev";
		double value = std::stod(stdDev.toStdString());
		simulator->setStdDev(value);
		c->response()->body() = "ok";
		return;
	}
}

