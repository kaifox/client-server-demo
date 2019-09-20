import requests
import json
import sseclient
import threading


# some utility methods

def api_url(path):
    return "http://localhost:8080/api" + path;


def sse_stream(path):
    resp = requests.get(api_url(path), stream=True)
    return map(lambda e: json.loads(e.data), sseclient.SSEClient(resp).events())


def get(path):
    result = requests.get(api_url(path))
    return result.json()


def post(path):
    requests.post(api_url(path))


# API access

def get_tune():
    tune = get("/measuredTune")
    print("measured tune from get: ", tune)


def set_stddev(stddev):
    print("setting stddev to {}.".format(stddev))
    post("/standardDev/" + str(stddev))


def get_tunes():
    return sse_stream("/measuredTunes")


def listen_to_updates(count):
    for i, t in zip(range(count), get_tunes()):
        print("index=" + str(i), "tune=" + str(t))


if __name__ == '__main__':
    set_stddev(0.01)
    get_tune()

    set_stddev(0.2)
    get_tune()

    threading.Thread(target=lambda: listen_to_updates(10)).start()

    print("waiting for updates...")
