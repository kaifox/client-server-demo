
Highcharts.setOptions({ global: { useUTC: false } });
function parseJSON(msg) {
	return JSON.parse(msg.data.replace(/(NaN|Infinity)/ig, null));
}

/**
 * Simple REST-only scenario. GET-only in this case.
 */

$('#getTuneButton').click(e => {
	$.get("http://" + location.host + "/api/measuredTune", msg => {
		$('#tuneValue').text(JSON.stringify(msg));
	});
})


/**
 * A simple update scenario, using server sent events.
 */

var source = new EventSource("http://" + location.host + "/api/measuredTunes");
source.onmessage = e => {
	$('#testTune').text('from SSE: ' + e.data);
};


/**
 * The following is a simple get/set scenario: a value (standardDeviation) is
 * retrieved from the server via GET requests and is set via a POST request.
 */

function updateStandardDev() {
	$.get("http://" + location.host + "/api/standardDev", msg => {
		$('#standardDevInput').val(msg);
	});
}

updateStandardDev();
$('#getStandardDevButton').click(e => {
	updateStandardDev();
})

$('#setStandardDevButton').click(e => {
	v = $('#standardDevInput').val();
	$.post("http://" + location.host + "/api/standardDev/" + v);
})

/**
 * The following subscribes to websockets on the server.
 */

var wsTune = new WebSocket("ws://" + location.host + "/ws/measuredTunes");
wsTune.onmessage = (msg) => {
	$('#wsTune').text('from WS: ' + msg.data);
}


/**
 * gRPC part
 */

const { MeasuredTuneRequest, MeasuredTuneReply, StandardDevRequest, StandardDevReply } = require('./tune_pb.js');
const { TuneServiceClient } = require('./tune_grpc_web_pb.js');

var tuneService = new TuneServiceClient('http://localhost:5353');


$('#grpcGetTuneButton').click(e => {
	var request = new proto.demo.MeasuredTuneRequest();
	tuneService.getMeasuredTune(request, {}, (err, response) => {
		console.log(response);
	});
})





