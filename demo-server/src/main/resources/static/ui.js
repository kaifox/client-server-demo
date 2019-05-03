Highcharts.setOptions({global: { useUTC: false } });
function parseJSON(msg) {
	return JSON.parse(msg.data.replace(/(NaN|Infinity)/ig, null));
}

$('#getTuneButton').click(e => {
	$.get("http://" + location.host + "/api/measuredTune", msg => {
		$('#tuneValue').text(JSON.stringify(msg));
	  });
})

var source = new EventSource("http://" + location.host + "/api/measuredTunes");
source.onmessage = e => {
		$('#testTune').text(e.data);
};


$('#getStandardDevButton').click(e => {
	$.get("http://" + location.host + "/api/standardDev", msg => {
		$('#standardDev').value = msg;
	  });
})

$('#setStandardDevButton').click(e => {
	v = $('#standardDev').value;
	$.post("http://" + location.host + "/api/standardDev/" + v);
})


