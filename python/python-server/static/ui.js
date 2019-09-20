
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







