#pragma once

#include <random>

class Tune {
private:
	double value;
	double error;


public:

	Tune(double _value, double _error) {
		value = _value;
		error = _error;
	}

	double getValue() {
		return value;
	}

	double getError() {
		return error;
	}


};

class TuneSimulator
{


private:
	double mean = 0.31;
	double stdDev = 0.01;

	std::random_device rd{};
	std::mt19937 gen{ rd() };

	std::normal_distribution<> dist{ mean , stdDev };

public:
	TuneSimulator::TuneSimulator() {};
	TuneSimulator::~TuneSimulator() {};

	void setStdDev(double _stdDev) {
		stdDev = _stdDev;
		dist = std::normal_distribution<>( mean, _stdDev );
	}

	double getStdDev() {
		return stdDev;
	}

	Tune nextValue() {
		return Tune(dist(gen), stdDev);
	}


};

