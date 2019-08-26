import json
from flask import Flask, Response
import time
import random

app = Flask(__name__, static_url_path='')


# Simulation part

class TuneSimulator(object):

    def __init__(self):
        self.nominal_tune = 0.31
        self.std_dev = 0.01;

    def actual_tune(self):
        return random.gauss(self.nominal_tune, self.std_dev);

    def set_std_dev(self, stddev):
        self.std_dev = stddev

    def set_nominal(self, nominal):
        self.nominal_tune = nominal;

    def get_std_def(self):
        return self.std_dev

    def get_nominal_tune(self):
        return self.nominal_tune


simulator = TuneSimulator()

def tuneDto():
    return {"value": simulator.actual_tune(), "error": simulator.get_std_def()}

def tunes():
    while True:
        yield tuneDto()
        time.sleep(1)


# Conversion stuff

def json_response(obj):
    return Response(json.dumps(obj), mimetype='application/json')

def sse_response(iterator):
    return Response(('data: {0}\n\n'.format(json.dumps(o)) for o in iterator), mimetype='text/event-stream')

def empty_response():
    return Response("{}", mimetype='application/json')

# Endpoints

@app.route("/api/measuredTune")
def measured_tune():
    return json_response(tuneDto())

@app.route("/api/measuredTunes")
def measured_tunes():
    return sse_response(tunes())

@app.route("/api/standardDev")
def standard_dev():
    return json_response(simulator.get_std_def())

@app.route("/api/standardDev/<stddev>", methods=["POST"])
def set_standard_dev(stddev):
    simulator.set_std_dev(float(stddev))
    return empty_response()

@app.route("/")
def root():
    return app.send_static_file("index.html")



if __name__ == '__main__':
    app.run(port=8080, debug=True, threaded=True)