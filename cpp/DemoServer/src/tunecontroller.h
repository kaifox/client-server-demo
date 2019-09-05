#ifndef TUNECONTROLLER_H
#define TUNECONTROLLER_H

#include <Cutelyst/Controller>
#include "TuneSimulator.h"


using namespace Cutelyst;

class Api : public Controller
{
    Q_OBJECT

private:
	TuneSimulator *simulator = new TuneSimulator();

public:
    explicit Api(QObject *parent = nullptr);
    ~Api();

    C_ATTR(index, :Path :AutoArgs)
    void index(Context *c);

	C_ATTR(measuredTune, :Local)
	void measuredTune(Context* c);

	// standard dev get/post
	C_ATTR(standardDev, :Local )
	void standardDev(Context* c, const QString& stdDev);

};

#endif //TUNECONTROLLER_H

